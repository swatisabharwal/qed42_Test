package com.qa.test.automation;

import java.io.File;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import org.openqa.selenium.remote.DesiredCapabilities;

import org.testng.Reporter;

public class WebDriverFactory {

	private String browser = "";

	public WebDriverFactory() {
	}

	public WebDriverFactory(String browserName) {
		browser = browserName;
	}

	//private static final DesiredCapabilities capabilities = new DesiredCapabilities();
	private static String seleniumserver = System.getProperty("seleniumserver");
	/*
	 * driver intialization for Firefox and Chrome
	 */
	public WebDriver getDriver(Map<String, String> seleniumconfig) {
		browser = System.getProperty("browser");
		if (browser == null || browser.isEmpty()) {
			browser = seleniumconfig.get("browser");
		} else if (browser.equalsIgnoreCase("firefox")) {
			return getFirefoxDriver();
		}
		System.out.println("browser is :" + seleniumconfig.get("browser"));
		Reporter.log("[INFO]: The test Browser is " + browser.toUpperCase() + " !!!", true);
		if (seleniumserver == null || seleniumserver.isEmpty() || seleniumserver.equals(" ")) {
			seleniumserver = seleniumconfig.get("seleniumserver");
		}
		if (seleniumserver.equalsIgnoreCase("local")) {
			if (browser.equalsIgnoreCase("chrome")) {
				return getChromeDriver(seleniumconfig.get("driverpath"));
			}
		}
		return new FirefoxDriver();
	}

	
	private static WebDriver getChromeDriver(String driverpath) {
		if (driverpath.endsWith(".exe")) {
			System.setProperty("webdriver.chrome.driver", driverpath);
		} else {
			System.setProperty("webdriver.chrome.driver", driverpath + "chromedriver.exe");
		}
		ChromeOptions options = new ChromeOptions();
		new File(System.getProperty("user.home") + File.separator + "Desktop" + File.separator
				+ "Redesign_Downloaded_Files").mkdir();
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		String downloadFilepath = System.getProperty("user.home") + File.separator + "Desktop" + File.separator
				+ "Redesign_Downloaded_Files";
		chromePrefs.put("download.default_directory", downloadFilepath);
		options.setExperimentalOption("prefs", chromePrefs);
		options.addExtensions(new File(System.getProperty("user.dir") + File.separator + "extension_1_5_294.crx"));
		options.addArguments("--disable-popup-blocking");
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		cap.setCapability(ChromeOptions.CAPABILITY, options);
		return new ChromeDriver(cap);
	}

	private static WebDriver getFirefoxDriver() {
		new File(System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "Export_Files")
				.mkdir();
		String downloadPath = System.getProperty("user.home") + File.separator + "Desktop" + File.separator
				+ "Export_Files";
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("browser.cache.disk.enable", false);
		profile.setPreference("browser.download.folderList", 2);
		profile.setPreference("browser.download.dir", downloadPath);
		profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
				"text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml,application/vnd.openxmlformats-officedocument.wordprocessingml.document;");
		return new FirefoxDriver(profile);
	}

}

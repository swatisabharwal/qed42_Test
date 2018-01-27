package com.qa.test.automation;

import static com.qa.test.automation.utils.ConfigPropertyReader.getProperty;
import static com.qa.test.automation.utils.YamlReader.getYamlValue;
import static com.qa.test.automation.utils.YamlReader.setYamlFilePath;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

import org.testng.Reporter;

import com.qa.test.Basic.keywords.FilterScenarioAction;
import com.qa.test.automation.utils.TakeScreenshot;

public class TestSessionInitiator {

	protected WebDriver driver;
	private WebDriverFactory wdfactory;
	
	String browser;
	String seleniumserver;

	static int timeout;

	/**
	 * Initiating the page objects
	 */

	public TakeScreenshot takescreenshot;
	private String testname;

	/*
	 * UI Actions initialization
	 */

	public FilterScenarioAction filterPage;

	public WebDriver getDriver() {
		return this.driver;
	}

	private void _initPageUI() {
		filterPage=new FilterScenarioAction(driver);	
	}

	/**
	 * Page object Initiation done
	 *
	 * @param testname
	 */
	public TestSessionInitiator(String testname) {
		wdfactory = new WebDriverFactory();
		testInitiator(testname);
		this.testname = testname;
	}

	public TestSessionInitiator(String testname, String browserName) {
		wdfactory = new WebDriverFactory(browserName);
		testInitiator(testname);
		this.testname = testname;
	}

	private void testInitiator(String testname) {
		setYamlFilePath();
		_configureBrowser();
		_initPageUI();
		takescreenshot = new TakeScreenshot(testname, this.driver);
	}
	
	public void closeBrowserSession() {
		Reporter.log("[INFO]: The Test: " + this.testname.toUpperCase() + " COMPLETED!"
				+ "\n", true);
		try {
			driver.quit();
			Thread.sleep(3000);// [INFO]: this to wait before you close every
								// thing
		} catch (Exception b) {
			b.getMessage();
		}
	}

	public void closeTestSession() {
		closeBrowserSession();
	}


	private void _configureBrowser() {
		driver = wdfactory.getDriver(_getSessionConfig());
		if (!_getSessionConfig().get("browser").toLowerCase().trim().equalsIgnoreCase("mobile")) {
			driver.manage().window().maximize();
		}
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(getProperty("timeout")), TimeUnit.SECONDS);
	}

	private Map<String, String> _getSessionConfig() {
		String[] configKeys = {  "browser", "seleniumserver","driverpath"};
		Map<String, String> config = new HashMap<String, String>();
		for (String string : configKeys) {
			config.put(string, getProperty("./Config.properties", string));
		}
		return config;
	}

	public void launchApplication() {
		launchApplication(getYamlValue("base_url"));
	}

	public void launchApplication(String base_url) {
		Reporter.log("\n[INFO]: The application url is :- " + base_url, true);
		driver.manage().deleteAllCookies();
		driver.get(base_url);
	}

	public void launchAdminApplication(String Adminbase_url) {
		Reporter.log("\n[INFO]: The application url is :- " + Adminbase_url, true);
		driver.manage().deleteAllCookies();
		driver.get(Adminbase_url);
	}

	public void openUrl(String url) {
		driver.get(url);
	}

}

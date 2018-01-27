/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qa.test.automation.getpageobjects;

import static com.qa.test.automation.getpageobjects.ObjectFileReader.getPageTitleFromFile;
import static com.qa.test.automation.utils.ConfigPropertyReader.getProperty;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.NoSuchFileException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.qa.test.automation.utils.SeleniumWait;

public class BaseUi {

	WebDriver driver;
	protected SeleniumWait wait;
	private String pageName;
	Set<String> windows;
	String wins[];

	protected BaseUi(WebDriver driver, String pageName) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
		this.pageName = pageName;
		this.wait = new SeleniumWait(driver, Integer.parseInt(getProperty("Config.properties", "timeout")));
	}

	protected String getPageTitle() {
		return driver.getTitle();
	}

	protected String logMessage(String message) {
		Reporter.log(message, true);
		return message;
	}

	protected String getCurrentURL() {
		return driver.getCurrentUrl();
	}

	protected void verifyPageTitleExact() {
		String pageTitle = getPageTitleFromFile(pageName);
		verifyPageTitleExact(pageTitle);
	}

	protected void verifyPageTitleExact(String expectedPagetitle) {
		if (((expectedPagetitle.equalsIgnoreCase("")) || (expectedPagetitle.equalsIgnoreCase (null)) || (expectedPagetitle.isEmpty()))
				&& (getProperty("browser").equalsIgnoreCase("chrome"))) {
			expectedPagetitle = getCurrentURL();
		}
		try {
			wait.waitForPageTitleToBeExact(expectedPagetitle.toString());
			logMessage("[ASSERT PASSED]: PageTitle for " + pageName + " is exactly: '" + expectedPagetitle + "'");
		} catch (TimeoutException ex) {
			logMessage("[ASSERT FAILED]: PageTitle for " + pageName + " is not exactly: '" + expectedPagetitle
					+ "'!!!\n instead it is :- " + driver.getTitle());
		}
	}



	protected void verifyPageUrlContains(String expectedPageUrl) {

		wait.waitForPageToLoadCompletely();
		String currenturl = getCurrentURL();
		Assert.assertTrue(currenturl.toLowerCase().trim().contains(expectedPageUrl.toLowerCase()),
				logMessage("[INFO]: verifying: URL - " + currenturl + " of the page '" + pageName + "' contains: "
						+ expectedPageUrl));
		logMessage("[ASSERT PASSED]: URL of the page " + pageName + " contains:- " + expectedPageUrl);

	}

	protected WebElement getElementByIndex(List<WebElement> elementlist, int index) {
		return elementlist.get(index);
	}

	protected WebElement getElementByExactText(List<WebElement> elementlist, String elementtext) {
		WebElement element = null;
		for (WebElement elem : elementlist) {
			if (elem.getText().equalsIgnoreCase(elementtext.trim())) {
				element = elem;
			}
		}
		if (element == null) {
		}
		return element;
	}
	
	public void changeImplicitTimeOut(int secs)
	{
		driver.manage().timeouts().implicitlyWait(secs,TimeUnit.SECONDS);
		this.wait = new SeleniumWait(driver, secs);
	}

	protected WebElement getElementByContainsText(List<WebElement> elementlist, String elementtext) {
		WebElement element = null;
		for (WebElement elem : elementlist) {
			if (elem.getText().contains(elementtext.trim())) {
				element = elem;
			}
		}
		// FIXME: handle if no element with the text is found in list
		if (element == null) {
		}
		return element;
	}

	protected void switchToFrame(WebElement element) {
		// switchToDefaultContent();
		wait.waitForElementToBeVisible(element);
		driver.switchTo().frame(element);
	}

	public void switchToFrame(int i) {
		driver.switchTo().frame(i);
	}

	public void switchToFrame(String id) {
		driver.switchTo().frame(id);
	}

	public void switchToDefaultContent() {
		driver.switchTo().defaultContent();
	}


	protected void hover(WebElement element) {
		Actions hoverOver = new Actions(driver);
		hoverOver.moveToElement(element).build().perform();
	}
	
//	protected void drag(WebElement element){
//		Actions 
//	}



	protected void scrollToTheElement(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}
	
	public void scrollToTheTop(){		
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
		
		
	}

	protected void hoverClick(WebElement element) {
		Actions hoverClick = new Actions(driver);
		hoverClick.moveToElement(element).click().build().perform();
	}

	protected void click(WebElement element) {
		try {
			wait.waitForElementToBeVisible(element);
			scrollToTheElement(element);
			element.click();
		} catch (StaleElementReferenceException ex1) {
			wait.waitForElementToBeVisible(element);
			scrollToTheElement(element);
			element.click();
			logMessage("Clicked Element " + element + " after catching Stale Element Exception");
		} catch (Exception ex2) {
			logMessage("Element " + element + " could not be clicked! " + ex2.getMessage());
		}
	}

	public void hardWait(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	
	public void launchSpecificUrl(String url) {
		driver.get(url);
	}

	protected void sendText(WebElement element, String text) {
		try {
			wait.waitForElementToBeVisible(element);
			scrollToTheElement(element);
			element.clear();
			hardWait(1);
			element.sendKeys(text);
			hardWait(1);
		} catch (StaleElementReferenceException ex1) {
			wait.waitForElementToBeVisible(element);
			scrollToTheElement(element);
			element.sendKeys(text);
			logMessage("Clicked Element " + element + " after catching Stale Element Exception");
		} catch (Exception ex2) {
			logMessage("Element " + element + " could not be clicked! " + ex2.getMessage());
		}
	}

	

	public void scrollWindow(int x, int y) {
		hardWait(1);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(" + x + "," + y + ")", "");

	}

	public void scrollWindowToTheTop() {
		//hardWait(1);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollTo(0,0)", "");

	}
	public void pageReload(){
		driver.navigate().refresh();
	}


	
	
	
	
	
}
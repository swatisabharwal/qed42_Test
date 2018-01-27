package com.qa.test.automation.getpageobjects;

import static com.qa.test.automation.getpageobjects.ObjectFileReader.getELementFromFile;
import static com.qa.test.automation.utils.ConfigPropertyReader.getProperty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import com.qa.test.automation.utils.ConfigPropertyReader;


public class GetPage extends BaseUi {

	protected WebDriver webdriver;
	String pageName;
	

	public GetPage(WebDriver driver, String pageName) {
		super(driver, pageName);
		this.webdriver = driver;
		this.pageName = pageName;

	}

	protected void switchToNestedFrames(String frameNames) {
		switchToDefaultContent();
		String[] frameIdentifiers = frameNames.split(":");
		for (String frameId : frameIdentifiers) {
			wait.waitForFrameToBeAvailableAndSwitchToIt(getLocator(frameId.trim()));
		}
	}

	protected Actions return_actions_object()
	{
		Actions ac=new Actions(driver);
		return ac;
	}
	protected WebElement element(String elementToken) {
		return element(elementToken, "");
	}

	protected WebElement element(String elementToken, String replacement) throws NoSuchElementException {
		WebElement elem = null;
		try {
			elem = wait.waitForElementToBeVisible(webdriver.findElement(getLocator(elementToken, replacement)));
		} catch (NoSuchElementException excp) {
			fail(logMessage(
					"[ASSERT FAILED]: Element " + elementToken + " not found on the " + this.pageName + " !!!"));
		} catch (NullPointerException npe) {

		}
		return elem;
	}

	protected WebElement childOfElement(WebElement el, String elementToken, String replacement)
			throws NoSuchElementException {
		WebElement elem = null;
		try {
			elem = wait.waitForElementToBeVisible(el.findElement(getLocator(elementToken, replacement)));
		} catch (NoSuchElementException excp) {
			fail(logMessage(
					"[ASSERT FAILED]: Element " + elementToken + " not found on the " + this.pageName + " !!!"));
		} catch (NullPointerException npe) {

		}
		return elem;
	}

	protected WebElement childOfElement(WebElement el, String elementToken) throws NoSuchElementException {
		return childOfElement(el, elementToken, "");
	}

	protected List<WebElement> elements(String elementToken, String replacement) {
		return wait.waitForElementsToBeVisible(webdriver.findElements(getLocator(elementToken, replacement)));
	}

	protected List<WebElement> elementsFromElement(WebElement el, String elementToken, String replacement) {
		return wait.waitForElementsToBeVisible(el.findElements(getLocator(elementToken, replacement)));
	}

	protected List<WebElement> elements(String elementToken) {
		return elements(elementToken, "");
	}

	protected List<WebElement> elementsFromElement(WebElement el, String elementToken) {
		return elementsFromElement(el, elementToken, "");
	}

	protected boolean isElementDisplayed(String elementName, String elementTextReplace) {
		wait.waitForElementToBeVisible(element(elementName, elementTextReplace));
		boolean result = element(elementName, elementTextReplace).isDisplayed();
		assertTrue(result, logMessage("[ASSERT FAILED]: element '" + elementName + "with text " + elementTextReplace
				+ "' is not displayed."));
		logMessage("[ASSERT PASSED]: element " + elementName + " with text " + elementTextReplace + " is displayed.");
		return result;
	}

	protected void verifyElementText(String elementName, String expectedText) {
		wait.waitForElementToBeVisible(element(elementName));
		assertEquals(element(elementName).getText().trim(), expectedText,
				logMessage("[ASSERT FAILED]: Text of the page element '" + elementName + "' is not as expected: "));
		logMessage("[ASSERT PASSED]: element " + elementName + " is visible and Text is " + expectedText);
	}

	protected void verifyElementTextContains(String elementName, String expectedText) {
		wait.waitForElementToBeVisible(element(elementName));
		assertThat(logMessage("[ASSERT FAILED]: Text of the page element '" + elementName + "' is not as expected: "),
				element(elementName).getText().trim(), containsString(expectedText));
		logMessage("[ASSERT PASSED]: element " + elementName + " is visible and Text is " + expectedText);
	}

	protected boolean isElementDisplayed(String elementName) {
		wait.waitForElementToBeVisible(element(elementName));
		boolean result = element(elementName).isDisplayed();
		assertTrue(result, "[ASSERT FAILED]: element '" + elementName + "' is not displayed.");
		logMessage("[ASSERT PASSED]: element " + elementName + " is displayed.");
		return result;
	}

	protected boolean isElementEnabled(String elementName, boolean expected) {
		wait.waitForElementToBeVisible(element(elementName));
		boolean result = expected && element(elementName).isEnabled();
		assertTrue(result, "[ASSERT FAILED]: element '" + elementName + "' is  ENABLED :- " + !expected);
		logMessage("[ASSERT PASSED]: element " + elementName + " is enabled :- " + expected);
		return result;
	}

	public WebElement element(String elementToken, String replacement1, String replacement2)
			throws NoSuchElementException {
		WebElement elem = null;
		try {
			elem = wait.waitForElementToBeVisible(webdriver
					.findElement(getLocator(elementToken, replacement1,replacement2)));
		} catch (NoSuchElementException excp) {
			switchToDefaultContent();
			fail(logMessage("[ASSERT FAILED]: Element " + elementToken
					+ " not found on the " + this.pageName + " !!!"));
			switchToDefaultContent();//}
		} catch (NullPointerException npe) {
 
			switchToDefaultContent();
		} 
		catch (StaleElementReferenceException excp) {
			switchToDefaultContent();
			elem = wait.waitForElementToBeVisible(webdriver
					.findElement(getLocator(elementToken, replacement1,replacement2)));
			
		}
		
		return elem;
	}
	protected By getLocator(String elementToken) {
		return getLocator(elementToken, "");
	}

	protected By getLocator(String elementToken, String replacement) {
		String[] locator = getELementFromFile(this.pageName, elementToken);
		locator[2] = locator[2].replaceAll("\\$\\{.+\\}", replacement);
		return getBy(locator[1].trim(), locator[2].trim());
	}

	protected By getLocator(String elementToken, String replacement1, String replacement2) {
		String[] locator = getELementFromFile(this.pageName, elementToken);
		locator[2] = StringUtils.replace(locator[2], "$", replacement1);
		locator[2] = StringUtils.replace(locator[2], "%", replacement2);
		return getBy(locator[1].trim(), locator[2].trim());
	}

	private By getBy(String locatorType, String locatorValue) {
		switch (Locators.valueOf(locatorType)) {
		case id:
			return By.id(locatorValue);
		case xpath:
			return By.xpath(locatorValue);
		case css:
			return By.cssSelector(locatorValue);
		case name:
			return By.name(locatorValue);
		case classname:
			return By.className(locatorValue);
		case linktext:
			return By.linkText(locatorValue);
		default:
			return By.id(locatorValue);
		}
	}

	protected String getLocatorTwo(String token, String repl) {
		String[] locator = getELementFromFile(this.pageName, token);
		locator[2] = locator[2].replaceAll("\\$\\{.+\\}", repl);
		return locator[2];

	}
	
	protected By getLocatorByReplacing(String elementToken, String replacement) {
		String[] locator = getELementFromFile(this.pageName, elementToken);
		locator[2] = locator[2].replaceAll("\\$\\{.+\\}", replacement);
		return getBy(locator[1].trim(), locator[2].trim());
	}
	
	
	protected WebElement elementPresentconstructed_dynamically(String elementToken, String replacement)
			throws NoSuchElementException {
		WebElement elem = null;
		try {
			elem = wait.checkPresenceOfElementInDom(getLocatorByReplacing(elementToken, replacement));
		} catch (NoSuchElementException excp) {
			switchToDefaultContent();
			fail(logMessage("[ASSERT FAILED]: Element " + elementToken
					+ " not found on the " + this.pageName + " !!!"));
			switchToDefaultContent();
		} catch (NullPointerException npe) {
			switchToDefaultContent();
		}
		catch(StaleElementReferenceException exe)
		{
			hardWait(1);
			elem = wait.waitForElementToBeVisible(webdriver
					.findElement(getLocatorByReplacing(elementToken, replacement)));
		}
		return elem;
	}
	
	protected WebElement elementConstructed_dynamically(String elementToken, String replacement)
			throws NoSuchElementException {
		WebElement elem = null;
		try {
			elem = webdriver.findElement(getLocatorByReplacing(elementToken, replacement));
		} catch (NoSuchElementException excp) {
			switchToDefaultContent();
			fail(logMessage("[ASSERT FAILED]: Element " + elementToken
					+ " not found on the " + this.pageName + " !!!"));
			switchToDefaultContent();
		} catch (NullPointerException npe) {
			switchToDefaultContent();
		}
		catch(StaleElementReferenceException exe)
		{
			hardWait(1);
			elem = wait.waitForElementToBeVisible(webdriver
					.findElement(getLocatorByReplacing(elementToken, replacement)));
		}
		return elem;
	}
	
	protected List<WebElement> elementsPresent(String elementToken, String replacement)
			throws NoSuchElementException {
		List<WebElement>  elemslist = null;
		try {
			elemslist = wait.checkPresenceOfElementsInDom(getLocatorByReplacing(elementToken, replacement));
		} catch (NoSuchElementException excp) {
			switchToDefaultContent();
			fail(logMessage("[ASSERT FAILED]: Element " + elementToken
					+ " not found on the " + this.pageName + " !!!"));
			switchToDefaultContent();
		} catch (NullPointerException npe) {
			switchToDefaultContent();
		}
		catch(StaleElementReferenceException exe)
		{
			hardWait(1);
			elemslist = wait.waitForElementsToBeVisible(webdriver
					.findElements(getLocatorByReplacing(elementToken, replacement)));
		}
		return elemslist;
	}
	
	public boolean Is_Element_Displayed(String token)
	{
	   boolean status=false;
	   
	   try
	   {
		   Assert.assertTrue(element(token).isDisplayed());
		   status=true;
	   }
	   catch(AssertionError er)
	   {
		   status=false;
	   }
	   
	   return status;
	}
	
	public boolean Is_Element_Displayed(String token,String repl)
	{
	   boolean status=false;
	   
	   try
	   {
		   Assert.assertTrue(element(token,repl).isDisplayed());
		   status=true;
	   }
	   catch(AssertionError er)
	   {
		   status=false;
	   }
	   
	   return status;
	}
	
	public boolean Are_Elements_Displayed(String token,String repl)
	{
	   boolean status=false;
	   
	   try
	   {
		   Assert.assertTrue(elements(token,repl).size()>=1);
		   
		   status=true;
	   }
	   catch(TimeoutException er)
	   {
		   status=false;
		   changeImplicitTimeOut(Integer.parseInt(ConfigPropertyReader.getProperty("timeout")));
	   }
	   catch(Exception er)
	   {
		   status=false;
		   changeImplicitTimeOut(Integer.parseInt(ConfigPropertyReader.getProperty("timeout")));
	   }
	   
	   return status;
	}
	
	
	public boolean Is_ElementPresent_Displayed(String token)
	{
	   boolean status=false;
	   
	   try
	   {
		   Assert.assertTrue(elementPresent(token).isDisplayed());
		   status=true;
	   }
	   catch(AssertionError er)
	   {
		   status=false;
	   }
	   
	   return status;
	}
	
	protected WebElement elementPresent(String token)
	{
		return elementPresent(token, "");
	}
	
	protected WebElement elementPresent(String elementToken, String replacement)
			throws NoSuchElementException {
		WebElement elem = null;
		try {
			elem = wait.checkPresenceOfElementInDom(getLocator(elementToken, replacement));
		} catch (NoSuchElementException excp) {
			switchToDefaultContent();
			fail(logMessage("[ASSERT FAILED]: Element " + elementToken
					+ " not found on the " + this.pageName + " !!!"));
			switchToDefaultContent();
		} catch (NullPointerException npe) {

		}
		return elem;
	}
	
	protected void perform_back_operation(){
		driver.navigate().back();
	}
}


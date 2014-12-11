package com.seta.automation.view;

import java.util.HashMap;
import java.util.Iterator;

import org.apache.http.util.TextUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.seta.automation.script.BaseScript;
import com.seta.automation.utils.Log;
import com.seta.automation.browser.InitBrowser;
import com.seta.automation.config.Constant;
import com.seta.automation.data.LoadableControl;
import com.seta.automation.data.LoadableData;
import com.seta.automation.data.LoadableObject;

public abstract class BaseView {

	private static final int DELAY_ACTION = 1000;// Milliseconds
	private static final int DEFAULT_TIMEOUT_FINDING_ELEMENT = 1 ;//seconds
	private static final int DEFAULT_TIMEOUT_LOADING_PAGE = 10 ;//seconds

	//List of control reference on a view page
	LoadableControl controlFields;
		
	private String viewID;
	public abstract String getConfigFilePath();
	public abstract String getDefaultURL();
	
	private static String TAG = "tag";
	
	protected WebDriver driver;
	private Actions builder;
	private WebDriverWait driverWait;
	private static HashMap<String, Object> pageObjectContainer = new HashMap<String, Object>();
	
	public BaseView(){
		driver = InitBrowser.getDriver();
		driverWait = new WebDriverWait(driver, DEFAULT_TIMEOUT_LOADING_PAGE);
		builder = new Actions(driver);
		
		LoadControlData(Constant.CPAGE_DEFAULT_FOLDER + getConfigFilePath());
		
		pageObjectContainer.put(getClass().getName(), this);
	}
	
	//Load control fields from properties file 
	private void LoadControlData(String fPath) {
		try {
			controlFields = new LoadableControl(fPath);
		} catch (Exception e) {
			Assert.assertNotNull(controlFields, "Error load file: "
					+ getConfigFilePath());
		}
	}
		
	//Get instance of view
	public static Object getView(Class<?> viewClass) throws Exception {
		Object viewObject = pageObjectContainer.get(viewClass.getName());
		
		if (viewObject == null) {
			viewObject = viewClass.getConstructor().newInstance();
		} else {
			WebDriver newReferWebdriver = InitBrowser.getDriver();
			((BaseView) viewObject).driver = newReferWebdriver;
			((BaseView) viewObject).driverWait =  new WebDriverWait(newReferWebdriver, 10); 
			((BaseView) viewObject).builder = new Actions(newReferWebdriver);
		}

		return viewObject;
	}
	
	public boolean isValidURL() {
		ExpectedCondition<Boolean> e = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				
				return (driver.getCurrentUrl().contains(getDefaultURL()));
			}
		};

		driverWait.until(e);
		String currentUrl = driver.getCurrentUrl();
		boolean contains = driver.getCurrentUrl().contains(getDefaultURL());

		Log.info(getClass().getSimpleName() + ": check valid url: "
				+ currentUrl + ", expected: " + getDefaultURL() + " ==> "
				+ (contains ? "OK" : "NOT VALID"));

		return contains;
	}
	
	public void populateData(Object testcaseData, boolean clearBeforeSet) throws Exception{
		if ((testcaseData instanceof LoadableData)){
			LoadableObject testcase = (LoadableObject) testcaseData;
			populateData(testcase, clearBeforeSet);
		} else
			Log.debug("Can't cast testcase from data provider");
	}
	
	public void populateData(LoadableData testcaseData, boolean clearBeforeSet) throws Exception{
		for(String fieldName: testcaseData.keySet()){
			String fieldType = testcaseData.getType(fieldName);
			String fieldValue = testcaseData.getValue(fieldName);
			if (!TextUtils.isEmpty(fieldValue)
					&& controlFields.containsField(fieldName)){
				WebElement element = scrollTo(fieldName);
				if (clearBeforeSet) element.clear();
				
				if (fieldType.equalsIgnoreCase(LoadableData.DTAG_INPUT)
						|| fieldType.equalsIgnoreCase(LoadableData.DTAG_UNIQUE)){
					fillInputData(fieldName, fieldValue, element);
				} else if (fieldType.equalsIgnoreCase(LoadableData.DTAG_COMBO)){
					fillComboData(fieldName, fieldValue, element);
				} else if (fieldType.equalsIgnoreCase(LoadableData.DTAG_RADIO)){
					checkRadio(fieldName, element);
				}
			}
		}
	}
	
	private void fillInputData(String fieldName, String fieldValue, WebElement element) throws Exception{
		Thread.sleep(DELAY_ACTION);
		element.sendKeys(fieldValue);
		Log.info(getClass().getSimpleName()
				+ ": populateData() - Set element < " + fieldName + " > - Input to value of < " + fieldValue + " >");
	}

	private void fillComboData(String fieldName, String fieldValue, WebElement element) throws Exception{
		Thread.sleep(DELAY_ACTION);
		element.sendKeys(fieldValue, Keys.TAB);
		Log.info(getClass().getSimpleName()
				+ ": populateData() - Set element < " + fieldName + " > - Combo to value of < " + fieldValue + " >");
	}

	private void checkRadio(String fieldName, WebElement element) {
		if (controlFields.getValue(fieldName).equalsIgnoreCase("true")) {
			element.click();
			Log.info(getClass().getSimpleName()	+ ": populateData() - Check radio button < " + controlFields.getValue(fieldName) + " >");
		}
	}
	
	public void click(String element, Object... args) throws Exception {
		Log.info(getClass().getSimpleName() + ".click(): " + element);
		getElement(element, args).click();
	}

	public void click(String element) throws Exception {
		Log.info(getClass().getSimpleName() + ": click on " + element);
		getElement(element).click();
	}
	
	public WebElement scrollTo(String elementName) throws Exception {
		Log.info(getClass().getSimpleName() + ": scrollTo " + elementName);
		WebElement element;
		try {
			element = getElement(elementName);
			((JavascriptExecutor) driver).executeScript(
					"arguments[0].scrollIntoView(true);", element);
		} catch (Exception e) {

			e.printStackTrace();

			throw new Exception("error scroll to " + elementName + " cause: "
					+ e.getMessage());

		}
		return element;
	}

	public WebElement scrollTo(String elementName, Object... args) throws Exception {
		Log.info(getClass().getSimpleName() + ": scrollTo " + elementName);
		WebElement element;
		try {
			element = getElement(elementName, args);
			((JavascriptExecutor) driver).executeScript(
					"arguments[0].scrollIntoView(true);",
					element);
		} catch (Exception e) {

			e.printStackTrace();

			throw new Exception("error scroll to " + elementName + " cause: "
					+ e.getMessage());

		}
		return element;
	}
	
	public void moveToElement(String element) throws Exception {
		builder.moveToElement(getElement(element)).build().perform();
		Log.info(getClass().getSimpleName() + ": move to  " + element);
	}

	public void moveToElement(String element, Object... args) throws Exception {
		builder.moveToElement(getElement(element, args)).build().perform();
		Log.info(getClass().getSimpleName() + ": move to  " + element);
	}

	public WebElement getElement(String name, Object... args) throws Exception {

		By locator = getLocator(name, args);
		return tryFindElement(name, locator);
	}

	private WebElement getElement(String name) throws Exception {

		By locator = getLocator(name);

		return tryFindElement(name, locator);
	}
	
	//Get locator by control ID 
	private By getLocator(String strElement, Object...args) throws Exception {
		
		// extract the locator type and value from control data field
		String locatorType = controlFields.getType(strElement);
		String locatorValue = controlFields.getValue(strElement);
		locatorValue = String.format(locatorValue, args);
		
		// return a instance of the By class based on the type of the locator
		// this By can be used by the browser object in the actual test
		if (locatorType.toLowerCase().equals(LoadableControl.CTAG_ID))
			return By.id(locatorValue);
		else if (locatorType.toLowerCase().equals(LoadableControl.CTAG_NAME))
			return By.name(locatorValue);
		else if ((locatorType.toLowerCase().equals(LoadableControl.CTAG_CLASSNAME))
				|| (locatorType.toLowerCase().equals(LoadableControl.CTAG_CLASS)))
			return By.className(locatorValue);
		else if ((locatorType.toLowerCase().equals(LoadableControl.CTAG_TAG))
				|| (locatorType.toLowerCase().equals(LoadableControl.CTAG_TAGNAME)))
			return By.className(locatorValue);
		else if ((locatorType.toLowerCase().equals(LoadableControl.CTAG_LINK))
				|| (locatorType.toLowerCase().equals(LoadableControl.CTAG_LINKTEXT)))
			return By.linkText(locatorValue);
		else if (locatorType.toLowerCase().equals(LoadableControl.CTAG_PARTIALlINKTEXT))
			return By.partialLinkText(locatorValue);
		else if ((locatorType.toLowerCase().equals(LoadableControl.CTAG_CSS))
				|| (locatorType.toLowerCase().equals(LoadableControl.CTAG_CSSSELECTOR)))
			return By.cssSelector(locatorValue);
		else if (locatorType.toLowerCase().equals(LoadableControl.CTAG_XPATH))
			return By.xpath(locatorValue);
		else
			throw new Exception("Unknown locator type '" + locatorType + "'");
	}
		
	private WebElement tryFindElement(String name, By locator) throws Exception {
		try {
			Thread.sleep(DELAY_ACTION);
			BaseScript.setTimeOut(DEFAULT_TIMEOUT_FINDING_ELEMENT);
			return driver.findElement(locator);

		} catch (Exception e) {
			String message = "Error find element name: " + name + " by: "
					+ locator + " class: " + getClass().getSimpleName();
			Log.error(message);
			throw new Exception(message);
		} finally {
			BaseScript.setDeafaultTimeOut();
		}
	}
	
	//Execute JS script 
	public void executeScript(String script, WebElement element) {
		Log.info(getClass().getSimpleName() + ".executeScript(): " + script);
		((JavascriptExecutor) driver).executeScript(script, element);

	}

	//Execute JS script with parameter
	public void executeScript(String script, String element, Object... args)
			throws Exception {
		Log.info(getClass().getSimpleName() + ".executeScript(): " + script + " element: " + element);
		((JavascriptExecutor) driver)
				.executeScript(script, getElement(element));

	}
	
	public String getTag() {
		return controlFields.getValue(TAG);
	}
}

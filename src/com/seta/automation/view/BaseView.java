package com.seta.automation.view;

import java.util.Map;

import org.apache.http.util.TextUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.seta.automation.utils.Log;
import com.seta.automation.data.LoadableObject;

public class BaseView {

	private static String TAG = "tag";
	
	protected WebDriver driver;
	
	//List of control reference on a view page
	LoadableObject lstControl;
	
	//ID of submit control
	String submitControlID = "";

	public BaseView(String xpathFile){
		LoadXpathData(xpathFile);
	}
	
	public BaseView(String xpathFile, WebDriver driver){
		LoadXpathData(xpathFile);
		this.driver = driver;
	}
	
	//Get instance of view
	public Object getView(Class<?> viewClass) throws Exception {
		return viewClass.getConstructor().newInstance();

	}
	
	//Initiate page view from excel data
//	public BaseView(String pathModel, String sheetModel, int keyRow, int dataRow) {
//		LoadExcelData(pathModel, sheetModel, keyRow, dataRow);
//	}
//	
//	//Load control ID from excel file
//	public void LoadExcelData(String pathModel, String sheetModel, int keyRow, int dataRow) {
//		LoadableExcel excelData = new LoadableExcel(pathModel, sheetModel, keyRow, dataRow);
//		this.lstControl = excelData;
//	}
	
	//Load control ID from Xpath file 
	public void LoadXpathData(String filePath) {
//		LoadableXpath xpathData = new LoadableXpath(filePath);
//		this.lstControl = xpathData;
	}
	
	//Get locator by control ID 
	public By getLocator(String strElement, Object...args) throws Exception {

		// retrieve the specified object from the object list
		String locator = lstControl.getValue(strElement);

		// extract the locator type and value from the object
		String locatorType = locator.split(":")[0];
		String locatorValue = locator.split(":")[1];

		// return a instance of the By class based on the type of the locator
		// this By can be used by the browser object in the actual test
		locatorValue = String.format(locatorValue, args);
		
		if (locatorType.toLowerCase().equals("id"))
			return By.id(locatorValue);
		else if (locatorType.toLowerCase().equals("name"))
			return By.name(locatorValue);
		else if ((locatorType.toLowerCase().equals("classname"))
				|| (locatorType.toLowerCase().equals("class")))
			return By.className(locatorValue);
		else if ((locatorType.toLowerCase().equals("tagname"))
				|| (locatorType.toLowerCase().equals("tag")))
			return By.className(locatorValue);
		else if ((locatorType.toLowerCase().equals("linktext"))
				|| (locatorType.toLowerCase().equals("link")))
			return By.linkText(locatorValue);
		else if (locatorType.toLowerCase().equals("partiallinktext"))
			return By.partialLinkText(locatorValue);
		else if ((locatorType.toLowerCase().equals("cssselector"))
				|| (locatorType.toLowerCase().equals("css")))
			return By.cssSelector(locatorValue);
		else if (locatorType.toLowerCase().equals("xpath"))
			return By.xpath(locatorValue);
		else
			throw new Exception("Unknown locator type '" + locatorType + "'");
	}
	
	public void click(String element, Object... args) throws Exception {
		Log.info(getClass().getSimpleName() + ".click(): " + element);
		getElement(element, args).click();
	}

	public void click(String element) throws Exception {
		Log.info(getClass().getSimpleName() + ": click on " + element);
		getElement(element).click();
	}
	
	public WebElement getElement(String name, Object... args) throws Exception {

		By locator = getLocator(name, args);
		return tryFindElement(name, locator);
	}

	private WebElement getElement(String name) throws Exception {

		By locator = getLocator(name);

		return tryFindElement(name, locator);
	}
	
	private WebElement tryFindElement(String name, By locator) throws Exception {
		try {
			return driver.findElement(locator);

		} catch (Exception e) {
			String message = "Error find element name: " + name + " by: "
					+ locator + " class: " + getClass().getSimpleName();
			Log.error(message);
			throw new Exception(message);
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
		return lstControl.getValue(TAG);
	}
	
	
	public void setSubmitControl(String submitControl ){
		if (lstControl.getValue(submitControl) != null){
			submitControlID = lstControl.getValue(submitControl);
		}
	}
	
	public String getSubmitControl(){
		return submitControlID;
	}
	
	public String getControlID(String controlKey){
		return lstControl.getValue(controlKey);
	}

	public void setViewPage(LoadableObject viewPage) {
		this.lstControl = viewPage;
	}
	
}

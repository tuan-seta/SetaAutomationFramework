package com.seta.automation.script;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.seta.automation.utils.Log;
import com.seta.automation.browser.ChromeBrowser;
import com.seta.automation.browser.FirefoxBrowser;
import com.seta.automation.config.Config;
import com.seta.automation.model.BaseModel;
import com.seta.automation.view.BaseView;

public abstract class BaseScript {
	
	protected WebDriver driver;
	protected String baseUrl;
	
	protected BaseView viewPage;
	protected BaseModel dataModel;
	
	protected static int DELAY_TIME = 1000;
	
	private LinkedHashMap<String, Object[]> result;
	private FileOutputStream outSteam;
	
//	@Parameters({ "browserName", "browserUrl" })
//	@BeforeSuite
//	public void BeforeSuite(String browserName, String browserURL) throws Exception  {
//
//		// Initiate web driver
//		GetBrowserInstance(browserName);
//		
//		driver.get(browserURL);
//		
//		//Initiate output file
////		outSteam = new FileOutputStream(new File(getOutputFilePath()));
//		
//		Thread.sleep(DELAY_TIME);
//	}
	
	//Initiate web driver
	protected void GetBrowserInstance(String browserName) {
		if (driver != null)
			return;

		if (browserName.equalsIgnoreCase("Firefox")) {
			driver = FirefoxBrowser.getInstance();
		} else if (browserName.equalsIgnoreCase("Chrome")) {
			driver = ChromeBrowser.getInstance();
		}
	}
		
	public BaseScript openUrl(String url) {
		String baseUrl = Config.getInstance().getBaseUrl();
		Log.info("Load url: " + baseUrl + url);
		driver.get(baseUrl + url);
		return this;
	}
	
	protected void fillDataToView(){
//		LogUtils.Log(getClass().getSimpleName() + " - Test : fillData");
		
		if(dataModel == null || viewPage == null){
			//TODO: show message
			return;
		}
		
		Map<String, String> data = null;
		
		if(data.isEmpty() || viewPage == null){
			return;
		}
		else {
			Set set = data.entrySet();
			Iterator i = set.iterator();

			//send string data to view page's controls 
			while(i.hasNext()) {
				//Get data
		        Map.Entry me = (Map.Entry)i.next();
		        String fieldID = (String) me.getKey();
		        String fieldData = (String) me.getValue();
		        
		        //get control ID
		        if (viewPage.getControlID(fieldID) != null){
			        String controlID = viewPage.getControlID(fieldID);
//			        LogUtils.Log(getClass().getSimpleName() + " - fill data control : " + controlID);

		        	//send data to a specified control
		        	WebElement element = driver.findElement(By.xpath(controlID));
		        	element.sendKeys(fieldData);
		        } 
			}
		}
	}
	
	
	//Load control xpath key from xpath file
	protected void initXpathControl(String pathControl, String submitControl){
		if(viewPage == null)
			viewPage = new BaseView(pathControl);
		else
			this.viewPage.LoadXpathData(pathControl);
		
		this.viewPage.setSubmitControl(submitControl);
	}
	
	//Load control xpath key from excel file 
//	protected void initExcelControl(String pathControl, String sheetControl, int keyRow, int controlRow, String submitControl){
//		if(viewPage == null)
//			viewPage = new BaseView(pathControl, sheetControl, keyRow, controlRow);
//		else 
//			viewPage.LoadExcelData(pathControl, sheetControl, keyRow, controlRow);
//		
//		this.viewPage.setSubmitControl(submitControl);
//	}
	
	@BeforeMethod
    public void handleBeginMethodName(Method method)
    {
        String testName = method.getAnnotation(org.testng.annotations.Test.class).testName();
        String methodName = method.getName();
        Log.startTestCase(testName + " (" + methodName+ ")");
    }
	
	@AfterMethod
    public void handleEndMethodName(Method method, ITestResult results)
    {
        String testName = method.getAnnotation(org.testng.annotations.Test.class).testName();
        String methodName = method.getName();
        String description = method.getAnnotation(org.testng.annotations.Test.class).description();
        Log.endTestCase(testName + " (" + methodName+ ")");
        String status = "";
        if (results.getStatus() == ITestResult.SUCCESS) {
        	status = "SUCCESS";
        } else {
        	status = "FAILURE";
        }
        if (result.size() == 0) {
        	result.put("0", new Object[]{"Test Case Name", "Description", "Result"});
        } 
        result.put(testName, new Object[]{testName, description, status});
    }
	
	public void exportTestResult(HashMap<String, Object[]> result, String sheetName) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(sheetName);
		Set<String> keyset = result.keySet();
		int rownum = 0;
		for (String key : keyset) {
		    Row row = sheet.createRow(rownum++);
		    Object [] objArr = result.get(key);
		    int cellnum = 0;
		    for (Object obj : objArr) {
		    	Cell cellData = row.createCell(cellnum++);
		    	cellData.setCellType(Cell.CELL_TYPE_STRING);
		    	cellData.setCellValue(String.valueOf(obj));
		    }
		}
		 
		try {
		    workbook.write(outSteam);
		    outSteam.flush();
		    outSteam.close();
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
}

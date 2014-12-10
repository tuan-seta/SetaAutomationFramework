package com.seta.automation.script;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.seta.automation.utils.ExcelHelper;
import com.seta.automation.utils.Log;
import com.seta.automation.browser.ChromeBrowser;
import com.seta.automation.browser.FirefoxBrowser;
import com.seta.automation.config.Config;
import com.seta.automation.config.Constant;
import com.seta.automation.data.LoadableData;
import com.seta.automation.view.BaseView;

public abstract class BaseScript {
	
	private static final int DEFAULT_TIME_OUT = 10;
	protected static int DELAY_TIME = 1000;
	
	protected static WebDriver driver;
	protected String baseUrl;
	private ExcelHelper loader;
	
	private LinkedHashMap<String, Object[]> result;
	private FileOutputStream outSteam;
	
	@BeforeTest (alwaysRun = true)
	public void beforeTest() throws Exception {

		Config config = Config.getInstance();
		Log.info(getClass().getName() + ": Before Test");

		if (driver == null) {
			getBrowserInstance(config.getBrowser());
		}
		
		driver.manage().timeouts()
				.implicitlyWait(getDeaultTimeOut(), TimeUnit.SECONDS);
		
	}
	
	private long getDeaultTimeOut() {
		return DEFAULT_TIME_OUT;
	}

	//Initiate web driver
	protected void getBrowserInstance(String browserName) {
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
	
	public LoadableData getSingleTestcase(String fPath, String sheetName) throws Exception{
		loader = new ExcelHelper(Constant.FDATA_DEFAULT_FOLDER + fPath, sheetName);
		Log.info(this.getClass().getName() + " load a testcase : " + loader.getFirstCase().toString());
		return loader.getFirstCase();
	}
	
	public Object getView(Class<?> view) throws Exception {
		
		return BaseView.getView(view);
	}

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
//        if (result.size() == 0) {
//        	result.put("0", new Object[]{"Test Case Name", "Description", "Result"});
//        } 
//        result.put(testName, new Object[]{testName, description, status});
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
	
	public static void setTimeOut(int timeOut) {
		driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
	}
	
	public static void setDeafaultTimeOut() {
		driver.manage().timeouts()
		.implicitlyWait(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
	}
}

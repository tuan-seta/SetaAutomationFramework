package com.seta.automation.browser;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeSuite;

import com.seta.automation.config.Config;
import com.seta.automation.config.Constant;

public class InitBrowser {

	private static WebDriver driver;
	
	public static WebDriver getDriver() {
		return driver;
	}

	private Config config; 

//	@Parameters({ "browserName", "browserVersion","browserUrl"})
	@BeforeSuite (alwaysRun = true)
	public void BeforeSuite()
//	(String browserName, String browserVersion, String browserUrl) 
	{

		this.config = Config.getConfig();
//		DOMConfigurator.configure("log4j.xml");
//		LogUtils.Log("Init web browser");
		
		//set browser
		if(config.getBrowser().equalsIgnoreCase("Chrome")){
		    
		}else if(config.getBrowser().equalsIgnoreCase("IE")){
		    driver = ChromeBrowser.getInstance();
		}else {
			driver = FirefoxBrowser.getInstance();
		}
		
		driver.get(config.getBaseUrl());
		
		driver.manage().timeouts().implicitlyWait(Constant.TIME_WAIT , TimeUnit.SECONDS);
		
		driver.manage().window().maximize();

//		MaximizeBrowser();
		
	}
	
	private void MaximizeBrowser() {
		if (driver != null) {
			// Maximize Browser
			driver.manage().window().setPosition(new Point(0, 0));
			java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
			Dimension dim = new Dimension((int) screenSize.getWidth(), (int) screenSize.getHeight());
			driver.manage().window().setSize(dim);
		}
	}
}

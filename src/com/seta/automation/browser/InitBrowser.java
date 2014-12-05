package com.seta.automation.browser;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeSuite;

import com.seta.automation.config.Config;
import com.seta.automation.config.Constant;
import com.seta.automation.utils.Log;

public class InitBrowser {

	private static WebDriver driver;
	
	public static WebDriver getDriver() {
		return driver;
	}

	private Config config; 

	@BeforeSuite (alwaysRun = true)
	public void BeforeSuite()
	{
		config = Config.getInstance();
		System.out.print(config.toString());
		//set browser
		if(config.getBrowser().equalsIgnoreCase("Chrome")){
			driver = ChromeBrowser.getInstance();
		}else if(config.getBrowser().equalsIgnoreCase("IE")){
		    driver = IEBrowser.getInstance();
		}else {
			driver = FirefoxBrowser.getInstance();
		}
		
//		driver.get(config.getBaseUrl());
		
		driver.manage().timeouts().implicitlyWait(Constant.TIME_WAIT , TimeUnit.SECONDS);
		
		MaximizeBrowser();
		
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

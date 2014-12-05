package com.seta.automation.browser;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;

import com.seta.automation.config.Constant;
import com.seta.automation.utils.Log;

public class CloseBrowser {
	private WebDriver driver;

	@AfterSuite
	public void AfterSuite() throws InterruptedException {
		Log.info("AfterSuite");
		Thread.sleep(10000);
		driver = InitBrowser.getDriver();
		if(driver !=null)
			driver.manage().timeouts().implicitlyWait(Constant.TIME_WAIT, TimeUnit.SECONDS);
			driver.quit();
	}

}

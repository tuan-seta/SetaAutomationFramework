package com.seta.automation.browser;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;

import com.seta.automation.config.Constant;
import com.seta.automation.utils.LogUtils;

public class CloseBrowser {
	private WebDriver driver;

	@AfterSuite
	public void AfterSuite() {
		LogUtils.Log("AfterSuite");
		driver = InitBrowser.getDriver();
		if(driver !=null)
			driver.manage().timeouts().implicitlyWait(Constant.TIME_WAIT, TimeUnit.SECONDS);
			driver.quit();
	}

}

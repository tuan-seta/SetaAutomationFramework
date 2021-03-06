package com.seta.automation.script.syspro;


import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.seta.automation.config.Constant;
import com.seta.automation.data.LoadableData;
import com.seta.automation.script.BaseScript;
import com.seta.automation.view.syspro.DashboardPage;
import com.seta.automation.view.syspro.LoginPage;

public class SimpleLoginScript extends BaseScript {

	private static String sheetSimpleAccount = "SimpleLoginSuccess";
	
	@BeforeTest
	public void loadUrl() {
		openUrl(Constant.URL_LOGIN);
	}
	
	@Test(priority = 0, description = "Simply login with success", testName = "LOGIN_001")
	public void loginSuccess() throws Exception {
				
		LoginPage loginPage = (LoginPage) getView(LoginPage.class);
		LoadableData simpleAccount = getSimpleAccount();
		loginPage.populateData(simpleAccount, false);
		
		DashboardPage dashboardPage = loginPage.submitLogin();
		Assert.assertTrue(dashboardPage.isValidURL());
	}

	private LoadableData getSimpleAccount() throws Exception {
		return getDefaultTestcase(Constant.FDATA_LOGIN, sheetSimpleAccount);
	}
	
}

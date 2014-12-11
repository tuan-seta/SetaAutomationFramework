package com.seta.automation.script.syspro;

import java.util.Iterator;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.seta.automation.config.Constant;
import com.seta.automation.data.LoadableData;
import com.seta.automation.script.BaseScript;
import com.seta.automation.utils.Log;
import com.seta.automation.view.syspro.DashboardPage;
import com.seta.automation.view.syspro.LoginPage;

public class LoginScript extends BaseScript {

	private static String sheetSimpleAccount = "SimpleLoginSuccess";
	private static String sheetMultiAccount = "LoginMultiTime";
	
	@BeforeTest
	public void loadUrl() {
		openUrl(Constant.URL_LOGIN);
	}
	
//	@Test(priority = 0, description = "Login", testName = "LOGIN_001")
//	public void loginSuccess() throws Exception {
//				
//		LoginPage loginPage = (LoginPage) getView(LoginPage.class);
//		LoadableData simpleAccount = getSimpleAccount();
//		loginPage.populateData(simpleAccount, false);
//		
//		DashboardPage dashboardPage = loginPage.submitLogin();
//		Assert.assertTrue(dashboardPage.isValidURL());
//		dashboardPage.logOut();
//	}
//
//	private LoadableData getSimpleAccount() throws Exception {
//		
//		return getSingleTestcase(Constant.FDATA_LOGIN, sheetSimpleAccount);
//	}
	
	@Test(priority = 0, description = "Login", testName = "LOGIN_002", dataProvider = "dataProviderLogin" )
	public void loginMultiTime(Object testcase) throws Exception {
				
		LoginPage loginPage = (LoginPage) getView(LoginPage.class);
		Log.info(this.getClass().getName() + " start to populate data ");
//		LoadableData castedAccount = (LoadableData) testcase;
		loginPage.populateData(testcase, false);
		
		DashboardPage dashboardPage = loginPage.submitLogin();
		Assert.assertTrue(dashboardPage.isValidURL());
		dashboardPage.logOut();
	}
	
	@DataProvider
	public Iterator<Object> dataProviderLogin() throws Exception {
		Log.info(this.getClass().getName() + " start to cast testcase data ");
		return getDataProvider(Constant.FDATA_LOGIN, sheetMultiAccount);
	}
	
}

package com.seta.automation.view.syspro;

import com.seta.automation.config.Constant;
import com.seta.automation.view.BaseView;

public class DashboardPage extends BaseView{

	private static String LINK_ACCOUNT = "linkAccount";
	private static String LINK_LOGOUT = "linkLogOut";

	@Override
	public String getConfigFilePath() {
		return Constant.CPAGE_DASHBOARD;
	}
	
	@Override
	public String getDefaultURL() {
		return Constant.URL_DASHBOARD;
	}
	
	public LoginPage logOut() throws Exception {
		
		moveToElement(LINK_ACCOUNT);
		
		click(LINK_LOGOUT);
		return new LoginPage();
	}
	
	
}

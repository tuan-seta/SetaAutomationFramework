package com.seta.automation.view.syspro;

import com.seta.automation.config.Constant;
import com.seta.automation.view.BaseView;

public class LoginPage extends BaseView{

	private static String btnLogin = "buttonLogin";
	
	@Override
	public String getConfigFilePath() {
		return Constant.CPAGE_LOGIN;
	}

	@Override
	public String getDefaultURL() {
		return Constant.URL_LOGIN;
	}

	public DashboardPage submitLogin() throws Exception{
		click(btnLogin);
		return (DashboardPage) BaseView.getView(DashboardPage.class);
	}
	
	public LoginPage submitLoginExpectingFailure() throws Exception{
		click(btnLogin);
		return this;
	}
}

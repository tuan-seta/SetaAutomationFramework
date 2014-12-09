package com.seta.automation.view.syspro;

import com.seta.automation.config.Constant;
import com.seta.automation.view.BaseView;

public class LoginPage extends BaseView{

	@Override
	public String getConfigFilePath() {
		return Constant.PROPERTIES_LOGIN_PAGE;
	}

	@Override
	public String getDefaultURL() {
		return Constant.LOGIN_URL;
	}

	public DashboardPage submitLogin() throws Exception{
		click("buttonLogin");
		return (DashboardPage) BaseView.getView(DashboardPage.class);
	}
	
	public LoginPage submitLoginExpectingFailure() throws Exception{
		click("buttonLogin");
		return this;
	}
}

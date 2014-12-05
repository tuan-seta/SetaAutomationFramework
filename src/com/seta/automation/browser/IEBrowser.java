package com.seta.automation.browser;

import org.openqa.selenium.ie.InternetExplorerDriver;

public class IEBrowser extends InternetExplorerDriver {

	private static InternetExplorerDriver instance = null;
	
	public static InternetExplorerDriver getInstance() {
		if(instance == null) {
			instance = new InternetExplorerDriver();
		}
		return instance;
	}
}

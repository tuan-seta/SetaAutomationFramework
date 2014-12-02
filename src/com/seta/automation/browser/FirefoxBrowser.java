package com.seta.automation.browser;

import org.openqa.selenium.firefox.FirefoxDriver;

public class FirefoxBrowser extends FirefoxDriver {

	private static FirefoxDriver instance = null;
	
	public static FirefoxDriver getInstance() {
		if(instance == null) {
			instance = new FirefoxDriver();
		}
		return instance;
	}
}

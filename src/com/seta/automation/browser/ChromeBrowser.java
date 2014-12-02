package com.seta.automation.browser;

import org.openqa.selenium.chrome.ChromeDriver;

public class ChromeBrowser extends ChromeDriver {

	private static ChromeDriver instance = null;
	
	public static ChromeDriver getInstance() {
		if(instance == null) {
			instance = new ChromeDriver();
		}
		return instance;
	}
}

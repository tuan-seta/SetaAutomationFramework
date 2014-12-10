package com.seta.automation.config;

import org.apache.http.util.TextUtils;
import org.apache.log4j.xml.DOMConfigurator;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;

import com.seta.automation.data.LoadableControl;
import com.seta.automation.data.LoadableObject;

public class Config {
	
	private static Config instance;
	private String browser;
	private String domConfigurator;
	private String baseUrl;
	
	private static final String DEFAULT_CONFIG_FILE = "conf//default.properties";
	
	private static final String TAG_BROWSER = "browser";
	private static final String TAG_DOM_CONFIGURATOR = "DOMConfigurator";
	private static final String TAG_BASE_URL = "url";
	
	public static Config getInstance() {
		if(instance == null){
			loadDefaultConfig();
		}
		return instance;
	}
	
	public static void setConfig(Config config) {
		Config.instance = config;
	}
	
	@BeforeSuite (alwaysRun = true)
	public static void loadDefaultConfig() {
		try {
			instance = loadConfig(DEFAULT_CONFIG_FILE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Config loadConfig(String fileName) throws Exception {
		Config config = new Config();

		LoadableObject data = new LoadableControl(fileName);

		config.setBrowser(data.getValue(TAG_BROWSER));
		config.setDomConfigurator(data.getValue(TAG_DOM_CONFIGURATOR));
		String baseUrl = data.getValue(TAG_BASE_URL);
		
		Assert.assertFalse(TextUtils.isEmpty(baseUrl), "Please set base url in default.properties.(example: url=http://google.com)");

		config.setBaseUrl(baseUrl);
		
		if (!TextUtils.isEmpty(config.getDomConfigurator())) {
			DOMConfigurator.configure(config.getDomConfigurator());
		}
		
		return config;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getDomConfigurator() {
		return domConfigurator;
	}

	public void setDomConfigurator(String domConfigurator) {
		this.domConfigurator = domConfigurator;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	
	@Override
	public String toString() {
		return "Config [browser=" + browser + ", domConfigurator="
				+ domConfigurator + ", baseUrl=" + baseUrl + "]\n";
	}

}

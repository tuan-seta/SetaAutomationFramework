package com.seta.automation.config;

import java.io.IOException;

import org.apache.http.util.TextUtils;
import org.apache.log4j.xml.DOMConfigurator;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;

import com.seta.automation.data.LoadableObject;
import com.seta.automation.data.LoadableProperty;

public class Config {
	private static Config config;
	private String browser;
	private String domConfigurator;
	private String baseUrl;
	
	private static final String DEFAULT_CONFIG_FILE = "conf//default.properties";
	
	private static final String TAG_BROWSER = "browser";
	private static final String TAG_DOM_CONFIGURATOR = "DOMConfigurator";
	private static final String TAG_BASE_URL = "url";
	
	public static Config getConfig() {
		return config;
	}
	
	public static void setConfig(Config config) {
		Config.config = config;
	}
	
	@BeforeSuite (alwaysRun = true)
	public Config loadDefaultConfig() throws IOException {
		config = loadConfig(DEFAULT_CONFIG_FILE);
		return config;
	}

	private Config loadConfig(String fileName) throws IOException {
		Config config = new Config();

		LoadableObject data = new LoadableProperty(fileName);

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
}

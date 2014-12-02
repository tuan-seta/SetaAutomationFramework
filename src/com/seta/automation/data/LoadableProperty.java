package com.seta.automation.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.seta.automation.utils.XpathLoader;

public class LoadableProperty extends LoadableObject {

	public LoadableProperty(String filePath) throws IOException {
		LoadKeyValue(filePath);
	}
	
	public void LoadKeyValue(String filePath) throws IOException {
//		Map<String, String> xPathElement = XpathLoader.LoadXPathFromFile(filePath);
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(filePath);
		prop.load(fis);
		
		Map<String, String> map = new HashMap<String, String>();
		for (String key : prop.stringPropertyNames()) {
		    map.put(key, prop.getProperty(key));
		}
		
		this.LoadData(map);
	}
}

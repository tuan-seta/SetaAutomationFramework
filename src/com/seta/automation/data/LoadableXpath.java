package com.seta.automation.data;

import java.util.Map;

import com.seta.automation.utils.LogUtils;
import com.seta.automation.utils.XpathLoader;

public class LoadableXpath extends LoadableObject {

	public LoadableXpath(String filePath) {
		LoadKeyValue(filePath);
	}
	
	public void LoadKeyValue(String filePath) {
		Map<String, String> xPathElement = XpathLoader.LoadXPathFromFile(filePath); 
		this.LoadData(xPathElement);
	}
}

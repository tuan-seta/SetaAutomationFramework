package com.seta.automation.data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.seta.automation.utils.Log;

public class LoadableControl extends LoadableObject{

	public static final String CTAG_SPLIT = ":";
	public static final String CTAG_ID = "id";
	public static final String CTAG_NAME = "name";
	public static final String CTAG_CLASSNAME = "classname";
	public static final String CTAG_TAG = "tag";
	public static final String CTAG_LINK = "link";
	public static final String CTAG_LINKTEXT = "linktext";
	public static final String CTAG_PARTIALlINKTEXT = "partiallinktext";
	public static final String CTAG_CSSSELECTOR = "cssselector";
	public static final String CTAG_CSS = "css";
	public static final String CTAG_XPATH = "xpath";
	
	public LoadableControl(String fPath) throws Exception{
		loadControlField(fPath);
	}
	
	public void loadControlField(String fPath) throws Exception {
		Properties prop = new Properties();
		
		try {
//			Log.debug("Load properties file: " + fPath);
			FileInputStream fis = new FileInputStream(fPath);
			prop.load(fis);
			fis.close();
		} 
		catch (FileNotFoundException e) {
			String message = "Load properties file error, file name: " + fPath + ", cause: FileNotFoundException ";
			Log.error(message);
			throw new IOException(message);
		}
		catch (IOException e) {
			String message = "Load properties file error, file name: " + fPath + ", cause: IOException"; 
			Log.error(message);
			throw new IOException(message);
		}
		
		this.clear();
		
		for (String fieldName : prop.stringPropertyNames()){
			String fieldData = (String) prop.get(fieldName);
			String fieldType = "";
			String fieldValue;
			if (fieldData.contains(CTAG_SPLIT)){
				fieldType = fieldData.split(CTAG_SPLIT)[0];
				fieldValue = fieldData.substring(fieldType.length() + 1);
			} else {
				fieldValue = fieldData;
			}
			this.put(fieldName, fieldType, fieldValue);
		}
	}
	
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer("Control fields : \n");
		result.append(super.toString());
		return result.toString();
	}
}

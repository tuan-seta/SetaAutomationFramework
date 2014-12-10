package com.seta.automation;

import com.seta.automation.config.Config;
import com.seta.automation.data.LoadableControl;
import com.seta.automation.utils.ExcelHelper;

public class UnitTestMain {

	public static void main(String[] args) {
//		getConfig();
		parseExcelData();
	}

	public static void loadProperty(){
		String fPath = "conf/default.properties";
		try {
			LoadableControl config = new LoadableControl(fPath);
			System.out.print(config.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void getConfig(){
		Config config = Config.getInstance();
		System.out.print(config.toString());
	}
	
	private static void parseExcelData(){
		String fPath = "input/data/LoginData.xlsx";
		String sheetName = "SimpleLoginSuccess";
		try {
			ExcelHelper parser = new ExcelHelper(fPath, sheetName);
			System.out.print(parser.getFirstCase().toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

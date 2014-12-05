package com.seta.automation;

import com.seta.automation.config.Config;
import com.seta.automation.data.LoadableControl;

public class UnitTestMain {

	public static void main(String[] args) {
		getConfig();
	}

//	public static void loadProperty(){
//		String fPath = "conf/default.properties";
//		try {
//			LoadableControl config = new LoadableControl(fPath);
//			System.out.print(config.toString());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	private static void getConfig(){
		Config config = Config.getInstance();
		System.out.print(config.toString());
	}
}

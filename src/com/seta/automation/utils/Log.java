 package com.seta.automation.utils;
import org.apache.log4j.Logger;

import com.seta.automation.config.Config;

 public class Log {
	// Initialize Log4j logs
	private static Logger Log = Logger.getLogger(Log.class.getName());//

	// This is to print log for the beginning of the test case, as we usually run so many test cases as a test suite
	public static void startTestCase(String sTestCaseName){
//		Log.info("****************************************************************************************");
//		Log.info("****************************************************************************************");
//		Log.info("$$$$$$$$$$$$$$$$$$$$$                 "+sTestCaseName+ "       $$$$$$$$$$$$$$$$$$$$$$$$$");
//		Log.info("****************************************************************************************");
//		Log.info("****************************************************************************************");
		if (Config.getInstance() != null) {
			Log.info("----------------------BENGIN TEST CASE-----------------------");
			Log.info("|                     " + sTestCaseName);
			Log.info("-------------------------------------------------------------");
		}
		System.out
				.println("-------------------------------------------------------------");
		System.out.println("|                  " + sTestCaseName);
		System.out
				.println("-------------------------------------------------------------");
	}
	
	//This is to print log for the ending of the test case
	public static void endTestCase(String sTestCaseName){
		Log.info("XXXXXXXXXXXXXXXXXXXXXXX             "+"-E---N---D-"+"             XXXXXXXXXXXXXXXXXXXXXX");
		Log.info("X");
		Log.info("X");
		Log.info("X");
		Log.info("X");
	}
	
	// Need to create these methods, so that they can be called  
	public static void info(String message) {
		if (Config.getInstance() != null) {
			Log.info(message);
		}
		System.out.println(message);
	}
	
	public static void warn(String message) {
	    Log.warn(message);
	}
	
	public static void error(String message) {
		if (Config.getInstance() != null) {
			Log.error(message);
		}

		System.err.println(message);
	}
	
	public static void fatal(String message) {
	    Log.fatal(message);
	}
	
	public static void debug(String message) {
	    Log.debug(message);
	}
}
package com.seta.automation.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.WebElement;

public class Utils {

	private static final long WAIT_TIMEOUT = 10;

	public static boolean checkStringEmty(String text) {
		if ("".equalsIgnoreCase(text) || text == null) {
			return true;
		}
		return false;
	}

	public static void waitForIsDisplayed(WebElement we) {
		do {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		} while (!we.isDisplayed());
	}

	/**
	 * 
	 * For write Report file
	 * 
	 * @param pathFile
	 * @param text
	 */
	public static void writeReportfile(String text) {

		File file = new File("data_output");
		if (!file.exists()) {
			file.mkdir();
		}
		String pathFile = file.getPath() + "\\report_file.txt";
		Log.info("pathFile = " + pathFile);
		Writer writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(pathFile), "utf-8"));
			writer.write(text + "");
		} catch (IOException ex) {
			// report
		} finally {
			try {
				writer.close();
			} catch (Exception ex) {
			}
		}
	}
	
	public static String today() {
		String currentDateTime = "";
		SimpleDateFormat DtFormat = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss_SSS");
		Date date = new Date();
		currentDateTime = DtFormat.format(date).toString();
		return currentDateTime;
	}
	
	/**
	 * For get time
	 */
	public static String getDateTime() {
		return new SimpleDateFormat("MM/dd/YY HH:mm:ss")
				.format(getCurrentDate().getTime());
	}

	public static Date getCurrentDate() {
		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}

}

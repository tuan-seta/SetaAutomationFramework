package com.seta.automation.listeners;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.xml.XmlSuite;

import com.seta.automation.utils.Log;
import com.seta.automation.utils.Utils;

public class IReporterListener implements IReporter{
	StringBuilder stringReport = new StringBuilder();
	int totalTestCase;
	int totalTestCaseFailed;
	int totalTestCasePassed;
	int totalTestCaseSkipped;

	@Override
	public void generateReport(List<XmlSuite> xmlSuite, List<ISuite> suites,
			String arg2) {
		// Iterating over each suite included in the test
		
		stringReport.append("\nREPORT TEST - Date:" + Utils.getCurrentDate().toString());
		for (ISuite suite : suites) {
			// Following code gets the suite name
			String suiteName = suite.getName();
			// Getting the results for the said suite
			final Map<String, ISuiteResult> suiteResults = suite.getResults();
			for (ISuiteResult sr : suiteResults.values()) {
				ITestContext tc = sr.getTestContext();
				stringReport.append("\n***********************");
				stringReport.append("\nTest Name:"	+ tc.getName());
				stringReport.append("\nPassed :" + tc.getPassedTests().getAllResults().size());
				stringReport.append("\nFailed :" + tc.getFailedTests().getAllResults().size());
				stringReport.append("\nSkipped:"	+ tc.getSkippedTests().getAllResults().size());
				
				totalTestCasePassed = totalTestCasePassed + tc.getPassedTests().getAllResults().size();
				totalTestCaseFailed = totalTestCaseFailed + tc.getFailedTests().getAllResults().size();
				totalTestCaseSkipped = totalTestCaseSkipped + tc.getSkippedTests().getAllResults().size();
				
				totalTestCase = totalTestCase + tc.getPassedTests().getAllResults().size()
								+ tc.getFailedTests().getAllResults().size()
								+ tc.getSkippedTests().getAllResults().size();
			}
			stringReport.append("\n***********************");
			stringReport.append("\nTOTAL TEST CASE : " + totalTestCase);
			stringReport.append("\nTotalTestCasePassed : " + totalTestCasePassed);
			stringReport.append("\nTotalTestCaseFailed : " + totalTestCaseFailed);
			stringReport.append("\nTotalTestCaseSkipped : " + totalTestCaseSkipped);
			Log.info("Report: "	+ stringReport.toString());
			Utils.writeReportfile(stringReport.toString());
		}
	}

}

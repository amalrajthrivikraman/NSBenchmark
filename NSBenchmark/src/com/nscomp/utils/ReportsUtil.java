package com.nscomp.utils;

import java.io.File;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.ExtentTestInterruptedException;

public class ReportsUtil{
	ExtentReports report;
	ExtentTest test;
	
	public ExtentReports startReport(){
		//ExtentReports(String filePath,Boolean replaceExisting) 
		//filepath - path of the file, in .htm or .html format - path where your report needs to generate. 
		//replaceExisting - Setting to overwrite (TRUE) the existing file or append to it
		//True (default): the file will be replaced with brand new markup, and all existing data will be lost. Use this option to create a brand new report
		//False: existing data will remain, new tests will be appended to the existing report. If the the supplied path does not exist, a new file will be created.
		report = new ExtentReports (System.getProperty("user.dir") +"/test-output/STMExtentReport.html", true);
		//extent.addSystemInfo("Environment","Environment Name")
		report
                .addSystemInfo("Host Name", "HEB")
                .addSystemInfo("Environment", "HEB Automation Testing")
                .addSystemInfo("User Name", "");
                //loading the external xml file (i.e., extent-config.xml) which was placed under the base directory
                //You could find the xml file below. Create xml file in your project and copy past the code mentioned below
		report.loadConfig(new File(System.getProperty("user.dir")+"\\extent-config.xml"));
		return report;
	}
		
//	public ExtentTest startTest(String testName){
//		logger = extent.startTest(testName);
//		return logger;
//	}
//	public void endTest(ExtentTest extlogger){
//		extent.endTest(extlogger);
//	}
	
	public void getResult(ITestResult result){
		if(result.getStatus() == ITestResult.FAILURE){
			test.log(LogStatus.FAIL, "Test Case Failed is "+result.getName());
			test.log(LogStatus.FAIL, "Test Case Failed is "+result.getThrowable());
		}else if(result.getStatus() == ITestResult.SKIP){
			test.log(LogStatus.SKIP, "Test Case Skipped is "+result.getName());
		}
		// ending test
		//endTest(logger) : It ends the current test and prepares to create HTML report
		report.endTest(test);
	}
	public void endReport(){
		// writing everything to document
		//flush() - to write or update test information to your report. 
                report.flush();
                //Call close() at the very end of your session to clear all resources. 
                //If any of your test ended abruptly causing any side-affects (not all logs sent to ExtentReports, information missing), this method will ensure that the test is still appended to the report with a warning message.
                //You should call close() only once, at the very end (in @AfterSuite for example) as it closes the underlying stream. 
                //Once this method is called, calling any Extent method will throw an error.
                //close() - To close all the operation
                //extent.close();
    }
}
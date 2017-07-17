package com.nscomp.tests;


import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.nscomp.amazon.pages.AdvancedSearch;
import com.nscomp.amazon.pages.AmazonHome;
import com.nscomp.amazon.pages.HelpTopics;
import com.nscomp.amazon.pages.NewAuthors;
import com.nscomp.amazon.pages.ZScaler;
import com.nscomp.setup.DriverSetup;
import com.nscomp.utils.Config;
import com.nscomp.utils.ExcelReader;
import com.nscomp.utils.ExtentReportsUtil;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import org.apache.commons.lang3.exception.ExceptionUtils;


@SuppressWarnings("unused")
public class VerifyBecomeAuthor extends DriverSetup {
	private WebDriver driver;
	Map<String, String> tcData;
	public String searchCriteria, itemName, price;

	static ExtentTest extTestlogger;
	final static Logger logger = Logger.getLogger(com.nscomp.tests.VerifyBecomeAuthor.class);
	
	//Data Values
	@Parameters({ "testData"})
	@BeforeClass
	public void setUp(String testData){
		driver=getDriver();
		tcData = new HashMap<String, String>();
		tcData = ExcelReader.readXLSXFile(Config.testDataLoc+testData);

		searchCriteria=tcData.get("searchCriteria").toString();
		itemName=tcData.get("itemName").toString();
		price=tcData.get("price").toString();
	}
	@Test
	public void xsearchOnCategory(){
		String testCaseName = "TC08 - Verify Become Author Pages";
		logger.info(testCaseName);
		extTestlogger = ExtentReportsUtil.startTest(testCaseName);
		ExtentTest childTest = ExtentReportsUtil.startTest("Step_1: Navigate to Home Page");

		try{
			logger.info("Step_1: Navigate to Home Page");
			AmazonHome amazonHome = new AmazonHome(driver);
			if(Config.zscalerEnabled){
				ZScaler scalerPage = new ZScaler(driver);
				scalerPage.accept();
			}
			childTest.log(LogStatus.PASS, "Access Home Page URL");
			extTestlogger.appendChild(childTest);

			childTest = ExtentReportsUtil.startTest("Step_2: Click Become an Author link");
			logger.info("Step_2: Click Become an Author link");
			NewAuthors newAuthorPage = amazonHome.navToBecomeAnAuthor();
			childTest.log(LogStatus.PASS, "Clicked on link to become an author");	
			Boolean result = newAuthorPage.verifyButtons();
			if(result == true){
				childTest.log(LogStatus.PASS, "Verification of Elements successful");
				logger.info("Verification of Elements successful");
			}
			else{
				childTest.log(LogStatus.FAIL, "Verification of Elements failed");
				logger.info("Verification of Elements failed");
			}
			extTestlogger.appendChild(childTest);
					
			childTest = ExtentReportsUtil.startTest("Step_3: Click on link Learn How Easy..");
			logger.info("Step_3: Click on link Learn How Easy..");
			HelpTopics helpPage = newAuthorPage.clickLearnLink();
			childTest.log(LogStatus.PASS, "Clicked on link Learn how easy...");
			result = helpPage.verifyContentHeader("Getting Started - How to Publish Your Book on KDP");	
			
			helpPage.clickProgPolLink();
			childTest.log(LogStatus.PASS, "Clicked on link Program Policies");
			result = helpPage.verifyContentHeader("Content Guidelines");
			if(result == true){
				childTest.log(LogStatus.PASS, "Verification of Content Header successful");
				logger.info("Verification of Content Header successful");
			}
			else{
				childTest.log(LogStatus.FAIL, "Verification of Content Header failed");
				logger.info("Verification of Content Header failed");
			}
			helpPage.clickTaxInfoLink();
			childTest.log(LogStatus.PASS, "Clicked on link Tax Information");
			result = helpPage.verifyContentHeader("Tax Account Status");
			if(result == true){
				childTest.log(LogStatus.PASS, "Verification of Content Header successful");
				logger.info("Verification of Content Header successful");
			}
			else{
				childTest.log(LogStatus.FAIL, "Verification of Content Header failed");
				logger.info("Verification of Content Header failed");
			}
			helpPage.clickTaxIDValLink();
			childTest.log(LogStatus.PASS, "Clicked on link Tax ID Validation");
			result = helpPage.verifyContentHeader("Tax ID Validation");
			if(result == true){
				childTest.log(LogStatus.PASS, "Verification of Content Header successful");
				logger.info("Verification of Content Header successful");
			}
			else{
				childTest.log(LogStatus.FAIL, "Verification of Content Header failed");
				logger.info("Verification of Content Header failed");
			}
			extTestlogger.appendChild(childTest);
		}
		catch (Exception e)
		{
			logger.error(ExceptionUtils.getStackTrace(e));
			extTestlogger.log(LogStatus.FAIL, testCaseName + " Failed with exception");	
		}
		logger.info("Completed Testcase " + testCaseName);
		//extTestlogger.log(LogStatus.PASS, "Create Offer Completed Successfully");	
	}

	@AfterMethod
	public static void getResult(ITestResult result){
		if(result.getStatus() == ITestResult.FAILURE){
			extTestlogger.log(LogStatus.FAIL, "Test Case Failed is "+result.getName());
			extTestlogger.log(LogStatus.FAIL, "Test Case Failed is "+result.getThrowable());
		}else if(result.getStatus() == ITestResult.SKIP){
			extTestlogger.log(LogStatus.SKIP, "Test Case Skipped is "+result.getName());
		}
		ExtentReportsUtil.endTest(extTestlogger);
	}
}
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
import com.nscomp.amazon.pages.ZScaler;
import com.nscomp.setup.DriverSetup;
import com.nscomp.utils.Config;
import com.nscomp.utils.ExcelReader;
import com.nscomp.utils.ExtentReportsUtil;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import org.apache.commons.lang3.exception.ExceptionUtils;


@SuppressWarnings("unused")
public class VerifyCountryPortals extends DriverSetup {
	private WebDriver driver;
	Map<String, String> tcData;
	public String searchCriteria, itemName, price;

	static ExtentTest extTestlogger;
	final static Logger logger = Logger.getLogger(com.nscomp.tests.VerifyCountryPortals.class);
	
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
	public void checkCountryPortals(){
		String testCaseName = "TC09 - Country Links";
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

			childTest = ExtentReportsUtil.startTest("Step_2: Choose Brazilian version of the Site");
			logger.info("Step_2: Choose Brazilian version of the Site");
			amazonHome.selectBrazilPortal();
			childTest.log(LogStatus.PASS, "Brazilian Portal Selected");
			Boolean result = amazonHome.verifyBrazilHomeLink("Amazon.com.br");
			if(result == true){
				childTest.log(LogStatus.PASS, "Home Page link verified successfully");
				logger.info("Home Page link verified successfully");
			}
			else{
				childTest.log(LogStatus.FAIL, "Home Page link verification failed");
				logger.info("Home Page link verification failed");
			}
			extTestlogger.appendChild(childTest);
			
			childTest = ExtentReportsUtil.startTest("Step_3: Choose Indian version of the Site");
			amazonHome.selectIndiaPortal();
			childTest.log(LogStatus.PASS, "Indian Portal Selected");
			result = amazonHome.verifyIndiaHomeLink("Amazon");
			if(result == true){
				childTest.log(LogStatus.PASS, "Home Page link verified successfully");
				logger.info("Home Page link verified successfully");
			}
			else{
				childTest.log(LogStatus.FAIL, "Home Page link verification failed");
				logger.info("Home Page link verification failed");
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
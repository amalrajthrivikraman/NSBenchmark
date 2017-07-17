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

import com.nscomp.amazon.pages.AmazonHome;
import com.nscomp.amazon.pages.ZScaler;
import com.nscomp.amazon.pages.TodaysDeal;
import com.nscomp.setup.DriverSetup;
import com.nscomp.utils.Config;
import com.nscomp.utils.ExcelReader;
import com.nscomp.utils.ExtentReportsUtil;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import org.apache.commons.lang3.exception.ExceptionUtils;


public class VerifyTodaysDeal extends DriverSetup {
	private WebDriver driver;
	Map<String, String> tcData;
	public String searchCriteria, itemName, price;
	
	TodaysDeal todaysDealPage;

	static ExtentTest extTestlogger;
	final static Logger logger = Logger.getLogger(com.nscomp.tests.VerifyTodaysDeal.class);
	
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
	public void testTodaysDeal(){
		System.out.println("THIRD METHOD");
		String testCaseName = "TC06 - Todays Deal Search";
		logger.info(testCaseName);
		extTestlogger = ExtentReportsUtil.startTest(testCaseName);
		ExtentTest childTest = ExtentReportsUtil.startTest("Step_1: Navigate to Home Page");

		try{
			logger.info("Step_1: Navigate to Home Page");
			AmazonHome amazonHome = new AmazonHome(driver);
			System.out.println("MANUAL CHECK");
			//Thread.sleep(15000);
			if(Config.zscalerEnabled){
				ZScaler scalerPage = new ZScaler(driver);
				scalerPage.accept();
			}
			childTest.log(LogStatus.PASS, "Access Home Page URL");
			extTestlogger.appendChild(childTest);

			childTest = ExtentReportsUtil.startTest("Step_2: Click Todays Deals link in top banner");
			logger.info("Step_2: Click Todays Deals link in top banner");
			todaysDealPage=amazonHome.selectTodaysDeal();
			childTest.log(LogStatus.PASS, "Click Todays Deal link");
			extTestlogger.appendChild(childTest);
			
			childTest = ExtentReportsUtil.startTest("Step_3: Filter on Deal Type");
			logger.info("Step_3: Filter on Deal Type");
			todaysDealPage.filterPrimeEarlyAccess();
			childTest.log(LogStatus.PASS, "Search Filtered on Prime Access");

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
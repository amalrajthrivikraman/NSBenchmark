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
import com.nscomp.setup.DriverSetup;
import com.nscomp.utils.Config;
import com.nscomp.utils.ExcelReader;
import com.nscomp.utils.ExtentReportsUtil;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import org.apache.commons.lang3.exception.ExceptionUtils;


public class SearchInvalidCategory extends DriverSetup {
	private WebDriver driver;
	Map<String, String> tcData;
	public String searchCriteria, itemName, price;

	static ExtentTest extTestlogger;
	final static Logger logger = Logger.getLogger(com.nscomp.tests.SearchInvalidCategory.class);
	
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
	public void searchOnCategory(){
		String testCaseName = "TC04 - Search on wrong Category";
		logger.info(testCaseName);
		extTestlogger = ExtentReportsUtil.startTest(testCaseName);
		ExtentTest childTest = ExtentReportsUtil.startTest("Step_1: Navigate to Home Page");

		try{
			logger.info("Step_1: Navigate to Home Page");
			AmazonHome amazonHome = new AmazonHome(driver);
			//Thread.sleep(15000);
			if(Config.zscalerEnabled){
				ZScaler scalerPage = new ZScaler(driver);
				scalerPage.accept();
			}
			childTest.log(LogStatus.PASS, "Access Home Page URL");
			extTestlogger.appendChild(childTest);

			childTest = ExtentReportsUtil.startTest("Step_2: Choose Books");
			logger.info("Step_2: Choose Books");
			amazonHome.selectCategory("Books");
			childTest.log(LogStatus.PASS, "Search Category Selected as Books");
			extTestlogger.appendChild(childTest);
			
			childTest = ExtentReportsUtil.startTest("Step_3: Enter Search Criteria and Submit");
			logger.info("Step_3: Enter Search Criteria and Submit");
			amazonHome.enterSearchValue(searchCriteria);
			childTest.log(LogStatus.PASS, "Search Item entered as " + searchCriteria);
			amazonHome.submitSearch();
			childTest.log(LogStatus.PASS, "Search submitted");
			//Boolean result = amazonHome.verifySearchUnderCatMessage("We didn't find results for \"CEETAL Submersible Pump\" in Books");
			Boolean result = amazonHome.verifySearchUnderCatMessage("We didn't find results for \"i-Flo Submersible Pump\" in Books");	
			if(result == true){
				childTest.log(LogStatus.PASS, "Result Message Verified Successfully");
				logger.info("Result Message Verified Successfully");
			}
			else{
				childTest.log(LogStatus.FAIL, "Result Message Incorrect");
				logger.info("Result Message Incorrect");
			}
			result = amazonHome.verifySearchItem(itemName, price);
			if(result == true){
				childTest.log(LogStatus.PASS, "Item name and price verified successfully");
				logger.info("Item name and price verified successfully");
			}
			else{
				childTest.log(LogStatus.FAIL, "Item name and price cannot be verified successfully");
				logger.info("Item name and price cannot be verified successfully");
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
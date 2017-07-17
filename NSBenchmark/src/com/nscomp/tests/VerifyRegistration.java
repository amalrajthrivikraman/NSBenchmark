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
import com.nscomp.amazon.pages.CreateAccount;
import com.nscomp.amazon.pages.SignIn;
import com.nscomp.setup.DriverSetup;
import com.nscomp.utils.Config;
import com.nscomp.utils.ExcelReader;
import com.nscomp.utils.ExtentReportsUtil;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.nscomp.amazon.pages.ZScaler;

import org.apache.commons.lang3.exception.ExceptionUtils;


public class VerifyRegistration extends DriverSetup {
	private WebDriver driver;
	Map<String, String> tcData;
	public String yourName, mobileNumber, emailAddress, password;

	private CreateAccount createAccountPage;
	static ExtentTest extTestlogger;
	final static Logger logger = Logger.getLogger(com.nscomp.tests.VerifyRegistration.class);
	
	//Data Values
	@Parameters({ "testData"})
	@BeforeClass
	public void setUp(String testData){
		driver=getDriver();
		tcData = new HashMap<String, String>();
		tcData = ExcelReader.readXLSXFile(Config.testDataLoc+testData);

		yourName=tcData.get("name").toString();
		mobileNumber= tcData.get("mobile").toString();
		emailAddress= tcData.get("email").toString();
		password= tcData.get("password").toString();
	}
	@Test
	public void validateRegistration(){
		String testCaseName = "TC01 - Registration Validations";
		logger.info(testCaseName);
		extTestlogger = ExtentReportsUtil.startTest(testCaseName);
		ExtentTest childTest = ExtentReportsUtil.startTest("Step_1: Navigate to Home Page");

		try{
			logger.info("Step_1: Navigate to Home Page");
			AmazonHome amazonHome = new AmazonHome(driver);
			childTest.log(LogStatus.PASS, "Access Home Page URL");
			extTestlogger.appendChild(childTest);
			System.out.println(Config.elemWaitTime);
			if(Config.zscalerEnabled){
				ZScaler scalerPage = new ZScaler(driver);
				scalerPage.accept();
			}
			
			childTest = ExtentReportsUtil.startTest("Step_2: Click on Start Here Link");
			createAccountPage = amazonHome.registerNewUser();
			logger.info("Step_2: Click on Start Here Link");
			childTest.log(LogStatus.PASS, "Start Here Link Clicked");
			Boolean result = createAccountPage.verifyCountryCode();
			if(result == true){
				childTest.log(LogStatus.PASS, "Country Code Validated Successfully");
				logger.info("Country Code Validated Successfully");
			}
			else{
				childTest.log(LogStatus.FAIL, "Country Code Not Set Correctly");
				logger.info("Country Code Not Set Correctly");
			}
			extTestlogger.appendChild(childTest);

			childTest = ExtentReportsUtil.startTest("Step_3: Enter Data and Continue");
			logger.info("Step_3: Enter Data and Continue");
			createAccountPage.enterName(yourName);
			childTest.log(LogStatus.PASS, "Name Entered as " + yourName);
			createAccountPage.enterPhone(mobileNumber);
			childTest.log(LogStatus.PASS, "Mobile number entered as " + mobileNumber);
			createAccountPage.clickContinue();
			childTest.log(LogStatus.PASS, "Continue Button Clicked");
			logger.info("Continue Button Clicked");
			result = createAccountPage.verifyMissingPwdAlertMsg("Enter your password");
			if(result == true){
				childTest.log(LogStatus.PASS, "Alert Message for Missing Password Shown successfully");
				logger.info("Alert Message for Missing Password Shown successfully");
			}
			else{
				childTest.log(LogStatus.FAIL, "Alert Message incorrect");
				logger.info("Alert Message incorrect");
			}
			extTestlogger.appendChild(childTest);
			Thread.sleep(5000);
			childTest = ExtentReportsUtil.startTest("Step_4: Enter short password");
			createAccountPage.enterPassword(password);
			childTest.log(LogStatus.PASS, "Password entered as " + password);
			logger.info("Password entered as " + password);
			createAccountPage.clickContinue();
			childTest.log(LogStatus.PASS, "Continue Button Clicked");
			result = createAccountPage.verifyShortPwdAlertMsg("Passwords must be at least 6 characters.");
			if(result == true){
				childTest.log(LogStatus.PASS, "Alert Message for short password Shown successfully");
				logger.info("Alert Message Shown successfully");
			}
			else{
				childTest.log(LogStatus.FAIL, "Alert Message for short password incorrect");
				logger.info("Alert Message for short password incorrect");
			}
			logger.info("Continue Button Clicked");
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
		// ending test
		//endTest(logger) : It ends the current test and prepares to create HTML report
		ExtentReportsUtil.endTest(extTestlogger);
	}
}
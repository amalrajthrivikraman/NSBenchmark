package com.nscomp.tests;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.nscomp.amazon.pages.AmazonHome;
import com.nscomp.amazon.pages.CreateAccount;
import com.nscomp.amazon.pages.SignIn;
import com.nscomp.amazon.pages.ZScaler;
import com.nscomp.setup.DriverSetup;
import com.nscomp.utils.Config;
import com.nscomp.utils.ExcelReader;
import com.nscomp.utils.ExtentReportsUtil;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import org.apache.commons.lang3.exception.ExceptionUtils;


public class VerifySignIn extends DriverSetup {
	private WebDriver driver;
	Map<String, String> tcData;
	public String yourName, mobileNumber, emailAddress, password;

	private SignIn signInPage;
	static ExtentTest extTestlogger;
	final static Logger logger = Logger.getLogger(com.nscomp.tests.VerifySignIn.class);
	
	//Data Values

	
	@Parameters({ "testData"})
	@BeforeClass
	public void setUp(String testData){
		driver=getDriver();
		tcData = new HashMap<String, String>();
		tcData = ExcelReader.readXLSXFile(Config.testDataLoc+testData);

		emailAddress= tcData.get("email").toString();
		password= tcData.get("password").toString();
	}
	@Test
	public void signInInvalidEmail(){
		System.out.println("SECOND METHOD");
		String testCaseName = "TC02 - Sign In with Invalid Email";
		logger.info(testCaseName);
		extTestlogger = ExtentReportsUtil.startTest(testCaseName);
		ExtentTest childTest = ExtentReportsUtil.startTest("Step_1: Navigate to Home Page");

		try{
			logger.info("Step_1: Navigate to Home Page");
			AmazonHome amazonHome = new AmazonHome(driver);
			childTest.log(LogStatus.PASS, "Access Home Page URL");
			if(Config.zscalerEnabled){
				ZScaler scalerPage = new ZScaler(driver);
				scalerPage.accept();
			}
			extTestlogger.appendChild(childTest);

			childTest = ExtentReportsUtil.startTest("Step_2: Click on Sign In Button");
			signInPage =  amazonHome.gotoSigninPage();
			logger.info("Step_2: Click on Sign In Button");
			childTest.log(LogStatus.PASS, "Sign In Button Selected");
			extTestlogger.appendChild(childTest);

			childTest = ExtentReportsUtil.startTest("Step_3: Enter Data and Click Login");
			logger.info("Step_3: Enter Data and Click Login");

			signInPage.enterEmail(emailAddress);
			childTest.log(LogStatus.PASS, "Email Entered as " + emailAddress);
			signInPage.enterPassword(password);
			childTest.log(LogStatus.PASS, "Password Entered as " + password);
			Boolean result = signInPage.verifyKeepSignInOption();
			if(result == true){
				childTest.log(LogStatus.PASS, "Keep Signed in option is unchecked");
				logger.info("Keep Signed in option is unchecked");
			}
			else{
				childTest.log(LogStatus.FAIL, "Keep Signed in option is checked");
				logger.info("Keep Signed in option is checked");
			}
			signInPage.submitLogin();
			childTest.log(LogStatus.PASS, "Login Action Tried");
			result = signInPage.verifyEmailAlert("Important Message!");
			if(result == true){
				childTest.log(LogStatus.PASS, "Alert Message correct for existing email");
				logger.info("Alert Message correct for existing email");
			}
			else{
				childTest.log(LogStatus.FAIL, "Alert Message incorrect for existing email");
				logger.info("Alert Message incorrect for existing email");
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
		// ending test
		//endTest(logger) : It ends the current test and prepares to create HTML report
		ExtentReportsUtil.endTest(extTestlogger);
	}
}
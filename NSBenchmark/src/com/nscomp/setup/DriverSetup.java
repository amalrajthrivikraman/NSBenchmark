package com.nscomp.setup;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.nscomp.utils.Config;
import com.nscomp.utils.ExtentReportsUtil;

public class DriverSetup {

	private WebDriver driver;
	static String driverPath;
	final static Logger logger = Logger.getLogger(com.nscomp.setup.DriverSetup.class);

	public WebDriver getDriver() {
		System.out.println("Returning Driver");
		logger.info("Returning Driver");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		return driver;
	}

	private void setDriver(String browserType, String appURL) throws MalformedURLException {
		switch (browserType) {
		case "chrome":
			//System.out.println("Browser type is Chrome");
			logger.info("Browser type is Chrome");
			driver = initChromeDriver(appURL);			
			break;
		case "firefox":
			logger.info("Browser type is Firefox");
			driver = initFirefoxDriver(appURL);
			break;
		default:
			logger.info("browser : " + browserType
					+ " is invalid, Launching Firefox as browser of choice..");
			driver = initFirefoxDriver(appURL);
		}
	}

	private static WebDriver initChromeDriver(String appURL) throws MalformedURLException {
		Config.loadProperties();
		String hubUrl = Config.testHub;
		System.out.println(Config.zscalerEnabled + "<<Config.zscalerEnabled");
		ChromeOptions chromeOptions = new ChromeOptions();
		DesiredCapabilities dc = DesiredCapabilities.chrome();
		dc.setBrowserName("chrome");
		dc.setPlatform(Platform.VISTA);
		if(!Config.zscalerEnabled){
			System.out.println(Config.zscalerEnabled + "<<Config.zscalerEnabled");
			chromeOptions.addArguments("user-data-dir=C:\\Users\\u56819\\AppData\\Local\\Google\\Chrome\\User Data\\SelProfile");
			dc.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
		}

		RemoteWebDriver driver = new RemoteWebDriver(new URL(hubUrl + "/wd/hub"), dc);
		driver.manage().window().maximize();
		driver.navigate().to(appURL);
		return driver;
	}
	
	private static WebDriver initFirefoxDriver(String appURL) throws MalformedURLException {
		System.out.println("Launching Firefox with new profile..");
		Config.loadProperties();
		String hubUrl = Config.testHub;
		DesiredCapabilities dc = DesiredCapabilities.firefox();
		dc.setBrowserName("firefox");
		dc.setPlatform(Platform.VISTA);
		RemoteWebDriver driver = new RemoteWebDriver(new URL(hubUrl + "/wd/hub"), dc);
		System.out.println("Remote drive created");

		driver.manage().window().maximize();
		driver.navigate().to(appURL);
		System.out.println("URL Set");
		return driver;
	}

	@Parameters({ "browserType", "appURL" })
	@BeforeClass
	public void initializeTestBaseSetup(String browserType, String appURL) {
		logger.info("Initializing Test Environment");

		Config.loadProperties();
		driverPath = Config.driverLoc;
		try {
			setDriver(browserType, appURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@AfterClass
	public void tearDown() {
		driver.quit();
	}
	
	@BeforeSuite
	public void report(){
		ExtentReportsUtil.startReport();
	}
	
	@AfterSuite
	public void closeReport(){
		ExtentReportsUtil.endReport();
	}
}

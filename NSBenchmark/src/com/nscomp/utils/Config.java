package com.nscomp.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
public class Config {
	static Properties prop = new Properties();
	static InputStream input = null;
	
	public static String testPlatform;
	public static String testBrowser;
	public static String testHub;
	
	public static String driverLoc;
	public static String testDataLoc;
	public static long elemWaitTime;
	public static String appURL;
	public static Boolean zscalerEnabled;
	public static void loadProperties(){
		try {

			input = new FileInputStream("D:\\Projects\\HEBCMS\\resources\\config.properties");
			prop.load(input);
			driverLoc = prop.getProperty("driver.loc","C:\\Drivers\\");
			testPlatform = prop.getProperty("test.platform", "VISTA");
			testBrowser = prop.getProperty("test.browser", "chrome");
			testHub = prop.getProperty("test.hub.address", "http://localhost:4444");
			
			testDataLoc = prop.getProperty("testdata.location", "C:\\TestData");

			elemWaitTime = Long.parseLong(prop.getProperty("element.waitinsec", "10"));

			appURL = prop.getProperty("app.url", "URL is NULL");

			zscalerEnabled = Boolean.parseBoolean(prop.getProperty("zscaler.proxyscript.enabled", "false"));

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
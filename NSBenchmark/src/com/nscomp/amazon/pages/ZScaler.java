package com.nscomp.amazon.pages;


import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.nscomp.utils.Config;
public class ZScaler {

	protected WebDriver driver;
	private By clickHereSlowLinkLoc = By.linkText("Click here");
	private By isemaillinkLoc = By.linkText("ishelpdesk@ust-global.com");
	private By acceptButtonLoc = By.name("button");
	final static Logger logger = Logger.getLogger(com.nscomp.amazon.pages.ZScaler.class);


	private WebDriverWait wait;


	public ZScaler (WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(this.driver,Config.elemWaitTime);
	}


	public void accept1(){
		wait.until(ExpectedConditions.visibilityOfElementLocated(isemaillinkLoc));
		driver.findElement(acceptButtonLoc).submit();
		System.out.println(driver.getCurrentUrl());
	}
	
	public void accept(){
		try{
			wait.until(ExpectedConditions.visibilityOfElementLocated(clickHereSlowLinkLoc));
			driver.findElement(clickHereSlowLinkLoc).click();
		}
		catch(Exception e){
			logger.error(ExceptionUtils.getStackTrace(e));
		}
		wait.until(ExpectedConditions.visibilityOfElementLocated(isemaillinkLoc));
		System.out.println(driver.getCurrentUrl());
		driver.findElement(acceptButtonLoc).submit();
		System.out.println(driver.getCurrentUrl());
	}
}
package com.nscomp.amazon.pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.nscomp.utils.Config;
public class CreateAccount {

	protected WebDriver driver;
	private By yourNameTBLoc = By.id("ap_customer_name");
	private By countryCodeListLoc = By.cssSelector("#auth-country-picker-container > span > span > span");
	private By phoneNumberTBLoc = By.id("ap_phone_number");
	private By emailTBLoc = By.id("ap_email");
	private By passwordTBLoc = By.id("ap_password");
	private By continueBtnLoc = By.id("continue");
	private By missingMobileAlertContentLoc = By.cssSelector("#auth-phoneNumber-missing-alert > div > div");
	private By shortPwdAlertContentLoc = By.cssSelector("#auth-password-invalid-password-alert > div > div");
	private By blankPwdAlertContentLoc = By.cssSelector("#auth-password-missing-alert > div > div");
	private WebDriverWait wait;



	public CreateAccount (WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(this.driver,Config.elemWaitTime);
	}

	public void enterName(String userFullName){
		wait.until(ExpectedConditions.visibilityOfElementLocated(yourNameTBLoc));
		WebElement yourNameTB = driver.findElement(yourNameTBLoc);
		yourNameTB.sendKeys(userFullName);
	}
	
	public void enterPhone(String phoneNumber){
		wait.until(ExpectedConditions.visibilityOfElementLocated(phoneNumberTBLoc));
		WebElement phoneNumberTB = driver.findElement(phoneNumberTBLoc);
		phoneNumberTB.sendKeys(phoneNumber);
	}
	
	public void enterEmail(String email){
		wait.until(ExpectedConditions.visibilityOfElementLocated(emailTBLoc));
		WebElement emailTB = driver.findElement(emailTBLoc);
		emailTB.sendKeys(email);
	}
	public void enterPassword(String password){
		wait.until(ExpectedConditions.visibilityOfElementLocated(passwordTBLoc));
		WebElement passwordTB = driver.findElement(passwordTBLoc);
		passwordTB.sendKeys(password);
	}
	
	public void clickContinue(){
		wait.until(ExpectedConditions.visibilityOfElementLocated(continueBtnLoc));
		WebElement continueBtn = driver.findElement(continueBtnLoc);
		continueBtn.click();
	}
	
	public boolean verifyCountryCode(){
		wait.until(ExpectedConditions.visibilityOfElementLocated(countryCodeListLoc));
		WebElement countryCodeList = driver.findElement(countryCodeListLoc);
		String countryCode = countryCodeList.getText().trim();
		//System.out.println(">>>" + countryCode + "<<<");
		return (countryCode.equalsIgnoreCase("IN +91"));
	}
	
	public boolean verifyMissingMobilAlertMsg(String msg){
		wait.until(ExpectedConditions.visibilityOfElementLocated(missingMobileAlertContentLoc));
		WebElement missingMobileAlertContent = driver.findElement(missingMobileAlertContentLoc);
		String missingMobileAlertContentText = missingMobileAlertContent.getText().trim();
		return (missingMobileAlertContentText.equalsIgnoreCase(msg));
	}
	
	public boolean verifyMissingPwdAlertMsg(String msg){
		wait.until(ExpectedConditions.visibilityOfElementLocated(blankPwdAlertContentLoc));
		WebElement blankPwdAlertContent = driver.findElement(blankPwdAlertContentLoc);
		String blankPwdAlertContentText = blankPwdAlertContent.getText().trim();
		return (blankPwdAlertContentText.equalsIgnoreCase(msg));
	}
	public Boolean verifyShortPwdAlertMsg(String msg) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(shortPwdAlertContentLoc));
		WebElement shortPwdAlertContent = driver.findElement(shortPwdAlertContentLoc);
		String shortPwdAlertContentText = shortPwdAlertContent.getText().trim();
		System.out.println(">>" + shortPwdAlertContentText + "<<");
		return (shortPwdAlertContentText.equalsIgnoreCase(msg));
	}
}
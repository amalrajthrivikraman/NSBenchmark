package com.nscomp.amazon.pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import com.nscomp.utils.Config;
public class SignIn {

	protected WebDriver driver;
	private By emailOrPhoneLoc = By.id("ap_email");
	private By passwordLoc = By.id("ap_password");
	private By errorMsg1Loc = By.cssSelector("#auth-warning-message-box > div > h4");
	private By keepSignInCBLoc = By.name("rememberMe");
	private By loginBtnLoc = By.id("signInSubmit");


	private WebDriverWait wait;



	public SignIn (WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(this.driver,Config.elemWaitTime);
	}

	public void enterEmail(String username){
		wait.until(ExpectedConditions.visibilityOfElementLocated(emailOrPhoneLoc));
		WebElement emailOrPhone = driver.findElement(emailOrPhoneLoc);
		emailOrPhone.sendKeys(username);
	}
	
	public Boolean verifyKeepSignInOption(){
		wait.until(ExpectedConditions.visibilityOfElementLocated(keepSignInCBLoc));
		WebElement keepSignInCB = driver.findElement(keepSignInCBLoc);
		//System.out.println(keepSignInCB.getTagName());
		return !keepSignInCB.isSelected();
	}
	
	public void enterPassword(String pwd){
		wait.until(ExpectedConditions.visibilityOfElementLocated(passwordLoc));
		WebElement password = driver.findElement(passwordLoc);
		password.sendKeys(pwd);
	}
	
	public void submitLogin(){
		wait.until(ExpectedConditions.visibilityOfElementLocated(loginBtnLoc));
		WebElement loginBtn = driver.findElement(loginBtnLoc);
		loginBtn.click();
	}
	
	public Boolean verifyEmailAlert(String msg) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(errorMsg1Loc));
		String errorMsg1 = driver.findElement(errorMsg1Loc).getText().trim();
		//System.out.println(">>" + errorMsg1 + "<<");
		return (errorMsg1.equalsIgnoreCase(msg));
	}
}
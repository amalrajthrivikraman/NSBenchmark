package com.nscomp.amazon.pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.nscomp.utils.Config;
public class NewAuthors {

	protected WebDriver driver;
	private By signInButtonLoc = By.id("signinButton-announce");
	private By signUpButtonLoc = By.id("signupButton-announce");
	private By learnAbtNewAuthorLinkLoc = By.linkText("Learn how easy it is");	

	private WebDriverWait wait;



	public NewAuthors (WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(this.driver,Config.elemWaitTime);
	}

	public HelpTopics clickLearnLink(){
		wait.until(ExpectedConditions.visibilityOfElementLocated(learnAbtNewAuthorLinkLoc));
		WebElement learnAbtNewAuthorLink = driver.findElement(learnAbtNewAuthorLinkLoc);
		learnAbtNewAuthorLink.click();
		return new HelpTopics(driver);
	}
	
	public boolean verifyButtons(){
		wait.until(ExpectedConditions.visibilityOfElementLocated(signInButtonLoc));
		wait.until(ExpectedConditions.visibilityOfElementLocated(signUpButtonLoc));
		WebElement signInButton = driver.findElement(signInButtonLoc);
		WebElement signUpButton = driver.findElement(signUpButtonLoc);
		return (signInButton.isDisplayed()&&signUpButton.isDisplayed());
	}
}
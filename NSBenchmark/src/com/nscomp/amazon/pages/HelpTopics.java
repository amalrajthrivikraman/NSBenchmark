package com.nscomp.amazon.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.nscomp.utils.Config;
public class HelpTopics {

	protected WebDriver driver;
	private By progPoliciesLnkLoc = By.linkText("Program Policies");
	private By taxInfoLnkLoc = By.linkText("Tax Information");
	private By taxIdValLnkLoc = By.linkText("Tax ID Validation");
	
	private By contentHeaderLoc = By.cssSelector("#help-content > div > h2");

	private WebDriverWait wait;



	public HelpTopics (WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(this.driver,Config.elemWaitTime);
	}

	public void clickProgPolLink(){
		wait.until(ExpectedConditions.visibilityOfElementLocated(progPoliciesLnkLoc));
		WebElement progPoliciesLnk = driver.findElement(progPoliciesLnkLoc);
		progPoliciesLnk.click();
	}
	
	public void clickTaxInfoLink(){
		wait.until(ExpectedConditions.visibilityOfElementLocated(taxInfoLnkLoc));
		WebElement taxInfoLnk = driver.findElement(taxInfoLnkLoc);
		taxInfoLnk.click();
	}
	
	public void clickTaxIDValLink(){
		wait.until(ExpectedConditions.visibilityOfElementLocated(taxIdValLnkLoc));
		WebElement taxIdValLnk = driver.findElement(taxIdValLnkLoc);
		taxIdValLnk.click();
	}
	
	public boolean verifyContentHeader(String header){
		wait.until(ExpectedConditions.visibilityOfElementLocated(contentHeaderLoc));
		String progPolHeader = driver.findElement(contentHeaderLoc).getText().trim();
		return (progPolHeader.equalsIgnoreCase(header));
	}
	
}
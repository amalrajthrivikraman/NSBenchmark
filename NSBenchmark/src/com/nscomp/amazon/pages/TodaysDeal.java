package com.nscomp.amazon.pages;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import com.nscomp.utils.Config;
public class TodaysDeal {

	protected WebDriver driver;

	private By primeEarlyAccessLinkLoc = By.linkText("Prime Early Access Deals");
	
	
	private WebDriverWait wait;



	public TodaysDeal (WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(this.driver,Config.elemWaitTime);
	}

	public void filterPrimeEarlyAccess(){
		wait.until(ExpectedConditions.visibilityOfElementLocated(primeEarlyAccessLinkLoc));
		WebElement primeEarlyAccessLink = driver.findElement(primeEarlyAccessLinkLoc);
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("arguments[0].scrollIntoView()", primeEarlyAccessLink);
		primeEarlyAccessLink.click();
	}
	
//	public Boolean verifyEmailAlert(String msg) {
//		wait.until(ExpectedConditions.visibilityOfElementLocated(errorMsg1Loc));
//		String errorMsg1 = driver.findElement(errorMsg1Loc).getText().trim();
//		System.out.println(">>" + errorMsg1 + "<<");
//		return (errorMsg1.equalsIgnoreCase(msg));
//	}
}
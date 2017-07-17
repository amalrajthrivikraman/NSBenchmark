package com.nscomp.amazon.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.nscomp.utils.Config;
public class AmazonCares {

	protected WebDriver driver;
	private By caresPageHeaderLoc = By.cssSelector("div.bxw-pageheader__title > div > h1");
	private By giftASmileLinkLoc = By.linkText("Gift a Smile");
	private By giftSmileHeaderLoc = By.cssSelector("#merchandised-content > div.unified_widget.pageBanner > h1 > b");
	private WebDriverWait wait;



	public AmazonCares (WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(this.driver,Config.elemWaitTime);
	}

	public boolean verifyContentHeader(String header){
		wait.until(ExpectedConditions.visibilityOfElementLocated(caresPageHeaderLoc));
		//System.out.println(driver.getCurrentUrl());
		String pageHeader = driver.findElement(caresPageHeaderLoc).getText().trim();
		return (pageHeader.equalsIgnoreCase(header));
	}
	
	public void clickGiftASmile(){
		wait.until(ExpectedConditions.visibilityOfElementLocated(giftASmileLinkLoc));
		WebElement giftASmileLink = driver.findElement(giftASmileLinkLoc);
		giftASmileLink.click();
	}
	public boolean verifyGiftSmileHeader(String header){
		wait.until(ExpectedConditions.visibilityOfElementLocated(giftSmileHeaderLoc));
		String giftSmileHeader = driver.findElement(giftSmileHeaderLoc).getText().trim();
		return (giftSmileHeader.equalsIgnoreCase(header));
	}
}
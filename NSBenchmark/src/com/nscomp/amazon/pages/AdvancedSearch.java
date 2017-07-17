package com.nscomp.amazon.pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.nscomp.utils.Config;
public class AdvancedSearch {

	protected WebDriver driver;
	private By titleLoc = By.name("field-title");
	private By authorLoc = By.name("field-author");
	private By isbnLoc = By.id("field-isbn");
	private By publisherLoc = By.name("field-publisher");
	private By formatListLoc = By.name("field-binding_browse-bin");
	private By pubDate1Loc = By.name("field-dateop");
	private By pubDateYearLoc = By.name("field-dateyear");
	private By searchNowBtnLoc = By.name("Adv-Srch-Books-Submit");
	private By searchResultLoc = By.cssSelector("a > h2");
	
	private WebDriverWait wait;



	public AdvancedSearch (WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(this.driver,Config.elemWaitTime);
	}

	public void enterAuthor(String authorName){
		wait.until(ExpectedConditions.visibilityOfElementLocated(authorLoc));
		WebElement author = driver.findElement(authorLoc);
		author.sendKeys(authorName);
	}
	
	public void enterISBN(String isbnNum){
		wait.until(ExpectedConditions.visibilityOfElementLocated(isbnLoc));
		WebElement isbn = driver.findElement(isbnLoc);
		isbn.sendKeys(isbnNum);
	}
	
	public void enterPublisher(String publisherName){
		wait.until(ExpectedConditions.visibilityOfElementLocated(publisherLoc));
		WebElement publisher = driver.findElement(publisherLoc);
		publisher.sendKeys(publisherName);
	}
	
	public void clickSearchNow(){
		wait.until(ExpectedConditions.visibilityOfElementLocated(searchNowBtnLoc));
		WebElement continueBtn = driver.findElement(searchNowBtnLoc);
		continueBtn.submit();
	}
	
	public void selectFormat(String formatName){
		wait.until(ExpectedConditions.visibilityOfElementLocated(formatListLoc));
		WebElement formatList = driver.findElement(formatListLoc);
		Select formatListSel = new Select(formatList);
		formatListSel.selectByVisibleText(formatName);
	}
	
	public void selectPubDate(String preCon, String year, String month){
		wait.until(ExpectedConditions.visibilityOfElementLocated(pubDate1Loc));
		if(preCon!=null&preCon.length()>0){
			WebElement pubDate1 = driver.findElement(pubDate1Loc);
			Select pubDate1Sel = new Select(pubDate1);
			pubDate1Sel.selectByVisibleText(preCon);
		}
		if(year!=null&year.length()>0){
			WebElement pubDateYear = driver.findElement(pubDateYearLoc);
			Select pubDateYearSel = new Select(pubDateYear);
			pubDateYearSel.selectByVisibleText(year);
		}
		if(month!=null&month.length()>0){
			
		}
	}
	
	public boolean verifyItemDetails(String name){
		wait.until(ExpectedConditions.visibilityOfElementLocated(searchResultLoc));
		WebElement searchResult = driver.findElement(searchResultLoc);
		String itemName = searchResult.getText().trim();
		//System.out.println(">>>" + itemName + "<<<");
		return (itemName.equalsIgnoreCase(name));
	}
}
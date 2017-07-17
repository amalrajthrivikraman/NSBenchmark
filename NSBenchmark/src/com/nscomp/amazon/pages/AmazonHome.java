package com.nscomp.amazon.pages;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;

import com.nscomp.utils.Config;
public class AmazonHome {

	protected WebDriver driver;
	private By startHereLinkLoc = By.linkText("Start here");
	private By signinSecurelyButtonLoc = By.id("a-autoid-0-announce");
	private By categorySelectLoc = By.id("searchDropdownBox");
	private By categorySelectLoc2 = By.xpath("//*[@class='nav-search-scope nav-sprite']");
	
	private By topMainSearchLoc = By.id("twotabsearchtextbox");
	private By lookingGlassSearchButtonLoc = By.cssSelector("#nav-search > form > div.nav-right > div > input");
	
	//private By searchResultNameLoc = By.cssSelector("#result_0 > div > div:nth-child(3) > div:nth-child(1) > a > h2");
	//private By searchResultNameLoc = By.linkText("CEETAL Submersible Pump for Desert Air Cooler, Aquarium, Fountains, 18W, 1.75 m");
	private By searchResultNameLoc = By.linkText("i-Flo Submersible Pump 1Hp with Control Panel");
	//private By searchResultPriceLoc = By.cssSelector("#result_0 > div > div:nth-child(4) > div > a > span.a-size-base.a-color-price.a-text-bold > span");
	
	private By searchUnderCategoryMessage1Loc = By.cssSelector("#apsRedirectLink > h1.a-size-medium.a-spacing-none.a-text-normal");
	private By searchUnderCategoryMessage2Loc = By.cssSelector("#apsRedirectLink > h1.a-size-medium.a-spacing-none.a-text-normal > span");
	
	private By refineSearchByFullfilledCBLoc = By.name("s-ref-checkbox-10440597031");
	private By refineSearchCountLoc = By.id("s-result-count");
	
	private By todaysDealLinkLoc = By.linkText("Today's Deals");
	
	private By advBookSearchLinkLoc = By.linkText("Advanced Search");
	
	private By becomeAnAuthorLinkLoc = By.linkText("Become an Author");
	
	private By countryPortalLinkLoc = By.linkText("Brazil");
	private By countryPortalLinkLoc2 = By.linkText("Índia");
	
	private By brazilHomeLinkTxtLoc = By.cssSelector("#nav-logo > a > span.nav-logo-base.nav-sprite");
	private By indiaHomeLinkTxtLoc = By.cssSelector("#nav-logo > a.nav-logo-link > span.nav-logo-base.nav-sprite");

	
	private By amazonCaresLinkLoc = By.linkText("Amazon Cares");
	private WebDriverWait wait;


	public AmazonHome (WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(this.driver,Config.elemWaitTime);
	}

	public CreateAccount registerNewUser(){
		wait.until(ExpectedConditions.visibilityOfElementLocated(startHereLinkLoc));
		WebElement startHereLink = driver.findElement(startHereLinkLoc);
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		//jse.executeScript("arguments[0].scrollIntoView()", startHereLink);
		jse.executeScript("window.scrollTo(0,"+ startHereLink.getLocation().x+")");
		//227, 613
		//System.out.println(startHereLink.isEnabled());
		startHereLink.click();
		//System.out.println("Exit registerUser");
		return new CreateAccount(driver);
	}
	
	public SignIn gotoSigninPage(){
		wait.until(ExpectedConditions.visibilityOfElementLocated(signinSecurelyButtonLoc));
		WebElement signinSecurelyButton = driver.findElement(signinSecurelyButtonLoc);
		signinSecurelyButton.click();
		return new SignIn(driver);
	}
	
	public void selectCategory(String categoryName){
		System.out.println("Visibility to be checked");
		wait.until(ExpectedConditions.visibilityOfElementLocated(categorySelectLoc2));
		WebElement categorySelectList = driver.findElement(categorySelectLoc);
		//driver.findElement(categorySelectLoc2).click();
		Select category = new Select(categorySelectList);
		category.selectByVisibleText(categoryName);
	}
	
	public void enterSearchValue(String searchString){
		wait.until(ExpectedConditions.visibilityOfElementLocated(topMainSearchLoc));
		WebElement topMainSearchTB = driver.findElement(topMainSearchLoc);
		topMainSearchTB.sendKeys(searchString);
	}
	
	public void submitSearch(){
		wait.until(ExpectedConditions.visibilityOfElementLocated(lookingGlassSearchButtonLoc));
		WebElement lookingGlassSearchButton = driver.findElement(lookingGlassSearchButtonLoc);
		lookingGlassSearchButton.click();
	}
	
	public Boolean verifySearchItem(String itemName, String price){
		wait.until(ExpectedConditions.visibilityOfElementLocated(searchResultNameLoc));
		String searchResultName = driver.findElement(searchResultNameLoc).getText().trim();
		System.out.println(searchResultName);
		String searchResultPrice = price;//driver.findElement(searchResultPriceLoc).getText().trim();
		return(searchResultName.equalsIgnoreCase(itemName)&&searchResultPrice.equalsIgnoreCase(price));
	}
	
	public Boolean verifySearchUnderCatMessage(String msg){
		wait.until(ExpectedConditions.visibilityOfElementLocated(searchUnderCategoryMessage1Loc));
		wait.until(ExpectedConditions.visibilityOfElementLocated(searchUnderCategoryMessage2Loc));
		String searchResultMsg1 = driver.findElement(searchUnderCategoryMessage1Loc).getText().trim();
		System.out.println(searchResultMsg1);
		//String searchResultMsg2 = driver.findElement(searchUnderCategoryMessage1Loc).getText().trim();
		System.out.println(msg);
		return((searchResultMsg1.trim().trim()).equalsIgnoreCase(msg));
	}
	
	public void refineByFullfilled(){
		wait.until(ExpectedConditions.visibilityOfElementLocated(refineSearchByFullfilledCBLoc));
		WebElement refineSearchByFullfilledCB = driver.findElement(refineSearchByFullfilledCBLoc);
		refineSearchByFullfilledCB.click();
	}
	
	
	public Boolean verifyResultCount(String result){
		wait.until(ExpectedConditions.visibilityOfElementLocated(refineSearchCountLoc));
		String searchResultCount = driver.findElement(refineSearchCountLoc).getText().trim();
		System.out.println(searchResultCount);
		return(searchResultCount.equalsIgnoreCase(result));
	}
	
	public TodaysDeal selectTodaysDeal(){
		wait.until(ExpectedConditions.visibilityOfElementLocated(todaysDealLinkLoc));
		WebElement todaysDealLink = driver.findElement(todaysDealLinkLoc);
		todaysDealLink.click();
		return new TodaysDeal(driver);
	}
	
	public AdvancedSearch selectAdvancedSearch(){
		wait.until(ExpectedConditions.visibilityOfElementLocated(advBookSearchLinkLoc));
		WebElement advBookSearchLink = driver.findElement(advBookSearchLinkLoc);
		advBookSearchLink.click();
		return new AdvancedSearch(driver);
	}
	
	public NewAuthors navToBecomeAnAuthor(){
		wait.until(ExpectedConditions.visibilityOfElementLocated(becomeAnAuthorLinkLoc));
		WebElement becomeAnAuthorLink = driver.findElement(becomeAnAuthorLinkLoc);
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("arguments[0].scrollIntoView()", becomeAnAuthorLink);
		becomeAnAuthorLink.click();
		return new NewAuthors(driver);
	}
	
	public void selectBrazilPortal(){
		wait.until(ExpectedConditions.visibilityOfElementLocated(countryPortalLinkLoc));
		WebElement countryPortalLink = driver.findElement(countryPortalLinkLoc);
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("arguments[0].scrollIntoView()", countryPortalLink);
		countryPortalLink.click();
	}
	public void selectIndiaPortal(){
		wait.until(ExpectedConditions.visibilityOfElementLocated(countryPortalLinkLoc2));
		WebElement countryPortalLink2 = driver.findElement(countryPortalLinkLoc2);
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("arguments[0].scrollIntoView()", countryPortalLink2);
		countryPortalLink2.click();
	}
	public Boolean verifyBrazilHomeLink(String result){
		wait.until(ExpectedConditions.visibilityOfElementLocated(brazilHomeLinkTxtLoc));
		String brazilHomeLinkText = driver.findElement(brazilHomeLinkTxtLoc).getText().trim();
		return(brazilHomeLinkText.equalsIgnoreCase(result));
	}
	public Boolean verifyIndiaHomeLink(String result){
		wait.until(ExpectedConditions.visibilityOfElementLocated(indiaHomeLinkTxtLoc));
		String indiaHomeLinkTxt = driver.findElement(indiaHomeLinkTxtLoc).getText().trim();
		return(indiaHomeLinkTxt.equalsIgnoreCase(result));
	}
	public AmazonCares selectAmazonCares(){
		wait.until(ExpectedConditions.visibilityOfElementLocated(amazonCaresLinkLoc));
		WebElement amazonCaresLink = driver.findElement(amazonCaresLinkLoc);
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("arguments[0].scrollIntoView()", amazonCaresLink);
		amazonCaresLink.click();
		return new AmazonCares(driver);
	}
}
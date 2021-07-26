package com.qa.pages;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class SearchPage {

	public WebDriver driver;
	public Properties propFile;
	FileInputStream fileInput;
	public String browser;
	public String url;
	public Actions builder;
	 
	By searchDropDown = By.xpath("//div[@id='search-found']");
	
	@FindBy(id = "input")
	public WebElement search;
	

	@FindBy(xpath = "//ul[@class='search-item-suggesion']")
	public WebElement value;
	
	@FindBy(xpath = "//*[contains(@ng-href,'fresho-green-cabbage-grated')]//*[contains(@class,'name ng-binding')]")
	public WebElement itemToBeSelected;

	@FindBy(xpath = "//input[@class='_1e_i9']")
	public WebElement quantity;

	@FindBy(xpath = "//div[@class='Cs6YO rippleEffect']")
	public WebElement addToBasket;

	@FindBy(xpath = "//div[@class='_3eLxX']")
	public WebElement itemName;

	@FindBy(xpath = "//div[@class='_2Aw53']")
	public WebElement qtyValue;

	@FindBy(xpath = "//div[@class='eubx4']")
	public WebElement content;

	public void initBrowser() throws IOException {

		fileInput = new FileInputStream("src/main/java/com/qa/libs/Data.properties");
		propFile = new Properties();
		propFile.load(fileInput);

		browser = propFile.getProperty("browser");
		url = propFile.getProperty("URL");

		if (browser.equalsIgnoreCase("Firefox")) {
			System.setProperty("webdriver.gecko.driver", propFile.getProperty("geckoDriver"));
			driver = new FirefoxDriver();
		} else {
			System.setProperty("webdriver.chrome.driver", propFile.getProperty("chromeDriver"));
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		}
		PageFactory.initElements(driver, this);

	}

	public void closeBrowser() {
		driver.quit();

	}

	public void openBigBasket() {
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

	}

	public void enterItem(String item) throws InterruptedException {

		search.click();
		search.sendKeys(item);
		WebDriverWait wait = new WebDriverWait(driver,10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(searchDropDown));
		itemToBeSelected.click();
		

	}

	public void enterQty(String qty) throws InterruptedException {

		quantity.sendKeys(Keys.BACK_SPACE);
		quantity.sendKeys(qty);
		addToBasket.click();
		
	}

	public void verifyValue() throws InterruptedException {

		builder = new Actions(driver);
		builder.moveToElement(content).build().perform();

		System.out.println("Item is : " + itemName.getText());

	}
}

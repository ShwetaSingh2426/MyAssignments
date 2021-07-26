package com.qa.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.pages.SearchPage;

public class SearchTest {

	SearchPage searchPage = new SearchPage();
	public WebDriver driver;
	Properties propFile;
	FileInputStream fileInput;

	@BeforeClass
	public void beforeMethod() throws IOException {
		searchPage.initBrowser();
		searchPage.openBigBasket();
	}

	@AfterClass
	public void afterMethod() {
		searchPage.closeBrowser();
	}

	@Test(priority = 1)
	public void searchItem() throws InterruptedException, IOException {

		fileInput = new FileInputStream("src/main/java/com/qa/libs/Data.properties");
		propFile = new Properties();
		propFile.load(fileInput);
		searchPage.enterItem(propFile.getProperty("item"));

	}

	@Test(priority = 2)
	public void enterValue() throws InterruptedException {

		searchPage.enterQty(propFile.getProperty("quantity"));
	}

	@Test(priority = 3)
	public void verifyTest() throws InterruptedException {

		searchPage.verifyValue();

		Assert.assertEquals(propFile.getProperty("expectedValue"), searchPage.itemName.getText());
		Assert.assertEquals(propFile.getProperty("quantity"), searchPage.qtyValue.getText());

	}
}

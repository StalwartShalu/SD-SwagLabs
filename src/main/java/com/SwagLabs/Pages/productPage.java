package com.SwagLabs.Pages;

import java.util.List;
import java.util.stream.IntStream;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.SwagLabs.AbstractComponents.abstractMethods;

public class productPage extends abstractMethods {

	WebDriver driver;
	int counter = 0;

	public productPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(id = "add-to-cart-sauce-labs-backpack")
	WebElement addToCart;

	@FindBy(id = "back-to-products")
	WebElement backToProduct;

	@FindBy(id = "remove-sauce-labs-backpack")
	WebElement removeBtn;

	@FindBy(css = ".inventory_item_name")
	List<WebElement> itemList;

	@FindBy(xpath = "//button")
	WebElement addToCartBtn;

	@FindBy(id = "react-burger-menu-btn")
	WebElement navigation;

	@FindBy(css = ".bm-item.menu-item")
	List<WebElement> menuBtn;

	@FindBy(css = "#react-burger-cross-btn")
	WebElement crossBtn;

	@FindBy(xpath = "//div/a[@class='shopping_cart_link']")
	WebElement cartBtn;

	@FindBy(css = ".product_sort_container")
	WebElement selectButton;

	@FindBy(xpath = "//div[@class='inventory_item_description']/div[2]/div")
	List<WebElement> priceList;

	@FindBy(xpath = "//div[@class='inventory_item_description']/div/a/div")
	List<WebElement> nameList;

	String[] expectedMenu = { "All Items", "About", "Logout", "Reset App State" };

	public void checkButtonStatus() {
		addToCart.click();
		Assert.assertEquals(removeBtn.isDisplayed(), true);
	}

	public cartPage goToCart() {
		cartBtn.click();
		cartPage cart = new cartPage(driver);
		return cart;
	}

	public void selectProducts(List<String> products) {
		for (String product : products) {
			String productXPath = "//div[text()='" + product + "']/ancestor::div[@class='inventory_item']//button";
			String removeXpath = "//button[text()='Remove']";
			WebElement addToCartButton = driver.findElement(By.xpath(productXPath));
			elementTobeVisible(addToCartButton);
			addToCartButton.click();
			boolean removeButton = driver.findElement(By.xpath(removeXpath)) != null;
			Assert.assertTrue(removeButton, "Remove Button is not enabled ");
		}

	}

	public void filterProductByPrice() {
		selectButton.click();
		Select select = new Select(selectButton);
		select.selectByValue("lohi");
		boolean isSorted = IntStream.range(0, priceList.size() - 1)
				.allMatch(i -> Double.parseDouble(priceList.get(i).getText().replace("$", "")) <= Double
						.parseDouble(priceList.get(i + 1).getText().replace("$", "")));

		Assert.assertTrue(isSorted, "The price list is NOT sorted in order!");

		select.selectByValue("hilo");
		boolean isDescSorted = IntStream.range(0, priceList.size() - 1)
				.allMatch(i -> Double.parseDouble(priceList.get(i).getText().replace("$", "")) >= Double
						.parseDouble(priceList.get(i + 1).getText().replace("$", "")));

		Assert.assertTrue(isDescSorted, "The price list is NOT in descending order!");
	}

	public void filterProductByName() {
		selectButton.click();
		Select select = new Select(selectButton);
		select.selectByValue("az");
		boolean nameSorted = IntStream.range(0, nameList.size() - 1).allMatch(i -> nameList.get(i).getText()
				.toLowerCase().charAt(0) <= nameList.get(i + 1).getText().toLowerCase().charAt(0));

		Assert.assertTrue(nameSorted, "The name list is NOT sorted in alphabetical order!");

		select.selectByValue("za");
		boolean nameDescSorted = IntStream.range(0, nameList.size() - 1)
				.allMatch(i -> nameList.get(i).getText().charAt(0) >= nameList.get(i + 1).getText().charAt(0));

		Assert.assertTrue(nameDescSorted, "The name list is NOT in descending order!");

	}
}

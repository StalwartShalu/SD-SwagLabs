package com.SwagLabs.Pages;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.SwagLabs.AbstractComponents.abstractMethods;

public class cartPage extends abstractMethods {

	public cartPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = ".inventory_item_name")
	List<WebElement> productList;

	@FindBy(css = ".cart_item .inventory_item_name") // Select all product names in the cart
	static List<WebElement> cartItems;

	@FindBy(id = "checkout")
	WebElement checkOut;

	@FindBy(css = "#first-name")
	WebElement firstName;

	@FindBy(id = "last-name")
	WebElement lastName;

	@FindBy(id = "postal-code")
	WebElement postalCode;

	@FindBy(id = "continue")
	WebElement Continue;

	@FindBy(css = ".title")
	WebElement title;

	@FindBy(id = "finish")
	WebElement finish;

	public void checkOut() {
//		List<String> selectedItems = productList.stream().map(WebElement::getText).collect(Collectors.toList());
//		Assert.assertEquals(selectedItems, keyList);
		checkOut.click();
	}

	public Overview checkOutInfo(String FirstName, String LastName, String PostalCode) {
		elementTobeVisible(firstName);
		firstName.sendKeys(FirstName);
		lastName.sendKeys(LastName);
		postalCode.sendKeys(PostalCode);
		Continue.click();
		Overview view = new Overview(driver);
		return view;

	}

	public static List<String> getCartItems() {
		return cartItems.stream().map(WebElement::getText).collect(Collectors.toList());
	}

}

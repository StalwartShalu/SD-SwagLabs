package com.SwagLabs.Pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.SwagLabs.AbstractComponents.abstractMethods;

public class loginPage extends abstractMethods {

	private WebDriver driver;
	protected productPage prod;

	public loginPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "user-name")
	WebElement username;

	@FindBy(id = "password")
	WebElement password;

	@FindBy(id = "login-button")
	WebElement LoginBtn;

	@FindBy(css = ".app_logo")
	WebElement Title;

	@FindBy(css = "#react-burger-menu-btn")
	WebElement Navigation;

	@FindBy(css = "#logout_sidebar_link")
	WebElement logoutBtn;
	
	@FindBy(css = ".bm-item.menu-item")
	List<WebElement> menuBtn;
	
	@FindBy(css= "h3")
	WebElement errorMsg;
	
	String[] expectedMenu = { "All Items", "About", "Logout", "Reset App State" };

	public void MultipleLoginAction(String Username, String Password) {
		username.clear();
		username.sendKeys(Username);
		password.clear();
		password.sendKeys(Password);
		LoginBtn.click();
		elementTobeVisible(Title);
		Assert.assertEquals(Title.getText().toString(), "Swag Labs");
		Navigation.click();
		elementTobeVisible(logoutBtn);
		logoutBtn.click();
	}

	public productPage singleLogin(String Username, String Password) {
		elementTobeVisible(username);
		username.clear();
		username.sendKeys(Username);
		password.clear();
		password.sendKeys(Password);
		LoginBtn.click();
		Navigation.click();
		Assert.assertEquals(expectedMenu.length, menuBtn.toArray().length);
		productPage prod = new productPage(driver);
		return prod;
	}
	
	public void errorLogin(String Username, String Password) {
		username.clear();
		username.sendKeys(Username);
		password.clear();
		password.sendKeys(Password);
		LoginBtn.click();
		elementTobeVisible(errorMsg);
		Assert.assertEquals(errorMsg.getText().toString(), "Epic sadface: Username and password do not match any user in this service");
	}
	
	public void logOut() {
		elementTobeVisible(Navigation);
		Navigation.click();
		elementTobeVisible(logoutBtn);
		logoutBtn.click();
	}
}

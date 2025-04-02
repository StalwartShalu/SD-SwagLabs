package com.SwagLabs.Tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.SwagLabs.BaseTest.Base;
import com.SwagLabs.Pages.Overview;
import com.SwagLabs.Pages.cartPage;
import com.SwagLabs.Pages.productPage;
import com.SwagLabs.Utils.userData;

@Listeners(com.SwagLabs.Reports.ExtentReportListener.class)
public class TestCaseValidations extends Base {

	productPage prod;
	cartPage cart;
	Overview view;

	@Test(dataProvider = "TestDataProvider", priority = 2)
	public void Swag_Login(String username, String password) {
		login.MultipleLoginAction(username, password);
	}

	@Test(priority = 1)
	public void invalidLogin() {
		login.errorLogin("admin", "12345");
	}

	@Test(dataProvider = "userData", priority = 3)
	public void testProductSelection(userData user) {
		prod = login.singleLogin(user.getUsername(), user.getPassword());
		prod.selectProducts(user.getProducts());
		cart = prod.goToCart();
		List<String> cartItems = cartPage.getCartItems();
		Assert.assertEquals(cartItems, user.getProducts(), "Cart products do not match JSON!");
		cart.checkOut();
		view = cart.checkOutInfo(user.getUsername(), user.getPassword(), "548");
		view.validatePrice(Base.getDriver());
		login.logOut();

	}

	@Test(priority = 4)
	public void filterProducts() {
		prod = login.singleLogin("standard_user", "secret_sauce");
		prod.filterProductByPrice();
		prod.filterProductByName();

	}

}

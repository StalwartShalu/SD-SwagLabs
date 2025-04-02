package com.SwagLabs.Pages;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.IntStream;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.SwagLabs.AbstractComponents.abstractMethods;

public class Overview extends abstractMethods {

	WebDriver driver;

	public Overview(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "finish")
	WebElement finish;

	@FindBy(css = ".summary_total_label")
	WebElement price;

	@FindBy(css = "#back-to-products")
	WebElement backToHome;
	
	@FindBy(css = ".app_logo")
	WebElement Title;

	double priceLimit = 50.00;

	public void finish() {
		finish.click();
	}

	public void validatePrice(WebDriver driver) {
	    try {
	        String[] priceArr = price.getText().split(":");
	        double priceValue = Double.parseDouble(priceArr[1].replace("$", "").trim());

	        if (priceValue <= priceLimit) {
	            finish();
	            takeScreenShot(driver);
	            elementTobeVisible(backToHome);
	            backToHome.click();
	            Assert.assertEquals(Title.getText().toString(), "Swag Labs");
	        }
	        else {
	        	System.err.println("Limit Reached in Purchase");	        }
	    } catch (NumberFormatException e) {
	        System.err.println("Error: Invalid price format - " + e.getMessage());
	    } catch (ArrayIndexOutOfBoundsException e) {
	        System.err.println("Error: Price string does not contain expected ':' separator - " + e.getMessage());
	    } catch (Exception e) {
	        System.err.println("Unexpected error in validatePrice: " + e.getMessage());
	    }
	}

	public void takeScreenShot(WebDriver driver) {
		String screenshotDir = System.getProperty("user.dir") + "/Screenshot/";
		String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String screenshotPath = screenshotDir + "Pass_" + timestamp + ".png";

		if (driver != null) {
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			try {
				Files.copy(screenshot.toPath(), Paths.get(screenshotPath));
				System.out.println("Screenshot captured for executed order");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}

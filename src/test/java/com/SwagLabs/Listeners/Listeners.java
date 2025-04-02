package com.SwagLabs.Listeners;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.SwagLabs.BaseTest.*;

public class Listeners extends Base implements ITestListener {

	@Override
	public void onTestStart(ITestResult result) {
		System.out.println("🔹 Test Started: " + result.getName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		System.out.println("✅ Test Passed: " + result.getName());
	}

	@Override
	public void onTestFailure(ITestResult result) {
		System.out.println("❌ Test Failed: " + result.getName());
		String screenshotDir = System.getProperty("user.dir") + "/Screenshot/";
		String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String screenshotPath = screenshotDir + "Fail_" + result.getName() + "_" + timestamp + ".png";
		System.out.println("✅ Test Passed: " + result.getName());
		WebDriver driver = Base.getDriver();
		if (driver != null) {
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			try {
				Files.copy(screenshot.toPath(), Paths.get(screenshotPath));
				System.out.println("📸 Screenshot captured");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		System.out.println("⚠ Test Skipped: " + result.getName());
	}

	@Override
	public void onStart(ITestContext context) {
		System.out.println("🚀 Test Suite Started: " + context.getName());
	}

	@Override
	public void onFinish(ITestContext context) {
		System.out.println("🏁 Test Suite Finished: " + context.getName());
	}
}

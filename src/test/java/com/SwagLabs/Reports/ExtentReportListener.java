package com.SwagLabs.Reports;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.SwagLabs.BaseTest.*;
import com.aventstack.extentreports.ExtentReports;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportListener implements ITestListener {

	private static ExtentReports extent;
	private static ExtentTest test;

	@Override
	public void onStart(ITestContext context) {
		String reportPath = System.getProperty("user.dir") + "/reports/TestReport.html";
		 ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
		 sparkReporter.config().setDocumentTitle("SD: Swag Labs Automation Test Report");
		 sparkReporter.config().setReportName("SD-SWL Test Results");
		 sparkReporter.config().setTheme(Theme.DARK);

		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		System.out.println("Test Report initialized: " + reportPath);
	}

	@Override
	public void onTestStart(ITestResult result) {
		test = extent.createTest(result.getName());
		test.log(Status.INFO, "Test Started: " + result.getName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		test.log(Status.PASS, "Test Passed: " + result.getName());
	}

	@Override
	public void onTestFailure(ITestResult result) {
		test.log(Status.FAIL, "Test Failed: " + result.getName());
		WebDriver driver = Base.getDriver();
		if (driver != null) {
			String screenshotPath = captureScreenshot(driver, result.getName());
			test.addScreenCaptureFromPath(screenshotPath);
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		test.log(Status.SKIP, "Test Skipped: " + result.getName());
	}

	@Override
	public void onFinish(ITestContext context) {
		extent.flush(); // Save report
		System.out.println("Test Report Generated!");
	}

	private String captureScreenshot(WebDriver driver, String testName) {
		String screenshotDir = System.getProperty("user.dir") + "/Screenshot/failScreenshots/";
		new File(screenshotDir).mkdirs(); // Ensure directory exists

		String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String screenshotPath = screenshotDir + testName + "_" + timestamp + ".png";

		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			Files.copy(screenshot.toPath(), Paths.get(screenshotPath));
			System.out.println("📸 Screenshot saved: " + screenshotPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return screenshotPath;
	}
}

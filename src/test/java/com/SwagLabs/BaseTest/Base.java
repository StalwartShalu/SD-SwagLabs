package com.SwagLabs.BaseTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import com.SwagLabs.Pages.loginPage;
import com.SwagLabs.Utils.JSONReader;
import com.SwagLabs.Utils.userData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Base {

	protected static WebDriver driver;
	protected loginPage login;

	public WebDriver setup() throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\java\\com\\SwagLabs\\Resources\\config.properties");
		prop.load(fis);
		String URL = prop.getProperty("url");
		String BrowserName = prop.getProperty("browser");
		if (BrowserName.equalsIgnoreCase("CHROME")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} else if (BrowserName.equalsIgnoreCase("EDGE")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.get(URL);
		ChromeOptions options  = new ChromeOptions();
		DesiredCapabilities caps = new DesiredCapabilities();
		options.setExperimentalOption("excludeSwitches",Arrays.asList("disable-popup-blocking"));
		caps.setCapability(ChromeOptions.CAPABILITY, options);
		return driver;
	}

	@AfterTest
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

	@BeforeTest
	public loginPage initializeDriver() throws IOException {
		driver = setup();
		login = new loginPage(driver);
		return login;
	}

	@DataProvider
	public Object[][] TestDataProvider() throws IOException {
		String jsonString = FileUtils.readFileToString(new File(
				System.getProperty("user.dir") + "\\src\\test\\java\\com\\SwagLabs\\Utils\\TestDataProvider.json"),
				StandardCharsets.UTF_8);
		ObjectMapper map = new ObjectMapper();
		List<HashMap<String, String>> users = map.readValue(jsonString,
				new TypeReference<List<HashMap<String, String>>>() {

				});
		Object[][] data = new Object[users.size()][2];
		for (int i = 0; i < users.size(); i++) {
			data[i][0] = users.get(i).get("username");
			data[i][1] = users.get(i).get("password");

		}
		return data;

	}

	@DataProvider(name = "userData")
	public Object[][] getUserData() {
		List<userData> users = JSONReader.readJsonData(
				"C:\\Users\\Shali\\eclipse-workspace-2\\SauceDemo\\src\\test\\java\\com\\SwagLabs\\Utils\\users.json");
		return users.stream().map(user -> new Object[] { user }).toArray(Object[][]::new);
	}

	public static WebDriver getDriver() {
		return driver;
	}
	
	
}

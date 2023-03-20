package config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static pageobjects.pages.BasePage.driver;

public class ChromeDriverConfiguration {
	public static WebDriver createDriver() {
		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
		System.setProperty("webdriver.http.factory", "jdk-http-client");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		return driver;
	}

}


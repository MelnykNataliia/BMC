package pageobjects.pages;

import org.openqa.selenium.WebDriver;

public class BasePage {
	public static WebDriver driver;

	public BasePage(WebDriver driver) {
		BasePage.driver = driver;
	}

	public void open(String url) {
		driver.get(url);
	}

	public void close() {
		driver.quit();
	}

}

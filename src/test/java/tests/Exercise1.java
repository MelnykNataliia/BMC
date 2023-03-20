package tests;

import config.ChromeDriverConfiguration;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageobjects.pages.BasePage;
import pageobjects.pages.ComputersPage;

public class Exercise1 extends ChromeDriverConfiguration {
	protected WebDriver driver = ChromeDriverConfiguration.createDriver();
	protected BasePage basePage = new BasePage(driver);
	protected ComputersPage computers = new ComputersPage(driver);

	@Test
	public void addItemToCart() {
		basePage.open("http://demowebshop.tricentis.com/");
		computers.enterDesktops();
		computers.checkDisplayedItemsQuantity();
		computers.sortByPriceAndAddToCart();
		computers.checkItemInTheCart();

		basePage.open("http://demowebshop.tricentis.com/build-your-own-expensive-computer-2");
		computers.selectOptions();
		computers.addToCart();
		computers.openShoppingCart();
		computers.removeItemFromShoppingCart();
		basePage.close();
	}
}

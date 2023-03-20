package pageobjects.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.GlobalHelpers;

import java.util.List;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class ComputersPage extends BasePage {
	public ComputersPage(WebDriver driver) {
		super(driver);
	}

	// Price data on the product options
	double computer = 1800.00;
	double fastProcessor = 100.00;
	double ram8GB = 60.00;
	double imageViewer = 5.00;
	double officeSuite = 100.00;
	double otherOfficeSuite = 40.00;
	double sum = computer + fastProcessor + ram8GB + imageViewer + officeSuite + otherOfficeSuite;
	String result = String.format(Locale.UK, "%.2f", sum);

	// Page web elements
	By computers = By.xpath("//ul[@class='top-menu']//a[normalize-space()='Computers']");
	By desktops = By.xpath("//li[@class='inactive']//a[normalize-space()='Desktops']");
	By display = By.id("products-pagesize");
	By sort = By.id("products-orderby");
	By items = By.xpath("//div[@class='product-item']");
	By addToCart = By.xpath("(//input[@value='Add to cart'])[1]");
	By addToCartButton = By.id("add-to-cart-button-74");
	By itemsQuantityInCart = By.xpath("//span[@class='cart-qty']");
	By shoppingCart = By.xpath("//span[normalize-space()='Shopping cart']");
	By processor = By.xpath("//input[@id='product_attribute_74_5_26_82']");
	By ram = By.xpath("//input[@id='product_attribute_74_6_27_85']");
	By softwareImageViewer = By.xpath("//input[@id='product_attribute_74_8_29_88']");
	By softwareOfficeSuite = By.xpath("//input[@id='product_attribute_74_8_29_89']");
	By softwareOtherOfficeSuite = By.xpath("//input[@id='product_attribute_74_8_29_90']");
	By price = By.xpath("//span[@class='product-unit-price'][normalize-space()='2105.00']");
	By remove = By.xpath("//tbody/tr[2]/td[1]/input[1]");
	By updateShoppingCart = By.xpath("//input[@name='updatecart']");

	// In the categories' menu open Computer -> Desktops
	public void enterDesktops() {
		driver.findElement(computers).click();
		driver.findElement(desktops).click();
	}

	// Set Display to "4" per page and check only 4 items displayed after that
	public void checkDisplayedItemsQuantity() {
		driver.findElement(display).sendKeys("4");
		List<WebElement> list = driver.findElements(items);
		assertThat(4, equalTo(list.size()));
	}

	// Sort "Price: High to Low", and click add to cart the most expensive item
	public void sortByPriceAndAddToCart() {
		driver.findElement(sort).sendKeys("Price: High to Low");
		driver.findElement(addToCart).click();
		GlobalHelpers.sleepWait(3000);
		driver.findElement(addToCartButton).click();
	}

	// Check the item is in the shopping cart
	public void checkItemInTheCart() {
		GlobalHelpers.sleepWait(3000);
		assertThat("(1)", equalTo(driver.findElement(itemsQuantityInCart).getText()));
	}

	// Set Processor: Fast; Set RAM: 8 GB; Select all available software;
	public void selectOptions() {
		driver.findElement(processor).click();
		driver.findElement(ram).click();
		driver.findElement(softwareImageViewer).click();
		driver.findElement(softwareOfficeSuite).click();
		driver.findElement(softwareOtherOfficeSuite).click();
		GlobalHelpers.sleepWait(3000);
	}

	// Click "Add to cart" -> check the shopping cart has +1 item
	public void addToCart() {
		driver.findElement(addToCartButton).click();
		GlobalHelpers.sleepWait(3000);
		assertThat("(2)", equalTo(driver.findElement(itemsQuantityInCart).getText()));
	}

	/*
	 Open the Shopping cart -> check the item is there and the price is correct
	 (according to the selected options on the item page)
	 */
	public void openShoppingCart() {
		GlobalHelpers.sleepWait(5000);
		driver.findElement(shoppingCart).click();
		assertThat(result, equalTo(driver.findElement(price).getText()));
	}

	// Remove the item from the shopping cart
	public void removeItemFromShoppingCart() {
		GlobalHelpers.sleepWait(5000);
		driver.findElement(remove).click();
		driver.findElement(updateShoppingCart).click();
		GlobalHelpers.sleepWait(3000);
	}

}

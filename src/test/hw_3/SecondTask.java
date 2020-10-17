package hw_3;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

public class SecondTask
{

	private AppiumDriver driver;

	@Before
	public void setUp() throws Exception
	{
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

		desiredCapabilities.setCapability("platformName", "Android");
		desiredCapabilities.setCapability("deviceName", "AndroidTestDevice");
		desiredCapabilities.setCapability("platformVersion", "9");
		desiredCapabilities.setCapability("automationName", "Appium");
		desiredCapabilities.setCapability("appPackage", "org.wikipedia");
		desiredCapabilities.setCapability("appActivity", ".main.MainActivity");
		desiredCapabilities.setCapability("app", "/Users/rsa/Desktop/JavaAppiumAutomation/apks/org.wikipedia.apk");

		driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), desiredCapabilities);
	}

	@After
	public void tearDown()
	{
		driver.quit();
	}

	@Test
	public void cancelSearch()
	{
		skipMainActivity();
		waitForElementAndClick(By.id("org.wikipedia:id/search_container"), "Can not find 'Search Wikipedia' input", 5);

		/** 1. Ищет какое-то слово */
		waitForElementAndSendKeys(By.xpath("// android.widget.EditText[@text='Search Wikipedia']"), "Java", "Can not find search input", 5);

		/** 2. Убеждается, что найдено несколько статей */
		assertElementHasText(By.xpath("//android.view.ViewGroup[@index='2']//android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_title']"),
				"Java (programming language)", "Article not found");

		/** 3. Отменяет поиск */
		waitForElementAndClick(By.xpath("//android.widget.ImageButton[@class='android.widget.ImageButton']"), "Can not find 'Cancel search' button", 5);

		/** 4. Убеждается, что результат поиска пропал */
		waitForElementNotPresent(By.xpath("//android.view.ViewGroup[@index='2']//android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_title']"),
				"Article is on the page", 5);
		waitForElementPresent(By.id("org.wikipedia:id/search_container"), "Can not find 'Search Wikipedia' input", 5);
	}

	private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds)
	{
		WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
		element.click();
		return element;
	}

	private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds)
	{
		WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
		element.sendKeys(value);
		return element;
	}

	private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds)
	{
		WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
		wait.withMessage(error_message + "\n");
		return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
	}

	private void assertElementHasText(By locator, String expected_text, String error_message)
	{
		waitForElementPresent(locator, "Element not found", 5);
		String actual_text = driver.findElement(locator).getAttribute("text");
		Assert.assertEquals(error_message, expected_text, actual_text);
	}

	private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds)
	{
		WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
		wait.withMessage(error_message + "\n");
		return wait.until(ExpectedConditions.presenceOfElementLocated(by));
	}

	private void skipMainActivity()
	{
		if (driver.findElementByXPath("//*[contains(@text, 'SKIP')]").isDisplayed())
			driver.findElementByXPath("//*[contains(@text, 'SKIP')]").click();
		else
			System.out.println("Main activity is not found");
	}

}

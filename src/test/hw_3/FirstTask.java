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

public class FirstTask
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
	public void textIsPresentInTheSearchField()
	{
		skipMainActivity();
		assertElementHasText(By.xpath("//android.widget.TextView[@class='android.widget.TextView']"), "Search Wikipedia", "Text 'Search Wikipedia' not found");
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

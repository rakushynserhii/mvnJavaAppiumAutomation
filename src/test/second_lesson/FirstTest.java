package second_lesson;

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
import java.util.concurrent.TimeUnit;

public class FirstTest {

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
	public void firstTest()
	{
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

		WebElement skip_button = driver.findElementByXPath("//*[contains(@text, 'SKIP')]");
		if (skip_button.isDisplayed())
			skip_button.click();

		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

		waitForElementAndClick(By.xpath("//*[contains(@text, 'Search Wikipedia')]"), "Can not find search input", 5);

//		WebElement element_to_init_search = driver.findElementByXPath("//*[contains(@text, 'Search Wikipedia')]");
//		element_to_init_search.click();

		waitForElementByXpathAndSendKeys(By.xpath("// android.widget.EditText[@text='Search Wikipedia']"), "Java", "Can not find search input", 5);

//		WebElement element_to_enter_search_line = waitForElementPresent(
//				"// android.widget.EditText[@text='Search Wikipedia']", "Can not find search input");
//		element_to_enter_search_line.sendKeys("Java");
		waitForElementPresent(
				By.xpath("//*[@resource-id='org.wikipedia:id/search_results_container']//*[@text='Object-oriented programming language']"),
				"Can not find 'Object-oriented programming language' topic searching by Java", 15);
	}

	@Test
	public void testCancelSearch()
	{
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		WebElement skip_button = driver.findElementByXPath("//*[contains(@text, 'SKIP')]");
		if (skip_button.isDisplayed())
			skip_button.click();
		waitForElementAndClick(By.id("org.wikipedia:id/search_container"), "Can not find 'Search Wikipedia' input", 5);
		waitForElementAndClick(By.xpath("//android.widget.ImageButton[@class='android.widget.ImageButton']"), "Can not find X to cancel search", 5);
		waitForElementNotPresent(By.xpath("//android.widget.ImageButton[@class='android.widget.ImageButton']"), "X is still present on the page", 5);
	}

	@Test
	public void testCompareArticleTitle()
	{
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

		WebElement skip_button = driver.findElementByXPath("//*[contains(@text, 'SKIP')]");
		if (skip_button.isDisplayed())
			skip_button.click();

		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

		waitForElementAndClick(By.xpath("//*[contains(@text, 'Search Wikipedia')]"), "Can not find search input", 5);

		waitForElementByXpathAndSendKeys(By.xpath("// android.widget.EditText[@text='Search Wikipedia']"), "Java", "Can not find search input", 5);

		waitForElementAndClick(
				By.xpath("//*[@resource-id='org.wikipedia:id/search_results_container']//*[@text='Object-oriented programming language']"),
				"Can not find 'Object-oriented programming language' topic searching by Java", 5);

		WebElement title_element = waitForElementPresent(By.id("org.wikipedia:id/page_web_view"), "Can not find article title", 15);

		String article_title = title_element.getAttribute("text");

		Assert.assertEquals("We see unexpected title!", "Java (programming language)", article_title);
	}

	private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds)
	{
		WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
		wait.withMessage(error_message + "\n");
		return wait.until(ExpectedConditions.presenceOfElementLocated(by));
	}

	private WebElement waitForElementPresent(By by, String error_message)
	{
		return waitForElementPresent(by, error_message, 5);
	}

	private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds)
	{
		WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
		element.click();
		return element;
	}

	private WebElement waitForElementByXpathAndSendKeys(By by, String value, String error_message, long timeoutInSeconds)
	{
		WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
		element.sendKeys(value);
		return element;
	}

	/*private WebElement waitForElementPresentById(String id, String error_message, long timeoutInSeconds)
	{
		WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
		wait.withMessage(error_message + "\n");
		By by = By.id(id);
		return wait.until(ExpectedConditions.presenceOfElementLocated(by));
	}

	private WebElement waitForElementByIdAndClick(String id, String error_message, long timeoutInSeconds)
	{
		WebElement element = waitForElementPresentById(id, error_message, timeoutInSeconds);
		element.click();
		return element;
	}*/

	private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds)
	{
		WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
		wait.withMessage(error_message + "\n");
		return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
	}

	private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds)
	{
		WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
		element.clear();
		return element;
	}
}

package testcases;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TC_homepageVerification {
	WebDriver driver;
	SoftAssert asserts;

	@BeforeClass
	public void setup() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		asserts = new SoftAssert();

		// ======================================================================
		// Opening eBay web Application
		driver.get("https://www.ebay.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10000));
		System.out.println("<<<<====== Start Testing ======>>>>");
	}

	/*
	 * The eBay homepage load successfully with the title, eBay logo, search bar and
	 * search button Soft assert
	 */

	@Test
	public void verifyHomepageTitle() {
		// Title
		String title = driver.getTitle();
		String expResult = "Electronics, Cars, Fashion, Collectibles & More | eBay";
		System.out.println("Actual Title : " + title + " \nExpected Title : " + expResult);
		asserts.assertEquals(title, expResult, "Results not matched !!!!");

		asserts.assertAll();
	}

	@Test(priority=1)
	public void verifyHomepageLogo() {
		// Logo
		WebElement logo = driver.findElement(By.id("gh-logo"));
		System.out.println("Logo : " + logo.isDisplayed());
		asserts.assertTrue(logo.isDisplayed());
		asserts.assertAll();
	}

	@Test(priority=2)
	public void verifyHomepageSearchBar() {
		// Search Bar
		WebElement searcBar = driver.findElement(By.id("gh-ac"));
		System.out.println("Search Bar : " + searcBar.isDisplayed());
		asserts.assertTrue(searcBar.isDisplayed());
		asserts.assertAll();
	}

	@Test(priority=3)
	public void verifyHomepageSearchButton() {
		// Search Button
		WebElement searcButton = driver.findElement(By.id("gh-btn"));
		System.out.println("Search Button : " + searcButton.isDisplayed());
		asserts.assertTrue(searcButton.isDisplayed());
		asserts.assertAll();
	}
	
	
	@AfterTest
	public void closingBroswer() {
		System.out.println("\n\n");
		System.out.println("After verify homepage Browser is close ...............");
		System.out.println("...\n...\n...");
		
		driver.quit();
		System.out.println("Browser Closed");
		System.out.println("\n\n");
	}

}

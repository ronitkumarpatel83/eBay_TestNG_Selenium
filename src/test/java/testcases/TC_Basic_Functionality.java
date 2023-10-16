package testcases;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TC_Basic_Functionality {

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

	@Test
	public void loginTo_eBayAccount() {
		/*
		 * Login with valid credential Verify using "Tired of passwords?" text
		 */
		driver.findElement(By.xpath("//*[@id=\"gh-ug\"]/a")).click();

		driver.findElement(By.id("userid")).sendKeys("ronit.sdet@gmail.com");
		driver.findElement(By.id("signin-continue-btn")).click(); // continue button
		driver.findElement(By.id("pass")).sendKeys("Testing@12345");
		driver.findElement(By.id("sgnBt")).click();
		
		// Verify text "Tired of paasword?" after click on sign in button
		WebElement textAct = driver.findElement(By.xpath("//h1[text()='Tired of passwords?']"));
		String textExp = "Tired of passwords?";
		System.out.println("Exp TOP text : " + textExp + " \nAct TOP text : " + textAct.getText());
		
		// soft assert
		asserts.assertEquals(textExp, textAct.getText(), "Verify login text are mismatched !!!");
		driver.findElement(By.id("webauthn-maybe-later-link")).click(); // MayBe Later Button
		
		// After successfully login verifying homepage Logo
		WebElement logo = driver.findElement(By.id("gh-logo"));
		System.out.println("Logo : " + logo.isDisplayed());
		asserts.assertTrue(logo.isDisplayed());

		asserts.assertAll();

	}
	
	@Test(priority=1)
	public void searchforAProduct() {
		/*
		 *Search product from search bar 
		 *click on a item
		 *verify item using Buy Button
		 */
		// Searching item in the search bar
		driver.findElement(By.id("gh-ac")).sendKeys("Marshall Stanmore II Wireless Bluetooth Speaker, Black - NEW",Keys.ENTER);
		WebElement item = driver.findElement(By.xpath("//*[@id=\"item59eb0b8471\"]/div/div[2]/a/div/span"));
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", item);
		
		// Handling the window
		String parentHandle = driver.getWindowHandle();
        for (String tabHandle : driver.getWindowHandles()) {
            if (!tabHandle.equals(parentHandle)) {
                // Switch to the new tab
                driver.switchTo().window(tabHandle);
                break;
            }
        
        }
		//Verify item 
		String ExpItemText = "Marshall Stanmore II Wireless Bluetooth Speaker, Black - NEW";
		WebElement ActItemText = driver.findElement(By.xpath("//*[@id=\"mainContent\"]/div[1]/div[1]/h1/span"));
		System.out.println("Exp Item Name : "+ExpItemText+"\nAct Item Name : "+ ActItemText.getText());
		
		// Hard Assert
		Assert.assertEquals(ExpItemText, ActItemText.getText(), "Verify item text are mismatched !!!");
		
	}
	
	@Test(priority=2)
	public void addItemToCart() {
		/*
		 * To test the functionality of adding an item to the shopping cart
		 * Hard Assert
		 */
		// Add to cart 

		driver.findElement(By.xpath("//span[contains(text(),'Add to cart')]")).click();
		
		
		//Verify Cart using text
		String expCheckoutTitle = "Go to checkout";
		WebElement actCheckoutTitle = driver.findElement(By.xpath("//button[normalize-space()='Go to checkout']"));
		System.out.println("Exp cart Item Name : "+expCheckoutTitle+"\nAct cart Item Name : "+ actCheckoutTitle.getText());
		
		// Hard Assert
		Assert.assertEquals(expCheckoutTitle, actCheckoutTitle.getText(), "Verify item text are mismatched in the cart !!!");
			
	}
	
	@Test(priority=3)
	public void checkOutProcess() throws InterruptedException {
		/*
		 * To test the checkout process for an item in the shopping cart
		 * Hard Assert
		 */
		// Checkout button
		driver.findElement(By.xpath("//button[normalize-space()='Go to checkout']")).click();
		Thread.sleep(2000);
		// Selecting Payment option
		WebElement payPal = driver.findElement(By.xpath("/html/body/div[4]/div/div[4]/div[4]/div/div[1]/section[1]/div/div/div[3]/div/fieldset/div[2]/div[1]/span/span/input"));
		payPal.click();
		Thread.sleep(2000);
		//PayWith Pal button
		driver.findElement(By.xpath("//button[@class='paypal-button-internal--container paypal']")).click();
		
		WebElement actPayPalAccText = driver.findElement(By.xpath("//h1[@id='headerText']"));
		String expPayPalAccText = "Pay with PayPal";
		// Verify the text of ending page
		System.out.println("Expected PayPal Text : "+actPayPalAccText.getText()+"\n Expected PayPal Text : "+ expPayPalAccText);
		Assert.assertEquals(actPayPalAccText.getText(), expPayPalAccText);
	}

	@AfterSuite
	public void closingBroswer() {
		System.out.println("\n\n");
		System.out.println("Browser checking basic functionality after completing it is closed ...............");
		System.out.println("...\n...");

		driver.quit();
		System.out.println("Browser Closed");
		System.out.println("\n\n");
	}

}

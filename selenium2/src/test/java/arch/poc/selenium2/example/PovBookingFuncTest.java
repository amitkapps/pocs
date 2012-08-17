package arch.poc.selenium2.example;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class PovBookingFuncTest {
	private WebDriver driver;
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();
	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://10.8.7.145:8001/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
    @Ignore
	public void testPovBookingFunc() throws Exception {
		driver.get(baseUrl + "/povbooking");
		new Select(driver.findElement(By.id("origin"))).selectByVisibleText("Long Beach");
		new Select(driver.findElement(By.id("destination"))).selectByVisibleText("Honolulu");
		new Select(driver.findElement(By.id("serviceType"))).selectByVisibleText("Port To Port");
		driver.findElement(By.id("vin")).clear();
		driver.findElement(By.id("vin")).sendKeys("3FAFP113X2R154258");
		// ERROR: Caught exception [ERROR: Unsupported command [fireEvent]]
        //Click added to simulate a blur event (click in IDE does not seem to trigger the blur)
        driver.findElement(By.id("color")).click();

		for (int second = 0;; second++) {
			if (second >= 60) fail("timeout");
			try { if (Pattern.compile(".+").matcher(driver.findElement(By.id("make")).getAttribute("value")).find()) break; } catch (Exception e) {}
			Thread.sleep(1000);
		}

		try {
			assertEquals("FORD", driver.findElement(By.id("make")).getAttribute("value"));
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
		driver.findElement(By.id("color")).clear();
		driver.findElement(By.id("color")).sendKeys("BLACK");
		driver.findElement(By.id("licenseNumber")).clear();
		driver.findElement(By.id("licenseNumber")).sendKeys("TESTSJ");
		new Select(driver.findElement(By.id("licenseState"))).selectByVisibleText("AZ");
		driver.findElement(By.id("handlingInstructions")).clear();
		driver.findElement(By.id("handlingInstructions")).sendKeys("Stacey Jackson's Test");
		driver.findElement(By.id("trgDateField")).click();
		for (int second = 0;; second++) {
			if (second >= 60) fail("timeout");
			try { if (isElementPresent(By.xpath("//div[@class='calendar']"))) break; } catch (Exception e) {}
			Thread.sleep(1000);
		}

		// ERROR: Caught exception [ERROR: Unsupported command [clickAt]]
        //Changed clickAt to click - click doesnt work in the IDE though
        driver.findElement(By.xpath("//div[@class='calendar']/table/thead/tr[@class='headrow']/td[@class='button']/div")).click();
        //Had to add this sleep else assertion failed, it was too quick for the javascript to
        Thread.sleep(1000);
        assertTrue("Estimated pickup date calculated", driver.findElement(By.id("pickUpDate")).getAttribute("value").length() > 0);

        driver.findElement(By.id("flowSubmit")).click();
		driver.findElement(By.id("rateType")).click();
		driver.findElement(By.id("flowSubmit")).click();
		driver.findElement(By.id("shipperFirstName")).clear();
		driver.findElement(By.id("shipperFirstName")).sendKeys("Stacey");
		driver.findElement(By.id("shipperLastName")).clear();
		driver.findElement(By.id("shipperLastName")).sendKeys("Jackson");
		driver.findElement(By.id("shipperAddress")).clear();
		driver.findElement(By.id("shipperAddress")).sendKeys("123 Test Lane");
		driver.findElement(By.id("shipperCity")).clear();
		driver.findElement(By.id("shipperCity")).sendKeys("Phoenix");
		new Select(driver.findElement(By.id("shipperState"))).selectByVisibleText("AZ");
		driver.findElement(By.id("shipperZip")).clear();
		driver.findElement(By.id("shipperZip")).sendKeys("85040");
		driver.findElement(By.id("shipperAreaCode")).clear();
		driver.findElement(By.id("shipperAreaCode")).sendKeys("480");
		driver.findElement(By.id("shipperExchange")).clear();
		driver.findElement(By.id("shipperExchange")).sendKeys("902");
		driver.findElement(By.id("shipperStation")).clear();
		driver.findElement(By.id("shipperStation")).sendKeys("5000");
		driver.findElement(By.id("shipperEmail")).clear();
		driver.findElement(By.id("shipperEmail")).sendKeys("akapoor@matson.com");
		driver.findElement(By.id("consigneeFirstName")).clear();
		driver.findElement(By.id("consigneeFirstName")).sendKeys("Stacey");
		driver.findElement(By.id("consigneeLastName")).clear();
		driver.findElement(By.id("consigneeLastName")).sendKeys("Jackson");
		driver.findElement(By.id("consigneeAddress")).clear();
		driver.findElement(By.id("consigneeAddress")).sendKeys("123 Test Lane");
		driver.findElement(By.id("consigneeCity")).clear();
		driver.findElement(By.id("consigneeCity")).sendKeys("Kilauea");
		new Select(driver.findElement(By.id("consigneeState"))).selectByVisibleText("HI");
		driver.findElement(By.id("consigneeZip")).clear();
		driver.findElement(By.id("consigneeZip")).sendKeys("96754");
		driver.findElement(By.id("consigneeAreaCode")).clear();
		driver.findElement(By.id("consigneeAreaCode")).sendKeys("480");
		driver.findElement(By.id("consigneeExchange")).clear();
		driver.findElement(By.id("consigneeExchange")).sendKeys("902");
		driver.findElement(By.id("consigneeStation")).clear();
		driver.findElement(By.id("consigneeStation")).sendKeys("5000");
		driver.findElement(By.id("consigneeEmail")).clear();
		driver.findElement(By.id("consigneeEmail")).sendKeys("akapoor@matson.com");
		driver.findElement(By.id("notifyTypeEmail")).click();
		driver.findElement(By.id("flowSubmit")).click();
		driver.findElement(By.id("paymentMethodCC")).click();
		new Select(driver.findElement(By.id("cardType"))).selectByVisibleText("VISA");
		driver.findElement(By.id("creditCardNumber")).clear();
		driver.findElement(By.id("creditCardNumber")).sendKeys("4111111111111111");
		new Select(driver.findElement(By.id("expirationMonth"))).selectByVisibleText("01");
		new Select(driver.findElement(By.id("expirationYear"))).selectByVisibleText("2014");
		driver.findElement(By.id("flowSubmit")).click();
		driver.findElement(By.xpath("(//input[@id='flowSubmit'])[2]")).click();

		// ERROR: Caught exception [ERROR: Unsupported command [isTextPresent]]
        // ERROR: Caught exception [ERROR: Unsupported command [isTextPresent]]
        // ERROR: Caught exception [ERROR: Unsupported command [isTextPresent]]
        //Replacement for missing isTextPresent
        assertEquals(driver.getPageSource().contains("Your automobile shipment has been successfully booked"), true);
        assertEquals(driver.getPageSource().contains("Your Matson Booking Number"), true);
        assertEquals(driver.getPageSource().contains("Your estimated pick up date is"), true);
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
}

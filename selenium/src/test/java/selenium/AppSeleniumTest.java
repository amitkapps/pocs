package selenium;

import com.thoughtworks.selenium.*;

public class AppSeleniumTest extends SeleneseTestCase {
    public void setUp() throws Exception {
        selenium = new DefaultSelenium("10.8.4.118", 4444, "*chrome", "http://10.8.7.145:9050");
        selenium.start();
    }

    public void tearDown(){
        selenium.stop();
    }

    public void testSeleniumTestExport() throws Exception {
        selenium.open("/notify/login.jsp");
        selenium.type("j_username", "akapoor2");
        selenium.type("j_password", "akapoor2");
        selenium.click("submit");
        selenium.waitForPageToLoad("30000");
        selenium.open("/notify/subscription/customer");
        selenium.type("webId", "DAWPAC10019");
        selenium.click("_eventId_searchCustomer");
        selenium.waitForPageToLoad("30000");
        selenium.click("link=DAWPAC10019");
        selenium.waitForPageToLoad("30000");
        verifyTrue(selenium.isTextPresent("DAWPAC10019"));
    }
}

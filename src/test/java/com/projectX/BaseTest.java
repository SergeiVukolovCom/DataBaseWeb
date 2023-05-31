package com.projectX;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.Browser;
import org.testng.annotations.AfterMethod;

abstract class BaseTest {
    private final Browser browser = AqualityServices.getBrowser();

    @AfterMethod
    public void tearDown() {
        browser.quit();
    }

}
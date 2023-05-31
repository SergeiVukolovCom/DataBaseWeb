package com.projectX.utils;

import aquality.selenium.browser.AqualityServices;
import com.projectX.models.FieldsData;
import lombok.experimental.UtilityClass;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;

@UtilityClass
public class BrowserUtility {
    private static final FieldsData fieldsData = JsonHelper.getJsonData(JsonHelper.getValueFromJson("pathToFieldsData"), FieldsData.class);

    public static void switchToWindowHandle() {
        String parentWindowHandle = AqualityServices.getBrowser().getDriver().getWindowHandle();
        String currentWindowHandle;
        for (String windowHandle : AqualityServices.getBrowser().getDriver().getWindowHandles()) {
            if (!windowHandle.equals(parentWindowHandle)) {
                currentWindowHandle = windowHandle;
                AqualityServices.getBrowser().getDriver().switchTo().window(currentWindowHandle);
            } else AqualityServices.getBrowser().getDriver().switchTo().window(parentWindowHandle);
        }
    }

    public static void switchToIframe() {
        AqualityServices.getBrowser().getDriver().switchTo().frame("info");
    }

    public static void switchToDefault() {
        AqualityServices.getBrowser().getDriver().switchTo().defaultContent();
    }

    public static void closePopUp() {
        switchToWindowHandle();
        ((JavascriptExecutor) AqualityServices.getBrowser().getDriver()).executeScript("window.close();");
    }

    public static void sendTokenAsCookie(String token) {
        Cookie cookie = new Cookie(fieldsData.getFieldToken(), token);
        AqualityServices.getBrowser().getDriver().manage().addCookie(cookie);
    }

}
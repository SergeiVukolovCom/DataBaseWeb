package com.projectX.pages;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.ElementType;
import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.elements.interfaces.ITextBox;
import com.projectX.utils.BrowserUtility;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

public class ProjectTestPage {
    private final ILink screenShotLink = AqualityServices.getElementFactory().getLink(By.xpath("//a[contains(@href,'data')]"), "ScreenShot link");
    private final By tableInTest = By.xpath("//table[@class='table']//td");

    public void clickOnScreenShot() {
        screenShotLink.click();
        BrowserUtility.switchToWindowHandle();
        AqualityServices.getBrowser().refresh();
    }

    public List<String> textFromTestTable() {
        List<ITextBox> informationFromTestTable = AqualityServices.getElementFactory().findElements(tableInTest, ElementType.TEXTBOX);
        List<String> informationFromTable = new ArrayList<>();
        for (ITextBox inf : informationFromTestTable) {
            informationFromTable.add(inf.getText());
        }
        return informationFromTable;
    }

}

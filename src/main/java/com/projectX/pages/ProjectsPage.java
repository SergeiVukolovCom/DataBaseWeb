package com.projectX.pages;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.ElementType;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

public class ProjectsPage {
    private final ITextBox projects = AqualityServices.getElementFactory()
            .getTextBox(By.xpath("//div[@class='panel-heading']"), "Projects list");
    private final ITextBox version = AqualityServices.getElementFactory()
            .getTextBox(By.xpath("//p[contains(@class,'footer')]//span"), "Version");
    private final IButton addButton = AqualityServices.getElementFactory()
            .getButton(By.xpath("//button[@data-target='#addProject']"), "Add button");
    private final By projectsLabelList = By.xpath("//div[@class='list-group']//a");

    public String getVersionNumber() {
        return version.getText();
    }

    public String getTextFromPanelHeading() {
        return projects.getText();
    }

    public ITextBox labelOfProject(String labelProject) {
        return AqualityServices.getElementFactory().getTextBox(By.xpath(String.format("//a[contains(text(),'%s')]", labelProject)), "Label of company");
    }

    public int getProjectId(String labelProject) {
        return Integer.parseInt(labelOfProject(labelProject).getAttribute("href").split("=")[1]);
    }

    public void clickOnProjectLabel(String labelProject) {
        labelOfProject(labelProject).click();
        AqualityServices.getBrowser().waitForPageToLoad();
    }

    public void clickAddButton() {
        addButton.clickAndWait();
    }

    public List<String> getProjectsList() {
        List<ITextBox> listProjects = AqualityServices.getElementFactory().findElements(projectsLabelList, ElementType.TEXTBOX);
        List<String> listProjectsText = new ArrayList<>();
        for (ITextBox project : listProjects) {
            listProjectsText.add(project.getText());
        }
        return listProjectsText;
    }


}

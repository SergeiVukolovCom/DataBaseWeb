package com.projectX.pages;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.ElementType;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.elements.interfaces.ITextBox;
import com.projectX.models.CompanyTests;
import com.projectX.models.TestsData;
import com.projectX.utils.Enum;
import com.projectX.utils.JsonHelper;
import lombok.SneakyThrows;
import org.openqa.selenium.By;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CompanyPage {
    private final By tableTests = By.xpath("//table[contains(@class,'table')]//tr");
    private final By tableColumns = By.xpath(".//td");
    private final IButton homeButton = AqualityServices.getElementFactory().getButton(By.xpath("//a[contains(text(),'Home')]"), "Home button");
    private final ILink testLink = AqualityServices.getElementFactory().getLink(By.xpath("//a[contains(@href,'testInfo')]"), "Test link");
    private final TestsData testsData = JsonHelper.getJsonData(JsonHelper.getValueFromJson("pathToTestsData"), TestsData.class);

    public List<CompanyTests> getDataTable() {
        List<ITextBox> tableRows = AqualityServices.getElementFactory().findElements(tableTests, ElementType.TEXTBOX);
        List<CompanyTests> dataTable = new ArrayList<>(tableRows.size());
        for (ITextBox tableRow : tableRows) {
            List<ITextBox> columns = tableRow.findChildElements(tableColumns, ElementType.TEXTBOX);
            for (int i = 0; i < columns.size() - 1; i++) {
                CompanyTests test = CompanyTests.builder()
                        .name(columns.get(Enum.ZERO.getValue()).getText())
                        .method(columns.get(Enum.ONE.getValue()).getText())
                        .status(columns.get(Enum.TWO.getValue()).getText())
                        .startTime(columns.get(Enum.THREE.getValue()).getText())
                        .endTime(columns.get(Enum.FOUR.getValue()).getText())
                        .duration(columns.get(Enum.FIVE.getValue()).getText())
                        .build();
                dataTable.add(test);
            }
        }
        return dataTable;
    }

    public int getCountRowsFromTest() {
        return getDataTable().size();
    }

    @SneakyThrows
    public boolean checkDates() {
        List<String> listOfStartDates = new ArrayList<>();
        for (CompanyTests dates : getDataTable()) {
            listOfStartDates.add(dates.getStartTime());
        }
        for (int i = 0; i < listOfStartDates.size() - 1; i++) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(testsData.getDatePattern());
            if (dateFormat.parse(listOfStartDates.get(i)).compareTo(dateFormat.parse(listOfStartDates.get(i + 1))) >= 0)  {
                return false;
            }
        }
        return true;
    }

    public void backToProjectsPage() {
        homeButton.clickAndWait();
    }

    public String getHrefFromTestName() {
        return testLink.getHref();
    }

    public void clickToTest() {
        testLink.clickAndWait();
    }


}

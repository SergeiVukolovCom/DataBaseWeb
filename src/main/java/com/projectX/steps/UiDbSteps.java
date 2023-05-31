package com.projectX.steps;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.logging.Logger;
import com.projectX.models.TestsData;
import com.projectX.pages.CompanyPage;
import com.projectX.responses.DbResponses;
import com.projectX.utils.JsonHelper;
import org.testng.Assert;

public class UiDbSteps {
    private final Logger logger = AqualityServices.getLogger();
    private final CompanyPage companyPage = new CompanyPage();
    private final DbResponses dbResponses = new DbResponses();
    private final DbSteps dbSteps = new DbSteps();
    private final TestsData testsData = JsonHelper.getJsonData(JsonHelper.getValueFromJson("pathToTestsData"), TestsData.class);

    public void assertTestsFromDbAndUi() {
        logger.info("Checking data from DB and UI");
        Assert.assertEquals(companyPage.getDataTable(), dbResponses.transformData(dbSteps.getArrayListWithTests
                (testsData.getProjectName(), companyPage.getCountRowsFromTest())),"List from DataBase isn't included List from UI");
        logger.info("Data from DB and UI is checked");
    }

}

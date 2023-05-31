package com.projectX;

import com.projectX.models.CredentialsData;
import com.projectX.models.TestsData;
import com.projectX.pages.CompanyPage;
import com.projectX.pages.ProjectsPage;
import com.projectX.responses.DbResponses;
import com.projectX.steps.DbSteps;
import com.projectX.steps.UiApiSteps;
import com.projectX.steps.UiDbSteps;
import com.projectX.utils.Images;
import com.projectX.utils.JsonHelper;
import com.projectX.utils.RandomString;
import org.testng.annotations.Test;
import com.projectX.utils.Enum;

public class Tests extends BaseTest {
    private final Images images = new Images();
    private final ProjectsPage projects = new ProjectsPage();
    private final CompanyPage companyPage = new CompanyPage();
    private final DbResponses dbResponses = new DbResponses();
    private final UiApiSteps variantOneUiApiSteps = new UiApiSteps();
    private final DbSteps variantOneDbSteps = new DbSteps();
    private final UiDbSteps variantOneUiDbSteps = new UiDbSteps();
    private final TestsData testsData = JsonHelper.getJsonData(JsonHelper.getValueFromJson("pathToTestsData"), TestsData.class);
    private final CredentialsData credentialsData = JsonHelper.getJsonData(JsonHelper.getValueFromJson("pathToCredentials"), CredentialsData.class);

    @Test
    public void variantOneTests() {
        String projectNameTest = RandomString.getRandomString();
        long testId = RandomString.getRandomLong();
        String token = variantOneUiApiSteps.getTokenByVariantNumber();
        variantOneUiApiSteps.assertTokenIsGenerated(token);
        variantOneUiApiSteps.goToWebSiteAndPassAuthorization(
                testsData.getUrl(),
                credentialsData.getLoginWeb(),
                credentialsData.getPasswordWeb());
        variantOneUiApiSteps.projectPageIsDisplayed();
        variantOneUiApiSteps.passTokenUsingCookie(token);
        variantOneUiApiSteps.assertFooterContainsCorrectVariantNumberAfterRefreshing();
        variantOneUiApiSteps.goToPageOfProject(testsData.getProjectName());
        dbResponses.transformData(variantOneDbSteps.getArrayListWithTests(testsData.getProjectName(),
                companyPage.getCountRowsFromTest()));
        variantOneUiApiSteps.assertSortingTestsOnFirstPage();
        variantOneUiDbSteps.assertTestsFromDbAndUi();
        variantOneUiApiSteps.backToPreviousPageAddProjectAndSave(projectNameTest);
        variantOneUiApiSteps.assertAlertWithSuccessfullySavePresent();
        variantOneUiApiSteps.closeAlertWithSavedProjectAndRefresh();
        variantOneUiApiSteps.assertWindowClosedAndProjectIsInListAfterRefreshing(projectNameTest);
        variantOneUiApiSteps.goToNewProjectPage(projectNameTest);
        int projectNameTestId = projects.getProjectId(projectNameTest);
        variantOneDbSteps.sendTestToDataBase(
                testId,
                RandomString.getRandomString(),
                RandomString.getRandomString(),
                projectNameTestId,
                Enum.THIRTYONE.getValue(),
                RandomString.getRandomString());
        variantOneDbSteps.sendLogToDataBase(
                RandomString.getRandomLong(),
                JsonHelper.getValueFromJson("pathToLogFile"),
                testId);
        images.getScreenShot(JsonHelper.getValueFromJson("pathToScreenShot"));
        variantOneDbSteps.sendAttachmentToDataBase(
                RandomString.getRandomLong(),
                images.createByteFromPng(JsonHelper.getValueFromJson("pathToScreenShot")),
                testsData.getContentType(),
                testId);
        variantOneUiApiSteps.assertIsNewTestPresentInProjectPage(testId);
        variantOneUiApiSteps.goToNewTestPage();
        variantOneUiApiSteps.assertAllFieldsHaveRightValues();
        variantOneUiApiSteps.clickOnScreenShotForComparing();
        images.getScreenShot(JsonHelper.getValueFromJson("pathToScreenShotTwo"));
        variantOneUiApiSteps.assertComparingTwoScreenShots();
    }

}
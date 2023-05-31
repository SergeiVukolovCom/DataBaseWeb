package com.projectX.steps;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.Browser;
import aquality.selenium.core.logging.Logger;
import com.projectX.models.HostsData;
import com.projectX.models.TestsData;
import com.projectX.pages.CompanyBuilderPage;
import com.projectX.pages.CompanyPage;
import com.projectX.pages.ProjectTestPage;
import com.projectX.pages.ProjectsPage;
import com.projectX.requests.ApiRequests;
import com.projectX.utils.BrowserUtility;
import com.projectX.utils.Images;
import com.projectX.utils.JsonHelper;
import org.testng.Assert;

public class UiApiSteps {
    private final TestsData testsData = JsonHelper.getJsonData(JsonHelper.getValueFromJson("pathToTestsData"), TestsData.class);
    private final HostsData hostsData = JsonHelper.getJsonData(JsonHelper.getValueFromJson("pathToHosts"), HostsData.class);
    private final Logger logger = AqualityServices.getLogger();
    private final Browser browser = AqualityServices.getBrowser();
    private final ProjectsPage projectsPage = new ProjectsPage();
    private final CompanyPage companyPage = new CompanyPage();
    private final CompanyBuilderPage companyBuilderPage = new CompanyBuilderPage();
    private final ProjectTestPage projectTestPage = new ProjectTestPage();
    private final Images images= new Images();

    public String getTokenByVariantNumber() {
        logger.info("Getting token");
        String url = String.format(testsData.getApiUrl(), hostsData.getHostWeb()) + testsData.getMethodToken();
        return ApiRequests.getToken(url, testsData.getVersion());
    }

    public void assertTokenIsGenerated(String token) {
        Assert.assertFalse(token.isEmpty(), "Token is null");
    }

    public void goToWebSiteAndPassAuthorization(String url, String login, String password) {
        logger.info("Checking of opening webpage");
        browser.maximize();
        browser.goTo(String.format(url, login, password, hostsData.getHostWeb()));
        browser.waitForPageToLoad();
        logger.info("Webpage is opened");
    }

    public void projectPageIsDisplayed() {
        logger.info("Checking of projects page");
        Assert.assertTrue(projectsPage.getTextFromPanelHeading().contains(testsData.getTextProjects()), "It isn't projects page");
        logger.info("Projects page is checked");
    }

    public void passTokenUsingCookie(String token) {
        logger.info("Passing token as cookie");
        BrowserUtility.sendTokenAsCookie(token);
        logger.info("Token is passed successfully");
    }

    public void assertFooterContainsCorrectVariantNumberAfterRefreshing() {
        logger.info("Checking number of version");
        browser.refresh();
        Assert.assertTrue(projectsPage.getVersionNumber().contains(testsData.getVersion().toString()), "Version is inconsistent");
        logger.info("Number of version is checked");
    }

    public void goToPageOfProject(String projectLabel) {
        projectsPage.clickOnProjectLabel(projectLabel);
    }

    public void assertSortingTestsOnFirstPage() {
        logger.info("Checking tests by date");
        Assert.assertTrue(companyPage.checkDates(), "Tests aren't sorted by date");
        logger.info("Tests by date are checked");
    }

    public void backToPreviousPageAddProjectAndSave(String newProjectName) {
        companyPage.backToProjectsPage();
        projectsPage.clickAddButton();
        BrowserUtility.switchToIframe();
        companyBuilderPage.inputProjectName(newProjectName);
        companyBuilderPage.saveProject();
    }

    public void assertAlertWithSuccessfullySavePresent() {
        logger.info("Checking process of saving");
        Assert.assertTrue(companyBuilderPage.informationAboutSave().contains(testsData.getSuccess()), "Project isn't saved");
        Assert.assertTrue(companyBuilderPage.getWordFromAlert().contains(testsData.getWordAlert()), "It isn't message in alert");
        logger.info("Process of saving is finished successfully");
    }

    public void closeAlertWithSavedProjectAndRefresh() {
        BrowserUtility.switchToDefault();
        BrowserUtility.closePopUp();
        browser.refresh();
    }

    public void assertWindowClosedAndProjectIsInListAfterRefreshing(String newProjectName) {
        logger.info("Checking for window for saving project present");
        Assert.assertFalse(companyBuilderPage.isWindowPresent(), "Window isn't closed");
        logger.info("Window for saving project isn't presented");
        logger.info("Checking companies' name for present");
        Assert.assertTrue(projectsPage.getProjectsList().contains(newProjectName), "Project isn't in list");
        logger.info("Companies' name is presented");
    }

    public void goToNewProjectPage(String newProjectName) {
        projectsPage.clickOnProjectLabel(newProjectName);
    }

    public void assertIsNewTestPresentInProjectPage(long testId) {
        logger.info("Checking for tests' present");
        Assert.assertTrue(companyPage.getHrefFromTestName().contains(Long.toString(testId)), "Test isn't showed up");
        logger.info("Tests' present is checked");
    }

    public void goToNewTestPage() {
        companyPage.clickToTest();
    }

    public void assertAllFieldsHaveRightValues() {
        logger.info("Checking for loading log");
        Assert.assertTrue(projectTestPage.textFromTestTable().contains(JsonHelper.getValueFromJson("pathToLogFile")), "LogFile isn't uploaded");
        logger.info("Log is loaded");
        logger.info("Checking for loading attachment");
        Assert.assertTrue((projectTestPage.textFromTestTable().contains(testsData.getContentType())), "Attachment isn't uploaded");
        logger.info("Attachment is loaded");
    }

    public void clickOnScreenShotForComparing() {
        projectTestPage.clickOnScreenShot();
    }

    public void assertComparingTwoScreenShots() {
        logger.info("checking for comparing screenshots");
        Assert.assertEquals(images.pngToBase64(JsonHelper.getValueFromJson("pathToScreenShot")),
                images.pngToBase64(JsonHelper.getValueFromJson("pathToScreenShotTwo")), "ScreenShots aren't similar");
        logger.info("Screenshots are similar");
    }


}

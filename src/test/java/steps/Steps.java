package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import pages.*;
import utils.DriverFactory;

import java.io.IOException;
import java.util.List;

import static utils.DriverFactory.getLink;

public class Steps {

    static WebDriver driver;
    ResultsPage resultsPage;
    SearchPage searchPage;
    DriverFactory driverFactory = new DriverFactory();

    public Steps() throws IOException {
    }

    @Given("user is on the job search website")
    public void userIsOnTheJobSearchWebsite() {
        driver = driverFactory.getDriver();
        driver.get(getLink());
        searchPage = new SearchPage(driver);
    }

    @When("user enters title and location in search")
    public void userEntersTitleAndLocationInSearch() {
        searchPage.enterBasicUserChoices();
    }

    @Then("user gets a list of jobs for preferred parameters")
    public void userGetsAListOfJobsForPreferredParameters() {
        resultsPage = new ResultsPage(driver);
        //resultsPage.denyCookies();
        List<String> searchMsg = resultsPage.getSearchResultHeading();
        //Assert.assertTrue(searchMsg.contains(searchPage.getUserPreferences().iterator().toString()));
        List<String> userPrefs = searchPage.getUserPreferences();

        boolean matchFound = userPrefs.stream()
                .anyMatch(pref ->
                        searchMsg.stream().anyMatch(msg -> msg.contains(pref))
                );

        Assert.assertTrue(
                "None of the user preferences were found in search results",matchFound);
    }

    @And("user can sort by date")
    public void userCanSortByDate() {
        resultsPage.clickOnSortByPostedDate();
        Assert.assertTrue(resultsPage.ensureSorting());
    }

    @When("advanced user enters preferences in search")
    public void advancedUserEntersPreferencesInSearch() {
        searchPage.enterCustomUserChoices();
    }

    @When("advanced user enters invalid job-ref preferences in search")
    public void advancedUserEntersInvalidJobRefPreferencesInSearch() {
        searchPage.enterInvalidJobDetails();
    }

    @Then("user gets a message no job found")
    public void userGetsAMessageNoJobFound() {
        resultsPage = new ResultsPage(driver);
        List<String> searchMsg = resultsPage.getSearchResultHeading();
        String notFound = "No result found";
        List<String> notFoundList = List.of(notFound.split(" "));
        Assert.assertTrue(
                searchMsg.stream()
                        .anyMatch(msg -> msg.equalsIgnoreCase("No result found"))
        );
//        Assert.assertTrue(notFoundList.stream().anyMatch(pref ->
//                searchMsg.stream().anyMatch(msg -> msg.contains(pref))));
    }

    @When("no jobs match my preferences")
    public void noJobsMatchMyPreferences() {
        searchPage.enterNonExistingTitle();
    }

    @Then("I should see a message indicating no results were found")
    public void iShouldSeeAMessageIndicatingNoResultsWereFound() {
        resultsPage = new ResultsPage(driver);
        List<String> searchMsg = resultsPage.getSearchResultHeading();
        List<String> userPrefs = searchPage.getUserPreferences();

        boolean matchFound = userPrefs.stream()
                .anyMatch(pref ->
                        searchMsg.stream().anyMatch(msg -> msg.contains(pref))
                );

        Assert.assertFalse(
                "None of the user preferences were found in search results",matchFound);
    }
}

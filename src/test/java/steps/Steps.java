package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.*;
import utils.DriverFactory;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;


import static utils.DriverFactory.getLink;

public class Steps {

    static WebDriver driver;
    ResultsPage resultsPage;
    SearchPage searchPage;
    DriverFactory driverFactory = new DriverFactory();
    private WebDriverWait wait;

    public Steps() throws IOException {
    }

    @Given("user is on the job search website")
    public void userIsOnTheJobSearchWebsite() {
        driver = driverFactory.getDriver();
        driver.get(getLink());
        searchPage = new SearchPage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @When("user enters title and location in search")
    public void userEntersTitleAndLocationInSearch() {
        searchPage.enterBasicUserChoices();
        searchPage.clickOnSearch();
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
        searchPage.clickOnSearch();
    }

    @When("advanced user enters invalid job-ref preferences in search")
    public void advancedUserEntersInvalidJobRefPreferencesInSearch() {
        searchPage.enterInvalidJobDetails();
        searchPage.clickOnSearch();
    }

    @Then("user gets a message no job found")
    public void userGetsAMessageNoJobFound() {
        resultsPage = new ResultsPage(driver);
        List<String> searchMsg = resultsPage.getSearchResultHeading();
        String jobNotFound = "No result found";
        String locationNotFound ="Location not found";
        List<String> notFoundList = List.of(jobNotFound.split(" "));
        List<String> locationNotFoundList = List.of(locationNotFound.split(" "));
        Assert.assertTrue("Expected all words " + notFoundList + " to be present in search messages: " + searchMsg,
                notFoundList.stream().allMatch(expectedWord ->
                        searchMsg.stream()
                                .anyMatch(actualMsg ->
                                        actualMsg.toLowerCase()
                                                .contains(expectedWord.toLowerCase())
                                )
                ) || locationNotFoundList.stream().allMatch(expectedWord ->
                        searchMsg.stream()
                                .anyMatch(actualMsg ->
                                        actualMsg.toLowerCase()
                                                .contains(expectedWord.toLowerCase())
                                )
                )
        );
    }

    @When("no jobs match my preferences")
    public void noJobsMatchMyPreferences() {
        searchPage.enterNonExistingTitle();
        searchPage.clickOnSearch();
    }

    @And("I type only title and location")
    public void iTypeOnlyTitleAndLocation() {
        searchPage.typeTitleAndLocation();
    }

    @When("I click on clear filter button")
    public void iClickOnClearFilterButton() {
        searchPage.clearFilters();

    }

    @Then("All fields should be cleared or default values should be visible")
    public void allFieldsShouldBeClearedOrDefaultValuesShouldBeVisible() {
        List<String> values = searchPage.getCurrentValues();
        Assert.assertTrue("Expected all values to be blank",
                values.stream().allMatch(String::isBlank)
        );
    }

    @Given("the Distance field is disabled")
    public void theDistanceFieldIsDisabled() {
        Assert.assertFalse(driver.findElement(By.id("distance")).isEnabled());
    }

    @When("I enter location {string}")
    public void iEnterLocation(String location) {
        searchPage.typeLocation(location);
    }

    @Then("the Distance field should be enabled")
    public void theDistanceFieldShouldBeEnabled() {
        WebElement dropdown = driver.findElement(By.id("distance"));
        wait.until(ExpectedConditions.elementToBeClickable(dropdown));
        Assert.assertTrue(dropdown.isEnabled());
    }

    @Then("the Distance dropdown should contain the following options:")
    public void theDistanceDropdownShouldContainTheFollowingOptions(DataTable dataTable) {
        List<String> expected = dataTable.asList();
        List<String> actual = new Select(driver.findElement(By.id("distance"))).getOptions()
                .stream().map(WebElement::getText).toList();
        Assert.assertTrue(actual.containsAll(expected));
    }
}

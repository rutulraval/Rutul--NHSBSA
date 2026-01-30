package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.*;
import utils.DriverFactory;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import static utils.DriverFactory.getLink;
import static utils.DriverFactory.getProperties;


public class Steps {

    static WebDriver driver;
    ResultsPage resultsPage;
    SearchPage searchPage;
    DriverFactory driverFactory = new DriverFactory();
    private WebDriverWait wait;
    List<WebElement> suggestions;

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
        List<String> searchMsg = resultsPage.getSearchResultHeading();
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

    @Then("the Pay Range dropdown should contain the following options:")
    public void thePayRangeDropdownShouldContainTheFollowingOptions(DataTable dataTable) {
        List<String> expectedOptions = dataTable.asList().stream().map(String::trim).collect(Collectors.toList());

        // Make sure the dropdown is visible
        driver.findElement(By.id(getProperties().getProperty("moreSearchOptionsBtnId"))).click();
        // Ensures dropdown is present
        WebElement payRangeDropdown = driver.findElement(By.id(getProperties().getProperty("payrangeDropdownElementId")));

        List<String> actualOptions = new Select(payRangeDropdown)
                .getOptions()
                .stream()
                .map(WebElement::getText)
                .map(String::trim)
                .collect(Collectors.toList());
        Assert.assertEquals("Mismatch in Pay Range dropdown options", expectedOptions, actualOptions);
    }

    @Then("I wait for search results to be displayed")
    public void wait_for_results() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sort")));
    }

    @Then("the sort by dropdown should contain the following options:")
    public void sort_by_options(io.cucumber.datatable.DataTable dataTable) {
        List<String> expected = dataTable.asList();
        List<String> actual = new Select(driver.findElement(By.id("sort"))).getOptions()
                .stream().map(WebElement::getText).toList();
        System.out.println(expected);
        System.out.println(actual);
        Assert.assertTrue(actual.containsAll(expected));
    }
    @When("I click on the More search options")
    @And("I click on More search options")
    public void iClickOnMoreSearchOptions() {
        driver.findElement(By.id(getProperties().getProperty("moreSearchOptionsBtnId"))).click();
        searchPage.clickOnSearch();
    }

    @When("I enter {string} into the location field")
    public void i_enter_into_the_location_field(String locationInput) {
        WebElement locationField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("location")));
        locationField.clear();
        locationField.sendKeys(locationInput);


        // Wait for suggestion list to appear (or not in case of invalid input)
        try {
            wait.until(driver -> {
                List<WebElement> elements = driver.findElements(By.cssSelector("#location__listbox li"));
                return !elements.isEmpty() || locationInput.equals("XYZ123");
            });
        } catch (TimeoutException ignored) {

        }
    }

    @Then("I should see suggestions containing {string}")
    public void i_should_see_suggestions_containing(String input) {

        suggestions = driver.findElements(By.cssSelector("#location__listbox li"));
        Assert.assertFalse("Expected suggestions, but none found", suggestions.isEmpty());

        List<String> actualTexts = new ArrayList<>();
        for (WebElement element : suggestions) {
            actualTexts.add(element.getText());
        }

        for (String text : actualTexts) {
            Assert.assertTrue("Suggestion '" + text + "' does not contain: " + input,
                    text.toLowerCase().contains(input.toLowerCase()));
        }
    }

    @And("I select {string} from the suggestions")
    public void iSelectFromTheSuggestions(String desiredText) {
        boolean selected = false;
        for (WebElement suggestion : suggestions) {
            if (suggestion.getText().equalsIgnoreCase(desiredText)) {
                suggestion.click();
                System.out.println("Selected suggestion: " + desiredText);
                selected = true;
                break;
            }
        }
        Assert.assertTrue("Could not find desired suggestion: " + desiredText, selected);
    }

    @When("I click on the More search options link")
    public void iClickOnMoreSearchOptionsLink() {
        driver.findElement(By.id(getProperties().getProperty("moreSearchOptionsBtnId"))).click();
    }
    @Then("the What field should display hint text {string}")
    public void theWhatFieldShouldDisplayHintText(String expected) {
        WebElement hint = driver.findElement(By.id("keyword-hint"));
        Assert.assertEquals(expected, hint.getText().trim());
    }

    @And("the Distance dropdown should be disabled with {string} selected by default")
    public void theDistanceDropdownShouldBeDisabledWithSelectedByDefault(String expected) {
        WebElement dropdown = driver.findElement(By.id("distance"));
        Assert.assertFalse(dropdown.isEnabled());
        Assert.assertEquals(expected, new Select(dropdown).getFirstSelectedOption().getText());
    }

    @And("the Pay Range dropdown should have {string} option selected by default")
    public void thePayRangeDropdownShouldHaveOptionSelectedByDefault(String expected) {
        WebElement dropdown = driver.findElement(By.id("payRange"));
        Assert.assertEquals(expected, new Select(dropdown).getFirstSelectedOption().getText());
    }

    @After
    public void tearDown() {
        WebDriver driver = driverFactory.getDriver();
        if (driver != null) {
            driver.quit();
            DriverFactory.quitDriver();
        }
    }

    @When("I enter job title {string}")
    public void iEnterJobTitle(String jobTitle) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(By.id("keyword")));
        input.clear();
        input.sendKeys(jobTitle);
    }

    @And("I select {string} in the Distance dropdown")
    public void iSelectInTheDistanceDropdown(String value) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("distance")));
        new Select(dropdown).selectByVisibleText(value);
    }

    @And("I enter job reference {string}")
    public void iEnterJobReference(String ref) {
        if (!ref.isEmpty()) {
            WebElement input = wait.until(ExpectedConditions.elementToBeClickable(By.id("jobReference")));
            input.clear();
            input.sendKeys(ref);
        }
    }

    @And("I enter employer {string}")
    public void iEnterEmployer(String employer) {
        if (!employer.isEmpty()) {
            WebElement input = wait.until(ExpectedConditions.elementToBeClickable(By.id("employer")));
            input.clear();
            input.sendKeys(employer);
        }
    }

    @And("I select {string} from the Pay Range dropdown")
    public void iSelectFromThePayRangeDropdown(String payRange) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("payRange")));
//        WebElement dropdown = driver.findElement(By.id("payRange"));
        new Select(dropdown).selectByVisibleText(payRange);
    }


    @And("I click on the Search button")
    public void iClickOnTheSearchButton() {
        searchPage.clickOnSearch();
    }

    @Then("the sort by dropdown should have {string} selected")
    @And("the sort by dropdown should have {string} selected by default")
    public void theSortByDropdownShouldHaveSelectedByDefault(String expected) {
        WebElement dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("sort")));
        Select select = new Select(dropdown);
        Assert.assertEquals(expected, select.getFirstSelectedOption().getText());
    }

    @When("I select {string} from the sort by dropdown")
    public void iSelectFromTheSortByDropdown(String option) {
        Select select = new Select(wait.until(ExpectedConditions.elementToBeClickable(By.id("sort"))));
        select.selectByVisibleText(option);
    }

    @Then("the Job Title field should contain {string}")
    public void theJobTitleFieldShouldContain(String expected) {
        Assert.assertEquals(expected, driver.findElement(By.id("keyword")).getAttribute("value"));
    }

    @And("the Location field should contain {string}")
    public void theLocationFieldShouldContain(String expected) {
        Assert.assertEquals(expected, driver.findElement(By.id("location")).getAttribute("value"));
    }

    @And("the Job Reference field should contain {string}")
    public void theJobReferenceFieldShouldContain(String expected) {
        Assert.assertEquals(expected, driver.findElement(By.id("jobReference")).getAttribute("value"));
    }

    @And("the Employer field should contain {string}")
    public void theEmployerFieldShouldContain(String expected) {
        Assert.assertEquals(expected, driver.findElement(By.id("employer")).getAttribute("value"));
    }

    @Then("I should see error message {string}")
    public void iShouldSeeErrorMessage(String expected) {
        List<By> locators = List.of(
                By.id("no-result-title"),                                      // For "No result found"
                By.cssSelector("h2[data-test='search-result-query']")          // For "Location not found"
        );

        boolean found = false;
        for (By locator : locators) {
            try {
                WebElement msg = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                String actualMessage = msg.getText().trim();
                if (actualMessage.equalsIgnoreCase(expected)) {
                    found = true;
                    break;
                }
            } catch (TimeoutException ignored) {
                // Continue trying other locators
            }
        }
        Assert.assertTrue("Expected error message not found: " + expected, found);
    }

    @And("I should see all available job results")
    public void iShouldSeeAllAvailableJobResults() {
        WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-results-heading")));
        Matcher matcher = Pattern.compile("(\\d+)\\s+jobs found").matcher(heading.getText());
        Assert.assertTrue("No job count found", matcher.find());
        int count = Integer.parseInt(matcher.group(1));
        Assert.assertTrue("Expected jobs but found none", count > 0);
    }

    @Then("I should see no suggestions")
    public void iShouldSeeNoSuggestions() {
        List<WebElement> emptySuggestions = driver.findElements(By.cssSelector("#location__listbox li"));
        Assert.assertEquals("Expected no suggestions, but some were found", 0, emptySuggestions.size());

    }
}

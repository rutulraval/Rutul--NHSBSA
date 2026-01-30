package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static utils.DriverFactory.*;

//public class SearchPage extends BaseSetup {
public class SearchPage{
    WebDriver driver;
    private static WebDriverWait wait;
    public SearchPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    WebElement title ;
    WebElement location ;
    WebElement searchBtn ;
    WebElement clearFilterBtn ;
    WebElement moreSearchOptionsBtn;
    WebElement jobReference;
    WebElement employer ;
    //WebElement payRange ;

    // Get values from properties file
    String userJobTitle = getProperties().getProperty("userJobTitle");
    String userPreferredLocation = getProperties().getProperty("userPreferredLocation");
    String userPreferredJobRef = getProperties().getProperty("userJobRef");
    String userPreferredEmployer = getProperties().getProperty("userPreferredEmployer");
    String invalidJobRef = getProperties().getProperty("invalidJobRef");
    String invalidJobTitle = getProperties().getProperty("invalidJobTitle");
    String invalidJobLocation = getProperties().getProperty("invalidJobLocation");
    String invalidEmployer = getProperties().getProperty("invalidEmployer");
    String nonExistingJobTitle = getProperties().getProperty("nonExistingJobTitle");
    //export user preferred values for advanced search for assertion
    public List<String> getUserPreferences(){
        List<String> userPreferences = List.of(userJobTitle, userPreferredLocation,userPreferredJobRef,userPreferredEmployer);
        return userPreferences;
    }

    //BASIC SEARCH:type Job-title and preferred Location from properties file and click Search button
    public void enterBasicUserChoices(){

        title = driver.findElement(By.id(getProperties().getProperty("titleElementId")));
        location = driver.findElement(By.id(getProperties().getProperty("locationElementId")));
        searchBtn = driver.findElement(By.id(getProperties().getProperty("searchBtnId")));

        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].scrollIntoView();",title);
        title.sendKeys(userJobTitle);
        location.sendKeys(userPreferredLocation);
    }

    //clear all filters - when you clear all filters you should verify all the fields have blank
    // or default values
    public void clearFilters(){
        clearFilterBtn = driver.findElement(By.id(getProperties().getProperty("clearFilterBtnId")));
        clearFilterBtn.click();
    }

    public void typeTitleAndLocation(){
        List<WebElement> titleAndLocation = getBasicElements();
        for (WebElement element:titleAndLocation)
            element.sendKeys("-");
    }
    public void typeLocation(String myLocation){
        location = driver.findElement(By.id(getProperties().getProperty("locationElementId")));
        location.sendKeys(myLocation);
    }
    public List<WebElement> getBasicElements(){
        title = driver.findElement(By.id(getProperties().getProperty("titleElementId")));
        location = driver.findElement(By.id(getProperties().getProperty("locationElementId")));
        return List.of(title, location);}

    public List<WebElement> getAdvancedElements(){
        jobReference = driver.findElement(By.id(getProperties().getProperty("jobRefElementId")));
        employer = driver.findElement(By.id(getProperties().getProperty("employerElementId")));
        //payRange = driver.findElement(By.id(getProperties().getProperty("payRange")));
        return List.of(jobReference, employer);}

    //get current values or default values in the fields
    public List<String> getCurrentValues(){
        //method call to get basic fields as List<WebElements>
        List<WebElement> basicElements = getBasicElements();
        //method call to get advanced fields List<WebElements>-only if element with id=additionalSearchPanel is visible or
        // element with id=searchOptionsBtn containing "Fewer search options" text is visible
        List<WebElement> advancedElements= Collections.emptyList();
        if(driver.findElement(By.id("searchOptionsBtn")).isDisplayed()
                && driver.findElement(By.id("searchOptionsBtn"))
                .getText().contains("Fewer search options")){
            advancedElements = getAdvancedElements();
        }
        //store current values of basic fields in a hash-map with element as key and value as current value
        List<String> currentValues = new ArrayList<>();
        for (WebElement element : basicElements) {
            currentValues.add(element.getAttribute("value"));
        }

        for (WebElement element : advancedElements) {
            currentValues.add(element.getAttribute("value"));
        }
        return currentValues;
    }

    //MORE OPTIONS SEARCH: type job-title or/and location or/and job ref or/and employer name
    // or/and pay-range
    public void enterCustomUserChoices(){
        moreSearchOptionsBtn = driver.findElement(By.id(getProperties().getProperty("moreSearchOptionsBtnId")));
        jobReference = driver.findElement(By.id(getProperties().getProperty("jobRefElementId")));
        employer = driver.findElement(By.id(getProperties().getProperty("employerElementId")));
        //payRange = driver.findElement(By.id(getProperties().getProperty("payRange")));

        title = driver.findElement(By.id(getProperties().getProperty("titleElementId")));
        location = driver.findElement(By.id(getProperties().getProperty("locationElementId")));
        searchBtn = driver.findElement(By.id(getProperties().getProperty("searchBtnId")));
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].scrollIntoView();",moreSearchOptionsBtn);
        clickOnMoreSearchBtn();
        if(userJobTitle!=null)
            title.sendKeys(userJobTitle);
        if(userPreferredLocation!=null)
            location.sendKeys(userPreferredLocation);
        if(userPreferredJobRef!=null)
            jobReference.sendKeys(userPreferredJobRef);
        if(userPreferredEmployer!=null)
            employer.sendKeys(userPreferredEmployer);
    }

    public void enterInvalidJobDetails(){
        moreSearchOptionsBtn = driver.findElement(By.id(getProperties().getProperty("moreSearchOptionsBtnId")));
        jobReference = driver.findElement(By.id(getProperties().getProperty("jobRefElementId")));
        employer = driver.findElement(By.id(getProperties().getProperty("employerElementId")));
        title = driver.findElement(By.id(getProperties().getProperty("titleElementId")));
        location = driver.findElement(By.id(getProperties().getProperty("locationElementId")));
        searchBtn = driver.findElement(By.id(getProperties().getProperty("searchBtnId")));
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].scrollIntoView();",moreSearchOptionsBtn);
        clickOnMoreSearchBtn();
        if(invalidJobTitle!=null)
            title.sendKeys(invalidJobTitle);
        if(invalidJobLocation!=null)
            location.sendKeys(invalidJobLocation);
        if(invalidJobRef!=null)
            jobReference.sendKeys(invalidJobRef);
        if(invalidEmployer!=null)
            employer.sendKeys(invalidEmployer);
    }

    public void enterNonExistingTitle(){
        title = driver.findElement(By.id(getProperties().getProperty("titleElementId")));
        searchBtn = driver.findElement(By.id(getProperties().getProperty("searchBtnId")));
        if(nonExistingJobTitle!=null)
            title.sendKeys(nonExistingJobTitle);

    }

    public void clickOnSearch() {
        driver.findElement(By.id(getProperties().getProperty("searchBtnId"))).click();
    }

    public static void clickOnMoreSearchBtn(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(getProperties().getProperty("moreSearchOptionsBtnId")))).click();
    }
}

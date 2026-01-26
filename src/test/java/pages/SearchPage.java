package pages;

import base.BaseSetup;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static utils.DriverFactory.*;

public class SearchPage extends BaseSetup {
    WebDriver driver;
    public SearchPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    WebElement title ;
    WebElement location ;
    WebElement searchBtn ;
    WebElement clearFilterBtn ;
    WebElement moreSearchOptionsBtn;
    WebElement jobReference;
    WebElement employer ;
    WebElement payRange ;

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
        searchBtn.click();
    }

    //clear all filters
    public void clearFilters(){
        clearFilterBtn = driver.findElement(By.id(getProperties().getProperty("clearFilterBtnId")));
        clearFilterBtn.click();
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
        moreSearchOptionsBtn.click();
        if(userJobTitle!=null)
            title.sendKeys(userJobTitle);
        if(userPreferredLocation!=null)
            location.sendKeys(userPreferredLocation);
        if(userPreferredJobRef!=null)
            jobReference.sendKeys(userPreferredJobRef);
        if(userPreferredEmployer!=null)
            employer.sendKeys(userPreferredEmployer);
        searchBtn.click();
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
        moreSearchOptionsBtn.click();
        if(invalidJobTitle!=null)
            title.sendKeys(invalidJobTitle);
        if(invalidJobLocation!=null)
            location.sendKeys(invalidJobLocation);
        if(invalidJobRef!=null)
            jobReference.sendKeys(invalidJobRef);
        if(invalidEmployer!=null)
            employer.sendKeys(invalidEmployer);
        searchBtn.click();
    }

    public void enterNonExistingTitle(){
        title = driver.findElement(By.id(getProperties().getProperty("titleElementId")));
        searchBtn = driver.findElement(By.id(getProperties().getProperty("searchBtnId")));
        if(nonExistingJobTitle!=null)
            title.sendKeys(nonExistingJobTitle);
        searchBtn.click();
    }
}

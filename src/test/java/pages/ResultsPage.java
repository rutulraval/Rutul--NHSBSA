package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static utils.DriverFactory.getProperties;

public class ResultsPage {
    WebDriver driver;
    public ResultsPage(WebDriver driver) {
        this.driver = driver;
//        PageFactory.initElements(driver, this);
    }

    WebElement declineCookies ;
    WebElement sortBtn ;
    String searchResultHeading;

    public void denyCookies(){
        declineCookies = driver.findElement(By.id(getProperties().getProperty("rejectCookiesBtnId")));
        declineCookies.click();
    }

    public List<String> getSearchResultHeading(){
        WebElement searchHeading = driver.findElement(By.id(getProperties().getProperty("searchResultsHeadingElementId")));
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].scrollIntoView();",searchHeading);
        searchResultHeading = searchHeading.getAttribute("aria-label");
        if(searchResultHeading.isEmpty()){
            WebElement searchResultQuery = driver.findElement(By.xpath("//h2[@data-test=\"search-result-query\"]"));
            String searchResultQueryMsg = searchResultQuery.getText();
            return List.of(searchResultQueryMsg.split(" "));
        }
        return List.of(searchResultHeading.split(" "));
    }

    public void clickOnSortByPostedDate(){
        if(!searchResultHeading.contains("No result found")) {
            sortBtn = driver.findElement(By.id(getProperties().getProperty("sortBtnId")));
            sortBtn.click();
            Select sortItems = new Select(sortBtn);
            sortItems.selectByVisibleText("Date Posted (newest)");
        }
    }

    public boolean ensureSorting(){
        List<WebElement> listOfPublicationDates = driver.findElements(By.xpath(getProperties().getProperty("publicationDatesXpath")));
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH);
        LocalDate firstDate = null;

        for(WebElement e:listOfPublicationDates){
            String cleanedText = e.getText().trim().replaceAll("\\s+", " ");
            LocalDate currentDate = LocalDate.parse(cleanedText, formatter);

            if (firstDate == null) {
                firstDate = currentDate;
                //System.out.println("First date: " + firstDate);
                continue;
            }

            if (currentDate.isBefore(firstDate)) {
                //System.out.println("Found earlier date: " + currentDate);
                return true;
            }
        }
        return false;
    }

}

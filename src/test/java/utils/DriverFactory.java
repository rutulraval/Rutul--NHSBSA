package utils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DriverFactory {
    private static WebDriver driver;
    private static FileInputStream fis;
    private static String link;
    private static Properties properties;

    public static Properties getProperties() {
        return properties;
    }

    public DriverFactory() throws IOException{

        properties = new Properties();
        fis = new FileInputStream("config.properties");
        properties.load(fis);
        link = properties.getProperty("url");
        if (properties.getProperty("browser").equalsIgnoreCase("chrome") || driver == null) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().window().maximize();
        } else if (properties.getProperty("browser").equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
            driver.manage().window().maximize();
        }
    }

    public static String getLink() {
        return link;
    }

    public WebDriver getDriver() {

//        if (properties.getProperty("browser").equalsIgnoreCase("chrome") || driver == null) {
//            WebDriverManager.chromedriver().setup();
//            driver = new ChromeDriver();
//            driver.manage().window().maximize();
//        } else if (properties.getProperty("browser").equalsIgnoreCase("firefox")) {
//            WebDriverManager.firefoxdriver().setup();
//            driver = new FirefoxDriver();
//            driver.manage().window().maximize();
//        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
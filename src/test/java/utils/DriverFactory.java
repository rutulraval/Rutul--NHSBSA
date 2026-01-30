package utils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class DriverFactory {
    private static WebDriver driver;
    private static FileInputStream fis;
    private static String link;
    private static Properties properties;
    static{
        properties = new Properties();
        try {
            fis = new FileInputStream("config.properties");
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public static Properties getProperties() {
        return properties;
    }

    public DriverFactory() throws IOException{
        String browser = System.getProperty(
                "browser",
                properties.getProperty("browser")
        );

        link = properties.getProperty("url");
//        if (browser.equalsIgnoreCase("chrome")||properties.getProperty("browser").equalsIgnoreCase("chrome")) {
//            WebDriverManager.chromedriver().setup();
//            driver = new ChromeDriver();
//            driver.manage().window().maximize();
//        } else if (browser.equalsIgnoreCase("firefox")||properties.getProperty("browser").equalsIgnoreCase("firefox")) {
//            WebDriverManager.firefoxdriver().setup();
//            driver = new FirefoxDriver();
//            driver.manage().window().maximize();
//        }
        switch (browser.toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;

            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
        }

        driver.manage().window().maximize();
    }

    public static String getLink() {
        return link;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
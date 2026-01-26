package base;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.DriverFactory;

import java.io.IOException;

import static java.sql.DriverManager.getDriver;
import static utils.DriverFactory.*;

public class BaseSetup {
    @BeforeMethod
    public void openLink() throws IOException {
        DriverFactory driverFactory = new DriverFactory();
        driverFactory.getDriver().get(getLink());
    }

    @AfterMethod
    public void tearDown(){
        quitDriver();
    }
}

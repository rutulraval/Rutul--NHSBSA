package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.testng.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/features"},
        glue ="steps",
        plugin = {"pretty",
        "html:target/cucumber-report.html"
        }
)
public class JobsRunner {
}

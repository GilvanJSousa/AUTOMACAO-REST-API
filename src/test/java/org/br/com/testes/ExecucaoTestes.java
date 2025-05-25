package org.br.com.testes;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "org.br.com.testes.steps",
        tags = "@Usuario",
        monochrome = false,
        dryRun = false,
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json"
        },
        publish = true,
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        stepNotifications = false
)
public class ExecucaoTestes {

}

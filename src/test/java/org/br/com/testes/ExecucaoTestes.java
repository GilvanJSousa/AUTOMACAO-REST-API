package org.br.com.testes;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"org.br.com.testes.steps", "org.br.com.testes.utils"},
        tags = "@login",
        monochrome = true,
        dryRun = false,
        plugin = {
                "org.br.com.testes.utils.LogSummaryPlugin",
                "org.br.com.testes.utils.AllureAutoReportPlugin",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        publish = false,
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        stepNotifications = false
)

public class ExecucaoTestes {
}

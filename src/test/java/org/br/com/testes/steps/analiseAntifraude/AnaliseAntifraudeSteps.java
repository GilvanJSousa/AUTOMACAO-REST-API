package org.br.com.testes.steps.analiseAntifraude;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.br.com.testes.controllers.analiseAntifraude.FraudAnalysisController;
import org.br.com.testes.utils.LogFormatter;

public class AnaliseAntifraudeSteps {
    private final FraudAnalysisController fraudAnalysisController;

    public AnaliseAntifraudeSteps() {
        this.fraudAnalysisController = new FraudAnalysisController();
    }

    @Given("que estou na pagina de pagamento")
    public void queEstouNaPaginaDePagamento() {
        LogFormatter.logStep("Acessando a pagina de pagamento");
    }

    @When("preparo a requisicao de antifraude")
    public void preparoARequisicaoDeAntifraude() throws Exception {
        LogFormatter.logStep("Preparando requisicao de antifraude");
        fraudAnalysisController.prepararRequisicaoAntifraude();
    }

    @And("valido que o status code e {int}")
    public void validoQueOStatusCodeE(int statusCode) {
        LogFormatter.logStep("Validando status code: " + statusCode);
        fraudAnalysisController.validarStatusCode(statusCode);
    }
} 
package org.br.com.testes.steps.tokenizacao;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.br.com.testes.controllers.tokenizacao.TokenizacaoController;
import org.br.com.testes.utils.LogFormatter;
import org.junit.jupiter.api.Assertions;

public class TokenizacaoSteps {
    private final TokenizacaoController tokenizacaoController;

    public TokenizacaoSteps() {
        this.tokenizacaoController = new TokenizacaoController();
    }

    @Given("que tenho os dados do cartao de credito")
    public void queTenhoOsDadosDoCartaoDeCredito() throws Exception {
        LogFormatter.logStep("Preparando dados do cartao de credito");
        tokenizacaoController.prepararRequisicaoTokenizacao();
    }

    @When("envio a requisicao de tokenizacao")
    public void envioARequisicaoDeTokenizacao() {
        LogFormatter.logStep("Enviando requisicao de tokenizacao");
        tokenizacaoController.enviarRequisicaoTokenizacao();
    }

    @Then("valido que o cartao foi tokenizado com sucesso")
    public void validoQueOCartaoFoiTokenizadoComSucesso() {
        LogFormatter.logStep("Validando tokenizacao com sucesso");
        tokenizacaoController.validarTokenizacaoSucesso();
        Assertions.assertNotNull(tokenizacaoController.getResponse().jsonPath().getString("CardToken"), 
            "CardToken nao deve ser nulo");
    }

    @And("valido o status code {int} para {string}")
    public void validoOStatusCodeParaAPI(int statusCode, String api) {
        LogFormatter.logStep("Validando status code " + statusCode + " para " + api);
        tokenizacaoController.validarStatusCode(statusCode);
    }
}

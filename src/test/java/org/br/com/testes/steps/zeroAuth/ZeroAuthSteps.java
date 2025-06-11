package org.br.com.testes.steps.zeroAuth;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.br.com.testes.controllers.zeroAuth.ZeroAuthController;

public class ZeroAuthSteps {
    private final ZeroAuthController zeroAuthController;

    public ZeroAuthSteps() {
        this.zeroAuthController = new ZeroAuthController();
    }

    @Given("que estou na pagina de validacao de cartao")
    public void queEstouNaPaginaDeValidacaoDeCartao() {
    }

    @When("realizo a validacao do cartao de credito")
    public void realizoAValidacaoDoCartaoDeCredito() {
        zeroAuthController.realizarValidacaoCartao();
    }

    @When("realizo a validacao do cartao de debito")
    public void realizoAValidacaoDoCartaoDebito() {
        zeroAuthController.realizarValidacaoCartaoDebito();
    }

    @Then("valido que o cartao foi validado com sucesso")
    public void validoQueOCartaoFoiValidadoComSucesso() {
        zeroAuthController.validarCartaoAutorizado();
    }

    @And("o status code do {string} ser {int}")
    public void oStatusCodeDoZeroAuthSer(int statusCode) {
        zeroAuthController.validarStatusCode(statusCode);
    }
} 
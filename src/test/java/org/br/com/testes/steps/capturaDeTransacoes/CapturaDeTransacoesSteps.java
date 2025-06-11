package org.br.com.testes.steps.capturaDeTransacoes;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;

import org.br.com.testes.controllers.capturaDeTransacoes.CapturaDeTransacoesController;
import org.br.com.testes.utils.LogFormatter;

public class CapturaDeTransacoesSteps {
    private final CapturaDeTransacoesController capturaDeTransacoesController;

    public CapturaDeTransacoesSteps() {
        this.capturaDeTransacoesController = new CapturaDeTransacoesController();
    }

    @Given("que estou na pagina de captura")
    public void queEstouNaPaginaDeCaptura() {
        LogFormatter.logStep("Acessando pagina de captura");
    }

    @When("realizo a captura da transacao por PaymentId")
    public void realizoACapturaDaTransacaoPorPaymentId() {
        LogFormatter.logStep("Realizando captura por PaymentId");
        capturaDeTransacoesController.capturarTransacaoPorPaymentId();
    }

    @When("realizo a captura parcial da transacao por PaymentId")
    public void realizoACapturaParcialDaTransacaoPorPaymentId() {
        LogFormatter.logStep("Realizando captura parcial por PaymentId");
        capturaDeTransacoesController.capturarTransacaoParcialPorPaymentId();
    }

    @Then("valido que a captura foi realizada com sucesso")
    public void validoQueACapturaFoiRealizadaComSucesso() {
        LogFormatter.logStep("Validando captura realizada com sucesso");
        capturaDeTransacoesController.validarCaptura();
    }

    @Then("valido que a captura parcial foi realizada com sucesso")
    public void validoQueACapturaParcialFoiRealizadaComSucesso() {
        LogFormatter.logStep("Validando captura parcial realizada com sucesso");
        capturaDeTransacoesController.validarCapturaParcial();
    }

    @And("valido a API Capturar com o status code {int}")
    public void validoAApiCapturarComOStatusCode(int statusCode) {
        LogFormatter.logStep("Validando status code: " + statusCode);
        capturaDeTransacoesController.validarStatusCode(statusCode);
    }
}

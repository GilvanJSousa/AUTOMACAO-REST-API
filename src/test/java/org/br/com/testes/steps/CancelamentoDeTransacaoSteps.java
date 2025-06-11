package org.br.com.testes.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;


import org.br.com.testes.controllers.cancelamentoDeTransacoes.CancelamentoDeTransacoesController;
import org.br.com.testes.utils.LogFormatter;

public class CancelamentoDeTransacaoSteps {
    private final CancelamentoDeTransacoesController cancelamentoDeTransacaoSteps;

    public CancelamentoDeTransacaoSteps() {
        this.cancelamentoDeTransacaoSteps = new CancelamentoDeTransacoesController();
    }

    @Given("que estou na pagina de cancelamento")
    public void queEstouNaPaginaDeCancelamento() {
        LogFormatter.logStep("Acessando pagina de cancelamento");
    }

    @When("realizo o cancelamento da transacao por PaymentId")
    public void realizoOCancelamentoDaTransacaoPorPaymentId() {
        LogFormatter.logStep("Realizando cancelamento por PaymentId");
        cancelamentoDeTransacaoSteps.cancelarTransacaoPorPaymentId();
    }

    @When("realizo o cancelamento da transacao por MerchantOrderId")
    public void realizoOCancelamentoDaTransacaoPorMerchantOrderId() {
        LogFormatter.logStep("Realizando cancelamento por MerchantOrderId");
        cancelamentoDeTransacaoSteps.cancelarTransacaoPorMerchantOrderId();
    }

    @When("realizo o cancelamento parcial da transacao por PaymentId")
    public void realizoOCancelamentoParcialDaTransacaoPorPaymentId() {
        LogFormatter.logStep("Realizando cancelamento parcial por PaymentId");
        cancelamentoDeTransacaoSteps.cancelarTransacaoParcialPorPaymentId();
    }

    @Then("valido que o cancelamento foi realizado com sucesso")
    public void validoQueOCancelamentoFoiRealizadoComSucesso() {
        LogFormatter.logStep("Validando cancelamento realizado com sucesso");
        cancelamentoDeTransacaoSteps.validarCancelamento();
    }

    @Then("valido que o cancelamento parcial foi realizado com sucesso")
    public void validoQueOCancelamentoParcialFoiRealizadoComSucesso() {
        LogFormatter.logStep("Validando cancelamento parcial realizado com sucesso");
        cancelamentoDeTransacaoSteps.validarCancelamentoParcial();
    }

    @And("valido a API Cancelar com o status code {int}")
    public void validoAApiCancelarComOStatusCode(int statusCode) {
        LogFormatter.logStep("Validando status code: " + statusCode);
        cancelamentoDeTransacaoSteps.validarStatusCode(statusCode);
    }
} 
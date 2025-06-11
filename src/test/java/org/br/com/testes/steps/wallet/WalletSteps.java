package org.br.com.testes.steps.wallet;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.br.com.testes.controllers.wallet.WalletController;
import org.br.com.testes.utils.LogFormatter;

public class WalletSteps {
    private final WalletController walletController;

    public WalletSteps() {
        this.walletController = new WalletController();
    }

    @Given("que estou na pagina de pagamento da carteira")
    public void queEstouNaPaginaDePagamentoDaCarteira() {
        LogFormatter.logStep("Iniciando teste de pagamento com VisaCheckout");
    }

    @When("realizo o pagamento com VisaCheckout")
    public void realizoOPagamentoComVisaCheckout() {
        LogFormatter.logStep("Realizando pagamento com VisaCheckout");
        walletController.realizarPagamentoVisaCheckout();
    }

    @Then("valido que o pagamento foi autorizado com sucesso")
    public void validoQueOPagamentoFoiAutorizadoComSucesso() {
        LogFormatter.logStep("Validando autorizacao do pagamento");
        walletController.validarPagamentoAutorizado();
        walletController.validarStatusCode(201);
    }
}
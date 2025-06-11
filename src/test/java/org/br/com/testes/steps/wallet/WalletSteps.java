package org.br.com.testes.steps.wallet;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.br.com.testes.controllers.wallet.WalletController;
import org.br.com.testes.utils.LogFormatter;
import org.junit.jupiter.api.Assertions;

import java.util.Map;

public class WalletSteps {
    private final WalletController walletController;
    private Response response;

    public WalletSteps() {
        this.walletController = new WalletController();
    }

    @Given("que estou na pagina de pagamento da carteira")
    public void queEstouNaPaginaDePagamentoDaCarteira() {
        LogFormatter.logStep("Iniciando teste de pagamento com VisaCheckout");
    }

    @When("realizo o pagamento com VisaCheckout")
    public void realizoOPagamentoComVisaCheckout(Map<String, String> dados) {
        LogFormatter.logStep("Realizando pagamento com VisaCheckout");
        response = walletController.realizarPagamentoVisaCheckout(dados);
    }

    @Then("valido que o pagamento foi autorizado com sucesso")
    public void validoQueOPagamentoFoiAutorizadoComSucesso() {
        LogFormatter.logStep("Validando autorizacao do pagamento");
        
        Assertions.assertEquals(201, response.getStatusCode(), "Status code deve ser 201");
        
        // Validações do Payment
        Assertions.assertEquals(2, response.jsonPath().getInt("Payment.Status"), "Status do pagamento deve ser 2 (Authorized)");
        Assertions.assertNotNull(response.jsonPath().getString("Payment.PaymentId"), "PaymentId nao deve ser nulo");
        Assertions.assertNotNull(response.jsonPath().getString("Payment.Tid"), "Tid nao deve ser nulo");
        Assertions.assertNotNull(response.jsonPath().getString("Payment.ProofOfSale"), "ProofOfSale nao deve ser nulo");
        Assertions.assertNotNull(response.jsonPath().getString("Payment.AuthorizationCode"), "AuthorizationCode nao deve ser nulo");
        
        // Validações do Wallet
        Assertions.assertEquals("VisaCheckout", response.jsonPath().getString("Payment.Wallet.Type"), "Tipo da wallet deve ser VisaCheckout");
        Assertions.assertEquals("7", response.jsonPath().getString("Payment.Wallet.Eci"), "ECI deve ser 7");
    }
}

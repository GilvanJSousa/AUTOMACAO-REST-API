package org.br.com.testes.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.br.com.testes.controllers.usuarios.MpCartaoDeDebitoController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MpCartaoDeDebitoSteps {
    private MpCartaoDeDebitoController controller;
    private static final Logger logger = LoggerFactory.getLogger(MpCartaoDeDebitoSteps.class);

    public MpCartaoDeDebitoSteps() {
        controller = new MpCartaoDeDebitoController();
    }

    @Given("que eu tenho um cartão de débito válido")
    public void queEuTenhoUmCartaoDeDebitoValido() {
        logger.info("Preparing standard Debit Card request...");
        // Example: logger.debug("Debit Card request details: {}", controller.getDebitCardRequestDetailsAsString()); // Assuming method exists
        controller.prepararRequisicaoCartaoDebito();
        logger.info("Standard Debit Card request prepared.");
    }

    @Given("que eu tenho um cartão de débito válido para autenticação")
    public void queEuTenhoUmCartaoDeDebitoValidoParaAutenticacao() {
        logger.info("Preparing authenticated Debit Card request...");
        // Example: logger.debug("Authenticated Debit Card request details: {}", controller.getDebitCardRequestDetailsAsString());
        controller.prepararRequisicaoCartaoDebitoAutenticado();
        logger.info("Authenticated Debit Card request prepared.");
    }

    @Given("que eu tenho um cartão de débito válido com dados completos")
    public void queEuTenhoUmCartaoDeDebitoValidoComDadosCompletos() {
        logger.info("Preparing complete Debit Card request...");
        // Example: logger.debug("Complete Debit Card request details: {}", controller.getDebitCardRequestDetailsAsString());
        controller.prepararRequisicaoCartaoDebitoCompleto();
        logger.info("Complete Debit Card request prepared.");
    }

    @When("eu envio a requisição de pagamento com débito")
    public void euEnvioARequisicaoDePagamentoComDebito() {
        logger.info("Attempting to send Debit Card payment request (standard)...");
        // Actual request sending is in controller. Controller should log:
        // logger.info("Sending POST request to: {}", url);
        // logger.debug("Request body: {}", requestBodyString);
        controller.enviarRequisicaoPagamento();
        logger.info("Debit Card payment request (standard) sent.");
        // Controller should log:
        // logger.info("Received response status code: {}", response.getStatusCode());
        // logger.debug("Response body: {}", response.getBody().asString());
    }

    @When("eu envio a requisição de pagamento com débito autenticado")
    public void euEnvioARequisicaoDePagamentoComDebitoAutenticado() {
        logger.info("Attempting to send Debit Card payment request (authenticated)...");
        // Actual request sending is in controller. Controller should log details.
        controller.enviarRequisicaoPagamento();
        logger.info("Debit Card payment request (authenticated) sent.");
        // Controller should log response details.
    }

    @When("eu envio a requisição de pagamento com débito completo")
    public void euEnvioARequisicaoDePagamentoComDebitoCompleto() {
        logger.info("Attempting to send Debit Card payment request (complete)...");
        // Actual request sending is in controller. Controller should log details.
        controller.enviarRequisicaoPagamento();
        logger.info("Debit Card payment request (complete) sent.");
        // Controller should log response details.
    }

    @Then("o pagamento com débito deve ser processado com sucesso")
    public void oPagamentoComDebitoDeveSerProcessadoComSucesso() {
        logger.info("Asserting that Debit Card payment was processed successfully...");
        controller.validarPagamentoProcessadoComSucesso();
        logger.info("Assertion for successful Debit Card payment complete.");
    }

    @And("o status code do pagamento com débito deve ser {int}")
    public void oStatusCodeDoPagamentoComDebitoDeveSer(int statusCode) {
        logger.info("Asserting that Debit Card payment response status code is {}", statusCode);
        controller.validarStatusCode(statusCode);
        logger.info("Assertion for Debit Card payment status code complete.");
    }
}
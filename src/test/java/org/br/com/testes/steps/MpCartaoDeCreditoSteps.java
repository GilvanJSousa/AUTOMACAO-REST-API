package org.br.com.testes.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.br.com.testes.controllers.mpCartao.MpCartaoDeCreditoController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MpCartaoDeCreditoSteps {
    private MpCartaoDeCreditoController controller;
    private static final Logger logger = LoggerFactory.getLogger(MpCartaoDeCreditoSteps.class);

    public MpCartaoDeCreditoSteps() {
        controller = new MpCartaoDeCreditoController();
    }

    @Given("que eu tenho um cartão de crédito válido")
    public void queEuTenhoUmCartaoDeCreditoValido() {
        logger.info("Preparing standard Credit Card request...");
        // Example: logger.debug("Credit Card request details: {}", controller.getCreditCardRequestDetailsAsString()); // Assuming method exists
        controller.prepararRequisicaoCartaoCredito();
        logger.info("Standard Credit Card request prepared.");
    }

    @Given("que eu tenho um cartão de crédito válido para autenticação")
    public void queEuTenhoUmCartaoDeCreditoValidoParaAutenticacao() {
        logger.info("Preparing authenticated Credit Card request...");
        // Example: logger.debug("Authenticated Credit Card request details: {}", controller.getCreditCardRequestDetailsAsString());
        controller.prepararRequisicaoCartaoCreditoAutenticado();
        logger.info("Authenticated Credit Card request prepared.");
    }

    @Given("que eu tenho um cartão de crédito válido com dados completos")
    public void queEuTenhoUmCartaoDeCreditoValidoComDadosCompletos() {
        logger.info("Preparing complete Credit Card request...");
        // Example: logger.debug("Complete Credit Card request details: {}", controller.getCreditCardRequestDetailsAsString());
        controller.prepararRequisicaoCartaoCreditoCompleto();
        logger.info("Complete Credit Card request prepared.");
    }

    @When("eu envio a requisição de pagamento")
    public void euEnvioARequisicaoDePagamento() {
        logger.info("Attempting to send Credit Card payment request (standard)...");
        // Actual request sending is in controller. Controller should log:
        // logger.info("Sending POST request to: {}", url);
        // logger.debug("Request body: {}", requestBodyString);
        controller.enviarRequisicaoPagamento();
        logger.info("Credit Card payment request (standard) sent.");
        // Controller should log:
        // logger.info("Received response status code: {}", response.getStatusCode());
        // logger.debug("Response body: {}", response.getBody().asString());
    }

    @When("eu envio a requisição de pagamento autenticado")
    public void euEnvioARequisicaoDePagamentoAutenticado() {
        logger.info("Attempting to send Credit Card payment request (authenticated)...");
        // Actual request sending is in controller. Controller should log details.
        controller.enviarRequisicaoPagamento();
        logger.info("Credit Card payment request (authenticated) sent.");
        // Controller should log response details.
    }

    @When("eu envio a requisição de pagamento completo")
    public void euEnvioARequisicaoDePagamentoCompleto() {
        logger.info("Attempting to send Credit Card payment request (complete)...");
        // Actual request sending is in controller. Controller should log details.
        controller.enviarRequisicaoPagamento();
        logger.info("Credit Card payment request (complete) sent.");
        // Controller should log response details.
    }

    @Then("o pagamento deve ser processado com sucesso")
    public void oPagamentoDeveSerProcessadoComSucesso() {
        logger.info("Asserting that Credit Card payment was processed successfully...");
        controller.validarPagamentoProcessadoComSucesso();
        logger.info("Assertion for successful Credit Card payment complete.");
    }

    @And("o status code deve ser {int}")
    public void oStatusCodeDeveSer(int statusCode) {
        logger.info("Asserting that Credit Card payment response status code is {}", statusCode);
        controller.validarStatusCode(statusCode);
        logger.info("Assertion for Credit Card payment status code complete.");
    }
}
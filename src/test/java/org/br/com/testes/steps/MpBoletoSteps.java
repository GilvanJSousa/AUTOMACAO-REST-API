package org.br.com.testes.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.br.com.testes.controllers.usuarios.MpBoletoController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MpBoletoSteps {
    private MpBoletoController controller;
    private static final Logger logger = LoggerFactory.getLogger(MpBoletoSteps.class);

    public MpBoletoSteps() {
        controller = new MpBoletoController();
    }

    @Given("que eu tenho um boleto válido")
    public void queEuTenhoUmBoletoValido() {
        logger.info("Preparing Boleto request...");
        // Example: logger.debug("Boleto request details: {}", controller.getBoletoRequestDetailsAsString()); // Assuming method exists in controller
        controller.prepararRequisicaoBoleto();
        logger.info("Boleto request prepared.");
    }

    @When("eu envio a requisição de pagamento com boleto")
    public void euEnvioARequisicaoDePagamentoComBoleto() {
        logger.info("Attempting to send Boleto payment request...");
        // Actual request sending is in controller. Controller should log:
        // logger.info("Sending POST request to: {}", url);
        // logger.debug("Request body: {}", requestBodyString);
        controller.enviarRequisicaoPagamento();
        logger.info("Boleto payment request sent.");
        // Controller should log:
        // logger.info("Received response status code: {}", response.getStatusCode());
        // logger.debug("Response body: {}", response.getBody().asString());
    }

    @Then("o pagamento com boleto deve ser processado com sucesso")
    public void oPagamentoComBoletoDeveSerProcessadoComSucesso() {
        logger.info("Asserting that Boleto payment was processed successfully...");
        controller.validarPagamentoProcessadoComSucesso();
        logger.info("Assertion for successful Boleto payment complete.");
    }

    @And("o status code do pagamento com boleto deve ser {int}")
    public void oStatusCodeDoPagamentoComBoletoDeveSer(int statusCode) {
        logger.info("Asserting that Boleto payment response status code is {}", statusCode);
        controller.validarStatusCode(statusCode);
        logger.info("Assertion for Boleto payment status code complete.");
    }
}
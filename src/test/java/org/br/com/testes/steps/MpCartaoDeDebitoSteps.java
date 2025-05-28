package org.br.com.testes.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.br.com.testes.controllers.mpCartao.MpCartaoDeDebitoController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MpCartaoDeDebitoSteps {
    private final MpCartaoDeDebitoController controller;
    private static final Logger logger = LoggerFactory.getLogger(MpCartaoDeDebitoSteps.class);

    public MpCartaoDeDebitoSteps() {
        controller = new MpCartaoDeDebitoController();
    }

    @Given("que eu tenho um cartão de débito válido")
    public void queEuTenhoUmCartaoDeDebitoValido() {
        controller.prepararRequisicaoCartaoDebito();

    }

    @Given("que eu tenho um cartão de débito válido para autenticação")
    public void queEuTenhoUmCartaoDeDebitoValidoParaAutenticacao() {
        controller.prepararRequisicaoCartaoDebitoAutenticado();
    }

    @Given("que eu tenho um cartão de débito válido com dados completos")
    public void queEuTenhoUmCartaoDeDebitoValidoComDadosCompletos() {
        controller.prepararRequisicaoCartaoDebitoCompleto();

    }

    @When("eu envio a requisição de pagamento com débito")
    public void euEnvioARequisicaoDePagamentoComDebito() {
        controller.enviarRequisicaoPagamento();
    }

    @When("eu envio a requisição de pagamento com débito autenticado")
    public void euEnvioARequisicaoDePagamentoComDebitoAutenticado() {
        controller.enviarRequisicaoPagamento();
    }

    @When("eu envio a requisição de pagamento com débito completo")
    public void euEnvioARequisicaoDePagamentoComDebitoCompleto() {
        controller.enviarRequisicaoPagamento();
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
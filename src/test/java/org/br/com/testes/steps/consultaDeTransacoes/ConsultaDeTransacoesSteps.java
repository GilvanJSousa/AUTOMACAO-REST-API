package org.br.com.testes.steps.consultaDeTransacoes;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.br.com.testes.controllers.consultaCapturaCancelamento.ConsultaTransacaoController;
import org.br.com.testes.manager.CartaoDeCreditoManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsultaDeTransacoesSteps {
    private final ConsultaTransacaoController consultaTransacaoController;
    private static final Logger logger = LoggerFactory.getLogger(ConsultaDeTransacoesSteps.class);

    public ConsultaDeTransacoesSteps() {
        consultaTransacaoController = new ConsultaTransacaoController();
    }

    @Given("que eu tenho um PaymentId válido")
    public void queEuTenhoUmPaymentIdValido() {
        logger.info("Verificando PaymentId válido: {}", CartaoDeCreditoManager.getPaymentId());
        // O PaymentId será obtido da transação anterior (CT-1001, CT-1002 ou CT-1003)
        // Não é necessário setar manualmente, pois o CartaoDeCreditoManager já terá o valor
    }

    @When("eu envio a requisição de consulta")
    public void euEnvioARequisicaoDeConsulta() {
        logger.info("Enviando requisição de consulta para o PaymentId: {}", CartaoDeCreditoManager.getPaymentId());
        consultaTransacaoController.consultarTransacao();
    }

    @Then("a transação deve ser consultada com sucesso")
    public void aTransacaoDeveSerConsultadaComSucesso() {
        logger.info("Validando consulta da transação");
        consultaTransacaoController.validarConsultaTransacao();
    }

    @And("o status code da consulta deve ser {int}")
    public void oStatusCodeDeveSer(int statusCode) {
        logger.info("Validando status code: {}", statusCode);
        consultaTransacaoController.validarStatusCode(statusCode);
    }

    @Given("que eu tenho um PaymentId válido e um valor parcial")
    public void queEuTenhoUmPaymentIdVálidoEUmValorParcial() {
        logger.info("Verificando PaymentId válido para consulta parcial: {}", CartaoDeCreditoManager.getPaymentId());
        // O PaymentId será obtido da transação anterior
        // O valor parcial será obtido do CartaoDeCreditoManager
        logger.info("Valor parcial para consulta: {}", CartaoDeCreditoManager.getAmount());
    }

    @When("eu envio a requisição de consulta parcial")
    public void euEnvioARequisiçãoDeConsultaParcial() {
        logger.info("Enviando requisição de consulta parcial para o PaymentId: {}", CartaoDeCreditoManager.getPaymentId());
        consultaTransacaoController.consultarTransacao();
    }

    @Then("a transação parcial deve ser consultada com sucesso")
    public void aTransacaoParcialDeveSerConsultadaComSucesso() {
        logger.info("Validando consulta da transação parcial");
        consultaTransacaoController.validarConsultaParcial();
    }
} 
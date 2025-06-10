package org.br.com.testes.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.br.com.testes.controllers.consultaCapturaCancelamento.ConsultaTransacaoController;
import org.br.com.testes.manager.UsuarioManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MpConsultaTransacaoSteps {
    private final ConsultaTransacaoController consultaTransacaoController;
    private static final Logger logger = LoggerFactory.getLogger(MpConsultaTransacaoSteps.class);

    public MpConsultaTransacaoSteps() {
        consultaTransacaoController = new ConsultaTransacaoController();
    }

    @Given("que eu tenho um PaymentId válido")
    public void queEuTenhoUmPaymentIdValido() {
        logger.info("Verificando PaymentId válido: {}", UsuarioManager.getPaymentId());
        // O PaymentId será obtido da transação anterior (CT-1001, CT-1002 ou CT-1003)
        // Não é necessário setar manualmente, pois o UsuarioManager já terá o valor
    }

    @When("eu envio a requisição de consulta")
    public void euEnvioARequisicaoDeConsulta() {
        logger.info("Enviando requisição de consulta para o PaymentId: {}", UsuarioManager.getPaymentId());
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
        logger.info("Verificando PaymentId válido para consulta parcial: {}", UsuarioManager.getPaymentId());
        // O PaymentId será obtido da transação anterior
        // O valor parcial será obtido do UsuarioManager
        logger.info("Valor parcial para consulta: {}", UsuarioManager.getAmount());
    }

    @When("eu envio a requisição de consulta parcial")
    public void euEnvioARequisiçãoDeConsultaParcial() {
        logger.info("Enviando requisição de consulta parcial para o PaymentId: {}", UsuarioManager.getPaymentId());
        consultaTransacaoController.consultarTransacao();
    }

    @Then("a transação parcial deve ser consultada com sucesso")
    public void aTransacaoParcialDeveSerConsultadaComSucesso() {
        logger.info("Validando consulta da transação parcial");
        consultaTransacaoController.validarConsultaParcial();
    }
} 
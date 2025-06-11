package org.br.com.testes.steps.recorrencia;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.br.com.testes.controllers.recorrencia.RecorrenciaController;
import org.br.com.testes.utils.LogFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RecorrenciaSteps {
    private final RecorrenciaController recorrenciaController;
    private static final Logger logger = LoggerFactory.getLogger(RecorrenciaSteps.class);

    public RecorrenciaSteps() {
        recorrenciaController = new RecorrenciaController();
    }

    @Given("que eu tenho um cartão de crédito válido para recorrência")
    public void queEuTenhoUmCartaoDeCreditoValidoParaRecorrencia() throws Exception {
        LogFormatter.logStep("Preparando cartão de crédito válido para recorrência");
        recorrenciaController.prepararRequisicaoRecorrencia();
    }

    @When("eu envio a requisição de pagamento recorrente")
    public void euEnvioARequisicaoDePagamentoRecorrente() {
        LogFormatter.logStep("Enviando requisição de pagamento recorrente");
        recorrenciaController.enviarRequisicaoRecorrencia();
    }

    @Then("o pagamento recorrente deve ser processado com sucesso")
    public void oPagamentoRecorrenteDeveSerProcessadoComSucesso() {
        LogFormatter.logStep("Validando processamento do pagamento recorrente");
        recorrenciaController.validarRecorrencia();
    }

    @Given("que eu tenho um ID de pagamento recorrente válido")
    public void queEuTenhoUmIDDePagamentoRecorrenteValido() throws Exception {
        LogFormatter.logStep("Preparando ID de pagamento recorrente válido");
        recorrenciaController.prepararRequisicaoRecorrencia();
        recorrenciaController.enviarRequisicaoRecorrencia();
    }

    @When("eu envio a requisição de consulta do pagamento recorrente")
    public void euEnvioARequisicaoDeConsultaDoPagamentoRecorrente() {
        LogFormatter.logStep("Enviando requisição de consulta do pagamento recorrente");
        recorrenciaController.consultarRecorrencia();
    }

    @Then("a consulta do pagamento recorrente deve ser processada com sucesso")
    public void aConsultaDoPagamentoRecorrenteDeveSerProcessadaComSucesso() {
        LogFormatter.logStep("Validando processamento da consulta do pagamento recorrente");
        recorrenciaController.validarStatusCode(200);
    }

    @When("eu envio a requisição para alterar os dados do comprador")
    public void euEnvioARequisicaoParaAlterarOsDadosDoComprador() throws Exception {
        LogFormatter.logStep("Enviando requisição para alterar dados do comprador");
        recorrenciaController.prepararRequisicaoAlterarDadosComprador();
        recorrenciaController.alterarDadosComprador();
    }

    @Then("a alteração dos dados do comprador deve ser processada com sucesso")
    public void aAlteracaoDosDadosDoCompradorDeveSerProcessadaComSucesso() {
        LogFormatter.logStep("Validando processamento da alteração dos dados do comprador");
        recorrenciaController.validarStatusCode(200);
    }

    @When("eu envio a requisição para alterar o intervalo do pagamento recorrente")
    public void euEnvioARequisicaoParaAlterarOIntervaloDoPagamentoRecorrente() {
        LogFormatter.logStep("Enviando requisição para alterar intervalo do pagamento recorrente");
        recorrenciaController.alterarIntervalo();
    }

    @Then("a alteração do intervalo deve ser processada com sucesso")
    public void aAlteracaoDoIntervaloDeveSerProcessadaComSucesso() {
        LogFormatter.logStep("Validando processamento da alteração do intervalo");
        recorrenciaController.validarStatusCode(200);
    }
}

package org.br.com.testes.steps.recorrencia;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
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
        recorrenciaController.prepararRequisicaoRecorrencia();
    }

    @When("eu envio a requisição de pagamento recorrente")
    public void euEnvioARequisicaoDePagamentoRecorrente() {
        recorrenciaController.enviarRequisicaoRecorrencia();
    }

    @Then("o pagamento recorrente deve ser processado com sucesso")
    public void oPagamentoRecorrenteDeveSerProcessadoComSucesso() {
        recorrenciaController.validarRecorrencia();
    }

    @And("valido o status code {int} para {string}")
    public void validoOStatusCodePara(int statusCode, String api) {
        recorrenciaController.validarStatusCode(statusCode);
    }
}

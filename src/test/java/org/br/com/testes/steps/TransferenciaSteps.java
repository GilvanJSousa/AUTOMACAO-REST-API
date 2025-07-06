package org.br.com.testes.steps;

import io.cucumber.java.en.*;
import org.br.com.testes.controllers.transferencia.TransferenciaController;

public class TransferenciaSteps {

    private TransferenciaController transferenciaController;

    public TransferenciaSteps() {
        transferenciaController = new TransferenciaController();
    }

    @Given("que envioa solicitação de POST para realizar uma transferencia entre contas")
    public void queEnvioaSolicitaçãoDePOSTParaRealizarUmaTransferenciaEntreContas() {
        transferenciaController.realizarTransferencia();
    }
    @Then("a API Transferencia deve retornar o status code {int}")
    public void aAPITransferenciaDeveRetornarOStatusCode(Integer statusCode) {
        transferenciaController.validarStatusCode(statusCode);
    }
}

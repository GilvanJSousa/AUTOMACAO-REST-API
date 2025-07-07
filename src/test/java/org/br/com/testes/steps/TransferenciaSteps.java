package org.br.com.testes.steps;

import io.cucumber.java.en.*;
import org.br.com.testes.controllers.transferencia.TransferenciaController;

public class TransferenciaSteps {

    private final TransferenciaController transferenciaController;

    public TransferenciaSteps() {
        transferenciaController = new TransferenciaController();
    }

    @Given("que envio a solicitação de POST para realizar uma transferencia entre contas")
    public void queEnvioaSolicitaçãoDePOSTParaRealizarUmaTransferenciaEntreContas() {
        transferenciaController.realizarTransferencia();
    }
    @Then("a API Transferencia deve retornar o status code {int}")
    public void aAPITransferenciaDeveRetornarOStatusCode(Integer statusCode) {
        transferenciaController.validarStatusCode(statusCode);
    }

    @Given("que envio a solicitação de GET para consultar transferencia especifica")
    public void queEnvioASolicitaçãoDeGETParaConsultarTransferenciaEspecifica() {
        transferenciaController.consultarTransferenciaBancaria();
    }

    @Given("que envio a solicitação de GET para Listar as transferências realizadas")
    public void queEnvioASolicitaçãoDeGETParaListarAsTransferênciasRealizadas() {
        transferenciaController.listarTransferenciasBancarias();
    }

    @Given("que envio uma requisição PUT atualiza completamente uma transferência")
    public void queEnvioUmaRequisiçãoPUTPUTAtualizaCompletamenteUmaTransferência() {
        transferenciaController.atualizarCompletamenteTransferencia();
    }

}

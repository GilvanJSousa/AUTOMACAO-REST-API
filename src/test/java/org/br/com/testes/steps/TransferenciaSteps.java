package org.br.com.testes.steps;

import io.cucumber.java.en.*;
import org.br.com.testes.controllers.transferencia.TransferenciaController;

public class TransferenciaSteps {

    private final TransferenciaController transferenciaController;

    public TransferenciaSteps() {
        transferenciaController = new TransferenciaController();
    }

    @Given("que envio a requisicao de POST para realizar uma transferencia entre contas")
    public void queEnvioaRequisicaoDePOSTParaRealizarUmaTransferenciaEntreContas() {
        transferenciaController.realizarTransferencia();
    }

    @Then("a API Transferencia deve retornar o status code {int}")
    public void aAPITransferenciaDeveRetornarOStatusCode(Integer statusCode) {
        transferenciaController.validarStatusCode(statusCode);
    }

    @Given("que envio a requisicao de GET para consultar transferencia especifica")
    public void queEnvioaRequisicaoDeGETParaConsultarTransferenciaEspecifica() {
        transferenciaController.consultarTransferenciaBancaria();
    }

    @Given("que envio a requisicao de GET para Listar as transferencias realizadas")
    public void queEnvioaRequisicaoDeGETParaListarAsTransferenciasRealizadas() {
        transferenciaController.listarTransferenciasBancarias();
    }

    @Given("que envio uma requisicao PUT atualiza completamente uma transferencia")
    public void queEnvioUmaRequisicaoPUTPUTAtualizaCompletamenteUmaTransferencia() {
        transferenciaController.atualizarCompletamenteTransferencia();
    }

    @Given("que envio uma requisicao PATCH atualiza parcialmente uma transferencia")
    public void queEnvioUmaRequisicaoPUTAtualizaParcialmenteUmaTransferencia() {
        transferenciaController.atualizarParcialmenteTransferencia();
    }

    @Given("que envio uma requisicao DELETE Remove uma transferencia")
    public void queEnvioUmaRequisicaoDELETERemoveUmaTransferencia() {
        transferenciaController.removeUmaTransferencia();
    }
}

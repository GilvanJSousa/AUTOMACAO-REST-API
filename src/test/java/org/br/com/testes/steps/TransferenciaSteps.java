package org.br.com.testes.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.java.en.*;
import org.br.com.testes.controllers.transferencia.TransferenciaController;

public class TransferenciaSteps {

    private final TransferenciaController transferenciaController;

    public TransferenciaSteps() {
        transferenciaController = new TransferenciaController();
    }

    @Given("que envio a requisicao de POST para realizar uma transferencia no valor de R$ {double} entre contas")
    public void queEnvioaRequisicaoDePOSTParaRealizarUmaTransferenciaEntreContas(double valor) throws JsonProcessingException {
        transferenciaController.prepararRequisicaoDeTransferencia(valor);
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

    @Given("que envio uma requisicao PUT atualizar completamente o valor R$ {double} da transferencia")
    public void queEnvioUmaRequisicaoPUTPUTAtualizaCompletamenteUmaTransferencia(double valor) {
        transferenciaController.atualizarCompletamenteTransferencia();
    }

    @Given("que envio uma requisicao PATCH atualiza parcialmente em valor R$ {double} da transferencia")
    public void queEnvioUmaRequisicaoPUTAtualizaParcialmenteUmaTransferencia(double valor) {
        transferenciaController.atualizarParcialmenteTransferencia();
    }

    @Given("que envio uma requisicao DELETE Remove uma transferencia")
    public void queEnvioUmaRequisicaoDELETERemoveUmaTransferencia() {
        transferenciaController.removeUmaTransferencia();
    }


    @Given("que envio a requisicao de POST para realizar uma transferencia de R$ {double} entre contas")
    public void queEnvioARequisicaoDePOSTParaRealizarUmaTransferenciaDeR$EntreContas(double valor) {
        transferenciaController.realizarTransferenciaComValorDivergente(valor);
    }

    @When("para realizar uma transferencia no valor de R$ {double} entre contas")
    public void umaTransferenciaDeR$ERealizada(double valor) {

    }
}

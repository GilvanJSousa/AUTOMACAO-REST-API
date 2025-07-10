package org.br.com.testes.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.java.en.*;
import org.br.com.testes.controllers.transferencia.TransferenciaController;
import org.br.com.testes.utils.LogFormatter;

public class TransferenciaSteps {

    private final TransferenciaController transferenciaController;

    public TransferenciaSteps() {
        transferenciaController = new TransferenciaController();
    }

    @Given("que envio a requisicao de POST para realizar uma transferencia no valor de R$ {double} entre contas")
    public void queEnvioaRequisicaoDePOSTParaRealizarUmaTransferenciaEntreContas(double valor) throws JsonProcessingException {
        transferenciaController.realizarTransferencia(valor);
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
    public void queEnvioUmaRequisicaoPUTPUTAtualizaCompletamenteUmaTransferencia(double valor) throws JsonProcessingException {
        transferenciaController.atualizarCompletamenteTransferencia(valor);
    }

    @Given("que envio uma requisicao PATCH atualiza parcialmente em valor R$ {double} da transferencia")
    public void queEnvioUmaRequisicaoPUTAtualizaParcialmenteUmaTransferencia(double valor) throws JsonProcessingException {
        transferenciaController.atualizarParcialmenteTransferencia(valor);
    }

    @Given("que envio uma requisicao DELETE Remove uma transferencia")
    public void queEnvioUmaRequisicaoDELETERemoveUmaTransferencia() {
        transferenciaController.removeUmaTransferencia();
    }


    @Given("que envio a requisicao de POST para realizar uma transferencia de R$ {double} entre contas")
    public void queEnvioARequisicaoDePOSTParaRealizarUmaTransferenciaDeR$EntreContas(double valor) throws JsonProcessingException {
        transferenciaController.realizarTransferenciaComValorDivergente(valor);
    }

    @Given("que a conta de origem possui saldo de R$ {double}")
    public void queAContaDeOrigemPossuiSaldoDeR$(double saldo) {
        transferenciaController.verificarSaldoContaOrigem(saldo);
    }

    @Given("a conta de destino esta ativa")
    public void aContaDeDestinoEstaAtiva() {
        transferenciaController.verificarContaDestinoAtiva();
    }

    @Given("o token de autenticacao {string} e fornecido")
    public void oTokenDeAutenticacaoEFornecido(String token) {
        transferenciaController.definirTokenAutenticacao(token);
    }

    @When("uma transferencia de R$ {double} e realizada")
    public void umaTransferenciaDeRERealizada(double valor) throws JsonProcessingException {
        transferenciaController.realizarTransferenciaComValidacao(valor);
    }

    @Then("a transferencia e processada com sucesso")
    public void aTransferenciaEProcessadaComSucesso() {
        // A validação já é feita no método realizarTransferenciaComValidacao
        // Este step apenas confirma que chegou até aqui sem exceções
        LogFormatter.logStep("Validacao de sucesso da transferencia concluida");
    }

    @When("uma transferencia de R$ {double} e realizada com validacao de erro")
    public void umaTransferenciaDeRERealizadaComValidacaoDeErro(double valor) throws JsonProcessingException {
        // Para valores abaixo de R$10,00, esperamos erro 422
        if (valor < 10) {
            transferenciaController.realizarTransferenciaComValidacaoDeErro(valor, 422, "R$10,00");
        } else {
            transferenciaController.realizarTransferenciaComValidacao(valor);
        }
    }

    @Then("o sistema retorna um erro indicando que o valor minimo e de R$ 10.00")
    public void oSistemaRetornaUmErroIndicandoQueOValorMinimoEDeR$10_00() {
        // A validação já é feita no método realizarTransferenciaComValidacaoDeErro
        // Este step apenas confirma que chegou até aqui sem exceções
        LogFormatter.logStep("Validacao de erro de valor minimo concluida");
    }


}

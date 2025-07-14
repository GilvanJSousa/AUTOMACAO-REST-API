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

    @Given("que envio a requisicao de {string} para realizar uma transferencia no valor de R$ {double} entre contas")
    public void queEnvioaRequisicaoDePOSTParaRealizarUmaTransferenciaEntreContas(String arg, double valor) throws JsonProcessingException {
        transferenciaController.realizarTransferencia(valor);
    }

    @Then("a API Transferencia deve retornar o status code {int}")
    public void aAPITransferenciaDeveRetornarOStatusCode(Integer statusCode) {
        transferenciaController.validarStatusCode(statusCode);
    }

    @Given("que envio a requisicao de {string} para consultar transferencia especifica")
    public void queEnvioaRequisicaoDeGETParaConsultarTransferenciaEspecifica(String arg) {
        transferenciaController.consultarTransferenciaBancaria();
    }

    @Given("que envio a requisicao de {string} para Listar as transferencias realizadas")
    public void queEnvioaRequisicaoDeGETParaListarAsTransferenciasRealizadas(String arg) {
        transferenciaController.listarTransferenciasBancarias();
    }

    @Given("que envio uma requisicao {string} atualizar completamente o valor R$ {double} da transferencia")
    public void queEnvioUmaRequisicaoPUTPUTAtualizaCompletamenteUmaTransferencia(String arg, double valor) throws JsonProcessingException {
        transferenciaController.atualizarCompletamenteTransferencia(valor);
    }

    @Given("que envio uma requisicao {string} atualiza parcialmente em valor R$ {double} da transferencia")
    public void queEnvioUmaRequisicaoPUTAtualizaParcialmenteUmaTransferencia(String arg, double valor) throws JsonProcessingException {
        transferenciaController.atualizarParcialmenteTransferencia(valor);
    }

    @Given("que envio uma requisicao {string} Remove uma transferencia")
    public void queEnvioUmaRequisicaoDELETERemoveUmaTransferencia(String arg) {
        transferenciaController.removeUmaTransferencia();
    }


    @Given("que envio a requisicao de {string} para realizar uma transferencia de R$ {double} entre contas")
    public void queEnvioARequisicaoDePOSTParaRealizarUmaTransferenciaDeR$EntreContas(String argc, double valor) throws JsonProcessingException {
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

    // --- CT-1015: Validar conta inativa ---
    private String contaOrigemCT1015;
    private String contaDestinoCT1015;

    @When("uma transferencia de R$ {double} e realizada")
    public void umaTransferenciaDeRERealizada(double valor) throws JsonProcessingException {
        // Se temos contas específicas do cenário CT-1015, usar elas
        if (contaOrigemCT1015 != null && contaDestinoCT1015 != null) {
            transferenciaController.realizarTransferenciaComValidacaoEntreContas(valor, contaOrigemCT1015, contaDestinoCT1015);
        } else {
            transferenciaController.realizarTransferenciaComValidacao(valor);
        }
    }

    @Then("a transferencia e processada com sucesso")
    public void aTransferenciaEProcessadaComSucesso() {
        // A validação já é feita no método realizarTransferenciaComValidacao
        // Este step apenas confirma que chegou até aqui sem exceções
//        LogFormatter.logStep("Validacao de sucesso da transferencia concluida");
    }

    @When("uma transferencia de R$ {double} e realizada com validacao de erro")
    public void umaTransferenciaDeRERealizadaComValidacaoDeErro(double valor) throws JsonProcessingException {
        transferenciaController.realizarTransferenciaComValidacaoInteligente(valor);
    }

    @Then("o sistema retorna um erro indicando que o valor minimo e de R$ {double}")
    public void oSistemaRetornaUmErroIndicandoQueOValorMinimoEDeR$10_00(double valor) {
        // A validação já é feita no método realizarTransferenciaComValidacaoDeErro
        // Este step apenas confirma que chegou até aqui sem exceções
//        LogFormatter.logStep("Validacao de erro de valor minimo concluida");
    }

    // --- CT-1013: Remover uma transferência e reverter saldos ---
    private String contaOrigemCT1013;
    private String contaDestinoCT1013;
    private double valorTransferenciaCT1013;

    @Given("que a transferencia de R$ {double} foi realizada entre a conta de origem {string} e a conta de destino {string}")
    public void queATransferenciaDeR$FoiRealizadaEntreAContaDeOrigemEAContaDeDestino(double valor, String contaOrigem, String contaDestino) throws JsonProcessingException {
        this.contaOrigemCT1013 = contaOrigem;
        this.contaDestinoCT1013 = contaDestino;
        this.valorTransferenciaCT1013 = valor;
        transferenciaController.realizarTransferenciaEntreContas(valor, contaOrigem, contaDestino);
        transferenciaController.armazenarSaldosAntesRemocao(contaOrigem, contaDestino);
    }

    @When("a transferencia e removida")
    public void aTransferenciaERemovida() {
        transferenciaController.removeUmaTransferencia();
    }

    @Then("o saldo da conta de origem e da conta de destino e revertido")
    public void oSaldoDaContaDeOrigemEDaContaDeDestinoERevertido() {
        transferenciaController.validarReversaoDeSaldo(contaOrigemCT1013, contaDestinoCT1013);
    }

    // --- CT-1014: Tentar remover uma transferência inexistente ---
    private String idTransferenciaInexistente;

    @Given("que nao existe transferancia com o ID {string}")
    public void queNaoExisteTransferanciaComOID(String id) {
        this.idTransferenciaInexistente = id;
    }

    @When("a tentativa de remoção da transferencia e realizada")
    public void aTentativaDeRemoçãoDaTransferenciaERealizada() {
        transferenciaController.tentarRemoverTransferenciaInexistente(idTransferenciaInexistente);
    }

    @Then("o sistema retorna um erro indicando que a transferencia nao foi encontrada")
    public void oSistemaRetornaUmErroIndicandoQueATransferenciaNaoFoiEncontrada() {
        transferenciaController.validarErroTransferenciaNaoEncontrada(404, "Transferencia nao encontrada.");
    }

    @When("a transferencia e atualizada com novos dados valor R$ {double} conta de destino {string}")
    public void aTransferenciaEAtualizadaComNovosDadosValorR$ContaDeDestino(double valor, String contaDestino) throws JsonProcessingException {
        transferenciaController.atualizarTransferenciaComNovosDados(valor, contaDestino);
    }

    @Then("todos os dados da transferencia sao atualizados com sucesso")
    public void todosOsDadosDaTransferenciaSaoAtualizadosComSucesso() {
        // A validação já é feita no método atualizarTransferenciaComNovosDados
        // Este step apenas confirma que chegou até aqui sem exceções
//        LogFormatter.logStep("Validacao de atualizacao da transferencia concluida");
    }

    @When("o valor da transferencia e modificado para R$ {double}")
    public void oValorDaTransferenciaEModificadoParaR$(Double valor) throws JsonProcessingException {
        transferenciaController.atualizarCompletamenteTransferencia(valor);
    }

    @Then("a transferencia e modificada com sucesso")
    public void aTransferenciaEModificadaComSucesso() {

    }

    @Given("que a conta de origem {string} possui saldo de R$ {double}")
    public void queAContaDeOrigemPossuiSaldoDeR$(String contaOrigem, Double saldo) {
        // Armazenar conta de origem para CT-1015
        this.contaOrigemCT1015 = contaOrigem;
        transferenciaController.verificarSaldoContaOrigem(contaOrigem, saldo);
    }

    @And("a conta de destino {string} esta inativa")
    public void aContaDeDestinoEstaInativa(String contaDestino) {
        // Armazenar conta de destino para CT-1015
        this.contaDestinoCT1015 = contaDestino;
        transferenciaController.verificarContaDestinoInativa(contaDestino);
    }

    @Then("o sistema retorna um erro indicando que a Conta de origem ou destino esta inativa.")
    public void oSistemaRetornaUmErroIndicandoQueAContaDeOrigemOuDestinoEstaInativa() {
        transferenciaController.validarErroContaInativa();
    }
}

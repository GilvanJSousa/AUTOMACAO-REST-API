package org.br.com.testes.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.br.com.testes.controllers.contas.ContasController;

public class ContasSteps {

    private final ContasController contasController;

    public ContasSteps() {
        this.contasController = new ContasController();
    }

    @Given("que envio a requisição de listar contas bancarias")
    public void queEnvioARequisicaoDeListarContasBancarias() {
        contasController.listarContasBancarias();
    }

    @Then("a API listar contas bancarias deve retornar status code {int}")
    public void aAPIListarContasBancariasDeveRetornarStatusCode(Integer statusCode) {
        contasController.validasStatusCode(statusCode);
    }

    @Given("que envio a requisicao de GET Obter detalhes de uma conta {string} bancaria")
    public void queEnvioARequisicaoDeGETObterDetalhesDeUmaContaBancaria(String conta) {
        contasController.obterContaBancaria(conta);
    }
}

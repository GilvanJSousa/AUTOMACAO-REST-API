package org.br.com.testes.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.br.com.testes.controllers.ContasController;

public class ContasSteps {

    private ContasController contasController;

    public ContasSteps() {
        this.contasController = new ContasController();
    }

    @Given("que envio a requisição de listar contas bancarias")
    public void queEnvioARequisiçãoDeListarContasBancarias() {
        contasController.listarContasBancarias();
    }

    @Then("a API listar contas bancarias deve retornar status code {int}")
    public void aAPIListarContasBancariasDeveRetornarStatusCode(Integer statusCode) {
        contasController.validasStatusCode(statusCode);
    }
}

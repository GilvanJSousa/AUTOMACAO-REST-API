package org.br.com.testes.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.br.com.testes.controllers.usuarios.MpCartaoDeCreditoController;

public class MpCartaoDeCreditoSteps {
    private MpCartaoDeCreditoController controller;

    public MpCartaoDeCreditoSteps() {
        controller = new MpCartaoDeCreditoController();
    }

    @Given("que eu tenho um cartão de crédito válido")
    public void queEuTenhoUmCartaoDeCreditoValido() {
        controller.prepararRequisicaoCartaoCredito();
    }

    @Given("que eu tenho um cartão de crédito válido para autenticação")
    public void queEuTenhoUmCartaoDeCreditoValidoParaAutenticacao() {
        controller.prepararRequisicaoCartaoCreditoAutenticado();
    }

    @Given("que eu tenho um cartão de crédito válido com dados completos")
    public void queEuTenhoUmCartaoDeCreditoValidoComDadosCompletos() {
        controller.prepararRequisicaoCartaoCreditoCompleto();
    }

    @When("eu envio a requisição de pagamento")
    public void euEnvioARequisicaoDePagamento() {
        controller.enviarRequisicaoPagamento();
    }

    @When("eu envio a requisição de pagamento autenticado")
    public void euEnvioARequisicaoDePagamentoAutenticado() {
        controller.enviarRequisicaoPagamento();
    }

    @When("eu envio a requisição de pagamento completo")
    public void euEnvioARequisicaoDePagamentoCompleto() {
        controller.enviarRequisicaoPagamento();
    }

    @Then("o pagamento deve ser processado com sucesso")
    public void oPagamentoDeveSerProcessadoComSucesso() {
        controller.validarPagamentoProcessadoComSucesso();
    }

    @And("o status code deve ser {int}")
    public void oStatusCodeDeveSer(int statusCode) {
        controller.validarStatusCode(statusCode);
    }
} 
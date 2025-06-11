package org.br.com.testes.steps.mpCartoes;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.br.com.testes.controllers.mpCartao.MpCartaoDeDebitoController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MpCartaoDeDebitoSteps {
    private final MpCartaoDeDebitoController mpCartaoDeDebitoController;
    private static final Logger logger = LoggerFactory.getLogger(MpCartaoDeDebitoSteps.class);

    public MpCartaoDeDebitoSteps() {
        mpCartaoDeDebitoController = new MpCartaoDeDebitoController();
    }

    @Given("que eu tenho um cartão de débito válido")
    public void queEuTenhoUmCartaoDeDebitoValido() {
        mpCartaoDeDebitoController.prepararRequisicaoCartaoDebito();

    }

    @Given("que eu tenho um cartão de débito válido para autenticação")
    public void queEuTenhoUmCartaoDeDebitoValidoParaAutenticacao() {
        mpCartaoDeDebitoController.prepararRequisicaoCartaoDebitoAutenticado();
    }

    @Given("que eu tenho um cartão de débito válido com dados completos")
    public void queEuTenhoUmCartaoDeDebitoValidoComDadosCompletos() {
        mpCartaoDeDebitoController.prepararRequisicaoCartaoDebitoCompleto();

    }

    @When("eu envio a requisição de pagamento com débito")
    public void euEnvioARequisicaoDePagamentoComDebito() {
        mpCartaoDeDebitoController.enviarRequisicaoPagamento();
    }

    @When("eu envio a requisição de pagamento com débito autenticado")
    public void euEnvioARequisicaoDePagamentoComDebitoAutenticado() {
        mpCartaoDeDebitoController.enviarRequisicaoPagamento();
    }

    @When("eu envio a requisição de pagamento com débito completo")
    public void euEnvioARequisicaoDePagamentoComDebitoCompleto() {
        mpCartaoDeDebitoController.enviarRequisicaoPagamento();
    }

    @Then("o pagamento com débito deve ser processado com sucesso")
    public void oPagamentoComDebitoDeveSerProcessadoComSucesso() {
        mpCartaoDeDebitoController.validarPagamentoProcessadoComSucesso();
    }

    @And("o status code do pagamento com débito deve ser {int}")
    public void oStatusCodeDoPagamentoComDebitoDeveSer(int statusCode) {
        mpCartaoDeDebitoController.validarStatusCode(statusCode);
    }
}
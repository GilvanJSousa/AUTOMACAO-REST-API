package org.br.com.testes.steps.mpCartoes;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.br.com.testes.controllers.mpCartao.MpCartaoDeCreditoController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MpCartaoDeCreditoSteps {
    private final MpCartaoDeCreditoController mpCartaoDeCreditoController;
    private static final Logger logger = LoggerFactory.getLogger(MpCartaoDeCreditoSteps.class);

    public MpCartaoDeCreditoSteps() {
        mpCartaoDeCreditoController = new MpCartaoDeCreditoController();
    }

    @Given("que eu tenho um cartão de crédito válido")
    public void queEuTenhoUmCartaoDeCreditoValido() throws Exception {
        mpCartaoDeCreditoController.prepararRequisicaoCartaoCredito();
    }

    @Given("que eu tenho um cartão de crédito válido para autenticação")
    public void queEuTenhoUmCartaoDeCreditoValidoParaAutenticacao() throws Exception {
        mpCartaoDeCreditoController.prepararRequisicaoCartaoCreditoAutenticado();
    }

    @Given("que eu tenho um cartão de crédito válido com dados completos")
    public void queEuTenhoUmCartaoDeCreditoValidoComDadosCompletos() throws Exception {
        mpCartaoDeCreditoController.prepararRequisicaoCartaoCreditoCompleto();
    }

    @When("eu envio a requisição de pagamento")
    public void euEnvioARequisicaoDePagamento() {
        mpCartaoDeCreditoController.enviarRequisicaoPagamento();
    }

    @When("eu envio a requisição de pagamento autenticado")
    public void euEnvioARequisicaoDePagamentoAutenticado() {
        mpCartaoDeCreditoController.enviarRequisicaoPagamento();
    }

    @When("eu envio a requisição de pagamento completo")
    public void euEnvioARequisicaoDePagamentoCompleto() {
        mpCartaoDeCreditoController.enviarRequisicaoPagamento();
    }

    @Then("o pagamento deve ser processado com sucesso")
    public void oPagamentoDeveSerProcessadoComSucesso() {
        mpCartaoDeCreditoController.validarPagamentoSimples();
    }

    @Then("o pagamento autenticado deve ser processado com sucesso")
    public void oPagamentoAutenticadoDeveSerProcessadoComSucesso() {
        mpCartaoDeCreditoController.validarPagamentoAutenticado();
    }

    @Then("o pagamento completo deve ser processado com sucesso")
    public void oPagamentoCompletoDeveSerProcessadoComSucesso() {
        mpCartaoDeCreditoController.validarPagamentoCompleto();
    }

    @And("o status code deve ser {int}")
    public void oStatusCodeDeveSer(int statusCode) {
        mpCartaoDeCreditoController.validarStatusCode(statusCode);
    }
}
package org.br.com.testes.steps.tokenizacao;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.br.com.testes.controllers.tokenizacao.TokenizacaoController;
import org.br.com.testes.manager.TokenizacaoManager;
import org.br.com.testes.manager.UsuarioManager;
import org.junit.jupiter.api.Assertions;

public class TokenizacaoSteps {
    private final TokenizacaoController tokenizacaoController;
    private final UsuarioManager usuarioManager;

    public TokenizacaoSteps() {
        this.tokenizacaoController = new TokenizacaoController();
        this.usuarioManager = new UsuarioManager();
    }

    @Given("que tenho os dados do cartao de credito")
    public void queTenhoOsDadosDoCartaoDeCredito() throws Exception {
        tokenizacaoController.prepararRequisicaoTokenizacao();
    }

    @When("envio a requisicao de tokenizacao")
    public void envioARequisicaoDeTokenizacao() {
        tokenizacaoController.enviarRequisicaoTokenizacao();
    }

    @Then("valido que o cartao foi tokenizado com sucesso")
    public void validoQueOCartaoFoiTokenizadoComSucesso() {
        tokenizacaoController.validarTokenizacaoSucesso();
    }

    @And("valido o status code {int} para {string}")
    public void validoOStatusCodePara(int statusCode, String api) {
        tokenizacaoController.validarStatusCode(statusCode);
    }

    @Given("que tenho o token do cartao")
    public void queTenhoOTokenDoCartao() {
        Assertions.assertNotNull(TokenizacaoManager.getCardToken(), "Token do cartão não encontrado");
    }

    @When("envio a requisicao de consulta do cartao tokenizado")
    public void envioARequisicaoDeConsultaDoCartaoTokenizado() {
        tokenizacaoController.consultarCartaoTokenizado();
    }

    @Then("valido que os dados do cartao foram retornados com sucesso")
    public void validoQueOsDadosDoCartaoForamRetornadosComSucesso() {
        tokenizacaoController.validarConsultaCartaoSucesso();
    }

    @Given("que tenho os dados do pagamento")
    public void queTenhoOsDadosDoPagamento() throws Exception {
        tokenizacaoController.prepararRequisicaoPagamento();
    }

    @Given("que tenho o token do cartao American Express")
    public void queTenhoOTokenDoCartaoAmericanExpress() {
        Assertions.assertNotNull(TokenizacaoManager.getCardToken(), "Token do cartão não encontrado");
    }

    @Given("que tenho os dados do pagamento com cartao American Express")
    public void queTenhoOsDadosDoPagamentoComCartaoAmericanExpress() throws Exception {
        tokenizacaoController.prepararRequisicaoPagamentoAmex();
    }

    @Given("que tenho o token do cartao Visa")
    public void queTenhoOTokenDoCartaoVisa() {
        Assertions.assertNotNull(TokenizacaoManager.getCardToken(), "Token do cartão não encontrado");
    }

    @Given("que tenho os dados do pagamento com cartao Visa")
    public void queTenhoOsDadosDoPagamentoComCartaoVisa() throws Exception {
        tokenizacaoController.prepararRequisicaoPagamentoVisa();
    }

    @When("envio a requisicao de pagamento com cartao tokenizado")
    public void envioARequisicaoDePagamentoComCartaoTokenizado() {
        tokenizacaoController.enviarRequisicaoPagamento();
    }

    @Then("valido que o pagamento foi realizado com sucesso")
    public void validoQueOPagamentoFoiRealizadoComSucesso() {
        tokenizacaoController.validarPagamentoSucesso();
    }
}

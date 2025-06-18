package org.br.com.testes.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.br.com.testes.controllers.carrinhos.CarrinhosController;

public class CarrinhosSteps {
    private final CarrinhosController carrinhosController;

    public CarrinhosSteps() {
        this.carrinhosController = new CarrinhosController();
    }

    @Given("que cadastro um carrinho válido para os testes")
    public void queCadastroUmCarrinhoValidoParaOsTestes() {
        carrinhosController.cadastrarNovoCarrinho();
    }

    @Given("que envio a solicitação de GET com id do carrinho")
    public void queEnvioASolicitacaoDeGetComIdDoCarrinho() {
        carrinhosController.buscarCarrinhoPorId();
    }

    @Given("envio a solicitação de GET para listar todos os carrinhos")
    public void envioASolicitacaoDeGetParaListarTodosOsCarrinhos() {
        carrinhosController.listarCarrinhos();
    }

    @Given("que envio a requisição PUT com id do carrinho para editar")
    public void queEnvioARequisicaoPutComIdDoCarrinhoParaEditar() {
        carrinhosController.editarCarrinho();
    }

    @Given("envio uma solicitação de DELETE para excluir o carrinho")
    public void envioUmaSolicitacaoDeDeleteParaExcluirOCarrinho() {
        carrinhosController.concluirCompra();
    }

    @Given("envio uma solicitação de DELETE para cancelar a compra")
    public void envioUmaSolicitacaoDeDeleteParaCancelarACompra() {
        carrinhosController.cancelarCompra();
    }

    @Then("deve retornar carrinho com status code {int} e mensagem {string}")
    public void deveRetornarCarrinhoComStatusCodeEMensagem(int statusCode, String mensagem) {
        carrinhosController.validarStatusCodeEMensagem(statusCode, mensagem);
    }

    @Then("deve retornar carrinho com status code {int}")
    public void deveRetornarCarrinhoComStatusCode(int statusCode) {
        carrinhosController.validarStatusCode(statusCode);
    }
} 
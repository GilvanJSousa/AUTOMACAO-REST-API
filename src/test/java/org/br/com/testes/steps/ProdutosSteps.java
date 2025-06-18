package org.br.com.testes.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.br.com.testes.controllers.produtos.ProdutosController;

public class ProdutosSteps {
    private final ProdutosController produtosController;

    public ProdutosSteps() {
        this.produtosController = new ProdutosController();
    }

    @Given("que cadastro um produto válido para os testes")
    public void queCadastroUmProdutoValidoParaOsTestes() {
        produtosController.cadastrarNovoProduto();
    }

    @Given("que envio a solicitação de GET com id do produto")
    public void queEnvioASolicitacaoDeGetComIdDoProduto() {
        produtosController.buscarProdutoPorId();
    }

    @Given("envio a solicitação de GET para listar todos os produtos")
    public void envioASolicitacaoDeGetParaListarTodosOsProdutos() {
        produtosController.listarProdutos();
    }

    @Given("que envio a requisição PUT com id do produto para editar")
    public void queEnvioARequisicaoPutComIdDoProdutoParaEditar() {
        produtosController.editarProduto();
    }

    @Given("envio uma solicitação de DELETE para excluir o produto")
    public void envioUmaSolicitacaoDeDeleteParaExcluirOProduto() {
        produtosController.excluirProduto();
    }

    @Then("deve retornar produto com status code {int} e mensagem {string}")
    public void deveRetornarProdutoComStatusCodeEMensagem(int statusCode, String mensagem) {
        produtosController.validarStatusCodeEMensagem(statusCode, mensagem);
    }

    @Then("deve retornar produto com status code {int}")
    public void deveRetornarProdutoComStatusCode(int statusCode) {
        produtosController.validarStatusCode(statusCode);
    }
}

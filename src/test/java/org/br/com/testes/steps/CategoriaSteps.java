package org.br.com.testes.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.br.com.testes.controllers.CategoriaController;
import org.br.com.testes.utils.LogFormatter;

public class CategoriaSteps {

	private final CategoriaController categoriaController;

	public CategoriaSteps() {
		this.categoriaController = new CategoriaController();
	}

	@Given("que envio uma requisição de cadastro de categoria")
	public void queEnvioUmaRequisicaoDeCadastroDeCategoria() throws Exception {
		LogFormatter.logStep("Enviando requisição de cadastro de categoria");
		categoriaController.cadastrarNovaCategoria();
	}

	@And("crio uma categoria para os testes")
	public void crioUmaCategoriaParaOsTestes() {
		LogFormatter.logStep("Criando categoria para os testes");
		categoriaController.cadastrarNovaCategoria();
	}

	@Then("a API Categoria deve retornar o código de status {int}")
	public void aAPIDeveRetornarOCodigoDeStatus(int statusCode) {
		LogFormatter.logStep("Validando status code: " + statusCode);
		categoriaController.validarStatusCodeCategoria(statusCode);
	}

	@When("eu envio a requisição de listar Categorias com autenticação")
	public void euEnvioARequisicaoDeListarCategoriasComAutenticacao() {
		categoriaController.listarCategorias();
	}

	@Given("eu envio a requisição de busca de categorias por ID")
	public void euEnvioARequisicaoDeBuscaDeCategoriasPorID() {
		LogFormatter.logStep("Enviando requisição para buscar categoria por ID");
		categoriaController.buscarCategoriaPorId();
	}

	@Given("eu envio a requisição de PUT com ID")
	public void euEnvioARequisicaoDePUTComID() {
		LogFormatter.logStep("Enviando requisição PUT para atualizar categoria");
		categoriaController.atualizarCategoriaPorId();
	}

	@Given("eu envio a requisição de DELETE para o ID")
	public void euEnvioARequisicaoDeDELETEParaOID() {
		LogFormatter.logStep("Enviando requisição DELETE para excluir categoria");
		categoriaController.excluirCategoriaPorId();
	}

	@Then("a API categoria deve retornar o status code {int} para exclusão")
	public void aApiCategoriaDeveRetornarOStatusCodeParaExclusao(int statusCode) {
		LogFormatter.logStep("Validando status code da exclusão: " + statusCode);
		categoriaController.validarStatusCodeCategoria(statusCode);
	}

	@Given("envio uma solicitação de DELETE para o categoria {string}")
	public void envioUmaSolicitacaoDeDELETEParaOCategoria(String arg0) {
		categoriaController.excluirCategoriaEmMassa(arg0);
	}
}

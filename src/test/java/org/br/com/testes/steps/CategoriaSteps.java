package org.br.com.testes.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.br.com.testes.controllers.CategoriaController;

public class CategoriaSteps {

	private final CategoriaController categoriaController;

	public CategoriaSteps() {
		this.categoriaController = new CategoriaController();
	}

	@Given("que envio uma requisição de cadastro de categoria")
	public void queEnvioUmaRequisicaoDeCadastroDeArtigo() throws Exception {
		categoriaController.cadastrarNovaCategoria();
	}

	@Then("a API Categoria deve retornar o código de status {int}")
	public void aAPIDeveRetornarOCodigoDeStatus(int statusCode) {
		categoriaController.validarStatusCodeCategoria(statusCode);
	}

	@When("eu envio a requisição de listar Categorias com autenticação")
	public void euEnvioARequisicaoDeListarCategoriasComAutenticacao() {
		categoriaController.listarCategorias();
	}

	@Given("eu envio a requisição de busca de categorias por ID")
	public void euEnvioARequisicaoDeBuscaDeCategoriasPorID() {
		categoriaController.buscarCategoriaPorId();
	}

	@Given("eu envio a requisição de PUT com ID")
	public void euEnvioARequisicaoDePUTComID() {
		categoriaController.atualizarCategoriaPorId();
	}

	@Given("eu envio a requisição de DELETE para o ID")
	public void euEnvioARequisicaoDeDELETEParaOID() {
		categoriaController.excluirCategoriaPorId();
	}
}

package org.br.com.testes.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.*;
import org.br.com.testes.controllers.CategoriaController;
import org.br.com.testes.utils.LogFormatter;

@Epic("Gestão de Categorias")
@Feature("Cadastro e Manutenção de Categorias")
public class CategoriaSteps {

	private final CategoriaController categoriaController;

	public CategoriaSteps() {
		this.categoriaController = new CategoriaController();
	}

	@Given("que envio uma requisição de cadastro de categoria")
	public void queEnvioUmaRequisicaoDeCadastroDeCategoria() throws Exception {
		categoriaController.cadastrarNovaCategoria();
	}

	@And("crio uma categoria para os testes")
	public void crioUmaCategoriaParaOsTestes() {
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

	@Then("a API categoria deve retornar o status code {int} para exclusão")
	public void aApiCategoriaDeveRetornarOStatusCodeParaExclusao(int statusCode) {
		categoriaController.validarStatusCodeCategoria(statusCode);
	}

	@Given("envio uma solicitação de DELETE para o categoria {string}")
	public void envioUmaSolicitacaoDeDELETEParaOCategoria(String arg0) {
		categoriaController.excluirCategoriaEmMassa(arg0);
	}
}

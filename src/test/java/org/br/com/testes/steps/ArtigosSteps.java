package org.br.com.testes.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.br.com.testes.controllers.ArtigosController;

public class ArtigosSteps {

	private final ArtigosController artigosController;

	public ArtigosSteps() {
		this.artigosController = new ArtigosController();
	}

	@Given("que envio uma requisição de cadastro de Artigos")
	public void queEnvioUmaRequisicaoDeCadastroDeArtigos() throws Exception {
		// Primeiro testa a conectividade básica
		artigosController.testarConectividadeAPI();
		// Depois testa se o endpoint existe
		artigosController.testarEndpointArtigos();
		// Cria a categoria antes do artigo
		artigosController.criarCategoriaAntesDoArtigo();
		// Cria o autor antes do artigo
		artigosController.criarAutorAntesDoArtigo();
		// Por fim tenta cadastrar o artigo
		artigosController.cadastrarArtigo();
	}

	@Then("a API Artigos deve retornar o código de status {int}")
	public void aApiArtigosDeveRetornarOCodigoDeStatus(int statusCode) {
		artigosController.validarStatusCodeArtigos(statusCode);
	}

	@When("eu envio a requisição de listar Artigos com autenticação")
	public void euEnvioARequisicaoDeListarArtigosComAutenticacao() {
		artigosController.listarArtigos();
	}

	@Given("eu envio a requisição de busca de artigos por ID")
	public void euEnvioARequisicaoDeBuscaDeArtigosPorID() {
		artigosController.buscarArtigoPorId();
	}

	@Given("eu envio a requisição PUT Artigos com ID")
	public void euEnvioARequisicaoPUTArtigosComID() {
		artigosController.atualizarArtigoPorId();
	}

	@Given("eu envio a requisição DELETE Artigos com ID")
	public void euEnvioARequisicaoDeDELETEParaOID() {
		artigosController.excluirArtigoPorId();
	}

	@Then("a API Artigos deve retornar o status code {int} para exclusão")
	public void aApiArtigosDeveRetornarOStatusCodeParaExclusao(int statusCode) {
		artigosController.validarStatusCodeArtigos(statusCode);
	}
}

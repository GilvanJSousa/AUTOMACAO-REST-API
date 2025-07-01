package org.br.com.testes.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.br.com.testes.controllers.ArtigosController;
import org.br.com.testes.utils.LogFormatter;

public class ArtigosSteps {

	private final ArtigosController artigosController;

	public ArtigosSteps() {
		this.artigosController = new ArtigosController();
	}

	@Given("que envio uma requisição de cadastro de Artigos")
	public void queEnvioUmaRequisicaoDeCadastroDeArtigos() throws Exception {
		LogFormatter.logStep("Enviando requisição de cadastro de artigo");
		// Cadastrar o artigo (categoria e autor já foram criados no Background)
		artigosController.cadastrarArtigo();
	}

	@And("crio uma categoria para os artigos")
	public void crioUmaCategoriaParaOsArtigos() {
		LogFormatter.logStep("Criando categoria para os artigos");
		artigosController.criarCategoriaAntesDoArtigo();
	}

	@And("preparo o autor para os artigos")
	public void preparoOAutorParaOsArtigos() {
		LogFormatter.logStep("Preparando autor para os artigos");
		artigosController.prepararAutorParaArtigos();
	}

	@And("crio um artigo para os testes")
	public void crioUmArtigoParaOsTestes() {
		LogFormatter.logStep("Criando artigo para os testes");
		artigosController.cadastrarArtigo();
	}

	@Then("a API Artigos deve retornar o código de status {int}")
	public void aApiArtigosDeveRetornarOCodigoDeStatus(int statusCode) {
		LogFormatter.logStep("Validando status code: " + statusCode);
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
		artigosController.validarStatusCodeExclusao(statusCode);
	}

	@Given("envio uma solicitação de DELETE para o Artigos {string}")
	public void envioUmaSolicitacaoDeDELETEParaOArtigos(String arg0) {
		artigosController.excluirArtigosEmMassa(arg0);
	}
}

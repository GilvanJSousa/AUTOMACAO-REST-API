package org.br.com.testes.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.br.com.testes.controllers.UsuarioController;

public class UsuarioSteps {

	private final UsuarioController usuarioController;


	public UsuarioSteps(){
		this.usuarioController = new UsuarioController();
	}

	@When("que envio uma requisição de registro de usuario CMS")
	public void queEnvioUmaRequisicaoDeRegistroUsuarioCMS() {
		usuarioController.cadastrarNovoUsuario();
	}

	@When("o sistema processa a requisição")
	public void oSistemaProcessaARequisicao() {
		// Implementação do processamento
	}

	@Then("a API deve retornar o código de status {int}")
	public void aAPIDeveRetornarOCodigoDeStatus(int statusCode) {
		usuarioController.validarStatusCode(statusCode);
	}

	@When("eu envio a requisição de login com as credenciais do usuário")
	public void euEnvioARequisicaoDeLoginComAsCredenciaisDoUsuario() {
		usuarioController.realizarLogin();
	}

	@Then("o token de autenticação deve ser retornado")
	public void oTokenDeAutenticacaoDeveSerRetornado() {
		usuarioController.validarStatusCode(200);
//        xrayController.uploadReportToXray("reports/reports2.xml");
	}

	@When("eu envio a requisição de listar de usuários com autenticação")
	public void euEnvioARequisicaoDeListarDeUsuariosComAutenticacao() {
		usuarioController.listarUsuariosComAutenticacao();
	}

	@Then("os dados do usuário devem ser retornados na resposta")
	public void osDadosDoUsuarioDevemSerRetornadosNaResposta() {
		usuarioController.validarNomeUsuario();
	}

	@When("eu envio a requisição de busca de usuário por ID")
	public void euEnvioARequisiçãoDeBuscaDeUsuárioPorID() {
		usuarioController.consultarUsuarioPorId();
	}

	@When("que envio a solicitação de PUT com ID")
	public void queEnvioASolicitacaoDePUTComID() {
        usuarioController.atualizarUsuarioPorId();
	}

	@Then("valido o retorno usuario atualizado com status code {int} e mensagem {string}")
	public void validoORetornoUsuarioAtualizadoComStatusCodeENesagem(int statusCode, String mensagem) {
		usuarioController.validarStatusCode(statusCode);
	}

	@When("envio uma solicitação de DELETE para o ID")
	public void envioUmaSolicitacaoDeDELETEParaOID() {
		usuarioController.excluirUsuarioPorId();
	}

	@Then("deve retornar o status code {int} para exclusão")
	public void deveRetornarOStatusCodeParaExclusao(int statusCode) {
		usuarioController.validarStatusCode(statusCode);
	}

}
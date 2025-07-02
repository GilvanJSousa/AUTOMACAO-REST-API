package org.br.com.testes.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.br.com.testes.controllers.UsuarioController;
import org.br.com.testes.utils.LogFormatter;
import io.qameta.allure.*;

@Epic("Gestão de Usuários")
@Feature("Cadastro e Manutenção de Usuários")
public class UsuarioSteps {

	private final UsuarioController usuarioController;


	public UsuarioSteps(){
		this.usuarioController = new UsuarioController();
	}

	@When("que envio uma requisição de registro de usuario CMS")
	public void queEnvioUmaRequisicaoDeRegistroUsuarioCMS() {
		usuarioController.cadastrarNovoUsuario();
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
//        xrayController.uploadReportToXray("reports/reports2.xml");
	}

	@When("eu envio a requisição de listar de usuários com autenticação")
	public void euEnvioARequisicaoDeListarDeUsuariosComAutenticacao() {
		usuarioController.listarUsuariosComAutenticacao();
	}

	@When("eu envio a requisição de busca de usuário por ID")
	public void euEnvioARequisicaoDeBuscaDeUsuarioPorID() {
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

	@Given("envio uma solicitação de DELETE para o {string}")
	public void envioUmaSolicitacaoDeDELETEParaO(String arg0) {
		usuarioController.massasParaExcluir(arg0);
	}
}
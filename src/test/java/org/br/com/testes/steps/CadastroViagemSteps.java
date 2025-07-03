package org.br.com.testes.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.br.com.testes.controllers.CadastroViagemController;

public class CadastroViagemSteps {

	private final CadastroViagemController cadastroViagemController;

	public CadastroViagemSteps() {
		this.cadastroViagemController = new CadastroViagemController();
	}

	@Given("envio uma solicitação de requisição de login")
	public void envioUmaSolicitacaoDeRequisicaoDeLogin() {
		cadastroViagemController.gerarTokenAdmin();
	}

	@Given("que envi uma requisição de cadastro de viagem")
	public void queEnviUmaRequisicaoDeCadastroDeViagem() {
		cadastroViagemController.cadastrarViagem();
	}
	@Then("a API cadastro de viagem deve retornar o código de status {int}")
	public void aAPICadastroDeViagemDeveRetornarOCodigoDeStatus(Integer int1) {
		cadastroViagemController.validarStatusCode(int1);
	}


}

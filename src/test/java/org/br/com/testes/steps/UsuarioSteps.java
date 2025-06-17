package org.br.com.testes.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.br.com.testes.controllers.usuarios.UsuarioController;


public class UsuarioSteps {

    private final UsuarioController usuarioController;


    public UsuarioSteps(){
        this.usuarioController = new UsuarioController();
    }

    @Given("que cadastro um usuario valido para os testes")
    public void queCadastroUmUsuarioValidoParaOsTestes() {
        usuarioController.cadastrarNovoUsuario();
    }

    @Given("envio a solicitação de cadastro na loja")
    public void envioASolicitacaoDeCompraNaLoja() {
        usuarioController.cadastrarNovoUsuario();
    }

    @Given("envio a solicitação de GET com id")
    public void envioASolicitaoDeGETComId() {
        usuarioController.consultarUsuarioPorId();
    }

    @Then("deve retornar usuario com status code {int}")
    public void deveRetornarUsuarioComStatusCode(int arg0) {
        usuarioController.validarStatusCode(200);
    }

}
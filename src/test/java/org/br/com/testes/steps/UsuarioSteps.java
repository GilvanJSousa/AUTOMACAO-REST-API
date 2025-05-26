package org.br.com.testes.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.br.com.testes.controllers.usuarios.UsuarioController;
import org.br.com.testes.utils.FakerApiData;

public class UsuarioSteps {

    private final UsuarioController usuarioController;
    String nome = FakerApiData.getName();
    String email = FakerApiData.getEmail();
    String password = FakerApiData.getPassword();

    public UsuarioSteps(){
        this.usuarioController = new UsuarioController();
    }

    @Given("que cadastro um usuario valido para os testes")
    public void queCadastroUmUsuarioValidoParaOsTestes() {
        usuarioController.cadastrarNovoUsuario(
                nome,
                email,
                password,
                "true"
        );
    }

    @Given("envio a solicitação de cadastro na loja")
    public void envioASolicitacaoDeCompraNaLoja() {
        usuarioController.cadastrarNovoUsuario(
                nome,
                email,
                password,
                "true");
    }

    @Given("envio a solicitação de GET com id")
    public void envioASolicitaçãoDeGETComId() {
        usuarioController.consultarUsuarioPorId();
    }

    @Then("deve retornar usuario com status code {int}")
    public void deveRetornarUsuarioComStatusCode(int arg0) {
        usuarioController.validarStatusCode(200);
    }

}
package org.br.com.testes.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.br.com.testes.controllers.usuarios.usuarioController;

public class UsuariosSteps {

    public usuarioController usuarioController = new usuarioController();

    @Given("que envio uma requisição de registro de usuario CMS")
    public void queEnvioUmaRequisiçãoDeRegistroDeUsuarioCMS() {
        usuarioController.cadastrarNovoUsuario();
    }

    @Then("a API deve retornar o código de status {int}")
    public void aAPIDeveRetornarOCodigoDeStatus(int statusCode) {
        usuarioController.validarStatusCode(statusCode);
    }
}

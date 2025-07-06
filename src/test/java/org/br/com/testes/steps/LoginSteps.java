package org.br.com.testes.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.br.com.testes.controllers.tokens.GerarTokenController;


public class LoginSteps {

    private final GerarTokenController gerarTokenController;

    public LoginSteps() {
        this.gerarTokenController = new GerarTokenController();
    }


    @Given("envio uma solicitação POST de login como Admin")
    public void envioUmaSolicitacaoPOSTDeLoginComoAdmin() {
        GerarTokenController.gerarTokenAdmin();
    }

    @Given("envio uma solicitação POST de login como Usuario")
    public void envioUmaSolicitacaoPOSTDeLoginComoUsuario() {
        gerarTokenController.gerarTokenUsuario();
    }

    @Then("valido o API Login com status code {int}")
    public void validoOAPILoginComStatusCoide(Integer int1) {
        gerarTokenController.validarStatusCode(int1);
    }


}

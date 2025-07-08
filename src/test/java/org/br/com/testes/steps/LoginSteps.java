package org.br.com.testes.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.br.com.testes.tokens.GerarToken;


public class LoginSteps {

    private final GerarToken gerarToken;

    public LoginSteps() {
        this.gerarToken = new GerarToken();
    }


    @Given("envio uma solicitação POST de login como Admin")
    public void envioUmaSolicitacaoPOSTDeLoginComoAdmin() {
        GerarToken.gerarTokenAdmin();
    }

    @Given("envio uma solicitação POST de login como Usuario")
    public void envioUmaSolicitacaoPOSTDeLoginComoUsuario() {
        gerarToken.gerarTokenUsuario();
    }

    @Then("valido o API Login com status code {int}")
    public void validoOAPILoginComStatusCoide(Integer int1) {
        gerarToken.validarStatusCode(int1);
    }


}

package org.br.com.testes.steps.binQuery;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.br.com.testes.controllers.binQuery.BinQueryController;

public class BinQuerySteps {
    private final BinQueryController binQueryController;

    public BinQuerySteps() {
        this.binQueryController = new BinQueryController();
    }

    @Given("que estou na pagina de consulta de BIN")
    public void queEstouNaPaginaDeConsultaDeBin() {
        // Inicialização da página
    }

    @When("realizo a consulta do BIN {string}")
    public void realizoAConsultaDoBin(String bin) {
        binQueryController.realizarConsultaBin(bin);
    }

    @Then("valido que o BIN foi consultado com sucesso")
    public void validoQueOBinFoiConsultadoComSucesso() {
        binQueryController.validarBinConsultado();
    }

    @And("valido o status code do {string} ser {int}")
    public void validoOStatusCodeDoSer(String api, int statusCode) {
        binQueryController.validarStatusCode(statusCode);
    }
} 
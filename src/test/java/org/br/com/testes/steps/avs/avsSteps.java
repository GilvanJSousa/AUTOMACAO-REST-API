package org.br.com.testes.steps.avs;

import org.br.com.testes.controllers.avs.AvsController;
import org.br.com.testes.utils.LogFormatter;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class AvsSteps {
    private final AvsController avsController;
    private Response response;

    public AvsSteps() {
        this.avsController = new AvsController();
    }

    @Given("que estou na pagina de pagamento com AVS")
    public void queEstouNaPaginaDePagamentoComAvs() {
        LogFormatter.logStep("Pagina de pagamento com AVS aberta");
    }

    @When("realizo o pagamento com cartao de credito e AVS")
    public void realizoOPagamentoComCartaoDeCreditoEAvs() {
        avsController.realizarPagamentoComAvs();
    }

    @Then("valido que o pagamento foi autorizado com sucesso com AVS")
    public void validoQueOPagamentoFoiAutorizadoComSucessoComAvs() {
        avsController.validarPagamentoAutorizado();
    }

    @And("o status code do 'AVS' ser {int}")
    public void oStatusCodeDoAvsSer(int statusCode) {
        avsController.validarStatusCode(statusCode);
    }
} 
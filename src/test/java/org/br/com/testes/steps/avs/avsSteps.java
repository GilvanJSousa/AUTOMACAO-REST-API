package org.br.com.testes.steps.avs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.br.com.testes.controllers.avs.AvsController;
import org.br.com.testes.utils.LogFormatter;
import org.junit.jupiter.api.Assertions;

public class AvsSteps {
    private final AvsController avsController;
    private Response response;

    public AvsSteps() {
        this.avsController = new AvsController();
    }

    @Given("que estou na pagina de pagamento com AVS")
    public void queEstouNaPaginaDePagamentoComAvs() {
    }

    @When("realizo o pagamento com cartao de credito e AVS")
    public void realizoOPagamentoComCartaoDeCreditoEAvs() {
        avsController.realizarPagamentoComAvs();
    }

    @Then("valido que o pagamento foi autorizado com sucesso com AVS")
    public void validoQueOPagamentoFoiAutorizadoComSucessoComAvs() {
    }
} 
package org.br.com.testes.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.br.com.testes.controllers.usuarios.MpBoletoController;

public class MpBoletoSteps {
    private MpBoletoController controller;

    public MpBoletoSteps() {
        controller = new MpBoletoController();
    }

    @Given("que eu tenho um boleto válido")
    public void queEuTenhoUmBoletoValido() {
        controller.prepararRequisicaoBoleto();
    }

    @When("eu envio a requisição de pagamento com boleto")
    public void euEnvioARequisicaoDePagamentoComBoleto() {
        controller.enviarRequisicaoPagamento();
    }

    @Then("o pagamento com boleto deve ser processado com sucesso")
    public void oPagamentoComBoletoDeveSerProcessadoComSucesso() {
        controller.validarPagamentoProcessadoComSucesso();
    }

    @And("o status code do pagamento com boleto deve ser {int}")
    public void oStatusCodeDoPagamentoComBoletoDeveSer(int statusCode) {
        controller.validarStatusCode(statusCode);
    }
} 
package org.br.com.testes.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.br.com.testes.controllers.usuarios.MpCartaoDeCreditoController;

public class MpCartaoDeCreditoSteps {
    private MpCartaoDeCreditoController mpCartaoDeCreditoController;

    public MpCartaoDeCreditoSteps() {
        mpCartaoDeCreditoController = new MpCartaoDeCreditoController();
    }

    @Given("que eu tenho um cartão de crédito válido")
    public void queEuTenhoUmCartaoDeCreditoValido() {
        mpCartaoDeCreditoController.prepararRequisicaoCartaoCredito();
    }

    @When("eu envio a requisição de pagamento")
    public void euEnvioARequisicaoDePagamento() {
        mpCartaoDeCreditoController.enviarRequisicaoPagamento();
    }

    @Then("o pagamento deve ser processado com sucesso")
    public void oPagamentoDeveSerProcessadoComSucesso() {
        mpCartaoDeCreditoController.validarPagamentoProcessadoComSucesso();
    }

    @And("o status code deve ser {int}")
    public void oStatusCodeDeveSer(int statusCode) {
        mpCartaoDeCreditoController.validarStatusCode(statusCode);
    }
} 
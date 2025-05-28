package org.br.com.testes.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.br.com.testes.controllers.mpBoleto.MpBoletoController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MpBoletoSteps {
    private final MpBoletoController mpBoletoController;
    private static final Logger logger = LoggerFactory.getLogger(MpBoletoSteps.class);

    public MpBoletoSteps() {
        mpBoletoController = new MpBoletoController();
    }

    @Given("que eu tenho um boleto válido")
    public void queEuTenhoUmBoletoValido() {
        mpBoletoController.prepararRequisicaoBoleto();
    }

    @When("eu envio a requisição de pagamento com boleto")
    public void euEnvioARequisicaoDePagamentoComBoleto() {
        mpBoletoController.enviarRequisicaoPagamento();
    }

    @Then("o pagamento com boleto deve ser processado com sucesso")
    public void oPagamentoComBoletoDeveSerProcessadoComSucesso() {
        mpBoletoController.validarPagamentoProcessadoComSucesso();
    }

    @And("o status code do pagamento com boleto deve ser {int}")
    public void oStatusCodeDoPagamentoComBoletoDeveSer(int statusCode) {
        mpBoletoController.validarStatusCode(statusCode);
    }
}
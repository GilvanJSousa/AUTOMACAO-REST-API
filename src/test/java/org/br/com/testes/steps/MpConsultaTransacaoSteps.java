package org.br.com.testes.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.br.com.testes.controllers.consultaCapturaCancelamento.ConsultaTransacaoController;
import org.br.com.testes.manager.UsuarioManager;

public class MpConsultaTransacaoSteps {
    private final ConsultaTransacaoController controller;

    public MpConsultaTransacaoSteps() {
        controller = new ConsultaTransacaoController();
    }

    @Given("que eu tenho um PaymentId válido")
    public void queEuTenhoUmPaymentIdValido() {
        // O PaymentId será obtido da transação anterior (CT-1001, CT-1002 ou CT-1003)
        // Não é necessário setar manualmente, pois o UsuarioManager já terá o valor
    }

    @When("eu envio a requisição de consulta")
    public void euEnvioARequisicaoDeConsulta() {
        controller.consultarTransacao();
    }

    @Then("a transação deve ser consultada com sucesso")
    public void aTransacaoDeveSerConsultadaComSucesso() {
        controller.validarConsultaTransacao();
    }

    @And("o status code deve ser {int}")
    public void oStatusCodeDeveSer(int statusCode) {
        controller.validarStatusCode(statusCode);
    }
} 
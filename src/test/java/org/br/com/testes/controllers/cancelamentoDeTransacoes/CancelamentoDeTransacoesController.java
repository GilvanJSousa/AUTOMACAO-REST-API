package org.br.com.testes.controllers.cancelamentoDeTransacoes;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.br.com.testes.manager.UsuarioManager;
import org.br.com.testes.utils.LogFormatter;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CancelamentoDeTransacoesController {
    private static final Logger logger = LoggerFactory.getLogger(CancelamentoDeTransacoesController.class);
    private static final String BASE_URL = "https://apisandbox.cieloecommerce.cielo.com.br";
    private static final String ENDPOINT_SALES = "/1/sales";
    private static final String MERCHANT_ID = "1dbf6ac5-0bb2-4fdb-a6a2-663f6e9554c3";
    private static final String MERCHANT_KEY = "DPECNPURVQHOKMIPZLWREWERXXKVRWXYUCRKGOBA";
    private Response response;

    public void cancelarTransacaoPorPaymentId() {
        String paymentId = UsuarioManager.getPaymentId();
        LogFormatter.logStep("Iniciando cancelamento por PaymentId: " + paymentId);
        
        response = given()
                .contentType("text/json")
                .header("MerchantId", MERCHANT_ID)
                .header("MerchantKey", MERCHANT_KEY)
                .log().all(true)
                .baseUri(BASE_URL)
                .when()
                .put(ENDPOINT_SALES + "/" + paymentId + "/void");
        
        LogFormatter.logStep("Cancelamento por PaymentId realizado. Status Code: " + response.getStatusCode());
        LogFormatter.logStep("Resposta: " + response.getBody().asString());
    }

    public void cancelarTransacaoPorMerchantOrderId() {
        String merchantOrderId = UsuarioManager.getMerchantOrderId();
        LogFormatter.logStep("Iniciando cancelamento por MerchantOrderId");
        response = given()
                .contentType(ContentType.JSON)
                .header("MerchantId", MERCHANT_ID)
                .header("MerchantKey", MERCHANT_KEY)
                .baseUri(BASE_URL)
                .when()
                .put(ENDPOINT_SALES + "/orderId/" + merchantOrderId + "/void");
        LogFormatter.logStep("Cancelamento por MerchantOrderId realizado");
    }


    public void cancelarTransacaoParcialPorPaymentId() {
        String paymentId = UsuarioManager.getPaymentId();
        String amount = UsuarioManager.getAmount();
        LogFormatter.logStep("Iniciando cancelamento parcial por PaymentId");
        response = given()
                .contentType(ContentType.JSON)
                .header("MerchantId", MERCHANT_ID)
                .header("MerchantKey", MERCHANT_KEY)
                .log().all(true)
                .baseUri(BASE_URL)
                .when()
                .put(ENDPOINT_SALES + "/" + paymentId + "/void?amount=" + amount);
    }

    public void validarCancelamento() {
        LogFormatter.logStep("Validando cancelamento");
        if (response == null) {
            throw new RuntimeException("Resposta não disponível para validação");
        }
        response.then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("Status", equalTo(10))
                .body("ReturnCode", equalTo("0"));
        LogFormatter.logStep("Cancelamento validado com sucesso");
    }

    public void validarCancelamentoParcial() {
        LogFormatter.logStep("Validando cancelamento parcial");
        if (response == null) {
            throw new RuntimeException("Resposta não disponível para validação");
        }
        response.then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("Status", equalTo(10))
                .body("ReturnCode", equalTo("0"))
                .body("Amount", equalTo(Integer.parseInt(UsuarioManager.getAmount())));
        LogFormatter.logStep("Cancelamento parcial validado com sucesso");
    }

    public void validarStatusCode(int statusCode) {
        LogFormatter.logStep("Validando status code: " + statusCode);
        if (response == null) {
            throw new RuntimeException("Resposta não disponível para validação");
        }
        response.then()
                .log().all(true)
                .statusCode(statusCode);
        LogFormatter.logStep("Status code validado com sucesso");
    }
}

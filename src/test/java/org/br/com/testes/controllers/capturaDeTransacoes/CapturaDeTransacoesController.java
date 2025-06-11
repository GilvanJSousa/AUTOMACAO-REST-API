package org.br.com.testes.controllers.capturaDeTransacoes;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.br.com.testes.manager.UsuarioManager;
import org.br.com.testes.utils.LogFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CapturaDeTransacoesController {
    private static final Logger logger = LoggerFactory.getLogger(CapturaDeTransacoesController.class);
    private static final String BASE_URL = "https://apisandbox.cieloecommerce.cielo.com.br";
    private static final String ENDPOINT_SALES = "/1/sales";
    private static final String MERCHANT_ID = "1dbf6ac5-0bb2-4fdb-a6a2-663f6e9554c3";
    private static final String MERCHANT_KEY = "DPECNPURVQHOKMIPZLWREWERXXKVRWXYUCRKGOBA";
    private Response response;

    public void capturarTransacaoPorPaymentId() {
        String paymentId = UsuarioManager.getPaymentId();
        LogFormatter.logStep("Iniciando captura por PaymentId: " + paymentId);
        
        response = given()
                .contentType("text/json")
                .header("MerchantId", MERCHANT_ID)
                .header("MerchantKey", MERCHANT_KEY)
                .baseUri(BASE_URL)
                .when()
                .put(ENDPOINT_SALES + "/" + paymentId + "/capture");
    }

    public void capturarTransacaoParcialPorPaymentId() {
        String paymentId = UsuarioManager.getPaymentId();
        String amount = UsuarioManager.getAmount();
        LogFormatter.logStep("Iniciando captura parcial por PaymentId");
        response = given()
                .contentType(ContentType.JSON)
                .header("MerchantId", MERCHANT_ID)
                .header("MerchantKey", MERCHANT_KEY)
                .baseUri(BASE_URL)
                .when()
                .put(ENDPOINT_SALES + "/" + paymentId + "/capture?amount=" + amount);
    }

    public void validarCaptura() {
        LogFormatter.logStep("Validando captura");
        response.then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("Status", equalTo(2))
                .body("ReturnCode", equalTo("6"))
                .body("ReturnMessage", equalTo("Operation Successful"));
    }

    public void validarCapturaParcial() {
        LogFormatter.logStep("Validando captura parcial");
        response.then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("Status", equalTo(2))
                .body("ReturnCode", equalTo("6"))
                .body("ReturnMessage", equalTo("Operation Successful"));
    }

    public void validarStatusCode(int statusCode) {
        LogFormatter.logStep("Validando status code: " + statusCode);
        response.then()
                .statusCode(statusCode);
    }
}

package org.br.com.testes.controllers.consultaCapturaCancelamento;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.br.com.testes.manager.UsuarioManager;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ConsultaTransacaoController {
    private static final String BASE_URL = "https://apiquerysandbox.cieloecommerce.cielo.com.br";
    private static final String ENDPOINT_SALES = "/1/sales";
    private static final String MERCHANT_ID = "1dbf6ac5-0bb2-4fdb-a6a2-663f6e9554c3";
    private static final String MERCHANT_KEY = "DPECNPURVQHOKMIPZLWREWERXXKVRWXYUCRKGOBA";
    private Response response;

    public Response consultarTransacao() {
        response = given()
                .contentType(ContentType.JSON)
                .header("MerchantId", MERCHANT_ID)
                .header("MerchantKey", MERCHANT_KEY)
                .log().all(true)
                .baseUri(BASE_URL)
                .when()
                .get(ENDPOINT_SALES + "/" + UsuarioManager.getPaymentId());
        
        return response;
    }

    public void validarConsultaTransacao() {
        response.then()
                .body("Payment.Status", equalTo(1))
                .body("Payment.ReturnCode", equalTo("4"))
                .body("Payment.ReturnMessage", equalTo("Operation Successful"));
    }

    public void validarStatusCode(int statusCode) {
        response.then()
                .log().all(true)
                .statusCode(statusCode);
    }
} 
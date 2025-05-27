package org.br.com.testes.controllers.usuarios;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class MpCartaoDeCreditoController {
    private static final String BASE_URL = "https://apisandbox.cieloecommerce.cielo.com.br";
    private static final String ENDPOINT_SALES = "/1/sales";
    private static final String MERCHANT_ID = "1dbf6ac5-0bb2-4fdb-a6a2-663f6e9554c3";
    private static final String MERCHANT_KEY = "DPECNPURVQHOKMIPZLWREWERXXKVRWXYUCRKGOBA";
    private String requestBody;
    private Response response;

    public void prepararRequisicaoCartaoCredito() {
        requestBody = "{\"MerchantOrderId\":\"2014111703\",\"Payment\":{\"Type\":\"CreditCard\",\"Amount\":15700,\"Installments\":1,\"SoftDescriptor\":\"123456789ABCD\",\"CreditCard\":{\"CardNumber\":\"4551870000000183\",\"Holder\":\"Teste Holder\",\"ExpirationDate\":\"12/2021\",\"SecurityCode\":\"123\",\"Brand\":\"Visa\"}}}";
    }

    public Response enviarRequisicaoPagamento() {
        response = given()
                .contentType(ContentType.JSON)
                .header("MerchantId", MERCHANT_ID)
                .header("MerchantKey", MERCHANT_KEY)
                .log().body(true)
                .baseUri(BASE_URL)
                .body(requestBody)
                .when()
                .post(ENDPOINT_SALES);
        return response;
    }

    public void validarPagamentoProcessadoComSucesso() {
        response.then()
                .body("Payment.Status", equalTo(3))
                .body("Payment.ReturnCode", equalTo("57"))
                .body("Payment.ReturnMessage", equalTo("Card Expired"));
    }

    public void validarStatusCode(int statusCode) {
        response.then()
                .log().all(true)
                .statusCode(statusCode);
    }
}
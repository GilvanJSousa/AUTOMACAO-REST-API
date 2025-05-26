package org.br.com.testes.controllers.usuarios;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;
import static io.restassured.RestAssured.given;

public class MpCartaoDeCredito {
    private static final String BASE_URL = "https://apisandbox.cieloecommerce.cielo.com.br";
    private static final String ENDPOINT_SALES = "/1/sales";
    private static final String MERCHANT_ID = "1dbf6ac5-0bb2-4fdb-a6a2-663f6e9554c3";
    private static final String MERCHANT_KEY = "DPECNPURVQHOKMIPZLWREWERXXKVRWXYUCRKGOBA";

    @Test
    public void realizarPagamentoCartaoCredito() {
        String requestBody = "{\"MerchantOrderId\":\"2014111703\",\"Payment\":{\"Type\":\"CreditCard\",\"Amount\":15700,\"Installments\":1,\"SoftDescriptor\":\"123456789ABCD\",\"CreditCard\":{\"CardNumber\":\"4551870000000183\",\"Holder\":\"Teste Holder\",\"ExpirationDate\":\"12/2021\",\"SecurityCode\":\"123\",\"Brand\":\"Visa\"}}}";

        Response response = given()
                .contentType(ContentType.JSON)
                .header("MerchantId", MERCHANT_ID)
                .header("MerchantKey", MERCHANT_KEY)
                .baseUri(BASE_URL)
                .body(requestBody)
                .when()
                .log().all(true)
                .post(ENDPOINT_SALES);

        response.then().statusCode(201);
    }
}
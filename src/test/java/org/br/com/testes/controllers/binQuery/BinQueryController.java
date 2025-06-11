package org.br.com.testes.controllers.binQuery;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class BinQueryController {
    private static final String BASE_URL = "https://apiquerysandbox.cieloecommerce.cielo.com.br";
    private static final String ENDPOINT_BINQUERY = "/1/cardBin/";
    private static final String MERCHANT_ID = "1dbf6ac5-0bb2-4fdb-a6a2-663f6e9554c3";
    private static final String MERCHANT_KEY = "DPECNPURVQHOKMIPZLWREWERXXKVRWXYUCRKGOBA";
    private Response response;

    public void realizarConsultaBin(String bin) {
        System.out.println("Realizando consulta do BIN: " + bin);

        response = given()
                .contentType(ContentType.JSON)
                .header("MerchantId", MERCHANT_ID)
                .header("MerchantKey", MERCHANT_KEY)
                .when()
                .get(BASE_URL + ENDPOINT_BINQUERY + bin);

        System.out.println("Response: " + response.getBody().asString());
    }

    public void validarBinConsultado() {
        response.then()
                .statusCode(200)
                .body("Status", equalTo("00"))
                .body("Provider", notNullValue())
                .body("CardType", notNullValue())
                .body("ForeignCard", notNullValue());
    }

    public void validarStatusCode(int statusCode) {
        System.out.println("Status Code: " + response.getStatusCode());
        response.then().statusCode(statusCode);
    }
} 
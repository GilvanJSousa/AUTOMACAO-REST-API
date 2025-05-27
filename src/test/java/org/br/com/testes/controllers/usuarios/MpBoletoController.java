package org.br.com.testes.controllers.usuarios;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class MpBoletoController {
    private static final String BASE_URL = "https://apisandbox.cieloecommerce.cielo.com.br";
    private static final String ENDPOINT_SALES = "/1/sales";
    private static final String MERCHANT_ID = "1dbf6ac5-0bb2-4fdb-a6a2-663f6e9554c3";
    private static final String MERCHANT_KEY = "DPECNPURVQHOKMIPZLWREWERXXKVRWXYUCRKGOBA";
    private String requestBody;
    private Response response;

    public void prepararRequisicaoBoleto() {
        requestBody = "{  " +
                "    \"MerchantOrderId\":\"2014111706\"," +
                "    \"Customer\":" +
                "    {  " +
                "        \"Name\":\"Comprador Teste Boleto\"," +
                "        \"Identity\": \"1234567890\"," +
                "        \"Address\":" +
                "        {" +
                "          \"Street\": \"Avenida Marechal Câmara\"," +
                "          \"Number\": \"160\",  " +
                "          \"Complement\": \"Sala 934\"," +
                "          \"ZipCode\" : \"22750012\"," +
                "          \"District\": \"Centro\"," +
                "          \"City\": \"Rio de Janeiro\"," +
                "          \"State\" : \"RJ\"," +
                "          \"Country\": \"BRA\"" +
                "        }" +
                "    }," +
                "    \"Payment\":" +
                "    {  " +
                "        \"Type\":\"Boleto\"," +
                "        \"Amount\":15700," +
                "        \"Provider\":\"bradesco2\"," +
                "        \"Address\": \"Rua Teste\"," +
                "        \"BoletoNumber\": \"123\"," +
                "        \"Assignor\": \"Empresa Teste\"," +
                "        \"Demonstrative\": \"Desmonstrative Teste\"," +
                "        \"ExpirationDate\": \"5/1/2015\"," +
                "        \"Identification\": \"11884926754\"," +
                "        \"Instructions\": \"Aceitar somente até a data de vencimento, após essa data juros de 1% dia.\"" +
                "    }" +
                "}";
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
                .body("Payment.Status", equalTo(1))
                .body("Payment.Url", notNullValue())
                .body("Payment.BarCodeNumber", notNullValue())
                .body("Payment.DigitableLine", notNullValue());
    }

    public void validarStatusCode(int statusCode) {
        response.then()
                .log().all(true)
                .statusCode(statusCode);
    }
} 
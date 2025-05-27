package org.br.com.testes.controllers.usuarios;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.anyOf;

public class MpCartaoDeDebitoController {
    private static final String BASE_URL = "https://apisandbox.cieloecommerce.cielo.com.br";
    private static final String ENDPOINT_SALES = "/1/sales";
    private static final String MERCHANT_ID = "1dbf6ac5-0bb2-4fdb-a6a2-663f6e9554c3";
    private static final String MERCHANT_KEY = "DPECNPURVQHOKMIPZLWREWERXXKVRWXYUCRKGOBA";
    private String requestBody;
    private Response response;
    private boolean isAutenticado = false;
    private boolean isCompleto = false;

    public void prepararRequisicaoCartaoDebito() {
        isAutenticado = false;
        isCompleto = false;
        requestBody = "{  " +
                "   \"MerchantOrderId\":\"2014121201\"," +
                "   \"Customer\":{  " +
                "      \"Name\":\"Paulo Henrique\"     " +
                "   }," +
                "   \"Payment\":{  " +
                "     \"Type\":\"DebitCard\"," +
                "     \"Amount\":15700," +
                "     \"Provider\":\"Simulado\"," +
                "     \"ReturnUrl\":\"http://www.google.com.br\"," +
                "     \"DebitCard\":{  " +
                "         \"CardNumber\":\"4532117080573703\"," +
                "         \"Holder\":\"Teste Holder\"," +
                "         \"ExpirationDate\":\"12/2019\"," +
                "         \"SecurityCode\":\"023\"," +
                "         \"Brand\":\"Visa\"" +
                "     }" +
                "   }" +
                "}";
    }

    public void prepararRequisicaoCartaoDebitoAutenticado() {
        isAutenticado = true;
        isCompleto = false;
        requestBody = "{  " +
                "   \"MerchantOrderId\":\"2014121202\"," +
                "   \"Customer\":{  " +
                "      \"Name\":\"Paulo Henrique\"     " +
                "   }," +
                "   \"Payment\":{  " +
                "     \"Type\":\"DebitCard\"," +
                "     \"Amount\":15700," +
                "     \"Provider\":\"Simulado\"," +
                "     \"ReturnUrl\":\"http://www.google.com.br\"," +
                "     \"Authenticate\":true," +
                "     \"DebitCard\":{  " +
                "         \"CardNumber\":\"4532117080573703\"," +
                "         \"Holder\":\"Teste Holder\"," +
                "         \"ExpirationDate\":\"12/2019\"," +
                "         \"SecurityCode\":\"023\"," +
                "         \"Brand\":\"Visa\"" +
                "     }" +
                "   }" +
                "}";
    }

    public void prepararRequisicaoCartaoDebitoCompleto() {
        isAutenticado = false;
        isCompleto = true;
        requestBody = "{  " +
                "   \"MerchantOrderId\":\"2014121203\"," +
                "   \"Customer\":{  " +
                "      \"Name\":\"Paulo Henrique\"," +
                "      \"Identity\":\"11225468954\"," +
                "      \"IdentityType\":\"CPF\"," +
                "      \"Email\":\"compradorteste@teste.com\"," +
                "      \"Birthdate\":\"1991-01-02\"," +
                "      \"Address\":{  " +
                "         \"Street\":\"Rua Teste\"," +
                "         \"Number\":\"123\"," +
                "         \"Complement\":\"AP 123\"," +
                "         \"ZipCode\":\"12345987\"," +
                "         \"City\":\"Rio de Janeiro\"," +
                "         \"State\":\"RJ\"," +
                "         \"Country\":\"BRA\"" +
                "      }," +
                "      \"DeliveryAddress\": {" +
                "         \"Street\":\"Rua Teste\"," +
                "         \"Number\":\"123\"," +
                "         \"Complement\":\"AP 123\"," +
                "         \"ZipCode\":\"12345987\"," +
                "         \"City\":\"Rio de Janeiro\"," +
                "         \"State\":\"RJ\"," +
                "         \"Country\":\"BRA\"" +
                "      }" +
                "   }," +
                "   \"Payment\":{  " +
                "     \"Type\":\"DebitCard\"," +
                "     \"Amount\":15700," +
                "     \"Currency\":\"BRL\"," +
                "     \"Country\":\"BRA\"," +
                "     \"Provider\":\"Simulado\"," +
                "     \"ServiceTaxAmount\":0," +
                "     \"Installments\":1," +
                "     \"Interest\":\"ByMerchant\"," +
                "     \"Capture\":false," +
                "     \"Authenticate\":false," +
                "     \"Recurrent\":false," +
                "     \"ReturnUrl\":\"http://www.google.com.br\"," +
                "     \"DebitCard\":{  " +
                "         \"CardNumber\":\"4532117080573703\"," +
                "         \"Holder\":\"Teste Holder\"," +
                "         \"ExpirationDate\":\"12/2019\"," +
                "         \"SecurityCode\":\"023\"," +
                "         \"Brand\":\"Visa\"" +
                "     }" +
                "   }" +
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
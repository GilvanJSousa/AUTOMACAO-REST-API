package org.br.com.testes.controllers.mpCartao;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.anyOf;

public class MpCartaoDeCreditoController {
    private static final String BASE_URL = "https://apisandbox.cieloecommerce.cielo.com.br";
    private static final String ENDPOINT_SALES = "/1/sales";
    private static final String MERCHANT_ID = "1dbf6ac5-0bb2-4fdb-a6a2-663f6e9554c3";
    private static final String MERCHANT_KEY = "DPECNPURVQHOKMIPZLWREWERXXKVRWXYUCRKGOBA";
    private String requestBody;
    private Response response;
    private boolean isAutenticado = false;
    private boolean isCompleto = false;

    public void prepararRequisicaoCartaoCredito() {
        isAutenticado = false;
        isCompleto = false;
        requestBody = "{\"MerchantOrderId\":\"2014111703\",\"Payment\":{\"Type\":\"CreditCard\",\"Amount\":15700,\"Installments\":1,\"SoftDescriptor\":\"123456789ABCD\",\"CreditCard\":{\"CardNumber\":\"4551870000000183\",\"Holder\":\"Teste Holder\",\"ExpirationDate\":\"12/2021\",\"SecurityCode\":\"123\",\"Brand\":\"Visa\"}}}";
    }

    public void prepararRequisicaoCartaoCreditoAutenticado() {
        isAutenticado = true;
        isCompleto = false;
        requestBody = "{ " +
                "\"MerchantOrderId\":\"2014111903\", " +
                "\"Customer\":{ " +
                "  \"Name\":\"Comprador Teste\" " +
                "}, " +
                "\"Payment\":{ " +
                "    \"Type\":\"CreditCard\", " +
                "    \"Amount\":15700, " +
                "    \"Provider\":\"Cielo\", " +
                "    \"ReturnUrl\":\"https://www.google.com.br\", " +
                "    \"Installments\":1, " +
                "    \"Authenticate\":true, " +
                "    \"CreditCard\":{ " +
                "      \"CardNumber\":\"1234123412341231\", " +
                "      \"Holder\":\"Teste Holder\", " +
                "      \"ExpirationDate\":\"12/2018\", " +
                "      \"SecurityCode\":\"123\", " +
                "      \"Brand\":\"Visa\" " +
                "    } " +
                "} " +
                "}";
    }

    public void prepararRequisicaoCartaoCreditoCompleto() {
        isAutenticado = false;
        isCompleto = true;
        requestBody = "{  " +
                "   \"MerchantOrderId\":\"2014111701\"," +
                "    \"Customer\":{  " +
                "      \"Name\":\"Comprador Teste\"," +
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
                "        \"DeliveryAddress\": {" +
                "            \"Street\": \"Rua Teste\"," +
                "            \"Number\": \"123\"," +
                "            \"Complement\": \"AP 123\"," +
                "            \"ZipCode\": \"12345987\"," +
                "            \"City\": \"Rio de Janeiro\"," +
                "            \"State\": \"RJ\"," +
                "            \"Country\": \"BRA\"" +
                "        }" +
                "   }," +
                "   \"Payment\":{  " +
                "     \"Type\":\"CreditCard\"," +
                "     \"Amount\":15700," +
                "     \"Currency\":\"BRL\"," +
                "     \"Country\":\"BRA\"," +
                "     \"Provider\":\"Simulado\"," +
                "     \"ServiceTaxAmount\":0," +
                "     \"Installments\":1," +
                "     \"Interest\":\"ByMerchant\"," +
                "     \"Capture\":false," +
                "     \"Authenticate\":false,    " +
                "     \"Recurrent\": false," +
                "     \"SoftDescriptor\":\"123456789ABCD\"," +
                "     \"CreditCard\":{  " +
                "         \"CardNumber\":\"4024007197692931\"," +
                "         \"Holder\":\"Teste Holder\"," +
                "         \"ExpirationDate\":\"12/2021\"," +
                "         \"SecurityCode\":\"123\"," +
                "         \"SaveCard\":\"false\"," +
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
        if (isAutenticado || isCompleto) {
            response.then()
                    .body("Payment.Status", equalTo(1))
                    .body("Payment.ReturnCode", equalTo("4"))
                    .body("Payment.ReturnMessage", equalTo("Operation Successful"));
        } else {
            response.then()
                    .body("Payment.Status", equalTo(3))
                    .body("Payment.ReturnCode", equalTo("57"))
                    .body("Payment.ReturnMessage", equalTo("Card Expired"));
        }
    }

    public void validarStatusCode(int statusCode) {
        response.then()
                .log().all(true)
                .statusCode(statusCode);
    }
}
package org.br.com.testes.controllers.zeroAuth;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.br.com.testes.manager.TokenizacaoManager;
import org.br.com.testes.model.zeroAuth.ZeroAuthRequest;
import org.br.com.testes.utils.LogFormatter;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ZeroAuthController {
    private static final String BASE_URL = "https://apisandbox.cieloecommerce.cielo.com.br";
    private static final String ENDPOINT_ZEROAUTH = "/1/zeroauth";
    private static final String MERCHANT_ID = "a5c4a7c8-1f1e-4f1e-9f1e-1f1e4f1e9f1e";
    private static final String MERCHANT_KEY = "a5c4a7c8-1f1e-4f1e-9f1e-1f1e4f1e9f1e";
    private Response response;

    public void realizarValidacaoCartao() {
        String cardToken = TokenizacaoManager.getCardToken();
        System.out.println("Realizando validacao do cartao com token: " + cardToken);

        ZeroAuthRequest request = ZeroAuthRequest.builder()
                .cardToken(cardToken)
                .saveCard("false")
                .brand("Visa")
                .build();

        System.out.println("Request: " + request);

        response = given()
                .contentType(ContentType.JSON)
                .header("MerchantId", MERCHANT_ID)
                .header("MerchantKey", MERCHANT_KEY)
                .body(request)
                .when()
                .post(BASE_URL + ENDPOINT_ZEROAUTH);

        System.out.println("Response: " + response.getBody().asString());
    }

    public void realizarValidacaoCartaoDebito() {
        System.out.println("Realizando validacao do cartao de debito");

        ZeroAuthRequest request = ZeroAuthRequest.builder()
                .cardType("DebitCard")
                .cardNumber("1234123412341234")
                .holder("Teste holder")
                .expirationDate("10/2021")
                .securityCode("123")
                .saveCard("false")
                .brand("Visa")
                .build();

        System.out.println("Request: " + request);

        response = given()
                .contentType(ContentType.JSON)
                .header("MerchantId", MERCHANT_ID)
                .header("MerchantKey", MERCHANT_KEY)
                .body(request)
                .when()
                .post(BASE_URL + ENDPOINT_ZEROAUTH);

        System.out.println("Response: " + response.getBody().asString());
    }

    public void validarCartaoAutorizado() {
        validarRespostaSucesso();
    }

    private void validarRespostaSucesso() {
        try {
            response.then()
                    .statusCode(200)
                    .body("Valid", equalTo(true))
                    .body("ReturnCode", equalTo("00"))
                    .body("ReturnMessage", equalTo("Transacao autorizada"));
        } catch (AssertionError e) {
            // Se a validação falhar, verifica se é um erro de serviço indisponível
            if (response.getBody().asString().contains("Service Unavailable")) {
                System.out.println("Serviço indisponível. Aguarde alguns minutos e tente novamente.");
                throw new RuntimeException("Serviço indisponível: " + response.getBody().asString());
            }
            throw e;
        }
    }

    public void validarStatusCode(int statusCode) {
        System.out.println("Status Code: " + response.getStatusCode());
        response.then().statusCode(statusCode);
    }
} 
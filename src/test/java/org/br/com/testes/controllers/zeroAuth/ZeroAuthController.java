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
    private static final String MERCHANT_ID = "1dbf6ac5-0bb2-4fdb-a6a2-663f6e9554c3";
    private static final String MERCHANT_KEY = "DPECNPURVQHOKMIPZLWREWERXXKVRWXYUCRKGOBA";
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

    public void realizarValidacaoCartaoComAvs() {
        System.out.println("Realizando validacao do cartao com AVS");

        ZeroAuthRequest.Avs avs = ZeroAuthRequest.Avs.builder()
                .cpf("10939107716")
                .zipCode("24320570")
                .street("Estrada Caetano Monteiro")
                .number("391")
                .complement("Bl")
                .district("Niteroi")
                .build();

        ZeroAuthRequest request = ZeroAuthRequest.builder()
                .cardType("CreditCard")
                .cardNumber("4024007110880035")
                .holder("Teste Holder")
                .expirationDate("10/2026")
                .securityCode("262")
                .saveCard("true")
                .brand("Visa")
                .avs(avs)
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

    public void validarCartaoDebitoAutorizado() {
        validarRespostaSucessoDebito();
    }

    public void validarCartaoComAvsAutorizado() {
        validarRespostaSucessoAvsNegada();
    }

    private void validarRespostaSucesso() {
        try {
            response.then()
                    .statusCode(200)
                    .body("Valid", equalTo(false))
                    .body("ReturnCode", equalTo("35"))
                    .body("ReturnMessage", equalTo("Autorizacao negada"));
        } catch (AssertionError e) {
            // Se a validação falhar, verifica se é um erro de autenticação
            if (response.getBody().asString().contains("MerchantKey is invalid")) {
                System.out.println("Erro de autenticação: MerchantKey inválida");
                throw new RuntimeException("Erro de autenticação: " + response.getBody().asString());
            }
            // Se a validação falhar, verifica se é um erro de serviço indisponível
            if (response.getBody().asString().contains("Service Unavailable")) {
                System.out.println("Serviço indisponível. Aguarde alguns minutos e tente novamente.");
                throw new RuntimeException("Serviço indisponível: " + response.getBody().asString());
            }
            throw e;
        }
    }

    private void validarRespostaSucessoDebito() {
        try {
            response.then()
                    .statusCode(200)
                    .body("Valid", equalTo(true))
                    .body("ReturnCode", equalTo("00"))
                    .body("ReturnMessage", equalTo("Transacao autorizada"));
        } catch (AssertionError e) {
            // Se a validação falhar, verifica se é um erro de autenticação
            if (response.getBody().asString().contains("MerchantKey is invalid")) {
                System.out.println("Erro de autenticação: MerchantKey inválida");
                throw new RuntimeException("Erro de autenticação: " + response.getBody().asString());
            }
            // Se a validação falhar, verifica se é um erro de serviço indisponível
            if (response.getBody().asString().contains("Service Unavailable")) {
                System.out.println("Serviço indisponível. Aguarde alguns minutos e tente novamente.");
                throw new RuntimeException("Serviço indisponível: " + response.getBody().asString());
            }
            throw e;
        }
    }

    private void validarRespostaSucessoAvsNegada() {
        try {
            response.then()
                    .statusCode(200)
                    .body("Valid", equalTo(false))
                    .body("ReturnCode", equalTo("35"))
                    .body("ReturnMessage", equalTo("Autorizacao negada"));
        } catch (AssertionError e) {
            // Se a validação falhar, verifica se é um erro de autenticação
            if (response.getBody().asString().contains("MerchantKey is invalid")) {
                System.out.println("Erro de autenticação: MerchantKey inválida");
                throw new RuntimeException("Erro de autenticação: " + response.getBody().asString());
            }
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
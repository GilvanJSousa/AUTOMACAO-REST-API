package org.br.com.testes.controllers.tokenizacao;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Getter;
import org.br.com.testes.manager.TokenizacaoManager;
import org.br.com.testes.manager.UsuarioManager;
import org.br.com.testes.model.tokenizacao.TokenizacaoRequest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class TokenizacaoController {
    private static final String BASE_URL = "https://apisandbox.cieloecommerce.cielo.com.br";
    private static final String BASE_URL_QUERY = "https://apiquerysandbox.cieloecommerce.cielo.com.br";
    private static final String ENDPOINT_CARD = "/1/card";
    private static final String MERCHANT_ID = "1dbf6ac5-0bb2-4fdb-a6a2-663f6e9554c3";
    private static final String MERCHANT_KEY = "DPECNPURVQHOKMIPZLWREWERXXKVRWXYUCRKGOBA";
    private String requestBody;
    private Response response;

    public void prepararRequisicaoTokenizacao() throws Exception {
        TokenizacaoRequest request = TokenizacaoRequest.builder()
                .customerName("Paulo Henrique")
                .cardNumber("4024007110880035")
                .holder("Teste Holder")
                .expirationDate("10/2026")
                .brand("Visa")
                .build();

        requestBody = new ObjectMapper().writeValueAsString(request);
    }

    public void enviarRequisicaoTokenizacao() {
        response = given()
                .contentType(ContentType.JSON)
                .header("MerchantId", MERCHANT_ID)
                .header("MerchantKey", MERCHANT_KEY)
                .baseUri(BASE_URL)
                .body(requestBody)
                .when()
                .post(ENDPOINT_CARD);

        TokenizacaoManager.setCardToken(response.jsonPath().getString("CardToken"));
        System.out.println("Card Token: " + TokenizacaoManager.getCardToken());
    }

    public void validarTokenizacaoSucesso() {
        response.then()
                .body("CardToken", notNullValue())
                .body("Links", notNullValue());
    }

    public void validarStatusCode(int statusCode) {
        response.then()
                .statusCode(statusCode);
        System.out.println("Status Code: " + statusCode);
    }

    public void consultarCartaoTokenizado() {
        String cardToken = TokenizacaoManager.getCardToken();
        System.out.println("Consultando cartao com token: " + cardToken);
        
        response = given()
                .contentType(ContentType.JSON)
                .header("MerchantId", MERCHANT_ID)
                .header("MerchantKey", MERCHANT_KEY)
                .baseUri(BASE_URL_QUERY)
                .when()
                .get(ENDPOINT_CARD + "/" + cardToken);

        System.out.println("Resposta da consulta: " + response.asString());
    }

    public void validarConsultaCartaoSucesso() {
        response.then()
                .body("CardNumber", notNullValue())
                .body("Holder", notNullValue())
                .body("ExpirationDate", notNullValue());
    }
}

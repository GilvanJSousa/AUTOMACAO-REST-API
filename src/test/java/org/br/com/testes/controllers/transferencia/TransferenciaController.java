package org.br.com.testes.controllers.transferencia;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Getter;
import org.br.com.testes.tokens.GerarToken;
import org.br.com.testes.manager.TokenManager;
import org.br.com.testes.manager.TransferenciaManager;
import org.br.com.testes.model.TransferenciaRequest;
import org.br.com.testes.utils.LogFormatter;

import static io.restassured.RestAssured.*;

@Getter
public class TransferenciaController {

    private Response response;

    private String requestBody;

    private static final String BASE_URL = "http://localhost:9090";

    private static final String ENDPOINT_TRANSFERENCIA = "/transferencias";

    // Token será obtido dinamicamente após a geração

    public TransferenciaController() {
        response = null;
    }


    public void prepararRequisicaoDeTransferencia(double valor) throws JsonProcessingException {

        // Joao Silva =====> ID: 6867c26d12ba0eba945873a5
        // Maria Santos ===> ID: 6867c26d12ba0eba945873a6
        // Pedro Oliveira => ID: 6867c26d12ba0eba945873a7
        // Ana Costa ======> ID: 6867c26d12ba0eba945873a8

        GerarToken.gerarTokenAdmin();

        String token = TokenManager.getToken();

        TransferenciaRequest request = TransferenciaRequest.builder()
                .contaOrigem("6867c26d12ba0eba945873a7")
                .contaDestino("6867c26d12ba0eba945873a6")
                .token(token)
                .valor(valor)
                .build();

        requestBody = new ObjectMapper().writeValueAsString(request);
    }

    public void realizarTransferencia() {

        String token = TokenManager.getToken();

        response = given()
                .baseUri(BASE_URL)
                .header("accept", "*/*")
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(ENDPOINT_TRANSFERENCIA)
                .then()
                .extract().response();
        LogFormatter.logJson(response.asPrettyString());
    }

    public void realizarTransferenciaComValorDivergente(double valor) {

        String token = TokenManager.getToken();

        response = given()
                .baseUri(BASE_URL)
                .header("accept", "*/*")
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(ENDPOINT_TRANSFERENCIA)
                .then()
                .extract().response();
        LogFormatter.logJson(response.asPrettyString());
    }

    public void listarTransferenciasBancarias() {
        GerarToken.gerarTokenAdmin();
        String token = TokenManager.getToken();

        response = given()
                .baseUri(BASE_URL)
                .param("page", 1)
                .param("limit", 10)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .get(ENDPOINT_TRANSFERENCIA)
                .then()
                .extract().response();

        LogFormatter.logJson(response.asPrettyString());
    }

    public void consultarTransferenciaBancaria() {
        GerarToken.gerarTokenAdmin();
        String token = TokenManager.getToken();

        String id = "6867c26d12ba0eba945873a7";

        response = given()
                .baseUri(BASE_URL)
                .pathParam("id", id)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .get(ENDPOINT_TRANSFERENCIA + "/{id}")
                .then()
                .extract().response();

        String idTransferencia = response.jsonPath().getString("_id");
        TransferenciaManager.setIdTransferencia(idTransferencia);
        LogFormatter.logJson(response.asPrettyString());
        LogFormatter.logStep("ID da Transferência: " + idTransferencia);
    }

    public void atualizarCompletamenteTransferencia() {

        String token = TokenManager.getToken();

        String idTransferencia = TransferenciaManager.getIdTransferencia();

        response = given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(requestBody)
                .when()
                .put(ENDPOINT_TRANSFERENCIA + "/" + idTransferencia);
    }

    public void atualizarParcialmenteTransferencia() {
        String token = TokenManager.getToken();

        String idTransferencia = TransferenciaManager.getIdTransferencia();

        response = given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(requestBody)
                .when()
                .patch(ENDPOINT_TRANSFERENCIA + "/" + idTransferencia);
        
    }

    public void removeUmaTransferencia() {
        GerarToken.gerarTokenAdmin();
        String token = TokenManager.getToken();

        String idTransferencia = TransferenciaManager.getIdTransferencia();

        response = given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .delete(ENDPOINT_TRANSFERENCIA + "/" + idTransferencia);
    }

    public void validarStatusCode(int statusCode) {
        response.then()
                .statusCode(statusCode);
        LogFormatter.logJson(String.valueOf("Status Code: " + statusCode));
    }

}

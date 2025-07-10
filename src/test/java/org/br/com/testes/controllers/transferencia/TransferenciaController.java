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
                .contaOrigem("686fa208cbdb4375dbb8ed47")
                .contaDestino("686fa208cbdb4375dbb8ed48")
                .token(token)
                .valor(valor)
                .build();

        requestBody = new ObjectMapper().writeValueAsString(request);
    }

    public void realizarTransferencia(double valor) throws JsonProcessingException {
        prepararRequisicaoDeTransferencia(valor);

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

    public void realizarTransferenciaComValorDivergente(double valor) throws JsonProcessingException {
        prepararRequisicaoDeTransferencia(valor);

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

        // Primeiro, vamos listar as transferências para obter um ID válido
        Response listResponse = given()
                .baseUri(BASE_URL)
                .param("page", 1)
                .param("limit", 10)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .get(ENDPOINT_TRANSFERENCIA)
                .then()
                .extract().response();

        // Obter o ID da primeira transferência
        String idTransferencia = listResponse.jsonPath().getString("transferencias[0]._id");
        
        // Se não houver transferências ou ID for null, criar uma primeiro
//        if (idTransferencia == null || idTransferencia.isEmpty()) {
//            LogFormatter.logStep("Nenhuma transferência encontrada. Criando uma transferência para consulta...");
//            try {
//                prepararRequisicaoDeTransferencia(15.00);
//                realizarTransferencia();
//
//                // Aguardar um pouco para garantir que a transferência foi criada
//                Thread.sleep(1000);
//
//                // Listar novamente para obter o ID
//                listResponse = given()
//                        .baseUri(BASE_URL)
//                        .param("page", 1)
//                        .param("limit", 10)
//                        .contentType(ContentType.JSON)
//                        .header("Authorization", "Bearer " + token)
//                        .when()
//                        .get(ENDPOINT_TRANSFERENCIA)
//                        .then()
//                        .extract().response();
//
//                idTransferencia = listResponse.jsonPath().getString("transferencias[0]._id");
//            } catch (JsonProcessingException e) {
//                throw new RuntimeException(e);
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
//        }
//
//        if (idTransferencia == null || idTransferencia.isEmpty()) {
//            throw new RuntimeException("Não foi possível obter ID de transferência para consulta");
//        }

        TransferenciaManager.setIdTransferencia(idTransferencia);
        LogFormatter.logStep("ID da Transferência para consulta: " + idTransferencia);

        // Agora consultar a transferência específica
        response = given()
                .baseUri(BASE_URL)
                .pathParam("id", idTransferencia)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .get(ENDPOINT_TRANSFERENCIA + "/{id}")
                .then()
                .extract().response();

        LogFormatter.logJson(response.asPrettyString());
    }

    public void atualizarCompletamenteTransferencia(double valor) throws JsonProcessingException {
        prepararRequisicaoDeTransferencia(valor);

        String token = TokenManager.getToken();

        String idTransferencia = TransferenciaManager.getIdTransferencia();

        response = given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(requestBody)
                .when()
                .put(ENDPOINT_TRANSFERENCIA + "/" + idTransferencia);
        System.out.println("Response PUT: " + response.asString());
    }

    public void atualizarParcialmenteTransferencia(double valor) throws JsonProcessingException {
        prepararRequisicaoDeTransferencia(valor);
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

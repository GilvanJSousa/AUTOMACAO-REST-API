package org.br.com.testes.controllers.transferencia;


import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Getter;
import org.br.com.testes.controllers.tokens.GerarTokenController;
import org.br.com.testes.manager.TokenManager;
import org.br.com.testes.model.TransferenciaRequest;
import org.br.com.testes.utils.LogFormatter;
import org.junit.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

import org.br.com.testes.controllers.tokens.GerarTokenController;
import static io.restassured.RestAssured.*;

@Getter
@Epic("API de Transferencias")
@Feature("Transferencia entre Contas")
public class TransferenciaController {

    private Response response;

    private static final String BASE_URL = "http://localhost:3000";

    private static final String ENDPOINT_TRANSFERENCIA = "/transferencias";

    private static final String token = TokenManager.getToken();

    public TransferenciaController() {
        response = null;
    }


    public void realizarTransferencia() {

        GerarTokenController.gerarTokenAdmin();

        TransferenciaRequest request = TransferenciaRequest.builder()
                .contaOrigem("6866ef0c822da5a2bb628768")
                .contaDestino("6866ef0c822da5a2bb628767")
                .token(token)
                .valor(100.00)
                .build();

//        String token = TokenManager.getToken();
//        if (token == null || token.isEmpty()) {
//            throw new RuntimeException("Token nao foi gerado corretamente");
//        }

        response = given()
                .baseUri(BASE_URL)
                .header("accept", "*/*")
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(ENDPOINT_TRANSFERENCIA)
                .then()
                .extract().response();
        LogFormatter.logJson(response.asPrettyString());
    }

    public void validarStatusCode(int statusCode) {
        response.then()
                .statusCode(statusCode);
        LogFormatter.logJson(String.valueOf("Status Code: " + statusCode));
    }

}

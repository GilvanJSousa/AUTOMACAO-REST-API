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

import static io.restassured.RestAssured.*;

@Getter
public class TransferenciaController {

    private Response response;

    private static final String BASE_URL = "http://localhost:3000";

    private static final String ENDPOINT_TRANSFERENCIA = "/transferencias";

    // Token será obtido dinamicamente após a geração

    public TransferenciaController() {
        response = null;
    }


    public void realizarTransferencia() {

        GerarTokenController.gerarTokenAdmin();

        // Obter o token atualizado após a geração
        String token = TokenManager.getToken();
        
        // Validar se o token foi obtido
        if (token == null || token.isEmpty()) {
            throw new RuntimeException("Token nao foi gerado corretamente");
        }

        TransferenciaRequest request = TransferenciaRequest.builder()
                .contaOrigem("6866ef0c822da5a2bb628768")
                .contaDestino("6866ef0c822da5a2bb628767")
                .token(token)
                .valor(100.00)
                .build();

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

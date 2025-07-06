package org.br.com.testes.controllers;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;
import org.br.com.testes.controllers.tokens.GerarTokenController;
import org.br.com.testes.manager.TokenManager;
import org.br.com.testes.utils.LogFormatter;
import org.junit.Test;

import static io.restassured.RestAssured.*;


@Getter
@Setter
public class ContasController {

    private Response response;

    private static final String BASE_URL = "http://localhost:3000";

    private static final String ENDPOINT_CONTAS = "/contas";

    private static final String token = TokenManager.getToken();
    
    public ContasController() {
        response = null;
    }
    
    public void listarContasBancarias() {

        GerarTokenController.gerarTokenAdmin();

        response = given()
                .baseUri(BASE_URL)
                .header("accept", "*/*")
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .when()
                .get(ENDPOINT_CONTAS);

        LogFormatter.logJson(response.asPrettyString());
    }

    public void validasStatusCode(int statusCode) {
        response.then().statusCode(statusCode);
        LogFormatter.logJson(String.valueOf("Status code: " + statusCode));
    }
    
}

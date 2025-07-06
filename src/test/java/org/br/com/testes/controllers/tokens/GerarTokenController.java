package org.br.com.testes.controllers.tokens;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;
import org.br.com.testes.manager.TokenManager;
import org.br.com.testes.model.GerarTokenResquest;
import org.br.com.testes.utils.LogFormatter;
import org.junit.Test;

import static io.restassured.RestAssured.*;

@Getter
@Setter
public class GerarTokenController {

    private static Response response;

    private static final String BASE_URL = "http://localhost:3000";

    private static final String ENDPOINT = "/login";

    public GerarTokenController() {
        response = null;
    }

    public static void gerarTokenAdmin() {

        GerarTokenResquest resquest = GerarTokenResquest.builder()
                .username("admin")
                .senha("admin123")
                .build();

        response = given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .body(resquest)
                .when()
                .post(ENDPOINT);
        String token = response.jsonPath().getString("token");
        TokenManager.setToken(token);
        LogFormatter.logJson("Token: " + token.substring(1, 10) + "...");

    }

    public void gerarTokenUsuario() {
        GerarTokenResquest resquest = GerarTokenResquest.builder()
                .username("usuario")
                .senha("senha123")
                .build();

        response = given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .body(resquest)
                .when()
                .post(ENDPOINT);

        String token = response.jsonPath().getString("token");
        TokenManager.setToken(token);
        LogFormatter.logStepJson("Token: ", token.substring(1, 10) + "...");
    }

    public void validarStatusCode(int statusCode) {
        response.then().statusCode(statusCode);
        LogFormatter.logJson(String.valueOf("Status Code: " + statusCode));
    }


    /**
     * Mascarar token para log seguro
     */
    private String mascararToken(String token) {
        if (token == null || token.length() <= 12) {
            return "****";
        }
        return token.substring(0, 6) + "..." + token.substring(token.length() - 6);
    }

}

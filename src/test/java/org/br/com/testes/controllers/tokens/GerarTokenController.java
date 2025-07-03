package org.br.com.testes.controllers.tokens;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.br.com.testes.manager.TokenManager;
import org.br.com.testes.model.GerarTokenResquest;
import org.br.com.testes.utils.LogFormatter;
import org.junit.Test;

import static io.restassured.RestAssured.*;


public class GerarTokenController {

    private static Response response;

    private static final String BASE_URL = "http://localhost:3000";

    private static final String ENDPOINT = "/login";

    public GerarTokenController() {
        response = null;
    }

    @Test
    public void gerarTokenAdmin() {

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
        LogFormatter.logStep("Token : " + token.replace((char) 1, (char) 10) + "...");

    }

}

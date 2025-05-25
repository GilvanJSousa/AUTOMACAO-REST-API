package org.br.com.testes.tokens;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.br.com.testes.manager.TokenManager;
import org.br.com.testes.manager.UsuarioManager;

import static io.restassured.RestAssured.given;

public class GerarToken {

    private Response response;
    private static final String BASE_URL = "http://localhost:3000";
    private static final String END_POINT_TOKEN = "/auth/login";

    public GerarToken() {
        response = null;
    }



    public void gerarTokenBearer() {
        System.out.println("Gerando token Bearer");
        String email = UsuarioManager.getEmailUsuario();
        String password = UsuarioManager.getSenhaUsuario();

        GerarTokenResquest gerarTokenResquest = GerarTokenResquest.builder()
                .email(email)
                .senha(password)
                .build();


        String token =
                given()
                        .body(gerarTokenResquest)
                        .contentType(ContentType.JSON)
                        .baseUri(BASE_URL)
                        .when()
                        .post(END_POINT_TOKEN)
                        .then()
                        .log().body()
                        .extract()
                        .path("token");

        TokenManager.setToken(token);
    }

}

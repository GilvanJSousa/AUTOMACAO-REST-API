package org.br.com.testes.tokens;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.apachecommons.CommonsLog;
import org.br.com.testes.manager.TokenManager;
import org.br.com.testes.utils.LogFormatter;
import org.br.com.testes.utils.LogFormatter;
import org.junit.Test;

import static io.restassured.RestAssured.*;

@CommonsLog
public class GerarToken {

	private static Response response;
	private static final String BASE_URL = "http://localhost:8089";
	private static final String ENDPOINT_TOKEN = "/api/v1/auth";

	public GerarToken() {
		response = null;
	}

	public static void gerarToken() {

		GerarTokenResquest resquest = GerarTokenResquest.builder()
				.email("admin@email.com")
				.senha("654321")
				.build();

		response = given()
				.contentType(ContentType.JSON)
				.header("accept", "application/json")
				.baseUri(BASE_URL)
				.body(resquest)
				.when()
				.post(ENDPOINT_TOKEN)
				.then()
				.statusCode(200)
				.extract().response();

		String token = response.jsonPath().getString("data.token");
		TokenManager.setToken(token);
		LogFormatter.logStep("Token gerado com sucesso: " + token.substring(0, 10) + "...");
	}
//    private Response response;
//    private static final String BASE_URL = "https://serverest.dev";
//    private static final String END_POINT_TOKEN = "/login";
//    private final CadastrarUsuarioController cadastrarUsuarioController;
//
//    public GerarToken() {
//        response = null;
//        cadastrarUsuarioController = new CadastrarUsuarioController();
//    }
//
//    public String gerarTokenBearer() {
//        log.info("Gerando token Bearer");
//        cadastrarUsuarioController.cadastrarNovoUsuario(
//            FakerApiData.getName02(),
//            FakerApiData.getEmail02(),
//            FakerApiData.getPassword02(),
//            "true"
//        );
//
//        GerarTokenResquest gerarTokenResquest = GerarTokenResquest.builder()
//                .email(FakerApiData.getEmail02())
//                .password(FakerApiData.getPassword02())
//                .build();
//
//        log.info("Tentando fazer login com email: " + FakerApiData.getEmail02());
//        AuthenticationToken token = (given()
//                .log().body(true)
//                .body(gerarTokenResquest)
//                .contentType(ContentType.JSON)
//                .baseUri(BASE_URL)
//                .when()
//                .post(END_POINT_TOKEN)
//                .then()
//                .log().body(true)
//                .assertThat().extract().as(AuthenticationToken.class));
//
//        String tokenValue = token.getToken();
//        TokenManager.token.set(tokenValue);
//        log.info("Token Bearer gerado com sucesso: " + tokenValue);
//        return tokenValue;
//    }
//
//    @Test
//    public void GerarTokenAdm() {
//        log.info("Gerando token de administrador");
//        cadastrarUsuarioController.cadastrarNovoUsuario(
//            FakerApiData.getName(),
//            FakerApiData.getEmail(),
//            FakerApiData.getPassword(),
//            "true"
//        );
//
//        GerarTokenResquest gerarTokenResquest = GerarTokenResquest.builder()
//                .email(FakerApiData.getEmail())
//                .password(FakerApiData.getPassword())
//                .build();
//
//        AuthenticationToken token = (given()
//                .log().body(true)
//                .body(gerarTokenResquest)
//                .contentType(ContentType.JSON)
//                .baseUri(BASE_URL)
//                .when()
//                .post(END_POINT_TOKEN)
//                .then()
//                .log().body(true)
//                .assertThat().extract().as(AuthenticationToken.class));
//
//        String tokenValue = token.getToken();
//        TokenManager.token.set(tokenValue);
//        log.info("Token gerado com sucesso");
//    }
//
//    @Test
//    public void GerarTokenCarrinho() {
//        log.info("Gerando token de carrinho");
//        cadastrarUsuarioController.cadastrarNovoUsuario(
//            FakerApiData.getName03(),
//            FakerApiData.getEmail03(),
//            FakerApiData.getPassword03(),
//            "false"
//        );
//
//        GerarTokenResquest gerarTokenResquest = GerarTokenResquest.builder()
//                .email(FakerApiData.getEmail03())
//                .password(FakerApiData.getPassword03())
//                .build();
//
//        AuthenticationToken token = (given()
//                .log().body(true)
//                .body(gerarTokenResquest)
//                .contentType(ContentType.JSON)
//                .baseUri(BASE_URL)
//                .when()
//                .post(END_POINT_TOKEN)
//                .then()
//                .log().body(true)
//                .assertThat().extract().as(AuthenticationToken.class));
//
//        String tokenValue = token.getToken();
//        TokenManager.token.set(tokenValue);
//        log.info("Token gerado com sucesso");
//    }
}

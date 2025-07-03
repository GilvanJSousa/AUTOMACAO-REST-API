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

	public static void gerarTokenUsuario() {
		GerarTokenResquest resquest = GerarTokenResquest.builder()
				.email("usuario@email.com")
				.senha("123456")
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

	public static void gerarTokenAdmin() {

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

}

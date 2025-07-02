package org.br.com.testes.controllers;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.br.com.testes.manager.TokenManager;
import org.br.com.testes.model.CadastroViagemRequest;
import org.br.com.testes.tokens.GerarToken;
import org.br.com.testes.utils.LogFormatter;
import org.junit.Test;

import static io.restassured.RestAssured.*;

public class CadastroViagemController {

	private Response response;
	private static final String BASE_URL = "http://localhost:8089/api";
	private static final String ENDPOINT = "/v1/viagens";
	private static final String token = TokenManager.getToken();

	public CadastroViagemController() {
		this.response = null;
	}

	@Test
	public void cadastrarViagem() {
		GerarToken.gerarToken();

		CadastroViagemRequest request = CadastroViagemRequest.builder()
				.acompanhate("Gilvan")
				.dataPartida("2023-08-01")
				.dataRetorno("2023-08-10")
				.localDeDestino("São Paulo")
				.regiao("Sul")
				.build();

		this. response = given()
				.contentType(ContentType.JSON)
				.header("Authorization", "Bearer " + token)
				.baseUri(BASE_URL)
				.body(request)
				.when()
				.post(ENDPOINT)
				.then()
				.extract()
				.response();

		LogFormatter.logStep(response.asPrettyString());

	}

	public void validarStatusCode(int statusCode) {
		response.then().statusCode(statusCode);
		LogFormatter.logStep("Status code: " + statusCode);
	}
}

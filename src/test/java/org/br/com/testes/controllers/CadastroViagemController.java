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


	public CadastroViagemController() {
		this.response = null;
	}

	public void gerarTokenAdmin() {
		GerarToken.gerarTokenAdmin();
	}

	public void cadastrarViagem() {
		String token = "";
		token = TokenManager.getToken();
		CadastroViagemRequest request = CadastroViagemRequest.builder()
				.acompanhate("Maria")
				.dataPartida("2025-03-07")
				.dataRetorno("2025-03-08")
				.localDeDestino("São Paulo")
				.regiao("Sul")
				.build();

		this. response = given()
//				.header("accept", "application/json")
				.header("Authorization", " " + token)
				.contentType(ContentType.JSON)
				.log().all()
				.body(request)
				.when()
				.post(BASE_URL + ENDPOINT);

		LogFormatter.logStep(response.asPrettyString());

	}

	public void validarStatusCode(int statusCode) {
		response.then().statusCode(statusCode);
		LogFormatter.logStep("Status code: " + statusCode);
	}
}

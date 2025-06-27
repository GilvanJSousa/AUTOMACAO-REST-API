package org.br.com.testes.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.br.com.testes.manager.CategoriaManager;
import org.br.com.testes.manager.TokenManager;
import org.br.com.testes.model.CategoriaRequest;
import org.br.com.testes.utils.JavaFaker;

import java.util.Map;

import static io.restassured.RestAssured.*;

public class CategoriaController {
	private static Response response;
	private static final String BASE_URL = "http://localhost:3000";
	private static final String ENDPOINT_CATEGORIA = "/categorias";
	private String requestBody;

	public CategoriaController() {
		response = null;
	}

	public void prepararRequisicaoCategoria() throws Exception {
		// Gera dados únicos para evitar conflitos
		Map<String, String> dadosCategoria = JavaFaker.categoriaJavaFake();
		
		CategoriaRequest request = CategoriaRequest.builder()
				.nome(dadosCategoria.get("nome"))
				.descricao(dadosCategoria.get("descricao"))
				.build();

		requestBody = new ObjectMapper().writeValueAsString(request);
	}

	public void cadastrarNovaCategoria() {
		String token = TokenManager.getToken();
		
		response = given()
				.contentType(ContentType.JSON)
				.header("Authorization", "Bearer " + token)
				.baseUri(BASE_URL)
				.body(requestBody)
				.when()
				.post(ENDPOINT_CATEGORIA);

		String categoriaId = CategoriaManager.setCategoriaId(response.jsonPath().getString("id"));
		System.out.println("Categoria ID: " + categoriaId);
	}

	public void validarStatusCodeCategoria(int statusCode) throws InterruptedException {
		response.then()
				.statusCode(statusCode);
		System.out.println("Status Code: " + statusCode);
		Thread.sleep(5000);
	}

	public void listarCategorias() {
		String token = TokenManager.getToken();
		response = given()
				.contentType(ContentType.JSON)
				.header("Authorization", "Bearer " + token)
				.baseUri(BASE_URL)
				.when()
				.get(ENDPOINT_CATEGORIA);
	}

	public void buscarCategoriaPorId() {
		String token = TokenManager.getToken();
		String  categoriaId = CategoriaManager.getCategoriaId();
		response = given()
				.contentType(ContentType.JSON)
				.header("Authorization", "Bearer " + token)
				.baseUri(BASE_URL)
				.when()
				.get(ENDPOINT_CATEGORIA + "/" + categoriaId);
	}

	public void atualizarCategoriaPorId() {
		String token = TokenManager.getToken();
		String categoriaId = CategoriaManager.getCategoriaId();

		Map<String, String> novaCategoria = JavaFaker.categoriaJavaFake();

		response = given()
				.contentType(ContentType.JSON)
				.header("Authorization", "Bearer " + token)
				.baseUri(BASE_URL)
				.body(novaCategoria)
				.log().body()
				.when()
				.put(ENDPOINT_CATEGORIA + "/" + categoriaId);
	}

	public void excluirCategoriaPorId() {
		String token = TokenManager.getToken();
		String categoriaId = CategoriaManager.getCategoriaId();

		response = given()
				.contentType(ContentType.JSON)
				.header("Authorization", "Bearer " + token)
				.baseUri(BASE_URL)
				.when()
				.delete(ENDPOINT_CATEGORIA + "/" + categoriaId);
	}
}

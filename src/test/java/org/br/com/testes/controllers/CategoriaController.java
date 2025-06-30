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
	private Response response;
	private static final String BASE_URL = "http://localhost:3000";
	private static final String ENDPOINT_CATEGORIA = "/categorias";

	public CategoriaController() {
		response = null;
	}

	public void cadastrarNovaCategoria() {

		String token = TokenManager.getToken();
		
		Map<String, String> categoriaRequest = JavaFaker.categoriaJavaFake();

		response = given()
				.contentType(ContentType.JSON)
				.header("Authorization", "Bearer " + token)
				.baseUri(BASE_URL)
				.body(categoriaRequest)
				.when()
				.post(ENDPOINT_CATEGORIA);

		String categoriaId = response.jsonPath().getString("id");
		CategoriaManager.setCategoriaId(categoriaId);
		System.out.println("Categoria ID: " + categoriaId);
	}

	public void validarStatusCodeCategoria(int statusCode) {
		response.then()
				.statusCode(statusCode);
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
				.log().all()
				.when()
				.put(ENDPOINT_CATEGORIA + "/" + categoriaId);
	}

	public void excluirCategoriaPorId() {
		String token = TokenManager.getToken();
		String categoriaId = CategoriaManager.getCategoriaId();

		if (categoriaId == null || categoriaId.isEmpty()) {
			throw new RuntimeException("ID da categoria não encontrado. Certifique-se de que uma categoria foi criada antes da exclusão.");
		}

		System.out.println("Tentando excluir categoria com ID: " + categoriaId);
		System.out.println("Token utilizado: " + token);

		response = given()
				.header("accept", "*/*")
				.header("Authorization", "Bearer " + token)
				.baseUri(BASE_URL)
				.log().all()
				.when()
				.delete(ENDPOINT_CATEGORIA + "/" + categoriaId);
		
		System.out.println("Status Code retornado: " + response.getStatusCode());
		System.out.println("Response Body: " + response.getBody().asString());
	}
}

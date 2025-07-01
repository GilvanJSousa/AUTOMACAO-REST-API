package org.br.com.testes.controllers;


import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.br.com.testes.manager.CategoriaManager;
import org.br.com.testes.manager.TokenManager;
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
				.post(ENDPOINT_CATEGORIA)
				.then()
				.extract().response();

		// Extrair e salvar todos os dados da categoria
		String nome = response.jsonPath().getString("nome");
		String descricao = response.jsonPath().getString("descricao");
		String categoriaId = response.jsonPath().getString("id");
		
		// Salvar no CategoriaManager
		CategoriaManager.setNomeCategoria(nome);
		CategoriaManager.setDescricaoCategoria(descricao);
		CategoriaManager.setCategoriaId(categoriaId);
		
		System.out.println("Categoria ID: " + categoriaId);
		System.out.println("Nome: " + nome);
		System.out.println("Descrição: " + descricao);
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
				.get(ENDPOINT_CATEGORIA)
				.then()
				.log().all()
				.extract().response();
	}

	public void buscarCategoriaPorId() {
		String token = TokenManager.getToken();
		String categoriaId = CategoriaManager.getCategoriaId();
		
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
				.when()
				.put(ENDPOINT_CATEGORIA + "/" + categoriaId);
	}

	public void excluirCategoriaPorId() {
		String token = TokenManager.getToken();
		String categoriaId = CategoriaManager.getCategoriaId();

		response = given()
				.header("accept", "*/*")
				.header("Authorization", "Bearer " + token)
				.baseUri(BASE_URL)
				.when()
				.delete(ENDPOINT_CATEGORIA + "/" + categoriaId);
	}

	public void excluirCategoriaEmMassa(String id) {

		String token = TokenManager.getToken();

		response = given()
				.header("accept", "*/*")
				.header("Authorization", "Bearer " + token)
				.baseUri(BASE_URL)
				.when()
				.delete(ENDPOINT_CATEGORIA + "/" + id);

	}
}

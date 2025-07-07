package org.br.com.testes.controllers;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.br.com.testes.manager.ArtigosManager;
import org.br.com.testes.manager.CategoriaManager;
import org.br.com.testes.manager.TokenManager;
import org.br.com.testes.manager.UsuarioManager;
import org.br.com.testes.utils.JavaFaker;
import org.br.com.testes.utils.LogFormatter;

import java.util.Map;

import static io.restassured.RestAssured.*;

public class ArtigosController {
	private Response response;
	private static final String BASE_URL = "http://localhost:3000";
	private static final String ENDPOINT_ARTIGOS = "/artigos";

	public ArtigosController() {
		response = null;
	}

	public void cadastrarArtigo() {
		String token = TokenManager.getToken();
		String nomeCategoria = CategoriaManager.getNomeCategoria();
		String nomeAutor = UsuarioManager.getNomeUsuario();

		Map<String, String> artigoRequest = JavaFaker.artigosTesteFixo(nomeAutor, nomeCategoria);

		response = given()
				.contentType(ContentType.JSON)
				.header("accept", "application/json")
				.header("Authorization", "Bearer " + token)
				.baseUri(BASE_URL)
				.body(artigoRequest)
				.when()
				.post(ENDPOINT_ARTIGOS);
		

		String artigoId = response.jsonPath().getString("id");
		ArtigosManager.setArtigoId(artigoId);
		System.out.println("Artigo ID: " + artigoId);
		// Log do body da resposta formatado em JSON
		LogFormatter.logJson(response.asString());
	}

	public void validarStatusCodeArtigos(int statusCodeEsperado) {
		response.then()
				.statusCode(statusCodeEsperado);
		LogFormatter.logStepJson("Validação de Status Code ", String.valueOf(+ statusCodeEsperado));

	}

	public void listarArtigos() {
		String token = TokenManager.getToken();
		String categoriaId = ArtigosManager.getCategoriaId();
		String autorId = TokenManager.getUserId(); // Usar o ID do usuário logado como autorId

		// Construir query parameters conforme o curl
		String queryParams = "?page=1&limit=10";
		if (categoriaId != null && !categoriaId.isEmpty()) {
			queryParams += "&categoriaId=" + categoriaId;
		}
		if (autorId != null && !autorId.isEmpty()) {
			queryParams += "&autorId=" + autorId;
		}
		
		response = given()
				.contentType(ContentType.JSON)
				.header("accept", "application/json")
				.header("Authorization", "Bearer " + token)
				.baseUri(BASE_URL)
				.when()
				.get(ENDPOINT_ARTIGOS + queryParams)
				.then()
				.log().all()
				.extract().response();

		// Log do body da resposta formatado em JSON
		LogFormatter.logJson(response.asString());
	}

	public void buscarArtigoPorId() {
		String token = TokenManager.getToken();
		String artigoId = ArtigosManager.getArtigoId();
		
		response = given()
				.contentType(ContentType.JSON)
				.header("accept", "application/json")
				.header("Authorization", "Bearer " + token)
				.baseUri(BASE_URL)
				.when()
				.get(ENDPOINT_ARTIGOS + "/" + artigoId);

		// Log do body da resposta formatado em JSON
		LogFormatter.logJson(response.asString());
	}

	public void atualizarArtigoPorId() {
		String token = TokenManager.getToken();
		String artigoId = ArtigosManager.getArtigoId();

		Map<String, String> dadosAtualizacao = JavaFaker.dadosAtualizacaoArtigo();

		response = given()
				.contentType(ContentType.JSON)
				.header("accept", "*/*")
				.header("Authorization", "Bearer " + token)
				.baseUri(BASE_URL)
				.body(dadosAtualizacao)
				.when()
				.put(ENDPOINT_ARTIGOS + "/" + artigoId);

		// Log do body da resposta formatado em JSON
		LogFormatter.logJson(response.asString());
	}

	public void excluirArtigoPorId() {
		String token = TokenManager.getToken();
		String artigoId = ArtigosManager.getArtigoId();

		response = given()
				.contentType(ContentType.JSON)
				.header("accept", "*/*")
				.header("Authorization", "Bearer " + token)
				.baseUri(BASE_URL)
				.when()
				.delete(ENDPOINT_ARTIGOS + "/" + artigoId);

		// Log do body da resposta formatado em JSON
		LogFormatter.logJson(response.asString());
	}

	public void excluirArtigosEmMassa(String id) {
		String token = TokenManager.getToken();

		response = given()
				.contentType(ContentType.JSON)
				.header("accept", "*/*")
				.header("Authorization", "Bearer " + token)
				.baseUri(BASE_URL)
				.when()
				.delete(ENDPOINT_ARTIGOS + "/" + id);
		// Log do body da resposta formatado em JSON
		LogFormatter.logJson(response.asString());
	}

	public void validarStatusCodeExclusao(int statusCodeEsperado) {
		response.then().statusCode(statusCodeEsperado);
		LogFormatter.logStepJson("Validação de Status Code ", String.valueOf(+ statusCodeEsperado));
	}

	public void criarCategoriaAntesDoArtigo() {
		String token = TokenManager.getToken();

		Map<String, String> categoriaRequest = JavaFaker.categoriaJavaFake();
		
		response = given()
				.contentType(ContentType.JSON)
				.header("accept", "application/json")
				.header("Authorization", "Bearer " + token)
				.baseUri(BASE_URL)
				.body(categoriaRequest)
				.when()
				.post("/categorias");

		String categoriaId = response.jsonPath().getString("id");
		String nomeCategoria = categoriaRequest.get("nome");
		ArtigosManager.setCategoriaId(categoriaId);
		ArtigosManager.setNomeCategoria(nomeCategoria);
		System.out.println("Categoria ID: " + categoriaId);
		// Log do body da resposta formatado em JSON
		LogFormatter.logJson(response.asString());
	}

}

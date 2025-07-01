package org.br.com.testes.controllers;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.br.com.testes.manager.ArtigosManager;
import org.br.com.testes.manager.CategoriaManager;
import org.br.com.testes.manager.TokenManager;
import org.br.com.testes.manager.UsuarioManager;
import org.br.com.testes.utils.JavaFaker;

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
		
		// Validar se o token existe
		if (token == null || token.isEmpty()) {
			throw new RuntimeException("Token não encontrado. Certifique-se de que o login foi realizado com sucesso.");
		}
		
		// Usar dados dos managers que foram criados no Background
		String nomeCategoria = CategoriaManager.getNomeCategoria();
		String nomeAutor = UsuarioManager.getNomeUsuario();
		
		// Se não houver dados nos managers, usar fallbacks
		if (nomeCategoria == null || nomeCategoria.isEmpty()) {
			nomeCategoria = "Tecnologia";
		}
		
		if (nomeAutor == null || nomeAutor.isEmpty()) {
			nomeAutor = "Usuario";
		}
		
		// Gerar dados do artigo conforme o cURL fornecido
		Map<String, String> artigoRequest = JavaFaker.artigosTesteFixo(nomeAutor, nomeCategoria);
		
		// Log do payload para debug
		System.out.println("Payload enviado: " + artigoRequest);
		
		response = given()
				.contentType(ContentType.JSON)
				.header("accept", "application/json")
				.header("Authorization", "Bearer " + token)
				.baseUri(BASE_URL)
				.body(artigoRequest)
				.when()
				.post(ENDPOINT_ARTIGOS);
		
		// Log da resposta para debug
		System.out.println("Resposta da API: " + response.getBody().asString());
		
		if (response.getStatusCode() == 201) {
			String artigoId = response.jsonPath().getString("id");
			ArtigosManager.setArtigoId(artigoId);
			System.out.println("Artigo ID: " + artigoId);
		}
		
		ArtigosManager.setResponse(response);
	}

	public void validarStatusCodeArtigos(int statusCodeEsperado) {
		Response response = ArtigosManager.getResponse();
		if (response != null) {
			response.then().statusCode(statusCodeEsperado);
		} else {
			throw new RuntimeException("Nenhuma resposta encontrada para validação");
		}
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
		
		ArtigosManager.setResponse(response);
	}

	public void buscarArtigoPorId() {
		String token = TokenManager.getToken();
		String artigoId = ArtigosManager.getArtigoId();
		
		if (artigoId == null || artigoId.isEmpty()) {
			throw new RuntimeException("ID do artigo não encontrado. Certifique-se de que um artigo foi criado antes da busca.");
		}
		
		response = given()
				.contentType(ContentType.JSON)
				.header("accept", "application/json")
				.header("Authorization", "Bearer " + token)
				.baseUri(BASE_URL)
				.when()
				.get(ENDPOINT_ARTIGOS + "/" + artigoId);
		
		ArtigosManager.setResponse(response);
	}

	public void atualizarArtigoPorId() {
		String token = TokenManager.getToken();
		String artigoId = ArtigosManager.getArtigoId();
		
		if (artigoId == null || artigoId.isEmpty()) {
			throw new RuntimeException("ID do artigo não encontrado. Certifique-se de que um artigo foi criado antes da atualização.");
		}

		// Usar dados fixos para atualização conforme o curl
		Map<String, String> dadosAtualizacao = JavaFaker.dadosAtualizacaoArtigo();

		response = given()
				.contentType(ContentType.JSON)
				.header("accept", "*/*")
				.header("Authorization", "Bearer " + token)
				.baseUri(BASE_URL)
				.body(dadosAtualizacao)
				.when()
				.put(ENDPOINT_ARTIGOS + "/" + artigoId);
		
		ArtigosManager.setResponse(response);
	}

	public void excluirArtigoPorId() {
		String token = TokenManager.getToken();
		String artigoId = ArtigosManager.getArtigoId();

		if (artigoId == null || artigoId.isEmpty()) {
			throw new RuntimeException("ID do artigo não encontrado. Certifique-se de que um artigo foi criado antes da exclusão.");
		}

		response = given()
				.contentType(ContentType.JSON)
				.header("accept", "*/*")
				.header("Authorization", "Bearer " + token)
				.baseUri(BASE_URL)
				.when()
				.delete(ENDPOINT_ARTIGOS + "/" + artigoId);
		
		ArtigosManager.setResponse(response);
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
	}

	public void validarStatusCodeExclusao(int statusCodeEsperado) {
		Response response = ArtigosManager.getResponse();
		if (response != null) {
			response.then().statusCode(statusCodeEsperado);
		} else {
			throw new RuntimeException("Nenhuma resposta encontrada para validação da exclusão");
		}
	}

	public void criarCategoriaAntesDoArtigo() {
		String token = TokenManager.getToken();
		
		// Validar se o token existe
		if (token == null || token.isEmpty()) {
			throw new RuntimeException("Token não encontrado. Certifique-se de que o login foi realizado com sucesso.");
		}
		
		Map<String, String> categoriaRequest = JavaFaker.categoriaJavaFake();
		
		response = given()
				.contentType(ContentType.JSON)
				.header("accept", "application/json")
				.header("Authorization", "Bearer " + token)
				.baseUri(BASE_URL)
				.body(categoriaRequest)
				.when()
				.post("/categorias");
		
		if (response.getStatusCode() == 201) {
			String categoriaId = response.jsonPath().getString("id");
			String nomeCategoria = categoriaRequest.get("nome");
			ArtigosManager.setCategoriaId(categoriaId);
			ArtigosManager.setNomeCategoria(nomeCategoria);
			System.out.println("Categoria ID: " + categoriaId);
		}
	}

	public void prepararAutorParaArtigos() {
		String userId = TokenManager.getUserId();
		
		// Validar se o userId existe
		if (userId == null || userId.isEmpty()) {
			throw new RuntimeException("User ID não encontrado. Certifique-se de que o login foi realizado com sucesso.");
		}
		
		String nomeAutor = "Usuario"; // Usar nome literal conforme cURL da API
		
		ArtigosManager.setAutorId(userId);
		ArtigosManager.setNomeAutor(nomeAutor);
		System.out.println("Autor ID: " + userId);
	}
}

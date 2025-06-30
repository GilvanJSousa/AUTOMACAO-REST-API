package org.br.com.testes.controllers;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.br.com.testes.manager.ArtigosManager;
import org.br.com.testes.manager.TokenManager;
import org.br.com.testes.manager.UsuarioManager;
import org.br.com.testes.utils.JavaFaker;

import java.util.Map;

import static io.restassured.RestAssured.*;

public class ArtigosController {
	private static final String BASE_URL = "http://localhost:3000";
	private static final String ENDPOINT_ARTIGOS = "/artigos";

	public ArtigosController() {
	}

	public void cadastrarArtigo() {
		String token = TokenManager.getToken();
		
		// Validar se o token existe
		if (token == null || token.isEmpty()) {
			throw new RuntimeException("Token não encontrado. Certifique-se de que o login foi realizado com sucesso.");
		}
		
		// Usar dados dos managers que foram criados no Background
		String nomeCategoria = ArtigosManager.getNomeCategoria();
		
		// Se não houver dados nos managers, usar fallbacks
		if (nomeCategoria == null || nomeCategoria.isEmpty()) {
			nomeCategoria = "Tecnologia";
		}
		
		// Usar nome literal "Usuario" conforme o cURL de exemplo da API
		String nomeAutor = "Usuario";
		
		// Gerar dados do artigo com nome literal do autor
		Map<String, String> artigoRequest = JavaFaker.artigosTesteFixo(nomeAutor, nomeCategoria);
		
		Response response = given()
				.contentType(ContentType.JSON)
				.header("accept", "application/json")
				.header("Authorization", "Bearer " + token)
				.baseUri(BASE_URL)
				.body(artigoRequest)
				.when()
				.post(ENDPOINT_ARTIGOS);
		
		if (response.getStatusCode() == 201) {
			String artigoId = response.jsonPath().getString("id");
			ArtigosManager.setArtigoId(artigoId);
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
		
		Response response = given()
				.contentType(ContentType.JSON)
				.header("accept", "application/json")
				.header("Authorization", "Bearer " + token)
				.baseUri(BASE_URL)
				.when()
				.get(ENDPOINT_ARTIGOS + queryParams);
		
		ArtigosManager.setResponse(response);
	}

	public void buscarArtigoPorId() {
		String token = TokenManager.getToken();
		String artigoId = ArtigosManager.getArtigoId();
		
		if (artigoId == null || artigoId.isEmpty()) {
			throw new RuntimeException("ID do artigo não encontrado. Certifique-se de que um artigo foi criado antes da busca.");
		}
		
		Response response = given()
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

		Response response = given()
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

		Response response = given()
				.contentType(ContentType.JSON)
				.header("accept", "*/*")
				.header("Authorization", "Bearer " + token)
				.baseUri(BASE_URL)
				.when()
				.delete(ENDPOINT_ARTIGOS + "/" + artigoId);
		
		ArtigosManager.setResponse(response);
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
		
		Response response = given()
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
	}
}

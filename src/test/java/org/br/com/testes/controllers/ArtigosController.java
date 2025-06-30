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
		
		System.out.println("=== CADASTRO DE ARTIGO ===");
		System.out.println("Nome do autor (fixo): " + nomeAutor);
		
		// Gerar dados do artigo com nome literal do autor
		Map<String, String> artigoRequest = JavaFaker.artigosTesteFixo(nomeAutor, nomeCategoria);
		
		System.out.println("Tentando cadastrar artigo...");
		System.out.println("Endpoint: " + BASE_URL + ENDPOINT_ARTIGOS);
		System.out.println("Token: " + token);
		System.out.println("Nome do Autor: " + nomeAutor);
		System.out.println("Nome da Categoria: " + nomeCategoria);
		System.out.println("Body: " + artigoRequest);
		
		Response response = given()
				.contentType(ContentType.JSON)
				.header("accept", "application/json")
				.header("Authorization", "Bearer " + token)
				.baseUri(BASE_URL)
				.body(artigoRequest)
				.log().all()
				.when()
				.post(ENDPOINT_ARTIGOS);
		
		System.out.println("Status Code retornado: " + response.getStatusCode());
		System.out.println("Response Body: " + response.getBody().asString());
		
		if (response.getStatusCode() == 201) {
			String artigoId = response.jsonPath().getString("id");
			ArtigosManager.setArtigoId(artigoId);
			System.out.println("Artigo criado com sucesso. ID: " + artigoId);
		} else {
			System.out.println("Erro ao criar artigo. Status: " + response.getStatusCode());
			System.out.println("Response: " + response.getBody().asString());
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
				.log().all()
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

		System.out.println("Tentando excluir artigo com ID: " + artigoId);
		System.out.println("Token utilizado: " + token);

		Response response = given()
				.contentType(ContentType.JSON)
				.header("accept", "*/*")
				.header("Authorization", "Bearer " + token)
				.baseUri(BASE_URL)
				.log().all()
				.when()
				.delete(ENDPOINT_ARTIGOS + "/" + artigoId);
		
		System.out.println("Status Code da exclusão: " + response.getStatusCode());
		System.out.println("Response Body da exclusão: " + response.getBody().asString());
		
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
		
		System.out.println("=== CRIAÇÃO DE CATEGORIA ===");
		System.out.println("Criando categoria antes do artigo...");
		System.out.println("Token: " + token);
		System.out.println("Body: " + categoriaRequest);
		
		Response response = given()
				.contentType(ContentType.JSON)
				.header("accept", "application/json")
				.header("Authorization", "Bearer " + token)
				.baseUri(BASE_URL)
				.body(categoriaRequest)
				.log().all()
				.when()
				.post("/categorias");
		
		System.out.println("Categoria - Status Code: " + response.getStatusCode());
		System.out.println("Categoria - Response: " + response.getBody().asString());
		
		if (response.getStatusCode() == 201) {
			String categoriaId = response.jsonPath().getString("id");
			String nomeCategoria = categoriaRequest.get("nome");
			ArtigosManager.setCategoriaId(categoriaId);
			ArtigosManager.setNomeCategoria(nomeCategoria);
			System.out.println("Categoria criada com ID: " + categoriaId);
			System.out.println("Nome da categoria: " + nomeCategoria);
		} else {
			System.out.println("Erro ao criar categoria. Status: " + response.getStatusCode());
			System.out.println("Response: " + response.getBody().asString());
		}
	}

	public void prepararAutorParaArtigos() {
		String userId = TokenManager.getUserId();
		
		// Validar se o userId existe
		if (userId == null || userId.isEmpty()) {
			throw new RuntimeException("User ID não encontrado. Certifique-se de que o login foi realizado com sucesso.");
		}
		
		String nomeAutor = "Usuario"; // Usar nome literal conforme cURL da API
		
		System.out.println("=== PREPARAÇÃO DO AUTOR ===");
		System.out.println("Usando usuário logado como autor...");
		System.out.println("Autor ID (usuário logado): " + userId);
		System.out.println("Nome do autor: " + nomeAutor);
		
		ArtigosManager.setAutorId(userId);
		ArtigosManager.setNomeAutor(nomeAutor);
	}
}

package org.br.com.testes.controllers;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.br.com.testes.manager.ArtigosManager;
import org.br.com.testes.manager.TokenManager;
import org.br.com.testes.utils.JavaFaker;

import java.util.Map;

import static io.restassured.RestAssured.*;

public class ArtigosController {
	private static final String BASE_URL = "http://localhost:3000";
	private static final String ENDPOINT_ARTIGOS = "/artigos";

	public ArtigosController() {
	}

	public void testarConectividadeAPI() {
		System.out.println("Testando conectividade básica com a API...");
		
		Response testResponse = given()
				.baseUri(BASE_URL)
				.when()
				.get("/");
		
		System.out.println("GET / - Status Code: " + testResponse.getStatusCode());
		System.out.println("GET / - Response: " + testResponse.getBody().asString());
	}

	public void testarEndpointArtigos() {
		String token = TokenManager.getToken();
		
		System.out.println("Testando se o endpoint /artigos existe...");
		
		Response testResponse = given()
				.header("accept", "application/json")
				.header("Authorization", "Bearer " + token)
				.baseUri(BASE_URL)
				.when()
				.get(ENDPOINT_ARTIGOS);
		
		System.out.println("GET /artigos - Status Code: " + testResponse.getStatusCode());
		System.out.println("GET /artigos - Response: " + testResponse.getBody().asString());
	}

	public void cadastrarArtigo() {
		String token = TokenManager.getToken();
		String userId = TokenManager.getUserId();
		
		// Buscar o nome do usuário logado
		System.out.println("Buscando dados do usuário logado...");
		Response userResponse = given()
				.contentType(ContentType.JSON)
				.header("accept", "application/json")
				.header("Authorization", "Bearer " + token)
				.baseUri(BASE_URL)
				.when()
				.get("/usuarios/" + userId);
		
		String nomeUsuario = "Usuario"; // fallback
		if (userResponse.getStatusCode() == 200) {
			// Usar nomeUsuario como prioridade (funcionou nos testes)
			nomeUsuario = userResponse.jsonPath().getString("nomeUsuario");
			if (nomeUsuario == null || nomeUsuario.trim().isEmpty()) {
				nomeUsuario = "Usuario";
			}
			System.out.println("Nome do usuário logado: " + nomeUsuario);
		} else {
			System.out.println("Erro ao buscar usuário. Status: " + userResponse.getStatusCode());
		}
		
		// Buscar o nome da categoria criada
		String nomeCategoria = "Tecnologia"; // fallback
		String categoriaId = ArtigosManager.getCategoriaId();
		if (categoriaId != null && !categoriaId.equals("categoria-existente")) {
			Response categoriaResponse = given()
					.contentType(ContentType.JSON)
					.header("accept", "application/json")
					.header("Authorization", "Bearer " + token)
					.baseUri(BASE_URL)
					.when()
					.get("/categorias/" + categoriaId);
			
			if (categoriaResponse.getStatusCode() == 200) {
				nomeCategoria = categoriaResponse.jsonPath().getString("nome");
				System.out.println("Nome da categoria: " + nomeCategoria);
			}
		}
		
		// Gerar dados do artigo com nomes reais
		Map<String, String> artigoRequest = JavaFaker.artigosJavaFake(nomeUsuario, nomeCategoria);
		
		System.out.println("Tentando cadastrar artigo...");
		System.out.println("Endpoint: " + BASE_URL + "/artigos");
		System.out.println("Token: " + token);
		System.out.println("Body: " + artigoRequest);
		
		Response response = given()
				.contentType(ContentType.JSON)
				.header("accept", "application/json")
				.header("Authorization", "Bearer " + token)
				.baseUri(BASE_URL)
				.body(artigoRequest)
				.log().all()
				.when()
				.post("/artigos");
		
		System.out.println("Status Code retornado: " + response.getStatusCode());
		System.out.println("Response Body: " + response.getBody().asString());
		
		if (response.getStatusCode() == 201) {
			String artigoId = response.jsonPath().getString("id");
			ArtigosManager.setArtigoId(artigoId);
			System.out.println("Artigo criado com sucesso. ID: " + artigoId);
		} else {
			System.out.println("Erro ao criar artigo. Status: " + response.getStatusCode());
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
		Response response = given()
				.contentType(ContentType.JSON)
				.header("Authorization", "Bearer " + token)
				.baseUri(BASE_URL)
				.when()
				.get(ENDPOINT_ARTIGOS);
		
		ArtigosManager.setResponse(response);
	}

	public void buscarArtigoPorId() {
		String token = TokenManager.getToken();
		String artigoId = ArtigosManager.getArtigoId();
		Response response = given()
				.contentType(ContentType.JSON)
				.header("Authorization", "Bearer " + token)
				.baseUri(BASE_URL)
				.when()
				.get(ENDPOINT_ARTIGOS + "/" + artigoId);
		
		ArtigosManager.setResponse(response);
	}

	public void atualizarArtigoPorId() {
		String token = TokenManager.getToken();
		String artigoId = ArtigosManager.getArtigoId();

		// Buscar dados atuais do artigo para manter consistência
		String nomeUsuario = "Usuario"; // fallback
		String nomeCategoria = "Tecnologia"; // fallback
		
		// Buscar nome do usuário logado
		String userId = TokenManager.getUserId();
		if (userId != null) {
			Response userResponse = given()
					.contentType(ContentType.JSON)
					.header("accept", "application/json")
					.header("Authorization", "Bearer " + token)
					.baseUri(BASE_URL)
					.when()
					.get("/usuarios/" + userId);
			
			if (userResponse.getStatusCode() == 200) {
				nomeUsuario = userResponse.jsonPath().getString("nomeUsuario");
				if (nomeUsuario == null || nomeUsuario.trim().isEmpty()) {
					nomeUsuario = "Usuario";
				}
			}
		}
		
		// Buscar nome da categoria
		String categoriaId = ArtigosManager.getCategoriaId();
		if (categoriaId != null && !categoriaId.equals("categoria-existente")) {
			Response categoriaResponse = given()
					.contentType(ContentType.JSON)
					.header("accept", "application/json")
					.header("Authorization", "Bearer " + token)
					.baseUri(BASE_URL)
					.when()
					.get("/categorias/" + categoriaId);
			
			if (categoriaResponse.getStatusCode() == 200) {
				nomeCategoria = categoriaResponse.jsonPath().getString("nome");
			}
		}

		Map<String, String> novoArtigo = JavaFaker.artigosJavaFake(nomeUsuario, nomeCategoria);

		Response response = given()
				.contentType(ContentType.JSON)
				.header("Authorization", "Bearer " + token)
				.baseUri(BASE_URL)
				.body(novoArtigo)
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
				.header("accept", "*/*")
				.header("Authorization", "Bearer " + token)
				.baseUri(BASE_URL)
				.log().all()
				.when()
				.delete(ENDPOINT_ARTIGOS + "/" + artigoId);
		
		System.out.println("Status Code retornado: " + response.getStatusCode());
		System.out.println("Response Body: " + response.getBody().asString());
		
		ArtigosManager.setResponse(response);
	}

	public void criarAutorAntesDoArtigo() {
		String token = TokenManager.getToken();
		
		// Como não existe endpoint /autores, vamos usar o usuário logado como autor
		System.out.println("Usando usuário logado como autor...");
		
		// O autorId será o ID do usuário logado
		String autorId = org.br.com.testes.manager.UsuarioManager.getIdUsuario();
		ArtigosManager.setAutorId(autorId);
		System.out.println("Autor ID (usuário logado): " + autorId);
	}

	public void criarCategoriaAntesDoArtigo() {
		String token = TokenManager.getToken();
		
		// Criar categoria com nome único
		Map<String, String> categoriaRequest = Map.of(
			"nome", "Tecnologia_" + System.currentTimeMillis(),
			"descricao", "Artigos sobre tecnologia"
		);
		
		System.out.println("Criando categoria antes do artigo...");
		
		Response categoriaResponse = given()
				.contentType(ContentType.JSON)
				.header("accept", "application/json")
				.header("Authorization", "Bearer " + token)
				.baseUri(BASE_URL)
				.body(categoriaRequest)
				.log().all()
				.when()
				.post("/categorias");
		
		System.out.println("Categoria - Status Code: " + categoriaResponse.getStatusCode());
		System.out.println("Categoria - Response: " + categoriaResponse.getBody().asString());
		
		// Tratar erro de JSON
		if (categoriaResponse.getStatusCode() == 201) {
			String categoriaId = categoriaResponse.jsonPath().getString("id");
			ArtigosManager.setCategoriaId(categoriaId);
			System.out.println("Categoria criada com ID: " + categoriaId);
		} else {
			System.out.println("Erro ao criar categoria. Status: " + categoriaResponse.getStatusCode());
			// Se não conseguir criar, usar uma categoria existente
			ArtigosManager.setCategoriaId("categoria-existente");
		}
	}
}

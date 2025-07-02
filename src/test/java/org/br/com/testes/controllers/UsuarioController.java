package org.br.com.testes.controllers;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.br.com.testes.manager.TokenManager;
import org.br.com.testes.manager.UsuarioManager;
import org.br.com.testes.model.LoginRequest;
import org.br.com.testes.model.UsuarioRequest;
import org.br.com.testes.utils.FakerApiData;
import org.br.com.testes.utils.JavaFaker;
import org.br.com.testes.utils.LogFormatter;

import static io.restassured.RestAssured.*;

import java.util.Map;

public class UsuarioController {
	private Response response;
	private static final String BASE_URL = "http://localhost:3000";
	private static final String ENDPOINT_USUARIOS = "/usuarios";
	private static final String ENDPOINT_LOGIN = "/auth/login";

	public UsuarioController() {
		response = null;
	}

	public void cadastrarNovoUsuario() {
		UsuarioRequest usuarioGerado = FakerApiData.gerarUsuarioRequestSimples();

		response = given()
				.contentType(ContentType.JSON)
				.baseUri(BASE_URL)
				.body(usuarioGerado)
				.log().body()
				.when()
				.post(ENDPOINT_USUARIOS);


		UsuarioManager.setEmailUsuario(usuarioGerado.getEmail());
		UsuarioManager.setSenhaUsuario(usuarioGerado.getSenha());
		UsuarioManager.setNomeCompletoUsuario(usuarioGerado.getNomeCompleto());
		UsuarioManager.setNomeUsuario(usuarioGerado.getNomeUsuario());
		String userId = response.jsonPath().getString("id");
		UsuarioManager.setIdUsuario(userId);
		// Log do body da resposta formatado em JSON
		LogFormatter.logJson(response.asString());
		System.out.println("Usuario ID: " + userId);



	}

	public void realizarLogin() {
		String email = UsuarioManager.getEmailUsuario();
		String senha = UsuarioManager.getSenhaUsuario();
		
		LoginRequest loginRequest = LoginRequest.builder()
				.email(email)
				.senha(senha)
				.build();

		response = given()
				.contentType(ContentType.JSON)
				.baseUri(BASE_URL)
				.body(loginRequest)
				.log().body()
				.when()
				.post(ENDPOINT_LOGIN);

		String token = response.jsonPath().getString("token");
		String userId = response.jsonPath().getString("user.id");
		TokenManager.setToken(token);
		TokenManager.setUserId(userId);
		UsuarioManager.setIdUsuario(userId);
		System.out.println("Token: " + token);
		// Log do body da resposta formatado em JSON
		LogFormatter.logJson(response.asString());
	}

	public void listarUsuariosComAutenticacao() {
		String token = TokenManager.getToken();
		this.response = given()
				.contentType(ContentType.JSON)
				.header("Authorization", "Bearer " + token)
				.baseUri(BASE_URL)
				.when()
				.get(ENDPOINT_USUARIOS)
				.then()
				.log().all()
				.extract().response();
	}

	public void consultarUsuarioPorId() {
		String token = TokenManager.getToken();
		String userId = UsuarioManager.getIdUsuario();
		this.response = given()
				.contentType(ContentType.JSON)
				.header("Authorization", "Bearer " + token)
				.baseUri(BASE_URL)
				.when()
				.get(ENDPOINT_USUARIOS + "/" + userId);
		// Log do body da resposta formatado em JSON
		LogFormatter.logJson(response.asString());
	}

	public void atualizarUsuarioPorId() {
		String token = TokenManager.getToken();
		String userId = UsuarioManager.getIdUsuario();

		Map<String, String> novosDados = JavaFaker.DadosAtualizacaoJavaFake();

		this.response = given()
				.contentType(ContentType.JSON)
				.header("Authorization", "Bearer " + token)
				.baseUri(BASE_URL)
				.body(novosDados)
				.when()
				.put(ENDPOINT_USUARIOS + "/" + userId);
		// Log do body da resposta formatado em JSON
		LogFormatter.logJson(response.asString());
	}

	public void excluirUsuarioPorId() {
		String token = TokenManager.getToken();
		String idUsuario = UsuarioManager.getIdUsuario();
		this.response = given()
				.contentType(ContentType.JSON)
				.header("Authorization", "Bearer " + token)
				.baseUri(BASE_URL)
				.when()
				.delete(ENDPOINT_USUARIOS + "/" + idUsuario);
		// Log do body da resposta formatado em JSON
		LogFormatter.logJson(response.asString());
	}

	public void validarStatusCode(int statusCode) {
		response.then()
				.statusCode(statusCode);
		LogFormatter.logStepJson("Validação de Status Code ", String.valueOf(+ statusCode));
	}

	public void exclusaoDeMassas(String email, String senha) {
		LoginRequest loginRequest = LoginRequest.builder()
				.email(email)
				.senha(senha)
				.build();

		this.response = given()
				.contentType(ContentType.JSON)
				.baseUri(BASE_URL)
				.body(loginRequest)
				.when()
				.post(ENDPOINT_LOGIN);

		String token = response.jsonPath().getString("token");
		TokenManager.setToken(token);
		System.out.println("Token: " + token);
		// Log do body da resposta formatado em JSON
		LogFormatter.logJson(response.asString());
	}

	public void massasParaExcluir(String idUsuario) {
		String token = TokenManager.getToken();

		this.response = given()
				.contentType(ContentType.JSON)
				.header("Authorization", "Bearer " + token)
				.baseUri(BASE_URL)
				.when()
				.delete(ENDPOINT_USUARIOS + "/" + idUsuario);
		// Log do body da resposta formatado em JSON
		LogFormatter.logJson(response.asString());

	}
}
package org.br.com.testes.controllers;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.br.com.testes.manager.TokenManager;
import org.br.com.testes.manager.UsuarioManager;
import org.br.com.testes.model.UsuarioRequest;
import org.br.com.testes.utils.FakerApiData;
import org.br.com.testes.utils.JavaFaker;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

import java.util.List;
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
		this.response = given()
				.contentType(ContentType.JSON)
				.baseUri(BASE_URL)
				.body(usuarioGerado)
				.when()
				.post(ENDPOINT_USUARIOS);

		UsuarioManager.setEmailUsuario(usuarioGerado.getEmail());
		UsuarioManager.setSenhaUsuario(usuarioGerado.getSenha());
		UsuarioManager.setIdUsuario(response.jsonPath().getString("id"));

	}

	public void realizarLogin() {
		String email = UsuarioManager.getEmailUsuario();
		String senha = UsuarioManager.getSenhaUsuario();
		UsuarioRequest usuarioRequest = UsuarioRequest.builder()
				.email(email)
				.senha(senha)
				.build();

		this.response = given()
				.contentType(ContentType.JSON)
				.baseUri(BASE_URL)
				.body(usuarioRequest)
				.when()
				.post(ENDPOINT_LOGIN);

		String token = response.jsonPath().getString("token");
		TokenManager.setToken(token);
		System.out.println("Token: " + TokenManager.getToken());
		UsuarioManager.setIdUsuario(response.jsonPath().getString("user.id"));
		System.out.println("ID do usuário: " + UsuarioManager.getIdUsuario());
	}

	public void listarUsuariosComAutenticacao() {
		String token = TokenManager.getToken();
		this.response = given()
				.contentType(ContentType.JSON)
				.header("Authorization", "Bearer " + token)
				.baseUri(BASE_URL)
				.when()
				.get(ENDPOINT_USUARIOS);
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
	}

	public void validarStatusCode(int statusCode) {
		response.then()
				.statusCode(statusCode);
	}

	public void validarNomeUsuario(){
		List<Map<String, Object>> usuarios = this.response.jsonPath().getList("$");
		boolean usuarioEncontrado = usuarios.stream()
				.anyMatch(usuario -> usuario.get("email").equals(UsuarioManager.getEmailUsuario()));

		assertTrue("Usuário não consta na lista de cadastrados",
				usuarioEncontrado);
	}

}
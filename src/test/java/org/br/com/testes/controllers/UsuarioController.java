package org.br.com.testes.controllers;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.br.com.testes.manager.TokenManager;
import org.br.com.testes.manager.UsuarioManager;
import org.br.com.testes.model.UsuarioRequest;
import org.br.com.testes.utils.FakerApiData;


import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
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
		UsuarioRequest usuarioGerado = FakerApiData.gerarUsuarioRequest();
		this.response = given()
				.contentType(ContentType.JSON)
				.baseUri(BASE_URL)
				.body(usuarioGerado)
				.log().body()
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

		FakerApiData fakeData = new FakerApiData();
		String novoNomeCompleto = fakeData.getFullName();
		String novoNomeUsuario = fakeData.getFirstName();

		Map<String, String> corpoRequisicao = new HashMap<>();
		corpoRequisicao.put("nomeCompleto", novoNomeCompleto);
		corpoRequisicao.put("nomeUsuario", novoNomeUsuario);

		this.response = given()
				.contentType(ContentType.JSON)
				.header("Authorization", "Bearer " + token)
				.baseUri(BASE_URL)
				.body(corpoRequisicao)
				.log().all()
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
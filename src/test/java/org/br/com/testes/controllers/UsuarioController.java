package org.br.com.testes.controllers;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.br.com.testes.manager.TokenManager;
import org.br.com.testes.manager.UsuarioManager;
import org.br.com.testes.model.LoginRequest;
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
		
		// Retry logic para garantir que o usuário seja criado
		int maxTentativas = 3;
		int tentativa = 1;
		String userId = null;
		
		while (tentativa <= maxTentativas && userId == null) {
	//		System.out.println("Tentativa " + tentativa + " de " + maxTentativas + " para cadastrar usuário");
			
			this.response = given()
					.contentType(ContentType.JSON)
					.baseUri(BASE_URL)
					.body(usuarioGerado)
					.when()
					.post(ENDPOINT_USUARIOS);

			// Verificar se o cadastro foi bem-sucedido
			if (response.getStatusCode() == 201) {
				userId = response.jsonPath().getString("id");

				if (userId != null && !userId.isEmpty()) {
//					System.out.println("Usuário cadastrado com sucesso na tentativa " + tentativa);
					break;
				} else {
//					System.out.println("User ID null na tentativa " + tentativa + ". Tentando novamente...");
				}
			} else {
				System.out.println("Cadastro falhou na tentativa " + tentativa + ". Status: " + response.getStatusCode() +
					" - Response: " + response.getBody().asString());
			}

			// Aguardar antes da próxima tentativa (exceto na última)
			if (tentativa < maxTentativas) {
				try {
					Thread.sleep(2000); // 2 segundos de espera
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
			
			tentativa++;
		}
		
		// Validar se conseguiu obter userId após todas as tentativas
		if (userId == null || userId.isEmpty()) {
			throw new RuntimeException("Usuário não foi cadastrado após " + maxTentativas + " tentativas. Response: " + 
				response.getBody().asString());
		}

		// Salvar dados no manager
		UsuarioManager.setEmailUsuario(usuarioGerado.getEmail());
		UsuarioManager.setSenhaUsuario(usuarioGerado.getSenha());
		UsuarioManager.setNomeCompletoUsuario(usuarioGerado.getNomeCompleto());
		UsuarioManager.setNomeUsuario(usuarioGerado.getNomeUsuario());
		UsuarioManager.setIdUsuario(userId);
		System.out.println("Usuario ID: " + userId);
	}

	public void realizarLogin() {
		String email = UsuarioManager.getEmailUsuario();
		String senha = UsuarioManager.getSenhaUsuario();
		
		LoginRequest loginRequest = LoginRequest.builder()
				.email("usuario_1751371948726@email.com")
				.senha(":;rtt0aAL\"")
				.build();

		// Retry logic para garantir que o token seja gerado
		String token = null;
		String userId = null;
		int maxTentativas = 3;
		int tentativa = 1;
		
		while (tentativa <= maxTentativas && (token == null || userId == null)) {
//			System.out.println("Tentativa " + tentativa + " de " + maxTentativas + " para realizar login");
			
			this.response = given()
					.contentType(ContentType.JSON)
					.baseUri(BASE_URL)
					.body(loginRequest)
					.log().body()
					.when()
					.post(ENDPOINT_LOGIN);

			// Verificar se o login foi bem-sucedido
			if (response.getStatusCode() == 200) {
				token = response.jsonPath().getString("token");
				userId = response.jsonPath().getString("user.id");
				
				if (token != null && userId != null) {
//					System.out.println("Login realizado com sucesso na tentativa " + tentativa);
					break;
				} else {
//					System.out.println("Token ou User ID null na tentativa " + tentativa + ". Tentando novamente...");
				}
			} else {
				System.out.println("Login falhou na tentativa " + tentativa + ". Status: " + response.getStatusCode() + 
					" - Response: " + response.getBody().asString());
			}
			
			// Aguardar antes da próxima tentativa (exceto na última)
			if (tentativa < maxTentativas) {
				try {
					Thread.sleep(2000); // 2 segundos de espera
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
			
			tentativa++;
		}
		
		// Validar se conseguiu obter token e userId após todas as tentativas
		if (token == null || token.isEmpty()) {
			throw new RuntimeException("Token não foi gerado após " + maxTentativas + " tentativas. Response: " + 
				response.getBody().asString());
		}
		
		if (userId == null || userId.isEmpty()) {
			throw new RuntimeException("User ID não foi gerado após " + maxTentativas + " tentativas. Response: " + 
				response.getBody().asString());
		}
		
		TokenManager.setToken(token);
		TokenManager.setUserId(userId);
		UsuarioManager.setIdUsuario(userId);
		System.out.println("Token: " + token);
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

		// Validação do status code do login
		if (response.getStatusCode() != 200) {
			System.out.println("Falha no login. Status: " + response.getStatusCode());
			System.out.println("Response: " + response.getBody().asString());
			throw new RuntimeException("Login falhou para o email: " + email);
		}

		String token = response.jsonPath().getString("token");
		if (token == null || token.isEmpty()) {
			throw new RuntimeException("Token não foi retornado no login para o email: " + email);
		}
		TokenManager.setToken(token);
		System.out.println("Token: " + token);
	}

	public void massasParaExcluir(String idUsuario) {
		String token = TokenManager.getToken();

		this.response = given()
				.contentType(ContentType.JSON)
				.header("Authorization", "Bearer " + token)
				.baseUri(BASE_URL)
				.when()
				.delete(ENDPOINT_USUARIOS + "/" + idUsuario);

	}
}
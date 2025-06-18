package org.br.com.testes.controllers.usuarios;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.br.com.testes.manager.TokenManager;
import org.br.com.testes.manager.UsuarioManager;
import org.br.com.testes.model.usuario.UsuarioResquest;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.br.com.testes.manager.UsuarioManager.*;
import static org.br.com.testes.utils.FakerApiData.*;
import static org.junit.Assert.assertTrue;

public class UsuarioController {

    private Response response;
    private static final String BASE_URL = "http://localhost:3000";
    private static final String ENDPOINT_USUARIOS = "/usuarios";
    private static final String ENDPOINT_LOGIN = "/login";

    public UsuarioController() {
        response = null;
    }

    // CADASTRAR USUARIOS
    public void cadastrarNovoUsuario() {
//        FakerApiData usuarioGerado = gerarUsuarioFake();
        UsuarioResquest usuarioRequest = UsuarioResquest.builder()
                .nome(gerarUsuarioFake().getNome())
                .email(gerarUsuarioFake().getEmail())
                .password(gerarUsuarioFake().getSenha())
                .administrador(gerarUsuarioFake().getAdministrador())
                .build();

        response = given()
                .contentType(ContentType.JSON)
                .body(usuarioRequest)
                .when()
                .post(BASE_URL + ENDPOINT_USUARIOS)
                .then()
                .extract().response();

        setEmail(usuarioRequest.getEmail());
        setPassword(usuarioRequest.getPassword());
        String id = setId(response.jsonPath().getString("_id"));

        System.out.println("Email do usuario cadastrado: " + usuarioRequest.getEmail());
        System.out.println("Senha do usuário cadastrada: " + usuarioRequest.getPassword());
        System.out.println("ID do usuário cadastrado: " + id);
    }

    public void realizarLogin() {
        String email = UsuarioManager.getEmail();
        String senha = UsuarioManager.setPassword();

        // Criar objeto apenas com email e password
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("email", email);
        loginRequest.put("password", senha);

        this.response = given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .body(loginRequest)
                .log().body()
                .when()
                .post(ENDPOINT_LOGIN)
                .then()
                .extract()
                .response();

        // Extrair o token do corpo da resposta no campo 'authorization'
        String token = TokenManager.setToken(response.jsonPath().getString("authorization"));
        System.out.println("Usuário logado com sucesso: " + email);
        System.out.println("Token gerado: " + token);
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
                .extract()
                .response();
    }

    public void consultarUsuarioPorId() {
        String token = TokenManager.getToken();
        String idUsuario = UsuarioManager.getId();

        this.response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .baseUri(BASE_URL)
                .when()
                .get(ENDPOINT_USUARIOS + "/" + idUsuario)
                .then()
                .extract()
                .response();
    }

    public void atualizarUsuarioPorId() {
        String token = TokenManager.getToken();
        String id = UsuarioManager.getId();
        String email = gerarUsuarioFake().getEmail();
        String senha = gerarUsuarioFake().getSenha();

        Map<String, String> editarUsuario = new HashMap<>();
        editarUsuario.put("email", email);
        editarUsuario.put("password", senha);

        this.response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .baseUri(BASE_URL)
                .body(editarUsuario)
                .when()
                .put(ENDPOINT_USUARIOS + "/" + id);

        System.out.println("Usuário atualizado com sucesso: " + email);
        System.out.println("Senha do usuário atualizada: " + senha);
    }

    public void excluirUsuarioPorId() {
        String token = TokenManager.getToken();
        String idUsuario = UsuarioManager.getId();

        this.response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .baseUri(BASE_URL)
                .when()
                .delete(ENDPOINT_USUARIOS + "/" + idUsuario)
                .then()
                .extract()
                .response();

        System.out.println("Usuário com ID " + idUsuario + " excluído com sucesso.");
    }

    public void validarStatusCode(int statusCode) {
        this.response.then()
                        .extract().response();
        System.out.println("Status Code: " + statusCode);
    }

    public void cadastrorUsuarioComEmailJaCadastrado() {
        UsuarioResquest usuarioRequest = UsuarioResquest.builder()
                .nome(gerarUsuarioFake().getNome())
                .email(UsuarioManager.getEmail())
                .password(gerarUsuarioFake().getSenha())
                .administrador(gerarUsuarioFake().getAdministrador())
                .build();

        this.response = given()
                .contentType(ContentType.JSON)
                .body(usuarioRequest)
                .log().body()
                .when()
                .post(BASE_URL + ENDPOINT_USUARIOS)
                .then()
                .extract().response();

        assertTrue("O email já está cadastrado.", response.jsonPath().getString("message").contains("Este email já está sendo usado"));
    }

    public void validarStatusCodeEMensagem(int statusCode, String mensagem) {
        this.response.then()
                .statusCode(statusCode)
                .extract().response();
        
        String mensagemResposta = response.jsonPath().getString("message");
        assertTrue("A mensagem da resposta não contém o texto esperado. Resposta: " + mensagemResposta, 
                mensagemResposta.toLowerCase().contains(mensagem.toLowerCase()));
    }

}
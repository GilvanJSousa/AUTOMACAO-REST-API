package org.br.com.testes.controllers.usuarios;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.br.com.testes.manager.TokenManager;
import org.br.com.testes.manager.UsuarioManager;
import org.br.com.testes.model.UsuarioResponse;
import org.br.com.testes.utils.FakerApiData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UsuarioController {
    private Response response;
    private static final String BASE_URL = "http://localhost:3000";
    private static final String ENDPOINT_USUARIOS = "/usuarios";
    private static final String ENDPOINT_LOGIN = "/auth/login";

    public UsuarioController() {
        response = null;
    }

    public void cadastrarNovoUsuario() {
        UsuarioResponse usuarioGerado = FakerApiData.gerarUsuarioFake();
        this.response = given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .body(usuarioGerado)
                .log().all()
                .when()
                .post(ENDPOINT_USUARIOS)
                .then()
                .extract()
                .response();

        UsuarioManager.setEmailUsuario(usuarioGerado.email());
        UsuarioManager.setSenhaUsuario(usuarioGerado.senha());
        UsuarioManager.setIdUsuario(response.jsonPath().getString("id"));
    }

    public void realizarLogin() {
        String email = UsuarioManager.getEmailUsuario();
        String senha = UsuarioManager.getSenhaUsuario();
        this.response = given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .body("" +
                        "{\"email\": \"" + email + "\"," +
                        " \"senha\": \"" + senha + "\"}"
                )
                .log().all()
                .when()
                .post(ENDPOINT_LOGIN)
                .then()
                .extract()
                .response();

        if (response.getStatusCode() == 200) {
            String token = response.jsonPath().getString("token");
            TokenManager.setToken(token);
        }
    }

    public void listarUsuariosComAutenticacao() {
        String token = TokenManager.getToken();
        this.response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .baseUri(BASE_URL)
                .log().all()
                .when()
                .get(ENDPOINT_USUARIOS)
                .then()
                .extract()
                .response();
    }

    public void consultarUsuarioPorId() {
        String token = TokenManager.getToken();
        String idUsuario = UsuarioManager.getIdUsuario();
        this.response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .baseUri(BASE_URL)
                .log().all()
                .when()
                .get(ENDPOINT_USUARIOS + "/" + idUsuario)
                .then()
                .extract()
                .response();
    }

    public void atualizarUsuarioPorId(String id, String nomeCompleto, String nomeUsuario) {
        String token = TokenManager.getToken();
        this.response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .baseUri(BASE_URL)
                .body(new HashMap<String, String>() {{
                    put("nomeCompleto", nomeCompleto);
                    put("nomeUsuario", nomeUsuario);
                }})
                .when()
                .put(ENDPOINT_USUARIOS + "/" + id);
    }

    public void excluirUsuarioPorId() {
        String token = TokenManager.getToken();
        String idUsuario = UsuarioManager.getIdUsuario();
        this.response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .baseUri(BASE_URL)
                .log().all()
                .when()
                .delete(ENDPOINT_USUARIOS + "/" + idUsuario)
                .then()
                .extract()
                .response();
    }

    public void validarStatusCode(int statusCode) {
        this.response.then()
                .log().body();
        assertEquals("StatusCode deve ser: " + statusCode, this.response.getStatusCode(), statusCode);
    }

    public void validarNomeUsuario(){
        List<Map<String, Object>> usuarios = this.response.jsonPath().getList("$");
        boolean usuarioEncontrado = usuarios.stream()
                .anyMatch(usuario -> usuario.get("email").equals(UsuarioManager.getEmailUsuario()));

        assertTrue("Usuário não consta na lista de cadastrados",
                usuarioEncontrado);
    }

    public void atualizarNomeUsuario() {
        String token = TokenManager.getToken();
        String idUsuario = UsuarioManager.getIdUsuario();
        UsuarioResponse usuarioGerado = FakerApiData.gerarUsuarioFake();
        
        this.response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .baseUri(BASE_URL)
                .body(new HashMap<String, String>() {{
                    put("nomeUsuario", usuarioGerado.nomeUsuario());
                }})
                .log().all()
                .when()
                .put(ENDPOINT_USUARIOS + "/" + idUsuario)
                .then()
                .extract()
                .response();
    }
}
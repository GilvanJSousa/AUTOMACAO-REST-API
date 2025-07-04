package org.br.com.testes.controllers.usuarios;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.br.com.testes.manager.UsuarioManager;
import org.br.com.testes.model.Usuario;
import org.br.com.testes.utils.FakerApiData;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class usuarioController {
    private static final Log log = LogFactory.getLog(usuarioController.class);
    private Response response;
    private static final String BASE_URL = "http://localhost:3000";
    private static final String ENDPOINT_USUARIOS = "/usuarios";
    private static final String ENDPOINT_LOGIN = "/login";

    public usuarioController() {
        response = null;
    }

    @Test
    public void cadastrarNovoUsuario() {
        Usuario usuarioFake = FakerApiData.gerarUsuarioFake();
        this.response = given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .body(usuarioFake)
                .when()
                .log().all(true)
                .post(ENDPOINT_USUARIOS);

        UsuarioManager.setEmailUsuario(usuarioFake.getEmail());
        UsuarioManager.setSenhaUsuario(usuarioFake.getSenha());
        UsuarioManager.setIdUsuario(response.jsonPath().getString("_id"));
    }

    public void validarStatusCode(int statusCode) {
        Assert.assertEquals("StatusCode deve ser: " + statusCode, this.response.getStatusCode(), statusCode);
    }
}

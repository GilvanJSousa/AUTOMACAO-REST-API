package org.br.com.testes.controllers.produtos;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.br.com.testes.manager.ProdutosManager;
import org.br.com.testes.manager.TokenManager;
import org.br.com.testes.model.produtos.ProdutosResquest;
import org.br.com.testes.utils.FakerApiData;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.br.com.testes.manager.ProdutosManager.*;
import static org.junit.Assert.assertTrue;

public class ProdutosController {
    private Response response;
    private static final String BASE_URL = "http://localhost:3000";
    private static final String ENDPOINT_PRODUTOS = "/produtos";

    public ProdutosController() {
        response = null;
    }

    public void cadastrarNovoProduto() {
        FakerApiData fakerData = FakerApiData.gerarProdutoFake();
        ProdutosResquest produtoRequest = ProdutosResquest.builder()
                .nome(fakerData.getNome())
                .preco(fakerData.getPreco())
                .descricao(fakerData.getDescricao())
                .quantidade(fakerData.getQuantidade())
                .build();

        String token = TokenManager.getToken();
        response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .baseUri(BASE_URL)
                .body(produtoRequest)
                .when()
                .post(ENDPOINT_PRODUTOS)
                .then()
                .extract().response();

        String id = setId(response.jsonPath().getString("_id"));
        System.out.println("Produto cadastrado com sucesso. ID: " + id);
    }

    public void buscarProdutoPorId() {
        String token = TokenManager.getToken();
        String idProduto = getId();
        
        response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .baseUri(BASE_URL)
                .when()
                .get(ENDPOINT_PRODUTOS + "/" + idProduto)
                .then()
                .extract().response();
    }

    public void listarProdutos() {
        String token = TokenManager.getToken();
        response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .baseUri(BASE_URL)
                .when()
                .get(ENDPOINT_PRODUTOS)
                .then()
                .extract().response();
    }

    public void editarProduto() {
        String token = TokenManager.getToken();
        String idProduto = getId();
        
        FakerApiData fakerData = FakerApiData.gerarProdutoFake();
        Map<String, Object> editarProduto = new HashMap<>();
        editarProduto.put("nome", fakerData.getNome());
        editarProduto.put("preco", fakerData.getPreco());
        editarProduto.put("descricao", fakerData.getDescricao());
        editarProduto.put("quantidade", fakerData.getQuantidade());

        response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .baseUri(BASE_URL)
                .body(editarProduto)
                .when()
                .put(ENDPOINT_PRODUTOS + "/" + idProduto)
                .then()
                .extract().response();

        System.out.println("Produto atualizado com sucesso. ID: " + idProduto);
    }

    public void excluirProduto() {
        String token = TokenManager.getToken();
        String idProduto = getId();
        
        response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .baseUri(BASE_URL)
                .when()
                .delete(ENDPOINT_PRODUTOS + "/" + idProduto)
                .then()
                .extract().response();

        System.out.println("Produto com ID " + idProduto + " excluído com sucesso.");
        remove();
    }

    public void validarStatusCodeEMensagem(int statusCode, String mensagem) {
        this.response.then()
                .statusCode(statusCode)
                .extract().response();
        
        String mensagemResposta = response.jsonPath().getString("message");
        assertTrue("A mensagem da resposta não contém o texto esperado. Resposta: " + mensagemResposta, 
                mensagemResposta.toLowerCase().contains(mensagem.toLowerCase()));
    }

    public void validarStatusCode(int statusCode) {
        this.response.then()
                .statusCode(statusCode)
                .extract().response();
        System.out.println("Status Code: " + statusCode);
    }
}

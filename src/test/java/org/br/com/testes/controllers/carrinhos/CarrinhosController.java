package org.br.com.testes.controllers.carrinhos;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.br.com.testes.manager.TokenManager;
import org.br.com.testes.model.carrinhos.CarrinhosRequest;
import org.br.com.testes.model.produtos.ProdutosResquest;
import org.br.com.testes.utils.FakerApiData;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.br.com.testes.manager.CarrinhosManager.*;
import static org.junit.Assert.assertTrue;

public class CarrinhosController {
    private Response response;
    private static final String BASE_URL = "http://localhost:3000";
    private static final String ENDPOINT_CARRINHOS = "/carrinhos";

    public CarrinhosController() {
        response = null;
    }

    public void cadastrarNovoCarrinho() {
        // Gerar dados fake para o produto
        FakerApiData fakerData = FakerApiData.gerarProdutoFake();
        String token = TokenManager.getToken();

        // Montar o request do produto corretamente
        ProdutosResquest produtoRequest = ProdutosResquest.builder()
                .nome(fakerData.getNome())
                .preco(fakerData.getPreco())
                .descricao(fakerData.getDescricao())
                .quantidade(fakerData.getQuantidade())
                .build();

        // Cadastrar o produto e obter o ID
        Response produtoResponse = given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .baseUri(BASE_URL)
                .body(produtoRequest)
                .when()
                .post("/produtos")
                .then()
                .statusCode(201)
                .extract().response();

        String idProduto = produtoResponse.jsonPath().getString("_id");
        System.out.println("ID do produto cadastrado: " + idProduto);

        // Cria o carrinho com o produto cadastrado
        List<CarrinhosRequest.ProdutoCarrinho> produtos = new ArrayList<>();
        produtos.add(CarrinhosRequest.ProdutoCarrinho.builder()
                .idProduto(idProduto)
                .quantidade(1)
                .build());

        CarrinhosRequest carrinhoRequest = CarrinhosRequest.builder()
                .produtos(produtos)
                .build();

        // Log do payload para debug
        System.out.println("Payload do carrinho: " + carrinhoRequest);

        response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .baseUri(BASE_URL)
                .body(carrinhoRequest)
                .when()
                .post(ENDPOINT_CARRINHOS)
                .then()
                .extract().response();

        // Log da resposta para debug
        System.out.println("Resposta do cadastro: " + response.getBody().asString());

        String id = setId(response.jsonPath().getString("_id"));
        System.out.println("Carrinho cadastrado com sucesso. ID: " + id);
    }

    public void buscarCarrinhoPorId() {
        String token = TokenManager.getToken();
        String idCarrinho = getId();
        
        response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .baseUri(BASE_URL)
                .when()
                .get(ENDPOINT_CARRINHOS + "/" + idCarrinho)
                .then()
                .extract().response();
    }

    public void listarCarrinhos() {
        String token = TokenManager.getToken();
        response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .baseUri(BASE_URL)
                .when()
                .get(ENDPOINT_CARRINHOS)
                .then()
                .extract().response();
    }

    public void concluirCompra() {
        String token = TokenManager.getToken();
        response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .baseUri(BASE_URL)
                .when()
                .delete(ENDPOINT_CARRINHOS + "/concluir-compra")
                .then()
                .extract().response();

        System.out.println("Compra concluída com sucesso.");
        remove();
    }

    public void cancelarCompra() {
        String token = TokenManager.getToken();
        response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .baseUri(BASE_URL)
                .when()
                .delete(ENDPOINT_CARRINHOS + "/cancelar-compra")
                .then()
                .extract().response();

        System.out.println("Compra cancelada com sucesso e estoque reabastecido.");
        remove();
    }

    public void editarCarrinho() {
        String token = TokenManager.getToken();
        String idCarrinho = getId();
        
        // Primeiro cadastra um novo produto para adicionar ao carrinho
        FakerApiData fakerData = FakerApiData.gerarProdutoFake();
        
        response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .baseUri(BASE_URL)
                .body(fakerData)
                .when()
                .post("/produtos")
                .then()
                .extract().response();

        String idProduto = response.jsonPath().getString("_id");
        
        // Cria a lista de produtos atualizada
        List<CarrinhosRequest.ProdutoCarrinho> produtos = new ArrayList<>();
        produtos.add(CarrinhosRequest.ProdutoCarrinho.builder()
                .idProduto(idProduto)
                .quantidade(2) // Quantidade diferente para simular edição
                .build());

        CarrinhosRequest carrinhoRequest = CarrinhosRequest.builder()
                .produtos(produtos)
                .build();

        response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .baseUri(BASE_URL)
                .body(carrinhoRequest)
                .when()
                .put(ENDPOINT_CARRINHOS + "/" + idCarrinho)
                .then()
                .extract().response();

        System.out.println("Carrinho atualizado com sucesso. ID: " + idCarrinho);
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
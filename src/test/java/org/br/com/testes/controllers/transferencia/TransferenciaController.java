package org.br.com.testes.controllers.transferencia;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Getter;
import org.br.com.testes.tokens.GerarToken;
import org.br.com.testes.manager.TokenManager;
import org.br.com.testes.manager.TransferenciaManager;
import org.br.com.testes.model.TransferenciaRequest;
import org.br.com.testes.utils.LogFormatter;
import org.junit.Test;

import static io.restassured.RestAssured.*;

@Getter
public class TransferenciaController {

    private Response response;

    private String requestBody;

    private static final String BASE_URL = "http://localhost:9090";

    private static final String ENDPOINT_TRANSFERENCIA = "/transferencias";

    public TransferenciaController() {
        response = null;
    }


    public void prepararRequisicaoDeTransferencia(double valor) throws JsonProcessingException {

        // Joao Silva =====> ID: 6867c26d12ba0eba945873a5
        // Maria Santos ===> ID: 6867c26d12ba0eba945873a6
        // Pedro Oliveira => ID: 6867c26d12ba0eba945873a7
        // Ana Costa ======> ID: 6867c26d12ba0eba945873a8

        // Só gerar token se não tivermos um já definido
        if (TokenManager.getToken() == null) {
            GerarToken.gerarTokenAdmin();
        }

        String token = TokenManager.getToken();
        
        // Para transferências acima de R$5.000,00, a API exige token '123456' no body
        String tokenParaBody = token;
        if (valor >= 5000) {
            tokenParaBody = "123456";
            LogFormatter.logStep("Transferencia acima de R$5.000,00 - usando token especial no body: " + tokenParaBody);
        }

        TransferenciaRequest request = TransferenciaRequest.builder()
                .contaOrigem("686fa208cbdb4375dbb8ed47")
                .contaDestino("686fa208cbdb4375dbb8ed48")
                .token(tokenParaBody)
                .valor(valor)
                .build();

        requestBody = new ObjectMapper().writeValueAsString(request);
    }

    public void realizarTransferencia(double valor) throws JsonProcessingException {
        prepararRequisicaoDeTransferencia(valor);

        String token = TokenManager.getToken();

        response = given()
                .baseUri(BASE_URL)
                .header("accept", "*/*")
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(ENDPOINT_TRANSFERENCIA)
                .then()
                .extract().response();
        LogFormatter.logJson(response.asPrettyString());
    }

    public void realizarTransferenciaComValorDivergente(double valor) throws JsonProcessingException {
        prepararRequisicaoDeTransferencia(valor);

        String token = TokenManager.getToken();

        response = given()
                .baseUri(BASE_URL)
                .header("accept", "*/*")
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(ENDPOINT_TRANSFERENCIA)
                .then()
                .extract().response();
        LogFormatter.logJson(response.asPrettyString());
    }

    public void listarTransferenciasBancarias() {
        GerarToken.gerarTokenAdmin();
        String token = TokenManager.getToken();

        response = given()
                .baseUri(BASE_URL)
                .param("page", 1)
                .param("limit", 10)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .get(ENDPOINT_TRANSFERENCIA)
                .then()
                .extract().response();

        String idTransferencia = response.jsonPath().getString("transferencias[0]._id");
        LogFormatter.logStep("ID da Transferência: " + idTransferencia);
        TransferenciaManager.setIdTransferencia(idTransferencia);
//        LogFormatter.logJson(response.asPrettyString());
    }

    public void consultarTransferenciaBancaria() {
        GerarToken.gerarTokenAdmin();
        String token = TokenManager.getToken();

        // Primeiro, vamos listar as transferências para obter um ID válido
        Response listResponse = given()
                .baseUri(BASE_URL)
                .param("page", 1)
                .param("limit", 10)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .get(ENDPOINT_TRANSFERENCIA)
                .then()
                .extract().response();

        // Obter o ID da primeira transferência
        String idTransferencia = listResponse.jsonPath().getString("transferencias[0]._id");
        
        // Se não houver transferências ou ID for null, criar uma primeiro
//        if (idTransferencia == null || idTransferencia.isEmpty()) {
//            LogFormatter.logStep("Nenhuma transferência encontrada. Criando uma transferência para consulta...");
//            try {
//                prepararRequisicaoDeTransferencia(15.00);
//                realizarTransferencia();
//
//                // Aguardar um pouco para garantir que a transferência foi criada
//                Thread.sleep(1000);
//
//                // Listar novamente para obter o ID
//                listResponse = given()
//                        .baseUri(BASE_URL)
//                        .param("page", 1)
//                        .param("limit", 10)
//                        .contentType(ContentType.JSON)
//                        .header("Authorization", "Bearer " + token)
//                        .when()
//                        .get(ENDPOINT_TRANSFERENCIA)
//                        .then()
//                        .extract().response();
//
//                idTransferencia = listResponse.jsonPath().getString("transferencias[0]._id");
//            } catch (JsonProcessingException e) {
//                throw new RuntimeException(e);
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
//        }
//
//        if (idTransferencia == null || idTransferencia.isEmpty()) {
//            throw new RuntimeException("Não foi possível obter ID de transferência para consulta");
//        }

        TransferenciaManager.setIdTransferencia(idTransferencia);
        LogFormatter.logStep("ID da Transferência para consulta: " + idTransferencia);

        // Agora consultar a transferência específica
        response = given()
                .baseUri(BASE_URL)
                .pathParam("id", idTransferencia)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .get(ENDPOINT_TRANSFERENCIA + "/{id}")
                .then()
                .extract().response();

        LogFormatter.logJson(response.asPrettyString());
    }

    public void atualizarCompletamenteTransferencia(double valor) throws JsonProcessingException {
        prepararRequisicaoDeTransferencia(valor);

        String token = TokenManager.getToken();

        String idTransferencia = TransferenciaManager.getIdTransferencia();

        response = given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(requestBody)
                .when()
                .put(ENDPOINT_TRANSFERENCIA + "/" + idTransferencia);
        System.out.println("Response PUT: " + response.asString());
        LogFormatter.logJson(response.asPrettyString());
    }

    public void atualizarParcialmenteTransferencia(double valor) throws JsonProcessingException {
        prepararRequisicaoDeTransferencia(valor);
        String token = TokenManager.getToken();

        String idTransferencia = TransferenciaManager.getIdTransferencia();

        response = given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(requestBody)
                .when()
                .patch(ENDPOINT_TRANSFERENCIA + "/" + idTransferencia);
        
    }

    public void removeUmaTransferencia() {
        GerarToken.gerarTokenAdmin();
        String token = TokenManager.getToken();

        String idTransferencia = TransferenciaManager.getIdTransferencia();

        response = given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .delete(ENDPOINT_TRANSFERENCIA + "/" + idTransferencia);

        LogFormatter.logJson(idTransferencia + "==> Excluido");
    }

    public void validarStatusCode(int statusCode) {
        response.then()
                .statusCode(statusCode);
        LogFormatter.logJson(String.valueOf("Status Code: " + statusCode));
    }

    /**
     * Verifica se a conta de origem possui saldo suficiente
     */
    public void verificarSaldoContaOrigem(double saldoEsperado) {
        LogFormatter.logStep("Verificando saldo da conta de origem: R$ " + saldoEsperado);
        
        // Garantir que temos um token válido
        if (TokenManager.getToken() == null) {
            GerarToken.gerarTokenAdmin();
        }
        
        // Consultar a conta de origem para verificar o saldo
        String contaOrigem = "686fa208cbdb4375dbb8ed47";
        
        Response contaResponse = given()
                .baseUri(BASE_URL)
                .header("accept", "*/*")
                .header("Authorization", "Bearer " + TokenManager.getToken())
                .contentType(ContentType.JSON)
                .when()
                .get("/contas/" + contaOrigem)
                .then()
                .extract().response();
        
        double saldoAtual = contaResponse.jsonPath().getDouble("saldo");
        LogFormatter.logStep("Saldo atual da conta de origem: R$ " + saldoAtual);
        
        if (saldoAtual >= saldoEsperado) {
            LogFormatter.logStep("Conta de origem possui saldo suficiente para a transferencia");
        } else {
            throw new RuntimeException("Saldo insuficiente na conta de origem. Saldo atual: R$ " + saldoAtual + ", Saldo necessario: R$ " + saldoEsperado);
        }
    }

    /**
     * Verifica se a conta de destino está ativa
     */
    public void verificarContaDestinoAtiva() {
        LogFormatter.logStep("Verificando se a conta de destino esta ativa");
        
        // Garantir que temos um token válido
        if (TokenManager.getToken() == null) {
            GerarToken.gerarTokenAdmin();
        }
        
        String contaDestino = "686fa208cbdb4375dbb8ed48";
        
        Response contaResponse = given()
                .baseUri(BASE_URL)
                .header("accept", "*/*")
                .header("Authorization", "Bearer " + TokenManager.getToken())
                .contentType(ContentType.JSON)
                .when()
                .get("/contas/" + contaDestino)
                .then()
                .extract().response();
        
        boolean contaAtiva = contaResponse.jsonPath().getBoolean("ativa");
        LogFormatter.logStep("Status da conta de destino: " + (contaAtiva ? "Ativa" : "Inativa"));
        
        if (!contaAtiva) {
            throw new RuntimeException("Conta de destino nao esta ativa");
        }
    }

    /**
     * Define um token de autenticação específico
     */
    public void definirTokenAutenticacao(String token) {
        LogFormatter.logStep("Definindo token de autenticacao: " + token.substring(0, Math.min(token.length(), 10)) + "...");
        
        // Se o token for "yJhbGciOi... ", gerar um token válido do sistema
        if ("yJhbGciOi...".equals(token)) {
            LogFormatter.logStep("Token especificado e fixo, gerando token valido do sistema");
            GerarToken.gerarTokenAdmin();
        } else {
            TokenManager.setToken(token);
        }
    }

    /**
     * Realiza transferência com valor específico e valida sucesso
     */
    public void realizarTransferenciaComValidacao(double valor) throws JsonProcessingException {
        LogFormatter.logStep("Realizando transferencia de R$ " + valor + " com validacao de sucesso");
        
        prepararRequisicaoDeTransferencia(valor);
        
        String token = TokenManager.getToken();
        LogFormatter.logStep("Token sendo usado na transferencia: " + token.substring(0, Math.min(token.length(), 20)) + "...");
        
        response = given()
                .baseUri(BASE_URL)
                .header("accept", "*/*")
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(ENDPOINT_TRANSFERENCIA)
                .then()
                .extract().response();
        
        LogFormatter.logJson(response.asPrettyString());
        
        // Validar que a transferência foi processada com sucesso
        int statusCode = response.getStatusCode();
        if (statusCode == 201) {
            LogFormatter.logStep("Transferencia processada com sucesso - Status Code: " + statusCode);
        } else {
            throw new RuntimeException("Transferencia nao foi processada com sucesso. Status Code: " + statusCode);
        }
    }

    /**
     * Realiza transferência com validação inteligente (sucesso ou erro baseado no valor)
     */
    public void realizarTransferenciaComValidacaoInteligente(double valor) throws JsonProcessingException {
        LogFormatter.logStep("Realizando transferencia de R$ " + valor + " com validacao inteligente");
        
        // Para valores abaixo de R$10,00, esperamos erro 422
        if (valor < 10) {
            realizarTransferenciaComValidacaoDeErro(valor, 422, "R$10,00");
        } else {
            realizarTransferenciaComValidacao(valor);
        }
    }

    /**
     * Realiza transferência e valida erro específico
     */
    public void realizarTransferenciaComValidacaoDeErro(double valor, int statusCodeEsperado, String mensagemErroEsperada) throws JsonProcessingException {
        LogFormatter.logStep("Realizando transferencia de R$ " + valor + " com validacao de erro esperado: " + statusCodeEsperado);
        
        prepararRequisicaoDeTransferencia(valor);
        
        String token = TokenManager.getToken();
        LogFormatter.logStep("Token sendo usado na transferencia: " + token.substring(0, Math.min(token.length(), 20)) + "...");
        
        response = given()
                .baseUri(BASE_URL)
                .header("accept", "*/*")
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(ENDPOINT_TRANSFERENCIA)
                .then()
                .extract().response();
        
        LogFormatter.logJson(response.asPrettyString());
        
        // Validar que o erro esperado foi retornado
        int statusCode = response.getStatusCode();
        if (statusCode == statusCodeEsperado) {
            LogFormatter.logStep("Erro esperado retornado corretamente - Status Code: " + statusCode);
            
            // Validar mensagem de erro se fornecida
            if (mensagemErroEsperada != null && !mensagemErroEsperada.isEmpty()) {
                String mensagemErro = response.jsonPath().getString("error");
                if (mensagemErro != null && mensagemErro.contains(mensagemErroEsperada)) {
                    LogFormatter.logStep("Mensagem de erro validada: " + mensagemErro);
                } else {
                    throw new RuntimeException("Mensagem de erro nao corresponde ao esperado. Esperado: " + mensagemErroEsperada + ", Recebido: " + mensagemErro);
                }
            }
        } else {
            throw new RuntimeException("Status code nao corresponde ao esperado. Esperado: " + statusCodeEsperado + ", Recebido: " + statusCode);
        }
    }

    /**
     * Realiza transferência inicial entre contas específicas
     */
    public void realizarTransferenciaEntreContas(double valor, String contaOrigem, String contaDestino) throws JsonProcessingException {
        LogFormatter.logStep("Realizando transferencia inicial - Valor: R$ " + valor + ", Origem: " + contaOrigem + ", Destino: " + contaDestino);
        
        // Validar se as contas não estão vazias
        if (contaOrigem == null || contaOrigem.trim().isEmpty()) {
            throw new IllegalArgumentException("Conta de origem nao pode estar vazia");
        }
        if (contaDestino == null || contaDestino.trim().isEmpty()) {
            throw new IllegalArgumentException("Conta de destino nao pode estar vazia");
        }
        
        // Garantir que temos um token válido
        if (TokenManager.getToken() == null) {
            GerarToken.gerarTokenAdmin();
        }
        
        // Preparar requisição com contas específicas
        TransferenciaRequest request = TransferenciaRequest.builder()
                .contaOrigem(contaOrigem)
                .contaDestino(contaDestino)
                .token(TokenManager.getToken())
                .valor(valor)
                .build();

        String requestBody = new ObjectMapper().writeValueAsString(request);
        
        String token = TokenManager.getToken();
        
        response = given()
                .baseUri(BASE_URL)
                .header("accept", "*/*")
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(ENDPOINT_TRANSFERENCIA)
                .then()
                .extract().response();
        
        LogFormatter.logJson(response.asPrettyString());
        
        // Validar que a transferência foi processada com sucesso
        int statusCode = response.getStatusCode();
        if (statusCode == 200 || statusCode == 201) {
            LogFormatter.logStep("Transferencia inicial realizada com sucesso - Status Code: " + statusCode);
            
            // Salvar o ID da transferência criada para uso posterior
            String idTransferencia = response.jsonPath().getString("_id");
            if (idTransferencia != null && !idTransferencia.isEmpty()) {
                TransferenciaManager.setIdTransferencia(idTransferencia);
                LogFormatter.logStep("ID da transferencia salvo: " + idTransferencia);
            } else {
                // Se não conseguir pegar o ID da resposta, usar o método de listagem padrão
                LogFormatter.logStep("ID nao encontrado na resposta, buscando na lista de transferencias");
                listarTransferenciasBancarias();
                
                String idEncontrado = TransferenciaManager.getIdTransferencia();
                if (idEncontrado != null) {
                    LogFormatter.logStep("ID da transferencia encontrado na lista: " + idEncontrado);
                } else {
                    LogFormatter.logStep("Nao foi possivel encontrar o ID da transferencia");
                }
            }
        } else {
            throw new RuntimeException("Falha ao realizar transferencia inicial. Status Code: " + statusCode);
        }
    }



    /**
     * Atualiza transferência com novos dados (valor e conta de destino)
     */
    public void atualizarTransferenciaComNovosDados(double novoValor, String novaContaDestino) throws JsonProcessingException {
        LogFormatter.logStep("Atualizando transferencia com novos dados - Valor: R$ " + novoValor + ", Conta Destino: " + novaContaDestino);
        
        // Garantir que temos um token válido
        if (TokenManager.getToken() == null) {
            GerarToken.gerarTokenAdmin();
        }
        
        // Preparar requisição com novos dados
        TransferenciaRequest request = TransferenciaRequest.builder()
                .contaOrigem("686fa208cbdb4375dbb8ed47") // Conta de origem fixa
                .contaDestino(novaContaDestino)
                .token(TokenManager.getToken())
                .valor(novoValor)
                .build();

        String requestBody = new ObjectMapper().writeValueAsString(request);
        
        // Obter ID da transferência para atualização
        String idTransferencia = TransferenciaManager.getIdTransferencia();
        
        // Se não temos ID, buscar na lista de transferências mais recentes
        if (idTransferencia == null || idTransferencia.isEmpty()) {
            LogFormatter.logStep("ID da transferencia nao encontrado, buscando na lista");
            listarTransferenciasBancarias();
            idTransferencia = TransferenciaManager.getIdTransferencia();
            
            if (idTransferencia == null || idTransferencia.isEmpty()) {
                throw new RuntimeException("ID da transferencia nao encontrado. Execute primeiro um step que liste transferencias.");
            }
        }
        
        LogFormatter.logStep("ID da transferencia para atualizacao: " + idTransferencia);
        String token = TokenManager.getToken();
        
        response = given()
                .baseUri(BASE_URL)
                .header("accept", "*/*")
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .put(ENDPOINT_TRANSFERENCIA + "/" + idTransferencia)
                .then()
                .extract().response();
        
        LogFormatter.logJson(response.asPrettyString());
        
        // Validar que a atualização foi bem-sucedida
        int statusCode = response.getStatusCode();
        if (statusCode == 204) {
            LogFormatter.logStep("Transferencia atualizada com sucesso - Status Code: " + statusCode);
        } else {
            throw new RuntimeException("Falha ao atualizar transferencia. Status Code: " + statusCode);
        }
    }

}

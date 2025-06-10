package org.br.com.testes.controllers.consultaCapturaCancelamento;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.br.com.testes.manager.UsuarioManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ConsultaTransacaoController {
    private static final Logger logger = LoggerFactory.getLogger(ConsultaTransacaoController.class);
    private static final String BASE_URL = "https://apiquerysandbox.cieloecommerce.cielo.com.br";
    private static final String ENDPOINT_SALES = "/1/sales";
    private static final String MERCHANT_ID = "1dbf6ac5-0bb2-4fdb-a6a2-663f6e9554c3";
    private static final String MERCHANT_KEY = "DPECNPURVQHOKMIPZLWREWERXXKVRWXYUCRKGOBA";
    private Response response;

    public void consultarTransacao() {
        logger.info("Iniciando consulta de transação para PaymentId: {}", UsuarioManager.getPaymentId());
        response = given()
                .contentType(ContentType.JSON)
                .header("MerchantId", MERCHANT_ID)
                .header("MerchantKey", MERCHANT_KEY)
                .log().all(true)
                .baseUri(BASE_URL)
                .when()
                .get(ENDPOINT_SALES + "/" + UsuarioManager.getPaymentId());
        logger.info("Consulta de transação realizada com sucesso");
    }

    public void validarConsultaTransacao() {
        logger.info("Validando resposta da consulta de transação");
        response.then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("Payment.Status", equalTo(1))
                .body("Payment.Amount", equalTo(Integer.parseInt(UsuarioManager.getAmount())));
        logger.info("Validação da consulta realizada com sucesso");
    }

    public void validarConsultaParcial() {
        logger.info("Validando resposta da consulta parcial");
        response.then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("Payment.Status", equalTo(1))
                .body("Payment.Amount", equalTo(Integer.parseInt(UsuarioManager.getAmount())))
                .body("Payment.IsSplitted", equalTo(true));
        logger.info("Validação da consulta parcial realizada com sucesso");
    }

    public void validarStatusCode(int statusCode) {
        logger.info("Validando status code: {}", statusCode);
        response.then()
                .log().all(true)
                .statusCode(statusCode);
        logger.info("Status code validado com sucesso");
    }
} 
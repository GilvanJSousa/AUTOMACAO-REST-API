package org.br.com.testes.controllers.recorrencia;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.br.com.testes.manager.UsuarioManager;
import org.br.com.testes.model.recorrencia.RecorrenciaRequest;
import org.br.com.testes.model.recorrencia.RecorrenciaRequest.*;
import org.br.com.testes.utils.LogFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class RecorrenciaController {
    private static final Logger logger = LoggerFactory.getLogger(RecorrenciaController.class);
    private static final String BASE_URL = "https://apisandbox.cieloecommerce.cielo.com.br";
    private static final String BASE_URL_QUERY = "https://apiquerysandbox.cieloecommerce.cielo.com.br";
    private static final String ENDPOINT_RECURRENT = "/1/sales";
    private static final String ENDPOINT_QUERY_RECURRENT = "/1/RecurrentPayment";
    private static final String MERCHANT_ID = "1dbf6ac5-0bb2-4fdb-a6a2-663f6e9554c3";
    private static final String MERCHANT_KEY = "DPECNPURVQHOKMIPZLWREWERXXKVRWXYUCRKGOBA";
    private String requestBody;
    private Response response;

    public void prepararRequisicaoRecorrencia() throws Exception {
        RecorrenciaRequest request = RecorrenciaRequest.builder()
                .merchantOrderId("559042024")
                .customer(Customer.builder()
                        .name("Fulano da Silva")
                        .build())
                .payment(Payment.builder()
                        .installments(1)
                        .recurrentPayment(RecurrentPayment.builder()
                                .authorizeNow(true)
                                .endDate("2017-02-27")
                                .interval("Bimonthly")
                                .build())
                        .creditCard(CreditCard.builder()
                                .cardNumber("4091688625337641")
                                .holder("Teste Holder")
                                .expirationDate("12/2031")
                                .securityCode("333")
                                .saveCard(true)
                                .brand("Visa")
                                .build())
                        .softDescriptor(".Net Tes")
                        .type("CreditCard")
                        .amount(15000)
                        .currency("BRL")
                        .country("BRA")
                        .build())
                .build();

        requestBody = new ObjectMapper().writeValueAsString(request);
    }

    public void enviarRequisicaoRecorrencia() {
        response = given()
                .contentType(ContentType.JSON)
                .header("MerchantId", MERCHANT_ID)
                .header("MerchantKey", MERCHANT_KEY)
                .baseUri(BASE_URL)
                .body(requestBody)
                .when()
                .post(ENDPOINT_RECURRENT);

        UsuarioManager.setPaymentId(response.jsonPath().getString("Payment.PaymentId"));
        UsuarioManager.setAmount(response.jsonPath().getString("Payment.Amount"));
        UsuarioManager.setMerchantOrderId(response.jsonPath().getString("MerchantOrderId"));
        UsuarioManager.setRecurrentPaymentId(response.jsonPath().getString("Payment.RecurrentPayment.RecurrentPaymentId"));

        System.out.println("PaymentId: " + UsuarioManager.getPaymentId());
        System.out.println("Amount: " + UsuarioManager.getAmount());
        System.out.println("MerchantOrderId: " + UsuarioManager.getMerchantOrderId());
        System.out.println("RecurrentPaymentId: " + UsuarioManager.getRecurrentPaymentId());
    }

    public void validarRecorrencia() {
        response.then()
                .body("Payment.Status", equalTo(1))
                .body("Payment.ReturnCode", equalTo("4"))
                .body("Payment.ReturnMessage", equalTo("Operation Successful"));
    }

    public void validarStatusCode(int statusCode) {
        response.then()
                .statusCode(statusCode);
    }

    public void consultarRecorrencia() {
        String recurrentPaymentId = UsuarioManager.getRecurrentPaymentId();
        response = given()
                .contentType(ContentType.JSON)
                .header("MerchantId", MERCHANT_ID)
                .header("MerchantKey", MERCHANT_KEY)
                .baseUri(BASE_URL_QUERY)
                .when()
                .get(ENDPOINT_QUERY_RECURRENT + "/" + recurrentPaymentId);

        System.out.println("Consultando recorrência ID: " + recurrentPaymentId);
    }

    public void validarConsultaRecorrencia() {
        response.then()
                .statusCode(200);
    }
}

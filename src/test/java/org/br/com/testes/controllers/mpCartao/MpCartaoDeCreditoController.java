package org.br.com.testes.controllers.mpCartao;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.br.com.testes.manager.CartaoDeCreditoManager;
import org.br.com.testes.model.mpCartao.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.br.com.testes.model.mpCartao.MpCartaoDeCreditoRequest.*;

public class MpCartaoDeCreditoController {
    private static final String BASE_URL = "https://apisandbox.cieloecommerce.cielo.com.br";
    private static final String ENDPOINT_SALES = "/1/sales";
    private static final String MERCHANT_ID = "1dbf6ac5-0bb2-4fdb-a6a2-663f6e9554c3";
    private static final String MERCHANT_KEY = "DPECNPURVQHOKMIPZLWREWERXXKVRWXYUCRKGOBA";
    private String requestBody;
    private Response response;


    public void prepararRequisicaoCartaoCredito() throws Exception {
        MpCartaoDeCreditoRequest request = MpCartaoDeCreditoRequest.builder()
                .merchantOrderId("2014111703")
                .payment(Payment.builder()
                        .type("CreditCard")
                        .amount(15700)
                        .installments(1)
                        .softDescriptor("123456789ABCD")
                        .creditCard(CreditCard.builder()
                                .cardNumber("4551870000000183")
                                .holder("Teste Holder")
                                .expirationDate("12/2021")
                                .securityCode("123")
                                .brand("Visa")
                                .build())
                        .build())
                .build();

        requestBody = new ObjectMapper().writeValueAsString(request);
    }

    public void prepararRequisicaoCartaoCreditoAutenticado() throws Exception {
        MpCartaoDeCreditoRequest request = MpCartaoDeCreditoRequest.builder()
                .merchantOrderId("2014111903")
                .customer(Customer.builder()
                        .name("Comprador Teste")
                        .build())
                .payment(Payment.builder()
                        .type("CreditCard")
                        .amount(15700)
                        .provider("Cielo")
                        .returnUrl("https://www.google.com.br")
                        .installments(1)
                        .authenticate(true)
                        .creditCard(CreditCard.builder()
                                .cardNumber("1234123412341231")
                                .holder("Teste Holder")
                                .expirationDate("12/2018")
                                .securityCode("123")
                                .brand("Visa")
                                .build())
                        .build())
                .build();

        requestBody = new ObjectMapper().writeValueAsString(request);
    }

    public void prepararRequisicaoCartaoCreditoCompleto() throws Exception {
        MpCartaoDeCreditoRequest request = MpCartaoDeCreditoRequest.builder()
                .merchantOrderId("2014111701")
                .customer(Customer.builder()
                        .name("Comprador Teste")
                        .identity("11225468954")
                        .identityType("CPF")
                        .email("compradorteste@teste.com")
                        .birthdate("1991-01-02")
                        .address(Address.builder()
                                .street("Rua Teste")
                                .number("123")
                                .complement("AP 123")
                                .zipCode("12345987")
                                .city("Rio de Janeiro")
                                .state("RJ")
                                .country("BRA")
                                .build())
                        .deliveryAddress(Address.builder()
                                .street("Rua Teste")
                                .number("123")
                                .complement("AP 123")
                                .zipCode("12345987")
                                .city("Rio de Janeiro")
                                .state("RJ")
                                .country("BRA")
                                .build())
                        .build())
                .payment(Payment.builder()
                        .type("CreditCard")
                        .amount(15700)
                        .currency("BRL")
                        .country("BRA")
                        .provider("Simulado")
                        .serviceTaxAmount(0)
                        .installments(1)
                        .interest("ByMerchant")
                        .capture(false)
                        .recurrent(false)
                        .softDescriptor("123456789ABCD")
                        .creditCard(CreditCard.builder()
                                .cardNumber("4024007197692931")
                                .holder("Teste Holder")
                                .expirationDate("12/2021")
                                .securityCode("123")
                                .saveCard("false")
                                .brand("Visa")
                                .build())
                        .build())
                .build();

        requestBody = new ObjectMapper().writeValueAsString(request);
    }

    public void enviarRequisicaoPagamento() {
        response = given()
                .contentType(ContentType.JSON)
                .header("MerchantId", MERCHANT_ID)
                .header("MerchantKey", MERCHANT_KEY)
                .baseUri(BASE_URL)
                .body(requestBody)
                .when()
                .post(ENDPOINT_SALES);

        System.out.println("Resposta da API: " + response.getBody().asString());

        CartaoDeCreditoManager.setPaymentId(response.jsonPath().getString("Payment.PaymentId"));
        CartaoDeCreditoManager.setAmount(response.jsonPath().getString("Payment.Amount"));
        CartaoDeCreditoManager.setMerchantOrderId(response.jsonPath().getString("MerchantOrderId"));

        System.out.println("Payment ID: " + CartaoDeCreditoManager.getPaymentId());
        System.out.println("Amount: " + CartaoDeCreditoManager.getAmount());
        System.out.println("Merchant Order ID: " + CartaoDeCreditoManager.getMerchantOrderId());
    }

    public void validarPagamentoSimples() {
        validarRespostaCartaoExpirado();
    }

    public void validarPagamentoAutenticado() {
        validarRespostaSucesso();
    }

    public void validarPagamentoCompleto() {
        validarRespostaSucesso();
    }

    private void validarRespostaSucesso() {
        response.then()
                .body("Payment.Status", equalTo(1))
                .body("Payment.ReturnCode", equalTo("4"))
                .body("Payment.ReturnMessage", equalTo("Operation Successful"));
    }

    private void validarRespostaCartaoExpirado() {
        response.then()
                .body("Payment.Status", equalTo(3))
                .body("Payment.ReturnCode", equalTo("57"))
                .body("Payment.ReturnMessage", equalTo("Card Expired"));
    }

    public void validarStatusCode(int statusCode) {
        response.then()
                .statusCode(statusCode);
    }
}
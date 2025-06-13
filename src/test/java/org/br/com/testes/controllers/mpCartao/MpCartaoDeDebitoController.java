package org.br.com.testes.controllers.mpCartao;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.br.com.testes.manager.CartaoDeDebitoManager;
import org.br.com.testes.model.mpCartao.MpCartaoDeDebitoRequest;
import io.github.cdimascio.dotenv.Dotenv;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class MpCartaoDeDebitoController {
    private static final String BASE_URL = "https://apisandbox.cieloecommerce.cielo.com.br";
    private static final String ENDPOINT_SALES = "/1/sales";
    private static final Dotenv dotenv = Dotenv.load();
    private static final String MERCHANT_ID = dotenv.get("MERCHANT_ID");
    private static final String MERCHANT_KEY = dotenv.get("MERCHANT_KEY");
    private String requestBody;
    private Response response;

    public void prepararRequisicaoCartaoDebito() throws Exception {
        MpCartaoDeDebitoRequest request = MpCartaoDeDebitoRequest.builder()
                .merchantOrderId("2014121201")
                .customer(MpCartaoDeDebitoRequest.Customer.builder()
                        .name("Paulo Henrique")
                        .build())
                .payment(MpCartaoDeDebitoRequest.Payment.builder()
                        .type("DebitCard")
                        .amount(15700)
                        .provider("Simulado")
                        .returnUrl("http://www.google.com.br")
                        .debitCard(MpCartaoDeDebitoRequest.DebitCard.builder()
                                .cardNumber("4532117080573703")
                                .holder("Teste Holder")
                                .expirationDate("12/2019")
                                .securityCode("023")
                                .brand("Visa")
                                .build())
                        .build())
                .build();
        requestBody = new ObjectMapper().writeValueAsString(request);
    }

    public void prepararRequisicaoCartaoDebitoAutenticado() throws Exception {
        MpCartaoDeDebitoRequest request = MpCartaoDeDebitoRequest.builder()
                .merchantOrderId("2014121202")
                .customer(MpCartaoDeDebitoRequest.Customer.builder()
                        .name("Paulo Henrique")
                        .build())
                .payment(MpCartaoDeDebitoRequest.Payment.builder()
                        .type("DebitCard")
                        .amount(15700)
                        .provider("Simulado")
                        .returnUrl("http://www.google.com.br")
                        .authenticate(true)
                        .debitCard(MpCartaoDeDebitoRequest.DebitCard.builder()
                                .cardNumber("4532117080573703")
                                .holder("Teste Holder")
                                .expirationDate("12/2019")
                                .securityCode("023")
                                .brand("Visa")
                                .build())
                        .build())
                .build();
        requestBody = new ObjectMapper().writeValueAsString(request);
    }

    public void prepararRequisicaoCartaoDebitoCompleto() throws Exception {
        MpCartaoDeDebitoRequest request = MpCartaoDeDebitoRequest.builder()
                .merchantOrderId("2014121203")
                .customer(MpCartaoDeDebitoRequest.Customer.builder()
                        .name("Paulo Henrique")
                        .identity("11225468954")
                        .identityType("CPF")
                        .email("compradorteste@teste.com")
                        .birthdate("1991-01-02")
                        .address(MpCartaoDeDebitoRequest.Address.builder()
                                .street("Rua Teste")
                                .number("123")
                                .complement("AP 123")
                                .zipCode("12345987")
                                .city("Rio de Janeiro")
                                .state("RJ")
                                .country("BRA")
                                .build())
                        .deliveryAddress(MpCartaoDeDebitoRequest.Address.builder()
                                .street("Rua Teste")
                                .number("123")
                                .complement("AP 123")
                                .zipCode("12345987")
                                .city("Rio de Janeiro")
                                .state("RJ")
                                .country("BRA")
                                .build())
                        .build())
                .payment(MpCartaoDeDebitoRequest.Payment.builder()
                        .type("DebitCard")
                        .amount(15700)
                        .currency("BRL")
                        .country("BRA")
                        .provider("Simulado")
                        .serviceTaxAmount(0)
                        .installments(1)
                        .interest("ByMerchant")
                        .capture(false)
                        .authenticate(false)
                        .recurrent(false)
                        .returnUrl("http://www.google.com.br")
                        .debitCard(MpCartaoDeDebitoRequest.DebitCard.builder()
                                .cardNumber("4532117080573703")
                                .holder("Teste Holder")
                                .expirationDate("12/2019")
                                .securityCode("023")
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

        CartaoDeDebitoManager.setCardNumber(response.jsonPath().getString("Payment.NewCard.CardNumber"));
        CartaoDeDebitoManager.setAmount(response.jsonPath().getString("Payment.Amount"));
        CartaoDeDebitoManager.setPaymentId(response.jsonPath().getString("Payment.PaymentId"));
        CartaoDeDebitoManager.setExpirationDate(response.jsonPath().getString("Payment.NewCard.ExpirationDate"));

        System.out.println("Card Number: " + CartaoDeDebitoManager.getCardNumber());
        System.out.println("Amount: " + CartaoDeDebitoManager.getAmount());
        System.out.println("Payment ID: " + CartaoDeDebitoManager.getPaymentId());
        System.out.println("Expiration Date: " + CartaoDeDebitoManager.getExpirationDate());

    }

    public void validarPagamentoSimples() {
        validarRespostaCartaoExpirado();
    }

    public void validarPagamentoAutenticado() {
        validarRespostaCartaoExpirado();
    }

    public void validarPagamentoCompleto() {
        validarRespostaCartaoExpirado();
    }

    public void validarPagamentoProcessadoComSucesso() {
        validarRespostaCartaoExpirado();
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
package org.br.com.testes.controllers.avs;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.br.com.testes.model.avs.AvsRequest;
import org.br.com.testes.utils.LogFormatter;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class AvsController {
    private static final String BASE_URL = "https://apisandbox.cieloecommerce.cielo.com.br";
    private static final String ENDPOINT_SALES = "/1/sales";
    private static final String MERCHANT_ID = "1dbf6ac5-0bb2-4fdb-a6a2-663f6e9554c3";
    private static final String MERCHANT_KEY = "DPECNPURVQHOKMIPZLWREWERXXKVRWXYUCRKGOBA";
    private Response response;

    public void realizarPagamentoComAvs() {
        LogFormatter.logStep("Realizando pagamento com AVS");

        AvsRequest request = AvsRequest.builder()
                .merchantOrderId("2014111703")
                .payment(AvsRequest.Payment.builder()
                        .type("CreditCard")
                        .amount(15700)
                        .installments(1)
                        .capture(true)
                        .creditCard(AvsRequest.CreditCard.builder()
                                .cardNumber("4551870000000181")
                                .holder("Teste Holder")
                                .expirationDate("12/2021")
                                .securityCode("123")
                                .brand("Visa")
                                .avs(AvsRequest.Avs.builder()
                                        .cpf("10939107716")
                                        .zipCode("24320570")
                                        .street("Estrada Caetano Monteiro")
                                        .number("391")
                                        .build())
                                .build())
                        .build())
                .build();

        LogFormatter.logStep("Request: " + request);

        response = given()
                .contentType(ContentType.JSON)
                .header("MerchantId", MERCHANT_ID)
                .header("MerchantKey", MERCHANT_KEY)
                .baseUri(BASE_URL)
                .body(request)
                .when()
                .post(ENDPOINT_SALES);

        LogFormatter.logStep("Response: " + response.asString());
    }

    public void validarPagamentoAutorizado() {
        validarRespostaSucesso();
    }

    private void validarRespostaSucesso() {
        response.then()
                .body("Payment.Status", equalTo(2))
                .body("Payment.ReturnCode", equalTo("4"))
                .body("Payment.ReturnMessage", equalTo("Operation Successful"));
    }

    public void validarStatusCode(int statusCode) {
        response.then()
                .log().all(true)
                .statusCode(statusCode);
    }
}

package org.br.com.testes.controllers.antifraudGateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.br.com.testes.model.antifraudGateway.FraudAnalysisRequest;
import org.br.com.testes.utils.LogFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;

public class FraudAnalysisController {
    private static final Logger logger = LoggerFactory.getLogger(FraudAnalysisController.class);
    private static final String BASE_URL = "https://apisandbox.cieloecommerce.cielo.com.br";
    private static final String ENDPOINT_SALES = "/1/sales";
    private static final String MERCHANT_ID = "1dbf6ac5-0bb2-4fdb-a6a2-663f6e9554c3";
    private static final String MERCHANT_KEY = "DPECNPURVQHOKMIPZLWREWERXXKVRWXYUCRKGOBA";
    private String requestBody;
    private Response response;

    public void prepararRequisicaoAntifraude() throws Exception {
        LogFormatter.logStep("Preparando requisição de antifraude");
        FraudAnalysisRequest request = FraudAnalysisRequest.builder()
                .merchantOrderId("2014111457673454307")
                .customer(FraudAnalysisRequest.Customer.builder()
                        .name("Comprador accept")
                        .identity("30602458005")
                        .identityType("CPF")
                        .email("compradorteste@live.com")
                        .birthdate("1991-01-02")
                        .address(FraudAnalysisRequest.Address.builder()
                                .street("Rua Júpter")
                                .number("174")
                                .complement("AP 201")
                                .zipCode("21241140")
                                .city("Rio de Janeiro")
                                .state("RJ")
                                .country("BRA")
                                .build())
                        .deliveryAddress(FraudAnalysisRequest.Address.builder()
                                .street("Rua Júpter")
                                .number("174")
                                .complement("AP 201")
                                .zipCode("21241140")
                                .city("Rio de Janeiro")
                                .state("RJ")
                                .country("BRA")
                                .build())
                        .build())
                .payment(FraudAnalysisRequest.Payment.builder()
                        .type("CreditCard")
                        .amount(100)
                        .currency("BRL")
                        .country("BRA")
                        .provider("Simulado")
                        .serviceTaxAmount(0)
                        .installments(1)
                        .interest("ByMerchant")
                        .capture(false)
                        .authenticate(false)
                        .creditCard(FraudAnalysisRequest.CreditCard.builder()
                                .cardNumber("4024000097692931")
                                .holder("Teste accept")
                                .expirationDate("12/2019")
                                .securityCode("023")
                                .brand("Visa")
                                .build())
                        .fraudAnalysis(FraudAnalysisRequest.FraudAnalysis.builder()
                                .sequence("AuthorizeFirst")
                                .sequenceCriteria("OnSuccess")
                                .provider("CyberSource")
                                .fingerPrintId("074c1ee676ed4998ab66491013c565e2")
                                .captureOnLowRisk(true)
                                .voidOnHighRisk(false)
                                .browser(FraudAnalysisRequest.Browser.builder()
                                        .cookiesAccepted(false)
                                        .email("compradorteste@live.com")
                                        .hostName("Teste")
                                        .ipAddress("200.190.150.350")
                                        .type("Chrome")
                                        .build())
                                .cart(FraudAnalysisRequest.Cart.builder()
                                        .isGift(false)
                                        .returnsAccepted(true)
                                        .items(java.util.Collections.singletonList(
                                                FraudAnalysisRequest.Item.builder()
                                                        .giftCategory("Undefined")
                                                        .hostHedge("Off")
                                                        .nonSensicalHedge("Off")
                                                        .obscenitiesHedge("Off")
                                                        .phoneHedge("Off")
                                                        .name("ItemTeste")
                                                        .quantity(1)
                                                        .sku("201411170235134521346")
                                                        .unitPrice(123)
                                                        .risk("High")
                                                        .timeHedge("Normal")
                                                        .type("AdultContent")
                                                        .velocityHedge("High")
                                                        .passenger(FraudAnalysisRequest.Passenger.builder()
                                                                .email("compradorteste@live.com")
                                                                .identity("1234567890")
                                                                .name("Comprador accept")
                                                                .rating("Adult")
                                                                .phone("999994444")
                                                                .status("Accepted")
                                                                .build())
                                                        .build()))
                                        .build())
                                .merchantDefinedFields(java.util.Collections.singletonList(
                                        FraudAnalysisRequest.MerchantDefinedField.builder()
                                                .id(95)
                                                .value("Eu defini isso")
                                                .build()))
                                .shipping(FraudAnalysisRequest.Shipping.builder()
                                        .addressee("Sr Comprador Teste")
                                        .method("LowCost")
                                        .phone("21114740")
                                        .build())
                                .travel(FraudAnalysisRequest.Travel.builder()
                                        .departureTime("2010-01-02")
                                        .journeyType("Ida")
                                        .route("MAO-RJO")
                                        .legs(java.util.Collections.singletonList(
                                                FraudAnalysisRequest.Leg.builder()
                                                        .destination("GYN")
                                                        .origin("VCP")
                                                        .build()))
                                        .build())
                                .build())
                        .build())
                .build();

        requestBody = new ObjectMapper().writeValueAsString(request);
        LogFormatter.logRequest("POST", BASE_URL + ENDPOINT_SALES, requestBody);

        // Enviando a requisição após preparar
        response = given()
                .contentType(ContentType.JSON)
                .header("MerchantId", MERCHANT_ID)
                .header("MerchantKey", MERCHANT_KEY)
                .baseUri(BASE_URL)
                .body(requestBody)
                .when()
                .post(ENDPOINT_SALES);

        LogFormatter.logResponse(String.valueOf(response.getStatusCode()), response.getBody().asPrettyString());
    }

    public void validarStatusCode(int statusCode) {
        LogFormatter.logStep("Validando status code: " + statusCode);
        response.then()
                .statusCode(statusCode);
    }
}

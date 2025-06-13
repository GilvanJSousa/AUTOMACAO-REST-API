package org.br.com.testes.controllers.mpBoleto;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.br.com.testes.manager.BoletoManager;
import org.br.com.testes.manager.CartaoDeCreditoManager;
import org.br.com.testes.model.mpBoleto.MpBoletoRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class MpBoletoController {
    private static final Logger logger = LoggerFactory.getLogger(MpBoletoController.class);
    private static final String BASE_URL = "https://apisandbox.cieloecommerce.cielo.com.br";
    private static final String ENDPOINT_SALES = "/1/sales";
    private static final Dotenv dotenv = Dotenv.load();
    private static final String MERCHANT_ID = dotenv.get("MERCHANT_ID");
    private static final String MERCHANT_KEY = dotenv.get("MERCHANT_KEY");
    private String requestBody;
    private Response response;

    public void prepararRequisicaoBoleto() throws Exception {
        logger.info("Preparando requisição de boleto");
        MpBoletoRequest request = MpBoletoRequest.builder()
                .merchantOrderId("2014111706")
                .customer(MpBoletoRequest.Customer.builder()
                        .name("Comprador Teste Boleto")
                        .identity("1234567890")
                        .address(MpBoletoRequest.Address.builder()
                                .street("Avenida Marechal Câmara")
                                .number("160")
                                .complement("Sala 934")
                                .zipCode("22750012")
                                .district("Centro")
                                .city("Rio de Janeiro")
                                .state("RJ")
                                .country("BRA")
                                .build())
                        .build())
                .payment(MpBoletoRequest.Payment.builder()
                        .type("Boleto")
                        .amount(15700)
                        .provider("bradesco2")
                        .address("Rua Teste")
                        .boletoNumber("123")
                        .assignor("Empresa Teste")
                        .demonstrative("Desmonstrative Teste")
                        .expirationDate("5/1/2015")
                        .identification("11884926754")
                        .instructions("Aceitar somente até a data de vencimento, após essa data juros de 1% dia.")
                        .build())
                .build();

        requestBody = new ObjectMapper().writeValueAsString(request);
    }

    public void enviarRequisicaoPagamento() {
        logger.info("Enviando requisição de pagamento com boleto");
        response = given()
                .contentType(ContentType.JSON)
                .header("MerchantId", MERCHANT_ID)
                .header("MerchantKey", MERCHANT_KEY)
                .baseUri(BASE_URL)
                .body(requestBody)
                .when()
                .post(ENDPOINT_SALES);

        BoletoManager.setBarCodeNumber(response.jsonPath().getString("Payment.BarCodeNumber"));
        BoletoManager.setDigitableLine(response.jsonPath().getString("Payment.DigitableLine"));
        CartaoDeCreditoManager.setPaymentId(response.jsonPath().getString("Payment.PaymentId"));
        CartaoDeCreditoManager.setAmount(response.jsonPath().getString("Payment.Amount"));
        System.out.println("Bar Code Number: " + BoletoManager.getBarCodeNumber());
        System.out.println("Digitable Line: " + BoletoManager.getDigitableLine());
        System.out.println("Payment Amount: " + CartaoDeCreditoManager.getAmount());
        System.out.println("Payment ID: " + CartaoDeCreditoManager.getPaymentId());
    }

    public void validarPagamentoProcessadoComSucesso() {
        logger.info("Validando processamento do pagamento");
        response.then()
                .body("Payment.Status", equalTo(1))
                .body("Payment.Url", notNullValue())
                .body("Payment.BarCodeNumber", notNullValue())
                .body("Payment.DigitableLine", notNullValue());
    }

    public void validarStatusCode(int statusCode) {
        logger.info("Validando status code: {}", statusCode);
        response.then()
                .statusCode(statusCode);
    }
} 
package org.br.com.testes.controllers.wallet;

import io.restassured.response.Response;
import org.br.com.testes.model.wallet.WalletRequest;
import org.br.com.testes.utils.Constants;
import org.br.com.testes.utils.LogFormatter;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class WalletController {
    private static final String BASE_URL = Constants.BASE_URL;
    private static final String MERCHANT_ID = Constants.MERCHANT_ID;
    private static final String MERCHANT_KEY = Constants.MERCHANT_KEY;

    public Response realizarPagamentoVisaCheckout(Map<String, String> dados) {
        LogFormatter.logStep("Realizando pagamento com VisaCheckout");

        WalletRequest request = WalletRequest.builder()
                .merchantOrderId(dados.get("merchantOrderId"))
                .customer(WalletRequest.Customer.builder()
                        .name("Comprador Teste")
                        .build())
                .payment(WalletRequest.Payment.builder()
                        .type("CreditCard")
                        .amount(Integer.parseInt(dados.get("amount")))
                        .installments(Integer.parseInt(dados.get("installments")))
                        .softDescriptor("123456789ABCD")
                        .capture(true)
                        .creditCard(WalletRequest.CreditCard.builder()
                                .securityCode(dados.get("securityCode"))
                                .build())
                        .wallet(WalletRequest.Wallet.builder()
                                .type("VisaCheckout")
                                .walletKey(dados.get("walletKey"))
                                .build())
                        .build())
                .build();

        LogFormatter.logStep("Request: " + request);

        Response response = given()
                .header("MerchantId", MERCHANT_ID)
                .header("MerchantKey", MERCHANT_KEY)
                .header("Content-Type", "application/json")
                .body(request)
                .when()
                .post(BASE_URL + "/1/sales");

        LogFormatter.logStep("Response Status Code: " + response.getStatusCode());
        LogFormatter.logStep("Response Body: " + response.getBody().asString());

        return response;
    }
}

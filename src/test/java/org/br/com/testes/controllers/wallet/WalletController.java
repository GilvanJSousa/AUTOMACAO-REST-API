package org.br.com.testes.controllers.wallet;

import io.restassured.response.Response;
import org.br.com.testes.model.wallet.WalletRequest;
import org.br.com.testes.utils.Constants;
import org.br.com.testes.utils.LogFormatter;
import org.junit.jupiter.api.Assertions;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class WalletController {
    private static final String BASE_URL = Constants.BASE_URL;
    private static final String MERCHANT_ID = Constants.MERCHANT_ID;
    private static final String MERCHANT_KEY = Constants.MERCHANT_KEY;

    // Constantes para dados do cartão
    private static final String CARD_NUMBER = "4551870000000183";
    private static final String CARD_HOLDER = "Teste Holder";
    private static final String EXPIRATION_DATE = "12/2026";
    private static final String CARD_BRAND = "Visa";
    private static final String SOFT_DESCRIPTOR = "123456789ABCD";
    private static final String PROVIDER = "Simulado";
    private static final String ECI = "7";
    private static final String CAVV = "AM1mbqehL24XAAa0J04CAoABFA==";
    private static final String CUSTOMER_CPF = "11225468954";
    private static final String CUSTOMER_NAME = "Comprador Teste";

    private Response response;

    /**
     * Realiza pagamento com VisaCheckout utilizando os dados fornecidos
     * @param dados Mapa contendo os dados necessários para o pagamento
     * @return Response da requisição
     */
    public Response realizarPagamentoVisaCheckout(Map<String, String> dados) {
        LogFormatter.logStep("Realizando pagamento com VisaCheckout");

        WalletRequest request = WalletRequest.builder()
                .merchantOrderId(dados.get("merchantOrderId"))
                .customer(WalletRequest.Customer.builder()
                        .name(CUSTOMER_NAME)
                        .identity(CUSTOMER_CPF)
                        .identityType("CPF")
                        .build())
                .payment(WalletRequest.Payment.builder()
                        .type("CreditCard")
                        .provider(PROVIDER)
                        .amount(Integer.parseInt(dados.get("amount")))
                        .installments(Integer.parseInt(dados.get("installments")))
                        .softDescriptor(SOFT_DESCRIPTOR)
                        .capture(true)
                        .creditCard(WalletRequest.CreditCard.builder()
                                .cardNumber(CARD_NUMBER)
                                .holder(CARD_HOLDER)
                                .expirationDate(EXPIRATION_DATE)
                                .securityCode(dados.get("securityCode"))
                                .brand(CARD_BRAND)
                                .build())
                        .wallet(WalletRequest.Wallet.builder()
                                .type("VisaCheckout")
                                .walletKey(dados.get("walletKey"))
                                .eci(ECI)
                                .cavv(CAVV)
                                .build())
                        .build())
                .build();

        LogFormatter.logStep("Request: " + request);

        response = given()
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

    /**
     * Realiza pagamento com VisaCheckout utilizando dados padrão definidos pelo cenário de teste.
     * @return Response da requisição
     */
    public Response realizarPagamentoVisaCheckout() {
        Map<String, String> dados = new HashMap<>();
        dados.put("merchantOrderId", "2014111703");
        dados.put("amount", "15700");
        dados.put("installments", "1");
        dados.put("securityCode", "123");
        dados.put("walletKey", "1140814777695873901");

        return realizarPagamentoVisaCheckout(dados);
    }

    /**
     * Valida o status code da resposta
     * @param statusCode Status code esperado
     */
    public void validarStatusCode(int statusCode) {
        LogFormatter.logStep("Validando status code: " + statusCode);
        Assertions.assertEquals(statusCode, response.getStatusCode(),
                "Status code deve ser " + statusCode);
    }

    /**
     * Valida que o pagamento foi autorizado com sucesso
     */
    public void validarPagamentoAutorizado() {
        LogFormatter.logStep("Validando autorizacao do pagamento");
        
        // Validação robusta para evitar NumberFormatException
        Object statusObj = response.jsonPath().get("Payment.Status");
        if (statusObj == null) {
            Assertions.fail("Campo Payment.Status ausente na resposta");
        }
        // Novo tratamento para lista vazia
        if (statusObj instanceof java.util.List && ((java.util.List<?>) statusObj).isEmpty()) {
            Assertions.fail("Campo Payment.Status retornou lista vazia. Verifique se a transação foi realmente processada ou se houve erro na requisição.");
            return;
        }
        int status;
        try {
            if (statusObj instanceof Integer) {
                status = (Integer) statusObj;
            } else if (statusObj instanceof String && !((String) statusObj).isEmpty()) {
                status = Integer.parseInt((String) statusObj);
            } else {
                Assertions.fail("Campo Payment.Status vazio ou em formato inesperado: " + statusObj);
                return;
            }
        } catch (Exception e) {
            Assertions.fail("Erro ao converter Payment.Status: " + statusObj + " - " + e.getMessage());
            return;
        }
        Assertions.assertEquals(2, status, "Status do pagamento deve ser 2 (Authorized)");
        Assertions.assertNotNull(response.jsonPath().getString("Payment.PaymentId"), "PaymentId nao deve ser nulo");
        Assertions.assertNotNull(response.jsonPath().getString("Payment.Tid"), "Tid nao deve ser nulo");
        Assertions.assertNotNull(response.jsonPath().getString("Payment.ProofOfSale"), "ProofOfSale nao deve ser nulo");
        Assertions.assertNotNull(response.jsonPath().getString("Payment.AuthorizationCode"), "AuthorizationCode nao deve ser nulo");
        // Validações do Wallet
        Assertions.assertEquals("VisaCheckout", response.jsonPath().getString("Payment.Wallet.Type"), "Tipo da wallet deve ser VisaCheckout");
        Assertions.assertEquals(ECI, response.jsonPath().getString("Payment.Wallet.Eci"), "ECI deve ser " + ECI);
    }
}
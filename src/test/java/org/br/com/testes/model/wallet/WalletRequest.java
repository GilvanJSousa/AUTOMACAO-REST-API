package org.br.com.testes.model.wallet;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class WalletRequest {
    private String merchantOrderId;
    private Customer customer;
    private Payment payment;

    @Data
    @Builder
    public static class Customer {
        private String name;
        private String identity;      // CPF do comprador
        private String identityType;  // Tipo do documento (CPF)
    }

    @Data
    @Builder
    public static class Payment {
        private String type;
        private String provider;      // Provider para ambiente sandbox
        private Integer amount;
        private Integer installments;
        private String softDescriptor;
        private Boolean capture;
        private CreditCard creditCard;
        private Wallet wallet;
    }

    @Data
    @Builder
    public static class CreditCard {
        private String cardNumber;
        private String holder;
        private String expirationDate;
        private String securityCode;
        private String brand;
    }

    @Data
    @Builder
    public static class Wallet {
        private String type;
        private String walletKey;
        private String eci;
        private String cavv;          // Cardholder Authentication Verification Value
    }
}
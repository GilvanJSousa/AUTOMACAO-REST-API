package org.br.com.testes.model.wallet;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class WalletRequest {
    private String merchantOrderId;
    private Customer customer;
    private Payment payment;

    @Getter
    @Setter
    @Builder
    public static class Customer {
        private String name;
        private String identity;
        private String identityType;
    }

    @Getter
    @Setter
    @Builder
    public static class Payment {
        private String type;
        private Integer amount;
        private String provider;
        private Integer installments;
        private String softDescriptor;
        private Boolean capture;
        private CreditCard creditCard;
        private Wallet wallet;
    }

    @Getter
    @Setter
    @Builder
    public static class CreditCard {
        private String cardNumber;
        private String holder;
        private String expirationDate;
        private String securityCode;
        private String brand;
    }

    @Getter
    @Setter
    @Builder
    public static class Wallet {
        private String type;
        private String eci;
        private String cavv;
        private String walletKey;
    }
}

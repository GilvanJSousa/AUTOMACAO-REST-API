package org.br.com.testes.model.recorrencia;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecorrenciaRequest {
    private String merchantOrderId;
    private Customer customer;
    private Payment payment;

    @Data
    @Builder
    public static class Customer {
        private String name;
    }

    @Data
    @Builder
    public static class Payment {
        private int installments;
        private RecurrentPayment recurrentPayment;
        private CreditCard creditCard;
        private String softDescriptor;
        private String type;
        private int amount;
        private String currency;
        private String country;
    }

    @Data
    @Builder
    public static class RecurrentPayment {
        private boolean authorizeNow;
        private String endDate;
        private String interval;
    }

    @Data
    @Builder
    public static class CreditCard {
        private String cardNumber;
        private String holder;
        private String expirationDate;
        private String securityCode;
        private boolean saveCard;
        private String brand;
    }
}

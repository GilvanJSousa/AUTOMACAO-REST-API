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
        private String type;
        private Integer amount;
        private Integer installments;
        private CreditCard creditCard;
        private RecurrentPayment recurrentPayment;
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
    public static class RecurrentPayment {
        private Boolean authorizeNow;
        private String endDate;
        private String interval;
    }
}

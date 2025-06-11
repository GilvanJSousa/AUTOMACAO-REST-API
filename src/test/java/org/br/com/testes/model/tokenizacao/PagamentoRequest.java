package org.br.com.testes.model.tokenizacao;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PagamentoRequest {
    private String merchantOrderId;
    private Customer customer;
    private Payment payment;

    @Getter
    @Setter
    @Builder
    public static class Customer {
        private String name;
    }

    @Getter
    @Setter
    @Builder
    public static class Payment {
        private String type;
        private Integer amount;
        private String provider;
        private Integer installments;
        private CreditCard creditCard;
    }

    @Getter
    @Setter
    @Builder
    public static class CreditCard {
        private String cardNumber;
        private String holder;
        private String expirationDate;
        private String securityCode;
        private String saveCard;
        private String brand;
        private String cardToken;
    }
} 
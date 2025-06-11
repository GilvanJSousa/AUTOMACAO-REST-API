package org.br.com.testes.model.avs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvsRequest {
    private String merchantOrderId;
    private Payment payment;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Payment {
        private String type;
        private Integer amount;
        private Integer installments;
        private String softDescriptor;
        private Boolean capture;
        private CreditCard creditCard;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreditCard {
        private String cardNumber;
        private String holder;
        private String expirationDate;
        private String securityCode;
        private String brand;
        private Avs avs;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Avs {
        private String cpf;
        private String zipCode;
        private String street;
        private String number;
        private String complement;
        private String district;
    }
} 
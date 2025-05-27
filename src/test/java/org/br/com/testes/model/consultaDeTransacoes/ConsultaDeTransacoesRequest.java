package org.br.com.testes.model.consultaDeTransacoes;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ConsultaDeTransacoesRequest {
    @JsonProperty("MerchantOrderId")
    private String merchantOrderId;

    @JsonProperty("Payment")
    private Payment payment;

    @Getter
    @Setter
    @Builder
    public static class Payment {
        @JsonProperty("Type")
        private String type;

        @JsonProperty("Amount")
        private Integer amount;

        @JsonProperty("Installments")
        private Integer installments;

        @JsonProperty("SoftDescriptor")
        private String softDescriptor;

        @JsonProperty("CreditCard")
        private CreditCard creditCard;
    }

    @Getter
    @Setter
    @Builder
    public static class CreditCard {
        @JsonProperty("CardNumber")
        private String cardNumber;

        @JsonProperty("Holder")
        private String holder;

        @JsonProperty("ExpirationDate")
        private String expirationDate;

        @JsonProperty("SecurityCode")
        private String securityCode;

        @JsonProperty("Brand")
        private String brand;
    }
} 
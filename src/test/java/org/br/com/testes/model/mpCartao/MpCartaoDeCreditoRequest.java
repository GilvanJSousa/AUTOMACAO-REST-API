package org.br.com.testes.model.mpCartao;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MpCartaoDeCreditoRequest {
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
        private String email;
        private String birthdate;
        private Address address;
        private Address deliveryAddress;
    }

    @Getter
    @Setter
    @Builder
    public static class Address {
        private String street;
        private String number;
        private String complement;
        private String zipCode;
        private String city;
        private String state;
        private String country;
    }

    @Getter
    @Setter
    @Builder
    public static class Payment {
        private String type;
        private Integer amount;
        private String currency;
        private String country;
        private String provider;
        private Integer serviceTaxAmount;
        private Integer installments;
        private String interest;
        private Boolean capture;
        private Boolean recurrent;
        private String softDescriptor;
        private String returnUrl;
        private Boolean authenticate;
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
        private String brand;
        private String saveCard;
    }
}
package org.br.com.testes.model.mpCartao;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
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
        @JsonProperty("Name")
        private String name;

        @JsonProperty("Identity")
        private String identity;

        @JsonProperty("IdentityType")
        private String identityType;

        @JsonProperty("Email")
        private String email;

        @JsonProperty("Birthdate")
        private String birthdate;

        @JsonProperty("Address")
        private Address address;

        @JsonProperty("DeliveryAddress")
        private Address deliveryAddress;
    }

    @Getter
    @Setter
    @Builder
    public static class Address {
        @JsonProperty("Street")
        private String street;

        @JsonProperty("Number")
        private String number;

        @JsonProperty("Complement")
        private String complement;

        @JsonProperty("ZipCode")
        private String zipCode;

        @JsonProperty("City")
        private String city;

        @JsonProperty("State")
        private String state;

        @JsonProperty("Country")
        private String country;
    }

    @Getter
    @Setter
    @Builder
    public static class Payment {
        @JsonProperty("Type")
        private String type;

        @JsonProperty("Amount")
        private Integer amount;

        @JsonProperty("Currency")
        private String currency;

        @JsonProperty("Country")
        private String country;

        @JsonProperty("Provider")
        private String provider;

        @JsonProperty("ServiceTaxAmount")
        private Integer serviceTaxAmount;

        @JsonProperty("Installments")
        private Integer installments;

        @JsonProperty("Interest")
        private String interest;

        @JsonProperty("Capture")
        private Boolean capture;

        @JsonProperty("Recurrent")
        private Boolean recurrent;

        @JsonProperty("SoftDescriptor")
        private String softDescriptor;

        @JsonProperty("ReturnUrl")
        private String returnUrl;

        @JsonProperty("Authenticate")
        private Boolean authenticate;

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

        @JsonProperty("SaveCard")
        private String saveCard;
    }
}
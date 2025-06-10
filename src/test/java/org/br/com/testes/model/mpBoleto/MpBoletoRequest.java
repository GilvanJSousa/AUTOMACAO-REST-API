package org.br.com.testes.model.mpBoleto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MpBoletoRequest {
    private String merchantOrderId;
    private Customer customer;
    private Payment payment;

    @Data
    @Builder
    public static class Customer {
        private String name;
        private String identity;
        private Address address;
    }

    @Data
    @Builder
    public static class Address {
        private String street;
        private String number;
        private String complement;
        private String zipCode;
        private String district;
        private String city;
        private String state;
        private String country;
    }

    @Data
    @Builder
    public static class Payment {
        private String type;
        private Integer amount;
        private String provider;
        private String address;
        private String boletoNumber;
        private String assignor;
        private String demonstrative;
        private String expirationDate;
        private String identification;
        private String instructions;
    }
} 
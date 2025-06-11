package org.br.com.testes.model.recorrencia;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlterarDadosCompradorRequest {
    private String name;
    private String email;
    private String birthdate;
    private String identity;
    private String identityType;
    private Address address;
    private Address deliveryAddress;

    @Data
    @Builder
    public static class Address {
        private String street;
        private String number;
        private String complement;
        private String zipCode;
        private String city;
        private String state;
        private String country;
        private String district;
    }
} 
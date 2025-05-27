package org.br.com.testes.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MpCartaoDeCreditoRequest {
    //TODO: Campos básicos
    private String merchantOrderId;
    private String type;
    private Integer amount;
    private Integer installments;
    private String softDescriptor;
    private String currency;
    private String country;
    private String provider;
    private Integer serviceTaxAmount;
    private String interest;
    private Boolean capture;
    private Boolean recurrent;

    //TODO: Campos do cartão
    private String cardNumber;
    private String holder;
    private String expirationDate;
    private String securityCode;
    private String brand;
    private String saveCard;

    //TODO: Campos do cliente
    private String customerName;
    private String customerIdentity;
    private String customerIdentityType;
    private String customerEmail;
    private String customerBirthdate;

    //TODO: Campos de endereço
    private String addressStreet;
    private String addressNumber;
    private String addressComplement;
    private String addressZipCode;
    private String addressCity;
    private String addressState;
    private String addressCountry;

    //TODO: Campos de endereço de entrega
    private String deliveryStreet;
    private String deliveryNumber;
    private String deliveryComplement;
    private String deliveryZipCode;
    private String deliveryCity;
    private String deliveryState;
    private String deliveryCountry;

    //TODO: Campos para autenticação
    private String returnUrl;
    private Boolean authenticate;
}
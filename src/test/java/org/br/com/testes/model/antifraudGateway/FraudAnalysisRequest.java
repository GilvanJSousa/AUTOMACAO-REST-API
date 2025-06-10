package org.br.com.testes.model.antifraudGateway;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class FraudAnalysisRequest {
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
        private Boolean authenticate;
        private CreditCard creditCard;
        private FraudAnalysis fraudAnalysis;
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
    }

    @Getter
    @Setter
    @Builder
    public static class FraudAnalysis {
        private String sequence;
        private String sequenceCriteria;
        private String provider;
        private String fingerPrintId;
        private Boolean captureOnLowRisk;
        private Boolean voidOnHighRisk;
        private Browser browser;
        private Cart cart;
        private List<MerchantDefinedField> merchantDefinedFields;
        private Shipping shipping;
        private Travel travel;
    }

    @Getter
    @Setter
    @Builder
    public static class Browser {
        private Boolean cookiesAccepted;
        private String email;
        private String hostName;
        private String ipAddress;
        private String type;
    }

    @Getter
    @Setter
    @Builder
    public static class Cart {
        private Boolean isGift;
        private Boolean returnsAccepted;
        private List<Item> items;
    }

    @Getter
    @Setter
    @Builder
    public static class Item {
        private String giftCategory;
        private String hostHedge;
        private String nonSensicalHedge;
        private String obscenitiesHedge;
        private String phoneHedge;
        private String name;
        private Integer quantity;
        private String sku;
        private Integer unitPrice;
        private String risk;
        private String timeHedge;
        private String type;
        private String velocityHedge;
        private Passenger passenger;
    }

    @Getter
    @Setter
    @Builder
    public static class Passenger {
        private String email;
        private String identity;
        private String name;
        private String rating;
        private String phone;
        private String status;
    }

    @Getter
    @Setter
    @Builder
    public static class MerchantDefinedField {
        private Integer id;
        private String value;
    }

    @Getter
    @Setter
    @Builder
    public static class Shipping {
        private String addressee;
        private String method;
        private String phone;
    }

    @Getter
    @Setter
    @Builder
    public static class Travel {
        private String departureTime;
        private String journeyType;
        private String route;
        private List<Leg> legs;
    }

    @Getter
    @Setter
    @Builder
    public static class Leg {
        private String destination;
        private String origin;
    }
}

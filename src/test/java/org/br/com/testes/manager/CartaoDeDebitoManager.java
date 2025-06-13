package org.br.com.testes.manager;

import io.cucumber.java.en.Then;

public class CartaoDeDebitoManager {

    public static final ThreadLocal<String> cardNumber = new ThreadLocal<>();
    public static final ThreadLocal<String> amount = new ThreadLocal<>();
    public static final ThreadLocal<String> paymentId = new ThreadLocal<>();
    public static final ThreadLocal<String> expirationDate = new ThreadLocal<>();

    public static String getCardNumber() {
        return cardNumber.get();
    }

    public static String getAmount() {
        return amount.get();
    }

    public static String getPaymentId() {
        return paymentId.get();
    }

    public static String getExpirationDate() {
        return expirationDate.get();
    }

    public static void setCardNumber(String id) {
        cardNumber.set(id);
    }

    public static void setAmount(String value) {
        amount.set(value);
    }

    public static void setPaymentId(String id) {
        paymentId.set(id);
    }

    public static void setExpirationDate(String id) {
        expirationDate.set(id);
    }

    public static void remove() {
        cardNumber.remove();
        amount.remove();
        paymentId.remove();
        expirationDate.remove();
    }

}

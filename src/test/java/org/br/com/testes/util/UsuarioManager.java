package org.br.com.testes.util;

public class UsuarioManager {
    private static String recurrentPaymentId;
    private static String paymentId;
    private static String amount;
    private static String merchantOrderId;

    public static void setRecurrentPaymentId(String id) {
        recurrentPaymentId = id;
    }

    public static String getRecurrentPaymentId() {
        return recurrentPaymentId;
    }

    public static void setPaymentId(String id) {
        paymentId = id;
    }

    public static String getPaymentId() {
        return paymentId;
    }

    public static void setAmount(String value) {
        amount = value;
    }

    public static String getAmount() {
        return amount;
    }

    public static void setMerchantOrderId(String id) {
        merchantOrderId = id;
    }

    public static String getMerchantOrderId() {
        return merchantOrderId;
    }
} 
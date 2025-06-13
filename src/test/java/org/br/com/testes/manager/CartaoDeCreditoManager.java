package org.br.com.testes.manager;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartaoDeCreditoManager {

    private static final ThreadLocal<String> paymentId = new ThreadLocal<>();
    private static final ThreadLocal<String> amount = new ThreadLocal<>();
    private static final ThreadLocal<String> merchantOrderId = new ThreadLocal<>();
    private static final ThreadLocal<String> recurrentPaymentId = new ThreadLocal<>();

    public static String getPaymentId() {
        return paymentId.get();
    }

    public static String getAmount() {
        return amount.get();
    }

    public static String getMerchantOrderId() {
        return merchantOrderId.get();
    }

    public static String getRecurrentPaymentId() {
        return recurrentPaymentId.get();
    }

    public static void setPaymentId(String id) {
        paymentId.set(id);
    }

    public static void setAmount(String value) {
        amount.set(value);
    }

    public static void setMerchantOrderId(String id) {
        merchantOrderId.set(id);
    }

    public static void setRecurrentPaymentId(String id) {
        recurrentPaymentId.set(id);
    }


    public static void remove() {
        paymentId.remove();
        amount.remove();
        merchantOrderId.remove();
        recurrentPaymentId.remove();
    }

}

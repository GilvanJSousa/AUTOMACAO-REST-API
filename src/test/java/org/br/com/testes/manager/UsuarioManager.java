package org.br.com.testes.manager;

public class UsuarioManager {

    private static final ThreadLocal<String> paymentId = new ThreadLocal<>();
    private static final ThreadLocal<String> amount = new ThreadLocal<>();

    public static String getPaymentId() {
        return paymentId.get();
    }

    public static String getAmount() {
        return amount.get();
    }

    public static void setPaymentId(String id) {
        paymentId.set(id);
    }

    public static void setAmount(String value) {
        amount.set(value);
    }

    public static void remove() {
        paymentId.remove();
        amount.remove();
    }


}

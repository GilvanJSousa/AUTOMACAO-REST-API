package org.br.com.testes.manager;

public class UsuarioManager {

    private static ThreadLocal<String> paymentId = new ThreadLocal<>();

    public static String getPaymentId() {
        return paymentId.get();
    }

    public static void setPaymentId(String id) {
        paymentId.set(id);
    }

    public static void remove() {
        paymentId.remove();
    }


}

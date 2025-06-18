package org.br.com.testes.manager;

public class CarrinhosManager {
    private static final ThreadLocal<String> id = new ThreadLocal<>();

    public static String getId() {
        return id.get();
    }

    public static String setId(String carrinhoId) {
        id.set(carrinhoId);
        return carrinhoId;
    }

    public static void remove() {
        id.remove();
    }
} 
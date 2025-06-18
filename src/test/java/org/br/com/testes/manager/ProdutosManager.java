package org.br.com.testes.manager;

public class ProdutosManager {
    private static final ThreadLocal<String> id = new ThreadLocal<>();

    public static String getId() {
        return id.get();
    }

    public static String setId(String produtoId) {
        id.set(produtoId);
        return produtoId;
    }

    public static void remove() {
        id.remove();
    }
}

package org.br.com.testes.manager;

public class TokenManager {
    private static final ThreadLocal<String> token = new ThreadLocal<>();

    public static String getToken() {
        if (token.get() == null) {
            throw new IllegalStateException("Token não encontrado. Realize o login primeiro.");
        }
        return token.get();
    }

    public static void setToken(String tk) {
        if (tk == null || tk.trim().isEmpty()) {
            throw new IllegalArgumentException("Token não pode ser nulo ou vazio");
        }
        token.set(tk);
    }

}

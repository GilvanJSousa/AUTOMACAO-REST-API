package org.br.com.testes.manager;

public class TokenManager {

    private static final ThreadLocal<String> token = new ThreadLocal<>();

	public static String getToken() {
        return token.get();
    }

	public static String setToken(String tk) {
        token.set(tk);
		return tk;
	}

    public static void remove() {
        token.remove();
    }

}

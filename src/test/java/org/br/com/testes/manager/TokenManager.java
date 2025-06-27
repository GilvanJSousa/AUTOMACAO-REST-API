package org.br.com.testes.manager;

import lombok.Getter;

public class TokenManager {
	private static final ThreadLocal<String> token = new ThreadLocal<>();

	public static String getToken() {
        return token.get();
    }

	public static void setToken(String tk) {
        token.set(tk);
    }

	public static void remove() {
        token.remove();
    }
}

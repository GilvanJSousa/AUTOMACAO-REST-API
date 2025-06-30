package org.br.com.testes.manager;

import lombok.Getter;

public class TokenManager {
	private static final ThreadLocal<String> token = new ThreadLocal<>();
	private static final ThreadLocal<String> userId = new ThreadLocal<>();

	public static String getToken() {
        return token.get();
    }

	public static void setToken(String tk) {
        token.set(tk);
    }

	public static String getUserId() {
        return userId.get();
    }

	public static void setUserId(String id) {
        userId.set(id);
    }

	public static void remove() {
        token.remove();
        userId.remove();
    }
}

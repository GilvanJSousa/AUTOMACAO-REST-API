package org.br.com.testes.manager;

import lombok.Getter;

public class TokenManager {
    @Getter
	private static String token;

	public static void setToken(String token) {
        TokenManager.token = token;
    }

}

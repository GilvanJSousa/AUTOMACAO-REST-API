package org.br.com.testes.manager;

public class TokenizacaoManager {

	public static ThreadLocal<String> cardToken = new ThreadLocal<>();

	public static String getCardToken() {
		return cardToken.get();
	}

	public static void setCardToken(String token) {
		cardToken.set(token);
	}

	public static void remove() {
		cardToken.remove();
	}

}

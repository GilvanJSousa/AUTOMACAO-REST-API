package org.br.com.testes.manager;

public class CategoriaManager {

	private static ThreadLocal<String> categoriaId = new ThreadLocal<String>();

	public static String getCategoriaId() {
		return categoriaId.get();
	}

	public static void setCategoriaId(String id) {
		categoriaId.set(id);
	}

	public static void remove() {
		categoriaId.remove();
	}

}

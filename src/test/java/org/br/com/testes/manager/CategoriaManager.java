package org.br.com.testes.manager;

public class CategoriaManager {

	public static ThreadLocal<String> categoriaId = new ThreadLocal<String>();

	public static String getCategoriaId() {
		return categoriaId.get();
	}

	public static String setCategoriaId(String id) {
		categoriaId.set(id);
		return id;
	}

	public static void remove() {
		categoriaId.remove();
	}
}

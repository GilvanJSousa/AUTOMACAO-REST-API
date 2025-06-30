package org.br.com.testes.manager;

import io.restassured.response.Response;

/**
 * Gerenciador de dados de artigos para testes.
 * Utiliza ThreadLocal para garantir isolamento entre threads de teste.
 */
public class ArtigosManager {

	private static final ThreadLocal<String> artigoId = new ThreadLocal<String>();
	private static final ThreadLocal<String> autorId = new ThreadLocal<String>();
	private static final ThreadLocal<String> categoriaId = new ThreadLocal<String>();
	private static final ThreadLocal<String> nomeCategoria = new ThreadLocal<String>();
	private static final ThreadLocal<String> nomeAutor = new ThreadLocal<String>();
	private static final ThreadLocal<Response> response = new ThreadLocal<Response>();

	public static String getArtigoId() {
		return artigoId.get();
	}

	public static String getAutorId() {
		return autorId.get();
	}

	public static String getCategoriaId() {
		return categoriaId.get();
	}

	public static String getNomeCategoria() {
		return nomeCategoria.get();
	}

	public static String getNomeAutor() {
		return nomeAutor.get();
	}

	public static Response getResponse() {
		return response.get();
	}

	public static void setArtigoId(String id) {
		artigoId.set(id);
	}

	public static void setAutorId(String id) {
		autorId.set(id);
	}

	public static void setCategoriaId(String id) {
		categoriaId.set(id);
	}

	public static void setNomeCategoria(String nome) {
		nomeCategoria.set(nome);
	}

	public static void setNomeAutor(String nome) {
		nomeAutor.set(nome);
	}

	public static void setResponse(Response resp) {
		response.set(resp);
	}

	public static void remove() {
		artigoId.remove();
		autorId.remove();
		categoriaId.remove();
		nomeCategoria.remove();
		nomeAutor.remove();
		response.remove();
	}
}

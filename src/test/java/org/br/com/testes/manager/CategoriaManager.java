package org.br.com.testes.manager;

/**
 * Gerenciador de dados de categoria para testes.
 * Utiliza ThreadLocal para garantir isolamento entre threads de teste.
 */
public class CategoriaManager {

	private static ThreadLocal<String> categoriaId = new ThreadLocal<String>();
	private static ThreadLocal<String> nomeCategoria = new ThreadLocal<String>();
	private static ThreadLocal<String> descricaoCategoria = new ThreadLocal<String>();

	public static String getCategoriaId() {
		return categoriaId.get();
	}

	public static String getNomeCategoria() {
		return nomeCategoria.get();
	}

	/**
	 * Retorna a descrição da categoria criada no cenário atual.
	 * @return descrição da categoria
	 */
	public static String getDescricaoCategoria() {
		return descricaoCategoria.get();
	}

	public static void setCategoriaId(String id) {
		categoriaId.set(id);
	}

	public static void setNomeCategoria(String nome) {
		nomeCategoria.set(nome);
	}

	public static void setDescricaoCategoria(String descricao) {
		descricaoCategoria.set(descricao);
	}

	public static void remove() {
		categoriaId.remove();
		nomeCategoria.remove();
		descricaoCategoria.remove();
	}

}

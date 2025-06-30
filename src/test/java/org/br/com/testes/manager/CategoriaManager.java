package org.br.com.testes.manager;

/**
 * Gerenciador de dados de categoria para testes.
 * Utiliza ThreadLocal para garantir isolamento entre threads de teste.
 */
public class CategoriaManager {

	private static ThreadLocal<String> categoriaId = new ThreadLocal<String>();

	/**
	 * Obtém o ID da categoria armazenado.
	 * @return ID da categoria ou null se não existir
	 */
	public static String getCategoriaId() {
		return categoriaId.get();
	}

	/**
	 * Define o ID da categoria para uso nos testes.
	 * @param id ID da categoria a ser armazenado
	 */
	public static void setCategoriaId(String id) {
		categoriaId.set(id);
	}

	/**
	 * Remove o ID da categoria armazenado.
	 * Utilizado para limpeza entre testes.
	 */
	public static void remove() {
		categoriaId.remove();
	}

	/**
	 * Verifica se existe um ID de categoria armazenado.
	 * @return true se existir ID, false caso contrário
	 */
	public static boolean hasCategoriaId() {
		return categoriaId.get() != null;
	}
}

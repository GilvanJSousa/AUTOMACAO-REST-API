package org.br.com.testes.utils;

import com.github.javafaker.Faker;
import lombok.extern.apachecommons.CommonsLog;
import org.br.com.testes.manager.CategoriaManager;
import org.br.com.testes.manager.UsuarioManager;
import org.br.com.testes.model.UsuarioRequest;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.br.com.testes.utils.FakerApiData.gerarSenhaValida;

@CommonsLog
public class JavaFaker {

	private static final Faker faker = new Faker(new Locale.Builder().setLanguage("pt").setRegion("BR").build());

	/**
	 * Gera dados de atualizacao contendo apenas nomeUsuario e senha.
	 * Utilizado para requisições de atualizacao parcial de usuario.
	 *
	 * @return Map contendo apenas os campos nomeUsuario e senha para atualizacao
	 */
	public static Map<String, String> DadosAtualizacaoJavaFake() {
		Map<String, String> dadosAtualizacao = new HashMap<>();
		dadosAtualizacao.put("nomeUsuario", faker.name().firstName());
		dadosAtualizacao.put("senha", gerarSenhaValida());
		return dadosAtualizacao;
	}

	/**
	 * Gera dados únicos para categoria usando JavaFaker.
	 * Evita conflitos de nome ao gerar nomes aleatórios.
	 *
	 * @return Map contendo nome e descricao únicos para categoria
	 */
	public static Map<String, String> categoriaJavaFake() {
		Map<String, String> categoria = new HashMap<>();
		
		// Gera nome único baseado em categoria + timestamp
		String categoriaBase = faker.commerce().department();
		String nomeUnico = categoriaBase + "_" + System.currentTimeMillis();
		
		// Gera descrição baseada no nome da categoria
		String descricao = "Artigos sobre " + categoriaBase.toLowerCase();
		
		categoria.put("nome", nomeUnico);
		categoria.put("descricao", descricao);
		
		return categoria;
	}

	/**
	 * Gera dados para teste de artigo usando nomes específicos.
	 * Usa dados que funcionam com a API.
	 * Se nomeAutor for nulo ou vazio, utiliza o nome completo do usuário logado via UsuarioManager.
	 *
	 * @param nomeAutor Nome do autor do artigo (ou null para usar o usuário logado)
	 * @param nomeCategoria Nome da categoria do artigo
	 * @return Map contendo titulo, conteudo, nomeAutor, nomeCategoria e dataPublicacao
	 */
	public static Map<String, String> artigosTesteFixo(String nomeAutor, String nomeCategoria) {
		if (nomeAutor == null || nomeAutor.isEmpty()) {
			// Usa o nome completo do usuário logado, se disponível
			nomeAutor = UsuarioManager.getNomeCompletoUsuario();
		}
		Map<String, String> artigo = new HashMap<>();
		artigo.put("titulo", "Introdução aos Testes Automatizados");
		artigo.put("conteudo", "Exemplos de ferramentas de testes automatizados...");
		artigo.put("nomeAutor", nomeAutor);
		artigo.put("nomeCategoria", nomeCategoria);
		artigo.put("dataPublicacao", gerarDataPublicacao());
		return artigo;
	}

	/**
	 * Gera data de publicação no formato ISO 8601.
	 * Retorna a data atual no formato esperado pela API.
	 *
	 * @return Data de publicação formatada
	 */
	private static String gerarDataPublicacao() {
		java.time.LocalDateTime agora = java.time.LocalDateTime.now();
		java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		return agora.format(formatter);
	}

	/**
	 * Gera dados para atualização de artigo conforme o curl fornecido.
	 * Usa apenas titulo e conteudo para atualização.
	 *
	 * @return Map contendo titulo e conteudo para atualização
	 */
	public static Map<String, String> dadosAtualizacaoArtigo() {
		Map<String, String> dadosAtualizacao = new HashMap<>();
		dadosAtualizacao.put("titulo", "string");
		dadosAtualizacao.put("conteudo", "string");
		return dadosAtualizacao;
	}

}

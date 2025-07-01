package org.br.com.testes.utils;

import com.github.javafaker.Faker;
import lombok.extern.apachecommons.CommonsLog;
import org.br.com.testes.manager.CategoriaManager;
import org.br.com.testes.manager.UsuarioManager;
import org.br.com.testes.model.UsuarioRequest;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@CommonsLog
public class JavaFaker {

	private static final Faker faker = new Faker(new Locale.Builder().setLanguage("pt").setRegion("BR").build());

	/**
	 * Gera um usuário CMS fake com dados aleatórios completos.
	 * Método estático que não requer instanciação da classe.
	 * Garante consistência entre nomeCompleto, nomeUsuario e email.
	 *
	 * @return um objeto UsuarioCms com dados fictícios completos
	 */
	public static UsuarioRequest UsuarioJavaFake() {
		String fullName = faker.name().fullName();
		String[] nameParts = fullName.split(" ");

		String firstName = nameParts[0];
		String lastName = nameParts.length > 1 ? nameParts[nameParts.length - 1] : firstName;

		String email = gerarEmailPersonalizado(firstName, lastName);

		return UsuarioRequest.builder()
				.nomeCompleto(fullName)
				.nomeUsuario(lastName)
				.email(email)
				.senha(gerarSenhaValida())
				.build();
	}

	/**
	 * Gera um email personalizado usando firstName e lastName.
	 * Formato: firstName.lastName@exemplo.com
	 *
	 * @param firstName Primeiro nome
	 * @param lastName Sobrenome
	 * @return Email personalizado
	 */
	private static String gerarEmailPersonalizado(String firstName, String lastName) {
		String emailBase = firstName.toLowerCase() + "." + lastName.toLowerCase();
		return emailBase + "@exemplo.com";
	}

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
	 * Gera dados de atualizacao contendo apenas nomeUsuario e senha.
	 * Utiliza o firstName do usuario fornecido para o nomeUsuario.
	 * Utilizado para requisições de atualizacao parcial de usuario.
	 *
	 * @param usuario UsuarioRequest do qual extrair o firstName
	 * @return Map contendo apenas os campos nomeUsuario e senha para atualizacao
	 */
	public static Map<String, String> dadosAtualizacaoJavaFake(UsuarioRequest usuario) {
		Map<String, String> dadosAtualizacao = new HashMap<>();
		
		// Extrai o firstName do nomeCompleto do usuario
		String fullName = usuario.getNomeCompleto();
		String[] nameParts = fullName.split(" ");
		String firstName = nameParts[0];
		
		dadosAtualizacao.put("nomeUsuario", firstName);
		dadosAtualizacao.put("senha", gerarSenhaValida());
		return dadosAtualizacao;
	}

	/**
	 * Gera uma senha válida que atende aos requisitos da API.
	 * Requisitos: pelo menos 8 caracteres, uma letra maiúscula e um número.
	 *
	 * @return uma senha válida
	 */
	private static String gerarSenhaValida() {
		return faker.internet().password(8, 12) + "A1";
	}

	public static UsuarioRequest fakerSimples() {
		return UsuarioRequest.builder()
				.nomeCompleto(faker.name().fullName())
				.nomeUsuario(faker.name().lastName())
				.email(faker.internet().emailAddress())
				.senha(gerarSenhaValida())
				.build();
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
	 * Gera dados para criação de artigo conforme o contrato do POST /artigos.
	 *
	 * @return Map contendo titulo, conteudo, nomeAutor, nomeCategoria e dataPublicacao
	 */
	public static Map<String, String> artigosJavaFake() {
		Map<String, String> artigo = new HashMap<>();
		artigo.put("titulo", "Introdução aos Testes Automatizados");
		artigo.put("conteudo", "Exemplos de ferramentas de testes automatizados...");
		artigo.put("nomeAutor", "Usuario");
		artigo.put("nomeCategoria", "Tecnologia");
		artigo.put("dataPublicacao", "2024-03-21T10:00:00Z");
		return artigo;
	}

	/**
	 * Gera dados para criação de artigo usando o nome do usuário logado.
	 *
	 * @param nomeUsuario Nome do usuário logado
	 * @param nomeCategoria Nome da categoria criada
	 * @return Map contendo titulo, conteudo, nomeAutor, nomeCategoria e dataPublicacao
	 */
	public static Map<String, String> artigosJavaFake(String nomeUsuario, String nomeCategoria) {
		Map<String, String> artigo = new HashMap<>();
		artigo.put("titulo", "Introdução aos Testes Automatizados");
		artigo.put("conteudo", "Exemplos de ferramentas de testes automatizados...");
		artigo.put("nomeAutor", nomeUsuario);
		artigo.put("nomeCategoria", nomeCategoria);
		artigo.put("dataPublicacao", "2024-03-21T10:00:00Z");
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
	 * Exemplo de uso do método artigosJavaFake.
	 * Demonstra como os dados são gerados e podem ser utilizados.
	 *
	 * @return String com exemplo de dados gerados
	 */
	public static String exemploArtigosJavaFake() {
		Map<String, String> artigo = artigosJavaFake();
		
		StringBuilder exemplo = new StringBuilder();
		exemplo.append("Exemplo de dados gerados para artigo:\n");
		exemplo.append("{\n");
		exemplo.append("  \"titulo\": \"").append(artigo.get("titulo")).append("\",\n");
		exemplo.append("  \"conteudo\": \"").append(artigo.get("conteudo")).append("\",\n");
		exemplo.append("  \"nomeAutor\": \"").append(artigo.get("nomeAutor")).append("\",\n");
		exemplo.append("  \"nomeCategoria\": \"").append(artigo.get("nomeCategoria")).append("\",\n");
		exemplo.append("  \"dataPublicacao\": \"").append(artigo.get("dataPublicacao")).append("\"\n");
		exemplo.append("}");
		
		return exemplo.toString();
	}

	/**
	 * Método para testar e demonstrar o uso do artigosJavaFake.
	 * Pode ser usado para debug ou validação dos dados gerados.
	 */
	public static void demonstrarArtigosJavaFake() {
		System.out.println("=== Demonstração do método artigosJavaFake ===");
		System.out.println(exemploArtigosJavaFake());
		System.out.println("=== Fim da demonstração ===");
	}

	/**
	 * Gera dados fixos para teste de artigo conforme o curl fornecido.
	 * Usa dados que funcionam com a API.
	 *
	 * @return Map contendo titulo, conteudo, nomeAutor, nomeCategoria e dataPublicacao
	 */
	public static Map<String, String> artigosTesteFixo() {
		Map<String, String> artigo = new HashMap<>();
		artigo.put("titulo", "Introdução aos Testes Automatizados");
		artigo.put("conteudo", "Exemplos de ferramentas de testes automatizados...");
		artigo.put("nomeAutor", "João Silva"); // Nome mais realista
		artigo.put("nomeCategoria", "Tecnologia");
		artigo.put("dataPublicacao", "2025-06-30T13:15:04.733Z");
		return artigo;
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
			nomeAutor = org.br.com.testes.manager.UsuarioManager.getNomeCompletoUsuario();
		}
		Map<String, String> artigo = new HashMap<>();
		artigo.put("titulo", "Introdução aos Testes Automatizados");
		artigo.put("conteudo", "Exemplos de ferramentas de testes automatizados...");
		artigo.put("nomeAutor", nomeAutor);
		artigo.put("nomeCategoria", nomeCategoria);
		artigo.put("dataPublicacao", "2025-06-30T13:15:04.733Z");
		return artigo;
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

	/**
	 * Gera dados para teste de artigo usando IDs de autor e categoria.
	 * Usa dados que funcionam com a API quando o backend espera IDs ao invés de nomes.
	 *
	 * @param autorId ID do autor do artigo
	 * @param categoriaId ID da categoria do artigo
	 * @return Map contendo titulo, conteudo, autorId, categoriaId e dataPublicacao
	 */
	public static Map<String, Object> artigosTesteFixoComIds(String autorId, String categoriaId) {
		Map<String, Object> artigo = new HashMap<>();
		artigo.put("titulo", "Introdução aos Testes Automatizados");
		artigo.put("conteudo", "Exemplos de ferramentas de testes automatizados...");
		artigo.put("autorId", autorId);
		artigo.put("categoriaId", categoriaId);
		artigo.put("dataPublicacao", "2025-06-30T13:15:04.733Z");
		return artigo;
	}

	/**
	 * Exemplo de uso do método artigosJavaFake.
	 */

}

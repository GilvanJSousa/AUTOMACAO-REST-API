package org.br.com.testes.utils;

import com.github.javafaker.Faker;
import lombok.extern.apachecommons.CommonsLog;
import org.br.com.testes.model.UsuarioRequest;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@CommonsLog
public class JavaFaker {

	private static final Faker faker = new Faker(new Locale("pt-BR"));

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
}

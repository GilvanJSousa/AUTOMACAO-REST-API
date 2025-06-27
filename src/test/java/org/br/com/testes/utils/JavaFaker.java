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
}

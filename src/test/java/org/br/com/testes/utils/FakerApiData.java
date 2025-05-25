package org.br.com.testes.utils;

import com.github.javafaker.Faker;
import lombok.Data;
import lombok.Getter;
import lombok.extern.apachecommons.CommonsLog;
import org.br.com.testes.model.Usuario;

import java.util.Locale;

@CommonsLog
@Data
public class FakerApiData {
    private static final Faker faker = new Faker(new Locale("pt-BR"));
    private static Usuario usuarioFaker;

    @Getter
    private static String name;
    @Getter
    private static String email;
    @Getter
    private static String senha;

    /**
     * Gera um usuário CMS fake com dados aleatórios ou retorna o usuário padrao.
     *
     * @return um objeto Usuario com dados fictícios
     */
    public static Usuario gerarUsuarioFake() {
        if (usuarioFaker == null) {
            usuarioFaker = new Usuario();
            usuarioFaker.setNomeCompleto(faker.name().fullName());
            usuarioFaker.setNomeUsuario(faker.name().username());
            usuarioFaker.setEmail(faker.internet().emailAddress());
            usuarioFaker.setSenha(gerarSenhaValida());
        }
        return usuarioFaker;
    }

    /**
     * Gera uma senha válida que atende aos requisitos da API.
     * Requisitos: pelo menos 8 caracteres, uma letra maiúscula e um número.
     *
     * @return uma senha válida
     */
    private static String gerarSenhaValida() {
        String senha = faker.internet().password(8, 12) + "A1";
        return senha;
    }
}

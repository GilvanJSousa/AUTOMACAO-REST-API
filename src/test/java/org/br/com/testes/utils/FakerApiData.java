package org.br.com.testes.utils;

import com.github.javafaker.Faker;
import lombok.Data;
import lombok.Getter;
import lombok.extern.apachecommons.CommonsLog;
import org.br.com.testes.model.UsuarioResponse;

import java.util.Locale;

@CommonsLog
@Data
public class FakerApiData {
    private static final Faker faker = new Faker(new Locale("pt-BR"));
    private static UsuarioResponse usuarioFaker;

    @Getter
    private static String name;
    @Getter
    private static String email;
    @Getter
    private static String password;

    /**
     * Gera um usuário CMS fake com dados aleatórios ou retorna o usuário padrao.
     *
     * @return um objeto UsuarioResponse com dados fictícios
     */
//    public static UsuarioResponse gerarUsuarioFake() {
//        if (usuarioFaker == null) {
//            usuarioFaker = new UsuarioResponse(
//                faker.name().fullName(),
//                faker.name().username(),
//                faker.internet().emailAddress(),
//                gerarSenhaValida()
//            );
//        }
//        return usuarioFaker;
//    }

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

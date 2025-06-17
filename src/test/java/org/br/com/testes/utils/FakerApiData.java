package org.br.com.testes.utils;

import com.github.javafaker.Faker;
import lombok.Builder;
import lombok.Data;
import lombok.extern.apachecommons.CommonsLog;
import org.br.com.testes.model.UsuarioResponse;

import java.util.Locale;

@CommonsLog
@Data
@Builder
public class FakerApiData {
    private static final Faker faker = new Faker(new Locale("pt-BR"));

    private String nome;
    private String email;
    private String senha;
    private String administrador;

    /**
     * Gera um usuário com dados aleatórios
     * @return FakerApiData com dados fictícios
     */
    public static FakerApiData gerarUsuarioFake() {
        return FakerApiData.builder()
                .nome(faker.name().fullName())
                .email(faker.internet().emailAddress())
                .senha("pwd123")
                .administrador("true")
                .build();
    }
}

package org.br.com.testes.utils;

import com.github.javafaker.Faker;
import lombok.Builder;
import lombok.Data;
import lombok.extern.apachecommons.CommonsLog;

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

    private Integer preco;
    private String descricao;
    private Integer quantidade;

    /**
     * Gera um usuário com dados aleatórios
     * @return FakerApiData com dados fictícios
     */
    public static FakerApiData gerarUsuarioFake() {
        return FakerApiData.builder()
                .nome(faker.name().fullName())
                .email(faker.internet().emailAddress())
                .senha("@password" + faker.number().randomDigit())
                .administrador("true")
                .build();
    }

    public static FakerApiData gerarProdutoFake() {
        return FakerApiData.builder()
                .nome(faker.commerce().productName())
                .preco(faker.number().numberBetween(1, 1000))
                .descricao(faker.commerce().department())
                .quantidade(faker.number().numberBetween(1, 1000))
                .build();
    }
}

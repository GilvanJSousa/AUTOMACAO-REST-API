package org.br.com.testes.model;

import lombok.Builder;

@Builder
public record UsuarioResquest(
        String nome,
        String email,
        String password,
        String administrador
) { }

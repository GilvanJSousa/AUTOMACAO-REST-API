package org.br.com.testes.model;

import lombok.Builder;

@Builder
public record UsuarioResponse(
    String nomeCompleto,
    String nomeUsuario,
    String email,
    String senha
) {} 
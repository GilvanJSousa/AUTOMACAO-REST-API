package org.br.com.testes.model;

public record UsuarioResponse(
    String nomeCompleto,
    String nomeUsuario,
    String email,
    String senha
) {} 
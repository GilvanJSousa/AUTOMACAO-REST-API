package org.br.com.testes.model;

import lombok.Builder;

@Builder
public class UsuarioResquest {
        private String nome;
        private String email;
        private String password;
        private String administrador;
 }

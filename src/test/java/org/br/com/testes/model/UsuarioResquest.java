package org.br.com.testes.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResquest {
    private String nome;
    private String email;
    private String password;
    private String administrador;
}

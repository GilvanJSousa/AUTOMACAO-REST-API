package org.br.com.testes.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    private String nomeCompleto;
    private String nomeUsuario;
    private String email;
    private String senha;

}

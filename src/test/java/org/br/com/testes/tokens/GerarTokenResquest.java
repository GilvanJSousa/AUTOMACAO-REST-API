package org.br.com.testes.tokens;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GerarTokenResquest {
    private String email;
    private String senha;
}

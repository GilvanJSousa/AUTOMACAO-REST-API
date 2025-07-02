package org.br.com.testes.tokens;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GerarTokenResquest {
    private String email;
    private String senha;
}

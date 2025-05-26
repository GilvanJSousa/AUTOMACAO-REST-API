package org.br.com.testes.tokens;

import lombok.Builder;

@Builder
public record GerarTokenResquest(String email, String password) {
}

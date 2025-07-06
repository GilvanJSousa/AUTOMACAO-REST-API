package org.br.com.testes.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TransferenciaRequest {

    private String contaOrigem;

    private String contaDestino;

    private Double valor;

    private String token;

}

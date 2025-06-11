package org.br.com.testes.model.tokenizacao;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TokenizacaoRequest {
    private String customerName;
    private String cardNumber;
    private String holder;
    private String expirationDate;
    private String brand;
}

package org.br.com.testes.model.capturaDeTransacoes;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CapturaDeTransacoesRequest {
    private Integer amount;
    private Integer status;
    private String returnCode;
    private String returnMessage;
    private Link[] links;

    @Getter
    @Setter
    @Builder
    public static class Link {
        private String method;
        private String rel;
        private String href;
    }
}

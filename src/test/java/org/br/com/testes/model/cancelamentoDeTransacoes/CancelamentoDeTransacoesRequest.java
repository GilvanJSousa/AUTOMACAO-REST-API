package org.br.com.testes.model.cancelamentoDeTransacoes;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CancelamentoDeTransacoesRequest {
    @JsonProperty("Amount")
    private Integer amount;

    @JsonProperty("Status")
    private Integer status;

    @JsonProperty("ReturnCode")
    private String returnCode;

    @JsonProperty("ReturnMessage")
    private String returnMessage;

    @JsonProperty("Links")
    private Link[] links;

    @Getter
    @Setter
    @Builder
    public static class Link {
        @JsonProperty("Method")
        private String method;

        @JsonProperty("Rel")
        private String rel;

        @JsonProperty("Href")
        private String href;
    }
}

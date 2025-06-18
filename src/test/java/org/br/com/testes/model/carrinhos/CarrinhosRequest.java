package org.br.com.testes.model.carrinhos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarrinhosRequest {
    private List<ProdutoCarrinho> produtos;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProdutoCarrinho {
        @JsonProperty("idProduto")
        private String idProduto;
        private Integer quantidade;
    }
} 
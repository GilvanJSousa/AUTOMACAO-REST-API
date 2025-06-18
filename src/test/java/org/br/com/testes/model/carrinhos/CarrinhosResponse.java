package org.br.com.testes.model.carrinhos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class CarrinhosResponse {

    @JsonProperty("_id")
    private String id;

    private List<ProdutoCarrinhoDetalheResponse> produtos;
    private Integer precoTotal;
    private Integer quantidadeTotalDeItems;
    private String idUsuario;
    private String message;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ProdutoCarrinhoDetalheResponse {
        @JsonProperty("idProduto")
        private String idProduto;
        private Integer quantidade;
        private Integer precoUnitario;
    }
}

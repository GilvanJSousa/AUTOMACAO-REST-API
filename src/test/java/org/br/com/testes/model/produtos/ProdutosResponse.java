package org.br.com.testes.model.produtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProdutosResponse {

    @JsonProperty("_id")
    private String id;

    private String nome;
    private Integer preco;
    private String descricao;
    private Integer quantidade;
    private String message;
}

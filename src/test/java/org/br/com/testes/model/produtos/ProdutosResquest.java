package org.br.com.testes.model.produtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProdutosResquest {
    private String nome;
    private Integer preco;
    private String descricao;
    private Integer quantidade;
}

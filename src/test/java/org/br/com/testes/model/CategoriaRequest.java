package org.br.com.testes.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoriaRequest {
	private String nome;
	private String descricao;
}

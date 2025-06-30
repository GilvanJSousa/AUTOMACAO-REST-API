package org.br.com.testes.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ArtigosRequest {
	private String titulo;
	private String conteudo;
	private String nomeAutor;
	private String nomeCategoria;
	private String dataPublicacao;
}

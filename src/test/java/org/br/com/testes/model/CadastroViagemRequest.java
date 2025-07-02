package org.br.com.testes.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CadastroViagemRequest {
	private String acompanhate;
	private String dataPartida;
	private String dataRetorno;
	private String localDeDestino;
	private String regiao;
}

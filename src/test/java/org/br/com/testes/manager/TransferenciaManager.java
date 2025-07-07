package org.br.com.testes.manager;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferenciaManager {

	private static final ThreadLocal<String> idTransferencia = new ThreadLocal<>();

	public static String getIdTransferencia() {
		return idTransferencia.get();
	}

	public static void setIdTransferencia(String id) {
		idTransferencia.set(id);
	}

	public static void remove() {
		idTransferencia.remove();
	}


}

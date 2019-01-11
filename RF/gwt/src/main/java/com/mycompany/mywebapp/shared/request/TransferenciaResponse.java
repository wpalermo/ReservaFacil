package com.mycompany.mywebapp.shared.request;

import com.mycompany.mywebapp.shared.bean.Transferencia;

import java.io.Serializable;
import java.util.List;

public class TransferenciaResponse implements Serializable {
	
	private Transferencia transferencia;
	private List<Transferencia> transferencias;
	private String message;

	public TransferenciaResponse() {
		
	}
	
	public TransferenciaResponse(String message) {
		this.message = message;
	}
	
	public Transferencia getTransferencia() {
		return transferencia;
	}

	public void setTransferencia(Transferencia transferencia) {
		this.transferencia = transferencia;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	public List<Transferencia> getTransferencias() {
		return transferencias;
	}

	public void setTransferencias(List<Transferencia> transferencias) {
		this.transferencias = transferencias;
	}
}

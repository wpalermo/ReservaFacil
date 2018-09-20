package com.mycompany.mywebapp.shared;

public class TransferenciaResponse {
	
	private Transferencia transferencia;
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
	

}

package br.com.cvc.taxas.request;

import java.time.LocalDate;

public class TaxaRequest {
	
	private LocalDate dataAgendamento;
	private LocalDate dataTransacao;
	private Float valor;
	
	
	public LocalDate getDataAgendamento() {
		return dataAgendamento;
	}
	public void setDataAgendamento(LocalDate dataAgendamento) {
		this.dataAgendamento = dataAgendamento;
	}
	public LocalDate getDataTransacao() {
		return dataTransacao;
	}
	public void setDataTransacao(LocalDate dataTransacao) {
		this.dataTransacao = dataTransacao;
	}
	public Float getValor() {
		return valor;
	}
	public void setValor(Float valor) {
		this.valor = valor;
	}
	
	

}

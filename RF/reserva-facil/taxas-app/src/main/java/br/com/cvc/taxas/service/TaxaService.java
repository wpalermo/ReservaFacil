package br.com.cvc.taxas.service;

import java.time.LocalDate;

import br.com.cvc.taxas.request.TaxaRequest;

public interface TaxaService {
	
	
	/**
	 * Servico responsavel por calcular a taxa de agendamento de acordo com as datas de transferencia e agendamento
	 * @param dataTransaferencia
	 * @param dataAgendamento
	 * @param valor
	 * @return Float
	 */
	Float calcularTaxa(LocalDate dataTransaferencia, LocalDate dataAgendamento, Float valor);

	Float calcularTaxa(TaxaRequest taxa);
	
	
}

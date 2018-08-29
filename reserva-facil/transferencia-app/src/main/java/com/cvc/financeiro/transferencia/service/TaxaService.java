package com.cvc.financeiro.transferencia.service;

import java.time.LocalDate;

public interface TaxaService {
	
	
	/**
	 * Servico responsavel por calcular a taxa de agendamento de acordo com as datas de transferencia e agendamento
	 * @param dataTransaferencia
	 * @param dataAgendamento
	 * @param valor
	 * @return Float
	 */
	Float calcularTaxa(LocalDate dataTransaferencia, LocalDate dataAgendamento, Float valor);

}
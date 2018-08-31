package com.cvc.financeiro.transferencia.service;

import com.cvc.financeiro.transferencia.entities.Transferencia;

public interface TaxaService {
	
	
	/**
	 * Servico responsavel por calcular a taxa de agendamento de acordo com as datas de transferencia e agendamento
	 * @param dataTransaferencia
	 * @param dataAgendamento
	 * @param valor
	 * @return Float
	 */
	void calcularTaxa(Transferencia transferencia);


}

package com.cvc.financeiro.transferencia.service;

import java.util.List;

import com.cvc.financeiro.transferencia.entities.Transferencia;
import com.cvc.financeiro.transferencia.utils.StatusTransferenciaEnum;

public interface TransferenciaService {
	
	
	/**
	 * Busca todas as transferencias feitas
	 * @return
	 */
	List<Transferencia> buscarTodasTransferencias();

	/**
	 * Realiza a transferencia de valores de uma conta origem para uma destino. 
	 * Busca no banco as transferencias que devem ser realizadas de acordo com a data de transferencia
	 * @param transferencia
	 * @return
	 */
	void realizarTransferencia();
	
	
	/**
	 * Agenda a transferencia. Usa o servico de taxa para setar o valor da taxa.
	 * @param transferencia
	 */
	
	void agendarTransferencia(Transferencia transferencia);
	
	void atualizarStatus(Transferencia transferencia, StatusTransferenciaEnum status);

	
}

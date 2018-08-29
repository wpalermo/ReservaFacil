package com.cvc.financeiro.transferencia.service;

import java.util.List;

import com.cvc.financeiro.transferencia.entities.Transferencia;

public interface TransferenciaService {
	
	
	/**
	 * Busca todas as transferencias feitas
	 * @return
	 */
	List<Transferencia> buscarTodasTransferencias();

	/**
	 * Realiza a transferencia de valores de uma conta origem para uma destino. 
	 * Usa o service de taxas para calcular o valor das taxas.
	 * @param transferencia
	 * @return
	 */
	Transferencia realizarTransferencia(Transferencia transferencia);

	
}

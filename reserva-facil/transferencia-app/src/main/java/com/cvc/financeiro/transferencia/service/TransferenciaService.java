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
	 * Busca no banco as transferencias que devem ser realizadas de acordo com a data de transferencia e seu status
	 * @param transferencia
	 * @return
	 */
	void realizarTransferencia();
	
	
	
	
	/**
	 * Agenda a transferencia. Usa o servico de taxa para setar o valor da taxa.
	 * @param transferencia
	 */
	
	void agendarTransferencia(Transferencia transferencia);
	
	
	/**
	 * Atualiza o status de uma transferencia
	 * @param transferencia
	 * @param status
	 */
	void atualizarStatus(Transferencia transferencia, StatusTransferenciaEnum status);

	void atualizarTaxa(Transferencia transferencia);
	
	
	/**
	 * Busca as transferencias com status AGUARDANDO_CALCULO_TAXA e reenvia para o servico de calculo de taxas
	 */
	void recalcularTaxas();

	
}

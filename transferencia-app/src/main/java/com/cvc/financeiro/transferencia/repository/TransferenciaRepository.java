package com.cvc.financeiro.transferencia.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cvc.financeiro.transferencia.entities.Transferencia;
import com.cvc.financeiro.transferencia.utils.StatusTransferenciaEnum;


@Repository
public interface TransferenciaRepository extends CrudRepository<Transferencia, String>{
	
	/**
	 * Busca todas as listas referente a uma data de transacao
	 * @param dataTransferencia
	 * @return
	 */
	List<Transferencia> findByDataTransferencia(LocalDate dataTransferencia);
	
	
	/**
	 * Busca todas as listas referente a uma data de transacao e status
	 * @param dataTransferencia
	 * @param status
	 * @return
	 */
	List<Transferencia> findByDataTransferenciaAndStatus(LocalDate dataTransferencia, StatusTransferenciaEnum status);

}

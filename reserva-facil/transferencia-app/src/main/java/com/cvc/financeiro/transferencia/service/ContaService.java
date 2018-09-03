package com.cvc.financeiro.transferencia.service;

import java.util.List;

import com.cvc.financeiro.transferencia.entities.Conta;

public interface ContaService {
	
	/**
	 * Atualiza dos valores de uma conta
	 * @param conta
	 * @return
	 */
	void atualizaConta(List<Conta> contas);
	
	void atualizaConta(Conta conta);
	
	/**
	 * Busca uma conta
	 * @param String conta
	 * @return Conta
	 */
	Conta buscarConta(String conta);
	
	/**
	 * Busca uma conta
	 * @param String conta
	 * @return Conta
	 */
	List<Conta> buscarTodasContas();
	
	/**
	 * Busca uma conta e verifica se é valida ou nao
	 * @param conta
	 * @return
	 */
	boolean isValida(String conta);
	
	/**
	 * Cadastra uma nova conta (apenas para teste 'integrado')
	 * @param conta
	 */
	void cadastraConta(Conta conta);

}

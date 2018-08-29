package com.cvc.financeiro.transferencia.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cvc.financeiro.transferencia.entities.Conta;
import com.cvc.financeiro.transferencia.entities.Transferencia;
import com.cvc.financeiro.transferencia.exception.TransferenciaException;
import com.cvc.financeiro.transferencia.repository.TransferenciaRepository;
import com.cvc.financeiro.transferencia.service.ContaService;
import com.cvc.financeiro.transferencia.service.TaxaService;
import com.cvc.financeiro.transferencia.service.TransferenciaService;
import com.google.common.collect.Lists;

@Service
public class TransferenciaServiceImpl implements TransferenciaService{
	
	@Autowired
	private TaxaService taxaService;
	
	@Autowired
	private ContaService contaService;
	
	@Autowired
	private TransferenciaRepository transferenciaRepository;

	@Override
	public Transferencia realizarTransferencia(Transferencia transferencia) {
		
		//Valida se contas existem na base.
		if(!contaService.isValida(transferencia.getContaDestrino()) || contaService.isValida(transferencia.getContaOrigem()))
			throw new TransferenciaException("Conta Invalida");

		//Chama o servico para calcular taxas
		Float taxa = taxaService.calcularTaxa(transferencia.getDataTransferencia(), transferencia.getDataAgendamento(), transferencia.getValor());
		transferencia.setTaxa(taxa);

		
		Conta contaOrigem = contaService.buscarConta(transferencia.getContaOrigem());
		
		//Verifica se a conta tem saldo suficiente para a transferencia
		if(contaOrigem.getSaldo() < (transferencia.getValor() + taxa))
			throw new TransferenciaException("Saldo Insuficiente");
			
		Conta contaDestino = contaService.buscarConta(transferencia.getContaDestrino());
		
		//Faz as alterações nos saldos de ambas as contas
		contaOrigem.setSaldo(contaOrigem.getSaldo() - (transferencia.getValor() + taxa));
		contaDestino.setSaldo(contaDestino.getSaldo() + transferencia.getValor());
		
		//atualiza as contas e salva a transferencia
		contaService.atualizaConta(Arrays.asList(contaOrigem, contaDestino));
		transferenciaRepository.save(transferencia);
		
		return transferencia;
	}
	

	@Override
	public List<Transferencia> buscarTodasTransferencias() {
		return Lists.newArrayList(transferenciaRepository.findAll());
	}
}

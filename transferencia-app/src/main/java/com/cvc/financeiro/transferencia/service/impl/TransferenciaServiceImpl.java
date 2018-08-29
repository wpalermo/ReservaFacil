package com.cvc.financeiro.transferencia.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cvc.financeiro.transferencia.entities.Conta;
import com.cvc.financeiro.transferencia.entities.Transferencia;
import com.cvc.financeiro.transferencia.exception.TransferenciaException;
import com.cvc.financeiro.transferencia.repository.ContaRepository;
import com.cvc.financeiro.transferencia.repository.TransferenciaRepository;
import com.cvc.financeiro.transferencia.service.TaxaService;
import com.cvc.financeiro.transferencia.service.TransferenciaService;
import com.google.common.collect.Lists;

@Service
public class TransferenciaServiceImpl implements TransferenciaService{
	
	@Autowired
	private TaxaService taxaService;
	
	@Autowired
	private ContaRepository contaRepository;
	
	@Autowired
	private TransferenciaRepository transferenciaRepository;
	
	@Override
	public List<Transferencia> buscarTodasTransferencias() {
		return Lists.newArrayList(transferenciaRepository.findAll());
	}

	@Override
	public Transferencia realizarTransferencia(Transferencia transferencia) {
		
		Float taxa = taxaService.calcularTaxa(transferencia.getDataTransferencia(), transferencia.getDataAgendamento(), transferencia.getValor());
		
		transferencia.setTaxa(taxa);
		
		Conta contaOrigem = contaRepository.findById(transferencia.getContaOrigem()).get();
		
		if(contaOrigem.getSaldo() < transferencia.getValor())
			throw new TransferenciaException("Saldo Insuficiente");
			
		contaOrigem.setSaldo(contaOrigem.getSaldo() - (transferencia.getValor() + taxa));
		Conta contaDestino = contaRepository.findById(transferencia.getContaDestrino()).get();
		contaDestino.setSaldo(contaDestino.getSaldo() + transferencia.getValor());
		
		contaRepository.saveAll(Arrays.asList(contaOrigem, contaDestino));
		
		transferenciaRepository.save(transferencia);
		
		return transferencia;
	}


}

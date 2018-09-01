package com.cvc.financeiro.transferencia.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cvc.financeiro.transferencia.entities.Conta;
import com.cvc.financeiro.transferencia.repository.ContaRepository;
import com.cvc.financeiro.transferencia.service.ContaService;
import com.google.common.collect.Lists;

@Service
public class ContaServiceImpl implements ContaService {

	@Autowired
	private ContaRepository contaRepository;

	@Override
	public Conta buscarConta(String conta) {
		return contaRepository.findById(conta).get();
	}

	@Override
	public boolean isValida(String conta) {
		return contaRepository.existsById(conta);
	}

	@Override
	public void atualizaConta(List<Conta> contas) {
		contaRepository.saveAll(contas);
	}

	@Override
	public void atualizaConta(Conta conta) {
		contaRepository.save(conta);
	}

	@Override
	public void cadastraConta(Conta conta) {
		contaRepository.save(conta);
	}

	@Override
	public List<Conta> buscarTodasContas() {
		return Lists.newArrayList(contaRepository.findAll());
	}

}

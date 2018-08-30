package com.cvc.financeiro.transferencia.service.impl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cvc.financeiro.transferencia.entities.Conta;
import com.cvc.financeiro.transferencia.entities.Transferencia;
import com.cvc.financeiro.transferencia.exception.TaxaException;
import com.cvc.financeiro.transferencia.exception.TransferenciaException;
import com.cvc.financeiro.transferencia.repository.TransferenciaRepository;
import com.cvc.financeiro.transferencia.service.ContaService;
import com.cvc.financeiro.transferencia.service.TaxaService;
import com.cvc.financeiro.transferencia.service.TransferenciaService;
import com.cvc.financeiro.transferencia.utils.StatusTransferenciaEnum;
import com.google.common.collect.Lists;

@Service
public class TransferenciaServiceImpl implements TransferenciaService {

	@Autowired
	private TaxaService taxaService;

	@Autowired
	private ContaService contaService;

	@Autowired
	private TransferenciaRepository transferenciaRepository;

	@Override
	public void realizarTransferencia() {

		List<Transferencia> transferencias = transferenciaRepository.findByDataTransferencia(LocalDate.now());

		for (Transferencia transferencia : transferencias) {
			// Valida se contas existem na base.
			if (!contaService.isValida(transferencia.getContaDestino())) {
				this.atualizarStatus(transferencia, StatusTransferenciaEnum.CONTA_DESTINO_INVALIDA);
				throw new TransferenciaException("Conta destino Invalida");
			}
			if (!contaService.isValida(transferencia.getContaOrigem())) {
				this.atualizarStatus(transferencia, StatusTransferenciaEnum.CONTA_ORIGEM_INVALIDA);
				throw new TransferenciaException("Conta origem Invalida");
			}

			Conta contaOrigem = contaService.buscarConta(transferencia.getContaOrigem());

			// Verifica se a conta tem saldo suficiente para a transferencia
			if (contaOrigem.getSaldo() < (transferencia.getValor() + transferencia.getTaxa())) {
				this.atualizarStatus(transferencia, StatusTransferenciaEnum.SALDO_INSUFICIENTE);
				throw new TransferenciaException("Saldo Insuficiente");
			}
			
			Conta contaDestino = contaService.buscarConta(transferencia.getContaDestino());

			// Faz as alterações nos saldos de ambas as contas
			contaOrigem.setSaldo(contaOrigem.getSaldo() - (transferencia.getValor() + transferencia.getTaxa()));
			contaDestino.setSaldo(contaDestino.getSaldo() + transferencia.getValor());

			// atualiza as contas e salva a transferencia
			contaService.atualizaConta(Arrays.asList(contaOrigem, contaDestino));
		}
	}

	@Override
	public List<Transferencia> buscarTodasTransferencias() {
		return Lists.newArrayList(transferenciaRepository.findAll());
	}

	@Override
	public void agendarTransferencia(Transferencia transferencia) {

		// Chama o servico para calcular taxas e salva a transacao com status de aguardando transferencia
		try {
			Float taxa = taxaService.calcularTaxa(transferencia.getDataTransferencia(),
					transferencia.getDataAgendamento(), transferencia.getValor());
			transferencia.setTaxa(taxa);
			transferencia.setStatus(StatusTransferenciaEnum.AGUARDANDO_TRANSFERENCIA);
			transferenciaRepository.save(transferencia);

		} catch (Exception t) {
			transferencia.setStatus(StatusTransferenciaEnum.TAXA_NAO_CALCULADA);
			transferenciaRepository.save(transferencia);
			throw new TaxaException("Problema ao calcular taxa - " + t.getMessage());
		}

	}

	@Override
	public void atualizarStatus(Transferencia transferencia, StatusTransferenciaEnum status) {
		transferencia.setStatus(status);
		transferenciaRepository.save(transferencia);
	}
}

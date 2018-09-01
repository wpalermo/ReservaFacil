package com.cvc.financeiro.transferencia.service.impl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cvc.financeiro.transferencia.entities.Conta;
import com.cvc.financeiro.transferencia.entities.Transferencia;
import com.cvc.financeiro.transferencia.exception.TaxaException;
import com.cvc.financeiro.transferencia.exception.TransferenciaException;
import com.cvc.financeiro.transferencia.repository.TransferenciaRepository;
import com.cvc.financeiro.transferencia.scheduled.ScheduledTasks;
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
	
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);


	
	@Override
	public List<Transferencia> buscarTodasTransferencias() {
		return Lists.newArrayList(transferenciaRepository.findAll());
	}
	
	@Override
	public void realizarTransferencia() {

		
		
		//Busca as transferencias que deve ser feitas
		List<Transferencia> transferencias = transferenciaRepository.findByDataTransferenciaAndStatus(LocalDate.now(), StatusTransferenciaEnum.AGUARDANDO_TRANSFERENCIA);

		
		
		for (Transferencia transferencia : transferencias) {
			
			log.info("Inciando transferencia");
			log.info("De " + transferencia.getContaOrigem()  + " para " + transferencia.getContaDestino());
			
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

			// atualiza as contas e atualiza status da transferencia
			contaService.atualizaConta(Arrays.asList(contaOrigem, contaDestino));
			this.atualizarStatus(transferencia, StatusTransferenciaEnum.SUCESSO);

		}
	}



	@Override
	public void agendarTransferencia(Transferencia transferencia) {

		log.info("Inciando agendamento");

		
		//Valida as datas de agendamento e transferencia
		if(transferencia.getDataAgendamento().isBefore(LocalDate.now())) {
			transferencia.setStatus(StatusTransferenciaEnum.DATA_AGENDAMENTO_INVALIDA);
			transferenciaRepository.save(transferencia);
			throw new TransferenciaException("Data de agendamento invalida");
		}
		
		if(transferencia.getDataTransferencia().isBefore(LocalDate.now())) {
			transferencia.setStatus(StatusTransferenciaEnum.DATA_TRANSACAO_INVALIDA);
			transferenciaRepository.save(transferencia);
			throw new TransferenciaException("Data de transferencia invalida");
		}
		
		try {
			//Chama o servico de taxa e atualiza a taxa da transferencia
			taxaService.calcularTaxa(transferencia);

			//TODO: especificar essa exception nao deixar a generica
		} catch (Exception t) {
			transferencia.setStatus(StatusTransferenciaEnum.TAXA_NAO_CALCULADA);
			transferenciaRepository.save(transferencia);
			throw new TaxaException("Problema ao calcular taxa - " + t.getMessage());
		}

	}
	
	@Override
	public void atualizarTaxa(Transferencia transferencia) {
		transferenciaRepository.save(transferencia);
	}

	@Override
	public void atualizarStatus(Transferencia transferencia, StatusTransferenciaEnum status) {
		transferencia.setStatus(status);
		transferenciaRepository.save(transferencia);
	}

	@Override
	public void recalcularTaxas() {
		List<Transferencia> transferencias = transferenciaRepository.findByDataTransferenciaAndStatus(LocalDate.now(), StatusTransferenciaEnum.AGUARDANDO_CALCULO_TAXA);
		transferencias.forEach(taxaService::calcularTaxa);
	}
}

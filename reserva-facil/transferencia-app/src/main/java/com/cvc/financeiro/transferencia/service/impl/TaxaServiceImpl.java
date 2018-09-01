package com.cvc.financeiro.transferencia.service.impl;

import java.time.LocalDate;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cvc.financeiro.transferencia.entities.Transferencia;
import com.cvc.financeiro.transferencia.histrixCommands.TaxaHttpRequest;
import com.cvc.financeiro.transferencia.request.TaxaRequest;
import com.cvc.financeiro.transferencia.resource.TaxaResource;
import com.cvc.financeiro.transferencia.response.TaxaResponse;
import com.cvc.financeiro.transferencia.service.TaxaService;
import com.cvc.financeiro.transferencia.service.TransferenciaService;

import rx.Scheduler;
import rx.schedulers.Schedulers;

@Service
public class TaxaServiceImpl implements TaxaService {
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private TaxaResource taxaResource;
	
	@Autowired
	private TransferenciaService transferenciaService;
	
	private TaxaResponse response;

	
	public void calcularTaxa(Transferencia transferencia) {
		
		TaxaHttpRequest taxaHttpRequest = new TaxaHttpRequest(taxaResource, transferencia);
		
		//Executa a chama do servico usando ReactiveX e hystrix para o fallback
		taxaHttpRequest.toObservable()
					   .subscribeOn(Schedulers.io())
					   .subscribe(returned -> transferenciaService.atualizarTaxa(returned));  
		
	}

	
}

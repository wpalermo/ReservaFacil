package com.cvc.financeiro.transferencia.service.impl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cvc.financeiro.transferencia.histrixCommands.TaxaHttpRequest;
import com.cvc.financeiro.transferencia.request.TaxaRequest;
import com.cvc.financeiro.transferencia.resource.TaxaResource;
import com.cvc.financeiro.transferencia.response.TaxaResponse;
import com.cvc.financeiro.transferencia.service.TaxaService;

@Service
public class TaxaServiceImpl implements TaxaService {
	
	@Autowired
	private TaxaResource taxaResource;
	
	private TaxaResponse response;

	@Override
	public Float calcularTaxa(LocalDate dataTransferencia, LocalDate dataAgendamento, Float valor) {
		
		TaxaRequest request = new TaxaRequest();
		
		request.setDataAgendamento(dataAgendamento);
		request.setDataTransferencia(dataTransferencia);
		request.setValor(valor);
		
		return this.calcularTaxa(request);
	}

	@Override
	public Float calcularTaxa(TaxaRequest taxaRequest) {
		
		TaxaHttpRequest taxaHttpRequest = new TaxaHttpRequest(taxaResource, taxaRequest);
		
		taxaHttpRequest.toObservable()
					   .subscribe(returned -> response = returned,
							   	  Throwable::printStackTrace,
							   	  () -> {});
		
		return returned;
	}

	
}

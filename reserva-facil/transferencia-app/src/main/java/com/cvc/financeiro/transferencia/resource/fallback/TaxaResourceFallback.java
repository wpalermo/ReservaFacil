package com.cvc.financeiro.transferencia.resource.fallback;

import org.jboss.logging.Logger;
import org.springframework.http.ResponseEntity;

import com.cvc.financeiro.transferencia.request.TaxaRequest;
import com.cvc.financeiro.transferencia.resource.TaxaResource;
import com.cvc.financeiro.transferencia.response.TaxaResponse;

public class TaxaResourceFallback implements TaxaResource{

	private Logger LOGGER = Logger.getLogger(this.getClass());
	
	@Override
	public ResponseEntity<TaxaResponse> post(TaxaRequest request) {
		
		LOGGER.error("PROBLEMA AO ACESSAR SERVICO DE TAXA");
		
		return null;
	}

}

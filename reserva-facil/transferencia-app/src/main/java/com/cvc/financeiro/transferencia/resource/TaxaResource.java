package com.cvc.financeiro.transferencia.resource;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cvc.financeiro.transferencia.request.TaxaRequest;
import com.cvc.financeiro.transferencia.resource.fallback.TaxaResourceFallback;
import com.cvc.financeiro.transferencia.response.TaxaResponse;


/**
 * Classe de resource, faz a chamada de servico usando feing client
 * 
 *
 */
@FeignClient(name = "taxa-app", fallback=TaxaResourceFallback.class)
public interface TaxaResource {

	/**
	 * Faz o post request no servico de taxas
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value="taxa")
	ResponseEntity<TaxaResponse> post(TaxaRequest request);
	
}

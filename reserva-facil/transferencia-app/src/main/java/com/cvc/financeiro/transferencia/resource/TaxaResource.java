package com.cvc.financeiro.transferencia.resource;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cvc.financeiro.transferencia.request.TaxaRequest;
import com.cvc.financeiro.transferencia.resource.fallback.TaxaResourceFallback;
import com.cvc.financeiro.transferencia.response.TaxaResponse;

@FeignClient(name = "taxa-app", fallback=TaxaResourceFallback.class)
public interface TaxaResource {

	@RequestMapping(method = RequestMethod.POST, value = "taxa")
	TaxaResponse post(TaxaRequest request);
	
}
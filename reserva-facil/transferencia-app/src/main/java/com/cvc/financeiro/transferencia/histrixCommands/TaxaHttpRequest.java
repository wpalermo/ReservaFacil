package com.cvc.financeiro.transferencia.histrixCommands;

import org.jboss.logging.Logger;

import com.cvc.financeiro.transferencia.request.TaxaRequest;
import com.cvc.financeiro.transferencia.resource.TaxaResource;
import com.cvc.financeiro.transferencia.response.TaxaResponse;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class TaxaHttpRequest extends HystrixCommand<TaxaResponse> {

	private Logger logger = Logger.getLogger(this.getClass());
	


	private TaxaResource taxaResource;
	private TaxaRequest taxaRequest;
	

	public TaxaHttpRequest(TaxaResource taxaResource, TaxaRequest taxaRequest ) {
		super(HystrixCommandGroupKey.Factory.asKey("taxa-app"));
		this.taxaResource = taxaResource;
		this.taxaRequest = taxaRequest;
	}

	@Override
	protected TaxaResponse run() throws Exception {
		logger.info("Fazendo requisicao para TAXA-APP");
		return taxaResource.post(taxaRequest);
	}

	@Override
	protected TaxaResponse getFallback() {
		logger.info("Problema ao acessar servico de campanhas TAXA-APP");
		return null;
	}

}

package com.cvc.financeiro.transferencia.histrixCommands;

import org.jboss.logging.Logger;

import com.cvc.financeiro.transferencia.entities.Transferencia;
import com.cvc.financeiro.transferencia.repository.TransferenciaRepository;
import com.cvc.financeiro.transferencia.request.TaxaRequest;
import com.cvc.financeiro.transferencia.resource.TaxaResource;
import com.cvc.financeiro.transferencia.response.TaxaResponse;
import com.cvc.financeiro.transferencia.utils.StatusTransferenciaEnum;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class TaxaHttpRequest extends HystrixCommand<Transferencia> {

	private Logger logger = Logger.getLogger(this.getClass());
	


	private TaxaResource taxaResource;
	private TaxaRequest taxaRequest;
	private TaxaResponse response;
	private Transferencia transferencia;
	

	public TaxaHttpRequest(TaxaResource taxaResource, Transferencia transferencia) {
		super(HystrixCommandGroupKey.Factory.asKey("transferencia-app"));
		this.taxaResource = taxaResource;
		this.transferencia = transferencia;
		
		this.taxaRequest = new TaxaRequest();
		
		this.taxaRequest.setDataAgendamento(transferencia.getDataAgendamento());
		this.taxaRequest.setDataTransferencia(transferencia.getDataTransferencia());
		this.taxaRequest.setValor(transferencia.getValor());
		
	}

	@Override
	protected Transferencia run() throws Exception {
		logger.info("Fazendo requisicao para TAXA-APP");
		response = taxaResource.post(taxaRequest);
		transferencia.setTaxa(response.getValor());
		transferencia.setStatus(StatusTransferenciaEnum.AGUARDANDO_TRANSFERENCIA);
		return transferencia;
	}

	@Override
	protected Transferencia getFallback() {
		logger.info("Problema ao acessar servico de campanhas TAXA-APP");
		transferencia.setStatus(StatusTransferenciaEnum.AGUARDANDO_CALCULO_TAXA);
		return transferencia;
	}

}

package com.cvc.financeiro.transferencia.histrixCommands;

import org.apache.http.HttpException;
import org.jboss.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cvc.financeiro.transferencia.entities.Transferencia;
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
	private ResponseEntity<TaxaResponse> response;
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
		try {
			response = taxaResource.post(taxaRequest);
			transferencia.setTaxa(response.getBody().getValor());
			transferencia.setStatus(StatusTransferenciaEnum.AGUARDANDO_TRANSFERENCIA);
		}catch(Exception e) {
			logger.error("Taxa não pode ser calculada, rever a transferencia");
			transferencia.setStatus(StatusTransferenciaEnum.TAXA_NAO_CALCULADA);
		}

		return transferencia;
	}

	@Override
	protected Transferencia getFallback() {
		logger.warn("Problema ao acessar servico de campanhas TAXA-APP");
		logger.warn("Transferencia salva para aguardar nova tentativa.");
		transferencia.setStatus(StatusTransferenciaEnum.AGUARDANDO_CALCULO_TAXA);
		
		return transferencia;
	}

}

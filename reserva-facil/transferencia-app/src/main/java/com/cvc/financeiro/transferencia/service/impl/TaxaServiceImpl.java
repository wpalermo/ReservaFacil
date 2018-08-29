package com.cvc.financeiro.transferencia.service.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

import com.cvc.financeiro.transferencia.exception.TaxaException;
import com.cvc.financeiro.transferencia.service.TaxaService;
import com.cvc.financeiro.transferencia.utils.TransaferenciaUtils;

@Service
public class TaxaServiceImpl implements TaxaService {

	@Override
	public Float calcularTaxa(LocalDate dataTransaferencia, LocalDate dataAgendamento, Float valor) {
		//Operacao TIPO A
		if(dataTransaferencia.isEqual(dataAgendamento))
			return Float.valueOf(3) + (valor * (3/100f));
		
		//Operacao TIPO B
		Long diasDiferenca = ChronoUnit.DAYS.between(dataTransaferencia, dataAgendamento);
		
		if(diasDiferenca < 0)
			throw new TaxaException("Data de agendamento invalida");
		
		if( diasDiferenca < 10) {
			return (diasDiferenca.floatValue() * 12);
		}
		
		//Operacao TIPO C
		if(TransaferenciaUtils.isBetween(10, 20, diasDiferenca.intValue()))
			return (valor * (8/100f));
		else if(TransaferenciaUtils.isBetween(20, 30, diasDiferenca.intValue()))
			return (valor * (6/100f));
		else if(TransaferenciaUtils.isBetween(30, 40, diasDiferenca.intValue()))
			return (valor * (3/100f));
		else if( diasDiferenca > 40 && valor > 100000) 
			return (valor * (2/100f));
		else 
			throw new TaxaException("Não foi possivel calcular taxa");
		
	}

	
}

package com.cvc.financeiro.transferencia.service;

import java.time.LocalDate;

public interface TaxaService {
	
	Integer calcularTaxa(LocalDate dataTransaferencia, LocalDate dataAgendamento);

}

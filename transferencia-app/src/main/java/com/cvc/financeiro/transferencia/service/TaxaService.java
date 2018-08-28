package com.cvc.financeiro.transferencia.service;

import java.time.LocalDate;

public interface TaxaService {
	
	Float calcularTaxa(LocalDate dataTransaferencia, LocalDate dataAgendamento, Float valor);

}

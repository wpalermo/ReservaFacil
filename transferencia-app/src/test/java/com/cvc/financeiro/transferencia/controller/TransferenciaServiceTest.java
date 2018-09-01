package com.cvc.financeiro.transferencia.controller;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.cvc.financeiro.transferencia.entities.Transferencia;
import com.cvc.financeiro.transferencia.repository.TransferenciaRepository;
import com.cvc.financeiro.transferencia.service.TaxaService;
import com.cvc.financeiro.transferencia.service.impl.TransferenciaServiceImpl;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class TransferenciaServiceTest {
	
	@InjectMocks
	private TransferenciaServiceImpl transferenciaService;
	
	@Mock
	private TaxaService taxaService;
	
	@Mock
	private TransferenciaRepository transferenciaRepository;
	
	
	@Test
	public void agendarTransferenciaTest() {
		
		Transferencia transferencia = new Transferencia();

		transferencia.setContaDestino("1001");
		transferencia.setContaOrigem("1000");
		transferencia.setDataAgendamento(LocalDate.now());
		transferencia.setDataTransferencia(LocalDate.now());
		transferencia.setValor(2000f);
		
		Mockito.when(taxaService.calcularTaxa(transferencia.getDataTransferencia(),
			transferencia.getDataAgendamento(), transferencia.getValor())).thenReturn(4f);

		transferenciaService.agendarTransferencia(transferencia);
		
		
	}

}

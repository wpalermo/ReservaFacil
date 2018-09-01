package com.cvc.financeiro.transferencia.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.cvc.financeiro.transferencia.entities.Conta;
import com.cvc.financeiro.transferencia.entities.Transferencia;
import com.cvc.financeiro.transferencia.repository.TransferenciaRepository;
import com.cvc.financeiro.transferencia.service.ContaService;
import com.cvc.financeiro.transferencia.service.impl.TransferenciaServiceImpl;
import com.cvc.financeiro.transferencia.utils.StatusTransferenciaEnum;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class ScheduledTasksTest {
	
	@InjectMocks
	private TransferenciaServiceImpl transferenciaService;
	
	@Spy
	private ContaService contaService;
	
	@Mock
	private TransferenciaRepository transferenciaRepository;
	
	@Test
	public void realizaTransaferencia() {
		
		
		Transferencia transferencia = new Transferencia();

		transferencia.setContaDestino("1001");
		transferencia.setContaOrigem("1000");
		transferencia.setDataAgendamento(LocalDate.now());
		transferencia.setDataTransferencia(LocalDate.now());
		transferencia.setValor(2000f);
		transferencia.setTaxa(4f);
		
		Conta conta1 = new Conta();
		conta1.setConta("1000");
		conta1.setNomeCliente("Tony Stark");
		conta1.setSaldo(1000000000f);
		
		Conta conta2 = new Conta();
		conta2.setConta("1001");
		conta2.setNomeCliente("Peter Parker");
		conta2.setSaldo(100f);
		
		
		List<Transferencia> transferencias = Arrays.asList(transferencia);
		Mockito.when(contaService.isValida(transferencia.getContaOrigem())).thenReturn(true);
		Mockito.when(contaService.isValida(transferencia.getContaDestino())).thenReturn(true);
		Mockito.when(contaService.buscarConta(transferencia.getContaDestino())).thenReturn(conta2);
		Mockito.when(contaService.buscarConta(transferencia.getContaOrigem())).thenReturn(conta1);

		
		Mockito.when(transferenciaRepository.findByDataTransferenciaAndStatus(LocalDate.now(), StatusTransferenciaEnum.AGUARDANDO_TRANSFERENCIA)).thenReturn(transferencias);

		
        transferenciaService.realizarTransferencia();
    }
	
	

}

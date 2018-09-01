package com.cvc.financeiro.transferencia.service;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.cvc.financeiro.transferencia.resource.TaxaResource;
import com.cvc.financeiro.transferencia.response.TaxaResponse;
import com.cvc.financeiro.transferencia.service.impl.TaxaServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class TaxaServiceTest {

	@InjectMocks
	private TaxaServiceImpl taxaService;
	
	@Mock
	private TaxaResource taxaResource;
	
	@Mock
	private TaxaResponse response;
	
	@Test
	public void calcularTaxaTest() {
		
		
		Mockito.when(response.getValor()).thenReturn(5f);
		//taxaService.calcularTaxa(LocalDate.now(), LocalDate.now(), 2000f);
		
		
	}
	
}

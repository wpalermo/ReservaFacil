package com.cvc.financeiro.transferencia.resource;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import com.cvc.financeiro.transferencia.request.TaxaRequest;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class TaxaResourceTest {
	
	@Autowired
	private TaxaResource resource;
	
	@Test(expected=Exception.class)
	public void resourceTest() {
		
		TaxaRequest request = new TaxaRequest();
		request.setDataAgendamento(LocalDate.now());
		request.setDataTransferencia(LocalDate.now().plusDays(45));
		request.setValor(2000f);
		
		
		Float f = resource.post(request).getBody().getValor();
		Assert.notNull(f, "");
	}

}

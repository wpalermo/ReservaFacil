package com.cvc.financeiro.transferencia.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.cvc.financeiro.transferencia.entities.Transferencia;
import com.cvc.financeiro.transferencia.exception.TaxaException;
import com.cvc.financeiro.transferencia.request.TransferenciaRequest;
import com.cvc.financeiro.transferencia.response.TransferenciaResponse;
import com.cvc.financeiro.transferencia.service.TaxaService;
import com.cvc.financeiro.transferencia.service.TransferenciaService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class TransferenciaControllerTest {

	final String BASE_PATH = "http://localhost:8083/transferencia";

	private RestTemplate restTemplate;

	@InjectMocks
	private TransferenciaController controller;

	@Mock
	private TransferenciaService transferenciaService;

	@Mock
	private TaxaService taxaService;

	@Before
	public void setUp() {

		restTemplate = new RestTemplate();

	}

	@Test
	public void agendarTransacaoTest() throws URISyntaxException {

		Transferencia transferencia = new Transferencia();

		transferencia.setContaDestino("1001");
		transferencia.setContaOrigem("1000");
		transferencia.setDataAgendamento(LocalDate.now());
		transferencia.setDataTransferencia(LocalDate.now());
		transferencia.setValor(2000f);

		TransferenciaRequest transferenciaRequest = new TransferenciaRequest();
		transferenciaRequest.setTransferencia(transferencia);

		Mockito.doNothing().when(transferenciaService).agendarTransferencia(transferencia);

		ResponseEntity<TransferenciaResponse> response = controller.post(
				new RequestEntity<TransferenciaRequest>(transferenciaRequest, HttpMethod.POST, new URI(BASE_PATH)));

		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

	}

	@Test
	public void agendarTransacaoBusinessExceptionTest() throws URISyntaxException {

		Transferencia transferencia = new Transferencia();

		transferencia.setContaDestino("1001");
		transferencia.setContaOrigem("1000");
		transferencia.setDataAgendamento(LocalDate.now());
		transferencia.setDataTransferencia(LocalDate.now());
		transferencia.setValor(2000f);

		TransferenciaRequest transferenciaRequest = new TransferenciaRequest();
		transferenciaRequest.setTransferencia(transferencia);

		Mockito.doThrow(TaxaException.class).when(transferenciaService).agendarTransferencia(transferencia);

		ResponseEntity<TransferenciaResponse> response = controller.post(
				new RequestEntity<TransferenciaRequest>(transferenciaRequest, HttpMethod.POST, new URI(BASE_PATH)));

		Assert.assertEquals(HttpStatus.EXPECTATION_FAILED, response.getStatusCode());

	}

	@Test
	public void getTest() throws URISyntaxException {

		Mockito.when(transferenciaService.buscarTodasTransferencias()).thenReturn(new ArrayList<Transferencia>());

		//ResponseEntity<List<TransferenciaResponse>> response = (ResponseEntity<List<TransferenciaResponse>>) controller.get();

		//Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

	}

}

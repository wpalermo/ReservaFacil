package br.com.cvc.taxas.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import br.com.cvc.taxas.request.TaxaRequest;
import br.com.cvc.taxas.response.TaxaResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class TaxaControllerTest {

	final String BASE_PATH = "http://localhost:8082/taxa";

	
	private RestTemplate restTemplate;
	
	
	
	@Before
	public void setUp() {
		restTemplate = new RestTemplate();
	}

	
	@Test
	public void testTipoA() throws URISyntaxException {
		
		TaxaRequest taxaRequest = new TaxaRequest();
		
		taxaRequest.setDataAgendamento(LocalDate.now());
		taxaRequest.setDataTransferencia(LocalDate.now());
		taxaRequest.setValor(2000f);
		
		
		
		RequestEntity<TaxaRequest> request = new RequestEntity<TaxaRequest>(taxaRequest, HttpMethod.GET, new URI(BASE_PATH));
		ResponseEntity<TaxaResponse> response = restTemplate.postForEntity(BASE_PATH, request, TaxaResponse.class);
		
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		
	}
	
	@Test
	public void testTipoB() throws URISyntaxException {
		
		TaxaRequest taxaRequest = new TaxaRequest();
		
		taxaRequest.setDataAgendamento(LocalDate.now());
		taxaRequest.setDataTransferencia(LocalDate.now().plusDays(4));
		taxaRequest.setValor(2000f);
		
		RequestEntity<TaxaRequest> request = new RequestEntity<TaxaRequest>(taxaRequest, HttpMethod.GET, new URI(BASE_PATH));
		ResponseEntity<TaxaResponse> response = restTemplate.postForEntity(BASE_PATH, request, TaxaResponse.class);
		
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		
	}
	
	@Test
	public void testTipoC10() throws URISyntaxException {
		
		TaxaRequest taxaRequest = new TaxaRequest();
		
		taxaRequest.setDataAgendamento(LocalDate.now());
		taxaRequest.setDataTransferencia(LocalDate.now().plusDays(15));
		taxaRequest.setValor(2000f);
		
		RequestEntity<TaxaRequest> request = new RequestEntity<TaxaRequest>(taxaRequest, HttpMethod.GET, new URI(BASE_PATH));
		ResponseEntity<TaxaResponse> response = restTemplate.postForEntity(BASE_PATH, request, TaxaResponse.class);
		
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		
	}
	
	@Test
	public void testTipoC20() throws URISyntaxException {
		
		TaxaRequest taxaRequest = new TaxaRequest();
		
		taxaRequest.setDataAgendamento(LocalDate.now());
		taxaRequest.setDataTransferencia(LocalDate.now().plusDays(25));
		taxaRequest.setValor(2000f);
		
		RequestEntity<TaxaRequest> request = new RequestEntity<TaxaRequest>(taxaRequest, HttpMethod.GET, new URI(BASE_PATH));
		ResponseEntity<TaxaResponse> response = restTemplate.postForEntity(BASE_PATH, request, TaxaResponse.class);
		
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		
	}
	
	@Test
	public void testTipoC30() throws URISyntaxException {
		
		TaxaRequest taxaRequest = new TaxaRequest();
		
		taxaRequest.setDataAgendamento(LocalDate.now());
		taxaRequest.setDataTransferencia(LocalDate.now().plusDays(35));
		taxaRequest.setValor(2000f);
		
		RequestEntity<TaxaRequest> request = new RequestEntity<TaxaRequest>(taxaRequest, HttpMethod.GET, new URI(BASE_PATH));
		ResponseEntity<TaxaResponse> response = restTemplate.postForEntity(BASE_PATH, request, TaxaResponse.class);
		
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		
	}
	
	@Test
	public void testTipoC40() throws URISyntaxException {
		
		TaxaRequest taxaRequest = new TaxaRequest();
		
		taxaRequest.setDataAgendamento(LocalDate.now());
		taxaRequest.setDataTransferencia(LocalDate.now().plusDays(45));
		taxaRequest.setValor(20000000f);
		
		RequestEntity<TaxaRequest> request = new RequestEntity<TaxaRequest>(taxaRequest, HttpMethod.GET, new URI(BASE_PATH));
		ResponseEntity<TaxaResponse> response = restTemplate.postForEntity(BASE_PATH, request, TaxaResponse.class);
		
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		
	}
	
	@Test
	public void testTipoC40Exception() throws URISyntaxException {
		
		TaxaRequest taxaRequest = new TaxaRequest();
		
		taxaRequest.setDataAgendamento(LocalDate.now());
		taxaRequest.setDataTransferencia(LocalDate.now().plusDays(45));
		taxaRequest.setValor(2000f);
		
		RequestEntity<TaxaRequest> request = new RequestEntity<TaxaRequest>(taxaRequest, HttpMethod.GET, new URI(BASE_PATH));
		ResponseEntity<TaxaResponse> response = null;
		try {
			response = restTemplate.postForEntity(BASE_PATH, request, TaxaResponse.class);
		}catch(Exception e) {
			e.getMessage();
			Assert.assertEquals("417 null", e.getMessage());
		}
		
	}
	
}

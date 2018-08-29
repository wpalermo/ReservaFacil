package com.cvc.financeiro.transferencia.controller;

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

import com.cvc.financeiro.transferencia.entities.Transferencia;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class TransferenciaControllerTest {

	final String BASE_PATH = "http://localhost:8080/transferencia";

	private RestTemplate restTemplate;
	
	

	
	@Before
	public void setUp() {
		
		restTemplate = new RestTemplate();
		

		
	}
	
	@Test
	public void postTipoATest() throws URISyntaxException {
		
		Transferencia transferencia = new Transferencia();
		
		transferencia.setContaDestrino("1001");
		transferencia.setContaOrigem("1000");
		transferencia.setDataAgendamento(LocalDate.now());
		transferencia.setDataTransferencia(LocalDate.now());
		transferencia.setValor(2000f);
		
		
		RequestEntity<Transferencia> request = new RequestEntity<Transferencia>(transferencia, HttpMethod.POST, new URI(BASE_PATH));
		ResponseEntity<Transferencia> response = restTemplate.postForEntity(BASE_PATH, request, Transferencia.class);
		
		Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());


	}
	
	@Test
	public void postTipoBTest() throws URISyntaxException {
		
		Transferencia transferencia = new Transferencia();
		
		transferencia.setContaDestrino("1001");
		transferencia.setContaOrigem("1000");
		transferencia.setDataAgendamento(LocalDate.now().plusDays(5L));
		transferencia.setDataTransferencia(LocalDate.now());
		transferencia.setValor(2000f);
		
		RequestEntity<Transferencia> request = new RequestEntity<Transferencia>(transferencia, HttpMethod.POST, new URI(BASE_PATH));
		ResponseEntity<Transferencia> response = restTemplate.postForEntity(BASE_PATH, request, Transferencia.class);
		
		Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());


	}
	
	@Test
	public void postTipoC10Test() throws URISyntaxException {
		
		Transferencia transferencia = new Transferencia();
		
		transferencia.setContaDestrino("1001");
		transferencia.setContaOrigem("1000");
		transferencia.setDataAgendamento(LocalDate.now().plusDays(15L));
		transferencia.setDataTransferencia(LocalDate.now());
		transferencia.setValor(2000f);
		
		RequestEntity<Transferencia> request = new RequestEntity<Transferencia>(transferencia, HttpMethod.POST, new URI(BASE_PATH));
		ResponseEntity<Transferencia> response = restTemplate.postForEntity(BASE_PATH, request, Transferencia.class);
		
		Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());


	}
	
	@Test
	public void postTipoC20Test() throws URISyntaxException {
		
		Transferencia transferencia = new Transferencia();
		
		transferencia.setContaDestrino("1001");
		transferencia.setContaOrigem("1000");
		transferencia.setDataAgendamento(LocalDate.now().plusDays(25L));
		transferencia.setDataTransferencia(LocalDate.now());
		transferencia.setValor(2000f);
		
		RequestEntity<Transferencia> request = new RequestEntity<Transferencia>(transferencia, HttpMethod.POST, new URI(BASE_PATH));
		ResponseEntity<Transferencia> response = restTemplate.postForEntity(BASE_PATH, request, Transferencia.class);
		
		Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());


	}
	
	@Test
	public void postTipoC30Test() throws URISyntaxException {
		
		Transferencia transferencia = new Transferencia();
		
		transferencia.setContaDestrino("1001");
		transferencia.setContaOrigem("1000");
		transferencia.setDataAgendamento(LocalDate.now().plusDays(35L));
		transferencia.setDataTransferencia(LocalDate.now());
		transferencia.setValor(2000f);
		
		RequestEntity<Transferencia> request = new RequestEntity<Transferencia>(transferencia, HttpMethod.POST, new URI(BASE_PATH));
		ResponseEntity<Transferencia> response = restTemplate.postForEntity(BASE_PATH, request, Transferencia.class);
		
		Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());


	}
	
	@Test
	public void postTipoC40Test() throws URISyntaxException {
		
		Transferencia transferencia = new Transferencia();
		
		transferencia.setContaDestrino("1001");
		transferencia.setContaOrigem("1000");
		transferencia.setDataAgendamento(LocalDate.now().plusDays(41L));
		transferencia.setDataTransferencia(LocalDate.now());
		transferencia.setValor(100001f);
		
		RequestEntity<Transferencia> request = new RequestEntity<Transferencia>(transferencia, HttpMethod.POST, new URI(BASE_PATH));
		ResponseEntity<Transferencia> response = restTemplate.postForEntity(BASE_PATH, request, Transferencia.class);
		
		Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());


	}
	
	@Test(expected=Exception.class)
	public void postTipoC40ExceptionTest() throws URISyntaxException {
		
		Transferencia transferencia = new Transferencia();
		
		transferencia.setContaDestrino("1001");
		transferencia.setContaOrigem("1000");
		transferencia.setDataAgendamento(LocalDate.now().plusDays(41L));
		transferencia.setDataTransferencia(LocalDate.now());
		transferencia.setValor(2000f);
		
		RequestEntity<Transferencia> request = new RequestEntity<Transferencia>(transferencia, HttpMethod.POST, new URI(BASE_PATH));
		restTemplate.postForEntity(BASE_PATH, request, null);
		


	}
	
	
}

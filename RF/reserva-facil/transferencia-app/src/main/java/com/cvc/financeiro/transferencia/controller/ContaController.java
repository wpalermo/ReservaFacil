package com.cvc.financeiro.transferencia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cvc.financeiro.transferencia.entities.Conta;
import com.cvc.financeiro.transferencia.exception.TaxaException;
import com.cvc.financeiro.transferencia.exception.TransferenciaException;
import com.cvc.financeiro.transferencia.response.TransferenciaResponse;
import com.cvc.financeiro.transferencia.service.ContaService;


@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/conta")
public class ContaController {
	
	
	@Autowired
	private ContaService contaService;
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResponseEntity<TransferenciaResponse> post(RequestEntity<Conta> request) {

		try {
			contaService.cadastraConta(request.getBody());
			return new ResponseEntity<>(HttpStatus.OK);

		} catch (TaxaException | TransferenciaException te) {
			return new ResponseEntity<>(new TransferenciaResponse(te.getMessage()), HttpStatus.EXPECTATION_FAILED);
		} 
	}
	
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResponseEntity<List<Conta>> get() {

		try {
			return new ResponseEntity<>(contaService.buscarTodasContas(), HttpStatus.OK);

		} catch (TaxaException | TransferenciaException te) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		} 
	}

}

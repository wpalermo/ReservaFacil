package com.cvc.financeiro.transferencia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cvc.financeiro.transferencia.entities.Transferencia;
import com.cvc.financeiro.transferencia.service.TransferenciaService;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/transferencia")
public class TransferenciaController {
	
	@Autowired
	private TransferenciaService transferenciaService;

	public ResponseEntity<Transferencia> get() {
		
		return null;
	}

	public ResponseEntity<Transferencia> put(Integer id, RequestEntity<Transferencia> request) {
		return null;
	}

	public ResponseEntity<Transferencia> post(RequestEntity<Transferencia> request) {
		return new ResponseEntity<>(transferenciaService.realizarTransferencia(request.getBody()), HttpStatus.ACCEPTED);
	}

	public void delete(String id) {
		
	}
	
	

}

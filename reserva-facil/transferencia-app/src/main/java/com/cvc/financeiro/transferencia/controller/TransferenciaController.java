package com.cvc.financeiro.transferencia.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.cvc.financeiro.transferencia.entities.Transferencia;
import com.cvc.financeiro.transferencia.exception.TaxaException;
import com.cvc.financeiro.transferencia.exception.TransferenciaException;
import com.cvc.financeiro.transferencia.service.TransferenciaService;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/transferencia")
public class TransferenciaController {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private TransferenciaService transferenciaService;

	@RequestMapping(method = RequestMethod.GET, produces = { "application/json" })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<List<Transferencia>> get() {
		return new ResponseEntity<>(transferenciaService.buscarTodasTransferencias(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResponseEntity<Transferencia> put(Integer id, RequestEntity<Transferencia> request) {
		return null;
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResponseEntity<Transferencia> post(RequestEntity<Transferencia> request) {

		Transferencia returnable;

		try {

			returnable = transferenciaService.realizarTransferencia(request.getBody());
			
			if(returnable != null)
				return new ResponseEntity<>(HttpStatus.OK);

			
		} catch (TaxaException | TransferenciaException te) {
			logger.error(te.getMessage());
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}

		return new ResponseEntity<>(HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
	@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
	@ResponseBody
	public void delete(String id) {

	}

}

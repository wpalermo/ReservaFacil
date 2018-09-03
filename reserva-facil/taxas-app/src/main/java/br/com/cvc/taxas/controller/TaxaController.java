package br.com.cvc.taxas.controller;

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

import br.com.cvc.taxas.exception.TaxaException;
import br.com.cvc.taxas.request.TaxaRequest;
import br.com.cvc.taxas.response.TaxaResponse;
import br.com.cvc.taxas.service.TaxaService;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/taxa")
public class TaxaController {

	@Autowired
	private TaxaService taxaService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResponseEntity<TaxaResponse> post(RequestEntity<TaxaRequest> request) {
		TaxaResponse response = new TaxaResponse();

		try {
			response.setValor(taxaService.calcularTaxa(request.getBody()));
		} catch (TaxaException te) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, produces = { "application/json" })
	@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
	public ResponseEntity<TaxaResponse> get(RequestEntity<TaxaRequest> request) {
		return null;

	}

	@RequestMapping(method = RequestMethod.PUT, path = "/{id}")
	@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
	@ResponseBody
	public ResponseEntity<String> put(Integer id, RequestEntity<String> request) {
		return null;
	}

	@RequestMapping(method = RequestMethod.PATCH)
	@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
	@ResponseBody
	public ResponseEntity<TaxaResponse> patch(RequestEntity<TaxaRequest> request) {
		return null;

	}

	@RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
	@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
	@ResponseBody
	public void delete(String id) {

	}

}

package com.cvc.financeiro.transferencia;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.cvc.financeiro.transferencia.entities.Conta;
import com.cvc.financeiro.transferencia.repository.ContaRepository;

@Component
public class DataLoader implements ApplicationRunner {

	@Autowired
	private ContaRepository contaRepository;
	
	
	@Override
	public void run(ApplicationArguments args) throws Exception {

		Conta conta1 = new Conta();
		conta1.setConta("1000");
		conta1.setNomeCliente("Tony Stark");
		conta1.setSaldo(1000000000f);
		
		Conta conta2 = new Conta();
		conta2.setConta("1001");
		conta2.setNomeCliente("Peter Parker");
		conta2.setSaldo(100f);
		
		Conta conta3 = new Conta();
		conta3.setConta("2000");
		conta3.setNomeCliente("Bruce Wayne");
		conta3.setSaldo(123423423f);
		
		Conta conta4 = new Conta();
		conta4.setConta("2001");
		conta4.setNomeCliente("Clark Kent");
		conta4.setSaldo(100000f);
		
		Conta conta5 = new Conta();
		conta5.setConta("1002");
		conta5.setNomeCliente("Steve Rogers");
		conta5.setSaldo(1400f);
		
		
		contaRepository.saveAll(Arrays.asList(conta1, conta2, conta3, conta4, conta5));
		
		
		contaRepository.findAll().forEach(ac -> System.out.println(ac.getNomeCliente()));
	
		
	}

}

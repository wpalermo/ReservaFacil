package com.cvc.financeiro.transferencia.repository;

import org.springframework.data.repository.CrudRepository;

import com.cvc.financeiro.transferencia.entities.Conta;

public interface ContaRepository extends CrudRepository<Conta, String> {

}

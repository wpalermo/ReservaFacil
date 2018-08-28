package com.cvc.financeiro.transferencia.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cvc.financeiro.transferencia.entities.Transferencia;


@Repository
public interface TransferenciaRepository extends CrudRepository<Transferencia, String>{

}

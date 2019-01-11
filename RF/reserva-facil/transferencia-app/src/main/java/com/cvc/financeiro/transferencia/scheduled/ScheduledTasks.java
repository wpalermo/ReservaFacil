package com.cvc.financeiro.transferencia.scheduled;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cvc.financeiro.transferencia.service.TransferenciaService;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    private TransferenciaService transferenciaService;
    
    /**
     * Roda o metodo realizaTransferencia agendado para rodar de 1 em 1 minuto
     */
    @Scheduled(cron = "1 * * * * *")
    public void realizaTransaferencia() {
    	log.info("Iniciando tarfefa agendada - REALIZAR TRANSFERENCIA {} ", LocalDate.now());
        transferenciaService.realizarTransferencia();
    }
    
    
    
    @Scheduled(cron = "1 * * * * *")
    public void atualizaTaxa() {
    	log.info("Iniciando tarfefa agendada - BUSCA DE TAXA {} ", LocalDate.now());
    	transferenciaService.recalcularTaxas();
    }
}
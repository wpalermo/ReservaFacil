package com.mycompany.mywebapp.shared;


import java.io.Serializable;

public class Transferencia implements Serializable {

    private String contaOrigem;

    private String contaDestino;

    private Float valor;

    private Float taxa;


    private String dataTransferencia;

    private String dataAgendamento;


    public Transferencia() {

    }

    public String getContaOrigem() {
        return contaOrigem;
    }

    public void setContaOrigem(String contaOrigem) {
        this.contaOrigem = contaOrigem;
    }

    public String getContaDestino() {
        return contaDestino;
    }

    public void setContaDestino(String contaDestino) {
        this.contaDestino = contaDestino;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public Float getTaxa() {
        return taxa;
    }

    public void setTaxa(Float taxa) {
        this.taxa = taxa;
    }

    public String getDataTransferencia() {
        return dataTransferencia;
    }

    public void setDataTransferencia(String dataTransferencia) {
        this.dataTransferencia = dataTransferencia;
    }

    public String getDataAgendamento() {
        return dataAgendamento;
    }

    public void setDataAgendamento(String dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }
}

package com.mycompany.mywebapp.shared.request;

import com.mycompany.mywebapp.shared.bean.Transferencia;

import java.io.Serializable;

public class TransferenciaRequest implements Serializable {

    private Transferencia transferencia;

    public Transferencia getTransferencia() {
        return transferencia;
    }

    public void setTransferencia(Transferencia transferencia) {
        this.transferencia = transferencia;
    }

}


package com.mycompany.mywebapp.shared;

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


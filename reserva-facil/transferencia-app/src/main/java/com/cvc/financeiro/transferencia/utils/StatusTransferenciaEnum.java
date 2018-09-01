package com.cvc.financeiro.transferencia.utils;

public enum StatusTransferenciaEnum {
	
	SUCESSO,
	AGUARDANDO_TRANSFERENCIA,
	AGUARDANDO_CALCULO_TAXA,
	SALDO_INSUFICIENTE,
	CONTA_ORIGEM_INVALIDA,
	CONTA_DESTINO_INVALIDA,
	DATA_TRANSACAO_INVALIDA,
	DATA_AGENDAMENTO_INVALIDA,
	TAXA_NAO_CALCULADA,
	ERRO_DESCONHECIDO;
	

}

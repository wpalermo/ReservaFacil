package com.cvc.financeiro.transferencia.utils;

public class TransaferenciaUtils {
	
	private TransaferenciaUtils() {
		
	}

	public static boolean isBetween(Integer a, Integer b, Integer value) {
		
		if(value >= a && value <=b)
			return true;
		else return false;
		
	}
	
	
}

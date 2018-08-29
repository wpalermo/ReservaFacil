package br.com.cvc.taxas.utils;

public class TransaferenciaUtils {
	
	private TransaferenciaUtils() {
		
	}

	/**
	 * Verifica se uma valor está entre o intervaldo de -a- e -b-
	 * @param a
	 * @param b
	 * @param value
	 * @return
	 */
	public static boolean isBetween(Integer a, Integer b, Integer value) {
		
		if(value >= a && value <=b)
			return true;
		else return false;
		
	}
	
	
}

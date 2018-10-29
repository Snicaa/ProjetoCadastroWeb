package br.com.fti.cadastro.controller;

public class UtilController {
	public static boolean validaCpf(String cpf){
		boolean verifica = false;
		if (cpf.length() == 11) {
			if (cpf.equals("11111111111") || cpf.equals("22222222222") || cpf.equals("33333333333") || cpf.equals("44444444444") || cpf.equals("55555555555") ||
					cpf.equals("66666666666") || cpf.equals("77777777777") || cpf.equals("88888888888") || cpf.equals("99999999999") || cpf.equals("00000000000")) {
				return false;
			}
			int digitoVerificador1 = Integer.parseInt(cpf.substring(9,10));
			int digitoVerificador2 = Integer.parseInt(cpf.substring(10,11));
			int resultado = 0;
			
			for(int i = 10, j = 0; i >= 2; i--, j++){
				resultado = resultado + (Integer.parseInt(cpf.substring(j,j+1)) * i);
			}
			resultado = (resultado * 10)%11;
			if(resultado == digitoVerificador1){
				resultado = 0;
				for(int i = 11, j = 0; i >= 2; i--, j++){
					resultado = resultado + (Integer.parseInt(cpf.substring(j, j+1)) * i);
				}
				resultado = (resultado * 10)%11;
				if (resultado == digitoVerificador2){
					verifica = true;
				} else if(digitoVerificador2 == 0 && resultado == 10){
					verifica = true;
				} else {
					verifica = false;
				}
			} else if(digitoVerificador1 == 0 && resultado == 10){
				resultado = 0;
				for(int i = 11, j = 0; i >= 2; i--, j++){
					resultado = resultado + (Integer.parseInt(cpf.substring(j, j+1)) * i);
				}
				resultado = (resultado * 10)%11;
				if (resultado == digitoVerificador2){
					verifica = true;					
				} else if(digitoVerificador2 == 0 && resultado == 10){
						verifica = true;
				} else {
					verifica = false;
				}
			} else {
				verifica = false;
			}
		} else {
			verifica = false;
		}
		return verifica;
	}
}

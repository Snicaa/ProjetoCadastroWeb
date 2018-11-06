package br.com.fti.cadastro.controller;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UtilController {
	
	public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	public static NumberFormat nF;
	
	@RequestMapping("/")
	public String home(){
		return "index";
	}
	
	@RequestMapping("validaCpf")
	public @ResponseBody String cpf(String cpf){
		if(UtilController.validaCpf(cpf)){
			return "";
		}
		return "CPF inválido";
	}
	
	@RequestMapping("validaData")
	public @ResponseBody String data(String data){
		if(UtilController.validaData(data)){
			return "";
		}
		return "Data inválida";
	}
	
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
		}
		return verifica;
	}
	
	public static boolean validaData(String data){
		boolean valida = false;
		UtilController.sdf.setLenient(false);
		try {
			Date dataAtual = new Date();
			Date dataInserida = UtilController.sdf.parse(data);
			if (dataAtual.before(dataInserida)){
				valida = false;
			} else {
				valida = true;
			}
		} catch (ParseException e){
			valida = false;
		}
		return valida;
	}
	
	public static String mascaraCpf(String cpf){
		try	{
			String cpfMascara = cpf.substring(0,3) + "." + cpf.substring(3,6) + '.' + cpf.substring(6,9) + '-' + cpf.substring(9,11); 
			return cpfMascara;
		} catch(Exception e){
		}
		return "";
	}
	
	public static String removeMascaraCpf(String cpf){
		cpf = cpf.replaceAll("[-. ]", "");
		return cpf;
	}
	
	public static String mascaraTelefone(String telefone){
		try{
			String telefoneMascara = "";
			if (telefone.length() == 10){
				telefoneMascara = "(" + telefone.substring(0,2) + ") " + telefone.substring(2,6) + "-" + telefone.substring(6, 10);
			} else {
				telefoneMascara = "(" + telefone.substring(0, 2) + ") " + telefone.substring(2, 7) + "-" + telefone.substring(7, telefone.length());
			}
			return telefoneMascara;
		} catch (Exception e){
			
		}
		return "";
	}
	
	public static String removeMascaraTelefone(String telefone){
		telefone = telefone.replaceAll("[()-]", "");
		return telefone;
	}
	
	public static String mascaraDinheiro(String dinheiro){
		String[] valor = dinheiro.split(",");
		dinheiro = "R$ " + valor[0] + "," + valor[1];
		return dinheiro;
	}
}

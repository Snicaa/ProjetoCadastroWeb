package br.com.fti.cadastro.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.springframework.format.annotation.DateTimeFormat;

import com.sun.istack.internal.NotNull;

public class Pessoa {
	
	@NotNull
	@Size(min=2, max=50, message = "Campo nome deve ser preenchido (2 a 50 caracteres")
	private String nome;
	
	@NotNull
	@Digits(integer=11, fraction=0)
	@Size(min=11, message = "Campo CPF deve estar preenchido")
	private String cpf;
	
	//@Pattern(message="Data inválida", regexp="^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$")
	private String dataNascimento;
	
	//@Past(message="Data de nascimento inválida")
	//@DateTimeFormat(pattern="dd/MM/yyyy")
	//private Date dataNascimento;
	
	@NotNull
	private char sexo;	
	
	@NotNull
	@Size(max=255, message="Endereço não pode possuir mais de 255 caracteres")
	private String endereco;
	
	@NotNull
	@Digits(integer=15, fraction=0)
	@Size(min=7, max=15, message="Número de telefone inválido")
	private String telefone;
	
	@Size(max=35, message = "E-mail muito longo")
	@Email(message = "E-mail deve ser válido.")
	private String email;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public Date getDataNascimento() {
		try {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.parse(dataNascimento);
		} catch (Exception e) {
			System.out.println("DEU ERRO AQUI");
			return null;
		}
	}

	public void setDataNascimento(Date dataNascimento) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		this.dataNascimento = sdf.format(dataNascimento);
	}

//	public Date getDataNascimento() {
//		try {
//		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//		return sdf.parse(getDataNascimentoStr());
//		} catch (ParseException e) {
//			System.out.println("DEU ERRO AQUI");
//			return null;
//		}
//	}
//
//	public void setDataNascimento(Date dataNascimento) {
//		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//		this.dataNascimentoStr = sdf.format(dataNascimentoStr);
//	}

	public char getSexo() {
		return sexo;
	}

	public void setSexo(char sexo) {
		this.sexo = sexo;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}

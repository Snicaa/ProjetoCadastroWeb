package br.com.fti.cadastro.model;

import java.util.Date;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.springframework.format.annotation.DateTimeFormat;

import com.sun.istack.internal.NotNull;

public class Pessoa {
	
	@Size(min=2, max=50, message = "Campo nome deve ser preenchido (2 a 50 caracteres")
	private String nome;
	
	@Size(min=11, message = "Campo CPF deve estar preenchido")
	private String cpf;
	
	@Past(message="Data de nascimento inválida")
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date dataNascimento;
	
	@NotNull
	private char sexo;
	
	@NotNull
	@Size(max=255, message="Endereço não pode possuir mais de 255 caracteres")
	private String endereco;
	
	@NotNull
	@Size(min=7, max=15, message="Número de telefone inválido")
	private String telefone;
	
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
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public char getSexo() {
		return sexo;
	}

	public void setSexo(char sexo) {
		this.sexo = sexo;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereço) {
		this.endereco = endereço;
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

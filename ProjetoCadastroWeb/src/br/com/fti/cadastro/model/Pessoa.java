package br.com.fti.cadastro.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import com.sun.istack.internal.NotNull;

import br.com.fti.cadastro.controller.UtilController;

@MappedSuperclass
public class Pessoa {
	
	@NotNull
	@Size(min=2, max=50, message = "Campo nome deve ser preenchido (2 a 50 caracteres")
	private String nome;
	
	@Size(min=11, max=11)
	private String cpf;
	
	@Column(name = "datanascimento")
	@Temporal(TemporalType.DATE)
	@Past
	private Date dataNascimento;	
	
	@NotNull
	@Size(min=1, max=1)
	private String sexo;	
	
	@NotNull
	@Column(name = "endereço")
	@Size(max=255, message="Endereço não pode possuir mais de 255 caracteres")
	private String endereco;
	
	@NotNull
	@Size(min=10, max=20, message="Número de telefone inválido")
	private String telefone;
	
	@Size(max=35, message = "E-mail muito longo")
	@Email(message = "E-mail deve ser válido.")
	private String email;
	
	private int ativo;

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
		if (cpf.contains(".")) {
			cpf = UtilController.removeMascaraCpf(cpf);
		}
		this.cpf = cpf;
	}
	
	public void setCpfFormatado(String cpf) {
		if (cpf.contains(".")) {
			cpf = UtilController.removeMascaraCpf(getCpf());
		}
		this.cpf = cpf;
	}
	
	public String getCpfFormatado(){
		return UtilController.mascaraCpf(getCpf());
	}
	
	public Date getDataNascimento() {
		return this.dataNascimento;
	}
	
	public String getDataNascimentoStr(){
		return UtilController.sdf.format(dataNascimento);
	}
	
	public void setDataNascimento(Date data){
		this.dataNascimento = data;
	}
	
	public void setDataNascimentoStr(String str) {
		this.dataNascimento = UtilController.converteStringEmData(str);
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	public String getTelefoneFormatado(){
		return UtilController.mascaraTelefone(telefone);
	}
	
	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		if (telefone.contains(")")) {
			telefone = UtilController.removeMascaraTelefone(telefone);
		}
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setAtivo(int ativo){
		this.ativo = ativo;
	}
	
	public int getAtivo(){
		return this.ativo;
	}
	
}

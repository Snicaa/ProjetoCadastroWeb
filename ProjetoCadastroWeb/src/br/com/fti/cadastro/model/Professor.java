package br.com.fti.cadastro.model;

import javax.validation.constraints.NotNull;

public class Professor extends Funcionario {
	
	@NotNull
	private String disciplina;
	
	public String getDisciplina() {
		return disciplina;
	}
	
	public void setDisciplina(String disciplina) {
		this.disciplina = disciplina;
	}
}

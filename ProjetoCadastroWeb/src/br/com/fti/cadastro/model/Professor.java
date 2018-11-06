package br.com.fti.cadastro.model;

public class Professor extends Funcionario {
	private String disciplina;

	public String getDisciplina() {
		return disciplina;
	}
	
	public void setDisciplina(String disciplina) {
		this.disciplina = disciplina;
	}
}

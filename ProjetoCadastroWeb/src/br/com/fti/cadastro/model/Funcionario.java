package br.com.fti.cadastro.model;

import java.util.ArrayList;

import br.com.fti.cadastro.controller.UtilController;

public class Funcionario extends Pessoa {
	private Long cadastro;
	private String cargo;
	private double salario, valeAlimentacao, valeTransporte, valeRefeicao;
	private int filhos;
	private ArrayList<Pessoa> listaFilhos;
	
	public Long getCadastro() {
		return cadastro;
	}
	
	public void setCadastro(Long cadastro) {
		this.cadastro = cadastro;
	}
	
	public String getCargo() {
		return cargo;
	}
	
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	
	public double getSalario() {
		return salario;
	}
	
	public String getSalarioFormatado(){
		return UtilController.mascaraDinheiro(salario);
	}
	
	public void setSalario(double salario) {
		this.salario = salario;
	}
	
	public double getValeAlimentacao() {
		return valeAlimentacao;
	}
	
	public String getValeAlimentacaoFormatado() {
		return UtilController.mascaraDinheiro(valeAlimentacao);
	}
	
	public void setValeAlimentacao(double valeAlimentacao) {
		this.valeAlimentacao = valeAlimentacao;
	}
	
	public double getValeTransporte() {
		return valeTransporte;
	}
	
	public String getValeTransporteFormatado() {
		return UtilController.mascaraDinheiro(valeTransporte);
	}
	
	public void setValeTransporte(double valeTransporte) {
		this.valeTransporte = valeTransporte;
	}
	
	public double getValeRefeicao() {
		return valeRefeicao;
	}
	
	public String getValeRefeicaoFormatado() {
		return UtilController.mascaraDinheiro(valeRefeicao);
	}
	
	public void setValeRefeicao(double valeRefeicao) {
		this.valeRefeicao = valeRefeicao;
	}
	
	public int getFilhos() {
		return filhos;
	}
	
	public void setFilhos(int filhos) {
		this.filhos = filhos;
	}
	
	public ArrayList<Pessoa> getListaFilhos() {
		return listaFilhos;
	}
	
	public void setListaFilhos(ArrayList<Pessoa> listaFilhos) {
		this.listaFilhos = listaFilhos;
	}

}

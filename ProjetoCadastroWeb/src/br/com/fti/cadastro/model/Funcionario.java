package br.com.fti.cadastro.model;

import java.math.BigDecimal;
import java.util.ArrayList;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import br.com.fti.cadastro.controller.UtilController;

public class Funcionario extends Pessoa {
	
	@NotNull
	private Long cadastro;
	
	@NotNull
	private String cargo;
	
	@NotNull
	@Digits(integer=12, fraction=2)
	private BigDecimal salario;
	
	@Digits(integer=12, fraction=2)
	BigDecimal valeAlimentacao, valeTransporte, valeRefeicao;
	
	@NotNull
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
	
	public BigDecimal getSalario() {
		return salario;
	}
	
	public String getSalarioFormatado(){
		return UtilController.mascaraDinheiro(salario);
	}
	
	public void setSalario(String salario) {
		if(salario.contains(",")){
			salario = UtilController.removeMascaraDinheiro(salario);
		}
		this.salario = new BigDecimal(salario);
	}
	
	public BigDecimal getValeAlimentacao() {
		return valeAlimentacao;
	}
	
	public String getValeAlimentacaoFormatado() {
		return UtilController.mascaraDinheiro(valeAlimentacao);
	}
	
	public void setValeAlimentacao(String valeAlimentacao) {
		if(valeAlimentacao.contains(",")){
			valeAlimentacao = UtilController.removeMascaraDinheiro(valeAlimentacao);
		}
		this.valeAlimentacao = new BigDecimal(valeAlimentacao);
	}
	
	public BigDecimal getValeTransporte() {
		return valeTransporte;
	}
	
	public String getValeTransporteFormatado() {
		return UtilController.mascaraDinheiro(valeTransporte);
	}
	
	public void setValeTransporte(String valeTransporte) {
		if(valeTransporte.contains(",")){
			valeTransporte = UtilController.removeMascaraDinheiro(valeTransporte);
		}
		this.valeTransporte = new BigDecimal(valeTransporte);
	}
	
	public BigDecimal getValeRefeicao() {
		return valeRefeicao;
	}
	
	public String getValeRefeicaoFormatado() {
		return UtilController.mascaraDinheiro(valeRefeicao);
	}
	
	public void setValeRefeicao(String valeRefeicao) {
		if(valeRefeicao.contains(",")){
			valeRefeicao = UtilController.removeMascaraDinheiro(valeRefeicao);
		}
		this.valeRefeicao = new BigDecimal(valeRefeicao);
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

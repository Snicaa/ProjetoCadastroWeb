package br.com.fti.cadastro.controller;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.fti.cadastro.dao.FuncionarioDAO;
import br.com.fti.cadastro.model.Funcionario;
import br.com.fti.cadastro.model.Pessoa;

@Controller
public class FuncionarioController {
	
	private final FuncionarioDAO dao;
	
	@Autowired
	public FuncionarioController(FuncionarioDAO dao){
		this.dao = dao;
	}
	
	@RequestMapping("cadastrarFuncionario")
	public String cadastrar(){
		return "funcionarios/formulario";
	}
	
	@RequestMapping("listaFuncionarios")
	public String lista(Model model) {
		model.addAttribute("funcionarios", dao.listaFuncionario());
		
		return "funcionarios/lista";
	}
	
	@RequestMapping("funcionarioCadastrado")
	public String adiciona(@Valid Funcionario funcionario, BindingResult result, String[] nomeFilho, String[] dataFilho, Model model) {
		
		model.addAttribute("aluno", funcionario);
		
		if(result.hasFieldErrors()){
			return"funcionarios/formulario";
		}
		
		if(!UtilController.validaCpf(funcionario.getCpf())){
			return "funcionarios/formulario";
		}
		
		if (!nomeFilho[0].equals("")){
			funcionario.setListaFilhos(geraListaFilhos(nomeFilho, dataFilho));
		}
		
		if(funcionario.getCadastro() > 0){
			dao.alterarFuncionario(funcionario);
			return "alunos/listaAlunos";
		}
		dao.cadastrarFuncionario(funcionario);
		return "funcionarios/lista";
	}
	
	public ArrayList<Pessoa> geraListaFilhos(String[] nomeFilho, String[] dataFilho){
		ArrayList<Pessoa> listaFilhos = new ArrayList<Pessoa>();
		
		for(int i = 0; i < nomeFilho.length; i++){
			Pessoa filho = new Pessoa();
			filho.setNome(nomeFilho[i]);
			filho.setDataNascimento(dataFilho[i]);
			
			listaFilhos.add(filho);
		}
		
		return listaFilhos;
	}
	
}

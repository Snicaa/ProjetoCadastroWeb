package br.com.fti.cadastro.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.fti.cadastro.dao.FuncionarioDAO;
import br.com.fti.cadastro.model.Aluno;
import br.com.fti.cadastro.model.Filho;
import br.com.fti.cadastro.model.Funcionario;
import br.com.fti.cadastro.model.Pessoa;
import br.com.fti.cadastro.model.Professor;

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
	
	@RequestMapping("editarFuncionario")
	public String editar(@RequestParam String cadastro, Model model){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("alunos");
    	EntityManager manager = factory.createEntityManager();
    	
    	model.addAttribute("funcionario", manager.find(Funcionario.class, Long.parseLong(cadastro)));
    	
    	manager.close();
	    factory.close();
    	return "funcionarios/formulario";
	}
	
	@RequestMapping("funcionarioCadastrado")
	public String adiciona(@Valid Funcionario funcionario, BindingResult result, String disciplina, String[] nomeFilho, String[] dataFilho, Model model) {
		
		model.addAttribute("funcionario", funcionario);
		
		if(result.hasFieldErrors()){
			return"funcionarios/formulario";
		}
		
		if(!UtilController.validaCpf(funcionario.getCpf())){
			return "funcionarios/formulario";
		}
		
		if (!nomeFilho[0].equals("")){
			funcionario.setListaFilhos(FuncionarioController.geraListaFilhos(nomeFilho, dataFilho));
		}
		
		if (disciplina != null && !disciplina.equals("")){
			funcionario = instanciaProfessor(funcionario, disciplina);
		}
		
		if(funcionario.getCadastro() > 0){
			dao.alterarFuncionario(funcionario);
			return "funcionarios/listaFuncionarios";
		}
		dao.cadastrarFuncionario(funcionario);
		return "funcionarios/lista";
	}
	
	public static List<Filho> geraListaFilhos(String[] nomeFilho, String[] dataFilho){
		List<Filho> listaFilhos = new ArrayList<Filho>();
		
		for(int i = 0; i < nomeFilho.length; i++){
			Filho filho = new Filho();
			filho.setNome(nomeFilho[i]);
			filho.setDataNascimento(UtilController.converteStringEmData(dataFilho[i]));
			
			listaFilhos.add(filho);
		}
		
		return listaFilhos;
	}
	
	private Professor instanciaProfessor(Funcionario func, String disciplina){
		Professor prof = new Professor();
		
		prof.setCadastro(func.getCadastro());
		prof.setNome(func.getNome());
		prof.setCpf(func.getCpf());
		prof.setDataNascimento(func.getDataNascimento());
		prof.setSexo(func.getSexo());
		prof.setEndereco(func.getEndereco());
		prof.setEmail(func.getEmail());
		prof.setTelefone(func.getTelefone());
		
		prof.setCargo(func.getCargo());
		prof.setDisciplina(disciplina);
		
		prof.setSalario(func.getSalario().toString());
		prof.setValeAlimentacao(func.getValeAlimentacao().toString());
		prof.setValeRefeicao(func.getValeRefeicao().toString());
		prof.setValeTransporte(func.getValeTransporte().toString());
		prof.setFilhos(func.getFilhos());
		prof.setListaFilhos(func.getListaFilhos());
		
		return prof;
	}
	
	@RequestMapping(value="getListaDependentes", method=RequestMethod.GET, produces="application/json")
	public @ResponseBody List<Filho> getListaDependentes(String cadastro) {
		System.out.println(cadastro);
		List<Filho> list = dao.consultarFilhos(Long.parseLong(cadastro));
		return list;
	}
	
}

package br.com.fti.cadastro.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
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
import br.com.fti.cadastro.model.Filho;
import br.com.fti.cadastro.model.Funcionario;

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
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("funcionarios");
		EntityManager manager = factory.createEntityManager();
		
		@SuppressWarnings("unchecked")
		List<Funcionario> lista = manager.createQuery("SELECT f FROM Funcionario as f WHERE f.ativo = 1 ORDER BY f.cadastro ASC").getResultList();
		
		manager.close();
	    factory.close();
	    
		model.addAttribute("funcionarios", lista);
		
		return "funcionarios/lista";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("editarFuncionario")
	public String editar(@RequestParam String cadastro, Model model){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("funcionarios");
    	EntityManager manager = factory.createEntityManager();
    	
    	Funcionario func = manager.find(Funcionario.class, Long.parseLong(cadastro));
    	
    	Query query = manager.createQuery("SELECT f FROM Filho as f WHERE f.funcionario.cadastro = :cadastro ORDER BY f.id ASC");
    	query.setParameter("cadastro", Long.parseLong(cadastro));
    	
		List<Filho> lista = query.getResultList();
    	
    	model.addAttribute("funcionario", func);
    	model.addAttribute("filhos", lista);
    	
    	manager.close();
	    factory.close();
    	return "funcionarios/formulario";
	}
	
	@RequestMapping("funcionarioCadastrado")
	public String adiciona(Funcionario funcionario, BindingResult result, String[] nomeFilho, String[] dataFilho, Model model) {
		/*
		if(result.hasFieldErrors()){
			return"funcionarios/formulario";
		}
		*/
		if(!UtilController.validaCpf(funcionario.getCpf())){
			return "funcionarios/formulario";
		}
		
		if (nomeFilho != null && !nomeFilho[0].equals("")){
			funcionario.setListaFilhos(FuncionarioController.geraListaFilhos(nomeFilho, dataFilho));
		}
		
		if (funcionario.getValeAlimentacao() == null){
			funcionario.setValeAlimentacao("0");
		}
		
		if (funcionario.getValeRefeicao() == null){
			funcionario.setValeRefeicao("0");
		}
		
		if (funcionario.getValeTransporte() == null){
			funcionario.setValeTransporte("0");
		}
		
		if(!funcionario.getCargo().equals("Professor")){
			funcionario.setDisciplina(null);
		}
		
		if(funcionario.getCadastro() > 0){
			funcionario.setAtivo(1);
			
			EntityManagerFactory factory = Persistence.createEntityManagerFactory("funcionarios");
	    	EntityManager manager = factory.createEntityManager();
	    	
	    	manager.getTransaction().begin();
	    	manager.merge(funcionario);
	    	manager.getTransaction().commit();
	    	
	    	manager.close();
		    factory.close();

			return "redirect:listaFuncionarios";
		}

		funcionario.setCadastro(null);
		funcionario.setAtivo(1);
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("funcionarios");
		EntityManager manager = factory.createEntityManager();
		
		manager.getTransaction().begin();
		manager.persist(funcionario);
		manager.getTransaction().commit();
				
		manager.close();
		factory.close();
		
		return "redirect:listaFuncionarios";
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
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="getListaDependentes", method=RequestMethod.GET, produces="application/json")
	public @ResponseBody List<Filho> getListaDependentes(String cadastro) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("funcionarios");
    	EntityManager manager = factory.createEntityManager();
		
    	Query query = manager.createQuery("SELECT f FROM Filho as f WHERE f.funcionario.cadastro = :cadastro ORDER BY f.id ASC");
    	query.setParameter("cadastro", Long.parseLong(cadastro));
    	
		List<Filho> list = query.getResultList();
		
		manager.close();
	    factory.close();
	    
		return list;
	}
	
}

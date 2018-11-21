package br.com.fti.cadastro.controller;

import br.com.fti.cadastro.dao.AlunoDAO;
import br.com.fti.cadastro.model.Aluno;

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
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AlunoController {
	
	private final AlunoDAO dao;
	
	@Autowired
	public AlunoController(AlunoDAO dao){
		this.dao = dao;
	}
	
	@RequestMapping("cadastrarAluno")
	public String form(Model model) {
		Aluno aluno = new Aluno();
		aluno.setMatricula((long)0);
		model.addAttribute("aluno", aluno);

		return "alunos/formulario";
	}
	
	@RequestMapping("editarAluno")
	public String editar(@RequestParam String matricula, Model model){
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("alunos");
    	EntityManager manager = factory.createEntityManager();
    	
    	model.addAttribute("aluno", manager.find(Aluno.class, Long.parseLong(matricula)));
    	
    	manager.close();
	    factory.close();
    	return "alunos/formulario";
	}
	
	@RequestMapping("alunoCadastrado")
	public String adiciona(@Valid Aluno aluno, BindingResult result, Model model) {
		
		if(result.hasFieldErrors()){
			model.addAttribute("aluno", aluno);
			return"alunos/formulario";
		}
		
		if(!UtilController.validaCpf(aluno.getCpf())){
			model.addAttribute("aluno", aluno);
			return "alunos/formulario";
		}
		
		if(aluno.getMatricula() > 0){
			aluno.setAtivo(1);
			EntityManagerFactory factory = Persistence.createEntityManagerFactory("alunos");
	    	EntityManager manager = factory.createEntityManager();
	    	
	    	manager.getTransaction().begin();
	    	manager.merge(aluno);
	    	manager.getTransaction().commit();
	    	
	    	manager.close();
		    factory.close();

			return "redirect:listaAlunos";
		}
		
		aluno.setMatricula(null);
		aluno.setAtivo(1);
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("alunos");
		EntityManager manager = factory.createEntityManager();
		
		manager.getTransaction().begin();
		manager.persist(aluno);
		manager.getTransaction().commit();
				
		manager.close();
		factory.close();
		
		return "redirect:listaAlunos";
	}
	
	@RequestMapping("listaAlunos")
	public String lista(Model model) {
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("alunos");
		EntityManager manager = factory.createEntityManager();
		
		@SuppressWarnings("unchecked")
		List<Aluno> lista = manager.createQuery("SELECT a FROM Aluno a WHERE a.ativo = 1 ORDER BY a.matricula ASC").getResultList();
		
		manager.close();
	    factory.close();
	    
		model.addAttribute("alunos", lista);
		
		return "alunos/lista";
	}
	
	@RequestMapping("removeAluno")
	public String remove(@RequestParam String matricula) {
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("alunos");
    	EntityManager manager = factory.createEntityManager();
    	
    	Aluno aluno = manager.find(Aluno.class, Long.parseLong(matricula));
    			
    	aluno.setAtivo(0);
    	
    	manager.getTransaction().begin();
    	manager.merge(aluno);
    	manager.getTransaction().commit();
    	
    	manager.close();
	    factory.close();

		return "redirect:listaAlunos";
	}

}

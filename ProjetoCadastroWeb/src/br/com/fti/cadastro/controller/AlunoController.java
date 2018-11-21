package br.com.fti.cadastro.controller;

import br.com.fti.cadastro.dao.AlunoDAO;
import br.com.fti.cadastro.model.Aluno;

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
	public String form(@RequestParam String matricula, Model model) {
		if (matricula != null && !matricula.equals("")) {
	    	EntityManagerFactory factory = Persistence.createEntityManagerFactory("alunos");
	    	EntityManager manager = factory.createEntityManager();
	    	
	    	model.addAttribute("aluno", manager.find(Aluno.class, Long.parseLong(matricula)));
	    	
	    	manager.close();
		    factory.close();
	    	return "alunos/formulario";
		} else {
			Aluno aluno = new Aluno();
			aluno.setMatricula((long)0);
			model.addAttribute("aluno", aluno);
		}
		
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
			dao.alterarAluno(aluno);
			model.addAttribute("alunos", dao.consultarListaAluno());
			return "alunos/listaAlunos";
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
		
		model.addAttribute("alunos", dao.consultarListaAluno());
		return "alunos/lista";
	}
	
	@RequestMapping("listaAlunos")
	public String lista(Model model) {
		model.addAttribute("alunos", dao.consultarListaAluno());
		
		return "alunos/lista";
	}
	
	@RequestMapping("removeAluno")
	public String remove(Aluno aluno) {
		dao.inativarAluno(aluno.getMatricula());
		return "redirect:listaAlunos";
	}
	
	@RequestMapping("mostraAluno")
	public String mostra(Long matricula, Model model) {
		model.addAttribute("aluno", dao.consultarPorMatricula(matricula));
		return "alunos/formulario";
	}
	
	@RequestMapping("alteraAluno")
	public String altera(Aluno aluno) {
		dao.alterarAluno(aluno);
		return "alunos/listaAlunos";
	}
}

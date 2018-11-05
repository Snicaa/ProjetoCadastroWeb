package br.com.fti.cadastro.controller;

import br.com.fti.cadastro.dao.AlunoDAO;
import br.com.fti.cadastro.model.Aluno;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	@RequestMapping("alunoCadastrado")
	public String adiciona(@Valid Aluno aluno, BindingResult result, Model model) {
		
		model.addAttribute("aluno", aluno);
		
		if(result.hasFieldErrors()){
			return"alunos/formulario";
		}
		
		System.out.println(aluno.getCpf());
		
		if(!UtilController.validaCpf(aluno.getCpf())){
			return "alunos/formulario";
		}
		
		if(aluno.getMatricula() > 0){
			dao.alterarAluno(aluno);
			return "alunos/listaAlunos";
		}
		dao.cadastrarAluno(aluno);
		return "alunos/lista";
	}
	
	@RequestMapping("listaAlunos")
	public String lista(Model model) {
		
		ArrayList<Aluno> arrayAlunos = dao.consultarListaAluno();
		
		model.addAttribute("alunos", arrayAlunos);
		
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

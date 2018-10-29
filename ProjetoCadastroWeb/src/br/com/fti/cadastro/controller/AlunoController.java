package br.com.fti.cadastro.controller;

import br.com.fti.cadastro.dao.AlunoDAO;
import br.com.fti.cadastro.model.Aluno;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AlunoController {
	
	private AlunoDAO dao = new AlunoDAO();
	
	@RequestMapping("cadastrarAluno")
	public String form() {
		return "alunos/formulario";
	}
	
	@RequestMapping("alunoCadastrado")
	public String adiciona(@Valid Aluno aluno, BindingResult result, Model model) {
		
		if(result.hasFieldErrors()){
			return"alunos/cadastro";
		}
		model.addAttribute("aluno", aluno);
		dao.cadastrarAluno(aluno);
		return "alunos/cadastrado";
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
		return "alunos/mostra";
	}
	
	@RequestMapping("alteraAluno")
	public String altera(Aluno aluno) {
		dao.alterarAluno(aluno);
		return "redirect:listaAlunos";
	}
}

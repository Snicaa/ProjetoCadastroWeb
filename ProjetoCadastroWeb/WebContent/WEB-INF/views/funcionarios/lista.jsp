<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cadastro FTI</title>
</head>
<body>
<h1>Lista de Funcionários</h1>
		<main>
			<table class="tabelaMain">
				<thead>
					<tr>
						<th>Cadastro</th>
						<th>Nome</th>
						<th>CPF</th>
						<th>Data de Nascimento</th>
						<th>Sexo</th>
						<th>Endereço</th>
						<th>Cargo</th>
						<th>Telefone</th>
						<th>E-mail</th>
						<th></th>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<th colspan="10">
							<input class="button" type="button" onclick="location.href='cadastrarFuncionario'" value="Cadastrar novo Funcionario">
							<a href="/ProjetoCadastroWeb/"><button>Menu</button></a>
						</th>
					</tr>
				</tfoot>
				<tbody>
					<c:forEach items="${funcionarios}" var="funcionario">
						<tr id="funcionario_${funcionario.cadastro}">
							<td>${funcionario.cadastro}</td>
							<td>${funcionario.nome}</td>
							<td>${funcionario.cpfFormatado}</td>
							<td><fmt:formatDate
									value="${funcionario.dataNascimento}"
									pattern="dd/MM/yyyy"/>
							</td>
							
								<c:if test="${funcionario.sexo == 'M'}">
								<td>Masculino</td>
								</c:if>
								<c:if test="${funcionario.sexo == 'F'}">
								<td>Feminino</td>
								</c:if>
							
							<td>${funcionario.endereco}</td>
							<td>${funcionario.cargo}</td>
							<td>${funcionario.telefoneFormatado}</td>
							<td>${funcionario.email}</td>
							<td style="display: flex-box; flex-orientation: row;"><a href="mostraAluno?matricula=${funcionario.cadastro}"><i style="color: black; margin-left:2%;" class="material-icons">create</i></a> &nbsp
							<a href="removeTarefa?id=${funcionario.cadastro}"><i style="color: black;" class="material-icons">delete</i></a></td>
						</tr>
						<tr id="funcionario_${funcionario.cadastro}aux" class="auxTr">
							<td colspan="10">
								<div class="divAuxInfo">
									<table style="width: 100%;">
									<tr>
										<th>Salário</th>
										<th>Vale Transporte</th>
										<th>Vale Alimentação</th>
										<th>Vale Refeição</th>
										<th>Nº de Dependentes</th>
										<c:if test="${funcionario.cargo eq 'Professor'}">
										<th>Disciplina</th>
										</c:if>
									</tr>
									<tr>
										<td>${funcionario.salario}</td>
										<td>${funcionario.valeTransporte}</td>
										<td>${funcionario.valeAlimentacao}</td>
										<td>${funcionario.valeRefeicao}</td>
										<td>${funcionario.filhos}</td>
										<c:if test="${funcionario.cargo eq 'Professor' }">
										<td>${funcionario.disciplina}</td>
										</c:if>
									</tr>
									
									<c:if test="${funcionario.filhos gt 0}">
										<tr></tr>
										<tr>
											<th colspan="5">Filhos</th>
										</tr>
										<tr>
											<th></th>
											<th colspan="2">Nome</th>
											<th colspan="2">Data de Nascimento</th>
											<th></th>
										</tr>
										<c:set var="counter" value="1"/>
										<c:forEach items="${funcionario.listaFilhos}" var="filho">
											<tr>
												<td style="text-align: right;">Filho ${counter}</td>
												<td colspan="2" style="text-align: center;">${filho.nome}</td>
												<td colspan="2" style="text-align: center;"><fmt:formatDate
															value="${filho.dataNascimento}"
															pattern="dd/MM/yyyy"/>
												</td>
											</tr>
											<c:set var="counter" value="${counter+1}"/>
										</c:forEach> 
									</c:if>
									</table>
								</div>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</main>
</body>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script>
	$(function(){
		$("td[colspan=10]").find(".divAuxInfo").hide();
		
		$(".tabelaMain").click(function(event){
			event.stopPropagation();
			
			var $target = $(event.target);
			
			if($target.closest("div").closest("tr").find("td").attr("colspan") > 9) {
				$target.closest("div").slideUp();
			} else { 
					$target.closest("tr").next().find(".divAuxInfo").slideToggle();
			}
		})
	})
	</script>
</html>
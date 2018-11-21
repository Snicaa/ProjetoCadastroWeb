<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link rel="stylesheet" type="text/css" href="css/style.css">
		<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
		<title>Cadastro FTI</title>
	</head>
	<body>
		<h1>Lista de Alunos</h1>
		<main>
			<table>
				<thead>
					<tr>
						<th>Matrícula</th>
						<th>Nome</th>
						<th>CPF</th>
						<th>Data de Nascimento</th>
						<th>Sexo</th>
						<th>Endereço</th>
						<th>Curso</th>
						<th>Telefone</th>
						<th>E-mail</th>
						<th></th>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<th colspan="10">
							<input class="button" type="button" onclick="location.href='cadastrarAluno'" value="Cadastrar novo Aluno">
							<a href="/ProjetoCadastroWeb/"><button>Menu</button></a>
						</th>
					</tr>
				</tfoot>
				<tbody>
					<c:forEach items="${alunos}" var="aluno">
						<tr id="aluno_${aluno.matricula}">
							<td>${aluno.matricula}</td>
							<td>${aluno.nome}</td>
							<td>${aluno.cpfFormatado}</td>
							<td><fmt:formatDate
									value="${aluno.dataNascimento}"
									pattern="dd/MM/yyyy"/>
							</td>
							
								<c:if test="${aluno.sexo == 'M'}">
								<td>Masculino</td>
								</c:if>
								<c:if test="${aluno.sexo == 'F'}">
								<td>Feminino</td>
								</c:if>
							
							<td>${aluno.endereco}</td>
							<td>${aluno.curso}</td>
							<td>${aluno.telefoneFormatado}</td>
							<td>${aluno.email}</td>
							<td style="display: flex-box; flex-orientation: row;"><a href="cadastrarAluno?matricula=${aluno.matricula}"><i style="color: black; margin-left:2%;" class="material-icons">create</i></a> &nbsp
							<a href="removeTarefa?id=${aluno.matricula}"><i style="color: black;" class="material-icons">delete</i></a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</main>
	</body>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

</html>
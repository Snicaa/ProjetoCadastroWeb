<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Cadastro FTI</title>
		<!--  <link rel="stylesheet" type="text/css" href="css/style.css"> -->
	</head>
	<h1>Cadastrar Aluno</h1>
	<main>
	<body>
		<div>
		<fmt:formatDate value="${aluno.dataNascimento}"  	
                pattern="yyyy-MM-dd"
                var="dataFormatada" />
			<form:errors path="*" cssStyle="color:red"/>
				<form id="formAluno" name="alunoCadastro" action="alunoCadastrado" method="post">
					<input type="hidden" name ="matricula" value="${aluno.matricula}"/>
					<label for ="nome">Nome:</label>
					<input type="text" min-length="2" name="nome" placeholder="" required value="${aluno.nome}" />
					<span class="validacao"></span><br/>
					
					<label for="cpf">CPF:</label>
					<input class="cpf" type="text" min-length="11" name="cpf" value="${aluno.cpf}" required>
					<span class="validacao"></span><br/>
					
					<label for="dataNascimento">Data de Nascimento:</label>
					<input type="text" name="dataNascimento" value="<fmt:formatDate value='${aluno.dataNascimento}'/>" required pattern="[0-9]{2}/[0-9]{2}/[0-9]{4}">
					<!-- <input type="date" name="dataNascimento" value='${dataFormatada}' required pattern="[0-9]{2}-[0-9]{2}-[0-9]{4}">-->
					<span class="validacao"></span><br/>
					Sexo:
					<input type="radio" name="sexo" value="M" <c:if test="${aluno.sexo eq 'M'.charAt(0)}">checked</c:if><c:if test="${empty aluno.sexo}">checked</c:if>> Masculino  &nbsp
					<input type="radio" name="sexo" value="F" <c:if test="${aluno.sexo eq 'F'.charAt(0)}">checked</c:if>> Feminino
					<span class="validacao"></span><br/>
					
					<label for="endereco">Endereço:</label>
					<textarea min-length="10" name="endereco" value="${aluno.endereco}" spellcheck="false" required placeholder="Avenida Ayrton Senna, 500 - Londrina, PR"></textarea>
					<span class="validacao"></span><br/><br/>
					<label for="curso">Curso:</label>
					<select name="curso" required>
					
					  	<option value="" disabled hidden 
					  	<c:if test='${empty aluno.curso }'>selected</c:if>
					  	>Selecionar...</option>
					  	
						<option value="Java WEB" 
						<c:if test='${aluno.curso eq "Java WEB"}'>selected</c:if>
						>Java WEB</option>
							
						<option value="Cobol"
						<c:if test='${aluno.curso eq "Cobol"}'>selected</c:if>
						>Cobol</option>
						
						<option value=".NET"
						<c:if test='${aluno.curso eq ".NET"}'>selected</c:if>
						>.NET</option>
						
						<option value="Redes"
						<c:if test='${aluno.curso eq "Redes"}'>selected</c:if>
						>Redes</option>
						
						<option value="Python"
						<c:if test='${aluno.curso eq "Python"}'>selected</c:if>
						>Python</option>
						
					</select>
					<span class="validacao"></span><br/>
					
					<label for="telefone">Telefone:</label>
					<input type="text" name="telefone" value="${aluno.telefone}" required placeholder="">
					<span class="validacao"></span><br/>
					
					<label for="email" >E-mail:</label>
					<input type="email" name="email" value="${aluno.email}" placeholder="exemplo: nome@gmail.com" required>
					<span class="validacao"></span>
					
				 	<input id="adicionar" class="button" type="submit" value="${aluno.matricula gt 0 ? 'Alterar' : 'Cadastrar'}"/>
				
					<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
					<script>
						
					</script>
				</form>
			</div>
		</body>
	</main>
</html>
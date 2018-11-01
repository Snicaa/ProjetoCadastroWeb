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
			<form:errors path="*" cssStyle="color:red"/>
				<form id="formAluno" name="alunoCadastro" action="alunoCadastrado" method="post">
					<input type="hidden" name ="matricula" value="${aluno.matricula}"/>
					
					<div class="campo">
						<label for ="nome">Nome:</label>
						<input id="campoNome" type="text" min-length="5" name="nome" placeholder="" required value="${aluno.nome}" />
						<span class="validacao"></span><br/>
					</div>
					
					<div class="campo">
						<label for="cpf">CPF:</label>
						<input id="campoCpf" class="cpf" type="text" name="cpf" value="${aluno.cpfFormatado}" required>
						<span class="validacao"></span><br/>
					</div>
					
					<label for="dataNascimento">Data de Nascimento:</label>
					<input id="campoData" type="text" name="dataNascimento" value="<fmt:formatDate value='${aluno.dataNascimento}'/>" required pattern="[0-9]{2}/[0-9]{2}/[0-9]{4}">
					<!-- <input type="date" name="dataNascimento" value='${dataFormatada}' required pattern="[0-9]{2}-[0-9]{2}-[0-9]{4}">-->
					<span class="validacao"></span><br/>
					Sexo:
					<input type="radio" name="sexo" value="M" <c:if test="${aluno.sexo eq 'M'}">checked</c:if><c:if test="${empty aluno.sexo}">checked</c:if>> Masculino  &nbsp
					<input type="radio" name="sexo" value="F" <c:if test="${aluno.sexo eq 'F'}">checked</c:if>> Feminino
					<span class="validacao"></span><br/>
					
					<label for="endereco">Endereço:</label>
					<textarea min-length="10" name="endereco" value="${aluno.endereco}" spellcheck="false" required placeholder="Avenida Ayrton Senna, 500 - Londrina, PR">${aluno.endereco}</textarea>
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
					<input id="campoTelefone" type="text" name="telefone" value="${aluno.telefone}" required placeholder="">
					<span class="validacao"></span><br/>
					
					<div class="campo">
						<label for="email" >E-mail:</label>
						<input id="campoEmail" type="email" name="email" value="${aluno.email}" placeholder="exemplo: nome@gmail.com" required>
						<span class="validacao"></span>
					</div>
					
				 	<input id="adicionar" class="button" type="submit" value="${aluno.matricula gt 0 ? 'Alterar' : 'Cadastrar'}"/>
				</form>
			</div>
		</body>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.0/jquery.mask.js"></script>
		
		<script>
		$(document).ready(function () { 
			
	        $("#campoCpf").mask('000.000.000-00', {reverse: true});
	        $('#campoData').mask('00/00/0000');
	       // $('#campoTelefone').mask('(00) 0000-0000');
	    });
		
		$("#campoNome").blur(function(){
			if($(this).val().length < 5 || $(this).val().length > 30){
				$(this).closest(".campo").find(".validacao").html("Nome inválido (5 à 50 caracteres)");
				alert("oi");	
			}
		});
		
		$("#campoCpf").blur(function(){
			var cpf = $('#campoCpf').val().replace(/[^0-9]/g, '').toString();
			$.post("confereCpf?cpf=" + cpf, function(resposta){
				if (resposta != ("")){
					$("#campoCpf").closest(".campo").find(".validacao").html("CPF inválido");
				}
			})
		});
		</script>
	</main>
</html>
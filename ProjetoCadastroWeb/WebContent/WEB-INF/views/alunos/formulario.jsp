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
               	type="date" 
                pattern="yyyy-MM-dd"
                var="dataFormatada" />
			<form:errors path="*" cssStyle="color:red"/>
				<form action="alunoCadastrado" method="post">
					<input type="hidden" value="${aluno.matricula}">
					Nome:<input type="text" pattern="[A-Za-z]" min-length="2" name="nome" placeholder="" required value="${aluno.nome}" /><br/>
					CPF:<input class="cpf" type="text" min-length="11" name="cpf" value="${aluno.cpf}" required><br/>
					Data de Nascimento: <input type="date" name="dataNascimento" value='${dataFormatada}' pattern="dd-MM-yyyy" required/><br/>
					Sexo: <input type="radio" name="sexo" value="M"> Masculino   &nbsp <input type="radio" name="sexo" value="F"> Feminino<br>
					Endereço: <textarea min-length="10" name="endereço" required placeholder="Avenida Ayrton Senna, 500\nLondrina - PR"></textarea><br/>
					Curso:
					<select name="curso" required>
					  	<option value="" disabled hidden>Selecionar...</option>
						<option value="Java WEB" 
						<c:if test='${aluno.curso eq "Java WEB"}'>selected</c:if>
						>Java WEB</option>
						<option value="Cobol"
						<c:if test='${aluno.curso eq "Java WEB"}'>selected</c:if>
						>Cobol</option>
						<option value=".NET"
						<c:if test='${aluno.curso eq "Java WEB"}'>selected</c:if>
						>.NET</option>
						<option value="Redes"
						<c:if test='${aluno.curso eq "Java WEB"}'>selected</c:if>
						>Redes</option>
						<option value="Python"
						<c:if test='${aluno.curso eq "Java WEB"}'>selected</c:if>
						>Python</option>
					</select><br/>
					Telefone: <input type="text" name="telefone" value="${aluno.telefone}" required placeholder=""><br/>
					E-mail: <input type="email" name="email" value="${aluno.email}" placeholder="exemplo: nome@gmail.com" required>
				 	<input id="adicionar" class="button" type="submit" value="${aluno.matricula gt 0 ? 'Alterar' : 'Cadastrar'}"/>
				
					<!--
					<textarea class="txt" placeholder="Informe a descrição da tarefa aqui." minlength="5"	 name="descricao" rows="10" cols="50" style="border: 1px solid black; overflow:auto; outline: none;"></textarea><br />
					<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
					<script>
							$(".txt").on("input", function(){
			                    var string = $(".txt").val();
								if (string.length < 5){
			                        if (string.length == 0){
			                            $(".txt").css("border", "1px black solid");
			                        } else {
			                            $(".txt").css("border", "1px red solid");
			                            $(".button").attr("disabled", "disabled");
			                        }
			                    } else {
			                        $(".txt").css("border", "1px green solid");
			                        $(".button").removeAttr("disabled");
			                    }
							});
						</script>
						-->
					</form>
				</div>
		</body>
	</main>
</html>
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
	<h1>Cadastrar Funcionário</h1>
	<main>
	<body>
		<div>
			<form:errors path="*" cssStyle="color:red"/>
				<form id="formAluno" name="alunoCadastro" action="alunoCadastrado" method="post">
					<input id="cadastro" type="hidden" name ="cadastro" value="${funcionario.cadastro}"/>
					
					<div class="campo">
						<label for ="nome">Nome*:</label>
						<input id="campoNome" type="text" min-length="5" name="nome" placeholder="" required value="${funcionario.nome}" />
						<span class="validacao"></span><br/>
					</div>
					
					<div class="campo">
						<label for="cpf">CPF*:</label>
						<input id="campoCpf" class="cpf" type="text" name="cpf" value="${funcionario.cpfFormatado}" required placeholder="___.___.___-__">
						<span class="validacao"></span><br/>
					</div>
					
					<div class="campo">
						<label for="dataNascimento">Data de Nascimento*:</label>
						<input id="campoData" type="text" name="dataNascimento" value="<fmt:formatDate value='${funcionario.dataNascimento}'/>" required pattern="[0-9]{2}/[0-9]{2}/[0-9]{4}" placeholder="dd/mm/aaaa">
						<span class="validacao"></span><br/>
					</div>
					<div class="campoRadio">
						<label for="sexo">Sexo*:</label>
						<input type="radio" name="sexo" value="M" <c:if test="${funcionario.sexo eq 'M'}">checked</c:if><c:if test="${empty funcionario.sexo}">checked</c:if>> Masculino  &nbsp
						<input type="radio" name="sexo" value="F" <c:if test="${funcionario.sexo eq 'F'}">checked</c:if>> Feminino
						<span class="validacao"></span><br/>
					</div>
					
					<div class="campo">
						<label for="endereco">Endereço*:</label>
						<textarea id="campoEndereco" min-length="10" name="endereco" spellcheck="false" required placeholder="Avenida Ayrton Senna, 500 - Londrina, PR">${funcionario.endereco}</textarea>
						<span class="validacao"></span><br/><br/>
					</div>
					<div class="campo">
						<label for="curso">Cargo*:</label>
						<select id="campoCurso" name="curso" required>
						
						  	<option value="" disabled hidden 
						  	<c:if test='${empty funcionario.cargo }'>selected</c:if>
						  	>Selecionar...</option>
						  	
							<option value="Professor" 
							<c:if test='${funcionario.cargo eq "Professor"}'>selected</c:if>
							>Professor</option>
								
							<option value="Cobol"
							<c:if test='${funcionario.cargo eq "Analista Mainframe"}'>selected</c:if>
							>Analista Mainframe</option>
							
							<option value="Analista Baixa Plataforma"
							<c:if test='${aluno.curso eq "Analista Baixa Plataforma"}'>selected</c:if>
							>Analista Baixa Plataforma</option>
							
							<option value="Programador Mainframe"
							<c:if test='${funcionario.cargo eq "Programador Mainframe"}'>selected</c:if>
							>Programador Mainframe</option>
							
							<option value="Programador Baixa Plataforma"
							<c:if test='${funcionario.cargo eq "Programador Baixa Plataforma"}'>selected</c:if>
							>Programador Baixa Plataforma</option>
							
							<option value="Líder de Projetos"
							<c:if test='${funcionario.cargo eq "Líder de Projetos"}'>selected</c:if>
							>Líder de Projetos</option>
							
							<option value="Gerente"
							<c:if test='${funcionario.cargo eq "Gerente"}'>selected</c:if>
							>Gerente</option>
							
						</select>
						<span class="validacao"></span><br/>
					</div>
					
					<div class="campo">
						<label for="telefone">Telefone*:</label>
						<input id="campoTelefone" type="text" name="telefone" value="${funcionario.telefoneFormatado}" required placeholder="(DD) XXXX-XXXX">
						<span class="validacao"></span><br/>
					</div>
					
					<div class="campo">
						<label for="email" >E-mail*:</label>
						<input id="campoEmail" type="email" name="email" value="${funcionario.email}" placeholder="exemplo: nome@gmail.com" required>
						<span class="validacao"></span>
					</div>
					
					<br>
					<br>
					<h2>Informações financeiras</h2>
					<div class="campo">
						<label for="salario">Salário*:</label>
						<input id="campoSalario" class="dinheiro" type="text" name="salario" value="${funcionario.salarioFormatado}" placeholder="R$ 0,00" required
						prefix="R$ " thousands="." decimal=",">
					</div>
					
					<div class="campo">
						<label for="valeAlimentacao">Vale Alimentação:</label>
						<input id="campoAlimentacao" class="dinheiro" type="text" name="valeAlimentacao" value="${funcionario.valeAlimentacaoFormatado}" placeholder="R$ 0,00">
					</div>
					
					<div class="campo">
						<label for="valeRefeicao">Vale Refeição:</label>
						<input id="campoRefeicao" class="dinheiro" type="text" name="valeRefeicao" value="${funcionario.valeRefeicaoFormatado}" placeholder="R$ 0,00">
					</div>
					
					<div class="campo">
						<label for="valeTransporte">Vale Transporte:</label>
						<input id="campoTransporte" class="dinheiro" type="text" name="valeTrasporte" value="${funcionario.valeAlimentacaoFormatado}" placeholder="R$ 0,00">
					</div>
					
					<br>
					<br>
					<div class="campo">
						<label for="filhos">Número de dependentes*:</label>
						<input id="campoFilhos" name="filhos" value="${funcionario.fihos}" required><button id="addFilhos" disabled>Cadastrar Filhos</button>
					</div>
					
				 	<input id="adicionar" class="button" type="submit" value="${funcionario.cadastro gt 0 ? 'Alterar' : 'Cadastrar'}"/>
				</form>
			</div>
		</body>
		
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.0/jquery.mask.js"></script>
		<script src="resources/js/jquery.maskMoney.js"></script>
		<jsp:include page="divFilhos.jsp" />  
		
		<script>
		var nomeTemErro = true;
		var cpfTemErro = true;
		var dataTemErro = true;
		var enderecoTemErro = true;
		var cursoTemErro = true;
		var telefoneTemErro = true;
		var emailTemErro = true;
		
		if ($("#cadastro").val() > 0){
			nomeTemErro = false;
			cpfTemErro = false;
			dataTemErro = false;
			enderecoTemErro = false;
			cursoTemErro = false;
			telefoneTemErro = false;
			emailTemErro = false;
		}
		
		$(document).ready(function () {
			$('.dinheiro').maskMoney();
			$("#adicionar").attr("title", erros);
	        $("#campoCpf").mask('000.000.000-00', {reverse: true});
	        $('#campoData').mask('00/00/0000');
	        $('#campoTelefone').mask('(00) 0000-0000');
	        if($("#campoFilhos").val() > 0){
	        	$("#addFilhos").removeAttr("disabled", "disabled");
	        }
	    });
		
		function erros(){
			if (nomeTemErro || cpfTemErro || dataTemErro || enderecoTemErro|| cursoTemErro || telefoneTemErro || emailTemErro){
				$("#adicionar").attr("disabled", "disabled");
			} else {
				$("#adicionar").removeAttr("disabled", "disabled");
			}
			var stringErros = "";
			if (nomeTemErro){
				stringErros = stringErros + "Nome inválido;\n";
			}
			if (cpfTemErro){
				stringErros = stringErros + "CPF inválido;\n";
			}
			if (dataTemErro){
				stringErros = stringErros + "Data inválida;\n";
			}
			if (enderecoTemErro){
				stringErros = stringErros + "Endereço inválido;\n";
			}
			if (cursoTemErro){
				stringErros = stringErros + "Selecione o curso;\n";
			}
			if (telefoneTemErro){
				stringErros = stringErros + "Número de telefone inválido;\n";
			}
			if (emailTemErro){
				stringErros = stringErros + "Endereço de e-mail inválido;";
			}
			return stringErros;
		}
		
		$("#campoNome").blur(function(){
			if($(this).val().length < 5 || $(this).val().length > 30){
				$(this).closest(".campo").find(".validacao").html("Nome inválido (5 a 50 caracteres)");
				nomeTemErro = true;
			} else {
				$(this).closest(".campo").find(".validacao").html("");
				nomeTemErro = false;
				$("#adicionar").attr("title", erros);
			}
		});
		
		$("#campoCpf").blur(function(){
			var cpf = $('#campoCpf').val().replace(/[^0-9]/g, '').toString();
			$.post("validaCpf?cpf=" + cpf, function(resposta){
				$("#campoCpf").closest(".campo").find(".validacao").html(resposta);
				if (resposta == ""){
					cpfTemErro = false;
					$("#adicionar").attr("title", erros);
				} else {
					cpfTemErro = true;
				}
			})
		});
		
		$("#campoData").blur(function(){
			$.post("validaData?data=" + $(this).val(), function(resposta){
				$("#campoData").closest(".campo").find(".validacao").html(resposta);
				if (resposta == ""){
					dataTemErro = false;
					$("#adicionar").attr("title", erros);
				} else {
					dataTemErro = true;
				}
			})
		});
		
		$("#campoEndereco").blur(function(){
			if($(this).val().length < 10 || $(this).val().length > 255){
				$(this).closest(".campo").find(".validacao").html("Endereço inválido (10 a 255 caracteres)");
				enderecoTemErro = true;
			} else {
				$(this).closest(".campo").find(".validacao").html("");
				enderecoTemErro = false;
				$("#adicionar").attr("title", erros);
			}
		});
		
		$("#campoCurso").blur(function(){
			if($(this).val() == "Selecionar..."){
				$(this).closest(".campo").find(".validacao").html("Selecione um curso");
				cursoTemErro = true;
			} else {
				$(this).closest(".campo").find(".validacao").html("");
				cursoTemErro = false;
				$("#adicionar").attr("title", erros);
			}
		});
		
		$("#campoTelefone").blur(function(){
			if ($(this).val().length < 10 || $(this).val().length > 15){
				$(this).closest(".campo").find(".validacao").html("Telefone inválido");
				telefoneTemErro = true;
			} else {
				$(this).closest(".campo").find(".validacao").html("");
				telefoneTemErro = false;
				$("#adicionar").attr("title", erros);
			}
		});
		
		$("#campoEmail").blur(function(){
			if ($(this).val().length < 5 || $(this).val().length > 30 || !$(this).val().includes("@")){
				$(this).closest(".campo").find(".validacao").html("E-mail inválido");
				emailTemErro = true;
			} else {
				$(this).closest(".campo").find(".validacao").html("");
				emailTemErro = false;
				$("#adicionar").attr("title", erros);
			}
		});
		
		$("#campoFilhos").on("input", function(){
			var valor = $(this).val();
			if (valor > 0){
				$("#addFilhos").removeAttr("disabled", "disabled");
			} else {
				$("#addFilhos").attr("disabled", "disabled");
			}
		});
		
		$("#divCadastroFilhos").dialog({
			autoOpen: false,
			title: 'Cadastrar Filhos',
			close: function(){
				
			}
		});
		$("#addFilhos").click(function(){
			$("#divCadastroFilhos").find("#numeroFilhos").val($("#campoFilhos").val())
			
			var numFilhos = $("#numeroFilhos").val();
			for(i = 1; i < numFilhos; i++){
				var novaLinha = $(".modelo").clone();
				novaLinha.removeClass("modelo").addClass("nova").find("input[name=txtModelo]").focus();
				novaLinha.find(".linkExcluir").removeClass("hidden");
				
				novaLinha.insertBefore(".fim");
			}
			
			$("#divCadastroFilhos").dialog('open');
			return false;
		});
		
		
		
		</script>
	</main>
</html>



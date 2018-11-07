<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="filhos" value=""/>
<div id="divCadastroFilhos">
<input type="hidden" id="numeroFilhos">

	<h2>Cadastro de filhos</h2>
	<br>
	<div class="modelo">	                        
	            <div class="campo" style="width:70%;">
	                <span>Filho </span>
	                <input type="text" name="txtModelo" value="" placeholder="Nome" />
					<a href="" class="linkExcluir hidden" onclick="excluirLinha(this)">Excluir linha</a>
				</div>							
	     </div>
	     <div class="fim"></div>
</div>
<script>
</script>
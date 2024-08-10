$(document).ready(function() {
	//Validação do campo da busca
	$("#erroNomeVazio").hide();
	
	$("#buscaPorNome").submit(function( event ) {	
	  	var nomeEvento = $( "#nomeEvento").val();
	  	if (nomeEvento == "") {
			event.preventDefault();
			$("#erroNomeVazio").show();
		} else {
			$("#buscaPorNome").submit();
			$("#erroNomeVazio").hide();
		}
	});
	
	
	//Validação dos campos de login
	$("#emailEmpty").hide();
	$("#passwordEmpty").hide();
	
	$("#formLogin").submit(function( event ) {	
	  	var username = $( "#username").val();
	  	var password = $( "#password").val();
	  	
	  	if (username == "") {
			event.preventDefault();
			$("#emailEmpty").show();
		}
		
		if (password == "") {
			event.preventDefault();
			$("#passwordEmpty").show();
		}
		
		if (username != "" && password != "") {
			$("#formLogin").submit();
			
			$("#emailEmpty").hide();
			$("#passwordEmpty").hide();
		}
	});
});
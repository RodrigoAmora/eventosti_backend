$(document).ready(function() {
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
});
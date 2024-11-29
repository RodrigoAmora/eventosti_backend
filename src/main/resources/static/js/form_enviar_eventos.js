$(document).ready(function() {
	$("#dataInicio").change(function() {
		var dataInicio = $("#dataInicio").val();
		var dateSplit = dataInicio.split("-");
		
		var d = new Date();
		d.setDate(dateSplit[2]);
		d.setMonth(dateSplit[1]);
		d.setFullYear(dateSplit[0]);
		
		
		const newDate = new Date(d);
    	newDate.setDate(d.getDate() + 1);
    
    	var day = ("0" + newDate.getDate()).slice(-2);
		var month = ("0" + (newDate.getMonth() + 1)).slice(-2);
		
		$("#dataFim").val(newDate.getFullYear()+"-"+(month)+"-"+(day));
	});
});

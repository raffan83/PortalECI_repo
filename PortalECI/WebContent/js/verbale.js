function eliminaRigaTabella(button, id_risp_tab, riga){
	var tr_element = $(button).parents("tr").first();
	var risposte_eliminate = [];
	$(tr_element).find(".input_hidden_id_risposta_colonna").each(function(i, element) {
		 risposte_eliminate.push($("<input>", {type:"hidden",name:"risposte_eliminate", value:$(element).val()}));
	});
	$(tr_element).html("");
	$(risposte_eliminate).each(function(i, element) {
		$(tr_element).append(element);
		$(tr_element).append($("<input>", {type:"hidden",name:"riga_risposta"+$(element).val(), value:riga}));
		$(tr_element).append($("<input>", {type:"hidden",name:"id_risposta_tabella"+$(element).val(), value:id_risp_tab}));
	});

}

function aggiungiRigaTabella(risposta_id, button){
	$(button).button("loading");
	$.ajax({
		type: "GET",
		url: "gestioneTabellaVerbale.do",
		data: {rispostaId:risposta_id},
		success: function( data, textStatus) {
			var tr_element = $(button).parents("tr").first();
			$(data).insertBefore(tr_element);
		},
		error: function( data, textStatus) {
			alert("ERRORE "+data.status)
		},
		complete:function(){
			$(button).button("reset");
		}
	});
}

$("#categoria-verifica-select").change(function() {
	//$("#tipo-verifica-select option[value='']").remove();
	if ($(this).data('options') == undefined) {
		/*Taking an array of all options-2 and kind of embedding it on the select1*/
		$(this).data('options', $('#tipo-verifica-select option').clone());
	}

	var id = $(this).val();
	var options = $(this).data('options');
	var opt = [];

	for (var i = 0; i < options.length; i++) {
		var str = options[i].value;

		if (str.substring(str.indexOf("_") + 1, str.length) == id || str=="") {
			opt.push(options[i]);
		}
	}

	$("#tipo-verifica-select").prop("disabled", false);
	$('#tipo-verifica-select').html(opt);

	$("#tipo-verifica-select").trigger("chosen:updated");
	$("#tipo-verifica-select").change();
});
$("#categoria-verifica-select").trigger("change");
 
//gruppo:	scedaTecnica | verbale | opzione
var indice = 0;
function aggiungiDomanda(gruppo, button){
	$.ajax({
		type: "GET",
		url: "gestioneDomandeQuestionario.do",
		data: {gruppo:gruppo, indice:indice},
		//if received a response from the server
		success: function( data, textStatus) {
			var container = $('#listaDomande'+gruppo);
			if(gruppo=="Opzione"){
				container = $(button).parents(".opzione-div").first().find(".lista-domande-opzioni-div").first();
				var counter = container.find(".numero-domande-opzione-input");
				counter.val(parseInt(counter.val())+1);

			}
			var new_domanda_element = jQuery(data)
			container.append(new_domanda_element);
			indice++;
            $('html, body').animate({
                scrollTop: $(new_domanda_element).offset().top
            }, 800);

		},
		error: function( data, textStatus) {
			alert("ERRORE "+data.status)
		}
	});
	
};

function aggiungiColonna(button, indiceColonna){
	$.ajax({
		type: "GET",
		url: "gestioneColonnaTabellaQuestionario.do",
		data: {indice:indice, indiceColonna:indiceColonna},
		//if received a response from the server
		success: function( data, textStatus) {
			container = $(button).parents(".risposta-RES_TABLE").first().find(".lista-colonne-div").first();
			var counter = container.find(".numero-colonne-tabella-input");
			counter.val(parseInt(counter.val())+1);

			var new_colonna_element = jQuery(data)
			container.append(new_colonna_element);
			indice++;
            $('html, body').animate({
                scrollTop: $(new_colonna_element).offset().top
            }, 800);

		},
		error: function( data, textStatus) {
			alert("ERRORE "+data.status)
		}
	});
	
};

$(document).ready(function(){
	if($(".domanda-indice-input").length)
		indice = Math.max.apply(Math, $(".domanda-indice-input").map(function() { return parseFloat(this.value) }));
	indice++;
})
$(document).on("click",".elimina-domanda-button",function() {
	var domanda_div = $(this).parents('.domanda-div').first();
	var gruppo = domanda_div.find(".domanda-gruppo-input").val();
	if(gruppo=="Opzione"){
		container = $(this).parents(".opzione-div").first().find(".lista-domande-opzioni-div")
		var counter = container.find(".numero-domande-opzione-input");
		counter.val(parseInt(counter.val())-1);
	}
	domanda_div.remove();
});

$(document).on("click",".elimina-colonna-button",function() {
	container = $(this).parents(".risposta-RES_TABLE").first().find(".lista-colonne-div").first();
	var counter = container.find(".numero-colonne-tabella-input");
	counter.val(parseInt(counter.val())-1);
	var colonna_div = $(this).parents('.colonna-div').first();
	colonna_div.remove();
});


$(document).on("change",".tipo-risposta-select",function() {
	$(this).parents(".domanda-div").first().find(".risposta-div").hide();
	$(this).parents(".domanda-div").first().find(".risposta-"+this.value).show();
	
	var optionElementList = $(this).parents(".domanda-div").first().find(".opzione-div input");
	if(this.value != "RES_CHOICE"){
		$(this).parents(".domanda-div").first().find(".numero-opzioni-input").val(0);
		optionElementList.prop( "disabled", true );
	}else{
		$(this).parents(".domanda-div").first().find(".numero-opzioni-input").val(optionElementList.length);
		optionElementList.prop( "disabled", false );
	}
});

function aggiungiOpzione(indice, button, aggiungiDomandaFlag=true) {
	newOption(indice, aggiungiDomandaFlag).insertBefore($(button).parents(".aggiungi-opzione-button-wp").first());
	
};

$(document).on("click",".rimuovi-opzione-button",function() {
	var counter = $(this).parents(".risposta-div").find(".numero-opzioni-input");
	counter.val(parseInt(counter.val())-1);
	$(this).parents(".opzione-div").first().remove();
});


function newOption(indice, aggiungiDomandaFlag){
	var elementString = '<div class="opzione-div row">'
							+'<div class="col-sm-6">'
								+'<div class="form-group">'
									+'<input type="text" class="form-control" placeholder="Opzione" name="opzione'+indice+'">'
								+'</div>'
							+'</div>'
							+'<div class="col-sm-3 ">'
								+'<button type="button" class="btn btn-danger btn-block rimuovi-opzione-button">elimina</button>'
							 +'</div>';
	if(aggiungiDomandaFlag){
		elementString = elementString+'<div class="col-sm-3">'
									+'<a class="btn btn-danger btn-block" onclick="aggiungiDomanda(\'Opzione\', this)">'
										+'<i class="fa fa-plus"></i> Aggiungi domanda'
									+'</a>'
								+'</div>';
	}
		elementString = elementString+'<div class="clearfix"></div>'
								+'<div class="lista-domande-opzioni-div">'
									+'<input type="hidden" name="numero-domande-opzione'+indice+'" class="numero-domande-opzione-input" value="0"/>'
								+'</div>'
							+'</div>';
	return jQuery(elementString)
}

function showError(textError){
	$('#myModalErrorContent').html(textError);
  	$('#myModalError').removeClass();
	$('#myModalError').addClass("modal modal-danger");
	$('#myModalError').modal('show');
}

$("#questionario-form").on("submit", function(event){
	if($("#questionario-form .domanda-div").length==0){
		showError("Devi inserire almeno una domanda");
		event.preventDefault();
	}
	$("#questionario-form .form-group").removeClass("has-error");
	$("#questionario-form .help-block").remove();
	var i=0;
	$("#questionario-form").find(":input").not(':button').not(":hidden").each(function(){
		var form_group = $(this).parents(".form-group").first();
		var target = $(this).parent(".input-group");
		if(target.length==0) target = $(this);
		
		if(this.value == ""){
			issueError(form_group,target, event,"Campo obbligatorio");
		}
		
		if($(this).hasClass("placeholder-domanda-input")){
			var checkel = this;
			$("#questionario-form").find(".placeholder-domanda-input").not(this).each(function(idx, el){
				if(el.value == checkel.value && !form_group.hasClass( "has-error" )){
					issueError(form_group,target, event,"Valore duplicato");
				}
			});
		}
		if($(this).hasClass("placeholder-risposta-input")){
			var checkel = this;
			$("#questionario-form").find(".placeholder-risposta-input").not(this).each(function(idx, el){
				if(el.value == checkel.value && !form_group.hasClass( "has-error" )){
					issueError(form_group,target, event,"Valore duplicato");
				}
			});
		}
		if($(this).hasClass("larghezza-colonna-input")){
			var somma = 0;
			$("[name="+$(this).attr("name")+"]").each(function(idx, el){
				somma=somma+parseInt(el.value);
			});
			if(somma != 100){
				issueError(form_group,target, event,"La somma deve essere 100");
			}
		}

	});
	function issueError(form_group,target, event,message){
		form_group.addClass( "has-error" );
		jQuery("<span/>", {"class":"help-block", "text":message}).insertAfter(target);
		event.preventDefault();
		if(!($("#myModalError").data('bs.modal') || {isShown: false}).isShown){
			$('#myModalErrorContent').html("Controlla le domande appena inserite. Correggi l'errore selezionato e riprova!");
			$('#myModalError').removeClass();
			$('#myModalError').addClass("modal modal-danger");
			$('#myModalError').modal('show');	
		}
	}
});

$(document).on("change","#questionario-form :input",function(){
	var form_group = $(this).parents(".form-group").first();
	form_group.removeClass("has-error");
	form_group.find(".help-block").remove();
})


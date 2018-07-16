
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
 
//tipo:		RES_TEXT | RES_CHOICE | RES_FORMULA
//gruppo:	scedaTecnica | verbale
function aggiungiDomanda(tipo, gruppo){
	$.ajax({
		type: "GET",
		url: "gestioneDomandeQuestionario.do",
		data: {gruppo:gruppo},
		//if received a response from the server
		success: function( data, textStatus) {            	
            $('#listaDomande'+gruppo).append(data);
		},
		error: function( data, textStatus) {
			alert("ERRORE "+data.status)
		}
	});
	
}

$(document).on("change",".tipo-risposta-select",function() {
	$(this).parents(".domanda-div").find(".risposta-div").hide();
	$(this).parents(".domanda-div").find(".risposta-"+this.value).show();
	
	var optionElementList = $(this).parents(".domanda-div").find(".opzione-div input");
	if(this.value != "RES_CHOICE"){
		$(this).parents(".domanda-div").find(".numero-opzioni-input").val(0);
		optionElementList.prop( "disabled", true );
	}else{
		$(this).parents(".domanda-div").find(".numero-opzioni-input").val(optionElementList.length);
		optionElementList.prop( "disabled", false );
	}
});

$(document).on("click",".aggiungi-opzione-button",function() {
	var counter = $(this).parents(".risposta-div").find(".numero-opzioni-input");
	counter.val(parseInt(counter.val())+1);
	newOption().insertBefore($(this).parents(".aggiungi-opzione-button-wp"));
	
});

$(document).on("click",".rimuovi-opzione-button",function() {
	var counter = $(this).parents(".risposta-div").find(".numero-opzioni-input");
	counter.val(parseInt(counter.val())-1);
	$(this).parents(".opzione-div").remove();
});


function newOption(){
	var elementString = '<div class="col-sm-4 opzione-div form-group">'
							+'<div class="input-group">'
								+'<input type="text" class="form-control" placeholder="Opzione" name="opzione">'
								+'<div class="input-group-btn">'
									+'<button type="button" class="btn btn-danger rimuovi-opzione-button">X</button>'
								+'</div>'
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
	$("#questionario-form .form-group").removeClass("has-error");
	$("#questionario-form .help-block").remove();
	$("#questionario-form").find(":input").not(':button').not(":hidden").each(function(){
		var form_group = $(this).parents(".form-group");
		var target = $(this).parent(".input-group");
		if(target.length==0) target = $(this);
		if(this.value == ""){
			form_group.addClass( "has-error" );
			jQuery("<span/>", {"class":"help-block", "text":"Campo obbligatorio"}).insertAfter(target);
			event.preventDefault();
		}
	});
});

$(document).on("change","#questionario-form :input",function(){
	var form_group = $(this).parents(".form-group");
	form_group.removeClass("has-error");
	form_group.find(".help-block").remove();
})


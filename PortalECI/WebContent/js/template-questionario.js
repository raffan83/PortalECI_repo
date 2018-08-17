var PLACEHOLDERS = [];

$(document).ready(function() {
	$('#summernote').summernote({
		height : 200,
		tabsize : 2,
		lang : 'it-IT',
		hint: {
			match: /\B\$\{(\w*)$/,
			search: function (keyword, callback) {
				callback($.grep(PLACEHOLDERS, function (item) {
					return item.toUpperCase().indexOf(keyword.toUpperCase())  == 0;
				}));
			},
			template: function (item) {
				return '${'+item+'}';
			},
			content: function (item) {
				return '${'+item+'}';
			}

		  }
	});
	
	$('.note-codable').on('blur', function() {
		  var codeviewHtml        = $(this).val();
		  var $summernoteTextarea = $(this).closest('.note-editor').siblings('textarea');

		  $summernoteTextarea.val(codeviewHtml);
	});
	
	$.ajax({
		type : "GET",
		url : "gestionePlaceholder.do",
		data : "idQuestionario="+$("#idQuestionario").val()+"&type="+$("#tipo").val(),
		dataType : "json",
		success : function(data, textStatus) {
			PLACEHOLDERS=data;
		},
		error : function(jqXHR, textStatus, errorThrown) {
			console.log(textStatus);
		}
	});

});

function uploadAj(inputFileElement, type) {
	$('#myModalErrorContent').html("Caricamento in corso");
	$('#myModalError').removeClass();
	$('#myModalError').addClass("modal modal-default");
	$('#myModalError').modal('show');
	
	if(inputFileElement.value==""){
		$('#myModalErrorContent').html("Non hai selezionato nessun file");
		$('#myModalError').removeClass();
		$('#myModalError').addClass("modal modal-danger");
		return false;
	}
	var data = new FormData();
	data.append("type", type);
	data.append("file", inputFileElement.files[0]);
	$.ajax({
		url : "headerFooterUpload.do",
		type : 'POST',
		data : data,
		cache : false,
		processData : false, // Don't process the files
		contentType : false, // Set content type to false as jQuery will tell the server its a query string request
		dataType: 'json',
		success : function(data, textStatus, jqXHR) {
			$('#myModalErrorContent').html("File caricato con successo");
			$('#myModalError').removeClass();
			$('#myModalError').addClass("modal modal-success");
			var option = $("#select-file-"+type).find("option[value='"+data.fileName+"']")
			if(option.length==0){
				$("#select-file-"+type).append(jQuery("<option/>",{"value":data.fileName,"text":data.fileName}));
			}
			$("#select-file-"+type).val(data.fileName);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			$('#myModalErrorContent').html("Si è verificato un errore durante l'upload del file. Riprova più tardi");
			$('#myModalError').removeClass();
			$('#myModalError').addClass("modal modal-danger");
		},
	});
}


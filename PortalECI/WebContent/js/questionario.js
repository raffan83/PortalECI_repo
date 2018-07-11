$("#categoria-verifica-select").change(function() {
	$("#categoria-verifica-select option[value='']").remove();
	$("#tipo-verifica-select option[value='']").remove();
	if ($(this).data('options') == undefined) {
		/*Taking an array of all options-2 and kind of embedding it on the select1*/
		$(this).data('options', $('#tipo-verifica-select option').clone());
	}

	var id = $(this).val();
	var options = $(this).data('options');
	var opt = [];

	for (var i = 0; i < options.length; i++) {
		var str = options[i].value;

		if (str.substring(str.indexOf("_") + 1, str.length) == id) {
			opt.push(options[i]);
		}
	}

	$("#tipo-verifica-select").prop("disabled", false);
	$('#tipo-verifica-select').html(opt);

	$("#tipo-verifica-select").trigger("chosen:updated");
	$("#tipo-verifica-select").change();

});

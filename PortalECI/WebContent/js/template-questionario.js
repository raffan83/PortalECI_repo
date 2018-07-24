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
	
	$.ajax({
		type : "GET",
		url : "gestionePlaceholder.do",
		data : "",
		dataType : "json",
		success : function(data, textStatus) {
			PLACEHOLDERS=data;
		},
		error : function(jqXHR, textStatus, errorThrown) {
			console.log(textStatus);
		}
	});

})

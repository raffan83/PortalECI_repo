$(document).ready(function() {
	$('#summernote').summernote({
		height : 200,
		tabsize : 2,
		lang : 'it-IT',
		hint : {
			tags : [ 'apple', 'orange', 'watermelon', 'lemon' ],
			match : /\{\w*$/,
			search : function(keyword, callback) {
				callback($.grep(Object.keys(this.tags), function(item) {
					if (!keyword)
						return true
					else
						return item.indexOf(keyword) == 0;
				}));
			},
			template : function(item) {
				return '{' + item + '} : ' + this.tags[item];
			},
			content : function(item) {
				return '{' + item + '}';
			}
		}
	});
})

function test() {
	$.ajax({
		type : "GET",
		url : "gestionePlaceholder.do",
		data : "",
		dataType : "json",
		success : function(data, textStatus) {
			console.log(data);
		},

		error : function(jqXHR, textStatus, errorThrown) {
			console.log(textStatus);
		}
	});
}

var PLACEHOLDERS = [];

$(function () { 

	CKEDITOR.replace( 'summernote', {
		// Define the toolbar: http://docs.ckeditor.com/ckeditor4/docs/#!/guide/dev_toolbar
		// The full preset from CDN which we used as a base provides more features than we need.
		// Also by default it comes with a 3-line toolbar. Here we put all buttons in a single row.

		// Since we define all configuration options here, let's instruct CKEditor to not load config.js which it does by default.
		// One HTTP request less will result in a faster startup time.
		// For more information check http://docs.ckeditor.com/ckeditor4/docs/#!/api/CKEDITOR.config-cfg-customConfig
		customConfig: 'config.js',
		removeButtons : 'CreateDiv,Templates,Link,Unlink,Anchor,Image,Flash,Link,Unlink,Anchor,Form,Checkbox,Radio,Textarea,TextField,Select,Button,ImageButton,HiddenField,Iframe,Language,Smiley,Print,NewPage,Save',
		// Sometimes applications that convert HTML to PDF prefer setting image width through attributes instead of CSS styles.
		// For more information check:
		//  - About Advanced Content Filter: http://docs.ckeditor.com/ckeditor4/docs/#!/guide/dev_advanced_content_filter
		//  - About Disallowed Content: http://docs.ckeditor.com/ckeditor4/docs/#!/guide/dev_disallowed_content
		//  - About Allowed Content: http://docs.ckeditor.com/ckeditor4/docs/#!/guide/dev_allowed_content_rules
		disallowedContent: 'img{width,height,float}',
		extraAllowedContent: 'img[width,height,align]',

		// Enabling extra plugins, available in the full-all preset: http://ckeditor.com/presets-all
		//extraPlugins: ',uploadimage,uploadfile',
		extraPlugins: 'autotag',
		/*********************** File management support ***********************/
		// In order to turn on support for file uploads, CKEditor has to be configured to use some server side
		// solution with file upload/management capabilities, like for example CKFinder.
		// For more information see http://docs.ckeditor.com/ckeditor4/docs/#!/guide/dev_ckfinder_integration

		// Uncomment and correct these lines after you setup your local CKFinder instance.
		// filebrowserBrowseUrl: 'http://example.com/ckfinder/ckfinder.html',
		// filebrowserUploadUrl: 'http://example.com/ckfinder/core/connector/php/connector.php?command=QuickUpload&type=Files',
		/*********************** File management support ***********************/

		// Make the editing area bigger than default.
		height: 400,

		// An array of stylesheets to style the WYSIWYG area.
		// Note: it is recommended to keep your own styles in a separate file in order to make future updates painless.
		contentsCss: [ 'plugins/ckeditor/contents.css', 'plugins/ckeditor/style.css' , "plugins/ckeditor/bootstrap.css"],

		// This is optional, but will let us define multiple different styles for multiple editors using the same CSS file.
		bodyClass: 'document-editor',

		// Reduce the list of block elements listed in the Format dropdown to the most commonly used.
		format_tags: 'p;h1;h2;h3;pre',

		// Simplify the Image and Link dialog windows. The "Advanced" tab is not needed in most cases.
		removeDialogTabs: 'image:advanced;link:advanced',

		// Define the list of styles which should be available in the Styles dropdown list.
		// If the "class" attribute is used to style an element, make sure to define the style for the class in "mystyles.css"
		// (and on your website so that it rendered in the same way).
		// Note: by default CKEditor looks for styles.js file. Defining stylesSet inline (as below) stops CKEditor from loading
		// that file, which means one HTTP request less (and a faster startup).
		// For more information see http://docs.ckeditor.com/ckeditor4/docs/#!/guide/dev_styles
		stylesSet: [
			// Inline Styles 
			{ name: 'Tabella con bordi', element: 'table', attributes: { 'class': 'table table-bordered table-sm' } },
		],
		
		font_names: 'Arial/Arial, Helvetica, sans-serif;' +
	    			'Times New Roman/Times New Roman, Times, serif;' +
	    			'Courier New/Courier New,Courier,monospace'
		
	} );


})

$(document).ready(function() {
	/*$('#summernote').summernote({
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
	});*/
	
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
	
	$("#template-form").submit(function() {
		$('#templateHiddenField').val(CKEDITOR.instances['summernote'].getData());
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


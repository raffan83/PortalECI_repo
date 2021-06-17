
var data,table; 

//$body = $("body");

//$(document).on({ 
	 
  //  ajaxStart: function() { $body.addClass("loading");    },
   //  ajaxStop: function() { $body.removeClass("loading"); }    
//}); 



function Controllo() {
	callAction("login.do","#loginForm");		
}
	
function inviaRichiesta(event,obj) {
	if (event.keyCode == 13) 
    	 Controllo();
}
	
//	function callAction(action)
//	{
//		
////		document.forms[0].action=action;
////		document.forms[0].submit();
//	}
	
function callAction(action,formid,loader){
	if(loader){
		pleaseWaitDiv = $('#pleaseWaitDialog');
		pleaseWaitDiv.modal();
	}
	if(!formid){
		$("#callActionForm").attr("action",action);
		$("#callActionForm").submit();
	}else{
		$(formid).attr("action",action);
		$(formid).submit();
	}
//		document.forms[0].action=action;
//		document.forms[0].submit();
}
	
function explore(action){

	pleaseWaitDiv = $('#pleaseWaitDialog');
	pleaseWaitDiv.modal();
	$.ajax({
		type: "POST",
		url: action,
 
		//if received a response from the server
		success: function( data, textStatus) {
            	
            $('#corpoframe').html(data);

            pleaseWaitDiv.modal('hide');
		},
		error: function( data, textStatus) {
            	
			$('#corpoframe').html('Errore Server '+textStatus + "data "+data);
			pleaseWaitDiv.modal('hide');
		}
	});
}

function exploreModal(action,postData,container,callback){

	pleaseWaitDiv = $('#pleaseWaitDialog');
	pleaseWaitDiv.modal();
	$.ajax({
		type: "POST",
		url: action,
		data: postData,

		//if received a response from the server
		success: function( data, textStatus) {
			pleaseWaitDiv.modal('hide');
			$(container).html(data);
			
			if (typeof callback === "function") {
				callback(data, textStatus);
			}
		},
		error: function( data, textStatus) {
			pleaseWaitDiv.modal('hide');

			$(container).html(data);
			if (typeof callback === "function") {
				callback(data, textStatus);
			}

		}
	});
}
	
var promise;
	
function resetCalendar(container){
	$(container).fullCalendar( 'destroy');
}

function loadCalendar(action,postData,container,callback){

	pleaseWaitDiv = $('#pleaseWaitDialog');
	pleaseWaitDiv.modal();
	$.ajax({
		type: "POST",
		dataType: "json",
		url: action,
		data: postData,

		//if received a response from the server
		success: function( data, textStatus) {
			pleaseWaitDiv.modal('hide');

			jsonObj =  data.dataInfo.dataInfo;
			$(container).fullCalendar({
				events:jsonObj,
				selectable:true,
				selectHelper: true,
				eventOverlap: false,
				
				select: function (start, end, jsEvent, view) {
//          		$(container).fullCalendar('renderEvent', {
//            			start: start,
//            		 	end: end,
//            		 	block: true,
//            		} );
            		if(start.isBefore(moment())) {
            			$(container).fullCalendar('unselect');
            			$("#myModalErrorContent").html("Selezionare un range di date maggiore della data attuale");
            			$("#myModalError").modal();
            		 	       
            			return false;
            		}
            		$("#myModalPrenotazione").modal();
            		$('#myModalPrenotazione').on('hidden.bs.modal', function (e) {
            			$("#noteApp").val('');
            			$("#emptyPrenotazione").html('');
            		})

            		var dataObj = new Object(); 
            		var event = new Object();
            		event.start = start;
            		event.end = end;
            		dataObj.event = event;
            		dataObj.container = container;
            		promise = new Promise(
            				function (resolve, reject) {
            					resolve(dataObj);
            				}
            		);
            		 		
            		$(container).fullCalendar("unselect");
				},
				selectOverlap: function(event) {
					if(event.ranges && event.ranges.length >0) {
						return (event.ranges.filter(function(range){
							return (event.start.isBefore(range.end) && event.end.isAfter(range.start));
						}).length)>0;
					}else {
						return !!event && event.overlap;
					}
				},
				header: {
					left: 'prev,next today',
					center: 'title',
					right: 'month'
				},
				buttonText: {
					today: 'today',
					month: 'month'
				},
//            	eventRender: function(event, element, view) {
//         			return $('<span class=\"badge bg-red bigText\"">' 
//         		    	+ event.title + 
//         		        '</span>');
//         		 },	 
            		  
				eventClick: function(calEvent, jsEvent, view) {					
					callAction('listaCampioni.do?date='+moment(calEvent.start).format());
					// alert('Event: ' + moment(calEvent.start).format());              		
            		             
					$(this).css('border-color', 'red');

				},
				editable: true,
				drop: function (date, allDay) { // this function is called when something is dropped

					// retrieve the dropped element's stored Event Object
					var originalEventObject = $(this).data('eventObject');

					// we need to copy it, so that multiple events don't have a reference to the same object
					var copiedEventObject = $.extend({}, originalEventObject);

					// assign it the date that was reported
					copiedEventObject.start = date;
					copiedEventObject.allDay = allDay;
					copiedEventObject.backgroundColor = $(this).css("background-color");
					copiedEventObject.borderColor = $(this).css("border-color");

					// render the event on the calendar
					// the last `true` argument determines if the event "sticks" (http://arshaw.com/fullcalendar/docs/event_rendering/renderEvent/)
					$(container).fullCalendar('renderEvent', copiedEventObject, true);

					// is the "remove after drop" checkbox checked?
					if ($('#drop-remove').is(':checked')) {
						// if so, remove the element from the "Draggable Events" list
						$(this).remove();
					}

				}
			});             	         
            	
			//$(container).html(data);

			if (typeof callback === "function") {
				callback(data, textStatus);
			}
		},
		error: function( data, textStatus) {
			pleaseWaitDiv.modal('hide');

			$(container).html(data);
			if (typeof callback === "function") {
				callback(data, textStatus);
        	}

		},
		complete: function (data, textStatus){
			pleaseWaitDiv.modal('hide');

			if (typeof callback === "function") {
				callback(data, textStatus);
			}
		}
	});
}
	
function soloNumeri(campo){
	if(!campo.value.match(/^\d+$/)) {
		alert("Questo campo accetta solo numeri");
		return false;
	}else{
		return true;
	}
}
	

function IsDate(txtDate){
	
	var result=true
	txtDate=txtDate.value
    try{
    	if (txtDate.length != 10){
    		result=false;
        }else if(
              isNaN(txtDate.substring(0, 2))       ||
              txtDate.substring(2, 3) != "/" ||
              isNaN(txtDate.substring(3, 5))       ||
              txtDate.substring(5, 6) != "/" ||
              isNaN(txtDate.substring(6, 10))
        ){
            result=false
        }else{
           result=true;
        }
       
        return result
    }catch (e){
    	return null;
    }
}

function approvazioneFromModal(app){
	var str=$('#noteApp').val();

	if(str.length != 0){
		$('#myModal').modal('hide')
		var dataArr={"idPrenotazione" :data[0], "nota":str};           
    
		$.ajax({
			type: "POST",
			url: "gestionePrenotazione.do?param="+app,
			data: "dataIn="+JSON.stringify(dataArr),
			dataType: "json",

			success: function( data, textStatus) {	  
				if(data.success){ 
 
					if(app=="app"){
						$('#myModalErrorContent').html("Prenotazione Approvata");
						$('#myModalError').removeClass();
						$('#myModalError').addClass("modal modal-success");
						$('#myModalError').modal('show');
						$('#myModalError').on('hidden.bs.modal', function (e) {
							callAction('listaPrenotazioniRichieste.do');
						});

					}else{
						$('#myModalErrorContent').html("Prenotazione Non Approvata");
						$('#myModalError').removeClass();
						$('#myModalError').addClass("modal modal-warning");
						$('#myModalError').modal('show');
						$('#myModalError').on('hidden.bs.modal', function (e) {
							callAction('listaPrenotazioniRichieste.do');
						});
					}      	 
                                            		
				}else{
					$('#myModalErrorContent').html(data.messaggio);
    			  	$('#myModalError').removeClass();
    				$('#myModalError').addClass("modal modal-danger");
    				$('#myModalError').modal('show');             
				}
			},
			error: function(jqXHR, textStatus, errorThrown){
          	
				$('#myModalErrorContent').html(data.messaggio);
			  	$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");
				$('#myModalError').modal('show');
			}
		});
	}else{
		$('#empty').html("Il campo non pu&ograve; essere vuoto"); 
	}
  	   
}
   
   
function nuovoInterventoFromModal(divID){
	if(divID){
		$( divID ).modal();
	}else{
		$( "#myModal" ).modal();
	}
	  	   
}
   

function stripHtml(html){
    // Create a new div element
    var temporalDivElement = document.createElement("div");
    // Set the HTML content with the providen
    temporalDivElement.innerHTML = html;
    // Retrieve the text property of the element (cross-browser support)
    return temporalDivElement.textContent || temporalDivElement.innerText || "";
}

function saveInterventoFromModal(){

	var str1=$('#tecnici').val();

	
	//var str2="&note="+$('#noteVerbale').val();
	//var str3=$('#select2').val();
	var listCatVer='';
	$('.categoriaTipiRow').each(function(index, item){
		listCatVer+="&categoriaTipo="+item.attributes[2].value;
	});
	var skTecObb='';
	$('.skTecObb').each(function(index, item){
		if($(this)[0].checked){
			//skTecObb+="&schedaTecnica="+$(this).closest('tr').prop('id');
			skTecObb+="&schedaTecnica="+1;
		}else{
			skTecObb+="&schedaTecnica="+0;
		}
			
	});
	
	var noteVerb='';
	
	var attrezzature='';
	
	var sedi = '';
	
	var esercenti = '';
	
	var eff_ver = '';
	
	var tipo_ver = '';
	
	var descrizione_util = '';
	
	if(listCatVer==""){
		$('#empty').html("Devi inserire almeno un 'Tipo Verifica' per poter creare l'intervento!"); 
	}else if(str1!= null){
		//var dataArr={"tecnico":str};
		var table = $('#tabVerifica').DataTable();
		
		var data = table.rows().data();
		data.each(function (value, index) {
			
			var x = stripHtml(value[2]);
			
			noteVerb+="&note="+value[10];
			sedi+= "&sedi="+value[5];
			attrezzature+='&attrezzature='+value[4];
			esercenti+='&esercenti='+value[6];
			descrizione_util+='&descrizione_util='+value[7];
			eff_ver+='&effettuazione_verifica='+value[8];
			tipo_ver+='&tipo_verifica='+value[9];
		 });
	            
		
		pleaseWaitDiv = $('#pleaseWaitDialog');
		pleaseWaitDiv.modal();
	    
		$.ajax({
			type: "POST",
			url: "gestioneIntervento.do?action=new",
			//data: "dataIn="+JSON.stringify(dataArr),
			//data: "dataIn="+str1,
			data : "tecnico="+str1 +listCatVer+skTecObb+noteVerb+attrezzature+sedi+esercenti+eff_ver+tipo_ver+descrizione_util,
			//	'id='+ encodeURIComponent(id) + '&name='+ encodeURIComponent(name)
			dataType: "json",
			success: function( data, textStatus) {

				if(data.success){ 

					$('#myModal').modal('hide')
//	          		$('#errorMsg').html("<h3 class='label label-primary' style=\"color:green\">"+textStatus+"</h3>");
					var table = $('#tabPM').DataTable();

					intervento = data.intervento;

					var user = intervento.user;
					var tecnico =intervento.tecnico_verificatore;
					//var dataCreazione = moment(intervento.dataCreazione,"MMM DD, YYYY",'it');
					var dataCreazione = moment(intervento.dataCreazione).format('DD/MM/YYYY');
					var tipo_verifica="";
					var cat_verifica="";				
					
					$.each(intervento.tipo_verifica, function() {
						tipo_verifica+=this.codice+"<br/>";
						cat_verifica+=this.categoria.codice+"<br/>";
					});
				
					
					var rowNode =  table.row.add( [
						'<a class="btn" onclick="callAction(\'gestioneInterventoDati.do?idIntervento='+intervento.id+'\');">'+intervento.id+'</a>',
						//'<span class="label label-info">'+presso+'</span>',
						intervento.nome_sede,
						dataCreazione,
						'<span class="label " style="color:#000000 !important; background-color:#E4E5E0" !important">CREATO</span>',
						user.nominativo,
						tecnico.nominativo,
						cat_verifica,
						tipo_verifica,
						'<a class="btn" onclick="callAction(\'gestioneInterventoDati.do?idIntervento='+intervento.id+'\');"> <i class="fa fa-arrow-right"></i> </a>'
						] ).draw();
	          			  		          		
				}else{
					$('#modalErrorDiv').html(data.messaggio+' '+data.dettaglio);
					$('#myModalError').removeClass();
					$('#myModalError').addClass("modal modal-danger");
					$('#myModalError').modal('show');
				}
				pleaseWaitDiv.modal('hide');
			},

			error: function(jqXHR, textStatus, errorThrown){
	          
				$('#errorMsg').html("<h3 class='label label-danger'>"+textStatus+"</h3>");
				//callAction('logout.do');
				pleaseWaitDiv.modal('hide');
			}
		});
	}else{
		$('#empty').html("Il campo 'Tecnico Verificatore' non pu&ograve; essere vuoto"); 
	}
	  	   
}
   
function scaricaCertificato( idcampione ){
	if(idcampione!= 'undefined'){
		callAction('scaricaCertificato.do?action=certificatoCampione&idC='+idcampione);
	}else{
		$('#myModalErrorContent').html("Cartificato non disponibile");
		$('#myModalError').modal();
	}
}
    
function addCalendar(){
	$.ajax({
		type: "POST",
		url: "Scadenziario_create.do",
		data: "",
		dataType: "json",
	          
		//if received a response from the server
		success: function( data, textStatus) {
	          	
			if(data.success){
	             		
				jsonObj = [];
				for(var i=0 ; i<data.dataInfo.length;i++){
					var str =data.dataInfo[i].split(";");
					item = {};
					item ["title"] = str[1];
					item ["start"] = str[0];
					item ["allDay"] = true;
					item ["backgroundColor"] = "#ffbf00";
					item ["borderColor"] = "#ffbf00";
					jsonObj.push(item);
				}
	             		
				$('#calendario').fullCalendar({
					header: {
						left: 'prev,next today',
						center: 'title',
						right: 'month,agendaWeek,agendaDay'
					},
					buttonText: {
						today: 'today',
						month: 'month',
						week: 'week',
						day: 'day'
					},
					eventRender: function(event, element, view) {
						return $('<span class=\"badge bg-red bigText\"">' 
							+ event.title + 
							'</span>');
					},	 
					events:jsonObj,
   		           	eventClick: function(calEvent, jsEvent, view) {

   		           		//explore('listaCampioni.do?date='+moment(calEvent.start).format());
   		        	
   		        	callAction('listaCampioni.do?date='+moment(calEvent.start).format());
   		              // alert('Event: ' + moment(calEvent.start).format());              		
   		             
   		               $(this).css('border-color', 'red');

   		           	},
   		           	editable: true,
   		           	drop: function (date, allDay) { // this function is called when something is dropped

   		           		// retrieve the dropped element's stored Event Object
   		           		var originalEventObject = $(this).data('eventObject');

   		           		// we need to copy it, so that multiple events don't have a reference to the same object
   		           		var copiedEventObject = $.extend({}, originalEventObject);

   		           		// assign it the date that was reported
   		           		copiedEventObject.start = date;
   		           		copiedEventObject.allDay = allDay;
   		           		copiedEventObject.backgroundColor = $(this).css("background-color");
   		           		copiedEventObject.borderColor = $(this).css("border-color");

   		           		// render the event on the calendar
   		           		// the last `true` argument determines if the event "sticks" (http://arshaw.com/fullcalendar/docs/event_rendering/renderEvent/)
   		           		$('#calendar').fullCalendar('renderEvent', copiedEventObject, true);

   		           		// is the "remove after drop" checkbox checked?
   		           		if ($('#drop-remove').is(':checked')) {
   		           			// if so, remove the element from the "Draggable Events" list
   		           			$(this).remove();
   		           		}

   		           	}
				}); 
			}
		            	
		}
	});
}
   
var campioneSelected=null;
function prenotazioneFromModal(){
	promise.then(function (data) { 
		var nota = $("#noteApp").val();
		if(nota!=""){

			$.ajax({
				type: "POST",
	            url: "gestionePrenotazione.do?param=pren",
	            dataType: "json",
	            data: "dataIn={campione:"+JSON.stringify(campioneSelected)+",start:"+data.event.start.toISOString()+",end:"+data.event.end.toISOString()+",nota:"+nota+"}",
	            //if received a response from the server
	            success: function( dataResp, textStatus) {
	            
	            	$(data.container).fullCalendar('renderEvent', {
	            		start: data.event.start,
	           	        end: data.event.end,
	           	        block: true,
	           	        title: nota,
	           	        color: "#ffbf00",
	            	} );
	                      
	            	   
	            	$("#noteApp").val('');
	            	$("#emptyPrenotazione").html('');
	            	$("#myModalPrenotazione").modal('hide');
	            	pleaseWaitDiv.modal('hide');
	            		            
	            	if(dataResp.success){ 
	 
	            		$('#myModalErrorContent').html("Prenotazione effettuata con successo");
              			$('#myModalError').removeClass();
              			$('#myModalError').addClass("modal modal-success");
              			$('#myModalError').modal('show');
          
	            	}else{
	            		$('#myModalErrorContent').html(dataResp.messaggio);
	    			  	$('#myModalError').removeClass();
	    				$('#myModalError').addClass("modal modal-danger");
	    				$('#myModalError').modal('show');	             
	            	}	            	
	            },
	            error: function( data, textStatus) {

	                console.log(data);

	                $('#myModalErrorContent').html('Errore salvataggio');
    			  	$('#myModalError').removeClass();
    				$('#myModalError').addClass("modal modal-danger");
    				$('#myModalError').modal('show');
             	
	            	pleaseWaitDiv.modal('hide');
	            }
			});
		   
		
		}else{
			$("#emptyPrenotazione").html('Inserire una nota');
		}

	});
}

function scaricaPacchetti(){
	
}

function scaricaPacchetto(filename){

	callAction('scaricoStrumento.do?filename='+filename);

}
   
function enableInput(container){
	  
	if(container == "#datipersonali"){
		$("#datipersonali #indirizzoUsr").prop('disabled', false);
		$("#datipersonali #comuneUsr").prop('disabled', false);
		$("#datipersonali #capUsr").prop('disabled', false);
		$("#datipersonali #emailUsr").prop('disabled', false);
		$("#datipersonali #telUsr").prop('disabled', false);
		$("#datipersonali #modificaUsr").prop('disabled', true);
		$("#datipersonali #inviaUsr").prop('disabled', false);
	  
	}else if(container == "#datiazienda"){

		$("#datiazienda #indCompany").prop('disabled', false);
		$("#datiazienda #comuneCompany").prop('disabled', false);
		$("#datiazienda #capCompany").prop('disabled', false);
		$("#datiazienda #emailCompany").prop('disabled', false);
		$("#datiazienda #telCompany").prop('disabled', false);
		$("#datiazienda #modificaCompany").prop('disabled', true);
		$("#datiazienda #inviaCompany").prop('disabled', false);
	}	
	  
}

function salvaUsr(){
	pleaseWaitDiv = $('#pleaseWaitDialog');
	pleaseWaitDiv.modal();
	  
	var jsonData = {};	  
	jsonData.indirizzoUsr =  $("#datipersonali #indirizzoUsr").val();
	jsonData.comuneUsr =  $("#datipersonali #comuneUsr").val();
	jsonData.capUsr =  $("#datipersonali #capUsr").val();
	jsonData.emailUsr =  $("#datipersonali #emailUsr").val();
	jsonData.telUsr =  $("#datipersonali #telUsr").val();
	  
	$.ajax({
		type: "POST",
		url: "areaUtente.do?action=modifica_utente",
		data: "dataIn="+JSON.stringify(jsonData),
		//if received a response from the server
		success: function( dataResp, textStatus) {
			var dataRsp = JSON.parse(dataResp);
			if(dataRsp.success){
				$("#datipersonali #indirizzoUsr").prop('disabled', true);
				$("#datipersonali #comuneUsr").prop('disabled', true);
				$("#datipersonali #capUsr").prop('disabled', true);
				$("#datipersonali #emailUsr").prop('disabled', true);
				$("#datipersonali #telUsr").prop('disabled', true);
				$("#datipersonali #modificaUsr").prop('disabled', false);
				$("#datipersonali #inviaUsr").prop('disabled', true);
        		  
				$("#usrError").html('<h5>Modifica eseguita con successo</h5>');
				$("#usrError").addClass("callout callout-success");
			}else{
				$("#usrError").html('<h5>Errore salvataggio Utente</h5>');
      			$("#usrError").addClass("callout callout-danger");
			}
			pleaseWaitDiv.modal('hide');
		},
		error: function( data, textStatus) {

			console.log(data);
			$("#usrError").html('<h5>Errore salvataggio Utente</h5>');
			$("#usrError").addClass("callout callout-danger");
              

			pleaseWaitDiv.modal('hide');

		}
	});
}
  


function modificaPinFirma(nuovo_pin, pin_attuale, firma_documento){
	  pleaseWaitDiv = $('#pleaseWaitDialog');
	  pleaseWaitDiv.modal();

	  var dataObj = {};
		dataObj.nuovo_pin = nuovo_pin;
		if(pin_attuale!=null){
		dataObj.pin_attuale = pin_attuale;
		}
		dataObj.firma_documento = firma_documento
		
	  $.ajax({
        type: "POST",
        url: "areaUtente.do?action=modifica_pin",
        data: dataObj,
        dataType: "json",
        //if received a response from the server
        success: function( data, textStatus) {
      	  //var dataRsp = JSON.parse(dataResp);
      	  if(data.success)
    		  {        		
      		  pleaseWaitDiv.modal('hide');
      		 
      		  $('#modalModificaPin').modal('toggle');
      		  $('#report_button').hide();
					$('#visualizza_report').hide();
					$('#myModalErrorContent').html(data.messaggio);
					if(data.pin!=null){
						pin0=data.pin;
					}
					
					
			  	$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-success");
				$('#myModalError').modal('show');
				

    		  }else{
    			 pleaseWaitDiv.modal('hide');
//    			$("#usrError").html('<h5>Attenzione! il PIN inserito non &egrave; associato all\'utente corrente!</h5>');
//    			$("#usrError").addClass("callout callout-danger");
    			 $('#modalModificaPin').modal('toggle');
    			 $('#myModalErrorContent').html(data.messaggio);
			  	$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");	  
				//$('#report_button').show();
					//$('#visualizza_report').show();
					$('#myModalError').modal('show');
    			
    		  }
      	 
        },
        error: function( data, textStatus) {

            console.log(data);
            ('#myModalErrorContent').html(data.messaggio);
			  	$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");	  
				$('#report_button').show();
					$('#visualizza_report').show();
					$('#myModalError').modal('show');

        	pleaseWaitDiv.modal('hide');

        }
        });

}

function verificaPinFirma(pin, filename){
	  pleaseWaitDiv = $('#pleaseWaitDialog');
	  pleaseWaitDiv.modal();

	  var dataObj = {};
		dataObj.pin = pin;
	  $.ajax({
        type: "POST",
        url: "firmaDocumento.do?action=checkPIN",
        data: dataObj,
        dataType: "json",
        //if received a response from the server
        success: function( data, textStatus) {
      	  //var dataRsp = JSON.parse(dataResp);
      	  if(data.success)
    		  {  
      		  pleaseWaitDiv.modal('hide');
    		  
      		  firmaDocumento(filename);
    		  }else{
    			pleaseWaitDiv.modal('hide');
    			$('#pin').val("");
    			
    			$('#myModalErrorContent').html(data.messaggio);
			  	$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");
				if(data.num_error!=1){
				$('#report_button').show();
				$('#visualizza_report').show();
				}
				$('#myModalError').modal('show');
				
				
    		  }
        },
        error: function( data, textStatus) {

      	  $('#myModalErrorContent').html(data.messaggio);
		  	$('#myModalError').removeClass();
			$('#myModalError').addClass("modal modal-danger");	  
			$('#report_button').show();
				$('#visualizza_report').show();
				$('#myModalError').modal('show');


        	pleaseWaitDiv.modal('hide');

        }
        });

}



function salvaCompany(){
	pleaseWaitDiv = $('#pleaseWaitDialog');
	pleaseWaitDiv.modal();
	  
	var jsonData = {};	  
	jsonData.indCompany =  $("#datiazienda #indCompany").val();
	jsonData.comuneCompany =  $("#datiazienda #comuneCompany").val();
	jsonData.capCompany =  $("#datiazienda #capCompany").val();
	jsonData.emailCompany =  $("#datiazienda #emailCompany").val();
	jsonData.telCompany =  $("#datiazienda #telCompany").val();
	jsonData.modificaCompany =  $("#datiazienda #modificaCompany").val();
	jsonData.inviaCompany =  $("#datiazienda #inviaCompany").val();
	  
	$.ajax({
		type: "POST",
		url: "salvaCompany.do",
		data: "dataIn="+JSON.stringify(jsonData),
		//if received a response from the server
		success: function( dataResp, textStatus) {
			var dataRsp = JSON.parse(dataResp);
			if(dataRsp.success){
				$("#datiazienda #indCompany").prop('disabled', true);
				$("#datiazienda #comuneCompany").prop('disabled', true);
				$("#datiazienda #capCompany").prop('disabled', true);
				$("#datiazienda #emailCompany").prop('disabled', true);
				$("#datiazienda #telCompany").prop('disabled', true);
				$("#datiazienda #modificaCompany").prop('disabled', false);
				$("#datiazienda #inviaCompany").prop('disabled', true);
        		  
				$("#companyError").html('<h5>Modifica eseguita con successo</h5>');
				$("#companyError").addClass("callout callout-success");
			}else{
				$("#companyError").html('<h5>Errore salvataggio Azienda</h5>');
      			$("#companyError").addClass("callout callout-danger");
			}
			pleaseWaitDiv.modal('hide');
		},
		error: function( data, textStatus) {

			console.log(data);
			$("#companyError").html('<h5>Errore salvataggio Azienda</h5>');
			$("#companyError").addClass("callout callout-danger");

          	pleaseWaitDiv.modal('hide');

		}
	});
}
  
function saveValoriCampione(idC){
	var valid=true;
	var count = $('#tblAppendGrid').appendGrid('getRowCount'), index = '';
	for (var z = 0; z < count; z++) {

		var elem1 = $('#tblAppendGrid').appendGrid('getCellCtrl', 'incertezza_assoluta', z);
		var elem2 = $('#tblAppendGrid').appendGrid('getCellCtrl', 'incertezza_relativa', z);
		if(elem1.value=="" && elem2.value==""){
			valid = false;
		}
	}
     
//  corrispondenze = 0;
//  $('#tblAppendGrid tbody tr').each(function(){
//		var td = $(this).find('td').eq(1);
//		attr = td.attr('id');
//		   valore = $("#" + attr  + " input").val();
//		    
//		    $('#tblAppendGrid tbody tr').each(function(){
//				var td2 = $(this).find('td').eq(1);
//				attr2 = td2.attr('id');
//			    valore2 = $("#" + attr2  + " input").val();
//
//			    if(valore == valore2){
//			    	corrispondenze++;
//			    }
//			    	
//			});
//
//		});
//      validCorr = true;
//	  if(corrispondenze > 0 && $('#interpolato').val()==0){
//		  validCorr = false;
//	  }
//	  
	  var jsonMap = {};
	  $('#tblAppendGrid tbody tr').each(function(){
		  var td = $(this).find('td').eq(1);
		  attr = td.attr('id');
		  valore = $("#" + attr  + " input").val();

		  if(jsonMap[valore]){
			  jsonMap[valore] ++;
		  }else{
			  jsonMap[valore]=1;
		  }
	  });
	  validCorr = true;
	  validCorr2 = true;
	  $.each(jsonMap, function() {
		  if(this<2 && $('#interpolato').val()==1){
			  validCorr2 = false;
		  }
		  if(this>1 && $('#interpolato').val()==0){
			  validCorr = false;
		  }
		});
	  
	  if($("#formAppGrid").valid() && valid && validCorr && validCorr2){
		  $.ajax({
			  type: "POST",
			  url: "modificaValoriCampione.do?view=save&idC="+idC,
			  data: $( "#formAppGrid" ).serialize(),
			  //if received a response from the server
			  success: function( dataResp, textStatus) {
				  var dataRsp = JSON.parse(dataResp);
				  if(dataRsp.success){ 
					  $("#ulError").html("<span class='label label-danger'>Modifica eseguita con successo</span>");
               		  
               		  $('#myModalSuccessContent').html("Salvataggio effettuato con successo, click su Chiudi per tornare alla lista dei campioni");
               		  $('#myModalSuccess').addClass("modal modal-success");
               		  $('#myModalSuccess').modal('show');
               		  $('#myModalSuccess').on('hidden.bs.modal', function (e) {
               			  callAction('listaCampioni.do');
               		  });                            	                  
               		
				  }else{
					  $('#myModalErrorContent').html(data.messaggio);
					  $('#myModalError').removeClass();
					  $('#myModalError').addClass("modal modal-danger");
					  $('#myModalError').modal('show');
         		
					  $("#ulError").html("<span class='label label-danger'>Errore salvataggio</span>");
				  }
				  pleaseWaitDiv.modal('hide');
			  },
			  error: function( data, textStatus) {				  

				  console.log(data);
				  $("#ulError").html("<span class='label label-danger'>Errore salvataggio</span>");
     		  
				  $('#myModalErrorContent').html(data.messaggio);
			  	  $('#myModalError').removeClass();
				  $('#myModalError').addClass("modal modal-danger");
				  $('#myModalError').modal('show');
				  pleaseWaitDiv.modal('hide');

			  }
		  });
	  }else{
		  $("#ulError").html("<span class='label label-danger'>Compilare tutti i campi obbligatori</span>");
		  if(!valid){
			  $("#ulError").html("<span class='label label-danger'>Compilare tutti i campi obbligatori. Insereire Incertezza Assoluta o Incertezza Relativa</span>");
		  }
		  if(!validCorr){
			  $("#ulError").html("<span class='label label-danger'>I parametri di taratura devono essere univoci.</span>");
		  }
		  if(!validCorr2){
			  $("#ulError").html("<span class='label label-danger'>Lo stesso parametro di taratura deve essere presente almeno 2 volte.</span>");
		  }
	  }
}
  
function nuovoStrumento(idSede,idCliente){

	var ref_stato_strumento=$('#ref_stato_strumento').val();
	var denominazione=$('#denominazione').val();
	var codice_interno=$('#codice_interno').val();
	var costruttore=$('#costruttore').val();
	var modello=$('#modello').val();
	var matricola=$('#matricola').val();
	var risoluzione=$('#risoluzione').val();
	var campo_misura=$('#campo_misura').val();
	var ref_tipo_strumento=$('#ref_tipo_strumento').val();
	var freq_mesi=$('#freq_mesi').val();
	var dataUltimaVerifica=$('#dataUltimaVerifica').val();
	var dataProssimaVerifica=$('#dataProssimaVerifica').val();
	var ref_tipo_rapporto=$('#ref_tipo_rapporto').val();
	var reparto=$('#reparto').val();
	var utilizzatore=$('#utilizzatore').val();
	var note=$('#note').val();
	var luogo_verifica=$('#luogo_verifica').val();
	var interpolazione=$('#interpolazione').val();
	var classificazione=$('#classificazione').val();
	
	  		
	var dataObj = {};
	          
	dataObj.idSede = idSede;
	dataObj.idCliente = idCliente;
	dataObj.ref_stato_strumento = ref_stato_strumento;
	dataObj.denominazione = denominazione;
	dataObj.codice_interno = codice_interno;
	dataObj.costruttore = costruttore;
	dataObj.modello = modello;
	dataObj.matricola = matricola;
	dataObj.risoluzione = risoluzione;
	dataObj.campo_misura = campo_misura;
	dataObj.freq_mesi = freq_mesi;
	dataObj.dataUltimaVerifica = dataUltimaVerifica;
	dataObj.ref_tipo_strumento = ref_tipo_strumento;
	dataObj.dataProssimaVerifica = dataProssimaVerifica;
	dataObj.ref_tipo_rapporto = ref_tipo_rapporto;
	
	dataObj.reparto = reparto;
	dataObj.utilizzatore = utilizzatore;
	dataObj.note = note;
	dataObj.luogo_verifica = luogo_verifica;
	dataObj.interpolazione = interpolazione;
	dataObj.classificazione = classificazione;
	  		  		
	$.ajax({
		type: "POST",
		url: "nuovoStrumento.do",
		data: dataObj,
		dataType: "json",

		success: function( data, textStatus) {

			if(data.success){ 
				$('#modalNuovoStrumento').modal('hide');
				dataString ="idSede="+ idSede+";"+idCliente;
				exploreModal("listaStrumentiSedeNew.do",dataString,"#posTab",function(datab,textStatusb){
					// $('#errorMsg').html("<h3 class='label label-success' style=\"color:green\">"+data.message+"</h3>");
					$("#myModalErrorContent").html(data.message);
					$("#myModalError").modal();
				});
	          			  		          		
			}else{
				// $('#empty').html("<h3 class='label label-error' style=\"color:green\">"+data.message+"</h3>");
				$("#myModalErrorContent").html(data.message);
				$("#myModalError").modal();
			}
		},

		error: function(jqXHR, textStatus, errorThrown){	          
			// $('#empty').html("<h3 class='label label-danger'>"+textStatus+"</h3>");
			$("#myModalErrorContent").html(textStatus);
			$("#myModalError").modal();
			
		}
	});	  	  	  	  
}
  
function modificaCampione(idCamampione){
	  
	var form = $('#aggiorna form')[0]; 
	var formData = new FormData(form);
	   
	var desc = $("#aggiorna #descrizione").val();
	$.ajax({
		type: "POST",
		url: "gestioneCampione.do?action=modifica&id="+idCamampione,
		data: formData,
		//dataType: "json",
		contentType: false, // NEEDED, DON'T OMIT THIS (requires jQuery 1.6+)
		processData: false, // NEEDED, DON'T OMIT THIS
		//enctype: 'multipart/form-data',
		success: function( data, textStatus) {

			if(data.success){ 
				$('#myModal').modal('hide');
				location.reload();
			}else{
				$('#errorModifica').html("<h3 class='label label-danger' style=\"color:green\">Errore Salvataggio Campione</h3>");			
			}
		},
		
		error: function(jqXHR, textStatus, errorThrown){
			$('#errorModifica').html("<h3 class='label label-danger'>"+jqXHR.responseJSON.messaggio+"</h3>");
			//callAction('logout.do');
			
		}
	});  
}

function toggleFuoriServizio(idStrumento,idSede,idCliente){
	pleaseWaitDiv = $('#pleaseWaitDialog');
	pleaseWaitDiv.modal();  
	$.ajax({
		type: "POST",
		url: "gestioneStrumento.do?action=toggleFuoriServizio&idStrumento="+idStrumento+"&idSede="+idSede+"&idCliente="+idCliente,
		dataType: "json",
		success: function( data, textStatus) {

			if(data.success){ 
				//callAction("listaStrumentiNew.do");
    			  
				stato = $('#stato_'+idStrumento).html();
    			  
				if(stato == "In servizio"){
					$('#stato_'+idStrumento).html("Fuori servizio");

				}else{
					$('#stato_'+idStrumento).html("In servizio");
				}
				exploreModal("dettaglioStrumento.do","id_str="+datax[0],"#dettaglio");
				pleaseWaitDiv.modal('hide');  
				$("#myModalErrorContent").html("Stato Strumento salvato con successo");
				$("#myModalError").modal();
			}else{
				pleaseWaitDiv.modal('hide');  
				$("#myModalErrorContent").html("Errore Salvataggio Strumento");
				$("#myModalError").modal();
			}
		},
		error: function(jqXHR, textStatus, errorThrown){    
			$("#myModalErrorContent").html(textStatus);
			$("#myModalError").modal();
			//callAction('logout.do');
    
		}
	});
}


//Gestione Utente
function nuovoUtente(){
	  
	if($("#formNuovoUtente").valid()){
		pleaseWaitDiv = $('#pleaseWaitDialog');
		pleaseWaitDiv.modal();

		var codice = $('#codice').val();
		var qualifica = $('#qualifica').val();
		var user=$('#user').val();
		var passw=$('#passw').val();
		var nome=$('#nome').val();
		var cognome=$('#cognome').val();
		var indirizzo=$('#indirizzo').val();
		var comune=$('#comune').val();
		var cap=$('#cap').val();
		var email=$('#email').val();
		var telefono=$('#telefono').val();
		var company=$('#company').val();
		var cf = $('#cf').val();
		var dataObj = {};
		
		dataObj.codice = codice;
		dataObj.qualifica = qualifica;
		dataObj.user = user;
		dataObj.passw = passw;
		dataObj.nome = nome;
		dataObj.cognome = cognome;
		dataObj.indirizzo = indirizzo;
		dataObj.comune = comune;
		dataObj.cap = cap;
		dataObj.email = email;
		dataObj.telefono = telefono;
		dataObj.company = company;
		dataObj.cf = cf;

		var sList = "";

		$('#formNuovoUtente input[type=checkbox]').each(function () {
			if(this.checked){
				if(sList.length>0){
					sList += ",";
				}
				sList += $(this).val();
			}		 		   
		});
		dataObj.ruoli = sList;
	  
		$.ajax({
			type: "POST",
			url: "gestioneUtenti.do?action=nuovo",
			data: dataObj,
			dataType: "json",
			success: function( data, textStatus) {
        		  
				pleaseWaitDiv.modal('hide');
				
				if(data.success){         		
					$("#modalNuovoUtente").modal("hide");
					$('#myModalErrorContent').html(data.messaggio);
					$('#myModalError').removeClass();
					$('#myModalError').addClass("modal modal-success");
					$('#myModalError').modal('show');
        				        		
				}else{
					$('#myModalErrorContent').html(data.messaggio);
					$('#myModalError').removeClass();
					$('#myModalError').addClass("modal modal-danger");
					$('#myModalError').modal('show');        			 
				}
			},
			error: function(jqXHR, textStatus, errorThrown){
				pleaseWaitDiv.modal('hide');

				$('#myModalErrorContent').html(textStatus);
  			  	$('#myModalError').removeClass();
  				$('#myModalError').addClass("modal modal-danger");
  				$('#myModalError').modal('show');
        
			}
		});
	}
}
  
function modificaUtente(){
	 
	pleaseWaitDiv = $('#pleaseWaitDialog');
	pleaseWaitDiv.modal();
	
	var id=$('#modid').val();
	var codice = $('#modcodice').val();
	var qualifica = $('#modqualifica').val();
	var user=$('#moduser').val();
	var passw=$('#modpassw').val();
	var nome=$('#modnome').val();
	var cognome=$('#modcognome').val();
	var indirizzo=$('#modindirizzo').val();
	var comune=$('#modcomune').val();
	var cap=$('#modcap').val();
	var email=$('#modemail').val();
	var telefono=$('#modtelefono').val();
	var company=$('#modcompany').val();
	var cf = $('#modcf').val();
	var dataObj = {};
	dataObj.id = id;
	dataObj.codice = codice;
	dataObj.qualifica = qualifica;
	dataObj.user = user;
	dataObj.passw = passw;
	dataObj.nome = nome;
	dataObj.cognome = cognome;
	dataObj.indirizzo = indirizzo;
	dataObj.comune = comune;
	dataObj.cap = cap;
	dataObj.email = email;
	dataObj.telefono = telefono;
	dataObj.company = company;
	dataObj.cf = cf;

	$.ajax({
		type: "POST",
		url: "gestioneUtenti.do?action=modifica",
		data: dataObj,
		dataType: "json",
		success: function( data, textStatus) {
      		  
			pleaseWaitDiv.modal('hide');
      		  
			if(data.success){ 
      			
				$("#modalModificaUtente").modal("hide");
				$('#myModalErrorContent').html(data.messaggio);
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-success");
				$('#myModalError').modal('show');
      				      		
			}else{
				$('#myModalErrorContent').html(data.messaggio);
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");
				$('#myModalError').modal('show');      			 
			}
		},
		error: function(jqXHR, textStatus, errorThrown){
			pleaseWaitDiv.modal('hide');

			$('#myModalErrorContent').html(textStatus);
			$('#myModalError').removeClass();
			$('#myModalError').addClass("modal modal-danger");
			$('#myModalError').modal('show');
			
		}
	});	 
}


function eliminaUtente(){
	 
	$("#modalEliminaUtente").modal("hide");

	pleaseWaitDiv = $('#pleaseWaitDialog');
	pleaseWaitDiv.modal();
	  
	var id=$('#idElimina').val();
	var dataObj = {};
	dataObj.id = id;

	$.ajax({
		type: "POST",
		url: "gestioneUtenti.do?action=elimina",
		data: dataObj,
		dataType: "json",
		success: function( data, textStatus) {
		  
			pleaseWaitDiv.modal('hide');
		  
			if(data.success){ 			
			 
				$('#myModalErrorContent').html(data.messaggio);
			  	$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-success");
				$('#myModalError').modal('show');
						
			}else{
				$('#myModalErrorContent').html(data.messaggio);
			  	$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");
				$('#myModalError').modal('show');			 
			}
		},
		error: function(jqXHR, textStatus, errorThrown){
			pleaseWaitDiv.modal('hide');

			$('#myModalErrorContent').html(textStatus);
		  	$('#myModalError').removeClass();
			$('#myModalError').addClass("modal modal-danger");
			$('#myModalError').modal('show');
		}
	});
}

function modalModificaUtente(id,codice,user,nome,cognome,indirizzo,comune,cap,email,telefono,company, qualifica, cf){
	
	$('#modid').val(id);
	$('#modcodice').val(codice)
	$('#moduser').val(user);
	$('#modnome').val(nome);
	$('#modcognome').val(cognome);
	$('#modindirizzo').val(indirizzo);
	$('#modcomune').val(comune);
	$('#modcap').val(cap);
	$('#modemail').val(email);
	$('#modtelefono').val(telefono);
	$('#modcompany').val(company);
	$('#modqualifica').val(qualifica);
	$('#modcf').val(cf);
	  	  
	$('#modalModificaUtente').modal();
	  
}

function modalEliminaUtente(id,nominativo){
	  
	$('#idElimina').val(id);
	$('#nominativoElimina').html(nominativo);
	  
	  
	$('#modalEliminaUtente').modal();
	  
}  
  
// Gestione Company  
function nuovaCompany(){
	  
	if($("#formNuovaCompany").valid()){
		pleaseWaitDiv = $('#pleaseWaitDialog');
		pleaseWaitDiv.modal();

	  
		var denominazione=$('#denominazione').val();
		var piva=$('#pIva').val();
		var indirizzo=$('#indirizzo').val();
		var comune=$('#comune').val();
		var cap=$('#cap').val();
		var email=$('#mail').val();
		var telefono=$('#telefono').val();
		var codiceAffiliato=$('#codAffiliato').val();
		var dataObj = {};
		
		dataObj.denominazione = denominazione;
		dataObj.piva = piva;
		dataObj.indirizzo = indirizzo;
		dataObj.comune = comune;
		dataObj.cap = cap;
		dataObj.email = email;
		dataObj.telefono = telefono;
		dataObj.codiceAffiliato = codiceAffiliato;

		$.ajax({
			type: "POST",
			url: "gestioneCompany.do?action=nuovo",
			data: dataObj,
			dataType: "json",
			success: function( data, textStatus) {
        		  
				pleaseWaitDiv.modal('hide');
        		  
				if(data.success){         		
					$("#modalNuovaCompany").modal("hide");
					$('#myModalErrorContent').html(data.messaggio);
					$('#myModalError').removeClass();
					$('#myModalError').addClass("modal modal-success");
					$('#myModalError').modal('show');        				        		
				}else{
					$('#myModalErrorContent').html(data.messaggio);
					$('#myModalError').removeClass();
					$('#myModalError').addClass("modal modal-danger");
					$('#myModalError').modal('show');        			 
				}
			},
			error: function(jqXHR, textStatus, errorThrown){
				pleaseWaitDiv.modal('hide');

				$('#myModalErrorContent').html(textStatus);
  			  	$('#myModalError').removeClass();
  				$('#myModalError').addClass("modal modal-danger");
  				$('#myModalError').modal('show');
        
			}
		});
	}
}
  
function modificaCompany(){
	  
	pleaseWaitDiv = $('#pleaseWaitDialog');
	pleaseWaitDiv.modal();
	
	var id=$('#modid').val();
	var denominazione=$('#moddenominazione').val();
	var piva=$('#modpIva').val();
	var indirizzo=$('#modindirizzo').val();
	var comune=$('#modcomune').val();
	var cap=$('#modcap').val();
	var email=$('#modmail').val();
	var telefono=$('#modtelefono').val();
	var codiceAffiliato=$('#modcodAffiliato').val();
	    
	var dataObj = {};
	dataObj.id = id;
	dataObj.denominazione = denominazione;
	dataObj.piva = piva;
	dataObj.indirizzo = indirizzo;
	dataObj.comune = comune;
	dataObj.cap = cap;
	dataObj.email = email;
	dataObj.telefono = telefono;
	dataObj.codiceAffiliato = codiceAffiliato;

	$.ajax({
		type: "POST",
		url: "gestioneCompany.do?action=modifica",
		data: dataObj,
		dataType: "json",
		success: function( data, textStatus) {
      		  
			pleaseWaitDiv.modal('hide');
      		  
			if(data.success){       			
				$("#modalModificaCompany").modal("hide");
				$('#myModalErrorContent').html(data.messaggio);
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-success");
				$('#myModalError').modal('show');      				      		
			}else{
				$('#myModalErrorContent').html(data.messaggio);
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");
				$('#myModalError').modal('show');      			
			}
		},
		error: function(jqXHR, textStatus, errorThrown){
			pleaseWaitDiv.modal('hide');

			$('#myModalErrorContent').html(textStatus);
			$('#myModalError').removeClass();
			$('#myModalError').addClass("modal modal-danger");
			$('#myModalError').modal('show');			
		}
	});	  
}

function eliminaCompany(){
	 
	$("#modalEliminaCompany").modal("hide");

	pleaseWaitDiv = $('#pleaseWaitDialog');
	pleaseWaitDiv.modal();

	var id=$('#idElimina').val();
	var dataObj = {};
	dataObj.id = id;

	$.ajax({
		type: "POST",
		url: "gestioneCompany.do?action=elimina",
		data: dataObj,
		dataType: "json",
		success: function( data, textStatus) {
		  
			pleaseWaitDiv.modal('hide');
		  
			if(data.success){ 						 
				$('#myModalErrorContent').html(data.messaggio);
			  	$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-success");
				$('#myModalError').modal('show');				
			}else{
				$('#myModalErrorContent').html(data.messaggio);
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");
				$('#myModalError').modal('show');				 
			}
		},
		error: function(jqXHR, textStatus, errorThrown){
			pleaseWaitDiv.modal('hide');

			$('#myModalErrorContent').html(textStatus);
		  	$('#myModalError').removeClass();
			$('#myModalError').addClass("modal modal-danger");
			$('#myModalError').modal('show');
		}
	});
}

function modalModificaCompany(id,denominazione,piva,indirizzo,comune,cap,email,telefono,codAffiliato){
	  
	$('#modid').val(id);
	$('#moddenominazione').val(denominazione);
	$('#modpIva').val(piva);
	$('#modindirizzo').val(indirizzo);
	$('#modcomune').val(comune);
	$('#modcap').val(cap);
	$('#modmail').val(email);
	$('#modtelefono').val(telefono);
	$('#modcodAffiliato').val(codAffiliato);
	    
	$('#modalModificaCompany').modal();
	  
}

function modalEliminaCompany(id,denominazione){
	  
	$('#idElimina').val(id);
	$('#denominazioneElimina').html(denominazione);
	    
	$('#modalEliminaCompany').modal();
	  
}  
  
//Gestione Ruoli
function nuovoRuolo(){
  	  
	if($("#formNuovoRuolo").valid()){
		pleaseWaitDiv = $('#pleaseWaitDialog');
		pleaseWaitDiv.modal();
  	  
  	  	var sigla=$('#sigla').val();
  	  	var descrizione=$('#descrizione').val();

  	  	var dataObj = {};
  		
  	  	dataObj.sigla = sigla;
  	  	dataObj.descrizione = descrizione;

  	  	var sList = "";

  	  	$('#formNuovoRuolo input[type=checkbox]').each(function () {
  	  		if(this.checked){
  	  			if(sList.length>0){
  	  				sList += ",";
  	  			}
  	  			sList += $(this).val();
  	  		}  		  		    
  	  	});
  	  	dataObj.permessi = sList;
  	  
  	  	$.ajax({
  	  		type: "POST",
  	  		url: "gestioneRuoli.do?action=nuovo",
  	  		data: dataObj,
  	  		dataType: "json",
  	  		success: function( data, textStatus) {
          		  
  	  			pleaseWaitDiv.modal('hide');
          		  
  	  			if(data.success){           		
  	  				$("#modalNuovoRuolo").modal("hide");
  	  				$('#myModalErrorContent').html(data.messaggio);
  	  				$('#myModalError').removeClass();
  	  				$('#myModalError').addClass("modal modal-success");
  	  				$('#myModalError').modal('show');
          				          		
  	  			}else{
  	  				$('#myModalErrorContent').html(data.messaggio);
  	  				$('#myModalError').removeClass();
  	  				$('#myModalError').addClass("modal modal-danger");
  	  				$('#myModalError').modal('show');
          				
  	  			}
  	  		},
  	  		error: function(jqXHR, textStatus, errorThrown){
  	  			pleaseWaitDiv.modal('hide');

  	  			$('#myModalErrorContent').html(textStatus);
  	  			$('#myModalError').removeClass();
  	  			$('#myModalError').addClass("modal modal-danger");
  	  			$('#myModalError').modal('show');
  	  			
  	  		}
  	  	});
	}
}
    
function modificaRuolo(){
  	 
	pleaseWaitDiv = $('#pleaseWaitDialog');
	pleaseWaitDiv.modal();

	var id=$('#modid').val();
	var sigla=$('#modsigla').val();
	var descrizione=$('#moddescrizione').val();
	
	var dataObj = {};
	dataObj.id = id;
	dataObj.sigla = sigla;
	dataObj.descrizione = descrizione;
  	 
	$.ajax({
		type: "POST",
		url: "gestioneRuoli.do?action=modifica",
		data: dataObj,
		dataType: "json",
		success: function( data, textStatus) {
			
			pleaseWaitDiv.modal('hide');
        		  
			if(data.success){ 
        			
				$("#modalModificaRuolo").modal("hide");
				$('#myModalErrorContent').html(data.messaggio);
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-success");
				$('#myModalError').modal('show');			        		
			}else{
				$('#myModalErrorContent').html(data.messaggio);
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");
				$('#myModalError').modal('show');
				
			}
		},
		error: function(jqXHR, textStatus, errorThrown){
			pleaseWaitDiv.modal('hide');

			$('#myModalErrorContent').html(textStatus);
			$('#myModalError').removeClass();
			$('#myModalError').addClass("modal modal-danger");
			$('#myModalError').modal('show');
        
		}
	});  	  
}


function eliminaRuolo(){
  	 
	$("#modalEliminaRuolo").modal("hide");
	
	pleaseWaitDiv = $('#pleaseWaitDialog');
	pleaseWaitDiv.modal();

	var id=$('#idElimina').val();
	var dataObj = {};
	dataObj.id = id;

	$.ajax({
		type: "POST",
  	  	url: "gestioneRuoli.do?action=elimina",
  	  	data: dataObj,
  	  	dataType: "json",
  	  	success: function( data, textStatus) {
  		  
  	  		pleaseWaitDiv.modal('hide');
  		  
  	  		if(data.success){   			  			 
  	  			$('#myModalErrorContent').html(data.messaggio);
  			  	$('#myModalError').removeClass();
  				$('#myModalError').addClass("modal modal-success");
  				$('#myModalError').modal('show');  				  				
  	  		}else{
  	  			$('#myModalErrorContent').html(data.messaggio);
  	  			$('#myModalError').removeClass();
  				$('#myModalError').addClass("modal modal-danger");
  				$('#myModalError').modal('show');  				
  	  		}
  	  	},
  	  	error: function(jqXHR, textStatus, errorThrown){
  	  		pleaseWaitDiv.modal('hide');

  	  		$('#myModalErrorContent').html(textStatus);
  		  	$('#myModalError').removeClass();
  			$('#myModalError').addClass("modal modal-danger");
  			$('#myModalError').modal('show');
  	  	}
    });
}

function modalModificaRuolo(id,sigla,descrizione){
  	  
	$('#modid').val(id);
	$('#modsigla').val(sigla);
	$('#moddescrizione').val(descrizione);  
  	  
	$('#modalModificaRuolo').modal();
  	  
}

function modalEliminaRuolo(id,descrizione){
  	  
	$('#idElimina').val(id);
	$('#descrizioneElimina').html(descrizione);
  	    	  
	$('#modalEliminaRuolo').modal();  	  
}
  
// Gestione Permessi    
function nuovoPermesso(){
  	  
	if($("#formNuovoPermesso").valid()){
		pleaseWaitDiv = $('#pleaseWaitDialog');
		pleaseWaitDiv.modal();
  	  
		var descrizione=$('#descrizione').val();
  	  	var chiave_permesso=$('#chiave_permesso').val();
  	  	var statoPermesso=$('#statoPermesso').is(':checked');
  	  	var dataObj = {};
  		
  	  	dataObj.chiave_permesso = chiave_permesso;
  	  	dataObj.descrizione = descrizione;
  	  	dataObj.statoPermesso= statoPermesso;
  	  	
  	  	$.ajax({
  	  		type: "POST",
  	  		url: "gestionePermessi.do?action=nuovo",
          	  data: dataObj,
          	  dataType: "json",
          	  success: function( data, textStatus) {
          		  
          		  pleaseWaitDiv.modal('hide');
          		  
          		  if(data.success){           			 
          			  $("#modalNuovoPermesso").modal("hide");
          			  $('#myModalErrorContent').html(data.messaggio);
          			  $('#myModalError').removeClass();
          			  $('#myModalError').addClass("modal modal-success");
          			  $('#myModalError').modal('show');          				          		
          		  }else{
          			  $('#myModalErrorContent').html(data.messaggio);
          			  $('#myModalError').removeClass();
          			  $('#myModalError').addClass("modal modal-danger");
          			  $('#myModalError').modal('show');          			 
          		  }
          	  },
          	  error: function(jqXHR, textStatus, errorThrown){
          		  pleaseWaitDiv.modal('hide');

          		  $('#myModalErrorContent').html(textStatus);
          		  $('#myModalError').removeClass();
          		  $('#myModalError').addClass("modal modal-danger");
          		  $('#myModalError').modal('show');          
          	  }
  	  	});
	}
}
    
function modificaPermesso(){  	  
	pleaseWaitDiv = $('#pleaseWaitDialog');
	pleaseWaitDiv.modal();

	var id=$('#modid').val();
	var descrizione=$('#moddescrizione').val();
	var chiave_permesso=$('#modchiavepermesso').val();  	  
	var statoPermesso=$('#modstatoPermesso').is(':checked');
	
	var dataObj = {};
	dataObj.id = id;
	dataObj.descrizione = descrizione;
	dataObj.chiave_permesso = chiave_permesso;
	dataObj.statoPermesso = statoPermesso;

	$.ajax({
		type: "POST",
		url: "gestionePermessi.do?action=modifica",
		data: dataObj,
		dataType: "json",
		success: function( data, textStatus) {
        		  
			pleaseWaitDiv.modal('hide');
        		  
			if(data.success){ 
        			
				$("#modalModificaPermesso").modal("hide");
				$('#myModalErrorContent').html(data.messaggio);
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-success");
				$('#myModalError').modal('show');        				
			}else{
				$('#myModalErrorContent').html(data.messaggio);
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");
				$('#myModalError').modal('show');
        			 
			}
		},
		error: function(jqXHR, textStatus, errorThrown){
			pleaseWaitDiv.modal('hide');

			$('#myModalErrorContent').html(textStatus);
			$('#myModalError').removeClass();
			$('#myModalError').addClass("modal modal-danger");
			$('#myModalError').modal('show');
        
		}
	});  	 
}

function eliminaPermesso(){
  	 
	$("#modalEliminaPermessoy").modal("hide");

	pleaseWaitDiv = $('#pleaseWaitDialog');
	pleaseWaitDiv.modal();
	
	var id=$('#idElimina').val();
	var dataObj = {};
	dataObj.id = id;

	$.ajax({
		type: "POST",
  	  	url: "gestionePermessi.do?action=elimina",
  	  	data: dataObj,
  	  	dataType: "json",
  	  	success: function( data, textStatus) {
  		  
  	  		pleaseWaitDiv.modal('hide');
  		  
  	  		if(data.success){   			  			 
  	  			$('#myModalErrorContent').html(data.messaggio);
  	  			$('#myModalError').removeClass();
  				$('#myModalError').addClass("modal modal-success");
  				$('#myModalError').modal('show');  				  		
  	  		}else{
  	  			$('#myModalErrorContent').html(data.messaggio);
  			  	$('#myModalError').removeClass();
  				$('#myModalError').addClass("modal modal-danger");
  				$('#myModalError').modal('show');  			 
  	  		}
  	  	},
  	  	error: function(jqXHR, textStatus, errorThrown){
  	  		pleaseWaitDiv.modal('hide');

  	  		$('#myModalErrorContent').html(textStatus);
  		  	$('#myModalError').removeClass();
  			$('#myModalError').addClass("modal modal-danger");
  			$('#myModalError').modal('show');
  	  	}
	});
}

function modalModificaPermesso(id,descrizione,chiave_permesso,statoPermesso){	
	$('#modid').val(id);
	$('#moddescrizione').val(descrizione);
	$('#modchiavepermesso').val(chiave_permesso);
	if(statoPermesso){
		$('#modstatoPermesso').iCheck('check');
	}else{
		$('#modstatoPermesso').iCheck('uncheck');
	}
	
  	
	$('#modalModificaPermesso').modal();
}
    
function modalEliminaPermessoy(id,descrizione){  	  
	$('#idElimina').val(id);
	$('#descrizioneElimina').html(descrizione);
  	  	
	$('#modalEliminaPermesso').modal();  	  
}            
  

function createLDTable(data){	 	  
	var dataSet = [];
	  
	if(data.result.duplicate){
		var jsonData = JSON.parse(data.result.duplicate);
		
		for(var i=0 ; i<jsonData.length;i++){
	
			item = ["<input type='checkbox' value='"+jsonData[i].__id+"'>",jsonData[i].__id,jsonData[i].denominazione];		 	
			dataSet.push(item);
		}
		$("#modalListaDuplicati").modal("show");
		
		$('#tabLD').DataTable( {
			data: dataSet,
			bDestroy: true,
			columns: [
				{ title: "Check" },
				{ title: "ID" },
				{ title: "Descrizione" }
			]
		} );
	}else{
		if(data.result.messaggio != ""){
			$('#modalErrorDiv').html(data.result.messaggio);
			$('#myModal').removeClass();
			$('#myModal').addClass("modal modal-success");
			$('#myModal').modal('show');
				
		}
//		  else{
//	  			$('#modalErrorDiv').html("Salvato");
//		  		$('#myModal').removeClass();
//		  		$('#myModal').addClass("modal modal-success");
//				$('#myModal').modal('show');
//	  		}
	}
}

function creaCertificato(idCertificato){
	pleaseWaitDiv = $('#pleaseWaitDialog');
	pleaseWaitDiv.modal();
	$.ajax({
		type: "POST",
		url: "listaCertificati.do?action=creaCertificato&idCertificato="+idCertificato,
		dataType: "json",

		success: function( data, textStatus) {
			pleaseWaitDiv.modal('hide');
			if(data.success){ 
				// $('#errorMsg').html("<h3 class='label label-success' style=\"color:green\">"+data.message+"</h3>");
				$('#modalErrorDiv').html(data.message);
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-success");
				$('#myModalError').modal('show');
							
			}else{
				$('#modalErrorDiv').html(data.message);
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");
				$('#myModalError').modal('show');
			}
		},
		error: function(jqXHR, textStatus, errorThrown){    	
			pleaseWaitDiv.modal('hide');
   			$('#modalErrorDiv').html(errorThrown.message);
		  	$('#myModalError').removeClass();
			$('#myModalError').addClass("modal modal-danger");
			$('#myModalError').modal('show');    
		}
	});
}

function annullaCertificato(idCertificato){
	pleaseWaitDiv = $('#pleaseWaitDialog');
	pleaseWaitDiv.modal();
	$.ajax({
		type: "POST",
		url: "listaCertificati.do?action=annullaCertificato&idCertificato="+idCertificato,
		dataType: "json",

		success: function( data, textStatus) {
			pleaseWaitDiv.modal('hide');
			if(data.success){ 
				// $('#errorMsg').html("<h3 class='label label-success' style=\"color:green\">"+data.message+"</h3>");
				$('#modalErrorDiv').html(data.message);
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-success");
				$('#myModalError').modal('show');								
			}else{
				$('#modalErrorDiv').html(data.message);
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");
				$('#myModalError').modal('show');
			}
		},
		error: function(jqXHR, textStatus, errorThrown){    	
			pleaseWaitDiv.modal('hide');
   			$('#modalErrorDiv').html(errorThrown.message);
		  	$('#myModalError').removeClass();
			$('#myModalError').addClass("modal modal-danger");
			$('#myModalError').modal('show');    
		}
	});
}
  
function inviaEmailCertificato(idCertificato){
	pleaseWaitDiv = $('#pleaseWaitDialog');
	pleaseWaitDiv.modal();
	$.ajax({
		type: "POST",
		url: "listaCertificati.do?action=inviaEmailCertificato&idCertificato="+idCertificato,
		dataType: "json",

		success: function( data, textStatus) {
			pleaseWaitDiv.modal('hide');
			if(data.success){ 
				// $('#errorMsg').html("<h3 class='label label-success' style=\"color:green\">"+data.message+"</h3>");
				$('#modalErrorDiv').html(data.message);
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-success");
				$('#myModalError').modal('show');       	             		
			}else{
				$('#modalErrorDiv').html(data.message);
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");
				$('#myModalError').modal('show');
			}
		},
		error: function(jqXHR, textStatus, errorThrown){
    	
			pleaseWaitDiv.modal('hide');
   			$('#modalErrorDiv').html(errorThrown.message);
		  	$('#myModalError').removeClass();
			$('#myModalError').addClass("modal modal-danger");
			$('#myModalError').modal('show');    
		}
	});
}
  
function firmaCertificato(idCertificato){
	pleaseWaitDiv = $('#pleaseWaitDialog');
	pleaseWaitDiv.modal();
	$.ajax({
		type: "POST",
		url: "listaCertificati.do?action=firmaCertificato&idCertificato="+idCertificato,
		dataType: "json",
		success: function( data, textStatus) {
			pleaseWaitDiv.modal('hide');
			if(data.success){ 
				// $('#errorMsg').html("<h3 class='label label-success' style=\"color:green\">"+data.message+"</h3>");
				$('#modalErrorDiv').html(data.message);
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-success");
				$('#myModalError').modal('show');       	             		
			}else{
				$('#modalErrorDiv').html(data.message);
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");
				$('#myModalError').modal('show');
			}
		},
		error: function(jqXHR, textStatus, errorThrown){
			pleaseWaitDiv.modal('hide');
   
   			$('#modalErrorDiv').html(errorThrown.message);
		  	$('#myModalError').removeClass();
			$('#myModalError').addClass("modal modal-danger");
			$('#myModalError').modal('show'); 
		}
	});
}

function approvaCertificatiMulti(selezionati){
		
	$.ajax({
		type: "POST",
		url: "listaCertificati.do?action=approvaCertificatiMulti",
		dataType: "json",
		data: "dataIn="+JSON.stringify(selezionati),
		success: function( data, textStatus) {
			pleaseWaitDiv.modal('hide');
			if(data.success){ 
				// $('#errorMsg').html("<h3 class='label label-success' style=\"color:green\">"+data.message+"</h3>");
				$('#modalErrorDiv').html(data.message);
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-success");
				$('#myModalError').modal('show');       	       
			}else{
				$('#modalErrorDiv').html(data.message);
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");
				$('#myModalError').modal('show');
			}
		},
		error: function(jqXHR, textStatus, errorThrown){
			pleaseWaitDiv.modal('hide');
			
   			$('#modalErrorDiv').html(errorThrown.message);
		  	$('#myModalError').removeClass();
			$('#myModalError').addClass("modal modal-danger");
			$('#myModalError').modal('show');
		}
	});
}

function annullaCertificatiMulti(selezionati){
	
	$.ajax({
		type: "POST",
		url: "listaCertificati.do?action=annullaCertificatiMulti",
		dataType: "json",
		data: "dataIn="+JSON.stringify(selezionati),
		success: function( data, textStatus) {
			pleaseWaitDiv.modal('hide');
			if(data.success){ 
				// $('#errorMsg').html("<h3 class='label label-success' style=\"color:green\">"+data.message+"</h3>");
				$('#modalErrorDiv').html(data.message);
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-success");
				$('#myModalError').modal('show');				    		
			}else{
				$('#modalErrorDiv').html(data.message);
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");
				$('#myModalError').modal('show');
			}
		},

		error: function(jqXHR, textStatus, errorThrown){
			pleaseWaitDiv.modal('hide');
    		  	
   			$('#modalErrorDiv').html(errorThrown.message);
		  	$('#myModalError').removeClass();
			$('#myModalError').addClass("modal modal-danger");
			$('#myModalError').modal('show');    
		}
	});
}
    
function saveDuplicatiFromModal(){
	  
	var ids = []; 
	$( "#tabLD input[type=checkbox]" ).each(function( i ) {
		if (this.checked) {
			console.log($(this).val()); 
			ids.push(""+this.value);
		}
	});
	  
	$("#modalListaDuplicati").modal("hide");
	pleaseWaitDiv = $('#pleaseWaitDialog');
	pleaseWaitDiv.modal();
	var  dataObj = {};
	dataObj.ids =""+ ids+"";
	  
	$.ajax({
		type: "POST",
		url: "caricaPacchettoDuplicati.do?",
		data: dataObj,
		dataType: "json",
		success: function( data, textStatus) {
	    		  
			$('#files').html("");
			
			pleaseWaitDiv.modal('hide');
			if(data.success){ 
				if(data.messaggio != ""){
					$('#modalErrorDiv').html(data.messaggio);
					$('#myModal').removeClass();
					$('#myModal').addClass("modal modal-success");
					$('#myModal').modal('show');
					
				}
//	    		else{
//	    			$('#modalErrorDiv').html("Salvato");
//	    			$('#myModal').removeClass();
//	    	  		$('#myModal').addClass("modal modal-success");
//	   				$('#myModal').modal('show');
//	    		 }
				$( "#tabLD" ).html("");							    		
			}else{
				$('#modalErrorDiv').html(data.messaggio);
				$('#myModal').removeClass();
				$('#myModal').addClass("modal modal-danger");
				$('#myModal').modal('show');
				$( "#tabLD" ).html("");
			}
			$('#progress .progress-bar').css(
					'width',
					'0%'
			);
		},	
		error: function(jqXHR, textStatus, errorThrown){

			pleaseWaitDiv.modal('hide');
	    		  
			$('#modalErrorDiv').html(textStatus);
			$('#myModal').removeClass();
			$('#myModal').addClass("modal modal-danger");
			$('#myModal').modal('show');
			$( "#tabLD" ).html("");
			$('#progress .progress-bar').css(
					'width',
					'0%'
				);
		}
	});  
}

function associaRuolo(idRuolo, idUtente){
	$.ajax({
		type: "POST",
		url: "gestioneAssociazioniAjax.do?action=associaRuolo&idRuolo="+idRuolo+"&idUtente="+idUtente,
		dataType: "json",
		success: function( data, textStatus) {
			pleaseWaitDiv.modal('hide');
			if(data.success){ 
				$('#tabRuoliTr_'+idRuolo).addClass("bg-blue color-palette");
				$('#btnAssociaRuolo_'+idRuolo).attr("disabled",true);
				$('#btnDisAssociaRuolo_'+idRuolo).attr("disabled",false);    			     		
			}else{
				$('#modalErrorDiv').html(data.message);
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");
				$('#myModalError').modal('show');
			}	
		},
		error: function(jqXHR, textStatus, errorThrown){
			pleaseWaitDiv.modal('hide');
			
			$('#modalErrorDiv').html(errorThrown.message);
		  	$('#myModalError').removeClass();
			$('#myModalError').addClass("modal modal-danger");
			$('#myModalError').modal('show');    
		}
	});
}

function disassociaRuolo(idRuolo, idUtente){
	$.ajax({
		type: "POST",
		url: "gestioneAssociazioniAjax.do?action=disassociaRuolo&idRuolo="+idRuolo+"&idUtente="+idUtente,
		dataType: "json",
		success: function( data, textStatus) {
			pleaseWaitDiv.modal('hide');
			if(data.success){ 
				$('#tabRuoliTr_'+idRuolo).removeClass("bg-blue color-palette");
				$('#btnAssociaRuolo_'+idRuolo).attr("disabled",false);
				$('#btnDisAssociaRuolo_'+idRuolo).attr("disabled",true);
				
			}else{
				$('#modalErrorDiv').html(data.message);
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");
				$('#myModalError').modal('show');
			}
		},
		error: function(jqXHR, textStatus, errorThrown){
			pleaseWaitDiv.modal('hide');
   
   			$('#modalErrorDiv').html(errorThrown.message);
		  	$('#myModalError').removeClass();
			$('#myModalError').addClass("modal modal-danger");
			$('#myModalError').modal('show');    
		}
	});
}
  


function associaCategoria(id_categoria, idUtente){
	$.ajax({
		type: "POST",
		url: "gestioneAssociazioniAjax.do?action=associaCategoria&id_categoria="+id_categoria+"&idUtente="+idUtente,
		dataType: "json",
		success: function( data, textStatus) {
			pleaseWaitDiv.modal('hide');
			if(data.success){ 
				$('#tabCategorieTr_'+id_categoria).addClass("bg-blue color-palette");
				$('#btnAssociaCategoria_'+id_categoria).attr("disabled",true);
				$('#btnDisAssociaCategoria_'+id_categoria).attr("disabled",false);    			     		
			}else{
				$('#modalErrorDiv').html(data.message);
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");
				$('#myModalError').modal('show');
			}	
		},
		error: function(jqXHR, textStatus, errorThrown){
			pleaseWaitDiv.modal('hide');
			
			$('#modalErrorDiv').html(errorThrown.message);
		  	$('#myModalError').removeClass();
			$('#myModalError').addClass("modal modal-danger");
			$('#myModalError').modal('show');    
		}
	});
}

function disassociaCategoria(id_categoria, idUtente){
	$.ajax({
		type: "POST",
		url: "gestioneAssociazioniAjax.do?action=disassociaCategoria&id_categoria="+id_categoria+"&idUtente="+idUtente,
		dataType: "json",
		success: function( data, textStatus) {
			pleaseWaitDiv.modal('hide');
			if(data.success){ 
				$('#tabCategorieTr_'+id_categoria).removeClass("bg-blue color-palette");
				$('#btnAssociaCategoria_'+id_categoria).attr("disabled",false);
				$('#btnDisAssociaCategoria_'+id_categoria).attr("disabled",true);
				
			}else{
				$('#modalErrorDiv').html(data.message);
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");
				$('#myModalError').modal('show');
			}
		},
		error: function(jqXHR, textStatus, errorThrown){
			pleaseWaitDiv.modal('hide');
   
   			$('#modalErrorDiv').html(errorThrown.message);
		  	$('#myModalError').removeClass();
			$('#myModalError').addClass("modal modal-danger");
			$('#myModalError').modal('show');    
		}
	});
}



function associaComunicazione(id_comunicazione, idUtente){
	$.ajax({
		type: "POST",
		url: "gestioneAssociazioniAjax.do?action=associaComunicazione&id_comunicazione="+id_comunicazione+"&idUtente="+idUtente,
		dataType: "json",
		success: function( data, textStatus) {
			pleaseWaitDiv.modal('hide');
			if(data.success){ 
				$('#tabComunicazioniTr_'+id_comunicazione).addClass("bg-blue color-palette");
				$('#btnAssociaComunicazione_'+id_comunicazione).attr("disabled",true);
				$('#btnDisAssociaComunicazione_'+id_comunicazione).attr("disabled",false);    			     		
			}else{
				$('#modalErrorDiv').html(data.message);
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");
				$('#myModalError').modal('show');
			}	
		},
		error: function(jqXHR, textStatus, errorThrown){
			pleaseWaitDiv.modal('hide');
			
			$('#modalErrorDiv').html(errorThrown.message);
		  	$('#myModalError').removeClass();
			$('#myModalError').addClass("modal modal-danger");
			$('#myModalError').modal('show');    
		}
	});
}

function disassociaComunicazione(id_comunicazione, idUtente){
	$.ajax({
		type: "POST",
		url: "gestioneAssociazioniAjax.do?action=disassociaComunicazione&id_comunicazione="+id_comunicazione+"&idUtente="+idUtente,
		dataType: "json",
		success: function( data, textStatus) {
			pleaseWaitDiv.modal('hide');
			if(data.success){ 
				$('#tabComunicazioniTr_'+id_comunicazione).removeClass("bg-blue color-palette");
				$('#btnAssociaComunicazione_'+id_comunicazione).attr("disabled",false);
				$('#btnDisAssociaComunicazione_'+id_comunicazione).attr("disabled",true);
				
			}else{
				$('#modalErrorDiv').html(data.message);
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");
				$('#myModalError').modal('show');
			}
		},
		error: function(jqXHR, textStatus, errorThrown){
			pleaseWaitDiv.modal('hide');
   
   			$('#modalErrorDiv').html(errorThrown.message);
		  	$('#myModalError').removeClass();
			$('#myModalError').addClass("modal modal-danger");
			$('#myModalError').modal('show');    
		}
	});
}


function associaUtente(idUtente,idRuolo){
	$.ajax({
		type: "POST",
		url: "gestioneAssociazioniAjax.do?action=associaUtente&idRuolo="+idRuolo+"&idUtente="+idUtente,
		dataType: "json",
		success: function( data, textStatus) {
			pleaseWaitDiv.modal('hide');
			if(data.success){ 
				$('#tabUtentiTr_'+idUtente).addClass("bg-blue color-palette");
				$('#btnAssociaUtente_'+idUtente).attr("disabled",true);
				$('#btnDisAssociaUtente_'+idUtente).attr("disabled",false);    			 
			}else{
				$('#modalErrorDiv').html(data.message);
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");
				$('#myModalError').modal('show');
			}
		},
		error: function(jqXHR, textStatus, errorThrown){
			pleaseWaitDiv.modal('hide');
   
   			$('#modalErrorDiv').html(errorThrown.message);
		  	$('#myModalError').removeClass();
			$('#myModalError').addClass("modal modal-danger");
			$('#myModalError').modal('show');
		}
	});
}

function disassociaUtente(idUtente,idRuolo){
	$.ajax({
		type: "POST",
		url: "gestioneAssociazioniAjax.do?action=disassociaUtente&idRuolo="+idRuolo+"&idUtente="+idUtente,
		dataType: "json",
		success: function( data, textStatus) {
			pleaseWaitDiv.modal('hide');
			if(data.success){ 
				$('#tabUtentiTr_'+idUtente).removeClass("bg-blue color-palette");
				$('#btnAssociaUtente_'+idUtente).attr("disabled",false);
				$('#btnDisAssociaUtente_'+idUtente).attr("disabled",true);    		
			}else{
				$('#modalErrorDiv').html(data.message);
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");
				$('#myModalError').modal('show');
			}
		},
		error: function(jqXHR, textStatus, errorThrown){
			pleaseWaitDiv.modal('hide');
    		  
   			$('#modalErrorDiv').html(errorThrown.message);
		  	$('#myModalError').removeClass();
			$('#myModalError').addClass("modal modal-danger");
			$('#myModalError').modal('show');
		}
	});
}
  
function associaPermesso(idPermesso,idRuolo){
	$.ajax({
		type: "POST",
		url: "gestioneAssociazioniAjax.do?action=associaPermesso&idRuolo="+idRuolo+"&idPermesso="+idPermesso,
		dataType: "json",
		success: function( data, textStatus) {
			pleaseWaitDiv.modal('hide');
			if(data.success){ 
				$('#tabPermessiTr_'+idPermesso).addClass("bg-blue color-palette");
				$('#btnAssociaPermessi_'+idPermesso).attr("disabled",true);
				$('#btnDisAssociaPermessi_'+idPermesso).attr("disabled",false);    			 
			}else{
				$('#modalErrorDiv').html(data.message);
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");
				$('#myModalError').modal('show');
			}
		},
		error: function(jqXHR, textStatus, errorThrown){
			pleaseWaitDiv.modal('hide');
   
   			$('#modalErrorDiv').html(errorThrown.message);
		  	$('#myModalError').removeClass();
			$('#myModalError').addClass("modal modal-danger");
			$('#myModalError').modal('show');    
		}
	});
}

function disassociaPermesso(idPermesso,idRuolo){
	$.ajax({
		type: "POST",
		url: "gestioneAssociazioniAjax.do?action=disassociaPermesso&idRuolo="+idRuolo+"&idPermesso="+idPermesso,
		dataType: "json",
		success: function( data, textStatus) {
			pleaseWaitDiv.modal('hide');
			if(data.success){ 
				$('#tabPermessiTr_'+idPermesso).removeClass("bg-blue color-palette");
				$('#btnAssociaPermessi_'+idPermesso).attr("disabled",false);
				$('#btnDisAssociaPermessi_'+idPermesso).attr("disabled",true);
    		
			}else{
				$('#modalErrorDiv').html(data.message);
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");
				$('#myModalError').modal('show');
			}
		},
		error: function(jqXHR, textStatus, errorThrown){
			pleaseWaitDiv.modal('hide');
			
   			$('#modalErrorDiv').html(errorThrown.message);
		  	$('#myModalError').removeClass();
			$('#myModalError').addClass("modal modal-danger");
			$('#myModalError').modal('show');
		}
	});
}
  
function openDettaglioInterventoModal(tipo,loop){
	 
	if(tipo == "intervento"){
		$('#interventiModal'+loop).modal("show");
	}
	if(tipo == "interventoDati"){
		$('#interventiDatiModal'+loop).modal("show");
	}
}
  
function modalEliminaCertificatoCampione(id){
	  
	$('#idElimina').val(id);
	$('#modalEliminaCertificatoCampione').modal();
	  
}
  
function eliminaCertificatoCampione(){			  
	$("#modalEliminaCertificatoCampione").modal("hide");

	pleaseWaitDiv = $('#pleaseWaitDialog');
	pleaseWaitDiv.modal();
	
	var id=$('#idElimina').val();
	var dataObj = {};
	dataObj.idCert = id;

	$.ajax({
		type: "POST",
		url: "scaricaCertificato.do?action=eliminaCertificatoCampione",
		data: dataObj,
		dataType: "json",
		success: function( data, textStatus) {
		  
			pleaseWaitDiv.modal('hide');
		  
			if(data.success){ 						 
				$('#myModalErrorContent').html(data.messaggio);
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-success");
				$('#myModalError').modal('show');						
			}else{
				$('#myModalErrorContent').html(data.messaggio);
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");
				$('#myModalError').modal('show');			 
			}
		},
		error: function(jqXHR, textStatus, errorThrown){
			pleaseWaitDiv.modal('hide');

			$('#myModalErrorContent').html(textStatus);
		  	$('#myModalError').removeClass();
			$('#myModalError').addClass("modal modal-danger");
			$('#myModalError').modal('show');
		}
	});		  
}
  
function modificaValoriCampioneTrigger(umJson) {	 	  
	$(".numberfloat").change(function(){

		var str = $(this).val();
		var res = str.replace(",",".");
		$(this).val(res);
	});
	$(".incRelativa").change(function(){
  		
		var str = $(this).val();
		var res = str.replace(",",".");	    	
		var thisid = $(this).attr('id');	    
		var resId = thisid.split("_");
		
		var taratura = $("#tblAppendGrid_valore_taratura_"+resId[3]).val();
		if(taratura != 0 && taratura != ""){
	    		
			x = new BigNumber(res);
			y = new BigNumber(taratura);
	    		
			var assoluta = x.times(y);
			$("#tblAppendGrid_incertezza_assoluta_"+resId[3]).val(assoluta);
		}	    	
  	});
	
  	$("input").change(function(){ 
  		$("#ulError").html("");
  	});
  	$("select").change(function(){   		
  		$("#ulError").html("");
  	});
  	$("input").keydown(function(){   		
  		$("#ulError").html("");
  	});
  	$("select").keydown(function(){   		
  		$("#ulError").html("");
  	});
  	$('.select2MV').select2({
  		placeholder: "Seleziona",
  		dropdownCssClass: "select2MVOpt",  		
  	});

	$('.tipograndezzeselect').on("select2:select",function(evt){
  		var str = $(this).attr("id");
  		var value = $(this).val();
  		var resId = str.split("_");
  		var select = $('#tblAppendGrid_unita_misura_'+resId[3]);   
		select.empty();
  		if(value!=0){	
  			var umList = umJson[value];

  			for (var j = 0; j < umList.length; j++){                 
  				select.append("<option value='" +umList[j].value+ "'>" +umList[j].label+ "</option>");    
  			}   
  		}  		
	});
}

function dettaglioStrumentoFromMisura(idStrumento){
	exploreModal("dettaglioStrumento.do","id_str="+idStrumento,"#dettaglio");
	$( "#myModalDettaglioStrumento" ).modal();
	$('body').addClass('noScroll');
}
 
var arrayListaPuntiJson;
function openDettaglioPunto(indexArrayPunti, indexPunto){
	//alert(arrayListaPuntiJson[indexArrayPunti][indexPunto].accettabilita);	  	
	
	$("#dettaglioPuntoID").html(arrayListaPuntiJson[indexArrayPunti][indexPunto].id);
	$("#dettaglioPuntoIdTabella").html(arrayListaPuntiJson[indexArrayPunti][indexPunto].id_tabella);
	$("#dettaglioPuntoOrdine").html(arrayListaPuntiJson[indexArrayPunti][indexPunto].ordine);
	$("#dettaglioPuntoTipoProva").html(arrayListaPuntiJson[indexArrayPunti][indexPunto].tipoProva);
	$("#dettaglioPuntoUM").html(arrayListaPuntiJson[indexArrayPunti][indexPunto].um);
	$("#dettaglioPuntoValoreCampione").html(arrayListaPuntiJson[indexArrayPunti][indexPunto].valoreCampione);
	$("#dettaglioPuntoValoreMedioCampione").html(arrayListaPuntiJson[indexArrayPunti][indexPunto].valoreMedioCampione);
	$("#dettaglioPuntoValoreStrumento").html(arrayListaPuntiJson[indexArrayPunti][indexPunto].valoreStrumento);
	$("#dettaglioPuntoValoreMedioStrumento").html(arrayListaPuntiJson[indexArrayPunti][indexPunto].valoreMedioStrumento);
	$("#dettaglioPuntoScostamento").html(arrayListaPuntiJson[indexArrayPunti][indexPunto].scostamento);
	$("#dettaglioPuntoAccettabilita").html(arrayListaPuntiJson[indexArrayPunti][indexPunto].accettabilita);
	$("#dettaglioPuntoIncertezza").html(arrayListaPuntiJson[indexArrayPunti][indexPunto].incertezza);
	$("#dettaglioPuntoEsito").html(arrayListaPuntiJson[indexArrayPunti][indexPunto].esito);
	$("#dettaglioPuntoDescrizioneCampione").html(arrayListaPuntiJson[indexArrayPunti][indexPunto].desc_Campione);
	$("#dettaglioPuntoDescrizioneParametro").html(arrayListaPuntiJson[indexArrayPunti][indexPunto].desc_parametro);
	$("#dettaglioPuntoMisura").html(arrayListaPuntiJson[indexArrayPunti][indexPunto].misura);
	$("#dettaglioPuntoUMCalcolata").html(arrayListaPuntiJson[indexArrayPunti][indexPunto].um_calc);
	$("#dettaglioPuntoRisoluzioneMisura").html(arrayListaPuntiJson[indexArrayPunti][indexPunto].risoluzione_misura);
	$("#dettaglioPuntoRisoluzioneCampione").html(arrayListaPuntiJson[indexArrayPunti][indexPunto].risoluzione_campione);
	$("#dettaglioPuntoFondoScala").html(arrayListaPuntiJson[indexArrayPunti][indexPunto].fondoScala);
	$("#dettaglioPuntoInterpolazione").html(arrayListaPuntiJson[indexArrayPunti][indexPunto].interpolazione);
	$("#dettaglioPuntoFM").html(arrayListaPuntiJson[indexArrayPunti][indexPunto].fm);
	$("#dettaglioPuntoSelConversione").html(arrayListaPuntiJson[indexArrayPunti][indexPunto].selConversione);
	$("#dettaglioPuntoSelTolleranza").html(arrayListaPuntiJson[indexArrayPunti][indexPunto].selTolleranza);
	$("#dettaglioPuntoLetturaCampione").html(arrayListaPuntiJson[indexArrayPunti][indexPunto].letturaCampione);
	$("#dettaglioPuntoPercUtil").html(arrayListaPuntiJson[indexArrayPunti][indexPunto].per_util);

	$("#myModalDettaglioPunto").modal();
}

function scaricaFile(){
	alert('download file');
}

//Gestione Categorie Verifica  
function nuovaCategoriaVerifica(){
	  
	if($("#formNuovaCategoriaVerifica").valid()){
		pleaseWaitDiv = $('#pleaseWaitDialog');
		pleaseWaitDiv.modal();

		var descrizione=$('#descrizione').val();
		var sigla=$('#sigla').val();
		var codice=$('#codice').val();
		var dataObj = {};
		
		dataObj.descrizione = descrizione;
		dataObj.sigla = sigla;
		dataObj.codice = codice;

		$.ajax({
			type: "POST",
			url: "gestioneCategorieVerifica.do?action=nuovo",
			data: dataObj,
			dataType: "json",
			success: function( data, textStatus) {
        		  
				pleaseWaitDiv.modal('hide');
        		  
				if(data.success){         		
					$("#modalNuovaCategoriaVerifica").modal("hide");
					$('#myModalErrorContent').html(data.messaggio);
					$('#myModalError').removeClass();
					$('#myModalError').addClass("modal modal-success");
					$('#myModalError').modal('show');        				        		
				}else{
					$('#myModalErrorContent').html(data.messaggio);
					$('#myModalError').removeClass();
					$('#myModalError').addClass("modal modal-danger");
					$('#myModalError').modal('show');        			 
				}
			},
			error: function(jqXHR, textStatus, errorThrown){
				pleaseWaitDiv.modal('hide');

				$('#myModalErrorContent').html(textStatus);
  			  	$('#myModalError').removeClass();
  				$('#myModalError').addClass("modal modal-danger");
  				$('#myModalError').modal('show');
        
			}
		});
	}
}
  
function modificaCategoriaVerifica(){
	  
	pleaseWaitDiv = $('#pleaseWaitDialog');
	pleaseWaitDiv.modal();
	
	var id=$('#modid').val();
	var sigla=$('#modsigla').val();
	var descrizione=$('#moddescrizione').val();
	var codice=$('#modcodice').val();
	    
	var dataObj = {};
	dataObj.id = id;
	dataObj.descrizione = descrizione;
	dataObj.codice = codice;
	dataObj.sigla = sigla;
	$.ajax({
		type: "POST",
		url: "gestioneCategorieVerifica.do?action=modifica",
		data: dataObj,
		dataType: "json",
		success: function( data, textStatus) {
      		  
			pleaseWaitDiv.modal('hide');
      		  
			if(data.success){       			
				$("#modalModificaCategoriaVerifica").modal("hide");
				$('#myModalErrorContent').html(data.messaggio);
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-success");
				$('#myModalError').modal('show');      				      		
			}else{
				$('#myModalErrorContent').html(data.messaggio);
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");
				$('#myModalError').modal('show');      			
			}
		},
		error: function(jqXHR, textStatus, errorThrown){
			pleaseWaitDiv.modal('hide');

			$('#myModalErrorContent').html(textStatus);
			$('#myModalError').removeClass();
			$('#myModalError').addClass("modal modal-danger");
			$('#myModalError').modal('show');			
		}
	});	  
}

function eliminaCategoriaVerifica(){
	 
	$("#modalEliminaCategoriaVerifica").modal("hide");

	pleaseWaitDiv = $('#pleaseWaitDialog');
	pleaseWaitDiv.modal();

	var id=$('#idElimina').val();
	var dataObj = {};
	dataObj.id = id;

	$.ajax({
		type: "POST",
		url: "gestioneCategorieVerifica.do?action=elimina",
		data: dataObj,
		dataType: "json",
		success: function( data, textStatus) {
		  
			pleaseWaitDiv.modal('hide');
		  
			if(data.success){ 						 
				$('#myModalErrorContent').html(data.messaggio);
			  	$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-success");
				$('#myModalError').modal('show');				
			}else{
				$('#myModalErrorContent').html(data.messaggio);
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");
				$('#myModalError').modal('show');				 
			}
		},
		error: function(jqXHR, textStatus, errorThrown){
			pleaseWaitDiv.modal('hide');

			$('#myModalErrorContent').html(textStatus);
		  	$('#myModalError').removeClass();
			$('#myModalError').addClass("modal modal-danger");
			$('#myModalError').modal('show');
		}
	});
}

function modalModificaCategoriaVerifica(id,sigla,descrizione,codice){
	  
	$('#modid').val(id);
	$('#modsigla').val(sigla);
	$('#moddescrizione').val(descrizione);
	$('#modcodice').val(codice);
	    
	$('#modalModificaCategoriaVerifica').modal();
	  
}

function modalEliminaCategoriaVerifica(id,descrizione){
	  
	$('#idElimina').val(id);
	$('#descrizioneElimina').html(descrizione);
	    
	$('#modalEliminaCategoriaVerifica').modal();
	  
}

//Gestione Tipi Verifica  
function nuovoTipoVerifica(){
	  
	if($("#formNuovoTipoVerifica").valid()){
		pleaseWaitDiv = $('#pleaseWaitDialog');
		pleaseWaitDiv.modal();

		var sigla=$('#sigla').val();
		var categoria=$('#categoria').val();
		var descrizione=$('#descrizione').val();
		var codice=$('#codice').val();
		var dataObj = {};
		
		dataObj.sigla = sigla;
		dataObj.categoria = categoria;
		dataObj.descrizione = descrizione;
		dataObj.codice = codice;

		$.ajax({
			type: "POST",
			url: "gestioneTipiVerifica.do?action=nuovo",
			data: dataObj,
			dataType: "json",
			success: function( data, textStatus) {
        		  
				pleaseWaitDiv.modal('hide');
        		  
				if(data.success){         		
					$("#modalNuovoTipoVerifica").modal("hide");
					$('#myModalErrorContent').html(data.messaggio);
					$('#myModalError').removeClass();
					$('#myModalError').addClass("modal modal-success");
					$('#myModalError').modal('show');        				        		
				}else{
					$('#myModalErrorContent').html(data.messaggio);
					$('#myModalError').removeClass();
					$('#myModalError').addClass("modal modal-danger");
					$('#myModalError').modal('show');        			 
				}
			},
			error: function(jqXHR, textStatus, errorThrown){
				pleaseWaitDiv.modal('hide');

				$('#myModalErrorContent').html(textStatus);
  			  	$('#myModalError').removeClass();
  				$('#myModalError').addClass("modal modal-danger");
  				$('#myModalError').modal('show');
        
			}
		});
	}
}
  
function modificaTipoVerifica(){
	  
	pleaseWaitDiv = $('#pleaseWaitDialog');
	pleaseWaitDiv.modal();
	
	var id=$('#modid').val();
	var sigla=$('#modsigla').val();
	var categoria=$('#modcategoria').val();
	var descrizione=$('#moddescrizione').val();
	var codice=$('#modcodice').val();
	    
	var dataObj = {};
	dataObj.id = id;
	dataObj.sigla = sigla;
	dataObj.categoria = categoria;
	dataObj.descrizione = descrizione;
	dataObj.codice = codice;

	$.ajax({
		type: "POST",
		url: "gestioneTipiVerifica.do?action=modifica",
		data: dataObj,
		dataType: "json",
		success: function( data, textStatus) {
      		  
			pleaseWaitDiv.modal('hide');
      		  
			if(data.success){       			
				$("#modalModificaTipoVerifica").modal("hide");
				$('#myModalErrorContent').html(data.messaggio);
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-success");
				$('#myModalError').modal('show');      				      		
			}else{
				$('#myModalErrorContent').html(data.messaggio);
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");
				$('#myModalError').modal('show');      			
			}
		},
		error: function(jqXHR, textStatus, errorThrown){
			pleaseWaitDiv.modal('hide');

			$('#myModalErrorContent').html(textStatus);
			$('#myModalError').removeClass();
			$('#myModalError').addClass("modal modal-danger");
			$('#myModalError').modal('show');			
		}
	});	  
}

function eliminaTipoVerifica(){
	 
	$("#modalEliminaTipoVerifica").modal("hide");

	pleaseWaitDiv = $('#pleaseWaitDialog');
	pleaseWaitDiv.modal();

	var id=$('#idElimina').val();
	var dataObj = {};
	dataObj.id = id;

	$.ajax({
		type: "POST",
		url: "gestioneTipiVerifica.do?action=elimina",
		data: dataObj,
		dataType: "json",
		success: function( data, textStatus) {
		  
			pleaseWaitDiv.modal('hide');
		  
			if(data.success){ 						 
				$('#myModalErrorContent').html(data.messaggio);
			  	$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-success");
				$('#myModalError').modal('show');				
			}else{
				$('#myModalErrorContent').html(data.messaggio);
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");
				$('#myModalError').modal('show');				 
			}
		},
		error: function(jqXHR, textStatus, errorThrown){
			pleaseWaitDiv.modal('hide');

			$('#myModalErrorContent').html(textStatus);
		  	$('#myModalError').removeClass();
			$('#myModalError').addClass("modal modal-danger");
			$('#myModalError').modal('show');
		}
	});
}

function modalModificaTipoVerifica(id,sigla,categoria,descrizione,codice){
	  
	$('#modid').val(id);
	$('#modsigla').val(sigla);
	$('#modcategoria').val(categoria);
	$('#moddescrizione').val(descrizione);
	$('#modcodice').val(codice);
	    
	$('#modalModificaTipoVerifica').modal();
	  
}

function modalEliminaTipoVerifica(id,descrizione){
	  
	$('#idElimina').val(id);
	$('#descrizioneElimina').html(descrizione);
	    
	$('#modalEliminaTipoVerifica').modal();
	  
}



function nuovaAttrezzatura(){

	pleaseWaitDiv = $('#pleaseWaitDialog');
	pleaseWaitDiv.modal();
	
	var cliente_return = $('#select1').val();	
	var sede_return = $('#select2').val();
	
	var id_cliente = $('#cliente').val();
	var id_sede = $('#sede').val();
	var matricola_inail = $('#matricola_inail').val();
	var numero_fabbrica = $('#numero_fabbrica').val();
	var descrizione = $('#descrizione').val();
	var tipo_attivita = $('#tipo_attivita').val();
	var data_ver_funz = $('#data_verifica_funzionamento').val();
	var data_pross_ver_funz = $('#data_prossima_verifica_funzionamento').val();
	var data_ver_integrita = $('#data_verifica_integrita').val();
	var data_pross_ver_integrita = $('#data_prossima_verifica_integrita').val();
	var data_ver_interna = $('#data_verifica_interna').val();
	var data_pross_ver_interna = $('#data_prossima_verifica_interna').val();	
	var anno_costruzione = $('#anno_costruzione').val();
	var fabbricante = $('#fabbricante').val();
	var modello = $('#modello').val();
	var settore_impiego = $('#settore_impiego').val();
	var note_tecniche = $('#note_tecniche').val();
	var note_generiche = $('#note_generiche').val();	
	var tipo_attivita = $('#tipo_attivita').val();
	var tipo_attrezzatura = $('#tipo_attrezzatura').val();
	var tipo_attrezzatura_gvr = $('#tipo_attrezzatura_gvr').val();
	var id_specifica = $('#id_specifica').val();
	var sogg_messa_serv_GVR = $('#sogg_messa_serv_GVR').val();
	var n_panieri_idroestrattori = $('#n_panieri_idroestrattori').val();
	var marcatura = $('#marcatura').val();
	var n_id_on = $('#n_id_on').val();
	var data_scadenza_ventennale = $('#data_scadenza_ventennale').val();
	var codice_milestone = $('#codice_milestone').val();

	var dataObj = {};
	          
	dataObj.id_cliente = id_cliente;
	dataObj.id_sede = id_sede;
	dataObj.matricola_inail = matricola_inail;
	dataObj.numero_fabbrica = numero_fabbrica;
	dataObj.descrizione = descrizione;
	dataObj.tipo_attivita = tipo_attivita;
	dataObj.data_ver_funz = data_ver_funz;
	dataObj.data_pross_ver_funz = data_pross_ver_funz;
	dataObj.data_ver_integrita = data_ver_integrita;
	dataObj.data_pross_ver_integrita = data_pross_ver_integrita;
	dataObj.data_ver_interna = data_ver_interna;
	dataObj.data_pross_ver_interna = data_pross_ver_interna;
	dataObj.anno_costruzione = anno_costruzione;
	dataObj.fabbricante = fabbricante;
	dataObj.modello = modello;
	dataObj.settore_impiego = settore_impiego;
	dataObj.note_tecniche = note_tecniche;
	dataObj.note_generiche = note_generiche;
	dataObj.tipo_attrezzatura = tipo_attrezzatura;
	dataObj.tipo_attrezzatura_gvr = tipo_attrezzatura_gvr;
	dataObj.id_specifica = id_specifica;
	dataObj.sogg_messa_serv_GVR = sogg_messa_serv_GVR;
	dataObj.n_panieri_idroestrattori = n_panieri_idroestrattori;
	dataObj.marcatura = marcatura;
	dataObj.n_id_on = n_id_on;
	dataObj.data_scadenza_ventennale = data_scadenza_ventennale;
	dataObj.codice_milestone = codice_milestone;

	$.ajax({
		type: "POST",
		url: "listaAttrezzature.do?action=nuovo",
		data: dataObj,
		dataType: "json",

		success: function( data, textStatus) {

			if(data.success){ 
				$('#modalNuovaAttrezzatura').modal('hide');
				
				 dataString ="action=cliente_sede&id_cliente="+ cliente_return+"&id_sede="+sede_return;
		          exploreModal("listaAttrezzature.do",dataString,"#posTab",function(data,textStatus){
				
		        	  pleaseWaitDiv.modal('hide');
				});
		          $('.modal-backdrop').hide();          		
			}else{
				pleaseWaitDiv.modal('hide');
				// $('#empty').html("<h3 class='label label-error' style=\"color:green\">"+data.message+"</h3>");
				$("#myModalErrorContent").html(data.messaggio);
				$("#myModalError").modal();
			}
		},

		error: function(jqXHR, textStatus, errorThrown){	          
			// $('#empty').html("<h3 class='label label-danger'>"+textStatus+"</h3>");
			pleaseWaitDiv.modal('hide');
			$("#myModalErrorContent").html(textStatus);
			$("#myModalError").modal();
			
		}
	});	  	  	  	  
}



function modificaAttrezzatura(scadenzario, date, tipo_data){

	pleaseWaitDiv = $('#pleaseWaitDialog');
	pleaseWaitDiv.modal();
	
	var cliente_return = $('#select1').val();	
	var sede_return = $('#select2').val();
	
	var id_attrezzatura = $('#id_attrezzatura').val();
	var id_cliente = $('#cliente_mod').val();
	var id_sede = $('#sede_mod').val();
	var matricola_inail = $('#matricola_inail_mod').val();
	var numero_fabbrica = $('#numero_fabbrica_mod').val();
	var descrizione = $('#descrizione_mod').val();
	var tipo_attivita = $('#tipo_attivita_mod').val();
	var data_ver_funz = $('#data_verifica_funzionamento_mod').val();
	var data_pross_ver_funz = $('#data_prossima_verifica_funzionamento_mod').val();
	var data_ver_integrita = $('#data_verifica_integrita_mod').val();
	var data_pross_ver_integrita = $('#data_prossima_verifica_integrita_mod').val();
	var data_ver_interna = $('#data_verifica_interna_mod').val();
	var data_pross_ver_interna = $('#data_prossima_verifica_interna_mod').val();		
	var anno_costruzione = $('#anno_costruzione_mod').val();
	var fabbricante = $('#fabbricante_mod').val();
	var modello = $('#modello_mod').val();
	var settore_impiego = $('#settore_impiego_mod').val();
	var note_tecniche = $('#note_tecniche_mod').val();
	var note_generiche = $('#note_generiche_mod').val();	
	var tipo_attrezzatura = $('#tipo_attrezzatura_mod').val();
	var tipo_attrezzatura_gvr = $('#tipo_attrezzatura_gvr_mod').val();
	var id_specifica = $('#id_specifica_mod').val();
	var sogg_messa_servizio_gvr = $('#sogg_messa_serv_GVR_mod').val();
	var n_panieri_idroestrattori = $('#n_panieri_idroestrattori_mod').val();
	var marcatura = $('#marcatura_mod').val();
	var n_id_on = $('#n_id_on_mod').val();
	var data_scadenza_ventennale = $('#data_scadenza_ventennale_mod').val();
	var numero_certificato = $('#numero_certificato_mod').val();
	var codice_milestone = $('#codice_milestone_mod').val();
	  		
	var dataObj = {};
	 
	dataObj.id_attrezzatura = id_attrezzatura;
	dataObj.id_cliente = id_cliente;
	dataObj.id_sede = id_sede;
	dataObj.matricola_inail = matricola_inail;
	dataObj.numero_fabbrica = numero_fabbrica;
	dataObj.descrizione = descrizione;
	dataObj.tipo_attivita = tipo_attivita;
	dataObj.data_ver_funz = data_ver_funz;
	dataObj.data_pross_ver_funz = data_pross_ver_funz;
	dataObj.data_ver_integrita = data_ver_integrita;
	dataObj.data_pross_ver_integrita = data_pross_ver_integrita;
	dataObj.data_ver_interna = data_ver_interna;
	dataObj.data_pross_ver_interna = data_pross_ver_interna;
	dataObj.anno_costruzione = anno_costruzione;
	dataObj.fabbricante = fabbricante;
	dataObj.modello = modello;
	dataObj.settore_impiego = settore_impiego;
	dataObj.note_tecniche = note_tecniche;
	dataObj.note_generiche = note_generiche;
	dataObj.tipo_attrezzatura = tipo_attrezzatura;
	dataObj.tipo_attrezzatura_gvr = tipo_attrezzatura_gvr;
	dataObj.id_specifica = id_specifica;
	dataObj.sogg_messa_serv_GVR = sogg_messa_servizio_gvr;
	dataObj.n_panieri_idroestrattori = n_panieri_idroestrattori;
	dataObj.marcatura = marcatura;
	dataObj.n_id_on = n_id_on;
	dataObj.data_scadenza_ventennale = data_scadenza_ventennale;
	dataObj.numero_certificato = numero_certificato;
	dataObj.codice_milestone = codice_milestone;
	
	$.ajax({
		type: "POST",
		url: "listaAttrezzature.do?action=modifica",
		data: dataObj,
		dataType: "json",

		success: function( data, textStatus) {

			if(data.success){ 
				$('#modalModificaAttrezzatura').modal('hide');
				
				if(scadenzario){
					dataString ="?action=scadenzario&data="+ date+"&tipo_data="+tipo_data;
					callAction("listaAttrezzature.do"+dataString)
		 
				}else{
					dataString ="action=cliente_sede&id_cliente="+ cliente_return+"&id_sede="+sede_return;
			         exploreModal("listaAttrezzature.do",dataString,"#posTab",function(data,textStatus){

			        	  pleaseWaitDiv.modal('hide');
					});	
				}
				
		          $('.modal-backdrop').hide();          		
			}else{
				pleaseWaitDiv.modal('hide');				
				$("#myModalErrorContent").html(data.messaggio);
				$("#myModalError").modal();
			}
		},

		error: function(jqXHR, textStatus, errorThrown){	          
			// $('#empty').html("<h3 class='label label-danger'>"+textStatus+"</h3>");
			pleaseWaitDiv.modal('hide');
			$("#myModalErrorContent").html(textStatus);
			$("#myModalError").modal();
			
		}
	});	  	  	  	  
}



function addCalendarAttrezzatura(tipo_data){ 
	
	   $('#tipo_data').val(tipo_data);
	   
	   pleaseWaitDiv = $('#pleaseWaitDialog');
	   pleaseWaitDiv.modal();
$.ajax({
       type: "POST",
       url: "scadenzarioCreateAttrezzatura.do",
       data: "",
       dataType: "json",
       
       //if received a response from the server
       success: function( data, textStatus) {
       	console.log("test");
       	var id = 0;
          	if(data.success)
           	{
          		
          		 jsonObj = [];
          		
          		 if(tipo_data==0 || tipo_data==1){
	             		for(var i=0 ; i<data.obj_funzionamento.length;i++)
	                    {
	             			var str =data.obj_funzionamento[i].split(";");
	             			item = {};
	             			item ["id"] = id;
	             	        item ["title"] = str[1];
	             	        item ["start"] = str[0];
	             	        item ["allDay"] = true;
	             	       item ["backgroundColor"] = "#00a65a";
	             	      item ["borderColor"] = "#00a65a";
	             	      item ["className"]
	             	        jsonObj.push(item);
	             	      id++;
	              		}
          		 }
          		if(tipo_data==0 || tipo_data==2){
	             		for(var i=0 ; i<data.obj_integrita.length;i++)
	                    {
	             			var str =data.obj_integrita[i].split(";");
	             			item = {};
	             			item ["id"] = id;
	             	        item ["title"] = str[1];
	             	        item ["start"] = str[0];
	             	        item ["allDay"] = true;
	             	       item ["backgroundColor"] = "#9d201d";
	             	      item ["borderColor"] = "#9d201d";
	             	        jsonObj.push(item);
	             	       id++;
	              		}
          		}
          		if(tipo_data==0 || tipo_data==3){
	             		for(var i=0 ; i<data.obj_interna.length;i++)
	                    {
	             			var str =data.obj_interna[i].split(";");
	             			item = {};
	             			item ["id"] = id;
	             	        item ["title"] = str[1];
	             	        item ["start"] = str[0];
	             	        item ["allDay"] = true;
	             	       item ["backgroundColor"] = "#777";
	             	      item ["borderColor"] = "#777";
	             	        jsonObj.push(item);
	             	       id++;
	              		}
          		}
          		 var calendario;
          		if(tipo_data==0){
          			 calendario= $('#calendario');
          		}else{
          			 calendario = $('#calendario2');
          		}
     
          		
     $('#calendario').fullCalendar({
		header: {
	        left: 'prev,next today',
	        center: 'title',
	        right: 'month,agendaWeek,agendaDay'
	      },

//	      eventRender: function(event, element, view) {
//			
//	    	  return $('<canvas id="pieChart_'+event.id+'" style="width:100%;height:50%"></canvas>');
//	    	  
//	    	  
//	         },	 
		  viewRender: function (view, element)
		    {
		        intervalStart = view.intervalStart;
		        intervalEnd = view.intervalEnd;
		        intervalEnd._i =intervalEnd._i+3600000+3600000; 
		        $('#data_start').val(moment(intervalStart).format());
		        $('#data_end').val(moment(intervalEnd).format());
		     
		    },
		  eventRender: function(event, element, view) {
			  if(event.backgroundColor=="#00a65a"){
				  return $('<span class=\"badge bg-green bigText\"">' 
				             + event.title + 
				             '</button>'); 
			  }else if(event.backgroundColor=="#9d201d"){
				  return $('<span class=\"badge bg-red bigText\"">' 
				             + event.title + 
				             '</span>');
			  }
			  else{
				  return $('<span class=\"badge bg-grey bigText\"">' 
				             + event.title + 
				             '</span>');
			  }
	            
	         },	 
	  events:jsonObj,
	           eventClick: function(calEvent, jsEvent, view) {
	        	var tipo_data;
	        	   if(calEvent.backgroundColor=="#00a65a"){
	        		   tipo_data = "data_prossima_verifica_funzionamento";
	        	   }else if(calEvent.backgroundColor=="#9d201d"){
	        		   tipo_data = "data_prossima_verifica_integrita";
	        	   }else if(calEvent.backgroundColor=="#777"){
	        		   tipo_data = "data_prossima_verifica_interna";
	        	   }
	        	   
	        	callAction('listaAttrezzature.do?action=scadenzario&data='+moment(calEvent.start).format()+'&tipo_data='+tipo_data);

	        	
	               $(this).css('border-color', '#228B22');
	           },
	     	 
//	         editable: true,
	       drop: function (date, allDay) { // this function is called when something is dropped

	         // retrieve the dropped element's stored Event Object
	         var originalEventObject = $(this).data('eventObject');

	         // we need to copy it, so that multiple events don't have a reference to the same object
	         var copiedEventObject = $.extend({}, originalEventObject);

	         // assign it the date that was reported
	         copiedEventObject.start = date;
	         copiedEventObject.allDay = allDay;
	         copiedEventObject.backgroundColor = $(this).css("background-color");
	         copiedEventObject.borderColor = $(this).css("border-color");

	         // render the event on the calendar
	         // the last `true` argument determines if the event "sticks" (http://arshaw.com/fullcalendar/docs/event_rendering/renderEvent/)
	         $('#calendar').fullCalendar('renderEvent', copiedEventObject, true);

	         // is the "remove after drop" checkbox checked?
	         if ($('#drop-remove').is(':checked')) {
	           // if so, remove the element from the "Draggable Events" list
	           $(this).remove();
	         }

	       }
}); 
           	}
           	

          	if(tipo_data!=0){
          	var	cal = $('#calendario').fullCalendar('getCalendar');
          	cal.removeEvents();
          	cal.addEventSource(jsonObj);
          
             $('#generale_btn').show();
          	}else{
          		var	cal = $('#calendario').fullCalendar('getCalendar');
          		cal.removeEvents();
          		cal.addEventSource(jsonObj);
          		
//          		
          		$('#generale_btn').hide();
          	}
         	pleaseWaitDiv.modal('hide');
	          }
	         });
}



function rendiAttrezzaturaObsoleta(scadenzario,date, tipo_data){
	
	pleaseWaitDiv = $('#pleaseWaitDialog');
	pleaseWaitDiv.modal();
	
	var id_attrezzatura = $('#id_attrezzatura').val();
	var id_cliente = $('#cliente_mod').val();
	var id_sede = $('#sede_mod').val();
	
	var dataObj = {};
	 
	dataObj.id_attrezzatura = id_attrezzatura;

	$.ajax({
		type: "POST",
		url: "listaAttrezzature.do?action=rendi_obsoleta",
		data: dataObj,
		dataType: "json",

		success: function( data, textStatus) {
			if(data.success){
				$('#modalModificaAttrezzatura').modal('hide');
				
				if(scadenzario){
					dataString ="?action=scadenzario&data="+ date+"&tipo_data="+tipo_data;
					callAction("listaAttrezzature.do"+dataString)
		 
				}else{
			
					dataString ="action=cliente_sede&id_cliente="+ id_cliente+"&id_sede="+id_sede;
			         exploreModal("listaAttrezzature.do",dataString,"#posTab",function(data,textStatus){
			        
			        	 pleaseWaitDiv.modal('hide');
					});	
		
				}
				 
		          $('.modal-backdrop').hide();   
				
				    				        		
				}else{
					$('#myModalErrorContent').html(data.messaggio);
					$('#myModalError').removeClass();
					$('#myModalError').addClass("modal modal-danger");
					$('#myModalError').modal('show');        			 
				}
		},

		error: function(jqXHR, textStatus, errorThrown){	          
			// $('#empty').html("<h3 class='label label-danger'>"+textStatus+"</h3>");
			pleaseWaitDiv.modal('hide');
			$("#myModalErrorContent").html(textStatus);
			$("#myModalError").modal();
			
		}
	});	  	  	  	  
	
}


function nuovoStrumentoVerificatore(){
	

	pleaseWaitDiv = $('#pleaseWaitDialog');
	pleaseWaitDiv.modal();

		  var form = $('#formNuovoStrumento')[0]; 
		  var formData = new FormData(form);
		 
$.ajax({
	  type: "POST",
	  url: "gestioneStrumentiVerificatore.do?action=nuovo",
	  data: formData,
	  contentType: false, // NEEDED, DON'T OMIT THIS (requires jQuery 1.6+)
	  processData: false, // NEEDED, DON'T OMIT THIS
	  success: function( data, textStatus) {
		pleaseWaitDiv.modal('hide');
		  	      		  
		  if(data.success)
		  { 
			$('#report_button').hide();
				$('#visualizza_report').hide();
				$('#myModalNuovoStrumento').modal('hide');
			  $('#myModalErrorContent').html(data.messaggio);
			  	$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-success");
				$('#myModalError').modal('show');
				
			$('#myModalError').on('hidden.bs.modal', function(){	         			
				
				 location.reload()
			});
		
		  }else{
			  $('#myModalErrorContent').html(data.messaggio);
			  	$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");
				$('#report_button').hide();
				$('#visualizza_report').hide();
					$('#myModalError').modal('show');	      			 
		  }
	  },

	  error: function(jqXHR, textStatus, errorThrown){
		  pleaseWaitDiv.modal('hide');

		  $('#myModalErrorContent').html("Errore nell'importazione!");
			  	$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");
				$('#report_button').show();
				$('#visualizza_report').show();
				$('#myModalError').modal('show');
				

	  }
});
	
	
}

function modificaStrumentoVerificatore(){
	

	pleaseWaitDiv = $('#pleaseWaitDialog');
	pleaseWaitDiv.modal();

		  var form = $('#formModificaStrumento')[0]; 
		  var formData = new FormData(form);
		 
$.ajax({
	  type: "POST",
	  url: "gestioneStrumentiVerificatore.do?action=modifica",
	  data: formData,
	  contentType: false, // NEEDED, DON'T OMIT THIS (requires jQuery 1.6+)
	  processData: false, // NEEDED, DON'T OMIT THIS
	  success: function( data, textStatus) {
		pleaseWaitDiv.modal('hide');
		  	      		  
		  if(data.success)
		  { 
			$('#report_button').hide();
				$('#visualizza_report').hide();
				$('#myModalModificaStrumento').modal('hide');
			  $('#myModalErrorContent').html(data.messaggio);
			  	$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-success");
				$('#myModalError').modal('show');
				
			$('#myModalError').on('hidden.bs.modal', function(){	         			
				
				 location.reload()
			});
		
		  }else{
			  $('#myModalErrorContent').html(data.messaggio);
			  	$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");
				$('#report_button').hide();
				$('#visualizza_report').hide();
					$('#myModalError').modal('show');	      			 
		  }
	  },

	  error: function(jqXHR, textStatus, errorThrown){
		  pleaseWaitDiv.modal('hide');

		  $('#myModalErrorContent').html("Errore nell'importazione!");
			  	$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");
				$('#report_button').show();
				$('#visualizza_report').show();
				$('#myModalError').modal('show');
				

	  }
});
	
	
}


	function dissociaStrumento(id_strumento){
			

		pleaseWaitDiv = $('#pleaseWaitDialog');
		pleaseWaitDiv.modal();
				
		var dataObj = {};
		 
		dataObj.id_strumento = id_strumento;

		$.ajax({
			type: "POST",
			url: "gestioneStrumentiVerificatore.do?action=dissocia",
			data: dataObj,
			dataType: "json",

			success: function( data, textStatus) {
				pleaseWaitDiv.modal('hide');
				if(data.success){
					$('#report_button').hide();
					$('#visualizza_report').hide();
					$('#myModalModificaStrumento').modal('hide');
				  $('#myModalErrorContent').html(data.messaggio);
				  	$('#myModalError').removeClass();
					$('#myModalError').addClass("modal modal-success");
					$('#myModalError').modal('show');
					
				$('#myModalError').on('hidden.bs.modal', function(){	         			
					
					 location.reload()
				});			        		
					}else{
						$('#myModalErrorContent').html(data.messaggio);
						$('#myModalError').removeClass();
						$('#myModalError').addClass("modal modal-danger");
						$('#myModalError').modal('show');        			 
					}
			},

			error: function(jqXHR, textStatus, errorThrown){	          
				// $('#empty').html("<h3 class='label label-danger'>"+textStatus+"</h3>");
				pleaseWaitDiv.modal('hide');
				$("#myModalErrorContent").html(textStatus);
				$("#myModalError").modal();
				
			}
		});	  	  	  	  
		
		}
	
	
	function eliminaStrumentoVerificatore(id_strumento){
		

		pleaseWaitDiv = $('#pleaseWaitDialog');
		pleaseWaitDiv.modal();
				
		var dataObj = {};
		 
		dataObj.id_strumento = id_strumento;

		$.ajax({
			type: "POST",
			url: "gestioneStrumentiVerificatore.do?action=elimina",
			data: dataObj,
			dataType: "json",

			success: function( data, textStatus) {
				pleaseWaitDiv.modal('hide');
				if(data.success){
					$('#report_button').hide();
					$('#visualizza_report').hide();
					$('#myModalYesOrNo').modal('hide');
				  $('#myModalErrorContent').html(data.messaggio);
				  	$('#myModalError').removeClass();
					$('#myModalError').addClass("modal modal-success");
					$('#myModalError').modal('show');
					
				$('#myModalError').on('hidden.bs.modal', function(){	         			
					
					 location.reload()
				});			        		
					}else{
						$('#myModalErrorContent').html(data.messaggio);
						$('#myModalError').removeClass();
						$('#myModalError').addClass("modal modal-danger");
						$('#myModalError').modal('show');        			 
					}
			},

			error: function(jqXHR, textStatus, errorThrown){	          
				// $('#empty').html("<h3 class='label label-danger'>"+textStatus+"</h3>");
				pleaseWaitDiv.modal('hide');
				$("#myModalErrorContent").html(textStatus);
				$("#myModalError").modal();
				
			}
		});	  	  	  	  
		
		}
	
	function nuovoCampione(){
		

		pleaseWaitDiv = $('#pleaseWaitDialog');
		pleaseWaitDiv.modal();

			  var form = $('#formNuovoCampione')[0]; 
			  var formData = new FormData(form);
			 
	$.ajax({
		  type: "POST",
		  url: "gestioneCampione.do?action=nuovo",
		  data: formData,
		  contentType: false, // NEEDED, DON'T OMIT THIS (requires jQuery 1.6+)
		  processData: false, // NEEDED, DON'T OMIT THIS
		  success: function( data, textStatus) {
			pleaseWaitDiv.modal('hide');
			  	      		  
			  if(data.success)
			  { 
				  $('#modalNuovoCampione').modal('hide');
				  $('#report_button').hide();
					$('#visualizza_report').hide();
					
				  $('#myModalErrorContent').html(data.messaggio);
				  	$('#myModalError').removeClass();
					$('#myModalError').addClass("modal modal-success");
					$('#myModalError').modal('show');
					
				$('#myModalError').on('hidden.bs.modal', function(){	         			
					
					 location.reload()
				});
			
			  }else{
				  $('#myModalErrorContent').html(data.messaggio);
				  	$('#myModalError').removeClass();
					$('#myModalError').addClass("modal modal-danger");
					$('#report_button').hide();
					$('#visualizza_report').hide();
						$('#myModalError').modal('show');	      			 
			  }
		  },

		  error: function(jqXHR, textStatus, errorThrown){
			  pleaseWaitDiv.modal('hide');

			  $('#myModalErrorContent').html("Errore nell'importazione!");
				  	$('#myModalError').removeClass();
					$('#myModalError').addClass("modal modal-danger");
					$('#report_button').show();
					$('#visualizza_report').show();
					$('#myModalError').modal('show');
					

		  }
	});
		
		
	}
	
	
	function nuovaAttivitaCampione(id_campione){
		 
		  var form = $('#formNuovaAttivita')[0]; 
		  var formData = new FormData(form);

	        $.ajax({
	      	  type: "POST",
	      	  url: "gestioneAttivitaCampioni.do?action=nuova&idCamp="+id_campione,
	      	  data: formData,
	      	  //dataType: "json",
	      	  contentType: false, // NEEDED, DON'T OMIT THIS (requires jQuery 1.6+)
	      	  processData: false, // NEEDED, DON'T OMIT THIS
	      	  //enctype: 'multipart/form-data',
	      	  success: function( data, textStatus) {

	      		  if(data.success)
	      		  { 
	      			  $('#report_button').hide();
							$('#visualizza_report').hide();
	      			  $('#myModalErrorContent').html(data.messaggio);
	      			  	$('#myModalError').removeClass();
	      				$('#myModalError').addClass("modal modal-success");
	      				$('#myModalError').modal('show');
	      				

	      		  }else{
	      			  $('#myModalErrorContent').html("Errore nell'inserimento dell'attivit&agrave;!");
	      			  	$('#myModalError').removeClass();
	      				$('#myModalError').addClass("modal modal-danger");	  
	      				$('#report_button').show();
							$('#visualizza_report').show();
							$('#myModalError').modal('show');
							

	      		  }
	      	  },

	      	  error: function(jqXHR, textStatus, errorThrown){
	      	
	      		  $('#myModalErrorContent').html(data.messaggio);
					$('#myModalError').removeClass();
					$('#myModalError').addClass("modal modal-danger");
					$('#report_button').show();
					$('#visualizza_report').show();
					$('#myModalError').modal('show');
					
				
	      	  }
	        });
		 
	}	  

	function modificaAttivitaCampione(id_campione){
		 
		  var form = $('#formModificaAttivita')[0]; 
		  var formData = new FormData(form);

	      $.ajax({
	    	  type: "POST",
	    	  url: "gestioneAttivitaCampioni.do?action=modifica&idCamp="+id_campione,
	    	  data: formData,
	    	  //dataType: "json",
	    	  contentType: false, // NEEDED, DON'T OMIT THIS (requires jQuery 1.6+)
	    	  processData: false, // NEEDED, DON'T OMIT THIS
	    	  //enctype: 'multipart/form-data',
	    	  success: function( data, textStatus) {

	    		  if(data.success)
	    		  { 
	    			  $('#report_button').hide();
							$('#visualizza_report').hide();
	    			  $('#myModalErrorContent').html(data.messaggio);
	    			  	$('#myModalError').removeClass();
	    				$('#myModalError').addClass("modal modal-success");
	    				$('#myModalError').modal('show');
	    				

	    		  }else{
	    			  $('#myModalErrorContent').html("Errore nella modifica dell'attivit&agrave;!");
	    			  	$('#myModalError').removeClass();
	    				$('#myModalError').addClass("modal modal-danger");	  
	    				$('#report_button').show();
							$('#visualizza_report').show();
							$('#myModalError').modal('show');
							

	    		  }
	    	  },

	    	  error: function(jqXHR, textStatus, errorThrown){
	    	
	    		  $('#myModalErrorContent').html(data.messaggio);
					$('#myModalError').removeClass();
					$('#myModalError').addClass("modal modal-danger");
					$('#report_button').show();
					$('#visualizza_report').show();
					$('#myModalError').modal('show');
					
				
	    	  }
	      });
		 
	}	  
	
	
	function modalEliminaDocumentoEsternoCampione(id){
		  
		  $('#idElimina').val(id);
		  $('#modalEliminaDocumentoEsternoCampione').modal();
		  
	}

	function eliminaDocumentoEsternoCampione(){
				  
				  $("#modalEliminaDocumentoEsternoCampione").modal("hide");

		  pleaseWaitDiv = $('#pleaseWaitDialog');
		  pleaseWaitDiv.modal();

		  var id=$('#idElimina').val();
		  var dataObj = {};
		  dataObj.idDoc = id;


	$.ajax({
		  type: "POST",
		  url: "documentiEsterni.do?action=eliminaDocumento",
		  data: dataObj,
		  dataType: "json",
		  success: function( data, textStatus) {
			  
			  pleaseWaitDiv.modal('hide');
			  
			  if(data.success)
			  { 
				
				  $('#report_button').hide();
		  			$('#visualizza_report').hide();		
				  $('#myModalErrorContent').html(data.messaggio);
				  	$('#myModalError').removeClass();
					$('#myModalError').addClass("modal modal-success");
					$('#myModalError').modal('show');
					
			
			  }else{
				  $('#myModalErrorContent').html(data.messaggio);
				  	$('#myModalError').removeClass();
					$('#myModalError').addClass("modal modal-danger");
					$('#report_button').show();
		  			$('#visualizza_report').show();		
					$('#myModalError').modal('show');
				 
			  }
		  },

		  error: function(jqXHR, textStatus, errorThrown){
			  pleaseWaitDiv.modal('hide');

			  $('#myModalErrorContent').html(textStatus);
			  	$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");
				$('#report_button').show();
				$('#visualizza_report').show();		
				$('#myModalError').modal('show');
				

		  }
	});
				  
	}

	
	
	function addCalendarAttivitaCampione(tipo_data, id_campione, registro_eventi){ 
		   
		   pleaseWaitDiv = $('#pleaseWaitDialog');
		   pleaseWaitDiv.modal();
		   
		   if(id_campione!=null){
			   var url = "scadenzario.do?action=create_scadenzario&id_campione="+id_campione;
		   }else{
			   var url= "scadenzario.do?action=create_scadenzario";
		   }
		   
	$.ajax({
	    type: "POST",
	    url: url,
	    data: "",
	    dataType: "json",
	    
	    //if received a response from the server
	    success: function( data, textStatus) {
	    	console.log("test");
	    	var id = 0;
	       	if(data.success)
	        	{
	       		
	       		 jsonObj = [];
	       		
	       		 if(tipo_data==0 || tipo_data==1){
		             		for(var i=0 ; i<data.obj_manutenzione.length;i++)
		                    {
		             			var str =data.obj_manutenzione[i].split(";");
		             			item = {};
		             			item ["id"] = id;
		             	        item ["title"] = str[1];
		             	        item ["start"] = str[0];
		             	        item ["allDay"] = true;
		             	       item ["backgroundColor"] = "#00a65a";
		             	      item ["borderColor"] = "#00a65a";
		             	      item ["className"]
		             	        jsonObj.push(item);
		             	      id++;
		              		}
	       		 }
	       		if(tipo_data==0 || tipo_data==2){
		             		for(var i=0 ; i<data.obj_verifica.length;i++)
		                    {
		             			var str =data.obj_verifica[i].split(";");
		             			item = {};
		             			item ["id"] = id;
		             	        item ["title"] = str[1];
		             	        item ["start"] = str[0];
		             	        item ["allDay"] = true;
		             	       item ["backgroundColor"] = "#9d201d";
		             	      item ["borderColor"] = "#9d201d";
		             	        jsonObj.push(item);
		             	       id++;
		              		}
	       		}
	       		if(tipo_data==0 || tipo_data==3){
		             		for(var i=0 ; i<data.obj_taratura.length;i++)
		                    {
		             			var str =data.obj_taratura[i].split(";");
		             			item = {};
		             			item ["id"] = id;
		             	        item ["title"] = str[1];
		             	        item ["start"] = str[0];
		             	        item ["allDay"] = true;
		             	       item ["backgroundColor"] = "#777";
		             	      item ["borderColor"] = "#777";
		             	        jsonObj.push(item);
		             	       id++;
		              		}
	       		}
	       		 var calendario;
	       		if(tipo_data==0){
	       			 calendario= $('#calendario');
	       		}else{
	       			 calendario = $('#calendario2');
	       		}
	  
	       		
	  $('#calendario').fullCalendar({
		 
			header: {
		        left: 'prev,next today',
		        center: 'title',     
		        right: 'listYear,year,month,agendaWeek,agendaDay'
		      },	
		     
		      
//		      views: {
//		    	    timeGridFourDay: {
//		    	      type: 'timeGrid',
//		    	      duration: { days: 4 },
//		    	      buttonText: '4 day'
//		    	    }
//		    	  },
		      
			  viewRender: function (view, element)
			    {
			        intervalStart = view.intervalStart;
			        intervalEnd = view.intervalEnd ;
			       
			        $('#data_start').val(moment(intervalStart).format());
			        $('#data_end').val(moment(intervalEnd).format());
			     
			    },
		     
			  eventRender: function(event, element, view) {
				  
				 
				  if(event.backgroundColor=="#00a65a"){
					  return $('<span class=\"badge bg-green bigText\"">' 
					             + event.title + 
					             '</button>'); 
				  }else if(event.backgroundColor=="#777"){
					  return $('<span class=\"badge bg-red bigText\"">' 
					             + event.title + 
					             '</span>');
				  }
				  else{
					  return $('<span class=\"badge bg-grey bigText\"">' 
					             + event.title + 
					             '</span>');
				  }
				  
		         },	 
		         
		  events:jsonObj,
		  

		  
		           eventClick: function(calEvent, jsEvent, view) {
		        	var tipo_data;
		        
		        	   if(calEvent.backgroundColor=="#00a65a"){
		        		   tipo_data = "1";	        		  
		        	   }else if(calEvent.backgroundColor=="#777"){
		        		   tipo_data = "3";
		        	   }else if(calEvent.backgroundColor=="#9d201d"){
		        		   tipo_data = "2";
		        	   }
		        	   
		        //	callAction('listaAttrezzature.do?action=scadenzario&data='+moment(calEvent.start).format()+'&tipo_data='+tipo_data);
		        	   callAction('scadenzario.do?action=campioni_scadenza&date='+moment(calEvent.start).format()+'&tipo_data_lat='+tipo_data);
		        	
		               $(this).css('border-color', '#228B22');
		           },
		     	 
//		         editable: true,
		       drop: function (date, allDay) { // this function is called when something is dropped

		         // retrieve the dropped element's stored Event Object
		         var originalEventObject = $(this).data('eventObject');

		         // we need to copy it, so that multiple events don't have a reference to the same object
		         var copiedEventObject = $.extend({}, originalEventObject);

		         // assign it the date that was reported
		         copiedEventObject.start = date;
		         copiedEventObject.allDay = allDay;
		         copiedEventObject.backgroundColor = $(this).css("background-color");
		         copiedEventObject.borderColor = $(this).css("border-color");

		         // render the event on the calendar
		         // the last `true` argument determines if the event "sticks" (http://arshaw.com/fullcalendar/docs/event_rendering/renderEvent/)
		         $('#calendar').fullCalendar('renderEvent', copiedEventObject, true);

		         // is the "remove after drop" checkbox checked?
		         if ($('#drop-remove').is(':checked')) {
		           // if so, remove the element from the "Draggable Events" list
		           $(this).remove();
		         }

		       }
	}); 
	        	}
	        	

	       	if(tipo_data!=0){
	       	var	cal = $('#calendario').fullCalendar('getCalendar');
	       	cal.removeEvents();
	       	cal.addEventSource(jsonObj);
	       
	          $('#generale_btn').show();
	       	}else{
	       		var	cal = $('#calendario').fullCalendar('getCalendar');
	       		cal.removeEvents();
	       		cal.addEventSource(jsonObj);
	       		

	       		$('#generale_btn').hide();
	       	}
	      	pleaseWaitDiv.modal('hide');
		          }
		         });
	}


	function esportaCampioniScadenzario(tipo){	
		
		pleaseWaitDiv = $('#pleaseWaitDialog');
		pleaseWaitDiv.modal();
		
		var start = $('#data_start').val();
		var end = $('#data_end').val();	
		
		
		
		var dataObj = {};
		
		dataObj.data_start = start;
		dataObj.data_end = end;
		dataObj.tipo = tipo;
		
			$.ajax({
		type: "POST",
		url: "scadenzario.do?action=esporta_campioni_scadenza",
		data: dataObj,
		dataType: "json",
		//if received a response from the server
		success: function( data, textStatus) {
			  if(data.success)
			  {  
				  pleaseWaitDiv.modal('hide');
				 
				   callAction("scadenzario.do?action=download_scadenzario");
				
			  }else{
				  
				pleaseWaitDiv.modal('hide');
				$('#myModalErrorContent').html(data.messaggio);
				$('#myModalError').removeClass();	
				$('#myModalError').addClass("modal modal-danger");	  
				$('#report_button').hide();
				$('#visualizza_report').hide();		
				$('#myModalError').modal('show');			
			
			  }
		},

		error: function( data, textStatus) {
			
			pleaseWaitDiv.modal('hide');
			  	$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");	  
				$('#report_button').show();
				$('#visualizza_report').show();
					$('#myModalError').modal('show');

		}
		});
		   
		
	}
	
	
function esportaAttrezzatureScadenzario(){	
		
		pleaseWaitDiv = $('#pleaseWaitDialog');
		pleaseWaitDiv.modal();
		
		var start = $('#data_start').val();
		var end = $('#data_end').val();	
		var tipo_data = $('#tipo_data').val();
		
		var dataObj = {};
		
		dataObj.data_start = start;
		dataObj.data_end = end;
		dataObj.tipo_data = tipo_data;
		
			$.ajax({
		type: "POST",
		url: "listaAttrezzature.do?action=esporta_attrezzature_scadenza",
		data: dataObj,
		dataType: "json",
		//if received a response from the server
		success: function( data, textStatus) {
			  if(data.success)
			  {  
				  pleaseWaitDiv.modal('hide');
				 
				   callAction("listaAttrezzature.do?action=download_scadenzario");
				
			  }else{
				  
				pleaseWaitDiv.modal('hide');
				$('#myModalErrorContent').html(data.messaggio);
				$('#myModalError').removeClass();	
				$('#myModalError').addClass("modal modal-danger");	  
				$('#report_button').hide();
				$('#visualizza_report').hide();		
				$('#myModalError').modal('show');			
			
			  }
		},

		error: function( data, textStatus) {
			
			pleaseWaitDiv.modal('hide');
			  	$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");	  
				$('#report_button').show();
				$('#visualizza_report').show();
					$('#myModalError').modal('show');

		}
		});
		   
		
	}



function firmaVerbale(id_verbale){
	
	
	pleaseWaitDiv = $('#pleaseWaitDialog');
	pleaseWaitDiv.modal();
	
	var pin = $('#pin').val();
	var controfirma = $('#controfirma').val();
	var scheda_tecnica = $('#scheda_tecnica').val();
	
	var dataObj = {};
	
	dataObj.pin = pin;
	dataObj.controfirma = controfirma;
	dataObj.scheda_tecnica = scheda_tecnica;
	
		$.ajax({
	type: "POST",
	url: "gestioneVerbale.do?action=firma_verbale&idVerbale="+id_verbale,
	data: dataObj,
	dataType: "json",
	//if received a response from the server
	success: function( data, textStatus) {
		  if(data.success)
		  {  
			  
			  pleaseWaitDiv.modal('hide');
				$('#modalErrorDiv').html(data.messaggio);
				$('#myModalError').removeClass();	
				$('#myModalError').addClass("modal modal-success");	  
				$('#report_button').hide();
				$('#visualizza_report').hide();		
				$('#myModalError').modal('show');
				
				$('#myModalError').on('hidden.bs.modal',function(){
					location.reload();
				});
			
		  }else{
			  
			pleaseWaitDiv.modal('hide');
			$('#modalErrorDiv').html(data.messaggio);
			$('#myModalError').removeClass();	
			$('#myModalError').addClass("modal modal-danger");	  
			$('#report_button').hide();
			$('#visualizza_report').hide();		
			$('#myModalError').modal('show');			
		
		  }
	},

	error: function( data, textStatus) {
		
		pleaseWaitDiv.modal('hide');
		$('#modalErrorDiv').html(textStatus);
		  	$('#myModalError').removeClass();
			$('#myModalError').addClass("modal modal-danger");	  
			$('#report_button').show();
			$('#visualizza_report').show();
				$('#myModalError').modal('show');

	}
	});
	   
	
}



function submitInfoVerbaleEmail(){
	
	  var form = $('#formEmail')[0]; 
	  var formData = new FormData(form);

      $.ajax({
    	  type: "POST",
    	  url: "gestioneInfoVerbale.do?action=invia_info",
    	  data: formData,
    	  //dataType: "json",
    	  contentType: false, // NEEDED, DON'T OMIT THIS (requires jQuery 1.6+)
    	  processData: false, // NEEDED, DON'T OMIT THIS
    	  //enctype: 'multipart/form-data',
    	  success: function( data, textStatus) {

    		  if(data.success)
    		  { 
    			  $('#report_button').hide();
						$('#visualizza_report').hide();
    			  $('#myModalErrorContent').html(data.messaggio);
    			  	$('#myModalError').removeClass();
    				$('#myModalError').addClass("modal modal-success");
    				$('#myModalError').modal('show');
    				

    		  }else{
    			  $('#myModalErrorContent').html("Errore nell'invio delle informazioni!");
    			  	$('#myModalError').removeClass();
    				$('#myModalError').addClass("modal modal-danger");	  
    				$('#report_button').show();
						$('#visualizza_report').show();
						$('#myModalError').modal('show');
						

    		  }
    	  },

    	  error: function(jqXHR, textStatus, errorThrown){
    	
    		  $('#myModalErrorContent').html(data.messaggio);
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");
				$('#report_button').show();
				$('#visualizza_report').show();
				$('#myModalError').modal('show');
				
			
    	  }
      });
	
}



function aggiungiVerbale(id_intervento){

	var str1=$('#tecnici').val();

	
	//var str2="&note="+$('#noteVerbale').val();
	//var str3=$('#select2').val();
	var listCatVer='';
	$('.categoriaTipiRow').each(function(index, item){
		listCatVer+="&categoriaTipo="+item.attributes[2].value;
	});
	var skTecObb='';
	$('.skTecObb').each(function(index, item){
		if($(this)[0].checked){
			//skTecObb+="&schedaTecnica="+$(this).closest('tr').prop('id');
			skTecObb+="&schedaTecnica="+1;
		}else{
			skTecObb+="&schedaTecnica="+0;
		}
			
	});
	
	var noteVerb='';
	
	var attrezzature='';
	
	var sedi = '';
	
	var esercenti = '';
	
	var eff_ver = '';
	
	var tipo_ver = '';
	
	var descrizione_util = '';
	
	if(listCatVer==""){
		$('#empty').html("Devi inserire almeno un 'Tipo Verifica' per poter creare l'intervento!"); 
	}else if(str1!= null){
		//var dataArr={"tecnico":str};
		var table = $('#tabVerifica').DataTable();
		
		var data = table.rows().data();
		data.each(function (value, index) {
			
			var x = stripHtml(value[2]);
			
			noteVerb+="&note="+value[10];
			sedi+= "&sedi="+value[5];
			attrezzature+='&attrezzature='+value[4];
			esercenti+='&esercenti='+value[6];
			descrizione_util+='&descrizione_util='+value[7];
			eff_ver+='&effettuazione_verifica='+value[8];
			tipo_ver+='&tipo_verifica='+value[9];
		 });
	            
		
		pleaseWaitDiv = $('#pleaseWaitDialog');
		pleaseWaitDiv.modal();
	    
		$.ajax({
			type: "POST",
			url: "gestioneIntervento.do?action=aggiungi_verbale&id_intervento="+id_intervento,
			//data: "dataIn="+JSON.stringify(dataArr),
			//data: "dataIn="+str1,
			data : "tecnico="+str1 +listCatVer+skTecObb+noteVerb+attrezzature+sedi+esercenti+eff_ver+tipo_ver+descrizione_util,
			//	'id='+ encodeURIComponent(id) + '&name='+ encodeURIComponent(name)
			dataType: "json",
			success: function( data, textStatus) {
				pleaseWaitDiv.hide();
				if(data.success){ 
					location.reload();
					

					$('#myModal').modal('hide')
//	          		$('#errorMsg').html("<h3 class='label label-primary' style=\"color:green\">"+textStatus+"</h3>");
					var table = $('#tabPM').DataTable();

					//verbale = data.verbale;
		
				}else{
					$('#modalErrorDiv').html(data.messaggio+' '+data.dettaglio);
					$('#myModalError').removeClass();
					$('#myModalError').addClass("modal modal-danger");
					$('#myModalError').modal('show');
				}
				pleaseWaitDiv.modal('hide');
			},

			error: function(jqXHR, textStatus, errorThrown){
	          
				$('#errorMsg').html("<h3 class='label label-danger'>"+textStatus+"</h3>");
				//callAction('logout.do');
				pleaseWaitDiv.modal('hide');
			}
		});
	}else{
		$('#empty').html("Il campo 'Tecnico Verificatore' non pu&ograve; essere vuoto"); 
	}
	  	   
}




function eliminaVerbale(id_verbale, id_intervento){
	pleaseWaitDiv = $('#pleaseWaitDialog');
	pleaseWaitDiv.modal();
	
	dataObj ={}

		$.ajax({
	type: "POST",
	url: "gestioneIntervento.do?action=elimina_verbale&idVerbale="+id_verbale+"&id_intervento="+id_intervento,
	data: dataObj,
	dataType: "json",
	//if received a response from the server
	success: function( data, textStatus) {
		  if(data.success)
		  {  
			  $('#myModalYesOrNo').modal('hide');
			  pleaseWaitDiv.modal('hide');
				$('#modalErrorDiv').html(data.messaggio);
				$('#myModalError').removeClass();	
				$('#myModalError').addClass("modal modal-success");	  
				$('#report_button').hide();
				$('#visualizza_report').hide();		
				$('#myModalError').modal('show');
				
				$('#myModalError').on('hidden.bs.modal',function(){
					location.reload();
				});
			
		  }else{
			  
			pleaseWaitDiv.modal('hide');
			$('#modalErrorDiv').html(data.messaggio);
			$('#myModalError').removeClass();	
			$('#myModalError').addClass("modal modal-danger");	  
			$('#report_button').hide();
			$('#visualizza_report').hide();		
			$('#myModalError').modal('show');			
		
		  }
	},

	error: function( data, textStatus) {
		
		pleaseWaitDiv.modal('hide');
		$('#modalErrorDiv').html(textStatus);
		  	$('#myModalError').removeClass();
			$('#myModalError').addClass("modal modal-danger");	  
			$('#report_button').show();
			$('#visualizza_report').show();
				$('#myModalError').modal('show');

	}
	});
	   
}

function controllaAssociati(ver, id_campione){
	

	var verificatori = ver.verificatori_json;
	 var opt = [];
	 
	opt.push("<option value=''></<option>");				 
	 
	var ids = "";
	 for(var i = 0;i<verificatori.length;i++){	
		 
		 var campioni = verificatori[i].listaCampioni;
		 
		 if(campioni.length>0){
			
			 for(var j = 0;j<campioni.length;j++){
				 
				 if(campioni[j].id == id_campione){
					 opt.push("<option value='"+verificatori[i].id+"' selected>"+verificatori[i].nome+ " " + verificatori[i].cognome+"</option>");
					 ids = ids +verificatori[i].id+";";
					 
					 $('#verificatori_associa_mod').val(ids);
				 }
			 }
		 }
		 
		 if(!ids.includes(verificatori[i].id)){
			 opt.push("<option value='"+verificatori[i].id+"' >"+verificatori[i].nome+ " " + verificatori[i].cognome+"</option>");
		 }
				 
		
	 }
	
	
	 $('#verificatori_mod').html(opt);
	 $('#verificatori_dtl').html(opt);
	 
}


function submitAllegatoMinistero(){
	
	  var form = $('#formAllegatiMinistero')[0]; 
	  var formData = new FormData(form);

      $.ajax({
    	  type: "POST",
    	  url: "gestioneListaVerbali.do?action=upload_allegato_ministero",
    	  data: formData,
    	  //dataType: "json",
    	  contentType: false, // NEEDED, DON'T OMIT THIS (requires jQuery 1.6+)
    	  processData: false, // NEEDED, DON'T OMIT THIS
    	  //enctype: 'multipart/form-data',
    	  success: function( data, textStatus) {

    		  if(data.success)
    		  { 
    			  $('#report_button').hide();
						$('#visualizza_report').hide();
    			  $('#modalErrorDiv').html(data.messaggio);
    			  	$('#myModalError').removeClass();
    				$('#myModalError').addClass("modal modal-success");
    				$('#myModalError').modal('show');
    				$('#myModalError').on('hidden.bs.modal', function(){
    					location.reload()
    				});

    		  }else{
    			  $('#modalErrorDiv').html("Errore nel caricamento del file!");
    			  	$('#myModalError').removeClass();
    				$('#myModalError').addClass("modal modal-danger");	  
    				$('#report_button').show();
						$('#visualizza_report').show();
						$('#myModalError').modal('show');
						

    		  }
    	  },

    	  error: function(jqXHR, textStatus, errorThrown){
    	
    		  $('#myModalErrorContent').html(data.messaggio);
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");
				$('#report_button').show();
				$('#visualizza_report').show();
				$('#myModalError').modal('show');
				
			
    	  }
      });
	
}

function eliminaAllegatoMise(id_allegato){
	
	
	
	pleaseWaitDiv = $('#pleaseWaitDialog');
	pleaseWaitDiv.modal();
	
	dataObj ={}

		$.ajax({
	type: "POST",
	url: "gestioneListaVerbali.do?action=elimina_allegato_ministero&id_allegato="+id_allegato,
	data: dataObj,
	dataType: "json",
	//if received a response from the server
	success: function( data, textStatus) {
		  if(data.success)
		  {  
			  $('#myModalYesOrNo').modal('hide');
			  pleaseWaitDiv.modal('hide');
				$('#modalErrorDiv').html(data.messaggio);
				$('#myModalError').removeClass();	
				$('#myModalError').addClass("modal modal-success");	  
				$('#report_button').hide();
				$('#visualizza_report').hide();		
				$('#myModalError').modal('show');
				
				$('#myModalError').on('hidden.bs.modal',function(){
					location.reload();
				});
			
		  }else{
			  
			pleaseWaitDiv.modal('hide');
			$('#modalErrorDiv').html(data.messaggio);
			$('#myModalError').removeClass();	
			$('#myModalError').addClass("modal modal-danger");	  
			$('#report_button').hide();
			$('#visualizza_report').hide();		
			$('#myModalError').modal('show');			
		
		  }
	},

	error: function( data, textStatus) {
		
		pleaseWaitDiv.modal('hide');
		$('#modalErrorDiv').html(textStatus);
		  	$('#myModalError').removeClass();
			$('#myModalError').addClass("modal modal-danger");	  
			$('#report_button').show();
			$('#visualizza_report').show();
				$('#myModalError').modal('show');

	}
	});
	   
	
}

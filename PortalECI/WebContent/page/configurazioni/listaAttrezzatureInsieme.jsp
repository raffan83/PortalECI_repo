<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page import="it.portalECI.DTO.UtenteDTO"%>
<%@ taglib uri="/WEB-INF/tld/utilities" prefix="utl" %>
<%
	UtenteDTO user = (UtenteDTO)request.getSession().getAttribute("userObj");
	request.setAttribute("user",user);
%>


    <div class="row">
<div class="col-lg-12">
<a class="btn btn-primary pull-right" onClick="modalNuovaAttrezzatura(true, ${id_insieme})">Nuova Attrezzatura Insieme</a>
</div>
</div><br>

    <div class="row">
<div class="col-lg-12">


<table id="tabAttrezzatureInsieme" class="table table-bordered table-hover dataTable table-striped" role="grid" width="100%">
							
 														<thead>
 														
 														
 															<tr class="active"> 
 																<th>ID</th>
 															<th>Matricola Inail</th>
 																<th>Numero di fabbrica</th>
 																<th>Descrizione</th>
 															
 																<th>Anno di costruzione</th>
 																<th>Fabbricante</th>
 															
 																<th>Modello</th>
 																<th>Tipo Attrezzatura</th>
 																
 																<th>Tipo Attrezzatura GVR</th>
 																<th>ID specifica</th>
 																<th>Marcatura</th>
 																<th>N.ID ON.</th>
 																<th>Codici Milestone</th>
 																<th>Note tecniche</th>
 																<th>Note generiche</th>
 																<th>
 																 	
 																</th>
 															</tr>
 															 															
 														</thead>
 
 														<tbody> 
  															<c:forEach items="${listaAttrezzatureInsieme}" var="attrezzatura">  															
 																<tr role="row">
 																<td>${attrezzatura.id }</td>
																	<td>${attrezzatura.matricola_inail }</td>
 	<td>${attrezzatura.numero_fabbrica }</td>

 	<td>${attrezzatura.descrizione }</td>
 	
 	<td>${attrezzatura.anno_costruzione }</td>
  	<td>${attrezzatura.fabbricante }</td>
 	<td>${attrezzatura.modello }</td>

 	<td>${attrezzatura.tipo_attrezzatura }</td>
 	<td>${attrezzatura.tipo_attrezzatura_GVR }</td>
 	<td>${attrezzatura.ID_specifica }</td>
	<td>${attrezzatura.marcatura }</td>

 
 	<td>${attrezzatura.n_id_on }</td>

 	<td>${attrezzatura.codice_milestone }</td>
 	<td>${attrezzatura.note_tecniche }</td> 	
 	<td>${attrezzatura.note_generiche }</td> 
 	<td>
 	
 	<c:if test="${user.checkPermesso('MODIFICA_ATTREZZATURE') }">	
																 	<a class="btn btn-warning" onClick="modalModificaAttrezzaturaInsieme('${attrezzatura.id }','${attrezzatura.matricola_inail }','${attrezzatura.numero_fabbrica }','${attrezzatura.tipo_attivita }','${attrezzatura.descrizione }','${attrezzatura.id_cliente }','${attrezzatura.id_sede }',
																 	'${attrezzatura.data_verifica_funzionamento }','${attrezzatura.data_prossima_verifica_funzionamento }','${attrezzatura.data_verifica_integrita }','${attrezzatura.data_prossima_verifica_integrita }','${attrezzatura.data_verifica_interna }','${attrezzatura.data_prossima_verifica_interna }',
																 	'${attrezzatura.anno_costruzione }','${attrezzatura.fabbricante }','${attrezzatura.modello }','${attrezzatura.settore_impiego }','${utl:escapeJS(attrezzatura.note_tecniche)}','${utl:escapeJS(attrezzatura.note_generiche)}','${attrezzatura.obsoleta }',
																 	'${attrezzatura.tipo_attrezzatura }','${attrezzatura.tipo_attrezzatura_GVR }','${attrezzatura.ID_specifica }','${attrezzatura.sogg_messa_serv_GVR }',
																 	'${attrezzatura.n_panieri_idroestrattori }','${attrezzatura.marcatura }','${attrezzatura.n_id_on }','${attrezzatura.data_scadenza_ventennale }', '${attrezzatura.codice_milestone }', ${attrezzatura.id_insieme })"><i class="fa fa-edit"></i></a>
																 	</c:if>
 </td>
																</tr>
															</c:forEach>
 														</tbody>
 											
 													
 														
 													</table>  












</div>
</div>


<!-- 						  <div id="myModalAllegati" class="modal fade" role="dialog" aria-labelledby="myLargeModalLabel">
    						<div class="modal-dialog" role="document">
    							<div class="modal-content">
     								<div class="modal-header">
        								<button type="button" class="close" onClick="$('#myModalAllegati').modal('hide')" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        								<h4 class="modal-title" id="myModalLabel">Download </h4>
      								</div>
       								<div class="modal-body">
										<div id="content_allegati">				
										</div>	   
  										<div id="empty2" class="label label-danger testo12"></div>
  		 							</div>
      								<div class="modal-footer">
        								<button type="button" class="btn btn-primary" onClick="$('#myModalAllegati').modal('hide')">Chiudi</button>
      								</div>
    							</div>
  							</div>
						</div>  -->

<link rel="stylesheet" href="https://cdn.datatables.net/select/1.2.2/css/select.dataTables.min.css">

<script src="https://cdn.datatables.net/select/1.2.2/js/dataTables.select.min.js"></script>
	<script type="text/javascript">
	
	
  		function modalAllegati(id_verbale, type){
  			
  			dataString = "action=allegati_verbale_cliente&id_verbale=" + id_verbale + "&type=" + type;
	 

	 	exploreModal("gestioneListaVerbali.do", dataString, '#content_allegati');
  			
	 	
	 	$('#myModalAllegati').modal()
  		}

  		
  		
  		function modalModificaAttrezzaturaInsieme(id_attrezzatura, matricola_inail, numero_fabbrica, tipo_attivita, descrizione, id_cliente, id_sede,
  				data_verifica_funzionamento, data_prossima_verifica_funzionamento,data_verifica_integrita, data_prossima_verifica_integrita, data_verifica_interna, data_prossima_verifica_interna,
  				anno_costruzione, fabbricante, modello, settore_impiego, note_tecniche, note_generiche, obsoleta, tipo_attrezzatura, tipo_attrezzatura_gvr,
  				id_specifica, sogg_messa_serv_GVR, n_panieri_idroestrattori, marcatura, n_id_on, data_scadenza_ventennale, codice_milestone, id_insieme){

  			$('#id_insieme_mod').val(id_insieme);
  			
  			$('#id_attrezzatura_ins').val(id_attrezzatura);
  			$('#matricola_inail_mod_ins').val(matricola_inail);
  			$('#numero_fabbrica_mod_ins').val(numero_fabbrica);
  			$('#tipo_attivita_mod_ins').val(tipo_attivita);
  			$('#tipo_attivita_mod_ins').change();
  			

  			
  			
  			if(id_specifica!=null && id_specifica!=''){
  				$('#descrizione_mod_ins').val(tipo_attivita+"_"+descrizione+"_"+id_specifica+"@"+codice_milestone);
  			}else{
  				$('#descrizione_mod_ins').val(tipo_attivita+"_"+descrizione+"_@"+codice_milestone);	
  			}
  			
  			$('#descrizione_mod_ins').change();
  			$('#cliente_mod_ins').val(id_cliente);
  			$('#cliente_mod_ins').change();
  			if(id_sede!=0){
  				$('#sede_mod_ins').val(id_sede+"_"+id_cliente);	
  			}else{
  				$('#sede_mod_ins').val(0);
  			}
  			
  			$('#sede_mod_ins').change();
  			$('#anno_costruzione_mod').val(anno_costruzione);
  			$('#fabbricante_mod_ins').val(fabbricante);
  			$('#modello_mod_ins').val(modello);
  			
  			if(id_insieme!=null && id_insieme!=''){
  					
  				$('#settore_impiego_content_mod_ins').hide();
  				$('#cliente_content_mod_ins').hide();
  				$('#tipo_attivita_ins').attr("disabled", true);
  				
  			}else{
  				$('#settore_impiego_mod_ins').val(settore_impiego);
  				$('#settore_impiego_mod_ins').change();
  				
  				$('#settore_impiego_content_mod_ins').show();
  				$('#cliente_content_mod_ins').show();
  				$('#tipo_attivita_ins').attr("disabled", false);
  			}
  			

  			$('#note_tecniche_mod_ins').val(note_tecniche);
  			$('#note_generiche_mod_ins').val(note_generiche);	
  			
  			
  			if(id_insieme!=null && id_insieme!=''){
  			
  				$('#data_scadenza_ventennale_content_mod_ins').hide();
  				$('#data_verifica_funzionamento_content_mod_ins').hide();
  				$('#data_prossima_verifica_funzionamento_content_mod_ins').hide();
  				$('#data_verifica_integrita_content_mod_ins').hide();
  				$('#data_prossima_verifica_integrita_content_mod_ins').hide();
  				$('#data_verifica_interna_content_mod_ins').hide();
  				$('#data_prossima_verifica_interna_content_mod_ins').hide();
  				$('#matricola_inail_ins').attr("required", false);
  			
  			}else{
  				
  				$('#data_scadenza_ventennale_content_mod_ins').show();
  				$('#data_verifica_funzionamento_content_mod_ins').show();
  				$('#data_prossima_verifica_funzionamento_content_mod_ins').show();
  				$('#data_verifica_integrita_content_mod_ins').show();
  				$('#data_prossima_verifica_integrita_content_mod_ins').show();
  				$('#data_verifica_interna_content_mod_ins').show();
  				$('#data_prossima_verifica_interna_content_mod_ins').show();
  				$('#matricola_inail_ins').attr("required", false);
  				
  				if(data_verifica_funzionamento!=null && data_verifica_funzionamento!= ''){
  					$('#data_verifica_funzionamento_mod').val(Date.parse(data_verifica_funzionamento).toString("dd/MM/yyyy"));	
  				}
  				if(data_prossima_verifica_funzionamento!=null && data_prossima_verifica_funzionamento!= ''){
  					$('#data_prossima_verifica_funzionamento_mod').val(Date.parse(data_prossima_verifica_funzionamento).toString("dd/MM/yyyy"));	
  				}
  				if(data_verifica_integrita!=null && data_verifica_integrita!= ''){
  					$('#data_verifica_integrita_mod').val(Date.parse(data_verifica_integrita).toString("dd/MM/yyyy"));	
  				}
  				if(data_prossima_verifica_integrita!=null && data_prossima_verifica_integrita!= ''){
  					$('#data_prossima_verifica_integrita_mod').val(Date.parse(data_prossima_verifica_integrita).toString("dd/MM/yyyy"));	
  				}
  				if(data_verifica_interna!=null && data_verifica_interna!= ''){
  					$('#data_verifica_interna_mod').val(Date.parse(data_verifica_interna).toString("dd/MM/yyyy"));	
  				}
  				if(data_prossima_verifica_interna!=null && data_prossima_verifica_interna!= ''){
  					$('#data_prossima_verifica_interna_mod').val(Date.parse(data_prossima_verifica_interna).toString("dd/MM/yyyy"));	
  				}
  				
  				if(data_scadenza_ventennale!=null && data_scadenza_ventennale!= ''){
  					$('#data_scadenza_ventennale_mod').val(Date.parse(data_scadenza_ventennale).toString("dd/MM/yyyy"));	
  				}
  				
  			}
  			
  			$('#id_specifica_mod_ins').val(id_specifica);	
  			
  			$('#data_prossima_verifica_funzionamento_mod').datepicker({
  					format: "dd/MM/yyyy"
  			});
  			

  			$('#tipo_attrezzatura_mod_ins').val(tipo_attrezzatura);
  			$('#tipo_attrezzatura_mod_ins').change();

  			$('#tipo_attrezzatura_gvr_mod_ins').val(tipo_attrezzatura_gvr);
  			$('#tipo_attrezzatura_gvr_mod_ins').change();
  			
  			
  			if(id_insieme!=null && id_insieme!=''){
  				
  				$('#sogg_messa_serv_GVR_content_mod').hide();
  				$('#n_panieri_idroestrattori_content_mod').hide();
  			}else{
  				$('#sogg_messa_serv_GVR_mod').val(sogg_messa_serv_GVR);
  				$('#sogg_messa_serv_GVR_mod').change();
  				
  				$('#n_panieri_idroestrattori_mod').val(n_panieri_idroestrattori);
  				$('#sogg_messa_serv_GVR_content_mod').show();
  				$('#n_panieri_idroestrattori_content_mod').show();
  			}

  			
  			$('#marcatura_mod_ins').val(marcatura);
  			$('#marcatura_mod_ins').change();

  			$('#n_id_on_mod_ins').val(n_id_on);
  			
  			if(obsoleta==0){
  				$('#rendi_obsoleta').addClass("btn-danger");
  				$('#rendi_obsoleta').show();
  				$('#rendi_non_obsoleta').hide();
  			}else{
  				$('#rendi_non_obsoleta').addClass("btn-success");
  				$('#rendi_obsoleta').hide();
  				$('#rendi_non_obsoleta').show();
  			}
  			
  			

  			$('#modalModificaAttrezzaturaInsieme').modal();
  			
  		}
	
	
	$(document).ready(function() {
		console.log("tt")
		
		var coldef = [
			{ responsivePriority: 1, targets: 0 },
            { responsivePriority: 3, targets: 2 },
            { responsivePriority: 4, targets: 3 },
            { responsivePriority: 2, targets: 6 },
            { responsivePriority: 2, targets: 8, type:"date-eu" },
            { orderable: false, targets: 6 },
        ]
		
		if(${user.checkRuolo('CLVAL')}){
			
			coldef = [];
			
		}
		
		
		
		table = $('#tabAttrezzatureInsieme').DataTable({
			language: {
     			emptyTable : 	"Nessun dato presente nella tabella",
     			info	:"Vista da _START_ a _END_ di _TOTAL_ elementi",
     			infoEmpty:	"Vista da 0 a 0 di 0 elementi",
     			infoFiltered:	"(filtrati da _MAX_ elementi totali)",
     			infoPostFix:	"",
     			infoThousands:	".",
     			lengthMenu:	"Visualizza _MENU_ elementi",
     			loadingRecords:	"Caricamento...",
     			processing:	"Elaborazione...",
     			search:	"Cerca:",
     			zeroRecords	:"La ricerca non ha portato alcun risultato.",
     			paginate:	{
	        			first:	"Inizio",
	        			previous:	"Precedente",
	        			next:	"Successivo",
	        			last:	"Fine",
     			},
     			aria:	{
	        			srtAscending:	": attiva per ordinare la colonna in ordine crescente",
	        			sortDescending:	": attiva per ordinare la colonna in ordine decrescente",
     			}
   		},
   		pageLength: 100,
     		paging: true, 
     		ordering: true,
     		info: true, 
     		searchable: false, 
     		targets: 0,
     		responsive: true,
     		scrollX: false,
 			order: [[ 0, "desc" ]],
     		//columnDefs: coldef
                  	     
   	});
		
		table.buttons().container().appendTo( '#tabAttrezzatureInsieme_wrapper .col-sm-6:eq(1)' );
    		       	           	           	 	

		$('#tabAttrezzatureInsieme thead th').each( function () {
			var title = $('#tabVerbali thead th').eq( $(this).index() ).text();
			$(this).append( '<div><input class="inputsearchtable" style="width:100%" type="text" /></div>');
		} );
			
		$('.inputsearchtable').on('click', function(e){
				e.stopPropagation();    
		});

		// DataTable
			table = $('#tabAttrezzatureInsieme').DataTable();

		// Apply the search
		table.columns().eq( 0 ).each( function ( colIdx ) {
			$( 'input', table.column( colIdx ).header() ).on( 'keyup', function () {
   			table.column( colIdx ).search( this.value ).draw();		
			} );
		} ); 
		
		table.columns.adjust().draw();

		$('#tabAttrezzatureInsieme').on( 'page.dt', function () {
			$('.customTooltip').tooltipster({
	        	theme: 'tooltipster-light'
	    	});
	  	} );
		
	});
	
	
	$("#descrizione_mod_ins").change(function() {

  		
	  	
		$('#n_panieri_idroestrattori_mod_ins').attr("disabled",true);
		$("#tipo_attrezzatura_gvr_mod_ins").prop("disabled", true);
		
		if($(this).val()!=null){
			 var gruppo = $(this).val().split('_')[0];
			 
			  var descrizione = $(this).val().split("_")[1];
			  

			  if($(this).val().split("_").length>2){
				  var id_spec = $(this).val().split("_")[2].split("@")[0];
			  }else{
				  var id_spec = "";
			  }
			  
			  var codice_milestone = $(this).val().split("@")[1];
			  //var id_spec = $(this).val().split("_")[2];

			  var opt_tipo_attr =[];
			  var opt_tipo_attr_gvr =[];	
			  
			  
			  if(descrizione.startsWith('Idroestrattor')){
				  $('#n_panieri_idroestrattori_mod_ins').attr("disabled",false);
			  }
			  	
			  $('#id_specifica_mod_ins').val(id_spec);
			  
			  
				if(gruppo == "GVR"){
					
					if(descrizione.startsWith("Recipienti")){
						opt_tipo_attr.push('<option value="a1">a1</option>');
						opt_tipo_attr.push('<option value="a2">a2</option>');
						opt_tipo_attr.push('<option value="a5">a5</option>');
						opt_tipo_attr.push('<option value="b1">b1</option>');
						opt_tipo_attr.push('<option value="b2">b2</option>');
						
						opt_tipo_attr_gvr.push('<option value="a1">a1</option>');

					}
					else if(descrizione.startsWith("Generatori")){
						opt_tipo_attr.push('<option value="b3">b3</option>');
						
						if(descrizione.startsWith("Generatori acqua")){
							
							
							opt_tipo_attr_gvr.push('<option value="a3">a3</option>');

						}else{
							opt_tipo_attr_gvr.push('<option value="a2">a2</option>');
						}
						

						
						
					}			
					else if(descrizione.startsWith("Forni")){
						opt_tipo_attr.push('<option value="a1">a1</option>');
						
						opt_tipo_attr_gvr.push('<option value="a6">a6</option>');
					

					}
					else if(descrizione.startsWith("Tubazioni")){
						opt_tipo_attr.push('<option value="a3">a3</option>');				
						opt_tipo_attr.push('<option value="a4">a4</option>');
						opt_tipo_attr.push('<option value="b4">b4</option>');
						opt_tipo_attr.push('<option value="b5">b5</option>');
						
						opt_tipo_attr_gvr.push('<option value="a4">a4</option>');
					}
					else if(descrizione.startsWith("Insieme")){
						
						opt_tipo_attr.push('<option value="a1">a1</option>');
						opt_tipo_attr.push('<option value="a2">a2</option>');
						opt_tipo_attr.push('<option value="a5">a5</option>');
						opt_tipo_attr.push('<option value="b1">b1</option>');
						opt_tipo_attr.push('<option value="b2">b2</option>');
						
						opt_tipo_attr_gvr.push('<option value="b">b</option>');
					}
					
					else{
						opt_tipo_attr.push('<option value=""></option>');
						
						opt_tipo_attr_gvr.push('<option value="a5">a5</option>');
					}

					$("#tipo_attrezzatura_gvr_mod_ins").prop("disabled", true);
				}
				
				else if(gruppo == "SP"){
					
					if(descrizione.startsWith("Scale")){
						opt_tipo_attr.push('<option value="a">a</option>');

					//	opt_tipo_attr_gvr.push('<option value=""></option>');				


						
					}
					else if(descrizione.startsWith("Ponti mobili")){
						
						if(descrizione.includes("azionamento")){
							
							opt_tipo_attr.push('<option value="b">b</option>');

						}else{
							opt_tipo_attr.push('<option value="c">c</option>');
						}
						

					}			
					else if(descrizione.startsWith("Carri") || descrizione.startsWith("Ponti sospesi")){
						
						opt_tipo_attr.push('<option value="d">d</option>');				
					
						
					}
					else if(descrizione.startsWith("Piattaforme")){
						opt_tipo_attr.push('<option value="e">e</option>');				

					}			
					else{
						opt_tipo_attr.push('<option value="f">f</option>');				
					}

				}

				else if(gruppo == "SC"){
							
					
					if(descrizione.startsWith("Carrelli")){
						opt_tipo_attr.push('<option value="c">c</option>');
						
				//		opt_tipo_attr_gvr.push('<option value="a1">a1</option>');
						
				
						
					}
					else if(descrizione.startsWith("Idroestrattori")){
						
					
						opt_tipo_attr.push('<option value="d1">d1</option>');
						opt_tipo_attr.push('<option value="d2">d2</option>');
						opt_tipo_attr.push('<option value="d3">d3</option>');
					
					}			
					else if(descrizione.startsWith("Gru")){
						
						if(descrizione.includes("braccio")||descrizione.includes("trasferibile")||descrizione.includes("torre")||descrizione.includes("Derrick")){
							opt_tipo_attr.push('<option value="b1">b1</option>');
							opt_tipo_attr.push('<option value="b2">b2</option>');
							opt_tipo_attr.push('<option value="b3">b3</option>');				
							
						}else{
							opt_tipo_attr.push('<option value="a1">a1</option>');
							opt_tipo_attr.push('<option value="a2">a2</option>');
							opt_tipo_attr.push('<option value="a3">a3</option>');
							opt_tipo_attr.push('<option value="a4">a4</option>');
						}
						
					}


					
					else{
						opt_tipo_attr.push('<option value="b1">b1</option>');
						opt_tipo_attr.push('<option value="b2">b2</option>');
						opt_tipo_attr.push('<option value="b3">b3</option>');	

					}
					
					
					
					
					
				}
				
				$('#codice_milestone_mod_ins').val(codice_milestone);
				 $("#tipo_attrezzatura_mod_ins").prop("disabled", false);
				  $('#tipo_attrezzatura_mod_ins').html(opt_tipo_attr);
					$("#tipo_attrezzatura_mod_ins").change();  
					
					 
					  $('#tipo_attrezzatura_gvr_mod_ins').html(opt_tipo_attr_gvr);
						$("#tipo_attrezzatura_gvr_mod_ins").change();  
						
						
						
		}

		 
		});
	
	
	$("#tipo_attivita_mod_ins").change(function() {
	

$('#tipo_attrezzatura_gvr_mod_ins').attr("disabled", true);
$('#sogg_messa_serv_GVR_mod_ins').attr("disabled", true);
$('#data_scadenza_ventennale_mod_ins').addClass("disabled");

  if ($(this).data('options') == undefined) 
  {
    /*Taking an array of all options-2 and kind of embedding it on the select1*/
    $(this).data('options', $('#descrizione_mod_temp option').clone());
  }
  
  var gruppo = $(this).val();
 
  var options = $(this).data('options');

  var opt=[];

//  opt.push("<option value = 0>Non Associate</option>");

   for(var  i=0; i<options.length;i++)
   {
	var str=options[i].value; 

	//if(str.substring(str.indexOf("_")+1,str.length)==id)
	if(str.split("_")[0] == gruppo)	
	{
		opt.push(options[i]);
	}   
   }
 $("#descrizione_mod_ins").prop("disabled", false);
 
  $('#descrizione_mod_ins').html(opt);

	$('#descrizione_mod_ins').change();  
	
	var settore_opt = [];
	
			
	if(gruppo == "SP" || gruppo == "GVR"){
		settore_opt.push('<option value="N.A.">N.A.</option>');
		
		if(gruppo == 'GVR'){
			$('#tipo_attrezzatura_gvr_mod_ins').attr("disabled", true);
			$('#sogg_messa_serv_GVR_mod_ins').attr("disabled", false);	
			$('#n_id_on_ins').attr("disabled", false);	
		}else{
			$('#data_scadenza_ventennale_mod_ins').removeClass("disabled");
			$('#n_id_on_ins').attr("disabled", true);	
		}
		
	}else if(gruppo == "SC"){
		settore_opt.push('<option value="regolare">Regolare</option>');
		settore_opt.push('<option value="costruzioni">Costruzioni</option>');
		settore_opt.push('<option value="siderurgico">Siderurgico</option>');
		settore_opt.push('<option value="estrattivo">Estrattivo</option>');
		settore_opt.push('<option value="rorturale">Portuale</option>');		
		$('#n_id_on_ins').attr("disabled", true);	
		

	}

});
	
	
	</script>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page import="it.portalECI.DTO.UtenteDTO"%>
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
 																<td></td>
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
 	<td>${attrezzatura.n_panieri_idroestrattori }</td>
 
 	<td>${attrezzatura.n_id_on }</td>

 	<td>${attrezzatura.codice_milestone }</td>
 	<td>${attrezzatura.note_tecniche }</td> 	
 	<td>${attrezzatura.note_generiche }</td> 
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

	
	
	$(document).ready(function() {
		
		
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
	
	

	
	
	</script>
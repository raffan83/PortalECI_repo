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


<table id="tabVerbali" class="table table-bordered table-hover dataTable table-striped" role="grid" width="100%">
										<c:if test="${!user.checkRuolo('CLVAL') }">
 														<thead>
 														
 														
 															<tr class="active"> 
 																<th>ID Verbale</th>
 															
 																<th>Numero Verbale</th>
 																<th>Matricola Attrezzatura</th>
 															
 																<th>ID Commessa</th>
 																<th>Codice Verifica</th>
 															
 																<th>Descrizione Verifica</th>
 																<th>Stato</th>
 																
 																<th>Data Creazione</th>
 																<td></td>
 															</tr>
 															 															
 														</thead>
 
 														<tbody> 
  															<c:forEach items="${listaVerbali}" var="verbale">  															
 																<tr role="row" id="${verbale.getId()}">
																	<td>
																		<a href="#" class="btn customTooltip customlink" title="Click per aprire il dettaglio del Verbale" onclick="callAction('gestioneVerbale.do?idVerbale=${verbale.getId()}');">
																			<c:out value='${verbale.getId()}'/>
																		</a>
																	</td>	
																
																	<td>
																		${verbale.numeroVerbale }
																	</td>
																	
																	<td>
																	<c:if test="${verbale.attrezzatura!=null}">
																	${verbale.attrezzatura.matricola_inail }
																	</c:if>
																	
																	
																	</td>
																	
																	
																	<td>
																		<c:out value='${verbale.intervento.idCommessa}'/>
																	</td>
																	<td>
																		<c:out value='${verbale.getCodiceVerifica()}'/>
																	</td>
																	
																	<td>
																		<c:out value='${verbale.getDescrizioneVerifica()}'/>
																	</td>
																	<td>
																		<span class="label" style="color:#000000 !important; background-color:${verbale.getStato().getColore(verbale.getStato().getId())} !important;">${verbale.getStato().getDescrizione()}</span> 
																		
																		 																	
																	</td>
																	
        															<td>
																		<fmt:formatDate pattern="dd/MM/yyyy" value='${verbale.getCreateDate()}' type='date' />
																	</td>
																	<td>
																		<a class="btn customTooltip" title="Click per aprire il dettaglio del Verbale" onclick="callAction('gestioneVerbale.do?idVerbale=${verbale.getId()}');">
                															<i class="fa fa-arrow-right"></i>
            															</a>
        															</td>
																</tr>
															</c:forEach>
 														</tbody>
 														</c:if>
 														<c:if test="${user.checkRuolo('CLVAL') }">
 														
 														
 														<thead>
 														
 														
 															<tr class="active"> 
 																<th>ID Verbale</th>
 															
 																<th>Numero Verbale</th>
 																<th>Matricola Attrezzatura</th>
 																<th>Tipo verifica</th>
 																
 																<th>Data Verifica</th>
 																<th>Certificati</th>
 																<th>Allegati</th>
 															</tr>
 															 															
 														</thead>
 
 														<tbody> 
  															<c:forEach items="${listaVerbali}" var="verbale">  	
  															<c:if test="${verbale.visibile_cliente==1 }">														
 																<tr role="row" id="${verbale.getId()}">
																	<td>
																		
																			<c:out value='${verbale.getId()}'/>
																		
																	</td>	
															
																	<td>
																		${verbale.numeroVerbale }
																	</td>
																	
																	<td>
																	<c:if test="${verbale.attrezzatura!=null}">
																	${verbale.attrezzatura.matricola_inail }
																	</c:if>
																	
																	
																	</td>
																	
																															
																	
																	<td>
																	<c:if test="${verbale.tipo_verifica == 1 || verbale.tipo_verifica == 2 }">
																	<c:if test="${verbale.tipo_verifica_gvr == 1}">
																	PVP
																	</c:if>
																	<c:if test="${verbale.tipo_verifica_gvr == 4}">
																	PVP + Integrità
																	</c:if>
																	<c:if test="${verbale.tipo_verifica_gvr == 5}">
																	PVP + Integrità + Interna
																	</c:if>
																	<c:if test="${verbale.tipo_verifica_gvr == 7}">
																	PVP + Interna
																	</c:if>
																	
																	</c:if>
																	
																	<c:if test="${verbale.tipo_verifica == 3 || verbale.tipo_verifica == 4 }">
																	
																	<c:if test="${verbale.tipo_verifica_gvr == 1}">
																	Funzionamento
																	</c:if>
																	<c:if test="${verbale.tipo_verifica_gvr == 2}">
																	Integrità
																	</c:if>
																	<c:if test="${verbale.tipo_verifica_gvr == 3}">
																	Interna
																	</c:if>
																	<c:if test="${verbale.tipo_verifica_gvr == 4}">
																	Funzionamento + Integrità
																	</c:if>
																	<c:if test="${verbale.tipo_verifica_gvr == 5}">
																	Funzionamento + Integrità + Interna
																	</c:if>
																	<c:if test="${verbale.tipo_verifica_gvr == 6}">
																	Interna + Integrità
																	</c:if>
																	<c:if test="${verbale.tipo_verifica_gvr == 7}">
																	Funzionamento + Interna
																	</c:if>
																	
																	</c:if>
																	
																	
																		
																	</td>
																	<td>
																		<c:choose>
																	<c:when test="${verbale.data_verifica!=null }">
																		<fmt:formatDate pattern="dd/MM/yyyy" value='${verbale.data_verifica}' type='date' />
																	</c:when>
																	<c:otherwise>
																		<c:choose>
																		<c:when test="${verbale.data_verifica_interna!=null }">
																			<fmt:formatDate pattern="dd/MM/yyyy" value='${verbale.data_verifica_interna}' type='date' />
																		</c:when>
																		<c:otherwise>
																			<c:if test="${verbale.data_verifica_integrita!=null }">
																			<fmt:formatDate pattern="dd/MM/yyyy" value='${verbale.data_verifica_integrita}' type='date' />
																			</c:if>																	
																		</c:otherwise>
																		</c:choose>
																	</c:otherwise>
																	</c:choose>
																	</td>
																	
																	<td>
																	
																		<a class="btn btn-primary customTooltip" onClick="modalAllegati('${verbale.id}', 'VERBALE')" title="Click per scaricare i certificati"><i class="fa fa-archive"></i></a>

																																		
																	</td>

																	<td>
																	
																		<a class="btn btn-primary customTooltip" onClick="modalAllegati('${verbale.id}', 'ALLEGATO')"title="Click per scaricare gli allegati"><i class="fa fa-archive"></i></a>
																																		
																	</td>
																	
        															
																</tr>
																</c:if>
															</c:forEach>
 														</tbody>
 														
 														</c:if>
 														
 													</table>  




</div>
</div>


						  <div id="myModalAllegati" class="modal fade" role="dialog" aria-labelledby="myLargeModalLabel">
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
						</div> 

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
		
		
		
		table = $('#tabVerbali').DataTable({
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
     		columnDefs: coldef
                  	     
   	});
		
		table.buttons().container().appendTo( '#tabVerbali_wrapper .col-sm-6:eq(1)' );
    		       	           	           	 	

		$('#tabVerbali thead th').each( function () {
			var title = $('#tabVerbali thead th').eq( $(this).index() ).text();
			$(this).append( '<div><input class="inputsearchtable" style="width:100%" type="text" /></div>');
		} );
			
		$('.inputsearchtable').on('click', function(e){
				e.stopPropagation();    
		});

		// DataTable
			table = $('#tabVerbali').DataTable();

		// Apply the search
		table.columns().eq( 0 ).each( function ( colIdx ) {
			$( 'input', table.column( colIdx ).header() ).on( 'keyup', function () {
   			table.column( colIdx ).search( this.value ).draw();		
			} );
		} ); 
		
		table.columns.adjust().draw();

		$('#tabVerbali').on( 'page.dt', function () {
			$('.customTooltip').tooltipster({
	        	theme: 'tooltipster-light'
	    	});
	  	} );
		
	});
	
	
	
	</script>
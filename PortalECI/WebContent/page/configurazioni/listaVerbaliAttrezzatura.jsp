<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


    <div class="row">
<div class="col-lg-12">


<table id="tabVerbali" class="table table-bordered table-hover dataTable table-striped" role="grid" width="100%">
 														<thead>
 															<tr class="active"> 
 																<th>ID Verbale</th>
 																<%-- <th>ID Intervento</th> --%>
 																<%-- <th>ID Commessa</th> --%>
 																<th>Numero Verbale</th>
 																<th>Matricola Attrezzatura</th>
 																<%-- <th>Sede Cliente</th> --%>
 																<th>ID Commessa</th>
 																<th>Codice Verifica</th>
 																<%-- <th>Tecnico Verificatore</th> --%>
 																<th>Descrizione Verifica</th>
 																<th>Stato</th>
 																<th>Stato S.T.</th>
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
																	<%-- <td>
																		<a href="#" class="btn customTooltip customlink" title="Click per aprire il dettaglio dell'Intervento" onclick="callAction('gestioneInterventoDati.do?idIntervento=${verbale.getIntervento().getId()}');">
																			${verbale.getIntervento().getId()}
																		</a>
																	</td> --%>
																	<%-- <td>
																	<a href="#" class="btn customTooltip customlink" title="Click per aprire il dettaglio della Commessa" onclick="callAction('gestioneIntervento.do?idCommessa=${verbale.intervento.idCommessa}');">
																			${verbale.intervento.idCommessa}
																		</a>
																	</td> --%>
																	<td>
																		${verbale.numeroVerbale }
																	</td>
																	
																	<td>
																	<c:if test="${verbale.attrezzatura!=null}">
																	${verbale.attrezzatura.matricola_inail }
																	</c:if>
																	
																	
																	</td>
																	
																	<%-- <td>
																		<c:out value='${verbale.getIntervento().getNome_sede()}'/>
																	</td> --%>
																	<td>
																		<c:out value='${verbale.intervento.idCommessa}'/>
																	</td>
																	<td>
																		<c:out value='${verbale.getCodiceVerifica()}'/>
																	</td>
																	<%-- <td>
																		<c:out value='${verbale.getIntervento().getTecnico_verificatore().getNominativo()}'/>
																	</td> --%>
																	<td>
																		<c:out value='${verbale.getDescrizioneVerifica()}'/>
																	</td>
																	<td>
																		<span class="label" style="color:#000000 !important; background-color:${verbale.getStato().getColore(verbale.getStato().getId())} !important;">${verbale.getStato().getDescrizione()}</span>
																		
																		 																	
																	</td>
																	<td>
																	<c:if test="${verbale.getSchedaTecnica()!=null }">
																			<span class="label" style="color:#000000 !important; background-color:${verbale.getStato().getColore(verbale.getSchedaTecnica().getStato().getId())} !important;">${verbale.getSchedaTecnica().getStato().getDescrizione()}</span>
																		</c:if>  											
																		<c:if test="${verbale.getSchedaTecnica()==null }">
																			<span class="label" style="color:#000000 !important; background-color:grey !important;">ASSENTE</span>
																		</c:if>	 
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
 													</table>  




</div>
</div>

<link rel="stylesheet" href="https://cdn.datatables.net/select/1.2.2/css/select.dataTables.min.css">

<script src="https://cdn.datatables.net/select/1.2.2/js/dataTables.select.min.js"></script>
	<script type="text/javascript">
	
	
	$(document).ready(function() {
		
		
		
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
     		columnDefs: [
				{ responsivePriority: 1, targets: 0 },
               { responsivePriority: 3, targets: 2 },
               { responsivePriority: 4, targets: 3 },
               { responsivePriority: 2, targets: 6 },
               { responsivePriority: 2, targets: 8, type:"date-eu" },
               { orderable: false, targets: 6 },
           ]
                  	     
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
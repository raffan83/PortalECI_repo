<%@page import="java.util.ArrayList"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/tld/utilities" prefix="utl" %>


<br><br>					
													
              										<table id="tabVerbale" class="table table-bordered table-hover dataTable table-striped" role="grid" width="100%">
 														<thead>
 														<tr class="active"> 
 																<th style="max-width:20px">ID Verbale</th>
 																<th style="max-width:30px">ID Intervento</th>
 																<th style="max-width:20px">ID Commessa</th>
 																<th>Stato</th>
 																<th>Numero Verbale</th>
 																<th>Data verifica</th>
 																<th>Data prossima verifica</th>
 																<th>Matricola Attrezzatura</th>
 																<th>Sede Cliente</th>
 																<th>Sede Utilizzatore</th>
 																<th>Codice Categoria</th>
 																<th>Codice Verifica</th>
 																<th>Tecnico Verificatore</th>
 																<th>Stato S.T.</th>
 																<th>Data Approvazione</th>
 																<th>Firmato</th>
 																<th>S.T. Firmata</th>
 																<th>Tipo verifica</th>
 																<th>Data Creazione</th>
 														
 																<th>Azioni</th>
 															</tr>
 															
 														</thead>

 														<tbody> 
 														<tr role="row" id="${verbale.getId()}">
																	<td>
																		 <a href="gestioneVerbale.do?action=dettaglio&idVerbale=${verbale.getId()}" type="submit" class="btn customTooltip customlink" title="Click per aprire il dettaglio del Verbale" >
																			<c:out value='${verbale.getId()}'/>
																		</a>
														</td>	
																	<td>
																		<a href="gestioneInterventoDati.do?idIntervento=${verbale.getIntervento().getId()}" class="btn customTooltip customlink" title="Click per aprire il dettaglio dell'Intervento">
																			${verbale.getIntervento().getId()}
																		</a>
																	</td>
																	<td>
																	<a href="gestioneIntervento.do?idCommessa=${verbale.intervento.idCommessa}" class="btn customTooltip customlink" title="Click per aprire il dettaglio della Commessa" >
																			${verbale.intervento.idCommessa}
																		</a>
																	</td>
																	<td>
																		<span class="label" style="color:#000000 !important; background-color:${verbale.getStato().getColore(verbale.getStato().getId())} !important;">${verbale.getStato().getDescrizione()}</span>
																		
																		 																	
																	</td>
																	<td>
																		${verbale.numeroVerbale }
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
																	<c:choose>
																		<c:when test="${verbale.getCodiceCategoria() == 'VIE'}">
																		<c:if test="${verbale.data_prossima_verifica!=null }">
																			<fmt:formatDate pattern="dd/MM/yyyy" value='${verbale.data_prossima_verifica}' type='date' />
																			</c:if>
																		</c:when>
																		<c:otherwise>
																			<c:choose>
																			<c:when test="${verbale.getCodiceVerifica().startsWith('GVR') }">
																			<fmt:formatDate pattern="dd/MM/yyyy" value='${utl:getDataPvP(verbale)}' type='date' />
																			</c:when>
																			<c:otherwise>
																			<c:if test="${verbale.data_prossima_verifica!=null }">
																				<fmt:formatDate pattern="dd/MM/yyyy" value='${verbale.data_prossima_verifica}' type='date' />
																				</c:if>
																			</c:otherwise>
																			</c:choose>
																		
															
																		
																		</c:otherwise>
																	</c:choose>
																	
																	
																	</td>
																	
																	
																	
																	<td>
																	<c:if test="${verbale.attrezzatura!=null}">
																	${verbale.attrezzatura.matricola_inail }
																	</c:if>
																	
																	
																	</td>
																	
																	<td>
																		<c:out value='${verbale.getIntervento().getNome_sede()}'/>
																	</td>
																	<td>
																		<c:out value='${verbale.getSedeUtilizzatore()}'/>
																	</td>
																	<td>
																		<c:out value='${verbale.getCodiceCategoria()}'/>
																	</td>
																	<td>
																		<c:out value='${verbale.getCodiceVerifica()}'/>
																	</td>
																	<td>
																		<c:out value='${verbale.getIntervento().getTecnico_verificatore().getNominativo()}'/>
																	</td>
																
																	
																	<td>
																	<c:if test="${verbale.getSchedaTecnica()!=null }">
																			<span class="label" style="color:#000000 !important; background-color:${verbale.getStato().getColore(verbale.getSchedaTecnica().getStato().getId())} !important;">${verbale.getSchedaTecnica().getStato().getDescrizione()}</span>
																		</c:if>  											
																		<c:if test="${verbale.getSchedaTecnica()==null }">
																			<span class="label" style="color:#000000 !important; background-color:grey !important;">ASSENTE</span>
																		</c:if>	 
																	</td>
																	
																	
																	<td><c:if test="${verbale.data_approvazione!=null }">
																		<fmt:formatDate pattern="dd/MM/yyyy" value='${verbale.data_approvazione}' type='date' />
																		</c:if> </td>
																	
																	<td>
																	
																	<c:if test="${verbale.firmato == 1 && verbale.controfirmato == 1}">
																		CF
																	</c:if>
																		<c:if test="${verbale.firmato == 1 && verbale.controfirmato == 0}">
																		F
																	</c:if>
																																		
																	</td>
																	
																	<td>
																	
																	<c:if test="${verbale.getSchedaTecnica()!=null && verbale.getSchedaTecnica().getFirmato() == 1 && verbale.getSchedaTecnica().getControfirmato()  == 1}">
																		CF
																	</c:if>
																	
																	<c:if test="${verbale.getSchedaTecnica()!=null && verbale.getSchedaTecnica().getFirmato() == 1 && verbale.getSchedaTecnica().getControfirmato()  == 0}">
																		F
																	</c:if>
																																		
																	</td>
																	
        															
																	<td>
																	<c:if test="${verbale.motivo_verifica == 1}">
																	Periodica
																	</c:if>
																	<c:if test="${verbale.motivo_verifica != 0 && verbale.motivo_verifica > 1 && verbale.motivo_verifica <=4}">
																	Straordinaria
																	</c:if>
																	<c:if test="${verbale.motivo_verifica != 0 && verbale.motivo_verifica > 4}">
																	Periodica + Straordinaria
																	</c:if>
																	</td>
																	<td>
																		<fmt:formatDate pattern="dd/MM/yyyy" value='${verbale.getCreateDate()}' type='date' />
																	</td>
																	
															<td  align="center" >
		
														<a class="btn btn-info" onclick="cambiaStatoVerbaleAdmin(8, ${verbale.getId()})"><i class="glyphicon glyphicon-refresh"></i></a>
														<a class="btn btn-info" onclick="cambiaStatoVerbaleAdmin(4, ${verbale.getId()})"><i class="glyphicon glyphicon-refresh"></i></a>
													</td>
        														
																</tr>
														
 														</tbody> 
 													</table>  
 													
 													
 													
<script type="text/javascript" src="https://ajax.aspnetcdn.com/ajax/jquery.validate/1.13.1/jquery.validate.min.js"></script>
<script src="https://cdn.datatables.net/select/1.2.2/js/dataTables.select.min.js"></script>
<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="plugins/datepicker/locales/bootstrap-datepicker.it.js"></script> 
<script type="text/javascript" src="plugins/datejs/date.js"></script> 													
 <script type="text/javascript">													
	$(document).ready(function() {
 
		
		 console.log($('#tabVerbale thead th').length)
			console.log($('#tabVerbale tbody tr:first td').length)
		console.log("tabella");
	 $('.select2').select2()
     $('.dropdown-toggle').dropdown();
	 
	 

	 tab = $('#tabVerbale').DataTable({
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
	        pageLength: 25,
	        "order": [[ 2, "desc" ]],
		      paging: false, 
		      ordering: true,
		      info: false, 
		      searchable: true, 
		      targets: 0,
		      responsive: true,  
		      scrollX: false,
		      stateSave: false,	

		           
		      columnDefs: [
		    	  
		    	  { responsivePriority: 1, targets: 1 },
		    	  { responsivePriority: 2, targets: 19 },
		    	  
		    	
		    	  ],
		    	  
		     	          
	  	      buttons: [   
	  	          {
	  	            extend: 'colvis',
	  	            text: 'Nascondi Colonne'  	                   
	 			  } ]
		               
		    });
		
		$('#tabVerbale thead th').each( function () {
			var title = $('#tabVerbale thead th').eq( $(this).index() ).text();
			$(this).append( '<div><input class="inputsearchtable" style="width:100%" type="text" /></div>');
		} );
		
		
		tab.buttons().container().appendTo( '#tabVerbale_wrapper .col-sm-6:eq(1)');
	 	    $('.inputsearchtable').on('click', function(e){
	 	       e.stopPropagation();    
	 	    });

	 	     tab.columns().eq( 0 ).each( function ( colIdx ) {
	  $( 'input', tab.column( colIdx ).header() ).on( 'keyup', function () {
	      tab
	          .column( colIdx )
	          .search( this.value )
	          .draw();
	  } );
	} );  
    
     
	 	   
});
	
	
	function cambiaStatoVerbaleAdmin(id_stato, id_verbale){
		
		var dataObj = {};
		dataObj.id_stato = id_stato
		dataObj.id_verbale = id_verbale
		
		
		callAjax(dataObj, "gestioneListaVerbali.do?action=cambia_stato_verbale_admin");
		
	}
	
	
	</script>
 													
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="it.portalECI.DTO.InterventoDTO"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<t:layout title="Dashboard" bodyClass="skin-red sidebar-mini wysihtml5-supported">

	<jsp:attribute name="body_area">

		<div class="wrapper">
	
  			<t:main-header  />
  			<t:main-sidebar />

  			<!-- Content Wrapper. Contains page content -->
			<div id="corpoframe" class="content-wrapper">  

				<!-- Content Header (Page header) -->
    			<section class="content-header">
      				<h1>
        				Lista Interventi
        				<small>Fai click per entrare nel dettaglio dell'Intervento</small>
      				</h1>
    			</section>

			    <!-- Main content -->
    			<section class="content">

					<div class="row">
        				<div class="col-xs-12">
          					<div class="box">
            					<div class="box-body">
              						<div class="row">
        								<div class="col-xs-12">
 											<div class="box box-danger box-solid">
												<div class="box-header with-border">
	 												Lista
													<div class="box-tools pull-right">
														<button data-widget="collapse" class="btn btn-box-tool"><i class="fa fa-minus"></i></button>
													</div>
												</div>
												<div class="box-body">
              										<table id="tabPM" class="table table-bordered table-hover dataTable table-striped" role="grid" width="100%">
 														<thead>
 															<tr class="active"> 
 																<th>ID Intervento</th>
 																<th>ID Commessa</th>
 																<th>Cliente</th>
 																<th>Sede</th>
 																<th>Stato Intervento</th>
 																<th>Tecnico Verificatore</th>
 																<th>Categoria Verifica</th>
 																<th>Tipo Verifica</th>
 																<th>Data Creazione</th>
 																<td></td>
 															</tr>
 														</thead>
 
 														<tbody> 
  															<c:forEach items="${listaInterventi}" var="intervento">
 																<tr role="row" id="${intervento.getId() }">
																	<td>
																		<a href="#" class="btn customTooltip customlink" title="Click per aprire il dettaglio dell'Intervento" onclick="callAction('gestioneInterventoDati.do?idIntervento=${intervento.getId()}');">
																			${intervento.getId() }
																		</a>
																	</td>
																	<td>
																		<a href="#" class="btn customTooltip customlink" title="Click per aprire il dettaglio dell'Intervento" onclick="callAction('gestioneIntervento.do?idCommessa=${intervento.getIdCommessa()}');">
																			<c:out value='${intervento.getIdCommessa()}'/>
																		</a>
																	</td>
																	<td>
																		<c:out value='${intervento.getId_cliente()}'/>
																	</td>
																	<td>
																		<c:out value='${intervento.getNome_sede()}'/>
																	</td>
																	<td>
																		<!--<c:out value='${intervento.getStatoIntervento().getDescrizione()}'/>-->
																		<a class="centered">
				 															<c:choose>
  																				<c:when test="${intervento.getStatoIntervento().getDescrizione().equals('CHIUSO')}">
    																				<span class="label label-danger">CHIUSO</span>
  																				</c:when>
  																				<c:when test="${intervento.getStatoIntervento().getDescrizione().equals('APERTO')}">
    																				<span class="label label-success">APERTO</span>
  																				</c:when>
  																				<c:otherwise>
    																				<span class="label label-info">-</span>
	 																			</c:otherwise>
																			</c:choose>  
																		</a>
																	</td>
																	<td>
																		<c:out value='${intervento.getTecnico_verificatore().getNominativo()}'/>
																	</td>
																	<td>
																		<c:out value='${intervento.getCat_verifica().getCodice()}'/>
																	</td>
																	<td>
																		<c:out value='${intervento.getTipo_verifica().getCodice()}'/>
																	</td>																															
        															<td>
																		<fmt:formatDate pattern="dd/MM/yyyy" value='${intervento.getDataCreazione() }' type='date' />
																	</td>
																	<td>
																		<a class="btn customTooltip" title="Click per aprire il dettaglio dell'Intervento" onclick="callAction('gestioneInterventoDati.do?idIntervento=${intervento.getId()}');">
                															<i class="fa fa-arrow-right"></i>
            															</a>
        															</td>
																</tr>
															</c:forEach>
 														</tbody>
 													</table>  
												</div>
											</div>
										</div>
									</div>
            						<!-- /.box-body -->
          						</div>
        					</div>
        					<!-- /.col -->
 						</div>
					</div>  	

				    <div id="errorMsg"><!-- Place at bottom of page --></div> 
				</section>  
  			</div>
  			<!-- /.content-wrapper -->
	
  			<t:dash-footer />
 
  			<t:control-sidebar />
  
		</div>
		<!-- ./wrapper -->

	</jsp:attribute>


	<jsp:attribute name="extra_css">
	</jsp:attribute>

	<jsp:attribute name="extra_js_footer">

  		<script type="text/javascript">
   
    		$(document).ready(function() {
     			table = $('#tabPM').DataTable({
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
    	      		columnDefs: [
						{ responsivePriority: 1, targets: 0 },
    	                { responsivePriority: 3, targets: 2 },
    	                { responsivePriority: 4, targets: 3 },
    	                { responsivePriority: 2, targets: 6 },
    	                { orderable: false, targets: 6 },
    	            ],             
    	            buttons: [ {
    	            	extend: 'copy',
    	                text: 'Copia',
    	                /* exportOptions: {
	                    	modifier: {
	                        	page: 'current'
	                       	}
	                   	} */
    	            },{
    	            	extend: 'excel',
    	               	text: 'Esporta Excel',
    	                /* exportOptions: {
    	                	modifier: {
    	                    	page: 'current'
    	                    }
    	                } */
    	            },{
    	            	extend: 'colvis',
    	                text: 'Nascondi Colonne'    	                  
    	            }]    	        	     
    	    	});
    			
     			table.buttons().container().appendTo( '#tabPM_wrapper .col-sm-6:eq(1)' );
     	    		       	           	           	 	
    
    			$('#tabPM thead th').each( function () {
        			var title = $('#tabPM thead th').eq( $(this).index() ).text();
        			$(this).append( '<div><input class="inputsearchtable" style="width:100%" type="text" /></div>');
				} );
       			
				$('.inputsearchtable').on('click', function(e){
   					e.stopPropagation();    
				});
 
    			// DataTable
  				table = $('#tabPM').DataTable();
    
    			// Apply the search
    			table.columns().eq( 0 ).each( function ( colIdx ) {
        			$( 'input', table.column( colIdx ).header() ).on( 'keyup', function () {
            			table.column( colIdx ).search( this.value ).draw();		
        			} );
    			} ); 
    			
    			table.columns.adjust().draw();
    
    	 		$('#tabPM').on( 'page.dt', function () {
					$('.customTooltip').tooltipster({
			        	theme: 'tooltipster-light'
			    	});
			  	} );
    		});
    
  		</script>
  
  
	</jsp:attribute> 
</t:layout>
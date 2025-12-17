<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@page import="java.text.SimpleDateFormat"%>
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
        				Lista Verbali PDF
        				<small>Fai click per entrare nel dettaglio del Verbale</small>
      				</h1>
    			</section>
    			


			    <!-- Main content -->
    			<section class="content">

					<div class="row">
        				<div class="col-xs-12">
          					<div class="box">
            					<div class="box-body">
            					
            		<div class="row">
        				
        				
        					<div class="col-xs-5">
			 <div class="form-group">
				 <label for="datarange" class="control-label">Filtra Data Verifica:</label>
					<div class="col-md-10 input-group" >
						<div class="input-group-addon">
				             <i class="fa fa-calendar"></i>
				        </div>				                  	
						 <input type="text" class="form-control" id="datarange" name="datarange" value=""/> 						    
							 <span class="input-group-btn">
				               <button type="button" class="btn btn-info btn-flat" onclick="filtraDate()">Cerca</button>
				               <button type="button" style="margin-left:5px" class="btn btn-primary btn-flat" onclick="resetDate()">Reset Date</button>
				             </span>				                     
  					</div>  								
			 </div>	
			 

        				</div>
            					</div>
            					
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
 																<th>ID Documento</th> 
 																<th>ID Verbale</th>
 																<th>ID Intervento</th>
 																<th>ID Commessa</th>
 																<th>Numero Verbale</th>
 																<th>Tipo Verifica</th>
 																<th>Data Verifica</th>
 																<th>Data Fine Verifica</th>
 																<th>Data Approvazione</th>
 																<th>Matricola Attrezzatura</th>
 																<th>Sede Cliente</th>

 																<th>Stato</th>
 															
 																<th>Data Creazione</th>
 																<th>Riemesso</th>
 																<td></td>
 															</tr>
 														</thead>
 
 														<tbody> 
  															<c:forEach items="${listaVerbali}" var="documento">  															
 																<tr role="row" id="${documentoverbale.getId()}">
 																<td>
																		
																			${documento.getId()}
																		
																	</td>
																	<td>
																		<a href="gestioneVerbale.do?action=dettaglio&idVerbale=${documento.verbale.getId()}" class="btn customTooltip customlink" title="Click per aprire il dettaglio del Verbale" >
																			<c:out value='${documento.verbale.getId()}'/>
																		</a>
																	</td>	
																	<td>
																		<a href="gestioneInterventoDati.do?idIntervento=${documento.verbale.getIntervento().getId()}" class="btn customTooltip customlink" title="Click per aprire il dettaglio dell'Intervento" >
																			${documento.verbale.getIntervento().getId()}
																		</a>
																	</td>
																	<td>
																	<a href="gestioneIntervento.do?idCommessa=${documento.verbale.intervento.idCommessa}" class="btn customTooltip customlink" title="Click per aprire il dettaglio della Commessa" >
																			${documento.verbale.intervento.idCommessa}
																		</a>
																	</td>
																	<td>
																	<a class="btn customTooltip customlink" href="gestioneDocumento.do?idDocumento=${documento.getId()}">${documento.getFileName().split('.pdf')[0] }</a>
																		
																	</td>
																	<td>
																	<c:if test="${documento.verbale.motivo_verifica == 1}">
																	Periodica
																	</c:if>
																	<c:if test="${documento.verbale.motivo_verifica != 0 && documento.verbale.motivo_verifica > 1 &&  documento.verbale.motivo_verifica<=4}">
																	Straordinaria
																	</c:if>
																	<c:if test="${documento.verbale.motivo_verifica != 0 && documento.verbale.motivo_verifica > 4}">
																	Periodica + Straordinaria
																	</c:if>
																	</td>
																	<td><fmt:formatDate pattern="dd/MM/yyyy" value='${documento.verbale.data_verifica}' type='date' /></td>
																	<td>
																	<c:if test="${documento.verbale.data_fine_verifica!=null }">
																		<fmt:formatDate pattern="dd/MM/yyyy" value='${documento.verbale.data_fine_verifica}' type='date' />
																	</c:if>
																	</td>
																	<td>
																	<c:if test="${documento.verbale.data_approvazione!=null }">
																		<fmt:formatDate pattern="dd/MM/yyyy" value='${documento.verbale.data_approvazione}' type='date' />
																	</c:if>
																	</td>
																	<td>
																	<c:if test="${documento.verbale.attrezzatura!=null}">
																	${documento.verbale.attrezzatura.matricola_inail }
																	</c:if>
																	
																	
																	</td>
																	
																	<td>
																		<c:out value='${documento.verbale.getIntervento().getNome_sede()}'/>
																	</td>
																
																	
																	
																	<td>
																		<span class="label" style="color:#000000 !important; background-color:${documento.verbale.getStato().getColore(documento.verbale.getStato().getId())} !important;">${documento.verbale.getStato().getDescrizione()}</span>
																																	
																	</td>
						
        															<td>
																		<fmt:formatDate pattern="dd/MM/yyyy" value='${documento.verbale.getCreateDate()}' type='date' />
																	</td>
																	<td style="text-align:center">
																	<c:if test="${documento.getInvalid() }">
																	<strong>X</strong>
																	</c:if>
																	</td>
																	
																	<td>
																	
																		
	                  											<c:if test="${documento.verbale.codiceCategoria == 'VAL' && documento.verbale.getFirmato()==1}">
	                  											<a class="btn btn-primary" href="gestioneDocumento.do?firmato=1&idDocumento=${documento.getId()}" style="margin-left:5px"><i class="glyphicon glyphicon-file"></i></a>
	                  											</c:if>			
	                  											
	                  											<c:if test="${documento.verbale.getFirmato()==1 && documento.verbale.getControfirmato() == 1   }"> 
	                  											<a class="btn btn-primary" href="gestioneDocumento.do?controfirmato=1&idDocumento=${documento.getId()}" style="margin-left:5px"><i class="glyphicon glyphicon-file"></i></a>
	                  											
	                  												
	                  											</c:if>		
	                  											
	                  											
	                  										
	                  											
																	
																		<%-- <a class="btn btn-primary" title="Click per scaricare il verbale" href="gestioneDocumento.do?idDocumento=${documento.getId()}"><i class="glyphicon glyphicon-file"></i></a> --%>
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
          						<!-- </div> -->
        					</div>
        					<!-- /.col -->
 						</div>
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
		<link type="text/css" href="css/bootstrap.min.css" />
	<link rel="stylesheet" type="text/css" href="//cdn.jsdelivr.net/bootstrap.daterangepicker/2/daterangepicker.css" />
	</jsp:attribute>

	<jsp:attribute name="extra_js_footer">
<script type="text/javascript" src="plugins/datepicker/locales/bootstrap-datepicker.it.js"></script> 
<script type="text/javascript" src="//cdn.jsdelivr.net/bootstrap.daterangepicker/2/daterangepicker.js"></script>
<script type="text/javascript" src="plugins/datejs/date.js"></script>
  		<script type="text/javascript">
  		
  		function filtraDate(){
  			
  			var startDatePicker = $("#datarange").data('daterangepicker').startDate;
  		 	var endDatePicker = $("#datarange").data('daterangepicker').endDate;
  		 	dataString = "?action=lista_file&dateFrom=" + startDatePicker.format('YYYY-MM-DD') + "&dateTo=" + 
  		 			endDatePicker.format('YYYY-MM-DD');
  		 	
  		 	 pleaseWaitDiv = $('#pleaseWaitDialog');
  			  pleaseWaitDiv.modal();

  		 	callAction("gestioneListaVerbali.do"+ dataString, false,true);

  		 	//exploreModal("gestioneListaVerbali.do", dataString, '#content_consuntivo');
  	}




  	function resetDate(){
  		pleaseWaitDiv = $('#pleaseWaitDialog');
  			  pleaseWaitDiv.modal();
  		callAction("gestioneListaVerbali.do?action=lista_file");

  	}

	
  	function formatDate(data){
  		
  		   var mydate =  Date.parse(data);
  		   
  		   if(!isNaN(mydate.getTime())){
  		   
  			var   str = mydate.toString("dd/MM/yyyy");
  		   }			   
  		   return str;	 		
  	}

   
    		$(document).ready(function() {
    			
    			
    			
    		     var start = "${dateFrom}";
    		   	var end = "${dateTo}";

    		   	if(start !='' && end !=''){
    		   		$('#scadenzario_btn').removeClass("disabled");
    		   		$('#scadenzario_vie_btn').removeClass("disabled");
    		   	}else{
    		   		$('#scadenzario_btn').addClass("disabled");
    		   		$('#scadenzario_vie_btn').addClass("disabled");
    		   	}
    		   	
    		   	$('input[name="datarange"]').daterangepicker({
    		  	    locale: {
    		  	      format: 'DD/MM/YYYY'
    		  	    
    		  	    }
    		  	}, 
    		  	function(start, end, label) {

    		  	});
    		   	
    		   	if(start!=null && start!=""){
    		  	 	$('#datarange').data('daterangepicker').setStartDate(formatDate(start));
    		  	 	$('#datarange').data('daterangepicker').setEndDate(formatDate(end));
    		  	
    		  	 }

    			
    			
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
	      			order: [[ 0, "desc" ]],
    	      		columnDefs: [
						{ responsivePriority: 1, targets: 0 },
    	                { responsivePriority: 3, targets: 2 },
    	                { responsivePriority: 4, targets: 14 },
						
    	                /* { orderable: false, targets: 7 }, */
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
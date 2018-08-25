<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="it.portalECI.DTO.QuestionarioDTO"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="it.portalECI.DTO.UtenteDTO"%>

<%
	UtenteDTO user = (UtenteDTO)request.getSession().getAttribute("userObj");
	request.setAttribute("user",user);
%>

<t:layout title="Dashboard" bodyClass="skin-red sidebar-mini wysihtml5-supported">

	<jsp:attribute name="body_area">

		<div class="wrapper">
	
  			<t:main-header  />
  			<t:main-sidebar />

  			<!-- Content Wrapper. Contains page content -->
			<div id="corpoframe" class="content-wrapper">  

				<!-- Content Header (Page header) -->
    			<section class="content-header">
      				<h1 class = "pull-left">
        				Lista Questionari
        				<small>Fai click per entrare nel dettaglio del questionario</small>
      				</h1>      			
      				<c:if test="${user.checkPermesso('CREA_QUESTIONARIO')}">
      					<a class="btn btn-default pull-right" href="gestioneQuestionario.do?"><i class="glyphicon glyphicon-plus"></i> Nuovo questionario</a>
      				</c:if>
    			</section>
				<div style="clear: both;"></div>
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
              										<table id="tabellaQuestionari" class="table table-bordered table-sm dataTable" role="grid" width="100%">
 														<thead>
 															<tr class="active"> 
 																<th>Id</th>
 																<th>Tipo</th>
 																<th>Titolo</th>
 																<th>Data creazione</th>
 																<th>Data ultima modifica</th>
 																<td></td>
 															</tr>
 														</thead>
 
 														<tbody> 
  															<c:forEach items="${listaQuestionari}" var="questionario">
 																<tr role="row" id="questionario_${questionario.id}" class="${questionario.isObsoleto?'bg-gray disabled text-muted':''}">
																	<td>${questionario.id }</td>
																	<td>${questionario.tipo.codice }</td>
																	<td>${questionario.titolo}</td>
																	<td>
																		<fmt:formatDate pattern="dd/MM/yyyy" value='${questionario.createDate}' type='date' />																
																	</td>
																	<td>
																		<fmt:formatDate pattern="dd/MM/yyyy" value='${questionario.updateDate}' type='date' />																
																	</td>
																	<td><nobr>
																		<c:if test="${user.checkPermesso('UPD_QUESTIONARIO')}">
																			<a href="gestioneQuestionario.do?idQuestionario=${questionario.id}&action=modifica" class="btn customTooltip customlink" title="Click per modificare il questionario">
																				<i class="fa fa-edit"></i>
																			</a>
																		</c:if>																		
																		<a href="gestioneQuestionario.do?idQuestionario=${questionario.id}" class="btn customTooltip customlink" title="Click per aprire il dettaglio del questionario">
																			<i class="fa fa-arrow-right"></i>
																		</a></nobr>
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

  					<div id="myModal" class="modal fade" role="dialog" aria-labelledby="myLargeModalLabel">
    					<div class="modal-dialog" role="document">
    						<div class="modal-content">
     							<div class="modal-header">
        							<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        							<h4 class="modal-title" id="myModalLabel">Approvazione</h4>
      							</div>
       							<div class="modal-body">     
        							<div class="form-group">		
                  						<textarea class="form-control" rows="3" id="noteApp" placeholder="Entra una nota ..."></textarea>
                					</div>               
  									<div id="empty" class="testo12"></div>
  		 						</div>
      							<div class="modal-footer">
        							<button type="button" class="btn btn-primary" onclick="approvazioneFromModal('app')"  >Approva</button>
        							<button type="button" class="btn btn-danger"onclick="approvazioneFromModal('noApp')"  >Non Approva</button>
      							</div>
    						</div>
  						</div>
					</div>

  					<div id="myModalError" class="modal fade" role="dialog" aria-labelledby="myLargeModalLabel">
    					<div class="modal-dialog" role="document">
    						<div class="modal-content">
     							<div class="modal-header">
        							<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        							<h4 class="modal-title" id="myModalLabel">Messaggio</h4>
      							</div>
       							<div class="modal-body">
									<div id="myModalErrorContent">			
									</div>		   
  									<div id="empty" class="testo12"></div>
  		 						</div>
      							<div class="modal-footer">		
        							<button type="button" class="btn btn-outline" data-dismiss="modal">Chiudi</button>
      							</div>
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
	</jsp:attribute>

	<jsp:attribute name="extra_js_footer">
		
  		<script type="text/javascript">
   
    		$(document).ready(function() {
     			table = $('#tabellaQuestionari').DataTable({
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
    	                { responsivePriority: 1, targets: 1 },
    	                { responsivePriority: 2, targets: 2 },
    	                { responsivePriority: 1, targets: 3 ,type:"date-eu"},
    	                { orderable: false, targets: 4 },
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
    			
     			table.buttons().container().appendTo( '#tabellaQuestionari_wrapper .col-sm-6:eq(1)' );
     	   
 				/* $('#tabPM').on( 'dblclick','tr', function () {
       				var id = $(this).attr('id');       		
       				var row = table.row('#'+id);
       				data = row.data();           
     	    		
       				if(data){
     	    	 		row.child.hide();
             			$( "#myModal" ).modal();
     	    		}
       			}); */
       	           	    
       	 		$('#myModal').on('hidden.bs.modal', function (e) {
       	  			$('#noteApp').val("");
       	 			$('#empty').html("");
       			})
    
    			$('#tabellaQuestionari thead th').each( function () {
        			var title = $('#tabellaQuestionari thead th').eq( $(this).index() ).text();
        			$(this).append( '<div><input class="inputsearchtable" style="width:100%" type="text" /></div>');
				} );
       			
				$('.inputsearchtable').on('click', function(e){
   					e.stopPropagation();    
				});
 
    			// DataTable
  				table = $('#tabellaQuestionari').DataTable();
    
    			// Apply the search
    			table.columns().eq( 0 ).each( function ( colIdx ) {
        			$( 'input', table.column( colIdx ).header() ).on( 'keyup', function () {
            			table.column( colIdx ).search( this.value ).draw();		
        			} );
    			} ); 
    			
    			table.columns.adjust().draw();
    
    	 		$('#tabellaQuestionari').on( 'page.dt', function () {
					$('.customTooltip').tooltipster({
			        	theme: 'tooltipster-light'
			    	});
			  	} );
    		});
    
  		</script>
  
  
	</jsp:attribute> 
</t:layout>
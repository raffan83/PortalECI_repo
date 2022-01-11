<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="it.portalECI.DTO.CommessaDTO"%>
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
        				Lista Commesse
        				<small>Fai click per entrare nel dettaglio della commessa</small>
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
												<div class="row">
													<div class="col-xs-6">
			 <div class="form-group">
				 <label for="datarange" class="control-label">Ricerca Date:</label>
					<div class="col-md-10 input-group" >
						<div class="input-group-addon">
				             <i class="fa fa-calendar"></i>
				        </div>				                  	
						 <input type="text" class="form-control" id="datarange" name="datarange" value=""/> 						    
							 <span class="input-group-btn">
				               <button type="button" class="btn btn-info btn-flat" onclick="filtraCommessePerData()">Cerca</button>
				               <button type="button" style="margin-left:5px" class="btn btn-primary btn-flat" onclick="resetDate()">Reset Date</button>
				             </span>				                     
  					</div>  								
			 </div>	
			 
			 

	</div>
	
															<div class="col-sm-6">  
								  						 <button class="btn btn-primary pull-right"  onclick="filtraCommesse('')" style="margin-top:25px"id="tutte_btn" >
															
															 Tutte
														</button> 
														 
														       
														         
														    <button class="btn btn-primary pull-right" onclick="filtraCommesse('CHIUSA')" style="margin-top:25px; margin-right:5px" id="chiuse_btn" >
															
															 Chiuse
														</button>  
														
														 <button class="btn btn-primary pull-right" disabled onclick="filtraCommesse('APERTA')" style="margin-top:25px; margin-right:5px" id="aperte_btn" >
															
															 Aperte
														</button> 						         
														 
												</div>
												</div>
												
												
												<div class="row"> 
<div class="col-xs-2">
 <label>Anno: </label>

   <select name="select1" id="select1" data-placeholder="Seleziona Cliente..." style="width:100%"  class="form-control select2" aria-hidden="true" data-live-search="true">
	<option value="0" selected="selected">TUTTI</option>
	 <c:forEach items="${yearList}" var="year">
	 
	 	<c:choose>
                       <c:when test="${year == current_year}">
                           <option value="${year}"  selected="selected">${year}</option> 
                        </c:when>
                        <c:otherwise>
                        <option value="${year}">${year}</option> 
                        </c:otherwise>
      	</c:choose>
      </c:forEach>
	</select>
	<br>
		</div>
	</div>
              										<table id="tabPM" class="table table-bordered table-hover dataTable table-striped" role="grid" width="100%">
 														<thead>
 															<tr class="active"> 
 																<th>ID commessa</th>
 																<th>Cliente</th>
 																<th>Indirizzo Cliente</th>
 																<th>Utilizzatore</th>
 																<th>Indirizzo Utilizzatore</th>
 																<th>Stato Richiedente</th>
 																<th>Data Apertura</th>
 																<th>Data Chiusura</th>
 																<td></td>
 															</tr>
 														</thead>
 
 														<tbody> 
  															<c:forEach items="${listaCommesse}" var="commessa">
 																<tr role="row" id="${commessa.ID_COMMESSA}">
																	<td>
																		<a href="gestioneIntervento.do?idCommessa=${commessa.ID_COMMESSA}" class="btn customTooltip customlink" title="Click per aprire il dettaglio della Commessa" >
																			${commessa.ID_COMMESSA}
																		</a>
																	</td>
																	<td>
																		<c:out value="${commessa.ID_ANAGEN_NOME}"/>
																	</td>
																	<td>
																		<c:out value="${commessa.INDIRIZZO_PRINCIPALE}"/>
																	</td>
																		<td>
																		<c:out value="${commessa.NOME_UTILIZZATORE}"/>
																	</td>
																		<td>
																		<c:out value="${commessa.INDIRIZZO_UTILIZZATORE}"/>
																	</td>
																	
																
																	<td class="centered">
 																		<c:choose>
  																			<c:when test="${commessa.SYS_STATO == '1CHIUSA'}">
    																			<span class="label label-danger">CHIUSA</span>
  																			</c:when>
  																			<c:when test="${commessa.SYS_STATO == '1APERTA'}">
    																			<span class="label label-success">APERTA</span>
  																			</c:when>
  																			<c:when test="${commessa.SYS_STATO == '0CREATA'}">
    																			<span class="label label-warning">CREATA</span>
  																			</c:when>
  																			<c:otherwise>
    																			<span class="label label-info">-</span>
  																			</c:otherwise>
																		</c:choose> 
																	</td>
																	<td>
																		<fmt:formatDate pattern="dd/MM/yyyy" value="${commessa.DT_COMMESSA}" type='date' />
																	</td>
																	<td>
																		<c:if test="${not empty commessa.FIR_CHIUSURA_DT}">
   																			<fmt:formatDate pattern="dd/MM/yyyy" value="${commessa.FIR_CHIUSURA_DT}" />
																		</c:if>
																	</td>
																	<td>
																		<a class="btn customTooltip" title="Click per aprire il dettaglio della Commessa" onclick="callAction('gestioneIntervento.do?idCommessa=${commessa.ID_COMMESSA}');">
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
  									<div id="empty" class="label label-danger testo12"></div>
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
  									<div id="empty" class="label label-danger testo12"></div>
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


	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-timepicker/0.5.2/css/bootstrap-timepicker.css">
	 <!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-timepicker/0.5.2/css/bootstrap-timepicker.css"></script> -->
	<link type="text/css" href="css/bootstrap.min.css" />
	
 	<link rel="stylesheet" type="text/css" href="plugins/daterangepicker/daterangepicker.css" /> 


	</jsp:attribute>

	<jsp:attribute name="extra_js_footer">

<script src="plugins/daterangepicker/daterangepicker.js"></script>
 <script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>

  <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-timepicker/0.5.2/js/bootstrap-timepicker.js"></script> 

   
  		<script type="text/javascript">
  		
  		
  		function filtraCommesse(filtro){
  			
			var table = $('#tabPM').DataTable();
		    
			table.column( 5 ).search( filtro ).draw();	
			
			
			if(filtro == 'APERTA'){
				$('#aperte_btn').attr("disabled", true);
				$('#chiuse_btn').attr("disabled", false);
				$('#tutte_btn').attr("disabled", false);
				
			}else if(filtro == 'CHIUSA'){
				$('#aperte_btn').attr("disabled", false);
				$('#chiuse_btn').attr("disabled", true);
				$('#tutte_btn').attr("disabled", false);
			}else{
				$('#aperte_btn').attr("disabled", false);
				$('#chiuse_btn').attr("disabled", false);
				$('#tutte_btn').attr("disabled", true);
			}
			

  			
  		}
   
  		
  	  function filtraCommessePerData(){		

  			
  			var startDatePicker = $("#datarange").data('daterangepicker').startDate;
  			var endDatePicker = $("#datarange").data('daterangepicker').endDate;
  			
  			dataString = "?action=date&dateFrom=" + startDatePicker.format('YYYY-MM-DD') + "&dateTo=" + endDatePicker.format('YYYY-MM-DD');			 	
  			pleaseWaitDiv = $('#pleaseWaitDialog');
  			pleaseWaitDiv.modal();

  			callAction("gestioneCommessa.do"+ dataString, false,true);			 
  	 
	
  	}
  	 
  	 function resetDate(){
  			pleaseWaitDiv = $('#pleaseWaitDialog');
  			pleaseWaitDiv.modal();
  			callAction('gestioneCommessa.do',null,true);

  		}
  		
	 var start = '${startDate}';
	var end = '${endDate}';
  		
    		$(document).ready(function() {
    			
    			
   			 $('input[name="datarange"]').daterangepicker({
				    locale: {
				      format: 'DD/MM/YYYY'
				    
				    }
				}, 
				function(start, end, label) {

				});
   			 
   			 
   		
   			 
   			 if(start!='' && end!=''){
   				 
   				
   				$('#datarange').data('daterangepicker').setStartDate(start);
   				$('#datarange').data('daterangepicker').setEndDate(end);
   				
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
    	      		"order": [[ 6, "desc" ]],
    	      		columnDefs: [
						{ responsivePriority: 1, targets: 0 },
    	                { responsivePriority: 3, targets: 2 },
    	                { responsivePriority: 4, targets: 6 ,type:"date-eu"},
    	                { responsivePriority: 4, targets: 7 ,type:"date-eu"},
    	                { responsivePriority: 2, targets: 8 },
    	                { orderable: false, targets: 8 },
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
    	 		
    	 		
    	 		
    	 		
    	 		
    	 		filtraCommesse('APERTA')
    		});
    		
    	    $("#select1").change(function(){	
    	    	
    	    	callAction('gestioneCommessa.do?year='+$("#select1").val(),null,true);
    	    	
    	    });
    
  		</script>
  
  
	</jsp:attribute> 
</t:layout>
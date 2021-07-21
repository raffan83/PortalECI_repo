<%@page import="java.util.ArrayList"%>
<%@page import="com.google.gson.JsonArray"%>
<%@page import="com.google.gson.Gson"%>

<%@page import="it.portalECI.DTO.UtenteDTO"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

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
      				<h1>
        				Gestione Versioni Portale
      				</h1>
    			</section>
    			
    			<!-- Main content -->
    			<section class="content">	
					<div class="row">
        				<div class="col-xs-12">
          					<div class="box">
          						<div class="box-header">
          						</div>
            					<div class="box-body">
              						<div class="row">
        								<div class="col-xs-12">	
 											<div class="box box-danger box-solid">
												<div class="box-header with-border">
	 												Lista aggiornamenti
													<div class="box-tools pull-right">			
														<button data-widget="collapse" class="btn btn-box-tool"><i class="fa fa-minus"></i></button>
													</div>
												</div>
												<div class="box-body">
												
          					
																								
													<div class="row">														
														<div class="col-lg-12">															

															<div id="errorMsg" >
															</div>
														</div>          												
													</div>
 													<div class="clearfix"></div>
 													<div class="row" style="margin-top:20px;">
														<div class="col-lg-12">
														<a class="btn btn-primary" onClick="modalNuovaVersione()"><i class="fa fa-plus"> Nuova versione</i></a>
														</div>
														</div>
													<div class="row" style="margin-top:20px;">
														<div class="col-lg-12">
  															<table id="tabVersioni" class="table table-bordered table-hover dataTable table-striped" role="grid" width="100%">
 																<thead>
 																	<tr class="active">
 																		<th>ID</th>
 																		<th>Versione</th>
 																		<th>Data aggiornamento</th>
 																		<th>Note aggiornamento</th>
 																		<th>Azioni</th>							
 																	</tr>
 																</thead> 
 																<tbody>	
 																	<c:forEach items="${lista_versioni}" var="versione" varStatus="loop">	
	 																	<tr role="row" id="${versione.id}-${loop.index}">	
																			<td>${versione.id}</td>
																			<td>${versione.versione}</td>
																			<td><fmt:formatDate pattern = "dd/MM/yyyy" value = "${versione.data_aggiornamento}" /></td>
																			<td>${versione.note_aggiornamento}</td>
																			<td>
																			
																			<a href="#" class="btn btn-warning customTooltip" title="Click per modificare la versione" onclick="modalModificaVersione('${versione.id }','${versione.versione }','${versione.data_aggiornamento }','${versione.note_aggiornamento }')"><i class="fa fa-edit"></i></a>
																			<a href="#" class="btn btn-danger customTooltip" title="Click per eliminare la versione" onclick="modalYesOrNo('${versione.id }')"><i class="fa fa-trash"></i></a>
																			</td>
																			
																			
																			
																		</tr>
		 															</c:forEach>		
 																</tbody>
 															</table>  
 														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
            						<!-- /.box-body -->
          						</div>
          						<!-- /.box -->
        					</div>
        					<!-- /.col -->

			

							
	<form id="formNuovaVersione" name="formNuovaVersione">					
	 <div id="modalNuovaVersione" class="modal fade" role="dialog" aria-labelledby="myLargeModalLabel">
  
    <div class="modal-dialog modal-md" role="document">
    <div class="modal-content">
     <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Nuova versione</h4>
      </div>
       <div class="modal-body">
       <div class="row">
        <div class="col-xs-3">
      	<label>Versione</label>
		</div>
		<div class="col-xs-9">
      		<input class="form-control" id="versione" name="versione" type="text" required>
		</div>
  		 </div><br>
  		 <div class="row">
        <div class="col-xs-3">
      	<label>Data aggiornamento</label>
		</div>
		<div class="col-xs-9">
      		 <input class="form-control datepicker " id="data_aggiornamento" type="text" name="data_aggiornamento"  value="" data-date-format="dd/mm/yyyy" required>
		</div>
  		 </div><br>
  		 <div class="row">
        <div class="col-xs-3">
      	<label>Note versione</label>
		</div>
		<div class="col-xs-9">
      		<textarea class="form-control" id="note_versione" name="note_versione" rows="3" required></textarea>
		</div>
  		 </div><br>
  		 </div>
      <div class="modal-footer">
      <button class="btn btn-primary pull-right" type="submit">Salva</button>
      </div>
   
  </div>
  </div>
</div>
</form>


	<form id="formModificaVersione" name="formModificaVersione">					
	 <div id="modalModificaVersione" class="modal fade" role="dialog" aria-labelledby="myLargeModalLabel">
  
    <div class="modal-dialog modal-md" role="document">
    <div class="modal-content">
     <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Modifica versione</h4>
      </div>
       <div class="modal-body">
       <div class="row">
        <div class="col-xs-3">
      	<label>Versione</label>
		</div>
		<div class="col-xs-9">
      		<input class="form-control" id="versione_mod" name="versione_mod" type="text" required>
		</div>
  		 </div><br>
  		 <div class="row">
        <div class="col-xs-3">
      	<label>Data aggiornamento</label>
		</div>
		<div class="col-xs-9">
      		 <input class="form-control datepicker " id="data_aggiornamento_mod" type="text" name="data_aggiornamento_mod"  value="" data-date-format="dd/mm/yyyy" required>
		</div>
  		 </div><br>
  		 <div class="row">
        <div class="col-xs-3">
      	<label>Note versione</label>
		</div>
		<div class="col-xs-9">
      		<textarea class="form-control" id="note_versione_mod" name="note_versione_mod" rows="3" required></textarea>
		</div>
  		 </div><br>
  		 </div>
      <div class="modal-footer">
      <input class="form-control" id="id_versione" name="id_versione" type="hidden">
      <button class="btn btn-primary pull-right" type="submit">Salva</button>
      </div>
   
  </div>
  </div>
</div>
</form>




     					<div id="myModalError" class="modal fade" role="dialog" aria-labelledby="myLargeModalLabel">
    						<div class="modal-dialog" role="document">
    							<div class="modal-content">
     								<div class="modal-header">
        								<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        								<h4 class="modal-title" id="myModalLabel">Messaggio</h4>
      								</div>
       								<div class="modal-body">
										<div id="modalErrorDiv">				
										</div>	   
  										<div id="empty" class="label label-danger testo12"></div>
  		 							</div>
      								<div class="modal-footer">
        								<button type="button" class="btn btn-outline" data-dismiss="modal">Chiudi</button>
      								</div>
    							</div>
  							</div>
						</div> 
						
						
	 <div id="myModalYesOrNo" class="modal fade" role="dialog" aria-labelledby="myLargeModalsaveStato">
   
    <div class="modal-dialog modal-md" role="document">
    <div class="modal-content">
     <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Attenzione</h4>
      </div>
       <div class="modal-body">       
      	Sei sicuro di voler eliminare la versione?
      	</div>
      <div class="modal-footer">
      <input type="hidden" id="id_versione_elimina">
      <a class="btn btn-primary" onclick="eliminaVersione($('#id_versione_elimina').val())" >SI</a>
		<a class="btn btn-primary" onclick="$('#myModalYesOrNo').modal('hide')" >NO</a>
      </div>
    </div>
  </div>

</div>
 

							
						</section>
  					</div>
  					<!-- /.content-wrapper -->
  					
  					<t:dash-footer />	
  					
  					<t:control-sidebar />   
				
				</div>
				<!-- ./wrapper -->

			</jsp:attribute>


			<jsp:attribute name="extra_css">
			<link rel="stylesheet" type="text/css" href="//cdn.jsdelivr.net/bootstrap.daterangepicker/2/daterangepicker.css" />
			</jsp:attribute>

			<jsp:attribute name="extra_js_footer">
<script type="text/javascript" src="//cdn.jsdelivr.net/bootstrap.daterangepicker/2/daterangepicker.js"></script>
<script type="text/javascript" src="plugins/datejs/date.js"></script>
  				<script type="text/javascript">
  				



  				function modalNuovaVersione(modalNuovaVersione){

  				$('#modalNuovaVersione').modal();
  				}
  				
  				function modalModificaVersione(id_versione, versione, data_aggiornamento, note_aggiornamento){
  					
  					$('#id_versione').val(id_versione);
  					$('#versione_mod').val(versione);
  					
  					if(data_aggiornamento!=null && data_aggiornamento!=''){
  						$('#data_aggiornamento_mod').val(Date.parse(data_aggiornamento).toString("dd/MM/yyyy"));
  					}
  					$('#note_versione_mod').val(note_aggiornamento);
  					
  					$('#modalModificaVersione').modal();	
  					
  				}
  				
  				
  				function modalYesOrNo(id_versione){
  					
  					
  					$('#id_versione_elimina').val(id_versione);
  					$('#myModalYesOrNo').modal();
  				}
  				
  				
    				$(document).ready(function() {       
    					
    					
    					
   					table = $('#tabVersioni').DataTable({
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
    					      stateSave: true,
    					      order:[[0, "desc"]],
    					      columnDefs: [
    									 
    					                   { responsivePriority: 1, targets: 0 },	                  
    					                   { responsivePriority: 2, targets: 4 }
    					                  
    					                  /*  { orderable: false, targets: 6 }, */
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
    					               },
    					               {
    					                   extend: 'colvis',
    					                   text: 'Nascondi Colonne'
    					                   
    					               }
    					                         
    					                          ]
    					    	
    					      
    					    });
    					
    					
    					$('#tabVersioni thead th').each( function () {
    	        			var title = $('#tabVersioni thead th').eq( $(this).index() ).text();
    	        			$(this).append( '<div><input class="inputsearchtable" style="width:100%" type="text" /></div>');
    					} );
    					
    					
    					table.buttons().container()
    				  .appendTo( '#tabVersioni_wrapper .col-sm-6:eq(1)' );
    					
    					
    					table.buttons().container().appendTo( '#tabVersioni_wrapper .col-sm-6:eq(1)');
    					    $('.inputsearchtable').on('click', function(e){
    					       e.stopPropagation();    
    					    });
    				//DataTable
    				//table = $('#tabSt').DataTable();
    				//Apply the search
    				table.columns().eq( 0 ).each( function ( colIdx ) {
    				$( 'input', table.column( colIdx ).header() ).on( 'keyup', function () {
    				  table
    				      .column( colIdx )
    				      .search( this.value )
    				      .draw();
    				} );
    				} );  
    				table.columns.adjust().draw();
    					 	
    	
  						$('.removeDefault').each(function() {
  	   						$(this).removeClass('btn-default');
  						})    	
	    	
    					if (!$.fn.bootstrapDP && $.fn.datepicker && $.fn.datepicker.noConflict) {
		   					var datepicker = $.fn.datepicker.noConflict();
		   					$.fn.bootstrapDP = datepicker;
						}

						$('.datepicker').bootstrapDP({
							format: "dd/mm/yyyy",
	    					startDate: '-3d'
						});

						$('#formNuovoTipoVerifica').on('submit',function(e){
				
							$("#ulError").html("");
	    					e.preventDefault();
	    					nuovoTipoVerifica();
						});
	   
						$('#formModificaTipoVerifica').on('submit',function(e){
			
							$("#ulError").html("");
	    					e.preventDefault();
	    					modificaTipoVerifica();		
						}); 	      
	    			});


	    			$('#modalNuovaVersione').on('hidden.bs.modal', function (e) {
	    				$(document.body).css('padding-right', '0px');	 		
  					});

	    			
	    			$('#formNuovaVersione').on('submit', function(e){
	    				
	    				e.preventDefault();
	    				nuovaVersione();
	    				
	    			});

	    			$('#formModificaVersione').on('submit', function(e){
	    				
	    				e.preventDefault();
	    				modificaVersione();
	    				
	    			});
	    			
	    			function eliminaVersione(id_versione){
	    				 
	    				 var dataObj = {};
	    					dataObj.id_versione = id_versione;
	    									
	    				  $.ajax({
	    			    type: "POST",
	    			    url: "gestioneVersionePortale.do?action=elimina&id_versione="+ id_versione,
	    			    data: dataObj,
	    			    dataType: "json",
	    			    //if received a response from the server
	    			    success: function( data, textStatus) {
	    			    	
	    			    	
	    				 	pleaseWaitDiv.modal('hide');
	    				 	$('#myModalYesOrNo').hide();
	    				 	if(data.success){
	    				 		$('#myModalAllegatiArchivio').modal('hide');
	    				 		
	    				 		$('#modalErrorDiv').html(data.messaggio);
	    						$('#myModalError').removeClass();
	    						$('#myModalError').addClass("modal modal-success");
	    						$('#myModalError').modal('show');	
	    						
	    						$('#myModalError').on("hidden.bs.modal",function(){
	    							$('#myModalArchivio').modal("hide");
	    							//location.reload();
	    							 $('.modal-backdrop').hide();       
	    						});
	    						 $('.modal-backdrop').hide(); 
	    				 	}else{		 			
	    				 		$('#modalErrorDiv').html(data.messaggio);
	    						$('#myModalError').removeClass();
	    						$('#myModalError').addClass("modal modal-danger");
	    						
	    				 			$('#report_button').show();
	    				 			$('#visualizza_report').show();
	    				 			$('#myModalError').modal('show');
	    				 		}
	    			    	

	    			    },
	    			    error: function( data, textStatus) {
	    			  	  $('#myModalYesOrNo').modal('hide');
	    			  	  $('#myModalErrorContent').html(data.messaggio);
	    					  	$('#myModalError').removeClass();
	    						$('#myModalError').addClass("modal modal-danger");	  
	    						$('#report_button').show();
	    						$('#visualizza_report').show();
	    							$('#myModalError').modal('show');

	    			    }
	    			    });
	    			}


	    			
  				</script>
	</jsp:attribute> 
</t:layout>
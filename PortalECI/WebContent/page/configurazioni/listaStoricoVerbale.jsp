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
        				Storico Verbale
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
	 												Storico
													<div class="box-tools pull-right">			
														<button data-widget="collapse" class="btn btn-box-tool"><i class="fa fa-minus"></i></button>
													</div>
												</div>
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
														<div class="col-lg-12">															

															<div id="errorMsg" >
															</div>
														</div>          												
													</div>
 													<div class="clearfix"></div>
													<div class="row" style="margin-top:20px;">
														<div class="col-lg-12">
  															<table id="tabSt" class="table table-bordered table-hover dataTable table-striped" role="grid" width="100%">
 																<thead>
 																	<tr class="active">
 																		<th>ID</th>
 																		<th>Codice Verificatore</th>
 																		<th>Verificatore</th>
 																		<th>Numero verbale</th>
 																		<th>Cliente</th>
 																		<th>Indirizzo Cliente</th>
 																		<th>Localit� Cliente</th>		
 																		<th>Ubicazione Impianto</th>
 																		<th>Localit� Impianto</th>		
 																		<th>Provincia</th>		
 																		<th>Esito</th>		
 																		<th>Codice Commessa</th>		
 																		<th>Strumento Utilizzato</th>		
 																		<th>Data Verifica</th>		
 																		<th>Frequenza</th>	
 																		<th>Data Prossima Verifica</th>	
 																		<th>Ore Uomo</th>	
 																		<th>Tipologia Verifica</th>
 																		<th>Azioni</th>							
 																	</tr>
 																</thead> 
 																<tbody>	
 																	<c:forEach items="${lista_verbali}" var="verbale" varStatus="loop">	
	 																	<tr role="row" id="${verbale.id}-${loop.index}">	
																			<td>${verbale.id}</td>
																			<td>${verbale.codice_verificatore}</td>
																			<td>${verbale.verificatore}</td>
																			<td>${verbale.numero_verbale}</td>
																			<td>${verbale.cliente}</td>
																			<td>${verbale.indirizzo_cliente}</td>
																			<td>${verbale.localita_cliente }</td>
																			<td>${verbale.ubicazione_impianto}</td>
																			<td>${verbale.localita_impianto}</td>
																			<td>${verbale.provincia}</td>
																			<td>${verbale.esito}</td>
																			<td>${verbale.codice_commessa}</td>
																			<td>${verbale.strumento_utilizzato}</td>
																			<td><fmt:formatDate pattern = "dd/MM/yyyy" value = "${verbale.data_verifica}" /></td>
																			<td>${verbale.frequenza}</td>
																			<td><fmt:formatDate pattern = "dd/MM/yyyy" value = "${verbale.data_prossima_verifica}" /></td>
																			<td>${verbale.ore_uomo}</td>
																			<td>${verbale.tipologia_verifica}</td>
																			<td>
																			
																			<a href="#" class="btn btn-primary customTooltip" title="Click per visualizzare l'archivio" onclick="modalArchivio('${verbale.id }')"><i class="fa fa-archive"></i></a>
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

			

							
							
	 <div id="myModalArchivio" class="modal fade" role="dialog" aria-labelledby="myLargeModalLabel">
  
    <div class="modal-dialog modal-md" role="document">
    <div class="modal-content">
     <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Allegati</h4>
      </div>
       <div class="modal-body">
       <div class="row">
        <div class="col-xs-12">
       <div id="tab_allegati"></div>
</div>
  		 </div>
  		 </div>
      <div class="modal-footer">
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
  				
  				
  		  		function filtraDate(){
  		  			
  		  			var startDatePicker = $("#datarange").data('daterangepicker').startDate;
  		  		 	var endDatePicker = $("#datarange").data('daterangepicker').endDate;
  		  		 	dataString = "?action=filtra_date&dateFrom=" + startDatePicker.format('YYYY-MM-DD') + "&dateTo=" + 
  		  		 			endDatePicker.format('YYYY-MM-DD');
  		  		 	
  		  		 	 pleaseWaitDiv = $('#pleaseWaitDialog');
  		  			  pleaseWaitDiv.modal();

  		  		 	callAction("gestioneStoricoVerbale.do"+ dataString, false,true);

  		  		 	//exploreModal("gestioneListaVerbali.do", dataString, '#content_consuntivo');
  		  	}




  		  	function resetDate(){
  		  		pleaseWaitDiv = $('#pleaseWaitDialog');
  		  			  pleaseWaitDiv.modal();
  		  		callAction("gestioneStoricoVerbale.do");

  		  	}

  			
  		  	function formatDate(data){
  		  		
  		  		   var mydate =  Date.parse(data);
  		  		   
  		  		   if(!isNaN(mydate.getTime())){
  		  		   
  		  			var   str = mydate.toString("dd/MM/yyyy");
  		  		   }			   
  		  		   return str;	 		
  		  	}

  		   
  				
  				
/*   				var columsDatatables = [];

   				$("#tabSt").on( 'init.dt', function ( e, settings ) {

  				    var api = new $.fn.dataTable.Api( settings );
  				    var state = api.state.loaded();
  				 
  				    if(state != null && state.columns!=null){
  				    		console.log(state.columns);
  				    
  				    columsDatatables = state.columns;
  				    }
  				    $('#tabSt thead th').each( function () {
  				     	//if(columsDatatables.length==0  ){columsDatatables.push({search:{search:""}});}
  				    	   var title = $('#tabSt thead th').eq( $(this).index() ).text();
  				    	   
  				    		   $(this).append( '<div><input class="inputsearchtable" id="inputsearchtable_'+$(this).index()+'" style="width:100%" type="text" value="'+columsDatatables[$(this).index()].search.search+'" /></div>');
  				    	   
  				    	  
  				    	} );

  				} );  */

  				function modalArchivio(id_verbale){
  					 
  					 $('#tab_archivio').html("");
  					 
  					 dataString ="action=archivio&id_verbale="+ id_verbale;
  				    exploreModal("gestioneStoricoVerbale.do",dataString,"#tab_allegati",function(datab,textStatusb){
  				    });
  				$('#myModalArchivio').modal();
  				}
  				
    				$(document).ready(function() {       
    					
    					
    					
    					   var start = "${dateFrom}";
    		    		   	var end = "${dateTo}";


    		    		   	
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


    					table = $('#tabSt').DataTable({
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
    					                   { responsivePriority: 2, targets: 18 }
    					                  
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
    					
    					
    					$('#tabSt thead th').each( function () {
    	        			var title = $('#tabSt thead th').eq( $(this).index() ).text();
    	        			$(this).append( '<div><input class="inputsearchtable" style="width:100%" type="text" /></div>');
    					} );
    					
    					
    					table.buttons().container()
    				  .appendTo( '#tabSt_wrapper .col-sm-6:eq(1)' );
    					
    					
    					table.buttons().container().appendTo( '#tabSt_wrapper .col-sm-6:eq(1)');
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


	    			$('#myModalArchivio').on('hidden.bs.modal', function (e) {
	    				$(document.body).css('padding-right', '0px');	 		
  					});

	    			
	    			

	    			function eliminaAllegatoStorico(id_allegato){
	    				 
	    				 var dataObj = {};
	    					dataObj.id_allegato = id_allegato;
	    									
	    				  $.ajax({
	    			    type: "POST",
	    			    url: "gestioneStoricoVerbale.do?action=elimina_allegato&id_allegato="+ id_allegato,
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
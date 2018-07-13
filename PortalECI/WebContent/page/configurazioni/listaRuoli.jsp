<%@page import="java.util.ArrayList"%>
<%@page import="com.google.gson.JsonArray"%>
<%@page import="com.google.gson.Gson"%>

<%@page import="it.portalECI.DTO.RuoloDTO"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%

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
        				Lista Ruoli	
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
	 												Lista
													<div class="box-tools pull-right">
		
														<button data-widget="collapse" class="btn btn-box-tool">
															<i class="fa fa-minus"></i>
														</button>

													</div>
												</div>
												<div class="box-body">
													<div class="row">
														<div class="col-lg-12">
															<button class="btn btn-primary" onClick="nuovoInterventoFromModal('#modalNuovoRuolo')">
																Nuovo Ruolo
															</button>
															<div id="errorMsg" ></div>
														</div>
													</div>
 													<div class="clearfix"></div>
													<div class="row" style="margin-top:20px;">
														<div class="col-lg-12">
  															<table id="tabPM" class="table table-bordered table-hover dataTable table-striped" role="grid" width="100%">
 																<thead>
 																	<tr class="active">
 																		<td>ID</td>
 																		<th>Sigla</th>
 																		<th>Descrizione</th>
  																		<th>Action</th>
 																	</tr>
 																</thead>
 
 																<tbody> 
 																	<c:forEach items="${listaRuoli}" var="ruolo" varStatus="loop">
	 																	<tr role="row" id="${ruolo.id}-${loop.index}">

																			<td>${ruolo.id}</td>
																			<td>${ruolo.sigla}</td>
																			<td>${ruolo.descrizione}</td>
																			<td>	
																				<a href="#" onClick="modalModificaRuolo('${ruolo.id}','${ruolo.sigla}','${ruolo.descrizione}')" class="btn btn-warning "><i class="fa fa-edit"></i></a> 
																				<%-- <a href="#" onClick="modalEliminaRuolo('${ruolo.id}','${ruolo.descrizione}')" class="btn btn-danger "><i class="fa fa-remove"></i></a>	 --%>
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
  
							<div id="modalNuovoRuolo" class="modal  modal-fullscreen fade" role="dialog" aria-labelledby="myLargeModalLabel">
    							<div class="modal-dialog modal-lg" role="document">
    								<div class="modal-content">
     									<div class="modal-header">
        									<button type="button" class="close" data-dismiss="modal" aria-label="Close">
        										<span aria-hidden="true">&times;</span>
        									</button>
        									<h4 class="modal-title" id="myModalLabel">Nuovo Ruolo</h4>
      									</div>
      									<form class="form-horizontal"  id="formNuovoRuolo">
       										<div class="modal-body">
       
												<div class="nav-tabs-custom">	   
            										<div class="tab-content">
              											<div class="tab-pane  table-responsive active" id="nuovoRuolo">	            
                											<div class="form-group">
          														<label for="user" class="col-sm-2 control-label">Sigla:</label>
         														<div class="col-sm-10">
         															<input class="form-control" id="sigla" type="text" name="sigla" value="" maxlength="2"  required />
     															</div>     	 
   															</div>    
    														
    														<div class="form-group">
          														<label for="nome" class="col-sm-2 control-label">Descrizione:</label>
         														<div class="col-sm-10">
         															<input class="form-control" id="descrizione" type="text" name="descrizione" value="" required />         
			
     															</div>
   															</div>

  															<%--   PERMESSI
 															<c:forEach items="${listaPermessi}" var="permesso" varStatus="loop">
 				  												<div class="form-group">
        															<label for="comnpany" class="col-sm-4 control-label">${permesso.chiave_permesso}</label>
        															<div class="col-sm-8">
             															<input class="form-control" id="${permesso.idPermesso}" type="checkbox" name="${permesso.idPermesso}"  value="${permesso.idPermesso}"/>
                      
    																</div>
     															</div>
															</c:forEach> --%>
       
		 												</div>
              											<!-- /.tab-pane -->
            										</div>
            										<!-- /.tab-content -->
          										</div>	                
  												<div id="empty" class="label label-danger testo12"></div>
  											</div>
      										<div class="modal-footer">
												<span id="ulError" class="pull-left"></span>
												<button type="submit" class="btn btn-danger" >Salva</button>
      										</div>
        								</form>
    								</div>
  								</div>
							</div>
							<div id="modalModificaRuolo" class="modal  modal-fullscreen fade" role="dialog" aria-labelledby="myLargeModalLabel">
    							<div class="modal-dialog modal-lg" role="document">
    								<div class="modal-content">
     									<div class="modal-header">
        									<button type="button" class="close" data-dismiss="modal" aria-label="Close">
        										<span aria-hidden="true">&times;</span>
        									</button>
        									<h4 class="modal-title" id="myModalLabel">Modifica Ruolo</h4>
      									</div>
      									<form class="form-horizontal"  id="formModificaRuolo">
       										<div class="modal-body">	       
												<div class="nav-tabs-custom">	     
            										<div class="tab-content">
              											<div class="tab-pane  table-responsive active" id="modificaRuolo">
	
         													<input class="form-control" id="modid" name="modid" value="" type="hidden" />                   
                											<div class="form-group">
          														<label for="moduser" class="col-sm-2 control-label">Sigla:</label>

         														<div class="col-sm-4">
         															<input class="form-control" id="modsigla" type="text" name="modsigla" value="" maxlength="2"  />
     															</div>    
   															</div>    

    														<div class="form-group">
          														<label for="modnome" class="col-sm-2 control-label">Descrizione:</label>

         														<div class="col-sm-10">
         															<input class="form-control" id="moddescrizione" type="text" name="moddescrizione" value=""  />         			
     															</div>
   															</div>       
	 													</div>
              											<!-- /.tab-pane -->
           											</div>
            										<!-- /.tab-content -->
          										</div>	        
  												
  												<div id="empty" class="label label-danger testo12"></div>
  		 									</div>
      										<div class="modal-footer">
												<span id="ulError" class="pull-left"></span>
												<button type="submit" class="btn btn-danger" >Salva</button>
      										</div>
        								</form>
    								</div>
  								</div>
							</div>

							<div id="modalEliminaRuolo" class="modal fade" role="dialog" aria-labelledby="myLargeModalLabel">
    							<div class="modal-dialog modal-sm" role="document">
       								<div class="modal-content">
	    
	    								<div class="modal-header">
        									<button type="button" class="close" data-dismiss="modal" aria-label="Close">
        										<span aria-hidden="true">&times;</span>
        									</button>
        									<h4 class="modal-title" id="myModalLabel">Attenzione</h4>
      									</div>
    									<div class="modal-content">
       										<div class="modal-body" id="">
			     
												<input class="form-control" id="idElimina" name="idElimina" value="" type="hidden" />
		
												Sei Sicuro di voler eliminare il ruolo 
												<span id="ruoloElimina"></span>              
  		 									</div>      
    									</div>
    									<div class="modal-footer">
    										<button type="button" class="btn btn-default pull-left" data-dismiss="modal">Annulla</button>
    										<button type="button" class="btn btn-danger" onClick="eliminaRuolo()">Elimina</button>
    									</div>
  									</div>
    							</div>

							</div>

							<div id="myModalError" class="modal fade" role="dialog" aria-labelledby="myLargeModalLabel">
    							<div class="modal-dialog modal-sm" role="document">
        							<div class="modal-content">
	    
    									<div class="modal-header">
        									<button type="button" class="close" data-dismiss="modal" aria-label="Close">
        										<span aria-hidden="true">&times;</span>
        									</button>
        									<h4 class="modal-title" id="myModalLabel">Attenzione</h4>
      									</div>
    									<div class="modal-content">
       										<div class="modal-body" id="myModalErrorContent">        	        
  		 									</div>      
    									</div>
     									<div class="modal-footer">
    										<button type="button" class="btn btn-outline" data-dismiss="modal">Chiudi</button>
    									</div>
  									</div>
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


	</jsp:attribute>

	<jsp:attribute name="extra_js_footer">
		<script src="plugins/jquery.appendGrid/jquery.appendGrid-1.6.3.js"></script>
		<script type="text/javascript" src="https://ajax.aspnetcdn.com/ajax/jquery.validate/1.13.1/jquery.validate.min.js"></script>

		<script type="text/javascript">

	   	</script>

  		<script type="text/javascript">

    		$(document).ready(function() {       	

    			table = $('#tabPM').DataTable({
  	      			paging: true, 
  	      			ordering: true,
  	      			info: true, 
  	      			searchable: false, 
  	      			targets: 0,
  	      			responsive: true,
  	      			scrollX: false,
  	      			columnDefs: [
						{ responsivePriority: 1, targets: 0 },
  	                   	{ responsivePriority: 2, targets: 1 }
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
  	               	}
  	              	/*  ,{
  	             		text: 'I Miei Strumenti',
                 		action: function ( e, dt, node, config ) {
                 			explore('listaCampioni.do?p=mCMP');
                 		},
                 		className: 'btn-info removeDefault'
    				},{
  	             		text: 'Tutti gli Strumenti',
                 		action: function ( e, dt, node, config ) {
                 			explore('listaCampioni.do');
                 		},
                 		className: 'btn-info removeDefault'
    				} */  	                        
  	          		]  	    	  	      
  	    		});    	
  				
    			table.buttons().container().appendTo( '#tabPM_wrapper .col-sm-6:eq(1)');
  
  				$('#tabPM thead th').each( function () {
      				var title = $('#tabPM thead th').eq( $(this).index() ).text();
      				$(this).append( '<div><input style="width:100%" type="text" placeholder="'+title+'" /></div>');
  				} );

  				// DataTable
				table = $('#tabPM').DataTable();
  				// Apply the search
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

				$('#formNuovoRuolo').on('submit',function(e){
			
					$("#ulError").html("");
	    			e.preventDefault();
	    			nuovoRuolo();

				});
   
				$('#formModificaRuolo').on('submit',function(e){
			
					$("#ulError").html("");
	    			e.preventDefault();
	    			modificaRuolo();

				}); 
		      
	    	});


	    	var validator = $("#formNuovoRuolo").validate({
	    		showErrors: function(errorMap, errorList) {
	    	  
	    	    	this.defaultShowErrors();
	    	  	},
	    	  	errorPlacement: function(error, element) {
	    		  	$("#ulError").html("<span class='label label-danger'>Compilare tutti i campi obbligatori</span>");
	    		},
	    		highlight: function(element) {
	    			$(element).closest('.form-group').addClass('has-error');
	    		    $(element).closest('.ui-widget-content input').addClass('error');	    		        
	    		},
	    		unhighlight: function(element) {
	    			$(element).closest('.form-group').removeClass('has-error');
	    		    $(element).closest('.ui-widget-content input').removeClass('error');	    		       
	    		}
	   		});
	  		
	    	$('#myModalError').on('hidden.bs.modal', function (e) {
				if($( "#myModalError" ).hasClass( "modal-success" )){
					callAction("listaRuoli.do");
				} 		
  			});
  		</script>
	</jsp:attribute> 
</t:layout>  
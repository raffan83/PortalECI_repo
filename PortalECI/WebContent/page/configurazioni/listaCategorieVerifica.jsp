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
        				Lista Categorie Verifica
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
														<button data-widget="collapse" class="btn btn-box-tool"><i class="fa fa-minus"></i></button>
													</div>
												</div>
												<div class="box-body">													
													<div class="row">
														<div class="col-lg-12">															
															<c:if test="${user.checkPermesso('NEW_CATEGORIA_VERIFICA')}">
																<button class="btn btn-primary" onClick="nuovoInterventoFromModal('#modalNuovaCategoriaVerifica')">Nuova Categoria Verifica</button>
															</c:if>
															<div id="errorMsg" >
															</div>
														</div>														
													</div>
 													<div class="clearfix"></div>
													<div class="row" style="margin-top:20px;">
														<div class="col-lg-12">
  															<table id="tabPM" class="table table-bordered table-hover dataTable table-striped" role="grid" width="100%">
 																<thead>
 																	<tr class="active">
 																		<td>ID</td>
 																		<td>Sigla</td>
 																		<th>Descrizione</th>
 																		<th>Codice</th>
 																		<th>Action</th>						
 																	</tr>
 																</thead> 
 																<tbody>	
 																	<c:forEach items="${listaCategorieVerifica}" var="categoria" varStatus="loop">	
	 																	<tr role="row" id="${categoria.id}-${loop.index}">	
																			<td>${categoria.id}</td>
																			<td>${categoria.sigla}</td>
																			<td>${categoria.descrizione}</td>
																			<td>${categoria.codice}</td>
																			<td>																					
																				<c:if test="${user.checkPermesso('UPD_CATEGORIA_VERIFICA')}">
																					<a href="#" onClick="modalModificaCategoriaVerifica('${categoria.id}','${categoria.sigla}','${categoria.descrizione}','${categoria.codice}')" class="btn btn-warning "><i class="fa fa-edit"></i></a>
																				</c:if> 																				
																				<c:if test="${user.checkPermesso('DEL_CATEGORIA_VERIFICA')}"> 
																					<a href="#" onClick="modalEliminaCategoriaVerifica('${categoria.id}','${categoria.descrizione}')" class="btn btn-danger "><i class="fa fa-remove"></i></a>
																				</c:if>
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

							<div id="modalNuovaCategoriaVerifica" class="modal  modal-fullscreen fade" role="dialog" aria-labelledby="myLargeModalLabel">
    							<div class="modal-dialog modal-lg" role="document">
    								<div class="modal-content">
     									<div class="modal-header">
        									<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        									<h4 class="modal-title" id="myModalLabel">Nuova Categoria Verifica</h4>
      									</div>
      									<form class="form-horizontal"  id="formNuovaCategoriaVerifica">
       										<div class="modal-body">       
												<div class="nav-tabs-custom">     
            										
            										<div class="tab-content">
              											<div class="tab-pane  table-responsive active" id="nuovaCategoriaVerifica">            
                											<div class="form-group">
          														<label for="descrizione" class="col-sm-2 control-label">Descrizione:</label>
         														
         														<div class="col-sm-10">
         															<input class="form-control" id="descrizione" type="text" name="descrizione" value="" required />
     															</div>	     
   															</div>    

    														<div class="form-group">
          														<label for="codice" class="col-sm-2 control-label">Codice:</label>
         														<div class="col-sm-10">
         															<input class="form-control" id="codice" type="text" name="codice" value="" required />	
     															</div>
   															</div>
    														<div class="form-group">
          														<label for="sigla" class="col-sm-2 control-label">Sigla:</label>
         														<div class="col-sm-10">
         															<input class="form-control" id="sigla" type="text" name="sigla" value="" required />	
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

							<div id="modalModificaCategoriaVerifica" class="modal  modal-fullscreen fade" role="dialog" aria-labelledby="myLargeModalLabel">
    							<div class="modal-dialog modal-lg" role="document">
    								<div class="modal-content">
     									<div class="modal-header">
        									<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        									<h4 class="modal-title" id="myModalLabel">Modifica Categoria Verifica</h4>
      									</div>
      									<form class="form-horizontal"  id="formModificaCategoriaVerifica">
       										<div class="modal-body">	       
												<div class="nav-tabs-custom">	  
            										<div class="tab-content">
              											<div class="tab-pane  table-responsive active" id="modificaCategoriaVerifica">
         													<input class="form-control" id="modid" name="modid" value="" type="hidden" />	        
            
                											<div class="form-group">
         														<label for="moddescrizione" class="col-sm-2 control-label">Descrizione:</label>
         														<div class="col-sm-10">
         															<input class="form-control" id="moddescrizione" type="text" name="moddescrizione" value=""  />
     															</div>	     	 
   															</div>    

    														<div class="form-group">
          														<label for="modcodice" class="col-sm-2 control-label">Codice:</label>	
         														<div class="col-sm-10">
         															<input class="form-control" id="modcodice" type="text" name="modcodice" value=""  />	         			
     															</div>
   															</div>           
                											<div class="form-group">
         														<label for="modsigla" class="col-sm-2 control-label">Sigla:</label>
         														<div class="col-sm-10">
         															<input class="form-control" id="modsigla" type="text" name="modsigla" value=""  />
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
							
							<div id="modalEliminaCategoriaVerifica" class="modal fade" role="dialog" aria-labelledby="myLargeModalLabel">
    							<div class="modal-dialog modal-sm" role="document">
        							<div class="modal-content">	    
    									
    									<div class="modal-header">
        									<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        									<h4 class="modal-title" id="myModalLabel">Attenzione</h4>
      									</div>
    									<div class="modal-content">
       										<div class="modal-body" id="">								     
												<input class="form-control" id="idElimina" name="idElimina" value="" type="hidden" />		
												Sei Sicuro di voler eliminare la categoria 
												<span id="descrizioneElimina"></span>                
  		 									</div>      
    									</div>
   										<div class="modal-footer">
    										<button type="button" class="btn btn-default pull-left" data-dismiss="modal">Annulla</button>
    										<button type="button" class="btn btn-danger" onClick="eliminaCategoriaVerifica()">Elimina</button>
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
  	               			}]  	    	  	      
  	    				});    	
  						
    					table.buttons().container().appendTo( '#tabPM_wrapper .col-sm-6:eq(1)');
  
	 					$('#tabPM thead th').each( function () {
      						var title = $('#tabPM thead th').eq( $(this).index() ).text();
      						$(this).append( '<div><input style="width:100%" type="text" placeholder="'+title+'" /></div>');
  						});

  						// DataTable
						table = $('#tabPM').DataTable();
  						
  						// Apply the search
  						table.columns().eq( 0 ).each( function ( colIdx ) {
      						$( 'input', table.column( colIdx ).header() ).on( 'keyup', function () {
          						table
              						.column( colIdx )
              						.search( this.value )
              						.draw();
      						});
  						}); 
  						
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

						$('#formNuovaCategoriaVerifica').on('submit',function(e){
				
							$("#ulError").html("");
	    					e.preventDefault();
	    					nuovaCategoriaVerifica();
						});
	   
						$('#formModificaCategoriaVerifica').on('submit',function(e){
			
							$("#ulError").html("");
	    					e.preventDefault();
	    					modificaCategoriaVerifica();		
						}); 	      
	    			});

	    			var validator = $("#formNuovaCategoriaVerifica").validate({
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
							callAction("listaCategorieVerifica.do");
						}		 		
  					});

  				</script>
	</jsp:attribute> 
</t:layout>
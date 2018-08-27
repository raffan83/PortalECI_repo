<%@page import="java.util.ArrayList"%>
<%@page import="com.google.gson.JsonArray"%>
<%@page import="com.google.gson.Gson"%>

<%@page import="it.portalECI.DTO.UtenteDTO"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<%
	UtenteDTO utente = (UtenteDTO)request.getSession().getAttribute("userObj");
	request.setAttribute("user",utente);
	//System.out.println("***"+listaUtentiJson);	
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
        				Lista Company
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
															<c:if test="${user.checkPermesso('NEW_COMPANY')}">
																<button class="btn btn-primary" onClick="nuovoInterventoFromModal('#modalNuovaCompany')">Nuova Company</button>
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
 																		<th>Denominazione</th>
 																		<th>Partita Iva</th>
 																		<th>Indirizzo</th>
 																		<th>Comune</th>
 																		<th>Cap</th>
 																		<th>e-mail</th>
 																		<th>Telefono</th>
 																		<th>Codice Affiliato</th>
  																		<th>Action</th>
 																	</tr>
 																</thead> 
 																<tbody>	
 																	<c:forEach items="${listaCompany}" var="company" varStatus="loop">	
	 																	<tr role="row" id="${company.id}-${loop.index}">	
																			<td>${company.id}</td>
																			<td>${company.denominazione}</td>
																			<td>${company.pIva}</td>
																			<td>${company.indirizzo}</td>
																			<td>${company.comune}</td>
																			<td>${company.cap}</td>
																			<td>${company.mail}</td>
																			<td>${company.telefono}</td>
																			<td>${company.codAffiliato}</td>
																			<td>	
																				<c:if test="${user.checkPermesso('UPD_COMPANY')}">
																					<a href="#" onClick="modalModificaCompany('${company.id}','${company.denominazione}','${company.pIva}','${company.indirizzo}','${company.comune}','${company.cap}','${company.mail}','${company.telefono}','${company.codAffiliato}')" class="btn btn-warning "><i class="fa fa-edit"></i></a>
																				</c:if> 
																				<%-- <a href="#" onClick="modalEliminaCompany('${company.id}','${company.denominazione}')" class="btn btn-danger "><i class="fa fa-remove"></i></a>	 --%>	
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

							<div id="modalNuovaCompany" class="modal  modal-fullscreen fade" role="dialog" aria-labelledby="myLargeModalLabel">
    							<div class="modal-dialog modal-lg" role="document">
    								<div class="modal-content">
     									<div class="modal-header">
        									<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        									<h4 class="modal-title" id="myModalLabel">Nuova Company</h4>
      									</div>
      									<form class="form-horizontal"  id="formNuovaCompany">
       										<div class="modal-body">       
												<div class="nav-tabs-custom">     
            										
            										<div class="tab-content">
              											<div class="tab-pane  table-responsive active" id="nuovaCompany">            
                											<div class="form-group">
          														<label for="denominazione" class="col-sm-2 control-label">Denominazione:</label>
         														
         														<div class="col-sm-10">
         															<input class="form-control" id="denominazione" type="text" name="denominazione" value="" required />
     															</div>	     
   															</div>    

    														<div class="form-group">
          														<label for="pIva" class="col-sm-2 control-label">Partita Iva:</label>
         														<div class="col-sm-10">
         															<input class="form-control" id="pIva" type="text" name="pIva" value="" required />	
     															</div>
   															</div>
	    
								    						<div class="form-group">
        														<label for="indirizzo" class="col-sm-2 control-label">Indirizzo:</label>
        														<div class="col-sm-10">
                      												<input class="form-control required" id="indirizzo" type="text" name="indirizzo"  value="" required/>
    															</div>
    														</div>
    														<div class="form-group">
        														<label for="comune" class="col-sm-2 control-label">Comune:</label>
        														<div class="col-sm-10">
                      												<input class="form-control required" id="comune" type="text" name="comune"  value="" required/>
    															</div>
    														</div>
    														<div class="form-group">
        														<label for="cap" class="col-sm-2 control-label">CAP:</label>
        														<div class="col-sm-10">
                      												<input class="form-control required" id="cap" type="text" name="cap"  value="" required/>
    															</div>
    														</div>
    														<div class="form-group">
        														<label for="mail" class="col-sm-2 control-label">E-mail:</label>
        														<div class="col-sm-10">
                      												<input class="form-control required" type="email" id="mail" type="text" name="mail"  value="" required/>
    															</div>
    														</div>
     														<div class="form-group">
        														<label for="telefono" class="col-sm-2 control-label">Telefono:</label>
        														<div class="col-sm-10">
                      												<input class="form-control required" id="telefono" type="text" name="telefono"  value="" required/>
    															</div>
     														</div>
     														<div class="form-group">
        														<label for="codAffiliato" class="col-sm-2 control-label">Codice Affiliato:</label>
        														<div class="col-sm-10">
                      												<input class="form-control required" id="codAffiliato" type="text" name="codAffiliato"  value="" required/>
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

							<div id="modalModificaCompany" class="modal  modal-fullscreen fade" role="dialog" aria-labelledby="myLargeModalLabel">
    							<div class="modal-dialog modal-lg" role="document">
    								<div class="modal-content">
     									<div class="modal-header">
        									<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        									<h4 class="modal-title" id="myModalLabel">Modifica Comnpany</h4>
      									</div>
      									<form class="form-horizontal"  id="formModificaCompany">
       										<div class="modal-body">	       
												<div class="nav-tabs-custom">	  
            										<div class="tab-content">
              											<div class="tab-pane  table-responsive active" id="modificaCompany">
         													<input class="form-control" id="modid" name="modid" value="" type="hidden" />	        
            
                											<div class="form-group">
         														<label for="moddenominazione" class="col-sm-2 control-label">Denominazione:</label>
         														<div class="col-sm-10">
         															<input class="form-control" id="moddenominazione" type="text" name="moddenominazione" value=""  />
     															</div>	     	 
   															</div>    

    														<div class="form-group">
          														<label for="modpIva" class="col-sm-2 control-label">Partita IVA:</label>	
         														<div class="col-sm-10">
         															<input class="form-control" id="modpIva" type="text" name="modpIva" value=""  />	         			
     															</div>
   															</div>    
    
    														<div class="form-group">
        														<label for="modindirizzo" class="col-sm-2 control-label">Indirizzo:</label>
        														<div class="col-sm-10">
                      												<input class="form-control required" id="modindirizzo" type="text" name="modindirizzo"  value="" />
																</div>
    														</div>
    														<div class="form-group">
        														<label for="modcomune" class="col-sm-2 control-label">Comune:</label>
        														<div class="col-sm-10">
                      												<input class="form-control required" id="modcomune" type="text" name="modcomune"  value="" />
    															</div>
    														</div>
    														<div class="form-group">
        														<label for="modcap" class="col-sm-2 control-label">CAP:</label>
        														<div class="col-sm-10">
                      												<input class="form-control required" id="modcap" type="text" name="modcap"  value="" />
    															</div>
    														</div>
    														<div class="form-group">
        														<label for="modemail" class="col-sm-2 control-label">E-mail:</label>
        														<div class="col-sm-10">
                      												<input class="form-control required" type="email" id="modmail" type="text" name="modmail"  value="" />
    															</div>
    														</div>
     														<div class="form-group">
        														<label for="modtelefono" class="col-sm-2 control-label">Telefono:</label>
        														<div class="col-sm-10">
                      												<input class="form-control required" id="modtelefono" type="text" name="modtelefono"  value="" />
    															</div>
     														</div>
     														<div class="form-group">
        														<label for="modtcodAffiliato" class="col-sm-2 control-label">Codice Affiliato:</label>
        														<div class="col-sm-10">
                      												<input class="form-control required" id="modcodAffiliato" type="text" name="modcodAffiliato"  value="" />
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
							
							<div id="modalEliminaCompany" class="modal fade" role="dialog" aria-labelledby="myLargeModalLabel">
    							<div class="modal-dialog modal-sm" role="document">
        							<div class="modal-content">	    
    									
    									<div class="modal-header">
        									<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        									<h4 class="modal-title" id="myModalLabel">Attenzione</h4>
      									</div>
    									<div class="modal-content">
       										<div class="modal-body" id="">								     
												<input class="form-control" id="idElimina" name="idElimina" value="" type="hidden" />		
												Sei Sicuro di voler eliminare la company 
												<span id="denominazioneElimina"></span>                
  		 									</div>      
    									</div>
   										<div class="modal-footer">
    										<button type="button" class="btn btn-default pull-left" data-dismiss="modal">Annulla</button>
    										<button type="button" class="btn btn-danger" onClick="eliminaCompany()">Elimina</button>
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
  	                   			{ responsivePriority: 2, targets: 1 },
  	                   			{ responsivePriority: 3, targets: 2 },
  	                   			{ responsivePriority: 4, targets: 6 }
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

						$('#formNuovaCompany').on('submit',function(e){
				
							$("#ulError").html("");
	    					e.preventDefault();
	    					nuovaCompany();
						});
	   
						$('#formModificaCompany').on('submit',function(e){
			
							$("#ulError").html("");
	    					e.preventDefault();
	    					modificaCompany();		
						}); 	      
	    			});

	    			var validator = $("#formNuovaCompany").validate({
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
							callAction("listaCompany.do");
						}		 		
  					});

  				</script>
	</jsp:attribute> 
</t:layout>
  
 

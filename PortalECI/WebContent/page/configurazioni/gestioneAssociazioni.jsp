<%@page import="java.util.ArrayList"%>
<%@page import="com.google.gson.JsonArray"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="it.portalECI.DTO.UtenteDTO"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<%
	UtenteDTO utente = (UtenteDTO)request.getSession().getAttribute("userObj");
	ArrayList<UtenteDTO> listaUtentiarr =(ArrayList<UtenteDTO>)request.getSession().getAttribute("listaUtenti");

	Gson gson = new Gson();
	JsonArray listaUtentiJson = gson.toJsonTree(listaUtentiarr).getAsJsonArray();
	request.setAttribute("listaUtentiJson", listaUtentiJson);
	request.setAttribute("utente", utente);
	
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
        				Lista Utenti
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
       										<div class="nav-tabs-custom">
						        	    		<ul id="mainTabs" class="nav nav-tabs">
						            	  			<li class="active">
						              					<a href="#utentiruoli" data-toggle="tab" aria-expanded="true"   id="utentiruoliTab">
						              						Associazione Utenti Ruoli
						              					</a>
							              			</li>
							              			<li>
								              			<a href="#ruoliutenti" data-toggle="tab" aria-expanded="false"   id="ruoliutentiTab">
								              				Associazione Ruoli Utenti
								              			</a>
								              		</li>
								              		<li>
								              			<a href="#ruolipermessi" data-toggle="tab" aria-expanded="false"   id="ruolipermessiTab">
							    	          				Associazione Ruoli Permessi
						    	    	      			</a>
						        	    	  		</li>
						        	    	  		<li>
								              			<a href="#verificatoricategoria" data-toggle="tab" aria-expanded="false"   id="verificatoricategoriaTab">
							    	          				Associazione Verificatore Categoria
						    	    	      			</a>
						    	    	      			
						    	    	      			
						        	    	  		</li>
						        	    	  		
						        	    	  		<li>
								              			<a href="#utentiComunicazioni" data-toggle="tab" aria-expanded="false"   id="utentiComunicazioneTab">
							    	          				Associazione Utente Tipo Comunicazione
						    	    	      			</a>
						        	    	  		</li>
						            			</ul>
						            			<div class="tab-content">
							              			<div class="tab-pane active" id="utentiruoli">
		
														<div class="form-group">
											           		<label>Utenti</label>
											                <select name="selectUtente" id="selectUtente" data-placeholder="Seleziona Utente..."  class="form-control selectUtente" aria-hidden="true" data-live-search="true">
											                	<option value=""></option>
											                    <c:forEach items="${listaUtenti}" var="utente">
											                    	<option value="${utente.id}">${utente.nominativo}</option> 
											                    </c:forEach>									
											                </select>
											        	</div>
										    	    	<div class="row">
															<div class="col-xs-12">												
														 		<div id="boxLista" class="box box-danger box-solid">
																	<div class="box-header with-border">
																 		Lista Ruoli
																		<div class="box-tools pull-right">															
																			<button data-widget="collapse" class="btn btn-box-tool"><i class="fa fa-minus"></i></button>													
																		</div>
																	</div>
																	<div class="box-body">
																		<div id="posTabRuoli">LISTA VUOTA</div>
																	</div>
																</div>
															</div>
														</div>
						    						</div> 						
													<div class="tab-pane" id="ruoliutenti">
							            	    		<div class="form-group">
										    	       		<label>Ruoli</label>
										        	        <select name="selectRuoloUtente" id="selectRuoloUtente" data-placeholder="Seleziona Ruolo..."  class="form-control selectRuoloUtente" aria-hidden="true" data-live-search="true">
										            	        <option value=""></option>
										                	    <c:forEach items="${listaRuoli}" var="ruolo">
										                    	   	<option value="${ruolo.id}">${ruolo.sigla}</option> 
											                    </c:forEach>									
											                </select>
											        	</div>
											        	<div class="row">
															<div class="col-xs-12">												
														 		<div id="boxLista" class="box box-danger box-solid">
																	<div class="box-header with-border">
																 		Lista Utenti
																		<div class="box-tools pull-right">															
																			<button data-widget="collapse" class="btn btn-box-tool"><i class="fa fa-minus"></i></button>													
																		</div>
																	</div>
																	<div class="box-body">
																		<div id="posTabUtenti">LISTA VUOTA</div>
																	</div>
																</div>
															</div>
														</div>						         
											 		</div>
								
							    	          		<!-- /.tab-pane -->
							        	     	 	<!-- /.tab-pane -->
						    	        	  		<div class="tab-pane" id="ruolipermessi">
						        	        			<div class="form-group">
										        	    	<label>Ruoli</label>
									    	        	    <select name="selectRuolo" id="selectRuolo" data-placeholder="Seleziona Ruolo..."  class="form-control selectRuolo" aria-hidden="true" data-live-search="true">
									        	        		<option value=""></option>
										                    	<c:forEach items="${listaRuoli}" var="ruolo">
											                    	<option value="${ruolo.id}">${ruolo.sigla}</option> 
											                    </c:forEach>									
											                </select>
											        	</div>
											        	<div class="row">
															<div class="col-xs-12">												
														 		<div id="boxLista" class="box box-danger box-solid">
																	<div class="box-header with-border">
																 		Lista Permessi
																		<div class="box-tools pull-right">															
																			<button data-widget="collapse" class="btn btn-box-tool"><i class="fa fa-minus"></i></button>													
																		</div>
																	</div>
																	<div class="box-body">
																		<div id="posTabPermessi">LISTA VUOTA</div>
																	</div>
																</div>
															</div>
														</div>								         
										 			</div>				
										 			<div class="tab-pane" id="verificatoricategoria">
		
														<div class="form-group">
											           		<label>Verificatori</label>
											                <select name="selectVerificatore" id="selectVerificatore" data-placeholder="Seleziona Utente..."  class="form-control selectUtente" aria-hidden="true" data-live-search="true">
											                	<option value=""></option>
											                    <c:forEach items="${listaUtenti}" var="utente">
											                    	<option value="${utente.id}">${utente.nominativo}</option> 
											                    </c:forEach>									
											                </select>
											        	</div>
										    	    	<div class="row">
															<div class="col-xs-12">												
														 		<div id="boxLista" class="box box-danger box-solid">
																	<div class="box-header with-border">
																 		Lista Categorie
																		<div class="box-tools pull-right">															
																			<button data-widget="collapse" class="btn btn-box-tool"><i class="fa fa-minus"></i></button>													
																		</div>
																	</div>
																	<div class="box-body">
																		<div id="posTabCategorie">LISTA VUOTA</div>
																	</div>
																</div>
															</div>
														</div>
						    						</div> 			
								              		<!-- /.tab-pane -->
								              		
								              		
								              		<div class="tab-pane" id="utentiComunicazioni">
		
														<div class="form-group">
											           		<label>Utenti</label>
											                <select name="selectUtenteCom" id="selectUtenteCom" data-placeholder="Seleziona Utente..."  class="form-control selectUtenteCom" aria-hidden="true" data-live-search="true">
											                	<option value=""></option>
											                    <c:forEach items="${listaUtenti}" var="utente">
											                    	<option value="${utente.id}">${utente.nominativo}</option> 
											                    </c:forEach>									
											                </select>
											        	</div>
										    	    	<div class="row">
															<div class="col-xs-12">												
														 		<div id="boxLista" class="box box-danger box-solid">
																	<div class="box-header with-border">
																 		Tipo Comunicazione
																		<div class="box-tools pull-right">															
																			<button data-widget="collapse" class="btn btn-box-tool"><i class="fa fa-minus"></i></button>													
																		</div>
																	</div>
																	<div class="box-body">
																		 <div id="posTabComunicazioni">LISTA VUOTA</div>
																	</div>
																</div>
															</div>
														</div>
						    						</div> 	
							    	        	</div>
							        	    	<!-- /.tab-content -->
							          		</div>       						
       									</div>
	        						</div>
    	    					</div>
       						</div>
	        			</div>
    	    		</div>
	
					<div id="myModalError" class="modal fade" role="dialog" aria-labelledby="myLargeModalLabel">
			    		<div class="modal-dialog modal-sm" role="document">
	    		    		<div class="modal-content">	    
	    						<div class="modal-header">
	        						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        						<h4 class="modal-title" id="myModalLabel">Attenzione</h4>
	      						</div>
			    				<div class="modal-content">
			       					<div class="modal-body" id="myModalErrorContent">		      	        
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

  				$("#selectRuolo").change(function(e){
        			//get the form data using another method 
        			var ruolo = $("#selectRuolo").val();    

		        	dataString ="idRuolo="+ ruolo;
        			exploreModal("listaPermessi.do",dataString,"#posTabPermessi",function(data,textStatus){
        			});     
 	 			});
			
  				$("#selectRuoloUtente").change(function(e){		
        			var ruolo = $("#selectRuoloUtente").val();
        			dataString ="idRuolo="+ ruolo;
        			exploreModal("listaUtenti.do",dataString,"#posTabUtenti",function(data,textStatus){
        			});       
 	 			});
				
  				$("#selectUtente").change(function(e){	
        			var utente = $("#selectUtente").val();        
        			dataString ="idUtente="+ utente;
        			exploreModal("listaRuoli.do",dataString,"#posTabRuoli",function(data,textStatus){			
        			});        
 	 			});  	
  				
  				$("#selectVerificatore").change(function(e){	
        			var utente = $("#selectVerificatore").val();        
        			dataString ="action=associazioni&idUtente="+ utente;
        			exploreModal("listaCategorieVerifica.do",dataString,"#posTabCategorie",function(data,textStatus){			
        			});        
 	 			});    		
  				
  				$("#selectUtenteCom").change(function(e){	
        			var utente = $("#selectUtenteCom").val();        
        			dataString ="action=comunicazioni&idUtente="+ utente;
        			exploreModal("listaCategorieVerifica.do",dataString,"#posTabComunicazioni",function(data,textStatus){			
        			});        
 	 			});
  				
  				
			});	   
		
	    	$('#myModalError').on('hidden.bs.modal', function (e) {
				if($( "#myModalError" ).hasClass( "modal-success" )){
					callAction("listaUtenti.do");
				} 		
  			});
  		</script>
	</jsp:attribute> 
</t:layout> 
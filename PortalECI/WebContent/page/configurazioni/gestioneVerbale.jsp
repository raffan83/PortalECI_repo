<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<%@ page import="it.portalECI.DTO.RispostaFormulaVerbaleDTO" %>
<%@ page import="it.portalECI.DTO.RispostaSceltaVerbaleDTO" %>
<%@ page import="it.portalECI.DTO.RispostaTestoVerbaleDTO" %>
<%@ page import="it.portalECI.DTO.DomandaVerbaleDTO" %>
<%@ page import="it.portalECI.DTO.VerbaleDTO" %>

<%@ page import="java.util.Collections" %>
<%@ page import="java.util.Comparator" %>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

<t:layout title="Dashboard" bodyClass="skin-red sidebar-mini wysihtml5-supported">

	<jsp:attribute name="body_area">

		<div class="wrapper">
	
		<t:main-header  />
  		<t:main-sidebar />
 
  		<!-- Content Wrapper. Contains page content -->
 		<div id="corpoframe" class="content-wrapper">
   			<!-- Content Header (Page header) -->
    		<section class="content-header">
          		<h1 class="pull-left">
        			Dettaglio Verbale
        			<small></small>
      			</h1>  
      			<c:if test="${verbale.getStato().getId()>=5 }">
      				<button class="btn btn-default pull-right" onClick="generaCertificato()"><i class="glyphicon glyphicon-edit"></i> Genera Certificato</button>
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
	 											Dati Verbale
												<div class="box-tools pull-right">		
													<button data-widget="collapse" class="btn btn-box-tool"><i class="fa fa-minus"></i></button>
												</div>
											</div>
											<div class="box-body">	
        										<ul class="list-group list-group-unbordered">
        											<li class="list-group-item">
                  										<b>ID Intervento</b>                   										
                  										<a class="pull-right" href="#" class="btn customTooltip customlink" title="Click per aprire il dettaglio dell'Intervento" onclick="callAction('gestioneInterventoDati.do?idIntervento=${verbale.getIntervento().getId()}');">
															${verbale.getIntervento().getId()}
														</a>														
                									</li>
        											<li class="list-group-item">
                  										<b>ID Verbale</b>                   										
                  										
															<a class="pull-right">${verbale.getId()}</a>
														
                									</li>                									               								
                									<li class="list-group-item">
                  										<b>Data Creazione Verbale</b> 
                  										<a class="pull-right"><fmt:formatDate pattern="dd/MM/yyyy" value="${verbale.getCreateDate()}" /></a>
                									</li>        
                									<li class="list-group-item">
                  										<b>Tecnico Verificatore</b>                   										
														<a class="pull-right">${intervento.getTecnico_verificatore().getNominativo()}</a>
                									</li>        									
                									<li class="list-group-item">
                  										<b>Codice Categoria</b> 
                  										<a class="pull-right">${verbale.getCodiceCategoria()}</a>
                									</li>
                									<li class="list-group-item">
                  										<b>Stato</b> 
                  										<!-- <a class="pull-right">${intervento.getStatoIntervento().getDescrizione()}</a>-->
                  										<a class="pull-right">				 											
    														<span class="label label-info" style="color:#000000 !important; background-color:${verbale.getStato().getColore(verbale.getStato().getId())} !important;">${verbale.getStato().getDescrizione()}</span>
														</a>
                									</li>
                									<li class="list-group-item">
                  										<b>Codice Verifica</b> 
                  										<a class="pull-right">${verbale.getCodiceVerifica()}</a>
                									</li>
                									<li class="list-group-item">
                  										<b>Descrizione Verifica</b>
                  										<a class="pull-right">
		 														${verbale.getDescrizioneVerifica()}
                  										</a>
                									</li>
                								
        										</ul>     
        										<div class="row">    
        										
        											<c:if test='${verbale.getStato().getId()== 4}'>
        												<button class="btn btn-default pull-right" onClick="$('#modalCambioStatoVerbale').modal('show');" style="margin-right:10px">
        													<i class="glyphicon glyphicon-transfer"></i>
        												 	Cambio Stato
        												</button>
        											</c:if>        											      											
        										</div>   									

											</div>
										</div>
									</div>
								</div>
             					
             					<c:if test="${verbale.getStato().getId()>=3 }">
        							<div class="row">         
        								<div class="col-xs-12">
											<div class="box box-danger box-solid">
												<div class="box-header with-border">
	 												Controllo Verbale
													<div class="box-tools pull-right">		
														<button data-widget="collapse" class="btn btn-box-tool"><i class="fa fa-minus"></i></button>
													</div>
												</div>
												<div class="box-body">	
													<form id="formVerbale" >
														<c:forEach items="${domandeVerbale}" var="domVerbale" varStatus="loop">	
        													<div class="col-xs-12" style="border-bottom: 1px solid #ddd;">
        														<label for="titolo-input" class="control-label col-xs-12">${domVerbale.getDomandaQuestionario().getTesto()}</label><br/>
        												
    	    													<c:set var="domVerbale" value="${domVerbale}" scope="request"></c:set>
																<jsp:include page="gestioneVerbaleDettaglio.jsp"></jsp:include>        													

        													</div>
														</c:forEach>
													</form>
												</div>
												<c:if test='${verbale.getStato().getId()== 4}'>
													<div class="box-footer">
												
														<button type="button" class="btn btn-default ml-1 savebutt" onclick="modificaRisposte()" style="margin-left: 1em; float: right;">	
															<span >SALVA MODIFICHE</span>
														</button>	
													
														<button type="button" class="btn btn-default ml-1 savebutt" onclick="annullaModifiche()" style="margin-left: 1em; float: right;">	
															<span >ANNULLA MODIFICHE</span>
														</button>	
																	
            	      									<button type="button" class="btn btn-default  ml-1 changestate" onclick="salvaCambioStato('6')" style="margin-left: 1em; color:#000000 !important; background-color:${verbale.getStato().getColore(6)} !important; float: right;">
                	  										<i class="glyphicon glyphicon-remove"></i>
                  											<span >RIFIUTATO</span>
                  										</button>
										
														<button type="button" class="btn btn-default ml-1 changestate" onclick="salvaCambioStato('5')" style="margin-left: 1em; color:#000000 !important; background-color:${verbale.getStato().getColore(5)} !important; float: right;">
															<i class="glyphicon glyphicon glyphicon-ok"></i>
															<span >ACCETTATO</span>
														</button>
															      										
													</div>
												</c:if>		
												
											</div>
										</div>
									</div>
								</c:if>
								
 							</div>
						</div>
						  		
						
						<div id="modalCambioStatoVerbale" class="modal fade" role="dialog" aria-labelledby="modalCambioStatoVerbale">
   							<div class="modal-dialog modal-lg" role="document">
    							<div class="modal-content">
     								<div class="modal-header">
        								<button type="button" class="close" data-dismiss="modal" aria-label="Close">
        									<span aria-hidden="true">&times;</span>
        								</button>
        								<h4 class="modal-title" id="myModalLabel">Cambio Stato</h4>
      								</div>
      									
       								<div class="modal-body" >
       									<div class="row">
											<label class="col-sm-12" style="text-align:center;">Attenzione, sicuro di voler modificare lo stato di questo verbale? </label>
                  							
                  							<div class="col-sm-12" style="margin:5px ; text-align:center;">
                  								<button type="button  pull-left" class="btn-sm " onclick="salvaCambioStato('6')" style="color:#000000 !important; background-color:${verbale.getStato().getColore(6)} !important;">
                  									<i class="glyphicon glyphicon-remove"></i>
                  									<span >RIFIUTATO</span>
                  								</button>
										
												<button type="button  pull-right" class="btn-sm" onclick="salvaCambioStato('5')" style="color:#000000 !important; background-color:${verbale.getStato().getColore(5)} !important;">
													<i class="glyphicon glyphicon glyphicon-ok"></i>
													<span >ACCETTATO</span>
												</button>
																								      										
											</div>											
										</div>
    								</div>
    								<div class="modal-footer">
										<button onclick=" $('#modalCambioStatoVerbale').modal('hide');" class="btn btn-danger" >Esci</button>
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
				
    		});	
			
			function salvaCambioStato(idstato){
				pleaseWaitDiv = $('#pleaseWaitDialog');
				pleaseWaitDiv.modal();
				
				$.ajax({
					type: "POST",
					url: "gestioneVerbale.do?action=cambioStato",
					data : "idVerbale=${verbale.getId()}&stato="+idstato,				
					dataType: "json",
					success: function( data, textStatus) {
						if(data.success){ 		
							location.reload();		          			  		          		
						}else{
							pleaseWaitDiv.modal('hide');	
							$('#modalErrorDiv').html(data.messaggio);
							$('#myModalError').removeClass();
							$('#myModalError').addClass("modal modal-danger");
							$('#myModalError').modal('show');															
						}						
					},
					error: function(jqXHR, textStatus, errorThrown){		          
						$('#errorMsg').html("<h3 class='label label-danger'>"+textStatus+"</h3>");
						//callAction('logout.do');
						pleaseWaitDiv.modal('hide');
					}
				});
			}
			
			function generaCertificato(){
				pleaseWaitDiv = $('#pleaseWaitDialog');
				pleaseWaitDiv.modal();
				$.ajax({
					type: "POST",
					url: "gestioneVerbale.do?action=generaCertificato",
					data : "idVerbale=${verbale.getId()}",				
					dataType: "json",
					success: function( data, textStatus) {
						if(data.success){
							 window.location.href = data.filePath;
							 //var a = document.createElement('a');
							  //window.open(data.filePath,'_blank');
							 pleaseWaitDiv.modal('hide');
						}else{
							pleaseWaitDiv.modal('hide');	
							$('#modalErrorDiv').html(data.messaggio);
							$('#myModalError').removeClass();
							$('#myModalError').addClass("modal modal-danger");
							$('#myModalError').modal('show');									
						}						
					},
					error: function(jqXHR, textStatus, errorThrown){		          
						$('#errorMsg').html("<h3 class='label label-danger'>"+textStatus+"</h3>");
						pleaseWaitDiv.modal('hide');
					}
				});
			}

			function modificaRisposte(){		
				pleaseWaitDiv = $('#pleaseWaitDialog');
				pleaseWaitDiv.modal();
				
				$.ajax({
					type: "GET",
					url: "gestioneVerbale.do?idVerbale=${verbale.getId()}",
					data : $("#formVerbale").serializeArray(),				
					dataType: "json",
					success: function( data, textStatus) {

						if(data.success){ 
								
							location.reload();
			          			  		          		
						}else{
							pleaseWaitDiv.modal('hide');
							
							$('#modalErrorDiv').html(data.messaggio);
							$('#myModalError').removeClass();
							$('#myModalError').addClass("modal modal-danger");
							$('#myModalError').modal('show');
															
						}
						
					},

					error: function(jqXHR, textStatus, errorThrown){
			          
						$('#errorMsg').html("<h3 class='label label-danger'>"+textStatus+"</h3>");
						//callAction('logout.do');
						pleaseWaitDiv.modal('hide');
					}
				});
								
			}
			
			function annullaModifiche(){
				location.reload();
			}
			
			/*$(".rispVerb").change(function() {
				$(".savebutt").css("display", "initial");
				$(".changestate").css("display", "none");
			});*/
					

  		</script>	  
	</jsp:attribute> 
</t:layout>
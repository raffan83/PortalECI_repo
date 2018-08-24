<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<%@ page import="it.portalECI.DTO.RispostaFormulaVerbaleDTO" %>
<%@ page import="it.portalECI.DTO.RispostaSceltaVerbaleDTO" %>
<%@ page import="it.portalECI.DTO.RispostaTestoVerbaleDTO" %>
<%@ page import="it.portalECI.DTO.DomandaVerbaleDTO" %>
<%@ page import="it.portalECI.DTO.VerbaleDTO" %>
<%@page import="it.portalECI.DTO.DocumentoDTO" %>
<%@page import="it.portalECI.DTO.UtenteDTO"%>

<%@ page import="java.util.Collections" %>
<%@ page import="java.util.Comparator" %>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

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
    		<section id="topbar" class="content-header">
          		<h1 class="pull-left">
        			Dettaglio Verbale
        			<small></small>
      			</h1>  
      			<c:if test="${verbale.getStato().getId()>=5 && user.checkPermesso('GENERA_CERTIFICATO')}">
      				<button class="btn btn-default pull-right" onClick="generaCertificato(${verbale.getId()})" style="margin-left:5px"><i class="glyphicon glyphicon-edit"></i> Genera Certificato</button>
      			</c:if>
      			<c:if test="${verbale.getSchedaTecnica()!=null && verbale.getSchedaTecnica().getStato().getId()>=5 && user.checkPermesso('GENERA_SKTECNICA')}">
      				<button class="btn btn-default pull-right" onClick="generaCertificato(${verbale.getSchedaTecnica().getId()})" style="margin-left:5px"><i class="glyphicon glyphicon-edit"></i> Genera Scheda Tecnica</button>
      			</c:if>   
      			<c:if test="${verbale.getDocumentiVerbale().size()>0}">
      				<c:forEach items="${verbale.getDocumentiVerbale()}" var="docum">	
      					<c:if test="${docum.getType().equals('CERTIFICATO') && user.checkPermesso('DOWNLOAD_CERTIFICATO')}">
    	  					<a class="btn btn-default pull-right" href="gestioneDocumento.do?idDocumento=${docum.id}" style="margin-left:5px"><i class="glyphicon glyphicon-file"></i> Download Certificato</a>
	      				</c:if>
	      			</c:forEach>
      			</c:if>
      			<c:if test="${verbale.getSchedaTecnica()!=null && verbale.getSchedaTecnica().getStato().getId()>=5 && verbale.getSchedaTecnica().getDocumentiVerbale().size()>0}">
      				<c:forEach items="${verbale.getSchedaTecnica().getDocumentiVerbale()}" var="documST">
      					<c:if test="${ documST.getType().equals('SCHEDA_TECNICA') && user.checkPermesso('DOWNLOAD_SKTECNICA')}">
	      					<a class="btn btn-default pull-right" href="gestioneDocumento.do?idDocumento=${documST.id}" style="margin-left:5px"><i class="glyphicon glyphicon-file"></i> Download Scheda Tecnica</a>
    	  				</c:if>
      				</c:forEach>
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
                  										<b>Stato Sk.Tecnica</b> 
                  										<!-- <a class="pull-right">${intervento.getStatoIntervento().getDescrizione()}</a>-->
                  										<a class="pull-right">				 											
    														<c:if test="${verbale.getSchedaTecnica()!=null }">
																<span class="label" style="color:#000000 !important; background-color:${verbale.getStato().getColore(verbale.getSchedaTecnica().getStato().getId())} !important;">${verbale.getSchedaTecnica().getStato().getDescrizione()}</span>
															</c:if>  											
															<c:if test="${verbale.getSchedaTecnica()==null }">
																<span class="label" style="color:#000000 !important; background-color:grey !important;">ASSENTE</span>
															</c:if>
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
        										<div class="row" id="cambiostato">    
        										
        											<c:if test='${verbale.getStato().getId()== 4 && user.checkPermesso("CH_STA_VERBALE")}'>
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
    	    													<c:set var="readonly" value="${verbale.getStato().getId()>=5}" scope="request"></c:set>
																<jsp:include page="gestioneVerbaleDettaglio.jsp"></jsp:include>        													

        													</div>
														</c:forEach>
													</form>
												</div>
												<c:if test='${verbale.getStato().getId()== 4}'>
													<div class="box-footer" id="formVerbalebox">
														<c:if test="${user.checkPermesso('UPD_VERBALE')}">
															<button type="button" class="btn btn-default ml-1 savebutt" onclick="modificaRisposte(${verbale.getId()},'formVerbale')" style="margin-left: 1em; float: right;">	
																<span >SALVA MODIFICHE</span>
															</button>	
													
															<button type="button" class="btn btn-default ml-1 savebutt" onclick="annullaModifiche('formVerbale')" style="margin-left: 1em; float: right;">	
																<span >ANNULLA MODIFICHE</span>
															</button>
														</c:if>
															
														<c:if test="${user.checkPermesso('CH_STA_VERBALE')}">
            	      										<button type="button" class="btn btn-default  ml-1 changestate" onclick="salvaCambioStato(${verbale.getId()},'formVerbale','6')" style="margin-left: 1em; color:#000000 !important; background-color:${verbale.getStato().getColore(6)} !important; float: right;">
                	  											<i class="glyphicon glyphicon-remove"></i>
                  												<span >RIFIUTATO</span>
                  											</button>
										
															<button type="button" class="btn btn-default ml-1 changestate" onclick="salvaCambioStato(${verbale.getId()},'formVerbale','5')" style="margin-left: 1em; color:#000000 !important; background-color:${verbale.getStato().getColore(5)} !important; float: right;">
																<i class="glyphicon glyphicon glyphicon-ok"></i>
																<span >ACCETTATO</span>
															</button>
														</c:if>
															      										
													</div>
												</c:if>		
												
											</div>
										</div>
									</div>
								</c:if>
								
								<c:if test="${verbale.getSchedaTecnica()!=null && verbale.getSchedaTecnica().getStato().getId()>=3 }">
        							<div class="row">         
        								<div class="col-xs-12">
											<div class="box box-danger box-solid">
												<div class="box-header with-border">
	 												Controllo Scheda Tecnica
													<div class="box-tools pull-right">		
														<button data-widget="collapse" class="btn btn-box-tool"><i class="fa fa-minus"></i></button>
													</div>
												</div>
												<div class="box-body">	
													<form id="formScTecnica" >
														<c:forEach items="${domandeVerbaleSchedaTecnica}" var="domVerbale" varStatus="loop">	
        													<div class="col-xs-12" style="border-bottom: 1px solid #ddd;">
        														<label for="titolo-input" class="control-label col-xs-12">${domVerbale.getDomandaQuestionario().getTesto()}</label><br/>
        												
    	    													<c:set var="domVerbale" value="${domVerbale}" scope="request"></c:set>
    	    													<c:set var="readonly" value="${verbale.getSchedaTecnica().getStato().getId()>=5}" scope="request"></c:set>
																<jsp:include page="gestioneVerbaleDettaglio.jsp"></jsp:include>        													

        													</div>
														</c:forEach>
													</form>
												</div>
												<c:if test='${verbale.getSchedaTecnica().getStato().getId()== 4}'>
													<div class="box-footer" id="formScTecnicabox">
														<c:if test="${user.checkPermesso('UPD_VERBALE')}">
															<button type="button" class="btn btn-default ml-1 savebutt" onclick="modificaRisposte(${verbale.getSchedaTecnica().getId()},'formScTecnica')" style="margin-left: 1em; float: right;">	
																<span >SALVA MODIFICHE</span>
															</button>	
													
															<button type="button" class="btn btn-default ml-1 savebutt" onclick="annullaModifiche('formScTecnica')" style="margin-left: 1em; float: right;">	
																<span >ANNULLA MODIFICHE</span>
															</button>	
														</c:if>
								
														<c:if test="${user.checkPermesso('CH_STA_VERBALE')}">
            	      										<button type="button" class="btn btn-default  ml-1 changestate" onclick="salvaCambioStato(${verbale.getSchedaTecnica().getId()},'formScTecnica','6')" style="margin-left: 1em; color:#000000 !important; background-color:${verbale.getStato().getColore(6)} !important; float: right;">
                		  										<i class="glyphicon glyphicon-remove"></i>
                	  											<span >RIFIUTATO</span>
            	      										</button>
										
															<button type="button" class="btn btn-default ml-1 changestate" onclick="salvaCambioStato(${verbale.getSchedaTecnica().getId()},'formScTecnica','5')" style="margin-left: 1em; color:#000000 !important; background-color:${verbale.getStato().getColore(5)} !important; float: right;">
																<i class="glyphicon glyphicon glyphicon-ok"></i>
																<span >ACCETTATO</span>
															</button>
														</c:if>   										
													</div>
												</c:if>		
												
											</div>
										</div>
									</div>
								</c:if>
								<!-- fine scheda tecnica -->
						
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
                  								<c:if test="${user.checkPermesso('CH_STA_VERBALE')}">
                  									<button type="button  pull-left" class="btn-sm " onclick="salvaCambioStato(null,null,'6')" style="color:#000000 !important; background-color:${verbale.getStato().getColore(6)} !important;">
                  										<i class="glyphicon glyphicon-remove"></i>
                  										<span >RIFIUTATO</span>
                  									</button>
										
													<button type="button  pull-right" class="btn-sm" onclick="salvaCambioStato(null,null,'5')" style="color:#000000 !important; background-color:${verbale.getStato().getColore(5)} !important;">
														<i class="glyphicon glyphicon glyphicon-ok"></i>
														<span >ACCETTATO</span>
													</button>
												</c:if>											      										
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
				$('.rispVerb').on('ifChanged', function(event) {
					$('.'+$(this).attr('id')).find('input').val('');
					$('.'+$(this).attr('id')).find('textarea').val('');
					$('.'+$(this).attr('id')).find(':checkbox').removeAttr('checked').iCheck('update');
					$('.'+$(this).attr('id')).find(':radio').removeAttr('checked').iCheck('update');
				});
    		});	
			
			function salvaCambioStato(idverbale, idform, idstato){
				pleaseWaitDiv = $('#pleaseWaitDialog');
				pleaseWaitDiv.modal();
				if(idverbale==null){
					idverbale=${verbale.getId()}+"&all="+true
				}
				$.ajax({
					type: "POST",
					url: "gestioneVerbale.do?action=cambioStato",
					data : "idVerbale="+idverbale+"&stato="+idstato,				
					dataType: "json",
					success: function( data, textStatus) {
						if(data.success){	
							if(idform==null){
								location.reload();
							}else{
								$("#"+idform+"box").hide();
								$("#"+idform+" input").attr('readonly', 'readonly');
								$("#"+idform+" textarea").attr('readonly', 'readonly');
								$("#"+idform+" :checkbox").attr('disabled', 'disabled');
								$("#"+idform+" :radio").attr('disabled', 'disabled');
								$("#"+idform+" input").iCheck('update');
								
								if(idform=="formVerbale" && ${user.checkPermesso('GENERA_CERTIFICATO')}){									
									$("#topbar").append('<button class="btn btn-default pull-right" onClick="generaCertificato(${verbale.getId()})" style="margin-left:5px"><i class="glyphicon glyphicon-edit"></i> Genera Certificato</button>');
								}else if(idform=="formScTecnica" && ${user.checkPermesso('GENERA_SKTECNICA')}){
									$("#topbar").append('<button class="btn btn-default pull-right" onClick="generaCertificato(${verbale.getSchedaTecnica().getId()})" style="margin-left:5px"><i class="glyphicon glyphicon-edit"></i> Genera Scheda Tecnica</button>');
								}
							}
						}else{							
							$('#modalErrorDiv').html(data.messaggio);
							$('#myModalError').removeClass();
							$('#myModalError').addClass("modal modal-danger");
							$('#myModalError').modal('show');															
						}		
						pleaseWaitDiv.modal('hide');	
						$('#modalCambioStatoVerbale').modal('hide');
					},
					error: function(jqXHR, textStatus, errorThrown){		          
						$('#errorMsg').html("<h3 class='label label-danger'>"+textStatus+"</h3>");
						//callAction('logout.do');
						pleaseWaitDiv.modal('hide');
					}
				});
			}
			
			function generaCertificato(id){
				pleaseWaitDiv = $('#pleaseWaitDialog');
				pleaseWaitDiv.modal();
				$.ajax({
					type: "POST",
					url: "gestioneVerbale.do?action=generaCertificato",
					data : "idVerbale="+id,				
					dataType: "json",
					success: function( data, textStatus) {
						location.reload();
					},
					error: function(jqXHR, textStatus, errorThrown){
						pleaseWaitDiv.modal('hide');
						$('#modalErrorDiv').html(jqXHR.responseText);
						$('#myModalError').removeClass();
						$('#myModalError').addClass("modal modal-danger");
						$('#myModalError').modal('show');															
					}
				});
			}
			
			function modificaRisposte(idVerb,idform){		
				pleaseWaitDiv = $('#pleaseWaitDialog');
				pleaseWaitDiv.modal();
				
				$.ajax({
					type: "GET",
					url: "gestioneVerbale.do?idVerbale="+idVerb,
					data : $("#"+idform).serializeArray(),				
					dataType: "json",
					success: function( data, textStatus) {

						if(!data.success){	
							$('#modalErrorDiv').html(data.messaggio);
							$('#myModalError').removeClass();
							$('#myModalError').addClass("modal modal-danger");
							$('#myModalError').modal('show');
						}
						
						pleaseWaitDiv.modal('hide');
					},

					error: function(jqXHR, textStatus, errorThrown){
						$('#errorMsg').html("<h3 class='label label-danger'>"+textStatus+"</h3>");
						//callAction('logout.do');
						pleaseWaitDiv.modal('hide');
					}
				});
								
			}
			
			function annullaModifiche(idform){
				document.getElementById(idform).reset();
				$('input').iCheck('update'); 
			}
			
					
			
  		</script>	  
	</jsp:attribute> 
</t:layout>
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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% pageContext.setAttribute("newLineChar", "\r\n"); %>
<% pageContext.setAttribute("newLineChar2", "\n"); %>
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
                									<li class="list-group-item">
                  										<b>Questionario</b>
                  										<c:choose>
                  										<c:when test='${user.checkPermesso("UPD_QUESTIONARIO")}' >
                  										<a class="pull-right"  href="gestioneQuestionario.do?idQuestionario=${questionario.id}" target="_blank">
		 												<c:if test="${questionario.isObsoleto}"> <span class="label bg-red">OBSOLETO</span> </c:if> ${questionario.titolo} del <fmt:formatDate pattern="dd/MM/yyyy" value='${questionario.createDate}' type='date' /> <i class="fa fa-arrow-right"></i>
                  										</a>
                  										</c:when>
                  										
                  										<c:otherwise>
                  										<a class="pull-right">
		 												<c:if test="${questionario.isObsoleto}"> <span class="label bg-red">OBSOLETO</span> </c:if> ${questionario.titolo} del <fmt:formatDate pattern="dd/MM/yyyy" value='${questionario.createDate}' type='date' />
                  										</a>
                  										</c:otherwise>
                  										</c:choose>
                  										
                									</li>
                									<li class="list-group-item">
                  										<b>Note verbale</b>
                  										<a class="pull-right">
		 													${verbale.getNote()}
                  										</a>
                									</li>
                									<li class="list-group-item">
                  										<b>Attrezzatura</b>
                  										<c:if test="${verbale.attrezzatura!=null }">
                  										<a class="pull-right btn customTooltip customlink"   onClick="dettaglioAttrezzatura('${verbale.attrezzatura.id }','${verbale.attrezzatura.matricola_inail }','${verbale.attrezzatura.numero_fabbrica }','${verbale.attrezzatura.tipo_attivita }','${verbale.attrezzatura.descrizione }','${verbale.attrezzatura.id_cliente }','${verbale.attrezzatura.id_sede }',
 																'${verbale.attrezzatura.data_verifica_funzionamento }','${verbale.attrezzatura.data_prossima_verifica_funzionamento }','${verbale.attrezzatura.data_verifica_integrita }','${verbale.attrezzatura.data_prossima_verifica_integrita }','${verbale.attrezzatura.data_verifica_interna }','${verbale.attrezzatura.data_prossima_verifica_interna }',
 																'${verbale.attrezzatura.anno_costruzione }','${verbale.attrezzatura.fabbricante }','${verbale.attrezzatura.modello }','${verbale.attrezzatura.settore_impiego }','${fn:replace(fn:replace(verbale.attrezzatura.note_tecniche.replace('\'',' ').replace('\\','/'),newLineChar, ' '),newLineChar2, ' ')}','${fn:replace(fn:replace(verbale.attrezzatura.note_generiche.replace('\'',' ').replace('\\','/').replace('\\n',' '),newLineChar, ' '),newLineChar2,' ')}','${verbale.attrezzatura.obsoleta }',
 																'${verbale.attrezzatura.tipo_attrezzatura }','${verbale.attrezzatura.tipo_attrezzatura_GVR }','${verbale.attrezzatura.ID_specifica }','${verbale.attrezzatura.sogg_messa_serv_GVR }','${verbale.attrezzatura.n_panieri_idroestrattori }','${verbale.attrezzatura.marcatura }','${verbale.attrezzatura.n_id_on }','${verbale.attrezzatura.data_scadenza_ventennale }')">
 																
 																
		 												${verbale.getAttrezzatura().getMatricola_inail()}
                  										</a>
                  									 </c:if>
                  									 
                  									       										
                									</li>
                									<li class="list-group-item">
                  										<b>Sede Utilizzatore</b>                  										
                  										<a class="pull-right "  >${verbale.sedeUtilizzatore}
                  										</a>
                  									
                  									 
                  									       										
                									</li>
                									<li class="list-group-item">
                  										<b>Esercente</b>
                  										<a class="pull-right b"  >${verbale.esercente}
                  										</a>
                  								
                  									       										
                									</li>
                									
        										</ul> 

												<c:if test='${verbale.getStato().getId()== 5 && (user.checkRuolo("AM") || user.checkRuolo("RT"))}'>										
													<!-- <button type="button" class="btn btn-sm pull-right" onclick="salvaCambioStato(null,null,'6')" style="color:#000000 !important;"> -->
													<button type="button" class="btn btn-sm pull-right" onclick="$('#confirmRifiuta').modal('show');" style="color:#000000 !important;">
														<i class="glyphicon glyphicon-remove"></i>
														<span>Rifiuta per modifica</span>
													</button>
												</c:if>	   
        										<div class="row" id="cambiostato">    
        											<c:if test='${verbale.getStato().getId()== 4 && (user.checkRuolo("AM") || user.checkRuolo("RT"))}'>
        												<button class="btn btn-default pull-right" onClick="$('#modalCambioStatoVerbale').modal('show');" style="margin-right:10px">
        													<i class="glyphicon glyphicon-transfer"></i>
        												 	Cambio Stato
        												</button>
        											</c:if>
        											<c:if test='${verbale.getStato().getId()== 1 && user.checkPermesso("CH_STA_VERBALE")}'>
        												<button class="btn btn-default pull-right" onClick="$('#modalCambioStatoVerbaleCompWeb').modal('show');" style="margin-right:10px">
        													<i class="glyphicon glyphicon-transfer"></i>
        												 	Compilazione Web
        												</button>
        											</c:if>           											      											
        										</div>   									
												<input id="changedForm" style="display:none;" disabled="disabled">
											</div>
										</div>
									</div>
								</div>
								      			
      				
      			
								<div class="row">
								<c:if test="${listaCertificati.size()>0 || user.checkRuolo('AM')}">
									<c:if test ="${verbale.getStato().getId()>=5 && verbale.getStato().getId()<8}">								  
       								<div class="col-md-6 col-xs-12" id="certificatoBox">
       									<div class="box box-danger box-solid">
											<div class="box-header with-border">
 												Certificato
												<div class="box-tools pull-right">	
													<button data-widget="collapse" class="btn btn-box-tool"><i class="fa fa-minus"></i></button>
												</div>
											</div>
											<div class="box-body">
												<ul class="list-group list-group-unbordered" id="">
        											<c:forEach items="${listaCertificati}" var="certificato"> 
	        											<li class="list-group-item ${certificato.getInvalid()?"text-muted":""}">
	                  										<b>${certificato.getFileName()}</b>
	                  										<c:if test="${user.checkPermesso('DOWNLOAD_CERTIFICATO')}">             										
	                  											<a class="btn btn-default btn-xs pull-right" href="gestioneDocumento.do?idDocumento=${certificato.getId()}" style="margin-left:5px"><i class="glyphicon glyphicon-file"></i> Download Certificato</a>														
	                										</c:if>
	                									</li>
                									</c:forEach>
                								</ul>
                								<c:if test="${user.checkPermesso('GENERA_CERTIFICATO') && verbale.getStato().getId()!=6}">    
                									<!-- <button class="btn btn-default pull-right" onClick="$('#confirmCertificato').modal('show');" style="margin-left:5px"><i class="glyphicon glyphicon-edit"></i> Genera Certificato</button> -->
                									<%-- <a class="btn btn-default pull-right" href="anteprimaCertificato.do?idVerbale=${verbale.getId()}" style="margin-left:5px"><i class="glyphicon glyphicon-eye-open"></i> Anteprima Certificato</a> --%>
												</c:if>
											</div>
										</div>
									</div>
								</c:if>
								</c:if>
								
								<c:if test="${verbale.getSchedaTecnica().getDocumentiVerbale().size()>0 || user.checkRuolo('AM') }">
								<c:if test="${verbale.getSchedaTecnica().getStato().getId()>=5 && verbale.getSchedaTecnica().getStato().getId()<8 }">
									<div class="col-md-6 col-xs-12" id="schedaTecnicaBox">
	     									<div class="box box-danger box-solid">
											<div class="box-header with-border">
													Scheda tecnica
												<div class="box-tools pull-right">		
													<button data-widget="collapse" class="btn btn-box-tool"><i class="fa fa-minus"></i></button>
												</div>
											</div>
											<div class="box-body">
												<ul class="list-group list-group-unbordered" id="">
	       											<c:forEach items="${listaSchedeTecniche}" var="schedaTec"> 
	        											<li class="list-group-item">
	                  										<b>${schedaTec.getFileName()}</b>
	                  										<c:if test="${user.checkPermesso('DOWNLOAD_SKTECNICA')}">             										
	                  											<a class="btn btn-default btn-xs pull-right" href="gestioneDocumento.do?idDocumento=${schedaTec.getId()}" style="margin-left:5px"><i class="glyphicon glyphicon-file"></i> Download SchedaTecnica</a>														
	                										</c:if>
	                									</li>
	               									</c:forEach>
	               								</ul>
	               								<c:if test="${user.checkPermesso('GENERA_SKTECNICA')}">
	               									<button class="btn btn-default pull-right" onClick="$('#confirmSchedaTecnica').modal('show');" style="margin-left:5px"><i class="glyphicon glyphicon-edit"></i> Genera Scheda Tecnica</button>
													<a class="btn btn-default pull-right" href="anteprimaCertificato.do?idVerbale=${verbale.getSchedaTecnica().getId()}" style="margin-left:5px"><i class="glyphicon glyphicon-eye-open"></i> Anteprima Scheda Tecnica</a>
												</c:if>
											</div>
										</div>
									</div>								
								</c:if>
								</c:if>
								</div>
								
								
       							<div class="row">         
       								<div class="col-xs-12">
										<div class="box box-danger box-solid">
											<div class="box-header with-border">
 												Allegati Verbale
												<div class="box-tools pull-right">		
													<button data-widget="collapse" class="btn btn-box-tool"><i class="fa fa-minus"></i></button>
												</div>
											</div>
											<div class="box-body">	
        										<ul class="list-group list-group-unbordered" id="allegatiList">
        											<c:forEach items="${listaAllegati}" var="allegato"> 
        											<li class="list-group-item">
                  										<b>${allegato.getFileName()}</b>    
                  										<c:if test="${user.checkPermesso('DOWNLOAD_ALLEGATO')}">              										
                  											<a class="btn btn-default btn-xs pull-right" href="gestioneDocumento.do?idDocumento=${allegato.getId()}" style="margin-left:5px"><i class="glyphicon glyphicon-file"></i> Download Allegato</a>														
                										</c:if>
                									</li>
                									</c:forEach>
                								</ul>
            									<div class="form-group">
													<label for="titolo-input" class="control-label">Carica un file da allegare al verbale</label>
													<div class="input-group">
														<input type="file" name="file" class="form-control"	id="file-input-allegato" placeholder="File">
														<div class="input-group-btn">
															<a class="btn btn-danger" onclick="uploadAllegato(document.getElementById('file-input-allegato'), ${verbale.getId()});return false;">Carica il file</a>
														</div>
													</div>
												</div>          			
											</div>
										</div>
									</div>
								</div>
								
							<form id="formVerbale">	
							<c:if test="${verbale.codiceCategoria == 'VIE'}">
							<div class="row">         
       								<div class="col-xs-12">
										<div class="box box-danger box-solid">
											<div class="box-header with-border">
 												Strumento Verificatore
												<div class="box-tools pull-right">		
													<button data-widget="collapse" class="btn btn-box-tool"><i class="fa fa-minus"></i></button>
												</div>
											</div>
											<div class="box-body">	
											<label>Seleziona uno strumento per il verificatore</label>
        										 	 <select id="strumento_verificatore" name="strumento_verificatore" class="form-contol select2" data-placeholder="Seleziona Strumento Verificatore..."  style="width:100%" required>
														<option value=""></option>
														<option value="0">Nessuno</option>
														<c:forEach items="${intervento.getTecnico_verificatore().getListaStrumentiVerificatore()}" var="str" varStatus="loop">
														<c:if test="${str.id == verbale.strumento_verificatore.id }">
															<option value="${str.id}" selected>${str.marca } - ${str.modello } - ${str.matricola }</option>
														</c:if>
														<c:if test="${str.id != verbale.strumento_verificatore.id }">
															<option value="${str.id}">${str.marca } - ${str.modello } - ${str.matricola }</option>
														</c:if>
														
														
														</c:forEach>
														
														
														</select>
      			
											</div>
										</div>
									</div>
								</div>
             					</c:if>
             					
             					
             					

             					
             					
             					
             					
             					
             					
        							<div class="row">         
        								<div class="col-xs-12">
											<div class="box box-danger box-solid">
												<div class="box-header with-border">
													<c:if test="${verbale.getStato().getId()==8 }">
														Compila Verbale
													</c:if>
													<c:if test="${verbale.getStato().getId()!=8 }">
	 													Controllo Verbale
	 												</c:if>
													<div class="box-tools pull-right">		
														<button data-widget="collapse" class="btn btn-box-tool"><i class="fa fa-minus"></i></button>
													</div>
												</div>
												<div class="box-body">	
													<!-- <form id="formVerbale"> -->
														<c:forEach items="${domandeVerbale}" var="domVerbale" varStatus="loop">	
        													<div class="col-xs-12" style="border-bottom: 1px solid #ddd;">
        														<label for="titolo-input" class="control-label col-xs-12">${domVerbale.getDomandaQuestionario().getTesto()}</label><br/>
        												
    	    													<c:set var="domVerbale" value="${domVerbale}" scope="request"></c:set>
    	    													<c:set var="storicoModificheVerb" value="${storicoModificheVerb}" scope="request"></c:set>        	
    	    													<c:set var="storicoModificheSkTec" value="${storicoModificheSkTec}" scope="request"></c:set>    
    	    													<c:set var="type" value="Verbale" scope="request"></c:set>    													    	    													
																<jsp:include page="gestioneVerbaleDettaglio.jsp"></jsp:include>        													
        													</div>
														</c:forEach>
													<!-- </form> -->
												</div>
												
												<div class="box-footer" id="formVerbalebox">
													<c:if test="${user.checkPermesso('UPD_VERBALE')}">
														<%-- <c:if test='${verbale.getStato().getId()!= 8}'>
															<button type="button" class="btn btn-default ml-1 savebutt" onclick="modificaRisposte(${verbale.getId()},'formVerbale')" style="margin-left: 1em; float: right;">	
																<span >SALVA MODIFICHE</span>
															</button>	
														
															<button type="button" class="btn btn-default ml-1 savebutt" onclick="annullaModifiche('formVerbale')" style="margin-left: 1em; float: right;">	
																<span >ANNULLA MODIFICHE</span>
															</button>
														</c:if> --%>
														<c:if test='${verbale.getStato().getId()== 8 }'>
															<button type="button" class="btn btn-default ml-1 savebutt" onclick="salvaRisposteCompWeb(${verbale.getId()},'formVerbale', 'salvaRisposteCompWeb')" style="margin-left: 1em; float: right;">	
																<span >SALVA MODIFICHE</span>
															</button>
																<button type="button" class="btn btn-default ml-1 changestate formVerbalebox" onclick="salvaRisposteCompWeb(${verbale.getId()},'formVerbale', 'confermaRisposteCompWeb')" style="margin-left: 1em; color:#000000 !important; background-color:${verbale.getStato().getColore(5)} !important; float: right;"> 
																<%-- <button type="button" class="btn btn-default ml-1 changestate formVerbalebox" onclick="$('#confirmCertificato').modal('show');" style="margin-left: 1em; color:#000000 !important; background-color:${verbale.getStato().getColore(5)} !important; float: right;"> --%>
																<i class="glyphicon glyphicon glyphicon-ok"></i>
																<span >CONFERMA</span>
															</button>
															
															<a class="btn btn-default pull-right" href="anteprimaCertificato.do?idVerbale=${verbale.getId()}" style="margin-left:5px"><i class="glyphicon glyphicon-eye-open"></i> Anteprima Certificato</a>
														</c:if>
														<c:if test='${verbale.getStato().getId()== 6 }'>
															<button type="button" class="btn btn-default ml-1 savebutt" onclick="modificaRisposte(${verbale.getId()},'formVerbale')" style="margin-left: 1em; float: right;">	
																<span >SALVA MODIFICHE</span>
															</button>
																 <%-- <button type="button" class="btn btn-default ml-1 changestate formVerbalebox" onclick="salvaRisposteCompWeb(${verbale.getId()},'formVerbale', 'confermaRisposteCompWeb')" style="margin-left: 1em; color:#000000 !important; background-color:${verbale.getStato().getColore(5)} !important; float: right;"> --%> 
																   <button type="button" class="btn btn-default ml-1 changestate formVerbalebox" onclick="$('#confirmConferma').modal('show');" style="margin-left: 1em; color:#000000 !important; background-color:${verbale.getStato().getColore(5)} !important; float: right;"> 
																
																
																
																<i class="glyphicon glyphicon glyphicon-ok"></i>
																<span >CONFERMA</span>
															</button>
															
															<a class="btn btn-default pull-right" href="anteprimaCertificato.do?idVerbale=${verbale.getId()}" style="margin-left:5px"><i class="glyphicon glyphicon-eye-open"></i> Anteprima Certificato</a>
														</c:if>
														
													
													
													</c:if>
													<c:if test='${verbale.getStato().getId()== 4 && (user.checkRuolo("AM") || user.checkRuolo("RT"))}'>					
														
														<button type="button" class="btn btn-default ml-1 savebutt" onclick="modificaRisposte(${verbale.getId()},'formVerbale')" style="margin-left: 1em; float: right;">	
																<span >SALVA MODIFICHE</span>
															</button>
															
            	      										<%-- <button type="button" class="btn btn-default  ml-1 changestate formVerbalebox" onclick="preCambioStato(${verbale.getId()},'formVerbale','6')" style="margin-left: 1em; color:#000000 !important; background-color:${verbale.getStato().getColore(6)} !important; float: right;"> --%>
            	      										<button type="button" class="btn btn-default  ml-1 changestate formVerbalebox" onclick="$('#confirmRifiutaDaVerificare').modal('show');" style="margin-left: 1em; color:#000000 !important; background-color:${verbale.getStato().getColore(6)} !important; float: right;">
            	      										
                	  											<i class="glyphicon glyphicon-remove"></i>
                  												<span >RIFIUTATO</span>
                  											</button>
										
															<%-- <button type="button" class="btn btn-default ml-1 changestate formVerbalebox" onclick="preCambioStato(${verbale.getId()},'formVerbale','5')" style="margin-left: 1em; color:#000000 !important; background-color:${verbale.getStato().getColore(5)} !important; float: right;"> --%>
															
															<button type="button" class="btn btn-default ml-1 changestate formVerbalebox" onclick="$('#confirmCertificato').modal('show');" style="margin-left: 1em; color:#000000 !important; background-color:${verbale.getStato().getColore(5)} !important; float: right;">
																<i class="glyphicon glyphicon glyphicon-ok"></i>
																<span >ACCETTATO</span>
															</button>
															
															<a class="btn btn-default pull-right" href="anteprimaCertificato.do?idVerbale=${verbale.getId()}" style="margin-left:5px"><i class="glyphicon glyphicon-eye-open"></i> Anteprima Certificato</a>
															
		
															      																							
													</c:if>		
												</div>
											</div>
										</div>
									</div>
								<%-- </c:if> --%>
								
								
								
								
								<c:if test="${verbale.getStato().getId()>=3 }">
             					
             					<div class="row">         
        								<div class="col-xs-12">
											<div class="box box-danger box-solid">
												<div class="box-header with-border">
													Data Verifica
													<div class="box-tools pull-right">		
														<button data-widget="collapse" class="btn btn-box-tool"><i class="fa fa-minus"></i></button>
													</div>
												</div>
												<div class="box-body">
												 <div class="row"> 
												
												<div class="col-xs-6">
												<label>Data Verifica</label>
												  <div class='input-group date datepicker' id='datepicker_data_inizio'>
												  <fmt:formatDate value="${verbale.data_verifica }" pattern="dd/MM/yyyy" var="myDate" />
												               <input type='text' class="form-control input-small" id="data_verifica" name="data_verifica" value="${myDate }">
												                <span class="input-group-addon">
												                    <span class="fa fa-calendar" >
												                    </span>
												                </span>
												        </div> 	
												
												</div>
												<!-- <div class="col-xs-4"></div> -->
												
												<div class="col-xs-6">
												<label>Data Prossima Verifica</label>
												  <div class='input-group date datepicker'>
												  <fmt:formatDate value="${verbale.data_prossima_verifica }" pattern="dd/MM/yyyy" var="myDate" />
												               <input type='text' class="form-control input-small" id="data_prossima_verifica_verb" name="data_prossima_verifica_verb" value="${myDate }">
												                <span class="input-group-addon">
												                    <span class="fa fa-calendar" >
												                    </span>
												                </span>
												        </div> 	
												
												<!-- </div> -->
												
												</div>
												       		
												<c:if test="${verbale.getAttrezzatura().getTipo_attivita().equals('GVR') }">      		
												<c:set var = "isGvr" value="1"></c:set>
												 <!-- <div class="row"> -->
												
												<div class="col-xs-6">
												<label>Data Verifica Integrità</label>
												  <div class='input-group date datepicker' id='datepicker_data_inizio'>
												  <fmt:formatDate value="${verbale.data_verifica_integrita }" pattern="dd/MM/yyyy" var="myDate" />
												               <input type='text' class="form-control input-small" id="data_verifica_integrita_verb" name="data_verifica_integrita_verb" value="${myDate }">
												                <span class="input-group-addon">
												                    <span class="fa fa-calendar" >
												                    </span>
												                </span>
												        </div> 	
												
												</div>
												<!-- <div class="col-xs-4"></div> -->
												
												<div class="col-xs-6">
												<label>Data Prossima Verifica Integrità</label>
												  <div class='input-group date datepicker'>
												  <fmt:formatDate value="${verbale.data_prossima_verifica_integrita }" pattern="dd/MM/yyyy" var="myDate" />
												               <input type='text' class="form-control input-small" id="data_prossima_verifica_integrita_verb" name="data_prossima_verifica_integrita_verb" value="${myDate }">
												                <span class="input-group-addon">
												                    <span class="fa fa-calendar" >
												                    </span>
												                </span>
												        </div> 	
												
												</div>
												
												<!-- </div>    -->   	
												
												<!-- <div class="row"> -->
												
												<div class="col-xs-6">
												<label>Data Verifica Interna</label>
												  <div class='input-group date datepicker' id='datepicker_data_inizio'>
												  <fmt:formatDate value="${verbale.data_verifica_interna }" pattern="dd/MM/yyyy" var="myDate" />
												               <input type='text' class="form-control input-small" id="data_verifica_interna_verb" name="data_verifica_interna_verb" value="${myDate }">
												                <span class="input-group-addon">
												                    <span class="fa fa-calendar" >
												                    </span>
												                </span>
												        </div> 	
												
												</div>
																								<!-- <div class="col-xs-4"></div> -->
												
												<div class="col-xs-6">
												<label>Data Prossima Verifica Interna</label>
												  <div class='input-group date datepicker'>
												  <fmt:formatDate value="${verbale.data_prossima_verifica_interna }" pattern="dd/MM/yyyy" var="myDate" />
												               <input type='text' class="form-control input-small" id="data_prossima_verifica_interna_verb" name="data_prossima_verifica_interna_verb" value="${myDate }">
												                <span class="input-group-addon">
												                    <span class="fa fa-calendar" >
												                    </span>
												                </span>
												        </div> 	
												
												</div>
												
											<!-- 	</div> -->	
												<!-- <div class="row"> -->
													<div class="col-xs-6">
													<c:set var="tipo_ver_gvr" value="${verbale.tipo_verifica_gvr }"></c:set>
												<label>Tipo Verifica GVR</label>
												   <select id="tipo_verifica_gvr" name="tipo_verifica_gvr" class="form-control select2" data-placeholder="Seleziona Tipo Verifica GVR" style="width:100%">
												 <option value=""></option>
												 <option value="1">Funzionamento</option>
												 <option value="2">Integrità</option>
												 <option value="3">Interna</option>
												 </select>	
												
												<!-- </div> -->
											</div>
												
												</c:if>		
												
												<!-- <div class="row"> -->
													
												
												<div class="col-xs-6">
												<label>Esito Verifica</label>
												
												<c:set var="esito_verbale" value="${verbale.esito }"></c:set>
												 <select id="esito" name="esito" class="form-control select2" data-placeholder="Seleziona Esito..." style="width:100%">
												 <option value=""></option>
												 <option value="P">Positivo</option>
												 <option value="N">Negativo</option>
												 <option value="S">Sospeso</option>
												 </select>
												               
												     
												</div>
												
												<div class="col-xs-6">
												<div id="motivo_sospensione" style="display:none">
												<label>Motivo Sospensione</label>
												
												<textarea rows="3" style="width:100%" class="form-control" id="descrizione_sospensione" name="descrizione_sospensione">${verbale.descrizione_sospensione }</textarea>
												
												               
												     </div>
												</div>
												
												</div>
												<br>
												<div class="row">
												<div class="col-xs-6">
												
												
												
												<input type="checkbox" id="check_sede_diversa" name="check_sede_diversa">
												<label>Sede Diversa da sede Attrezzatura</label>
												               
												    
												</div>
												</div>
												
												<div id="sede_diversa" style="display:none">
												
												<br>
												<div class="row">
												<div class="col-xs-3">
												<label>Indirizzo</label>
												<input type="text" id="indirizzo" name="indirizzo" class="form-control" value="${verbale.attrezzatura.indirizzo_div }">
												
												</div>
												
												<div class="col-xs-3">
												<label>CAP</label>
												<input type="text" id="cap" name="cap" class="form-control" value="${verbale.attrezzatura.cap_div }">
												
												</div>
										       <div class="col-xs-3">
												<label>Comune</label>
												<select id="comune" name="comune" class="form-control select2" disabled></select>
												
												</div>
												<div class="col-xs-3">
												<label>Provincia</label>
												<input type="text" name="provincia" id="provincia" class="form-control" readonly value="${verbale.attrezzatura.provincia_div }">
												
												</div>
												<div class="col-xs-3">
												<label>Regione</label>
												<input type="text" name="regione" id="regione" class="form-control" readonly  value="${verbale.attrezzatura.regione_div }">
												
												</div>
												</div>
												
												</div>
												
												
											<c:if test="${verbale.attrezzatura.comune_div!=null &&  verbale.attrezzatura.comune_div!=''}">
												<input type="hidden" id="check_sede" name="check_sede" value="1">
											</c:if>
											
												
											<c:if test="${verbale.attrezzatura.comune_div==null ||  verbale.attrezzatura.comune_div==''}">
												<input type="hidden" id="check_sede" name="check_sede" value="0">
											</c:if>	
												
												<!-- </div>	 -->										
												</div>
             					</div>
             					
             					</div>
             				</div>
             					</c:if>
								</form>
								
								
								
								
								
								
								
								<c:if test="${verbale.getSchedaTecnica()!=null && verbale.getSchedaTecnica().getStato().getId()>=3 }">
        							<div class="row">         
        								<div class="col-xs-12">
											<div class="box box-danger box-solid">
												<div class="box-header with-border">
													<c:if test="${verbale.getStato().getId()==8 }">
														Compila Scheda Tecnica
													</c:if>
													<c:if test="${verbale.getStato().getId()!=8 }">
	 													Controllo Scheda Tecnica
	 												</c:if>
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
    	    													<c:set var="storicoModificheVerb" value="${storicoModificheVerb}" scope="request"></c:set>        	
    	    													<c:set var="storicoModificheSkTec" value="${storicoModificheSkTec}" scope="request"></c:set>    
    	    													<c:set var="type" value="SchedaTecnica" scope="request"></c:set>    	      	    													
																<jsp:include page="gestioneVerbaleDettaglio.jsp"></jsp:include>        													

        													</div>
														</c:forEach>
													</form>
												</div>
												<div class="box-footer" id="formScTecnicabox">																										
													<c:if test="${user.checkPermesso('UPD_VERBALE')}">
													<%-- 	<c:if test='${verbale.getSchedaTecnica().getStato().getId()!= 8}'>
															<button type="button" class="btn btn-default ml-1 savebutt" onclick="modificaRisposte(${verbale.getSchedaTecnica().getId()},'formScTecnica')" style="margin-left: 1em; float: right;">	
																<span >SALVA MODIFICHE</span>
															</button>	
													
															<button type="button" class="btn btn-default ml-1 savebutt" onclick="annullaModifiche('formScTecnica')" style="margin-left: 1em; float: right;">	
																<span >ANNULLA MODIFICHE</span>
															</button>
														</c:if> --%>
														<c:if test='${verbale.getSchedaTecnica().getStato().getId()== 8}'>
															<button type="button" class="btn btn-default ml-1 savebutt" onclick="salvaRisposteCompWeb(${verbale.getSchedaTecnica().getId()},'formScTecnica', 'salvaRisposteCompWeb')" style="margin-left: 1em; float: right;">	
																<span >SALVA MODIFICHE</span>
															</button>
															<button type="button" class="btn btn-default ml-1 changestate formVerbalebox" onclick="salvaRisposteCompWeb(${verbale.getSchedaTecnica().getId()},'formScTecnica', 'confermaRisposteCompWeb')" style="margin-left: 1em; color:#000000 !important; background-color:${verbale.getStato().getColore(5)} !important; float: right;">
																<i class="glyphicon glyphicon glyphicon-ok"></i>
																<span >CONFERMA</span>
															</button>
														</c:if>	
														<c:if test='${verbale.getSchedaTecnica().getStato().getId()== 6}'>
															<button type="button" class="btn btn-default ml-1 savebutt" onclick="modificaRisposte(${verbale.getSchedaTecnica().getId()},'formScTecnica')" style="margin-left: 1em; float: right;">	
																<span >SALVA MODIFICHE</span>
															</button>	
															<button type="button" class="btn btn-default ml-1 changestate formVerbalebox" onclick="salvaRisposteCompWeb(${verbale.getSchedaTecnica().getId()},'formScTecnica', 'confermaRisposteCompWeb')" style="margin-left: 1em; color:#000000 !important; background-color:${verbale.getStato().getColore(5)} !important; float: right;">
																<i class="glyphicon glyphicon glyphicon-ok"></i>
																<span >CONFERMA</span>
															</button>
														</c:if>	
													</c:if>
													<c:if test='${verbale.getSchedaTecnica().getStato().getId()== 4 && (user.checkRuolo("AM") || user.checkRuolo("RT"))}'>			
														<c:if test="${user.checkPermesso('CH_STA_VERBALE')}">
															<button type="button" class="btn btn-default ml-1 savebutt" onclick="modificaRisposte(${verbale.getSchedaTecnica().getId()},'formScTecnica')" style="margin-left: 1em; float: right;">	
																<span >SALVA MODIFICHE</span>
															</button>	
														
            	      										<button type="button" class="btn btn-default  ml-1 changestate formScTecnicabox" onclick="preCambioStato(${verbale.getSchedaTecnica().getId()},'formScTecnica','6')" style="margin-left: 1em; color:#000000 !important; background-color:${verbale.getStato().getColore(6)} !important; float: right;">
                		  										<i class="glyphicon glyphicon-remove"></i>
                	  											<span >RIFIUTATO</span>
            	      										</button>
										
															<button type="button" class="btn btn-default ml-1 changestate formScTecnicabox" onclick="preCambioStato(${verbale.getSchedaTecnica().getId()},'formScTecnica','5')" style="margin-left: 1em; color:#000000 !important; background-color:${verbale.getStato().getColore(5)} !important; float: right;">
																<i class="glyphicon glyphicon glyphicon-ok"></i>
																<span >ACCETTATO</span>
															</button>
														</c:if>
													</c:if>		
												</div>
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
						
						<div id="modalCambioStatoVerbaleCompWeb" class="modal fade" role="dialog" aria-labelledby="modalCambioStatoVerbaleCompWeb">
   							<div class="modal-dialog modal-lg" role="document">
    							<div class="modal-content">
     								<div class="modal-header">
        								<button type="button" class="close" data-dismiss="modal" aria-label="Close">
        									<span aria-hidden="true">&times;</span>
        								</button>
        								<h4 class="modal-title" id="myModalLabel">Compilazione Web</h4>
      								</div>
      									
       								<div class="modal-body" >
       									<div class="row">
											<label class="col-sm-12" style="text-align:center;">Attenzione, sicuro di voler compilare il questionario direttamente dall'interfaccia WEB? </label>
                  							
                  							<div class="col-sm-12" style="margin:5px ; text-align:center;">
                  								<c:if test="${user.checkPermesso('CH_STA_VERBALE')}">										
													<a class="btn btn-sm" onclick="salvaCambioStato(null,null,'8')" style="color:#000000 !important; background-color:${verbale.getStato().getColore(8)} !important;">
														<i class="glyphicon glyphicon glyphicon-ok"></i>
														<span>CAMBIA STATO</span>
													</a>
												</c:if>											      										
											</div>											
										</div>
    								</div>
    								<div class="modal-footer">
										<button onclick=" $('#modalCambioStatoVerbaleCompWeb').modal('hide');" class="btn btn-danger" >Esci</button>
	      							</div>
  								</div>
							</div>
						</div>
						
						<div id="confirmModal" class="modal fade" role="dialog" aria-labelledby="myLargeModalLabel">
    						<div class="modal-dialog" role="document">
    							<div class="modal-content">
     								<div class="modal-header">
        								<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        								<h4 class="modal-title text-center" id="myModalLabel">Attenzione!</h4>
      								</div>
       								<div class="modal-body">
										<h3>Sono state apportate delle modifiche, desideri di volerle salvare prima di procedere?</h3>
										<input id="idverbCambioStato" disabled="disabled" style="display:none" readonly="readonly">
										<input id="idformCambioStato" disabled="disabled" style="display:none" readonly="readonly">
										<input id="idstatoCambioStato" disabled="disabled" style="display:none" readonly="readonly">
										
  		 							</div>
      								<div class="modal-footer">
      									<button type="button" class="btn btn-default" onclick="cambiaStato(true)" >Salva e cambia stato</button>
      									<button type="button" class="btn btn-default" onclick="cambiaStato(false)" >Annulla modifiche e cambia stato</button>      									
        								<button type="button" class="btn btn-default" data-dismiss="modal">Chiudi</button>
      								</div>
    							</div>
  							</div>
						</div> 
						
						<div id="confirmCertificato" class="modal fade" role="dialog" aria-labelledby="myLargeModalLabel">
    						<div class="modal-dialog" role="document">
    							<div class="modal-content">
     								<div class="modal-header">
        								<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        								<h4 class="modal-title text-center" id="myModalLabel">Attenzione!</h4>
      								</div>
       								<div class="modal-body">
										<h3 class="text-center">Attenzione, stai per generare un certificato!<br/>
											Questa operazione non può essere annullata. <br/>
											Sei sicuro di voler generare il certificato?</h3>
  		 							</div>
      								<div class="modal-footer">
      									<button type="button" class="btn btn-default" onclick="generaCertificato(${verbale.getId()})" >Genera Certificato</button>     									
        								<button type="button" class="btn btn-default" data-dismiss="modal">Annulla</button>
      								</div>
    							</div>
  							</div>
						</div>
						<div id="confirmSchedaTecnica" class="modal fade" role="dialog" aria-labelledby="myLargeModalLabel">
    						<div class="modal-dialog" role="document">
    							<div class="modal-content">
     								<div class="modal-header">
        								<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        								<h4 class="modal-title text-center" id="myModalLabel">Attenzione!</h4>
      								</div>
       								<div class="modal-body">
										<h3 class="text-center">Attenzione, stai per generare una scheda tecnica!<br/>
											Questa operazione non può essere annullata. <br/>
											Sei sicuro di voler generare la scheda tecnica?</h3>
  		 							</div>
      								<div class="modal-footer">
      									<button type="button" class="btn btn-default" onclick="generaCertificato(${verbale.getSchedaTecnica().getId()})" >Genera Scheda Tecnica</button>     									
        								<button type="button" class="btn btn-default" data-dismiss="modal">Annulla</button>
      								</div>
    							</div>
  							</div>
						</div> 
						
						<div id="confirmRifiuta" class="modal fade" role="dialog" aria-labelledby="myLargeModalLabel">
    						<div class="modal-dialog" role="document">
    							<div class="modal-content">
     								<div class="modal-header">
        								<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        								<h4 class="modal-title text-center" id="myModalLabel">Attenzione!</h4>
      								</div>
       								<div class="modal-body">
										<h3 class="text-center">Attenzione, stai per annullare un certificato!<br/>
											Questa operazione non può essere annullata. <br/>
											Sei sicuro di voler annullare il certificato?</h3>
  		 							</div>
      								<div class="modal-footer">
      									<button type="button" class="btn btn-default" onclick="salvaCambioStato(null,null,'6')" >Rifiuta per modifica</button>     									
        								<button type="button" class="btn btn-default" data-dismiss="modal">Annulla</button>
      								</div>
    							</div>
  							</div>
						</div>
						
						<div id="confirmRifiutaDaVerificare" class="modal fade" role="dialog" aria-labelledby="myLargeModalLabel">
    						<div class="modal-dialog" role="document">
    							<div class="modal-content">
     								<div class="modal-header">
        								<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        								<h4 class="modal-title text-center" id="myModalLabel">Attenzione!</h4>
      								</div>
       								<div class="modal-body">
										<h3 class="text-center">Attenzione, stai per annullare un certificato!<br/>
											Questa operazione non può essere annullata. <br/>
											Sei sicuro di voler annullare il certificato?</h3>
  		 							</div>
      								<div class="modal-footer">
      									<button type="button" class="btn btn-default" onclick="preCambioStato(${verbale.getId()},'formVerbale','6')" >Rifiuta per modifica</button>     									
        								<button type="button" class="btn btn-default" data-dismiss="modal">Annulla</button>
      								</div>
    							</div>
  							</div>
						</div>
						
						<div id="confirmConferma" class="modal fade" role="dialog" aria-labelledby="myLargeModalLabel">
    						<div class="modal-dialog" role="document">
    							<div class="modal-content">
     								<div class="modal-header">
        								<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        								<h4 class="modal-title text-center" id="myModalLabel">Attenzione!</h4>
      								</div>
       								<div class="modal-body">
										<h3 class="text-center">
										Il verbale sarà inviato in approvazione al RT. <br> Sicuro di voler procedere?
											
											</h3>
  		 							</div>
      								<div class="modal-footer">
      									<button type="button" class="btn btn-default" onclick="salvaRisposteCompWeb(${verbale.getId()},'formVerbale', 'confermaRisposteCompWeb')" >Conferma</button>     									
        								<button type="button" class="btn btn-default" data-dismiss="modal">Annulla</button>
      								</div>
    							</div>
  							</div>
						</div>
						
						<div id="listChange" class="modal fade" role="dialog" aria-labelledby="myLargeModalLabel">
    						<div class="modal-dialog" role="document">
    							<div class="modal-content">
     								<div class="modal-header">
        								<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        								<h4 class="modal-title" id="myModalLabel">Lista Modifiche</h4>
      								</div>
       								<div class="modal-body">
										<div id="listChangeDiv">				
										
										</div>	   
  		 							</div>
      								<div class="modal-footer">
        								<button type="button" class="btn btn-outline" data-dismiss="modal">Chiudi</button>
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

						
	 <form class="form-horizontal" id="formNuovaAttrezzatura">
<div id="modalDettaglioAttrezzatura" class="modal fade" role="dialog" aria-labelledby="myLargeModalLabel">
    <div class="modal-dialog modal-md" role="document">
    <div class="modal-content">
     <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Dettaglio Attrezzatura</h4>
      </div>
       <div class="modal-body">

       
        
         <div class="form-group">
          <label for="inputEmail" class="col-sm-4 control-label">Cliente:</label>

         <div class="col-sm-8">
          <select name="cliente" id="cliente" data-placeholder="Seleziona Cliente..." disabled class="form-control select2" aria-hidden="true" data-live-search="true" style="width:100%">
                  <option value=""></option>
                      <c:forEach items="${listaClienti}" var="cliente">
                           <option value="${cliente.__id}">${cliente.nome} </option> 
                     </c:forEach>
                  </select>
    	</div>
   </div>
    <div class="form-group">
          <label for="inputEmail" class="col-sm-4 control-label">Sede:</label>

         <div class="col-sm-8">
       <select name="sede" id="sede" data-placeholder="Seleziona Sede..."  disabled class="form-control select2" aria-hidden="true" data-live-search="true" style="width:100%">
                   <option value=""></option>
             		<c:forEach items="${listaSedi}" var="sedi">
                        <option value="${sedi.__id}_${sedi.id__cliente_}">${sedi.descrizione} - ${sedi.indirizzo} - ${sedi.comune} (${sedi.siglaProvincia})</option>              
                     	</c:forEach>
                  </select>
    	</div>
   </div>
              

    <div class="form-group">
          <label for="inputEmail" class="col-sm-4 control-label">Numero matricola INAIL:</label>

         <div class="col-sm-8">
         <input class="form-control" id="matricola_inail" type="text" name="matricola_inail" readonly value=""/>
    	</div>
   </div>
	<div class="form-group">
          <label for="inputEmail" class="col-sm-4 control-label">Numero di fabbrica:</label>

         <div class="col-sm-8">
         <input class="form-control" id="numero_fabbrica" type="text" name="numero_fabbrica" readonly value=""/>
    	</div>
   </div>

   <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Descrizione:</label>
        <div class="col-sm-8">
                      <input class="form-control" id="descrizione" type="text" name="descrizione" readonly value=""/>
    </div>
    </div>
    <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Anno di costruzione:</label>
        <div class="col-sm-8">
                      <input class="form-control" id="anno_costruzione" type="number" name="anno_costruzione"  value="" readonly/>
    </div>
    </div>
    <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Fabbricante:</label>
        <div class="col-sm-8">
                      <input class="form-control" id="fabbricante" type="text" name="fabbricante"  value="" readonly/>
    </div>
    </div>
    <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Modello:</label>
        <div class="col-sm-8">
                      <input class="form-control" id="modello" type="text" name="modello"  value="" readonly/>
    </div>
    </div>
    <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Settore d'impiego:</label>
        <div class="col-sm-8">
                      <input class="form-control" id="settore_impiego" type="text" name="settore_impiego"  value="" readonly/>
    </div>
    </div>
   
    <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Gruppo:</label>
        <div class="col-sm-8">
                      <input class="form-control" id="tipo_attivita" type="text" name=tipo_attivita readonly value="" />
    </div>
    </div>



  
  <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Tipo attrezzatura:</label>
        <div class="col-sm-8">
                     
          <input class="form-control" readonly id="tipo_attrezzatura" name="tipo_attrezzatura" value="" type="text">
	 
	
               
    </div>
    </div>
    
            <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Tipo attrezzatura GVR:</label>
        <div class="col-sm-8">

          <input class="form-control" readonly id="tipo_attrezzatura_gvr" name="tipo_attrezzatura_gvr" value="" type="text">
    </div>
    </div>
    
  <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">ID specifica:</label>
        <div class="col-sm-8">
                     
         
            <input class="form-control" id="id_specifica" type="text" name="id_specifica" readonly  value=""/>
		
    
    </div>
    </div>
    
    
     <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Sogg. messa servizio GVR:</label>
        <div class="col-sm-8">
                     
          
        <input class="form-control" id="sogg_messa_serv_GVR" type="text" name="sogg_messa_serv_GVR" readonly  value=""/>
    </div>
    </div>
    
    
             <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">N. panieri idroestrattori:</label>
        <div class="col-sm-8">
                      <input class="form-control" id="n_panieri_idroestrattori" type="text" name="n_panieri_idroestrattori" readonly  value=""/>
    </div>
       </div> 
       
       
     <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Marcatura:</label>
        <div class="col-sm-8">
                     
          <input class="form-control" id="marcatura" type="text" name="marcatura"  value="" readonly/>
    </div>
    </div>
    
    
       <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">N. ID ON:</label>
        <div class="col-sm-8">
                      <input class="form-control" id="n_id_on" type="text" name="n_id_on"  value="" readonly/>
    </div>
       </div> 




         <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Data verifica funzionamento:</label>
        <div class="col-sm-8">
                      <input class="form-control " id="data_verifica_funzionamento" type="text" name="data_verifica_funzionamento"  value="" data-date-format="dd/mm/yyyy" readonly/>
    </div>
       </div> 
       
         <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Data prossima verifica funzionamento:</label>
        <div class="col-sm-8">
                      <input class="form-control " id="data_prossima_verifica_funzionamento" type="text" name="data_prossima_verifica_funzionamento"  value="" data-date-format="dd/mm/yyyy" readonly/>
    </div>
       </div> 
       
             <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Data verifica integrità:</label>
        <div class="col-sm-8">
                      <input class="form-control " id="data_verifica_integrita" type="text" name="data_verifica_integrita"  value="" data-date-format="dd/mm/yyyy" readonly/>
    </div>
       </div> 
       
         <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Data prossima verifica integrità:</label>
        <div class="col-sm-8">
                      <input class="form-control " id="data_prossima_verifica_integrita" type="text" name="data_prossima_verifica_integrita"  value="" data-date-format="dd/mm/yyyy" readonly/>
    </div>
       </div>
             <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Data verifica interna:</label>
        <div class="col-sm-8">
                      <input class="form-control " id="data_verifica_interna" type="text" name="data_verifica_interna"  value="" data-date-format="dd/mm/yyyy" readonly/>
    </div>
       </div> 
       
         <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Data prossima verifica interna:</label>
        <div class="col-sm-8">
                      <input class="form-control " id="data_prossima_verifica_interna" type="text" name="data_prossima_verifica_interna"  value="" data-date-format="dd/mm/yyyy" readonly/>
    </div>
       </div>
 <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Note tecniche:</label>
        <div class="col-sm-8">
                      <textarea class="form-control" id="note_tecniche" name="note_tecniche" rows ="3" readonly></textarea>
    </div>
    </div>
    <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Note generiche:</label>
        <div class="col-sm-8">
                      <textarea class="form-control" id="note_generiche" name="note_generiche" rows ="3" readonly></textarea>
    </div>
    </div>

  		 </div>
      <div class="modal-footer">

      </div>
    </div>
  </div>
 
</div>
   </form>
						
						
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
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/datejs/1.0/date.min.js"></script>
		<script src="js/verbale.js" type="text/javascript"></script>
		<script type="text/javascript" src="plugins/datepicker/locales/bootstrap-datepicker.it.js"></script> 
<!-- <script type="text/javascript" src="plugins/datejs/date.js"></script> -->
 		<script type="text/javascript">
		   
 		function checkStrumentoVerificatore(){
 			
 			var strumento = $('#strumento_verificatore').val();
 			var ret = true;
 			
 			
 			if(strumento==''){
 				ret = false;
 			}
 			
 			return ret;
 		}
 	     $('.datepicker').datepicker({
 			 format: "dd/mm/yyyy"
 		 });    
 		
 		
 		function dettaglioAttrezzatura(id_attrezzatura, matricola_inail, numero_fabbrica, tipo_attivita, descrizione, id_cliente, id_sede,
 				data_verifica_funzionamento, data_prossima_verifica_funzionamento,data_verifica_integrita, data_prossima_verifica_integrita, data_verifica_interna, data_prossima_verifica_interna,
 				anno_costruzione, fabbricante, modello, settore_impiego, note_tecniche, note_generiche, obsoleta, tipo_attrezzatura, tipo_attrezzatura_gvr,id_specifica, sogg_messa_serv_gvr, n_panieri_idroestrattori, marcatura,
 				n_id_on, data_scadenza_ventennale){
 			
 			
 			$('#id_attrezzatura').val(id_attrezzatura);
 			$('#matricola_inail').val(matricola_inail);
 			$('#numero_fabbrica').val(numero_fabbrica);
 			$('#tipo_attivita').val(tipo_attivita);
 			$('#descrizione').val(descrizione);
 			$('#cliente').val(id_cliente);
 			$('#cliente').change();
 			if(id_sede!=0){
 				$('#sede').val(id_sede+"_"+id_cliente);	
 			}else{
 				$('#sede').val(0);
 			}
 			
 			$('#sede').change();
 			$('#anno_costruzione').val(anno_costruzione);
 			$('#fabbricante').val(fabbricante);
 			$('#modello').val(modello);
 			$('#settore_impiego').val(settore_impiego);
 			$('#note_tecniche').val(note_tecniche);
 			$('#note_generiche').val(note_generiche);	
 			
 			if(data_verifica_funzionamento!=null && data_verifica_funzionamento!= ''){
 				$('#data_verifica_funzionamento').val(Date.parse(data_verifica_funzionamento).toString("dd/MM/yyyy"));	
 			}
 			if(data_prossima_verifica_funzionamento!=null && data_prossima_verifica_funzionamento!= ''){
 				$('#data_prossima_verifica_funzionamento').val(Date.parse(data_prossima_verifica_funzionamento).toString("dd/MM/yyyy"));	
 			}
 			if(data_verifica_integrita!=null && data_verifica_integrita!= ''){
 				$('#data_verifica_integrita').val(Date.parse(data_verifica_integrita).toString("dd/MM/yyyy"));	
 			}
 			if(data_prossima_verifica_integrita!=null && data_prossima_verifica_integrita!= ''){
 				$('#data_prossima_verifica_integrita').val(Date.parse(data_prossima_verifica_integrita).toString("dd/MM/yyyy"));	
 			}
 			if(data_verifica_interna!=null && data_verifica_interna!= ''){
 				$('#data_verifica_interna').val(Date.parse(data_verifica_interna).toString("dd/MM/yyyy"));	
 			}
 			if(data_prossima_verifica_interna!=null && data_prossima_verifica_interna!= ''){
 				$('#data_prossima_verifica_interna').val(Date.parse(data_prossima_verifica_interna).toString("dd/MM/yyyy"));	
 			}
 			
 		//	$('#id_attrezzatura').val(id_attrezzatura);	
 			
 			/* $('#data_prossima_verifica_funzionamento').datepicker({
 					format: "dd/MM/yyyy"
 			}); */
 			
 			if(obsoleta==0){
 				$('#rendi_obsoleta').addClass("btn-danger");
 				$('#rendi_obsoleta').show();
 				$('#rendi_non_obsoleta').hide();
 			}else{
 				$('#rendi_non_obsoleta').addClass("btn-success");
 				$('#rendi_obsoleta').hide();
 				$('#rendi_non_obsoleta').show();
 			}
 			
 			
 			
				
			$('#tipo_attrezzatura').val(tipo_attrezzatura);	
 			$('#tipo_attrezzatura_gvr').val(tipo_attrezzatura_gvr);	
 			$('#id_specifica').val(id_specifica);	
 			$('#sogg_messa_serv_gvr').val(sogg_messa_serv_gvr);	
 			$('#n_panieri_idroestrattori').val(n_panieri_idroestrattori);
 			$('#marcatura').val(marcatura);	
 			$('#n_id_on').val(n_id_on);	
 			
 			
				if(data_scadenza_ventennale!=null && data_scadenza_ventennale!= ''){
	 				$('#data_scadenza_ventennale').val(Date.parse(data_scadenza_ventennale).toString("dd/MM/yyyy"));	
	 			}
 			
 			$('#modalDettaglioAttrezzatura').modal();
 		}
 		
 		$('#esito').change(function(){
 		
 			$('#motivo_sospensione').hide();
 			
 			if($(this).val()=="S"){
 				
 				$('#motivo_sospensione').show();
 			}else{
 				$('#descrizione_sospensione').val(null);
 			}
 			
 		});
 		
 		
		$('#check_sede_diversa').on('ifChecked', function(event) {
			
			$('#sede_diversa').show();
			
			$('#check_sede').val("1");
			if(sede_div!=''){
				$('#cap').focusout();
				$('#check_sede_diversa').iCheck('check');
			
				
			}
			
		});
 		
	$('#check_sede_diversa').on('ifUnchecked', function(event) {
			
			$('#sede_diversa').hide();
			$('#check_sede').val("0");
			$('#indirizzo').val("");
			$('#cap').val("");
			$('#comune').attr('disabled', true);
			$('#provincia').val("");
			$('#regione').val("");
			
		});
	
	var sede_div = "${verbale.attrezzatura.comune_div }";
	
			$(document).ready(function() {
				
				$('.rispVerb').on('ifChanged', function(event) {
					$('.'+$(this).attr('id')).find('input').val('');
					//$('.'+$(this).attr('id')).find('textarea').val('');
					
					var index = $(this).attr('id').split("_")[1]
					if(index != null){
						var textarea = $('.'+$(this).attr('id').split("_")[0]).find('textarea')[index];
					}
					if(textarea != null){
						$('#comment'+textarea.name).val('');
					}
					$('.'+$(this).attr('id')).find(':checkbox').removeAttr('checked').iCheck('update');
					$('.'+$(this).attr('id')).find(':radio').removeAttr('checked').iCheck('update');
					
				});
				
				var esito_verbale = "${esito_verbale}";
				var tipo_ver_gvr = "${tipo_ver_gvr}";
				
			
				
				if(sede_div!=''){
					$('#cap').focusout();
					$('#check_sede_diversa').iCheck('check');
				
					
				}
				
				$('#esito').select2();
				$('#tipo_verifica_gvr').select2();
				
								
				if(esito_verbale!=''){
					$('#esito').val(esito_verbale);
					$('#esito').change();
				}
				if(tipo_ver_gvr!='0'){
					$('#tipo_verifica_gvr').val(tipo_ver_gvr);
					$('#tipo_verifica_gvr').change();
				}
				
				$('#strumento_verificatore').select2();
				
				$('.rispVerb').on('ifClicked', function (ev) {
				
					var id = this.id;
					if(this.type=='radio' && this.checked && !this.required){
						
						$('#'+id).iCheck('uncheck');
					}
				})
				
				$("#changedForm").val(false);
				$(":input",this).change(function() {
					$("#changedForm").val(true);
				});
				
  				$('input').on('ifChanged', function (event) { 
					$(event.target).trigger('change'); });  
				
				if (!$("#certificatoBox").length) {
					$("#schedaTecnicaBox").removeClass("col-md-6");
				} else if (!$("#schedaTecnicaBox").length){
					$("#certificatoBox").removeClass("col-md-6");
				}			
				
				
				
				
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
								location.reload();
								/* if(idform==null){
									location.reload();
								}else{
									$("."+idform+"box").css("display", "none");
									//TODO: modificare logica
									if(idform=="formVerbale" && ${user.checkPermesso('GENERA_CERTIFICATO')}){									
										$("#"+idform+"box").append('<button class="btn btn-default pull-right" onClick="$("#confirmCertificato").modal("show");" style="margin-left:5px"><i class="glyphicon glyphicon-edit"></i> Genera Certificato</button>');
									}else if(idform=="formScTecnica" && ${user.checkPermesso('GENERA_SKTECNICA')}){
										$("#"+idform+"box").append('<button class="btn btn-default pull-right" onClick="$("#confirmSchedaTecnica").modal("show");" style="margin-left:5px"><i class="glyphicon glyphicon-edit"></i> Genera Scheda Tecnica</button>');
									}
								} */
							}else{							
								$('#modalErrorDiv').html(data.messaggio);
								$('#myModalError').removeClass();
								$('#myModalError').addClass("modal modal-danger");
								$('#myModalError').modal('show');															
							}		
							pleaseWaitDiv.modal('hide');	
							$("#changedForm").val(false);
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
				$("#confirmCertificato").modal('hide');
				$("#confirmSchedaTecnica").modal('hide');
				pleaseWaitDiv = $('#pleaseWaitDialog');
				pleaseWaitDiv.modal();
				
				
				
				$.ajax({
					type: "POST",
					url: "gestioneVerbale.do?action=generaCertificato",
					data : "idVerbale="+id,				
					dataType: "json",
					success: function( data, textStatus) {
						
						
						/* salvaRisposteCompWeb(id,'formVerbale', 'confermaRisposteCompWeb'); */
						preCambioStato(id,'formVerbale','5')
						
						//location.reload();
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
			
			function preCambioStato(idverbale, idform, idstato){
				
				if($("#changedForm").val()=='true'){
					$("#idverbCambioStato").val(idverbale);
					$("#idformCambioStato").val(idform);
					$("#idstatoCambioStato").val(idstato);
				
					$("#confirmModal").modal('show');
				}else{
					salvaCambioStato(idverbale, idform, idstato);
				}
			}
			
			function cambiaStato(value){
				$("#confirmModal").modal('hide');
			
				var idverbale=$("#idverbCambioStato").val();
				var idform=$("#idformCambioStato").val();
				var idstato=$("#idstatoCambioStato").val();
				
				if(value){
					modificaRisposte(idverbale,idform);
				}else{
					annullaModifiche(idform);
				}
				
				salvaCambioStato(idverbale, idform, idstato);
			}
			
			function modificaRisposte(idVerb,idform){
				
			if(!checkStrumentoVerificatore()){
					
					$('#modalErrorDiv').html("Il campo strumento verificatore è obbligatorio");
					$('#myModalError').removeClass();
					$('#myModalError').addClass("modal modal-danger");
					$('#myModalError').modal('show');	
					
				}else{
					
					
				
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
						
						location.reload();
					},

					error: function(jqXHR, textStatus, errorThrown){
						$('#errorMsg').html("<h3 class='label label-danger'>"+textStatus+"</h3>");
						//callAction('logout.do');
						pleaseWaitDiv.modal('hide');
					}
				});
			}		
			}
			
			function salvaRisposteCompWeb(idVerb,idform,action){
				
				var gvr = "${isGvr}";
				
			if(!checkStrumentoVerificatore()){
					
				$('#modalErrorDiv').html("Il campo strumento verificatore è obbligatorio");
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");
				$('#myModalError').modal('show');	
					
			}
			else if($('#data_verifica').val()==''){
				
				$('#modalErrorDiv').html("Il campo data verifica è obbligatorio");
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");
				$('#myModalError').modal('show');	
			}
			else if($('#data_prossima_verifica_verb').val()==''){
				
				$('#modalErrorDiv').html("Il campo data prossima verifica è obbligatorio");
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");
				$('#myModalError').modal('show');	
			}
			else if(gvr !='' && gvr=='1' &&$('#data_verifica_integrita_verb').val()==''){
				
				$('#modalErrorDiv').html("Il campo data verifica integrità è obbligatorio");
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");
				$('#myModalError').modal('show');	
			}
			else if(gvr !='' && gvr=='1' &&$('#data_prossima_verifica_integrita_verb').val()==''){
				
				$('#modalErrorDiv').html("Il campo data prossima verifica integrità è obbligatorio");
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");
				$('#myModalError').modal('show');	
			}
			else if(gvr !='' && gvr=='1' &&$('#data_verifica_interna_verb').val()==''){
				
				$('#modalErrorDiv').html("Il campo data prossima verifica interna è obbligatorio");
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");
				$('#myModalError').modal('show');	
			}
			else if(gvr !='' && gvr=='1' &&$('#data_prossima_verifica_interna_verb').val()==''){
				
				$('#modalErrorDiv').html("Il campo data prossima verifica interna è obbligatorio");
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");
				$('#myModalError').modal('show');	
			}
			
			else if($('#esito').val()==''){
				
				$('#modalErrorDiv').html("Il campo Esito verifica è obbligatorio");
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");
				$('#myModalError').modal('show');	
			}
			else if($('#esito').val()=='S' && $('#descrizione_sospensione').val()==''){
				
				$('#modalErrorDiv').html("Il campo Motivo sospensione è obbligatorio");
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");
				$('#myModalError').modal('show');	
			}
			
			else if(gvr !='' && gvr=='1' &&$('#tipo_verifica_gvr').val()==''){
				
				$('#modalErrorDiv').html("Il campo Tipo verifica GVR è obbligatorio");
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");
				$('#myModalError').modal('show');	
			}
			
			else if($('#check_sede_diversa').is(':checked')  && $('#indirizzo').val()==''){
				
				$('#modalErrorDiv').html("Il campo indirizzo è obbligatorio");
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");
				$('#myModalError').modal('show');	
			}
			
			else if($('#check_sede_diversa').is(':checked') && $('#cap').val()==''){
				
				$('#modalErrorDiv').html("Il campo cap è obbligatorio");
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-danger");
				$('#myModalError').modal('show');	
			}
			
			else{
				
				pleaseWaitDiv = $('#pleaseWaitDialog');
				pleaseWaitDiv.modal();		
				
				$.ajax({
					type: "GET",
					url: "gestioneVerbale.do?action="+action+"&currentState=compilazioneWeb&idVerbale="+idVerb,
					data : $("#"+idform).serializeArray(),				
					dataType: "json",
					success: function( data, textStatus) {

						pleaseWaitDiv.modal('hide');
						if(!data.success){	
							$('#modalErrorDiv').html(data.messaggio);
							$('#myModalError').removeClass();
							$('#myModalError').addClass("modal modal-danger");
							$('#myModalError').modal('show');
						} else {
							if(action == "confermaRisposteCompWeb") {
								location.reload();
							}
						}
					
					},

					error: function(jqXHR, textStatus, errorThrown){
						$('#errorMsg').html("<h3 class='label label-danger'>"+textStatus+"</h3>");
						//callAction('logout.do');
						pleaseWaitDiv.modal('hide');
					}
				});
				}			
			}
			
			function annullaModifiche(idform){
				document.getElementById(idform).reset();
				$("#changedForm").val(false);
				$('input').iCheck('update'); 
			}
			
			function uploadAllegato(inputFileElement, verbaleID) {
				
				$('#modalErrorDiv').html("Caricamento in corso");
				$('#myModalError').removeClass();
				$('#myModalError').addClass("modal modal-default");
				$('#myModalError').modal('show');
				
				if(inputFileElement.value==""){
					$('#modalErrorDiv').html("Non hai selezionato nessun file");
					$('#myModalError').removeClass();
					$('#myModalError').addClass("modal modal-danger");
					return false;
				}
				var data = new FormData();
				data.append("file", inputFileElement.files[0]);
				data.append("verbaleID", verbaleID);

				$.ajax({
					url : "allegatoUpload.do",
					type : 'POST',
					data : data,
					cache : false,
					processData : false, // Don't process the files
					contentType : false, // Set content type to false as jQuery will tell the server its a query string request
					dataType: 'json',
					success : function(data, textStatus, jqXHR) {
						$('#modalErrorDiv').html("File caricato con successo");
						$('#myModalError').removeClass();
						$('#myModalError').addClass("modal modal-success");

						$("#allegatiList").append('<li class="list-group-item"><b>'+data.fileName+'</b><a class="btn btn-default btn-xs pull-right" href="gestioneDocumento.do?idDocumento='+data.idDocumento+'" style="margin-left:5px"><i class="glyphicon glyphicon-file"></i> Download Allegato</a></li>');		
					},
					error : function(jqXHR, textStatus, errorThrown) {
						$('#modalErrorDiv').html("Si è verificato un errore durante l'upload del file. Riprova più tardi");	
						$('#myModalError').removeClass();
						$('#myModalError').addClass("modal modal-danger");
					},
				});
			}

			
			function detailStorico(idrisp){
				$.ajax({
					type: "GET",
					url: "gestioneStoricoModifiche.do?idRisposta="+idrisp,	
					dataType: "json",
					success: function( data, textStatus) {

						
						$('#listChangeDiv').html(data.dataobject);

						$('#listChange').modal('show');
						
						
						pleaseWaitDiv.modal('hide');
					},

					error: function(jqXHR, textStatus, errorThrown){
						$('#errorMsg').html("<h3 class='label label-danger'>"+textStatus+"</h3>");
						//callAction('logout.do');
						pleaseWaitDiv.modal('hide');
					}
				});
			}
			
			
			$('#cap').focusout(function(){
				
				var value = $(this).val();
				
				$.ajax({
					type: "POST",
					url: "gestioneVerbale.do?action=comuni&cap="+value,	
					dataType: "json",
					success: function( data, textStatus) {

						if(data.success){
							
							var lista_comuni = data.comuni;
							
							var opt = [];
							var comune;
							lista_comuni.forEach(function(item){
								comune = item;
								opt.push("<option value=\""+item.descrizione+"\">"+item.descrizione+"</option>")
								
							});
							
							$('#comune').attr("disabled", false);
							$('#comune').html(opt);
							$('#comune').select2();
							$('#comune').val(sede_div);
							$('#comune').change();
							if(comune!=null){
								$('#provincia').val(comune.provincia);
								$('#regione').val(comune.regione);
							}else{
								$('#comune').attr("disabled", true);
								$('#provincia').val("");
								$('#regione').val("");
							}
							
						}
						
					},

					error: function(jqXHR, textStatus, errorThrown){
						$('#errorMsg').html("<h3 class='label label-danger'>"+textStatus+"</h3>");
						//callAction('logout.do');
						pleaseWaitDiv.modal('hide');
					}
				});
				
			});
			
  		</script>	  
	</jsp:attribute> 
</t:layout>
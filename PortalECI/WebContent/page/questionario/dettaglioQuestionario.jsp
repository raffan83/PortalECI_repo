<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="it.portalECI.DTO.UtenteDTO"%>

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
          		<h1 class="pull-left">
        			Dettaglio Questionario
        			<small></small>
      			</h1>
      			<c:if test="${user.checkPermesso('UPD_QUESTIONARIO')}">
      				<a class="btn btn-default pull-right" href="gestioneQuestionario.do?idQuestionario=${questionario.id}&action=modifica"><i class="glyphicon glyphicon-edit"></i> Modifica</a>
      			</c:if>
      			<%-- <c:if test="${userObj.checkPermesso('NUOVO_INTERVENTO_METROLOGIA')}">  <button class="btn btn-default pull-right" onClick="nuovoInterventoFromModal()"><i class="glyphicon glyphicon-edit"></i> Nuovo Intervento</button></c:if> --%>
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
	 											Dati Questionario
												<div class="box-tools pull-right">		
													<button data-widget="collapse" class="btn btn-box-tool"><i class="fa fa-minus"></i></button>
												</div>
											</div>
											<div class="box-body">	
        										<ul class="list-group list-group-unbordered">
                									<li class="list-group-item">
                  										<b>Titolo</b> 
                  										<a class="pull-right">${questionario.titolo}</a>
                									</li>
                 									<li class="list-group-item">
                  										<b>Tipo</b> 
                  										<a class="pull-right">${questionario.tipo.codice}</a>
                									</li>
                									<li class="list-group-item">
                  										<b>Versione del </b> 
                  										<a class="pull-right"><fmt:formatDate pattern="dd/MM/yyyy" value="${questionario.updateDate}" /></a>
                									</li>
                									<c:if test="${questionario.domandeVerbale.size()>0 }">
	                									<li class="list-group-item">
	                  										<b>Template Verbale </b>&nbsp;
	                  										<c:if test="${user.checkPermesso('NEW_TEMPLATE_VERBALE')}">
	                  											<a href="gestioneTemplateQuestionario.do?idQuestionario=${questionario.id}&tipo=Verbale&idTemplate=${questionario.templateVerbale.id}" class="btn btn-default btn-xs pull-right">Imposta Template</a>
	                  										</c:if>
	                  										<a class="pull-right">${questionario.templateVerbale.titolo}</a>
	                									</li>
                									</c:if>
                									<c:if test="${questionario.domandeSchedaTecnica.size()>0 }">
	                									<li class="list-group-item">
	                  										<b>Template Scheda Tecnica </b>&nbsp;
	                  										<c:if test="${user.checkPermesso('NEW_TEMPLATE_SKTECNICA')}">
	                  											<a href="gestioneTemplateQuestionario.do?idQuestionario=${questionario.id}&tipo=SchedaTecnica&idTemplate=${questionario.templateSchedaTecnica.id}" class="btn btn-default btn-xs pull-right">Imposta Template</a>
	                  										</c:if> 
	                  										<a class="pull-right">${questionario.templateSchedaTecnica.titolo}</a>
	                									</li>
                									</c:if>
        										</ul>
											</div>
										</div>
									</div>
								</div>
             					<div class="row">
        							<div class="col-xs-12">
										<div class="box box-danger box-solid collapsed-box">
											<div class="box-header with-border">
	 											Lista domande verbale
												<div class="box-tools pull-right">		
													<button data-widget="collapse" class="btn btn-box-tool"><i class="fa fa-plus"></i></button>
												</div>
											</div>
											<div class="box-body">
												<c:forEach items="${questionario.domandeVerbale}" var="domanda">
													<c:set var="domanda" value="${domanda}" scope="request"></c:set>
													<jsp:include page="domanda/dettaglioDomanda.jsp"></jsp:include>
												</c:forEach>
											</div>
										</div>
            							<!-- /.box-body -->
          							</div>
          							<!-- /.box -->
        						</div>                                          
             					<div class="row">
        							<div class="col-xs-12">
										<div class="box box-danger box-solid collapsed-box">
											<div class="box-header with-border">
	 											Lista domande scheda tecnica
												<div class="box-tools pull-right">		
													<button data-widget="collapse" class="btn btn-box-tool"><i class="fa fa-plus"></i></button>
												</div>
											</div>
											<div class="box-body">
												<c:forEach items="${questionario.domandeSchedaTecnica}" var="domanda">
													<c:set var="domanda" value="${domanda}" scope="request"></c:set>
													<jsp:include page="domanda/dettaglioDomanda.jsp"></jsp:include>
												</c:forEach>
											</div>
										</div>
            							<!-- /.box-body -->
          							</div>
          							<!-- /.box -->
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
  										<div id="empty" class="testo12"></div>
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
	</jsp:attribute> 
</t:layout>
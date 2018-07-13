<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

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
      			<%--<button class="btn btn-default pull-right" onClick="nuovoInterventoFromModal()"><i class="glyphicon glyphicon-edit"></i> Nuovo Intervento</button> %-->
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
                  										<b>Data Creazione</b> 
                  										<a class="pull-right"><fmt:formatDate pattern="dd/MM/yyyy" value="${questionario.createDate}" /></a>
                									</li>
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
												<c:forEach items="${questionario.domandeVerbale }" var="domanda" >
        											<ul class="list-group list-group">
	                									<li class="list-group-item">
	                  										<b>Testo della domanda</b> 
	                  										<a class="pull-right">${domanda.testo}</a>
	                									</li>
	                									<li class="list-group-item">
	                  										<b>Placeholder della domanda</b> 
	                  										<a class="pull-right">${domanda.placeholder}</a>
	                									</li>
	                									<li class="list-group-item">
	                  										<b>Domanda obbligatoria</b> 
	                  										<a class="pull-right">${domanda.obbligatoria?'SI':'NO'}</a>
	                									</li>
	                									<li class="list-group-item">
	                  										<b>Tipo di risposta</b> 
	                  										<a class="pull-right">${domanda.risposta.tipo}</a>
	                									</li>
	                									<c:if test="${domanda.risposta.tipo=='RES_CHOICE'}">
		                									<li class="list-group-item">
		                										<b>Opzioni</b>
		                										<a class="pull-right">
																	<c:forEach items="${domanda.risposta.opzioni }" var="opzione">
					                  									${opzione.testo}<br/>
																	</c:forEach>
																</a>
					                						</li>
				                						</c:if>
	                									<c:if test="${domanda.risposta.tipo=='RES_FORMULA'}">
		                									<li class="list-group-item">
		                  										<b>Operazione</b> 
		                  										<a class="pull-right">${domanda.risposta.operatore}</a>
		                									</li>
		                									<li class="list-group-item">
		                  										<b>Nome primo valore</b> 
		                  										<a class="pull-right">${domanda.risposta.valore1}</a>
		                									</li>
		                									<li class="list-group-item">
		                  										<b>Nome secondo valore</b> 
		                  										<a class="pull-right">${domanda.risposta.valore2}</a>
		                									</li>
		                									<li class="list-group-item">
		                  										<b>Nome risultato</b> 
		                  										<a class="pull-right">${domanda.risposta.risultato}</a>
		                									</li>
				                						</c:if>
				                					</ul>
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
              									<table id="tabellaDomande" class="table table-bordered table-hover dataTable table-striped" role="grid" width="100%">
 													<thead>
 														<tr class="active"> 
 															<th>Domanda</th>
 															<th>Tipo</th>
 														</tr>
 													</thead>
 													<tbody>
 														<c:forEach items="${questionario.domandeSchedaTecnica}" var="domanda"> 
 															<tr role="row">
																<td>
  																	${domanda.testo}
																</td>
																<td>${domanda.risposta.tipo }</td>
															</tr>	 
														</c:forEach>
 													</tbody>
 												</table>  
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
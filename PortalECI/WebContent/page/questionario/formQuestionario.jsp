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
        			${questionario.id==null?"Crea nuovo":"Modifica"} questionario
        			<small></small>
      			</h1>
      			<%-- <c:if test="${userObj.checkPermesso('NUOVO_INTERVENTO_METROLOGIA')}">  <button class="btn btn-default pull-right" onClick="nuovoInterventoFromModal()"><i class="glyphicon glyphicon-edit"></i> Nuovo Intervento</button></c:if> --%>
    		</section>
			<div style="clear: both;"></div>
    		
    		<!-- Main content -->
    		<section class="content">
            <form class="form" id="questionario-form" action="gestioneQuestionario.do" method="POST">
				<c:if test="${questionario.id!=null}">
					<input type="hidden" name="idQuestionario" value="${questionario.id}" >
					<input type="hidden" name="_method" value="put" />
				</c:if>
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
											<div class="box-body form-horizontal">	
												<div class="form-group">
													<label for="titolo-input" class="col-sm-2 control-label">Titolo</label>
													<div class="col-sm-10">
														<input type="text" name="titolo" class="form-control" id="titolo-input" placeholder="Titolo" value="${questionario.titolo}">
													</div>
												</div>
		              							<div class="form-group">
		                  							<label for="categoria-verifica-select" class="col-sm-2 control-label">Categoria Verifica</label>
		                  							<div class="col-sm-10">
		    	              							<select id="categoria-verifica-select" data-placeholder="Seleziona Categoria..."  class="form-control">
		        	          								<option value="">Seleziona Categoria...</option>
		            	          							<c:forEach items="${categorie_verifica}" var="categoria">
		                	           							<option value="${categoria.id}"<c:if test="${questionario.tipo.categoria.id == categoria.id}">selected</c:if>>${categoria.codice}</option> 	
		                    	 							</c:forEach>
		                  								</select> 
		        									</div>
		        								</div>
		              							<div class="form-group">
	                  								<label for="tipo-verifica-select" class="col-sm-2 control-label">Tipo Verifica</label>
		                  							<div class="col-sm-10">
		                  								<select name="tipo" id="tipo-verifica-select" data-placeholder="Seleziona Tipo"  class="form-control" disabled="true">
		                									<option value="">Seleziona Tipo...</option>
		                									<c:forEach items="${tipi_verifica}" var="tipo">                		
			                        							<option value="${tipo.id}_${tipo.categoria.id}" <c:if test="${questionario.tipo.id == tipo.id}">selected</c:if>>${tipo.codice}</option>
		    	                 							</c:forEach>
		        	         							</select>                  
		        									</div>
		        								</div>
											</div>
										</div>
									</div>
								</div>
             					<div class="row">
        							<div class="col-xs-12">
										<div class="box box-danger box-solid">
											<div class="box-header with-border">
	 											Lista domande verbale
												<div class="box-tools pull-right">		
													<button data-widget="collapse" class="btn btn-box-tool"><i class="fa fa-minus"></i></button>
												</div>
											</div>
											<div class="box-body">
												<div id="listaDomandeVerbale" class="row">
													<c:forEach items="${questionario.domandeVerbale}" var="domanda">
														<c:set var="domanda" value="${domanda}" scope="request"></c:set>
														<c:set var="gruppo" value="Verbale" scope="request"></c:set>
														<jsp:include page="domanda/formDomanda.jsp"></jsp:include>
													</c:forEach>
												</div>
												<div class="row margin-botton">
													<div class="col-xs-12">
														<a class="btn btn-default pull-right" onclick="aggiungiDomanda('','Verbale')">
															<i class="fa fa-plus"></i> Aggiungi domanda
														</a>
													</div>
												</div>
											</div>
										</div>
            							<!-- /.box-body -->
          							</div>
          							<!-- /.box -->
        						</div>                                          
             					<div class="row">
        							<div class="col-xs-12">
										<div class="box box-danger box-solid">
											<div class="box-header with-border">
	 											Lista domande scheda tecnica
												<div class="box-tools pull-right">		
													<button data-widget="collapse" class="btn btn-box-tool"><i class="fa fa-minus"></i></button>
												</div>
											</div>
											<div class="box-body">
												<div id="listaDomandeSchedaTecnica" class="row">
													<c:forEach items="${questionario.domandeSchedaTecnica}" var="domanda">
														<c:set var="domanda" value="${domanda}" scope="request"></c:set>
														<c:set var="gruppo" value="SchedaTecnica" scope="request"></c:set>
														<jsp:include page="domanda/formDomanda.jsp"></jsp:include>
													</c:forEach>
												</div>
												<div class="row margin-botton">
													<div class="col-xs-12">
														<a class="btn btn-default pull-right" onclick="aggiungiDomanda('','SchedaTecnica')">
															<i class="fa fa-plus"></i> Aggiungi domanda
														</a>
													</div>
												</div>
											</div>
										</div>
            							<!-- /.box-body -->
          							</div>
          							<!-- /.box -->
        						</div>
        						<button class="btn btn-default pull-right" type="submit"><i class="fa fa-save"></i> Salva Questionario</button>
        					</div>
   						</div>
        			</div>
        		</div>
        	</form>                                    

			<div id="myModalError" class="modal fade" role="dialog" aria-labelledby="myLargeModalLabel">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
 							<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
 							<h4 class="modal-title" id="myModalLabel">Messaggio</h4>
						</div>
						<div class="modal-body">
							<div id="myModalErrorContent"></div>	   
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-outline" data-dismiss="modal">Chiudi</button>
						</div>
					</div>
				</div>
			</div> 
     					
     		<div id="errorMsg"><!-- Place at bottom of page --></div> 
			</section>
  		</div><!-- /.content-wrapper -->	
 
 		<t:dash-footer />
  		<t:control-sidebar />
		</div><!-- ./wrapper -->
	</jsp:attribute>
	
	<jsp:attribute name="extra_css"></jsp:attribute>
	
	<jsp:attribute name="extra_js_footer">
		<script src="js/questionario.js" type="text/javascript"></script>
		<c:if test="${error!=null }">
			<script  type="text/javascript">
			showError("${error}");
			</script>
		</c:if>
	</jsp:attribute> 
</t:layout>
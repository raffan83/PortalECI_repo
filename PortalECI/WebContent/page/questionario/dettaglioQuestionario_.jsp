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
              									<table id="tabellaDomande" class="table table-bordered table-hover dataTable table-striped" role="grid" width="100%">
 													<thead>
 														<tr class="active"> 
 															<th>Domanda</th>
 															<th>Tipo</th>
 														</tr>
 													</thead>
 													<tbody>
 														<c:forEach items="${questionario.domandeVerbale}" var="domanda"> 
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
        						                                    
  						
  						<div id="myModal" class="modal fade" role="dialog" aria-labelledby="myLargeModalLabel">
    						<div class="modal-dialog" role="document">
    							<div class="modal-content">
     								<div class="modal-header">
        								<button type="button" class="close" data-dismiss="modal" aria-label="Close">
        									<span aria-hidden="true">&times;</span>
        								</button>
        								<h4 class="modal-title" id="myModalLabel">Nuovo Intervento</h4>
      								</div>
       								<div class="modal-body">             
        								<div class="form-group">
        									<label>Tecnico Verificatore</label>
											<select class="form-control" id="tecnici" class="selectpicker">
												<option value="" disabled selected>Seleziona Tecnico...</option>
												<c:forEach items="${tecnici}" var="tecnico">
				  									<option value="${tecnico.id}">${tecnico.nominativo} </option>
												</c:forEach>
											</select>
                						</div>
              							<div class="row" style="position:relative;">
            								<div class="form-group col-sm-5">
	                  							<label>Categoria Verifica</label>
    	              							<select name="select1" id="select1" data-placeholder="Seleziona Categoria..."  class="form-control select2" aria-hidden="true" data-live-search="true">
        	          								<option value="" disabled selected>Seleziona Categoria...</option>
            	          							<c:forEach items="${categorie_verifica}" var="categoria">
                	           							<option value="${categoria.id}">${categoria.codice}</option> 	
                    	 							</c:forEach>
                  								</select> 
        									</div>
        
							    	        <div class="form-group col-sm-5">
                  								<label>Tipo Verifica</label>
                  								<select name="select2" id="select2" data-placeholder="Seleziona Tipo"  disabled class="form-control select2" aria-hidden="true" data-live-search="true">
                									<option value="" disabled selected>Seleziona Tipo...</option>
                									<c:forEach items="${tipi_verifica}" var="tipo">                		
	                        							<option value="${tipo.id}_${tipo.categoria.id}">${tipo.codice}</option>     	                            
    	                 							</c:forEach>
        	         							</select>                  
        									</div>    
        									<div class="form-group col-sm-2 text-center" style="position: absolute;	bottom: 0; right: 0;">        									
                  								<button class="btn-sm" onclick="addRow()"><i class="fa fa-plus"></i></button>              
        									</div>                                     
  										</div>		
  														
  										<div class="row">
  											<div class="col-sm-12 ">		
  											<table id="tabVerifica" class="table table-bordered table-hover dataTable table-striped" role="grid" width="100%">
 													<thead>
 														<tr class="active">
 															<th>Categoria Verifica</th>
 															<th>Tipo Verifica</th> 														
 															<td></td>
														</tr>
													</thead>
 													<tbody id="bodytabVerifica"> 			
 													
												
 													</tbody>
 												</table>  
  											</div>
  										</div>
  		 							</div>
      								<div class="modal-footer">
						        		<button type="button" class="btn btn-danger"onclick="saveInterventoFromModal()"  >Salva</button>
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
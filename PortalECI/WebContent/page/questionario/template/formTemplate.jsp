<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<t:layout title="Dashboard" bodyClass="skin-red sidebar-mini wysihtml5-supported">

	<jsp:attribute name="body_area">

		<div class="wrapper">
	
			<t:main-header />
  			<t:main-sidebar />
 
  			<!-- Content Wrapper. Contains page content -->
 			<div id="corpoframe" class="content-wrapper">
   				<!-- Content Header (Page header) -->
    			<section class="content-header">
          			<h1 class="pull-left">
        				${template.id==null?"Crea nuovo":"Modifica"} questionario 
        				<small></small>
      				</h1>
      				<c:if test="${questionario.id!=null}"> 
      					<a class="btn btn-default pull-right" href="gestioneQuestionario.do?idQuestionario=${questionario.id}">
	      					<i class="glyphicon glyphicon-left"></i> Torna al questionario
    	  				</a>
					</c:if> 
    			</section>
				<div style="clear: both;"></div>
    			<section class="content">
					<div class="row">    		            																
						<form class="form" id="template-form" action="gestioneTemplateQuestionario.do" method="POST" >
	        				<div class="col-xs-12">
	          					<div class="box">
	            					<div class="box-body">      
										<c:if test="${template.id!=null}">
											<input type="hidden" name="idTemplate" value="${template.id}">
											<input type="hidden" name="_method" value="put" />
										</c:if>
										<input type="hidden" id="idQuestionario" name="idQuestionario" value="${questionario.id}">
										<input type="hidden" id="tipo" name="tipo" value="${tipo}">
	            						<div class="row">
											<div class="col-xs-12">
												<div class="box box-danger box-solid">
													<div class="box-header with-border">
		 												Template
														<div class="box-tools pull-right">		
															<button data-widget="collapse" class="btn btn-box-tool">
																<i class="fa fa-minus"></i>
															</button>
														</div>
													</div>
													<div class="box-body">
														<div class="row">
															<div class="col-sm-6">
																<div class="form-group">
																	<label for="titolo-input" class="control-label">Titolo</label>
																	<input type="text" name="titolo" class="form-control" id="titolo-input" placeholder="Titolo" value="${template.titolo}">
																</div>
															</div>
															<div class="col-sm-6">
																<div class="form-group">
																	<label for="revisione-input" class="control-label">Revisione</label>
																	<input type="text" name="revisione" class="form-control" id="titolo-input" placeholder="Revisione" value="${template.revisione}">
																</div>
															</div>
														</div>
														<textarea id="summernoteTemplate">${template.template}</textarea>
				        							</div>
				   								</div>
				   							</div>
				   					
											<div class="col-xs-12">
												<div class="box box-danger box-solid">
													<div class="box-header with-border">
		 												Header
														<div class="box-tools pull-right">		
															<button data-widget="collapse" class="btn btn-box-tool">
																<i class="fa fa-minus"></i>
															</button>
														</div>
													</div>
													<div class="box-body">
														<div class="row">
															<div class="col-sm-12">
																<div class="form-group">
																	<label for="titolo-input" class="control-label">Carica un file da usare come header</label>
																	<div class="input-group">
																		<input type="file" name="file" class="form-control"	id="file-input-header" placeholder="File">
																		<div class="input-group-btn">
																			<a class="btn btn-danger" onclick="uploadAj(document.getElementById('file-input-header'),'header');return false;">Carica il file</a>
																		</div>
																	</div>
																</div>
																<div class="form-group">
																	<label for="titolo-input" class="control-label">O sceglilo dal menu a tendina</label>
   																	<select name="headerFileName" id="select-file-header" data-placeholder="Seleziona Header..." class="form-control">
        		          												<option value="">Nessun header...</option>
            		          											<c:forEach items="${listaHeader}" var="head">
                		           											<option value="${head}" ${template.header==head?"selected":"" }>${head}</option>
                    		 											</c:forEach>
                  													</select>
																</div>
																<textarea id="summernoteSubheader">${template.subheader}</textarea>
															</div>
														</div>
													</div>
												</div>
											</div>
											<div class="col-xs-12">
												<div class="box box-danger box-solid">
													<div class="box-header with-border">
			 											Footer
														<div class="box-tools pull-right">		
															<button data-widget="collapse" class="btn btn-box-tool">
																<i class="fa fa-minus"></i>
															</button>
														</div>
													</div>
													<div class="box-body">
														<div class="row ">
															<div class="col-sm-12">
																<div class="form-group">
																	<label for="titolo-input" class="control-label">Carica un file da usare come footer</label>
																	<div class="input-group">
																		<input type="file" class="form-control"	id="file-input-footer" placeholder="File">
																		<div class="input-group-btn">
																			<a class="btn btn-danger" onclick="uploadAj(document.getElementById('file-input-footer'),'footer');return false;">Carica il file</a>
																		</div>
																	</div>
																</div>
																<div class="form-group">
																	<label for="titolo-input" class="control-label">O sceglilo dal menu a tendina</label>
   																	<select name="footerFileName" id="select-file-footer" class="form-control">
        	          													<option value="">Nessun footer...</option>
            	          												<c:forEach items="${listaFooter}" var="footer">
                	           												<option value="${footer}" ${template.footer==footer?"selected":"" }>${footer}</option>
                    	 												</c:forEach>
                  													</select>
																</div>
															</div>
														</div>
													</div>
												</div>
											</div>
				   						</div>
						        	</div>
						        	<input name="template" id="templateHiddenField" type="hidden" />
						        	<input name="subheader" id="subheaderHiddenField" type="hidden" />  	
							        <div class="col-xs-12 margin-bottom">							        	
										
										<button class="btn btn-default pull-right" type="submit">
											<i class="fa fa-save"></i> Salva Template
										</button>
										
										<button name="action" value="generaDocumentoProva" class="btn btn-default pull-right" style="margin-right:5px" >
											<i class="fa fa-download"></i> 
							        		Download prova
										</button>
							     
							       	</div>
								</div>
							</div>
						</form>           
					</div>                         
					<div id="myModalError" class="modal fade" role="dialog" aria-labelledby="myLargeModalLabel">
						<div class="modal-dialog" role="document">
							<div class="modal-content">
								<div class="modal-header">
 									<button type="button" class="close" data-dismiss="modal" aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
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
     					
     				<div id="errorMsg">
						<!-- Place at bottom of page -->
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
		<script src="plugins/ckeditor/ckeditor.js"></script>
		<script src="js/template-questionario.js" type="text/javascript"></script>
	
		<script >
			$(document).ready(function() {
				
				<c:if test="${error!=null }">
					showError("${error}");
				</c:if>
				
			
			});
		</script>
	</jsp:attribute>
</t:layout>
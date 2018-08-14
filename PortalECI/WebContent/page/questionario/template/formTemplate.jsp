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
        			${template.id==null?"Crea nuovo":"Modifica"} questionario
        			<small></small>
      			</h1>
      			<c:if test="${questionario.id!=null}">  <a class="btn btn-default pull-right" href="gestioneQuestionario.do?idQuestionario=${questionario.id}"><i class="glyphicon glyphicon-left"></i> Torna al questionario</a></c:if> 
    		</section>
			<div style="clear: both;"></div>
    		
    		<!-- Main content -->
    		<section class="content">
            <form class="form" id="questionario-form" action="gestioneTemplateQuestionario.do" method="POST">
				<c:if test="${template.id!=null}">
					<input type="hidden" name="idTemplate" value="${template.id}" >
					<input type="hidden" name="_method" value="put" />
				</c:if>
				<input type="hidden" id="idQuestionario" name="idQuestionario" value="${questionario.id}" >
				<input type="hidden" id="tipo" name="tipo" value="${tipo}" >
				<div class="row">
        			<div class="col-xs-12">
          				<div class="box">
            				<div class="box-body">            
            					<div class="row">
									<div class="col-xs-12">
										<div class="box box-danger box-solid">
											<div class="box-header with-border">
	 											Template
												<div class="box-tools pull-right">		
													<button data-widget="collapse" class="btn btn-box-tool"><i class="fa fa-minus"></i></button>
												</div>
											</div>
											<div class="box-body">
												<div class="row">
													<div class="col-sm-12">
														<div class="form-group">
															<label for="titolo-input" class="control-label">Titolo</label>
															<input type="text" name="titolo" class="form-control" id="titolo-input" placeholder="Titolo" value="${template.titolo}">
														</div>
													</div>
												</div>
												<textarea id="summernote" name="template">${template.template}</textarea>
												
												
												<div class="row">
													<div class="col-sm-12">
														<div class="form-group">
															<div class="col-sm-5">
																<div class="form-group">														
																	<input type="radio" name="header" value="seleziona" checked> 
																	<label for="titolo-seleziona-header" class="control-label">Seleziona Header</label><br>
	   																<select name="selheader" id="selheader" data-placeholder="Seleziona Header..."  class="form-control select2" aria-hidden="true" data-live-search="true">
	        	          												<option value="" disabled selected>Seleziona Header...</option>
	            	          											<c:forEach items="${listaHeader}" var="head">
	                	           											<option value="${head}">${head}</option> 	
	                    	 											</c:forEach>
	                  												</select>
                  												</div>
                  												<div class="form-group">
	                  												<input type="radio" name="header" value="carica" onclick="checkHeader()">
	                  												<label for="titolo-carica-header" class="control-label">Carica Header</label><br>
	                  												<div id="ttt">
																	<input type="file" name="caricaheader" id="caricaheader">
																	</div>
																</div>
	                  										</div>
		                  									<div class="col-sm-1"></div>
															<div class="col-sm-5">
																<div class="form-group">														
																	<input type="radio" name="footer" value="seleziona" onclick="checkFooter()" checked> 
																	<label for="titolo-seleziona-footer" class="control-label">Seleziona Footer</label><br>
	   																<select name="selfooter" id="selfooter" data-placeholder="Seleziona Footer..."  class="form-control select2" aria-hidden="true" data-live-search="true">
	        	          												<option value="" disabled selected>Seleziona Footer...</option>
	            	          											<c:forEach items="${listaFooter}" var="footer">
	                	           											<option value="${footer}">${footer}</option> 	
	                    	 											</c:forEach>
	                  												</select>
                  												</div>
                  												<div class="form-group">
	                  												<input type="radio" name="footer" value="carica" onclick="checkFooter()">
	                  												<label for="titolo-carica-footer" class="control-label">Carica Footer</label><br>
																	<input type="file" name="caricafooter" id="caricafooter">
																</div>
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
        						                                        
        						<button class="btn btn-default pull-right" type="submit"><i class="fa fa-save"></i> Salva Template</button>
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
	
	<jsp:attribute name="extra_css">
		<link rel="stylesheet"  href="css/plugins/summernote/summernote.css"/>
	</jsp:attribute>
	
	<jsp:attribute name="extra_js_footer">
	
		<script src="js/template-questionario.js" type="text/javascript"></script>
	
		<script src="plugins/summernote/summernote.js" type="text/javascript"></script>
		<script src="plugins/summernote/summernote-it-IT.js" type="text/javascript"></script>
		<script>$(document).ready(function() {
			$('#selheader').val(${template.header});
		})
		</script>
		
		<c:if test="${error!=null }">
			<script  type="text/javascript">
			showError("${error}");
			</script>
		</c:if>
		
	</jsp:attribute> 
</t:layout>
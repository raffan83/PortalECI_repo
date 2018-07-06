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
        			Dettaglio Intervento
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
	 											Dati Intervento
												<div class="box-tools pull-right">		
													<button data-widget="collapse" class="btn btn-box-tool"><i class="fa fa-minus"></i></button>
												</div>
											</div>
											<div class="box-body">	
        										<ul class="list-group list-group-unbordered">
        											<li class="list-group-item">
                  										<b>ID Commessa</b>                   										
                  										<a href="#" class="btn customTooltip customlink pull-right" title="Click per aprire il dettaglio dell'Intervento" onclick="callAction('gestioneIntervento.do?idCommessa=${intervento.getIdCommessa()}');">
															<c:out value='${intervento.getIdCommessa()}'/>
														</a>
                									</li>
                									<li class="list-group-item">
                  										<b>ID Intervento</b> 
                  										<a class="pull-right">${intervento.getId()}</a>
                									</li>
                									
                									<li class="list-group-item">
                  										<b>Data Creazione Intervento</b> 
                  										<a class="pull-right"><fmt:formatDate pattern="dd/MM/yyyy" value="${intervento.getDataCreazione()}" /></a>
                									</li>
                									<li class="list-group-item">
                  										<b>Cliente</b> 
                  										<a class="pull-right">${intervento.getId_cliente()}</a>
                									</li>
                									<li class="list-group-item">
                  										<b>Sede</b> 
                  										<a class="pull-right">${intervento.getNome_sede()}</a>
                									</li>
                									<li class="list-group-item">
                  										<b>Stato</b> 
                  										<!-- <a class="pull-right">${intervento.getStatoIntervento().getDescrizione()}</a>-->
                  										<a class="pull-right">
				 											<c:choose>
  																<c:when test="${intervento.getStatoIntervento().getDescrizione().equals('CHIUSO')}">
    																<span class="label label-danger">CHIUSO</span>
  																</c:when>
  																<c:when test="${intervento.getStatoIntervento().getDescrizione().equals('CREATO')}">
    																<span class="label label-success">CREATO</span>
  																</c:when>
  																<c:otherwise>
    																<span class="label label-info">${intervento.getStatoIntervento().getDescrizione()}</span>
	 															</c:otherwise>
															</c:choose>  
														</a>
                									</li>
                									<li class="list-group-item">
                  										<b>Tecnico Verificatore</b> 
                  										<a class="pull-right">${intervento.getTecnico_verificatore().getNominativo()}</a>
                									</li>
                									<li class="list-group-item">
                  										<b>Categoria Verifica</b>
                  										<a class="pull-right">
                  											<c:forEach items="${intervento.getTipo_verifica()}" var="tipo_verifica" varStatus="loop">                											
		 														${tipo_verifica.getCategoria().getCodice() } &nbsp
		 													</c:forEach>		 														
                  										</a>
                									</li>
                									<li class="list-group-item">
                  										<b>Tipo Verifica</b>                   										
                  										<a class="pull-right">
                  											<c:forEach items="${intervento.getTipo_verifica()}" var="tipo_verifica" varStatus="loop">
		 														${tipo_verifica.getCodice() } &nbsp
		 													</c:forEach>		 														
                  										</a>
                  										
                									</li>                									                									  
                									
        										</ul>

											</div>
										</div>
									</div>
								</div>
             					<div class="row">
        							<div class="col-xs-12">
										<div class="box box-danger box-solid">
											<div class="box-header with-border">
	 											Lista Verifiche
												<div class="box-tools pull-right">		
													<button data-widget="collapse" class="btn btn-box-tool"><i class="fa fa-minus"></i></button>
												</div>
											</div>
											<div class="box-body">
		      									<table id="tabPM" class="table table-bordered table-hover dataTable table-striped" role="grid" width="100%">
 													<thead>
 														<tr class="active">
 															<th>ID</th> 															
 															<th>Codice Categoria</th>
 															<th>Codice Verifica</th>
 															<th>Stato</th>
 															<td></td>
														</tr>
													</thead>
 													<tbody>
 														
 													</tbody>
 												</table>  
											</div>
										</div>
            							<!-- /.box-body -->
          							</div>
          							<!-- /.box -->
        						</div>
        						<!-- /.col -->               
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
  		</script>	  
	</jsp:attribute> 
</t:layout>
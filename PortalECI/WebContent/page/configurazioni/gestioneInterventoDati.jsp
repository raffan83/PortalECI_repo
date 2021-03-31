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
                  										<a href="#" class="customTooltip customlink pull-right" title="Click per aprire il dettaglio della Commessa" onclick="callAction('gestioneIntervento.do?idCommessa=${intervento.getIdCommessa()}');">
															${intervento.getIdCommessa()}
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
                  										<a class="pull-right">${nome_cliente}</a>
                									</li>
                									<li class="list-group-item">
                  										<b>Sede</b> 
                  										<a class="pull-right">${intervento.getNome_sede()}</a>
                									</li>
                									<li class="list-group-item">
                  										<b>Stato</b> 
                  										<!-- <a class="pull-right">${intervento.getStatoIntervento().getDescrizione()}</a>-->
                  										<a class="pull-right">				 											
    														<span class="label" style="color:#000000 !important; background-color:${intervento.statoIntervento.getColore(intervento.statoIntervento.id)} !important;">${intervento.getStatoIntervento().getDescrizione()}</span>
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
        										<div class="row">    
        											<c:if test='${intervento.getStatoIntervento().getDescrizione().equals("CREATO") && user.checkPermesso("UPD_INTERVENTO")}'>    											
        												<button class="btn btn-default pull-right" onClick="$('#modalModificaIntervento').modal('show');" style="margin-right:10px">
        													<i class="glyphicon glyphicon-edit"></i>
        												 	Modifica
        												</button>  
        											</c:if>
        											<c:if test="${user.checkPermesso('CH_STA_INTERVENTO')}">
        												<button class="btn btn-default pull-right" onClick="$('#modalCambioStato').modal('show');" style="margin-right:10px">
        													<i class="glyphicon glyphicon-transfer"></i>
        												 	Cambio Stato
        												</button>
        											</c:if>        											      											
        										</div>

											</div>
										</div>
									</div>
								</div>
								
								
								
								<div class="row">
        							<div class="col-xs-12">
										<div class="box box-danger box-solid">
											<div class="box-header with-border">
	 											Lista Attivit&agrave;
												<div class="box-tools pull-right">		
													<button data-widget="collapse" class="btn btn-box-tool"><i class="fa fa-plus"></i></button>
												</div>
											</div>
											<div class="box-body">
              									<table id="tabAttivita" class="table table-bordered table-hover dataTable table-striped" role="grid" width="100%">
 													<thead>
 														<tr class="active"> 
 															<th>Descrizione Attivita</th>
 															<th>Note</th>
 															<th>Descrizione Articolo</th>
 															<th>Ore uomo</th>
 															<th>Quantit&agrave;</th>
 														</tr>
 													</thead>
 													<tbody>
 														<c:forEach items="${commessa.listaAttivita}" var="attivita"> 
 															<tr role="row">
																<td>
  																	${attivita.descrizioneAttivita}
																</td>
																<td>
  																	${attivita.noteAttivita}
																</td>	
																<td>
  																	${attivita.descrizioneArticolo}
																</td>	
																<td>
																	${attivita.oreUomo }
																</td>
																<td>
  																	${attivita.quantita}
																</td>
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
										<div class="box box-danger box-solid">
											<div class="box-header with-border">
	 											Lista Verbali
												<div class="box-tools pull-right">		
													<button data-widget="collapse" class="btn btn-box-tool"><i class="fa fa-minus"></i></button>
												</div>
											</div>
											<div class="box-body">
											
											<a class="btn btn-primary" onClick="modalAggiungiVerbale()"><i class="fa fa-plus"></i> Aggiungi verbale</a>
											
											<a class="btn btn-primary pull-right" onClick="getDestinatarioEmail('${intervento.id}')" style="margin-left:5px"><i class="fa fa-paper-plane-o" ></i> Invia Verbale</a>
											
		      									<table id="tabPM" class="table table-bordered table-hover dataTable table-striped" role="grid" width="100%">
 													<thead>
 														<tr class="active">
 														<td><input type="checkbox" id="check_all" class="form-control"></td>
 															<th>Codice Categoria</th> 															
 															<th>Codice Verifica</th>
 															<th>Data Creazione</th>
 															<th>Sede Utilizzatore</th>
 															<th>Stato</th>
 															<th>Numero Certificato</th>
 															<th>Certificato</th>
 															
 															<th width="150px">Sc. Tecnica</th>
 															<th>Attrezzatura</th>
 															<th>Storico invio</th>
 															<th>Note</th>
 															<td></td>
														</tr>
													</thead>
 													<tbody>
 														<c:forEach items="${intervento.verbali}" var="verbale"> 
 															<tr role="row">
 															<td>
 															<c:if test="${verbale.firmato == 1 && (verbale.codiceCategoria == 'VAL' || verbale.controfirmato == 1)  }">
 															<input type="checkbox" id="check_${verbale.id }" class="form-control" >
 															</c:if>
 															
 															</td>
																<td>
  																	${verbale.getCodiceCategoria()}
																</td>
																<td>
  																	${verbale.getCodiceVerifica()}
																</td>	
																<td>
  																	${verbale.getCreateDate()}
																</td>	
																<td>
  																	${verbale.getSedeUtilizzatore()}
																</td>
																<td>
																	<span class="label" style="color:#000000 !important; background-color:${verbale.getStato().getColore(verbale.getStato().getId())} !important;">${verbale.getStato().getDescrizione()}</span>  																	
																</td>
																<td>
																	${verbale.numeroVerbale }
																</td>
																
																<td>
  																	<c:if test="${verbale.getDocumentiVerbale().size()>0 && user.checkPermesso('DOWNLOAD_CERTIFICATO')}">
      																	<c:forEach items="${verbale.getDocumentiVerbale()}" var="docum">	
      																		<c:if test="${docum.getType().equals('CERTIFICATO')}">
    	  																		<a class="btn customTooltip" title="Click per aprire il certificato" onclick="scaricaFile(${docum.id}, ${docum.verbale.id });">
	      																			<i class="glyphicon glyphicon-file"></i>
            																	</a>
	      																	</c:if>      																		
      																	</c:forEach>
      																</c:if>    
																</td>
																<td>
  																	<c:if test="${verbale.getSchedaTecnica().getDocumentiVerbale().size()>0 && user.checkPermesso('DOWNLOAD_SKTECNICA')}">
      																	<c:forEach items="${verbale.getSchedaTecnica().getDocumentiVerbale()}" var="docum">	      																		
      																		<c:if test="${docum.getType().equals('SCHEDA_TECNICA')}">
	      																		<a class="btn customTooltip" title="Click per aprire la scheda tecnica" onclick="scaricaFile(${docum.id}, ${docum.verbale.id });">
	      																			<i class="glyphicon glyphicon-file"></i>
            																	</a>
    	  																	</c:if>
      																	</c:forEach>
      																</c:if> 
																</td>
																<td>${verbale.attrezzatura.matricola_inail }</td>
																<td>
																																	
																<c:if test="${verbale.inviato == 1 }">
																	<a class="btn btn-primary customTooltip" title="Vedi storico invio" onClick="modalStorico(${verbale.id})"><i class="fa fa-envelope"></i></a>
																	</c:if>
																</td>
																<td>
  																	${verbale.getNote()}
																</td>	
																<td>
																<c:if test="${verbale.getStato().getId()==1 }">
																
																<a class="btn customTooltip" title="Elimina verbale" onclick="modalYesOrNo('${verbale.id}');">
                															<i class="fa fa-times"></i>
            															</a>
																</c:if>
																	<c:if test="${user.checkPermesso('GESTIONE_VERBALI')}">
																		<a class="btn customTooltip" title="Click per aprire il dettaglio del Verbale" onclick="callAction('gestioneVerbale.do?idVerbale=${verbale.id}');">
                															<i class="fa fa-arrow-right"></i>
            															</a>
            														</c:if>
        														</td>															
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
        						<!-- /.col -->               
 							</div>
						</div>
  						
<!--  -->
  						<%-- <div id="modalModificaIntervento" class="modal fade" role="dialog" aria-labelledby="myLargeModalLabel">
   							<div class="modal-dialog modal-lg" role="document">
    							<div class="modal-content">
     								<div class="modal-header">
        								<button type="button" class="close" data-dismiss="modal" aria-label="Close">
        									<span aria-hidden="true">&times;</span>
        								</button>
        								<h4 class="modal-title" id="myModalLabel">Modifica Intervento</h4>
      								</div>
      									
       								<div class="modal-body" >
	       								<form class="form-horizontal"  id="formModificaIntervento">
  											<div class="col-sm-12 ">	
    											<div class="form-group">
        											<label>Tecnico Verificatore</label>
													<select class="form-control" id="tecnici" class="selectpicker">
														<option value="" disabled selected>Seleziona Tecnico...</option>
														<c:forEach items="${tecnici}" var="tecnico">
					  										<option value="${tecnico.id}" <c:if test='${tecnico.id == intervento.getTecnico_verificatore().getId()}'> selected</c:if>>${tecnico.nominativo} </option>
														</c:forEach>
													</select>
            	    							</div>
            	    						</div>
            	    						<div class="col-sm-12 ">
              									<div class="row" style="position:relative;">
            										<div class="form-group col-sm-5">
	                  									<label>Categoria Verifica</label>
    	              									<select name="selectCat" id="selectCat" data-placeholder="Seleziona Categoria..."  class="form-control select2" aria-hidden="true" data-live-search="true">
	        	          									<option value="" disabled selected>Seleziona Categoria...</option>
    	        	          								<c:forEach items="${categorie_verifica}" var="categoria">    	        	          								
        	        	           								<option value="${categoria.id}">${categoria.codice}</option> 	
            	        	 								</c:forEach>
                	  									</select> 
        											</div>
		        
								    	        	<div class="form-group col-sm-offset-1 col-sm-5">
                  										<label>Tipo Verifica</label>
                  										<select name="selectTipo" id="selectTipo" data-placeholder="Seleziona Tipo"  disabled class="form-control select2" aria-hidden="true" data-live-search="true">
                											<option value="" disabled selected>Seleziona Tipo...</option>
                											<c:forEach items="${tipi_verifica}" var="tipo">                		
		                        								<option value="${tipo.id}_${tipo.categoria.id}">${tipo.codice}</option>     	                            
    		                 								</c:forEach>
        		         								</select>                  
        											</div>    
        											<div class="form-group col-sm-1 text-center" style="position: absolute;	bottom: 0; right: 0;">        									
                  									<button type="button" id="addrow" class="btn-sm" onclick="addRow()"><i class="fa fa-plus"></i></button>              
        											</div>                                     
  												</div>		                                     
  											</div>		
  														
					  						<div class="row">
  												<div class="col-sm-12 ">		
  													 <table id="tabVerifica" class="table table-bordered table-hover dataTable table-striped" role="grid" width="100%">
 														<thead>
 															<tr class="active">
 																<th>Categoria Verifica</th>
 																<th>Tipo Verifica</th> 		
 																<th>Scheda Tecnica da compilare</th>												
 																<td></td>
															</tr>
														</thead>
 														<tbody id="bodytabVerifica">
 																		
 															<c:forEach items="${listIdCodiciCategoria}" var="codCateg" varStatus="loop">
 																<tr class="categoriaTipiRow" id="${listIdCodiciTipo[loop.index]}_${codCateg}" role="row" >
																	<td >${listCodiciCategoria[loop.index]}</td>
																	<td >${listCodiciTipo[loop.index]}</td>
																	<td >
																		<c:if test="${listEsisteTemplateScTec[loop.index]}">
																			<input type="checkbox" class="skTecObb" ${listEsisteScTec[loop.index]?'checked':''}/>
																		</c:if>
																		<c:if test="${!listEsisteTemplateScTec[loop.index]}">
																			<i class="fa fa-ban" title="Template Scheda Tecnica non esiste per questo Tipo Verifica"></i>
																		</c:if>
																	</td>
																	<td>
																		<a class="btn customTooltip" title="Click per eliminare la riga" onclick="removeRow('${listIdCodiciTipo[loop.index]}_${codCateg}')">
																			<i class="fa fa-minus"></i>
																		</a>
																	</td>
																</tr>
															</c:forEach>												
					 									</tbody>
 													</table>  
 													
 													
 													
  												</div>
  											</div>
       
		 													              										
            								<div id="empty" class="label label-danger testo12"></div>
  		 									
      										<div class="modal-footer">
												<span id="ulError" class="pull-left"></span>
												<c:if test="${user.checkPermesso('UPD_INTERVENTO')}">
													<button type="submit" class="btn btn-danger" >Salva</button>
												</c:if>
      										</div>
        								</form>
    								</div>
  								</div>
							</div>
						</div> --%>
						
						
						
						<div id="myModal" class="modal fade" role="dialog" aria-labelledby="myLargeModalLabel">
    						<div class="modal-dialog modal-lg" role="document">
    							<div class="modal-content">
     								<div class="modal-header">
        								<button type="button" class="close" data-dismiss="modal" aria-label="Close">
        									<span aria-hidden="true">&times;</span>
        								</button>
        								<h4 class="modal-title" id="myModalLabel">Nuovo Intervento</h4>
      								</div>
       								<div class="modal-body">             
        								<div class="form-group">
        									<label>Categoria Verifica</label>
	                  							<%-- <select name="categorie" id="categorie" data-placeholder="Seleziona Categoria..."  class="form-control select2" aria-hidden="true" data-live-search="true" style="display:none">
        	          								<option value="" disabled selected>Seleziona Categoria...</option>
            	          							<c:forEach items="${categorie_verifica}" var="categoria">            	          							         	          							
                	           							<option value="${categoria.id}_${categoria.sigla}">${categoria.codice}</option> 	
                    	 							</c:forEach>
                  								</select>  --%>
    	              							<select name="select1" id="select1" data-placeholder="Seleziona Categoria..."  class="form-control select2" aria-hidden="true" data-live-search="true">
        	          								<option value="" disabled selected>Seleziona Categoria...</option>
            	          							<c:forEach items="${categorie_verifica}" var="categoria">
                	           							<option value="${categoria.id}">${categoria.codice}</option> 	
                    	 							</c:forEach> 
                  								</select> 
                						</div>
              							<div class="row" style="position:relative;">
            								<div class="form-group col-sm-5">
            								
            								<label>Tecnico Verificatore</label>
											<select class="form-control" id="tecnici" class="selectpicker">
												
											</select>
											
										 <select name="tecnici_temp" id="tecnici_temp" data-placeholder="Seleziona Categoria..."  class="form-control select2" aria-hidden="true" data-live-search="true" style="display:none">
        	          								<option value="" disabled selected>Seleziona Tecnico...</option>
												<c:forEach items="${tecnici}" var="tecnico">
													
												<option value="${tecnico.id}">${tecnico.nominativo} </option>	
												
				  												  									   
												</c:forEach>
                  								</select>  
        									</div>
        
							    	        <div class="form-group col-sm-5">
                  								<label>Tipo Gruppo</label>
                  								<select name="select2" id="select2" data-placeholder="Seleziona Tipo"  disabled class="form-control select2" aria-hidden="true" data-live-search="true">
                									<option value="" disabled selected>Seleziona Tipo Gruppo...</option>
                									<c:forEach items="${tipi_verifica}" var="tipo">                		
	                        							<option value="${tipo.id}_${tipo.categoria.id}_${tipo.sigla}">${tipo.codice}</option>     	                            
    	                 							</c:forEach>
        	         							</select>                  
        									</div>    
        									<div id="content_sede" style="display:none">
	        									<div class="form-group col-sm-5" >
	                  								<label>Sede</label>
	                  								<select name="sede" id="sede" data-placeholder="Seleziona Sede"  class="form-control select2" aria-hidden="true" data-live-search="true" style="width:100%">
	                									<option value="" disabled selected>Seleziona Sede...</option>
	                									<option value="0">Default</option>
	                									<c:forEach items="${lista_sedi_cliente}" var="sd">                		
		                        							<option value="${sd.indirizzo} - ${sd.cap } - ${sd.comune } (${sd.siglaProvincia })_${sd.esercente }_${sd.descrizione}">${sd.indirizzo} - ${sd.comune } (${sd.siglaProvincia })</option>     	                            
	    	                 							</c:forEach>
	        	         							</select>
	        	         							<br><br>
	        	         							<label>Utilizzatore da descrizione</label>
	        	         							<input id="check_descrizione" name = "check_descrizione" class="form-control" type="checkbox"  >                  
	        									</div>  
	        									    
	        										
	        									
        									</div>
        									<div id="content_esercente" style="display:none">
	        									<div class="form-group col-sm-5" >
	                  								<label>Esercente</label>
	                  								<input id="esercente" name = "esercente" class="form-control" type="text" value="" >                 
	        									</div>  
	        									
	        									<div id="content_descrizione_utilizzatore" style="display:none">
	        									<div class="form-group col-sm-5" >
	                  								<label>Descrizione Sede Utilizzatore</label>
	                  								<input id="descrizione_sede_util" name = "descrizione_sede_util" class="form-control" type="text"  >                 
	        									</div>  
	        									</div>
        									</div>
        									<div id="content_attrezzatura" style="display:none" >
	        									<div class="form-group col-sm-5">
	                  								<label>Attrezzatura</label>
	                  								 <select name="attrezzatura" id="attrezzatura" data-placeholder="Seleziona Attrezzatura"  class="form-control select2" aria-hidden="true" data-live-search="true" style="width:100%">
	                									     
	        	         							</select>          
	        	         							<select name="attrezzatura_temp" id="attrezzatura_temp" data-placeholder="Seleziona Attrezzatura"  class="form-control select2" aria-hidden="true" data-live-search="true" style="display:none">
	                									<option value="" disabled selected>Seleziona Tipo...</option>
	                									<option value="0">Nessuna</option>
	                									<c:forEach items="${listaAttrezzature}" var="attrezzatura">                		
		                        							<option value="${attrezzatura.id}_${attrezzatura.matricola_inail}_${attrezzatura.tipo_attivita}">${attrezzatura.matricola_inail}</option>     	                            
	    	                 							</c:forEach>
	        	         							</select>    
	        									</div>  
        									</div>
        									
        									<div style="display:none" id="content_val">
        								<div class="form-group col-sm-5">
	                  								<label>Effettuazione verifica</label>
	                  								 <select name="effettuazione_verifica" id="effettuazione_verifica" data-placeholder="Seleziona Effettuazione Verifica..."  class="form-control select2" aria-hidden="true" data-live-search="true">
	                								 <option value=""></option> 
	                								<option value="1">Affidamento da parte del titolare della funzione</option>
	                								<option value="2">Affidamento diretto da parte del datore di lavoro</option>
	        	         							</select>          

	        									</div>  
	        										<div class="form-group col-sm-5">
	                  								<label>Tipo Verifica</label>
	                  								
	                  								 <select name="tipo_verifica" id="tipo_verifica" data-placeholder="Seleziona Effettuazione Verifica..."  class="form-control select2" aria-hidden="true" data-live-search="true">

	        	         							</select>          

	        									</div>  
	        									
	        							</div>		
        									<div class="form-group col-sm-2 text-center" style="position: absolute;	bottom: 0; right: 0;">        									
                  								<button class="btn-sm" id="addrow" onclick="addRow()"><i class="fa fa-plus"></i></button>              
        									</div> 
        									</div>		
        									
        								        									 									
  											<div class="form-group">
        									<label>Note</label>
											<input type="text" class="form-control" id="noteVerbale" >
												
                						</div>                                    			
  										<div class="row">
  											<div class="col-sm-12">		
  											<table id="tabVerifica" class="table table-bordered table-hover dataTable table-striped" role="grid" width="100%">
 													<thead>
 														<tr class="active">
 															<th>Categoria Verifica</th>
 															<th>Tipo Gruppo</th>
 															<th>ST obbligatoria</th>
 															<th>Attrezzatura</th>
 															<th style="display:none"></th>
 															<th>Sede</th>
 															<th>Esercente</th> 	
 															<th>Descrizione Utilizzatore</th>														
 															<th>Effettuazione verifica</th>
 															<th>Tipo verifica</th>
 															<th>Note</th>		 														
 															<td></td>
														</tr>
													</thead>
 													<tbody id="bodytabVerifica"> 			
 													
												
 													</tbody>
 												</table>  
  											</div>
  										</div>
  										<div id="empty" class=" label label-danger testo12"></div>
  										<input type="hidden" id="str_attrezzature" name="str_attrezzature" >
  		 							</div>
      								<div class="modal-footer">
						        		<button type="button" class="btn btn-danger"onclick="aggiungiVerbale(${intervento.id})"  >Salva</button>
      								</div>
			    				</div>
  							</div>
						</div>
						
<!--  -->							

						<div id="modalCambioStato" class="modal fade" role="dialog" aria-labelledby="modalCambioStato">
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
											<label class="col-sm-12" style="text-align:center;">Attenzione, sicuro di voler modificare lo stato di questo intervento? </label>
                  							
                  							<div class="col-sm-12" style="margin:5px ; text-align:center;">
                  								<button type="button  pull-left" class="btn-sm " onclick="salvaCambioStato('ANNULLATO')" style="color:#000000 !important; background-color:${intervento.statoIntervento.getColore(6)} !important;">
                  									<i class="glyphicon glyphicon-remove"></i>
                  									ANNULLATO
                  								</button>
										
												<button type="button  pull-right" class="btn-sm" onclick="salvaCambioStato('CHIUSO')" style="color:#000000 !important; background-color:${intervento.statoIntervento.getColore(7)} !important;">
													<i class="glyphicon glyphicon-remove"></i>
													CHIUSO
												</button>
												
											<%-- 	<c:if test='${intervento.getStatoIntervento().getDescrizione().equals("CREATO")}'>
													<button type="button  pull-right" class="btn-sm" onclick="salvaCambioStato('COMPILAZIONE_WEB')" style="color:#000000 !important; background-color:${intervento.statoIntervento.getColore(8)} !important;">
														<i class="glyphicon glyphicon-remove"></i>
														COMPILAZIONE WEB
													</button>
												</c:if> --%>
																								      										
											</div>											
										</div>
    								</div>
    								<div class="modal-footer">
										<button onclick=" $('#modalCambioStato').modal('hide');" class="btn btn-danger" >Esci</button>
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
  										<div id="empty2" class="label label-danger testo12"></div>
  		 							</div>
      								<div class="modal-footer">
        								<button type="button" class="btn btn-outline" data-dismiss="modal">Chiudi</button>
      								</div>
    							</div>
  							</div>
						</div> 
						
						
						<div id="modalDestinatario" class="modal fade" role="dialog" aria-labelledby="myLargeModalLabel">
    						<div class="modal-dialog" role="document">
    							<div class="modal-content">
     								<div class="modal-header">
        								<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        								<h4 class="modal-title text-center" id="myModalLabel">Invia verbale</h4>
      								</div>
       								<div class="modal-body">
       								<div class="row">
       								<div class="col-xs-2">
       								<label>Destinatario:</label>
										
       								</div>
       								<div class="col-xs-6">
       								<input type="text" id="destinatario" name = "destinatario" class="form-control">
       								<input type="hidden" id="id_documento" name = "id_documento" class="form-control">
       								
       								</div>
										</div>
  		 							</div>
      								<div class="modal-footer">
      									<button type="button" class="btn btn-default" onclick="inviaPec()" >Invia</button>
        								<button type="button" class="btn btn-default" data-dismiss="modal">Chiudi</button>
      								</div>
    							</div>
  							</div>
						</div> 
						
		 <div id="myModalStorico" class=" modal fade" role="dialog" aria-labelledby="myLargeModalLabel">
    <div class="modal-dialog" role="document">
    <div class="modal-content">
     <div class="modal-header">
        <a type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></a>
        <h4 class="modal-title" id="myModalLabelHeader">Storico email</h4>
      </div>
       <div class="modal-body">
       <div class="row">
       <div class="col-sm-12">
			
       <div id="tab_storico">
       
       <table id="table_storico" class="table table-bordered table-hover dataTable table-striped" role="grid" width="100%">
 													<thead>
 														<tr class="active">
 															<th>Data</th>
 															<th>Destinatario</th>
 															<th>Utente</th>
 															
														</tr>
													</thead> 													
 												</table>  
       
       </div>
        </div>

     		</div>

		</div>
      <div class="modal-footer">
 		
 		<a class="btn btn-default pull-right" onClick="$('#myModalStorico').modal('hide')">Chiudi</a>
         
         
         </div>
     
    </div>
  </div>
</div>
						
						
 <div id="myModalYesOrNo" class="modal fade" role="dialog" aria-labelledby="myLargeModalsaveStato">
   
    <div class="modal-dialog modal-md" role="document">
    <div class="modal-content">
     <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Attenzione</h4>
      </div>
       <div class="modal-body">       
      	Sei sicuro di voler eliminare il verbale?
      	</div>
      <div class="modal-footer">
      <input type="hidden" id="elimina_verbale_id">
      <a class="btn btn-primary" onclick="eliminaVerbale($('#elimina_verbale_id').val())" >SI</a>
		<a class="btn btn-primary" onclick="$('#myModalYesOrNo').modal('hide')" >NO</a>
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
	<script type="text/javascript" src="plugins/datejs/date.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.1/moment-with-locales.min.js"></script>
 		<script type="text/javascript">
 		
 		
 		function modalYesOrNo(id_verbale){
 		
 			$('#elimina_verbale_id').val(id_verbale);
 			$('#myModalYesOrNo').modal();
 		}
 		
 		function formatDate(data){
 			
 		   var mydate = new Date(data);
 		   
 		   if(!isNaN(mydate.getTime())){
 		   
 			   str = mydate.toString("dd/MM/yyyy");
 		   }			   
 		   return str;	 		
 	}
 		
 		
 		function modalStorico(id_verbale){
 			
 			exploreModal("gestioneVerbale.do?action=storico_email&idVerbale="+id_verbale, null, null, function(datab){
 				
 				
 				
				if(datab.success){
					var lista_email = datab.lista_email;
					var table_data = [];
					for(var i = 0; i<lista_email.length;i++){
		    			  var dati = {};
		    			 	
		    			  //dati.data = formatDate(moment(lista_email[i].data, "DD, MMM YY"));
		    			  dati.data = lista_email[i].data;
		    			  dati.destinatario = lista_email[i].destinatario;
		    			  dati.utente =  lista_email[i].utente.nominativo;
		    			
		    			  table_data.push(dati);
		    			  
		    		  }
		    		  var table = $('#table_storico').DataTable();
		    		  
		     		   table.clear().draw();
		     		   
		     			table.rows.add(table_data).draw();
		     			table.columns.adjust().draw();
					
				} 			

 				$('#myModalStorico').modal();
 			});
 		}
 		
 		function modalAggiungiVerbale(){
 			
 			//$('#modalModificaIntervento').modal();
 			$('#myModal').modal();
 			
 		}
 		var id_row_intervento = 0;
 		
 		var str_attrezzature = "";
			
			
			var lang = {
			emptyTable : 	"Nessun dato presente nella tabella",
			info	:"Vista da _START_ a _END_ di _TOTAL_ elementi",
			infoEmpty:	"Vista da 0 a 0 di 0 elementi",
			infoFiltered:	"(filtrati da _MAX_ elementi totali)",
			infoPostFix:	"",
			infoThousands:	".",
			lengthMenu:	"Visualizza _MENU_ elementi",
			loadingRecords:	"Caricamento...",
			processing:	"Elaborazione...",
			search:	"Cerca:",
			zeroRecords	:"La ricerca non ha portato alcun risultato.",
			   
			paginate:	{
					first:	"Inizio",
					previous:	"Precedente",
					next:	"Successivo",
					last:	"Fine",
			},
			 aria:	{
					srtAscending:	": attiva per ordinare la colonna in ordine crescente",
					sortDescending:	": attiva per ordinare la colonna in ordine decrescente",
			} 
			}
 		
			
			
			
			function checkVerbale(id_verbale){
				
				
			}
			
			$('input:checkbox').on('ifToggled', function() {
				
				//var id =$(this)[0].id;
				
				var id ="#"+$(this)[0].id;
				
				if(id!='#check_all' ){
					$(id).on('ifChecked', function(event){
						//send_verbali = send_verbali.replace(id.split("_")[1]+";", "");
						send_verbali = send_verbali+id.split("_")[1]+";"
					});


					$(id).on('ifUnchecked', function(event) {
						
					//	send_verbali = send_verbali+id.split("_")[1]+";"
						send_verbali = send_verbali.replace(id.split("_")[1]+";", "");
						
					});
				}
				

				});
			
			
			
		    $("#check_all").on('ifChecked', function(event){
		    	 
		    	$('input:checkbox').each(function(){
		    		
		    		var id =$(this)[0].id;
		    		console.log($(this)[0].id);
		    		if(id!=''&& id!='check_all'){
		    			console.log(id);
		    			id="#"+id;
		    			if($(id).prop('checked')== false){
		    				
		    			$(id).iCheck('check');        			    			
		    			send_verbali = send_verbali+id.split("_")[1]+";"
		    		
		    			}

		    		}
		    	
		    	
		    		
		    	})
		    });


		    $("#check_all").on('ifUnchecked', function(event){
		     
		    	$('input:checkbox').each(function(){
		    		
		    		var id =$(this)[0].id;
		    		
		    		if(id!=''&& id!='check_all'){
		    				
		    			id="#"+id;
		    			if($(id).prop('checked')== true){
		    			$(id).iCheck('uncheck');        			    			
		     		
		    			send_verbali = send_verbali.replace(id.split("_")[1]+";", "");
		    			}

		    		}
		    	
		    	
		    		
		    	})
		    });

			
			
 		var attrezzatura_options;
			var tecnici_options;
			
			var send_verbali;
 		
			$(document).ready(function() {
				
				
				send_verbali = "";
				
				$('#formModificaIntervento').on('submit',function(e){		
					$("#ulError").html("");
	    			e.preventDefault();
	    			updateIntervento();
				}); 
				
				
				attrezzatura_options = $('#attrezzatura_temp option').clone();
		    	 categoria_options = $('#categorie option').clone();
		    	
		    	 
		    	 
			    	var commessa = "${commessa.ID_COMMESSA}";
			    	
			    	if(commessa.split("_")[1]=='VAL'){
			    		$("#select1").val("10");
			    		$("#select1").change();
			    		$('#select1').attr("disabled", true);
			    		$('#content_val').show();
			    	}else if(commessa.split("_")[1]=='VIE'){
			    		$("#select1").val("1");
			    		$("#select1").change();
			    		$('#select1').attr("disabled", true);
			    	}
			    	
			    $('#tecnici').val(${intervento.tecnico_verificatore.id})	
		    	$('#tecnici').attr('disabled', true);
			    
			    
			    
			    
			    
			    table_storico = $('#table_storico').DataTable({
					language: lang,
			        pageLength: 25,
			        "order": [[ 0, "desc" ]],
				      paging: false, 
				      ordering: true,
				      info: false, 
				      searchable: false, 
				      targets: 0,
				      responsive: true,  
				      scrollX: false,
				      stateSave: true,	
				      columns : [
				      	{"data" : "data"},
				      	{"data" : "destinatario"},
				      	{"data" : "utente"},
				       ],	
				           
				      columnDefs: [
				    	  
				    	  { responsivePriority: 1, targets: 1 },
		    	  
				               ], 	        
			  	      buttons: [   
			  	          {
			  	            extend: 'colvis',
			  	            text: 'Nascondi Colonne'  	                   
			 			  } ]
				               
				    });
				

												
    		});	
			
/* 			function addRow(){
				var categorie_verifica=$('#select1').val();
				var tipi_verifica=$('#selectTipo').val();									
				
				if(categorie_verifica!=null && tipi_verifica!=null){
					//if($("#" +tipi_verifica).length == 0) {						 										
					
						var id_tipo=tipi_verifica.substring(0, tipi_verifica.indexOf("_"));
							
						$.ajax({
							type: "GET",
							url: "gestioneTipiVerifica.do?action=checkSchedaTecnica&idVerifica="+id_tipo,
							dataType: "json",
							beforeSend: function(xhr){
								$("#addrow").html('<i class="fa fa-spinner fa-spin" style="font-size:24px"></i>');
								$("#addrow").prop('disabled', true);
							},
							success: function(  dataResp, textStatus) {
								var objectdata='<tr class="categoriaTipiRow" id="'+tipi_verifica+'" role="row" >'+
									'<td >'+$('#selectCat').find('[value='+categorie_verifica+']').text()+'</td>'+
									'<td >'+$('#selectTipo').find('[value='+tipi_verifica+']').text()+'</td>'+	
									'<td >';
								if(!dataResp.schedaTecnicaPresente){
									objectdata+='<i class="fa fa-ban" title="Template Scheda Tecnica non esiste per questo Tipo Verifica"></i>';
								}else{
									objectdata+='<input type="checkbox" class="skTecObb" />';
								}
									
								objectdata+='</td>'+
									'<td><a class="btn customTooltip" title="Click per eliminare la riga" onclick="removeRow(\''+tipi_verifica+'\')"><i class="fa fa-minus"></i></a></td></tr>';
									
								$("#bodytabVerifica").append(objectdata);
								
								$('.skTecObb').iCheck({
						      		checkboxClass: 'icheckbox_square-blue',
						      		radioClass: 'iradio_square-blue',
						      		increaseArea: '20%' // optional
						    	});
							},
							error: function( data, textStatus) {
	
							},
							complete: function(data, textStatus){
								$("#addrow").html('<i class="fa fa-plus"></i>');
								$("#addrow").prop('disabled', false);
							}
						});
					
				}else{						
					$('#empty').html("Scegli la categoria di verifica e il tipo verifica per procedere!");
				}
				$('#pleaseWaitDialog').modal('hide');
			} */
			
			
			
			
			
			
			
			
			function addRow(){
				var categorie_verifica=$('#select1').val();
				var tipi_verifica=$('#select2').val();		
				var attrezzatura = "";
				var eff_ver = $('#effettuazione_verifica').val();
				var tipo_ver =$('#tipo_verifica').val();
				
				var descrizione_util = $('#descrizione_sede_util').val();
				
				var is_vie = false;
				if(categorie_verifica == "10" && $('#attrezzatura').val()!='0'){
					attrezzatura = $('#attrezzatura').val();	
				}
				
				if(categorie_verifica == "1"){
					is_vie = true;
				}					
				
				if(categorie_verifica!=null && tipi_verifica!=null && !is_vie && eff_ver!=null && eff_ver!=''&& tipo_ver!=null && tipo_ver!=''){
					//if($("#" +tipi_verifica).length == 0) {						 										
						
						var id_tipo=tipi_verifica.substring(0, tipi_verifica.indexOf("_"));
						
						$.ajax({
							type: "GET",
							url: "gestioneTipiVerifica.do?action=checkSchedaTecnica&idVerifica="+id_tipo,
							dataType: "json",
							beforeSend: function(xhr){
								$("#addrow").html('<i class="fa fa-spinner fa-spin" style="font-size:24px"></i>');
								 $("#addrow").prop('disabled', true);
							},
							success: function(  dataResp, textStatus) {
								var objectdata='<tr class="categoriaTipiRow" id="row_'+id_row_intervento+'" name="'+tipi_verifica+'" role="row" >'+
								'<td >'+$('#select1').find('[value='+categorie_verifica+']').text()+'</td>'+
								'<td >'+$('#select2').find('[value='+tipi_verifica+']').text()+'</td>'+	
								'<td >';
								if(!dataResp.schedaTecnicaPresente){							
									objectdata+='<i class="fa fa-ban" title="Template Scheda Tecnica non esiste per questo Tipo Verifica"></i>';
								}else{
									
									if($('#tipo_verifica').val()==1|| ($('#select2').val().split("_")[2]== 'GVR' && $('#tipo_verifica').val()==2)){
										objectdata+='<input type="checkbox" class="skTecObb" checked disabled/>';
									}else{
										objectdata+='<input type="checkbox" class="skTecObb" disabled/>';	
									}
									
								}
								objectdata+='</td>';
								if(attrezzatura !=null && attrezzatura!=""){
									objectdata+='<td>'+attrezzatura.split("_")[1]+'</td>';
									objectdata+='<td style="display:none">' +attrezzatura.split("_")[0]+'</td>';
								}else{
									objectdata+='<td></td>';
									objectdata+='<td style="display:none"></td>';
									//str_attrezzature +=  "_";
								}	
								
								var sede = $('#sede').val();
								if(sede!= null && sede!= "" && sede !="0"){
									objectdata+="<td>"+	sede.split("_")[0]+"</td>";	
								}else{
									objectdata+="<td>${commessa.INDIRIZZO_UTILIZZATORE}</td>";
								}
								var esercente = $('#esercente').val();
								if(esercente !=null && esercente !=''){
									objectdata+="<td>"+esercente+"</td>";	
								}else{
									objectdata+="<td></td>";
								}
								objectdata+="<td></td>";
								
								objectdata+='<td>'+eff_ver+'</td>'+
								'<td>'+tipo_ver+'</td>';	
								objectdata+='<td>'+$("#noteVerbale").val()+'</td>'+    
									'<td><a class="btn customTooltip" title="Click per eliminare la riga" onclick="removeRow(\'row_'+id_row_intervento+'\')"><i class="fa fa-minus"></i></a></td></tr>';
								
								if($("#bodytabVerifica").find("tr").size()>0){
									$('#tabVerifica').DataTable().destroy();	
								}
								$("#bodytabVerifica").append(objectdata);	
									 
								 var table = $('#tabVerifica').DataTable({language : lang, responsive: true, ordering: false, paging: true, pageLength: 100,columnDefs: [{ responsivePriority: 1, targets: 10 }]});
								var column =  table.column(4 );
								column.visible(!column.visible());
								$('#str_attrezzature').val(str_attrezzature)
								$('.skTecObb').iCheck({
						      		checkboxClass: 'icheckbox_square-blue',
						      		radioClass: 'iradio_square-blue',
						      		increaseArea: '20%' // optional
						    	});
								
								id_row_intervento++;
							},
							error: function( data, textStatus) {

							
							},
							complete: function(data, textStatus){
								$("#addrow").html('<i class="fa fa-plus"></i>');
								$("#addrow").prop('disabled', false);
							}
						});

				}
				else if(categorie_verifica!=null && tipi_verifica!=null && is_vie){
					var id_tipo=tipi_verifica.substring(0, tipi_verifica.indexOf("_"));
					
					$.ajax({
						type: "GET",
						url: "gestioneTipiVerifica.do?action=checkSchedaTecnica&idVerifica="+id_tipo,
						dataType: "json",
						beforeSend: function(xhr){
							$("#addrow").html('<i class="fa fa-spinner fa-spin" style="font-size:24px"></i>');
							 $("#addrow").prop('disabled', true);
						},
						success: function(  dataResp, textStatus) {
							var objectdata='<tr class="categoriaTipiRow" id="row_'+id_row_intervento+'" name="'+tipi_verifica+'" role="row" >'+
							'<td >'+$('#select1').find('[value='+categorie_verifica+']').text()+'</td>'+
							'<td >'+$('#select2').find('[value='+tipi_verifica+']').text()+'</td>'+	
							'<td >';
							if(!dataResp.schedaTecnicaPresente){							
								objectdata+='<i class="fa fa-ban" title="Template Scheda Tecnica non esiste per questo Tipo Verifica"></i>';
							}else{
								
								if($('#tipo_verifica').val()==1|| ($('#select2').val().split("_")[2]== 'GVR' && $('#tipo_verifica').val()==2)){
									objectdata+='<input type="checkbox" class="skTecObb" checked/ disabled>';
								}else{
									objectdata+='<input type="checkbox" class="skTecObb" disabled/>';	
								}
								
							}
							objectdata+='</td>';
							if(attrezzatura !=null && attrezzatura!=""){
								objectdata+='<td>'+attrezzatura.split("_")[1]+'</td>';
								objectdata+='<td style="display:none">' +attrezzatura.split("_")[0]+'</td>';
							}else{
								objectdata+='<td></td>';
								objectdata+='<td style="display:none"></td>';
								//str_attrezzature +=  "_";
							}	
							
							var sede = $('#sede').val();
							if(sede!= null && sede!= "" && sede !="0"){
								objectdata+="<td>"+	sede.split("_")[0]+"</td>";	
							}else{
								objectdata+="<td>${commessa.INDIRIZZO_UTILIZZATORE}</td>";
							}
							var esercente = $('#esercente').val();
							if(esercente !=null && esercente !=''){
								objectdata+="<td>"+esercente+"</td>";	
							}else{
								objectdata+="<td></td>";
							}
						
							if(descrizione_util !=null && descrizione_util !=''){
								objectdata+="<td>"+descrizione_util+"</td>";	
							}else{
								objectdata+="<td></td>";
							}
							objectdata+='<td></td>'+
							'<td></td>';	
							objectdata+='<td>'+$("#noteVerbale").val()+'</td>'+    
								'<td><a class="btn customTooltip" title="Click per eliminare la riga" onclick="removeRow(\'row_'+id_row_intervento+'\')"><i class="fa fa-minus"></i></a></td></tr>';
							
						
							if($("#bodytabVerifica").find("tr").size()>0){
								$('#tabVerifica').DataTable().destroy();	
							}
							$("#bodytabVerifica").append(objectdata);	
								 
							 var table = $('#tabVerifica').DataTable({language : lang, responsive: true, ordering: false, paging: true, pageLength: 100,columnDefs: [{ responsivePriority: 1, targets: 10 }]});
							var column =  table.column(4 );
							column.visible(!column.visible());
							$('#str_attrezzature').val(str_attrezzature)
							$('.skTecObb').iCheck({
					      		checkboxClass: 'icheckbox_square-blue',
					      		radioClass: 'iradio_square-blue',
					      		increaseArea: '20%' // optional
					    	});
							id_row_intervento++;
						},
						error: function( data, textStatus) {

						
						},
						complete: function(data, textStatus){
							$("#addrow").html('<i class="fa fa-plus"></i>');
							$("#addrow").prop('disabled', false);
						}
					});

			
				}
				
				else{						
					$('#empty').html("Scegli la categoria di verifica, il tipo gruppo e l'effettuazione verifica per procedere!");
				}
			}
			
			
			
			
			
			
  			function removeRow(tipi_verifica){
      			//	$("#"+tipi_verifica).remove();
      				var table = $('#tabVerifica').DataTable();

      				var index = tipi_verifica.split("_")[1];
      				table.row("#"+tipi_verifica).remove().draw();
      				
      				if($("#bodytabVerifica").find("tr").size()==0){
      					$("#bodytabVerifica").html("");
      					$('#tabVerifica').DataTable().clear().destroy();
      				}/* else{
      					table.rows().draw();
      				} */
      			}

			$("#tecnici").change(function(){    	 
				$("#tecnici option[value='']").remove();
			});

			$("#selectCat").change(function() {    
				$("#selectCat option[value='']").remove();
				$("#selectTipo option[value='']").remove(); 
				if ($(this).data('options') == undefined) {
				/*Taking an array of all options-2 and kind of embedding it on the selectCat*/
					$(this).data('options', $('#selectTipo option').clone());
				}

				var id = $(this).val();	    	
				var options = $(this).data('options');	
				var opt=[];    	

			   	for(var  i=0; i<options.length;i++){
					var str=options[i].value; 	    	
						
					if(str.substring(str.indexOf("_")+1,str.length)==id){     			        		
						opt.push(options[i]);
					}   
				}
					
			   	$("#selectTipo").prop("disabled", false);    	 
				$('#selectTipo').html(opt);

				$("#selectTipo").trigger("chosen:updated");    	      	 
				$("#selectTipo").change();  
				
			});	       

			function updateIntervento(){
										
				var str1=$('#tecnici').val();

				var listCatVer='';
				$('.categoriaTipiRow').each(function(index, item){
					listCatVer+="&categoriaTipo="+item.id;
				});
				
				var skTecObb='';
				$('.skTecObb').each(function(index, item){
					if($(this)[0].checked)
						skTecObb+="&schedaTecnica="+$(this).closest('tr').prop('id');
				});
				
				   
				if(listCatVer==""){
					$('#empty').html("Devi inserire almeno un 'Tipo Verifica'!"); 
				}else if(str1!= null){					
					
					pleaseWaitDiv = $('#pleaseWaitDialog');
					pleaseWaitDiv.modal();
				    
					$.ajax({
						type: "POST",
						url: "gestioneIntervento.do?action=update",
						data : "idIntervento=${intervento.getId()}&tecnico="+str1 +listCatVer+skTecObb,				
						dataType: "json",
						success: function( data, textStatus) {
							
							
							if(data.success){ 
								$('#modalModificaIntervento').modal('hide');
								
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
				}else{
					$('#empty').html("Il campo 'Tecnico Verificatore' non pu&ograve; essere vuoto"); 
				}
				  	   
			}
			
			function salvaCambioStato(idstato){
				pleaseWaitDiv = $('#pleaseWaitDialog');
				pleaseWaitDiv.modal();
				
				$.ajax({
					type: "POST",
					url: "gestioneIntervento.do?action=cambioStato",
					data : "idIntervento=${intervento.getId()}&stato="+idstato,				
					dataType: "json",
					success: function( data, textStatus) {

						if(data.success){ 
								
							location.reload();
			          			  		          		
						}else{
							pleaseWaitDiv.modal('hide');
							
							$('#modalErrorDiv').html(data.messaggio+' '+data.dettaglio);
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
			
			function scaricaFile(idDoc, id_verbale){
				pleaseWaitDiv = $('#pleaseWaitDialog');
				pleaseWaitDiv.modal();
				$.ajax({
					type: "POST",
					url: "gestioneVerbale.do?action=visualizzaDocumento&idVerbale="+id_verbale,
					data : "idDoc="+idDoc,				
					dataType: "json",
					success: function( data, textStatus) {
						if(data.success){
							var objbuilder = '';
						    objbuilder += ('<object width="100%" height="100%"      data="data:application/pdf;base64,');
						    objbuilder += (data.pdfString);
						    objbuilder += ('" type="application/pdf" class="internal">');
						    objbuilder += ('<embed src="data:application/pdf;base64,');
						    objbuilder += (data.pdfString);
						    objbuilder += ('" type="application/pdf" />');
						    objbuilder += ('</object>');
						    var win = window.open('_blank','titlebar=yes');
					        win.document.title = 'Certificato';
					        win.document.write('<html><body>');					       
					        win.document.write(objbuilder);
					        win.document.write('</body></html>');
					        layer = jQuery(win.document);
							// window.open(objbuilder,'_blank');
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
			
			
			
  			$("#select1").change(function() {   
  				
  				$('#content_attrezzatura').hide();
  				$('#content_sede').hide();
  				$('#content_esercente').hide();
  				
	  			//$("#select1 option[value='']").remove();
	  			//$("#select2 option[value='']").remove(); 
	  			if ($(this).data('options') == undefined) {
	    			/*Taking an array of all options-2 and kind of embedding it on the select1*/
	    			$(this).data('options', $('#select2 option').clone());
	  			}
	  
	  			var id = $(this).val().split("_")[0];	    	
	  			var options = $(this).data('options');	
	  			var opt=[];    	

	    	   for(var  i=0; i<options.length;i++){
					var str=options[i].value; 	    	
		 			
					/* if(str.substring(str.indexOf("_")+1,str.length)==id){ */     			        		
					if(id!=null && str.split("_")[1]==id){
						opt.push(options[i]);
		  			}   
	   			}
	 			
	    	   	$("#select2").prop("disabled", false);    	 
	  		   	$('#select2').html(opt);
	  
		    	$("#select2").trigger("chosen:updated");    
		    	document.getElementById("select2").selectedIndex = 0;
				$("#select2").change();  
	      	
				if(id=="10"){
					$('#content_attrezzatura').show();	
					$('#attrezzatura').select2();
					$('#sede').val("${commessa.INDIRIZZO_UTILIZZATORE}");
					$('#esercente').val("${esercente}");
				}else if(id=="1"){
					$('#content_sede').show();	
					$('#sede').select2();
					$('#content_esercente').show();
				}
				
				
				
				var tecnici = ${tecnici_json};
	 			var sigla = $(this).val().split("_")[1];
 			
	 				
	 				var opt =[];
	 				    	 				
	 				opt.push("<option value=''></option>");
	 				for(var i = 0;i<tecnici.length;i++){    	 					
	 					
	 					var categorie = tecnici[i].categorie;
	 					
	 					if(categorie!=null){
	 					for(var j = 0; j<categorie.length;j++){
	 						if(categorie[j].id==id){
	 							
	 							//opt.push(tecnici_options_options[i]);	
	 							opt.push("<option value='"+tecnici[i].id+"'>"+tecnici[i].nominativo+"</option>");
	 							
	 							}
	 						}
	 					}
	 					
	 					
	 				} 	 				

				
				$('#tecnici').html(opt);
			});	
			
 			$('#select2').change(function(){
 				var selection = $(this).val();
 				if(selection!=null){
	 				var sigla = selection.split("_")[2];
	 				
	 				var opt =[];
	 				opt.push("<option value='0'>Nessuna</option>")
	 				for(var i = 0;i<attrezzatura_options.length;i++){
	 					if( attrezzatura_options[i].value.split("_")[2]!=null && sigla == attrezzatura_options[i].value.split("_")[2]){
	 						opt.push(attrezzatura_options[i]);		
	 					}
	 				}
	 				
	 				$('#attrezzatura').html(opt)
	 				document.getElementById("attrezzatura").selectedIndex = 0;
 				}
 			});
 			
  			
  			$('#select2').change(function(){
  				
  				var val = $(this).val();
  				var opt = [];
  				if(val!=null && val!=''){
  					
  					var gruppo = val.split("_")[2];
  					
  					if(gruppo=="GVR"){
  						opt.push("<option value='1'>Prima verifica periodica attr. e attr. di insiemi soggetti a verifica (art.4)</option>");
  						opt.push("<option value='2'>Prima verifica periodica attr. di insiemi NON soggetti a verifica (art.5)</option>");
  						opt.push("<option value='3'>Verifica successiva alla prima (funzionamento e/o interna)</option>");
  						opt.push("<option value='4'>Verifica successiva alla prima (funzionamento e/o interna e integrità)</option>");
  					}else{
  						opt.push("<option value='1'>Prima periodica</option>");
  						opt.push("<option value='2'>Periodica successiva</option>");
  					}
  					
  					
  					$('#tipo_verifica').html(opt);
  				}
  				
  			});
  			
  			
  			$('#sede').change(function(){
  				
  				var value = $(this).val();
  				
  				if(value !=null && value!='' && value!="0"){
  					$('#esercente').val(value.split("_")[1]);
  					if($('#check_descrizione').prop('checked')){
  						$('#descrizione_sede_util').val(value.split("_")[2]);	
  					}
  					
  				}else if(value=="0"){
  					$('#esercente').val("${esercente}");
  				}
  				
  				
  			});
  			
  			
  			function getDestinatarioEmail(id_intervento){
  				
  				var checked = false;
  				
  				$('input:checkbox').each(function(){
  					
  					var id ="#"+this.id;
  					
  					if($(id).prop('checked')== true){  
  						checked = true;
  						
  					}
  				});
  				
  				if(!checked){
  					$('#modalErrorDiv').html("Attezione! Selezionare almeno un verbale!");
						$('#myModalError').removeClass();
						$('#myModalError').addClass("modal modal-danger");
						$('#myModalError').modal('show');			 				
  				}else{
  					
	  				pleaseWaitDiv = $('#pleaseWaitDialog');
	  				pleaseWaitDiv.modal();
	  				
	  				/* var id ="${verbale.id}";
	  				$('#id_documento').val(id_documento); */
	  					
	  				$.ajax({
	  					type: "POST",
	  					url: "gestioneVerbale.do?action=email_destinatario",
	  					data : "id_intervento="+id_intervento,				
	  					dataType: "json",
	  					success: function( data, textStatus) {
	  						pleaseWaitDiv.modal('hide');
	  						if(data.success){
	  							
	  							$('#destinatario').val(data.indirizzo);
	  							$('#modalDestinatario').modal();
	  							
	  						}else{
	  							$('#modalErrorDiv').html(data.messaggio);
	  							$('#myModalError').removeClass();
	  							$('#myModalError').addClass("modal modal-danger");
	  							$('#myModalError').modal('show');	
	  						}
	  						
	  						
	  						
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
  			}


  			function inviaPec(){
  				
  				 pleaseWaitDiv = $('#pleaseWaitDialog');
  				 pleaseWaitDiv.modal();
  				  
  				
  				  var destinatario = $('#destinatario').val();
  				  var id_documento = $('#id_documento').val();
  				  
  				  if(destinatario!=null && destinatario!=''){
  				  
  					  
  					  $.ajax({
  					  type: "POST",
  					  url: "gestioneVerbale.do?action=invia_email&id_verbali="+send_verbali+"&destinatario="+destinatario,
  					  dataType: "json",
  				
  					  success: function( data, textStatus) {
  						  pleaseWaitDiv.modal('hide');
  						  if(data.success)
  						  { 
  							$('#modalDestinatario').modal('hide');
  						    $('#modalErrorDiv').html(data.messaggio);
  				 			  	$('#myModalError').removeClass();
  				 				$('#myModalError').addClass("modal modal-success");
  				 				$('#report_button').hide();
  				 				$('#visualizza_report').hide();
  				 				$('#myModalError').modal('show');
  				 				$('#myModalError').on('hidden.bs.modal', function(){
  									location.reload();
  								});
  						
  						  }else{
  							  
  							  $('#modalErrorDiv').html(data.messaggio);
  				 			  	$('#myModalError').removeClass();
  				 				$('#myModalError').addClass("modal modal-danger");
  				 				$('#report_button').hide();
  				 				$('#visualizza_report').hide();
  				 				$('#myModalError').modal('show');			
  						  }
  					  
  				  },

  				  error: function(jqXHR, textStatus, errorThrown){
  					  pleaseWaitDiv.modal('hide');

  						$('#modalErrorDiv').html(errorThrown.message);
  					  	$('#myModalError').removeClass();
  						$('#myModalError').addClass("modal modal-danger");
  						$('#report_button').show();
  							$('#visualizza_report').show();
  						$('#myModalError').modal('show');
  						

  				  }
  			  });
  				  }else{
  					  pleaseWaitDiv.modal('hide');
  						  $('#myModalErrorContent').html("Nessun indirizzo email inserito!");
  						  	$('#myModalError').removeClass();
  							$('#myModalError').addClass("modal modal-danger");
  							$('#report_button').hide();
  							$('#visualizza_report').hide();
  							$('#myModalError').modal('show');	
  					  }
  				
  			}
  			
  			
  			
  		</script>	  
	</jsp:attribute> 
</t:layout>
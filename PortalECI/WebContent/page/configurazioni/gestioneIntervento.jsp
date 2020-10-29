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
        			Dettaglio Commessa
        			<small></small>
      			</h1>
      			<c:if test="${user.checkPermesso('NEW_INTERVENTO')}">
      				<button class="btn btn-default pull-right" onClick="nuovoInterventoFromModal()"><i class="glyphicon glyphicon-edit"></i> Nuovo Intervento</button>
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
	 											Dati Commessa
												<div class="box-tools pull-right">		
													<button data-widget="collapse" class="btn btn-box-tool"><i class="fa fa-minus"></i></button>
												</div>
											</div>
											<div class="box-body">	
        										<ul class="list-group list-group-unbordered">
                									<li class="list-group-item">
                  										<b>ID</b> 
                  										<a class="pull-right">${commessa.ID_COMMESSA}</a>
                									</li>
                									<li class="list-group-item">
                  										<b>Data Commessa</b> 
                  										<a class="pull-right"><fmt:formatDate pattern="dd/MM/yyyy" value="${commessa.DT_COMMESSA}" /></a>
                									</li>
                									<li class="list-group-item">
                  										<b>Cliente</b> 
                  										<a class="pull-right">${commessa.ID_ANAGEN_NOME}</a>
                									</li>
                									<li class="list-group-item">
                  										<b>Indirizzo Cliente</b> 
                  										<a class="pull-right">${commessa.INDIRIZZO_PRINCIPALE}</a>
                									</li>
                								<li class="list-group-item">
                  										<b>Utilizzatore</b> 
                  										<a class="pull-right">${commessa.NOME_UTILIZZATORE}</a>
                									</li>
                									<li class="list-group-item">
                  										<b>Indirizzo Utilizzatore</b> 
                  										<a class="pull-right">${commessa.INDIRIZZO_UTILIZZATORE}</a>
                									</li>
                									<li class="list-group-item">
                  										<b>Stato</b> 
                  										<a class="pull-right">
				 											<c:choose>
  																<c:when test="${commessa.SYS_STATO == '1CHIUSA'}">
    																<span class="label label-danger">CHIUSA</span>
  																</c:when>
  																<c:when test="${commessa.SYS_STATO == '1APERTA'}">
    																<span class="label label-success">APERTA</span>
  																</c:when>
  																<c:when test="${commessa.SYS_STATO == '0CREATA'}">
	    															<span class="label label-warning">CREATA</span>
  																</c:when>
  																<c:otherwise>
    																<span class="label label-info">-</span>
	 															</c:otherwise>
															</c:choose>  
														</a>
                									</li>     
                									<li class="list-group-item">
                  										<b>Oggetto:</b> 
                  										<a class="pull-right">${commessa.DESCR}</a>
                									</li>           
                									<li class="list-group-item">
                  										<b>Note:</b> 
                  										<spanclass="pull-right">${commessa.NOTE_GEN}</span>
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
	 											Lista Interventi
												<div class="box-tools pull-right">		
													<button data-widget="collapse" class="btn btn-box-tool"><i class="fa fa-minus"></i></button>
												</div>
											</div>
											<div class="box-body">
		      									<table id="tabPM" class="table table-bordered table-hover dataTable table-striped" role="grid" width="100%">
 													<thead>
 														<tr class="active">
 															<th>ID</th>
 															<th>Sede</th>
 															<th>Data Creazione</th>
 															<th>Stato</th>
 															<th>Responsabile</th>
 															<th>Tecnico Verificatore</th>
 															<th>Codice Categoria</th>
 															<th>Codice Verifica</th>

 															<td></td>
														</tr>
													</thead>
 													<tbody>
 														<c:forEach items="${listaInterventi}" var="intervento" varStatus="loop">	
 															<tr role="row" id="${intervento.id}">
																<td>
																	<a href="#" class="btn customTooltip customlink" title="Click per aprire il dettaglio dell'Intervento" onclick="callAction('gestioneInterventoDati.do?idIntervento=${intervento.id}');">
																		${intervento.id}
																	</a>
																</td>
																<td>${intervento.nome_sede}</td>
																<td>
																	<c:if test="${not empty intervento.dataCreazione}">
																		<fmt:setLocale value="it_IT" />
   																		<fmt:formatDate pattern="dd/MM/yyyy" value="${intervento.dataCreazione}" />
																	</c:if>
																</td>
																<td class="centered">	
	 																<c:if test="${userObj.checkPermesso('CAMBIO_STATO_INTERVENTO_METROLOGIA')}">
																		<c:if test="${intervento.statoIntervento.id == 0}">
																			<a href="#" class="customTooltip" title="Click per chiudere l'Intervento" onClick="chiudiIntervento(${intervento.id},1,'${loop.index}')" id="statoa_${intervento.id}">
																				 <span class="label label-info">${intervento.statoIntervento.descrizione}</span>
																			</a>
																		</c:if>		
																		
																		<c:if test="${intervento.statoIntervento.id == 1}">
																			<a href="#" class="customTooltip" title="Click per chiudere l'Intervento" onClick="chiudiIntervento(${intervento.id},1,'${loop.index}')" id="statoa_${intervento.id}">
																				<span class="label label-success">${intervento.statoIntervento.descrizione}</span>
																			</a>
																		</c:if>		
																		<c:if test="${intervento.statoIntervento.id == 7}">
																			<a href="#" class="customTooltip" title="Click per aprire l'Intervento"  onClick="apriIntervento(${intervento.id},1,'${loop.index}')" id="statoa_${intervento.id}">
																				<span class="label label-warning">${intervento.statoIntervento.descrizione}</span>
																			</a>
																		</c:if>
																		
																	</c:if>
	
	 																<c:if test="${!userObj.checkPermesso('CAMBIO_STATO_INTERVENTO_METROLOGIA')}"> 	
	 																	<a href="#" id="stato_${intervento.id}"> <span class="label" style="color:#000000 !important; background-color:${intervento.statoIntervento.getColore(intervento.statoIntervento.id)} !important;">${intervento.statoIntervento.descrizione}</span></a>
																	</c:if>
																</td>		
																<td>${intervento.user.nominativo}</td>
		 														<td>${intervento.tecnico_verificatore.nominativo}</td> 
		 														<td>
		 															<c:forEach items="${intervento.tipo_verifica}" var="tipo_verifica" varStatus="loop">
		 																${tipo_verifica.categoria.codice }<br/>
		 															</c:forEach>
		 														</td>
		 														<td>
		 															<c:forEach items="${intervento.tipo_verifica}" var="tipo_verifica" varStatus="loop">
		 																${tipo_verifica.codice }<br/>
		 															</c:forEach>
		 														</td>		 		
																<td>
																	<c:if test="${user.checkPermesso('GESTIONE_INTERVENTO')}">
																		<a class="btn customTooltip" title="Click per aprire il dettaglio dell'Intervento" onclick="callAction('gestioneInterventoDati.do?idIntervento=${intervento.id}');">
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
  										<div id="empty" class=" label label-danger testo12"></div>
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
		   
 			var attrezzatura_options;
 			var tecnici_options;
 			
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
 			
 			

 			
 			$('#check_descrizione').on('ifChecked', function(event) {
 				
 				if($('#sede').val()!=''){
 					$('#sede').change();	
 				}
 				
 				$('#content_descrizione_utilizzatore').show();
 				
 				
 				
 			});
 			

 	 		
 		$('#check_descrizione').on('ifUnchecked', function(event) {
 				
 			$('#content_descrizione_utilizzatore').hide();
 			$('#descrizione_sede_util').val('');
 				
 			});
 			
 			var id_row_intervento = 0;
 			
			    $(document).ready(function() {
			    			    	
			    	
			    	
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
				    	
			    	 
			    	 
    				table = $('#tabPM').DataTable({
    					language: {
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
	        			},
	        			pageLength: 100,
    	      			paging: true, 
    	      			ordering: true,
    	      			info: true, 
    	      			searchable: false, 
    	      			targets: 0,
    	      			responsive: true,
    	      			scrollX: false,
    	      			order: [[ 0, "desc" ]],
    	      			columnDefs: [
							{ responsivePriority: 1, targets: 0 },
    	                   	{ responsivePriority: 3, targets: 2 ,type:"date-eu"},
    	                   	{ responsivePriority: 4, targets: 3 },
    	                   	{ responsivePriority: 2, targets: 5 },
    	                   	{ orderable: false, targets: 5 },
    	                   	{ width: "50px", targets: 0 },
    	                   	{ width: "250px", targets: 1 },
    	                   	{ width: "80px", targets: 3 },    	                   
    	               	],             
    	               	buttons: [ {
    	               		extend: 'copy',
    	                   	text: 'Copia',
    	                   	/* exportOptions: {
	                       		modifier: {
	                           		page: 'current'
	                       		}
	                   		} */
    	               	},{
    	                	extend: 'excel',
    	                   	text: 'Esporta Excel',
    	                   	/* exportOptions: {
    	                   		modifier: {
    	                           page: 'current'
    	                       }
    	                   	} */
    	               	},{
    	                	extend: 'colvis',
    	                   	text: 'Nascondi Colonne'    	                   
    	               	}],
    	                "rowCallback": function( row, data, index ) {    	                        	       	                        	      
    	                	$('td:eq(3)', row).addClass("centered");
    	                }
    	  			});
    				
    				table.buttons().container().appendTo( '#tabPM_wrapper .col-sm-6:eq(1)' );
     	          	           	           	     
    				$('#tabPM thead th').each( function () {
        				var title = $('#tabPM thead th').eq( $(this).index() ).text();
        				$(this).append( '<div><input class="inputsearchtable" style="width:100%" type="text" /></div>');
    				} );
  
    				// DataTable
  					table = $('#tabPM').DataTable();
    				// Apply the search
    				table.columns().eq( 0 ).each( function ( colIdx ) {
        				$( 'input', table.column( colIdx ).header() ).on( 'keyup', function () {
            				table
                				.column( colIdx )
                				.search( this.value )
                				.draw();
        				} );
    				} ); 
    
    				table.columns.adjust().draw();
    				$('#tabPM').on( 'page.dt', function () {
						$('.customTooltip').tooltipster({
		        			theme: 'tooltipster-light'
		    			});
		  			} );
    
    				
    				
				    $('#myModal').on('hidden.bs.modal', function (e) {
   	  					$('#noteApp').val("");
   	 					$('#empty').html("");
   	 					
   	 					//$("#select1").val("").trigger("change");
   	 					$("#tecnici").val("").trigger("change");
   						$("#select2").val("").trigger("change");
   						
   						
   					//	$("#select2").prop("disabled", true); 
   					
   						$(".categoriaTipiRow").remove();
   						
   						
   						$('#tabVerifica').DataTable().clear().destroy();
   						$('#bodytabVerifica').html("");
   						$('#noteVerbale').val("");
   						$('#sede').val("");
   						$('#esercente').val("${esercente}")
   					})    
    			});
                
     			//$body = $("body");     
     			$("#tecnici").change(function(){    	 
    	 		
    	 			
    	 			
     			});

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
    	 				
    	 			
      	 	/* 			for(var i = 0;i<tecnici_options.length;i++){
    	 					if( categoria_options[i].value.split("_")[1]!=null && categorie!=null){
    	 						for(var j = 0; j<categorie.length;j++){
    	 							if(categorie[j].codice == categoria_options[i].value.split("_")[1]){
    	 									
    	 							}
    	 						}
    	 						
    	 						
    	 					}
    	 				}
     			 */
    				
    				$('#tecnici').html(opt);
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
      					$('#descrizione_sede_util').val(value.split("_")[2]);
      				}else if(value=="0"){
      					$('#esercente').val("${esercente}");
      				}
      				
      				
      			});
      			
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
					}else{
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
										 
									 var table = $('#tabVerifica').DataTable({language : lang, responsive: true, ordering: false,columnDefs: [{ responsivePriority: 1, targets: 10 }]});
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
									 
								 var table = $('#tabVerifica').DataTable({language : lang, responsive: true, ordering: false,columnDefs: [{ responsivePriority: 1, targets: 10 }]});
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
      				$("#"+tipi_verifica).remove();
      				var table = $('#tabVerifica').DataTable();

      				var index = tipi_verifica.split("_")[1];
      				table.row(index).remove();
      				
      				if($("#bodytabVerifica").find("tr").size()==0){
      					$("#bodytabVerifica").html("");
      					$('#tabVerifica').DataTable().clear().destroy();
      				}
      			}
  			</script>	  
	</jsp:attribute> 
</t:layout>
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
		      									<table id="tabPM" class="table table-bordered table-hover dataTable table-striped" role="grid" width="100%">
 													<thead>
 														<tr class="active">
 															<th>Codice Categoria</th> 															
 															<th>Codice Verifica</th>
 															<th>Data Creazione</th>
 															<th>Descrizione Verifica</th>
 															<th>Stato</th>
 															<th>Numero Certificato</th>
 															<th>Certificato</th>
 															
 															<th width="150px">Sc. Tecnica</th>
 															<th>Attrezzatura</th>
 															<th>Note</th>
 															<td></td>
														</tr>
													</thead>
 													<tbody>
 														<c:forEach items="${intervento.verbali}" var="verbale"> 
 															<tr role="row">
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
  																	${verbale.getDescrizioneVerifica()}
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
    	  																		<a class="btn customTooltip" title="Click per aprire il certificato" onclick="scaricaFile(${docum.id});">
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
	      																		<a class="btn customTooltip" title="Click per aprire la scheda tecnica" onclick="scaricaFile(${docum.id});">
	      																			<i class="glyphicon glyphicon-file"></i>
            																	</a>
    	  																	</c:if>
      																	</c:forEach>
      																</c:if> 
																</td>
																<td>${verbale.attrezzatura.matricola_inail }</td>
																<td>
  																	${verbale.getNote()}
																</td>	
																<td>
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
  						<div id="modalModificaIntervento" class="modal fade" role="dialog" aria-labelledby="myLargeModalLabel">
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
  										<div id="empty" class="label label-danger testo12"></div>
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
		   
			$(document).ready(function() {
				$('#formModificaIntervento').on('submit',function(e){		
					$("#ulError").html("");
	    			e.preventDefault();
	    			updateIntervento();
				}); 
												
    		});	
			
			function addRow(){
				var categorie_verifica=$('#selectCat').val();
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
					/*}else{							
						$('#empty').html("La coppia Categoria Verifica/Tipo Verifica selezionata è già stata inserita!");
					}*/
				}else{						
					$('#empty').html("Scegli la categoria di verifica e il tipo verifica per procedere!");
				}
				$('#pleaseWaitDialog').modal('hide');
			}
				
			function removeRow(tipi_verifica){
				$("#"+tipi_verifica).remove();
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
			
			function scaricaFile(idDoc){
				pleaseWaitDiv = $('#pleaseWaitDialog');
				pleaseWaitDiv.modal();
				$.ajax({
					type: "POST",
					url: "gestioneVerbale.do?action=visualizzaDocumento",
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
			
  		</script>	  
	</jsp:attribute> 
</t:layout>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="it.portalECI.DTO.UtenteDTO" %>
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
      				<h1>
        				Lista Verbali
        				<small>Fai click per entrare nel dettaglio del Verbale</small>
      				</h1>
    			</section>
    			


			    <!-- Main content -->
    			<section class="content">

					<div class="row">
        				<div class="col-xs-12">
          					<div class="box">
            					<div class="box-body">
            					
            		<div class="row">
        				
        				
        					<div class="col-xs-5">
			 <div class="form-group">
				 <label for="datarange" class="control-label">Filtra Data Verifica:</label>
					<div class="col-md-10 input-group" >
						<div class="input-group-addon">
				             <i class="fa fa-calendar"></i>
				        </div>				                  	
						 <input type="text" class="form-control" id="datarange" name="datarange" value=""/> 						    
							 <span class="input-group-btn">
				               <button type="button" class="btn btn-info btn-flat" onclick="filtraDate()">Cerca</button>
				               <button type="button" style="margin-left:5px" class="btn btn-primary btn-flat" onclick="resetDate()">Reset Date</button>
				             </span>				                     
  					</div>  								
			 </div>	
			 
			 
        				</div>
        				<div class="col-xs-7">
        				<c:if test="${userObj.checkRuolo('AM') || userObj.checkCategoria('VAL') }">
        				<a class="btn btn-primary disabled pull-right" id="scadenzario_btn" style="margin-top:25px" onClick="callAction('gestioneListaVerbali.do?action=scadenzario_val&dateFrom=${dateFrom}&dateTo=${dateTo }')">Scadenzario VAL</a>
        				</c:if>
        				<c:if test="${userObj.checkRuolo('AM') || userObj.checkCategoria('VIE') }">
        				<a class="btn btn-primary disabled pull-right" id="scadenzario_vie_btn" style="margin-top:25px;margin-right:5px" onClick="callAction('gestioneListaVerbali.do?action=scadenzario_vie&dateFrom=${dateFrom}&dateTo=${dateTo }')">Scadenzario VIE</a>
        				</c:if>
        				</div>
        				
        				</div>
            					
            					
              						<div class="row">
        								<div class="col-xs-12">
 											<div class="box box-danger box-solid">
												<div class="box-header with-border">
	 												Lista
													<div class="box-tools pull-right">
														<button data-widget="collapse" class="btn btn-box-tool"><i class="fa fa-minus"></i></button>
													</div>
												</div>
												<div class="box-body">												
													
              										<table id="tabPM" class="table table-bordered table-hover dataTable table-striped" role="grid" width="100%">
 														<thead>
 														<c:if test="${user.checkRuolo('CL') }">
 														<th>ID Verbale</th>
 																<th>ID Intervento</th>
 																<th>ID Commessa</th>
 																<th>Numero Verbale</th>
 																<th>Data verifica</th>
 																<th>Matricola Attrezzatura</th>
 																<th style="min-width:200px">Sede Cliente</th>
 																<th>Sede Utilizzatore</th>
 																
 																<th>Codice Verifica</th>
 																<th>Tecnico Verificatore</th><%-- 
 																<th>Descrizione Verifica</th> --%>
 																<th>Stato</th>
 																<th>Stato S.T.</th>
 																<th>Certificati</th>
 																
 																<th>Allegati</th>
 													
 																
 														</c:if>
 														<c:if test="${!user.checkRuolo('CL') }">
 															<tr class="active"> 
 																<th>ID Verbale</th>
 																<th>ID Intervento</th>
 																<th>ID Commessa</th>
 																<th>Numero Verbale</th>
 																<th>Data verifica</th>
 																<th>Matricola Attrezzatura</th>
 																<th style="min-width:200px">Sede Cliente</th>
 																<th>Sede Utilizzatore</th>
 																<th>Codice Categoria</th>
 																<th>Codice Verifica</th>
 																<th>Tecnico Verificatore</th><%-- 
 																<th>Descrizione Verifica</th> --%>
 																<th>Stato</th>
 																<th>Stato S.T.</th>
 																<th>Firmato</th>
 																
 																<th>S.T. Firmata</th>
 													
 																<th>Data Creazione</th>
 																<td></td>
 															</tr>
 															</c:if>
 														</thead>
 
 														<tbody> 
  															<c:forEach items="${listaVerbali}" var="verbale">  	
  															<c:if test="${user.checkRuolo('CL') }">														
 																<tr role="row" id="${verbale.getId()}">
																	<td>
																		${verbale.getId()}
																		
																	</td>	
																	<td>
																	
																			${verbale.getIntervento().getId()}
																		
																	</td>
																	<td>
																	
																			${verbale.intervento.idCommessa}
																		
																	</td>
																	<td>
																		${verbale.numeroVerbale }
																	</td>
																	<td>
																	<c:choose>
																	<c:when test="${verbale.data_verifica!=null }">
																		<fmt:formatDate pattern="dd/MM/yyyy" value='${verbale.data_verifica}' type='date' />
																	</c:when>
																	<c:otherwise>
																		<c:choose>
																		<c:when test="${verbale.data_verifica_interna!=null }">
																			<fmt:formatDate pattern="dd/MM/yyyy" value='${verbale.data_verifica_interna}' type='date' />
																		</c:when>
																		<c:otherwise>
																			<c:if test="${verbale.data_verifica_integrita!=null }">
																			<fmt:formatDate pattern="dd/MM/yyyy" value='${verbale.data_verifica_integrita}' type='date' />
																			</c:if>																	
																		</c:otherwise>
																		</c:choose>
																	</c:otherwise>
																	</c:choose>
																	
																	
																	</td>
																	<td>
																	<c:if test="${verbale.attrezzatura!=null}">
																	${verbale.attrezzatura.matricola_inail }
																	</c:if>
																	
																	
																	</td>
																	
																	<td>
																		<c:out value='${verbale.getIntervento().getNome_sede()}'/>
																	</td>
																	<td>
																		<c:out value='${verbale.getSedeUtilizzatore()}'/>
																	</td>
																
																	<td>
																		<c:out value='${verbale.getCodiceVerifica()}'/>
																	</td>
																	<td>
																		<c:out value='${verbale.getIntervento().getTecnico_verificatore().getNominativo()}'/>
																	</td>
																	<%-- <td>
																		<c:out value='${verbale.getDescrizioneVerifica()}'/>
																	</td> --%>
																	<td>
																		<span class="label" style="color:#000000 !important; background-color:${verbale.getStato().getColore(verbale.getStato().getId())} !important;">${verbale.getStato().getDescrizione()}</span>
																		
																		 																	
																	</td>
																	<td>
																	<c:if test="${verbale.getSchedaTecnica()!=null }">
																			<span class="label" style="color:#000000 !important; background-color:${verbale.getStato().getColore(verbale.getSchedaTecnica().getStato().getId())} !important;">${verbale.getSchedaTecnica().getStato().getDescrizione()}</span>
																		</c:if>  											
																		<c:if test="${verbale.getSchedaTecnica()==null }">
																			<span class="label" style="color:#000000 !important; background-color:grey !important;">ASSENTE</span>
																		</c:if>	 
																	</td>
																	<td>
																	
																
      																	<c:forEach items="${verbale.getDocumentiVerbale()}" var="docum">	
      																		<c:if test="${docum.getType().equals('CERTIFICATO') && !docum.getInvalid()}">
<!--       																		if(verbale.getCodiceCategoria().equals("VAL")) {
					docPdf	= new File(Costanti.PATH_CERTIFICATI+doc.getFilePath().substring(0, doc.getFilePath().length()-4)+"_F.pdf");
				}else {
					docPdf	= new File(Costanti.PATH_CERTIFICATI+doc.getFilePath().substring(0, doc.getFilePath().length()-4)+"_CF.pdf");
				} -->
																				<c:if test="${verbale.getCodiceCategoria().equals('VAL') }">
																					<%-- <a class="btn customTooltip" title="Click per aprire il certificato" onclick="scaricaFile(${docum.id}, ${docum.verbale.id });"> --%>
																					<a class="btn customTooltip" title="Click per aprire l'allegato" href="gestioneDocumento.do?firmato=1&idDocumento=${docum.getId()}">
																					<i class="glyphicon glyphicon-file"></i>
            																	</a>
																				</c:if>
																				<c:if test="${!verbale.getCodiceCategoria().equals('VAL') }">
																					<%-- <a class="btn customTooltip" title="Click per aprire il certificato" onclick="scaricaFile(${docum.id}, ${docum.verbale.id });"> --%>
																				<a class="btn customTooltip" title="Click per aprire l'allegato" href="gestioneDocumento.do?controfirmato=1&idDocumento=${docum.getId()}">
    	  																		<%-- <a class="btn customTooltip" title="Click per aprire il certificato" onclick="scaricaFile(${docum.id}, ${docum.verbale.id });"> --%>
	      																			<i class="glyphicon glyphicon-file"></i>
            																	</a>
            																	</c:if>
	      																	</c:if>      																		
      																	</c:forEach>
      																
																																		
																	</td>

																	<td>
																	
																		
      																	<c:forEach items="${verbale.getDocumentiVerbale()}" var="docum">	      																		
      																		<c:if test="${docum.getType().equals('ALLEGATO')}">
	      																		<%-- <a class="btn customTooltip" title="Click per aprire l'allegato" onclick="scaricaFile(${docum.id}, ${docum.verbale.id });">gestioneDocumento.do?idDocumento=${allegato.getId() --%>
	      																		<a class="btn customTooltip" title="Click per aprire l'allegato" href="gestioneDocumento.do?idDocumento=${docum.getId()}">
	      																		
	      																			<i class="glyphicon glyphicon-file"></i>
            																	</a>
    	  																	</c:if>
      																	</c:forEach>
      														
																																		
																	</td>
																	
        															
																	
																</tr>
																</c:if>
																
																
																
																<c:if test="${!user.checkRuolo('CL') }">														
 																<tr role="row" id="${verbale.getId()}">
																	<td>
																		 <a href="gestioneVerbale.do?action=dettaglio&idVerbale=${verbale.getId()}" type="submit" class="btn customTooltip customlink" title="Click per aprire il dettaglio del Verbale" >
																			<c:out value='${verbale.getId()}'/>
																		</a>
																		<%-- <a target="_blank" href="gestioneVerbale.do?idVerbale=${verbale.getId()}" class="btn customTooltip customlink" title="Click per aprire il dettaglio del Verbale" >
																			<c:out value='${verbale.getId()}'/>
																		</a> --%>
																																			</td>	
																	<td>
																		<a href="gestioneInterventoDati.do?idIntervento=${verbale.getIntervento().getId()}" class="btn customTooltip customlink" title="Click per aprire il dettaglio dell'Intervento">
																			${verbale.getIntervento().getId()}
																		</a>
																	</td>
																	<td>
																	<a href="gestioneIntervento.do?idCommessa=${verbale.intervento.idCommessa}" class="btn customTooltip customlink" title="Click per aprire il dettaglio della Commessa" >
																			${verbale.intervento.idCommessa}
																		</a>
																	</td>
																	<td>
																		${verbale.numeroVerbale }
																	</td>
																	<td>
																	<c:choose>
																	<c:when test="${verbale.data_verifica!=null }">
																		<fmt:formatDate pattern="dd/MM/yyyy" value='${verbale.data_verifica}' type='date' />
																	</c:when>
																	<c:otherwise>
																		<c:choose>
																		<c:when test="${verbale.data_verifica_interna!=null }">
																			<fmt:formatDate pattern="dd/MM/yyyy" value='${verbale.data_verifica_interna}' type='date' />
																		</c:when>
																		<c:otherwise>
																			<c:if test="${verbale.data_verifica_integrita!=null }">
																			<fmt:formatDate pattern="dd/MM/yyyy" value='${verbale.data_verifica_integrita}' type='date' />
																			</c:if>																	
																		</c:otherwise>
																		</c:choose>
																	</c:otherwise>
																	</c:choose>
																	
																	
																	</td>
																	<td>
																	<c:if test="${verbale.attrezzatura!=null}">
																	${verbale.attrezzatura.matricola_inail }
																	</c:if>
																	
																	
																	</td>
																	
																	<td>
																		<c:out value='${verbale.getIntervento().getNome_sede()}'/>
																	</td>
																	<td>
																		<c:out value='${verbale.getSedeUtilizzatore()}'/>
																	</td>
																	<td>
																		<c:out value='${verbale.getCodiceCategoria()}'/>
																	</td>
																	<td>
																		<c:out value='${verbale.getCodiceVerifica()}'/>
																	</td>
																	<td>
																		<c:out value='${verbale.getIntervento().getTecnico_verificatore().getNominativo()}'/>
																	</td>
																	<%-- <td>
																		<c:out value='${verbale.getDescrizioneVerifica()}'/>
																	</td> --%>
																	<td>
																		<span class="label" style="color:#000000 !important; background-color:${verbale.getStato().getColore(verbale.getStato().getId())} !important;">${verbale.getStato().getDescrizione()}</span>
																		
																		 																	
																	</td>
																	<td>
																	<c:if test="${verbale.getSchedaTecnica()!=null }">
																			<span class="label" style="color:#000000 !important; background-color:${verbale.getStato().getColore(verbale.getSchedaTecnica().getStato().getId())} !important;">${verbale.getSchedaTecnica().getStato().getDescrizione()}</span>
																		</c:if>  											
																		<c:if test="${verbale.getSchedaTecnica()==null }">
																			<span class="label" style="color:#000000 !important; background-color:grey !important;">ASSENTE</span>
																		</c:if>	 
																	</td>
																	<td>
																	
																	<c:if test="${verbale.firmato == 1 && verbale.controfirmato == 1}">
																		CF
																	</c:if>
																		<c:if test="${verbale.firmato == 1 && verbale.controfirmato == 0}">
																		F
																	</c:if>
																																		
																	</td>

																	<td>
																	
																	<c:if test="${verbale.getSchedaTecnica()!=null && verbale.getSchedaTecnica().getFirmato() == 1 && verbale.getSchedaTecnica().getControfirmato()  == 1}">
																		CF
																	</c:if>
																	
																	<c:if test="${verbale.getSchedaTecnica()!=null && verbale.getSchedaTecnica().getFirmato() == 1 && verbale.getSchedaTecnica().getControfirmato()  == 0}">
																		F
																	</c:if>
																																		
																	</td>
																	
        															<td>
																		<fmt:formatDate pattern="dd/MM/yyyy" value='${verbale.getCreateDate()}' type='date' />
																	</td>
																	<td>
																		<a class="btn customTooltip" title="Click per aprire il dettaglio del Verbale" onclick="callAction('gestioneVerbale.do?idVerbale=${verbale.getId()}');">
                															<i class="fa fa-arrow-right"></i>
            															</a>
        															</td>
																</tr>
																</c:if>
																
																
															</c:forEach>
 														</tbody>
 													</table>  
												</div>
											</div>
										</div>
									</div>
            						<!-- /.box-body -->
          						</div>
        					</div>
        					<!-- /.col -->
 						</div>
					</div>  	

				    <div id="errorMsg"><!-- Place at bottom of page --></div> 
				    
				    
				    
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
				</section>  
  			</div>
  			<!-- /.content-wrapper -->
	
  			<t:dash-footer />
 
  			<t:control-sidebar />
  
		</div>
		<!-- ./wrapper -->

	</jsp:attribute>
     					

	<jsp:attribute name="extra_css">
		<link type="text/css" href="css/bootstrap.min.css" />
	<link rel="stylesheet" type="text/css" href="//cdn.jsdelivr.net/bootstrap.daterangepicker/2/daterangepicker.css" />
	</jsp:attribute>

	<jsp:attribute name="extra_js_footer">
<script type="text/javascript" src="plugins/datepicker/locales/bootstrap-datepicker.it.js"></script> 
<script type="text/javascript" src="//cdn.jsdelivr.net/bootstrap.daterangepicker/2/daterangepicker.js"></script>
<script type="text/javascript" src="plugins/datejs/date.js"></script>
  		<script type="text/javascript">
  		
  		function filtraDate(){
  			
  			var startDatePicker = $("#datarange").data('daterangepicker').startDate;
  		 	var endDatePicker = $("#datarange").data('daterangepicker').endDate;
  		 	dataString = "?action=filtra_date&dateFrom=" + startDatePicker.format('YYYY-MM-DD') + "&dateTo=" + 
  		 			endDatePicker.format('YYYY-MM-DD');
  		 	
  		 	 pleaseWaitDiv = $('#pleaseWaitDialog');
  			  pleaseWaitDiv.modal();

  		 	callAction("gestioneListaVerbali.do"+ dataString, false,true);

  		 	//exploreModal("gestioneListaVerbali.do", dataString, '#content_consuntivo');
  	}




  	function resetDate(){
  		pleaseWaitDiv = $('#pleaseWaitDialog');
  			  pleaseWaitDiv.modal();
  		callAction("gestioneListaVerbali.do");

  	}

	
  	function formatDate(data){
  		
  		   var mydate =  Date.parse(data);
  		   
  		   if(!isNaN(mydate.getTime())){
  		   
  			var   str = mydate.toString("dd/MM/yyyy");
  		   }			   
  		   return str;	 		
  	}

   
    		$(document).ready(function() {
    			
    			
    			
    		     var start = "${dateFrom}";
    		   	var end = "${dateTo}";

    		   	if(start !='' && end !=''){
    		   		$('#scadenzario_btn').removeClass("disabled");
    		   		$('#scadenzario_vie_btn').removeClass("disabled");
    		   	}else{
    		   		$('#scadenzario_btn').addClass("disabled");
    		   		$('#scadenzario_vie_btn').addClass("disabled");
    		   	}
    		   	
    		   	$('input[name="datarange"]').daterangepicker({
    		  	    locale: {
    		  	      format: 'DD/MM/YYYY'
    		  	    
    		  	    }
    		  	}, 
    		  	function(start, end, label) {

    		  	});
    		   	
    		   	if(start!=null && start!=""){
    		  	 	$('#datarange').data('daterangepicker').setStartDate(formatDate(start));
    		  	 	$('#datarange').data('daterangepicker').setEndDate(formatDate(end));
    		  	
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
    	                { responsivePriority: 3, targets: 2 },
    	                { responsivePriority: 4, targets: 3 },
    	                { responsivePriority: 2, targets: 6 },
    	                { responsivePriority: 2, targets: 8, type:"date-eu" },
    	                { responsivePriority: 5, targets: 11 },
    	                { orderable: false, targets: 6 },
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
    	            }]    	        	     
    	    	});
    			
     			table.buttons().container().appendTo( '#tabPM_wrapper .col-sm-6:eq(1)' );
     	    		       	           	           	 	
    
    			$('#tabPM thead th').each( function () {
        			var title = $('#tabPM thead th').eq( $(this).index() ).text();
        			$(this).append( '<div><input class="inputsearchtable" style="width:100%" type="text" /></div>');
				} );
       			
				$('.inputsearchtable').on('click', function(e){
   					e.stopPropagation();    
				});
 
    			// DataTable
  				table = $('#tabPM').DataTable();
    
    			// Apply the search
    			table.columns().eq( 0 ).each( function ( colIdx ) {
        			$( 'input', table.column( colIdx ).header() ).on( 'keyup', function () {
            			table.column( colIdx ).search( this.value ).draw();		
        			} );
    			} ); 
    			
    			table.columns.adjust().draw();
    
    	 		$('#tabPM').on( 'page.dt', function () {
					$('.customTooltip').tooltipster({
			        	theme: 'tooltipster-light'
			    	});
			  	} );
    	 		
    		});
    		
    		
    		
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
    		    
  		</script>
  
  
	</jsp:attribute> 
</t:layout>
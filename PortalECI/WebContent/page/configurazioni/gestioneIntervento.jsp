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
                  										<b>Sede</b> 
                  										<a class="pull-right">${commessa.ANAGEN_INDR_DESCR} ${commessa.ANAGEN_INDR_INDIRIZZO}</a>
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
										<div class="box box-danger box-solid collapsed-box">
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
 															<th>Scheda Tecnica da compilare</th> 		 														
 															<td></td>
														</tr>
													</thead>
 													<tbody id="bodytabVerifica"> 			
 													
												
 													</tbody>
 												</table>  
  											</div>
  										</div>
  										<div id="empty" class=" label label-danger testo12"></div>
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
		   
			    $(document).ready(function() {
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
    
    				var tableAttiìvita = $('#tabAttivita').DataTable({
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
	      				paging: true, 
	      				pageLength: 5,
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
	               		],       
	               		buttons: [ {
	                   		extend: 'copy',
	                   		text: 'Copia',	                   
	               		},{
	                   		extend: 'excel',
	                   		text: 'Esporta Excel',	                 
	               		},{
	                   		extend: 'pdf',
	                   		text: 'Esporta Pdf',	                  
	               		},{
	                   		extend: 'colvis',
	                   		text: 'Nascondi Colonne'	                   
	               		}],
	                    "rowCallback": function( row, data, index ) {	                        	  
	                    	$('td:eq(1)', row).addClass("centered");
	                       	$('td:eq(4)', row).addClass("centered");
	                    }	    		      
	    			});
    				
    				tableAttiìvita.buttons().container().appendTo( '#tabAttivita_wrapper .col-sm-6:eq(1)' );	   
    				$('#tabAttivita').on( 'page.dt', function () {
						$('.customTooltip').tooltipster({
		        			theme: 'tooltipster-light'
		    			});
		  			} );
 	     	 
					$('#tabAttivita thead th').each( function () {
  						var title = $('#tabAttivita thead th').eq( $(this).index() ).text();
  						$(this).append( '<div><input class="inputsearchtable" style="width:100%" type="text"  /></div>');
					} );
					
					$('.inputsearchtable').on('click', function(e){
    					e.stopPropagation();    
 					});
					
					// DataTable
					tableAttiìvita = $('#tabAttivita').DataTable();
					// Apply the search
					tableAttiìvita.columns().eq( 0 ).each( function ( colIdx ) {
  						$( 'input', table.column( colIdx ).header() ).on( 'keyup', function () {
      						table
          						.column( colIdx )
          						.search( this.value )
          						.draw();
  						} );
					} ); 
					
					tableAttiìvita.columns.adjust().draw();            
    
				    $('#myModal').on('hidden.bs.modal', function (e) {
   	  					$('#noteApp').val("");
   	 					$('#empty').html("");
   	 					
   	 					$("#select1").val(null).trigger("change");
   	 					$("#tecnici").val(null).trigger("change");
   						$("#select2").val(null).trigger("change");
   					
   					
   						$("#select2").prop("disabled", true); 
   					
   						$(".categoriaTipiRow").remove();
   					})    
    			});
                
     			//$body = $("body");     
     			$("#tecnici").change(function(){    	 
    	 			$("#tecnici option[value='']").remove();
     			});

      			$("#select1").change(function() {    
    	  			$("#select1 option[value='']").remove();
    	  			$("#select2 option[value='']").remove(); 
    	  			if ($(this).data('options') == undefined) {
    	    			/*Taking an array of all options-2 and kind of embedding it on the select1*/
    	    			$(this).data('options', $('#select2 option').clone());
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
    	 			
		    	   	$("#select2").prop("disabled", false);    	 
    	  		   	$('#select2').html(opt);
    	  
			    	$("#select2").trigger("chosen:updated");    	      	 
    				$("#select2").change();  
    	      	
    			});	        
      			
				function addRow(){
					var categorie_verifica=$('#select1').val();
					var tipi_verifica=$('#select2').val();									
					
					if(categorie_verifica!=null && tipi_verifica!=null){
						//if($("#" +tipi_verifica).length == 0) {						 										
							
							var id_tipo=tipi_verifica.substring(0, tipi_verifica.indexOf("_"));
							
							$.ajax({
								type: "GET",
								url: "gestioneTipiVerifica.do?action=checkSchedaTecnica&idVerifica="+id_tipo,
								dataType: "json",
								success: function(  dataResp, textStatus) {
									var objectdata='<tr class="categoriaTipiRow" id="'+tipi_verifica+'" role="row" >'+
									'<td >'+$('#select1').find('[value='+categorie_verifica+']').text()+'</td>'+
									'<td >'+$('#select2').find('[value='+tipi_verifica+']').text()+'</td>'+	
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

								
								}
							});
							/*$('#select1').find('[value='+categorie_verifica+']').remove();
							$('#select2').find('[value='+tipi_verifica+']').remove();*/
						/*}else{							
							$('#empty').html("La coppia Categoria Verifica/Tipo Verifica selezionata è già stata inserita!");
						}*/
					}else{						
						$('#empty').html("Scegli la categoria di verifica e il tipo verifica per procedere!");
					}
				}
      			
      			function removeRow(tipi_verifica){
      				$("#"+tipi_verifica).remove();
      			}
  			</script>	  
	</jsp:attribute> 
</t:layout>
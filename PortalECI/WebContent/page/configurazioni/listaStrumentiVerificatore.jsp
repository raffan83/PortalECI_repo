<%@page import="java.util.ArrayList"%>
<%@page import="com.google.gson.JsonArray"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="it.portalECI.DTO.UtenteDTO"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<%

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
        				Gestione Strumenti Verificatore
      				</h1>
    			</section>

    			<!-- Main content -->
    			<section class="content">

					<div class="row">
    	   	 			<div class="col-xs-12">
       						<div class="box">
       							<div class="box-header">
         	
         			 			</div>
       				 			<div class="box-body">
              						<div class="row">
       									<div class="col-xs-12">
       									
       											<div class="row">
<div class="col-lg-12">

<a class="btn btn-primary pull-right" onClick="modalNuovoStrumento()"><i class="fa fa-plus"></i>Nuovo Strumento</a>
</div>
</div>
		<br>
		
		<div class="row">
<div class="col-lg-12">


	<table id="tabStrumenti" class="table table-bordered table-hover dataTable table-striped" role="grid" width="100%">
<thead><tr class="active">
 					
 						<th>ID</th> 						  
            	       <th>Marca</th>		   
            		   <th>Modello</th>
                       <th>Matricola</th>
                       <th>Data ultima taratura</th>
                       <th>Scadenza</th>
                       <th>Associato a</th>
                       <td style="min-width:100px;">Azioni</td>
 </tr></thead>
 
 <tbody>
 	<c:forEach items="${lista_strumenti}" var="strumento" varStatus="loop">
<tr>
 	<td>${strumento.id }</td>
 	<td>${strumento.marca }</td>
 	<td>${strumento.modello }</td>
 	<td>${strumento.matricola }</td>
 	<td><fmt:formatDate pattern = "dd/MM/yyyy" value = "${strumento.data_ultima_taratura }" /></td>
 	<td><fmt:formatDate pattern = "dd/MM/yyyy" value = "${strumento.scadenza }" /></td>
 	<td>${strumento.nominativo_verificatore }</td>
 	<td>
 	
 	<a class="btn btn-warning customTooltip" title="Modifica e associa strumento" onClick="modalModificaStrumento('${strumento.id }','${strumento.marca }','${strumento.matricola }','${strumento.modello }','${strumento.data_ultima_taratura }','${strumento.scadenza }','${strumento.id_verificatore }')"><i class="fa fa-edit"></i></a>
 	<c:if test="${strumento.id_verificatore!=null }">
 	<a class="btn btn-info customTooltip" title="Dissocia strumento" onClick="dissociaStrumento('${strumento.id}')"><i  class="fa fa-times"></i></a>
 	
 	</c:if>
<a class="btn btn-danger customTooltip" title="Elimina strumento" onClick="eliminaStrumentoModal('${strumento.id}','${strumento.id_verificatore }')"><i  class="fa fa-trash"></i></a> 
 	</td>
 	
 	</tr>
 	</c:forEach>
 
</tbody>


</table>



</div>
</div>
       									
       									
       										<%-- <div class="nav-tabs-custom">
						        	    		<ul id="mainTabs" class="nav nav-tabs">
						            	  			<li class="active">
						              					<a href="#strumenti" data-toggle="tab" aria-expanded="true"   id="strumentiTab">
						              						Gestione Strumenti
						              					</a>
		
							              			</li>
							              			<li>
								              			<a href="#associazione" data-toggle="tab" aria-expanded="false"   id="associazioneTab">
								              				Associazione Strumenti Verificatore
								              			</a>
								              		</li>
								              		
						            			</ul>
						            			<div class="tab-content">
							              			<div class="tab-pane active" id="strumenti">
		
														
						    						</div> 		
						    						
						    						
						    										
<div class="tab-pane table-responsive" id="associazione">

<div class="row">
<div class="col-xs-12" >
<select id="verificatori" name="verificatori" class="form-contol select2" data-placeholder="Seleziona Tecnico Verificatore..." style="width:100%">
<option value=""></option>
<c:forEach items="${lista_verificatori }" var="verificatore" varStatus="loop">
<option value="${verificatore.id}">${verificatore.nominativo }</option>

</c:forEach>


</select>


  </div>
  
  
  
  
</div>
<br>

<div class="row">
			<div class="col-xs-12">												
		<div id="boxLista" class="box box-danger box-solid">
			<div class="box-header with-border">
			Lista Strumenti Verificatore
					<div class="box-tools pull-right">															
				<button data-widget="collapse" class="btn btn-box-tool"><i class="fa fa-minus"></i></button>													
							</div>
						</div>
					<div class="box-body">
			<div id="posTabStrumentiVerificatore">LISTA VUOTA</div>
				</div>
				</div>
				</div>
			</div>


							            	    							         
	</div>
							
										 				
								              		<!-- /.tab-pane -->
							    	        	</div>
							        	    	<!-- /.tab-content -->
							          		</div>       						 --%>
       									</div>
	        						</div>
    	    					</div>
       						</div>
	        			</div>
    	    		</div>
	
       <div id="myModalError" class="modal fade" role="dialog" aria-labelledby="myLargeModalLabel">
    <div class="modal-dialog" role="document">
    <div class="modal-content">
     <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabelHeader">Messaggio</h4>
      </div>
       <div class="modal-body">
			<div id="myModalErrorContent">
			
			</div>
   
  		<div id="empty" class="testo12"></div>
  		 </div>
      <div class="modal-footer">
 
        <button  type="button" class="btn btn-outline" data-dismiss="modal">Chiudi</button>

      
      </div>
    </div>
  </div>
</div>
     
					
<form class="form-horizontal" id="formNuovoStrumento">
<div id="myModalNuovoStrumento" class="modal fade" role="dialog" aria-labelledby="myLargeModalLabel">
    <div class="modal-dialog modal-md" role="document">
    <div class="modal-content">
     <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Nuovo Strumento</h4>
      </div>
       <div class="modal-body">



    <div class="form-group">
          <label for="inputEmail" class="col-sm-4 control-label">Marca:</label>

         <div class="col-sm-8">
         <input class="form-control" id="marca" type="text" name="marca" required value=""/>
    	</div>
   </div>
	<div class="form-group">
          <label for="inputEmail" class="col-sm-4 control-label">Modello:</label>

         <div class="col-sm-8">
         <input class="form-control" id="modello" type="text" name="modello" required value=""/>
    	</div>
   </div>
   
   	<div class="form-group">
          <label for="inputEmail" class="col-sm-4 control-label">Matricola:</label>

         <div class="col-sm-8">
         <input class="form-control" id="matricola" type="text" name="matricola" required value=""/>
    	</div>
   </div>


         <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Data ultima taratura:</label>
        <div class="col-sm-8">
                      <input class="form-control datepicker" id="data_ultima_taratura" type="text" name="data_ultima_taratura"  value="" required data-date-format="dd/mm/yyyy"/>
    </div>
       </div> 
       
         <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Data scadenza:</label>
        <div class="col-sm-8">
                      <input class="form-control datepicker" id="scadenza" type="text" name="scadenza"  value="" required data-date-format="dd/mm/yyyy"/>
    </div>
       </div> 
       
       
                <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Associa a:</label>
        <div class="col-sm-8">
                            <select id="verificatore" name="verificatore" class="form-contol select2" data-placeholder="Seleziona Tecnico Verificatore..." style="width:100%">
<option value=""></option>
<option value="0">Nessuno</option>
<c:forEach items="${lista_verificatori }" var="verificatore" varStatus="loop">
<option value="${verificatore.id}">${verificatore.nominativo }</option>

</c:forEach>


</select>
    </div>
       </div> 
       


  		 </div>
      <div class="modal-footer">
			<button type="submit" class="btn btn-primary pull-right" >Salva</button>
      </div>
    </div>
  </div>
 
</div>
   </form>
   
   
   
   
   <form class="form-horizontal" id="formModificaStrumento">
<div id="myModalModificaStrumento" class="modal fade" role="dialog" aria-labelledby="myLargeModalLabel">
    <div class="modal-dialog modal-md" role="document">
    <div class="modal-content">
     <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Modifica Strumento</h4>
      </div>
       <div class="modal-body">



    <div class="form-group">
          <label for="inputEmail" class="col-sm-4 control-label">Marca:</label>

         <div class="col-sm-8">
         <input class="form-control" id="marca_mod" type="text" name="marca_mod" required value=""/>
    	</div>
   </div>
	<div class="form-group">
          <label for="inputEmail" class="col-sm-4 control-label">Modello:</label>

         <div class="col-sm-8">
         <input class="form-control" id="modello_mod" type="text" name="modello_mod" required value=""/>
    	</div>
   </div>
   
   	<div class="form-group">
          <label for="inputEmail" class="col-sm-4 control-label">Matricola:</label>

         <div class="col-sm-8">
         <input class="form-control" id="matricola_mod" type="text" name="matricola_mod" required value=""/>
    	</div>
   </div>


         <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Data ultima taratura:</label>
        <div class="col-sm-8">
                      <input class="form-control datepicker" id="data_ultima_taratura_mod" type="text" name="data_ultima_taratura_mod"  value="" required data-date-format="dd/mm/yyyy"/>
    </div>
       </div> 
       
         <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Data scadenza:</label>
        <div class="col-sm-8">
                      <input class="form-control datepicker" id="scadenza_mod" type="text" name="scadenza_mod"  value="" required data-date-format="dd/mm/yyyy"/>
    </div>
       </div> 
       
                       <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Associa a:</label>
        <div class="col-sm-8">
                            <select id="verificatore_mod" name="verificatore_mod" class="form-contol select2" data-placeholder="Seleziona Tecnico Verificatore..."  style="width:100%">
<option value=""></option>
<option value="0">Nessuno</option>
<c:forEach items="${lista_verificatori }" var="verificatore" varStatus="loop">
<option value="${verificatore.id}">${verificatore.nominativo }</option>

</c:forEach>


</select>
    </div>
       </div> 
       
<input type="hidden" id ="id_strumento" name="id_strumento">
  		 </div>
      <div class="modal-footer">
			<button type="submit" class="btn btn-primary pull-right" >Salva</button>
      </div>
    </div>
  </div>
 
</div>
   </form>
					
					
					  <div id="myModalYesOrNo" class="modal fade" role="dialog" aria-labelledby="myLargeModalsaveStato">
   
    <div class="modal-dialog modal-md" role="document">
    <div class="modal-content">
     <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Attenzione</h4>
      </div>
       <div class="modal-body">       
      	<div id="delete_text"></div>
      	</div>
      <div class="modal-footer">
      <input type="hidden" id="elimina_strumento_id">
      <a class="btn btn-primary" onclick="eliminaStrumentoVerificatore($('#elimina_strumento_id').val())" >SI</a>
		<a class="btn btn-primary" onclick="$('#myModalYesOrNo').modal('hide')" >NO</a>
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

	</jsp:attribute>

	<jsp:attribute name="extra_js_footer">
		<script src="plugins/jquery.appendGrid/jquery.appendGrid-1.6.3.js"></script>
		<script type="text/javascript" src="https://ajax.aspnetcdn.com/ajax/jquery.validate/1.13.1/jquery.validate.min.js"></script>
	<script type="text/javascript" src="plugins/datejs/date.js"></script>
<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="plugins/datepicker/locales/bootstrap-datepicker.it.js"></script>

		<script type="text/javascript">
	
   		</script>

  		<script type="text/javascript">
  		
  		$('#verificatori').on('change',function(){
  			
  			var utente = $("#verificatori").val();        
			dataString ="action=lista_strumenti_verificatore&idUtente="+ utente;
			exploreModal("listaCategorieVerifica.do",dataString,"#posTabCategorie",function(data,textStatus){			
			});
  			
  		});
  		
  		function eliminaStrumentoModal(id_strumento, id_verificatore){
  			
  			if(id_verificatore!=null && id_verificatore!=''){
  				
  				$('#delete_text').html("Attenzione! Lo strumento è associato ad un verificatore. Procedere con la dissociazione e l'eliminazione dello strumento?");
  				
  			}else{
  				$('#delete_text').html("Sicuro di volere eliminare lo strumento selezionato?");
  			}
  			
  			
  			$('#elimina_strumento_id').val(id_strumento);
  			$('#myModalYesOrNo').modal();
  			
  		}
  		
  		
  		var columsDatatables = [];

  		$("#tabStrumenti").on( 'init.dt', function ( e, settings ) {

  		    var api = new $.fn.dataTable.Api( settings );
  		    var state = api.state.loaded();
  		 
  		    if(state != null && state.columns!=null){
  		    		console.log(state.columns);
  		    
  		    columsDatatables = state.columns;
  		    }
  		    $('#tabStrumenti thead th').each( function () {
  		     	if(columsDatatables.length==0 || columsDatatables[$(this).index()]==null ){columsDatatables.push({search:{search:""}});}
  		    	   var title = $('#tabStrumenti thead th').eq( $(this).index() ).text();
  		    	  
  		    		   $(this).append( '<div><input class="inputsearchtable" id="inputsearchtable_'+$(this).index()+'" style="width:100%" type="text" value="'+columsDatatables[$(this).index()].search.search+'" /></div>');
  		    	     		    	  
  		    	});

  		});
  		
  		
  		
  		
  		

  		function modalModificaStrumento(id_strumento, marca, matricola, modello, data_taratura, scadenza, id_verificatore){
  			

  			$('#id_strumento').val(id_strumento);
  			$('#marca_mod').val(marca);
  			$('#matricola_mod').val(matricola);
  			$('#modello_mod').val(modello);
  			$('#modello_mod').val(modello);
  			$('#verificatore_mod').val(id_verificatore);
  			$('#verificatore_mod').change();

  			if(data_taratura!=null && data_taratura!=""){
  				  $('#data_ultima_taratura_mod').val(Date.parse(data_taratura).toString("dd/MM/yyyy"));
  			  }
  			if(scadenza!=null && scadenza!=""){
  				  $('#scadenza_mod').val(Date.parse(scadenza).toString("dd/MM/yyyy"));
  			  }

  			$('#myModalModificaStrumento').modal();
  			
  		}

  		
  	     $('.datepicker').datepicker({
  			 format: "dd/mm/yyyy"
  		 }); 

  		
  		
  		function modalNuovoStrumento(){
  			
  			$('#myModalNuovoStrumento').modal();
  		}
  		
  		$('#formNuovoStrumento').on('submit', function(e){
  			
  			e.preventDefault();
  			nuovoStrumentoVerificatore();
  		});
  		
  		$('#formModificaStrumento').on('submit', function(e){
  			
  			e.preventDefault();
  			modificaStrumentoVerificatore();
  		});
  		
  		

  		
  		
  		$(document).ready(function() {    		
  			
  			$('.dropdown-toggle').dropdown();
    			
  			var tab_str = "${tab_str_attivo}";
  			var tab_ass = "${tab_ass_attivo}";
  			
  			if(tab_str!=null && tab_str!=''){

  				$('#associazione').removeClass('active');  			
  				$('#strumenti').addClass('active');
  				
  				
  				 //$('.nav-tabs a[href="#rilievi"]').tab('show');
  				 $('a[data-toggle="associazione"]').tab('show');
  				 
  		  
  			}
  			if(tab_ass!=null && tab_ass!=''){

  				$('#associazione').removeClass('active');
  				
  				$('#strumenti').addClass('active');
  				
  				
  				 //$('.nav-tabs a[href="#rilievi"]').tab('show');
  				 $('a[data-toggle="strumenti"]').tab('show');
  				 
  		    
  			}
			
			
			$('.select2').select2();
			
			 //$('.nav-tabs a[href="#rilievi"]').tab('show');
			 $('a[data-toggle="tab2"]').tab('show');
    			
    			table = $('#tabStrumenti').DataTable({
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
    			      stateSave: true,
    			      order:[[0, "desc"]],
    			      columnDefs: [
    							 
    			                   { responsivePriority: 1, targets: 0 },	                  
    			                   
    			              
    			                  /*  { orderable: false, targets: 6 }, */
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
    			               },
    			               {
    			                   extend: 'colvis',
    			                   text: 'Nascondi Colonne'
    			                   
    			               }
    			                         
    			                          ]
    			    	
    			      
    			    });
    			table.buttons().container()
    		  .appendTo( '#tabStrumenti_wrapper .col-sm-6:eq(1)' );
    			
    			
    			table.buttons().container().appendTo( '#tabStrumenti_wrapper .col-sm-6:eq(1)');
    			    $('.inputsearchtable').on('click', function(e){
    			       e.stopPropagation();    
    			    });
    		//DataTable
    		//table = $('#tabPM').DataTable();
    		//Apply the search
    		table.columns().eq( 0 ).each( function ( colIdx ) {
    		$( 'input', table.column( colIdx ).header() ).on( 'keyup', function () {
    		  table
    		      .column( colIdx )
    		      .search( this.value )
    		      .draw();
    		} );
    		} );  
    		table.columns.adjust().draw();
    			

  				/* $("#selectRuolo").change(function(e){
        			//get the form data using another method 
        			var ruolo = $("#selectRuolo").val();    

		        	dataString ="idRuolo="+ ruolo;
        			exploreModal("listaPermessi.do",dataString,"#posTabPermessi",function(data,textStatus){
        			});     
 	 			});
			
  				$("#selectRuoloUtente").change(function(e){		
        			var ruolo = $("#selectRuoloUtente").val();
        			dataString ="idRuolo="+ ruolo;
        			exploreModal("listaUtenti.do",dataString,"#posTabUtenti",function(data,textStatus){
        			});       
 	 			});
				
  				$("#selectUtente").change(function(e){	
        			var utente = $("#selectUtente").val();        
        			dataString ="idUtente="+ utente;
        			exploreModal("listaRuoli.do",dataString,"#posTabRuoli",function(data,textStatus){			
        			});        
 	 			});  	
  				
  				$("#selectVerificatore").change(function(e){	
        			var utente = $("#selectVerificatore").val();        
        			dataString ="action=associazioni&idUtente="+ utente;
        			exploreModal("listaCategorieVerifica.do",dataString,"#posTabCategorie",function(data,textStatus){			
        			});        
 	 			});  
			});	   
		
	    	$('#myModalError').on('hidden.bs.modal', function (e) {
				if($( "#myModalError" ).hasClass( "modal-success" )){
					callAction("listaUtenti.do");
				}*/ 		
  			}); 
  		</script>
	</jsp:attribute> 
</t:layout> 
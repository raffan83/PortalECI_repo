<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% pageContext.setAttribute("newLineChar", "\r\n"); %>
<% pageContext.setAttribute("newLineChar2", "\n"); %>

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
       <%--  Lista Attrezzature con ${tipo_scadenza } in scadenza il <fmt:formatDate pattern = "dd/MM/yyyy" value = "${data_scadenza }" /> --%>      
       Lista Attrezzature con ${tipo_scadenza } in scadenza il  ${data_scadenza }
        <small></small>
      </h1>
      <br><br>
       <a class="btn btn-default pull-right" style="margin-right:5px;"  href="scadenzarioAttrezzature.do" ><i class="fa fa-arrow-left"></i> Torna allo scadenzario</a>
    </section>
    <div style="clear: both;"></div>    
  <!-- Main content -->
    <section class="content">

 <div class="box">
          <div class="box-header">

<div class="row">
<div class="col-lg-12">

<div id="posTab">
	<table id="tabPM" class="table table-bordered table-hover dataTable table-striped" role="grid" width="100%">
<thead><tr class="active">
 					
 						<th>ID</th> 						  
            	       <th>N. matricola INAIL</th>		   
            		   <th>N. di fabbrica</th>
                       <th>Tipo attività</th>
                       <th>Descrizione</th>
                       <th>Cliente</th>
                       <th>Sede</th>
                       <th>Data Verifica Funzionamento</th>
                       <th>Data Prossima Verifica Funzionamento</th>
                       <th>Data Verifica Integrità</th>
                       <th>Data Prossima Verifica Integrità</th>
                       <th>Data Verifica Interna</th>
                       <th>Data Prossima Verifica Interna</th>         
                       <th>Anno di costruzione</th>
                       <th>Fabbricante</th>
                       <th>Modello</th>
                       <th>Settore d'impiego</th>
                       <th>Note tecniche</th>
                       <th>Note generiche</th>       
                                   
                       <td style="min-width:100px;">Azioni</td>
 </tr></thead>
 
 <tbody>
 	<c:forEach items="${lista_attrezzature}" var="attrezzatura" varStatus="loop">
 	<tr>
 	<td>${attrezzatura.id }</td>
 	<td>${attrezzatura.matricola_inail }</td>
 	<td>${attrezzatura.numero_fabbrica }</td>
 	<td>${attrezzatura.tipo_attivita }</td>
 	<td>${attrezzatura.descrizione }</td>
 	<td>${attrezzatura.nome_cliente }</td>
 	<td>${attrezzatura.nome_sede }</td>
 	<td><fmt:formatDate pattern = "dd/MM/yyyy" value = "${attrezzatura.data_verifica_funzionamento }" /></td>
 	<td><fmt:formatDate pattern = "dd/MM/yyyy" value = "${attrezzatura.data_prossima_verifica_funzionamento }" /></td>
 	<td><fmt:formatDate pattern = "dd/MM/yyyy" value = "${attrezzatura.data_verifica_integrita }" /></td>
 	<td><fmt:formatDate pattern = "dd/MM/yyyy" value = "${attrezzatura.data_prossima_verifica_integrita }" /></td>
 	<td><fmt:formatDate pattern = "dd/MM/yyyy" value = "${attrezzatura.data_verifica_interna }" /></td>
 	<td><fmt:formatDate pattern = "dd/MM/yyyy" value = "${attrezzatura.data_prossima_verifica_interna }" /></td> 	
 	<td>${attrezzatura.anno_costruzione }</td>
  	<td>${attrezzatura.fabbricante }</td>
 	<td>${attrezzatura.modello }</td>
 	<td>${attrezzatura.settore_impiego }</td>
 	<td>${attrezzatura.note_tecniche }</td> 	
 	<td>${attrezzatura.note_generiche }</td> 
 	<td><a class="btn btn-warning" onClick="modalModificaAttrezzatura('${attrezzatura.id }','${attrezzatura.matricola_inail }','${attrezzatura.numero_fabbrica }','${attrezzatura.tipo_attivita }','${attrezzatura.descrizione }','${attrezzatura.id_cliente }','${attrezzatura.id_sede }',
 	'${attrezzatura.data_verifica_funzionamento }','${attrezzatura.data_prossima_verifica_funzionamento }','${attrezzatura.data_verifica_integrita }','${attrezzatura.data_prossima_verifica_integrita }','${attrezzatura.data_verifica_interna }','${attrezzatura.data_prossima_verifica_interna }',
 	'${attrezzatura.anno_costruzione }','${attrezzatura.fabbricante }','${attrezzatura.modello }','${attrezzatura.settore_impiego }','${fn:replace(fn:replace(attrezzatura.note_tecniche.replace('\'',' ').replace('\\','/'),newLineChar, ' '),newLineChar2,' ')}','${fn:replace(fn:replace(attrezzatura.note_generiche.replace('\'',' ').replace('\\','/'),newLineChar, ' '),newLineChar2,' ')}')"><i class="fa fa-edit"></i></a></td>
 	
 	</tr>
 	</c:forEach>
 
</tbody>


</table>
</div>


</div>
</div>
</div>
</div>

 <form class="form-horizontal" id="formNuovaAttrezzatura">
<div id="modalNuovaAttrezzatura" class="modal fade" role="dialog" aria-labelledby="myLargeModalLabel">
    <div class="modal-dialog modal-md" role="document">
    <div class="modal-content">
     <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Nuova Attrezzatura</h4>
      </div>
       <div class="modal-body">

       
        
         <div class="form-group">
          <label for="inputEmail" class="col-sm-4 control-label">Cliente:</label>

         <div class="col-sm-8">
          <select name="cliente" id="cliente" data-placeholder="Seleziona Cliente..."  class="form-control select2" aria-hidden="true" data-live-search="true" style="width:100%">
                  <option value=""></option>
                      <c:forEach items="${listaClienti}" var="cliente">
                           <option value="${cliente.__id}">${cliente.nome} </option> 
                     </c:forEach>
                  </select>
    	</div>
   </div>
    <div class="form-group">
          <label for="inputEmail" class="col-sm-4 control-label">Sede:</label>

         <div class="col-sm-8">
       <select name="sede" id="sede" data-placeholder="Seleziona Sede..."  disabled class="form-control select2" aria-hidden="true" data-live-search="true" style="width:100%">
                   <option value=""></option>
             		<c:forEach items="${listaSedi}" var="sedi">
                        <option value="${sedi.__id}_${sedi.id__cliente_}">${sedi.descrizione} - ${sedi.indirizzo} - ${sedi.comune} (${sedi.siglaProvincia})</option>              
                     	</c:forEach>
                  </select>
    	</div>
   </div>
              

    <div class="form-group">
          <label for="inputEmail" class="col-sm-4 control-label">Numero matricola INAIL:</label>

         <div class="col-sm-8">
         <input class="form-control" id="matricola_inail" type="text" name="matricola_inail" required value=""/>
    	</div>
   </div>
	<div class="form-group">
          <label for="inputEmail" class="col-sm-4 control-label">Numero di fabbrica:</label>

         <div class="col-sm-8">
         <input class="form-control" id="numero_fabbrica" type="text" name="numero_fabbrica" required value=""/>
    	</div>
   </div>

   <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Descrizione:</label>
        <div class="col-sm-8">
                      <input class="form-control" id="descrizione" type="text" name="descrizione" required value=""/>
    </div>
    </div>
    
    <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Tipo Attività:</label>
        <div class="col-sm-8">
                      <input class="form-control" id="tipo_attivita" type="text" name=tipo_attivita required value=""/>
    </div>
    </div>

         <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Data verifica funzionamento:</label>
        <div class="col-sm-8">
                      <input class="form-control datepicker" id="data_verifica_funzionamento" type="text" name="data_verifica_funzionamento"  value="" data-date-format="dd/mm/yyyy"/>
    </div>
       </div> 
       
         <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Data prossima verifica funzionamento:</label>
        <div class="col-sm-8">
                      <input class="form-control datepicker" id="data_prossima_verifica_funzionamento" type="text" name="data_prossima_verifica_funzionamento"  value="" data-date-format="dd/mm/yyyy"/>
    </div>
       </div> 
       
             <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Data verifica integrità:</label>
        <div class="col-sm-8">
                      <input class="form-control datepicker" id="data_verifica_integrita" type="text" name="data_verifica_integrita"  value="" data-date-format="dd/mm/yyyy"/>
    </div>
       </div> 
       
         <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Data prossima verifica integrità:</label>
        <div class="col-sm-8">
                      <input class="form-control datepicker" id="data_prossima_verifica_integrita" type="text" name="data_prossima_verifica_integrita"  value="" data-date-format="dd/mm/yyyy"/>
    </div>
       </div>
             <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Data verifica interna:</label>
        <div class="col-sm-8">
                      <input class="form-control datepicker" id="data_verifica_interna" type="text" name="data_verifica_interna"  value="" data-date-format="dd/mm/yyyy"/>
    </div>
       </div> 
       
         <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Data prossima verifica interna:</label>
        <div class="col-sm-8">
                      <input class="form-control datepicker" id="data_prossima_verifica_interna" type="text" name="data_prossima_verifica_interna"  value="" data-date-format="dd/mm/yyyy"/>
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

  <form class="form-horizontal" id="formModificaAttrezzatura">
<div id="modalModificaAttrezzatura" class="modal fade" role="dialog" aria-labelledby="myLargeModalLabel">
    <div class="modal-dialog modal-md" role="document">
    <div class="modal-content">
     <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Modifica Attrezzatura</h4>
      </div>
       <div class="modal-body">

      
        
         <div class="form-group">
          <label for="inputEmail" class="col-sm-4 control-label">Cliente:</label>

         <div class="col-sm-8">
          <select name="cliente_mod" id="cliente_mod" data-placeholder="Seleziona Cliente..."  class="form-control select2" aria-hidden="true" data-live-search="true" style="width:100%">
                  <option value=""></option>
                      <c:forEach items="${listaClienti}" var="cliente">
                           <option value="${cliente.__id}">${cliente.nome} </option> 
                     </c:forEach>
                  </select>
    	</div>
   </div>
    <div class="form-group">
          <label for="inputEmail" class="col-sm-4 control-label">Sede:</label>

         <div class="col-sm-8">
       <select name="sede_mod" id="sede_mod" data-placeholder="Seleziona Sede..."  disabled class="form-control select2" aria-hidden="true" data-live-search="true" style="width:100%">
                   <option value=""></option>
             		<c:forEach items="${listaSedi}" var="sedi">
                        <option value="${sedi.__id}_${sedi.id__cliente_}">${sedi.descrizione} - ${sedi.indirizzo} - ${sedi.comune} (${sedi.siglaProvincia})</option>              
                     	</c:forEach>
                  </select>
    	</div>
   </div>
              

    <div class="form-group">
          <label for="inputEmail" class="col-sm-4 control-label">Numero matricola INAIL:</label>

         <div class="col-sm-8">
         <input class="form-control" id="matricola_inail_mod" type="text" name="matricola_inail_mod" required value=""/>
    	</div>
   </div>
	<div class="form-group">
          <label for="inputEmail" class="col-sm-4 control-label">Numero di fabbrica:</label>

         <div class="col-sm-8">
         <input class="form-control" id="numero_fabbrica_mod" type="text" name="numero_fabbrica_mod" required value=""/>
    	</div>
   </div>

   <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Descrizione:</label>
        <div class="col-sm-8">
                      <input class="form-control" id="descrizione_mod" type="text" name="descrizione_mod" required value=""/>
    </div>
    </div>
    <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Anno di costruzione:</label>
        <div class="col-sm-8">
                      <input class="form-control" id="anno_costruzione_mod" type="number" name="anno_costruzione_mod"  value=""/>
    </div>
    </div>
    <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Fabbricante:</label>
        <div class="col-sm-8">
                      <input class="form-control" id="fabbricante_mod" type="text" name="fabbricante_mod"  value=""/>
    </div>
    </div>
    <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Modello:</label>
        <div class="col-sm-8">
                      <input class="form-control" id="modello_mod" type="text" name="modello_mod"  value=""/>
    </div>
    </div>
    <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Settore d'impiego:</label>
        <div class="col-sm-8">
                      <input class="form-control" id="settore_impiego_mod" type="text" name="settore_impiego_mod"  value=""/>
    </div>
    </div>
    <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Tipo Attività:</label>
        <div class="col-sm-8">
                      <input class="form-control" id="tipo_attivita_mod" type="text" name="tipo_attivita_mod" required value=""/>
    </div>
    </div>

         <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Data verifica funzionamento:</label>
        <div class="col-sm-8">
                      <input class="form-control datepicker" id="data_verifica_funzionamento_mod" type="text" name="data_verifica_funzionamento_mod"  value="" data-date-format="dd/mm/yyyy"/>
    </div>
       </div> 
       
         <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Data prossima verifica funzionamento:</label>
        <div class="col-sm-8">
                      <input class="form-control datepicker" id="data_prossima_verifica_funzionamento_mod" type="text" name="data_prossima_verifica_funzionamento_mod"  value="" data-date-format="dd/mm/yyyy"/>
    </div>
       </div> 
       
             <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Data verifica integrità:</label>
        <div class="col-sm-8">
                      <input class="form-control datepicker" id="data_verifica_integrita_mod" type="text" name="data_verifica_integrita_mod"  value="" data-date-format="dd/mm/yyyy"/>
    </div>
       </div> 
       
         <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Data prossima verifica integrità:</label>
        <div class="col-sm-8">
                      <input class="form-control datepicker" id="data_prossima_verifica_integrita_mod" type="text" name="data_prossima_verifica_integrita_mod"  value="" data-date-format="dd/mm/yyyy"/>
    </div>
       </div>
             <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Data verifica interna:</label>
        <div class="col-sm-8">
                      <input class="form-control datepicker" id="data_verifica_interna_mod" type="text" name="data_verifica_interna_mod"  value="" data-date-format="dd/mm/yyyy"/>
    </div>
       </div> 
       
         <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Data prossima verifica interna:</label>
        <div class="col-sm-8">
                      <input class="form-control datepicker" id="data_prossima_verifica_interna_mod" type="text" name="data_prossima_verifica_interna_mod"  value="" data-date-format="dd/mm/yyyy"/>
    </div>
       </div>
 <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Note tecniche:</label>
        <div class="col-sm-8">
                      <textarea class="form-control" id="note_tecniche_mod" name="note_tecniche_mod" rows ="3"></textarea>
    </div>
    </div>
    <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Note generiche:</label>
        <div class="col-sm-8">
                      <textarea class="form-control" id="note_generiche_mod" name="note_generiche_mod" rows ="3"></textarea>
    </div>
    </div>
  		 </div>
      <div class="modal-footer">
      
      <input type="hidden" id="id_attrezzatura" name="id_attrezzatura">
      
		<button type="submit" class="btn btn-primary pull-right" >Salva</button>
      </div>
    </div>
  </div>
</div>
 </form>




  <div id="myModal" class="modal fade" role="dialog" aria-labelledby="myLargeModalLabel">
    <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
     <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Strumento</h4>
      </div>
       <div class="modal-body">

        <div class="nav-tabs-custom">
            <ul class="nav nav-tabs">
              <li class="active"><a href="#dettaglio" data-toggle="tab" aria-expanded="true" onclick="" id="dettaglioTab">Dettaglio Strumento</a></li>
              <li class=""><a href="#misure" data-toggle="tab" aria-expanded="false" onclick="" id="misureTab">Misure</a></li>
       <!--        <li class=""><a href="#prenotazione" data-toggle="tab" aria-expanded="false" onclick="" id="prenotazioneTab">Stato Prenotazione</a></li> -->
        
 		<c:if test="${userObj.checkPermesso('MODIFICA_STRUMENTO_METROLOGIA')}">
               <li class=""><a href="#modifica" data-toggle="tab" aria-expanded="false" onclick="" id="modificaTab">Modifica Strumento</a></li>
		</c:if>		
		 <li class=""><a href="#documentiesterni" data-toggle="tab" aria-expanded="false" onclick="" id="documentiesterniTab">Documenti esterni</a></li>
             </ul>
            <div class="tab-content">
              <div class="tab-pane active" id="dettaglio">

    			</div> 

              <!-- /.tab-pane -->
             
			  <div class="tab-pane" id="misure">
                

         
			 </div> 


              <!-- /.tab-pane -->


               		<c:if test="${userObj.checkPermesso('MODIFICA_STRUMENTO_METROLOGIA')}">
              
              			<div class="tab-pane" id="modifica">
              

              			</div> 
              		</c:if>		
              		
              		<div class="tab-pane" id="documentiesterni">
              

              			</div> 
              
            </div>
            <!-- /.tab-content -->
          </div>
    
        
        
        
        
  		<div id="empty" class="testo12"></div>
  		 </div>
      <div class="modal-footer">
       <!--  <button type="button" class="btn btn-primary" onclick="approvazioneFromModal('app')"  >Approva</button>
        <button type="button" class="btn btn-danger"onclick="approvazioneFromModal('noApp')"   >Non Approva</button> -->
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
								<div id="myModalErrorContent">				
								</div>	   
								<div id="empty" class="testo12"></div>
 							</div>
							<div class="modal-footer">
      								<button type="button" class="btn btn-outline" data-dismiss="modal">Chiudi</button>
							</div>
						</div>
					</div>
				</div> 


<div id="modalEliminaDocumentoEsternoStrumento" class="modal fade" role="dialog" aria-labelledby="myLargeModalLabel">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
    
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Attenzione</h4>
      </div>
    <div class="modal-content">
       <div class="modal-body" id="">
		     
			<input class="form-control" id="idElimina" name="idElimina" value="" type="hidden" />
		
			Sei Sicuro di voler eliminare il documento?
        
        
  		 </div>
      
    </div>
    <div class="modal-footer">
    	<button type="button" class="btn btn-default pull-left" data-dismiss="modal">Annulla</button>
    	<button type="button" class="btn btn-danger" onClick="eliminaDocumentoEsternoStrumento()">Elimina</button>
    </div>
  </div>
    </div>

</div>

 
 
  <div  class="modal"><!-- Place at bottom of page --></div> 
   <div id="modal1"><!-- Place at bottom of page --></div> 
   
    </section>
    <!-- /.content -->
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

<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/datejs/1.0/date.min.js"></script>
<script type="text/javascript">

var columsDatatables = [];

$("#tabPM").on( 'init.dt', function ( e, settings ) {

    var api = new $.fn.dataTable.Api( settings );
    var state = api.state.loaded();
 
    if(state != null && state.columns!=null){
    		console.log(state.columns);
    
    columsDatatables = state.columns;
    }
    $('#tabPM thead th').each( function () {
     	if(columsDatatables.length==0 || columsDatatables[$(this).index()]==null ){columsDatatables.push({search:{search:""}});}
    	   var title = $('#tabPM thead th').eq( $(this).index() ).text();
    	   if($(this).index()!= 0){
    		   $(this).append( '<div><input class="inputsearchtable" id="inputsearchtable_'+$(this).index()+'" style="width:100%" type="text" value="'+columsDatatables[$(this).index()].search.search+'" /></div>');
    	   }
    	  
    	} );

} );

$('#formNuovaAttrezzatura').on('submit',function(e){
    e.preventDefault();
	
	nuovaAttrezzatura();
});

$('#formModificaAttrezzatura').on('submit',function(e){
    e.preventDefault();
	
    modificaAttrezzatura(true, formatDate("${data_scadenza}"), "${tipo_scadenza.replace(' ','_').replace('à','a')}");
    $('#modalModificaAttrezzatura').modal('hide');
});
	 	
	 	
function formatDate(data){
	
	var dateParts = data.split("/");

	   var mydate = new Date(+dateParts[2], dateParts[1] - 1, +dateParts[0]); 
	   var str;
	   if(!isNaN(mydate.getTime())){
	   
		   str = mydate.toString("yyyy-MM-dd");
	   }			   
	   return str;	 		
}

function modalModificaAttrezzatura(id_attrezzatura, matricola_inail, numero_fabbrica, tipo_attivita, descrizione, id_cliente, id_sede,
		data_verifica_funzionamento, data_prossima_verifica_funzionamento,data_verifica_integrita, data_prossima_verifica_integrita, data_verifica_interna, data_prossima_verifica_interna,
		anno_costruzione, fabbricante, modello, settore_impiego, note_tecniche, note_generiche){
	
	$('#id_attrezzatura').val(id_attrezzatura)
	$('#matricola_inail_mod').val(matricola_inail);
	$('#numero_fabbrica_mod').val(numero_fabbrica);
	$('#tipo_attivita_mod').val(tipo_attivita);
	$('#descrizione_mod').val(descrizione);
	$('#cliente_mod').val(id_cliente);
	$('#cliente_mod').change();
	if(id_sede!=0){
		$('#sede_mod').val(id_sede+"_"+id_cliente);	
	}else{
		$('#sede_mod').val(0);
	}
	
	$('#sede_mod').change();
	
	$('#fabbricante_mod').val(fabbricante);
	$('#modello_mod').val(modello);
	$('#settore_impiego_mod').val(settore_impiego);
	$('#note_tecniche_mod').val(note_tecniche);
	$('#note_generiche_mod').val(note_generiche);	
	
	if(data_verifica_funzionamento!=null && data_verifica_funzionamento!= ''){
		$('#data_verifica_funzionamento_mod').val(Date.parse(data_verifica_funzionamento).toString("dd/MM/yyyy"));	
	}
	if(data_prossima_verifica_funzionamento!=null && data_prossima_verifica_funzionamento!= ''){
		$('#data_prossima_verifica_funzionamento_mod').val(Date.parse(data_prossima_verifica_funzionamento).toString("dd/MM/yyyy"));	
	}
	if(data_verifica_integrita!=null && data_verifica_integrita!= ''){
		$('#data_verifica_integrita_mod').val(Date.parse(data_verifica_integrita).toString("dd/MM/yyyy"));	
	}
	if(data_prossima_verifica_integrita!=null && data_prossima_verifica_integrita!= ''){
		$('#data_prossima_verifica_integrita_mod').val(Date.parse(data_prossima_verifica_integrita).toString("dd/MM/yyyy"));	
	}
	if(data_verifica_interna!=null && data_verifica_interna!= ''){
		$('#data_verifica_interna_mod').val(Date.parse(data_verifica_interna).toString("dd/MM/yyyy"));	
	}
	if(data_prossima_verifica_interna!=null && data_prossima_verifica_interna!= ''){
		$('#data_prossima_verifica_interna_mod').val(Date.parse(data_prossima_verifica_interna).toString("dd/MM/yyyy"));	
	}
	
	$('#id_attrezzatura').val(id_attrezzatura);	
	
	$('#data_prossima_verifica_funzionamento_mod').datepicker({
			format: "dd/MM/yyyy"
	});
	$('#modalModificaAttrezzatura').modal();
	
}



$(document).ready(function() {
	
	$('.datepicker').datepicker();
	$('.select2').select2();
	
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
	      stateSave: true,
	      order:[[0, "desc"]],
	      columnDefs: [
					 
	                   { responsivePriority: 1, targets: 0 },	                  
	                   { responsivePriority: 2, targets: 19 }
	              
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
  .appendTo( '#tabPM_wrapper .col-sm-6:eq(1)' );
	
	
	table.buttons().container().appendTo( '#tabPM_wrapper .col-sm-6:eq(1)');
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
	
	
});


$("#cliente").change(function() {
    
	  if ($(this).data('options') == undefined) 
	  {
	    /*Taking an array of all options-2 and kind of embedding it on the select1*/
	    $(this).data('options', $('#sede option').clone());
	  }
	  
	  var id = $(this).val();
	 
	  var options = $(this).data('options');

	  var opt=[];
	
	  opt.push("<option value = 0>Non Associate</option>");

	   for(var  i=0; i<options.length;i++)
	   {
		var str=options[i].value; 
	
		if(str.substring(str.indexOf("_")+1,str.length)==id)
		{
			opt.push(options[i]);
		}   
	   }
	 $("#sede").prop("disabled", false);
	 
	  $('#sede').html(opt);

		$("#sede").change();  

	
	});
	
$("#cliente_mod").change(function() {
    
	  if ($(this).data('options') == undefined) 
	  {
	    /*Taking an array of all options-2 and kind of embedding it on the select1*/
	    $(this).data('options', $('#sede_mod option').clone());
	  }
	  
	  var id = $(this).val();
	 
	  var options = $(this).data('options');

	  var opt=[];
	
	  opt.push("<option value = 0>Non Associate</option>");

	   for(var  i=0; i<options.length;i++)
	   {
		var str=options[i].value; 
	
		if(str.substring(str.indexOf("_")+1,str.length)==id)
		{
			opt.push(options[i]);
		}   
	   }
	 $("#sede_mod").prop("disabled", false);
	 
	  $('#sede_mod').html(opt);

		$("#sede_mod").change();  

	
	});



    
    
  </script>
</jsp:attribute> 
</t:layout>

 
 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page import="it.portalECI.DTO.UtenteDTO"%>
<% pageContext.setAttribute("newLineChar", "\r\n"); %>
<% pageContext.setAttribute("newLineChar2", "\n"); %>
<%
	UtenteDTO user = (UtenteDTO)request.getSession().getAttribute("userObj");
	request.setAttribute("user",user);
%>

    <div class="row">
<div class="col-lg-12">
<!-- <button class="btn btn-primary" onClick="nuovoInterventoFromModal('#modalNuovaAttrezzatura')">Nuova Attrezzatura</button> -->
<c:if test="${user.checkPermesso('MODIFICA_ATTREZZATURE') }">
<button class="btn btn-primary" onClick="modalNuovaAttrezzatura()">Nuova Attrezzatura</button>
</c:if>

<button class="btn btn-primary pull-right"  id="btnObsolete" >Obsolete</button>
<button class="btn btn-primary pull-right"  style="margin-right:5px" id="btnInVerifica" disabled>In verifica</button>
<button class="btn btn-primary pull-right"  style="margin-right:5px" id="btnTutte">Tutte</button>
<div id="errorMsg" ></div>
</div>
</div><br>


<div class="row">
<div class="col-lg-12">

            		

	<table id="tableAttr" class="table table-bordered table-hover dataTable table-striped" role="grid" width="100%">
	<thead>

	
	<c:if test="${!user.checkRuolo('CLVAL') }">
<tr class="active">
 					
 						<th>ID</th> 		
 						 <th hidden="hidden"></th>  				  
            	       <th>N. matricola INAIL</th>		   
            		   <th>N. di fabbrica</th>
                       <th>Gruppo</th>
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
                       <th>Tipo Attrezzatura</th>
                       <th>Tipo Attrezzatura GVR</th>
                       <th>ID Specifica</th>
                       <th>Sogg. messa servizio GVR</th>
                       <th>N. panieri idroestrattori</th>
                       <th>Marcatura</th>
                       <th>N. ID ON</th>
                       <th>Scadenza Ventennale</th>
                       <th>Codici Milestone</th>
                       <th>Note tecniche</th>
                       <th>Note generiche</th>     
                      
                       <td style="min-width:100px;">Azioni</td>
                       
 </tr>
 </c:if>
 
 	<c:if test="${user.checkRuolo('CLVAL') }">
	<tr class="active">
	<th>ID</th> 		
 						 <th hidden="hidden"></th>  				  
            	       <th>N. matricola INAIL</th>		   
            		   <th>N. di fabbrica</th>
                     
                   
                       <th>Data Verifica Funzionamento</th>
                       <th>Data Prossima Verifica Funzionamento</th>
                       <th>Data Verifica Integrità</th>
                       <th>Data Prossima Verifica Integrità</th>
                       <th>Data Verifica Interna</th>
                       <th>Data Prossima Verifica Interna</th>     
                       <th>Anno di costruzione</th>
                       <th>Fabbricante</th>
                     
                      
                         
                      
                       <td style="min-width:100px;">Azioni</td>
	</tr>
	</c:if>
 </thead>
 <tbody>
 <c:if test="${!user.checkRuolo('CLVAL') }">
 	<c:forEach items="${lista_attrezzature}" var="attrezzatura" varStatus="loop">
 	<c:if test="${attrezzatura.id_insieme==null }">
 	<c:choose>
 	<c:when test="${attrezzatura.obsoleta==0}">
 		<tr  onDblClick="openTab('${attrezzatura.id }','${attrezzatura.matricola_inail }','${attrezzatura.numero_fabbrica }','${attrezzatura.tipo_attivita }','${attrezzatura.descrizione }','${attrezzatura.id_cliente }','${attrezzatura.id_sede }',
 	'${attrezzatura.data_verifica_funzionamento }','${attrezzatura.data_prossima_verifica_funzionamento }','${attrezzatura.data_verifica_integrita }','${attrezzatura.data_prossima_verifica_integrita }','${attrezzatura.data_verifica_interna }','${attrezzatura.data_prossima_verifica_interna }',
 	'${attrezzatura.anno_costruzione }','${attrezzatura.fabbricante }','${attrezzatura.modello }','${attrezzatura.settore_impiego }','${fn:replace(fn:replace(attrezzatura.note_tecniche.replace('\'',' ').replace('\\','/'),newLineChar, ' '),newLineChar2, ' ')}','${fn:replace(fn:replace(attrezzatura.note_generiche.replace('\'',' ').replace('\\','/').replace('\\n',' '),newLineChar, ' '),newLineChar2,' ')}','${attrezzatura.obsoleta }',
 	'${attrezzatura.tipo_attrezzatura }','${attrezzatura.tipo_attrezzatura_GVR }','${attrezzatura.ID_specifica }','${attrezzatura.sogg_messa_serv_GVR }',
 	'${attrezzatura.n_panieri_idroestrattori }','${attrezzatura.marcatura }','${attrezzatura.n_id_on }')" >
 	
 	
 	
 	
 	</c:when>
 	<c:otherwise>
 		<tr style="background-color:#ff6666">
 	</c:otherwise>
 	</c:choose>

 	<td>${attrezzatura.id }</td>
 	<td hidden="hidden">${attrezzatura.obsoleta }</td>
 	<td>${attrezzatura.matricola_inail }</td>
 	<td>${attrezzatura.numero_fabbrica }</td>
 	<td>${attrezzatura.tipo_attivita }</td>
 	<td>${attrezzatura.descrizione }</td>
 		<td>${attrezzatura.nome_cliente }</td>
 	<c:if test="${attrezzatrua.nome_sede == null || attrezzatura.nome_sede == '' }">
 	<td>${attrezzatura.nome_cliente } - ${attrezzatura.indirizzo} - ${attrezzatura.comune} (${attrezzatura.provincia})</td>
 	</c:if>	
 	<c:if test="${attrezzatrua.nome_sede != null && attrezzatura.nome_sede != '' }">
 	<td>${attrezzatura.nome_sede } - ${attrezzatura.indirizzo} - ${attrezzatura.comune} (${attrezzatura.provincia})</td>
 	</c:if>	
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
 	<td>${attrezzatura.tipo_attrezzatura }</td>
 	<td>${attrezzatura.tipo_attrezzatura_GVR }</td>
 	<td>${attrezzatura.ID_specifica }</td>
 	<td>${attrezzatura.sogg_messa_serv_GVR }</td>
 	<td>${attrezzatura.n_panieri_idroestrattori }</td>
 	<td>${attrezzatura.marcatura }</td>
 	<td>${attrezzatura.n_id_on }</td>
 	<td>${attrezzatura.data_scadenza_ventennale}</td>
 	<td>${attrezzatura.codice_milestone }</td>
 	<td>${attrezzatura.note_tecniche }</td> 	
 	<td>${attrezzatura.note_generiche }</td> 
 	<td>
 	<c:if test="${user.checkPermesso('MODIFICA_ATTREZZATURE') }">	
 	<a class="btn btn-warning" onClick="modalModificaAttrezzatura('${attrezzatura.id }','${attrezzatura.matricola_inail }','${attrezzatura.numero_fabbrica }','${attrezzatura.tipo_attivita }','${attrezzatura.descrizione }','${attrezzatura.id_cliente }','${attrezzatura.id_sede }',
 	'${attrezzatura.data_verifica_funzionamento }','${attrezzatura.data_prossima_verifica_funzionamento }','${attrezzatura.data_verifica_integrita }','${attrezzatura.data_prossima_verifica_integrita }','${attrezzatura.data_verifica_interna }','${attrezzatura.data_prossima_verifica_interna }',
 	'${attrezzatura.anno_costruzione }','${attrezzatura.fabbricante }','${attrezzatura.modello }','${attrezzatura.settore_impiego }','${fn:replace(fn:replace(attrezzatura.note_tecniche.replace('\'',' ').replace('\\','/'),newLineChar, ' '),newLineChar2, ' ')}','${fn:replace(fn:replace(attrezzatura.note_generiche.replace('\'',' ').replace('\\','/').replace('\\n',' '),newLineChar, ' '),newLineChar2,' ')}','${attrezzatura.obsoleta }',
 	'${attrezzatura.tipo_attrezzatura }','${attrezzatura.tipo_attrezzatura_GVR }','${attrezzatura.ID_specifica }','${attrezzatura.sogg_messa_serv_GVR }',
 	'${attrezzatura.n_panieri_idroestrattori }','${attrezzatura.marcatura }','${attrezzatura.n_id_on }','${attrezzatura.data_scadenza_ventennale }', '${attrezzatura.codice_milestone }')"><i class="fa fa-edit"></i></a>
 	</c:if>
 	</td>
 	
 	
 	
 	</tr>
 	 </c:if>
 	

 	
 	</c:forEach>
 	
 	</c:if>
  	
 	<c:if test="${user.checkRuolo('CLVAL') }">
	 	<c:forEach items="${lista_attrezzature}" var="attrezzatura" varStatus="loop">
 	<c:choose>
 	<c:when test="${attrezzatura.obsoleta==0}">
 		<tr>
 	
 	
 	</c:when>
 	<c:otherwise>
 		<tr style="background-color:#ff6666">
 	</c:otherwise>
 	</c:choose>
 
 	<td>${attrezzatura.id }</td>
 	<td hidden="hidden">${attrezzatura.obsoleta }</td>
 	<td>${attrezzatura.matricola_inail }</td>
 	<td>${attrezzatura.numero_fabbrica }</td>

  	
 	<td><fmt:formatDate pattern = "dd/MM/yyyy" value = "${attrezzatura.data_verifica_funzionamento }" /></td>
 	<td><fmt:formatDate pattern = "dd/MM/yyyy" value = "${attrezzatura.data_prossima_verifica_funzionamento }" /></td>
 	<td><fmt:formatDate pattern = "dd/MM/yyyy" value = "${attrezzatura.data_verifica_integrita }" /></td>
 	<td><fmt:formatDate pattern = "dd/MM/yyyy" value = "${attrezzatura.data_prossima_verifica_integrita }" /></td>
 	<td><fmt:formatDate pattern = "dd/MM/yyyy" value = "${attrezzatura.data_verifica_interna }" /></td>
 	<td><fmt:formatDate pattern = "dd/MM/yyyy" value = "${attrezzatura.data_prossima_verifica_interna }" /></td>
	<td>${attrezzatura.anno_costruzione }</td>
  	<td>${attrezzatura.fabbricante }</td>

 	
 	
 	<td>
	<a class="btn btn-info" onClick="openVerbaliModal('${attrezzatura.id}')"><i class="fa fa-search"></i></a>
 	</td>
 	
 	
 	
 	</tr>
 	
 	

 	
 	</c:forEach>

	
	</c:if>
</tbody>


</table>



</div>
</div>

 <form class="form-horizontal" id="formNuovaAttrezzatura">
<div id="modalNuovaAttrezzatura" class="modal fade" role="dialog" aria-labelledby="myLargeModalLabel" style="z-index:9999">
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
          <label for="inputEmail" class="col-sm-4 control-label">Numero matricola INAIL: <span id="label_pattern" style="display:none"><font style="color:red">Pattern matricola errato!</font></span></label>
			
         <div class="col-sm-8">
         <input class="form-control" id="matricola_inail" type="text" name="matricola_inail" placeholder="0000/0/00000/AA" required value=""/>
    	</div>
   </div>
	<div class="form-group">
          <label for="inputEmail" class="col-sm-4 control-label">Numero di fabbrica:</label>

         <div class="col-sm-8">
         <input class="form-control" id="numero_fabbrica" type="text" name="numero_fabbrica" required value=""/>
    	</div>
   </div>

   <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Gruppo:</label>
        <div class="col-sm-8">
                      <!-- <select class="form-control" id="tipo_attivita" type="text" name=tipo_attivita required value=""/></ -->
         <select class="form-control select2" required id="tipo_attivita" name="tipo_attivita" data-placeholder="Seleziona Gruppo..." style="width:100%">
         <option value=""></option>
         <option value="SP">SP</option>
         <option value="SC">SC</option>
         <option value="GVR">GVR</option>
         </select>             
    </div>
    </div>

   <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Descrizione:</label>
        <div class="col-sm-8">
                     <!--  <input class="form-control" id="descrizione" type="text" name="descrizione" required value=""/> -->
        <select class="form-control select2" disabled id="descrizione" name="descrizione" data-placeholder="Seleziona Descrizione..." style="width:100%">
         <option value=""></option>
        <c:forEach items="${lista_descrizioni_gruppi }" var="desc" varStatus="loop">
        <%-- <option value="${desc.gruppo}_${desc.descrizione}_${desc.id_specifica}">${desc.descrizione }</option> --%>
        <option value="${desc.gruppo}_${desc.descrizione}_${desc.id_specifica}@${desc.codici_milestone}">${desc.descrizione }</option>
        
        </c:forEach>
         </select>  
    </div>
    </div>
    <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Anno di costruzione:</label>
        <div class="col-sm-8">
                      <input class="form-control" id="anno_costruzione" type="number" name="anno_costruzione"  value=""/>
    </div>
    </div>
    <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Fabbricante:</label>
        <div class="col-sm-8">
                      <input class="form-control" id="fabbricante" type="text" name="fabbricante"  value=""/>
    </div>
    </div>
    <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Modello:</label>
        <div class="col-sm-8">
                      <input class="form-control" id="modello" type="text" name="modello"  value=""/>
    </div>
    </div>
    <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Settore d'impiego:</label>
        <div class="col-sm-8">
                     <!--  <input class="form-control" id="settore_impiego" type="text" name="settore_impiego"  value=""/> -->
          <select class="form-control select2" disabled id="settore_impiego" name="settore_impiego" data-placeholder="Seleziona settore d'impiego..." style="width:100%">
		<option value=""></option>
	
              
          </select>     
    </div>
    </div>
    
    
        <div class="form-group" id="form_group_id">
        <label for="inputName" class="col-sm-4 control-label">Tipo attrezzatura:</label>
        <div class="col-sm-8">
                     
          <select class="form-control select2" disabled id="tipo_attrezzatura" name="tipo_attrezzatura" data-placeholder="Seleziona tipo attrezzatura..." style="width:100%">
		<option value=""></option>   
	
          </select>     
    </div>
    </div>
    
            <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Tipo attrezzatura GVR:</label>
        <div class="col-sm-8">
                     
          <select class="form-control select2" disabled id="tipo_attrezzatura_gvr" name="tipo_attrezzatura_gvr" data-placeholder="Seleziona tipo attrezzatura GVR..." style="width:100%">
		<option value=""></option>              		

          </select>     
    </div>
    </div>
    
  <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">ID specifica:</label>
        <div class="col-sm-8">
                     
         
            <input class="form-control" id="id_specifica" type="text" name="id_specifica" readonly  value=""/>
		
    
    </div>
    </div>
    
    
     <div class="form-group" id="sogg_messa_serv_GVR_content">
        <label for="inputName" class="col-sm-4 control-label">Sogg. messa servizio GVR:</label>
        <div class="col-sm-8">
                     
          <select class="form-control select2"  id="sogg_messa_serv_GVR" name="sogg_messa_serv_GVR" data-placeholder="Seleziona Sogg. Messa Servizio GVR..." style="width:100%">
		<option value=""></option>
          	<option value="SI">SI</option>
         <option value="NO">NO</option>    
          </select>     
    </div>
    </div>
    
    
             <div class="form-group" id="n_panieri_idroestrattori_content">
        <label for="inputName" class="col-sm-4 control-label">N. panieri idroestrattori:</label>
        <div class="col-sm-8">
                      <input class="form-control" id="n_panieri_idroestrattori" type="text" name="n_panieri_idroestrattori"  value=""/>
    </div>
       </div> 
       
       
     <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Marcatura:</label>
        <div class="col-sm-8">
                     
          <select class="form-control select2" id="marcatura" name="marcatura" data-placeholder="Seleziona Marcatura..." style="width:100%">
         <option value=""></option>
		<option value="SI">SI</option>
         <option value="NO">NO</option>
          </select>     
    </div>
    </div>
    
    
       <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">N. ID ON:</label>
        <div class="col-sm-8">
                      <input class="form-control" id="n_id_on" type="text" name="n_id_on"  value=""/>
    </div>
       </div> 
       
                <div class="form-group" id="data_scadenza_ventennale_content">
        <label for="inputName" class="col-sm-4 control-label">Data scadenza ventennale:</label>
        <div class="col-sm-8">
                      <input class="form-control datepicker disabled" id="data_scadenza_ventennale" type="text" name="data_scadenza_ventennale"  value="" data-date-format="dd/mm/yyyy"/>
    </div>
       </div> 
       

         <div class="form-group" id="data_verifica_funzionamento_content">
        <label for="inputName" class="col-sm-4 control-label">Data verifica funzionamento:</label>
        <div class="col-sm-8">
                      <input class="form-control datepicker" id="data_verifica_funzionamento" type="text" name="data_verifica_funzionamento"  value="" data-date-format="dd/mm/yyyy"/>
    </div>
       </div> 
       
         <div class="form-group" id="data_prossima_verifica_funzionamento_content">
        <label for="inputName" class="col-sm-4 control-label">Data prossima verifica funzionamento:</label>
        <div class="col-sm-8">
                      <input class="form-control datepicker" id="data_prossima_verifica_funzionamento" type="text" name="data_prossima_verifica_funzionamento"  value="" data-date-format="dd/mm/yyyy"/>
    </div>
       </div> 
       
             <div class="form-group" id="data_verifica_integrita_content">
        <label for="inputName" class="col-sm-4 control-label">Data verifica integrità:</label>
        <div class="col-sm-8">
                      <input class="form-control datepicker" id="data_verifica_integrita" type="text" name="data_verifica_integrita"  value="" data-date-format="dd/mm/yyyy"/>
    </div>
       </div> 
       
         <div class="form-group" id="data_prossima_verifica_integrita_content">
        <label for="inputName" class="col-sm-4 control-label">Data prossima verifica integrità:</label>
        <div class="col-sm-8">
                      <input class="form-control datepicker" id="data_prossima_verifica_integrita" type="text" name="data_prossima_verifica_integrita"  value="" data-date-format="dd/mm/yyyy"/>
    </div>
       </div>
             <div class="form-group" id="data_verifica_interna_content">
        <label for="inputName" class="col-sm-4 control-label">Data verifica interna:</label>
        <div class="col-sm-8">
                      <input class="form-control datepicker" id="data_verifica_interna" type="text" name="data_verifica_interna"  value="" data-date-format="dd/mm/yyyy"/>
    </div>
       </div> 
       
         <div class="form-group" id="data_prossima_verifica_interna_content">
        <label for="inputName" class="col-sm-4 control-label">Data prossima verifica interna:</label>
        <div class="col-sm-8">
                      <input class="form-control datepicker" id="data_prossima_verifica_interna" type="text" name="data_prossima_verifica_interna"  value="" data-date-format="dd/mm/yyyy"/>
    </div>
       </div>
       
        <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Codici Milestone:</label>
        <div class="col-sm-8">
                      <textarea class="form-control" id="codice_milestone" name="codice_milestone" rows ="3" readonly></textarea>
    </div>
    </div>
       

 <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Note tecniche:</label>
        <div class="col-sm-8">
                      <textarea class="form-control" id="note_tecniche" name="note_tecniche" rows ="3"></textarea>
    </div>
    </div>
    <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Note generiche:</label>
        <div class="col-sm-8">
                      <textarea class="form-control" id="note_generiche" name="note_generiche" rows ="3"></textarea>
    </div>
    </div>

  		 </div>
      <div class="modal-footer">
       <input type="hidden" id="id_insieme" name="id_insieme"/>
<button type="submit" class="btn btn-primary pull-right" >Salva</button>
      </div>
    </div>
  </div>
 
</div>
   </form>





<form class="form-horizontal" id="formModificaAttrezzatura">
<div id="modalModificaAttrezzatura" class="modal fade" role="dialog" aria-labelledby="myLargeModalLabel">
    <%-- <div class="modal-dialog modal-${user.checkRuolo('CLVAL')? 'lg':'md'}" role="document"> --%>
     <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
     <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabelMod">${user.checkRuolo('CLVAL')? 'Dettaglio':'Modifica'} Attrezzatura</h4>
      </div>
       <div class="modal-body">
        <div class="nav-tabs-custom">
            <ul id="mainTabs" class="nav nav-tabs">
            <c:if test="${!user.checkRuolo('CLVAL') }">
              <li class="active" id="tab1"><a href="#modifica" data-toggle="tab" aria-expanded="true"   id="standardTab">Modifica</a></li>
              
              </c:if>
              		<li class="${user.checkRuolo('CLVAL')? 'active':''}" id="tab2"><a href="#verbali" data-toggle="tab" aria-expanded="false"   id="verbaliTab">Verbali</a></li>
              		
              		<li class="${user.checkRuolo('CLVAL')? 'active':''}" id="tab3"><a href="#attrezzature_insieme" data-toggle="tab" aria-expanded="false"   id="attrezzatureInsiemeTab">Attrezzature insieme</a></li>
              	
              
            </ul>
            <div class="tab-content">
         <c:if test="${!user.checkRuolo('CLVAL') }">
              <div class="tab-pane active" id="modifica">
              
              
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
        <label for="inputName" class="col-sm-4 control-label">Gruppo:</label>
        <div class="col-sm-8">
                   <!-- <input class="form-control" id="tipo_attivita_mod" type="text" name="tipo_attivita_mod" required value=""/> -->
                      
         <select class="form-control select2" required id="tipo_attivita_mod" name="tipo_attivita_mod" data-placeholder="Seleziona Gruppo..." style="width:100%">
         <option value=""></option>
         <option value="SP">SP</option>
         <option value="SC">SC</option>
         <option value="GVR">GVR</option>
         </select>   
    </div>
    </div>

   <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Descrizione:</label>
        <div class="col-sm-8">
                    <!--   <input class="form-control" id="descrizione_mod" type="text" name="descrizione_mod" required value=""/> -->
          <select class="form-control select2" disabled id="descrizione_mod" name="descrizione_mod" data-placeholder="Seleziona Descrizione..." style="width:100%">
         <option value=""></option>

         </select>  
    </div>
    
            <div class="col-sm-8" style="display:none">
                    <!--   <input class="form-control" id="descrizione_mod" type="text" name="descrizione_mod" required value=""/> -->
          <select class="form-control select2" disabled id="descrizione_mod_temp" name="descrizione_mod_temp" data-placeholder="Seleziona Descrizione..." style="width:100%">
         <option value=""></option>
        <c:forEach items="${lista_descrizioni_gruppi }" var="desc">
        <c:if test="${desc.id_specifica!=null && desc.id_specifica!=''}">
        <option value="${desc.gruppo}_${desc.descrizione}_${desc.id_specifica}@${desc.codici_milestone}">${desc.descrizione }</option>
        </c:if>
        <c:if test="${desc.id_specifica==null || desc.id_specifica==''}">
        <option value="${desc.gruppo}_${desc.descrizione}_@${desc.codici_milestone}">${desc.descrizione }</option>
        </c:if>
        
        </c:forEach>
         </select>  
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
        <!--               <input class="form-control" id="settore_impiego_mod" type="text" name="settore_impiego_mod"  value=""/> -->
          <select class="form-control select2" disabled id="settore_impiego_mod" name="settore_impiego_mod" data-placeholder="Seleziona settore d'impiego..." style="width:100%">
		<option value=""></option>
              
          </select>   
    </div>
    </div>
  
  
  <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Tipo attrezzatura:</label>
        <div class="col-sm-8">
                     
          <select class="form-control select2" disabled id="tipo_attrezzatura_mod" name="tipo_attrezzatura_mod" data-placeholder="Seleziona tipo attrezzatura..." style="width:100%">
		<option value=""></option>   
	
          </select>     
    </div>
    </div>
    
            <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Tipo attrezzatura GVR:</label>
        <div class="col-sm-8">
                     
          <select class="form-control select2" disabled id="tipo_attrezzatura_gvr_mod" name="tipo_attrezzatura_gvr_mod" data-placeholder="Seleziona tipo attrezzatura GVR..." style="width:100%">
		<option value=""></option>              		

          </select>     
    </div>
    </div>
    
  <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">ID specifica:</label>
        <div class="col-sm-8">
                     
         
            <input class="form-control" id="id_specifica_mod" type="text" name="id_specifica_mod" readonly  value=""/>
		
    
    </div>
    </div>
    
    
     <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Sogg. messa servizio GVR:</label>
        <div class="col-sm-8">
                     
          <select class="form-control select2"  id="sogg_messa_serv_GVR_mod" name="sogg_messa_serv_GVR_mod" data-placeholder="Seleziona Sogg. Messa Servizio GVR..." style="width:100%">
		<option value=""></option>
          	<option value="SI">SI</option>
         <option value="NO">NO</option>    
          </select>     
    </div>
    </div>
    
    
             <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">N. panieri idroestrattori:</label>
        <div class="col-sm-8">
                      <input class="form-control" id="n_panieri_idroestrattori_mod" type="text" name="n_panieri_idroestrattori_mod"  value=""/>
    </div>
       </div> 
       
       
     <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Marcatura:</label>
        <div class="col-sm-8">
                     
          <select class="form-control select2" id="marcatura_mod" name="marcatura_mod" data-placeholder="Seleziona Marcatura..." style="width:100%">
         <option value=""></option>
		<option value="SI">SI</option>
         <option value="NO">NO</option>
          </select>     
    </div>
    </div>
    
    
       <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">N. ID ON:</label>
        <div class="col-sm-8">
                      <input class="form-control" id="n_id_on_mod" type="text" name="n_id_on_mod"  value=""/>
    </div>
       </div> 
       
                <div class="form-group">
        <label for="inputName" class="col-sm-4 control-label">Data scadenza ventennale:</label>
        <div class="col-sm-8">
                      <input class="form-control datepicker disabled" id="data_scadenza_ventennale_mod" type="text" name="data_scadenza_ventennale_mod"  value="" data-date-format="dd/mm/yyyy"/>
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
        <label for="inputName" class="col-sm-4 control-label">Codici Milestone:</label>
        <div class="col-sm-8">
                      <textarea class="form-control" id="codice_milestone_mod" name="codice_milestone_mod" rows ="3" readonly></textarea>
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
    
    
    
    <div class="row">
    <div class="col-xs-12">
    
          <input type="hidden" id="id_attrezzatura" name="id_attrezzatura">
      <a class="btn pull-left" onClick="rendiAttrezzaturaObsoleta()" id="rendi_obsoleta" style="display:none">Rendi obsoleta</a>
      <a class="btn pull-left" onClick="rendiAttrezzaturaObsoleta()" id="rendi_non_obsoleta" style="display:none">Rendi non obsoleta</a>
		<button type="submit" class="btn btn-primary pull-right" >Salva</button>
    
    </div>
    
    </div>
    
  		 </div>
              </c:if>
              
              
              
              
             
              <div class="tab-pane table-responsive ${user.checkRuolo('CLVAL')? 'active':''}"  id="verbali">
    
    
    			<c:if test="${user.checkRuolo('CLVAL') }"><input type="hidden" id="id_attrezzatura" name="id_attrezzatura"></c:if>
              <div id="content_verbali"></div>
              
              
              </div>
            
              
               <div class="tab-pane table-responsive ${user.checkRuolo('CLVAL')? 'active':''}"  id="attrezzature_insieme"></div>
              
              </div>
              </div>
      
        
         
<!--       <div class="modal-footer">
      

      </div> -->
    </div>
    </div>
  </div>
</div>

 </form>


<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/datejs/1.0/date.min.js"></script>

<script type="text/javascript">


$('#matricola_inail').focusout(function(){
	
	var string = $(this).val();

	var pattern = /^[0-9][0-9][0-9][0-9]\/[0-9]\/[0-9][0-9][0-9][0-9][0-9]\/[A-Z][A-Z]$/.test(string);
	
	if(pattern==false){
		$('#label_pattern').show();
	}else{
		$('#label_pattern').hide();
	}
	
});



function openVerbaliModal(id_attrezzatura){
	
	//exploreModal("listaAttrezzature.do","action=verbali_attrezzatura&id_attrezzatura="+id_attrezzatura,"#verbali");
	
	$('#id_attrezzatura').val(id_attrezzatura)
	
	exploreModal("listaAttrezzature.do","action=verbali_attrezzatura&id_attrezzatura="+id_attrezzatura,"#verbali");
	$('#modalModificaAttrezzatura').modal();
}


 $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {


	var  contentID = e.target.id;

	
	if(contentID == "verbaliTab"){
		
		exploreModal("listaAttrezzature.do","action=verbali_attrezzatura&id_attrezzatura="+$('#id_attrezzatura').val(),"#verbali");
	}
	
if(contentID == "attrezzatureInsiemeTab"){
		
		exploreModal("listaAttrezzature.do","action=attrezzature_insieme&id_attrezzatura="+$('#id_attrezzatura').val(),"#attrezzature_insieme");
	}

	
	
	}); 


 var columsDatatables = [];

$("#tableAttr").on( 'init.dt', function ( e, settings ) {

    var api = new $.fn.dataTable.Api( settings );
    var state = api.state.loaded();
 
    if(state != null && state.columns!=null){
    		console.log(state.columns);
    
    columsDatatables = state.columns;
    }
    $('#tableAttr thead th').each( function () {
     	if(columsDatatables.length==0 || columsDatatables[$(this).index()]==null ){columsDatatables.push({search:{search:""}});}
    	   var title = $('#tableAttr thead th').eq( $(this).index() ).text();
    	   
    		   $(this).append( '<div><input class="inputsearchtable" id="inputsearchtable_'+$(this).index()+'" style="width:100%" type="text" value="'+columsDatatables[$(this).index()].search.search+'" /></div>');
    	   
    	  
    	} );

} ); 

$('#formNuovaAttrezzatura').on('submit',function(e){
    e.preventDefault();
	
	nuovaAttrezzatura();
});

$('#formModificaAttrezzatura').on('submit',function(e){
    e.preventDefault();
	
    modificaAttrezzatura();
});

function modalNuovaAttrezzatura(attrezzatura_insieme, id_insieme){
	
	$('#cliente').val($('#select1').val());
	$('#cliente').change();
	
	$('#sede').val($('#select2').val());
	$('#sede').change(); 
	
	if(attrezzatura_insieme){
		
		$('#settore_impiego_content').hide();
		$('#sogg_messa_serv_GVR_content').hide();
		$('#n_panieri_idroestrattori_content').hide();
		$('#data_scadenza_ventennale_content').hide();
		$('#data_verifica_funzionamento_content').hide();
		$('#data_prossima_verifica_funzionamento_content').hide();
		$('#data_verifica_integrita_content').hide();
		$('#data_prossima_verifica_integrita_content').hide();
		$('#data_verifica_interna_content').hide();
		$('#data_prossima_verifica_interna_content').hide();
		$('#matricola_inail').attr("required", false);
		$('#id_insieme').val(id_insieme);		
		
	} else{
		
		$('#settore_impiego_content').show();
		$('#sogg_messa_serv_GVR_content').show();
		$('#n_panieri_idroestrattori_content').show();
		$('#data_scadenza_ventennale_content').show();
		$('#data_verifica_funzionamento_content').show();
		$('#data_prossima_verifica_funzionamento_content').show();
		$('#data_verifica_integrita_content').show();
		$('#data_prossima_verifica_integrita_content').show();
		$('#data_verifica_interna_content').show();
		$('#data_prossima_verifica_interna_content').show();
		$('#matricola_inail').attr("required", true);
		
		
	}
	
	
$("#modalNuovaAttrezzatura select.select2").each(function (index, element) {
        
		if(element.id!="cliente_appoggio" && element.id!="select2"){
			$('#'+element.id).select2(
					
					{dropdownParent: $('#form_group_id')});
		}
       
    });
	
	$('#modalNuovaAttrezzatura').modal();
}

function modalModificaAttrezzatura(id_attrezzatura, matricola_inail, numero_fabbrica, tipo_attivita, descrizione, id_cliente, id_sede,
		data_verifica_funzionamento, data_prossima_verifica_funzionamento,data_verifica_integrita, data_prossima_verifica_integrita, data_verifica_interna, data_prossima_verifica_interna,
		anno_costruzione, fabbricante, modello, settore_impiego, note_tecniche, note_generiche, obsoleta, tipo_attrezzatura, tipo_attrezzatura_gvr,
		id_specifica, sogg_messa_serv_GVR, n_panieri_idroestrattori, marcatura, n_id_on, data_scadenza_ventennale, codice_milestone){

	
	
	$('#id_attrezzatura').val(id_attrezzatura);
	$('#matricola_inail_mod').val(matricola_inail);
	$('#numero_fabbrica_mod').val(numero_fabbrica);
	$('#tipo_attivita_mod').val(tipo_attivita);
	$('#tipo_attivita_mod').change();
	if(id_specifica!=null && id_specifica!=''){
		$('#descrizione_mod').val(tipo_attivita+"_"+descrizione+"_"+id_specifica+"@"+codice_milestone);
	}else{
		$('#descrizione_mod').val(tipo_attivita+"_"+descrizione+"_@"+codice_milestone);	
	}
	
	$('#descrizione_mod').change();
	$('#cliente_mod').val(id_cliente);
	$('#cliente_mod').change();
	if(id_sede!=0){
		$('#sede_mod').val(id_sede+"_"+id_cliente);	
	}else{
		$('#sede_mod').val(0);
	}
	
/* 	var z = tipo_attivita+"_"+descrizione+"_"+id_specifica+"@"+codice_milestone
	var y = $('#descrizione_mod option').clone();
	
	if(z == y[14].value){
		alert("OK")
	}
	var x = $('#descrizione_mod').val(); */
	
	$('#sede_mod').change();
	$('#anno_costruzione_mod').val(anno_costruzione);
	$('#fabbricante_mod').val(fabbricante);
	$('#modello_mod').val(modello);
	$('#settore_impiego_mod').val(settore_impiego);
	$('#settore_impiego_mod').change();
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
	
	if(data_scadenza_ventennale!=null && data_scadenza_ventennale!= ''){
		$('#data_scadenza_ventennale_mod').val(Date.parse(data_scadenza_ventennale).toString("dd/MM/yyyy"));	
	}
	
	$('#id_specifica_mod').val(id_specifica);	
	
	$('#data_prossima_verifica_funzionamento_mod').datepicker({
			format: "dd/MM/yyyy"
	});
	

	$('#tipo_attrezzatura_mod').val(tipo_attrezzatura);
	$('#tipo_attrezzatura_mod').change();

	$('#tipo_attrezzatura_gvr_mod').val(tipo_attrezzatura_gvr);
	$('#tipo_attrezzatura_gvr_mod').change();
	
	$('#sogg_messa_serv_GVR_mod').val(sogg_messa_serv_GVR);
	$('#sogg_messa_serv_GVR_mod').change();
	
	$('#n_panieri_idroestrattori_mod').val(n_panieri_idroestrattori);
	
	$('#marcatura_mod').val(marcatura);
	$('#marcatura_mod').change();

	$('#n_id_on_mod').val(n_id_on);
	
	if(obsoleta==0){
		$('#rendi_obsoleta').addClass("btn-danger");
		$('#rendi_obsoleta').show();
		$('#rendi_non_obsoleta').hide();
	}else{
		$('#rendi_non_obsoleta').addClass("btn-success");
		$('#rendi_obsoleta').hide();
		$('#rendi_non_obsoleta').show();
	}
	
	

	$('#modalModificaAttrezzatura').modal();
	
}
function formatDate(data){
	
	   var mydate = new Date(data);
	   
	   if(!isNaN(mydate.getTime())){
	   
		   str = mydate.toString("dd/MM/yyyy");
	   }			   
	   return str;	 		
}

$('#modalModificaAttrezzatura').on('hidden.bs.modal', function(){
	   $(document.body).css('padding-right', '0px'); 
});



function filtraObsolete(obsolete){
	
	var table = $('#tableAttr').DataTable();
	
	
		table
        .columns( 1 )
        .search( obsolete)
        .draw();
	
}

$('#btnObsolete').click(function(){
	
	$('#btnObsolete').attr('disabled', true);
	$('#btnInVerifica').attr('disabled', false);
	$('#btnTutte').attr('disabled', false);
	
	filtraObsolete(1)
	
});

$('#btnInVerifica').click(function(){
	
	$('#btnObsolete').attr('disabled', false);
	$('#btnInVerifica').attr('disabled', true);
	$('#btnTutte').attr('disabled', false);
	filtraObsolete(0)
});

$('#btnTutte').click(function(){
	
	$('#btnObsolete').attr('disabled', false);
	$('#btnInVerifica').attr('disabled', false);
	$('#btnTutte').attr('disabled', true);
	filtraObsolete('');
});







$(document).ready(function() {
	console.log("test");
	$('.datepicker').datepicker();

/* 	$("select.select2").each(function (index, element) {
        
		if(element.id!="cliente_appoggio" && element.id!="select2"){
			$(element).select2(
					
					{dropdownParent: $('#modalNuovaAttrezzatura')});
		}
       
    });
	 */

	
	var col_azioni = 29;
	if(${user.checkRuolo('CLVAL')}){
		col_azioni = 12;
	}
	

	
	tableAttr = $('#tableAttr').DataTable({
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
	                   { responsivePriority: 2, targets: col_azioni } 
	                  
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
	
	
	tableAttr.buttons().container()
  .appendTo( '#tableAttr_wrapper .col-sm-6:eq(1)' );
	
	
	tableAttr.buttons().container().appendTo( '#tableAttr_wrapper .col-sm-6:eq(1)');
	    $('.inputsearchtable').on('click', function(e){
	       e.stopPropagation();    
	    });
//DataTable
//table = $('#tabPM').DataTable();
//Apply the search
tableAttr.columns().eq( 0 ).each( function ( colIdx ) {
$( 'input', tableAttr.column( colIdx ).header() ).on( 'keyup', function () {
	tableAttr
      .column( colIdx )
      .search( this.value )
      .draw();
} );
} );  
tableAttr.columns.adjust().draw();
	
	filtraObsolete(0)
	
	
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
	
	
		

$("#tipo_attivita").change(function() {
	
	$('#tipo_attrezzatura_gvr').attr("disabled", true);
	$('#sogg_messa_serv_GVR').attr("disabled", true);
	$('#data_scadenza_ventennale').addClass("disabled");
	
	
	  if ($(this).data('options') == undefined) 
	  {
	    /*Taking an array of all options-2 and kind of embedding it on the select1*/
	    $(this).data('options', $('#descrizione option').clone());
	  }
	  
	  var gruppo = $(this).val();
	 
	  var options = $(this).data('options');

	  var opt=[];
	
	//  opt.push("<option value = 0>Non Associate</option>");

	   for(var  i=0; i<options.length;i++)
	   {
		var str=options[i].value; 
	
		//if(str.substring(str.indexOf("_")+1,str.length)==id)
		if(str.split("_")[0] == gruppo)	
		{
			opt.push(options[i]);
		}   
	   }
	 $("#descrizione").prop("disabled", false);
	 
	  $('#descrizione').html(opt);

		$("#descrizione").change();  
		
		var settore_opt = [];
		
				
		if(gruppo == "SP" || gruppo == "GVR"){
			settore_opt.push('<option value="N.A.">N.A.</option>');			
			
			if(gruppo == 'GVR'){
				$('#tipo_attrezzatura_gvr').attr("disabled", true);
				$('#sogg_messa_serv_GVR').attr("disabled", false);	
			}else{
				$('#data_scadenza_ventennale').removeClass("disabled");
			}
			
						
			
		}else if(gruppo == "SC"){
			settore_opt.push('<option value="regolare">Regolare</option>');
			settore_opt.push('<option value="costruzioni">Costruzioni</option>');
			settore_opt.push('<option value="siderurgico">Siderurgico</option>');
			settore_opt.push('<option value="estrattivo">Estrattivo</option>');
			settore_opt.push('<option value="porturale">Portuale</option>');		
			
			$('#data_scadenza_ventennale').removeClass("disabled");
		}
		
		 $("#settore_impiego").prop("disabled", false);
		  $('#settore_impiego').html(settore_opt);
			$("#settore_impiego").change();  
	});
	
	
$("#tipo_attivita_mod").change(function() {
	
	
	$('#tipo_attrezzatura_gvr_mod').attr("disabled", true);
	$('#sogg_messa_serv_GVR_mod').attr("disabled", true);
	$('#data_scadenza_ventennale_mod').addClass("disabled");
    
	  if ($(this).data('options') == undefined) 
	  {
	    /*Taking an array of all options-2 and kind of embedding it on the select1*/
	    $(this).data('options', $('#descrizione_mod_temp option').clone());
	  }
	  
	  var gruppo = $(this).val();
	 
	  var options = $(this).data('options');

	  var opt=[];
	
	//  opt.push("<option value = 0>Non Associate</option>");

	   for(var  i=0; i<options.length;i++)
	   {
		var str=options[i].value; 
	
		//if(str.substring(str.indexOf("_")+1,str.length)==id)
		if(str.split("_")[0] == gruppo)	
		{
			opt.push(options[i]);
		}   
	   }
	 $("#descrizione_mod").prop("disabled", false);
	 
	  $('#descrizione_mod').html(opt);

		$('#descrizione_mod').change();  
		
		var settore_opt = [];
		
				
		if(gruppo == "SP" || gruppo == "GVR"){
			settore_opt.push('<option value="N.A.">N.A.</option>');
			
			if(gruppo == 'GVR'){
				$('#tipo_attrezzatura_gvr_mod').attr("disabled", true);
				$('#sogg_messa_serv_GVR_mod').attr("disabled", false);	
			}else{
				$('#data_scadenza_ventennale_mod').removeClass("disabled");
			}
			
		}else if(gruppo == "SC"){
			settore_opt.push('<option value="regolare">Regolare</option>');
			settore_opt.push('<option value="costruzioni">Costruzioni</option>');
			settore_opt.push('<option value="siderurgico">Siderurgico</option>');
			settore_opt.push('<option value="estrattivo">Estrattivo</option>');
			settore_opt.push('<option value="rorturale">Portuale</option>');		
			
			$('#data_scadenza_ventennale_mod').removeClass("disabled");
		}
		 $("#settore_impiego_mod").prop("disabled", false);
		  $('#settore_impiego_mod').html(settore_opt);
			$("#settore_impiego_mod").change();  
	});
  



$("#descrizione").change(function() {
    
	$('#n_panieri_idroestrattori').attr("disabled",true);
	$("#tipo_attrezzatura_gvr").prop("disabled", true);


	  var gruppo = $(this).val().split('_')[0];
	 
	  var descrizione = $(this).val().split("_")[1];
	
	 // var id_spec = $(this).val().split("_")[3];
	  var id_spec = $(this).val().split("_")[2].split("@")[0];
	  var codice_milestone = $(this).val().split("@")[1];


	  var opt_tipo_attr =[];
	  var opt_tipo_attr_gvr =[];	
	  
	  
	  if(descrizione.startsWith('Idroestrattor')){
		  $('#n_panieri_idroestrattori').attr("disabled",false);
	  }
	  	
	  $('#id_specifica').val(id_spec);
	  
	  
		if(gruppo == "GVR"){
			
			if(descrizione.startsWith("Recipienti")){
				opt_tipo_attr.push('<option value="a1">a1</option>');
				opt_tipo_attr.push('<option value="a2">a2</option>');
				opt_tipo_attr.push('<option value="a5">a5</option>');
				opt_tipo_attr.push('<option value="b1">b1</option>');
				opt_tipo_attr.push('<option value="b2">b2</option>');
				
				opt_tipo_attr_gvr.push('<option value="a1">a1</option>');

			}
			else if(descrizione.startsWith("Generatori")){
				opt_tipo_attr.push('<option value="b3">b3</option>');
				
				if(descrizione.startsWith("Generatori acqua")){
					
					opt_tipo_attr_gvr.push('<option value="a3">a3</option>');

				}else{
					
					opt_tipo_attr_gvr.push('<option value="a2">a2</option>');
				}
				
			}			
			else if(descrizione.startsWith("Forni")){
				opt_tipo_attr.push('<option value="a1">a1</option>');
				
				opt_tipo_attr_gvr.push('<option value="a6">a6</option>');
			

			}
			else if(descrizione.startsWith("Tubazioni")){
				opt_tipo_attr.push('<option value="a3">a3</option>');				
				opt_tipo_attr.push('<option value="a4">a4</option>');
				opt_tipo_attr.push('<option value="b4">b4</option>');
				opt_tipo_attr.push('<option value="b5">b5</option>');
				
				opt_tipo_attr_gvr.push('<option value="a4">a4</option>');
			}
			else if(descrizione.startsWith("Insieme")){
				
				opt_tipo_attr.push('<option value="a1">a1</option>');
				opt_tipo_attr.push('<option value="a2">a2</option>');
				opt_tipo_attr.push('<option value="a5">a5</option>');
				opt_tipo_attr.push('<option value="b1">b1</option>');
				opt_tipo_attr.push('<option value="b2">b2</option>');
				
				opt_tipo_attr_gvr.push('<option value="b">b</option>');
			}
			
			else{
				opt_tipo_attr.push('<option value=""></option>');
				
				opt_tipo_attr_gvr.push('<option value="a5">a5</option>');
			}
			$("#tipo_attrezzatura_gvr").prop("disabled", true);
		}
		
		else if(gruppo == "SP"){
			
			if(descrizione.startsWith("Scale")){
				opt_tipo_attr.push('<option value="a">a</option>');

			//	opt_tipo_attr_gvr.push('<option value=""></option>');				


				
			}
			else if(descrizione.startsWith("Ponti mobili")){
				
				if(descrizione.includes("azionamento")){
					
					opt_tipo_attr.push('<option value="b">b</option>');

				}else{
					opt_tipo_attr.push('<option value="c">c</option>');
				}
				

			}			
			else if(descrizione.startsWith("Carri") || descrizione.startsWith("Ponti sospesi")){
				
				opt_tipo_attr.push('<option value="d">d</option>');				
			
				
			}
			else if(descrizione.startsWith("Piattaforme")){
				opt_tipo_attr.push('<option value="e">e</option>');				

			}			
			else{
				opt_tipo_attr.push('<option value="f">f</option>');				
			}

		}

		else if(gruppo == "SC"){
					
			
			if(descrizione.startsWith("Carrelli")){
				opt_tipo_attr.push('<option value="c">c</option>');
				
		//		opt_tipo_attr_gvr.push('<option value="a1">a1</option>');
				
		
				
			}
			else if(descrizione.startsWith("Idroestrattori")){
				
			
				opt_tipo_attr.push('<option value="d1">d1</option>');
				opt_tipo_attr.push('<option value="d2">d2</option>');
				opt_tipo_attr.push('<option value="d3">d3</option>');
			
			}			
			else if(descrizione.startsWith("Gru")){
				
				if(descrizione.includes("braccio")||descrizione.includes("trasferibile")||descrizione.includes("torre")||descrizione.includes("Derrick")){
					opt_tipo_attr.push('<option value="b1">b1</option>');
					opt_tipo_attr.push('<option value="b2">b2</option>');
					opt_tipo_attr.push('<option value="b3">b3</option>');				
					
				}else{
					opt_tipo_attr.push('<option value="a1">a1</option>');
					opt_tipo_attr.push('<option value="a2">a2</option>');
					opt_tipo_attr.push('<option value="a3">a3</option>');
					opt_tipo_attr.push('<option value="a4">a4</option>');
				}
				
			}


			
			else{
				opt_tipo_attr.push('<option value="b1">b1</option>');
				opt_tipo_attr.push('<option value="b2">b2</option>');
				opt_tipo_attr.push('<option value="b3">b3</option>');	

			}
			
			
			
			
			
		}
		
		$('#codice_milestone').val(codice_milestone);
		 $("#tipo_attrezzatura").prop("disabled", false);
		  $('#tipo_attrezzatura').html(opt_tipo_attr);
			$("#tipo_attrezzatura").change();  
			
			 //$("#tipo_attrezzatura_gvr").prop("disabled", false);
			  $('#tipo_attrezzatura_gvr').html(opt_tipo_attr_gvr);
				$("#tipo_attrezzatura_gvr").change();  
				
				select_settore_impiego("");
	});



$("#descrizione_mod").change(function() {
    
	$('#n_panieri_idroestrattori_mod').attr("disabled",true);
	$("#tipo_attrezzatura_gvr_mod").prop("disabled", true);
	
	if($(this).val()!=null){
		 var gruppo = $(this).val().split('_')[0];
		 
		  var descrizione = $(this).val().split("_")[1];
		  if($(this).val().split("_").length>2){
			  var id_spec = $(this).val().split("_")[2].split("@")[0];
		  }else{
			  var id_spec = "";
		  }
		  
		  var codice_milestone = $(this).val().split("@")[1];
		  //var id_spec = $(this).val().split("_")[2];

		  var opt_tipo_attr =[];
		  var opt_tipo_attr_gvr =[];	
		  
		  
		  if(descrizione.startsWith('Idroestrattor')){
			  $('#n_panieri_idroestrattori_mod').attr("disabled",false);
		  }
		  	
		  $('#id_specifica_mod').val(id_spec);
		  
		  
			if(gruppo == "GVR"){
				
				if(descrizione.startsWith("Recipienti")){
					opt_tipo_attr.push('<option value="a1">a1</option>');
					opt_tipo_attr.push('<option value="a2">a2</option>');
					opt_tipo_attr.push('<option value="a5">a5</option>');
					opt_tipo_attr.push('<option value="b1">b1</option>');
					opt_tipo_attr.push('<option value="b2">b2</option>');
					
					opt_tipo_attr_gvr.push('<option value="a1">a1</option>');

				}
				else if(descrizione.startsWith("Generatori")){
					opt_tipo_attr.push('<option value="b3">b3</option>');
					
					if(descrizione.startsWith("Generatori acqua")){
						
						
						opt_tipo_attr_gvr.push('<option value="a3">a3</option>');

					}else{
						opt_tipo_attr_gvr.push('<option value="a2">a2</option>');
					}
					

					
					
				}			
				else if(descrizione.startsWith("Forni")){
					opt_tipo_attr.push('<option value="a1">a1</option>');
					
					opt_tipo_attr_gvr.push('<option value="a6">a6</option>');
				

				}
				else if(descrizione.startsWith("Tubazioni")){
					opt_tipo_attr.push('<option value="a3">a3</option>');				
					opt_tipo_attr.push('<option value="a4">a4</option>');
					opt_tipo_attr.push('<option value="b4">b4</option>');
					opt_tipo_attr.push('<option value="b5">b5</option>');
					
					opt_tipo_attr_gvr.push('<option value="a4">a4</option>');
				}
				else if(descrizione.startsWith("Insieme")){
					
					opt_tipo_attr.push('<option value="a1">a1</option>');
					opt_tipo_attr.push('<option value="a2">a2</option>');
					opt_tipo_attr.push('<option value="a5">a5</option>');
					opt_tipo_attr.push('<option value="b1">b1</option>');
					opt_tipo_attr.push('<option value="b2">b2</option>');
					
					opt_tipo_attr_gvr.push('<option value="b">b</option>');
				}
				
				else{
					opt_tipo_attr.push('<option value=""></option>');
					
					opt_tipo_attr_gvr.push('<option value="a5">a5</option>');
				}

				$("#tipo_attrezzatura_gvr_mod").prop("disabled", true);
			}
			
			else if(gruppo == "SP"){
				
				if(descrizione.startsWith("Scale")){
					opt_tipo_attr.push('<option value="a">a</option>');

				//	opt_tipo_attr_gvr.push('<option value=""></option>');				


					
				}
				else if(descrizione.startsWith("Ponti mobili")){
					
					if(descrizione.includes("azionamento")){
						
						opt_tipo_attr.push('<option value="b">b</option>');

					}else{
						opt_tipo_attr.push('<option value="c">c</option>');
					}
					

				}			
				else if(descrizione.startsWith("Carri") || descrizione.startsWith("Ponti sospesi")){
					
					opt_tipo_attr.push('<option value="d">d</option>');				
				
					
				}
				else if(descrizione.startsWith("Piattaforme")){
					opt_tipo_attr.push('<option value="e">e</option>');				

				}			
				else{
					opt_tipo_attr.push('<option value="f">f</option>');				
				}

			}

			else if(gruppo == "SC"){
						
				
				if(descrizione.startsWith("Carrelli")){
					opt_tipo_attr.push('<option value="c">c</option>');
					
			//		opt_tipo_attr_gvr.push('<option value="a1">a1</option>');
					
			
					
				}
				else if(descrizione.startsWith("Idroestrattori")){
					
				
					opt_tipo_attr.push('<option value="d1">d1</option>');
					opt_tipo_attr.push('<option value="d2">d2</option>');
					opt_tipo_attr.push('<option value="d3">d3</option>');
				
				}			
				else if(descrizione.startsWith("Gru")){
					
					if(descrizione.includes("braccio")||descrizione.includes("trasferibile")||descrizione.includes("torre")||descrizione.includes("Derrick")){
						opt_tipo_attr.push('<option value="b1">b1</option>');
						opt_tipo_attr.push('<option value="b2">b2</option>');
						opt_tipo_attr.push('<option value="b3">b3</option>');				
						
					}else{
						opt_tipo_attr.push('<option value="a1">a1</option>');
						opt_tipo_attr.push('<option value="a2">a2</option>');
						opt_tipo_attr.push('<option value="a3">a3</option>');
						opt_tipo_attr.push('<option value="a4">a4</option>');
					}
					
				}


				
				else{
					opt_tipo_attr.push('<option value="b1">b1</option>');
					opt_tipo_attr.push('<option value="b2">b2</option>');
					opt_tipo_attr.push('<option value="b3">b3</option>');	

				}
				
				
				
				
				
			}
			
			$('#codice_milestone_mod').val(codice_milestone);
			 $("#tipo_attrezzatura_mod").prop("disabled", false);
			  $('#tipo_attrezzatura_mod').html(opt_tipo_attr);
				$("#tipo_attrezzatura_mod").change();  
				
				 
				  $('#tipo_attrezzatura_gvr_mod').html(opt_tipo_attr_gvr);
					$("#tipo_attrezzatura_gvr_mod").change();  
					
					
					
					
					select_settore_impiego("_mod");
	}

	 
	});

$('#settore_impiego_mod').change(function(){
select_settore_impiego("_mod");
	
	
});

$('#settore_impiego').change(function(){
	select_settore_impiego("");
		
		
	});


function select_settore_impiego(mod){
	

	var opt = [];
	
	var selection = $('#settore_impiego'+mod).val();
	var gruppo = $('#tipo_attivita'+mod).val();
	var descrizione = $('#descrizione'+mod).val().split("_")[1];
	
	if(gruppo == 'SC' && !descrizione.startsWith("Carrelli") && !descrizione.startsWith("Idroestrattori")){
		
		if(descrizione.startsWith("Gru")){
			if(descrizione.includes("braccio")||descrizione.includes("trasferibile")||descrizione.includes("torre")||descrizione.includes("Derrick")){
				if(selection == 'regolare'){
					opt.push('<option value="b2">b2</option>');
					opt.push('<option value="b3">b3</option>');
				}else{
					opt.push('<option value="b1">b1</option>');
					
				}
				
				
			}else{
				if(selection == 'regolare'){
					opt.push('<option value="a3">a3</option>');
					opt.push('<option value="a4">a4</option>');
				}else{
					opt.push('<option value="a1">a1</option>');
					opt.push('<option value="a2">a2</option>');
					
				}
			}
			
			
		}else{
			if(selection == 'regolare'){
				opt.push('<option value="b2">b2</option>');
				opt.push('<option value="b3">b3</option>');
			}else{
				opt.push('<option value="b1">b1</option>');
				
			}
		}
		
		
		$('#tipo_attrezzatura'+mod).html(opt);
	}

	
	
	

}

</script>
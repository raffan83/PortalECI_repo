<%@page import="it.portalECI.DTO.UtenteDTO"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="com.google.gson.JsonObject"%>
<%@page import="com.google.gson.JsonElement"%>
<%@page import="it.portalECI.DTO.CampioneDTO"%>
<%@page import="it.portalECI.DTO.TipoCampioneDTO"%>
<%@ page language="java" import="java.util.List" %>
<%@ page language="java" import="java.util.ArrayList" %>
<%@page import="java.text.SimpleDateFormat"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% 
UtenteDTO utente = (UtenteDTO)request.getSession().getAttribute("userObj");

JsonObject json = (JsonObject)session.getAttribute("myObj");
JsonElement jsonElem = (JsonElement)json.getAsJsonObject("dataInfo");
Gson gson = new Gson();
CampioneDTO campione=(CampioneDTO)gson.fromJson(jsonElem,CampioneDTO.class); 

ArrayList<TipoCampioneDTO> listaTipoCampione = (ArrayList)session.getAttribute("listaTipoCampione");

SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");
%>
	
 <c:set var="tipo_camp" value="<%=campione.getTipo_campione().getId() %>"></c:set>
 <form class="form-horizontal" id="formAggiornamentoCampione">
 
 
 
      	            <div class="form-group">
        <label for="inputName" class="col-sm-3 control-label">Proprietario:</label>
        <div class="col-sm-9">
                      <input class="form-control  " id="proprietario_mod" type="text" name="proprietario_mod"  value="<%if(campione.getProprietario()!=null){out.println(campione.getProprietario());}%>" required />
    </div>
     </div>    
 
        <div class="form-group">
          <label for="inputEmail" class="col-sm-3 control-label">Settore:</label>

         <div class="col-sm-9">
         <select class="form-control select2" id="settore_mod" name="settore_mod" required>
         <option value=""></option>
        <%if(campione.getSettore()==0){ %>
         <option value="0" selected>Organismo di ispezione</option>
         <option value="1" >Soggetto abilitato</option>
         <%}else{ %>
         <option value="0" >Organismo di ispezione</option>
         <option value="1" selected>Soggetto abilitato</option>
         <%} %>
         </select>
			
     	</div>
   </div>
   
 
 
           <div class="form-group">
        <label for="inputName" class="col-sm-3 control-label">Tipo Campione:</label>
        <div class="col-sm-9">
                     
					   <select class="form-control" id="tipoCampione_mod" name="tipoCampione_mod" required >
                      
                                            <%
                                            for(TipoCampioneDTO cmp :listaTipoCampione)
                                            {
                                            	String def = "";
                                            	if(campione.getTipo_campione().getId() == cmp.getId()){
                                            		def = "selected";
                                            	}else{
                                            		def = "";
                                            	}
                                            	 %> 
                            	            	 <option <%=def%> value="<%=cmp.getId() %>"><%=cmp.getNome() %></option>
                            	            	 <%	 
                                            }
                                            %>
                                            
                      </select>
                      
                      
    </div>
     </div>


          <div class="form-group">
        <label for="inputName" class="col-sm-3 control-label">Codice:</label>
        <div class="col-sm-9">
                      <input class="form-control" id="codice_mod" type="text" name="codice_mod" required value="<%=campione.getCodice() %>"/>
    </div>
     </div>
      
            <div class="form-group">
        <label for="inputName" class="col-sm-3 control-label">Descrizione:</label>
        <div class="col-sm-9">
                      <input class="form-control" id="descrizione_mod" type="text" required name="descrizione_mod"  value="<%=campione.getDescrizione() %>"/>
    </div>
     </div>
     
              <div class="form-group">
        <label for="inputName" class="col-sm-3 control-label">Modello:</label>
        <div class="col-sm-9">
                      <input class="form-control" id="modello_mod" type="text" required name="modello_mod"  value="<%=campione.getModello() %>"/>
    </div>
       </div> 
       
              <div class="form-group">
        <label for="inputName" class="col-sm-3 control-label">Costruttore:</label>
        <div class="col-sm-9">
                      <input class="form-control" id="costruttore_mod" type="text" required name="costruttore_mod"  value="<%=campione.getCostruttore() %>"/>
    </div>
       </div>
	<div class="form-group">
        <label for="inputName" class="col-sm-3 control-label">Matricola:</label>
        <div class="col-sm-9">
                      <input class="form-control" id="matricola_mod" type="text" required name="matricola_mod"  value="<%=campione.getMatricola() %>"/>
    </div>
    </div>

       <div class="form-group">
        <label for="distributore" class="col-sm-3 control-label">Distributore:</label>
        <div class="col-sm-9">
                      <input class="form-control required" id="distributore_mod" type="text" name="distributore_mod"  value="<%if(campione.getDistributore()!=null){out.println(campione.getDistributore());}%>" />
    </div>
       </div> 
       
              <div class="form-group">
        <label for="data_acquisto" class="col-sm-3 control-label">Data Acquisto:</label>
        <div class="col-sm-9">
                      <input class="form-control datepicker required" id="data_acquisto_mod" type="text" name="data_acquisto_mod"   value="<%if(campione.getData_acquisto()!=null){out.println(sdf.format(campione.getData_acquisto()));}%>" data-date-format="dd/mm/yyyy"/>
    </div>
       </div> 
       
       
                     <div class="form-group">
        <label for="data_acquisto" class="col-sm-3 control-label">Data Messa in servizio:</label>
        <div class="col-sm-9">
                      <input class="form-control datepicker required" id="data_messa_in_servizio_mod" type="text" name="data_messa_in_servizio_mod"   value="<%if(campione.getData_messa_in_servizio()!=null){out.println(sdf.format(campione.getData_messa_in_servizio()));}%>" data-date-format="dd/mm/yyyy"/>
    </div>
       </div> 
       
       
              <div class="form-group">
        <label for="distributore" class="col-sm-3 control-label">Ubicazione:</label>
        <div class="col-sm-9">
                      <input class="form-control required" id="ubicazione_mod" type="text" name="ubicazione_mod"  value="<%if(campione.getUbicazione()!=null){out.println(campione.getUbicazione());}%>" />
    </div>
       </div> 
       
          <div class="form-group">
        <label for="inputName" class="col-sm-3 control-label">Campo di misura:</label>
        <div class="col-sm-9">
                      
                      <input class="form-control" id="campo_misura_mod" type="text" name="campo_misura_mod"   value="<%if(campione.getCampo_misura()!=null){out.println(campione.getCampo_misura());}%>"/>
                      																											
    </div>																																
       </div>
       <div class="form-group">
        <label for="inputName" class="col-sm-3 control-label">Unità di formato:</label>
        <div class="col-sm-9">
                      
                      <input class="form-control" id="unita_formato_mod" type="text" name="unita_formato_mod"  value="<%if(campione.getUnita_formato()!=null){out.println(campione.getUnita_formato());} %>"/>
    </div>
       </div>
       
       
                  <div class="form-group">
          <label  class="col-sm-3 control-label">Descrizione attività di manutenzione:</label>

         <div class="col-sm-4">
         <select class="form-control select2" id="select_manutenzione_mod" data-placeholder="Seleziona descrizione manutenzione..." name="select_manutenzione_mod" style="width:100%">
         <option value=""></option>
         <option value="Controllo presenza di ammaccature o malformazioni (visivo)">Controllo presenza di ammaccature o malformazioni (visivo)</option>
         <option value="Controllo presenza di ossidazione / ruggine (visivo)">Controllo presenza di ossidazione / ruggine (visivo)</option>
         <option value="Controllo integrità indicatore (visivo)">Controllo integrità indicatore (visivo)</option>
         <option value="Controllo integrità segmenti del display (visivo)">Controllo integrità segmenti del display (visivo)</option>
         <option value="Controllo integrità terminali di collegamento (visivo)">Controllo integrità terminali di collegamento (visivo)</option>
         <option value="Controllo dello stato delle batterie (visivo)">Controllo dello stato delle batterie (visivo)</option>
         <option value="Controllo buono stato delle connessioni (visivo)">Controllo buono stato delle connessioni (visivo)</option>
         <option value="Controllo dello stato qualitativo (visivo)">Controllo dello stato qualitativo (visivo)</option>
         <option value="Pulizia">Pulizia</option>
         <option value="Controllo presenza grasso di vaselina (visivo)">Controllo presenza grasso di vaselina (visivo) </option>
         <option value="Controllo interno / esterno dello stato del contenitore (visivo)">Controllo interno / esterno dello stato del contenitore (visivo)</option>
         <option value="Verifica sicurezza elettrica">Verifica sicurezza elettrica</option>
          <option value="Controllo dello stato integrità del vetro (visivo)">Controllo dello stato integrità del vetro (visivo)</option>
         <option value="Controllo stato di serraggio del vetro al contenitore (pratico)">Controllo stato di serraggio del vetro al contenitore (pratico)</option>
         <option value="Controllo della presenza di elementi che ostruiscono il passaggio del fluido nel condotto (Visivo)">Controllo della presenza di elementi che ostruiscono il passaggio del fluido nel condotto (Visivo)</option>
         <option value="Controllo stato della filettatura (visivo)">Controllo stato della filettatura (visivo)</option>
         </select>
			
     	</div>
     	<div class="col-sm-5">
     	<textarea id="descrizione_manutenzione_mod" name ="descrizione_manutenzione_mod" style="width:100%" class="form-control" rows="3"><%if(campione.getDescrizione_manutenzione()!=null){out.println(campione.getDescrizione_manutenzione());} %></textarea>
     	</div>
   </div>
       
        <div class="form-group">
        <label for="inputName" class="col-sm-3 control-label">Frequenza Manutenzioni:</label>
        <div class="col-sm-9">
                      <input class="form-control" id="frequenza_manutenzione_mod" type="text" name="frequenza_manutenzione_mod"   value="<%if(campione.getFrequenza_manutenzione()!=0){out.println(campione.getFrequenza_manutenzione());} %>"/> 
                      
    </div>
       </div>
       
               <div class="form-group">
        <label for="attivita_di_taratura" class="col-sm-3 control-label">Attività Di Taratura:</label>
       
        <div class="col-sm-4">
				
         			<select  class="form-control" id="attivita_taratura_mod"  name="attivita_taratura_mod" >
						<option value="0">ESTERNA</option>
         				<option value="1">INTERNA</option>
         			
         			</select>
     	</div>
     	<div class="col-sm-1">
     	 <label for="attivita_taratura_text" class=" control-label pull-right">Presso: </label>
     	 </div>
     	<div class="col-sm-4">
     	  <input class="form-control required" id="attivita_taratura_text_mod" type="text" name="attivita_taratura_text_mod"  value="<%if(campione.getAttivita_di_taratura()!=null){out.println(campione.getAttivita_di_taratura());} %>"/>
    </div>
     	</div>    
   
       </div> 
       
             <div class="form-group">
        <label for="note_attivita_taratura" class="col-sm-3 control-label">Descrizione Attività di Taratura:</label>
        <div class="col-sm-9">
                      <input class="form-control" id="note_attivita_taratura_mod" type="text" name="note_attivita_taratura_mod"  value="<%if(campione.getNote_attivita()!=null){out.println(campione.getNote_attivita());} %>" />
    </div>
       </div> 
       
                <div class="form-group">
        <label for="inputName" class="col-sm-3 control-label">Frequenza Taratura:</label>
        <div class="col-sm-9">
                      <input class="form-control" id="freqTaratura_mod" type="number" required name="freqTaratura_mod"  value="<%=campione.getFreqTaraturaMesi() %>"/>
    </div>
       </div> 
       
              <div class="form-group">
        <label for="campo_accettabilita" class="col-sm-3 control-label">Campo Di Accettabilità:</label>
        <div class="col-sm-9">
                      <input class="form-control required" id="campo_accettabilita_mod" type="text" name="campo_accettabilita_mod"  value="<%if(campione.getCampo_accettabilita()!=null){out.println(campione.getCampo_accettabilita());}%>" />
    </div>
       </div> 
       

       <div class="form-group">
        <label for="inputName" class="col-sm-3 control-label">Data Verifica:</label>
        <div class="col-sm-9">

                      <input class="form-control datepicker" id="dataVerifica_mod" required type="text" name="dataVerifica_mod"  required value="<%
                      
                      if(campione.getDataVerifica() != null){
                    	 out.println(sdf.format(campione.getDataVerifica()));
                      }
                      %>" />
<!-- data-date-format="dd/mm/yyyy" -->
    </div>
       </div> 
     
       
         <div class="form-group">
        <label for="inputName" class="col-sm-3 control-label">Certificato:</label>
        <div class="col-sm-9">

                        <input accept="application/pdf" onChange="validateSize(this)" type="file" class="form-control" id="certificato_mod" type="text" name="certificato_mod" />
    </div>
       </div> 
       
         <div class="form-group">
        <label for="inputName" class="col-sm-3 control-label">Numero Certificato:</label>
        <div class="col-sm-9">
                      <input class="form-control" id="numeroCerificato_mod" type="text" required name="numeroCerificato_mod"  value="<%=campione.getNumeroCertificato() %>"/>
    </div>
       </div> 
       
<%--        <div class="form-group">
        <label for="ente_certificatore" class="col-sm-3 control-label">Ente Certificatore:</label>
        <div class="col-sm-9">
                      <input class="form-control required" id="ente_certificatore_mod" type="text" name="ente_certificatore_mod"  value="<%=campione.getEnte() %>" readonly/>
    </div>
       </div>  --%>


         <div class="form-group">
        <label for="inputName" class="col-sm-3 control-label">Stato Campione:</label>
        <div class="col-sm-9">

                        <select class="form-control" id="statoCampione_mod" required name="statoCampione_mod" required>
                      
                                            <%
                                     			String def1 = "";
                                            	if(campione.getStatoCampione().equals("S")){
                                            		def1 = "selected";
                                            	}
                                            %> 
                       	            	 	<option <%=def1%> value="S">In Servizio</option>
 											
                            	          <%
 											String def3 = "";
                                            	if(campione.getStatoCampione().equals("F")){
                                            		def3 = "selected";
                                            	}
                                            %> 
                            	          	<option <%=def3%> value="F">Fuori Servizio</option>
                      </select>
                      
    </div>
       </div> 
       
         
       

       
        <button type="submit" class="btn btn-danger" >Invia Modifica</button>
    <span id="errorModifica"></span>
   </form>
   
   
   
   
   
   <div id="modalStrumenti" class="modal fade" role="dialog" aria-labelledby="myLargeModalLabel">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
    
    <div class="modal-header">
        <button type="button" class="close"  aria-label="Close" id="close_modal_str"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Seleziona Strumento</h4>
      </div>
   
       <div class="modal-body">
		<div id="strumenti_content">
		
		</div>
        
  		 </div>
      
   
    <div class="modal-footer">
    	
    	<button type="button" class="btn btn-primary" onClick="selezionaStrumento()">Seleziona</button>
    </div>
     
  </div>
    </div>

</div>

<link rel="stylesheet" href="https://cdn.datatables.net/select/1.2.2/css/select.dataTables.min.css">
<script src="https://cdn.datatables.net/select/1.2.2/js/dataTables.select.min.js"></script>
<script type="text/javascript" src="plugins/datejs/date.js"></script>   
<script>


/* function caricaListaStrumenti(){
	
	exploreModal("listaStrumentiSedeNew.do","action=lista_strumenti_campione","#strumenti_content");
	$('#modalStrumenti').modal();
	
 	$('#modalStrumenti').on('shown.bs.modal', function (){
    	t = $('#tabStrumentiCampioni').DataTable();
    	
/* 		  t.columns().eq( 0 ).each( function ( colIdx ) {
			  
		 $( 'input', t.column( colIdx ).header() ).on( 'keyup', function () {
			 if(this.value <colIdx){
			 t.column( colIdx )
		      .search( this.value )
		      .draw();
			 }
		 } );
				
		 } );    
 
	t.columns.adjust().draw();
 
});   
} */




$('#modalStrumenti').on('hidden.bs.modal', function(){
	  contentID == "registro_attivitaTab";
	  
}); 

$('#close_modal_str').on('click', function(){
	$('#modalStrumenti').modal('hide');
})


	$('#select_manutenzione_mod').change(function(){	
		var selection = $(this).val();
		
		
		$('#descrizione_manutenzione_mod').append(selection+";\n");
	});
	


$(document).ready(function(){
	console.log("test");
	
	
	var tipo_camp = ${tipo_camp};
	
	$('#tipoCampione_mod').select2();
	$('#settore_mod').select2();	
	
	
	
	var selection = $('#attivita_taratura_text_mod').val();
	
	if(selection=="INTERNA"){
		$('#attivita_taratura_mod').val(1);
		$('#attivita_taratura_text_mod').attr("readonly", true);
	}else{
		$('#attivita_taratura_mod').val(0);
		$('#attivita_taratura_text_mod').attr("readonly", false);
	}
	
$('#select_manutenzione_mod').select2();
 $('.datepicker').bootstrapDP({
		format: "dd/mm/yyyy"
	});
	 
/* 	$('#dataVerifica_').bootstrapDP({
		format: "dd/mm/yyyy"
	});
	$('#data_acquisto').bootstrapDP({
		format: "dd/mm/yyyy"
	}); */
	
});


$('#formAggiornamentoCampione').on('submit',function(e){
    e.preventDefault();
    modificaCampione(<%=campione.getId() %>);

});

$('.form-control').keypress(function(e){
    if(e.key==";")
      return false;
    });


/* $(function(){
	if (!$.fn.bootstrapDP && $.fn.datepicker && $.fn.datepicker.noConflict) {
		   var datepicker = $.fn.datepicker.noConflict();
		   $.fn.bootstrapDP = datepicker;
		}

	

	
 });
 */

 $('#attivita_taratura_mod').change(function(){
	var selection = $('#attivita_taratura_mod').val();
	
	if(selection==1){
		$('#attivita_taratura_text_mod').val("INTERNA");
		$('#attivita_taratura_text_mod').attr("readonly", true);
		
	}else{
		$('#attivita_taratura_text_mod').val("");
		$('#attivita_taratura_text_mod').attr("readonly", false);
	}
	
}); 

 </script>
 
				
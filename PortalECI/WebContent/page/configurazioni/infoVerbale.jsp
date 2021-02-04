<%@page import="java.util.ArrayList"%>
<%@page import="com.google.gson.JsonArray"%>
<%@page import="com.google.gson.Gson"%>

<%@page import="it.portalECI.DTO.UtenteDTO"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>


	
<t:layout title="Dashboard" bodyClass="skin-red sidebar-mini wysihtml5-supported">
	<jsp:attribute name="body_area">
		<div class="wrapper">		
<%--   			<t:main-header  />
  			<t:main-sidebar />  --%>
  			    			
    			  <header class="main-header" style="width:100%"> 
    			  <nav class="navbar"  style="width:100%"> 
    		
    			  <div class="col-lg-12">
    			  <p style="text-align: center;margin-right:160px"><img src="images/logo_ecispa.png" style="height:50px; "><span style="font-size:30px"> <b>Eci</b> srl </span></p>
    			  </div>
    			
    			 </nav> 
    		 	 </header> 
   <!--  <div class="login-logo">
   
  </div> -->

    			
  			<!-- Content Wrapper. Contains page content -->
  			<div id="corpoframe" class="content-wrapper">
  			

  			
   				<!-- Content Header (Page header) -->
<!-- Main content -->
    			<section class="content">	
    
					<div class="row">
        				<div class="col-xs-12">
          					<div class="box">
          						<div class="row">
        							 <!-- <div class="col-xs-1">
	        						</div>		 --> 
	        						<div class="col-xs-12">
    			      				<h3 style="text-align: center;">
        				Controllo validità verbali
      				</h3>
      				</div>
      				<!-- <div class="col-xs-2">
      				</div> -->
</div><br>

            					<div class="box-body">
            					<div class="row">
        						<!-- 	<div class="col-xs-2">
	        						</div> -->
	        						<div class="col-xs-5">
	        						
	        						<label class="pull-left">Numero Verbale</label>
	        						</div>
	        						<div class="col-xs-7">
	        						<input type="text" class="form-control" value="${verbale.numeroVerbale }" readonly>
	        						</div>
	        			<!-- 			<div class="col-xs-2">
	        						</div> -->
        						</div><br>
        						
        						<div class="row">
        						<!-- 	<div class="col-xs-2">
	        						</div> -->
	        						<div class="col-xs-5">
	        						
	        						<label class="pull-left">Stato</label>
	        						</div>
	        						<div class="col-xs-7">	        
	        							<c:choose>
	        							<c:when test="${verbale.stato.id == 10}">
	        							<a><span class="label label-info" style="color:#000000 !important; background-color:${verbale.getStato().getColore(verbale.getStato().getId())} !important;">${verbale.getStato().getDescrizione()}</span> --%>
	        							
												</a>
	        							</c:when>
	        							<c:otherwise>
	        							
	        							</c:otherwise>
	        							</c:choose>						
<%-- 	        						<a><span class="label label-info" id="stato" style="color:#000000 !important; background-color:${verbale.getStato().getColore(verbale.getStato().getId())} !important;">${verbale.getStato().getDescrizione()}</span> --%>
	        							<a><span class="label label-info" id="stato"></span>
												</a>
	        						</div>
	        						<!-- <div class="col-xs-2">
	        						</div> -->
        						</div><br>
        						
        						<c:if test="${verbale.codiceCategoria == 'VAL'}" >
        						<div class="row">
        							<!-- <div class="col-xs-2">
	        						</div> -->
	        						<div class="col-xs-5">
	        						
	        						<label class="pull-left">Matricola attrezzatura</label>
	        						</div>
	        						<div class="col-xs-7">	        
	        							<input type="text" class="form-control" value="${verbale.attrezzatura.matricola_inail }" readonly>
	        						</div>
	        					<!-- 	<div class="col-xs-2">
	        						</div> -->
        						</div><br>
        						
        						</c:if>
        						
        						<div class="row">
        							<!-- <div class="col-xs-2">
	        						</div> -->
	        						<div class="col-xs-5">
	        						
	        					<c:choose>
	        						<c:when test="${verbale.codiceCategoria == 'VAL' && verbale.codiceVerifica.startsWith('GVR')}">
	        						<label class="pull-left">Data verifica funzionamento</label>
	        						</c:when>
	        						<c:otherwise >
	        						<label class="pull-left">Data verifica</label>
	        						</c:otherwise>
	        						</c:choose>
	        						</div>
	        						<div class="col-xs-7">
	        						<fmt:formatDate value="${verbale.data_verifica }"  pattern="dd/MM/yyyy" var="myDate" />      
	        						<input type="text" class="form-control" value="${myDate }" readonly> 						
	        						</div>
	        						<!-- <div class="col-xs-2">
	        						</div> -->
        						</div><br>
        						
        						
        						<div class="row">
        							<!-- <div class="col-xs-2">
	        						</div> -->
	        						<div class="col-xs-5">
	        						<c:choose>
	        						<c:when test="${verbale.codiceCategoria == 'VAL' && verbale.codiceVerifica.startsWith('GVR')}">
	        						<label class="pull-left">Data prossima verifica funzionamento</label>
	        						</c:when>
	        						<c:otherwise >
	        						<label class="pull-left">Data prossima verifica</label>
	        						</c:otherwise>
	        						</c:choose>
	        						
	        						</div>
	        						<div class="col-xs-7">
	        						<c:choose>
	        						<c:when test="${verbale.codiceCategoria == 'VAL' && verbale.codiceVerifica.startsWith('GVR')}">
	        						<fmt:formatDate value="${verbale.attrezzatura.data_prossima_verifica_funzionamento }" pattern="dd/MM/yyyy" var="myDate" />
	        						</c:when>
	        						<c:otherwise >
	        						<fmt:formatDate value="${verbale.data_prossima_verifica }" pattern="dd/MM/yyyy" var="myDate" />
	        						</c:otherwise>
	        						</c:choose>
	        						      
	        					<!-- 	<input type="text" class="form-control"  id="data_scadenza_input"  readonly> 		 -->				
	        						<input type="text" class="form-control" value="${myDate }" id="data_scadenza" style="width:100%" readonly>

	        						</div>
	        						<!-- <div class="col-xs-2">
	        						</div> -->
        						</div><br>
        						
        						<c:if test="${verbale.codiceCategoria == 'VAL' && verbale.codiceVerifica.startsWith('GVR')}">
            					
            					<div class="row">
        							<!-- <div class="col-xs-2">
	        						</div> -->
	        						<div class="col-xs-5">
	        						
	        						<label class="pull-left">Data verifica integrità</label>
	        						</div>
	        						<div class="col-xs-7">
	        						<fmt:formatDate value="${verbale.attrezzatura.data_verifica_integrita }" pattern="dd/MM/yyyy" var="myDate" />      
	        						<input type="text" class="form-control" value="${myDate }" readonly> 						
	        						</div>
	        						<!-- <div class="col-xs-2">
	        						</div> -->
        						</div><br>
        						
        						
        						<div class="row">
        							<!-- <div class="col-xs-2">
	        						</div> -->
	        						<div class="col-xs-5">
	        						
	        						<label class="pull-left">Data prossima verifica integrità</label>
	        						</div>
	        						<div class="col-xs-7">
	        						<fmt:formatDate value="${verbale.attrezzatura.data_prossima_verifica_integrita }" pattern="dd/MM/yyyy" var="myDate" />      
	        						<!-- <input type="text" class="form-control"  id="data_scadenza_integrita_input" readonly>  -->						
	        						<input type="text" class="form-control" value="${myDate }" id="data_scadenza_integrita" readonly>
	        						</div>
	        						<!-- <div class="col-xs-2">
	        						</div> -->
        						</div><br>
        						
        						
        						<div class="row">
        							<!-- <div class="col-xs-2">
	        						</div> -->
	        						<div class="col-xs-5">
	        						
	        						<label class="pull-left">Data verifica interna</label>
	        						</div>
	        						<div class="col-xs-7">
	        						<fmt:formatDate value="${verbale.attrezzatura.data_verifica_interna }" pattern="dd/MM/yyyy" var="myDate" />      
	        						<input type="text" class="form-control" value="${myDate }" readonly> 						
	        						</div>
	        						<!-- <div class="col-xs-2">
	        						</div> -->
        						</div><br>
        						
        						
        						<div class="row">
        							<!-- <div class="col-xs-2">
	        						</div> -->
	        						<div class="col-xs-5">
	        						
	        						<label class="pull-left">Data prossima verifica interna</label>
	        						</div>
	        						<div class="col-xs-7">
	        						<fmt:formatDate value="${verbale.attrezzatura.data_prossima_verifica_interna }" pattern="dd/MM/yyyy" var="myDate" />      
	        						<input type="text" class="form-control"  id="data_scadenza_interna" value="${myDate}" readonly>
	        					<!-- 	<input type="text" class="form-control"  id="data_scadenza_interna_input" readonly> 	 --> 						
	        						</div>
	        						<!-- <div class="col-xs-2">
	        						</div> -->
        						</div><br>
            					
            					
            					</c:if>
            					<div class="row">
        							<!-- <div class="col-xs-2">
	        						</div> -->
	        						<div class="col-xs-5">
	        						
	        						<label class="pull-left">Rilasciato a</label>
	        						</div>
	        						<div class="col-xs-7">
	        					      
	        						<input type="text" class="form-control" value="${ragione_sociale }" readonly> 						
	        						</div>
	        						<!-- <div class="col-xs-2">
	        						</div> -->
        						</div><br>
        						
        						
        						<div class="row">
        							<!-- <div class="col-xs-2">
	        						</div> -->
	        						<div class="col-xs-5">
	        						
	        						<label class="pull-left">Partita IVA</label>
	        						</div>
	        						<div class="col-xs-7">
	        					      
	        						<input type="text" class="form-control" value="${partita_iva }" readonly> 						
	        						</div>
	        						<!-- <div class="col-xs-2">
	        						</div> -->
        						</div><br>
            					
            					
            					<div class="row">
        							<!-- <div class="col-xs-2">
	        						</div> -->
	        						<div class="col-xs-5">
	        						
	        						<c:choose>
	        						<c:when test="${verbale.codiceCategoria == 'VAL'}">
	        						<label class="pull-left">Indirizzo attrezzatura</label>
	        						</c:when>
	        						<c:otherwise >
	        						<label class="pull-left">Indirizzo impianto</label>
	        						</c:otherwise>
	        						</c:choose>
	        						
	        					
	        						</div>
	        						<div class="col-xs-7">
	        					      
	        						<input type="text" class="form-control" value="${indirizzo }" readonly> 						
	        						</div>
	        						<!-- <div class="col-xs-2">
	        						</div> -->
        						</div><br>
        						
        						<div class="row">
        							<!-- <div class="col-xs-2">
	        						</div> -->
	        						<div class="col-xs-5">
	        						
	        						<label class="pull-left">Normativa di riferimento</label>
	        						</div>
	        						<div class="col-xs-7">
	        						<c:choose>
	        						<c:when test="${verbale.codiceCategoria == 'VIE'}">
	        						  <input type="text" class="form-control" value="D.P.R. 462/01" readonly>
	        						</c:when>
	        						<c:when test="${verbale.codiceCategoria == 'VAL'}">
	        						  <input type="text" class="form-control" value="D.M. 11/04/2011 - ALL. VII D.LGS. 81/08" readonly>
	        						</c:when>
	        						
	        						</c:choose>
	        					    
	        					   	
	        						</div>
	        						<!-- <div class="col-xs-2">
	        						</div> -->
        						</div><br><br>
        						
        						
        						<div class="row">
        							<div class="col-xs-3">
	        						</div>
	        						<div class="col-xs-2">
	        						
	        						<a class="btn btn-primary" onClick="modalEmail()">RICHIEDI COPIA DEL VERBALE</a>
	        						</div>
	        						<div class="col-xs-5">
	        					      
	        							
	        						</div>
	        						
        						</div><br>
            					

            						<!-- /.box-body -->
          						</div>
          						<!-- /.box -->
        					</div>
        					</div>
        					</div>
        					<!-- /.col -->

			

							
	<form id="formEmail" name="formEmail">						
	 <div id="myModalEmail" class="modal fade" role="dialog" aria-labelledby="myLargeModalLabel">
  
    <div class="modal-dialog modal-md" role="document">
    <div class="modal-content">
     <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Richiedi copia del verbale</h4>
      </div>
       <div class="modal-body">
       <div class="row">
        <div class="col-xs-4">
        <label>Nome</label>
		</div>
		<div class="col-xs-8">
        <input type="text" class="form-control" id="nome" name="nome" required>
		</div>
  		 </div><br>
  		 <div class="row">
        <div class="col-xs-4">
        <label>Cognome</label>
		</div>
		<div class="col-xs-8">
        <input type="text" class="form-control" id="cognome" name="cognome" required>
		</div>
  		 </div><br>
  		 <div class="row">
        <div class="col-xs-4">
        <label>Motivazione della richiesta</label>
		</div>
		<div class="col-xs-8">
        <input type="text" class="form-control" id="motivazione" name="motivazione" required>
		</div>
  		 </div><br>
  		 <div class="row">
        <div class="col-xs-4">
        <label>Indirizzo email</label>
		</div>
		<div class="col-xs-8">
        <input type="text" class="form-control" id="email" name="email" required>
		</div>
  		 </div><br>
  		 
  		  <div class="row">

      <div class="col-xs-2" style="margin-top:10px">
      <input type="checkbox" id="check1">*
      </div>
	<div class="col-xs-10">
       <p align="justify">Ai sensi del D. Lgs. 196/2003 e smi e dell'art. 7 Regolamento UE 2016/679, il sottoscritto dichiara di aver preso visione, letto e compreso il contenuto dell'<u><a target="_blank" href="gestioneInfoVerbale.do?action=informativa_privacy" >Informativa privacy</a></u> da Voi fornita ai sensi del D. Lgs. 196/2003 e smi e dell'art. 13 del Regolamento UE 2016/679.</p>
      </div>
      </div>

      <div class="modal-footer">
      
      <input type="hidden" id="id_verbale" name="id_verbale">
      <button class="btn btn-primary pull-right disabled" type="submit" id="invia_btn" >Invia</button>
     
      </div>
   
  </div>
  </div>
</div>
</form>





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
  										<div id="empty" class="label label-danger testo12"></div>
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
			<link rel="stylesheet" type="text/css" href="//cdn.jsdelivr.net/bootstrap.daterangepicker/2/daterangepicker.css" />
			</jsp:attribute>

			<jsp:attribute name="extra_js_footer">
<script type="text/javascript" src="//cdn.jsdelivr.net/bootstrap.daterangepicker/2/daterangepicker.js"></script>
<script type="text/javascript" src="plugins/datejs/date.js"></script>
  				<script type="text/javascript">

  			   $('#check1').on('ifClicked',function(e){
  					
  				   if($('#check1').is( ':checked' )){
  					   $('#invia_btn').addClass('disabled');
  				   }else{
  					 $('#invia_btn').removeClass('disabled');
  				   }
  				 });  

function modalEmail(){
	
	$('#id_verbale').val('${verbale.id}')
	$('#myModalEmail').modal();
	
}
  			
  		  	function formatDate(data){
  		  		
  		  		   var mydate =  Date.parse(data);
  		  		   
  		  		   if(!isNaN(mydate.getTime())){
  		  		   
  		  			var   str = mydate.toString("dd/MM/yyyy");
  		  		   }			   
  		  		   return str;	 		
  		  	}

  		   
  				
$('#formEmail').on('submit', function(e){
	e.preventDefault();
	submitInfoVerbaleEmail();
});


function checkScadenza(data){
	
	if(data!=null && data!=''){
		var d = data.split("/")[0];
		var M = data.split("/")[1];
		var y = data.split("/")[2]
		
		var day = parseInt(d)-1;
		if(day<10){
			day ="0"+day; 
		}
		return day+"/"+M+"/"+y;
	}else{
		return "";
	}
	
}

function checkStato(){
	
	
	var today = new Date();
	var data_funz = $('#data_scadenza').val().split("/");
	
	
	var scaduto = false;
	
	if(today > new Date(data_funz[2], (parseInt(data_funz[1])-1),data_funz[0] )){
		scaduto = true;
	}
	if($('#data_scadenza_integrita').val()!=null){
		var data_integrita = $('#data_scadenza_integrita').val().split("/");
		if(today > new Date(data_integrita[2], (parseInt(data_integrita[1])-1),data_integrita[0] )){
			scaduto = true;
		}
	}
	if($('#data_scadenza_interna').val()!=null){
		var data_interna = $('#data_scadenza_interna').val().split("/");
		if(today > new Date(data_interna[2], (parseInt(data_interna[1])-1),data_interna[0] )){
			scaduto = true;
		}
	}
	
	
	if(!scaduto){
		$('#stato').html("IN CORSO");
		//background-color:${verbale.getStato().getColore(verbale.getStato().getId())} !important;
		$('#stato').removeClass("label-info");
		$('#stato').addClass("label-success");
	}else{
		$('#stato').html("SCADUTO");
		$('#stato').removeClass("label-info");
		$('#stato').addClass("label-danger");
	}
	
}
  				
    				$(document).ready(function() {       
    					
    					
/*     					$('#data_scadenza_input').val(checkScadenza($('#data_scadenza').val()));
    					$('#data_scadenza_integrita_input').val(checkScadenza($('#data_scadenza_integrita').val()));
    					$('#data_scadenza_interna_input').val(checkScadenza($('#data_scadenza_interna').val())); */
    					checkStato()
    				});


	    		

	    			
  				</script>
	</jsp:attribute> 
</t:layout>
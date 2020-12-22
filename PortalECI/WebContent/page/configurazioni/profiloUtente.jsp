<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<%@page import="it.portalECI.DTO.CompanyDTO"%>
<%@page import="it.portalECI.DTO.UtenteDTO"%>



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
        Dati Profilo Utente
        <small>Modifica i dati personali ed i dati aziendali</small>
      </h1>
      
    </section>
    <div style="clear: both;"></div>    
    <!-- Main content -->
    <section class="content">
    
    
    <div class="nav-tabs-custom">
            <ul class="nav nav-tabs">
              <li class="active"><a href="#datipersonali" data-toggle="tab" aria-expanded="true" onclick="openCity(event, 'datPer')">Dati Personali</a></li>
              
            </ul>
            <div class="tab-content">
              <div class="tab-pane active" id="datipersonali">
                
                
                <form class="form-horizontal">
                  <div class="form-group">
                    <label for="inputName" class="col-sm-2 control-label">Codice Utente:</label>
 
                    <div class="col-sm-10">
 						<input class="form-control" class="form-control" id="codUser" type="text" name="codUser" disabled="disabled" value="${userObj.id}"/>
                     </div>
     				</div>

                  <div class="form-group">
                    <label for="inputEmail" class="col-sm-2 control-label">Nome</label>

                    <div class="col-sm-10">
						<input class="form-control" id="nome" type="text" name="nome" disabled="disabled" value="${userObj.getNome()}" />
                    </div>
                  </div>

   <div class="form-group">
        <label for="inputName" class="col-sm-2 control-label">Cognome:</label>
        <div class="col-sm-10">
                      <input class="form-control" id="cognome" type="text" name="cognome" disabled="disabled"  value="${userObj.getCognome()}"/>
    </div>
     </div>
       <div class="form-group">
        <label for="inputName" class="col-sm-2 control-label">Indirizzo:</label>
        <div class="col-sm-10">
                      <input class="form-control" id="indirizzoUsr" type="text" name="indirizzoUsr" disabled="disabled"  value="${userObj.getIndirizzo()}"/>
    </div>
     </div>
       <div class="form-group">
        <label for="inputName" class="col-sm-2 control-label">Comune:</label>
        <div class="col-sm-10">
                      <input class="form-control" id="comuneUsr" type="text" name="comuneUsr" disabled="disabled" value="${userObj.getComune()}"/>
    </div>
     </div>
       <div class="form-group">
        <label for="inputName" class="col-sm-2 control-label">Cap:</label>
        <div class="col-sm-10">
                      <input class="form-control" id="capUsr" type="text" name="capUsr" disabled="disabled"  value="${userObj.getCap()}"/>
    </div>
     </div>
       <div class="form-group">
        <label for="inputName" class="col-sm-2 control-label">E-mail:</label>
        <div class="col-sm-10">
                      <input class="form-control" id="emailUsr" type="text" name="emailUsr" disabled="disabled"  value="${userObj.getEMail()}"/>
    </div>
     </div>
       <div class="form-group">
        <label for="inputName" class="col-sm-2 control-label">Telefono:</label>
        <div class="col-sm-10">
                      <input class="form-control" id="telUsr" type="text" name="telUsr" disabled="disabled"  value="${userObj.getTelefono()}"/>
    </div>
       </div> 
       <div class="form-group">
        <label for="inputName" class="col-sm-2 control-label">File Firma:</label>
        <div class="col-sm-7">
      	  <!-- <div class="col-md-3"> -->
      	<c:if test="${userObj.file_firma !=null && !userObj.file_firma.equals('')}">
      		<a class="btn btn-primary" onClick="callAction('areaUtente.do?action=download_img')" title="Click per scaricare l'immagine della firma"><i class="fa fa-image"></i></a>
      	</c:if>
      	</div>
           <div class="col-md-3">
		       <span class="btn btn-primary fileinput-button " >
		        <i class="glyphicon glyphicon-plus"></i>
		        <span>Seleziona un file...</span>

		        <input id="fileupload" accept=".jpg,.JPG,.jpeg,.JPEG,.png,.PNG,.btm,.BTM,.tiff,.TIFF"  type="file" name="fileupload" class="form-control" />
		   	 </span>
		</div>
    </div>
       
       <div class="form-group">
        <label for="inputName" class="col-sm-2 control-label">PIN Firma Digitale:</label>
        <div class="col-sm-7">
                      <input class="form-control" id="pinFirmaUsr" type="password" name="pinFirmaUsr" disabled="disabled"  value="${userObj.getPin_firma()}"/>
    </div>
    <div class="col-sm-3">
                      <a class="btn btn-info" onClick="openModalModificaPIN()"><i class="fa fa-pencil"></i> Modifica</a>
    </div>
       </div> 
     
         <div class="form-group">
      

       <div class="col-sm-offset-2 col-sm-10">
                   <div class="box-footer">
		<button type="button" id="modificaUsr" onclick="enableInput('#datipersonali')" class="btn btn-primary" >Modifica Dati</button>
 		<button type="button" id="inviaUsr" onclick="salvaUsr()" class="btn btn-danger" disabled="disabled"  >Invia Modifica</button>
</div>   
              </div>




  </div>  
        </form>
       <div id="usrError"></div>
    </div> 
		          


                
              <!-- /.tab-pane -->
              <div class="tab-pane" id="datiazienda">
                

         <form class="form-horizontal">
   <div class="form-group">
        <label for="inputName" class="col-sm-2 control-label">Denominazione:</label>
        <div class="col-sm-10">
                      <input class="form-control" id="denominazioneConpany" type="text" name="name" disabled="disabled" value="${usrCompany.getDenominazione()}"/>
    </div>
     </div>
   <div class="form-group">
        <label for="inputName" class="col-sm-2 control-label">PartitaIva:</label>
        <div class="col-sm-10">
                      <input class="form-control" id="pivaCompany" type="text" name="name" disabled="disabled" value="${usrCompany.getpIva()}"/>
    </div>
     </div>
       <div class="form-group">
        <label for="inputName" class="col-sm-2 control-label">Indirizzo:</label>
        <div class="col-sm-10">
                      <input class="form-control" id="indCompany" type="text" name="name" disabled="disabled" value="${usrCompany.getIndirizzo()}"/>
    </div>
     </div>
       <div class="form-group">
        <label for="inputName" class="col-sm-2 control-label">Comune:</label>
        <div class="col-sm-10">
                      <input class="form-control" id="comuneCompany" type="text" name="name" disabled="disabled" value="${usrCompany.getComune()}"/>
    </div>
     </div>
       <div class="form-group">
        <label for="inputName" class="col-sm-2 control-label">Cap:</label>
        <div class="col-sm-10">
                      <input class="form-control" id="capCompany" type="text" name="name" disabled="disabled" value="${usrCompany.getCap()}"/>
    </div>
     </div>
       <div class="form-group">
        <label for="inputName" class="col-sm-2 control-label">E-mail:</label>
        <div class="col-sm-10">
                      <input class="form-control" id="emailCompany" type="text" name="name" disabled="disabled" value="${usrCompany.getMail()}"/>
    </div>
     </div>
       <div class="form-group">
        <label for="inputName" class="col-sm-2 control-label">Telefono:</label>
        <div class="col-sm-10">
                      <input class="form-control" id="telCompany" type="text" name="name" disabled="disabled" value="${usrCompany.getTelefono()}"/>
    </div>
     </div>
       
         <div class="form-group">
      

       <div class="col-sm-offset-2 col-sm-10">
                   <div class="box-footer">
		<button type="button" id="modificaCompany" onclick="enableInput('#datiazienda')" class="btn btn-primary" >Modifica Dati</button>
 		<button type="button" id="inviaCompany" onclick="salvaCompany()" class="btn btn-danger" disabled >Invia Modifica</button>
</div>   
              </div>
              </div>
     </form>
     <div id="companyError"></div>
 </div>

              <!-- /.tab-pane -->

              <div class="tab-pane" id="profilo">
                 Il tuo profilo utente è di tipo: ${userObj.getTipoutente()}
              </div>
              <!-- /.tab-pane -->
            </div>
            <!-- /.tab-content -->
          </div>
    


    

</section>
   

</div>  
   
   
  </div>    


       <div id="modalModificaPin" class=" modal fade" role="dialog" aria-labelledby="myLargeModalLabel">
    <div class="modal-dialog" role="document">
    <div class="modal-content">
     <div class="modal-header">
        <a type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></a>
        <h4 class="modal-title" id="myModalLabelHeader">Modifica PIN</h4>
      </div>
       <div class="modal-body">
	 <c:if test="${!userObj.getPin_firma().equals('0000') }"> 
       
       <div class="row">
       <div class="col-sm-3">
			
        <label >PIN attuale:</label>
        </div>
        
        <div class="col-sm-9">
                      <input class="form-control" id="pin_attuale" type="password" name="pin_attuale"/>
   			 </div>
     		</div><br>
     		</c:if>
       <div class="row">
       <div class="col-sm-3">
			
        <label >Nuovo PIN:</label>
        </div>
        
        <div class="col-sm-9">
                      <input class="form-control" id="nuovo_pin" type="password" name="nuovo_pin"/>
   			 </div>
     		</div><br>
     		<div class="row">
       <div class="col-sm-3">
			
        <label >Conferma PIN:</label>
        </div>
        
        <div class="col-sm-9">
                      <input class="form-control" id="conferma_pin" type="password" name="conferma_pin"/>
   			 </div>
     		</div>

   
  		 </div>
      <div class="modal-footer">
 		<div class="row">
 		<div class="col-sm-2">
 		<a id="close_button" type="button" class="btn btn-info pull-left" onClick="checkPIN()">Salva</a> 
 		</div>
 		<div class="col-sm-10">
 		
 		<label class="pull-left" id="result_label" style="display:none"></label>
 		</div>
 		</div>
         
         
         </div>
     
    </div>
  </div>
</div>
 
  <!-- /.content-wrapper -->



<div id="myModalError" class="modal fade" role="dialog" aria-labelledby="myLargeModalLabel">
    							<div class="modal-dialog modal-sm" role="document">
        							<div class="modal-content">
	    
    									<div class="modal-header">
        									<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        									<h4 class="modal-title" id="myModalLabel">Attenzione</h4>
      									</div>
    									<div class="modal-content">
       										<div class="modal-body" id="myModalErrorContent"></div>      
    									</div>
     									<div class="modal-footer">
    										<button type="button" class="btn btn-outline" data-dismiss="modal">Chiudi</button>
    									</div>
  									</div>
    							</div>
							</div>

	
  <t:dash-footer />
  

  <t:control-sidebar />
   

<!-- </div> -->
<!-- ./wrapper -->

</jsp:attribute>


<jsp:attribute name="extra_css">


</jsp:attribute>

<jsp:attribute name="extra_js_footer">
    
<script src="plugins/jqueryuploadfile/js/jquery.fileupload.js"></script>
<script src="plugins/jqueryuploadfile/js/jquery.fileupload-process.js"></script>
<script src="plugins/jqueryuploadfile/js/jquery.fileupload-validate.js"></script>
<script src="plugins/jqueryuploadfile/js/jquery.fileupload-ui.js"></script>
<script src="plugins/fileSaver/FileSaver.min.js"></script>

<script type="text/javascript">


$('#fileupload').fileupload({
	 url: "areaUtente.do?action=upload_firma",
	 dataType: 'json',	 
	 getNumberOfFiles: function () {
	     return this.filesContainer.children()
	         .not('.processing').length;
	 }, 
	 start: function(e){
	 	pleaseWaitDiv = $('#pleaseWaitDialog');
	 	pleaseWaitDiv.modal();
	 	
	 },
	 singleFileUploads: false,
	  add: function(e, data) {
	     var uploadErrors = [];
	     var acceptFileTypes = /(\.|\/)(gif|jpg|jpeg|tiff|png|bmp)$/i;
	     
	     for(var i =0; i< data.originalFiles.length; i++){
	    	 if(data.originalFiles[i]['name'].length && !acceptFileTypes.test(data.originalFiles[0]['name'])) {
		         uploadErrors.push('Tipo del File '+data.originalFiles[i]['name']+' non accettato. ');
		         break;
		     }	 
	    	 if(data.originalFiles[i]['size'] > 30000000) {
		         uploadErrors.push('File '+data.originalFiles[i]['name']+' troppo grande, dimensione massima 30mb');
		         break;
		     }
	     }	     		     
	     if(uploadErrors.length > 0) {
	     	$('#myModalErrorContent').html(uploadErrors.join("\n"));
	 			$('#myModalError').removeClass();
	 			$('#myModalError').addClass("modal modal-danger");
	 			$('#myModalError').modal('show');
	     } 
	     else {
	         data.submit();
	     }  
	 },
	
	 done: function (e, data) {
	 		
	 	pleaseWaitDiv.modal('hide');
	 	
	 	if(data.result.success){
	 		$('#myModalAllegatiArchivio').modal('hide');
	 		$('#myModalErrorContent').html(data.result.messaggio);
			$('#myModalError').removeClass();
			$('#myModalError').addClass("modal modal-success");
			$('#myModalError').modal('show');
	 	}else{		 			
	 			$('#myModalErrorContent').html(data.result.messaggio);
	 			$('#myModalError').removeClass();
	 			$('#myModalError').addClass("modal modal-danger");
	 			$('#report_button').show();
	 			$('#visualizza_report').show();
	 			$('#myModalError').modal('show');
	 		}
	 },
	 fail: function (e, data) {
	 	pleaseWaitDiv.modal('hide');

	     $('#myModalErrorContent').html(errorMsg);
	     
	 		$('#myModalError').removeClass();
	 		$('#myModalError').addClass("modal modal-danger");
	 		$('#report_button').show();
	 		$('#visualizza_report').show();
	 		$('#myModalError').modal('show');

	 		$('#progress .progress-bar').css(
	                'width',
	                '0%'
	            );
	 },
	 progressall: function (e, data) {
	     var progress = parseInt(data.loaded / data.total * 100, 10);
	     $('#progress .progress-bar').css(
	         'width',
	         progress + '%'
	     );

	 }
});		



function openModalModificaPIN(){
	$('#result_label').hide();
	$('#pin_attuale').css('border', '1px solid #d2d6de');
	$('#nuovo_pin').css('border', '1px solid #d2d6de');
	$('#conferma_pin').css('border', '1px solid #d2d6de');
	$('#pin_attuale').val("");
	$('#nuovo_pin').val("");
	$('#conferma_pin').val("");
	$("#modalModificaPin").modal();
	
}

function checkPIN(){
	$('#result_label').hide();
	var pin = $('#nuovo_pin').val();
	var confPin = $('#conferma_pin').val();
	var pin_attuale = $('#pin_attuale').val();
	
	$('#pin_attuale').css('border', '1px solid #d2d6de');
	$('#pin_attuale').css('border', '1px solid #d2d6de');
	$('#nuovo_pin').css('border', '1px solid #d2d6de');
	$('#conferma_pin').css('border', '1px solid #d2d6de');
	if(pin_attuale!=null&&isNaN(pin_attuale)){
		$('#result_label').html("Attenzione! Il PIN deve essere un numero!");
		$('#pin_attuale').css('border', '1px solid #f00');
		$('#result_label').css("color", "red");
		$('#result_label').show();
	}
	else if(pin_attuale!=null&&pin_attuale.length!=4){
		$('#result_label').html("Attenzione! Il PIN deve essere di 4 caratteri!");
		$('#result_label').css("color", "red");
		$('#pin_attuale').css('border', '1px solid #f00');
		$('#result_label').show();
	}
	else if(isNaN(pin)){
		$('#result_label').html("Attenzione! Il PIN deve essere un numero!");
		$('#nuovo_pin').css('border', '1px solid #f00');
		$('#result_label').css("color", "red");
		$('#result_label').show();
	}	
	else if(pin.length!=4){
		$('#result_label').html("Attenzione! Il PIN deve essere di 4 caratteri!");
		$('#result_label').css("color", "red");
		$('#nuovo_pin').css('border', '1px solid #f00');
		$('#result_label').show();
	}
	
	else if(pin!=confPin){
		$('#result_label').html("Attenzione! Conferma PIN fallita!");
		$('#result_label').css("color", "red");
		$('#conferma_pin').css('border', '1px solid #f00');
		$('#result_label').show();
	}
	else if(pin =="0000"){
		$('#result_label').html("Attenzione! Il PIN non può essere 0000");
		$('#nuovo_pin').css('border', '1px solid #f00');
		$('#result_label').css("color", "red");
		$('#result_label').show();
	}
	else{
		modificaPinFirma(pin, pin_attuale);
	}
	
}



function openCity(evt, cityName) {
    var i, tabcontent, tablinks;
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }
    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }
   // document.getElementById(cityName).style.display = "block";
    evt.currentTarget.className += " active";
}

$('#myModalError').on('hidden.bs.modal', function(){

	if($('#myModalError').hasClass("modal-success")){
		location.reload();
	}
	
});


// Get the element with id="defaultOpen" and click on it
//document.getElementById("defaultOpen").click();
</script> 
</jsp:attribute> 
</t:layout>



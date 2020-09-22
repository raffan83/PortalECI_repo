<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


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
        Lista Attrezzature        
        <small>Elenco Attrezzature Portale</small>
      </h1>
   
    </section>
    <div style="clear: both;"></div>    
  <!-- Main content -->
    <section class="content">

<div class="row">
        <div class="col-xs-12">
       
          <div class="box">
          <div class="box-header">
             
                        <div class="row">
        <div class="col-xs-6">


    <div class="form-group">
    

    
                  <label>Cliente</label>
                 <%--  <select name="select1" id="select1" data-placeholder="Seleziona Cliente..."  class="form-control select2" aria-hidden="true" data-live-search="true" style="width:100%">
                  <option value=""></option>
                  <option value="0">TUTTI</option>
                      <c:forEach items="${listaClienti}" var="cliente">
                           <option value="${cliente.__id}">${cliente.nome} </option> 
                     </c:forEach>
                  </select> --%>
                  
                  <select name="cliente_appoggio" id="cliente_appoggio" class="form-control select2" aria-hidden="true" data-live-search="true" style="width:100%;display:none" required>
                
                      <c:forEach items="${listaClienti}" var="cliente">
                     
                           <option value="${cliente.__id}">${cliente.nome}</option> 
                         
                     </c:forEach>

                  </select> 
                  <input id="select1" name="select1" class="form-control" style="width:100%">
        </div>

  </div>
    <div class="col-xs-6"> 


     <div class="form-group">
                  <label>Sede</label>
                  <select name="select2" id="select2" data-placeholder="Seleziona Sede..."  disabled class="form-control select2" aria-hidden="true" data-live-search="true" style="width:100%">
                   <option value=""></option>
             		<c:forEach items="${listaSedi}" var="sedi">
                        <option value="${sedi.__id}_${sedi.id__cliente_}">${sedi.descrizione} - ${sedi.indirizzo} - ${sedi.comune} (${sedi.siglaProvincia})</option>              
                     	</c:forEach>
                  </select>
        </div>

  
</div>
</div>

          </div>
            <div class="box-body">

<div class="row">
	<div class="col-xs-12">
	
	 <div id="boxLista" class="box box-danger box-solid">
<div class="box-header with-border">
	 Lista
	<div class="box-tools pull-right">
		
		<button data-widget="collapse" class="btn btn-box-tool"><i class="fa fa-minus"></i></button>

	</div>
</div>
<div class="box-body">
		<div id="posTab">LISTA VUOTA</div>
</div>
</div>
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

<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/lodash@4.17.11/lodash.min.js"></script> 
  <script type="text/javascript">

  var json_obj = '${json}';
  if(json_obj!= ''){
	  var json = JSON.parse(json_obj);  
  }
  
  
  

    $("#select1").change(function() {
    
  	  if ($(this).data('options') == undefined) 
  	  {
  	    /*Taking an array of all options-2 and kind of embedding it on the select1*/
  	    $(this).data('options', $('#select2 option').clone());
  	  }
  	  
  	  var id = $(this).val();
  	  
  	  if(id == 0){
  		$('#select2').val("");
  		$("#select2").prop("disabled", true);
  		$("#select2").change();  
  	  }else{
  		  
  		var options = $(this).data('options');
  		
	  	  var opt=[];

	  	 if(json!=null){
	 	  	var cl = parseInt(id);
	 	  	
	  		  for(var i = 0; i<json.length;i++){
	  			  if(json[i][0] == cl && json[i][1] == 0){
	  			
	  				  opt.push("<option value = 0>Non Associate</option>");  
	  				  break;
	  			  }
	  		 } 
	  		 
	  	 }else{
	  		  opt.push("<option value = 0>Non Associate</option>");
	  	 }
	  	
	
	  	   for(var  i=0; i<options.length;i++)
	  	   {
	  		var str=options[i].value; 
	  	
	  		if(str.substring(str.indexOf("_")+1,str.length)==id)
	  		{
	  			opt.push(options[i]);
	  		}   
	  	   }
	  	 $("#select2").prop("disabled", false);
	  	 
	  	  $('#select2').html(opt);
	
	  		$("#select2").change();  
	  		
  	  }
	  	  

  	
  	});
    
    $(document).ready(function() {
    
    	
    	$("#select2").select2();
    	initSelect2('#select1');
      	 
   
    });
   
    
    $("#select2").change(function(e){
		
          //get the form data using another method 
          var sede = $("#select2").val();
          var cliente = $("#select1").val();
      	
          if(sede==""){
        	   sede = null;
          }

          dataString ="action=cliente_sede&id_cliente="+ cliente+"&id_sede="+sede;
          exploreModal("listaAttrezzature.do",dataString,"#posTab",function(data,textStatus){
        	  $('#myModal').on('hidden.bs.modal', function (e) {
             	  	$('#noteApp').val("");
             	 	$('#empty').html("");
             	 	$('body').removeClass('noScroll');
             	 	$(document.body).css('padding-right', '0px');
             	});
        	  
 			
        	  $('#myModalError').on('hidden.bs.modal', function (e) {
        		  
        		  var input = $("#uploadSuccess").val();
        		  if(input){
        			  $('#myModal').modal("hide");
				
        		  }

        	   	 	
        	   	 	
        	   	});


        		  
        	  
          });
         
          
    });
    
    
    var options =  $('#cliente_appoggio option').clone();
    function mockData() {
    	  return _.map(options, function(i) {		  
    	    return {
    	      id: i.value,
    	      text: i.text,
    	    };
    	  });
    	}
    	


    function initSelect2(id_input, placeholder) {

   	 if(placeholder==null){
  		  placeholder = "Seleziona Cliente...";
  	  }
    	$(id_input).select2({
    	    data: mockData(),
    	    placeholder: placeholder,
    	    multiple: false,
    	    // query with pagination
    	    query: function(q) {
    	      var pageSize,
    	        results,
    	        that = this;
    	      pageSize = 20; // or whatever pagesize
    	      results = [];
    	      if (q.term && q.term !== '') {
    	        // HEADS UP; for the _.filter function i use underscore (actually lo-dash) here
    	        results = _.filter(x, function(e) {
    	        	
    	          return e.text.toUpperCase().indexOf(q.term.toUpperCase()) >= 0;
    	        });
    	      } else if (q.term === '') {
    	        results = that.data;
    	      }
    	      q.callback({
    	        results: results.slice((q.page - 1) * pageSize, q.page * pageSize),
    	        more: results.length >= q.page * pageSize,
    	      });
    	    },
    	  });
    }
    
  </script>
</jsp:attribute> 
</t:layout>

 
 
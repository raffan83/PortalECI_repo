<%@page import="java.util.ArrayList"%>
<%@page import="com.google.gson.JsonArray"%>
<%@page import="com.google.gson.Gson"%>

<%@page import="it.portalECI.DTO.RuoloDTO"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%

%>
	

 <table id="tabComunicazioni" class="table table-bordered table-hover dataTable table-striped" role="grid" width="100%">
 	<thead>
 		<tr class="active">
 			<td>ID</td> 			
 			<th>Descrizione</th>
  			<td>Action</td>
 		</tr>
 	</thead>
 
 	<tbody>
 
 		<c:forEach items="${listaComunicazioni}" var="comunicazione" varStatus="loop">

	 		<tr <c:if test = "${utente.checkComunicazione(comunicazione.id)}">class="bg-blue color-palette"</c:if> role="row" id="tabComunicazioniTr_${comunicazione.id}">

				<td>${comunicazione.id}</td>				

				<td>${comunicazione.descrizione}</td>
				<td>								
					<button id="btnAssociaComunicazione_${comunicazione.id}" onClick="associaComunicazione('${comunicazione.id}','${id_utente}')" class="btn btn-success  customTooltip" title="Abilita Categoria" <c:if test = "${utente.checkComunicazione(comunicazione.id)}">disabled="disabled"</c:if> ><i class="fa fa-check"></i></button> 
					<button id="btnDisAssociaComunicazione_${comunicazione.id}" onClick="disassociaComunicazione('${comunicazione.id}','${id_utente}')" class="btn btn-danger  customTooltip" title="Disabilita Categoria" <c:if test = "${!utente.checkComunicazione(comunicazione.id)}">disabled="disabled"</c:if> ><i class="fa fa-remove"> </i></button>
				</td>
			</tr>
		 
		</c:forEach>
 	
 	</tbody>
</table>  
<script src="plugins/jquery.appendGrid/jquery.appendGrid-1.6.3.js"></script>
<script type="text/javascript" src="https://ajax.aspnetcdn.com/ajax/jquery.validate/1.13.1/jquery.validate.min.js"></script>

<script type="text/javascript">

</script>

<script type="text/javascript">
  
	$(document).ready(function() {

		tabComunicazioni = $('#tabComunicazioni').DataTable({
  	    	paging: true, 
  	      	ordering: true,
  	      	info: true, 
  	      	searchable: false, 
  	      	targets: 0,
  	      	responsive: true,
  	      	scrollX: false,
  	      	columnDefs: [
				{ responsivePriority: 1, targets: 0 },
  	            { responsivePriority: 2, targets: 1 }
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
  	        }
			/*  ,{
  	        	text: 'I Miei Strumenti',
                action: function ( e, dt, node, config ) {
                	explore('listaCampioni.do?p=mCMP');
                },
                className: 'btn-info removeDefault'
    		},{
  	            text: 'Tutti gli Strumenti',
                action: function ( e, dt, node, config ) {
                	explore('listaCampioni.do');
                },
                className: 'btn-info removeDefault'
    		} */
  	        ]  	    	  	      
  	    });
    	
    	tabComunicazioni.buttons().container().appendTo( '#tabComunicazioni_wrapper .col-sm-6:eq(1)');
  
  		$('#tabComunicazioni thead th').each( function () {
      		var title = $('#tabComunicazioni thead th').eq( $(this).index() ).text();
      		$(this).append( '<div><input style="width:100%" type="text" class="inputsearchtable" /></div>');
  		} );

  		// DataTable
  		tabComunicazioni.buttons().container().appendTo( '#tabComunicazioni_wrapper .col-sm-6:eq(1)');
  		 	    $('.inputsearchtable').on('click', function(e){
  		 	       e.stopPropagation();    
  		 	    });
		tabComunicazioni = $('#tabComunicazioni').DataTable();
  		// Apply the search
  		tabComunicazioni.columns().eq( 0 ).each( function ( colIdx ) {
      		$( 'input', tabComunicazioni.column( colIdx ).header() ).on( 'keyup', function () {
      			tabComunicazioni
              		.column( colIdx )
              		.search( this.value )
              		.draw();
      		} );
  		} ); 
  		tabComunicazioni.columns.adjust().draw();
    	    	    
  		$('.removeDefault').each(function() {
  	   		$(this).removeClass('btn-default');
  		})
    
    	$('#myModalError').on('hidden.bs.modal', function (e) {
			if($( "#myModalError" ).hasClass( "modal-success" )){

			}		
		});
  	 
  		$('.customTooltip').tooltipster({        
  			theme: 'tooltipster-light'
     	});    		      
	});
	  
</script>
<%@page import="java.util.ArrayList"%>
<%@page import="com.google.gson.JsonArray"%>
<%@page import="com.google.gson.Gson"%>

<%@page import="it.portalECI.DTO.RuoloDTO"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%

%>
	

 <table id="tabCategorie" class="table table-bordered table-hover dataTable table-striped" role="grid" width="100%">
 	<thead>
 		<tr class="active">
 			<td>ID</td> 			
 			<th>Descrizione</th>
 			<th>Codice</th>
 			<th>Sigla</th>
  			<td>Action</td>
 		</tr>
 	</thead>
 
 	<tbody>
 
 		<c:forEach items="${listaCategorieVerifica}" var="categoria" varStatus="loop">

	 		<tr <c:if test = "${utente.checkCategoria(categoria.sigla)}">class="bg-blue color-palette"</c:if> role="row" id="tabCategoriaTr_${categoria.id}">

				<td>${categoria.id}</td>				
				<td>${categoria.descrizione}</td>
				<td>${categoria.codice }</td>
				<td>${categoria.sigla}</td>
				<td>								
					<button id="btnAssociaCategoria_${categoria.id}" onClick="associaCategoria('${categoria.id}','${idVerificatore}')" class="btn btn-success  customTooltip" title="Abilita Categoria" <c:if test = "${utente.checkCategoria(categoria.sigla)}">disabled="disabled"</c:if> ><i class="fa fa-check"></i></button> 
					<button id="btnDisAssociaCategoria_${categoria.id}" onClick="disassociaCategoria('${categoria.id}','${idVerificatore}')" class="btn btn-danger  customTooltip" title="Disabilita Categoria" <c:if test = "${!utente.checkCategoria(categoria.sigla)}">disabled="disabled"</c:if> ><i class="fa fa-remove"> </i></button>
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

		tabCategorie = $('#tabCategorie').DataTable({
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
    	
    	tabCategorie.buttons().container().appendTo( '#tabCategorie_wrapper .col-sm-6:eq(1)');
  
  		$('#tabCategorie thead th').each( function () {
      		var title = $('#tabCategorie thead th').eq( $(this).index() ).text();
      		$(this).append( '<div><input style="width:100%" type="text" class="inputsearchtable" /></div>');
  		} );

  		// DataTable
  		tabCategorie.buttons().container().appendTo( '#tabCategorie_wrapper .col-sm-6:eq(1)');
  		 	    $('.inputsearchtable').on('click', function(e){
  		 	       e.stopPropagation();    
  		 	    });
		tabCategorie = $('#tabCategorie').DataTable();
  		// Apply the search
  		tabCategorie.columns().eq( 0 ).each( function ( colIdx ) {
      		$( 'input', tabCategorie.column( colIdx ).header() ).on( 'keyup', function () {
      			tabCategorie
              		.column( colIdx )
              		.search( this.value )
              		.draw();
      		} );
  		} ); 
  		tabCategorie.columns.adjust().draw();
    	    	    
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
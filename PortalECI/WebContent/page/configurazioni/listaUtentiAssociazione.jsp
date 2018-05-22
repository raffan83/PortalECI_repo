<%@page import="java.util.ArrayList"%>
<%@page import="com.google.gson.JsonArray"%>
<%@page import="com.google.gson.Gson"%>

<%@page import="it.portalECI.DTO.RuoloDTO"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
	<%



	%>
	

  <table id="tabUtenti" class="table table-bordered table-hover dataTable table-striped" role="grid" width="100%">
 <thead><tr class="active">
 <td>ID</td>
 <th>Nome</th>
 <th>Cognome</th>
  <td>Action</td>
 </tr></thead>
 
 <tbody>
 
 <c:forEach items="${listaUtenti}" var="utente" varStatus="loop">

	 <tr <c:if test = "${utente.checkRuolo(ruolo.sigla)}">class="bg-blue color-palette"</c:if> role="row" id="tabUtentiTr_${utente.id}">

	<td>${utente.id}</td>
	<td>${utente.nome}</td>
	<td>${utente.cognome}</td>
	<td>
		
			<button id="btnAssociaUtente_${utente.id}" onClick="associaUtente('${utente.id}','${idRuolo}')" class="btn btn-success customTooltip" title="Abilita Utente" <c:if test = "${utente.checkRuolo(ruolo.sigla)}">disabled="disabled"</c:if> ><i class="fa fa-check"></i></button> 
			<button id="btnDisAssociaUtente_${utente.id}" onClick="disassociaUtente('${utente.id}','${idRuolo}')" class="btn btn-danger customTooltip" title="Disabilita Utente"  <c:if test = "${!utente.checkRuolo(ruolo.sigla)}">disabled="disabled"</c:if> ><i class="fa fa-remove"> </i></button>
		
		

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

    	tabUtenti = $('#tabUtenti').DataTable({
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
  	               },
  	               {
  	                   extend: 'colvis',
  	                   text: 'Nascondi Colonne'
  	                   
  	               }
  	                         
  	          ]
  	    	
  	      
  	    });
    	
    	tabUtenti.buttons().container().appendTo( '#tabUtenti_wrapper .col-sm-6:eq(1)');
  
  $('#tabUtenti thead th').each( function () {
      var title = $('#tabUtenti thead th').eq( $(this).index() ).text();
      $(this).append( '<div><input style="width:100%" type="text" placeholder="'+title+'" /></div>');
  } );

  // DataTable
	tabUtenti = $('#tabUtenti').DataTable();
  // Apply the search
  tabUtenti.columns().eq( 0 ).each( function ( colIdx ) {
      $( 'input', tabUtenti.column( colIdx ).header() ).on( 'keyup', function () {
    	  tabUtenti
              .column( colIdx )
              .search( this.value )
              .draw();
      } );
  } ); 
  tabUtenti.columns.adjust().draw();
    	
    	
    	
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


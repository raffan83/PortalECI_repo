<%@page import="java.util.ArrayList"%>
<%@page import="com.google.gson.JsonArray"%>
<%@page import="com.google.gson.Gson"%>

<%@page import="it.portalECI.DTO.RuoloDTO"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
	<%



	%>
	

  <table id="tabRuoli" class="table table-bordered table-hover dataTable table-striped" role="grid" width="100%">
 <thead><tr class="active">
 <td>ID</td>
 <th>Sigla</th>
 <th>Descrizione</th>
  <td>Action</td>
 </tr></thead>
 
 <tbody>
 
 <c:forEach items="${listaRuoli}" var="ruolo" varStatus="loop">

	 <tr <c:if test = "${utente.checkRuolo(ruolo.sigla)}">class="bg-blue color-palette"</c:if> role="row" id="tabRuoliTr_${ruolo.id}">

	<td>${ruolo.id}</td>
	<td>${ruolo.sigla}</td>
	<td>${ruolo.descrizione}</td>
	<td>
		
		
		
		
			<button id="btnAssociaRuolo_${ruolo.id}" onClick="associaRuolo('${ruolo.id}','${idUtente}')" class="btn btn-success  customTooltip" title="Abilita Ruolo" <c:if test = "${utente.checkRuolo(ruolo.sigla)}">disabled="disabled"</c:if> ><i class="fa fa-check"></i></button> 
			<button id="btnDisAssociaRuolo_${ruolo.id}" onClick="disassociaRuolo('${ruolo.id}','${idUtente}')" class="btn btn-danger  customTooltip" title="Disabilita Ruolo" <c:if test = "${!utente.checkRuolo(ruolo.sigla)}">disabled="disabled"</c:if> ><i class="fa fa-remove"> </i></button>

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

    	tabRuoli = $('#tabRuoli').DataTable({
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
  	              /*  ,
  	               {
  	             		text: 'I Miei Strumenti',
                 		action: function ( e, dt, node, config ) {
                 			explore('listaCampioni.do?p=mCMP');
                 		},
                 		 className: 'btn-info removeDefault'
    				},
  	               {
  	             		text: 'Tutti gli Strumenti',
                 		action: function ( e, dt, node, config ) {
                 			explore('listaCampioni.do');
                 		},
                 		 className: 'btn-info removeDefault'
    				} */
  	                         
  	          ]
  	    	
  	      
  	    });
    	
    	tabRuoli.buttons().container().appendTo( '#tabRuoli_wrapper .col-sm-6:eq(1)');
  
  $('#tabRuoli thead th').each( function () {
      var title = $('#tabRuoli thead th').eq( $(this).index() ).text();
      $(this).append( '<div><input style="width:100%" type="text" placeholder="'+title+'" /></div>');
  } );

  // DataTable
	tabRuoli = $('#tabRuoli').DataTable();
  // Apply the search
  tabRuoli.columns().eq( 0 ).each( function ( colIdx ) {
      $( 'input', tabRuoli.column( colIdx ).header() ).on( 'keyup', function () {
    	  tabRuoli
              .column( colIdx )
              .search( this.value )
              .draw();
      } );
  } ); 
  tabRuoli.columns.adjust().draw();
    	
    	
    	
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


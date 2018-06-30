<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<t:layout title="Dashboard" bodyClass="skin-red-light sidebar-mini wysihtml5-supported">

	<jsp:attribute name="body_area">

		<div class="wrapper">	
   
   			<section class="content">

      			<div class="error-page">
        			<h2 class="headline text-red"></h2>

        			<div class="error-content">
          				<h3><i class="fa fa-warning text-red"></i> Oops! Sessione Scaduta.</h3>

        			</div>
      			</div>
      			<!-- /.error-page -->

			</section>
   
   			<div id="myModalError" class="modal fade" role="dialog" aria-labelledby="myLargeModalLabel">
    			<div class="modal-dialog modal-sm" role="document">
    				<div class="modal-content">
     					<div class="modal-header">
        					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        					<h4 class="modal-title" id="myModalLabel">Attenzione</h4>
      					</div>
       					<div class="modal-body" id="myModalErrorContent" >
       						Sessione Scaduta, effettuare il login per accedere dinuovo al portale.  
  		 				</div>
      					<div class="modal-footer">      
							<a type="button" class="btn btn-primary" href="/PortalECI"  >OK</a>
							<!--    <button type="button" class="btn btn-danger"onclick="approvazioneFromModal('noApp')"   >Non Approva</button> -->
      					</div>
    				</div>
  				</div>
			</div>   
	 
  			<t:control-sidebar />
  
		</div>
		<!-- ./wrapper -->

	</jsp:attribute>


	<jsp:attribute name="extra_css">


	</jsp:attribute>

	<jsp:attribute name="extra_js_footer">
  		<script>
  			$( function() {
	  			$( "#myModalError" ).modal();
  			} );
  		</script>
	</jsp:attribute> 
</t:layout>
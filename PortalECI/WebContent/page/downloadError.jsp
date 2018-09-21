<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<t:layout title="Dashboard" bodyClass="skin-red-light sidebar-mini wysihtml5-supported">

	<jsp:attribute name="body_area">

		<div class="wrapper">	
   
   			<section class="content">

      			<div class="error-page">
        			<h2 class="headline text-red"></h2>

        			<div class="error-content" style="text-align: center; margin-left: 0px;">
          				<h3>
          					<i class="fa fa-warning text-red"></i> 
          					Errore imprevisto durante il download del file richiesto.          					
          				</h3>
          				
          				<br/><br/><br/>
       					<h4>
       						${issue}
       					</h4>       					
					</div>
      			</div>

			</section>
	 
  			<t:control-sidebar />
  
		</div>
		<!-- ./wrapper -->

	</jsp:attribute>


	<jsp:attribute name="extra_css">


	</jsp:attribute>

	<jsp:attribute name="extra_js_footer">
  		<script>
  			$( function() {
	  			
  			} );
  		</script>
	</jsp:attribute> 
</t:layout>
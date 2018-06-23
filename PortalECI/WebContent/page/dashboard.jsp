<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<t:layout title="Dashboard" bodyClass="skin-red sidebar-mini wysihtml5-supported">

	<jsp:attribute name="body_area">

		<div class="wrapper">
	
  			<t:main-header  />
	  		<t:main-sidebar />

			<!-- Content Wrapper. Contains page content -->
  			<div id="corpoframe" class="content-wrapper">
   
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

	</jsp:attribute> 
</t:layout>



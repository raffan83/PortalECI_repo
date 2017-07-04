

<%@page import="java.util.ArrayList"%>
<%@page import="com.google.gson.JsonArray"%>
<%@page import="com.google.gson.Gson"%>
 
<%@page import="it.portalECI.DTO.UtenteDTO"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
  
<%String[] buff=(String[])request.getAttribute("error"); %>
   
<t:layout title="Dashboard" bodyClass="skin-red-light sidebar-mini wysihtml5-supported">

<jsp:attribute name="body_area">

<div class="wrapper">
	
 

  <!-- Content Wrapper. Contains page content -->
  <div id="corpoframe" class="content-wrapper">
   <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
         Ops!! Errore 500
        <small>Contattare l'assistenza</small>
      </h1>
    </section>

    <!-- Main content -->
    <section class="content">


  
  <c:forEach items="${error}" var="buffered" varStatus="loop">
 	<p> ${buffered}</p>
  </c:forEach>

  
</section>
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
  
 

<%@page import="java.util.ArrayList"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/tld/utilities" prefix="utl" %>


<t:layout title="Dashboard" bodyClass="skin-red sidebar-mini wysihtml5-supported">

<jsp:attribute name="body_area">

<div class="wrapper">
	

  <t:main-sidebar />
 <t:main-header />

  <!-- Content Wrapper. Contains page content -->
  <div id="corpoframe" class="content-wrapper">
   <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1 class="pull-left">
        Gestione Verbali Admin
        <!-- <small></small> -->
      </h1>
       <a class="btn btn-default pull-right" href="/"><i class="fa fa-dashboard"></i> Home</a>
    </section>
    <div style="clear: both;"></div>    
    <!-- Main content -->
     <section class="content">
<div class="row">
      <div class="col-xs-12">

 <div class="box box-danger box-solid">
<div class="box-header with-border">
	 Gestione Verbali Admin
	<div class="box-tools pull-right">
		
		<button data-widget="collapse" class="btn btn-box-tool"><i class="fa fa-minus"></i></button>

	</div>
</div>

<div class="box-body">



<div class="row">

 <div class="col-sm-5">
 <label>ID Verbale</label>
 
 <input class="form-control" id="id" name="id">

 
</div>

 <div class="col-sm-2">
 <a class="btn btn-primary" style="margin-top:25px" onClick="ricerca()">Cerca</a>
 </div>
</div>


<div class="row">
<div class="col-sm-12">

<div id="tabella" >



 

</div>

</div>

</div>

 
</div>
</div>
</div>

</section>





</div>
   <t:dash-footer />
   
  <t:control-sidebar />
</div>
<!-- ./wrapper -->

</jsp:attribute>


<jsp:attribute name="extra_css">

	<link rel="stylesheet" href="https://cdn.datatables.net/select/1.2.2/css/select.dataTables.min.css">
	<link type="text/css" href="css/bootstrap.min.css" />

<style>
      .img-container {
            max-width: 200px;
            max-height: 200px;
            overflow: hidden;
        }
        .img-container img {
            width: 100%;
            height: auto;
        }
        
        .table th input {
    min-width: 45px !important;
}

</style>
</jsp:attribute>

<jsp:attribute name="extra_js_footer">
<script type="text/javascript" src="https://ajax.aspnetcdn.com/ajax/jquery.validate/1.13.1/jquery.validate.min.js"></script>
<script src="https://cdn.datatables.net/select/1.2.2/js/dataTables.select.min.js"></script>
<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="plugins/datepicker/locales/bootstrap-datepicker.it.js"></script> 
<script type="text/javascript" src="plugins/datejs/date.js"></script>
<script type="text/javascript">



function ricerca(){
	

	$('#id').css('border', '1px solid #d1d1d1');
	
	if($('#id').val()==''){
		$('#id').css('border', '2px solid red');
	}else{
		exploreModal("gestioneListaVerbali.do","action=ricerca&id="+$('#id').val().trim(),"#tabella");	
	}
}

$("#id").keypress(function(event) {
    if (event.which === 13) { 
        event.preventDefault();
        ricerca(); 
    }
});

$(document).ready(function() {
 
	 $('.select2').select2()
     $('.dropdown-toggle').dropdown();
	 
	 
     

});



 


 
  </script>
  
</jsp:attribute> 
</t:layout>


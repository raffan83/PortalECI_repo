<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
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
        Scadenzario
        <small>Fai click per visualizzare le scadenze dell'attrezzatura</small>
      </h1>
   
    </section>
    <div style="clear: both;"></div>    
    <!-- Main content -->
    <section class="content">

<div class="row">
        <div class="col-xs-12">
          <div class="box">
            <div class="box-body">

<div class="row">
	
	<div class="col-xs-3">
	<div class="btn btn-primary" style="background-color:#00a65a;border-color:#00a65a" onClick="addCalendarAttrezzatura(1)"></div><label style="margin-left:5px">Data verifica funzionamento</label>
	</div>
	<div class="col-xs-3">
	<div class="btn btn-primary" style="background-color:#9d201d;border-color:#9d201d"  onClick="addCalendarAttrezzatura(2)"></div><label style="margin-left:5px">Data verifica integrità</label>
	</div>
	<div class="col-xs-3">
	<div class="btn btn-primary" style="background-color:#777;border-color:#777"  onClick="addCalendarAttrezzatura(3)"></div><label style="margin-left:5px">Data verifica interna</label>
	</div>
	<div class="col-xs-3">
	<a class="btn btn-default pull-right" id="generale_btn" onClick="addCalendarAttrezzatura(0)" style="display:none"><i class="fa fa-arrow-left"></i> Torna al generale</a>
	</div>
	<div class="col-xs-12">
	 <div id="calendario" >
	</div>
	<div id="calendario2" >
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

<script type="text/javascript">

$(function () {
	
	addCalendarAttrezzatura(0);

	});
	
	
</script>
</jsp:attribute> 
</t:layout>
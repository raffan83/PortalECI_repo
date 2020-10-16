<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
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
        Scadenzario Campioni
      </h1>
       <a class="btn btn-default pull-right" href="/AccPoint"><i class="fa fa-dashboard"></i> Home</a>
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
	<div class="btn btn-primary" style="background-color:#00a65a;border-color:#00a65a" onClick="addCalendarAttivitaCampione(1)"></div><label style="margin-left:5px">Data manutenzione</label>
	</div>

	<div class="col-xs-3">
	<div class="btn btn-primary" style="background-color:#dd4b39;border-color:#dd4b39"  onClick="addCalendarAttivitaCampione(3)"></div><label style="margin-left:5px">Data taratura</label>
	</div>
	<div class="col-xs-6">
	<a class="btn btn-default pull-right" id="generale_btn" onClick="addCalendarAttivitaCampione(0)" style="display:none"><i class="fa fa-arrow-left"></i> Torna al generale</a>
	<br><br></div>
	
	<div class="col-xs-12">
	
	<!-- <a target="_blank" class="btn btn-danger pull-right" href="listaCampioni.do?action=campioni_scadenza&data_start=+$('#data_start').val()+&data_end=+$('#data_end').val()">Esporta Campioni in scadenza</a> -->
	<a class="btn btn-danger pull-right" onClick="esportaCampioniScadenzario()">Esporta Campioni in scadenza</a>
	
	</div>
	<div class="col-xs-12">
	 <div id="calendario" >
	</div>
	<div id="calendario2" >
	</div>
	</div>
	
		<input type="hidden" id="data_start">
	<input type="hidden" id="data_end">
</div>

							<div id="myModalError" class="modal fade" role="dialog" aria-labelledby="myLargeModalLabel">
    							<div class="modal-dialog modal-sm" role="document">
        							<div class="modal-content">
	    
    									<div class="modal-header">
        									<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        									<h4 class="modal-title" id="myModalLabel"></h4>
      									</div>
    									<div class="modal-content">
       										<div class="modal-body" id="myModalErrorContent"></div>      
    									</div>
     									<div class="modal-footer">
    										<button type="button" class="btn btn-outline" data-dismiss="modal">Chiudi</button>
    									</div>
  									</div>
    							</div>
							</div>

<div class="row">
	<div class="col-xs-12">
	 <div id='calendario' >
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


/* $('#button_export').click(function(){
	
	var start = $('#data_start').val();
	var end = $('#data_end').val();	
	
	$('#button_export').attr("href",'listaCampioni.do?action=campioni_scadenza&data_start='+start+'&data_end='+end);
}); */



$(function () {
	
	addCalendarAttivitaCampione(0);

	});
	
	
</script>
</jsp:attribute> 
</t:layout>



<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<t:layout title="Dashboard" bodyClass="skin-red sidebar-mini wysihtml5-supported">

	<jsp:attribute name="body_area">

		<div class="wrapper">
	
  			<t:main-header  />
	  		<t:main-sidebar />

			<!-- Content Wrapper. Contains page content -->
  			<div id="corpoframe" class="content-wrapper">
  			
  			<section class="content">
  			
  			       							<div class="row">         
       								<div class="col-xs-6">
										<div class="box box-danger box-solid">
											<div class="box-header with-border">
 											Stato verbali
												<div class="box-tools pull-right">		
													<button data-widget="collapse" class="btn btn-box-tool"><i class="fa fa-minus"></i></button>
												</div>
											</div>
											<div class="box-body" id="box_chart_1">	
        											 <div class="chart">
			             							  <canvas id="graph_1" ></canvas>
			             							  <div class="row">
			             							  <div class="col-xs-12">
			             							  <div class="info-box-sm">
			             							  <span class="info-box-icon bg-aqua"><i class="fa fa-file"></i></span>
			             							  <div class="info-box-content">
			             							   <span class="info-box-text"><h3>Verbali totali</h3></span>
			             							   <span class="info-box-number" id="verbali_totali"></span>
			             							  </div>
			             							  </div>
			             							  </div>         
       								
			             							  </div>
			         							     </div>         			
											</div>
										</div>
									</div>
									
									<div class="col-xs-6">
										<div class="box box-danger box-solid">
											<div class="box-header with-border">
 											Categorie Verbali
												<div class="box-tools pull-right">		
													<button data-widget="collapse" class="btn btn-box-tool"><i class="fa fa-minus"></i></button>
												</div>
											</div>
											<div class="box-body" id="box_chart_2">	
        											 <div class="chart">
			             							  <canvas id="graph_2"></canvas>
			             							  <div class="row">
			             							  <div class="col-xs-2"></div>         
       								<div class="col-xs-9">
			             							   <div id="chartjs-legend" style="width:100%;" ></div> 
			             							  </div>
			             							  <div class="col-xs-1"></div>
			             							  </div>
			         							     </div>         			
											</div>
										</div>
									</div>
							
									
								</div>
								
<c:if test="${userObj.checkRuolo('AM') }">								
	<div class="row">         
       								<div class="col-xs-6">
										<div class="box box-danger box-solid">
											<div class="box-header with-border">
 											Verbali per verificatore
												<div class="box-tools pull-right">		
													<button data-widget="collapse" class="btn btn-box-tool"><i class="fa fa-minus"></i></button>
												</div>
											</div>
											<div class="box-body" id="box_chart_3">	
        											 <div class="chart">
			             							  <canvas id="graph_3" ></canvas>
			             							  <div class="row">
			             							  <div class="col-xs-2"></div>         
       								<div class="col-xs-9">
			             							<!--   <div id="chartjs-legend" style="width:100%;" ></div> -->
			             							  </div>
			             							  <div class="col-xs-1"></div>
			             							  </div>
			         							     </div>         			
											</div>
										</div>
									</div>
									</div>							  			
  			       						
  </c:if>     			
  			
   </section>
	  		</div>
  			<!-- /.content-wrapper -->	
  
	  		<t:dash-footer />
  
  			<t:control-sidebar />
		</div>
		<!-- ./wrapper -->

	</jsp:attribute>

	<jsp:attribute name="extra_css">
<style>
.Mylegend { list-style: none; }

.Mylegend li { float: left; margin-right: 10px; }

.Mylegend span
{ border: 1px solid #ccc; float: left; width: 15px; height: 12px; margin: 2px; }
</style>

	</jsp:attribute>

	<jsp:attribute name="extra_js_footer">
	 <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.3/Chart.js"></script>
	<script>
	
	
	
	$(document).ready(function() {
		
		
		var lista_categorie = JSON.parse('${obj_codice_categoria}');
		var lista_verifiche = JSON.parse('${obj_codice_verifica}');
		var map_stati = JSON.parse('${map_stati}');
		var map_color = JSON.parse('${map_color}')

		
		var data_graph_1 = [];
		var label_graph_1 = [];
		var color_graph_1 = [];
		var length = Object.keys(map_stati).length;
		var totali = 0;
		for(var i = 0; i<length;i++){
			if(Object.keys(map_stati)[i]!='IN_COMPILAZIONE'){
				label_graph_1.push(Object.keys(map_stati)[i].replace("_"," "));
				data_graph_1.push(parseInt(Object.values(map_stati)[i]));
				color_graph_1.push(Object.values(map_color)[i])
				totali = totali +(parseInt(Object.values(map_stati)[i]));
			}
		}
	
		
		$('#verbali_totali').html(totali);
		

		
				
		var ctx = document.getElementById('graph_1').getContext('2d');
		
		 var myChart1 = new Chart(ctx, {
			    type: 'pie',
			    data: {
			     // labels: ["CREATO", "IN CORSO", "IN APPROVAZIONE", "APPROVATO", "SOSPESO", "RIFIUTATO", "ANNULLATO"],
			      labels: label_graph_1,
			      datasets: [ {
			        data: data_graph_1,
			        backgroundColor: color_graph_1,
			        /* backgroundColor: [
			        	

			        	"#000000",
			        	"#ffff00",
			        	"#c8b5da",
			        	"#660000",			        	
			        	"#77DD77",	
			        	"#E4E5E0",			        	
			        	"#CE3018"
			        	

				        ], */

			      },]
			    },
			    options: {
			      responsive: true,
			      showAllTooltips: true,
			      legend: {
			        display: true,  
			      },

			      
			      title: {
			            display: true,
			            text: 'Stato Verbali',
			            fontSize: 18,
			            fontColor: '#747474'
			        }
			    }
			  });
		
		var val = 0;
		var vie = 0;
		
		for(var i = 0; i<lista_categorie.length;i++){
			if(lista_categorie[i] == "VIE"){
				vie++;
			}else{
				val++;
			}
		}
		
		var gvr = 0;
		var vt = 0;
		var ve = 0;
		var vs = 0;
		var sc = 0;
		var sp = 0;
		var labels = [];
		
		for(var i = 0; i<lista_verifiche.length;i++){
			if(lista_verifiche[i] == "GVR"){
				gvr++;				
			}else if(lista_verifiche[i] == "VT"){
				vt++;
			}else if(lista_verifiche[i] == "VE"){
				ve++;
			}else if(lista_verifiche[i] == "VS"){
				vs++;
			}else if(lista_verifiche[i] == "SC"){
				sc++;
			}else if(lista_verifiche[i] == "SP"){
				sp++
			}
		}
		
		
		
		var ctx = document.getElementById('graph_2').getContext('2d');
    	
		 var myChart = new Chart(ctx, {
			    type: 'doughnut',
			    data: {
			      labels: ["VIE ", "VAL"],
			      datasets: [{
			        data: [vie, val],
			        backgroundColor: [
				          '#00205A',
				          '#747474',          
				        ],
			        labels: [
			          'VIE',
			          'VAL', 
			          
			        ]
			      }, {
			        data: [vs, vt, ve, sc, sp, gvr],
			       
			        backgroundColor: [
			        	'rgba(54, 162, 235, 0.2)',
			        	 'rgba(255, 206, 86, 0.2)',
			        	 'rgba(153, 102, 255, 0.2)',
			        	 'rgba(255, 99, 132, 0.2)',
				          '#ff3333',  
				          '#88ff4d'
				        ],
			        labels: [
			          'VS',
			          'VT', 
			          'VE',
			          'SC',
			          'SP',
			          'GVR'
			        ],
			      }, ]
			    },
			    options: {
			      responsive: true,
			      showAllTooltips: true,
			       legendCallback: function(chart) {
			    var text = [];
			    var legs = [];
			    for( var j=0; j<chart.data.datasets.length;j++)
			    {
			      for (var i = 0; i < chart.data.datasets[j].data.length; i++) 
			      {
			          var newd = { label: chart.data.datasets[j].labels[i] , color: chart.data.datasets[j].backgroundColor[i]  };

			         // if( !containsObject (newd,legs) )
			        if(!legs.includes(newd))	 
			          {
			           legs.push(newd);
			          }          
			       } 
			    }
			    
			    text.push('<ul class="Mylegend ' + chart.id + '-legend">');
			    for( var k =0;k<legs.length;k++)
			    {
			     text.push('<li><span style="background-color:' + legs[k].color + '"></span>');
			    text.push(legs[k].label);
			    text.push('</li>');    
			    if(legs[k].label == 'VAL') {
			    	text.push('</br>');    
			    }
			    }    
			    text.push('</ul>');
			    return text.join("");
			  },    
			      legend: {
			        display: false,  
			 
			  callbacks: {
		        	label: function(tooltipItem, data) {
		          	var dataset = data.datasets[tooltipItem.datasetIndex];
		            var index = tooltipItem.index;
		            return dataset.labels[index] + ': ' + dataset.data[index];
		          }
		        }
			      },
			      tooltips: {
			      	callbacks: {
			        	label: function(tooltipItem, data) {
			          	var dataset = data.datasets[tooltipItem.datasetIndex];
			            var index = tooltipItem.index;
			            return dataset.labels[index] + ': ' + dataset.data[index];
			          }
			        }
			      },
			      
			      title: {
			            display: true,
			            text: 'Categorie Verbali',
			            fontSize: 18,
			            fontColor: '#747474'
			        }
			    }
			  });
			  
			  
		 $("#chartjs-legend").html(myChart.generateLegend());
    		
    		

		var height = $('#box_chart_1').height();
		$('#box_chart_2').height(height);
		
		
		var map_verbali = JSON.parse('${map_verbali}');
		
		var data_graph_3 = [];
		var label_graph_3 = [];
		var length = Object.keys(map_verbali).length;
		for(var i = 0; i<length;i++){
			label_graph_3.push(Object.keys(map_verbali)[i].replaceAll("_"," ").replace("&prime","'"));
			data_graph_3.push(Object.values(map_verbali)[i]);
		}


		var ctx = document.getElementById('graph_3').getContext('2d');
		
		 var myChart3 = new Chart(ctx, {
			    type: 'bar',
			    data: {
			     // labels: ["CREATO", "IN CORSO", "IN APPROVAZIONE", "APPROVATO", "SOSPESO", "RIFIUTATO", "ANNULLATO"],
			      labels: label_graph_3,
			      datasets: [ {
			        data: data_graph_3,
			        backgroundColor: [
			        	'rgba(255, 99, 132, 0.2)',
				         'rgba(54, 162, 235, 0.2)',
				         'rgba(255, 206, 86, 0.2)',
				         'rgba(75, 192, 192, 0.2)',
				         'rgba(153, 102, 255, 0.2)',
				         'rgba(255, 159, 64, 0.2)',
				         'rgba(255,0,0,0.2)',
				         'rgba(46,46,255,0.2)',
				         'rgba(255,102,143,0.2)',
				         'rgba(255,240,36,0.2)',
				         'rgba(255,54,255,0.2)',
				         'rgba(107,255,235,0.2)',
				         'rgba(255,83,64,0.2)'
				        ],

			      },]
			    },
			    options: {
			      responsive: true,
			      showAllTooltips: true,
			      legend: {
			        display: false,  
			      },

			      
			      title: {
			            display: true,
			            text: 'Verbali per Verificatore',
			            fontSize: 18,
			            fontColor: '#747474'
			        }
			    }
			  });

		
	});
	
	
	</script>

	</jsp:attribute> 
</t:layout>



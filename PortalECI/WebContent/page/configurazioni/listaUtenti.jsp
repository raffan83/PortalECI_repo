<%@page import="java.util.ArrayList"%>
<%@page import="com.google.gson.JsonArray"%>
<%@page import="com.google.gson.Gson"%>

<%@page import="it.portalECI.DTO.UtenteDTO"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<% pageContext.setAttribute("newLineChar", "\'"); %>
<%

	UtenteDTO utente = (UtenteDTO)request.getSession().getAttribute("userObj");
	request.setAttribute("user",utente);
	ArrayList<UtenteDTO> listaUtentiarr =(ArrayList<UtenteDTO>)request.getSession().getAttribute("listaUtenti");

	Gson gson = new Gson();
	JsonArray listaUtentiJson = gson.toJsonTree(listaUtentiarr).getAsJsonArray();
	request.setAttribute("listaUtentiJson", listaUtentiJson);
	request.setAttribute("utente", utente);


	//System.out.println("***"+listaUtentiJson);	
%>
	
<t:layout title="Dashboard" bodyClass="skin-red sidebar-mini wysihtml5-supported">

	<jsp:attribute name="body_area">

		<div class="wrapper">
	
  			<t:main-header  />
  			<t:main-sidebar />
 
  			<!-- Content Wrapper. Contains page content -->
  			<div id="corpoframe" class="content-wrapper">
   				<!-- Content Header (Page header) -->
    			<section class="content-header">
      				<h1>
        				Lista Utenti
      				</h1>
    			</section>

    			<!-- Main content -->
    			<section class="content">

					<div class="row">
        				<div class="col-xs-12">
          					<div class="box">
          						<div class="box-header">
          						</div>
            					<div class="box-body">
              						<div class="row">
        								<div class="col-xs-12">
 											<div class="box box-danger box-solid">
												<div class="box-header with-border">
	 												Lista
													<div class="box-tools pull-right">
			
														<button data-widget="collapse" class="btn btn-box-tool"><i class="fa fa-minus"></i></button>

													</div>
												</div>
												<div class="box-body">
													<div class="row">
														<div class="col-lg-12">
															<c:if test="${user.checkPermesso('NEW_UTENTE')}">
																<button class="btn btn-primary" onClick="nuovoInterventoFromModal('#modalNuovoUtente')">Nuovo Utente</button>
															</c:if>
															<div id="errorMsg" >
															</div>
														</div>
													</div>
 													<div class="clearfix"></div>
													<div class="row" style="margin-top:20px;">
														<div class="col-lg-12">
  															<table id="tabPM" class="table table-bordered table-hover dataTable table-striped" role="grid" width="100%">
 																<thead>
 																	<tr class="active">
 																		<td>ID</td>
 																		<th>Codice</th>
 																		<th>Qualifica</th>
 																		<th>Username</th>
 																		<th>Nominativo</th>
 																		<th>Nome</th>
 																		<th>Cognome</th>
 																		<th>Codice Fiscale</th>
 																		<th>Indirizzo</th>
 																		<th>Comune</th>
 																		<th>Cap</th>
 																		<th>e-mail</th>
 																		<th>Telefono</th>
 																		<th>Company</th>
  																		<th>Action</th>
 																	</tr>
 																</thead>	 
 																<tbody>
 
 																	<c:forEach items="${listaUtenti}" var="utente" varStatus="loop">

											 							<tr role="row" id="${utente.id}-${loop.index}">

																			<td>${utente.id}</td>
																			<td>${utente.codice}</td>
																			<td>${utente.qualifica }</td>
																			<td>${utente.user}</td>
																			<td>${utente.nominativo}</td>
																			<td>${utente.nome}</td>
																			<td>${utente.cognome}</td>
																			<td>${utente.cf }</td>
																			<td>${utente.indirizzo}</td>
																			<td>${utente.comune}</td>
																			<td>${utente.cap}</td>
																			<td>${utente.EMail}</td>
																			<td>${utente.telefono}</td>
																			<td>${utente.company.denominazione}</td>
																			<td>
																				<c:if test="${user.checkPermesso('UPD_UTENTE')}">
																				<%-- <c:set var="check_vie_mod" value="${utente.ch }"></c:set> --%>
																					 <%-- <a href="#" onClick="modalModificaUtente('${utente.id}','${utente.codice}','${utente.user}','${utente.nome}','<c:out value="${fn:escapeXml(utente.cognome)}" />','${utente.indirizzo}','${utente.comune}','${utente.cap}','${utente.EMail}','${utente.telefono}','${utente.company.id}')" class="btn btn-warning "><i class="fa fa-edit"></i></a> --%> 
																					 <a href="#" onClick="modalModificaUtente('${utente.id}','${utente.codice}','${utente.user}','${utente.nome}','${utente.cognome.replace('\'', '&prime;') }','${utente.indirizzo.replace('\'', '&prime;')}','${utente.comune.replace('\'', '&prime;')}','${utente.cap}','${utente.EMail}','${utente.telefono}','${utente.company.id}','${utente.qualifica }','${utente.cf }', '${utente.tipoutente }', '${utente.idCliente }','${utente.idSede }', ${utente.checkRuolo('CLVIE')}, ${utente.checkRuolo('CLVAL') })" class="btn btn-warning "><i class="fa fa-edit"></i></a>
																					
																					
																				</c:if> 
																				<%-- <a href="#" onClick="modalEliminaUtente('${utente.id}','${utente.nominativo}')" class="btn btn-danger "><i class="fa fa-remove"></i></a>	 --%>
																			</td>
																		</tr>	
																	</c:forEach> 	
 																</tbody>
 															</table>  
 														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
            						<!-- /.box-body -->
          						</div>
          						<!-- /.box -->
        					</div>
        					<!-- /.col -->	 
  
							<div id="modalNuovoUtente" class="modal  modal-fullscreen fade" role="dialog" aria-labelledby="myLargeModalLabel">
    							<div class="modal-dialog modal-lg" role="document">
    								<div class="modal-content">
     									<div class="modal-header">
        									<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        									<h4 class="modal-title" id="myModalLabel">Nuovo Utente</h4>
      									</div>
      									<form class="form-horizontal"  id="formNuovoUtente">
       										<div class="modal-body">       
												<div class="nav-tabs-custom">
        											<!--     <ul id="mainTabs" class="nav nav-tabs">
              											<li class="active"><a href="#nuovoCampione" data-toggle="tab" aria-expanded="true"   id="nuovoCampioneTab">Dettaglio Campione</a></li>
              											<li class=""><a href="#nuoviValori" data-toggle="tab" aria-expanded="false"   id="nuoviValoriTab">Valori Campione</a></li>
            										</ul> -->
            										<div class="tab-content">
              											<div class="tab-pane  table-responsive active" id="nuovoUtente">
	            
                											<div class="form-group">
          														<label for="user" class="col-sm-2 control-label">Username:</label>
	
         														<div class="col-sm-4">
         															<input class="form-control" id="user" type="text" name="user" value="" required />
     															</div>
     	 														<label for="passw" class="col-sm-2 control-label">Password:</label>
	
         														<div class="col-sm-4">
         															<input class="form-control" id="passw" type="text" name="passw" value="" required />
     															</div>
   															</div>
   															
   															<div class="form-group">
          														<label for="tipo_utente" class="col-sm-2 control-label">Tipo Utente:</label>
         														<div class="col-sm-10">
         															<select class="form-control select2" id="tipo_utente" name="tipo_utente" required data-placeholder="Seleziona tipo utente...">
         															<option value="2">Interno</option>
         															<option value="3">Cliente</option>
         															</select>	         		
     															</div>
   															</div>
   															
	        												<div class="form-group">
          														<label for="codice" class="col-sm-2 control-label">Codice:</label>
         														<div class="col-sm-10">
         															<input class="form-control" id="codice" type="text" name="codice" value="" required />	         		
     															</div>
   															</div>
   															
   															<div class="form-group">
          														<label for="modcodice" class="col-sm-2 control-label">Qualifica:</label>

         														<div class="col-sm-10">
         															<input class="form-control" id="qualifica" type="text" name="qualifica" value="" required/>	         			
     															</div>
   															</div>
	    
	    
    														<div class="form-group">
          														<label for="nome" class="col-sm-2 control-label">Nome:</label>
         														<div class="col-sm-10">
         															<input class="form-control" id="nome" type="text" name="nome" value="" required />	         		
     															</div>
   															</div>
   															
   															<div class="form-group">
        														<label for="cognome" class="col-sm-2 control-label">Cognome:</label>
        														<div class="col-sm-10">
                      												<input class="form-control required" id="cognome" type="text" name="cognome"  value="" required/>
    															</div>
    														</div>
    
    														<div class="form-group">
        														<label for="modcf" class="col-sm-2 control-label">Codice Fiscale:</label>
        														<div class="col-sm-10">
            	          											<input class="form-control required" id="cf" type="text" name="cf"  value="" />
    															</div>
    														</div>
    														
    														<div class="form-group">
        														<label for="indirizzo" class="col-sm-2 control-label">Indirizzo:</label>
        														<div class="col-sm-10">
                      												<input class="form-control required" id="indirizzo" type="text" name="indirizzo"  value="" required/>
    															</div>
    														</div>
    														<div class="form-group">
        														<label for="comune" class="col-sm-2 control-label">Comune:</label>
        														<div class="col-sm-10">
                      												<input class="form-control required" id="comune" type="text" name="comune"  value="" required/>
    															</div>
    														</div>
    														<div class="form-group">
        														<label for="cap" class="col-sm-2 control-label">CAP:</label>
        														<div class="col-sm-10">
                      												<input class="form-control required" id="cap" type="text" name="cap"  value="" required/>
    															</div>
    														</div>
    														<div class="form-group">
        														<label for="email" class="col-sm-2 control-label">E-mail:</label>
        														<div class="col-sm-10">
                      												<input class="form-control required" type="email" id="email" type="text" name="email"  value="" required/>
    															</div>
    														</div>
     														<div class="form-group">
        														<label for="telefono" class="col-sm-2 control-label">Telefono:</label>
        														<div class="col-sm-10">
                      												<input class="form-control required" id="telefono" type="text" name="telefono"  value="" required/>
    															</div>
     														</div>
	
       														<%-- <div class="form-group">
        														<label for="comnpany" class="col-sm-2 control-label">Company:</label>
        														<div class="col-sm-10">
		                     
					   												<select class="form-control required" id="company" name="company" required>
                       													<option value="">Seleziona una Company</option>
                                            							<c:forEach items="${listaCompany}" var="company" varStatus="loop">	
 																			<option value="${company.id}" selected>${company.denominazione}</option>
	 
																		</c:forEach>
                                                                    </select>                                           
    															</div>
     														</div>   --%>   
     														<div  id="content_cliente" style="display:none">
     														<div class="form-group">
        													 <label class="col-sm-2 control-label">Cliente:</label>
																	<div class="col-sm-4">
																	                  <select name="cliente_appoggio" id="cliente_appoggio" class="form-control select2" aria-hidden="true" data-live-search="true" style="width:100%;display:none" >
																	                <option value=""></option>
																	                      <c:forEach items="${listaClienti}" var="cliente">
																	                     
																	                           <option value="${cliente.__id}">${cliente.nome}</option> 
																	                         
																	                     </c:forEach>
																	
																	                  </select> 
																	                  <input id="select1" name="select1" class="form-control" style="width:100%" required>
																	    </div>
																	
																	     <label class="col-sm-2 control-label">Sede:</label>
																	     <div class="col-sm-4">
																	                  <select name="select2" id="select2" data-placeholder="Seleziona Sede..."  disabled class="form-control select2" aria-hidden="true" data-live-search="true" style="width:100%" required>
																	                   <option value=""></option>
																	             		<c:forEach items="${listaSedi}" var="sedi">
																	                        <option value="${sedi.__id}_${sedi.id__cliente_}">${sedi.descrizione} - ${sedi.indirizzo} - ${sedi.comune} (${sedi.siglaProvincia})</option>              
																	                     	</c:forEach>
																	                  </select>
																	
																	</div>

     														</div>   
     														<div class="form-group">
        													 <label class="col-sm-2 control-label">Visualizzazioni abilitate:</label>
																	<div class="col-sm-4">
																	                 
																	                  <input id="checkvie" name="checkvie" class="form-control" style="width:100%" type="checkbox">
																	                  <label>VIE</label>
																	    </div>
																	
																	     <div class="col-sm-4">
																	<input id="checkval" name="checkval" class="form-control" style="width:100%" type="checkbox">
																	<label>VAL</label>
																	</div>

     														</div> 
     														</div>    
	 													</div>
              											<!-- /.tab-pane -->
            										</div>
            										<!-- /.tab-content -->
          										</div>	               
  												<div id="empty" class="label label-danger testo12"></div>
  		 									</div>
      										<div class="modal-footer">
												<span id="ulError" class="pull-left"></span>
												<button type="submit" class="btn btn-danger" >Salva</button>
      										</div>
        								</form>
    								</div>
  								</div>
							</div>	
							<div id="modalModificaUtente" class="modal  modal-fullscreen fade" role="dialog" aria-labelledby="myLargeModalLabel">
   								<div class="modal-dialog modal-lg" role="document">
    								<div class="modal-content">
     									<div class="modal-header">
        									<button type="button" class="close" data-dismiss="modal" aria-label="Close">
        										<span aria-hidden="true">&times;</span>
        									</button>
        									<h4 class="modal-title" id="myModalLabel">Modifica Utente</h4>
      									</div>
      									<form class="form-horizontal"  id="formModificaUtente">
       										<div class="modal-body">
	       
												<div class="nav-tabs-custom">
        											<!--     <ul id="mainTabs" class="nav nav-tabs">
              											<li class="active"><a href="#nuovoCampione" data-toggle="tab" aria-expanded="true"   id="nuovoCampioneTab">Dettaglio Campione</a></li>
              											<li class=""><a href="#nuoviValori" data-toggle="tab" aria-expanded="false"   id="nuoviValoriTab">Valori Campione</a></li>
            										</ul> -->
            										<div class="tab-content">
              											<div class="tab-pane  table-responsive active" id="modificaUtente">
	
         													<input class="form-control" id="modid" name="modid" value="" type="hidden" />                   
                											<div class="form-group">
          														<label for="moduser" class="col-sm-2 control-label">Username:</label>

         														<div class="col-sm-4">
         															<input class="form-control" id="moduser" type="text" name="moduser" value=""  />
     															</div>
     	 														<label for="modpassw" class="col-sm-2 control-label">Password:</label>
	
         														<div class="col-sm-4">
         															<input class="form-control" id="modpassw" type="text" name="modpassw" value=""  />
     															</div>
   															</div>    	
   															
   															<div class="form-group">
          														<label for="tipo_utente_mod" class="col-sm-2 control-label">Tipo Utente:</label>
         														<div class="col-sm-10">
         															<select class="form-control select2" id="tipo_utente_mod" name="tipo_utente_mod" required data-placeholder="Seleziona tipo utente...">
         															<option value="2">Interno</option>
         															<option value="3">Cliente</option>
         															</select>	         		
     															</div>
   															</div>
    														<div class="form-group">
          														<label for="modcodice" class="col-sm-2 control-label">Codice:</label>

         														<div class="col-sm-10">
         															<input class="form-control" id="modcodice" type="text" name="modcodice" value="" required/>	         			
     															</div>
   															</div>
   															
   															<div class="form-group">
          														<label for="modcodice" class="col-sm-2 control-label">Qualifica:</label>

         														<div class="col-sm-10">
         															<input class="form-control" id="modqualifica" type="text" name="modqualifica" value=""/>	         			
     															</div>
   															</div>
   															
    														<div class="form-group">
          														<label for="modnome" class="col-sm-2 control-label">Nome:</label>

         														<div class="col-sm-10">
         															<input class="form-control" id="modnome" type="text" name="modnome" value=""  />	         			
     															</div>
   															</div>	
   															<div class="form-group">
        														<label for="modcognome" class="col-sm-2 control-label">Cognome:</label>
        														<div class="col-sm-10">
            	          											<input class="form-control required" id="modcognome" type="text" name="modcognome"  value="" />
    															</div>
    														</div>
    														
    														<div class="form-group">
        														<label for="modcf" class="col-sm-2 control-label">Codice Fiscale:</label>
        														<div class="col-sm-10">
            	          											<input class="form-control required" id="modcf" type="text" name="modcf"  value="" />
    															</div>
    														</div>
	    
	    													<div class="form-group">
        														<label for="modindirizzo" class="col-sm-2 control-label">Indirizzo:</label>
        														<div class="col-sm-10">
                      												<input class="form-control required" id="modindirizzo" type="text" name="modindirizzo"  value="" />
    															</div>
    														</div>
    														<div class="form-group">
        														<label for="modcomune" class="col-sm-2 control-label">Comune:</label>
        														<div class="col-sm-10">
                      												<input class="form-control required" id="modcomune" type="text" name="modcomune"  value="" />
    															</div>
    														</div>
    														<div class="form-group">
        														<label for="modcap" class="col-sm-2 control-label">CAP:</label>
        														<div class="col-sm-10">
    	                  											<input class="form-control required" id="modcap" type="text" name="modcap"  value="" />
	    														</div>
    														</div>
    														<div class="form-group">
        														<label for="modemail" class="col-sm-2 control-label">E-mail:</label>
        														<div class="col-sm-10">
                    	  											<input class="form-control required" type="text" id="modemail"  name="modemail"  value="" />
    															</div>
    														</div>
    	 													<div class="form-group">
        														<label for="modtelefono" class="col-sm-2 control-label">Telefono:</label>
	        													<div class="col-sm-10">
                      												<input class="form-control required" id="modtelefono" type="text" name="modtelefono"  value="" />
    															</div>
     														</div>
	
       										<%-- 				<div class="form-group">
        														<label for="modcomnpany" class="col-sm-2 control-label">Company:</label>
        														<div class="col-sm-10">
	                     
					   												<select class="form-control required" id="modcompany" name="modcompany" >
                       													<option value="">Seleziona una Company</option>
                            	                						<c:forEach items="${listaCompany}" var="company" varStatus="loop">

 																			<option value="${company.id}">${company.denominazione}</option>
		 
																		</c:forEach>	                                                                    
                	      											</select>                                            
    															</div>
     														</div> --%>
     														
     														
     														<div  id="content_cliente_mod" style="display:none">
     														<div class="form-group">
        													 <label class="col-sm-2 control-label">Cliente:</label>
																	<div class="col-sm-4">
																	                  <select name="select1_mod" id="select1_mod" class="form-control select2" aria-hidden="true" data-live-search="true" style="width:100%;display:none" >
																	                <option value=""></option>
																	                      <c:forEach items="${listaClienti}" var="cliente">
																	                     
																	                           <option value="${cliente.__id}">${cliente.nome}</option> 
																	                         
																	                     </c:forEach>
																	
																	                  </select> 
																	                  <!-- <input id="select1_mod" name="select1_mod" class="form-control" style="width:100%" required> -->
																	    </div>
																	
																	     <label class="col-sm-2 control-label">Sede:</label>
																	     <div class="col-sm-4">
																	                  <select name="select2_mod" id="select2_mod" data-placeholder="Seleziona Sede..."  disabled class="form-control select2" aria-hidden="true" data-live-search="true" style="width:100%" required>
																	                   <option value=""></option>
																	             		<c:forEach items="${listaSedi}" var="sedi">
																	                        <option value="${sedi.__id}_${sedi.id__cliente_}">${sedi.descrizione} - ${sedi.indirizzo} - ${sedi.comune} (${sedi.siglaProvincia})</option>              
																	                     	</c:forEach>
																	                  </select>
																	
																	</div>

     														</div>   
     														<div class="form-group">
        													 <label class="col-sm-2 control-label">Visualizzazioni abilitate:</label>
																	<div class="col-sm-4">
																	                 
																	                  <input id="checkvie_mod" name="checkvie_mod" class="form-control" style="width:100%" type="checkbox">
																	                  <label>VIE</label>
																	    </div>
																	
																	     <div class="col-sm-4">
																	<input id="checkval_mod" name="checkval_mod" class="form-control" style="width:100%" type="checkbox">
																	<label>VAL</label>
																	</div>

     														</div> 
     														</div>    
       
		 												</div>
	              										<!-- /.tab-pane -->
            										</div>
            										<!-- /.tab-content -->
          										</div>	        
  												<div id="empty" class="label label-danger testo12"></div>
  		 									</div>
      										<div class="modal-footer">
												<span id="ulError" class="pull-left"></span>
												<button type="submit" class="btn btn-danger" >Salva</button>
      										</div>
        								</form>
    								</div>
  								</div>
							</div>	
						
							<div id="modalEliminaUtente" class="modal fade" role="dialog" aria-labelledby="myLargeModalLabel">
    							<div class="modal-dialog modal-sm" role="document">
        							<div class="modal-content">
	    
								    	<div class="modal-header">
        									<button type="button" class="close" data-dismiss="modal" aria-label="Close">
	        									<span aria-hidden="true">&times;</span>
    	    								</button>
        									<h4 class="modal-title" id="myModalLabel">Attenzione</h4>
      									</div>
    									<div class="modal-content">
       										<div class="modal-body" id="">			     	
												<input class="form-control" id="idElimina" name="idElimina" value="" type="hidden" />			
											Sei Sicuro di voler eliminare l'utente <span id="nominativoElimina"></span>               
  		 									</div>      
    									</div>
    									<div class="modal-footer">
    										<button type="button" class="btn btn-default pull-left" data-dismiss="modal">Annulla</button>
    										<button type="button" class="btn btn-danger" onClick="eliminaUtente()">Elimina</button>
    									</div>
  									</div>
    							</div>
							</div>

							<div id="myModalError" class="modal fade" role="dialog" aria-labelledby="myLargeModalLabel">
    							<div class="modal-dialog modal-sm" role="document">
        							<div class="modal-content">
	    
    									<div class="modal-header">
        									<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        									<h4 class="modal-title" id="myModalLabel">Attenzione</h4>
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
		<script src="plugins/jquery.appendGrid/jquery.appendGrid-1.6.3.js"></script>
		<script type="text/javascript" src="https://ajax.aspnetcdn.com/ajax/jquery.validate/1.13.1/jquery.validate.min.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/lodash@4.17.11/lodash.min.js"></script>
		<script type="text/javascript">


   		</script>

  		<script type="text/javascript">
  
  		
  		$('#tipo_utente').change(function(){
  			
  			var value = $(this).val();
  			
  			if(value==2){
  				
  				$('#select1').attr("disabled", true);
  				$('#select2').attr("disabled", true);
  				$('#content_cliente').hide();
  			}else{
  				initSelect2('#select1');
  				$('#select1').attr("disabled", false);
  				$('#content_cliente').show();
  			}
  			
  		});
  		
$('#tipo_utente_mod').change(function(){
  			
  			var value = $(this).val();
  			
  			if(value==2){
  				
  				$('#select1_mod').attr("disabled", true);
  				$('#select2_mod').attr("disabled", true);
  				$('#content_cliente_mod').hide();
  			}else{
  				
  				$('#select1_mod').attr("disabled", false);
  				$('#select2_mod').attr("disabled", false);
  				$('#content_cliente_mod').show();
  			}
  			
  		});
  
	    	$(document).ready(function() {
	    		
	    		
	    		$('#select2').select2();
	    		$('#select2_mod').select2();
	    		$('#select1_mod').select2();
	    		//initSelect2('#select1_mod');
    			table = $('#tabPM').DataTable({
  	    	  		paging: true, 
  	      			ordering: true,
  	      			info: true, 
  	      			searchable: false, 
  	      			targets: 0,
  	      			responsive: true,
  	      			scrollX: false,
  	      			columnDefs: [
						{ responsivePriority: 1, targets: 0 },
  	                	{ responsivePriority: 2, targets: 1 },
  	                	{ responsivePriority: 3, targets: 2 },
  	                	{ responsivePriority: 4, targets: 6 }
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
  	            	},{
  	            		extend: 'colvis',
	  	                text: 'Nascondi Colonne'  	                   
  		            }
  	    	        /*  ,{
  	        	    	text: 'I Miei Strumenti',
                	 	action: function ( e, dt, node, config ) {
                 			explore('listaCampioni.do?p=mCMP');
	                	},
                 		className: 'btn-info removeDefault'
    				},{
  	             		text: 'Tutti gli Strumenti',
                 		action: function ( e, dt, node, config ) {
	                 		explore('listaCampioni.do');
    	             	},
        	         	className: 'btn-info removeDefault'
    				} */  	                         
  	           		]
    			});
    	
  				table.buttons().container().appendTo( '#tabPM_wrapper .col-sm-6:eq(1)');
	  
  				$('#tabPM thead th').each( function () {
      				var title = $('#tabPM thead th').eq( $(this).index() ).text();
      				$(this).append( '<div><input style="width:100%" type="text" class="inputsearchtable" /></div>');
  				} );
  				
  				
  				table.buttons().container().appendTo( '#tabPM_wrapper .col-sm-6:eq(1)');
  		 	    $('.inputsearchtable').on('click', function(e){
  		 	       e.stopPropagation();    
  		 	    });
  				// DataTable
				table = $('#tabPM').DataTable();
  				// Apply the search
  				table.columns().eq( 0 ).each( function ( colIdx ) {
      				$( 'input', table.column( colIdx ).header() ).on( 'keyup', function () {
          				table
              				.column( colIdx )
              				.search( this.value )
	              			.draw();
    	  			} );
  				} ); 
	  			table.columns.adjust().draw();
  	
  				$('.removeDefault').each(function() {
  	   				$(this).removeClass('btn-default');
	  			})    	
    	
    			if (!$.fn.bootstrapDP && $.fn.datepicker && $.fn.datepicker.noConflict) {
		   			var datepicker = $.fn.datepicker.noConflict();
		   			$.fn.bootstrapDP = datepicker;
				}

				$('.datepicker').bootstrapDP({
					format: "dd/mm/yyyy",
	    			startDate: '-3d'
				});

				$('#formNuovoUtente').on('submit',function(e){
		
					$("#ulError").html("");
	    			e.preventDefault();
	    			nuovoUtente();

				});
   
				$('#formModificaUtente').on('submit',function(e){		
					$("#ulError").html("");
	    			e.preventDefault();
	    			modificaUtente();
				}); 	      
	    	});

	    	var validator = $("#formNuovoUtente").validate({
	    		showErrors: function(errorMap, errorList) {
	    	  
	    	    	this.defaultShowErrors();
	    	  	},
	    	  	errorPlacement: function(error, element) {
	    			$("#ulError").html("<span class='label label-danger'>Compilare tutti i campi obbligatori</span>");
	    		},
	    		 
	    		highlight: function(element) {
	    		    $(element).closest('.form-group').addClass('has-error');
	    			$(element).closest('.ui-widget-content input').addClass('error');
	    		        
	    		},
	    		unhighlight: function(element) {
	    		    $(element).closest('.form-group').removeClass('has-error');
	    			$(element).closest('.ui-widget-content input').removeClass('error');
	    		       
	    		}
	    	});
	  		
	    	$('#myModalError').on('hidden.bs.modal', function (e) {
				if($( "#myModalError" ).hasClass( "modal-success" )){
					callAction("listaUtenti.do");
				}
 		
  			});
	    	
	    	
	    	  var options =  $('#cliente_appoggio option').clone();
	    	    function mockData() {
	    	    	  return _.map(options, function(i) {		  
	    	    	    return {
	    	    	      id: i.value,
	    	    	      text: i.text,
	    	    	    };
	    	    	  });
	    	    	}
	    	    	


	    	    function initSelect2(id_input, placeholder) {

	    	   	 if(placeholder==null){
	    	  		  placeholder = "Seleziona Cliente...";
	    	  	  }
	    	    	$(id_input).select2({
	    	    	    data: mockData(),
	    	    	    placeholder: placeholder,
	    	    	    multiple: false,
	    	    	    // query with pagination
	    	    	    query: function(q) {
	    	    	      var pageSize,
	    	    	        results,
	    	    	        that = this;
	    	    	      pageSize = 20; // or whatever pagesize
	    	    	      results = [];
	    	    	      if (q.term && q.term !== '') {
	    	    	        // HEADS UP; for the _.filter function i use underscore (actually lo-dash) here
	    	    	        results = _.filter(x, function(e) {
	    	    	        	
	    	    	          return e.text.toUpperCase().indexOf(q.term.toUpperCase()) >= 0;
	    	    	        });
	    	    	      } else if (q.term === '') {
	    	    	        results = that.data;
	    	    	      }
	    	    	      q.callback({
	    	    	        results: results.slice((q.page - 1) * pageSize, q.page * pageSize),
	    	    	        more: results.length >= q.page * pageSize,
	    	    	      });
	    	    	    },
	    	    	  });
	    	    }

	    	    $("#select1_mod").change(function() {
	    	        
	    	    	  if ($(this).data('options') == undefined) 
	    	    	  {
	    	    	    /*Taking an array of all options-2 and kind of embedding it on the select1*/
	    	    	    $(this).data('options', $('#select2_mod option').clone());
	    	    	  }
	    	    	  
	    	    	  var id = $(this).val();
	    	    	  
	    	    		  
	    	    		var options = $(this).data('options');
	    	    		
	    	  	  	  var opt=[];
	    	  	  	 
	    	  	  		  opt.push("<option value = 0>Non Associate</option>");
	    	  	  	
	    	  	  	   for(var  i=0; i<options.length;i++)
	    	  	  	   {
	    	  	  		var str=options[i].value; 
	    	  	  	
	    	  	  		if(str.substring(str.indexOf("_")+1,str.length)==id)
	    	  	  		{
	    	  	  			opt.push(options[i]);
	    	  	  		}   
	    	  	  	   }
	    	  	  	 $("#select2_mod").prop("disabled", false);
	    	  	  	 
	    	  	  	  $('#select2_mod').html(opt);
	    	  	

	    	  	  		$("#select2_mod").change();  
	    	  	  			    	    	

	    	    	});
	    	    
	    	    $("#select1").change(function() {
	    	        
	    	    	  if ($(this).data('options') == undefined) 
	    	    	  {
	    	    	    /*Taking an array of all options-2 and kind of embedding it on the select1*/
	    	    	    $(this).data('options', $('#select2 option').clone());
	    	    	  }
	    	    	  
	    	    	  var id = $(this).val();
	    	    	  
	    	    		  
	    	    		var options = $(this).data('options');
	    	    		
	    	  	  	  var opt=[];
	    	  	  	 
	    	  	  		  opt.push("<option value = 0>Non Associate</option>");
	    	  	  	
	    	  	  	   for(var  i=0; i<options.length;i++)
	    	  	  	   {
	    	  	  		var str=options[i].value; 
	    	  	  	
	    	  	  		if(str.substring(str.indexOf("_")+1,str.length)==id)
	    	  	  		{
	    	  	  			opt.push(options[i]);
	    	  	  		}   
	    	  	  	   }
	    	  	  	 $("#select2").prop("disabled", false);
	    	  	  	 
	    	  	  	  $('#select2').html(opt);
	    	  	

	    	  	  		$("#select2").change();  
	    	  	  		
	    	    	

	    	    	
	    	    	});
	    	    
	    	    
	    	    
	    	   
  		</script>
	</jsp:attribute> 
</t:layout>
  
 

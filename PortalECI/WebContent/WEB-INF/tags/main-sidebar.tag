<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@tag import="it.portalECI.DTO.UtenteDTO"%>

<% 
	UtenteDTO user =(UtenteDTO)request.getSession().getAttribute("userObj");
%>


<!-- Left side column. contains the logo and sidebar -->
<aside class="main-sidebar">

	<!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">

    	<!-- Sidebar Menu -->
      	<ul class="sidebar-menu">
	        <li class="header">Menu</li>
        
    	     <li class="treeview">
    	     <% if(user.checkPermesso("GESTIONE_COMMESSA")){%> 
        	 	<a href="#">
        	 		<i class="fa fa-link"></i> 
        	 		<span>Commesse</span>
            		<span class="pull-right-container">
              			<i class="fa fa-angle-left pull-right"></i>
            		</span>
          		</a>
          		
          			<ul class="treeview-menu">
            			<li>
            				<a href="gestioneCommessa.do">Gestione Commessa</a>
            			</li>
          			</ul>
          		<% }%>
        	</li>
        	<% if(user.checkPermesso("GESTIONE_INTERVENTO")){%> 
        	<li class="treeview">
        	 	<a href="#">
        	 		<i class="fa fa-wrench "></i> 
        	 		<span>Interventi</span>
            		<span class="pull-right-container">
              			<i class="fa fa-angle-left pull-right"></i>
            		</span>
          		</a>
          		<%-- <% if(user.checkPermesso("GESTIONE_INTERVENTO")){%>  --%>
          			<ul class="treeview-menu">
            			<li>
            				<a href="gestioneListaInterventi.do?stato=0">Gestione Intervento</a>
            			</li>
          			</ul>
          		
        	</li>
        	<% }%>
        		<% if(user.checkPermesso("GESTIONE_VERBALI")){%> 
        	<li class="treeview">
        	 	<a href="#">
        	 		<i class="fa fa-clipboard "></i> 
        	 		<span>Verbali</span>
            		<span class="pull-right-container">
              			<i class="fa fa-angle-left pull-right"></i>
            		</span>
          		</a>
          	
          			<ul class="treeview-menu">
            			<li>
	            			<a href="gestioneListaVerbali.do">Gestione Verbali</a>
	            			<% if(user.checkRuolo("AM")){%>
	            			<a href="#" onClick="callAction('gestioneListaVerbali.do?action=lista_file')">Lista Verbali PDF</a>
	            		<% }%>
	            	
    	        		</li>
        	  		</ul>
        	  	
        	</li>
        	<% }%>
        	<% if(user.checkPermesso("GESTIONE_QUESTIONARI")){%>
        	<li class="treeview">
        	 	<a href="#">
        	 		<i class="fa fa-question"></i> 
        	 		<span>Questionari</span>
            		<span class="pull-right-container">
              			<i class="fa fa-angle-left pull-right"></i>
            		</span>
          		</a>
          		
          			<ul class="treeview-menu">
            			<li>
            				<a href="gestioneListaQuestionari.do">Gestione Questionario</a>
            			</li>
            			<% if(user.checkPermesso("NEW_QUESTIONARIO")){%>
             				<li>
            					<a href="gestioneQuestionario.do">Crea Questionario</a>
            				</li>
            			<% }%>		
          			</ul>          		
          		<% }%>
        	</li>
       		<!-- <li class="treeview">
          		<a href="#"><i class="fa fa-link"></i> <span>Downloads Utility</span>
            		<span class="pull-right-container">
              			<i class="fa fa-angle-left pull-right"></i>
            		</span>
          		</a>
          		<ul class="treeview-menu">
					<li><a href="downloadCalver.do"><i class="fa fa-link"></i>Calver Desktop</a></li>
          		</ul>
        	</li> -->
         <% if(user.checkRuolo("AM") ||(user.checkPermesso("ATTREZZATURE") && user.checkCategoria("VAL"))){%> 
        	<li class="treeview">
        	 	<a href="#">
        	 		<i class="fa fa-briefcase"></i>
        	 		<span>Archivio attrezzature</span>
            		<span class="pull-right-container">
              			<i class="fa fa-angle-left pull-right"></i>
            		</span>
          		</a>
          		
          			<ul class="treeview-menu">
            			<li>
            				<a href="listaAttrezzature.do">Attrezzature di lavoro</a>
            			</li>
            			<% if(user.checkPermesso("SCADENZARIO_ATTREZZATURE")){%>
             				<li>
            					<a href="scadenzarioAttrezzature.do">Scadenzario Attrezzature</a>
            				</li>
            			<% }%>		
          			</ul>          		
          	
        	</li>
        	<% }%>
        	 <% if(user.checkRuolo("AM") || user.checkPermesso("GESTIONE_CAMPIONI")){%> 
			<li class="treeview">
			<a href="#">
			<i class="fa fa-eyedropper"></i>
			
        	 		<span>Campioni</span>
            		<span class="pull-right-container">
              			<i class="fa fa-angle-left pull-right"></i>
            		</span>
            		</a>
			<ul class="treeview-menu">
			<li>
			<a  href="gestioneCampione.do?action=lista" >Gestione Campioni</a>
			</li>
			<% if(user.checkRuolo("AM") || user.checkPermesso("SCADENZARIO_CAMPIONI")){ %>
			<li>
			<a href="scadenzario.do" >Scadenzario</a>
			
			</li>
			<%}%>
			</ul>
			</li>
			<%} %>
        	<% if(user.checkRuolo("AM") || user.checkPermesso("ACCESS_CONFIG")){%>		
         	<li class="treeview">
          		<a href="#">
          			<i class="fa fa-group"></i> 
          			<span>Configurazioni</span>
            		<span class="pull-right-container">
              			<i class="fa fa-angle-left pull-right"></i>
            		</span>
          		</a>
          		
          			<ul class="treeview-menu">
          				<% if(user.checkPermesso("GESTIONE_UTENTI")){%>          					
							<li>
								<a href="listaUtenti.do"><i class="fa fa-group"></i>Gestione Utenti</a>
							</li>
						<% }%>
						<% if(user.checkPermesso("GESTIONE_COMPANY")){%>
							<li>
								<a href="listaCompany.do"><i class="fa fa-industry"></i>Gestione Company</a>
							</li>
						<% }%>
						<% if(user.checkPermesso("GESTIONE_RUOLI")){%>							
							<li>
								<a href="listaRuoli.do"><i class="fa fa-hand-stop-o"></i>Gestione Ruoli</a>
							</li>
						<% }%>
						<% if(user.checkPermesso("GESTIONE_PERMESSI")){%>
							<li>
								<a href="listaPermessi.do"><i class="fa fa-hand-pointer-o"></i>Gestione Permessi</a>
							</li>
						<% }%>
						<% if(user.checkPermesso("GESTIONE_ASSOCIAZIONI")){%>
							<li>
								<a href="gestioneAssociazioni.do"><i class="fa fa-hand-peace-o"></i>Gestione Associazioni</a>
							</li>
						<% }%>
						<%-- <% if(user.checkPermesso("GESTIONE_CATEGORIE_VERIFICA")){%>
							<li>
								<a href="listaCategorieVerifica.do"><i class="fa fa-archive"></i>Gestione Categorie Verifica</a>
							</li>
						<% }%> --%>
						<% if(user.checkPermesso("GESTIONE_TIPI_VERIFICA")){%>
							<li>
								<a href="listaTipiVerifica.do"><i class="fa fa-sitemap"></i>Gestione Tipi Verifica</a>
							</li>
						<% }%>
						<% if(user.checkPermesso("GESTIONE_STRUMENTI_VERIFICATORE")){%>
							<li>
								<a href="gestioneStrumentiVerificatore.do"><i class="fa fa-wrench"></i>Gestione Strumenti Verificatore</a>
							</li>
						<% }%>
					<% if(user.checkRuolo("AM")){%>
							<li>
								<a href="gestioneVersionePortale.do"><i class="fa fa-code-fork""></i>Gestione Versioni Portale</a>
							</li>
						<% }%>
						
						
						
          			</ul>          			
          		
        	</li>
        	<% }%>
        	<% if(user.checkPermesso("GESTIONE_VERBALI")){%> 
        		<li class="treeview">
        		
        		<a href="#">
          			<i class="fa fa-hourglass"></i> 
          			<span>Storico</span>
            		<span class="pull-right-container">
              			<i class="fa fa-angle-left pull-right"></i>
            		</span>
          		</a>
        		
        		<ul class="treeview-menu">
        		
        		<li>
        			<a href="gestioneStoricoVerbale.do"><i class="fa fa-clock-o"></i>Storico Verbale</a>
				</li>
        		
        		</ul>
        		</li>
        		<% }%>
        	<% if(user.checkPermesso("GESTIONE_ACCESSO_MINISTERO")){%> 
        		<li class="treeview">
        		
        		<a href="#">
          			<i class="fa fa-clipboard"></i> 
          			<span>Lista Verbali MISE</span>
            		<span class="pull-right-container">
              			<i class="fa fa-angle-left pull-right"></i>
            		</span>
          		</a>
        		
        		<ul class="treeview-menu">
        		
        		<li>
        				
	            		<a href="#" onClick="callAction('gestioneListaVerbali.do?action=accesso_ministero')">Lista Verbali Ministero</a>
	            			
				</li>
        		
        		<li>
        				
	            		<a href="#" onClick="callAction('gestioneListaVerbali.do?action=allegati_ministero')">Gestione allegati</a>
	            			
				</li>
        		
        		</ul>
        		</li>
        		
        		<% }%>
        		
        		        		
      	</ul>
      	<!-- /.sidebar-menu -->
      	
      	
      	
      	 
    </section>
    <!-- /.sidebar -->
 </aside>
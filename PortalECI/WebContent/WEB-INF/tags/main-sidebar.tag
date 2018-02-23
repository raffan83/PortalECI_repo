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
          <a href="#"><i class="fa fa-link"></i> <span>Commesse</span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
            <li><a href="gestioneCommessa.do">Gestione Commessa</a></li>
          </ul>
        </li>
       <!-- 
        <li class="treeview">
          <a href="#"><i class="fa fa-link"></i> <span>Downloads Utility</span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
			<li><a href="downloadCalver.do"><i class="fa fa-link"></i>Calver Desktop</a></li>
          </ul>
        </li> -->
         <li class="treeview">
          <a href="#"><i class="fa fa-group"></i> <span>Configurazioni</span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <% if(user.checkRuolo("AM")){%>
          <ul class="treeview-menu">
			<li><a href="listaUtenti.do"><i class="fa fa-group"></i>Gestione Utenti</a></li>
			<li><a href="listaCompany.do"><i class="fa fa-industry"></i>Gestione Company</a></li>
			<li><a href="listaRuoli.do"><i class="fa fa-hand-stop-o"></i>Gestione Ruoli</a></li>
			<li><a href="listaPermessi.do"><i class="fa fa-hand-pointer-o"></i>Gestione Permessi</a></li>
			<li><a href="gestioneAssociazioni.do"><i class="fa fa-hand-peace-o"></i>Gestione Associazioni</a></li>
          </ul>
          <% }%>
        </li>
      </ul>
      <!-- /.sidebar-menu -->
    </section>
    <!-- /.sidebar -->
  </aside>
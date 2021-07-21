<%@tag import="it.portalECI.DTO.UtenteDTO"%>
<%@ tag language="java" pageEncoding="ISO-8859-1"%>


<% 
	UtenteDTO user =(UtenteDTO)request.getSession().getAttribute("userObj");
%>
<!-- Main Header -->
<header class="main-header">

	<!-- Logo -->
    <a href="/PortalECI/login.do" class="logo">
    	<!-- mini logo for sidebar mini 50x50 pixels -->
    	<span class="logo-mini"><b><font size="2">ISPECI</font> </b></span>
    	<!-- logo for regular state and mobile devices -->
    	<span class="logo-lg"> <img src="images/logo_eci_s.png" style="height:35px"> <b> ISPECI</b> </span>
    </a>

    <!-- Header Navbar -->
    <nav class="navbar navbar-static-top" role="navigation">
    	<!-- Sidebar toggle button-->
      	<a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
        	<span class="sr-only">Toggle navigation</span>
      	</a>
      	<!-- Navbar Right Menu -->
      	<div class="navbar-custom-menu">
        	<ul class="nav navbar-nav">                            
          		<!-- User Account Menu -->
          		<li class="dropdown user user-menu">
            		<!-- Menu Toggle Button -->
            		<a href="#" class="dropdown-toggle" data-toggle="dropdown">
              			<!-- The user image in the navbar-->
              			<img src="images/default_avatar_large.png" class="user-image" alt="User Image">
              			<!-- hidden-xs hides the username on small devices so only the image appears. -->
              			<span class="hidden-xs"><%= user.getNominativo() %></span>
            		</a>
            		<ul class="dropdown-menu">
              			<!-- The user image in the menu -->
              			<li class="user-header">
                			<img src="images/default_avatar_large.png" class="img-circle" alt="User Image">
                			<p>
                  				<%= user.getNome() + " " + user.getCognome() %>
                  				<small><%= user.getNominativo()  %></small>
                			</p>
              			</li>
              			<!-- Menu Body -->
              			<li class="user-body">
                			<div class="row">
                  				<div class="col-xs-4 text-center">
                   				</div>
                  				<div class="col-xs-4 text-center">
                   				</div>
                  				<div class="col-xs-4 text-center">
                   				</div>
                			</div>
                			<!-- /.row -->
              			</li>
              			<!-- Menu Footer-->
              			<li class="user-footer">
                			<div class="pull-left">
                  				<a onclick="callAction('areaUtente.do');" href="#" class="btn btn-default btn-flat">Profilo</a>
                			</div>
                			<div class="pull-right">
                  				<a onclick="callAction('logout.do')" href="#" class="btn btn-default btn-flat">Log-out</a>
                			</div>
              			</li>
            		</ul>
          		</li>
          		<!-- <li class="">
           			<a href="http://www.accpoint.com" class="">
              			<!- - The user image in the navbar- ->
              			<i class="fa fa-globe"></i>
              			<!- - hidden-xs hides the username on small devices so only the image appears. - ->
              			<!- - <span class="hidden-xs">ACCPOINT</span> - ->
            		</a>
          		</li>
           		<li class="">
           			<a href="assistenza.do" class="">
              			< ! - - The user image in the navbar- - >
	              		<i class="fa fa-info-circle"></i>
    	          		<!- - hidden-xs hides the username on small devices so only the image appears. - ->
        	      		<!- - <span class="hidden-xs">ACCPOINT</span> - ->
            		</a>
          		</li>-->
          		<!-- Control Sidebar Toggle Button -->
         		<!--  <li>
            		<a href="#" data-toggle="control-sidebar"><i class="fa fa-gears"></i></a>
          		</li> -->
        	</ul>
      	</div>
    </nav>
</header>
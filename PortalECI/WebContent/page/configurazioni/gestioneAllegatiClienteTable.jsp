<%@page import="java.util.ArrayList"%>
<%@page import="com.google.gson.JsonArray"%>
<%@page import="com.google.gson.Gson"%>

<%@page import="it.portalECI.DTO.UtenteDTO"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<%
	UtenteDTO user = (UtenteDTO)request.getSession().getAttribute("userObj");
	request.setAttribute("user",user);
%>
<c:set var="lista_size" value="0"></c:set>
<ul class="list-group list-group-unbordered" >
<c:forEach items="${lista_allegati}" var="allegato" varStatus="loop">
<c:choose>
<c:when test="${type!=null && type=='ALLEGATO'}">
<c:if test="${allegato.type=='ALLEGATO' && !allegato.getInvalid() && allegato.allegato_inviabile == 1}">
<li class="list-group-item">
	       <b>${allegato.getFileName()}</b>
	                  							       										
	                  										
	 <a class="btn btn-default btn-xs pull-right" href="gestioneDocumento.do?idDocumento=${allegato.getId()}" style="margin-left:5px"><i class="glyphicon glyphicon-file"></i> Download Allegato</a>
	                  											
	  </li>
	  
	   <c:set var="lista_size" value="1"></c:set>
	  	</c:if>
	 
</c:when>
<c:otherwise>
<c:if test="${allegato.type!='ALLEGATO' && !allegato.getInvalid()}">
<li class="list-group-item">
		<b>${allegato.getFileName()}</b>
		<c:if test="${allegato.getVerbale().getCodiceCategoria()=='VIE' }">
		<a class="btn btn-default btn-xs pull-right" href="gestioneDocumento.do?controfirmato=1&idDocumento=${allegato.getId()}" style="margin-left:5px"><i class="glyphicon glyphicon-file"></i> Download Verbale</a>
		</c:if>
		<c:if test="${allegato.getVerbale().getCodiceCategoria()=='VAL' }">
		<a class="btn btn-default btn-xs pull-right" href="gestioneDocumento.do?firmato=1&idDocumento=${allegato.getId()}" style="margin-left:5px"><i class="glyphicon glyphicon-file"></i> Download Verbale</a>
		</c:if>
	</li>
	
	<c:set var="lista_size" value="1"></c:set>
	</c:if>
</c:otherwise>
</c:choose>

</c:forEach>


<c:if test="${lista_size == 0 && type!=null && type=='ALLEGATO'}">
<li class="list-group-item">Nessun allegato presente!</li>
</c:if>
   
   <c:if test="${lista_size == 0 && type!=null && type!='ALLEGATO'}">
<li class="list-group-item">Nessun certificato presente!</li>
</c:if>  

<c:if test="${lista_size == 1 }">
 <a class="btn btn-default btn-xs pull-right" style="margin-top:10px" onClick="callAction('gestioneDocumento.do?action=scarica_tutti&id_verbale=${id_verbale }&type=${type }')"><i class="fa fa-file-archive-o "></i> Scarica tutti</a>
</c:if>
</ul>


    					
 
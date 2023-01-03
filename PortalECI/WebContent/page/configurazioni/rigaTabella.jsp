<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<tr>
	<c:forEach items="${risposte}" var="risposta" varStatus="loopCol">
		<c:set var="rispostaTabella" value="${risposta}" scope="request"></c:set>
		<jsp:include page="gestioneVerbaleDettaglioTabella.jsp"></jsp:include>
	</c:forEach>
	<td>
	<a class="btn btn-default"  onclick="aggiungiRigaTabella(${rispostaParent.getId()}, this)"> Aggiungi</a>
	<a class="btn btn-default" onclick="eliminaRigaTabella(this, ${rispostaParent.getId()},${colonne[0].risposte.size()-1 } )"> Elimina</a>
	</td>
</tr>

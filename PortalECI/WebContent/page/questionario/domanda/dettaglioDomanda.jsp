<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="it.portalECI.Util.Funzioni"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page import="it.portalECI.DTO.RispostaFormulaQuestionarioDTO" %>
<%@ page import="it.portalECI.DTO.RispostaSceltaQuestionarioDTO" %>
<%@ page import="it.portalECI.DTO.OpzioneRispostaQuestionarioDTO" %>

<%@ page import="it.portalECI.DTO.DomandaQuestionarioDTO" %>


<%
	DomandaQuestionarioDTO domanda = (DomandaQuestionarioDTO) request.getAttribute("domanda");

	org.hibernate.Session hibernateSession = (org.hibernate.Session) request.getAttribute("hibernateSession");
	List<OpzioneRispostaQuestionarioDTO> lista_opzioni = new ArrayList<OpzioneRispostaQuestionarioDTO>();
	
	if(domanda!=null && domanda.getRisposta().getTipo().equals("RES_FORMULA")){
		RispostaFormulaQuestionarioDTO risp =(RispostaFormulaQuestionarioDTO) hibernateSession.get(RispostaFormulaQuestionarioDTO.class, domanda.getRisposta().getId());
		request.setAttribute("risposta",risp);
	
	}else if(domanda!=null && domanda.getRisposta().getTipo().equals("RES_CHOICE")){
		RispostaSceltaQuestionarioDTO risp =(RispostaSceltaQuestionarioDTO) hibernateSession.get(RispostaSceltaQuestionarioDTO.class, domanda.getRisposta().getId());
		lista_opzioni = risp.getOpzioni();
		request.setAttribute("risposta",risp);
	}
	
	request.setAttribute("lista_opzioni",lista_opzioni);
%>


<ul class="list-group list-group">
	<li class="list-group-item">
		<b>Testo della domanda</b> 
		<a class="pull-right">${domanda.testo}</a>
	</li>
	<li class="list-group-item">
		<b>Placeholder della domanda</b> 
		<a class="pull-right">${domanda.placeholder}</a>
	</li>
	<li class="list-group-item">
		<b>Domanda obbligatoria</b> 
		<a class="pull-right">${domanda.obbligatoria?'SI':'NO'}</a>
	</li>
	<c:if test="${domanda.risposta.tipo=='RES_CHOICE'}">
		<li class="list-group-item">
			<b>Tipo di risposta</b>
			<a class="pull-right">Scelta multipla</a>
		</li>
		<li class="list-group-item" style="display: block;">
			<b>Opzioni</b> 
			<a class="pull-right">
				<c:forEach items="${risposta.opzioni }" var="opzione">
       				${opzione.testo}, 
				</c:forEach>
			</a>
		</li>
	</c:if>
	<c:if test="${domanda.risposta.tipo=='RES_FORMULA'}">
		<li class="list-group-item">
			<b>Tipo di risposta</b>
			<a class="pull-right">Formula</a>
		</li>
		<li class="list-group-item">
			<b>Operazione</b>
			<a class="pull-right">${risposta.operatore}</a>
		</li>
		<li class="list-group-item">
			<b>Nome primo valore</b>
			<a class="pull-right">${risposta.valore1}</a>
		</li>
		<li class="list-group-item">
			<b>Nome secondo valore</b>
			<a class="pull-right">${risposta.valore2}</a>
		</li>
		<li class="list-group-item">
			<b>Nome risultato</b>
			<a class="pull-right">${risposta.risultato}</a>
		</li>
	</c:if>
	<c:if test="${domanda.risposta.tipo=='RES_TEXT'}">
		<li class="list-group-item">
			<b>Tipo di risposta</b>
			<a class="pull-right">Testo libero</a>
		</li>
	</c:if>
</ul>

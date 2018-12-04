<%@page import="it.portalECI.DTO.ColonnaTabellaQuestionarioDTO"%>
<%@page import="it.portalECI.DTO.RispostaTabellaQuestionarioDTO"%>
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
	List<ColonnaTabellaQuestionarioDTO> lista_colonne = new ArrayList<ColonnaTabellaQuestionarioDTO>();
	if(domanda!=null && domanda.getRisposta().getTipo().equals("RES_FORMULA")){
		RispostaFormulaQuestionarioDTO risp =(RispostaFormulaQuestionarioDTO) hibernateSession.get(RispostaFormulaQuestionarioDTO.class, domanda.getRisposta().getId());
		request.setAttribute("risposta",risp);
	
	}else if(domanda!=null && domanda.getRisposta().getTipo().equals("RES_CHOICE")){
		RispostaSceltaQuestionarioDTO risp =(RispostaSceltaQuestionarioDTO) hibernateSession.get(RispostaSceltaQuestionarioDTO.class, domanda.getRisposta().getId());
		lista_opzioni = risp.getOpzioni();
		request.setAttribute("risposta",risp);
	}else if(domanda!=null && domanda.getRisposta().getTipo().equals("RES_TABLE")){
		RispostaTabellaQuestionarioDTO risp =(RispostaTabellaQuestionarioDTO) hibernateSession.get(RispostaTabellaQuestionarioDTO.class, domanda.getRisposta().getId());
		lista_colonne = risp.getColonne();
		request.setAttribute("risposta",risp);
	}
	
	request.setAttribute("lista_opzioni",lista_opzioni);
	request.setAttribute("lista_colonne",lista_colonne);
%>
<div class="box box-danger box-domanda">

	<div class="box-header">
		<div class="box-title"><h4>${domanda_header}</h4></div>
	</div>

	<div class="box-body">
		<div class="col-sm-12">
			<b>Testo della domanda</b> 
			<p class="well well-sm">${domanda.testo}</p>
		</div>
		<div class="col-sm-3">
			<b>Placeholder della domanda</b> 
			<p class="well well-sm">${domanda.placeholder}</p>
		</div>
		<div class="col-sm-3">
			<b>Placeholder della risposta</b> 
			<p class="well well-sm">${domanda.risposta.placeholder}</p>
		</div>
		<div class="col-sm-3">
			<b>Domanda obbligatoria</b> 
			<p class="well well-sm">${domanda.obbligatoria?'SI':'NO'}</p>
		</div>
		<div class="col-sm-3">
			<b>Tipo di risposta</b> 
			<c:if test="${domanda.risposta.tipo=='RES_CHOICE'}">
				<p class="well well-sm">Scelta multipla <c:if test="${!risposta.multipla}">con mutua esclusione</c:if></p>
			</c:if>
			<c:if test="${domanda.risposta.tipo=='RES_FORMULA'}">
				<p class="well well-sm">Formula</p>
			</c:if>
			<c:if test="${domanda.risposta.tipo=='RES_TEXT'}">
				<p class="well well-sm">Testo libero</p>
			</c:if>
			<c:if test="${domanda.risposta.tipo=='RES_TABLE'}">
				<p class="well well-sm">Tabella</p>
			</c:if>			
		</div>
		
		<c:if test="${domanda.risposta.tipo=='RES_FORMULA'}">
			<div class="col-sm-3">
				<b>Operazione</b> 
				<p class="well well-sm">${risposta.operatore}</p>
			</div>
			<div class="col-sm-3">
				<b>Nome primo valore</b> 
				<p class="well well-sm">${risposta.valore1}</p>
			</div>
			<div class="col-sm-3">
				<b>Nome secondo valore</b> 
				<p class="well well-sm">${risposta.valore2}</p>
			</div>
			<div class="col-sm-3">
				<b>Nome risultato</b> 
				<p class="well well-sm">${risposta.risultato}</p>
			</div>
		</c:if>
		<c:if test="${domanda.risposta.tipo=='RES_CHOICE'}">
			<c:forEach items="${risposta.opzioni }" var="opzione" varStatus="status">
				<div class="col-sm-3">
					 
					<p class="well well-sm">
					<b>Testo opzione  ${status.index+1}</b><br/>
					${opzione.testo}<br/>
					<b>Placeholder opzione  ${status.index+1}</b><br/>
					${opzione.placeholder}<br/>
					</p>
				</div>
			</c:forEach>
		</c:if>
		<c:if test="${domanda.risposta.tipo=='RES_TABLE'}">
			<div class="col-xs-12">
			<table class="table table-bordered">
				<tr>
					<c:forEach items="${risposta.colonne }" var="colonna" varStatus="status">
						<th style="width: ${colonna.larghezza}%">
							${colonna.domanda.testo}
						</th>
					</c:forEach>
				</tr>
			</table>
			 </div>
		</c:if>
		<div class="clearfix"></div>
	</div>
</div>
		<c:if test="${domanda.risposta.tipo=='RES_CHOICE'}">
			<c:set var="conta_domande" value="${1}" scope="page"></c:set>
			<c:forEach items="${risposta.opzioni}" var="opzione" varStatus="status">
				<c:if test="${opzione.domande.size()>0 }">
						<c:set var="domanda_original" value="${domanda}" scope="page"></c:set>
						<c:set var="domanda_header_original" value="${domanda_header}" scope="page"></c:set>
						<div class="col-sm-12 lista-domande-annidate-col"><h4>Se l&apos; opzione <b>${status.index+1}</b> di <b>${domanda_header}</b> &egrave; selezionata</h4></div>
						<c:forEach items="${opzione.domande}" var="domandaOpzione">
							<c:set var="domanda" value="${domandaOpzione}" scope="request"></c:set>
							<div class="col-md-12 lista-domande-annidate-col">
								<c:set var="domanda_header" value="${domanda_header}.${conta_domande}" scope="request"></c:set>
								<c:set var="conta_domande" value="${conta_domande+1}" scope="page"></c:set>
								<jsp:include page="dettaglioDomanda.jsp"></jsp:include>
								<c:set var="domanda_header" value="${domanda_header_original}" scope="request"></c:set>
							</div>
						</c:forEach>
						<c:set var="domanda" value="${domanda_original}" scope="request"></c:set>
				</c:if>
			</c:forEach>
		</c:if>

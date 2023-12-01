<%@page import="it.portalECI.DTO.RispostaVerbaleDTO"%>
<%@page import="it.portalECI.DTO.ColonnaTabellaVerbaleDTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/tld/utilities" prefix="utl" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<%@ page import="it.portalECI.DTO.RispostaFormulaVerbaleDTO" %>
<%@ page import="it.portalECI.DTO.RispostaSceltaVerbaleDTO" %>
<%@ page import="it.portalECI.DTO.RispostaTestoVerbaleDTO" %>
<%@ page import="it.portalECI.DTO.OpzioneRispostaVerbaleDTO" %>
<%@ page import="it.portalECI.DTO.RispostaTabellaVerbaleDTO" %>
<%@ page import="it.portalECI.DTO.DomandaVerbaleDTO" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.Comparator" %>

<% 
	RispostaVerbaleDTO rispostaTabella = (RispostaVerbaleDTO) request.getAttribute("rispostaTabella");

	org.hibernate.Session hibernateSession = (org.hibernate.Session) request.getAttribute("hibernateSession");
	
	
	if(rispostaTabella!=null && rispostaTabella.getTipo().equals("RES_FORMULA")){
		RispostaFormulaVerbaleDTO risp =(RispostaFormulaVerbaleDTO) hibernateSession.get(RispostaFormulaVerbaleDTO.class, rispostaTabella.getId());
		pageContext.setAttribute("rispostaPage",risp);
	
	}else if(rispostaTabella!=null && rispostaTabella.getTipo().equals("RES_CHOICE")){

			RispostaSceltaVerbaleDTO risp =(RispostaSceltaVerbaleDTO) hibernateSession.get(RispostaSceltaVerbaleDTO.class, rispostaTabella.getId());
			List<OpzioneRispostaVerbaleDTO> opzioni=new ArrayList<OpzioneRispostaVerbaleDTO>();
			
				opzioni.addAll(risp.getOpzioni());
				
				Collections.sort(opzioni, new Comparator<OpzioneRispostaVerbaleDTO>() {
			        @Override
			        public int compare(OpzioneRispostaVerbaleDTO op2, OpzioneRispostaVerbaleDTO op1){
						int pos1=op1.getOpzioneQuestionario().getPosizione();
						int pos2=op2.getOpzioneQuestionario().getPosizione();
			            return  pos2 - pos1;
			        }
			    });
		

			pageContext.setAttribute("opzioniPage", opzioni);		
			pageContext.setAttribute("rispostaPage",risp);


		
	
	}else if(rispostaTabella!=null && rispostaTabella.getTipo().equals("RES_TEXT")){
		RispostaTestoVerbaleDTO risp =(RispostaTestoVerbaleDTO) hibernateSession.get(RispostaTestoVerbaleDTO.class, rispostaTabella.getId());
		pageContext.setAttribute("rispostaPage",risp);
	}
	
%>
<td>
	<div class="input-group">

		<input type="hidden" class="input_hidden_id_risposta_colonna" value="${rispostaTabella.getId()}">
	
		<c:choose>
			<c:when test="${rispostaTabella!=null && rispostaTabella.getTipo()!=null && rispostaTabella.getTipo().equals('RES_TEXT')}">
				<input class="form-control" type="text" name="${rispostaTabella.id}" value="${utl:escapeHTML(rispostaPage.responseValue)}"/>
			</c:when>
			<c:when test="${rispostaTabella!=null && rispostaTabella.getTipo()!=null && rispostaTabella.getTipo().equals('RES_FORMULA')}">
				<input class="form-control" type="text" name="value1${rispostaTabella.id}" value="${rispostaPage.value1}"/>
				<span class="input-group-addon">${rispostaPage.getRispostaQuestionario().getSimboloOperatore()}</span>
				<input class="form-control" type="text" name="value2${rispostaTabella.id}" value="${rispostaPage.value2}"/>
				<span class="input-group-addon">=</span>			
				<input class="form-control" type="text" name="responseValue${rispostaTabella.id}" value="${rispostaPage.responseValue}"/>
			</c:when>
			<c:when test="${rispostaTabella!=null && rispostaTabella.getTipo()!=null && rispostaTabella.getTipo().equals('RES_CHOICE')}">
				<select class="form-control" name="options${rispostaTabella.id}">
					<option value="" ></option>
					<c:forEach items="${opzioniPage}" var="opzionePage" varStatus="loopOPT">
						<option value="${opzionePage.id }" ${opzionePage.checked?"selected":"" }>${opzionePage.getOpzioneQuestionario().getTesto() }</option>
					</c:forEach>
				</select>
			</c:when>
		</c:choose>
		<c:if test="${storico!=null && storico.contains(rispostaTabella.getId()) }">
			<span class="input-group-btn">
				<a class="btn btn-warning" onclick="detailStorico('${rispostaTabella.getId()}')" title="ELEMENTO MODIFICATO - Clicca per vedere lo storico delle modifiche">
					<i class="fa fa-pencil" aria-hidden="true"></i>
				</a>
			</span>
		</c:if>	
	</div>
</td>

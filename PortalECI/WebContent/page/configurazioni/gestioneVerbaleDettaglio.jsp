<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<%@ page import="it.portalECI.DTO.RispostaFormulaVerbaleDTO" %>
<%@ page import="it.portalECI.DTO.RispostaSceltaVerbaleDTO" %>
<%@ page import="it.portalECI.DTO.RispostaTestoVerbaleDTO" %>
<%@ page import="it.portalECI.DTO.DomandaVerbaleDTO" %>

<% DomandaVerbaleDTO domVerbale = (DomandaVerbaleDTO) request.getAttribute("domVerbale");

	org.hibernate.Session hibernateSession = (org.hibernate.Session) request.getAttribute("hibernateSession");
	
	
	if(domVerbale!=null && domVerbale.getRisposta().getTipo().equals("RES_FORMULA")){
		RispostaFormulaVerbaleDTO risp =(RispostaFormulaVerbaleDTO) hibernateSession.get(RispostaFormulaVerbaleDTO.class, domVerbale.getRisposta().getId());
		request.setAttribute("risposta",risp);
	
	}else if(domVerbale!=null && domVerbale.getRisposta().getTipo().equals("RES_CHOICE")){
		RispostaSceltaVerbaleDTO risp =(RispostaSceltaVerbaleDTO) hibernateSession.get(RispostaSceltaVerbaleDTO.class, domVerbale.getRisposta().getId());
		request.setAttribute("risposta",risp);
	}else if(domVerbale!=null && domVerbale.getRisposta().getTipo().equals("RES_TEXT")){
		RispostaTestoVerbaleDTO risp =(RispostaTestoVerbaleDTO) hibernateSession.get(RispostaTestoVerbaleDTO.class, domVerbale.getRisposta().getId());	
		request.setAttribute("risposta",risp);
	} %>

<c:choose>
	<c:when test="${domVerbale.getRisposta().getTipo().equals('RES_CHOICE')}">
  																
  		<div class="col-sm-12">	
  			<div class="form-group">													
    			<c:forEach items="${risposta.getOpzioni()}" var="domande" varStatus="loop">	
					<label class="${domVerbale.getRisposta().getTipo().equals('RES_CHOICE') && risposta.getRispostaQuestionario().getMultipla()?'checkbox':'radio'}-inline">
						<input type="${domVerbale.getRisposta().getTipo().equals('RES_CHOICE') && risposta.getRispostaQuestionario().getMultipla()?'checkbox':'radio'}"
  							value="${risposta.getRispostaQuestionario().getOpzioni()[loop.index].getTesto()}"> ${domande.getOpzioneQuestionario().getTesto()}
					</label>
				</c:forEach>
			</div>
		</div>
  	</c:when>
  	<c:when test="${domVerbale.getRisposta().getTipo().equals('RES_FORMULA')}">
    				
  		<div class="col-sm-3">
			<div class="form-group">
				<label for="titolo-input" class="control-label">${risposta.getRispostaQuestionario().getValore1()}</label>
				<input type="text" name="formula-valore-1" class="form-control" value="${risposta.getValue1()}"/>

			</div>
		</div>
		<div class="col-sm-1">
			<label for="titolo-input" class="control-label">${risposta.getRispostaQuestionario().getOperatore()}</label>
		</div>
		<div class="col-sm-3">
			<div class="form-group">
				<label for="titolo-input" class="control-label">${risposta.getRispostaQuestionario().getValore2()}</label>
	 			<input type="text" name="formula-valore-1" class="form-control" value="${risposta.getValue2()}"/>
			</div>
		</div>
		<div class="col-sm-3">
			<div class="form-group">
				<label for="titolo-input" class="control-label">${risposta.getRispostaQuestionario().getRisultato()}</label>
				<input type="text" name="formula-valore-1" class="form-control" value="${risposta.getResponseValue()}"/>
			</div>
		</div>
  																
  	</c:when>
  	<c:when test="${domVerbale.getRisposta().getTipo().equals('RES_TEXT')}">
  		<div class="col-sm-12">
  			<div class="form-group">		
  				<textarea class="form-control" rows="5" id="comment">${risposta.getResponseValue()}</textarea>
  			</div>
  		</div>
	</c:when>
</c:choose>

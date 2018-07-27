<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<%@ page import="it.portalECI.DTO.RispostaFormulaVerbaleDTO" %>
<%@ page import="it.portalECI.DTO.RispostaSceltaVerbaleDTO" %>
<%@ page import="it.portalECI.DTO.RispostaTestoVerbaleDTO" %>
<%@ page import="it.portalECI.DTO.OpzioneRispostaVerbaleDTO" %>
<%@ page import="it.portalECI.DTO.DomandaVerbaleDTO" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.Comparator" %>

<% DomandaVerbaleDTO domVerbale = (DomandaVerbaleDTO) request.getAttribute("domVerbale");

	org.hibernate.Session hibernateSession = (org.hibernate.Session) request.getAttribute("hibernateSession");
	
	
	if(domVerbale!=null && domVerbale.getRisposta().getTipo().equals("RES_FORMULA")){
		RispostaFormulaVerbaleDTO risp =(RispostaFormulaVerbaleDTO) hibernateSession.get(RispostaFormulaVerbaleDTO.class, domVerbale.getRisposta().getId());
		request.setAttribute("risposta",risp);
	
	}else if(domVerbale!=null && domVerbale.getRisposta().getTipo().equals("RES_CHOICE")){
		RispostaSceltaVerbaleDTO risp =(RispostaSceltaVerbaleDTO) hibernateSession.get(RispostaSceltaVerbaleDTO.class, domVerbale.getRisposta().getId());	
		List opzioni=new ArrayList();
		opzioni.addAll(risp.getOpzioni());
		
		Collections.sort(opzioni, new Comparator<OpzioneRispostaVerbaleDTO>() {
	        @Override
	        public int compare(OpzioneRispostaVerbaleDTO op2, OpzioneRispostaVerbaleDTO op1){
				int pos1=op1.getOpzioneQuestionario().getPosizione();
				int pos2=op2.getOpzioneQuestionario().getPosizione();
	            return  pos2 - pos1;
	        }
	    });
		
		request.setAttribute("opzioni",opzioni);
		request.setAttribute("risposta",risp);
	
	}else if(domVerbale!=null && domVerbale.getRisposta().getTipo().equals("RES_TEXT")){
		RispostaTestoVerbaleDTO risp =(RispostaTestoVerbaleDTO) hibernateSession.get(RispostaTestoVerbaleDTO.class, domVerbale.getRisposta().getId());	
		request.setAttribute("risposta",risp);
	} %>

<c:choose>
	<c:when test="${domVerbale.getRisposta().getTipo().equals('RES_CHOICE')}">
  																
  		<div class="col-sm-12">	
  			<div class="form-group">	
  			
												
    			<c:forEach items="${opzioni}" var="opzione">	
					<label class="${domVerbale.getRisposta().getTipo().equals('RES_CHOICE') && risposta.getRispostaQuestionario().getMultipla()?'checkbox':'radio'}-inline">
						<input type="${domVerbale.getRisposta().getTipo().equals('RES_CHOICE') && risposta.getRispostaQuestionario().getMultipla()?'checkbox':'radio'}"
  							${opzione.getChecked()?'checked="checked"':''} name="options${risposta.getId()}" value="${opzione.getId()}" class="rispVerb" ${domVerbale.getDomandaQuestionario().getObbligatoria()?'required':''}> 
  							${opzione.getOpzioneQuestionario().getTesto()}
					</label><br/>
				</c:forEach>
			</div>
		</div>
  	</c:when>
  	<c:when test="${domVerbale.getRisposta().getTipo().equals('RES_FORMULA')}">

  		<div class="col-sm-3">
			<div class="form-group">
				<label for="titolo-input" class="control-label">${risposta.getRispostaQuestionario().getValore1()}</label>
				<input type="text" name="value1${risposta.getId()}" class="form-control rispVerb" value="${risposta.getValue1()}" ${domVerbale.getDomandaQuestionario().getObbligatoria()?'required':''}/>
			</div>
		</div>
		<div class="col-sm-1">
			<label for="titolo-input" class="control-label" style="display: flex; justify-content: flex-end; flex-direction: column; width: 300px; height: 55px; text-align: center;">
				<b style="font-size:1.5em;">${risposta.getRispostaQuestionario().getSimboloOperatore()}</b>
			</label>
		</div>
		<div class="col-sm-3">
			<div class="form-group">
				<label for="titolo-input" class="control-label">${risposta.getRispostaQuestionario().getValore2()}</label>
	 			<input type="text" name="value2${risposta.getId()}" class="form-control rispVerb" value="${risposta.getValue2()}" ${domVerbale.getDomandaQuestionario().getObbligatoria()?'required':''}/>
			</div>
		</div>
		<div class="col-sm-3">
			<div class="form-group">
				<label for="titolo-input" class="control-label">${risposta.getRispostaQuestionario().getRisultato()}</label>
				<input type="text" class="form-control rispVerb"  name="responseValue${risposta.getId()}" value="${risposta.getResponseValue()}" ${domVerbale.getDomandaQuestionario().getObbligatoria()?'required':''}/>
			</div>
		</div>
  																
  	</c:when>
  	<c:when test="${domVerbale.getRisposta().getTipo().equals('RES_TEXT')}">
  		<div class="col-sm-12">
  			<div class="form-group">		
  				<textarea class="form-control rispVerb" rows="5" id="comment" name="${risposta.getId()}" ${domVerbale.getDomandaQuestionario().getObbligatoria()?'required':''}>${risposta.getResponseValue()}</textarea>
  			</div>
  		</div>
	</c:when>
</c:choose>

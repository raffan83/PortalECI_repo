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
	}
	
	if(request.getAttribute("type").equals("Verbale")){
		request.setAttribute("storico", request.getAttribute("storicoModificheVerb"));		
	}else{
		request.setAttribute("storico",request.getAttribute("storicoModificheSkTec"));
	}
%>
	
<c:choose>
	<c:when test="${domVerbale.getRisposta().getTipo().equals('RES_CHOICE')}">
  																
  		<div class="col-sm-12">	
  			<div class="form-group">	
  				<c:if test="${storico.contains(risposta.getId()) }">	
  					<div class="row" style="text-align: right;">    
  						<i class="fa fa-pencil" aria-hidden="true" onclick="detailStorico('${risposta.getId()}')" title="Click per vedere le modifiche sulla risposta" style="cursor: pointer;"></i>
  					</div>
  				</c:if>
  				
  				<c:set var="domVerbalePage" value="${domVerbale}" scope="page"></c:set>
  				<c:set var="rispostaPage" value="${risposta}" scope="page"></c:set>
    			
    			<c:forEach items="${opzioni}" var="opzione">	
					<label class="${domVerbalePage.getRisposta().getTipo().equals('RES_CHOICE') && rispostaPage.getRispostaQuestionario().getMultipla()?'checkbox':'radio'}-inline">
						<input type="${domVerbalePage.getRisposta().getTipo().equals('RES_CHOICE') && rispostaPage.getRispostaQuestionario().getMultipla()?'checkbox':'radio'}"
  							${opzione.getChecked()?'checked="checked"':''} name="options${rispostaPage.getId()}" value="${opzione.getId()}" id="options${domVerbalePage.getId()}" class="rispVerb" ${domVerbalePage.getDomandaQuestionario().getObbligatoria()?'required':''} > 
  							${opzione.getOpzioneQuestionario().getTesto()}
					</label><br/>
					<c:if test="${opzione.getDomande().size()>0}">
						<c:forEach items="${opzione.getDomande()}" var="domVerbalenew" varStatus="loop">	
							<div class="options${domVerbalePage.getId()}" style="margin-left:20px;">   	 
								<label>Se opzione ${opzione.getOpzioneQuestionario().getTesto()} è selezionata</label>
							   			
								<c:set var="domVerbale" value="${domVerbalenew}" scope="request"></c:set>
								<div class="box box-danger box-domanda ">
									<label for="titolo-input" class="control-label col-xs-12">${domVerbalenew.getDomandaQuestionario().getTesto()}</label><br/>
									<jsp:include page="gestioneVerbaleDettaglio.jsp"></jsp:include>
								</div>
							</div>
						</c:forEach>  						
					</c:if>
				</c:forEach>				
			</div>
		</div>
  	</c:when>
  	<c:when test="${domVerbale.getRisposta().getTipo().equals('RES_FORMULA')}">
		<c:if test="${storico.contains(risposta.getId()) }">	
			<div class="row" style="text-align: right;">    
  				<i class="fa fa-pencil" aria-hidden="true" onclick="detailStorico('${risposta.getId()}')" title="Click per vedere le modifiche sulla risposta" style="cursor: pointer;"></i>
  			</div>
  		</c:if>
  		
  		<div class="col-sm-3">
			<div class="form-group">
				<label for="titolo-input" class="control-label">${risposta.getRispostaQuestionario().getValore1()}</label>
				<input type="text" name="value1${risposta.getId()}" class="form-control rispVerb" value="${risposta.getValue1()}" ${domVerbale.getDomandaQuestionario().getObbligatoria()?'required':''} />
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
				<input type="text" class="form-control rispVerb"  name="responseValue${risposta.getId()}" value="${risposta.getResponseValue()}" ${domVerbale.getDomandaQuestionario().getObbligatoria()?'required':''} />
			</div>
		</div>
  																
  	</c:when>
  	<c:when test="${domVerbale.getRisposta().getTipo().equals('RES_TEXT')}">
  		<div class="col-sm-12">
  			<div class="form-group">
  				<c:if test="${storico.contains(risposta.getId()) }">
  					<div class="row" style="text-align: right;">    	
  						<i class="fa fa-pencil" aria-hidden="true" onclick="detailStorico('${risposta.getId()}')" title="Click per vedere le modifiche sulla risposta" style="cursor: pointer;"></i>
  					</div>
  				</c:if>
  				
  				<textarea class="form-control rispVerb" rows="5" id="comment" name="${risposta.getId()}" ${domVerbale.getDomandaQuestionario().getObbligatoria()?'required':''} >${risposta.getResponseValue()}</textarea>
  			</div>
  		</div>
	</c:when>
</c:choose>

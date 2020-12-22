<%@page import="it.portalECI.DTO.RispostaVerbaleDTO"%>
<%@page import="it.portalECI.DTO.ColonnaTabellaVerbaleDTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<%@ page import="it.portalECI.DTO.RispostaFormulaVerbaleDTO" %>
<%@ page import="it.portalECI.DTO.RispostaSceltaVerbaleDTO" %>
<%@ page import="it.portalECI.DTO.RispostaTestoVerbaleDTO" %>
<%@ page import="it.portalECI.DTO.OpzioneRispostaVerbaleDTO" %>
<%@ page import="it.portalECI.DTO.RispostaTabellaVerbaleDTO" %>
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
		
		request.setAttribute("opzioni",opzioni);
		request.setAttribute("risposta",risp);
	
	}else if(domVerbale!=null && domVerbale.getRisposta().getTipo().equals("RES_TEXT")){
		RispostaTestoVerbaleDTO risp =(RispostaTestoVerbaleDTO) hibernateSession.get(RispostaTestoVerbaleDTO.class, domVerbale.getRisposta().getId());	
		request.setAttribute("risposta",risp);
	}else if(domVerbale!=null && domVerbale.getRisposta().getTipo().equals("RES_TABLE")){
		RispostaTabellaVerbaleDTO risp =(RispostaTabellaVerbaleDTO) hibernateSession.get(RispostaTabellaVerbaleDTO.class, domVerbale.getRisposta().getId());	
		List<ColonnaTabellaVerbaleDTO> colonne=new ArrayList<ColonnaTabellaVerbaleDTO>();
		colonne.addAll(risp.getColonne());
		
		Collections.sort(colonne, new Comparator<ColonnaTabellaVerbaleDTO>() {
	        @Override
	        public int compare(ColonnaTabellaVerbaleDTO op2, ColonnaTabellaVerbaleDTO op1){
				int pos1=op1.getColonnaQuestionario().getPosizione().intValue();
				int pos2=op2.getColonnaQuestionario().getPosizione().intValue();
	            return  pos2 - pos1;
	        }
	    });
		
		request.setAttribute("colonne",colonne);
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
  					<p><a class="label label-warning" onclick="detailStorico('${risposta.getId()}')" title="Clicca per vedere lo storico delle modifiche"> Elemento modificato <i class="fa fa-pencil" aria-hidden="true"></i></a></p>
  				</c:if>
  				
  				<c:set var="domVerbalePage" value="${domVerbale}" scope="page"></c:set>
  				<c:set var="rispostaPage" value="${risposta}" scope="page"></c:set>
    			
    			<c:forEach items="${opzioni}" var="opzione" varStatus ="loop">	
					<label class="${domVerbalePage.getRisposta().getTipo().equals('RES_CHOICE') && rispostaPage.getRispostaQuestionario().getMultipla()?'checkbox':'radio'}-inline">
						<input type="${domVerbalePage.getRisposta().getTipo().equals('RES_CHOICE') && rispostaPage.getRispostaQuestionario().getMultipla()?'checkbox':'radio'}"
  							${opzione.getChecked()?'checked="checked"':''} name="options${rispostaPage.getId()}" value="${opzione.getId()}" id="options${domVerbalePage.getId()}_${loop.index}" class="rispVerb" ${domVerbalePage.getDomandaQuestionario().getObbligatoria()?'required':''} > 
  							${opzione.getOpzioneQuestionario().getTesto()}
					</label><br/>
					<c:if test="${opzione.getDomande().size()>0}">
						<c:forEach items="${opzione.getDomande()}" var="domVerbalenew" varStatus="loop">	
							<div class="options${domVerbalePage.getId()}" style="margin-left:20px;">   	 
								<label>Se opzione ${opzione.getOpzioneQuestionario().getTesto()} &egrave; selezionata</label>
							   			
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
  			<p><a class="label label-warning" onclick="detailStorico('${risposta.getId()}')" title="Clicca per vedere lo storico delle modifiche"> Elemento modificato <i class="fa fa-pencil" aria-hidden="true"></i></a></p>
  		</c:if>
  		
  		<div class="col-sm-3">
			<div class="form-group">
				<label for="titolo-input" class="control-label">${risposta.getRispostaQuestionario().getValore1()}</label>
				<input type="text" name="value1${risposta.getId()}" class="form-control rispVerb" value="${risposta.getValue1()}" onkeyup="calcoloRisultato(${risposta.getId()}, '${risposta.getRispostaQuestionario().getOperatore()}')" ${domVerbale.getDomandaQuestionario().getObbligatoria()?'required':''} />
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
	 			<input type="text" name="value2${risposta.getId()}" class="form-control rispVerb" value="${risposta.getValue2()}" onkeyup="calcoloRisultato(${risposta.getId()}, '${risposta.getRispostaQuestionario().getOperatore()}')" ${domVerbale.getDomandaQuestionario().getObbligatoria()?'required':''}/>
			</div>
		</div>
		<div class="col-sm-3">
			<div class="form-group">
				<label for="titolo-input" class="control-label">${risposta.getRispostaQuestionario().getRisultato()}</label>
				<input type="text" class="form-control rispVerb"  name="responseValue${risposta.getId()}" value="${risposta.getResponseValue()}" ${domVerbale.getDomandaQuestionario().getObbligatoria()?'required':''} readonly/>
			</div>
		</div>
  																
  	</c:when>
  	<c:when test="${domVerbale.getRisposta().getTipo().equals('RES_TEXT')}">
  		<div class="col-sm-12">
  			<div class="form-group">
  				<c:if test="${storico.contains(risposta.getId()) }">
  					<p><a class="label label-warning" onclick="detailStorico('${risposta.getId()}')" title="Clicca per vedere lo storico delle modifiche"> Elemento modificato <i class="fa fa-pencil" aria-hidden="true"></i></a></p>
  				</c:if>
  				
  				<textarea class="form-control rispVerb" rows="5" id="comment${risposta.getId()}" name="${risposta.getId()}" ${domVerbale.getDomandaQuestionario().getObbligatoria()?'required':''} >${risposta.getResponseValue()}</textarea> 
  				
  			</div>
  		</div>
	</c:when>
	<c:when test="${domVerbale.getRisposta().getTipo().equals('RES_TABLE')}">
		<c:if test="${storico.contains(risposta.getId()) }">
			<p><a class="label label-warning" onclick="detailStorico('${risposta.getId()}')" title="Clicca per vedere lo storico delle modifiche"> Elemento modificato <i class="fa fa-pencil" aria-hidden="true"></i></a></p>
		</c:if>
		<div class="col-sm-12"><div class="table-responsive">
		<table class="table table-bordered table-condensed">
			<thead>
				<tr>
					<c:forEach items="${colonne}" var="colonnaVerbale" varStatus="loop">
        				<th> ${colonnaVerbale.getColonnaQuestionario().getDomanda().getTesto()}</th>
        			</c:forEach>
        			<th></th>
				</tr>
			</thead>
			<tbody id="tabella_risposta_${domVerbale.getRisposta().getId()}" class="table table-bordered">
				<c:forEach items="${colonne[0].risposte}" var="rispostaPrimaColonna" varStatus="loopRes" >
					<tr>
						<c:forEach items="${colonne}" var="colonnaVerbale" varStatus="loopCol">
							<c:set var="rispostaTabella" value="${colonnaVerbale.getRisposte().get(loopRes.index)}" scope="request"></c:set>
							<jsp:include page="gestioneVerbaleDettaglioTabella.jsp"></jsp:include>
						</c:forEach>
						<td>
							<a onclick="eliminaRigaTabella(this,${domVerbale.getRisposta().getId()} ,${loopRes.index})"> Elimina</a>
						</td>					
					</tr>
				</c:forEach>
			</tbody>
			<tr>
				<td colspan="${colonne.size()}"><a class="btn btn-block btn-danger" style="cursor:pointer" data-loading-text="<i class='fa fa-circle-o-notch fa-spin'></i> Caricamento" onclick="aggiungiRigaTabella(${domVerbale.getRisposta().getId()}, this)"> Aggiungi riga </a></td>
			</tr>
		</table>
		</div></div>
  	</c:when>
</c:choose>

<script type="text/javascript">
	function calcoloRisultato(idrisposta, operatore){
		var valore1 = parseFloat($("[name='value1"+idrisposta+"']").val());
		var valore2 = parseFloat($("[name='value2"+idrisposta+"']").val());
		var risultato = null;
		
		if (!isNaN(valore1) && !isNaN(valore2)) {
			switch (operatore) {
			case "Somma":
				risultato = valore1 + valore2;
				break;
			case "Sottrazione":
				risultato = valore1 - valore2;
				break;
			case "Moltiplicazione":
				risultato = valore1 * valore2;
				break;
			case "Divisione":
				risultato = valore1 / valore2;
				risultato = risultato.toFixed(1);
				break;
			case "Potenza":
				risultato = Math.pow(valore1, valore2);
				break;
			}
		}
		
		$("[name='responseValue"+idrisposta+"']").val(risultato);
	}
</script>

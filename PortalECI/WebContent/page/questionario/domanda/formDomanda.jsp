<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="it.portalECI.DTO.RispostaQuestionario"%>
<%@page import="it.portalECI.Util.Funzioni"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page import="it.portalECI.DTO.RispostaFormulaQuestionarioDTO" %>
<%@ page import="it.portalECI.DTO.RispostaSceltaQuestionarioDTO" %>
<%@ page import="it.portalECI.DTO.OpzioneRispostaQuestionarioDTO" %>

<%@ page import="it.portalECI.DTO.DomandaQuestionarioDTO" %>
<%@ page import="it.portalECI.DTO.DomandaVerbaleQuestionarioDTO" %>
<%@ page import="it.portalECI.DTO.DomandaSchedaTecnicaQuestionarioDTO" %>
<div class="col-xs-12 domanda-div">

	<input type="hidden" name="domanda.gruppo" value="${gruppo}" >
	<div class="form-horizontal">
		<div class="form-group">
			<div class="col-sm-12"><a class="btn btn-danger btn-xs pull-right margin-right" onclick="$(this).parents('.domanda-div').remove()">elimina domanda</a></div>
		</div>
		<div class="form-group">
			<label for="domanda.testo" class="col-sm-2 control-label">Testo della domanda</label>
			<div class="col-sm-10">
				<textarea name="domanda.testo" class="form-control" placeholder="Testo della domanda">${domanda.testo}</textarea>
			</div>
		</div>
		<div class="form-group">
			<label for="domanda.placeholder" class="col-sm-2 control-label">Placeholder della domanda</label>
			<div class="col-sm-10">
				<div class="input-group">
	                <input type="text" name="domanda.placeholder" class="form-control" placeholder="Placeholder della domanda" value="${domanda.placeholder!=null?domanda.placeholder.substring(0, domanda.placeholder.length()-4):''}"/>
	                <span class="input-group-addon">_QST</span>
	              </div>
			</div>
		</div>
		<div class="form-group">
			<label for="domanda.obbligatoria" class="col-sm-2 control-label">Domanda obbligatoria</label>
			<div class="col-sm-10">
				<select name="domanda.obbligatoria" class="form-control domanda-obbligatoria-select">
			    	<option value="false">NO</option>
			    	<option value="true" ${domanda.obbligatoria?'selected':''}>SI</option>
				</select>
			</div>
		</div>
		<div class="form-group">
			<label for="risposta.tipo" class="col-sm-2 control-label">Tipo di risposta</label>
			<div class="col-sm-10">
				<select name="risposta.tipo" class="form-control tipo-risposta-select">
			    	<option value="" disabled selected>Seleziona Tipo...</option>
			    	<option value="RES_TEXT" ${domanda.risposta.tipo!=null && domanda.risposta.tipo.equals('RES_TEXT')?'selected':''}>Testo libero</option>
			    	<option value="RES_CHOICE" ${domanda.risposta.tipo!=null && domanda.risposta.tipo.equals('RES_CHOICE')?'selected':''}>Scelta multipla</option>
			    	<option value="RES_FORMULA" ${domanda.risposta.tipo!=null && domanda.risposta.tipo.equals('RES_FORMULA')?'selected':''}>Formula</option>
				</select>
			</div>
		</div>		
	</div>
			<%DomandaQuestionarioDTO domanda = (DomandaQuestionarioDTO) request.getAttribute("domanda");%>
			<%org.hibernate.Session hibernateSession = (org.hibernate.Session) request.getAttribute("hibernateSession");%>
			<%	
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
	<div class="risposta-div risposta-RES_CHOICE" style="display: ${domanda!=null && domanda.risposta.tipo.equals('RES_CHOICE')?'block':'none'}">
		<div class="form-horizontal">
		<div class="form-group">
			<label for="risposta.multipla" class="col-sm-2 control-label">Mutua esclusione</label>
			<div class="col-sm-10">
				<select name="risposta.multipla" class="form-control domanda-obbligatoria-select">
			    	<option value="false">SI</option>
			    	<option value="true" ${risposta.multipla?'selected':''}>NO</option>
				</select>
			</div>
		</div>
		</div>
		
		<input type="hidden" name="numero-opzioni" class="numero-opzioni-input" value="${lista_opzioni.size()}"/>
		<div class="lista-opzioni-div row ">
			<c:forEach items="${lista_opzioni}" var="opzione" >
				<div class="col-sm-4 opzione-div form-group">
					<div class="input-group">
						<input type="text" class="form-control" placeholder="Opzione" name="opzione" value="${opzione.testo}">
						<div class="input-group-btn">
							<button type="button" class="btn btn-danger rimuovi-opzione-button">X</button>
						</div>
					</div>
				</div>
			</c:forEach>
			<div class="col-sm-4 aggiungi-opzione-button-wp">
				<button type="button" class="btn btn-block btn-danger aggiungi-opzione-button" >
					<i class="fa fa-plus"></i>Aggiungi una opzione
				</button>
			</div>
		</div>
	</div>

	
	<div class="form-group risposta-div risposta-RES_FORMULA" style="display: ${domanda!=null && domanda.risposta.tipo.equals('RES_FORMULA')?'block':'none'}">
		<div class="row">
			<div class="col-sm-3">
				<div class="form-group">
					<label for="titolo-input" class="control-label">Operazione</label>
					<select name="fromula-operazione" class="form-control">
				    	<option value="" >Seleziona l'operazione...</option>
				    	<option value="Somma" ${risposta!=null && risposta.tipo.equals('RES_FORMULA') && risposta.getOperatore()=='Somma'?'selected':''}>Somma</option>
				    	<option value="Sottrazione" ${risposta!=null && risposta.tipo.equals('RES_FORMULA') && risposta.getOperatore().equals('Sottrazione')?'selected':''}>Sottrazione</option>
				    	<option value="Moltiplicazione" ${risposta!=null && risposta.tipo.equals('RES_FORMULA') && risposta.getOperatore().equals('Moltiplicazione')?'selected':''}>Moltiplicazione</option>
				    	<option value="Divisione" ${risposta!=null && risposta.tipo.equals('RES_FORMULA') && risposta.getOperatore().equals('Divisione')?'selected':''}>Divisione</option>
				    	<option value="Potenza" ${risposta!=null && risposta.tipo.equals('RES_FORMULA') && risposta.getOperatore().equals('Potenza')?'selected':''}>Potenza</option>
					</select>
				</div>
			</div>
			<div class="col-sm-3">
				<div class="form-group">
					<label for="titolo-input" class="control-label">Nome primo valore</label>
					<input type="text" name="formula-valore-1" class="form-control" placeholder="Nome primo valore" value="${risposta!=null && risposta.tipo.equals('RES_FORMULA')?risposta.valore1:''}"/>
				</div>
			</div>
			<div class="col-sm-3">
				<div class="form-group">
					<label for="titolo-input" class=" control-label">Nome secondo valore</label>
					<input type="text" name="formula-valore-2" class="form-control" placeholder="Nome secondo valore" value="${risposta!=null && risposta.tipo.equals('RES_FORMULA')?risposta.valore2:''}">
				</div>
			</div>
			<div class="col-sm-3">
				<div class="form-group">
					<label for="titolo-input" class=" control-label">Nome risultato</label>
					<input type="text" name="formula-risultato" class="form-control" placeholder="Nome risultato" value="${risposta!=null && risposta.tipo.equals('RES_FORMULA')?risposta.risultato:''}">
				</div>
			</div>
		</div>
	</div>
	<hr/>
</div>

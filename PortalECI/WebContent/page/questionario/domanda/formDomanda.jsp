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
	<c:set var="indice" value="${indice==null?domanda.id:indice}" scope="page"></c:set>
	<c:set var="indice" value="${null}" scope="request"></c:set>
	<input type="hidden" class="domanda-indice-input" name="domanda.indice" value="${indice}"/>
	<input type="hidden" name="domanda.gruppo${indice}" class="domanda-gruppo-input" value="${gruppo}" >
	<div class="form-horizontal">
		<div class="form-group">
			<div class="col-sm-12"><a class="btn btn-danger btn-xs pull-right margin-right elimina-domanda-button">elimina domanda</a></div>
		</div>
		<div class="form-group">
			<label for="domanda.testo" class="col-sm-2 control-label">Testo della domanda</label>
			<div class="col-sm-10">
				<textarea name="domanda.testo${indice}" class="form-control" placeholder="Testo della domanda">${domanda.testo}</textarea>
			</div>
		</div>
		<div class="form-group">
			<label for="domanda.placeholder" class="col-sm-2 control-label">Placeholder della domanda</label>
			<div class="col-sm-10">
				<div class="input-group">
	                <input type="text" name="domanda.placeholder${indice}" class="form-control placeholder-domanda-input" placeholder="Placeholder della domanda" value="${domanda.placeholder!=null?domanda.placeholder.substring(0, domanda.placeholder.length()-4):''}"/>
	                <span class="input-group-addon">_QST</span>
	              </div>
			</div>
		</div>
		<div class="form-group">
			<label for="domanda.obbligatoria" class="col-sm-2 control-label">Domanda obbligatoria</label>
			<div class="col-sm-10">
				<select name="domanda.obbligatoria${indice}" class="form-control domanda-obbligatoria-select">
			    	<option value="false">NO</option>
			    	<option value="true" ${domanda.obbligatoria?'selected':''}>SI</option>
				</select>
			</div>
		</div>
		<div class="form-group">
			<label for="risposta.tipo" class="col-sm-2 control-label">Tipo di risposta</label>
			<div class="col-sm-10">
				<select name="risposta.tipo${indice}" class="form-control tipo-risposta-select">
			    	<option value="" disabled selected>Seleziona Tipo...</option>
			    	<option value="RES_TEXT" ${domanda.risposta.tipo!=null && domanda.risposta.tipo.equals('RES_TEXT')?'selected':''}>Testo libero</option>
			    	<option value="RES_CHOICE" ${domanda.risposta.tipo!=null && domanda.risposta.tipo.equals('RES_CHOICE')?'selected':''}>Scelta multipla</option>
			    	<option value="RES_FORMULA" ${domanda.risposta.tipo!=null && domanda.risposta.tipo.equals('RES_FORMULA')?'selected':''}>Formula</option>
				</select>
			</div>
		</div>	
		<div class="form-group">
			<label for="risposta.placeholder" class="col-sm-2 control-label ">Placeholder della risposta</label>
			<div class="col-sm-10">
				<div class="input-group">
	                <input type="text" name="risposta.placeholder${indice}" class="form-control placeholder-risposta-input" placeholder="Placeholder della risposta" value="${domanda.risposta.placeholder!=null?domanda.risposta.placeholder.substring(0, domanda.risposta.placeholder.length()-4):''}"/>
	                <span class="input-group-addon">_RES</span>
	              </div>
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
					<select name="risposta.multipla${indice}" class="form-control domanda-obbligatoria-select">
			    		<option value="false">SI</option>
			    		<option value="true" ${domanda.risposta!=null && risposta.tipo.equals('RES_CHOICE') && risposta.multipla?'selected':''}>NO</option>
					</select>
				</div>
			</div>
		</div>
		<div class="form-group row">
			<label for="risposta.tipo" class="col-sm-2 control-label text-right">Opzioni</label>
			<div class="col-sm-10">
				<div class="lista-opzioni-div">
					<c:forEach items="${lista_opzioni}" var="opzione" >
						<div class="opzione-div row">
							<div class="col-sm-6">
								<div class="form-group">
									<input type="text" class="form-control" placeholder="Opzione" name="opzione${indice}" value="${opzione.testo}">
								</div>
							</div>
							<div class="col-sm-3 ">
								<button type="button" class="btn btn-danger btn-block rimuovi-opzione-button">elimina</button>
							</div>
							<div class="col-sm-3">
								<a class="btn btn-danger btn-block" onclick="aggiungiDomanda('Opzione', this)">
									<i class="fa fa-plus"></i> Aggiungi domanda
								</a>
							</div>
							<div class="clearfix"></div>
							<div class="lista-domande-opzioni-div">
								<input type="hidden" name="numero-domande-opzione${indice}" class="numero-domande-opzione-input" value="${opzione.domande==null?0:opzione.domande.size()}"/>
								<c:set var="domanda_original" value="${domanda}" scope="page"></c:set>
								<c:forEach items="${opzione.domande}" var="domanda_opzione" >
									<c:set var="domanda" value="${domanda_opzione}" scope="request"></c:set>
									<c:set var="gruppo" value="Opzione" scope="request"></c:set>
									<jsp:include page="formDomanda.jsp"></jsp:include>
								</c:forEach>
								<c:set var="domanda" value="${domanda_original}" scope="request"></c:set>
							</div>
						</div>
					</c:forEach>
					<div class="aggiungi-opzione-button-wp">
						<button type="button" class="btn btn-block btn-danger aggiungi-opzione-button" onclick="aggiungiOpzione(${indice}, this)" >
							<i class="fa fa-plus"></i>Aggiungi una opzione
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>

	
	<div class="form-group col-lg-offset-2 risposta-div risposta-RES_FORMULA" style="display: ${domanda!=null && domanda.risposta.tipo.equals('RES_FORMULA')?'block':'none'}">
		<div class="row">
			<div class="col-sm-3">
				<div class="form-group">
					<label for="titolo-input" class="control-label">Operazione</label>
					<select name="fromula-operazione${indice}" class="form-control col-sm-12">
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
					<input type="text" name="formula-valore-1${indice}" class="form-control" placeholder="Nome primo valore" value="${risposta!=null && risposta.tipo.equals('RES_FORMULA')?risposta.valore1:''}"/>
				</div>
			</div>
			<div class="col-sm-3">
				<div class="form-group">
					<label for="titolo-input" class=" control-label">Nome secondo valore</label>
					<input type="text" name="formula-valore-2${indice}" class="form-control" placeholder="Nome secondo valore" value="${risposta!=null && risposta.tipo.equals('RES_FORMULA')?risposta.valore2:''}">
				</div>
			</div>
			<div class="col-sm-3">
				<div class="form-group">
					<label for="titolo-input" class=" control-label">Nome risultato</label>
					<input type="text" name="formula-risultato${indice}" class="form-control" placeholder="Nome risultato" value="${risposta!=null && risposta.tipo.equals('RES_FORMULA')?risposta.risultato:''}">
				</div>
			</div>
		</div>
	</div>
	<hr/>
</div>

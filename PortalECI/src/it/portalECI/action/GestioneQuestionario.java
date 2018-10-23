package it.portalECI.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;

import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.CategoriaVerificaDTO;
import it.portalECI.DTO.DomandaOpzioneQuestionarioDTO;
import it.portalECI.DTO.DomandaQuestionarioDTO;
import it.portalECI.DTO.DomandaSchedaTecnicaQuestionarioDTO;
import it.portalECI.DTO.DomandaVerbaleQuestionarioDTO;
import it.portalECI.DTO.OpzioneRispostaQuestionarioDTO;
import it.portalECI.DTO.QuestionarioDTO;
import it.portalECI.DTO.RispostaFormulaQuestionarioDTO;
import it.portalECI.DTO.RispostaQuestionario;
import it.portalECI.DTO.RispostaSceltaQuestionarioDTO;
import it.portalECI.DTO.RispostaTestoQuestionarioDTO;
import it.portalECI.DTO.TemplateQuestionarioDTO;
import it.portalECI.DTO.TipoVerificaDTO;
import it.portalECI.DTO.VerbaleDTO;
import it.portalECI.Util.Utility;
import it.portalECI.bo.GestioneInterventoBO;
import it.portalECI.bo.GestioneQuestionarioBO;

@WebServlet(name = "gestioneQuestionario", urlPatterns = { "/gestioneQuestionario.do" })
public class GestioneQuestionario extends HttpServlet {
	
	int count = 0;

	public GestioneQuestionario() {
		// TODO Auto-generated constructor stub
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(Utility.validateSession(request,response,getServletContext()))return;
		
		Session session=SessionFacotryDAO.get().openSession();

		ArrayList<TipoVerificaDTO> tipi_verifica = GestioneInterventoBO.getTipoVerifica(session);
		ArrayList<CategoriaVerificaDTO> categorie_verifica = GestioneInterventoBO.getCategoriaVerifica(session);
		request.setAttribute("hibernateSession", session);
		request.setAttribute("tipi_verifica", tipi_verifica);
		request.setAttribute("categorie_verifica", categorie_verifica);

		String idQuestionario = request.getParameter("idQuestionario");
		if(idQuestionario == null || idQuestionario.isEmpty()) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/questionario/formQuestionario.jsp");
			dispatcher.forward(request,response);
			session.close();
			return;
		}

		QuestionarioDTO questionario = GestioneQuestionarioBO.getQuestionarioById(idQuestionario, session);
		request.setAttribute("questionario", questionario);
		
		RequestDispatcher dispatcher;
		if(request.getParameter("action") != null && request.getParameter("action").equals("modifica")) {
			dispatcher = getServletContext().getRequestDispatcher("/page/questionario/formQuestionario.jsp");
		}else {
			dispatcher = getServletContext().getRequestDispatcher("/page/questionario/dettaglioQuestionario.jsp");
		}
		dispatcher.forward(request,response);
		session.close();
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(Utility.validateSession(request,response,getServletContext()))return;	

		Session session=SessionFacotryDAO.get().openSession();
		request.setAttribute("hibernateSession", session);
		
		ArrayList<TipoVerificaDTO> tipi_verifica = GestioneInterventoBO.getTipoVerifica(session);
		ArrayList<CategoriaVerificaDTO> categorie_verifica = GestioneInterventoBO.getCategoriaVerifica(session);
		request.setAttribute("hibernateSession", session);
		request.setAttribute("tipi_verifica", tipi_verifica);
		request.setAttribute("categorie_verifica", categorie_verifica);
		QuestionarioDTO questionario = new QuestionarioDTO();
		questionario.setVersion(1);
		// se c'� l'id � una modifica se non c'� � l'inserimento di un nuovo questionario
		String idQuestionario = request.getParameter("idQuestionario");
		Integer id = null;
		if(idQuestionario != null && idQuestionario != "") {
			try {
				id = Integer.parseInt(idQuestionario);
			}catch (NumberFormatException e) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
				session.close();
				return;
			}		
			if(!GestioneQuestionarioBO.controlloQuestionarioInUso(id, session)) {
				questionario = GestioneQuestionarioBO.getQuestionarioById(id, session);
				if(questionario == null) {
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
					session.close();
					return;
				}
				questionario = setQuestionarioFromRequest(request, questionario, session);
				session.update(questionario);
			} else if(GestioneQuestionarioBO.controlloQuestionarioInUso(id, session)){
				questionario = setQuestionarioFromRequest(request, questionario, session);
				QuestionarioDTO oldQuest = GestioneQuestionarioBO.getQuestionarioById(id, session);
				if(oldQuest!=null) {
					questionario = addOldTemplate(questionario, oldQuest.getTemplateVerbale(), oldQuest.getTemplateSchedaTecnica(), session);
					questionario.setVersion(maxVersionOldQuest(oldQuest.getTipo(), session)+1);
				}
				session.save(questionario);
				//Devo aggiornare l'id di tutti i vebali in stato creato con id del verbale aggiornato con il nuovo verbale
				for(VerbaleDTO ver :GestioneQuestionarioBO.getVerbaliConQuestionarioAggiornato(oldQuest.getId(), session)) {
					ver.setQuestionarioID(questionario.getId());
					session.update(ver);
				}
			}
		} else {
			//nuovo questionario
			questionario.setDomandeSchedaTecnica(new ArrayList<DomandaSchedaTecnicaQuestionarioDTO>());
			questionario.setDomandeVerbale(new ArrayList<DomandaVerbaleQuestionarioDTO>());	
			questionario = setQuestionarioFromRequest(request, questionario, session);
			//vedo se esiste un questionario per quella verifica
			QuestionarioDTO lastQuestionario = GestioneQuestionarioBO.getLastQuestionarioByVerifica(questionario.getTipo(), session); 
			if(lastQuestionario != null && !GestioneQuestionarioBO.controlloQuestionarioInUso(lastQuestionario.getId(), session)) {
				questionario = GestioneQuestionarioBO.getQuestionarioById(lastQuestionario.getId(), session);
				if(questionario == null) {
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
					session.close();
					return;
				}
				//setto il vecchio id e gli passo le nuove info
				questionario = setQuestionarioFromRequest(request, questionario, session);
				session.update(questionario);
			} else	if(lastQuestionario == null || (lastQuestionario != null && GestioneQuestionarioBO.controlloQuestionarioInUso(lastQuestionario.getId(), session))){
				if(lastQuestionario != null) {
					questionario = addOldTemplate(questionario, lastQuestionario.getTemplateVerbale(), lastQuestionario.getTemplateSchedaTecnica(), session);
					questionario.setVersion(maxVersionOldQuest(lastQuestionario.getTipo(), session)+1);
					session.save(questionario);
					//Devo aggiornare l'id di tutti i vebali in stato creato con id del verbale aggiornato con il nuovo verbale
					for(VerbaleDTO ver :GestioneQuestionarioBO.getVerbaliConQuestionarioAggiornato(lastQuestionario.getId(), session)) {
						ver.setQuestionarioID(questionario.getId());
						session.update(ver);
						
					}				
				} else {
					session.save(questionario);
				}
			}
		}
		Transaction transaction = session.beginTransaction();
		try {
			transaction.commit();
		}
		catch (Exception e) {
			System.out.println("ROLLBACK");
			e.printStackTrace();
			transaction.rollback();
			request.setAttribute("error", "Non &egrave; stato possibile salvare le modifiche");
		}
		request.setAttribute("questionario", questionario);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/questionario/dettaglioQuestionario.jsp");
		dispatcher.forward(request, response);
		session.close();
		return;
	}
	
	
	private QuestionarioDTO setQuestionarioFromRequest(HttpServletRequest request, QuestionarioDTO questionario, Session hibernateSession) {
		questionario.setTitolo(request.getParameter("titolo"));
		
		String tipoQuestionarioValue = request.getParameter("tipo");
		String[] tipoQuestionario = tipoQuestionarioValue.split("_");
		questionario.setTipo(GestioneInterventoBO.getTipoVerifica(tipoQuestionario[0], hibernateSession));
				
		if(questionario.getDomandeVerbale()!=null) {
			questionario.getDomandeVerbale().clear();
		} else {
			questionario.setDomandeVerbale(new ArrayList<DomandaVerbaleQuestionarioDTO>());
		}
		if(questionario.getDomandeSchedaTecnica()!=null) {
			questionario.getDomandeSchedaTecnica().clear();
		} else {
			questionario.setDomandeSchedaTecnica(new ArrayList<DomandaSchedaTecnicaQuestionarioDTO>());
		}
		
		String[] domandaIndice = request.getParameterValues("domanda.indice");
		
		for(int i=0; i<domandaIndice.length;i++) {
			count = i;
			getDomandaFromRequest(request,questionario,hibernateSession,domandaIndice, i);
		}
		
		return questionario;
	}
	
	private DomandaQuestionarioDTO getDomandaFromRequest(HttpServletRequest request, QuestionarioDTO questionario, Session hibernateSession, String[] domandaIndice, int i) {
		String indice = domandaIndice[i];
		
		DomandaQuestionarioDTO domandaQuestionario = new  DomandaQuestionarioDTO();
		String domandaGruppo = request.getParameter("domanda.gruppo"+indice);
		if(domandaGruppo.equals("Verbale")) {
			DomandaVerbaleQuestionarioDTO domanda = new DomandaVerbaleQuestionarioDTO();
			domanda.setTesto(request.getParameter("domanda.testo"+indice));
			domanda.setObbligatoria(new Boolean(request.getParameter("domanda.obbligatoria"+indice)));
			domanda.setPlaceholder(request.getParameter("domanda.placeholder"+indice)+"_QST");
			domanda.setQuestionario(questionario);
			domanda.setPosizione(questionario.getDomandeVerbale().size());
			
			questionario.getDomandeVerbale().add((DomandaVerbaleQuestionarioDTO)domanda);
			domandaQuestionario = domanda;
			
		}else if(domandaGruppo.equals("SchedaTecnica")) {
			DomandaSchedaTecnicaQuestionarioDTO domanda = new DomandaSchedaTecnicaQuestionarioDTO();
			domanda.setTesto(request.getParameter("domanda.testo"+indice));
			domanda.setObbligatoria(new Boolean(request.getParameter("domanda.obbligatoria"+indice)));
			domanda.setPlaceholder(request.getParameter("domanda.placeholder"+indice)+"_QST");
			domanda.setQuestionario(questionario);
			domanda.setPosizione(questionario.getDomandeSchedaTecnica().size());
			
			questionario.getDomandeSchedaTecnica().add((DomandaSchedaTecnicaQuestionarioDTO)domanda);
			domandaQuestionario = domanda;
		}else if(domandaGruppo.equals("Opzione")) {
			DomandaOpzioneQuestionarioDTO domanda = new DomandaOpzioneQuestionarioDTO();
			domanda.setTesto(request.getParameter("domanda.testo"+indice));
			domanda.setObbligatoria(new Boolean(request.getParameter("domanda.obbligatoria"+indice)));
			domanda.setPlaceholder(request.getParameter("domanda.placeholder"+indice)+"_QST");
			//domanda.setPosizione(questionario.getDomandeSchedaTecnica().size());
			domandaQuestionario = domanda;
		}
		
		String rispostaTipo = request.getParameter("risposta.tipo"+indice);
		
		if(rispostaTipo.equals(RispostaQuestionario.TIPO_TESTO)) {
			
			RispostaTestoQuestionarioDTO risposta = new RispostaTestoQuestionarioDTO();
			risposta.setTipo(RispostaQuestionario.TIPO_TESTO);
			risposta.setPlaceholder(request.getParameter("risposta.placeholder"+indice)+"_RES");
			risposta.setDomanda(domandaQuestionario);
			domandaQuestionario.setRisposta(risposta);
			
		}else if(rispostaTipo.equals(RispostaQuestionario.TIPO_FORMULA)){
			
			RispostaFormulaQuestionarioDTO risposta = new RispostaFormulaQuestionarioDTO();
			risposta.setTipo(RispostaQuestionario.TIPO_FORMULA);
			risposta.setValore1(request.getParameter("formula-valore-1"+indice));
			risposta.setValore2(request.getParameter("formula-valore-2"+indice));
			risposta.setOperatore(request.getParameter("fromula-operazione"+indice));
			risposta.setRisultato(request.getParameter("formula-risultato"+indice));
			risposta.setPlaceholder(request.getParameter("risposta.placeholder"+indice)+"_RES");
			risposta.setDomanda(domandaQuestionario);
			domandaQuestionario.setRisposta(risposta);
							
		}else if(rispostaTipo.equals(RispostaQuestionario.TIPO_SCELTA)){
			RispostaSceltaQuestionarioDTO risposta = new RispostaSceltaQuestionarioDTO();
			risposta.setTipo(RispostaQuestionario.TIPO_SCELTA);
			String placeholderRisposta = request.getParameter("risposta.placeholder"+indice);
			risposta.setPlaceholder(placeholderRisposta+"_RES");
			risposta.setDomanda(domandaQuestionario);
			risposta.setMultipla(new Boolean(request.getParameter("risposta.multipla"+indice)));
			List<OpzioneRispostaQuestionarioDTO> listaOpzioni = new ArrayList<OpzioneRispostaQuestionarioDTO>();
			String[] nomiOpzione = request.getParameterValues("opzione"+indice);
			String[] numeroDomandeOpzioneParams = request.getParameterValues("numero-domande-opzione"+indice);
			for(int idx=0;idx<nomiOpzione.length; idx++) {
				OpzioneRispostaQuestionarioDTO opzione = new OpzioneRispostaQuestionarioDTO();
				opzione.setPosizione(idx);
				opzione.setTesto(nomiOpzione[idx]);
				opzione.setRisposta(risposta);
				opzione.setPlaceholder(placeholderRisposta+"_OPT"+String.valueOf(idx+1));
				int numeroDomandeOpzione = Integer.parseInt(numeroDomandeOpzioneParams[idx]);
				List<DomandaOpzioneQuestionarioDTO> listadomandeOpzione  = new ArrayList<DomandaOpzioneQuestionarioDTO>();
				for(int k=0; k<numeroDomandeOpzione;k++ ) {
					count++;
					DomandaOpzioneQuestionarioDTO domandaOpzione = (DomandaOpzioneQuestionarioDTO)getDomandaFromRequest(request, questionario, hibernateSession, domandaIndice, count);
					domandaOpzione.setPosizione(k);
					domandaOpzione.setOpzione(opzione);
					listadomandeOpzione.add(domandaOpzione);
				}
				opzione.setDomande(listadomandeOpzione);
				listaOpzioni.add(opzione);
			}
			risposta.setOpzioni(listaOpzioni);
			domandaQuestionario.setRisposta(risposta);
		}
		return domandaQuestionario;
	}
	
	public static int maxVersionOldQuest(TipoVerificaDTO tipoVerifica, Session session) {
		QuestionarioDTO lastQuestionario = GestioneQuestionarioBO.getLastQuestionarioByVerifica(tipoVerifica, session); 
		
		return lastQuestionario.getVersion();
	}
	
	public static QuestionarioDTO addOldTemplate(QuestionarioDTO ques, TemplateQuestionarioDTO certificato, TemplateQuestionarioDTO skTec, Session session) {
		if(certificato!=null) {
			TemplateQuestionarioDTO newTemplateVerbale = new TemplateQuestionarioDTO();
			newTemplateVerbale.setTitolo(certificato.getTitolo());
			newTemplateVerbale.setTemplate(certificato.getTemplate());
			newTemplateVerbale.setHeader(certificato.getHeader());
			newTemplateVerbale.setFooter(certificato.getFooter());
			session.save(newTemplateVerbale);
			ques.setTemplateVerbale(newTemplateVerbale);
		}
		if(skTec != null) {
			TemplateQuestionarioDTO newTemplateSkTec = new TemplateQuestionarioDTO();
			newTemplateSkTec.setTitolo(skTec.getTitolo());
			newTemplateSkTec.setTemplate(skTec.getTemplate());
			newTemplateSkTec.setHeader(skTec.getHeader());
			newTemplateSkTec.setFooter(skTec.getFooter());
			session.save(newTemplateSkTec);
			ques.setTemplateSchedaTecnica(newTemplateSkTec);
		}
		return ques;
	}
}
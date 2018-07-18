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
import it.portalECI.DTO.DomandaQuestionarioDTO;
import it.portalECI.DTO.DomandaSchedaTecnicaQuestionarioDTO;
import it.portalECI.DTO.DomandaVerbaleQuestionarioDTO;
import it.portalECI.DTO.OpzioneRispostaQuestionarioDTO;
import it.portalECI.DTO.QuestionarioDTO;
import it.portalECI.DTO.RispostaFormulaQuestionarioDTO;
import it.portalECI.DTO.RispostaQuestionario;
import it.portalECI.DTO.RispostaSceltaQuestionarioDTO;
import it.portalECI.DTO.RispostaTestoQuestionarioDTO;
import it.portalECI.DTO.TipoVerificaDTO;
import it.portalECI.Util.Utility;
import it.portalECI.bo.GestioneInterventoBO;
import it.portalECI.bo.GestioneQuestionarioBO;

@WebServlet(name = "gestioneQuestionario", urlPatterns = { "/gestioneQuestionario.do" })
public class GestioneQuestionario extends HttpServlet {

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

		Integer id = null;
		try {
			id = Integer.parseInt(idQuestionario);
		}catch (NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			session.close();
			return;
		}


		QuestionarioDTO questionario = GestioneQuestionarioBO.getQuestionarioById(id, session);
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
		
		if(request.getParameter("_method")!= null && request.getParameter("_method").equalsIgnoreCase("PUT")) {
			doPut(request,response);
			return;
		}
		
		if(Utility.validateSession(request,response,getServletContext()))return;
		
		Session session=SessionFacotryDAO.get().openSession();
		request.setAttribute("hibernateSession", session);

		Transaction transaction = session.beginTransaction();
		QuestionarioDTO questionario = new QuestionarioDTO();
		questionario.setDomandeSchedaTecnica(new ArrayList<DomandaSchedaTecnicaQuestionarioDTO>());
		questionario.setDomandeVerbale(new ArrayList<DomandaVerbaleQuestionarioDTO>());
		
		questionario = setQuestionarioFromRequest(request, questionario, session);
		
		session.save(questionario);
		transaction.commit();
		
		request.setAttribute("questionario", questionario);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/questionario/dettaglioQuestionario.jsp");
		dispatcher.forward(request,response);
		session.close();
	
	}
	
	public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(Utility.validateSession(request,response,getServletContext()))return;
		
		Session session=SessionFacotryDAO.get().openSession();
		Transaction transaction = session.beginTransaction();
	
		ArrayList<TipoVerificaDTO> tipi_verifica = GestioneInterventoBO.getTipoVerifica(session);
		ArrayList<CategoriaVerificaDTO> categorie_verifica = GestioneInterventoBO.getCategoriaVerifica(session);
		request.setAttribute("hibernateSession", session);
		request.setAttribute("tipi_verifica", tipi_verifica);
		request.setAttribute("categorie_verifica", categorie_verifica);

		String idQuestionario = request.getParameter("idQuestionario");
		
		Integer id = null;
		try {
			id = Integer.parseInt(idQuestionario);
		}catch (NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			session.close();
			return;
		}
		
		QuestionarioDTO questionario = GestioneQuestionarioBO.getQuestionarioById(id, session);
		
		if(questionario == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			session.close();
			return;
		}
		
		questionario = setQuestionarioFromRequest(request, questionario, session);
		request.setAttribute("questionario", questionario);
		try {
			session.update(questionario);
			transaction.commit();
		}
		catch (Exception e) {
			System.out.println("ROLLBACK");
			e.printStackTrace();
			transaction.rollback();
			request.setAttribute("error", "Non è stato possibile salvare le modifiche");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/questionario/formQuestionario.jsp");
			dispatcher.forward(request, response);
			session.close();
			return;
		}
		
				
		request.setAttribute("questionario", questionario);
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/questionario/dettaglioQuestionario.jsp");
		dispatcher.forward(request, response);
		session.close();
		

	}
	
	private QuestionarioDTO setQuestionarioFromRequest(HttpServletRequest request, QuestionarioDTO questionario, Session hibernateSession) {
		questionario.setTitolo(request.getParameter("titolo"));
		
		String tipoQuestionarioValue = request.getParameter("tipo");
		String[] tipoQuestionario = tipoQuestionarioValue.split("_");
		questionario.setTipo(GestioneInterventoBO.getTipoVerifica(tipoQuestionario[0], hibernateSession));
				
		if(questionario.getDomandeVerbale()!=null) questionario.getDomandeVerbale().clear();
		if(questionario.getDomandeSchedaTecnica()!=null) questionario.getDomandeSchedaTecnica().clear();

		
		String[] domandaGruppo = request.getParameterValues("domanda.gruppo");
		String[] domandaTesto = request.getParameterValues("domanda.testo");
		String[] domandaObbligatoria = request.getParameterValues("domanda.obbligatoria");
		String[] domandaPlaceholder = request.getParameterValues("domanda.placeholder");
		String[] rispostaTipo = request.getParameterValues("risposta.tipo");
		String[] numeroOpzioni = request.getParameterValues("numero-opzioni");
		String[] nomiOpzioni = request.getParameterValues("opzione");
		String[] rispostaValore1 = request.getParameterValues("formula-valore-1");
		String[] rispostaValore2 = request.getParameterValues("formula-valore-2");
		String[] formulaOperazione = request.getParameterValues("fromula-operazione");
		String[] formulaRisultato = request.getParameterValues("formula-risultato");
		
		//TODO: gestire il placeholder della risposta sulla view
		String[] rispostaPlaceholder = request.getParameterValues("domanda.placeholder");
		
		
		int indexOption = 0;
		int orderSchedaTecnica = 0;
		int orderVerbale=0;
		for(int i=0;i<domandaGruppo.length;i++) {
			
			DomandaQuestionarioDTO domandaQuestionario = new  DomandaQuestionarioDTO();
			
			if(domandaGruppo[i].equals("Verbale")) {
				DomandaVerbaleQuestionarioDTO domanda = new DomandaVerbaleQuestionarioDTO();
				domanda.setTesto(domandaTesto[i]);
				domanda.setObbligatoria(new Boolean(domandaObbligatoria[i]));
				domanda.setPlaceholder(domandaPlaceholder[i]+"_QST");
				domanda.setQuestionario(questionario);
				domanda.setPosizione(orderVerbale);
				
				questionario.getDomandeVerbale().add((DomandaVerbaleQuestionarioDTO)domanda);
				domandaQuestionario = domanda;
				
				orderVerbale++;
			}else if(domandaGruppo[i].equals("SchedaTecnica")) {
				DomandaSchedaTecnicaQuestionarioDTO domanda = new DomandaSchedaTecnicaQuestionarioDTO();
				domanda.setTesto(domandaTesto[i]);
				domanda.setObbligatoria(new Boolean(domandaObbligatoria[i]));
				domanda.setPlaceholder(domandaPlaceholder[i]+"_QST");
				domanda.setQuestionario(questionario);
				domanda.setPosizione(orderSchedaTecnica);
				
				questionario.getDomandeSchedaTecnica().add((DomandaSchedaTecnicaQuestionarioDTO)domanda);
				domandaQuestionario = domanda;
				
				orderSchedaTecnica++;
			}else if(domandaGruppo[i].equals("Opzione")) {
				
			}

			
			if(rispostaTipo[i].equals(RispostaQuestionario.TIPO_TESTO)) {
				
				RispostaTestoQuestionarioDTO risposta = new RispostaTestoQuestionarioDTO();
				risposta.setTipo(RispostaQuestionario.TIPO_TESTO);
				risposta.setPlaceholder(rispostaPlaceholder[i]+"_RES");
				risposta.setDomanda(domandaQuestionario);
				domandaQuestionario.setRisposta(risposta);
				
			}else if(rispostaTipo[i].equals(RispostaQuestionario.TIPO_FORMULA)){
				
				RispostaFormulaQuestionarioDTO risposta = new RispostaFormulaQuestionarioDTO();
				risposta.setTipo(RispostaQuestionario.TIPO_FORMULA);
				risposta.setValore1(rispostaValore1[i]);
				risposta.setValore2(rispostaValore2[i]);
				risposta.setOperatore(formulaOperazione[i]);
				risposta.setRisultato(formulaRisultato[i]);
				risposta.setPlaceholder(rispostaPlaceholder[i]+"_RES");
				risposta.setDomanda(domandaQuestionario);
				domandaQuestionario.setRisposta(risposta);
								
			}else if(rispostaTipo[i].equals(RispostaQuestionario.TIPO_SCELTA)){
				RispostaSceltaQuestionarioDTO risposta = new RispostaSceltaQuestionarioDTO();
				int numeroScelte = Integer.parseInt(numeroOpzioni[i]);
				List<OpzioneRispostaQuestionarioDTO> listaOpzioni = new ArrayList<OpzioneRispostaQuestionarioDTO>();
				
				for(int idx=0;idx<numeroScelte; idx++) {
					OpzioneRispostaQuestionarioDTO opzione = new OpzioneRispostaQuestionarioDTO();
					opzione.setPosizione(idx);
					opzione.setTesto(nomiOpzioni[indexOption]);
					opzione.setRisposta(risposta);
					listaOpzioni.add(opzione);
					indexOption++;
				}
				risposta.setTipo(RispostaQuestionario.TIPO_SCELTA);
				risposta.setOpzioni(listaOpzioni);
				risposta.setPlaceholder(rispostaPlaceholder[i]+"_RES");
				risposta.setDomanda(domandaQuestionario);
				domandaQuestionario.setRisposta(risposta);
				
			}
		}
		
		return questionario;
	}
}

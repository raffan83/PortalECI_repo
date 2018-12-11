package it.portalECI.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import it.portalECI.DAO.GestioneVerbaleDAO;
import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.ColonnaTabellaVerbaleDTO;
import it.portalECI.DTO.OpzioneRispostaQuestionarioDTO;
import it.portalECI.DTO.OpzioneRispostaVerbaleDTO;
import it.portalECI.DTO.RispostaFormulaQuestionarioDTO;
import it.portalECI.DTO.RispostaFormulaVerbaleDTO;
import it.portalECI.DTO.RispostaSceltaQuestionarioDTO;
import it.portalECI.DTO.RispostaSceltaVerbaleDTO;
import it.portalECI.DTO.RispostaTabellaVerbaleDTO;
import it.portalECI.DTO.RispostaTestoQuestionarioDTO;
import it.portalECI.DTO.RispostaTestoVerbaleDTO;
import it.portalECI.DTO.RispostaVerbaleDTO;

@WebServlet(name = "gestioneTabellaVerbale", urlPatterns = { "/gestioneTabellaVerbale.do" })
public class GestioneTabellaVerbale extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public GestioneTabellaVerbale() {
		super();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Session session=SessionFacotryDAO.get().openSession();
		Transaction transaction = session.beginTransaction();
		String rispostaId = request.getParameter("rispostaId");
		Integer rispostaidInt = Integer.parseInt(rispostaId);
		RispostaTabellaVerbaleDTO risposta = (RispostaTabellaVerbaleDTO) session.get(RispostaTabellaVerbaleDTO.class, rispostaidInt);
		
		List<ColonnaTabellaVerbaleDTO> colonne=new ArrayList<ColonnaTabellaVerbaleDTO>();
		List<RispostaVerbaleDTO> risposte=new ArrayList<RispostaVerbaleDTO>();

		colonne.addAll(risposta.getColonne());
		
		Collections.sort(colonne, new Comparator<ColonnaTabellaVerbaleDTO>() {
	        @Override
	        public int compare(ColonnaTabellaVerbaleDTO op2, ColonnaTabellaVerbaleDTO op1){
				int pos1=op1.getColonnaQuestionario().getPosizione().intValue();
				int pos2=op2.getColonnaQuestionario().getPosizione().intValue();
	            return  pos2 - pos1;
	        }
	    });
		
		for(ColonnaTabellaVerbaleDTO colonna: colonne){
			switch (colonna.getDomanda().getRisposta().getTipo()) {
			case RispostaVerbaleDTO.TIPO_TESTO:
				RispostaTestoVerbaleDTO rispostaTesto =  new RispostaTestoVerbaleDTO();
				RispostaTestoQuestionarioDTO rispostaQuestionario =(RispostaTestoQuestionarioDTO)session.get(RispostaTestoQuestionarioDTO.class, colonna.getColonnaQuestionario().getDomanda().getRisposta().getId());
				rispostaTesto.setRispostaQuestionario(rispostaQuestionario);
				session.save(rispostaTesto);
				colonna.getRisposte().add(rispostaTesto);
				risposte.add(rispostaTesto);
				break;
			case RispostaVerbaleDTO.TIPO_SCELTA:
				RispostaSceltaVerbaleDTO rispostaScelta = new RispostaSceltaVerbaleDTO();
				RispostaSceltaQuestionarioDTO rispostaSceltaQuestionario = (RispostaSceltaQuestionarioDTO) session.get(RispostaSceltaQuestionarioDTO.class, colonna.getColonnaQuestionario().getDomanda().getRisposta().getId());
				rispostaScelta.setRispostaQuestionario(rispostaSceltaQuestionario);
				rispostaScelta.setOpzioni(new HashSet<OpzioneRispostaVerbaleDTO>());
				for(OpzioneRispostaQuestionarioDTO opzioneQuestionario: rispostaSceltaQuestionario.getOpzioni()) {
					OpzioneRispostaVerbaleDTO opzioneRispostaVerbale = new OpzioneRispostaVerbaleDTO();
					opzioneRispostaVerbale.setRisposta(rispostaScelta);
					opzioneRispostaVerbale.setOpzioneQuestionario(opzioneQuestionario);
					rispostaScelta.getOpzioni().add(opzioneRispostaVerbale);
					//Non si gestiscono domande annidate perchè le opzioni delle domande figlie di una tabella non possono avere altre domande
				}
				session.save(rispostaScelta);
				colonna.getRisposte().add(rispostaScelta);
				risposte.add(rispostaScelta);
				break;
			case RispostaVerbaleDTO.TIPO_FORMULA:
				RispostaFormulaVerbaleDTO rispostaFormula =  new RispostaFormulaVerbaleDTO();
				RispostaFormulaQuestionarioDTO rispostaFormulaQuestionario = (RispostaFormulaQuestionarioDTO) session.get(RispostaFormulaQuestionarioDTO.class, colonna.getColonnaQuestionario().getDomanda().getRisposta().getId());
				rispostaFormula.setRispostaQuestionario(rispostaFormulaQuestionario);
				session.save(rispostaFormula);
				colonna.getRisposte().add(rispostaFormula);
				risposte.add(rispostaFormula);
				break;
			default:
				break;
			}
			
			session.update(colonna);
		}
		transaction.commit();
		request.setCharacterEncoding("UTF-8");
		request.setAttribute("rispostaParent", risposta);
		request.setAttribute("risposte", risposte);
		request.setAttribute("colonne", colonne);
		request.setAttribute("hibernateSession", session);
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/rigaTabella.jsp");
		dispatcher.forward(request,response);
	}
}

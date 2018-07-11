package it.portalECI.action;

import java.io.IOException;
import java.util.ArrayList;

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
import it.portalECI.DTO.QuestionarioDTO;
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
		
		if(Utility.validateSession(request,response,getServletContext()))return;
		
		if(request.getParameter("_method")!= null && request.getParameter("_method").equalsIgnoreCase("PUT")) {
			doPut(request,response);
			return;
		}
		
		Session session=SessionFacotryDAO.get().openSession();
		
		QuestionarioDTO questionario = new QuestionarioDTO();
		
		questionario = setQuestionarioFromRequest(request, questionario, session);
		
		Transaction transaction = session.beginTransaction();
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
		
		Transaction transaction = session.beginTransaction();
		session.update(questionario);
		transaction.commit();
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
		
		return questionario;
	}
}

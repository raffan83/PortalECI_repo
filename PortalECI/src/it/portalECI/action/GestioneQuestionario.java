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

		String idQuestionario = request.getParameter("idQuestionario");
		if(idQuestionario == null || idQuestionario.isEmpty()) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/questionario/formQuestionario.jsp");
			dispatcher.forward(request,response);
			return;
		}

		Integer id = null;
		try {
			id = Integer.parseUnsignedInt(idQuestionario);
		}catch (NumberFormatException e) {
			response.sendRedirect("gestioneListaQuestionari.do");
			return;
		}

		Session session=SessionFacotryDAO.get().openSession();

		QuestionarioDTO questionario = GestioneQuestionarioBO.getQuestionarioById(id, session);

		ArrayList<TipoVerificaDTO> tipi_verifica = GestioneInterventoBO.getTipoVerifica(session);
		ArrayList<CategoriaVerificaDTO> categorie_verifica = GestioneInterventoBO.getCategoriaVerifica(session);

		request.getSession().setAttribute("questionario", questionario);
		request.getSession().setAttribute("tipi_verifica", tipi_verifica);
		request.getSession().setAttribute("categorie_verifica", categorie_verifica);

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/questionario/dettaglioQuestionario.jsp");
		dispatcher.forward(request,response);
	}
}

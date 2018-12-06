package it.portalECI.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import it.portalECI.DAO.GestioneVerbaleDAO;
import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.RispostaTabellaVerbaleDTO;

@WebServlet(name = "gestioneTabellaVerbale", urlPatterns = { "/gestioneTabellaVerbale.do" })
public class GestioneTabellaVerbale extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public GestioneTabellaVerbale() {
		super();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Session session=SessionFacotryDAO.get().openSession();
		String rispostaId = request.getParameter("rispostaId");
		RispostaTabellaVerbaleDTO risposta = (RispostaTabellaVerbaleDTO) session.get(RispostaTabellaVerbaleDTO.class, rispostaId);
		
		request.setCharacterEncoding("UTF-8");
		request.setAttribute("rispostaTabella", risposta);
		request.setAttribute("hibernateSession", session);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/gestioneVerbaleDettaglioTabella.jsp");
		dispatcher.forward(request,response);
	}
}

package it.portalECI.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "gestioneColonnaTabellaQuestionario", urlPatterns = { "/gestioneColonnaTabellaQuestionario.do" })
public class GestioneColonnaTabellaQuestionario extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public GestioneColonnaTabellaQuestionario() {
		super();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		request.setCharacterEncoding("UTF-8");
		request.setAttribute("gruppo", request.getParameter("gruppo"));
		request.setAttribute("indice", request.getParameter("indice"));
		request.setAttribute("indiceColonna", request.getParameter("indiceColonna"));
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/questionario/domanda/formColonnaTabella.jsp");
		dispatcher.forward(request,response);
	}

}

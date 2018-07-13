package it.portalECI.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "gestioneDomandeQuestionario", urlPatterns = { "/gestioneDomandeQuestionario.do" })
public class GestioneDomandeQuestionario extends HttpServlet {

	public GestioneDomandeQuestionario() {
		super();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println( request.getParameter("gruppo"));
		request.setAttribute("gruppo", request.getParameter("gruppo"));
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/questionario/domanda/formDomanda.jsp");
		dispatcher.forward(request,response);
	}

}

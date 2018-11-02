package it.portalECI.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;

import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.bo.GestioneQuestionarioBO;

/**
 * Servlet implementation class GestioneUtenti
 */
@WebServlet(name = "gestionePlaceholder", urlPatterns = { "/gestionePlaceholder.do" })
public class GestionePlaceholder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestionePlaceholder() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// TODO Auto-generated method stub
		//doPost(request, response);
		
		//if(Utility.validateSession(request,response,getServletContext()))return;
		String idQuestionario = request.getParameter("idQuestionario");
		String type = request.getParameter("type");
		
		Session session=SessionFacotryDAO.get().openSession();
		Transaction transaction = session.beginTransaction();
		
		response.setContentType("text/html");
		JsonArray responseJson = new JsonArray();
		PrintWriter out = response.getWriter();
		
		List<String> retrievePlaceholder= GestioneQuestionarioBO.getQuestionariPlaceholder(type, idQuestionario,session);
		for (String placeholder : retrievePlaceholder) {
			JsonPrimitive element = new JsonPrimitive(placeholder);
			responseJson.add(element);
			//responseJson.addProperty("placeholder", placeholder);
		}
		out.println(responseJson);
		
		session.getTransaction().commit();
		session.close();
	

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}

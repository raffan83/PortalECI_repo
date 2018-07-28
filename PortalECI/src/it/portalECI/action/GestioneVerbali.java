package it.portalECI.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.google.gson.JsonObject;

import it.portalECI.DAO.GestioneStatoInterventoDAO;
import it.portalECI.DAO.GestioneStatoVerbaleDAO;
import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.InterventoDTO;
import it.portalECI.DTO.StatoInterventoDTO;
import it.portalECI.DTO.StatoVerbaleDTO;
import it.portalECI.DTO.VerbaleDTO;
import it.portalECI.Util.Utility;
import it.portalECI.bo.GestioneInterventoBO;
import it.portalECI.bo.GestioneVerbaleBO;

/**
 * Servlet implementation class GestioneIntervento
 */
@WebServlet(name = "gestioneVerbale", urlPatterns = { "/gestioneVerbale.do" })
public class GestioneVerbali extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestioneVerbali() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(Utility.validateSession(request,response,getServletContext()))return;
		
		Session session=SessionFacotryDAO.get().openSession();
		session.beginTransaction();
		
		String idVerbale=request.getParameter("idVerbale");
		
		VerbaleDTO verbale= GestioneVerbaleBO.getVerbale(idVerbale, session);		
		
		JsonObject myObj = new JsonObject();
		PrintWriter  out = response.getWriter();
		String action=request.getParameter("action");
		
		if(action !=null && action.equals("cambioStato")){			 					
			
			String stato = request.getParameter("stato" );										
				
			GestioneVerbaleBO.cambioStato( verbale, GestioneStatoVerbaleDAO.getStatoVerbaleById( Integer.parseInt(stato), session) , session);	
				
			myObj.addProperty("success", true);
			myObj.addProperty("messaggio", "Stato modificato con successo");
		
			out.print(myObj);
		} else if(action !=null && action.equals("generaCertificato")) {
			
		} else {
			//caso genericoc della ricerca del verbale per aprire gestioneVerbali
			request.getSession().setAttribute("verbale", verbale);
			request.setAttribute("hibernateSession", session);
			
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/gestioneVerbale.jsp");
			dispatcher.forward(request,response);
				
		}	
	
		session.getTransaction().commit();
		session.close();	
	}
}
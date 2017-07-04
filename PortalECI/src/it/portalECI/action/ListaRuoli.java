package it.portalECI.action;

import it.portalECI.DAO.GestioneAccessoDAO;
import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.CompanyDTO;
import it.portalECI.DTO.PermessoDTO;
import it.portalECI.DTO.RuoloDTO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.Exception.ECIException;
import it.portalECI.Util.Utility;
import it.portalECI.bo.GestioneUtenteBO;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class listaCampioni
 */
@WebServlet(name="listaRuoli" , urlPatterns = { "/listaRuoli.do" })

public class ListaRuoli extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListaRuoli() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(Utility.validateSession(request,response,getServletContext()))return;
		Session session = SessionFacotryDAO.get().openSession();
		session.beginTransaction();
		
		response.setContentType("text/html");
		
		try 
		{
			String idUtente = request.getParameter("idUtente");
			if(idUtente != null && !idUtente.equals("")){
				ArrayList<RuoloDTO> listaRuoli =  (ArrayList<RuoloDTO>) GestioneAccessoDAO.getListRole();
				ArrayList<PermessoDTO> listaPermessi =  (ArrayList<PermessoDTO>) GestioneAccessoDAO.getListPermission();
		        UtenteDTO utente = GestioneUtenteBO.getUtenteById(idUtente, session);

		        request.getSession().setAttribute("listaRuoli",listaRuoli);
		        request.getSession().setAttribute("idUtente",idUtente);
		        request.getSession().setAttribute("utente",utente);

				
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/listaRuoliAssociazione.jsp");
		     	dispatcher.forward(request,response);
			}else{
				ArrayList<RuoloDTO> listaRuoli =  (ArrayList<RuoloDTO>) GestioneAccessoDAO.getListRole();
				ArrayList<PermessoDTO> listaPermessi =  (ArrayList<PermessoDTO>) GestioneAccessoDAO.getListPermission();
	
		        request.getSession().setAttribute("listaRuoli",listaRuoli);
		        request.getSession().setAttribute("listaPermessi",listaPermessi);
				
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/listaRuoli.jsp");
		     	dispatcher.forward(request,response);
			}
			session.close();
		} 
		catch (Exception ex) {
			
		//	ex.printStackTrace();
			session.close();
		     request.setAttribute("error",ECIException.callException(ex));
			 RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/site/error.jsp");
		     dispatcher.forward(request,response);
		}
	
	}

}

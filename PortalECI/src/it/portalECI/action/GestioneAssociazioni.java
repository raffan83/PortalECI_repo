package it.portalECI.action;



import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import it.portalECI.DAO.GestioneAccessoDAO;
import it.portalECI.DTO.PermessoDTO;
import it.portalECI.DTO.RuoloDTO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.Exception.ECIException;
import it.portalECI.Util.Utility;

/**
 * Servlet implementation class listaCampioni
 */
@WebServlet(name="gestioneAssociazioni" , urlPatterns = { "/gestioneAssociazioni.do" })

public class GestioneAssociazioni extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestioneAssociazioni() {
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

		
		response.setContentType("text/html");
		
		try 
		{
			
			ArrayList<UtenteDTO> listaUtenti =  (ArrayList<UtenteDTO>) GestioneAccessoDAO.getListUser();
			ArrayList<PermessoDTO> listaPermessi =  (ArrayList<PermessoDTO>) GestioneAccessoDAO.getListPermission();
			ArrayList<RuoloDTO> listaRuoli =  (ArrayList<RuoloDTO>) GestioneAccessoDAO.getListRole();
			
			request.getSession().setAttribute("listaRuoli",listaRuoli);
	        request.getSession().setAttribute("listaUtenti",listaUtenti);
	        request.getSession().setAttribute("listaPermessi",listaPermessi);


			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/gestioneAssociazioni.jsp");
	     	dispatcher.forward(request,response);
		} 
		catch (Exception ex) {
			
		//	ex.printStackTrace();
		     request.setAttribute("error",ECIException.callException(ex));
			 RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/site/error.jsp");
		     dispatcher.forward(request,response);
		}
	
	}

}

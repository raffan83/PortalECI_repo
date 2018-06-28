package it.portalECI.action;

import it.portalECI.DAO.GestioneAccessoDAO;
import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.CompanyDTO;
import it.portalECI.DTO.PermessoDTO;
import it.portalECI.DTO.RuoloDTO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.Exception.ECIException;
import it.portalECI.Util.Utility;
import it.portalECI.bo.GestioneRuoloBO;
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
@WebServlet(name="listaPermessi" , urlPatterns = { "/listaPermessi.do" })

public class ListaPermessi extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListaPermessi() {
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
		
		try {
			String idRuolo = request.getParameter("idRuolo");
			if(idRuolo != null && !idRuolo.equals("")){

				ArrayList<PermessoDTO> listaPermessi =  (ArrayList<PermessoDTO>) GestioneAccessoDAO.getListPermission();
		        RuoloDTO ruolo = GestioneRuoloBO.getRuoloById(idRuolo, session);

		        request.getSession().setAttribute("listaPermessi",listaPermessi);
		        request.getSession().setAttribute("idRuolo",idRuolo);
		        request.getSession().setAttribute("ruolo",ruolo);

				
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/listaPermessiAssociazione.jsp");
		     	dispatcher.forward(request,response);
			}else{
				ArrayList<PermessoDTO> listaPermessi =  (ArrayList<PermessoDTO>) GestioneAccessoDAO.getListPermission();
		        request.getSession().setAttribute("listaPermessi",listaPermessi);

				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/listaPermessi.jsp");
		     	dispatcher.forward(request,response);
			}
			session.close();
			
		} catch (Exception ex) {
			
		//	ex.printStackTrace();
		     request.setAttribute("error",ECIException.callException(ex));
			 RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/site/error.jsp");
		     dispatcher.forward(request,response);
		}
	}
}

package it.portalECI.action;

import it.portalECI.DAO.GestioneAccessoDAO;
import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.CategoriaVerificaDTO;
import it.portalECI.DTO.ComunicazioneUtenteDTO;
import it.portalECI.DTO.TipoComunicazioneUtenteDTO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.Exception.ECIException;
import it.portalECI.Util.Utility;
import it.portalECI.bo.GestioneCategorieVerificaBO;
import it.portalECI.bo.GestioneComunicazioniBO;
import it.portalECI.bo.GestioneUtenteBO;

import java.io.IOException;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;


/**
 * Servlet implementation class listaCampioni
 */
@WebServlet(name="listaCategorieVerifica" , urlPatterns = { "/listaCategorieVerifica.do" })

public class ListaCategorieVerifica extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListaCategorieVerifica() {
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
			
			String action = request.getParameter("action");
			
			if(action == null || action.equals("")) {
				
				ArrayList<CategoriaVerificaDTO> listaCategorieVerifica =  (ArrayList<CategoriaVerificaDTO>) GestioneAccessoDAO.getListCategorieVerifica();
				 
		        request.getSession().setAttribute("listaCategorieVerifica",listaCategorieVerifica);

				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/listaCategorieVerifica.jsp");
		     	dispatcher.forward(request,response);
		     	session.close();
		     	
			}else if(action.equals("associazioni")){
				
				String id_utente = request.getParameter("idUtente");
				
				UtenteDTO utente = GestioneUtenteBO.getUtenteById(id_utente, session);
				
				ArrayList<CategoriaVerificaDTO> listaCategorieVerifica =  (ArrayList<CategoriaVerificaDTO>) GestioneAccessoDAO.getListCategorieVerifica();
				 
		        request.getSession().setAttribute("listaCategorieVerifica",listaCategorieVerifica);
		        request.getSession().setAttribute("utente",utente);
		        request.getSession().setAttribute("idVerificatore",id_utente);

				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/listaCategorieVerificaAssociazioni.jsp");
		     	dispatcher.forward(request,response);
		     	session.close();
			}
			else if(action.equals("comunicazioni")){
				
				String id_utente = request.getParameter("idUtente");
				
				UtenteDTO utente = GestioneUtenteBO.getUtenteById(id_utente, session);
				
				ArrayList<TipoComunicazioneUtenteDTO> listaComunicazioni =  (ArrayList<TipoComunicazioneUtenteDTO>) GestioneComunicazioniBO.getListaTipoComunicazione(session);
				 
		        request.getSession().setAttribute("listaComunicazioni",listaComunicazioni);
		        request.getSession().setAttribute("utente",utente);
		        request.getSession().setAttribute("id_utente",id_utente);

				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/listaComunicazioniAssociazioni.jsp");
		     	dispatcher.forward(request,response);
		     	session.close();
			}
			
 			
		} catch (Exception ex) {
			
		//	ex.printStackTrace();
		     request.setAttribute("error",ECIException.callException(ex));
			 RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/site/error.jsp");
		     dispatcher.forward(request,response);
		}
	}
}
package it.portalECI.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.TransformerUtils;
import org.hibernate.Session;

import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.CategoriaVerificaDTO;
import it.portalECI.DTO.CompanyDTO;
import it.portalECI.DTO.InterventoDTO;
import it.portalECI.DTO.TipoVerificaDTO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.DTO.VerbaleDTO;
import it.portalECI.Exception.ECIException;
import it.portalECI.Util.Utility;
import it.portalECI.bo.GestioneInterventoBO;
import it.portalECI.bo.GestioneVerbaleBO;

/**
 * Servlet implementation class GestioneUtenti
 */
@WebServlet(name = "gestioneListaVerbali", urlPatterns = { "/gestioneListaVerbali.do" })
public class GestioneListaVerbali extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestioneListaVerbali() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(Utility.validateSession(request,response,getServletContext())) return;
		
		response.setContentType("text/html");
		 
		try {
			Session session=SessionFacotryDAO.get().openSession();
			session.beginTransaction();
				
			UtenteDTO user = (UtenteDTO)request.getSession().getAttribute("userObj");
			
			List<VerbaleDTO> listaVerbali =GestioneVerbaleBO.getListaVerbali(session,user) ;
			
			request.getSession().setAttribute("listaVerbali", listaVerbali);

			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/gestioneListaVerbali.jsp");
	     	dispatcher.forward(request,response);
	     	
	     	session.getTransaction().commit();
			session.close();	
		}catch(Exception ex){
   		 	ex.printStackTrace();
   		 	request.setAttribute("error",ECIException.callException(ex));
   		 	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/error.jsp");
   		 	dispatcher.forward(request,response);	
		}  
	}
}

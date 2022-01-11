package it.portalECI.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.CategoriaVerificaDTO;
import it.portalECI.DTO.CommessaDTO;
import it.portalECI.DTO.CompanyDTO;
import it.portalECI.DTO.InterventoDTO;
import it.portalECI.DTO.TipoVerificaDTO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.Exception.ECIException;
import it.portalECI.Util.Utility;
import it.portalECI.bo.GestioneCommesseBO;
import it.portalECI.bo.GestioneInterventoBO;

/**
 * Servlet implementation class GestioneUtenti
 */
@WebServlet(name = "gestioneListaInterventi", urlPatterns = { "/gestioneListaInterventi.do" })
public class GestioneListaInterventi extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestioneListaInterventi() {
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
		Session session=SessionFacotryDAO.get().openSession();
		session.beginTransaction();
		try {
			
			
			String stato = request.getParameter("stato");			
			
			CompanyDTO company =(CompanyDTO)request.getSession().getAttribute("usrCompany");
			
			UtenteDTO user = (UtenteDTO)request.getSession().getAttribute("userObj");
					
			List<InterventoDTO> listaInterventi =GestioneInterventoBO.getListaInterventi(null,session,user, stato) ;
			
			ArrayList<TipoVerificaDTO> tipi_verifica = GestioneInterventoBO.getTipoVerifica(session);
			ArrayList<CategoriaVerificaDTO> categorie_verifica = GestioneInterventoBO.getCategoriaVerifica(session);
			
			Map<String,String> clienti = new HashMap<String,String>();
			clienti=GestioneCommesseBO.getMappaClienti();

			request.getSession().setAttribute("listaInterventi", listaInterventi);
			request.getSession().setAttribute("tipi_verifica", tipi_verifica);
			request.getSession().setAttribute("categorie_verifica", categorie_verifica);
			request.getSession().setAttribute("clienti", clienti);
			request.getSession().setAttribute("stato", stato);
			
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/gestioneListaInterventi.jsp");
	     	dispatcher.forward(request,response);
	     	
	     	session.getTransaction().commit();
			session.close();	
		}catch(Exception ex){
			
			session.getTransaction().rollback();
			session.close();
			
   		 	ex.printStackTrace();
   		 	request.setAttribute("error",ECIException.callException(ex));
   		 	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/error.jsp");
   		 	dispatcher.forward(request,response);	
		}  
	}
}

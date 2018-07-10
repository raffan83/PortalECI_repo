package it.portalECI.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.CommessaDTO;
import it.portalECI.DTO.CompanyDTO;
import it.portalECI.DTO.QuestionarioDTO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.Exception.ECIException;
import it.portalECI.Util.Utility;
import it.portalECI.bo.GestioneCommesseBO;
import it.portalECI.bo.GestioneQuestionarioBO;

/**
 * Servlet implementation class GestioneUtenti
 */
@WebServlet(name = "gestioneListaQuestionari", urlPatterns = { "/gestioneListaQuestionari.do" })
public class GestioneListaQuestionari extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestioneListaQuestionari() {
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
			CompanyDTO company =(CompanyDTO)request.getSession().getAttribute("usrCompany");
			
			UtenteDTO user = (UtenteDTO)request.getSession().getAttribute("userObj");
			
			Session session=SessionFacotryDAO.get().openSession();
			session.beginTransaction();
					
			ArrayList<QuestionarioDTO> listaQuestionari =(ArrayList<QuestionarioDTO>) GestioneQuestionarioBO.getListaQuestionari(session);
			
			request.getSession().setAttribute("listaQuestionari", listaQuestionari);
			
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/questionario/listaQuestionario.jsp");
	     	dispatcher.forward(request,response);
			
		}catch(Exception ex){
   		 	ex.printStackTrace();
   		 	request.setAttribute("error",ECIException.callException(ex));
   		 	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/error.jsp");
   		 	dispatcher.forward(request,response);	
		}  
	}
}

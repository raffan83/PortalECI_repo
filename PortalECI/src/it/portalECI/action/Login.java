package it.portalECI.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.portalECI.DAO.GestioneAccessoDAO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.Exception.ECIException;

/**
 * Servlet implementation class Login
 */
@WebServlet(name="login" , urlPatterns = { "/login.do" })
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Login() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
    	dispatcher.forward(request,response);
     	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//if(Utility.validateSession(request,response,getServletContext()))return;
			
		try{
			response.setContentType("text/html");
		        
			String user=request.getParameter("uid");
			String pwd=request.getParameter("pwd");
		        
			UtenteDTO utente=GestioneAccessoDAO.controllaAccesso(user,pwd);
		        
			if(utente!=null){
				request.setAttribute("forward","site/home.jsp"); 	
				request.getSession().setAttribute("nomeUtente","  "+utente.getNominativo());
				request.getSession().setAttribute("idUtente",utente.getId());
				request.getSession().setAttribute("tipoAccount",utente.getTipoutente());
				
				request.getSession().setAttribute("userObj", utente);
				request.getSession().setAttribute("usrCompany", utente.getCompany());
				
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/dashboard.jsp");
				dispatcher.forward(request,response);
			}else{
				request.setAttribute("errorMessage", "Username o Password non validi");
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
				dispatcher.forward(request,response);
		             
			}
		        
		}catch(Exception ex){
			request.setAttribute("error",ECIException.callException(ex));
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/error.jsp");
			dispatcher.forward(request,response);	
		}  
	}
}

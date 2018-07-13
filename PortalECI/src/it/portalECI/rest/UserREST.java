package it.portalECI.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.google.gson.JsonArray;

import it.portalECI.DAO.GestioneAccessoDAO;
import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.Exception.ECIException;
import it.portalECI.bo.GestioneUtenteBO;

@WebServlet(name="UserREST" , urlPatterns = { "/rest/user" })

public class UserREST extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserREST() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//READ		
		// TODO Auto-generated method stub
		//doPost(request,response);
		
		//if(Utility.validateSession(request,response,getServletContext()))return;
		
    	response.setContentType("application/json");
    	JsonArray responseJson = new JsonArray();
		PrintWriter out = response.getWriter();
		
		try{	
			//Diabeld After inserto into Token Auth
			String user=request.getParameter("username");
			String pwd=request.getParameter("password");
				
			UtenteDTO utente=GestioneAccessoDAO.controllaAccesso(user,pwd);

			//Enabeld After inserto into Token Auth
			//UtenteDTO utente=(UtenteDTO) request.getAttribute("x-user");
			if(utente!=null){		
				if(utente.checkRuolo("AM")) {
					
					Session session=SessionFacotryDAO.get().openSession();
					session.beginTransaction();
					
					List<UtenteDTO> listaOperatori = GestioneUtenteBO.getTecnici("2", session);
					
					for(UtenteDTO operatore : listaOperatori) {												
						responseJson.add(operatore.getUtenteJsonObject(true));
			   		}				
					
					session.getTransaction().commit();
					session.close();
				}else {
					response.setStatus(response.SC_FORBIDDEN);
					return;
				}
			
			}else {
				response.setStatus(response.SC_UNAUTHORIZED);
				return;
			}
				 
		}catch(Exception ex){		
			response.setStatus(response.SC_INTERNAL_SERVER_ERROR);
			responseJson.add(ECIException.callExceptionJsonObject(ex));
			ex.printStackTrace();
		}  
				
		out.println(responseJson);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//CREATE		
		response.setStatus(response.SC_NOT_FOUND);
		return;
		
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//DELETE
		response.setStatus(response.SC_NOT_FOUND);
		return;
	}
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//UPDATE
		response.setStatus(response.SC_NOT_FOUND);
		return;
	}

}

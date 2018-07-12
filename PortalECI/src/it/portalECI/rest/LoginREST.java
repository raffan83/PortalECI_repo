package it.portalECI.rest;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jose4j.jwk.RsaJsonWebKey;

import com.google.gson.JsonObject;

import it.portalECI.DAO.GestioneAccessoDAO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.Exception.ECIException;
import it.portalECI.rest.secure.JWTUtility;

@WebServlet(name="loginREST" , urlPatterns = { "/rest/login" })

public class LoginREST extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginREST() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//CREATE		
		response.setContentType("application/json");
		JsonObject myObj = new JsonObject();
		PrintWriter out = response.getWriter();
		try{			
			String user=request.getParameter("username");
			String pwd=request.getParameter("password");
			
			UtenteDTO utente=GestioneAccessoDAO.controllaAccesso(user,pwd);
			        
			if(utente!=null){
				//generate JWT
			   	myObj.add("userObj", utente.getUtenteJsonObject());
				myObj.addProperty("access_toke", JWTUtility.getJWTAcessToken(utente));
			   	
			}else{
				response.setStatus(response.SC_UNAUTHORIZED);
				myObj.addProperty("error", "Username o Password non validi");
			}
			
		}catch(Exception ex){
			//request.setAttribute("error",ECIException.callException(ex));	
			myObj.add("error", ECIException.callExceptionJsonObject(ex));
		}  
				
		out.println(myObj);
	
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//DELETE
		// TODO Auto-generated method stub
		//doPost(request,response);
		
		//if(Utility.validateSession(request,response,getServletContext()))return;
		
		response.setContentType("application/json");
		JsonObject myObj = new JsonObject();
		PrintWriter out = response.getWriter();
		myObj.addProperty("result", "dentro loginREST doDelete");
		out.println(myObj);
	}
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//UPDATE
		// TODO Auto-generated method stub
		//doPost(request,response);
		//if(Utility.validateSession(request,response,getServletContext()))return;
		
		response.setContentType("application/json");
		JsonObject myObj = new JsonObject();
		PrintWriter out = response.getWriter();
		myObj.addProperty("result", "dentro loginREST doPut");
		out.println(myObj);
	}

}

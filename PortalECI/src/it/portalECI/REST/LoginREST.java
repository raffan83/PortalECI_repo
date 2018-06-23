package it.portalECI.REST;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import it.portalECI.DAO.GestioneAccessoDAO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.Exception.ECIException;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//READ		
		// TODO Auto-generated method stub
		//doPost(request,response);
		
		//if(Utility.validateSession(request,response,getServletContext()))return;
		
		response.setContentType("application/json");
		JsonObject myObj = new JsonObject();
		PrintWriter out = response.getWriter();
		myObj.addProperty("result", "dentro loginREST doGet");
		out.println(myObj);
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
			   	myObj.add("userObj", utente.getUtenteJsonObject());
			}else{		        				
				myObj.addProperty("error", "Username o Password non validi");
			}
			
				
			/*UtenteDTO utente= new UtenteDTO( 12,  "macrosolution",  "macrosolution",  "macrosolution", "macrosolution",  "macrosolution",  "macrosolution",  "macrosolution", "03100",  "macrosolution@it.it",  "macrosolution", null, "AM");
			RuoloDTO ruolo=new RuoloDTO();
			ruolo.setId(1);
			ruolo.setDescrizione("AM");
			ruolo.setSigla("AM");
			Set<RuoloDTO> lista= new HashSet<RuoloDTO>();
		        	
			lista.add(ruolo);
			utente.setListaRuoli(lista);*/		    				
			
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

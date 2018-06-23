package it.portalECI.REST;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import it.portalECI.DTO.AttivitaMilestoneDTO;
import it.portalECI.DTO.CommessaDTO;
import it.portalECI.DTO.CompanyDTO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.Exception.ECIException;
import it.portalECI.Util.Utility;
import it.portalECI.bo.GestioneCommesseBO;

@WebServlet(name="CommessaREST" , urlPatterns = { "/rest/commessa" })

public class CommessaREST extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommessaREST() {
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
		myObj.addProperty("result", "dentro CommessaREST doGet");
		out.println(myObj);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//CREATE		
		//if(Utility.validateSession(request,response,getServletContext()))return;

		//Session session =SessionFacotryDAO.get().openSession();
		//session.beginTransaction();
		
		response.setContentType("application/json");
		JsonObject result = new JsonObject();
		PrintWriter out = response.getWriter();
		
				
		if(Utility.validateSession(request,response,getServletContext()))return;
		 
		try {
			CompanyDTO company =(CompanyDTO)request.getSession().getAttribute("usrCompany");
			
			UtenteDTO user = (UtenteDTO)request.getSession().getAttribute("userObj");
					
			ArrayList<CommessaDTO> listaCommesse =GestioneCommesseBO.getListaCommesse(company,"",user);
			
			//request.getSession().setAttribute("listaCommesse", listaCommesse);
			JsonObject myObj = new JsonObject();
			for(CommessaDTO commessa : listaCommesse) {
			
				myObj.add(commessa.getID_COMMESSA(), commessa.getCommessaJsonObject());
			}
			result.add("listaCommesse", myObj);
			
		}catch(Exception ex){
   		 	ex.printStackTrace();
   		 	
   		 	result.add("error", ECIException.callExceptionJsonObject(ex));
		}  
		
		
		out.println(result);
		
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
		myObj.addProperty("result", "dentro CommessaREST doDelete");
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
		myObj.addProperty("result", "dentro CommessaREST doPut");
		out.println(myObj);
	}

}

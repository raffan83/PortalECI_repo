package it.portalECI.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;

import org.apache.commons.io.IOUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import it.portalECI.DAO.GestioneAccessoDAO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.Exception.ECIException;
import it.portalECI.Util.Utility;
import it.portalECI.bo.GestioneVerbaleBO;

@WebServlet(name="VerbaleREST" , urlPatterns = { "/rest/verbale" })

public class VerbaleREST extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VerbaleREST() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//READ		
		UtenteDTO utente=(UtenteDTO) request.getAttribute("user");
		System.out.println("UTENTE DA RICHIESTA : "+utente);
		response.setContentType("application/json");
		JsonObject myObj = new JsonObject();
		PrintWriter out = response.getWriter();
		myObj.addProperty("result", "dentro VerbaleREST doGet");
		out.println(myObj);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("application/json");
    	JsonArray responseJson = new JsonArray();
		PrintWriter out = response.getWriter();
		
		try{	
			//Enabeld After inserto into Token Auth
			UtenteDTO utente=(UtenteDTO) request.getAttribute("x-user");
			
			String jsonString =   IOUtils.toString(request.getInputStream());
			JsonObject jsonRequest = new JsonParser().parse(jsonString).getAsJsonObject();
			
			GestioneVerbaleBO.saveVerbaleResponses(utente,jsonRequest);
			JsonObject result = new JsonObject();
			result.addProperty("result", "success");
			responseJson.add(result);
			
		}catch (Exception e) {
			responseJson=new JsonArray();
			if(e instanceof ValidationException) {
				response.setStatus(response.SC_BAD_REQUEST);
				JsonObject result = new JsonObject();
				result.addProperty("result", "false");
				result.addProperty("error", e.getMessage());
				responseJson.add(result);
				
			}else {
				response.setStatus(response.SC_INTERNAL_SERVER_ERROR);
				responseJson.add(ECIException.callExceptionJsonObject(e));
				e.printStackTrace();	
			}
			
			
		}  	
		out.println(responseJson);
	
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
		myObj.addProperty("result", "dentro VerbaleREST doDelete");
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
		myObj.addProperty("result", "dentro VerbaleREST doPut");
		out.println(myObj);
	}

}

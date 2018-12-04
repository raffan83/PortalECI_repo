package it.portalECI.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;

import org.apache.commons.io.IOUtils;
import org.hibernate.Session;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import it.portalECI.DAO.GestioneAccessoDAO;
import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.InterventoDTO;
import it.portalECI.DTO.StatoInterventoDTO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.Exception.ECIException;
import it.portalECI.bo.GestioneInterventoBO;
import it.portalECI.bo.GestioneUtenteBO;
import it.portalECI.bo.GestioneVerbaleBO;

@WebServlet(name="InterventoREST" , urlPatterns = { "/rest/intervento" })

public class InterventoREST extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InterventoREST() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//READ		
    	response.setContentType("application/json");
    	JsonArray responseJson = new JsonArray();
		PrintWriter out = response.getWriter();
		
		try{	
			//Diabeld After inserto into Token Auth
//			String user=request.getParameter("username");
//			String pwd=request.getParameter("password");
//			UtenteDTO utente=GestioneAccessoDAO.controllaAccesso(user,pwd);
			
			//Enabeld After inserto into Token Auth
			UtenteDTO utente=(UtenteDTO) request.getAttribute("x-user");
			
			String idIntervento=request.getParameter("id");  
			
			if(utente!=null){					        	
			  
				Session session=SessionFacotryDAO.get().openSession();
				session.beginTransaction();
				String action=request.getParameter("action")!=null?request.getParameter("action"):"list";
					
				if(idIntervento==null) {	
					//lista interventi
					ArrayList<InterventoDTO> listaInterventi = null;
					if(action.equals("download") )
						listaInterventi = GestioneInterventoBO.getListaInterventiDownload( session, utente.getId());
					else if(action.equals("list"))
						listaInterventi = GestioneInterventoBO.getListaInterventiTecnico( session, utente.getId());
					else {
						response.setStatus(response.SC_BAD_REQUEST);
						return;
					}
					for(InterventoDTO intervento : listaInterventi) {
						try {
							if(action.equals("download") ) {
								if(GestioneInterventoBO.scaricaIntervento(intervento,session)) {
									responseJson.add( intervento.getInterventoJsonObject());
								}
							}else if(action.equals("list")){
								listaInterventi = GestioneInterventoBO.getListaInterventiTecnico( session, utente.getId());
								responseJson.add( intervento.getInterventoJsonObject());
							}
						}catch(Exception ex){		
							ex.printStackTrace();
						}  
			   		}
					
				}else {	
					//dettaglio intervento
					InterventoDTO intervento = GestioneInterventoBO.getInterventoTecnico( session, utente.getId(), Integer.parseInt(idIntervento));
					if(intervento!=null) {
							responseJson.add(intervento.getInterventoJsonObject());		
			   		}
				}
				
				session.getTransaction().commit();
				session.close();	
			   		
				//fine intervento
			}else{		        				
				response.setStatus(response.SC_UNAUTHORIZED);
				return;
			}		
			
		}catch(Exception ex){
			responseJson=new JsonArray();
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
		response.setContentType("application/json");
    	JsonArray responseJson = new JsonArray();
		PrintWriter out = response.getWriter();
		Session session = SessionFacotryDAO.get().openSession();
		session.beginTransaction();
		try{	
			//Enabeld After inserto into Token Auth
			UtenteDTO utente=(UtenteDTO) request.getAttribute("x-user");
			
			String jsonString =   IOUtils.toString(request.getInputStream(), "UTF-8");
			JsonArray jsonRequest = new JsonParser().parse(jsonString).getAsJsonArray();

			//lista di INterventi
			Iterator<JsonElement> iterator = jsonRequest.iterator();
			while (iterator.hasNext()) {
				JsonObject intevento = (JsonObject) iterator.next();
				GestioneInterventoBO.saveInterventoResponses(utente,intevento,session);	
			}
			
			JsonObject result = new JsonObject();
			result.addProperty("result", "success");
			responseJson.add(result);
			session.getTransaction().commit();
		}catch (Exception e) {
			session.getTransaction().rollback();
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
			
			
		}  finally {
			session.close();
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
		myObj.addProperty("result", "dentro InterventoREST doDelete");
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
		myObj.addProperty("result", "dentro InterventoREST doPut");
		out.println(myObj);
	}

}

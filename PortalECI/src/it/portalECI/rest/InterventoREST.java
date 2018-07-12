package it.portalECI.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import it.portalECI.DAO.GestioneAccessoDAO;
import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.InterventoDTO;
import it.portalECI.DTO.StatoInterventoDTO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.Exception.ECIException;
import it.portalECI.bo.GestioneInterventoBO;

@WebServlet(name="InterventoREST" , urlPatterns = { "/rest/interventi" })

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
			String user=request.getParameter("username");
			String pwd=request.getParameter("password");
			
			
			UtenteDTO utente=GestioneAccessoDAO.controllaAccesso(user,pwd);
			
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
						if(action.equals("download") ) {
							if(GestioneInterventoBO.scaricaIntervento(intervento,session)) {
								responseJson.add( intervento.getInterventoJsonObject());
							}
						}else if(action.equals("list")){
							listaInterventi = GestioneInterventoBO.getListaInterventiTecnico( session, utente.getId());
							responseJson.add( intervento.getInterventoJsonObject());
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
		
		/*response.setContentType("application/json");
		JsonObject myObj = new JsonObject();
		PrintWriter out = response.getWriter();
		
		try{			
			String user=request.getParameter("username");
			String pwd=request.getParameter("password");
			
			UtenteDTO utente=GestioneAccessoDAO.controllaAccesso(user,pwd);
			
			String idIntervento=request.getParameter("id");  
			
			if(utente!=null){					        	
			  
				Session session=SessionFacotryDAO.get().openSession();
				session.beginTransaction();
				String action=request.getParameter("action");
					
				if(idIntervento==null) {
					ArrayList<InterventoDTO> listaInterventi = GestioneInterventoBO.getListaInterventiTecnico( session, utente.getId());

					JsonArray singleObj = new JsonArray();
					for(InterventoDTO intervento : listaInterventi) {						
						
						singleObj.add( intervento.getInterventoJsonObject());
						
						try {
							intervento.cambioStatoIntervento(StatoInterventoDTO.SCARICATO);
							GestioneInterventoBO.update(intervento, session);
						}catch(IllegalStateException e) {
							myObj.addProperty("success", false);
							myObj.addProperty("messaggio", e.getMessage());
							
							out.print(myObj);
							return;
						}
			   		}
					myObj.add("listaInterventi", singleObj);
				}else {					
					InterventoDTO intervento = GestioneInterventoBO.getInterventoTecnico( session, utente.getId(), Integer.parseInt(idIntervento));
			   		
					if(intervento==null) {
						myObj.addProperty("Errore", "Intervento inesistente o non associata all'utente");
			   		}else {
			   			myObj.add("Intervento", intervento.getInterventoJsonObject());
			   			
			   			try {
							intervento.cambioStatoIntervento(StatoInterventoDTO.SCARICATO);
							GestioneInterventoBO.update(intervento, session);
						}catch(IllegalStateException e) {
							myObj.addProperty("success", false);
							myObj.addProperty("messaggio", e.getMessage());
							
							out.print(myObj);
							return;
						}
			   		}
					
				}
			   		
				session.getTransaction().commit();
				session.close();	
			   		
//fine intervento
			}else{		        				
				myObj.addProperty("error", "Username o Password non validi");
			}		
			
		}catch(Exception ex){			
			myObj.add("error", ECIException.callExceptionJsonObject(ex));
		}  
				
		out.println(myObj);*/
		

		response.setContentType("application/json");
		JsonObject myObj = new JsonObject();
		PrintWriter out = response.getWriter();
		myObj.addProperty("result", "dentro InterventoREST doPost");
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

package it.portalECI.REST;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import it.portalECI.DAO.GestioneAccessoDAO;
import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.CategoriaVerificaDTO;
import it.portalECI.DTO.CommessaDTO;
import it.portalECI.DTO.CompanyDTO;
import it.portalECI.DTO.InterventoDTO;
import it.portalECI.DTO.StatoInterventoDTO;
import it.portalECI.DTO.TipoVerificaDTO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.Exception.ECIException;
import it.portalECI.Util.Utility;
import it.portalECI.bo.GestioneCommesseBO;
import it.portalECI.bo.GestioneInterventoBO;
import it.portalECI.bo.GestioneUtenteBO;

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
		// TODO Auto-generated method stub
		//doPost(request,response);
		
		//if(Utility.validateSession(request,response,getServletContext()))return;
		
		response.setContentType("application/json");
		JsonObject myObj = new JsonObject();
		PrintWriter out = response.getWriter();
		myObj.addProperty("result", "dentro InterventoREST doGet");
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
			
			String idIntervista=request.getParameter("id");  
			
			if(utente!=null){					        	
			  
				Session session=SessionFacotryDAO.get().openSession();
				session.beginTransaction();
				String action=request.getParameter("action");
					
				if(idIntervista==null) {
					ArrayList<InterventoDTO> listaInterventi = GestioneInterventoBO.getListaInterventiTecnico( session, utente.getId());

					JsonObject singleObj = new JsonObject();
					for(InterventoDTO intervento : listaInterventi) {
						
						singleObj.add(Integer.toString(intervento.getId()), intervento.getInterventoJsonObject());
			   		}
					myObj.add("listaInterventi", singleObj);
				}else {					
					InterventoDTO intervento = GestioneInterventoBO.getInterventoTecnico( session, utente.getId(), Integer.parseInt(idIntervista));
			   		
					if(intervento==null) {
						myObj.addProperty("Errore", "Intervista inesistente o non associata all'utente");
			   		}else {
			   			myObj.add("Intervento", intervento.getInterventoJsonObject());
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

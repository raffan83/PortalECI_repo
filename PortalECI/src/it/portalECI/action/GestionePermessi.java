package it.portalECI.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.google.gson.JsonObject;

import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.CompanyDTO;
import it.portalECI.DTO.PermessoDTO;
import it.portalECI.Exception.ECIException;
import it.portalECI.Util.Utility;
import it.portalECI.bo.GestionePermessiBO;

/**
 * Servlet implementation class GestioneUtenti
 */
@WebServlet(name = "gestionePermessi", urlPatterns = { "/gestionePermessi.do" })
public class GestionePermessi extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestionePermessi() {
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
		// TODO Auto-generated method stub
		if(Utility.validateSession(request,response,getServletContext()))return;
		Session session = SessionFacotryDAO.get().openSession();
		session.beginTransaction();
		
		PrintWriter out = response.getWriter();
		JsonObject myObj = new JsonObject();
   
        response.setContentType("application/json");
        
        try{
       	 	 String action =  request.getParameter("action");
	       	 if(action !=null ){
	       		
	    	 	if(action.equals("nuovo")){
	    	 		String descrizione = request.getParameter("descrizione");
	    	 		String chiave_permesso = request.getParameter("chiave_permesso");
	    	 		
	    	 		if(GestionePermessiBO.checkChiavePermesso(chiave_permesso, 0, session)) {
	    	 			PermessoDTO permesso = new PermessoDTO();
	    	 			permesso.setDescrizione(descrizione);
	    	 			permesso.setChiave_permesso(chiave_permesso);
	    	    	 			
	    	 			Boolean success = GestionePermessiBO.savePermesso(permesso, action, session);
	   	 			
	    	 			if(success) {
	    	 				myObj.addProperty("success", true);
	    	 				myObj.addProperty("messaggio", "Permesso salvato con successo");
	    	 			}else {
	    	 				myObj.addProperty("success", false);
	    	 				myObj.addProperty("messaggio","Errore Salvataggio");
				
	    	 				session.getTransaction().rollback();
	    	 				session.close();
	    	 			}
	    	 		}else {
	    	 			myObj.addProperty("success", false);
    	 				myObj.addProperty("messaggio","Esiste gi\u00E0 un permesso con questa chiave!");
	    	 		}
	    	 	}else if(action.equals("modifica")){
	    	 			
	    	 		String id = request.getParameter("id");

	    	 		String descrizione = request.getParameter("descrizione");
	    	 		String chiave_permesso = request.getParameter("chiave_permesso");
	    	 		
	    	 		if(GestionePermessiBO.checkChiavePermesso(chiave_permesso, Integer.parseInt(id), session)) {
	    	 			PermessoDTO permesso = GestionePermessiBO.getPermessoById(id, session);
	    	 			
	    	 			if(descrizione != null && !descrizione.equals("")){
	    	 				permesso.setDescrizione(descrizione);
	    	 			}
	    	 			if(chiave_permesso != null && !chiave_permesso.equals("")){
	    	 				permesso.setChiave_permesso(chiave_permesso);
	    	 			}
	    	 			
	    	 			Boolean success = GestionePermessiBO.savePermesso(permesso, action, session);
	    	 			if(success) {	
	    	 				myObj.addProperty("success", true);
	    	 				myObj.addProperty("messaggio", "Permesso modificato con successo");
	    	 			}else {
	    	 				myObj.addProperty("success", false);
	    	 				myObj.addProperty("messaggio","Errore Salvataggio");
					
	    	 				session.getTransaction().rollback();
	    	 				session.close();
	    	 			}
	    	 		}else {
	    	 			myObj.addProperty("success", false);
    	 				myObj.addProperty("messaggio","Esiste gi\u00E0 un permesso con questa chiave!");
	    	 		}
	    	 	}else if(action.equals("elimina")){
	    	 			
	    	 		String id = request.getParameter("id");

	    	 				
	    	 		PermessoDTO permesso = GestionePermessiBO.getPermessoById(id, session);
	    	 			
	    	 		/*
	    	 		 * TO DO Elimina Company
	    	 		 */
	    	 			
	    	 			
	    	 		myObj.addProperty("success", true);
		 		 	myObj.addProperty("messaggio", "Permesso eliminato con successo");  
	    		}
	    	 		
	    	 }else{
	    	 		
	    		myObj.addProperty("success", false);
	    	 	myObj.addProperty("messaggio", "Nessuna action riconosciuta");  
	    	 }	
	       	 
	       	 session.getTransaction().commit();
	       	 session.close();	
			
	       	 out.println(myObj.toString());
	       	 
        }catch(Exception ex){
        	
        	ex.printStackTrace();
        	session.getTransaction().rollback();
        	session.close();
        	myObj.addProperty("success", false);
        	myObj.addProperty("messaggio", ECIException.callException(ex).toString());
        	out.println(myObj.toString());
        } 
	}
}

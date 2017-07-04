package it.portalECI.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.google.gson.JsonObject;

import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.CompanyDTO;
import it.portalECI.DTO.RuoloDTO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.Exception.ECIException;
import it.portalECI.Util.Utility;
import it.portalECI.bo.GestioneCompanyBO;
import it.portalECI.bo.GestioneUtenteBO;

/**
 * Servlet implementation class GestioneUtenti
 */
@WebServlet(name = "gestioneCompany", urlPatterns = { "/gestioneCompany.do" })
public class GestioneCompany extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestioneCompany() {
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
	       	 if(action !=null )
	    	 	{
					
	    	 		if(action.equals("nuovo"))
	    	 		{
	    	 			String denominazione = request.getParameter("denominazione");
	    	 			String pIva = request.getParameter("piva");
	    	 			String indirizzo = request.getParameter("indirizzo");
	    	 			String comune = request.getParameter("comune");
	    	 			String cap = request.getParameter("cap");
	    	 			String EMail = request.getParameter("email");
	    	 			String telefono = request.getParameter("telefono");
	    	 			String codAffiliato = request.getParameter("codiceAffiliato");
	    	 			
	    	 
	    	 			
	    	 			CompanyDTO company = new CompanyDTO();
	    	 			company.setDenominazione(denominazione);
	    	 			company.setpIva(pIva);
	    	 			company.setIndirizzo(indirizzo);
	    	 			company.setComune(comune);
	    	 			company.setCap(cap);
	    	 			company.setMail(EMail);
	    	 			company.setTelefono(telefono);
	    	 			company.setCodAffiliato(codAffiliato);
	    	 			

	    	 			int success = GestioneCompanyBO.saveCompany(company,action,session);

	    	 			if(success==0)
	    				{
	    					myObj.addProperty("success", true);
	    					myObj.addProperty("messaggio","Company salvata con successo");
	    					session.getTransaction().commit();
	    					session.close();
	    				
	    				}
	    				if(success==1)
	    				{
	    					
	    					myObj.addProperty("success", false);
	    					myObj.addProperty("messaggio","Errore Salvataggio");
	    					
	    					session.getTransaction().rollback();
	    			 		session.close();
	    			 		
	    				} 
		 			 	
	    	 		}else if(action.equals("modifica")){
	    	 			
	    	 			String id = request.getParameter("id");

	    	 			String denominazione = request.getParameter("denominazione");
	    	 			String pIva = request.getParameter("piva");
	    	 			String indirizzo = request.getParameter("indirizzo");
	    	 			String comune = request.getParameter("comune");
	    	 			String cap = request.getParameter("cap");
	    	 			String EMail = request.getParameter("email");
	    	 			String telefono = request.getParameter("telefono");
	    	 			String codAffiliato = request.getParameter("codiceAffiliato");
	    	 			
	    	 
	    	 			
	    	 			
	    	 			CompanyDTO company = GestioneCompanyBO.getCompanyById(id, session);
	    	 			
	    	 			if(denominazione != null && !denominazione.equals("")){
		    	 			company.setDenominazione(denominazione);
	    	 			}
	    	 			if(pIva != null && !pIva.equals("")){
		    	 			company.setpIva(pIva);
	    	 			}
	    	 			if(indirizzo != null && !indirizzo.equals("")){
		    	 			company.setIndirizzo(indirizzo);
	    	 			}
	    	 			if(comune != null && !comune.equals("")){
		    	 			company.setComune(comune);
	    	 			}
	    	 			if(cap != null && !cap.equals("")){
		    	 			company.setCap(cap);
	    	 			}
	    	 			if(EMail != null && !EMail.equals("")){
		    	 			company.setMail(EMail);
	    	 			}
	    	 			if(telefono != null && !telefono.equals("")){
		    	 			company.setTelefono(telefono);
	    	 			}
	    	 			if(codAffiliato != null && !codAffiliato.equals("")){
		    	 			company.setCodAffiliato(codAffiliato);
	    	 			}
	    	 			

	    	 			int success = GestioneCompanyBO.saveCompany(company,action,session);

	    	 			if(success==0)
	    				{
	    					myObj.addProperty("success", true);
	    					myObj.addProperty("messaggio","Company modificata con successo");
	    					session.getTransaction().commit();
	    					session.close();
	    				
	    				}
	    				if(success==1)
	    				{
	    					
	    					myObj.addProperty("success", false);
	    					myObj.addProperty("messaggio","Errore Salvataggio");
	    					
	    					session.getTransaction().rollback();
	    			 		session.close();
	    			 		
	    				} 
	    	 		}else if(action.equals("elimina")){
	    	 			
	    	 			String id = request.getParameter("id");

	    	 				
	    	 			
	    	 			CompanyDTO company = GestioneCompanyBO.getCompanyById(id, session);
	    	 			

	    	 			/*
	    	 			 * TO DO Elimina Company
	    	 			 */
	    	 			
	    	 			
	    	 			myObj.addProperty("success", true);
		 			 	myObj.addProperty("messaggio", "Company eliminato con successo");  
	    	 		}
	    	 		
	    	 	}else{
	    	 		
	    	 		myObj.addProperty("success", false);
	    	 		myObj.addProperty("messaggio", "Nessuna action riconosciuta");  
	    	 	}	
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

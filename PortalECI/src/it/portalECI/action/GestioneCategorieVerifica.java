package it.portalECI.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.google.gson.JsonObject;

import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.CategoriaVerificaDTO;
import it.portalECI.DTO.CompanyDTO;
import it.portalECI.Exception.ECIException;
import it.portalECI.Util.Utility;
import it.portalECI.bo.GestioneCategorieVerificaBO;
import it.portalECI.bo.GestioneCompanyBO;

/**
 * Servlet implementation class GestioneUtenti
 */
@WebServlet(name = "gestioneCategorieVerifica", urlPatterns = { "/gestioneCategorieVerifica.do" })
public class GestioneCategorieVerifica extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestioneCategorieVerifica() {
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
	    	 		String codice = request.getParameter("codice");    	 			    	 
	    	 			
    	 			CategoriaVerificaDTO categoria = new CategoriaVerificaDTO();
    	 			categoria.setDescrizione(descrizione);
    	 			categoria.setCodice(codice);	 			

    	 			int success = GestioneCategorieVerificaBO.saveCategoriaVerifica(categoria,action,session);

    	 			if(success==0){
    					myObj.addProperty("success", true);
    					myObj.addProperty("messaggio","Categoria verifica salvata con successo");
    					session.getTransaction().commit();
    					session.close();	    				
    				}
	    	 			
    				if(success==1){
	    					
    					myObj.addProperty("success", false);
    					myObj.addProperty("messaggio","Errore Salvataggio");
    					
    					session.getTransaction().rollback();
    			 		session.close();	    			 		
    				} 
		 			 	
    	 		}else if(action.equals("modifica")){
	    	 			
    	 			String id = request.getParameter("id");

    	 			String descrizione = request.getParameter("descrizione");
    	 			String codice = request.getParameter("codice");    	 				    	 
	    	 			
    	 			CategoriaVerificaDTO categoria = GestioneCategorieVerificaBO.getCategoriaVerificaById(id, session);
	    	 			
    	 			if(descrizione != null && !descrizione.equals("")){
	    	 			categoria.setDescrizione(descrizione);
    	 			}
    	 			if(codice != null && !codice.equals("")){
	    	 			categoria.setCodice(codice);
    	 			}	    	 			

    	 			int success = GestioneCategorieVerificaBO.saveCategoriaVerifica(categoria,action,session);

    	 			if(success==0){
    					myObj.addProperty("success", true);
    					myObj.addProperty("messaggio","Categoria verifica modificata con successo");
    					session.getTransaction().commit();
    					session.close();	    				
    				}
	    	 			
    				if(success==1){
	    					
    					myObj.addProperty("success", false);
    					myObj.addProperty("messaggio","Errore Salvataggio");
	    					
    					session.getTransaction().rollback();
    			 		session.close();
	    			 		
    				} 
    	 		}else if(action.equals("elimina")){
	    	 			
    	 			String id = request.getParameter("id");
	    	 			
    	 			CategoriaVerificaDTO categoria = GestioneCategorieVerificaBO.getCategoriaVerificaById(id, session);	
    	 			
    	 			int success = GestioneCategorieVerificaBO.deleteCategoriaVerifica(categoria, session);
    	 			if(success==0){
    					myObj.addProperty("success", true);
    					myObj.addProperty("messaggio","Categoria verifica eliminata con successo");
    					session.getTransaction().commit();
    					session.close();	    				
    				}else if(success==2) {
    	 				myObj.addProperty("success", false);
    					myObj.addProperty("messaggio","Prima di procedere elimina tutti i Tipi Verifica associcati a questa Categoria!");
    	 			}else {
	    					
    					myObj.addProperty("success", false);
    					myObj.addProperty("messaggio","Errore Eliminazione");
    					
    					session.getTransaction().rollback();
    			 		session.close();	    			 		
    				} 
 
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
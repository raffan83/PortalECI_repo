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
import it.portalECI.DTO.QuestionarioDTO;
import it.portalECI.DTO.TipoVerificaDTO;
import it.portalECI.Exception.ECIException;
import it.portalECI.Util.Utility;
import it.portalECI.bo.GestioneCategorieVerificaBO;
import it.portalECI.bo.GestioneQuestionarioBO;
import it.portalECI.bo.GestioneTipiVerificaBO;

/**
 * Servlet implementation class GestioneUtenti
 */
@WebServlet(name = "gestioneTipiVerifica", urlPatterns = { "/gestioneTipiVerifica.do" })
public class GestioneTipiVerifica extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestioneTipiVerifica() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		
		String action =  request.getParameter("action");
		if(action.equals("checkSchedaTecnica")) {
			String idVerifica =  request.getParameter("idVerifica");
			Session session = SessionFacotryDAO.get().openSession();
			session.beginTransaction();
			
			JsonObject myObj = new JsonObject();
			PrintWriter  out = response.getWriter();
			
			TipoVerificaDTO tipo;
			try {
				tipo = GestioneTipiVerificaBO.getTipoVerificaById(idVerifica, session);
				QuestionarioDTO quest= GestioneQuestionarioBO.getLastQuestionarioByVerifica(tipo, session);
				
				if(quest!= null && quest.getTemplateSchedaTecnica()!=null) {
					myObj.addProperty("schedaTecnicaPresente", true);
				}else {
					myObj.addProperty("schedaTecnicaPresente", false);
				}
				
				myObj.addProperty("success", true);
				out.print(myObj);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			session.close();
		}else {
			doPost(request, response);
		}
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
	       			
	       			String sigla = request.getParameter("sigla");
	       			String codiceCategoria = request.getParameter("categoria");
	    	 		String descrizione = request.getParameter("descrizione");
	    	 		String codice = request.getParameter("codice");    	 			    	 
	    	 			
    	 			TipoVerificaDTO tipo = new TipoVerificaDTO();
    	 			
    	 			CategoriaVerificaDTO categoria = new CategoriaVerificaDTO();
    	 			int codCategoria = Integer.parseInt(codiceCategoria);
    	 			categoria.setId(codCategoria);
    	 			
    	 			tipo.setSigla(sigla);
    	 			tipo.setCategoria(categoria);
    	 			tipo.setDescrizione(descrizione);
    	 			tipo.setCodice(codice);	 			

    	 			int success = GestioneTipiVerificaBO.saveTipoVerifica(tipo,action,session);

    	 			if(success==0){
    					myObj.addProperty("success", true);
    					myObj.addProperty("messaggio","Tipo verifica salvato con successo");
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
    	 			String sigla = request.getParameter("sigla");
    	 			String codiceCategoria = request.getParameter("categoria");
    	 			String descrizione = request.getParameter("descrizione");
    	 			String codice = request.getParameter("codice");    	 				    	 
	    	 			
    	 			TipoVerificaDTO tipo = GestioneTipiVerificaBO.getTipoVerificaById(id, session);
    	 			
    	 			if(sigla != null && !sigla.equals("")){
	    	 			tipo.setSigla(sigla);
    	 			}
	    	 			
    	 			if(codiceCategoria != null && !codiceCategoria.equals("")){
    	 				CategoriaVerificaDTO categoria = new CategoriaVerificaDTO();
        	 			int codCategoria = Integer.parseInt(codiceCategoria);
        	 			categoria.setId(codCategoria);
        	 			
        	 			tipo.setCategoria(categoria);
    	 			}
    	 			
    	 			if(descrizione != null && !descrizione.equals("")){
	    	 			tipo.setDescrizione(descrizione);
    	 			}
    	 			if(codice != null && !codice.equals("")){
	    	 			tipo.setCodice(codice);
    	 			}    	 			

    	 			int success = GestioneTipiVerificaBO.saveTipoVerifica(tipo,action,session);

    	 			if(success==0){
    					myObj.addProperty("success", true);
    					myObj.addProperty("messaggio","Tipo verifica modificato con successo");
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
	    	 			
    	 			TipoVerificaDTO tipo = GestioneTipiVerificaBO.getTipoVerificaById(id, session);	
    	 			
    	 			int success = GestioneTipiVerificaBO.deleteTipoVerifica(tipo, session);

    	 			if(success==0){
    					myObj.addProperty("success", true);
    					myObj.addProperty("messaggio","Tipo verifica eliminato con successo");
    					session.getTransaction().commit();
    					session.close();	    				
    				}else if(success==2) {
    	 				myObj.addProperty("success", false);
    					myObj.addProperty("messaggio","Impossibile eliminare una Tipologia di Verifica associata a Verbali o Questionari esistenti!");
    	 			}else{
	    					
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
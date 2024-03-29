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

import it.portalECI.DAO.DirectMySqlDAO;
import it.portalECI.DAO.GestioneAccessoDAO;
import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.CompanyDTO;
import it.portalECI.DTO.RuoloDTO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.Exception.ECIException;
import it.portalECI.Util.Utility;
import it.portalECI.bo.GestioneCompanyBO;
import it.portalECI.bo.GestioneRuoloBO;
import it.portalECI.bo.GestioneUtenteBO;

/**
 * Servlet implementation class GestioneUtenti
 */

@WebServlet(name = "gestioneUtenti", urlPatterns = { "/gestioneUtenti.do" })

public class GestioneUtenti extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestioneUtenti() {
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
	  
	    	if(action.equals("nuovo")){
	    		String nome = request.getParameter("nome");
	    		String codice = request.getParameter("codice");
	    		String qualifica = request.getParameter("qualifica");
	    	 	String cognome = request.getParameter("cognome");
	    	 	String user = request.getParameter("user");
	    	 	String passw = request.getParameter("passw");
	    	 	String indirizzo = request.getParameter("indirizzo");
	    	 	String comune = request.getParameter("comune");
	    	 	String cap = request.getParameter("cap");
	    	 	String email = request.getParameter("email");
	    	 	String telefono = request.getParameter("telefono");
	    	 	String companyId = request.getParameter("company");
	    	 	String cf = request.getParameter("cf");
	    	 	String id_cliente = request.getParameter("id_cliente");
	    	 	String id_sede = request.getParameter("id_sede");
	    	 	String tipo_utente = request.getParameter("tipo_utente");
	    	 	String check= request.getParameter("check");

	    	 	CompanyDTO company = null;
	    	 	if(companyId!=null) {
	    	 		company = GestioneCompanyBO.getCompanyById(companyId, session);
	    	 	}else {
	    	 		company = GestioneCompanyBO.getCompanyById("1703", session);
	    	 	}
	    	 	
	    	 	
	    	 			    	 			
	    	 	UtenteDTO utente = new UtenteDTO();
	    	 	utente.setCodice(codice);
	    	 	utente.setQualifica(qualifica);
	    	 	utente.setNome(nome);
	    	 	utente.setCognome(cognome.replace("\u2032", "'"));
	    	 	utente.setUser(user);
	    	 	utente.setPassw(DirectMySqlDAO.getPassword(passw));
	    	 	utente.setIndirizzo(indirizzo.replace("\u2032", "'"));
	    	 	utente.setComune(comune.replace("\u2032", "'"));
	    	 	utente.setCap(cap);
	    	 	utente.setEMail(email);
	    	 	utente.setTelefono(telefono);
	    	 	utente.setCompany(company);
	    	 	utente.setNominativo(nome+" "+cognome.replace("\u2032", "'"));
	    	 	utente.setTipoutente(tipo_utente);
	    	 	utente.setCf(cf);
	    	 	if(id_cliente!=null && !id_cliente.equals("")) {
	    	 		utente.setIdCliente(Integer.parseInt(id_cliente));
	    	 	}
	    	 	if(id_sede!=null && !id_sede.equals("")) {
	    	 		utente.setIdSede(Integer.parseInt(id_sede.split("_")[0]));
	    	 	}
	    	 	
	    	 	
	    	 	
	    	 	//GestioneUtenteBO.save(utente,session);

	    	 	int success = GestioneUtenteBO.saveUtente(utente, action, session);
	    	 	if(success==0){
	    	 		
	    	 		if(check!=null && check.split(",").length==1) {
		    	 		if(check.split(",")[0].split("_")[0].equals("checkvie")) {
		    	 			RuoloDTO ruolo = GestioneRuoloBO.getRuoloById("9", session);
		    	 			
		    	 			utente.getListaRuoli().add(ruolo);
		    	 		}else if(check.split(",")[0].split("_")[0].equals("checkval")) {
		    	 			RuoloDTO ruolo = GestioneRuoloBO.getRuoloById("10", session);
		    	 			
		    	 			utente.getListaRuoli().add(ruolo);
		    	 		}
		    	 		
		    	 		
		    	 	}else if(check!=null && check.split(",").length==2) {
		    	 		RuoloDTO ruolo_vie = GestioneRuoloBO.getRuoloById("9", session);    	 			
	    	 			
		    	 		RuoloDTO ruolo_val = GestioneRuoloBO.getRuoloById("10", session);
	    	 			
	    	 			utente.getListaRuoli().add(ruolo_vie);
	    	 			utente.getListaRuoli().add(ruolo_val);
		    	 	}
		    	 	
	    	 		
	    	 		myObj.addProperty("success", true);
	    	 		myObj.addProperty("messaggio","Utente salvato con successo");
	    	 		session.getTransaction().commit();
	    	 		session.close();
	    				
	    	 	}
	    	 	
	    	 	
	    	 	
	    	 	if(success==1){
	    	 		
	    	 		myObj.addProperty("success", false);
	    	 		myObj.addProperty("messaggio","Errore Salvataggio");
	    					
	    	 		session.getTransaction().rollback();
	    	 		session.close();
	    			 		
	    	 	} 
	    	 				 			 	
	    	}
	    	 	
	    	if(action.equals("modifica")){
	    	 			
	    		String id = request.getParameter("id");

	    		String nome = request.getParameter("nome");
	    		String codice = request.getParameter("codice");
	    		String qualifica = request.getParameter("qualifica");
	    		String cognome = request.getParameter("cognome");
	    		String user = request.getParameter("user");
	    		String passw = request.getParameter("passw");
	    		String indirizzo = request.getParameter("indirizzo");
	    		String comune = request.getParameter("comune");
	    		String cap = request.getParameter("cap");
	    		String EMail = request.getParameter("email");
	    		String telefono = request.getParameter("telefono");
	    		String companyId = request.getParameter("company");
	    		String cf = request.getParameter("cf");
	    	 				
	    		String id_cliente = request.getParameter("id_cliente");
	    	 	String id_sede = request.getParameter("id_sede");
	    	 	String tipo_utente = request.getParameter("tipo_utente");
	    	 	String check= request.getParameter("check");
	    	 	
	    	 	
	    		UtenteDTO utente = GestioneUtenteBO.getUtenteById(id, session);
	    	 			
	    	 			
	    		if(nome != null && !nome.equals("")){
	    			utente.setNome(nome);
	    		}
	    		
	    		utente.setQualifica(qualifica);
	    		
	    		if(cognome != null && !cognome.equals("")){
	    			utente.setCognome(cognome.replace("\u2032", "'"));
	    		}
	    		if(codice != null && !codice.equals("")){
	    			utente.setCodice(codice);
	    		}

	    		if(user != null && !user.equals("")){
	    			utente.setUser(user);
	    		}
	    		if(tipo_utente != null && !tipo_utente.equals("")){
	    			utente.setTipoutente(tipo_utente);
	    		}
	    		
	    		if(passw != null && !passw.equals("")){
	    			utente.setPassw(DirectMySqlDAO.getPassword(passw));
	    		}
	    		if(indirizzo != null && !indirizzo.equals("")){
	    			utente.setIndirizzo(indirizzo.replace("\u2032", "'"));
	    		}
	    		if(comune != null && !comune.equals("")){
	    			utente.setComune(comune.replace("\u2032", "'"));
	    		}
	    		if(cap != null && !cap.equals("")){
	    			utente.setCap(cap);
	    		}
	    		if(EMail != null && !EMail.equals("")){
	    			utente.setEMail(EMail);
	    		}
	    		if(telefono != null && !telefono.equals("")){
	    			utente.setTelefono(telefono);
	    		}
	    			
	    		utente.setNominativo(utente.getNome()+" "+utente.getCognome());
	    	 	
	    		utente.setCf(cf);
	    		if(companyId != null && !companyId.equals("")){
	    			CompanyDTO company = GestioneCompanyBO.getCompanyById(companyId, session);
	    			utente.setCompany(company);
	    		}
	    		
	    		int success = GestioneUtenteBO.saveUtente(utente, action, session);
	    		
	    		
	    	 			
	    		if(success==0){
	    			
	    			
		    	 		
		    	 		if(check!=null && check.split(",").length==1) {
		    	 			
		    	 			RuoloDTO ruolovie = GestioneRuoloBO.getRuoloById("9", session);
		    	 			RuoloDTO ruoloval = GestioneRuoloBO.getRuoloById("10", session);
		    	 			
		    	 			utente.getListaRuoli().remove(ruolovie);		    	 			
		    	 			utente.getListaRuoli().remove(ruoloval);
		    	 			
			    	 		if(check.split(",")[0].split("_")[0].equals("checkvie")) {
			    	 			
			    	 			utente.getListaRuoli().add(ruolovie);
			    	 			
			    	 		}else if(check.split(",")[0].split("_")[0].equals("checkval")) {
			    	 			
			    	 			utente.getListaRuoli().add(ruoloval);
			    	 		}
			    	 		
			    	 		
			    	 	}else if(check!=null && check.split(",").length==2) {
			    	 		
			    	 		RuoloDTO ruolo_vie = GestioneRuoloBO.getRuoloById("9", session);    	 			
		    	 			
			    	 		RuoloDTO ruolo_val = GestioneRuoloBO.getRuoloById("10", session);
		    	 			
		    	 			utente.getListaRuoli().add(ruolo_vie);
		    	 			utente.getListaRuoli().add(ruolo_val);
			    	 	}
	    			
	    			
	    			myObj.addProperty("success", true);
	    			myObj.addProperty("messaggio","Salvato con Successo");
	    			session.getTransaction().commit();
	    			session.close();
	    			
	    		}
	    		if(success==1){
	    					
	    			myObj.addProperty("success", false);
	    			myObj.addProperty("messaggio","Errore Salvataggio");
	    			
	    			session.getTransaction().rollback();
	    			session.close();
	    		} 
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

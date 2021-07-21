package it.portalECI.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.hibernate.Session;

import com.google.gson.JsonObject;

import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.CampioneDTO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.DTO.VersionePortaleDTO;
import it.portalECI.Exception.ECIException;
import it.portalECI.Util.Utility;
import it.portalECI.bo.GestioneVersionePortaleBO;

/**
 * Servlet implementation class GestioneVersionePortale
 */
@WebServlet("/gestioneVersionePortale.do")
public class GestioneVersionePortale extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestioneVersionePortale() {
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


if(Utility.validateSession(request,response,getServletContext()))return;
		
		response.setContentType("text/html");
		Session session = SessionFacotryDAO.get().openSession();
		session.beginTransaction();
		JsonObject myObj = new JsonObject();
		boolean ajax = false;
		String action = request.getParameter("action");
		
		UtenteDTO utente=(UtenteDTO)request.getSession().getAttribute("userObj");
		
		try {
			if(action==null || action.equals("")) {
				
				ArrayList<VersionePortaleDTO> lista_versioni = GestioneVersionePortaleBO.getListaVersioniPortale(session);
				
				request.getSession().setAttribute("lista_versioni", lista_versioni);
				
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/gestioneVersionePortale.jsp");
		     	dispatcher.forward(request,response);
				
			}
			else if(action!=null && action.equals("nuovo")) {
				
				ajax = true;
				
				response.setContentType("application/json");
				 
			  	List<FileItem> items = null;
		        if (request.getContentType() != null && request.getContentType().toLowerCase().indexOf("multipart/form-data") > -1 ) {

		        		items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
		        	}
		        
		       
				FileItem fileItem = null;
				String filename= null;
		        Hashtable<String,String> ret = new Hashtable<String,String>();
		      
		        for (FileItem item : items) {
	            	 if (!item.isFormField()) {
	            		
	                     fileItem = item;
	                     filename = item.getName();
	                     
	            	 }else
	            	 {
	                      ret.put(item.getFieldName(), new String (item.getString().getBytes ("iso-8859-1"), "UTF-8"));
	            	 }
	            	
	            }
			        
		      DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		      String versione = (String) ret.get("versione");
			  String data_aggiornamento = (String) ret.get("data_aggiornamento");
			  String note_versione = (String) ret.get("note_versione");
			  
			  VersionePortaleDTO versione_portale = new VersionePortaleDTO();
			  
			  versione_portale.setVersione(versione);
			  versione_portale.setData_aggiornamento(df.parse(data_aggiornamento));
			  versione_portale.setNote_aggiornamento(note_versione);
			  
	
			  session.save(versione_portale);
			  
			  String versione_corrente = GestioneVersionePortaleBO.getVersioneCorrente(session);
			  request.getSession().setAttribute("versione_portale",versione_corrente);
			  
			  session.getTransaction().commit();
			  session.close();
			  
			  PrintWriter out = response.getWriter();			
	        		     
	          myObj.addProperty("success", true);
	          myObj.addProperty("messaggio", "Versione salvata con successo!");
	          out.print(myObj);
				
			}
			else if(action!=null && action.equals("modifica")) {
				
				ajax = true;
				
				response.setContentType("application/json");
				 
			  	List<FileItem> items = null;
		        if (request.getContentType() != null && request.getContentType().toLowerCase().indexOf("multipart/form-data") > -1 ) {

		        		items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
		        	}
		        
		       
				FileItem fileItem = null;
				String filename= null;
		        Hashtable<String,String> ret = new Hashtable<String,String>();
		      
		        for (FileItem item : items) {
	            	 if (!item.isFormField()) {
	            		
	                     fileItem = item;
	                     filename = item.getName();
	                     
	            	 }else
	            	 {
	                      ret.put(item.getFieldName(), new String (item.getString().getBytes ("iso-8859-1"), "UTF-8"));
	            	 }
	            	
	            }
			        
		      DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		      String id_versione = (String) ret.get("id_versione");
		      String versione = (String) ret.get("versione_mod");
			  String data_aggiornamento = (String) ret.get("data_aggiornamento_mod");
			  String note_versione = (String) ret.get("note_versione_mod");
			  
			  VersionePortaleDTO versione_portale = GestioneVersionePortaleBO.getVersioneFromId(Integer.parseInt(id_versione), session);
			  
			  versione_portale.setVersione(versione);
			  versione_portale.setData_aggiornamento(df.parse(data_aggiornamento));
			  versione_portale.setNote_aggiornamento(note_versione);
			  
			  session.update(versione_portale);
			  
			  
			  String versione_corrente = GestioneVersionePortaleBO.getVersioneCorrente(session);
			  request.getSession().setAttribute("versione_portale",versione_corrente);
			  
			  
			  session.getTransaction().commit();
			  session.close();
			  
			  PrintWriter out = response.getWriter();			
			  
			  
	        		     
	          myObj.addProperty("success", true);
	          myObj.addProperty("messaggio", "Versione salvata con successo!");
	          out.print(myObj);
				
			}
			else if(action!=null && action.equals("elimina")) {
				
			 ajax = true;
				
				
		      String id_versione = request.getParameter("id_versione");
		      
		      VersionePortaleDTO versione_portale = GestioneVersionePortaleBO.getVersioneFromId(Integer.parseInt(id_versione), session);
			  
			  versione_portale.setDisabilitato(1);
			  session.update(versione_portale);
			  			  
			  String versione_corrente = GestioneVersionePortaleBO.getVersioneCorrente(session);
			  request.getSession().setAttribute("versione_portale",versione_corrente);
			  
			  session.getTransaction().commit();
			  session.close();
			  
			  PrintWriter out = response.getWriter();			
			  
	        		     
	          myObj.addProperty("success", true);
	          myObj.addProperty("messaggio", "Versione eliminata con successo!");
	          out.print(myObj);
				
			}
	
		
		}
			catch(Exception ex)
	    	{
				session.getTransaction().rollback();
	        	session.close();
				if(ajax) {
					PrintWriter out = response.getWriter();
					ex.printStackTrace();
		        	
		        	request.getSession().setAttribute("exception", ex);
		        	myObj = ECIException.callExceptionJsonObject(ex);
		        	myObj.addProperty("success", false);
		        	out.print(myObj);
	        	}else {
			   		ex.printStackTrace();
			   		request.setAttribute("error",ECIException.callException(ex));
			   	    request.getSession().setAttribute("exception", ex);
			   		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/error.jsp");
			   	    dispatcher.forward(request,response);	
	        	}
	    	}  
	}
}

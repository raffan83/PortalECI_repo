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
import it.portalECI.DTO.StrumentoVerificatoreDTO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.Exception.ECIException;
import it.portalECI.Util.Utility;
import it.portalECI.bo.GestioneAnagraficaRemotaBO;
import it.portalECI.bo.GestioneStrumentiVerificatoreBO;
import it.portalECI.bo.GestioneUtenteBO;

/**
 * Servlet implementation class GestioneStrumentiVerificatore
 */
@WebServlet("/gestioneStrumentiVerificatore.do")
public class GestioneStrumentiVerificatore extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestioneStrumentiVerificatore() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
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
			
			if(action == null) {
				
				ArrayList<StrumentoVerificatoreDTO> lista_strumenti = GestioneStrumentiVerificatoreBO.getListaStrumentiAll(session);
				List<UtenteDTO> lista_verificatori = GestioneUtenteBO.getTecnici("2", session);				
							
				request.getSession().setAttribute("lista_strumenti", lista_strumenti);
				request.getSession().setAttribute("lista_verificatori", lista_verificatori);				
				
				session.close();
				
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/listaStrumentiVerificatore.jsp");
		     	dispatcher.forward(request,response);
				
			}
			else if(action.equals("nuovo")) {
				
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
		
				String marca = ret.get("marca");
				String modello = ret.get("modello");
				String matricola = ret.get("matricola");
				String data_ultima_taratura = ret.get("data_ultima_taratura");
				String scadenza = ret.get("scadenza");
				String id_verificatore = ret.get("verificatore");
				String numero_certificato = ret.get("numero_certificato");

				StrumentoVerificatoreDTO strumento = new StrumentoVerificatoreDTO();
				
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				
				strumento.setMarca(marca);
				strumento.setModello(modello);
				strumento.setMatricola(matricola);
				if(data_ultima_taratura!=null && !data_ultima_taratura.equals("")) {
					strumento.setData_ultima_taratura(df.parse(data_ultima_taratura));	
				}
				if(scadenza!=null && !scadenza.equals("")) {
					strumento.setScadenza(df.parse(scadenza));	
				}
				if(id_verificatore!=null && !id_verificatore.equals("") && !id_verificatore.equals("0")) {					
					strumento.setId_verificatore(Integer.parseInt(id_verificatore));
					strumento.setNominativo_verificatore(GestioneUtenteBO.getUtenteById(id_verificatore, session).getNominativo());
				}
				strumento.setNumero_certificato(numero_certificato);
				
				session.save(strumento);				
				
				myObj = new JsonObject();
				PrintWriter  out = response.getWriter();
				myObj.addProperty("success", true);
				myObj.addProperty("messaggio", "Strumento salvato con successo!");
				out.print(myObj);
				session.getTransaction().commit();
				session.close();
			}
			else if(action.equals("modifica")) {
				
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
		
				String marca = ret.get("marca_mod");
				String modello = ret.get("modello_mod");
				String matricola = ret.get("matricola_mod");
				String data_ultima_taratura = ret.get("data_ultima_taratura_mod");
				String scadenza = ret.get("scadenza_mod");
				String id_strumento = ret.get("id_strumento");
				String id_verificatore = ret.get("verificatore_mod");
				String numero_certificato = ret.get("numero_certificato_mod");

				StrumentoVerificatoreDTO strumento = GestioneStrumentiVerificatoreBO.getStrumentoFromId(Integer.parseInt(id_strumento), session);
				
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				
				strumento.setMarca(marca);
				strumento.setModello(modello);
				strumento.setMatricola(matricola);
				if(data_ultima_taratura!=null && !data_ultima_taratura.equals("")) {
					strumento.setData_ultima_taratura(df.parse(data_ultima_taratura));	
				}else {
					strumento.setData_ultima_taratura(null);	
				}
				if(scadenza!=null && !scadenza.equals("")) {
					strumento.setScadenza(df.parse(scadenza));	
				}else {
					strumento.setScadenza(null);
				}
				
				if(id_verificatore!=null && !id_verificatore.equals("")) {
					if(id_verificatore.equals("0")) {
						strumento.setId_verificatore(null);
						strumento.setNominativo_verificatore(null);
					}else {
						strumento.setId_verificatore(Integer.parseInt(id_verificatore));
						strumento.setNominativo_verificatore(GestioneUtenteBO.getUtenteById(id_verificatore, session).getNominativo());
					}
				}
				strumento.setNumero_certificato(numero_certificato);
				
				session.update(strumento);				
				
				myObj = new JsonObject();
				PrintWriter  out = response.getWriter();
				myObj.addProperty("success", true);
				myObj.addProperty("messaggio", "Strumento modificato con successo!");
				out.print(myObj);
				session.getTransaction().commit();
				session.close();
				
			}
			else if(action.equals("dissocia")) {
				
				ajax = true;
				
				String id_strumento = request.getParameter("id_strumento");
				
				StrumentoVerificatoreDTO strumento = GestioneStrumentiVerificatoreBO.getStrumentoFromId(Integer.parseInt(id_strumento), session);
				
				strumento.setId_verificatore(null);
				strumento.setNominativo_verificatore(null);
				session.update(strumento);
				
				myObj = new JsonObject();
				PrintWriter  out = response.getWriter();
				myObj.addProperty("success", true);
				myObj.addProperty("messaggio", "Strumento dissociato con successo!");
				out.print(myObj);
				session.getTransaction().commit();
				session.close();
			}
			else if(action.equals("elimina")) {
				
				ajax = true;
				
				String id_strumento = request.getParameter("id_strumento");
				
				StrumentoVerificatoreDTO strumento = GestioneStrumentiVerificatoreBO.getStrumentoFromId(Integer.parseInt(id_strumento), session);
				strumento.setDisabilitato(1);
				
				session.update(strumento);
				
				myObj = new JsonObject();
				PrintWriter  out = response.getWriter();
				myObj.addProperty("success", true);
				myObj.addProperty("messaggio", "Strumento eliminato con successo!");
				out.print(myObj);
				session.getTransaction().commit();
				session.close();
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

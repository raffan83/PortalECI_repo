package it.portalECI.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.Session;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import it.portalECI.Exception.ECIException;
import it.portalECI.Util.Utility;
import it.portalECI.DAO.GestioneAccessoDAO;
import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.Util.Costanti;
import it.portalECI.bo.GestioneUtenteBO;

/**
 * Servlet implementation class AreaUtente
 */
@WebServlet("/areaUtente.do")
public class AreaUtente extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AreaUtente() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Session session = SessionFacotryDAO.get().openSession();
		session.beginTransaction();
		
		boolean ajax = false;
		
		JsonObject myObj = new JsonObject();
		try
		{
		if(Utility.validateSession(request,response,getServletContext()))return;
		

   
        //response.setContentType("application/json");
		
		String action = request.getParameter("action");
		
		
		if(action==null || action.equals("")) {

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/profiloUtente.jsp");
	    dispatcher.forward(request,response);
	    
		}
		
		else if(action.equals("modifica_utente")) {
			String result = request.getParameter("param");
			
			String json = request.getParameter("dataIn");
			
			JsonElement jelement = new JsonParser().parse(json);
			 
			JsonObject  jobject = jelement.getAsJsonObject();

			
			UtenteDTO usr = (UtenteDTO)request.getSession().getAttribute("userObj");
			UtenteDTO utente = GestioneUtenteBO.getUtenteById(String.valueOf(usr.getId()), session);
		
			utente.setIndirizzo(jobject.get("indirizzoUsr").getAsString());
			utente.setComune(jobject.get("comuneUsr").getAsString());
			utente.setCap(jobject.get("capUsr").getAsString());
			utente.setEMail(jobject.get("emailUsr").getAsString());
			utente.setTelefono(jobject.get("telUsr").getAsString());
			
			session.update(utente);
			//GestioneAccessoDAO.updateUser(utente);
			session.getTransaction().commit();
			session.close();

			request.getSession().setAttribute("userObj", utente);
			
		 	myObj.addProperty("success", true);
		 	PrintWriter out = response.getWriter();
			out.println(myObj);
		   
		out.close();
		
		}
		else if(action.equals("modifica_pin")) {

			
			String pin_attuale = request.getParameter("pin_attuale");
			String nuovo_pin = request.getParameter("nuovo_pin");
			String firma_documento = request.getParameter("firma_documento");
			boolean esito = true;
			
			UtenteDTO usr = (UtenteDTO)request.getSession().getAttribute("userObj");
			UtenteDTO utente = GestioneUtenteBO.getUtenteById(String.valueOf(usr.getId()), session);			
			
			if(pin_attuale!=null) {
			 esito= GestioneUtenteBO.checkPINFirma(utente.getId(), pin_attuale, session);
			}
			
			if(esito) {
				utente.setPin_firma(nuovo_pin);
							
				session.update(utente);
	
				request.getSession().setAttribute("userObj", utente);
				myObj.addProperty("success", true);
			 	//myObj.addProperty("pin", nuovo_pin);
				if(firma_documento!=null) {
					myObj.addProperty("pin", nuovo_pin);
				}
				
			 	myObj.addProperty("messaggio", "Modifica eseguita con successo");
			}else {
				myObj.addProperty("success", false);
				myObj.addProperty("messaggio", "Attenzione! il PIN inserito non &egrave; associato all\'utente corrente!");
			 }
			session.getTransaction().commit();
		 	session.close();
		 	PrintWriter out = response.getWriter();
			out.println(myObj.toString());
		   
		out.close();
			
		}else if(action.equals("upload_firma")) {
			
			ajax = true;				
		
			ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());				
			response.setContentType("application/json");
								
			UtenteDTO usr = (UtenteDTO)request.getSession().getAttribute("userObj");
			UtenteDTO utente = GestioneUtenteBO.getUtenteById(String.valueOf(usr.getId()), session);
	
			List<FileItem> items;
			
				items = uploadHandler.parseRequest(request);
				File folder = new File(Costanti.PATH_ROOT+"\\FileFirme\\"); 
				for (FileItem item : items) {
					if (!item.isFormField()) {
						if(item.getName()!="") {		
							if(!folder.exists()) {
								folder.mkdirs();
							}
							
							File file = new File(folder.getPath() + "\\"+ utente.getId()+ "."+ FilenameUtils.getExtension(item.getName()));

								while(true) {		
										item.write(file);
										break;
									} 									
								}
							utente.setFile_firma(utente.getId() + "."+ FilenameUtils.getExtension(item.getName()));
						}								
					}
				
			
				session.update(utente);
				session.getTransaction().commit();
				session.close();			
				request.getSession().setAttribute("userObj", utente);
				myObj.addProperty("success", true);					
				myObj.addProperty("messaggio", "Allegato caricato con successo!");
				PrintWriter out = response.getWriter();
				out.print(myObj);		
			
			}
		
		else if (action.equals("download_img")) {
			
			UtenteDTO usr = (UtenteDTO)request.getSession().getAttribute("userObj");
			
			File file = new File(Costanti.PATH_ROOT+"\\FileFirme\\"+usr.getFile_firma());
			
			FileInputStream fileIn = new FileInputStream(file);
			
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition","attachment;filename="+ file.getName());

			 ServletOutputStream outp = response.getOutputStream();
			     
			    byte[] outputByte = new byte[1];
			    
			    while(fileIn.read(outputByte, 0, 1) != -1)
			    {
			    	outp.write(outputByte, 0, 1);
			    }
			    
			    session.close();
			    fileIn.close();
			    outp.flush();
			    outp.close();
			
		}
		
		
		}
		
		
		
		
		catch(Exception ex)
    	{
			PrintWriter out = response.getWriter();
			if(ajax) {
				ex.printStackTrace();
	        	session.getTransaction().rollback();
	        	session.close();
	        	request.getSession().setAttribute("exception", ex);
	        	//myObj.addProperty("success", false);
	        	//myObj.addProperty("messaggio", STIException.callException(ex).toString());
	        	myObj = ECIException.callExceptionJsonObject(ex);
	        	out.println(myObj.toString());
	        	  	
			}else {
	    		 ex.printStackTrace();
	    		 request.getSession().setAttribute("exception",ex);
	    	     request.setAttribute("error",ECIException.callException(ex));
	    		 RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/error.jsp");
	    	     dispatcher.forward(request,response);
			}
    	}  
		
	}

}

package it.portalECI.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
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
import org.hibernate.Session;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import it.portalECI.DAO.GestioneDocumentoDAO;
import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.Exception.ECIException;
import it.portalECI.Util.Utility;
import it.portalECI.DTO.DocumentoDTO;
import it.portalECI.bo.GestioneCampioneBO;
import it.portalECI.DAO.GestioneCampioneDAO;
import it.portalECI.DTO.CampioneDTO;
import it.portalECI.Util.Costanti;

/**
 * Servlet implementation class DocumentiEsterni
 */
@WebServlet("/documentiEsterni.do")
public class DocumentiEsterni extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DocumentiEsterni() {
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
		
		if(Utility.validateSession(request,response,getServletContext()))return;
		Session session = SessionFacotryDAO.get().openSession();
		session.beginTransaction();
		UtenteDTO utente = (UtenteDTO) request.getSession().getAttribute("userObj");
		
		String action = request.getParameter("action");
		JsonObject myObj = new JsonObject();
		boolean ajax = false;
        response.setContentType("application/json");
	
    	try {
			if(action.equals("campioni")) {
				
				String idS = request.getParameter("id_str");

				 ArrayList<DocumentoDTO> lista_documenti_esterni = GestioneCampioneBO.getListaDocumentiEsterni(Integer.parseInt(idS),session);

				 request.getSession().setAttribute("lista_documenti_esterni",lista_documenti_esterni);
			        request.getSession().setAttribute("id_campione",idS);
			        session.close();
					 RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/documentiEsterniCampione.jsp");
				     dispatcher.forward(request,response);
			}
			if(action.equals("caricaDocumento")) {
				PrintWriter writer = response.getWriter();
				JsonObject jsono = new JsonObject();
				
				ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());
				List<FileItem> items = uploadHandler.parseRequest(request);
				
				String id_campione = "";
		
				FileItem fileUploaded = null;
				for (FileItem item : items) {
					if (!item.isFormField()) {
 
						fileUploaded = item;
 
					}else {
						
						if(item.getFieldName().equals("id_campione")) {
							id_campione = item.getString();
						}
						

					}
					
				
				}
				
				CampioneDTO campione = GestioneCampioneDAO.getCampioneFromId(id_campione, session);
				if(fileUploaded != null) {
					
					File directory =new File(Costanti.PATH_ROOT+"//Campioni//DocumentiEsterni//"+id_campione);
					
					if(directory.exists()==false)
					{
						directory.mkdirs();
					}
					
					File file = new File(Costanti.PATH_ROOT+"//Campioni//DocumentiEsterni//"+id_campione+"//"+fileUploaded.getName());
		
						fileUploaded.write(file);
											
						DocumentoDTO documento = new DocumentoDTO();
								
						documento.setCreateDate(new Date());
						documento.setCampione(campione);
						documento.setFilePath(fileUploaded.getName());
						
						session.save(documento);
				
						session.getTransaction().commit();
						session.close();
						
						
						
						jsono.addProperty("success", true);
						jsono.addProperty("messaggio","ok");
					}

				writer.write(jsono.toString());
				writer.close();
			}
			
			
			
			if(action.equals("scaricaDocumento"))
			{
			String idDocumento= request.getParameter("idDoc");
			 	
			 	DocumentoDTO documento= GestioneDocumentoDAO.getDocumento(idDocumento, session);
				session.close();	
				
			 	if(documento!=null)
			 	{
				
			     File d = new File(Costanti.PATH_ROOT+"//Campioni//DocumentiEsterni//"+documento.getCampione().getId()+"//"+documento.getFilePath());
				 
				 FileInputStream fileIn = new FileInputStream(d);
				 
				 response.setContentType("application/pdf");
				 response.setHeader("Content-Disposition","attachment;filename="+documento.getFilePath());
				 
				 ServletOutputStream outp = response.getOutputStream();
				     
				    byte[] outputByte = new byte[1];
				    
				    while(fileIn.read(outputByte, 0, 1) != -1)
				    {
				    	outp.write(outputByte, 0, 1);
				     }
				    
				    
				    fileIn.close();
			
				    outp.flush();
				    outp.close();
			 	}
			}
			if(action.equals("eliminaDocumento"))
			{
				PrintWriter writer = response.getWriter();
				JsonObject jsono = new JsonObject();
				
				String idDocumento= request.getParameter("idDoc");
				
				DocumentoDTO documento =  GestioneDocumentoDAO.getDocumento(idDocumento, session);
				session.delete(documento);

				jsono.addProperty("success", true);
				jsono.addProperty("messaggio", "Documento eliminato correttamente");
				
				writer.write(jsono.toString());
				writer.close();
				
				
				session.getTransaction().commit();
				session.close();
			}
    	}catch (Exception e) {
			session.getTransaction().rollback();
        	session.close();
			if(ajax) {
				PrintWriter out = response.getWriter();
				e.printStackTrace();	        	
	        	request.getSession().setAttribute("exception", e);
	        
	        	myObj = ECIException.callExceptionJsonObject(e);
	        	out.print(myObj);
        	}else {   			    			
    			e.printStackTrace();
    			request.setAttribute("error",ECIException.callException(e));
    	  	    request.getSession().setAttribute("exception", e);
    			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/error.jsp");
    		    dispatcher.forward(request,response);	
        	}
		}
}
}
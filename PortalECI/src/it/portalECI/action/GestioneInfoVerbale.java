package it.portalECI.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.google.gson.JsonObject;

import it.portalECI.DAO.GestioneCommesseDAO;
import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.ClienteDTO;
import it.portalECI.DTO.CommessaDTO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.DTO.VerbaleDTO;
import it.portalECI.Exception.ECIException;
import it.portalECI.Util.Utility;
import it.portalECI.bo.GestioneAnagraficaRemotaBO;
import it.portalECI.bo.GestioneCommesseBO;
import it.portalECI.bo.GestioneComunicazioniBO;
import it.portalECI.bo.GestioneVerbaleBO;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class GestioneInfoVerbale
 */
@WebServlet("/gestioneInfoVerbale.do")
public class GestioneInfoVerbale extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestioneInfoVerbale() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Session session=SessionFacotryDAO.get().openSession();
		session.beginTransaction();
		boolean ajax = true;
			try {
				
				
				String action = request.getParameter("action");
			
				
				if(action==null || action.equals("")) {
					
					String id = request.getParameter("id");
					
					byte[] valueDecoded = Base64.decodeBase64(id.getBytes());
					
					String id_verbale = new String(valueDecoded);
					
					VerbaleDTO verbale = GestioneVerbaleBO.getVerbale(id_verbale, session);
					
					ClienteDTO cliente = GestioneAnagraficaRemotaBO.getClienteById(""+verbale.getIntervento().getId_cliente());		
					
					CommessaDTO clienteUtilizzatore = GestioneCommesseBO.getCommessaById(verbale.getIntervento().getIdCommessa());
					
					String indirizzo_utilizzatore ="";
					String cliente_utilizzatore = "";
					
					if(verbale.getAttrezzatura()!=null) {
						
						if(verbale.getAttrezzatura().getIndirizzo_div()!=null) {
							cliente_utilizzatore = verbale.getAttrezzatura().getPresso_div();
							indirizzo_utilizzatore = verbale.getAttrezzatura().getIndirizzo_div() + " - "+verbale.getAttrezzatura().getCap_div()+" - "+verbale.getAttrezzatura().getComune_div()+" ("+verbale.getAttrezzatura().getProvincia_div()+")";
							
						}else {
							if(verbale.getAttrezzatura().getId_sede()==0) {
								cliente_utilizzatore = verbale.getAttrezzatura().getNome_cliente();
							}else {
								cliente_utilizzatore = verbale.getAttrezzatura().getNome_sede();
							}
							indirizzo_utilizzatore = verbale.getAttrezzatura().getIndirizzo() + " - "+verbale.getAttrezzatura().getCap()+" - "+verbale.getAttrezzatura().getComune()+" ("+verbale.getAttrezzatura().getProvincia()+")";
							
						}

						//html = html.replaceAll("\\$\\{CLIENTE_UTILIZZATORE\\}", cliente_utilizzatore);
						//html = html.replaceAll("\\$\\{INDIRIZZO_CLIENTE_UTILIZZATORE\\}", indirizzo_utilizzatore);
					}else {
						
					
						if(verbale.getDescrizione_sede_utilizzatore()!=null && !verbale.getDescrizione_sede_utilizzatore().equals("")) {
							cliente_utilizzatore = verbale.getDescrizione_sede_utilizzatore(); 
						//	html = html.replaceAll("\\$\\{CLIENTE_UTILIZZATORE\\}", verbale.getDescrizione_sede_utilizzatore());				
						
						}else {
							if(clienteUtilizzatore != null && clienteUtilizzatore.getNOME_UTILIZZATORE()!=null) {
								cliente_utilizzatore =  clienteUtilizzatore.getNOME_UTILIZZATORE(); 
					//			html = html.replaceAll("\\$\\{CLIENTE_UTILIZZATORE\\}", clienteUtilizzatore.getNOME_UTILIZZATORE());
							}
						}
						
						
						if(clienteUtilizzatore != null && clienteUtilizzatore.getINDIRIZZO_UTILIZZATORE()!=null) {

							indirizzo_utilizzatore = clienteUtilizzatore.getINDIRIZZO_UTILIZZATORE();
					//		html = html.replaceAll("\\$\\{INDIRIZZO_CLIENTE_UTILIZZATORE\\}", clienteUtilizzatore.getINDIRIZZO_UTILIZZATORE());
											
						}
					}
					
					if(verbale.getSedeUtilizzatore() != null ) 
					{
						indirizzo_utilizzatore = verbale.getSedeUtilizzatore();
					}
					
					

					request.getSession().setAttribute("verbale", verbale);
					request.getSession().setAttribute("ragione_sociale",cliente_utilizzatore);
					request.getSession().setAttribute("indirizzo",indirizzo_utilizzatore);
					request.getSession().setAttribute("partita_iva",cliente.getPartita_iva());

					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/infoVerbale.jsp");
				    dispatcher.forward(request,response);

				}
				else if(action!=null && action.equals("informativa_privacy")) {
					
					 RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/informativaPrivacy.jsp");
			            dispatcher.forward(request,response);
				}
				else if(action!= null && action.equals("invia_info")) {
					
					
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
			
					String nome = ret.get("nome");
					String cognome = ret.get("cognome");
					String motivazione = ret.get("motivazione");
					String email = ret.get("email");
					String id_verbale = ret.get("id_verbale");
					VerbaleDTO verbale = GestioneVerbaleBO.getVerbale(id_verbale, session);
					
					GestioneComunicazioniBO.sendEmailInfoVerbale(verbale, nome, cognome, motivazione, email);
					
					JsonObject myObj = new JsonObject();
					PrintWriter  out = response.getWriter();
					myObj.addProperty("success", true);
					myObj.addProperty("messaggio", "Informazioni inviate con successo!");
					out.print(myObj);
					
				}
				
							     	
			    session.getTransaction().commit();
				session.close();
				
			}catch(Exception ex){
				
				session.getTransaction().rollback();
	        	session.close();
				if(ajax) {
					PrintWriter out = response.getWriter();
					ex.printStackTrace();
		        	
		        	request.getSession().setAttribute("exception", ex);
		        	JsonObject myObj = ECIException.callExceptionJsonObject(ex);
		        	myObj.addProperty("success", false);
		        	out.print(myObj);
	        	}else {
					ex.printStackTrace();
		   		 	request.setAttribute("error",ECIException.callException(ex));
		   		 	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("page/error.jsp");
		   		 	dispatcher.forward(request,response);	
				}
				
	   		 	
			}  
	}

}

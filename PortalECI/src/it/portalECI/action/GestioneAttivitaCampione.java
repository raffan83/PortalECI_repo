package it.portalECI.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
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

import com.google.gson.JsonObject;

import it.portalECI.DAO.GestioneAccessoDAO;
import it.portalECI.DAO.GestioneCampioneDAO;
import it.portalECI.DAO.GestioneUtenteDAO;
import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.AcAttivitaCampioneDTO;
import it.portalECI.DTO.AcTipoAttivitaCampioniDTO;
import it.portalECI.DTO.CampioneDTO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.Exception.ECIException;
import it.portalECI.Util.Utility;
import it.portalECI.bo.CreateSchedaApparecchiaturaCampioni;
import it.portalECI.bo.CreateSchedaManutenzioniCampione;
import it.portalECI.bo.CreateSchedaTaraturaVerificaIntermedia;
import it.portalECI.bo.GestioneAttivitaCampioneBO;
import it.portalECI.bo.GestioneUtenteBO;
import it.portalECI.Util.Costanti;


/**
 * Servlet implementation class GestioneAttivitaCampione
 */
@WebServlet("/gestioneAttivitaCampioni.do")
public class GestioneAttivitaCampione extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestioneAttivitaCampione() {
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
		//UtenteDTO utente = (UtenteDTO) request.getSession().getAttribute("userObj");
		
		String action = request.getParameter("action");
		JsonObject myObj = new JsonObject();
		boolean ajax = false;
        response.setContentType("application/json");
		try {
			
			if(action.equals("lista")) {
				String idC = request.getParameter("idCamp");
				
				ArrayList<AcAttivitaCampioneDTO> lista_attivita = GestioneAttivitaCampioneBO.getListaAttivita(Integer.parseInt(idC), session);
				ArrayList<AcTipoAttivitaCampioniDTO> lista_tipo_attivita = GestioneAttivitaCampioneBO.getListaTipoAttivitaCampione(session);
				
				//CampioneDTO campione = GestioneCampioneDAO.getCampioneFromId(idC);
				ArrayList<UtenteDTO> lista_utenti = (ArrayList<UtenteDTO>) GestioneAccessoDAO.getListUser(session);
				
				request.getSession().setAttribute("lista_attivita", lista_attivita);
				request.getSession().setAttribute("lista_tipo_attivita_campioni", lista_tipo_attivita);
				request.getSession().setAttribute("lista_utenti", lista_utenti);
				session.getTransaction().commit();
				session.close();
				
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/listaAttivitaCampione.jsp");
				dispatcher.forward(request,response);
			}
			else if(action.equals("nuova")) {
				
				 response.setContentType("application/json");
				 
				  	List<FileItem> items = null;
			        if (request.getContentType() != null && request.getContentType().toLowerCase().indexOf("multipart/form-data") > -1 ) {

			        		items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
			        	}			        
					FileItem fileItemAllegato = null;
					String filenameAllegato= null;
					FileItem fileItemCertificato= null;
					String filenameCertificato= null;
			        Hashtable<String,String> ret = new Hashtable<String,String>();			      
			        for (FileItem item : items) {
		            	 if (!item.isFormField() && item.getFieldName().equals("fileupload_certificato")) {
		            		 fileItemCertificato = item;
		                     filenameCertificato = item.getName();
		            	 }
		            	 else if  (!item.isFormField() && item.getFieldName().equals("fileupload_all")) {
		            		 fileItemAllegato = item;
		                     filenameAllegato = item.getName();
		            	 }
		            	 else
		            	 {
		                      ret.put(item.getFieldName(), new String (item.getString().getBytes ("iso-8859-1"), "UTF-8"));
		            	 }		            	
		            }
				
				String idC = request.getParameter("idCamp");				
				String tipo_attivita = ret.get("select_tipo_attivita");
				String data_attivita = ret.get("data_attivita");
				String descrizione = ret.get("descrizione");
				String tipo_manutenzione = ret.get("select_tipo_manutenzione");
				String ente = ret.get("ente");
				String data_scadenza = ret.get("data_scadenza");
				String etichettatura = ret.get("etichettatura");
				String stato = ret.get("stato");
				String campo_sospesi = ret.get("campo_sospesi");
				String operatore = ret.get("operatore");				
				String numero_certificato = ret.get("numero_certificato");
				
				CampioneDTO campione = GestioneCampioneDAO.getCampioneFromId(idC, session);				
				
				AcAttivitaCampioneDTO attivita = new AcAttivitaCampioneDTO();
				attivita.setCampione(campione);
				attivita.setTipo_attivita(new AcTipoAttivitaCampioniDTO(Integer.parseInt(tipo_attivita),""));
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date date = format.parse(data_attivita);
				attivita.setData(date);				
				attivita.setDescrizione_attivita(descrizione);
				attivita.setObsoleta("N");
				if(operatore!=null && !operatore.equals("")) {
					UtenteDTO user = GestioneUtenteBO.getUtenteById(operatore, session);
					attivita.setOperatore(user);
				}
				if(tipo_manutenzione!=null && !tipo_manutenzione.equals("")) {
					attivita.setTipo_manutenzione(Integer.parseInt(tipo_manutenzione));	
				}
				if(Integer.parseInt(tipo_attivita)==2 || Integer.parseInt(tipo_attivita)==3) {
					attivita.setEnte(ente);					
					attivita.setData_scadenza(format.parse(data_scadenza));
					
					if(Integer.parseInt(tipo_attivita)==3) {
						campione.setDataVerifica(date);
						campione.setDataScadenza(format.parse(data_scadenza));
						if(numero_certificato!=null) {
							campione.setNumeroCertificato(numero_certificato);
						}
						campione.setStatoCampione("S");
						session.update(campione);
					}
					attivita.setNumero_certificato(numero_certificato);
					attivita.setEtichettatura(etichettatura);
					attivita.setStato(stato);
					attivita.setCampo_sospesi(campo_sospesi);
				}else {
					
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);
					calendar.add(Calendar.MONTH, campione.getFrequenza_manutenzione());

					Date data_scadenza_man = calendar.getTime();
					attivita.setData_scadenza(data_scadenza_man);
				}
				
				if(fileItemCertificato!=null && !filenameCertificato.equals("")) {

					saveFile(fileItemCertificato, campione.getId(), 1, filenameCertificato);
					attivita.setCertificato(filenameCertificato);
				}
				
				if(fileItemAllegato!=null && !filenameAllegato.equals("")) {

					saveFile(fileItemAllegato, campione.getId(), 0, filenameAllegato);
					attivita.setAllegato(filenameAllegato);
				}
				
				if(tipo_attivita.equals("2") ||  tipo_attivita.equals("3") || (tipo_attivita.equals("1") && tipo_manutenzione!=null && tipo_manutenzione.equals("1"))) {
					GestioneAttivitaCampioneBO.updateObsolete(idC, Integer.parseInt(tipo_attivita),session);
				}
				
				session.save(attivita);
				
				
				session.getTransaction().commit();
				session.close();
				
				PrintWriter out = response.getWriter();
				myObj.addProperty("success", true);
				myObj.addProperty("messaggio", "Attività salvata con successo!");
				out.print(myObj);
			}

			
			else if(action.equals("modifica")) {
				
				 response.setContentType("application/json");
				 
				  	List<FileItem> items = null;
			        if (request.getContentType() != null && request.getContentType().toLowerCase().indexOf("multipart/form-data") > -1 ) {

			        		items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
			        	}			        
			        FileItem fileItemAllegato = null;
					String filenameAllegato= null;
					FileItem fileItemCertificato= null;
					String filenameCertificato= null;
			        Hashtable<String,String> ret = new Hashtable<String,String>();			      
			        for (FileItem item : items) {
		            	 if (!item.isFormField() && item.getFieldName().equals("fileupload_certificato_mod")) {
		            		 fileItemCertificato = item;
		                     filenameCertificato = item.getName();
		            	 }
		            	 else if  (!item.isFormField() && item.getFieldName().equals("fileupload_all_mod")) {
		            		 fileItemAllegato = item;
		                     filenameAllegato = item.getName();
		            	 }else
		            	 {
		                      ret.put(item.getFieldName(), new String (item.getString().getBytes ("iso-8859-1"), "UTF-8"));
		            	 }		            	
		            }
			
			    String id_attivita = ret.get("id_attivita");
				String tipo_attivita = ret.get("select_tipo_attivita_mod");
				String data_attivita = ret.get("data_attivita_mod");
				String descrizione = ret.get("descrizione_mod");
				String tipo_manutenzione = ret.get("select_tipo_manutenzione_mod");				
			
				String ente = ret.get("ente_mod");
				String data_scadenza = ret.get("data_scadenza_mod");
				String etichettatura = ret.get("etichettatura_mod");
				String stato = ret.get("stato_mod");
				String campo_sospesi = ret.get("campo_sospesi_mod");
				String operatore = ret.get("operatore_mod");
				String numero_certificato = ret.get("numero_certificato_mod");
				
				AcAttivitaCampioneDTO attivita = GestioneAttivitaCampioneBO.getAttivitaFromId(Integer.parseInt(id_attivita), session);
				
				attivita.setTipo_attivita(new AcTipoAttivitaCampioniDTO(Integer.parseInt(tipo_attivita),""));
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date date = format.parse(data_attivita);
				attivita.setData(date);
				attivita.setDescrizione_attivita(descrizione);
				if(operatore!=null && !operatore.equals("")) {
					UtenteDTO user = GestioneUtenteBO.getUtenteById(operatore, session);
					attivita.setOperatore(user);
				}
				if(tipo_manutenzione!=null && !tipo_manutenzione.equals("")) {
					attivita.setTipo_manutenzione(Integer.parseInt(tipo_manutenzione));	
				}
				
				if(Integer.parseInt(tipo_attivita)==2 || Integer.parseInt(tipo_attivita)==3) {
					attivita.setEnte(ente);					
					attivita.setData_scadenza(format.parse(data_scadenza));
					
					if(Integer.parseInt(tipo_attivita)==3) {
						attivita.getCampione().setDataVerifica(date);
						attivita.getCampione().setDataScadenza(format.parse(data_scadenza));
						if(numero_certificato!=null) {
							attivita.getCampione().setNumeroCertificato(numero_certificato);
						}
						session.update(attivita.getCampione());
					}
					
					attivita.setNumero_certificato(numero_certificato);
					attivita.setEtichettatura(etichettatura);
					attivita.setStato(stato);
					attivita.setCampo_sospesi(campo_sospesi);
				}else {
					
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);
					calendar.add(Calendar.MONTH, attivita.getCampione().getFrequenza_manutenzione());

					Date data_scadenza_man = calendar.getTime();
					attivita.setData_scadenza(data_scadenza_man);
				}
				
				
				if(fileItemCertificato!=null && !filenameCertificato.equals("")) {

					saveFile(fileItemCertificato, attivita.getCampione().getId(), 1, filenameCertificato);
					attivita.setCertificato(filenameCertificato);
				}
				
				if(fileItemAllegato!=null && !filenameAllegato.equals("")) {

					saveFile(fileItemAllegato, attivita.getCampione().getId(), 0, filenameAllegato);
					attivita.setAllegato(filenameAllegato);
				}
				
				session.update(attivita);
				session.getTransaction().commit();
				session.close();
				
				PrintWriter out = response.getWriter();
				myObj.addProperty("success", true);
				myObj.addProperty("messaggio", "Attività modificata con successo!");
				out.print(myObj);
			}
			
			else if(action.equals("scheda_manutenzioni")) {
				
				ajax = false;
				
				String id_campione = request.getParameter("id_campione");
				
				ArrayList<AcAttivitaCampioneDTO> lista_manutenzioni = GestioneAttivitaCampioneBO.getListaManutenzioni(Integer.parseInt(id_campione), session);
				ArrayList<AcAttivitaCampioneDTO> lista_fuori_servizio = GestioneAttivitaCampioneBO.getListaFuoriServizio(Integer.parseInt(id_campione), session);
				CampioneDTO campione= null;
				if(lista_manutenzioni.size()>0) {
					campione = lista_manutenzioni.get(0).getCampione();
				}else {
					campione = GestioneCampioneDAO.getCampioneFromId(id_campione, session);
				}
				new CreateSchedaManutenzioniCampione(lista_manutenzioni, lista_fuori_servizio, campione);
				
				String path = Costanti.PATH_ROOT+"\\Campioni\\"+id_campione+"\\SchedaManutenzione\\sma_"+id_campione+".pdf";
				
				File file = new File(path);
				
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
			
			else if(action.equals("scheda_verifiche_intermedie")) {
				
				ajax = false;
				
				String id_campione = request.getParameter("id_campione");
				
				ArrayList<AcAttivitaCampioneDTO> lista_verifiche = GestioneAttivitaCampioneBO.getListaTaratureVerificheIntermedie(Integer.parseInt(id_campione), session);
				ArrayList<AcAttivitaCampioneDTO> lista_fuori_servizio = GestioneAttivitaCampioneBO.getListaFuoriServizio(Integer.parseInt(id_campione), session);
				CampioneDTO campione= null;
				if(lista_verifiche.size()>0) {
					campione = lista_verifiche.get(0).getCampione();
				}else {
					campione = GestioneCampioneDAO.getCampioneFromId(id_campione, session);
				}
				new CreateSchedaTaraturaVerificaIntermedia(lista_verifiche,lista_fuori_servizio, campione);
				
				String path = Costanti.PATH_ROOT+"\\Campioni\\"+id_campione+"\\Taratura\\sta_"+id_campione+".pdf";
			
				File file = new File(path);
				
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
			else if(action.equals("scheda_apparecchiatura")) {
				
				ajax = false;
				
				String id_campione = request.getParameter("id_campione");
				
				//ArrayList<AcAttivitaCampioneDTO> lista_verifiche = GestioneAttivitaCampioneBO.getListaTaratureVerificheIntermedie(Integer.parseInt(id_campione), session);
				CampioneDTO campione = GestioneCampioneDAO.getCampioneFromId(id_campione, session);
				
				new CreateSchedaApparecchiaturaCampioni(campione,false, session);
				
				String path = Costanti.PATH_ROOT+"\\Campioni\\"+id_campione+"\\SchedaApparecchiatura\\sa_"+id_campione+".pdf";
				
				File file = new File(path);
				
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
			else if(action.equals("download_allegato_certificato")) {
				
				ajax = false;
				String id_attivita = request.getParameter("id_attivita");
				String certificato = request.getParameter("isCertificato");
								
				
				AcAttivitaCampioneDTO attivita = GestioneAttivitaCampioneBO.getAttivitaFromId(Integer.parseInt(id_attivita), session);
				
				
				String destinazione = "Allegati";
				String nome_file = attivita.getAllegato();
				if(certificato!=null) {
					destinazione = "Certificati";
					nome_file = attivita.getCertificato();
				}
				
				String path = Costanti.PATH_ROOT+"//Campioni//"+attivita.getCampione().getId()+"//"+destinazione+"//AttivitaManutenzione//"+nome_file;

				File file = new File(path);
				
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
			if(action.equals("lista_verifiche_intermedie")) {
				String idC = request.getParameter("idCamp");
				
				ArrayList<AcAttivitaCampioneDTO> lista_verifiche_intermedie = GestioneAttivitaCampioneBO.getListaVerificheIntermedie(Integer.parseInt(idC), session);
				
				request.getSession().setAttribute("lista_attivita", lista_verifiche_intermedie);
							
				session.close();
				
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/site/listaVerificheIntermedie.jsp");
				dispatcher.forward(request,response);
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
	
	
	
	 private void saveFile(FileItem item, int id_campione,int tipo_file, String filename) {

		 
		 String tipo = "Allegati";
		 if(tipo_file == 1) {
			 tipo = "Certificati";
		 }
		 	String path_folder = Costanti.PATH_ROOT+"//Campioni//"+id_campione+"//"+tipo+"//AttivitaManutenzione//";
			File folder=new File(path_folder);
			
			if(!folder.exists()) {
				folder.mkdirs();
			}
		
			
			while(true)
			{
				File file=null;
				
				
				file = new File(path_folder+filename);					
				
					try {
						item.write(file);
						break;

					} catch (Exception e) 
					{

						e.printStackTrace();
						break;
					}
			}
		
		}
	

}

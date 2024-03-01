package it.portalECI.action;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.google.gson.JsonObject;

import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.AllegatoClienteDTO;
import it.portalECI.DTO.AllegatoMinisteroDTO;
import it.portalECI.DTO.ClienteDTO;
import it.portalECI.DTO.CompanyDTO;
import it.portalECI.DTO.DocumentoDTO;
import it.portalECI.DTO.SedeDTO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.DTO.VerbaleDTO;
import it.portalECI.Exception.ECIException;
import it.portalECI.Util.Utility;
import it.portalECI.bo.CreateScadenzarioVAL;
import it.portalECI.bo.CreateScadenzarioVIE;
import it.portalECI.bo.GestioneAnagraficaRemotaBO;
import it.portalECI.bo.GestioneVerbaleBO;
import it.portalECI.Util.Costanti;

/**
 * Servlet implementation class GestioneUtenti
 */
@WebServlet(name = "gestioneListaVerbali", urlPatterns = { "/gestioneListaVerbali.do" })
public class GestioneListaVerbali extends HttpServlet {
	
	static final Logger logger = Logger.getLogger(GestioneListaVerbali.class);
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestioneListaVerbali() {
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
		if(Utility.validateSession(request,response,getServletContext())) return;
		
		response.setContentType("text/html");
		boolean ajax = false;
		Session session=SessionFacotryDAO.get().openSession();
		session.beginTransaction();
		 
		try {
			
				
			UtenteDTO user = (UtenteDTO)request.getSession().getAttribute("userObj");
			
			String action = request.getParameter("action");
			if(action == null || action.equals("") ) {
				
				
				
				LocalDateTime startDate = LocalDateTime.now().minusYears(1);
				LocalDateTime endDate  = LocalDateTime.now();
				
				String dateTo = endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss "));
				
				String dateFrom = startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));				
								
				List<VerbaleDTO> listaVerbali =GestioneVerbaleBO.getListaVerbaliDataCreazione(session,user,dateFrom, dateTo) ;					
				List<VerbaleDTO> listaVerbaliInCorso = GestioneVerbaleBO.getVerbaliAttivi(session, user,dateFrom, dateTo );
							
				listaVerbali.addAll(listaVerbaliInCorso);
				request.getSession().setAttribute("listaVerbali", listaVerbali);
				request.getSession().setAttribute("dateFrom", null);
				request.getSession().setAttribute("dateTo", null);

				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/gestioneListaVerbali.jsp");
		     	dispatcher.forward(request,response);
		     	
		     	session.getTransaction().commit();
				session.close();
			}
			
			else if(action.equals("filtra_date")){				
				
				String dateFrom = request.getParameter("dateFrom");
				String dateTo = request.getParameter("dateTo");	
				

				List<VerbaleDTO> listaVerbali =GestioneVerbaleBO.getListaVerbaliDate(session,user, dateFrom, dateTo);
				
				request.getSession().setAttribute("listaVerbali", listaVerbali);
				request.getSession().setAttribute("dateFrom", dateFrom);
				request.getSession().setAttribute("dateTo", dateTo);

				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/gestioneListaVerbali.jsp");
		     	dispatcher.forward(request,response);
		     	
		     	session.getTransaction().commit();
				session.close();
			}
			
			else if(action.equals("lista_file")) {
				
				String dateFrom = request.getParameter("dateFrom");
				String dateTo = request.getParameter("dateTo");		
			
				
				List<DocumentoDTO> listaVerbali = GestioneVerbaleBO.getVerbaliPDFAll(session,dateFrom, dateTo);
				
				request.getSession().setAttribute("listaVerbali", listaVerbali);
				request.getSession().setAttribute("dateFrom", dateFrom);
				request.getSession().setAttribute("dateTo", dateTo);

				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/gestioneListaVerbaliPDF.jsp");
		     	dispatcher.forward(request,response);
		     	session.getTransaction().commit();
				session.close();
				
			}
			
			else if(action.equals("scadenzario_val")) {
				
				String dateFrom = request.getParameter("dateFrom");
				String dateTo = request.getParameter("dateTo");	
				String download_file = request.getParameter("download_file");
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				
				Date from = df.parse(dateFrom);
				Date to = df.parse(dateTo);
				
				List<VerbaleDTO> listaVerbali = (List<VerbaleDTO>) request.getSession().getAttribute("listaVerbali");

				List<VerbaleDTO> listaVerbaliValidi = new ArrayList<VerbaleDTO>();
				
				
				for (VerbaleDTO verbaleDTO : listaVerbali) {
					
					if((verbaleDTO.getStato().getId()==5 || verbaleDTO.getStato().getId()==9) && verbaleDTO.getCodiceCategoria().equals("VAL")) {
						listaVerbaliValidi.add(verbaleDTO);
					}
					
				}
				
				
				new CreateScadenzarioVAL(listaVerbaliValidi, dateFrom, dateTo, session);
				
				DateFormat sdf = new SimpleDateFormat("ddMMyyyy");
				
				String path = "";
								
				if(download_file!=null && download_file.equals("1")) {
										
					GestioneVerbaleBO.createZipVerbali(listaVerbaliValidi, sdf.format(from), sdf.format(to), session);
					
					path = Costanti.PATH_ROOT+"\\temp\\fileverbali\\verbali"+sdf.format(from)+ sdf.format(to)+".zip";
									     
				}else {
					
					path = Costanti.PATH_ROOT + "ScadenzarioVAL\\SCADVAL"+sdf.format(from)+ sdf.format(to)+".xlsx";
					
				}
				
								
				 File file = new File(path);
					
					FileInputStream fileIn = new FileInputStream(file);

					ServletOutputStream outp = response.getOutputStream();
					response.setContentType("application/octet-stream");
					if(download_file!=null && download_file.equals("1")) {
						response.setHeader("Content-Disposition","attachment;filename=verbali"+sdf.format(from)+ sdf.format(to)+".zip");
					}else {
						response.setHeader("Content-Disposition","attachment;filename=SCADVAL"+ sdf.format(from)+sdf.format(to)+".xlsx");
					}
					
			
					    byte[] outputByte = new byte[1];
					    
					    while(fileIn.read(outputByte, 0, 1) != -1)
					    {
					    	outp.write(outputByte, 0, 1);
					    }
					    				    
					 
					    fileIn.close();
					    outp.flush();
					    outp.close();
				
				session.close();
				
			}
			else if(action.equals("scadenzario_vie")) {
				
				String dateFrom = request.getParameter("dateFrom");
				String dateTo = request.getParameter("dateTo");	
				
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				
				Date from = df.parse(dateFrom);
				Date to = df.parse(dateTo);
				
				List<VerbaleDTO> listaVerbali = (List<VerbaleDTO>) request.getSession().getAttribute("listaVerbali");

				List<VerbaleDTO> listaVerbaliValidi = new ArrayList<VerbaleDTO>();
				
				
				for (VerbaleDTO verbaleDTO : listaVerbali) {
					
					if(verbaleDTO.getStato().getId()==5 && verbaleDTO.getCodiceCategoria().equals("VIE")) {
						listaVerbaliValidi.add(verbaleDTO);
					}
					
				}
				
				
				new CreateScadenzarioVIE(listaVerbaliValidi, dateFrom, dateTo, session);
				
				DateFormat sdf = new SimpleDateFormat("ddMMyyyy");
				

				String path = Costanti.PATH_ROOT + "ScadenzarioVIE\\SCADVIE"+sdf.format(from)+ sdf.format(to)+".xlsx";
				
				 File file = new File(path);
					
					FileInputStream fileIn = new FileInputStream(file);

					ServletOutputStream outp = response.getOutputStream();
					response.setContentType("application/octet-stream");
					response.setHeader("Content-Disposition","attachment;filename=SCADVIE"+ sdf.format(from)+sdf.format(to)+".xlsx");
			
					    byte[] outputByte = new byte[1];
					    
					    while(fileIn.read(outputByte, 0, 1) != -1)
					    {
					    	outp.write(outputByte, 0, 1);
					    }
					    				    
					 
					    fileIn.close();
					    outp.flush();
					    outp.close();
				
				session.close();
				
			}
			else if(action.equals("accesso_ministero")) {
				
				String dateFrom = request.getParameter("dateFrom");
				String dateTo = request.getParameter("dateTo");	
				
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				
				
				List<VerbaleDTO> listaVerbali =GestioneVerbaleBO.getListaVerbaliMinistero(session, dateFrom, dateTo) ;					

				
				request.getSession().setAttribute("listaVerbali", listaVerbali);
				request.getSession().setAttribute("dateFrom", dateFrom);
				request.getSession().setAttribute("dateTo", dateTo);

				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/gestioneListaVerbaliMinistero.jsp");
		     	dispatcher.forward(request,response);
		     	
		     	session.getTransaction().commit();
				session.close();
				
			}
			
			else if(action.equals("allegati_ministero")) {
				
				ArrayList<AllegatoMinisteroDTO> lista_allegati = GestioneVerbaleBO.getListaAllegatiMinistero(session);
				
				request.getSession().setAttribute("lista_allegati", lista_allegati);

				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/gestioneAllegatiMinistero.jsp");
		     	dispatcher.forward(request,response);
		     	
		     	session.getTransaction().commit();
				session.close();
				
			}
			
			else if(action.equals("allegati_cliente")) {
				
								
				List<SedeDTO> listaSedi = (List<SedeDTO>) request.getSession().getAttribute("listaSedi");
				if(listaSedi==null) {
					listaSedi = GestioneAnagraficaRemotaBO.getListaSedi();	
				}
				CompanyDTO cmp=(CompanyDTO)request.getSession().getAttribute("usrCompany");
				
				String idCompany=""+cmp.getId();
				
				List<ClienteDTO> listaClientiFull = GestioneAnagraficaRemotaBO.getListaClienti(idCompany);		
				
				int id_cliente = 0;
				int id_sede = 0;
				
				if(user.checkRuolo("CLVIE") || user.checkRuolo("CLVAL")) {
					id_cliente = user.getIdCliente();
					id_sede = user.getIdSede();
				}
				
				ArrayList<AllegatoClienteDTO> lista_allegati = GestioneVerbaleBO.getListaAllegatiCliente(id_cliente, id_sede,session);
				
				request.getSession().setAttribute("lista_allegati", lista_allegati);
				request.getSession().setAttribute("listaClienti", listaClientiFull);
				request.getSession().setAttribute("listaSedi", listaSedi);

				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/gestioneAllegatiCliente.jsp");
		     	dispatcher.forward(request,response);
		     	
		     	session.getTransaction().commit();
				session.close();
				
			}
			
			
			else if(action.equals("upload_allegato_cliente")) {
				
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
		        
		        		
				String descrizione = ret.get("descrizione");
				String id_cliente = ret.get("id_cliente");
				String id_sede = ret.get("id_sede");
				
				AllegatoClienteDTO allegato = new AllegatoClienteDTO();
				allegato.setDescrizione(descrizione);
				
				if(filename!=null && !filename.equals("")) {
					allegato.setNome_file(filename);
					//saveFile(fileItem, "DocumentiTest//"+lista_corsi, filename);
				}
				
				
				ClienteDTO cliente = GestioneAnagraficaRemotaBO.getClienteById(id_cliente);
				
				List<SedeDTO> listaSedi = (List<SedeDTO>) request.getSession().getAttribute("listaSedi");
				if(listaSedi==null) {
					listaSedi = GestioneAnagraficaRemotaBO.getListaSedi();	
				}
				
				SedeDTO sede = null;
				if(!id_sede.equals("0")) {
					sede = GestioneAnagraficaRemotaBO.getSedeFromId(listaSedi, Integer.parseInt(id_sede.split("_")[0]), cliente.get__id());
					allegato.setId_sede(sede.get__id());
					allegato.setNome_sede(sede.getDescrizione() +" - "+sede.getIndirizzo()+" - " +sede.getComune()+" ("+sede.getSiglaProvincia()+")");
				}else {
					allegato.setId_sede(0);
					allegato.setNome_sede("Non associate");
				}
				
				
				allegato.setId_cliente(cliente.get__id());
				allegato.setNome_cliente(cliente.getNome());
				
				session.save(allegato);
				
				if(filename!=null && !filename.equals("")) {
					
					saveFile(fileItem, allegato.getId(),true, filename);
				}
				
				JsonObject myObj = new JsonObject();
				PrintWriter  out = response.getWriter();
				myObj.addProperty("success", true);
				myObj.addProperty("messaggio", "Allegato salvato con successo!");
				out.print(myObj);
				session.getTransaction().commit();
				session.close();
				
			}
			else if(action.equals("download_allegato_cliente")) {
				
				String id_allegato = request.getParameter("id_allegato");

				AllegatoClienteDTO allegato = GestioneVerbaleBO.getAllegatoCliente(Integer.parseInt(id_allegato), session);
					
				String path = Costanti.PATH_ROOT+"//AllegatiCliente//"+allegato.getId()+"//"+allegato.getNome_file();
					
				response.setHeader("Content-disposition", "attachment; filename=\""+allegato.getNome_file()+"\"");
				
				downloadFile(path, response.getOutputStream());
					
				response.setContentType("application/pdf");	
				
					
				session.close();
					
			}
			
			else if(action.equals("elimina_allegato_cliente")) {
				
				String id_allegato = request.getParameter("id_allegato");

				AllegatoClienteDTO allegato = GestioneVerbaleBO.getAllegatoCliente(Integer.parseInt(id_allegato), session);
					
				allegato.setDisabilitato(1);
				session.update(allegato);
				
				JsonObject myObj = new JsonObject();
				PrintWriter  out = response.getWriter();
				myObj.addProperty("success", true);
				myObj.addProperty("messaggio", "Allegato eliminato con successo!");
				out.print(myObj);
				session.getTransaction().commit();
				session.close();
				
				
			}
			
			
			else if(action.equals("upload_allegato_ministero")) {
				
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
		        
		        		
				String descrizione = ret.get("descrizione");				
				
				AllegatoMinisteroDTO allegato = new AllegatoMinisteroDTO();
				allegato.setDescrizione(descrizione);
				
							
				if(filename!=null && !filename.equals("")) {
					allegato.setNome_file(filename);
					//saveFile(fileItem, "DocumentiTest//"+lista_corsi, filename);
				}
				
				session.save(allegato);
				
				if(filename!=null && !filename.equals("")) {
					
					saveFile(fileItem, allegato.getId(), false,filename);
				}
				
				JsonObject myObj = new JsonObject();
				PrintWriter  out = response.getWriter();
				myObj.addProperty("success", true);
				myObj.addProperty("messaggio", "Allegato salvato con successo!");
				out.print(myObj);
				session.getTransaction().commit();
				session.close();
				
			}
			else if(action.equals("download_allegato")) {
				
				String id_allegato = request.getParameter("id_allegato");

				AllegatoMinisteroDTO allegato = GestioneVerbaleBO.getAllegatoMinistero(Integer.parseInt(id_allegato), session);
					
				String path = Costanti.PATH_ROOT+"//AllegatiMinistero//"+allegato.getId()+"//"+allegato.getNome_file();
					
				response.setHeader("Content-disposition", "attachment; filename=\""+allegato.getNome_file()+"\"");
				
				downloadFile(path, response.getOutputStream());
					
				response.setContentType("application/pdf");	
				
					
				session.close();
					
			}
			
			else if(action.equals("elimina_allegato_ministero")) {
				
				String id_allegato = request.getParameter("id_allegato");

				AllegatoMinisteroDTO allegato = GestioneVerbaleBO.getAllegatoMinistero(Integer.parseInt(id_allegato), session);
					
				allegato.setDisabilitato(1);
				session.update(allegato);
				
				JsonObject myObj = new JsonObject();
				PrintWriter  out = response.getWriter();
				myObj.addProperty("success", true);
				myObj.addProperty("messaggio", "Allegato eliminato con successo!");
				out.print(myObj);
				session.getTransaction().commit();
				session.close();
				
				
			}
			
			else if(action.equals("allegati_verbale_cliente")) {
				
				String id_verbale = request.getParameter("id_verbale");
				String type = request.getParameter("type");
				
				VerbaleDTO verbale = GestioneVerbaleBO.getVerbale(id_verbale, session);
				
				Set<DocumentoDTO> documenti = new HashSet<DocumentoDTO>();
		
				documenti.addAll(verbale.getDocumentiVerbale());
				if(verbale.getSchedaTecnica()!=null) {
					documenti.addAll(verbale.getSchedaTecnica().getDocumentiVerbale());	
				}
				
				request.getSession().setAttribute("lista_allegati", documenti);
				request.getSession().setAttribute("type", type);
				request.getSession().setAttribute("id_verbale", id_verbale);

				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/gestioneAllegatiClienteTable.jsp");
		     	dispatcher.forward(request,response);
		     	
		     	session.getTransaction().commit();
				session.close();
				
				
			}
				
		}catch(Exception ex){
			session.getTransaction().rollback();
        	session.close();
   		 	ex.printStackTrace();
   		 	request.setAttribute("error",ECIException.callException(ex));
   		 	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/error.jsp");
   		 	dispatcher.forward(request,response);	
		}  
	}
	
	
	 private void saveFile(FileItem item, int id, boolean cliente, String filename) {

		 	String path_folder = Costanti.PATH_ROOT+"//AllegatiMinistero//"+id+"//";
		 	
		 	if(cliente) {
		 		path_folder = Costanti.PATH_ROOT+"//AllegatiCliente//"+id+"//";	
		 	}
		 	 
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
	 
	 private void downloadFile(String path,  ServletOutputStream outp) throws Exception {
		 
		 File file = new File(path);
			
			FileInputStream fileIn = new FileInputStream(file);

	
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

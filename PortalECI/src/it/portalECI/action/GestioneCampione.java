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
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

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

import it.portalECI.DAO.GestioneCampioneDAO;
import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.CampioneDTO;
import it.portalECI.DTO.CompanyDTO;
import it.portalECI.DTO.TipoCampioneDTO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.Exception.ECIException;
import it.portalECI.Util.Costanti;
import it.portalECI.Util.Utility;
import it.portalECI.bo.GestioneCampioneBO;
import it.portalECI.bo.GestioneUtenteBO;

/**
 * Servlet implementation class GestioneCampione1
 */
@WebServlet(name="gestioneCampione" , urlPatterns = { "/gestioneCampione.do" })
public class GestioneCampione extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestioneCampione() {
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
		Session session = SessionFacotryDAO.get().openSession();
		session.beginTransaction();
	
		JsonObject myObj = new JsonObject();
   
		  response.setContentType("application/json");
			
        boolean ajax = false;
        try{
        	
        	
        	 String action=  request.getParameter("action");
        	 CompanyDTO company=(CompanyDTO)request.getSession().getAttribute("usrCompany");
        		UtenteDTO utente = (UtenteDTO)request.getSession().getAttribute("userObj");
        			if(action.equals("lista")) {
        				
        				boolean ck_AM=utente.checkRuolo("AM");
        				boolean ck_ST=utente.checkRuolo("ST");
        				boolean ck_RT=utente.checkRuolo("RT");
        				boolean ck_SRT=utente.checkRuolo("SRT");
        				        				
        				ArrayList<CompanyDTO> lista_company = new ArrayList<CompanyDTO>();
        				lista_company.add(company);
        				
        				ArrayList<CampioneDTO> listaCampioni = null;
        				ArrayList<TipoCampioneDTO> listaTipoCampione= GestioneCampioneDAO.getListaTipoCampione();
        				
        				if(ck_ST==false && ck_AM==false && ck_RT==false && ck_SRT==false) {
        					listaCampioni = new ArrayList<CampioneDTO>();
        							
        					Iterator<CampioneDTO> iterator = utente.getListaCampioni().iterator();
        					while(iterator.hasNext()) {
        						listaCampioni.add(iterator.next());
        					}
        					
        				}else {
        					listaCampioni =  GestioneCampioneDAO.getListaCampioni(null,company.getId());
        				}
        				
        				request.getSession().setAttribute("listaTipoCampione",listaTipoCampione);
        				request.getSession().setAttribute("listaCampioni",listaCampioni);
        				request.getSession().setAttribute("lista_company",lista_company);
        				request.getSession().setAttribute("userObj",utente);
        				
        				session.close();
        	
        				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/listaCampioni.jsp");
        			    dispatcher.forward(request,response);
        		
        				
        			}
        			        			
        			else if(action.equals("nuovo")) {
        				//List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
        				ajax = true;
        				
        				CampioneDTO campione = new CampioneDTO();

        				
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
        			        
        			 // String nome = (String) ret.get("nome");
        	
        		   
        		      String settore = (String) ret.get("settore");
        			  String descrizione = (String) ret.get("descrizione");
        			  String costruttore = (String) ret.get("costruttore");
        			  String modello = (String) ret.get("modello");        			
        			  String freqTaratura = (String) ret.get("freqTaratura");
        			  String statoCampione = (String) ret.get("statoCampione");
        			  String dataVerifica = (String) ret.get("dataVerifica");
        			  String numeroCerificato  = (String) ret.get("numeroCerificato");        			 
        			  String tipoCampione = (String) ret.get("tipoCampione");
        			  String codice  = (String) ret.get("codice");
        			  String matricola = (String) ret.get("matricola");        	
        			  String utilizzatore = (String) ret.get("utilizzatore");    
        			  String distributore = (String) ret.get("distributore");
        			  String data_acquisto = (String) ret.get("data_acquisto");
        			  String campo_accettabilita = (String)ret.get("campo_accettabilita");
        			  String ente_certificatore = (String)ret.get("ente_certificatore");        			  
        			  String data_messa_in_servizio = (String) ret.get("data_messa_in_servizio");
        			  String campo_misura = (String)ret.get("campo_misura");
        			  String unita_formato = (String)ret.get("unita_formato");
        			  String frequenza_manutenzione = (String)ret.get("frequenza_manutenzione");
        			  String condizioni_utilizzo = (String)ret.get("condizioni_utilizzo");
        			  String note_attivita_taratura = (String)ret.get("note_attivita_taratura");
        			  String ubicazione = (String)ret.get("ubicazione");
        			  String id_strumento = (String)ret.get("strumento");
        			  String descrizione_manutenzione = (String)ret.get("descrizione_manutenzione");
        			
        			  String slash = (String)ret.get("slash");
        			  String proprietario = (String)ret.get("proprietario");
        			  
        			  
        			  String attivita_di_taratura = (String) ret.get("attivita_di_taratura_text");
        				 
        			 
        				//campione.setNome(nome);
        				campione.setMatricola(matricola);
        	 			campione.setDescrizione(descrizione);
        				campione.setCostruttore(costruttore);
        				campione.setModello(modello);
        				campione.setCondizioni_utilizzo(condizioni_utilizzo);
        				if(freqTaratura!=null && !freqTaratura.equals("")) {
        					campione.setFreqTaraturaMesi(Integer.parseInt(freqTaratura));	
        				}
        				
        				campione.setStatoCampione(statoCampione);
        				
        				if(settore!=null && !settore.equals("")) {
        					campione.setSettore(Integer.parseInt(settore));
        				}
        				
        		 
        				campione.setAttivita_di_taratura(attivita_di_taratura);
        				campione.setCampo_accettabilita(campo_accettabilita);
        				campione.setDistributore(distributore);
        				
        				campione.setCampo_misura(campo_misura);
        				campione.setUnita_formato(unita_formato);
        				
        				if(id_strumento!=null && !id_strumento.equals("")) {
        					campione.setId_strumento(Integer.parseInt(id_strumento));
        				}
        				if(frequenza_manutenzione!=null && !frequenza_manutenzione.equals("")) {
        					campione.setFrequenza_manutenzione(Integer.parseInt(frequenza_manutenzione));	
        				}
        				

        				campione.setNote_attivita(note_attivita_taratura);
        				campione.setUbicazione(ubicazione);
        				
        				campione.setDescrizione_manutenzione(descrizione_manutenzione);
        	
        				
        				DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN);
        				
        				if(data_acquisto !=null && !data_acquisto.equals("")) {
        				Date dataAcquisto = (Date) format.parse(data_acquisto);
        				campione.setData_acquisto(dataAcquisto);
        				}else {
        					campione.setData_acquisto(null);
        				}
        				
        				if(data_messa_in_servizio !=null && !data_messa_in_servizio.equals("")) {
        					Date d = (Date) format.parse(data_messa_in_servizio);
        					campione.setData_messa_in_servizio(d);
        					}else {
        						campione.setData_messa_in_servizio(null);
        					}
        				
        				if(dataVerifica !=null && !dataVerifica.equals("")) {
        					Date dataVerificaDate = (Date) format.parse(dataVerifica);
        		 			campione.setDataVerifica(dataVerificaDate);
        		 			Date dataScadenzaCampione=null;
        		 			
        		 			Calendar cal = Calendar.getInstance();
        		 			cal.setTime(dataVerificaDate);
        		 			cal.add(Calendar.MONTH, Integer.parseInt(freqTaratura));
        		
        		 			dataScadenzaCampione=cal.getTime();
        		 			
        		 			campione.setDataScadenza(dataScadenzaCampione);
        				}
        				
        				campione.setNumeroCertificato(numeroCerificato);
        	
        			
        					campione.setUtilizzatore(utilizzatore);
        					campione.setTipo_campione(new TipoCampioneDTO(Integer.parseInt(tipoCampione),""));
        					if(slash!=null && !slash.equals("")) {
        						campione.setCodice(codice+"/"+slash);						
        					}else {
        						campione.setCodice(codice);	

        					}
        					
        					
        					campione.setCompany((CompanyDTO) request.getSession().getAttribute("usrCompany"));	
        					campione.setProprietario(proprietario);
        					
        					
        					campione.setCompany_utilizzatore((CompanyDTO) request.getSession().getAttribute("usrCompany"));	
        					
        					campione.setPrenotabile("N");

        				int success = GestioneCampioneBO.saveCampione(campione, action, fileItem,ente_certificatore, session);
        	
        					if(success==0)
        					{
        						myObj.addProperty("success", true);
        						myObj.addProperty("messaggio","Salvato con Successo");
        						session.getTransaction().commit();
        						session.close();
        					
        					}
        					if(success==1)
        					{
        						
        						myObj.addProperty("success", false);
        						myObj.addProperty("messaggio","Errore Salvataggio");
        						
        						session.getTransaction().rollback();
        				 		session.close();
        				 		
        					} 
        					if(success==2)
        					{
        						
        						myObj.addProperty("success", false);
        						myObj.addProperty("messaggio","Caricare solo file in formato pdf");
        						
        						session.getTransaction().rollback();
        				 		session.close();
        				 		
        					} 
        					PrintWriter out = response.getWriter();
        					
        					
        					out.println(myObj.toString());
        				}
        			else if(action.equals("modifica")) {
        				
        				ajax = true;
        				
        				List<FileItem> items = null;
        		        if (request.getContentType() != null && request.getContentType().toLowerCase().indexOf("multipart/form-data") > -1 ) {

        		        		items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
        		        	}
        				
        				CampioneDTO campione = GestioneCampioneDAO.getCampioneFromId( request.getParameter("id"));
        				
        				
        				FileItem fileItem = null;
        				
        		        Hashtable<String,String> ret = new Hashtable<String,String>();
        		      
        		        for (FileItem item : items) {
        	            	 if (!item.isFormField()) {
        	            		
        	                     fileItem = item;
        	                     
        	            	 }else
        	            	 {
        	                      ret.put(item.getFieldName(), new String (item.getString().getBytes ("iso-8859-1"), "UTF-8"));
        	
        	            	 }
        	            	
        	            
        	            }
        			        
        			 // String nome = (String) ret.get("nome_mod");
        	
        		      String codice = (String) ret.get("codice_mod");
        		      String settore = (String) ret.get("settore_mod");
        			  String descrizione = (String) ret.get("descrizione_mod");
        			  String costruttore = (String) ret.get("costruttore_mod");
        			  String modello = (String) ret.get("modello_mod");
        			  String freqTaratura = (String) ret.get("freqTaratura_mod");
        			  String statoCampione = (String) ret.get("statoCampione_mod");
        			  String dataVerifica = (String) ret.get("dataVerifica_mod");
        			  String numeroCerificato  = (String) ret.get("numeroCerificato_mod");        			  		  
        			  String matricola = (String) ret.get("matricola_mod");			         			  
        			  String distributore = (String) ret.get("distributore_mod");
        			  String data_acquisto = (String) ret.get("data_acquisto_mod");
        			  String data_messa_in_servizio = (String) ret.get("data_messa_in_servizio_mod");
        			  String campo_accettabilita = (String)ret.get("campo_accettabilita_mod");
        			  String ente_certificatore = (String)ret.get("ente_certificatore_mod");		
        			  String campo_misura = (String)ret.get("campo_misura_mod");
        			  String unita_formato = (String)ret.get("unita_formato_mod");
        			  String frequenza_manutenzione = (String)ret.get("frequenza_manutenzione_mod");
        			  String note_attivita_taratura = (String)ret.get("note_attivita_taratura_mod");
        			  String ubicazione = (String)ret.get("ubicazione_mod");
        			  String id_strumento = (String)ret.get("strumento");
        			  String descrizione_manutenzione = (String)ret.get("descrizione_manutenzione_mod");
        			  String attivita_di_taratura = (String) ret.get("attivita_taratura_text_mod");
        			  String tipo_campione = (String) ret.get("tipoCampione_mod");
        			  String proprietario = (String) ret.get("proprietario_mod");
        			  String condizioni_utilizzo = (String)ret.get("condizioni_utilizzo_mod");
        			  String verificatori_associa_mod = (String)ret.get("verificatori_associa_mod");
        			  String verificatori_dissocia_mod = (String)ret.get("verificatori_dissocia_mod");
        			  
        				campione.setCodice(codice);
        				campione.setMatricola(matricola);
        	 			campione.setDescrizione(descrizione);
        				campione.setCostruttore(costruttore);
        				campione.setModello(modello);
        				
        				campione.setCondizioni_utilizzo(condizioni_utilizzo);
        				
        				if(freqTaratura!=null && !freqTaratura.equals("")) {
        					campione.setFreqTaraturaMesi(Integer.parseInt(freqTaratura));	
        				}else {
        					campione.setFreqTaraturaMesi(0);
        				}
        				
        				if(settore!=null && !settore.equals("")) {
        					campione.setSettore(Integer.parseInt(settore));
        				}
        				
        				campione.setStatoCampione(statoCampione);
        		 
        				campione.setAttivita_di_taratura(attivita_di_taratura);
        				campione.setCampo_accettabilita(campo_accettabilita);
        				campione.setDistributore(distributore);
        				
        				campione.setCampo_misura(campo_misura);
        				campione.setUnita_formato(unita_formato);
        				campione.setTipo_campione(new TipoCampioneDTO(Integer.parseInt(tipo_campione), ""));
        				
        				if(id_strumento!=null && !id_strumento.equals("")) {
        					campione.setId_strumento(Integer.parseInt(id_strumento));
        				}
        				if(frequenza_manutenzione!=null && !frequenza_manutenzione.equals("")) {
        					campione.setFrequenza_manutenzione(Integer.parseInt(frequenza_manutenzione));	
        				}else {
        					campione.setFrequenza_manutenzione(0);
        				}
        				
        				campione.setProprietario(proprietario);
        				campione.setNote_attivita(note_attivita_taratura);
        				campione.setUbicazione(ubicazione);
        				
        				campione.setDescrizione_manutenzione(descrizione_manutenzione);
        			
        				DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN);
        				
        				if(data_acquisto !=null && !data_acquisto.equals("")) {
        				Date dataAcquisto = (Date) format.parse(data_acquisto);
        				campione.setData_acquisto(dataAcquisto);
        				}else {
        					campione.setData_acquisto(null);
        				}
        				
        				if(data_messa_in_servizio !=null && !data_messa_in_servizio.equals("")) {
        					Date d = (Date) format.parse(data_messa_in_servizio);
        					campione.setData_messa_in_servizio(d);
        					}else {
        						campione.setData_messa_in_servizio(null);
        					}
        				
        				if(dataVerifica!=null && !dataVerifica.equals("")) {
        					Date dataVerificaDate = (Date) format.parse(dataVerifica);
        		 			campione.setDataVerifica(dataVerificaDate);
        		 			Date dataScadenzaCampione=null;
        		 			
        		 			Calendar cal = Calendar.getInstance();
        		 			cal.setTime(dataVerificaDate);
        		 			cal.add(Calendar.MONTH, Integer.parseInt(freqTaratura));
        		
        		 			dataScadenzaCampione=cal.getTime();
        		 			
        		 			campione.setDataScadenza(dataScadenzaCampione);
        				}else {
        					campione.setDataVerifica(null);
        					campione.setDataScadenza(null);
        				}
        	 				 			
        	 				campione.setNumeroCertificato(numeroCerificato);
        	 				
        	 				int success = GestioneCampioneBO.saveCampione(campione, action, fileItem,ente_certificatore, session);
        	 				
        	 				if(verificatori_associa_mod !=null && !verificatori_associa_mod.equals("")) {
        						
        						for (String id : verificatori_associa_mod.split(";")) {
        							UtenteDTO verificatore = GestioneUtenteBO.getUtenteById(id, session);
        						
        							campione.getListaVerificatori().add(verificatore);	
        							session.update(campione);
        						}
        						
        					}
        					
        					if(verificatori_dissocia_mod !=null && !verificatori_dissocia_mod.equals("")) {
        						
        						for (String id : verificatori_dissocia_mod.split(";")) {
        							UtenteDTO verificatore = GestioneUtenteBO.getUtenteById(id, session);
        							campione.getListaVerificatori().remove(verificatore);
        							session.update(campione);
        						}
        						
        					}
        					
        	
        					if(success==0)
        					{
        						myObj.addProperty("success", true);
        						myObj.addProperty("messaggio","Salvato con Successo");
        						session.getTransaction().commit();
        						session.close();
        					
        					}
        					if(success==1)
        					{
        						
        						myObj.addProperty("success", false);
        						myObj.addProperty("messaggio","Errore Salvataggio");
        						
        						session.getTransaction().rollback();
        				 		session.close();
        				 		
        					} 
        					if(success==2)
        					{
        						
        						myObj.addProperty("success", false);
        						myObj.addProperty("messaggio","Caricare solo file in formato pdf");
        						
        						session.getTransaction().rollback();
        				 		session.close();
        				 		
        					} 
        					PrintWriter out = response.getWriter();
        					
        					out.println(myObj.toString());
        				
        			}
        				
        		
        			else if(action.equals("download_certificato"))
        			{
        			
        			String idCampione= request.getParameter("idC");
        			 	
        			 	CampioneDTO campione= GestioneCampioneDAO.getCampioneFromId(idCampione);
        			   
        			 	
        			 	if(campione!=null && campione.getCertificatoCorrente(campione.getListaCertificatiCampione())!=null)
        			 	{
        				
        			     File d = new File(Costanti.PATH_ROOT+"//Campioni//"+campione.getId()+"/"+campione.getCertificatoCorrente(campione.getListaCertificatiCampione()).getFilename());
        				 
        				 FileInputStream fileIn = new FileInputStream(d);
        				 
        				 response.setContentType("application/octet-stream");
        								 
        				 response.setHeader("Content-Disposition","attachment;filename="+campione.getCertificatoCorrente(campione.getListaCertificatiCampione()).getFilename());
        				 
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
        	
        	
        }  
        catch(Exception ex)
    	{
        	
			if(ajax) {
				PrintWriter out = response.getWriter();
				ex.printStackTrace();
	        	session.getTransaction().rollback();
	        	session.close();
	        	request.getSession().setAttribute("exception", ex);
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

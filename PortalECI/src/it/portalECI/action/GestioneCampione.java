package it.portalECI.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

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

import it.portalECI.DAO.GestioneCampioneDAO;
import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.CampioneDTO;
import it.portalECI.DTO.CompanyDTO;
import it.portalECI.DTO.TipoCampioneDTO;
import it.portalECI.Exception.ECIException;
import it.portalECI.Util.Utility;
import it.portalECI.bo.GestioneCampioneBO;
import it.portalECI.bo.GestioneCompanyBO;

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
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		if(Utility.validateSession(request,response,getServletContext()))return;
		Session session = SessionFacotryDAO.get().openSession();
		session.beginTransaction();
		PrintWriter out = response.getWriter();
		JsonObject myObj = new JsonObject();
   
		  response.setContentType("application/json");
			
        
        try{
        	
        	
        	 String action=  request.getParameter("action");
        	 CompanyDTO company=(CompanyDTO)request.getSession().getAttribute("usrCompany");

        			if(action.equals("lista")) {
        				
        				ArrayList<CompanyDTO> lista_company = new ArrayList<CompanyDTO>();
        				lista_company.add(company);
        				
        				ArrayList<CampioneDTO> listaCampioni = GestioneCampioneDAO.getListaCampioni(null,company.getId());
        				ArrayList<TipoCampioneDTO> listaTipoCampione= GestioneCampioneDAO.getListaTipoCampione();
        				request.getSession().setAttribute("listaTipoCampione",listaTipoCampione);
        				request.getSession().setAttribute("listaCampioni",listaCampioni);
        				request.getSession().setAttribute("lista_company",lista_company);
        				
        				session.close();
        	
        				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/listaCampioni.jsp");
        			    dispatcher.forward(request,response);
        		
        				
        			}
        			        			
        			else if(action.equals("nuovo")) {
        				//List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
        				
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
        			        
        			  String nome = (String) ret.get("nome");
        	
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
        			
        			  String note_attivita_taratura = (String)ret.get("note_attivita_taratura");
        			  String ubicazione = (String)ret.get("ubicazione");
        			  String id_strumento = (String)ret.get("strumento");
        			  String descrizione_manutenzione = (String)ret.get("descrizione_manutenzione");
        			
        			  String slash = (String)ret.get("slash");
        			  String proprietario = (String)ret.get("proprietario");
        			  
        			  
        			  String attivita_di_taratura = (String) ret.get("attivita_di_taratura_text");
        				 
        			 
        				campione.setNome(nome);
        				campione.setMatricola(matricola);
        	 			campione.setDescrizione(descrizione);
        				campione.setCostruttore(costruttore);
        				campione.setModello(modello);

        				if(freqTaratura!=null && !freqTaratura.equals("")) {
        					campione.setFreqTaraturaMesi(Integer.parseInt(freqTaratura));	
        				}
        				
        				campione.setStatoCampione(statoCampione);
        				
        		 
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
        					
        					if(proprietario!=null && !proprietario.equals("")) {
        						CompanyDTO cmp = GestioneCompanyBO.getCompanyById(proprietario, session);
        						campione.setCompany(cmp);
        					}else {
        						campione.setCompany((CompanyDTO) request.getSession().getAttribute("usrCompany"));	
        					}
        					
        					if(utilizzatore!=null && !utilizzatore.equals("")) {
        						CompanyDTO cmp = GestioneCompanyBO.getCompanyById(proprietario, session);
        						campione.setCompany_utilizzatore(cmp);
        					}else {
        						campione.setCompany_utilizzatore((CompanyDTO) request.getSession().getAttribute("usrCompany"));	
        					}
        					
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
        					
        					
        					
        					out.println(myObj.toString());
        				}
        			else if(action.equals("modifica")) {
        				
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
        			        
        			  String nome = (String) ret.get("nome_mod");
        	
        			  String descrizione = (String) ret.get("descrizione_mod");
        			  String costruttore = (String) ret.get("costruttore_mod");
        			  String modello = (String) ret.get("modello_mod");
        			  String interpolazione = (String) ret.get("interpolazione_mod");
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
        			  String frequenza_verifica_intermedia = (String)ret.get("frequenza_verifica_intermedia_mod");
        			  String note_attivita_taratura = (String)ret.get("note_attivita_taratura_mod");
        			  String ubicazione = (String)ret.get("ubicazione_mod");
        			  String id_strumento = (String)ret.get("strumento");
        			  String descrizione_manutenzione = (String)ret.get("descrizione_manutenzione_mod");
        			  String descrizione_verifica_intermedia = (String)ret.get("descrizione_verifica_intermedia_mod");
        			  String attivita_di_taratura = (String) ret.get("attivita_taratura_text_mod");
        			  String tipo_campione = (String) ret.get("tipoCampione_mod");
        			  
        			 
        				campione.setNome(nome);
        				campione.setMatricola(matricola);
        	 			campione.setDescrizione(descrizione);
        				campione.setCostruttore(costruttore);
        				campione.setModello(modello);
        				
        			
        				if(freqTaratura!=null && !freqTaratura.equals("")) {
        					campione.setFreqTaraturaMesi(Integer.parseInt(freqTaratura));	
        				}else {
        					campione.setFreqTaraturaMesi(0);
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
        					
        					
        					out.println(myObj.toString());
        				
        			}
        				
        		
        			
        	
        	
        }  
        catch(Exception ex)
    	{
        	ex.printStackTrace();
        	session.getTransaction().rollback();
        	session.close();
        	request.getSession().setAttribute("exception", ex);
        	//myObj.addProperty("success", false);
        	//myObj.addProperty("messaggio", STIException.callException(ex).toString());
        	myObj = ECIException.callExceptionJsonObject(ex);
        	out.println(myObj.toString());
        	}  
        	
	}

}
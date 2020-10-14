package it.portalECI.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.AttrezzaturaDTO;
import it.portalECI.Exception.ECIException;
import it.portalECI.Util.Utility;
import it.portalECI.bo.GestioneAttrezzatureBO;
import it.portalECI.bo.GestioneInterventoBO;
import it.portalECI.bo.GestioneVerbaleBO;

import it.portalECI.DTO.ClienteDTO;
import it.portalECI.DTO.CompanyDTO;
import it.portalECI.DTO.DescrizioneGruppoAttrezzaturaDTO;
import it.portalECI.DTO.InterventoDTO;
import it.portalECI.DTO.SedeDTO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.DTO.VerbaleDTO;
import it.portalECI.bo.GestioneAnagraficaRemotaBO;



/**
 * Servlet implementation class ListaAttrezzature
 */
@WebServlet(name="listaAttrezzature" , urlPatterns = { "/listaAttrezzature.do" })

public class ListaAttrezzature extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListaAttrezzature() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
			if(action==null || action.equals("")) {
				
				CompanyDTO cmp=(CompanyDTO)request.getSession().getAttribute("usrCompany");
				
				String idCompany=""+cmp.getId();
				
				List<ClienteDTO> listaClientiFull = GestioneAnagraficaRemotaBO.getListaClienti(idCompany);		
				List<SedeDTO> listaSediFull = GestioneAnagraficaRemotaBO.getListaSedi();
				
				if(!utente.checkRuolo("AM") && !utente.checkRuolo("ST") ) {
					
					List<ClienteDTO> listaClienti = new ArrayList<ClienteDTO>();	
					
					List<SedeDTO> listaSedi = new ArrayList<SedeDTO>();
					
					//ArrayList<Integer> lista_id_clienti = GestioneAttrezzatureBO.getClientiSediTecnico(session, utente.getId(), 0);
					ArrayList<Object[]> lista_id_clienti_sedi = GestioneAttrezzatureBO.getClientiSediTecnico(session, utente.getId(), 0);
					ArrayList<Integer> lista_id_clienti = new ArrayList<Integer>();
					ArrayList<Integer> lista_id_sedi = new ArrayList<Integer>();
					
					
					for (Object[] integers : lista_id_clienti_sedi) {
						lista_id_clienti.add((Integer)integers[0]);
						lista_id_sedi.add((Integer)integers[1]);
					}
					
					for (ClienteDTO cliente : listaClientiFull) {						
						if(lista_id_clienti.contains(cliente.get__id())) {
							listaClienti.add(cliente);
						}
					}
					
					for (SedeDTO sede : listaSediFull) {
						
						if(lista_id_sedi.contains(sede.get__id())) {
							listaSedi.add(sede);
						}
						
					}
					
					 Gson gson = new Gson(); 
			

				    JsonElement obj = gson.toJsonTree(lista_id_clienti_sedi);
					
				    request.getSession().setAttribute("json",obj);
					request.getSession().setAttribute("listaClienti",listaClienti);
					request.getSession().setAttribute("lista_id_clienti",lista_id_clienti);
					request.getSession().setAttribute("lista_id_sedi",lista_id_sedi);	
					request.getSession().setAttribute("listaSedi",listaSedi);						
					
				}else {
					
					request.getSession().setAttribute("listaClienti",listaClientiFull);
					request.getSession().setAttribute("listaSedi",listaSediFull);	
				}								
				
//				
//								
//
//					File file = new File("C:\\Users\\antonio.dicivita\\Desktop\\ELENCO ATTREZZATURE_x Raffaele.xlsm");
//					
//					FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file  
//					//creating Workbook instance that refers to .xlsx file  
//					XSSFWorkbook wb = new XSSFWorkbook(fis);   
//					XSSFSheet sheet = wb.getSheetAt(0);     //creating a Sheet object to retrieve object  
//					Iterator<Row> itr = sheet.iterator();    //iterating over excel file  
//					
//					List<SedeDTO> lista_sedi = GestioneAnagraficaRemotaBO.getListaSedi();
//
//					while (itr.hasNext())                 
//					{  
//						Row row = itr.next();  
//						Iterator<Cell> cellIterator = row.cellIterator();   //iterating over each column  
//						
//						if(row.getRowNum()>=2) {
//							
//	
//							
//							String matricola = row.getCell(0).getStringCellValue();
//										AttrezzaturaDTO attrezzatura =  GestioneAttrezzatureBO.getAttrezzaturaFromMatricola(matricola, session);
//										
//										
//										String n_fabbrica ="";
//										if(row.getCell(1).getCellType()==Cell.CELL_TYPE_STRING)
//										{
//											n_fabbrica = row.getCell(1).getStringCellValue();
//										}else {
//											n_fabbrica = (int)row.getCell(1).getNumericCellValue() +"";
//										}
//												
//										String gruppo = row.getCell(2).getStringCellValue();
//										String descrizione = row.getCell(3).getStringCellValue();
//										
//										
//										int id_cliente = 0;
//										int id_sede = 0;
//										
//										if(row.getCell(5).getCellType()==Cell.CELL_TYPE_STRING)
//										{
//											id_cliente = Integer.parseInt(row.getCell(5).getStringCellValue());
//										}else {
//											id_cliente = (int) row.getCell(5).getNumericCellValue();
//										}
//										
//										if(row.getCell(6).getCellType()==Cell.CELL_TYPE_STRING)
//										{
//											id_sede = Integer.parseInt(row.getCell(6).getStringCellValue());
//										}else {
//											id_sede = (int) row.getCell(6).getNumericCellValue();
//										}
//										
//										int anno_costruzione = 0;
//										if(row.getCell(7).getCellType()==Cell.CELL_TYPE_STRING)
//										{
//											if(!row.getCell(7).getStringCellValue().equals("N.D.")) {
//											anno_costruzione = Integer.parseInt(row.getCell(7).getStringCellValue());
//											}
//										}else {
//											anno_costruzione = (int) row.getCell(7).getNumericCellValue();
//										}
//										
//										
//										String fabbricante = row.getCell(8).getStringCellValue();
//										String modello ="";
//										
//										if(row.getCell(9).getCellType()==Cell.CELL_TYPE_STRING)
//										{
//											modello = row.getCell(9).getStringCellValue();
//										}else {
//											modello = ""+row.getCell(9).getNumericCellValue();
//										}
//										
//										
//										String settore_impiego = row.getCell(10).getStringCellValue();
//										String tipo_attr = row.getCell(11).getStringCellValue();
//										String tipo_attr_gvr = row.getCell(12).getStringCellValue();
//										int id_specifica = (int) row.getCell(13).getNumericCellValue();
//										String sogg_messa_servizio_gvr = row.getCell(14).getStringCellValue();
//										String marcatura = row.getCell(16).getStringCellValue();
//										String n_id_on  = row.getCell(17).getStringCellValue();
//										String data_ventennale  = row.getCell(20).getStringCellValue();
//										String note_tecniche = row.getCell(18).getStringCellValue();
//										String note_generiche = row.getCell(19).getStringCellValue();
//										
//										Date data_verifica_funz = null;
//										Date data_pros_verifica_funz = null;
//										Date data_verifica_integrita = null;
//										Date data_pros_verifica_integrita = null;
//										Date data_verifica_interna = null;
//										Date data_pros_verifica_interna = null;
//										SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
//										if(row.getCell(24).getCellType()==Cell.CELL_TYPE_STRING)
//										{
//											if(row.getCell(24).getStringCellValue().length()==10) {
//												data_verifica_funz = df.parse(row.getCell(24).getStringCellValue());
//											}
//											 
//										}else if(row.getCell(24).getCellType()!=Cell.CELL_TYPE_ERROR){											
//											 data_verifica_funz = row.getCell(24).getDateCellValue();
//										}
//										 
//										  
//
//										 
//										 if(row.getCell(25).getCellType()==Cell.CELL_TYPE_STRING)
//											{
//												if(row.getCell(25).getStringCellValue().length()==10) {
//													data_pros_verifica_funz = df.parse(row.getCell(25).getStringCellValue());
//												}
//												 
//											}else if(row.getCell(25).getCellType()!=Cell.CELL_TYPE_ERROR){	
//												data_pros_verifica_funz = row.getCell(25).getDateCellValue();
//											}
//										 
//										 
//										
//										 if(row.getCell(29).getCellType()==Cell.CELL_TYPE_STRING)
//											{
//												if(row.getCell(29).getStringCellValue().length()==10) {
//													data_verifica_integrita = df.parse(row.getCell(29).getStringCellValue());
//												}
//												 
//											}else if(row.getCell(29).getCellType()!=Cell.CELL_TYPE_ERROR){	
//												data_verifica_integrita = row.getCell(29).getDateCellValue();
//											}
//										 
//																			 
//										 
//										 if(row.getCell(30).getCellType()==Cell.CELL_TYPE_STRING)
//											{
//												if(row.getCell(30).getStringCellValue().length()==10) {
//													data_pros_verifica_integrita = df.parse(row.getCell(30).getStringCellValue());
//												}
//												 
//											}else if(row.getCell(30).getCellType()!=Cell.CELL_TYPE_ERROR){	
//												data_pros_verifica_integrita = row.getCell(30).getDateCellValue();
//											}
//										
//																		 
//										 
//										 if(row.getCell(34).getCellType()==Cell.CELL_TYPE_STRING)
//											{
//												if(row.getCell(34).getStringCellValue().length()==10) {
//													data_verifica_interna = df.parse(row.getCell(34).getStringCellValue());
//												}
//												 
//											}else if(row.getCell(34).getCellType()!=Cell.CELL_TYPE_ERROR){	
//												data_verifica_interna = row.getCell(34).getDateCellValue();
//											}
//										 
//										 						
//										 
//										 if(row.getCell(35).getCellType()==Cell.CELL_TYPE_STRING)
//											{
//												if(row.getCell(35).getStringCellValue().length()==10) {
//													data_pros_verifica_interna = df.parse(row.getCell(35).getStringCellValue());
//												}
//												 
//											}else if(row.getCell(35).getCellType()!=Cell.CELL_TYPE_ERROR){	
//												data_pros_verifica_interna = row.getCell(35).getDateCellValue();
//											}
//										
//										attrezzatura.setMatricola_inail(matricola);
//										attrezzatura.setNumero_fabbrica(n_fabbrica);
//										attrezzatura.setTipo_attivita(gruppo);
//										attrezzatura.setDescrizione(descrizione);
//										
//										attrezzatura.setId_cliente(id_cliente);
//										attrezzatura.setId_sede(id_sede);
//										
//										ClienteDTO cl = GestioneAnagraficaRemotaBO.getClienteById(""+id_cliente);
//										
//										attrezzatura.setNome_cliente(cl.getNome());
//										
//									
//										if(id_sede!=0) {
//											SedeDTO sd = GestioneAnagraficaRemotaBO.getSedeFromId(lista_sedi, id_sede, id_cliente);
//											attrezzatura.setNome_sede(sd.getDescrizione()+" - "+sd.getIndirizzo()+" - "+sd.getComune()+"("+sd.getSiglaProvincia()+")");
//										}
//										
//										attrezzatura.setAnno_costruzione(anno_costruzione);
//										attrezzatura.setFabbricante(fabbricante);
//										attrezzatura.setSettore_impiego(settore_impiego);
//										attrezzatura.setModello(modello);
//										attrezzatura.setNote_tecniche(note_tecniche);
//										attrezzatura.setNote_generiche(note_generiche);
//										attrezzatura.setData_verifica_funzionamento(data_verifica_funz);
//										attrezzatura.setData_prossima_verifica_funzionamento(data_pros_verifica_funz);
//										attrezzatura.setData_verifica_integrita(data_verifica_integrita);
//										attrezzatura.setData_prossima_verifica_integrita(data_pros_verifica_integrita);
//										attrezzatura.setData_verifica_interna(data_verifica_interna);
//										attrezzatura.setData_prossima_verifica_interna(data_pros_verifica_interna);
//										
//											
//											attrezzatura.setTipo_attrezzatura(tipo_attr);
//											if(!tipo_attr_gvr.equals("")) {
//												attrezzatura.setTipo_attrezzatura_GVR(tipo_attr_gvr);
//											}
//											
//											attrezzatura.setID_specifica(id_specifica+"");
//											
//											if(!sogg_messa_servizio_gvr.equals("")) {
//												attrezzatura.setSogg_messa_serv_GVR(sogg_messa_servizio_gvr);
//											}
//											if(!marcatura.equals("N.A.")) {
//												attrezzatura.setMarcatura(marcatura);
//											}
//											if(!n_id_on.equals("N.A.")) {
//												attrezzatura.setN_id_on(n_id_on);
//											}
//											if(!data_ventennale.equals("")&&!data_ventennale.equals("N.A.")&& data_ventennale.length()==10) {
//												
//												Date d = df.parse(data_ventennale);
//												if(d!=null) {
//													attrezzatura.setData_scadenza_ventennale(d);
//												}
//												
//											}
//											
//											
//											session.update(attrezzatura);
//										//	System.out.println("riga "+row.getRowNum()+" - attrezzatura: "+matricola +" - n_id_on: "+n_id_on+" - data_ver funz: "+data_verifica_funz);
//										
//										
//
//									//}
//							//}
//						}else if(row.getRowNum()==931) {
//							break;
//						}
//					}
									
									

				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
					session.getTransaction().commit();
				session.close();
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/listaAttrezzature.jsp");
		     	dispatcher.forward(request,response);
			}
			else if(action.equals("cliente_sede")) {
				
				String id_cliente = request.getParameter("id_cliente");
				String id_sede = request.getParameter("id_sede");				
				
				
				ArrayList<AttrezzaturaDTO> lista_attrezzature = null;
				
				if(id_cliente.equals("0")) {
					
					ArrayList<AttrezzaturaDTO> lista_attrezzatureTutte = GestioneAttrezzatureBO.getlistaAttrezzature(session);
					
					if(!utente.checkRuolo("AM") && !utente.checkRuolo("ST")) {
						ArrayList<Integer> lista_id_clienti =  (ArrayList<Integer>) request.getSession().getAttribute("lista_id_clienti");	
						ArrayList<Integer> lista_id_sedi =  (ArrayList<Integer>) request.getSession().getAttribute("lista_id_sedi");	
						
						lista_attrezzature = new ArrayList<AttrezzaturaDTO>();
						
						if(lista_id_clienti!=null && lista_id_sedi!=null) {
							for (AttrezzaturaDTO attrezzatura : lista_attrezzatureTutte) {
								if(lista_id_clienti.contains(attrezzatura.getId_cliente()) && lista_id_sedi.contains(attrezzatura.getId_sede())) {
									lista_attrezzature.add(attrezzatura);
								}
							}
						}
						
					}else {
						
						lista_attrezzature = lista_attrezzatureTutte;
						
					}
					
				}
				else {
					
					lista_attrezzature = GestioneAttrezzatureBO.getlistaAttrezzatureSede(Integer.parseInt(id_cliente), Integer.parseInt(id_sede.split("_")[0]),true,session);
					
				}
				
				
				ArrayList<DescrizioneGruppoAttrezzaturaDTO> lista_descrizioni_gruppi = GestioneAttrezzatureBO.getListaDescrizioniGruppi(session);
				
				 request.getSession().setAttribute("lista_attrezzature",lista_attrezzature);
				 request.getSession().setAttribute("lista_descrizioni_gruppi",lista_descrizioni_gruppi);
				
				 session.close();
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/listaAttrezzatureSede.jsp");
		     	dispatcher.forward(request,response);
			}
			else if(action.equals("nuovo")) {
				
				ajax=true;
				
				String id_cliente = request.getParameter("id_cliente");
				String id_sede = request.getParameter("id_sede");
				String matricola_inail = request.getParameter("matricola_inail");
				String numero_fabbrica = request.getParameter("numero_fabbrica");
				String descrizione = request.getParameter("descrizione");
				String tipo_attivita = request.getParameter("tipo_attivita");
				String data_ver_funz = request.getParameter("data_ver_funz");
				String data_pross_ver_funz = request.getParameter("data_pross_ver_funz");
				String data_ver_integrita = request.getParameter("data_ver_integrita");
				String data_pross_ver_integrita = request.getParameter("data_pross_ver_integrita");
				String data_ver_interna = request.getParameter("data_ver_interna");
				String data_pross_ver_interna = request.getParameter("data_pross_ver_interna");				
				String anno_costruzione = request.getParameter("anno_costruzione");
				String fabbricante = request.getParameter("fabbricante");
				String modello = request.getParameter("modello");
				String settore_impiego = request.getParameter("settore_impiego");
				String note_tecniche = request.getParameter("note_tecniche");
				String note_generiche = request.getParameter("note_generiche");
				String numero_certificato = request.getParameter("numero_certificato");
				
				
				String tipo_attrezzatura = request.getParameter("tipo_attrezzatura");
				String tipo_attrezzatura_gvr = request.getParameter("tipo_attrezzatura_gvr");
				String id_specifica = request.getParameter("id_specifica");
				String sogg_messa_servizio_gvr = request.getParameter("sogg_messa_serv_GVR");
				String n_panieri_idroestrattori = request.getParameter("n_panieri_idroestrattori");
				String marcatura = request.getParameter("marcatura");
				String n_id_on = request.getParameter("n_id_on");
				String data_scadenza_ventennale = request.getParameter("data_scadenza_ventennale");
				
				
				
				List<SedeDTO> listaSedi = (List<SedeDTO>) request.getSession().getAttribute("listaSedi");
				SedeDTO sede = null;
				if(!id_sede.equals("0")) {
					sede = GestioneAnagraficaRemotaBO.getSedeFromId(listaSedi, Integer.parseInt(id_sede.split("_")[0]), Integer.parseInt(id_cliente));
				}
				DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				
				AttrezzaturaDTO attrezzatura = new AttrezzaturaDTO();
				attrezzatura.setId_cliente(Integer.parseInt(id_cliente));				
				attrezzatura.setId_sede(Integer.parseInt(id_sede.split("_")[0]));			
				
				ClienteDTO cliente = GestioneAnagraficaRemotaBO.getClienteById(id_cliente);
				
				attrezzatura.setNome_cliente(cliente.getNome());	
				if(!id_sede.equals("0")) {
					attrezzatura.setNome_sede(sede.getDescrizione() +" - "+sede.getIndirizzo()+" - "+sede.getComune()+ "("+sede.getSiglaProvincia()+")");
					attrezzatura.setIndirizzo(sede.getIndirizzo());
					attrezzatura.setComune(sede.getComune());
					attrezzatura.setProvincia(sede.getSiglaProvincia());
					attrezzatura.setCap(sede.getCap());
					attrezzatura.setRegione(GestioneAnagraficaRemotaBO.getRegioneFromProvincia(sede.getSiglaProvincia(),session));
				}else {
					attrezzatura.setNome_sede("");
					
					attrezzatura.setIndirizzo(cliente.getIndirizzo());
					attrezzatura.setComune(cliente.getCitta());
					attrezzatura.setProvincia(cliente.getProvincia());
					attrezzatura.setCap(cliente.getCap());
					attrezzatura.setRegione(GestioneAnagraficaRemotaBO.getRegioneFromProvincia(cliente.getProvincia(),session));
				}				
				attrezzatura.setMatricola_inail(matricola_inail);
				attrezzatura.setNumero_fabbrica(numero_fabbrica);
				if(descrizione!=null && !descrizione.equals("")) {
					attrezzatura.setDescrizione(descrizione.split("_")[1]);	
				}				
				attrezzatura.setTipo_attivita(tipo_attivita);
				attrezzatura.setTipo_attrezzatura(tipo_attrezzatura);
				attrezzatura.setTipo_attrezzatura_GVR(tipo_attrezzatura_gvr);
				attrezzatura.setID_specifica(id_specifica);
				attrezzatura.setSogg_messa_serv_GVR(sogg_messa_servizio_gvr);
				attrezzatura.setNome_sede(n_panieri_idroestrattori);
				attrezzatura.setMarcatura(marcatura);
				attrezzatura.setN_id_on(n_id_on);
				attrezzatura.setNumero_certificato(numero_certificato);
				
				if(data_scadenza_ventennale!=null && !data_scadenza_ventennale.equals("")) {
					attrezzatura.setData_scadenza_ventennale(format.parse(data_scadenza_ventennale));
				}
				if(data_ver_funz!=null && !data_ver_funz.equals("")) {
					attrezzatura.setData_verifica_funzionamento(format.parse(data_ver_funz));
				}
				if(data_pross_ver_funz!=null && !data_pross_ver_funz.equals("")) {
					attrezzatura.setData_prossima_verifica_funzionamento(format.parse(data_pross_ver_funz));	
				}
				if(data_ver_integrita!=null && !data_ver_integrita.equals("")) {
					attrezzatura.setData_verifica_integrita(format.parse(data_ver_integrita));	
				}
				if(data_pross_ver_integrita!=null && !data_pross_ver_integrita.equals("")) {
					attrezzatura.setData_prossima_verifica_integrita(format.parse(data_pross_ver_integrita));	
				}
				if(data_ver_interna!=null && !data_ver_interna.equals("")) {
					attrezzatura.setData_verifica_interna(format.parse(data_ver_interna));	
				}
				if(data_pross_ver_interna!=null && !data_pross_ver_interna.equals("")) {
					attrezzatura.setData_prossima_verifica_interna(format.parse(data_pross_ver_interna));	
				}			
				
				if(anno_costruzione!=null && !anno_costruzione.equals("")) {
					attrezzatura.setAnno_costruzione(Integer.parseInt(anno_costruzione));	
				}
				attrezzatura.setFabbricante(fabbricante);
				attrezzatura.setModello(modello);
				attrezzatura.setSettore_impiego(settore_impiego);
				attrezzatura.setNote_tecniche(note_tecniche);
				attrezzatura.setNote_generiche(note_generiche);
				
				session.save(attrezzatura);
				session.getTransaction().commit();
				session.close();
				
				PrintWriter out = response.getWriter();
				myObj.addProperty("success", true);
				myObj.addProperty("messaggio", "Attrezzatura salvata con successo!");
	        	out.print(myObj);
				
			}
			else if(action.equals("modifica")) {
				
				ajax=true;
				
				String id_attrezzatura = request.getParameter("id_attrezzatura");
				String id_cliente = request.getParameter("id_cliente");
				String id_sede = request.getParameter("id_sede");
				String matricola_inail = request.getParameter("matricola_inail");
				String numero_fabbrica = request.getParameter("numero_fabbrica");
				String descrizione = request.getParameter("descrizione");
				String tipo_attivita = request.getParameter("tipo_attivita");
				String data_ver_funz = request.getParameter("data_ver_funz");
				String data_pross_ver_funz = request.getParameter("data_pross_ver_funz");
				String data_ver_integrita = request.getParameter("data_ver_integrita");
				String data_pross_ver_integrita = request.getParameter("data_pross_ver_integrita");
				String data_ver_interna = request.getParameter("data_ver_interna");
				String data_pross_ver_interna = request.getParameter("data_pross_ver_interna");
				String anno_costruzione = request.getParameter("anno_costruzione");
				String fabbricante = request.getParameter("fabbricante");
				String modello = request.getParameter("modello");
				String settore_impiego = request.getParameter("settore_impiego");
				String note_tecniche = request.getParameter("note_tecniche");
				String note_generiche = request.getParameter("note_generiche");
				String numero_certificato = request.getParameter("numero_certificato");
				
				String tipo_attrezzatura = request.getParameter("tipo_attrezzatura");
				String tipo_attrezzatura_gvr = request.getParameter("tipo_attrezzatura_gvr");
				String id_specifica = request.getParameter("id_specifica");
				String sogg_messa_servizio_gvr = request.getParameter("sogg_messa_serv_GVR");
				String n_panieri_idroestrattori = request.getParameter("n_panieri_idroestrattori");
				String marcatura = request.getParameter("marcatura");
				String n_id_on = request.getParameter("n_id_on");
				String data_scadenza_ventennale = request.getParameter("data_scadenza_ventennale");
				
				
				List<SedeDTO> listaSedi = (List<SedeDTO>) request.getSession().getAttribute("listaSedi");
				SedeDTO sede = null;
				if(!id_sede.equals("0")) {
					sede = GestioneAnagraficaRemotaBO.getSedeFromId(listaSedi, Integer.parseInt(id_sede.split("_")[0]), Integer.parseInt(id_cliente));
				}
				DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				
				AttrezzaturaDTO attrezzatura = GestioneAttrezzatureBO.getAttrezzaturaFromId(Integer.parseInt(id_attrezzatura), session);
				attrezzatura.setId_cliente(Integer.parseInt(id_cliente));				
				attrezzatura.setId_sede(Integer.parseInt(id_sede.split("_")[0]));		
				
				ClienteDTO cliente = GestioneAnagraficaRemotaBO.getClienteById(id_cliente);
				
				attrezzatura.setNome_cliente(cliente.getNome());	
				if(!id_sede.equals("0")) {
					attrezzatura.setNome_sede(sede.getDescrizione() +" - "+sede.getIndirizzo()+" - "+sede.getComune()+ "("+sede.getSiglaProvincia()+")");
					attrezzatura.setIndirizzo(sede.getIndirizzo());
					attrezzatura.setComune(sede.getComune());
					attrezzatura.setProvincia(sede.getSiglaProvincia());
					attrezzatura.setCap(sede.getCap());
					attrezzatura.setRegione(GestioneAnagraficaRemotaBO.getRegioneFromProvincia(sede.getSiglaProvincia(),session));
				}else {
					attrezzatura.setNome_sede("");
					attrezzatura.setIndirizzo(cliente.getIndirizzo());
					attrezzatura.setComune(cliente.getCitta());
					attrezzatura.setProvincia(cliente.getProvincia());
					attrezzatura.setCap(cliente.getCap());
					attrezzatura.setRegione(GestioneAnagraficaRemotaBO.getRegioneFromProvincia(cliente.getProvincia(),session));
				}				
				attrezzatura.setMatricola_inail(matricola_inail);
				attrezzatura.setNumero_fabbrica(numero_fabbrica);
				if(descrizione!=null && !descrizione.equals("")) {
					attrezzatura.setDescrizione(descrizione.split("_")[1]);	
				}	
				attrezzatura.setTipo_attivita(tipo_attivita);
				
				attrezzatura.setTipo_attrezzatura(tipo_attrezzatura);
				attrezzatura.setTipo_attrezzatura_GVR(tipo_attrezzatura_gvr);
				attrezzatura.setID_specifica(id_specifica);
				attrezzatura.setSogg_messa_serv_GVR(sogg_messa_servizio_gvr);
				attrezzatura.setNome_sede(n_panieri_idroestrattori);
				attrezzatura.setMarcatura(marcatura);
				attrezzatura.setN_id_on(n_id_on);
				attrezzatura.setNumero_certificato(numero_certificato);
				
				if(data_scadenza_ventennale!=null && !data_scadenza_ventennale.equals("")) {
					attrezzatura.setData_scadenza_ventennale(format.parse(data_scadenza_ventennale));
				}else {
					attrezzatura.setData_scadenza_ventennale(null);
				}
				
				if(data_ver_funz!=null && !data_ver_funz.equals("")) {
					attrezzatura.setData_verifica_funzionamento(format.parse(data_ver_funz));
				}else {
					attrezzatura.setData_verifica_funzionamento(null);
				}
				if(data_pross_ver_funz!=null && !data_pross_ver_funz.equals("")) {
					attrezzatura.setData_prossima_verifica_funzionamento(format.parse(data_pross_ver_funz));	
				}else {
					attrezzatura.setData_prossima_verifica_funzionamento(null);
				}
				if(data_ver_integrita!=null && !data_ver_integrita.equals("")) {
					attrezzatura.setData_verifica_integrita(format.parse(data_ver_integrita));	
				}else {
					attrezzatura.setData_verifica_integrita(null);
				}
				if(data_pross_ver_integrita!=null && !data_pross_ver_integrita.equals("")) {
					attrezzatura.setData_prossima_verifica_integrita(format.parse(data_pross_ver_integrita));	
				}else {
					attrezzatura.setData_prossima_verifica_integrita(null);
				}
				if(data_ver_interna!=null && !data_ver_interna.equals("")) {
					attrezzatura.setData_verifica_interna(format.parse(data_ver_interna));	
				}else {
					attrezzatura.setData_verifica_interna(null);
				}
				if(data_pross_ver_interna!=null && !data_pross_ver_interna.equals("")) {
					attrezzatura.setData_prossima_verifica_interna(format.parse(data_pross_ver_interna));	
				}else {
					attrezzatura.setData_prossima_verifica_interna(null);
				}				
				if(anno_costruzione!=null && !anno_costruzione.equals("")) {
					attrezzatura.setAnno_costruzione(Integer.parseInt(anno_costruzione));	
				}else {
					attrezzatura.setAnno_costruzione(1900);
				}
				attrezzatura.setFabbricante(fabbricante);
				attrezzatura.setModello(modello);
				attrezzatura.setSettore_impiego(settore_impiego);
				attrezzatura.setNote_tecniche(note_tecniche);
				attrezzatura.setNote_generiche(note_generiche);
				
				session.update(attrezzatura);
				session.getTransaction().commit();
				session.close();
				
				PrintWriter out = response.getWriter();
				myObj.addProperty("success", true);
				myObj.addProperty("messaggio", "Attrezzatura modifica con successo!");
	        	out.print(myObj);
				
			}
			else if(action.equals("scadenzario")) {
				
				String data = request.getParameter("data");
				String tipo_data = request.getParameter("tipo_data");								
				
				List<ClienteDTO> listaClientiFull = (List<ClienteDTO>) request.getSession().getAttribute("listaClienti");
				
				if(listaClientiFull==null) {
					CompanyDTO cmp=(CompanyDTO)request.getSession().getAttribute("usrCompany");
					
					String idCompany=""+cmp.getId();
					listaClientiFull = GestioneAnagraficaRemotaBO.getListaClienti(idCompany);			
				}
							
				List<SedeDTO>listaSediFull  = (List<SedeDTO>) request.getSession().getAttribute("listaSedi");	

				if(listaSediFull== null) {
					listaSediFull = GestioneAnagraficaRemotaBO.getListaSedi();
				}
				
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
				ArrayList<AttrezzaturaDTO> lista_attrezzature = GestioneAttrezzatureBO.getlistaAttrezzatureData(df.parse(data), tipo_data, session);
				
				 request.getSession().setAttribute("lista_attrezzature",lista_attrezzature);
				
				 if(tipo_data.equals("data_prossima_verifica_integrita")) {
					 tipo_data = tipo_data.substring(0, tipo_data.length()-1) + "à";
				 }
				 
				 ArrayList<DescrizioneGruppoAttrezzaturaDTO> lista_descrizioni_gruppi = GestioneAttrezzatureBO.getListaDescrizioniGruppi(session);
				 request.getSession().setAttribute("tipo_scadenza",tipo_data.replace("_", " "));				 
				 request.getSession().setAttribute("data_scadenza",df2.format(df.parse(data)));				
				 request.getSession().setAttribute("listaClienti",listaClientiFull);
				 request.getSession().setAttribute("listaSedi",listaSediFull);					
				 request.getSession().setAttribute("lista_descrizioni_gruppi",lista_descrizioni_gruppi);
				 
				 session.close();
				 
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/listaAttrezzatureCalendario.jsp");
		     	dispatcher.forward(request,response);
				
			}
			else if(action.equals("rendi_obsoleta")) {
				ajax = true;
				
				String id_attrezzatura = request.getParameter("id_attrezzatura");
								
				AttrezzaturaDTO attrezzatura = GestioneAttrezzatureBO.getAttrezzaturaFromId(Integer.parseInt(id_attrezzatura), session);
				if(attrezzatura.getObsoleta()==0) {
					attrezzatura.setObsoleta(1);
				}else {
					attrezzatura.setObsoleta(0);
				}
				
				session.getTransaction().commit();
				session.close();
				
				PrintWriter out = response.getWriter();
				myObj.addProperty("success", true);
				myObj.addProperty("messaggio", "Modifica effettuata con successo!");
				out.print(myObj);
			}
			else if(action.equals("verbali_attrezzatura")) {
				
				String id_attrezzatura = request.getParameter("id_attrezzatura");				
				
				ArrayList<VerbaleDTO> listaVerbali = GestioneVerbaleBO.getListaVerbaliFromAttrezzatura(Integer.parseInt(id_attrezzatura), utente,session);
				
				request.getSession().setAttribute("listaVerbali",listaVerbali);
				 
				session.close();
				 
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/listaVerbaliAttrezzatura.jsp");
				dispatcher.forward(request,response);
				
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

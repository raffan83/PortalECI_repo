package it.portalECI.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;

import com.google.gson.JsonObject;

import it.portalECI.DAO.GestioneStoricoVerbaleDAO;
import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.SedeDTO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.DTO.VerbaleDTO;
import it.portalECI.DTO.VerbaleStoricoAllegatoDTO;
import it.portalECI.DTO.VerbaleStoricoDTO;
import it.portalECI.Exception.ECIException;
import it.portalECI.Util.Utility;
import it.portalECI.bo.GestioneStoricoVerbaleBO;
import it.portalECI.bo.GestioneVerbaleBO;
import it.portalECI.Util.Costanti;

/**
 * Servlet implementation class GestioneStoricoVerbale
 */
@WebServlet("/gestioneStoricoVerbale.do")
public class GestioneStoricoVerbale extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestioneStoricoVerbale() {
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
				
				ArrayList<VerbaleStoricoDTO> lista_verbali = GestioneStoricoVerbaleBO.getListaVerbaliStorico(session, utente);
				
				request.getSession().setAttribute("lista_verbali", lista_verbali);
				request.getSession().setAttribute("dateFrom", null);
				request.getSession().setAttribute("dateTo", null); 
				
				//importaDaExcel(session);
				
				
				
				
//				//File file = new File("C:\\Users\\antonio.dicivita\\Desktop\\tbl_verbeletter new (1).xlsx");
//				//File file = new File("C:\\Users\\antonio.dicivita\\Desktop\\tbl_upload (1).xlsx");
//				//File file = new File("C:\\Users\\antonio.dicivita\\Desktop\\tbl_verbespl.xlsx");
//				//File file = new File("C:\\Users\\antonio.dicivita\\Desktop\\tbl_espl (8).xlsx");
//				//File file = new File("C:\\Users\\antonio.dicivita\\Desktop\\tbl_verbscariche.xlsx");
//				File file = new File("C:\\Users\\antonio.dicivita\\Desktop\\tbl_scariche (9).xlsx");
//				
//				FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file  
//				//creating Workbook instance that refers to .xlsx file  
//				XSSFWorkbook wb = new XSSFWorkbook(fis);   
//				XSSFSheet sheet = wb.getSheetAt(0);     //creating a Sheet object to retrieve object  
//				Iterator<Row> itr = sheet.iterator();    //iterating over excel file  
//				
//				ArrayList<Integer> lista_inseriti = new ArrayList<Integer>();
//				ArrayList<String> lista_saltati = new ArrayList<String>();
//				HashMap<Integer, Integer> map = new HashMap<>();
//				
//				int i = 0;
//				while (itr.hasNext())                 
//				{  
//					Row row = itr.next();  
//					Iterator<Cell> cellIterator = row.cellIterator();   //iterating over each column  
//					
//					if(row.getRowNum()== 1035) {
//						System.out.println("inside");
//					}
//					
//					if(row.getRowNum()>=1 ) {
//						
//						int id =  (int)row.getCell(0).getNumericCellValue();
//						
//						
//						for (VerbaleStoricoDTO verbale : lista_verbali) {
//							lista_inseriti.add(verbale.getId_verbale_storico());
//						}
//						
//						
//						int verbeletter_id = (int) row.getCell(2-i).getNumericCellValue();	
//						String nome_file = row.getCell(1).getStringCellValue();
//						String nome_scheda = null;
//						
//						if(row.getCell(27-i)!=null) {
//							nome_scheda = row.getCell(27-i).getStringCellValue();
//						}
//						//String nome_file = nome_scheda;
//						String codice_verificatore = row.getCell(9-i).getStringCellValue();
//						String verificatore = row.getCell(10-i).getStringCellValue()+" " +row.getCell(11-i).getStringCellValue();
//						int x = (int)row.getCell(26-i).getNumericCellValue();
//						String numero_verbale =codice_verificatore+"-VS-"+String.format("%06d" , x)+"-"+row.getCell(16-i).getStringCellValue();
//						String cliente = row.getCell(5-i).getStringCellValue();
//						
//						String indirizzo_cliente = "";
//						
//						if(row.getCell(7-i).getCellType()==Cell.CELL_TYPE_STRING && row.getCell(8-i).getCellType()==Cell.CELL_TYPE_STRING) {
//							indirizzo_cliente = row.getCell(7-i).getStringCellValue()+" " +row.getCell(8-i).getStringCellValue();
//						}else if(row.getCell(7-i).getCellType()==Cell.CELL_TYPE_STRING && row.getCell(8-i).getCellType()!=Cell.CELL_TYPE_STRING){
//							indirizzo_cliente = row.getCell(7-i).getStringCellValue()+" " +(int)row.getCell(8-i).getNumericCellValue();
//						}else if(row.getCell(7-i).getCellType()!=Cell.CELL_TYPE_STRING && row.getCell(8-i).getCellType()==Cell.CELL_TYPE_STRING){
//							indirizzo_cliente = (int)row.getCell(7-i).getNumericCellValue()+" " +row.getCell(8-i).getStringCellValue();
//						}else {
//							indirizzo_cliente = row.getCell(7-i).getNumericCellValue()+" " +(int)row.getCell(8-i).getNumericCellValue();
//						}
//						
//						String localita_cliente = row.getCell(6-i).getStringCellValue();
//						String ubicazione_impianto = "";
//						
//						if(row.getCell(22-i).getCellType()!=Cell.CELL_TYPE_STRING) {
//							ubicazione_impianto=row.getCell(18-i).getStringCellValue() +" - "+row.getCell(21-i).getStringCellValue()+" - "+(int)row.getCell(22-i).getNumericCellValue();
//						}else {
//							ubicazione_impianto=row.getCell(18-i).getStringCellValue() +" - "+row.getCell(21-i).getStringCellValue()+" - "+row.getCell(22-i).getStringCellValue();
//						}
//						
//						String localita_impianto = row.getCell(19-i).getStringCellValue();
//						String provincia = row.getCell(20-i).getStringCellValue();
//						int esit = (int) row.getCell(24-i).getNumericCellValue();
//						String esito ="POSITIVO";
//						if(esit!=0) {
//							esito = "NEGATIVO";
//						}
//						String codice_commessa = row.getCell(3-i).getStringCellValue();
//						String strumento_utilizzato = row.getCell(25-i).getStringCellValue();
//						Date data_verifica = row.getCell(15-i).getDateCellValue();
//						int frequenza = (int)row.getCell(17-i).getNumericCellValue();
//						Date data_prossima_verifica = row.getCell(23-i).getDateCellValue();
//						String ore_uomo = "";
//						if(row.getCell(13-i).getCellType()==Cell.CELL_TYPE_STRING) {
//							ore_uomo = row.getCell(13-i).getStringCellValue();
//						}else {
//							ore_uomo = ""+ row.getCell(13-i).getNumericCellValue();
//						}
//						
//						int tipologia_verifica = (int)row.getCell(4-i).getNumericCellValue();
//									//AttrezzaturaDTO attrezzatura =  GestioneAttrezzatureBO.getAttrezzaturaFromMatricola(matricola, session);
//									
//						VerbaleStoricoDTO verbale = new VerbaleStoricoDTO();
//						
//						verbale.setCliente(cliente);
//						verbale.setCodice_commessa(codice_commessa);
//						verbale.setCodice_verificatore(codice_verificatore);
//						verbale.setData_prossima_verifica(data_prossima_verifica);
//						verbale.setData_verifica(data_verifica);
//						verbale.setEsito(esito);
//						verbale.setFrequenza(frequenza);
//						verbale.setIndirizzo_cliente(indirizzo_cliente);
//						verbale.setLocalita_cliente(localita_cliente);
//						verbale.setLocalita_impianto(localita_impianto);
//						verbale.setNumero_verbale(numero_verbale);
//						verbale.setOre_uomo(ore_uomo);
//						verbale.setProvincia(provincia);
//						verbale.setStrumento_utilizzato(strumento_utilizzato);
//						verbale.setTipologia_verifica(tipologia_verifica);
//						verbale.setUbicazione_impianto(ubicazione_impianto);
//						verbale.setVerificatore(verificatore);
//						verbale.setId_verbale_storico(verbeletter_id);
//						//if(!lista_inseriti.contains(verbeletter_id)) {
//						//	session.save(verbale);	
//						//	lista_inseriti.add(verbeletter_id);	
//						//	map.put(verbeletter_id, verbale.getId());
//						//}
////						
//						int id_verb = GestioneStoricoVerbaleDAO.getVerbaleFromIdStorico(verbeletter_id, session);
//						
//						map.put(verbeletter_id, id_verb);
//										System.out.println("riga "+row.getRowNum());
//									
//								
//
//								//}
//						//}
//										
//										
//										
//										//String path = "C:\\Users\\antonio.dicivita\\Desktop\\app\\upload\\verbelett\\";
//										//String path = "C:\\Users\\antonio.dicivita\\Desktop\\app\\upload\\verbespl\\";
//										String path = "C:\\Users\\antonio.dicivita\\Desktop\\app\\upload\\verbscariche\\";
//										File fileToCopy = new File(path+verbeletter_id+"\\"+nome_file);
//										
//										String pathDest = Costanti.PATH_ROOT+"//Storico//Verbali//"+verbale.getId()+"//"+nome_file;
//										 
//										String path_folder =Costanti.PATH_ROOT+"//Storico//Verbali//"+map.get(verbeletter_id)+"//";
//										File folder=new File(path_folder);
//										
//										if(!folder.exists()) {
//											folder.mkdirs();
//										}
//									
//										File f = new File(path+verbeletter_id+"\\"+nome_file);
//										
//										if(f.exists()) {
//											
//											File f1 = new File(path_folder+"\\"+nome_file);
//											
//											if(!f1.exists()&& map.get(verbeletter_id)!=null) {
//												VerbaleStoricoAllegatoDTO allegato = new VerbaleStoricoAllegatoDTO();
//												allegato.setId_verbale_storico(map.get(verbeletter_id));
//												allegato.setFilename(nome_file);
//												session.save(allegato);
//											}
//																					
//											Files.copy(Paths.get(path+verbeletter_id+"\\"+nome_file), Paths.get(path_folder+"\\"+nome_file), StandardCopyOption.REPLACE_EXISTING);
//																			
////											if(nome_scheda!=null && !nome_scheda.equals("")) {
////												
////												File f2 = new File(path_folder+"\\"+nome_scheda);
////												File f3 = new File(path+verbeletter_id+"\\"+nome_scheda);
////												
////												if(f3.exists() && !f2.exists() && map.get(verbeletter_id)!=null) {
////													VerbaleStoricoAllegatoDTO allegato = new VerbaleStoricoAllegatoDTO();
////													allegato.setId_verbale_storico(map.get(verbeletter_id));
////													allegato.setFilename(nome_scheda);
////													session.save(allegato);
////													
////													Files.copy(Paths.get(path+verbeletter_id+"\\"+nome_scheda), Paths.get(path_folder+"\\"+nome_scheda), StandardCopyOption.REPLACE_EXISTING);
////												}
////											}
//											
//										}else {
//											System.out.println(verbeletter_id+"\\"+nome_file+"\\"+map.get(verbeletter_id));
//											lista_saltati.add(verbeletter_id+"\\"+nome_file+"\\"+map.get(verbeletter_id));
//										}				
//										
////					}					
//////					}else if(row.getRowNum()==3602) {
//////						break;
//					}
//				}
//				
//				for (String string : lista_saltati) {
//					System.out.println(string);
//				}
				
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/listaStoricoVerbale.jsp");
				dispatcher.forward(request,response);
				
			}
			else if(action.equals("filtra_date")){				
				
				String dateFrom = request.getParameter("dateFrom");
				String dateTo = request.getParameter("dateTo");		
				
				List<VerbaleStoricoDTO> listaVerbali = GestioneStoricoVerbaleBO.getListaVerbaliStoricoDate(session, utente, dateFrom, dateTo);
				
				request.getSession().setAttribute("lista_verbali", listaVerbali);
				request.getSession().setAttribute("dateFrom", dateFrom);
				request.getSession().setAttribute("dateTo", dateTo);

				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/listaStoricoVerbale.jsp");
		     	dispatcher.forward(request,response);
		     	
		     	
			}
			
			else if(action.equals("archivio")) {
				
				String id_verbale = request.getParameter("id_verbale");
				
				ArrayList<VerbaleStoricoAllegatoDTO> lista_allegati=  GestioneStoricoVerbaleBO.getAllegatiVerbale(Integer.parseInt(id_verbale), session);					
			
				
				request.getSession().setAttribute("lista_allegati", lista_allegati);
				request.getSession().setAttribute("id_verbale", id_verbale);	
				

				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/listaArchivioStorico.jsp");
		     	dispatcher.forward(request,response);
			}
			else if(action.equals("archivio_upload")) {
				ajax = true;
				
				String id_verbale = request.getParameter("id_verbale");		
								
				ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());
				PrintWriter out = response.getWriter();
				response.setContentType("application/json");						
					
					List<FileItem> items = uploadHandler.parseRequest(request);
					for (FileItem item : items) {
						if (!item.isFormField()) {
							if(id_verbale!=null && !id_verbale.equals("0")) {
								
								VerbaleStoricoAllegatoDTO allegato = new VerbaleStoricoAllegatoDTO();
								allegato.setFilename(item.getName());
								allegato.setId_verbale_storico(Integer.parseInt(id_verbale));

								saveFile(item, Integer.parseInt(id_verbale),item.getName());	
								session.save(allegato);
								
							}
							
						}
					}


					myObj.addProperty("success", true);
					myObj.addProperty("messaggio", "Upload effettuato con successo!");
					out.print(myObj);
			}
			else if (action.equals("archivio_download")) {
		
				String id_allegato = request.getParameter("id_allegato");
				
				String path = "";
				
				if(id_allegato!=null && !id_allegato.equals("0")) {
					
					VerbaleStoricoAllegatoDTO allegato = GestioneStoricoVerbaleBO.getAllegatoFormId(Integer.parseInt(id_allegato), session);					
					
					response.setContentType("application/octet-stream");
					response.setHeader("Content-Disposition","attachment;filename="+ allegato.getFilename());
					
					downloadFile(allegato.getFilename(),allegato.getId_verbale_storico(), response.getOutputStream());
				}
							
			}
			
			
			else if(action.equals("elimina_allegato")) {
				
				ajax=true;
				
				String id_allegato = request.getParameter("id_allegato");
				
				if(id_allegato!=null && !id_allegato.equals("0")) {
					
					VerbaleStoricoAllegatoDTO allegato = GestioneStoricoVerbaleBO.getAllegatoFormId(Integer.parseInt(id_allegato), session);
					allegato.setDisabilitato(1);
					session.update(allegato);
				}
				PrintWriter out = response.getWriter();

				myObj.addProperty("success", true);
				myObj.addProperty("messaggio", "Allegato eliminato con successo!");
				out.print(myObj);
				
				
			}
			
			
			session.getTransaction().commit();
			session.close();
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
	
	
	
	 private void saveFile(FileItem item, int id_verbale, String filename) {

		 	
			String path_folder =Costanti.PATH_ROOT+"//Storico//Verbali//"+id_verbale+"//";
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
	 
	 private void downloadFile(String filename, int id_verbale, ServletOutputStream outp) throws Exception {
		 
		 String path = Costanti.PATH_ROOT+"//Storico//Verbali//"+id_verbale+"//"+filename;
		 
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
	 
	 
	 
	 
	 
	 public static void importaDaExcel( Session session) throws Exception {
			
			
			File file = new File("C:\\Users\\antonio.dicivita\\Desktop\\tbl_upload.xlsx");
	
			int esito_generale = 0;
			FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file  
			//creating Workbook instance that refers to .xlsx file  
			XSSFWorkbook wb = new XSSFWorkbook(fis);   
			XSSFSheet sheet = wb.getSheetAt(0);     //creating a Sheet object to retrieve object  
			Iterator<Row> itr = sheet.iterator();    //iterating over excel file  
			

			while (itr.hasNext())                 
			{  
				Row row = itr.next();  
				Iterator<Cell> cellIterator = row.cellIterator();   //iterating over each column  
						
				boolean esito = false;
				String nome_file = null;
				String codice_commessa = null;
				String verbeletter_id = null;
				while (cellIterator.hasNext())  
				{  
					Cell cell = cellIterator.next();  
				
					
					if(cell.getRowIndex()>0) {		
						
						if(row.getCell(cell.getColumnIndex()).getCellType()==Cell.CELL_TYPE_STRING) {
							
								esito = true;		
								
							    if(cell.getColumnIndex()==1) {
									nome_file = cell.getStringCellValue();
								}	
							    
							    if(cell.getColumnIndex()==2) {
							    	verbeletter_id =""+ (int)cell.getNumericCellValue();
								}	
							    	
								else if(cell.getColumnIndex()==4) {
									codice_commessa = cell.getStringCellValue();
								}
						
							
						}else {
								esito = true;		
								
							    
							    if(cell.getColumnIndex()==2) {
							    	verbeletter_id =""+ (int)cell.getNumericCellValue();
								}	
						}
				}
			}
				
				
				if(codice_commessa!=null) {
					VerbaleStoricoDTO verbale = GestioneStoricoVerbaleBO.getVerbaleFromCommessa(codice_commessa, session);
					
					if(verbale!=null) {
						String path = "C:\\Users\\antonio.dicivita\\Desktop\\app\\upload\\verbscariche\\";
						File fileToCopy = new File(path+verbeletter_id+"\\"+nome_file);
						
						String pathDest = Costanti.PATH_ROOT+"//Storico//Verbali//"+verbale.getId()+"//"+nome_file;
						 
						String path_folder =Costanti.PATH_ROOT+"//Storico//Verbali//"+verbale.getId()+"//";
						File folder=new File(path_folder);
						
						if(!folder.exists()) {
							folder.mkdirs();
						}
					
						File f = new File(path+verbeletter_id+"\\"+nome_file);
						
						if(f.exists()) {
						
							Files.copy(Paths.get(path+verbeletter_id+"\\"+nome_file), Paths.get(path_folder+"\\"+nome_file), StandardCopyOption.REPLACE_EXISTING);
															
							
							VerbaleStoricoAllegatoDTO allegato = new VerbaleStoricoAllegatoDTO();
							allegato.setId_verbale_storico(verbale.getId());
							allegato.setFilename(nome_file);
							session.save(allegato);
						}else {
							System.out.println(verbeletter_id+"\\"+nome_file);
						}
				}
					
		}
	    
	}

	 }
	 
	 
	 
}




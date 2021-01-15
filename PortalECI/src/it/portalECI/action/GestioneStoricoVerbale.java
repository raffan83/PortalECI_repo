package it.portalECI.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
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
				
//				
//				
//				
//				File file = new File("C:\\Users\\antonio.dicivita\\Desktop\\storico.xlsx");
//				
//				FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file  
//				//creating Workbook instance that refers to .xlsx file  
//				XSSFWorkbook wb = new XSSFWorkbook(fis);   
//				XSSFSheet sheet = wb.getSheetAt(0);     //creating a Sheet object to retrieve object  
//				Iterator<Row> itr = sheet.iterator();    //iterating over excel file  
//				
//			
//
//				while (itr.hasNext())                 
//				{  
//					Row row = itr.next();  
//					Iterator<Cell> cellIterator = row.cellIterator();   //iterating over each column  
//					
//					if(row.getRowNum()== 1035) {
//						System.out.println("inside");
//					}
//					
//					if(row.getRowNum()>=1 && row.getRowNum()!=2697) {
//						
//
//						
//						String codice_verificatore = row.getCell(0).getStringCellValue();
//						String verificatore = row.getCell(1).getStringCellValue();
//						String numero_verbale = row.getCell(2).getStringCellValue();
//						String cliente = row.getCell(3).getStringCellValue();
//						String indirizzo_cliente = row.getCell(4).getStringCellValue();
//						String localita_cliente = row.getCell(5).getStringCellValue();
//						String ubicazione_impianto = row.getCell(6).getStringCellValue();
//						String localita_impianto = row.getCell(7).getStringCellValue();
//						String provincia = row.getCell(8).getStringCellValue();
//						String esito = row.getCell(9).getStringCellValue();
//						String codice_commessa = row.getCell(10).getStringCellValue();
//						String strumento_utilizzato = row.getCell(11).getStringCellValue();
//						Date data_verifica = row.getCell(12).getDateCellValue();
//						int frequenza = (int)row.getCell(13).getNumericCellValue();
//						Date data_prossima_verifica = row.getCell(14).getDateCellValue();
//						String ore_uomo = "";
//						if(row.getCell(15).getCellType()==Cell.CELL_TYPE_STRING) {
//							ore_uomo = row.getCell(15).getStringCellValue();
//						}else {
//							ore_uomo = ""+ row.getCell(15).getNumericCellValue();
//						}
//						
//						int tipologia_verifica = (int)row.getCell(16).getNumericCellValue();
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
//						session.save(verbale);
//										System.out.println("riga "+row.getRowNum());
//									
//									
//
//								//}
//						//}s
//					}else if(row.getRowNum()==2698) {
//						break;
//					}
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
}

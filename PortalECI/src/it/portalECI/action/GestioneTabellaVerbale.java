package it.portalECI.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import it.portalECI.DAO.GestioneVerbaleDAO;
import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.ColonnaTabellaVerbaleDTO;
import it.portalECI.DTO.OpzioneRispostaQuestionarioDTO;
import it.portalECI.DTO.OpzioneRispostaVerbaleDTO;
import it.portalECI.DTO.RispostaFormulaQuestionarioDTO;
import it.portalECI.DTO.RispostaFormulaVerbaleDTO;
import it.portalECI.DTO.RispostaSceltaQuestionarioDTO;
import it.portalECI.DTO.RispostaSceltaVerbaleDTO;
import it.portalECI.DTO.RispostaTabellaVerbaleDTO;
import it.portalECI.DTO.RispostaTestoQuestionarioDTO;
import it.portalECI.DTO.RispostaTestoVerbaleDTO;
import it.portalECI.DTO.RispostaVerbaleDTO;
import it.portalECI.Exception.ECIException;
import it.portalECI.Util.Costanti;

@WebServlet(name = "gestioneTabellaVerbale", urlPatterns = { "/gestioneTabellaVerbale.do" })
public class GestioneTabellaVerbale extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public GestioneTabellaVerbale() {
		super();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Session session=SessionFacotryDAO.get().openSession();
		Transaction transaction = session.beginTransaction();
		
		
		String action = request.getParameter("action");
		
		if(action==null) {
			String rispostaId = request.getParameter("rispostaId");
			Integer rispostaidInt = Integer.parseInt(rispostaId);
			RispostaTabellaVerbaleDTO risposta = (RispostaTabellaVerbaleDTO) session.get(RispostaTabellaVerbaleDTO.class, rispostaidInt);
			
			List<ColonnaTabellaVerbaleDTO> colonne=new ArrayList<ColonnaTabellaVerbaleDTO>();
			List<RispostaVerbaleDTO> risposte=new ArrayList<RispostaVerbaleDTO>();

			colonne.addAll(risposta.getColonne());
			
			Collections.sort(colonne, new Comparator<ColonnaTabellaVerbaleDTO>() {
		        @Override
		        public int compare(ColonnaTabellaVerbaleDTO op2, ColonnaTabellaVerbaleDTO op1){
					int pos1=op1.getColonnaQuestionario().getPosizione().intValue();
					int pos2=op2.getColonnaQuestionario().getPosizione().intValue();
		            return  pos2 - pos1;
		        }
		    });
			
			for(ColonnaTabellaVerbaleDTO colonna: colonne){
				switch (colonna.getDomanda().getRisposta().getTipo()) {
				case RispostaVerbaleDTO.TIPO_TESTO:
					RispostaTestoVerbaleDTO rispostaTesto =  new RispostaTestoVerbaleDTO();
					RispostaTestoQuestionarioDTO rispostaQuestionario =(RispostaTestoQuestionarioDTO)session.get(RispostaTestoQuestionarioDTO.class, colonna.getColonnaQuestionario().getDomanda().getRisposta().getId());
					rispostaTesto.setRispostaQuestionario(rispostaQuestionario);
					session.save(rispostaTesto);
					colonna.getRisposte().add(rispostaTesto);
					risposte.add(rispostaTesto);
					break;
				case RispostaVerbaleDTO.TIPO_SCELTA:
					RispostaSceltaVerbaleDTO rispostaScelta = new RispostaSceltaVerbaleDTO();
					RispostaSceltaQuestionarioDTO rispostaSceltaQuestionario = (RispostaSceltaQuestionarioDTO) session.get(RispostaSceltaQuestionarioDTO.class, colonna.getColonnaQuestionario().getDomanda().getRisposta().getId());
					rispostaScelta.setRispostaQuestionario(rispostaSceltaQuestionario);
					rispostaScelta.setOpzioni(new HashSet<OpzioneRispostaVerbaleDTO>());
					for(OpzioneRispostaQuestionarioDTO opzioneQuestionario: rispostaSceltaQuestionario.getOpzioni()) {
						OpzioneRispostaVerbaleDTO opzioneRispostaVerbale = new OpzioneRispostaVerbaleDTO();
						opzioneRispostaVerbale.setRisposta(rispostaScelta);
						opzioneRispostaVerbale.setOpzioneQuestionario(opzioneQuestionario);
						rispostaScelta.getOpzioni().add(opzioneRispostaVerbale);
						//Non si gestiscono domande annidate perchè le opzioni delle domande figlie di una tabella non possono avere altre domande
					}
					session.save(rispostaScelta);
					colonna.getRisposte().add(rispostaScelta);
					risposte.add(rispostaScelta);
					break;
				case RispostaVerbaleDTO.TIPO_FORMULA:
					RispostaFormulaVerbaleDTO rispostaFormula =  new RispostaFormulaVerbaleDTO();
					RispostaFormulaQuestionarioDTO rispostaFormulaQuestionario = (RispostaFormulaQuestionarioDTO) session.get(RispostaFormulaQuestionarioDTO.class, colonna.getColonnaQuestionario().getDomanda().getRisposta().getId());
					rispostaFormula.setRispostaQuestionario(rispostaFormulaQuestionario);
					session.save(rispostaFormula);
					colonna.getRisposte().add(rispostaFormula);
					risposte.add(rispostaFormula);
					break;
				default:
					break;
				}
				
				session.update(colonna);
			}
			transaction.commit();
			request.setCharacterEncoding("UTF-8");
			request.setAttribute("rispostaParent", risposta);
			request.setAttribute("risposte", risposte);
			request.setAttribute("colonne", colonne);
			request.setAttribute("hibernateSession", session);
		}
		else if(action.equals("importa_excel")) {
			
			String rispostaId = request.getParameter("rispostaId");
			Integer rispostaidInt = Integer.parseInt(rispostaId);
			RispostaTabellaVerbaleDTO risposta = (RispostaTabellaVerbaleDTO) session.get(RispostaTabellaVerbaleDTO.class, rispostaidInt);
			
			List<ColonnaTabellaVerbaleDTO> colonne=new ArrayList<ColonnaTabellaVerbaleDTO>();
			List<RispostaVerbaleDTO> risposte=new ArrayList<RispostaVerbaleDTO>();

			colonne.addAll(risposta.getColonne());
			
			Collections.sort(colonne, new Comparator<ColonnaTabellaVerbaleDTO>() {
		        @Override
		        public int compare(ColonnaTabellaVerbaleDTO op2, ColonnaTabellaVerbaleDTO op1){
					int pos1=op1.getColonnaQuestionario().getPosizione().intValue();
					int pos2=op2.getColonnaQuestionario().getPosizione().intValue();
		            return  pos2 - pos1;
		        }
		    });
			
			String[] risposteExcel = {"ris1","ris2","ris3","ris4","NEG"};
			
			int index = 0;
				
				for(ColonnaTabellaVerbaleDTO colonna: colonne){
					switch (colonna.getDomanda().getRisposta().getTipo()) {
					case RispostaVerbaleDTO.TIPO_TESTO:
						RispostaTestoVerbaleDTO rispostaTesto =  new RispostaTestoVerbaleDTO();
						RispostaTestoQuestionarioDTO rispostaQuestionario =(RispostaTestoQuestionarioDTO)session.get(RispostaTestoQuestionarioDTO.class, colonna.getColonnaQuestionario().getDomanda().getRisposta().getId());
						rispostaTesto.setRispostaQuestionario(rispostaQuestionario);
						session.save(rispostaTesto);
						colonna.getRisposte().add(rispostaTesto);
						risposte.add(rispostaTesto);
						break;
					case RispostaVerbaleDTO.TIPO_SCELTA:
						RispostaSceltaVerbaleDTO rispostaScelta = new RispostaSceltaVerbaleDTO();
						RispostaSceltaQuestionarioDTO rispostaSceltaQuestionario = (RispostaSceltaQuestionarioDTO) session.get(RispostaSceltaQuestionarioDTO.class, colonna.getColonnaQuestionario().getDomanda().getRisposta().getId());
						rispostaScelta.setRispostaQuestionario(rispostaSceltaQuestionario);
						rispostaScelta.setOpzioni(new HashSet<OpzioneRispostaVerbaleDTO>());
						for(OpzioneRispostaQuestionarioDTO opzioneQuestionario: rispostaSceltaQuestionario.getOpzioni()) {
							OpzioneRispostaVerbaleDTO opzioneRispostaVerbale = new OpzioneRispostaVerbaleDTO();
							opzioneRispostaVerbale.setRisposta(rispostaScelta);
							opzioneRispostaVerbale.setOpzioneQuestionario(opzioneQuestionario);
							
							rispostaScelta.getOpzioni().add(opzioneRispostaVerbale);
							//Non si gestiscono domande annidate perchè le opzioni delle domande figlie di una tabella non possono avere altre domande
						}
						session.save(rispostaScelta);
						colonna.getRisposte().add(rispostaScelta);
						risposte.add(rispostaScelta);
						break;
					case RispostaVerbaleDTO.TIPO_FORMULA:
						RispostaFormulaVerbaleDTO rispostaFormula =  new RispostaFormulaVerbaleDTO();
						RispostaFormulaQuestionarioDTO rispostaFormulaQuestionario = (RispostaFormulaQuestionarioDTO) session.get(RispostaFormulaQuestionarioDTO.class, colonna.getColonnaQuestionario().getDomanda().getRisposta().getId());
						rispostaFormula.setRispostaQuestionario(rispostaFormulaQuestionario);
						session.save(rispostaFormula);
						colonna.getRisposte().add(rispostaFormula);
						risposte.add(rispostaFormula);
						break;
					default:
						break;
					}
					
					session.update(colonna);
					index++;
			
			
			
			}
			transaction.commit();
			request.setCharacterEncoding("UTF-8");
			request.setAttribute("rispostaParent", risposta);
			request.setAttribute("risposte", risposte);
			request.setAttribute("colonne", colonne);
			request.setAttribute("hibernateSession", session);
			
		}
		
		
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/page/configurazioni/rigaTabella.jsp");
		dispatcher.forward(request,response);
	}
	
	
	
	
	
public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		Session session=SessionFacotryDAO.get().openSession();
		Transaction transaction = session.beginTransaction();
		PrintWriter out = response.getWriter();
		
		JsonObject myObj = new JsonObject();
		
		boolean ajax = true;
		try {
		String action = request.getParameter("action");
		
		if(action.equals("importa_excel")) {
			
			String rispostaId = request.getParameter("rispostaId");
			Integer rispostaidInt = Integer.parseInt(rispostaId);
			RispostaTabellaVerbaleDTO risposta = (RispostaTabellaVerbaleDTO) session.get(RispostaTabellaVerbaleDTO.class, rispostaidInt);
			
			
			ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());
			List<FileItem> items = null;
			
				items = uploadHandler.parseRequest(request);
			
			

			FileItem fileUploaded = null;
			for (FileItem item : items) {
				if (!item.isFormField()) {

					fileUploaded = item;
				}

			}
			
			File file = new File(Costanti.PATH_ROOT+"temp\\tempImportazione.xlsx");
	
			fileUploaded.write(file);

			FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file  
			//creating Workbook instance that refers to .xlsx file  
			XSSFWorkbook wb = new XSSFWorkbook(fis);   
			XSSFSheet sheet = wb.getSheetAt(0);     //creating a Sheet object to retrieve object  
			Iterator<Row> itr = sheet.iterator();    //iterating over excel file  
			ArrayList<ArrayList<String>> lista_risposte = new ArrayList<ArrayList<String>>();
			
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			
 			while (itr.hasNext())                 
			{  
				Row row = itr.next();  
				
				
				if(row.getRowNum()!=0) {
					  //iterating over each column  
					Iterator<Cell> cellIterator = row.cellIterator(); 
					ArrayList<String> risposte_riga = new ArrayList<String>();
					while (cellIterator.hasNext())   
					{  
						
						Cell cell = cellIterator.next();
						if(cell.getCellType() == Cell.CELL_TYPE_STRING ) {
							if(cell.getStringCellValue()!=null )
							{
								risposte_riga.add(cell.getStringCellValue());
							}
						}
						else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
							
							
							
								if(!DateUtil.isCellDateFormatted(cell)&&cell.getNumericCellValue() == Math.floor(cell.getNumericCellValue()) && !Double.isInfinite(cell.getNumericCellValue())) {
									risposte_riga.add(""+(int)cell.getNumericCellValue());
								}
								else if(DateUtil.isCellDateFormatted(cell)) {							
									risposte_riga.add(""+ df.format(cell.getDateCellValue()));						
								}
								else {
									risposte_riga.add(""+cell.getNumericCellValue());
								}
								
								
							
						}
						
					}
					lista_risposte.add(risposte_riga);
				}
				
			}
			
			List<ColonnaTabellaVerbaleDTO> colonne=new ArrayList<ColonnaTabellaVerbaleDTO>();
			List<RispostaVerbaleDTO> risposte=new ArrayList<RispostaVerbaleDTO>();

			colonne.addAll(risposta.getColonne());
			
			Collections.sort(colonne, new Comparator<ColonnaTabellaVerbaleDTO>() {
		        @Override
		        public int compare(ColonnaTabellaVerbaleDTO op2, ColonnaTabellaVerbaleDTO op1){
					int pos1=op1.getColonnaQuestionario().getPosizione().intValue();
					int pos2=op2.getColonnaQuestionario().getPosizione().intValue();
		            return  pos2 - pos1;
		        }
		    });
			
			
			myObj.addProperty("success", true);
			myObj.addProperty("messaggio", "Caricato con successo!");
			
		for (int i = 0; i < lista_risposte.size(); i++) {
			
			ArrayList<String> riga = lista_risposte.get(i);
			
			if(colonne.size()!=riga.size()) {
				myObj.addProperty("success", false);
				myObj.addProperty("messaggio", "Attenzione! Numero di colonne errato!");
				break;
			}else {
				int index = 0;
				for(ColonnaTabellaVerbaleDTO colonna: colonne){
					switch (colonna.getDomanda().getRisposta().getTipo()) {
					case RispostaVerbaleDTO.TIPO_TESTO:
						RispostaTestoVerbaleDTO rispostaTesto =  new RispostaTestoVerbaleDTO();
						RispostaTestoQuestionarioDTO rispostaQuestionario =(RispostaTestoQuestionarioDTO)session.get(RispostaTestoQuestionarioDTO.class, colonna.getColonnaQuestionario().getDomanda().getRisposta().getId());
						rispostaTesto.setRispostaQuestionario(rispostaQuestionario);
						rispostaTesto.setResponseValue(riga.get(index));
						session.save(rispostaTesto);
						colonna.getRisposte().add(rispostaTesto);
						risposte.add(rispostaTesto);
						break;
					case RispostaVerbaleDTO.TIPO_SCELTA:
						RispostaSceltaVerbaleDTO rispostaScelta = new RispostaSceltaVerbaleDTO();
						RispostaSceltaQuestionarioDTO rispostaSceltaQuestionario = (RispostaSceltaQuestionarioDTO) session.get(RispostaSceltaQuestionarioDTO.class, colonna.getColonnaQuestionario().getDomanda().getRisposta().getId());
						rispostaScelta.setRispostaQuestionario(rispostaSceltaQuestionario);
						rispostaScelta.setOpzioni(new HashSet<OpzioneRispostaVerbaleDTO>());
						for(OpzioneRispostaQuestionarioDTO opzioneQuestionario: rispostaSceltaQuestionario.getOpzioni()) {
							OpzioneRispostaVerbaleDTO opzioneRispostaVerbale = new OpzioneRispostaVerbaleDTO();
							opzioneRispostaVerbale.setRisposta(rispostaScelta);
							opzioneRispostaVerbale.setOpzioneQuestionario(opzioneQuestionario);
							if(opzioneQuestionario.getTesto().equals(riga.get(index))) {
								opzioneRispostaVerbale.setChecked(true);
							}
							
							rispostaScelta.getOpzioni().add(opzioneRispostaVerbale);
							//Non si gestiscono domande annidate perchè le opzioni delle domande figlie di una tabella non possono avere altre domande
						}
						session.save(rispostaScelta);
						colonna.getRisposte().add(rispostaScelta);
						risposte.add(rispostaScelta);
						break;
					case RispostaVerbaleDTO.TIPO_FORMULA:
						RispostaFormulaVerbaleDTO rispostaFormula =  new RispostaFormulaVerbaleDTO();
						RispostaFormulaQuestionarioDTO rispostaFormulaQuestionario = (RispostaFormulaQuestionarioDTO) session.get(RispostaFormulaQuestionarioDTO.class, colonna.getColonnaQuestionario().getDomanda().getRisposta().getId());
						rispostaFormula.setRispostaQuestionario(rispostaFormulaQuestionario);
						session.save(rispostaFormula);
						colonna.getRisposte().add(rispostaFormula);
						risposte.add(rispostaFormula);
						break;
					default:
						break;
					}
					
					session.update(colonna);
					index++;
				}
			
		
			}
			
			
		}
				
			transaction.commit();
			request.setCharacterEncoding("UTF-8");
			request.setAttribute("rispostaParent", risposta);
			request.setAttribute("risposte", risposte);
			request.setAttribute("colonne", colonne);
			request.setAttribute("hibernateSession", session);
			
			
		}
		
		
		
		
		out.print(myObj);
		
			
	} catch (Exception ex) {
		session.getTransaction().rollback();
		session.close();
		
		if(ajax) {
			ex.printStackTrace();
        	
        	request.getSession().setAttribute("exception", ex);
        	myObj = ECIException.callExceptionJsonObject(ex);
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

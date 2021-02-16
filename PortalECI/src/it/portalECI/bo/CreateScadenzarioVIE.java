package it.portalECI.bo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;

import TemplateReport.PivotTemplate;
import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.ClienteDTO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.DTO.VerbaleDTO;
import it.portalECI.Util.Costanti;

public class CreateScadenzarioVIE {
	
	
	public CreateScadenzarioVIE(List<VerbaleDTO> listaVerbali,String dateFom, String dateTo, Session session) throws Exception  {

		build(listaVerbali,dateFom, dateTo, session);
		
	}

	
	private void build(List<VerbaleDTO> listaVerbali,String dateFom, String dateTo, Session session) throws Exception {

		
		 InputStream file = PivotTemplate.class.getResourceAsStream("template_scadenzario_inail.xlsx");
		 

        XSSFWorkbook workbook = new XSSFWorkbook();         
           
		 XSSFSheet sheet0 = workbook.createSheet("Rinnovi scadenze");
		 
		 workbook.setSheetOrder("Rinnovi scadenze", 0);
		 workbook.setActiveSheet(0);
		 sheet0.setSelected(true);
		 
		 
		 sheet0.setMargin(Sheet.RightMargin, 0.39);
		 sheet0.setMargin(Sheet.LeftMargin, 0.39);
		 sheet0.setMargin(Sheet.TopMargin, 0.39);
		 sheet0.setMargin(Sheet.BottomMargin, 0.39);
		 sheet0.setMargin(Sheet.HeaderMargin, 0.157);
		 sheet0.setMargin(Sheet.FooterMargin, 0.39);		
		 Font headerFont = workbook.createFont();
	     headerFont.setBold(false);
	     headerFont.setFontHeightInPoints((short) 12);
	     headerFont.setColor(IndexedColors.BLACK.getIndex());		
		 
//	     CellStyle redStyle = workbook.createCellStyle();
//
//	     redStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
//	     redStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);	    
//	     redStyle.setBorderBottom(BorderStyle.MEDIUM);
//	     redStyle.setBorderTop(BorderStyle.MEDIUM);
//	     redStyle.setBorderLeft(BorderStyle.MEDIUM);
//	     redStyle.setBorderRight(BorderStyle.MEDIUM);
//	     redStyle.setAlignment(HorizontalAlignment.CENTER);
//	     redStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//	     redStyle.setFont(headerFont);
//	     
//	     CellStyle yellowStyle = workbook.createCellStyle();
//
//	     yellowStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
//	     yellowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);	    
//	     yellowStyle.setBorderBottom(BorderStyle.MEDIUM);
//	     yellowStyle.setBorderTop(BorderStyle.MEDIUM);
//	     yellowStyle.setBorderLeft(BorderStyle.MEDIUM);
//	     yellowStyle.setBorderRight(BorderStyle.MEDIUM);
//	     yellowStyle.setAlignment(HorizontalAlignment.CENTER);
//	     yellowStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//	     yellowStyle.setFont(headerFont);
//	     
	     CellStyle greenStyle = workbook.createCellStyle();

	     greenStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
	     greenStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);	    
	     greenStyle.setBorderBottom(BorderStyle.THIN);
	     greenStyle.setBorderTop(BorderStyle.THIN);
	     greenStyle.setBorderLeft(BorderStyle.THIN);
	     greenStyle.setBorderRight(BorderStyle.THIN);
	     greenStyle.setAlignment(HorizontalAlignment.CENTER);
	     greenStyle.setVerticalAlignment(VerticalAlignment.CENTER);
	     greenStyle.setFont(headerFont);
		
	     CellStyle titleStyle = workbook.createCellStyle();
	        
	     titleStyle.setBorderBottom(BorderStyle.THIN);
	     titleStyle.setBorderTop(BorderStyle.THIN);
	     titleStyle.setBorderLeft(BorderStyle.THIN);
	     titleStyle.setBorderRight(BorderStyle.THIN);
	     titleStyle.setAlignment(HorizontalAlignment.CENTER);
	     titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
	     titleStyle.setFont(headerFont);
	     
	     
		 Row rowHeader = sheet0.createRow(0);
		 
		// rowHeader.setHeight((short)1000);
		 for(int j = 0; j<19; j++) {
			 rowHeader.createCell(j);
			 
			 rowHeader.getCell(j).setCellStyle(greenStyle);
		 }
		 
	
		 sheet0.getRow(0).getCell(0).setCellValue("Tipo verifica");
		 
		 sheet0.getRow(0).getCell(1).setCellValue("Motivo straordinaria");
		 
		 sheet0.getRow(0).getCell(2).setCellValue("Matricola impianto");		 
		 		 
		 sheet0.getRow(0).getCell(3).setCellValue("Esercente");		 
		 
		 sheet0.getRow(0).getCell(4).setCellValue("Codice verbale");
		 
		 sheet0.getRow(0).getCell(5).setCellValue("Verificatore");		 
		 		 
		 sheet0.getRow(0).getCell(6).setCellValue("Tipologia verifica");		 
		 
		 sheet0.getRow(0).getCell(7).setCellValue("Cliente");		 
		 
		 sheet0.getRow(0).getCell(8).setCellValue("Indirizzo cliente");
		 
		 sheet0.getRow(0).getCell(9).setCellValue("Località cliente");
		 
		 sheet0.getRow(0).getCell(10).setCellValue("Ubicazione impianto");
		 
		 sheet0.getRow(0).getCell(11).setCellValue("Località impianto");
		 
		 sheet0.getRow(0).getCell(12).setCellValue("Provincia");
		 
		 sheet0.getRow(0).getCell(13).setCellValue("Codice commessa");
		 
		 sheet0.getRow(0).getCell(14).setCellValue("Data verifica");
		 
		 sheet0.getRow(0).getCell(15).setCellValue("Frequenza (anni)");
		 
		 sheet0.getRow(0).getCell(16).setCellValue("Prossima verifica");
		 
		 sheet0.getRow(0).getCell(17).setCellValue("Ore uomo");
		 
		 sheet0.getRow(0).getCell(18).setCellValue("Esito");
		 
  
	     int row_index = 0;	        
	     for (int i = 0; i<listaVerbali.size();i++) {
	    	 
	    	 Row row = sheet0.createRow(1+row_index);
	    	 
	    	 VerbaleDTO verbale = listaVerbali.get(i);
	    	 
	    	 int col = 0;
	    	 
	    	 Cell cell = row.createCell(col);
	    	 
	    	 if(verbale.getMotivo_verifica()==1) {
	    		 
	    			 cell.setCellValue("Verifica periodica");
	    		
	    	 }
	    	 else if(verbale.getMotivo_verifica()>1) {
	    		 
	    		 cell.setCellValue("Verifica straordinaria");	 
	    	 }
	    	 else {
	    		 cell.setCellValue("");
	    	 }
	    	 
	    	 col++;
	    	 cell = row.createCell(col);
	    	 
	    	 if(verbale.getMotivo_verifica()==2) {
	    		 
    			 cell.setCellValue("Verifica periodica con esito negativo");
    		
	    	 }
	    	 else if(verbale.getMotivo_verifica()==3) {
    		 
	    		 cell.setCellValue("Modifiche sostanziali all'impianto");	 
    		 
	    	 }
	    	 else if(verbale.getMotivo_verifica()==4) {
	    		 
	    		 cell.setCellValue("Richiesta del datore di lavoro");	 
	    		 
		     }
	    	 else {
	    		 
	    		 cell.setCellValue("");
	    		 
	    	 }
	    	 
	    	 col++;
	    	 cell = row.createCell(col);
	    	 
	    	 if(verbale.getMatricola_vie()!=null) {
	    		 cell.setCellValue(verbale.getMatricola_vie());
	    	 }else {
	    		 cell.setCellValue("");
	    	 }
	    	 
	    	 col++;
	    	 cell = row.createCell(col);
	    	 
	    	 if(verbale.getEsercente()!=null) {
	    		 cell.setCellValue(verbale.getEsercente());
	    	 }else {
	    		 cell.setCellValue("");
	    	 }
	    	 
	    	 col++;
	    	 cell = row.createCell(col);	    	 
	    	 
	    	 if(verbale.getNumeroVerbale()!=null) {
	    		 cell.setCellValue(verbale.getNumeroVerbale());
	    	 }else {
	    		 cell.setCellValue("");
	    	 }
	    	 
	    	 col++;
	    	 cell = row.createCell(col);	
	    	 
	    	 if(verbale.getIntervento().getTecnico_verificatore()!=null) {
	    		 cell.setCellValue(verbale.getIntervento().getTecnico_verificatore().getNominativo() +" ("+verbale.getIntervento().getTecnico_verificatore().getCodice()+")");
	    	 }else {
	    		 cell.setCellValue("");
	    	 }
	    	 
	    	 col++;
	    	 cell = row.createCell(col);
	    	 
	    	 if(verbale.getTipologia_verifica()!=0) {
	    		 cell.setCellValue(verbale.getTipologia_verifica());
	    	 }else {
	    		 cell.setCellValue("");
	    	 }
	    	 
	    	 col++;
	    	 cell = row.createCell(col);
	    	 
	    	 ClienteDTO cliente = GestioneAnagraficaRemotaBO.getClienteById(verbale.getIntervento().getId_cliente()+"");
	    	 
	    	 if(cliente!=null && cliente.getNome()!=null) {					
				cell.setCellValue(cliente.getNome());
	    	 }else {				
				cell.setCellValue("");
	    	 }
	    	 
	    	 col++;
    		 cell = row.createCell(col);
    		 if(cliente!=null && cliente.getIndirizzo()!=null) {
				cell.setCellValue(cliente.getIndirizzo());
    		 }else {
				cell.setCellValue("");
    		 }
			 
    		 col++;
    		 cell = row.createCell(col);
			
    		 if(cliente!=null && cliente.getCitta()!=null) {						
				cell.setCellValue(cliente.getCitta());
    		 }else {
				cell.setCellValue("");
    		 }
	    	 
    		 col++;
   		 	 cell = row.createCell(col);
	    	 
   		 	 
   		 	 String indirizzo_impianto = "";
   		 	 String localita_impianto = "";
   		     String provincia_impianto = "";
   		 	 
   		 	 if(verbale.getSedeUtilizzatore()!=null && !verbale.getSedeUtilizzatore().equals("")) { 
   		 		indirizzo_impianto = verbale.getSedeUtilizzatore().split("-")[0];
   		 		
   		 		if(verbale.getSedeUtilizzatore().split("-").length>1) {
   		 			localita_impianto = verbale.getSedeUtilizzatore().split("-")[2].substring(0, verbale.getSedeUtilizzatore().split("-")[2].length()-4);
   		 			provincia_impianto = verbale.getSedeUtilizzatore().split("-")[2].substring(verbale.getSedeUtilizzatore().split("-")[2].length()-4, verbale.getSedeUtilizzatore().split("-")[2].length()).replace("(", "").replace(")", "");	
   		 		}
   		 		
   		 	 }
   		 	   	
   		 	 cell.setCellValue(indirizzo_impianto); 
   		 	 
   		 	 col++;
  		 	 cell = row.createCell(col);  
  		 	 
  		     cell.setCellValue(localita_impianto);
  		   
  		     col++;
		 	 cell = row.createCell(col);  
		 	 
		     cell.setCellValue(provincia_impianto); 
   		 	
		     col++;
		 	 cell = row.createCell(col);  
		 	 
		 	 cell.setCellValue(verbale.getIntervento().getIdCommessa().split("_")[1]+verbale.getIntervento().getIdCommessa().split("_")[2].substring(0,verbale.getIntervento().getIdCommessa().split("_")[2].length()-3));
   		 	
		 	 col++;
		 	 cell = row.createCell(col);  
   		 	
		 	 Date data_prossima_verifica = null;
		 	 SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		 	 
		 	cell.setCellValue(df.format(verbale.getData_verifica())+""); 		 
		 	
   		 	 if(verbale.getMotivo_verifica()<2 && verbale.getData_verifica()!=null) {   		 		
   		 		
   		 		if(verbale.getFrequenza()!=0) {
   		 			
   		 			Calendar c = Calendar.getInstance();
   		 			c.setTime(verbale.getData_verifica());
   		 			c.add(Calendar.YEAR, verbale.getFrequenza());
   		 			
   		 			data_prossima_verifica = c.getTime();
   		 			
   		 		}
   		 	 }else {
   		 		cell.setCellValue("");
   		 	 }
   		 	
   		 	 col++;
		 	 cell = row.createCell(col); 
		 	 
   		 	 if(verbale.getMotivo_verifica()<2 && verbale.getFrequenza()!=0 ) {
   		 		cell.setCellValue(verbale.getFrequenza()); 
   		 	 }else {
   		 		cell.setCellValue("");
   		 	 }
   		 	 
   		 	 
   		 	 col++;
		 	 cell = row.createCell(col); 
		 	 
		 	 if(data_prossima_verifica!=null) {
		 		cell.setCellValue(""+df.format(data_prossima_verifica));
		 	 }else {
		 		cell.setCellValue("");
		 	 }
		 	 
		 	 col++;
		 	 cell = row.createCell(col); 
		 	 
		 	 if(verbale.getOre_uomo()!=null) {
		 		cell.setCellValue(verbale.getOre_uomo()); 
		 	 }else {
		 		cell.setCellValue("");
		 	 }
		 	col++;
		 	 cell = row.createCell(col); 
		 	 
		 	if(verbale.getEsito()!=null && !verbale.getEsito().equals("")) {
		 		if(verbale.getEsito().equals("P")) {
		 			cell.setCellValue("Positivo");
		 		}else {
		 			cell.setCellValue("Negativo");	
		 		}
		 		 
		 	 }else {
		 		cell.setCellValue("");
		 	 }
		 	 
					row_index++;
	    	
	    		 
		}
	     
	     
	    	 for(int j = 0; j<20;j++) {
	    		 sheet0.autoSizeColumn(j);
	    	 }
	     

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			
			Date from = df.parse(dateFom);
			Date to = df.parse(dateTo);
	     
	 		//String path = "C:\\Users\\antonio.dicivita\\Desktop\\";
	 		String path = Costanti.PATH_ROOT + "ScadenzarioVIE\\";
	 		
			df = new SimpleDateFormat("ddMMyyyy");
		
	 		
	 		if(!new File(path).exists()) {
	 			new File(path).mkdirs();
	 		}
	        FileOutputStream fileOut = new FileOutputStream(path +"SCADVIE"+df.format(from)+ df.format(to)+".xlsx");
	        workbook.write(fileOut);
	        fileOut.close();

	        workbook.close();
	  
	}
	
	
	
	public static void main(String[] args) throws Exception {
		System.out.println("START");
		Session session=SessionFacotryDAO.get().openSession();
		
		session.beginTransaction();
		
		UtenteDTO user = GestioneUtenteBO.getUtenteById(""+11, session);
		
		String dateFrom = "2020-09-01";
		String dateTo = "2020-09-23";		
		
		
		
		List<VerbaleDTO> listaVerbali =GestioneVerbaleBO.getListaVerbaliDate(session,user, dateFrom, dateTo);
		
		new CreateScadenzarioVIE(listaVerbali, dateFrom, dateTo, session);
		
		session.close();
		System.out.println("END");
	}
}

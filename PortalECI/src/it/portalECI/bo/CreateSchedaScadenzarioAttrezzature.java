package it.portalECI.bo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;

import TemplateReport.PivotTemplate;
import it.portalECI.DTO.AttrezzaturaDTO;
import it.portalECI.DTO.ClienteDTO;
import it.portalECI.Util.Costanti;

public class CreateSchedaScadenzarioAttrezzature {

	public CreateSchedaScadenzarioAttrezzature(ArrayList<AttrezzaturaDTO> listaAttrezzature, String dateFrom, String dateTo, Session session) throws Exception {
		build(listaAttrezzature,dateFrom, dateTo, session);	
	}
	
	private void build(ArrayList<AttrezzaturaDTO> listaAttrezzature, String dateFrom, String dateTo, Session session) throws Exception {
		
		 InputStream file = PivotTemplate.class.getResourceAsStream("template_scadenzario_inail.xlsx");
		 

	        XSSFWorkbook workbook = new XSSFWorkbook();         
	           
			 XSSFSheet sheet0 = workbook.createSheet("Scadenze attrezzature");
			 
			 workbook.setSheetOrder("Scadenze attrezzature", 0);
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
		     
		     
			 Row rowTitle = sheet0.createRow(0);
			 Row rowHeader = sheet0.createRow(1);
			 
			// rowHeader.setHeight((short)1000);
			 
			
			 
			 for(int j = 0; j<12; j++) {
				 rowHeader.createCell(j);
				 
				 rowHeader.getCell(j).setCellStyle(greenStyle);
				 rowTitle.createCell(j);
				 rowTitle.getCell(j).setCellStyle(greenStyle);
			 }
			 
			 sheet0.addMergedRegion(CellRangeAddress.valueOf("A1:L1"));	
			 
			 DateFormat df = new SimpleDateFormat("yyyy-MM-dd");		
			 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			 
			 Date dStart = df.parse(dateFrom);
			 Date dEnd = df.parse(dateTo);
			 long time = dEnd.getTime()-3600000;
			 dEnd.setTime(time);
			 sheet0.getRow(0).getCell(0).setCellValue("Attrezzature in scadenza tra il "+sdf.format(dStart)+" e il "+sdf.format(dEnd));
			 
			 sheet0.getRow(1).getCell(0).setCellValue("Matricola Inail");
			 
			 sheet0.getRow(1).getCell(1).setCellValue("Numero di Fabbrica");
			 
			 sheet0.getRow(1).getCell(2).setCellValue("Gruppo");
			 
			 sheet0.getRow(1).getCell(3).setCellValue("Descrizione");		 
			 		 
			 sheet0.getRow(1).getCell(4).setCellValue("Cliente");	
			 
			 sheet0.getRow(1).getCell(5).setCellValue("Sede cliente");
			 
			 sheet0.getRow(1).getCell(6).setCellValue("Verifica funzionamento");
			 
			 sheet0.getRow(1).getCell(7).setCellValue("Verifica integrità");
			 
			 sheet0.getRow(1).getCell(8).setCellValue("Verifica interna");
			 
			 
			 sheet0.getRow(1).getCell(9).setCellValue("Note tecniche");
			 
			 sheet0.getRow(1).getCell(10).setCellValue("Note generiche");
			 
			 sheet0.getRow(1).getCell(11).setCellValue("Codici Milestone");
			 
		     int row_index = 0;	        
		     for (int i = 0; i<listaAttrezzature.size();i++) {
		    	 
		    	 AttrezzaturaDTO attrezzatura = listaAttrezzature.get(i);
		    	 
		    	 if(attrezzatura.getObsoleta()==0) {
		    		 
		    		 Row row = sheet0.createRow(2+row_index);
			    	 
				    	
			    	 
			    	 int col = 0;
			    	 
			    	 Cell cell = row.createCell(col);
			    	 
			    	 if(attrezzatura.getMatricola_inail()!=null) {		    		 
			    		 cell.setCellValue(attrezzatura.getMatricola_inail());		    		
			    	 }
			    	 else {
			    		 cell.setCellValue("");
			    	 }
			    	 
			    	 col++;
			    	 cell = row.createCell(col);
			    	 
			    	 if(attrezzatura.getNumero_fabbrica()!=null) {		    		 
		    			 cell.setCellValue(attrezzatura.getNumero_fabbrica());	    		
			    	 }		    	
			    	 else {		    		 
			    		 cell.setCellValue("");		    		 
			    	 }
			    	 
			    	 
			    	 col++;
			    	 cell = row.createCell(col);
			    	 
			    	 if(attrezzatura.getTipo_attivita()!=null) {		    		 
		    			 cell.setCellValue(attrezzatura.getTipo_attivita());	    		
			    	 }		    	
			    	 else {		    		 
			    		 cell.setCellValue("");		    		 
			    	 }
			    	 
			    	 col++;
			    	 cell = row.createCell(col);
			    	 
			    	 if(attrezzatura.getDescrizione()!=null) {
			    		 cell.setCellValue(attrezzatura.getDescrizione());
			    	 }else {
			    		 cell.setCellValue("");
			    	 }
			    	 
			    	 col++;
			    	 cell = row.createCell(col);
			    	 
			    	 if(attrezzatura.getNome_cliente()!=null) {
			    		 cell.setCellValue(attrezzatura.getNome_cliente());
			    	 }else {
			    		 cell.setCellValue("");
			    	 }
			    	 
			    	 
			    	 col++;
			    	 cell = row.createCell(col);
			    	 
			    	 String sede = "";
			    	 if(attrezzatura.getId_sede()!=0) {		
			    		 
			    		 if(attrezzatura.getIndirizzo_div()!=null) {
			    			 sede = attrezzatura.getNome_sede() +" - "+attrezzatura.getIndirizzo_div()+" - "+attrezzatura.getCap_div()+" - "+attrezzatura.getComune_div()+" ("+attrezzatura.getProvincia_div()+")";
			    		 }else {
			    			 sede = attrezzatura.getNome_sede() +" - "+attrezzatura.getIndirizzo()+" - "+attrezzatura.getCap()+" - "+attrezzatura.getComune()+" ("+attrezzatura.getProvincia()+")";
			    		 }
			    		 
			    		 cell.setCellValue(sede);
			    	 }else {
			    		 
			    		 if(attrezzatura.getIndirizzo()!=null) {
			    			 sede = attrezzatura.getIndirizzo()+" - "+attrezzatura.getCap()+" - "+attrezzatura.getComune()+" ("+attrezzatura.getProvincia()+")";
			    			 cell.setCellValue(sede);
			    		 }else {
			    			 cell.setCellValue("");
			    		 }
			    		 
			    	 }
			    	 
			    	 col++;
			    	 cell = row.createCell(col);	    	 
		    	
			    	 
			    	 if(attrezzatura.getData_prossima_verifica_funzionamento()!=null && ((attrezzatura.getData_prossima_verifica_funzionamento().after(df.parse(dateFrom)) && attrezzatura.getData_prossima_verifica_funzionamento().before(df.parse(dateTo))) 
			    			 || attrezzatura.getData_prossima_verifica_funzionamento().equals(df.parse(dateFrom)))) {
			    		 
			    		 cell.setCellValue(""+sdf.format(attrezzatura.getData_prossima_verifica_funzionamento()));
			    		 
			    	 }else {
			    		 cell.setCellValue("");
			    	 }
			    	 
			    	 col++;
			    	 cell = row.createCell(col);
			    	 
			    	  if(attrezzatura.getData_prossima_verifica_integrita()!=null && ((attrezzatura.getData_prossima_verifica_integrita().after(df.parse(dateFrom)) && attrezzatura.getData_prossima_verifica_integrita().before(df.parse(dateTo)))
			    		 || attrezzatura.getData_prossima_verifica_integrita().equals(df.parse(dateFrom)))) {
			    		 
			    		 cell.setCellValue(""+sdf.format(attrezzatura.getData_prossima_verifica_integrita()));
			    		 
			    	 }else{
			    		 cell.setCellValue("");
			    	 }
			    	  
			    	  col++;
				      cell = row.createCell(col);
			    	 
			    	 
			    	  if(attrezzatura.getData_prossima_verifica_interna()!=null && ((attrezzatura.getData_prossima_verifica_interna().after(df.parse(dateFrom)) && attrezzatura.getData_prossima_verifica_interna().before(df.parse(dateTo))) 
			    		 || attrezzatura.getData_prossima_verifica_interna().equals(df.parse(dateFrom)))) {
			    		 
			    		 cell.setCellValue(""+sdf.format(attrezzatura.getData_prossima_verifica_interna()));
			    		 
			    	 }else {
			    		 cell.setCellValue("");
			    	 }
			    	 
			    	  col++;
				    	 cell = row.createCell(col);
				    	 
				    	 if(attrezzatura.getNote_tecniche()!=null) {		    		 
				    		 
				    		 cell.setCellValue(attrezzatura.getNote_tecniche());
				    	 }else {
				    		 cell.setCellValue("");
				    	 }
			    	  
				    	 col++;
				    	 cell = row.createCell(col);
				    	 
				    	 if(attrezzatura.getNote_generiche()!=null) {		    		 
				    		 
				    		 cell.setCellValue(attrezzatura.getNote_generiche());
				    	 }else {
				    		 cell.setCellValue("");
				    	 }
				    	 
				    	 col++;
				    	 cell = row.createCell(col);
				    	 
				    	 
				    	 if(attrezzatura.getCodice_milestone()!=null) {		    		 
				    		 
				    		 cell.setCellValue(attrezzatura.getCodice_milestone());
				    	 }else {
				    		 cell.setCellValue("");
				    	 }
			    	  
			    	 row_index++;
		    		 
		    	 }
		    
		     }
		     
		     
		     
	    	 for(int j = 0; j<20;j++) {
	    		 sheet0.autoSizeColumn(j);
	    	 }
	     
		     
				Date from = df.parse(dateFrom);
				Date to = df.parse(dateTo);
		     
		 		//String path = "C:\\Users\\antonio.dicivita\\Desktop\\";
		 		String path = Costanti.PATH_ROOT + "ScadenzarioAttrezzature\\";
		 		
				df = new SimpleDateFormat("ddMMyyyy");
			
		 		
		 		if(!new File(path).exists()) {
		 			new File(path).mkdirs();
		 		}
		        FileOutputStream fileOut = new FileOutputStream(path +"ScadenzarioAttrezzature.xlsx");
		        workbook.write(fileOut);
		        fileOut.close();

		        workbook.close();
		
		
	}
}

package it.portalECI.bo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;

import TemplateReport.PivotTemplate;
import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.ClienteDTO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.DTO.VerbaleDTO;
import it.portalECI.Util.Costanti;
public class CreateScadenzarioInail {
	
	public CreateScadenzarioInail(List<VerbaleDTO> listaVerbali,String dateFom, String dateTo, Session session) throws Exception  {

		build(listaVerbali,dateFom, dateTo, session);
		
	}

	
	private void build(List<VerbaleDTO> listaVerbali,String dateFom, String dateTo, Session session) throws Exception {
		
		
		 InputStream file = PivotTemplate.class.getResourceAsStream("template_scadenzario_inail.xlsx");


         XSSFWorkbook workbook = new XSSFWorkbook(file);
         
            
		 XSSFSheet sheet0 = workbook.createSheet("Dati");
		 
		 workbook.setSheetOrder("Dati", 0);
		 workbook.setSheetOrder("informazioni", 1);
		 workbook.setSheetOrder("specifica apparecchi", 2);
		 workbook.setActiveSheet(0);
		 sheet0.setSelected(true);
		 workbook.getSheetAt(1).setSelected(false);
		 
		 sheet0.setMargin(Sheet.RightMargin, 0.39);
		 sheet0.setMargin(Sheet.LeftMargin, 0.39);
		 sheet0.setMargin(Sheet.TopMargin, 0.39);
		 sheet0.setMargin(Sheet.BottomMargin, 0.39);
		 sheet0.setMargin(Sheet.HeaderMargin, 0.157);
		 sheet0.setMargin(Sheet.FooterMargin, 0.39);		
		// sheet0.getPrintSetup().setPaperSize(PrintSetup.A4_PAPERSIZE);
		 //sheet0.getPrintSetup().setScale((short)85);
		 Font headerFont = workbook.createFont();
	     headerFont.setBold(true);
	     headerFont.setFontHeightInPoints((short) 12);
	     headerFont.setColor(IndexedColors.BLACK.getIndex());		
		 
	     CellStyle redStyle = workbook.createCellStyle();

	     redStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
	     redStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);	    
	     redStyle.setBorderBottom(BorderStyle.MEDIUM);
	     redStyle.setBorderTop(BorderStyle.MEDIUM);
	     redStyle.setBorderLeft(BorderStyle.MEDIUM);
	     redStyle.setBorderRight(BorderStyle.MEDIUM);
	     redStyle.setAlignment(HorizontalAlignment.CENTER);
	     redStyle.setVerticalAlignment(VerticalAlignment.CENTER);
	     redStyle.setFont(headerFont);
	     
	     CellStyle yellowStyle = workbook.createCellStyle();

	     yellowStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
	     yellowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);	    
	     yellowStyle.setBorderBottom(BorderStyle.MEDIUM);
	     yellowStyle.setBorderTop(BorderStyle.MEDIUM);
	     yellowStyle.setBorderLeft(BorderStyle.MEDIUM);
	     yellowStyle.setBorderRight(BorderStyle.MEDIUM);
	     yellowStyle.setAlignment(HorizontalAlignment.CENTER);
	     yellowStyle.setVerticalAlignment(VerticalAlignment.CENTER);
	     yellowStyle.setFont(headerFont);
	     
	     CellStyle greenStyle = workbook.createCellStyle();

	     greenStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
	     greenStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);	    
	     greenStyle.setBorderBottom(BorderStyle.MEDIUM);
	     greenStyle.setBorderTop(BorderStyle.MEDIUM);
	     greenStyle.setBorderLeft(BorderStyle.MEDIUM);
	     greenStyle.setBorderRight(BorderStyle.MEDIUM);
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
		 
		 rowHeader.setHeight((short)1000);
		 for(int j = 0; j<47; j++) {
			 rowHeader.createCell(j);
			 
			 if(j==8 || j==21|| j==22|| j==23|| j==25) {
				 rowHeader.getCell(j).setCellStyle(yellowStyle);
			 }else if(j==29){
				 rowHeader.getCell(j).setCellStyle(greenStyle);
			 }else {
				 rowHeader.getCell(j).setCellStyle(redStyle);
			 }
		
			 
		 }
		 
		 sheet0.addMergedRegion(CellRangeAddress.valueOf("A1:H1"));		 
		 
		 sheet0.getRow(0).getCell(0).setCellValue("Dati sempre obbligatori");
		 
		 sheet0.getRow(0).getCell(8).setCellValue("dati obbligatori solo nel caso esito_verifica sia sospeso");
		 
		 sheet0.addMergedRegion(CellRangeAddress.valueOf("J1:U1"));
		 
		 sheet0.getRow(0).getCell(9).setCellValue("Dati sempre obbligatori");		 
		 
		 sheet0.addMergedRegion(CellRangeAddress.valueOf("V1:X1"));
		 sheet0.getRow(0).getCell(21).setCellValue("Dati obbligatori per attrezzature GVR vedi anche (Informazioni)");		 
		 
		 sheet0.getRow(0).getCell(24).setCellValue("Dato obbligatorio");
		 
		 sheet0.getRow(0).getCell(25).setCellValue("Dati obbligatori per attrezzature SC vedi anche (Informazioni");		 
		 
		 sheet0.addMergedRegion(CellRangeAddress.valueOf("AA1:AC1"));
		 
		 sheet0.getRow(0).getCell(26).setCellValue("Dati obbligatori");
		 
		 
		 sheet0.getRow(0).getCell(29).setCellValue("Dato opzionale");		 
		 
		 sheet0.getRow(0).getCell(30).setCellValue("Dati obbligatori");
		 
		 sheet0.addMergedRegion(CellRangeAddress.valueOf("AE1:AT1"));
		 
		 
		 
		 
		 sheet0.getRow(0).getCell(46).setCellValue("Dato obbligatorio");
		 
		 Row rowTitle = sheet0.createRow(1);
		 		 
		 
		 rowTitle.createCell(0).setCellValue("eff_verifica");
		 rowTitle.createCell(1).setCellValue("anno Matricola");
		 rowTitle.createCell(2).setCellValue("cod.att. Matricola");
		 rowTitle.createCell(3).setCellValue("numero Matricola");
		 rowTitle.createCell(4).setCellValue("provincia Matricola");
		 rowTitle.createCell(5).setCellValue("Matricola");
		 rowTitle.createCell(6).setCellValue("esito_verifica");
		 rowTitle.createCell(7).setCellValue("tipo_verifica");
		 rowTitle.createCell(8).setCellValue("sospensione");
		 rowTitle.createCell(9).setCellValue("data_rilascio");
		 rowTitle.createCell(10).setCellValue("data_pvp");
		 rowTitle.createCell(11).setCellValue("dl_rag_sociale");
		 rowTitle.createCell(12).setCellValue("dl_indirizzo");
		 rowTitle.createCell(13).setCellValue("dl_comune");
		 rowTitle.createCell(14).setCellValue("dl_cap");
		 rowTitle.createCell(15).setCellValue("dl_prov");
		 rowTitle.createCell(16).setCellValue("dl_reg");
		 rowTitle.createCell(17).setCellValue("dl_cod_fisc");
		 rowTitle.createCell(18).setCellValue("dl_part_iva");
		 rowTitle.createCell(19).setCellValue("attr_gruppo");
		 rowTitle.createCell(20).setCellValue("tipo_attr");
		 rowTitle.createCell(21).setCellValue("tipo_attr_GVR");
		 rowTitle.createCell(22).setCellValue("tipo_verifica_GVR");
		 rowTitle.createCell(23).setCellValue("Sogg_Messa_Servizio_GVR");
		 rowTitle.createCell(24).setCellValue("ID Specifica");
		 rowTitle.createCell(25).setCellValue("n_Panieri_Idroestrattori");
		 rowTitle.createCell(26).setCellValue("modello_attr");
		 rowTitle.createCell(27).setCellValue("num_fabbrica");
		 rowTitle.createCell(28).setCellValue("marcatura_CE");
		 rowTitle.createCell(29).setCellValue("num_id_ON");
		 rowTitle.createCell(30).setCellValue("sv_rag_sociale");
		 rowTitle.createCell(31).setCellValue("sv_nome_tecn");
		 rowTitle.createCell(32).setCellValue("sv_cognome_tecn");
		 rowTitle.createCell(33).setCellValue("sv_CF_tecn");
		 rowTitle.createCell(34).setCellValue("tariffa_app");
		 rowTitle.createCell(35).setCellValue("tariffa_regolare");
		 rowTitle.createCell(36).setCellValue("contributo");
		 rowTitle.createCell(37).setCellValue("attr_indirizzo");
		 rowTitle.createCell(38).setCellValue("attr_comune");
		 rowTitle.createCell(39).setCellValue("attr_cap");
		 rowTitle.createCell(40).setCellValue("attr_provincia");
		 rowTitle.createCell(41).setCellValue("attr_regione");
		 rowTitle.createCell(42).setCellValue("fattura");
		 rowTitle.createCell(43).setCellValue("verbale");
		 rowTitle.createCell(44).setCellValue("scheda_tecnica");
		 rowTitle.createCell(45).setCellValue("user_inserimento");
		 rowTitle.createCell(46).setCellValue("cs_ragionesociale");
		 
		 for(int j = 0; j<47; j++) {
			 rowTitle.getCell(j).setCellStyle(titleStyle);
		 }
	     	        
	     	        
	     for (int i = 0; i<listaVerbali.size();i++) {
	    	 
	    	 if(listaVerbali.get(i).getAttrezzatura()!=null) {
	    		 
	    		 Row row = sheet0.createRow(2+i);
	    		 
	    		 int col = 0;
	    		 
	    		 Cell cell = row.createCell(col);
	    		 
	    		 cell.setCellValue("");
	    		 col++;
	    		 cell = row.createCell(col);
	    		 if(listaVerbali.get(i).getAttrezzatura()!=null && listaVerbali.get(i).getAttrezzatura().getMatricola_inail().split("/").length>0) {
						cell.setCellValue(listaVerbali.get(i).getAttrezzatura().getMatricola_inail().split("/")[0]);
	    		 }else {
						cell.setCellValue("");
	    		 }
	    		 
	    		 col++;
	    		 cell = row.createCell(col);
	    		 if(listaVerbali.get(i).getAttrezzatura()!=null && listaVerbali.get(i).getAttrezzatura().getMatricola_inail().split("/").length>1) {
						cell.setCellValue(listaVerbali.get(i).getAttrezzatura().getMatricola_inail().split("/")[1]);
					}else {
						cell.setCellValue("");
					}
	    		 col++;
	    		 cell = row.createCell(col);	
					if(listaVerbali.get(i).getAttrezzatura()!=null && listaVerbali.get(i).getAttrezzatura().getMatricola_inail().split("/").length>2) {
						cell.setCellValue(listaVerbali.get(i).getAttrezzatura().getMatricola_inail().split("/")[2]);
					}else {
						cell.setCellValue("");
					}
					 col++;
		    		 cell = row.createCell(col);	
					if(listaVerbali.get(i).getAttrezzatura()!=null && listaVerbali.get(i).getAttrezzatura().getMatricola_inail().split("/").length>3) {
						cell.setCellValue(listaVerbali.get(i).getAttrezzatura().getMatricola_inail().split("/")[3]);
					}else {
						cell.setCellValue("");
					}
					
					 col++;
		    		 cell = row.createCell(col);
					if(listaVerbali.get(i).getAttrezzatura()!=null) {
						cell.setCellValue(listaVerbali.get(i).getAttrezzatura().getMatricola_inail());
					}else {
						cell.setCellValue("");
					}
					 col++;
		    		 cell = row.createCell(col);
					cell.setCellValue(""); //ESITO VERIFICA
					 col++;
		    		 cell = row.createCell(col);
					cell.setCellValue(""); //TIPO VERIFICA
					 col++;
		    		 cell = row.createCell(col);
					cell.setCellValue(""); //SOSPENSIONE
					 col++;
		    		 cell = row.createCell(col);
					cell.setCellValue(""); //DATA RILASCIO
					 col++;
		    		 cell = row.createCell(col);
					cell.setCellValue(""); //DATA PROSSIMA VERIFICA PERIODICA
					 col++;
		    		 cell = row.createCell(col);
					
					ClienteDTO cliente = GestioneAnagraficaRemotaBO.getClienteById(listaVerbali.get(i).getIntervento().getId_cliente()+"");
										
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
					if(cliente!=null && cliente.getCap()!=null) {						
						cell.setCellValue(cliente.getCap());
					}else {
						cell.setCellValue("");
					}
					 col++;
		    		 cell = row.createCell(col);
					if(cliente!=null && cliente.getProvincia()!=null) {						
						cell.setCellValue(cliente.getProvincia());
					}else {
						cell.setCellValue("");
					}
					 col++;
		    		 cell = row.createCell(col);
					cell.setCellValue(""); //DL REGIONE
					 col++;
		    		 cell = row.createCell(col);
					if(cliente!=null && cliente.getCf()!=null) {						
						cell.setCellValue(cliente.getCf());
					}else {
						cell.setCellValue("");
					}
					col++;
		    		 cell = row.createCell(col);
					if(cliente!=null && cliente.getPartita_iva()!=null) {						
						cell.setCellValue(cliente.getPartita_iva());
					}else {
						cell.setCellValue("");
					}
					 col++;
		    		 cell = row.createCell(col);
					if(listaVerbali.get(i).getAttrezzatura()!=null && listaVerbali.get(i).getAttrezzatura().getTipo_attivita()!=null) {						
						cell.setCellValue(listaVerbali.get(i).getAttrezzatura().getTipo_attivita());
					}else {
						cell.setCellValue("");
					}
					 col++;
		    		 cell = row.createCell(col);
					if(listaVerbali.get(i).getAttrezzatura()!=null && listaVerbali.get(i).getAttrezzatura().getTipo_attrezzatura()!=null) {						
						cell.setCellValue(listaVerbali.get(i).getAttrezzatura().getTipo_attrezzatura());
					}else {
						cell.setCellValue("");
					}
					 col++;
		    		 cell = row.createCell(col);
					if(listaVerbali.get(i).getAttrezzatura()!=null && listaVerbali.get(i).getAttrezzatura().getTipo_attrezzatura_GVR()!=null) {						
						cell.setCellValue(listaVerbali.get(i).getAttrezzatura().getTipo_attrezzatura_GVR());
					}else {
						cell.setCellValue("");
					}
					 col++;
		    		 cell = row.createCell(col);
				
					cell.setCellValue(""); //TIPO VERIFICA GVR
					
					 col++;
		    		 cell = row.createCell(col);				
					if(listaVerbali.get(i).getAttrezzatura()!=null && listaVerbali.get(i).getAttrezzatura().getSogg_messa_serv_GVR()!=null) {						
						cell.setCellValue(listaVerbali.get(i).getAttrezzatura().getSogg_messa_serv_GVR());
					}else {
						cell.setCellValue("");
					}
					
					 col++;
		    		 cell = row.createCell(col);
					if(listaVerbali.get(i).getAttrezzatura()!=null && listaVerbali.get(i).getAttrezzatura().getID_specifica()!=null) {						
						cell.setCellValue(listaVerbali.get(i).getAttrezzatura().getID_specifica());
					}else {
						cell.setCellValue("");
					}
					 col++;
		    		 cell = row.createCell(col);
					if(listaVerbali.get(i).getAttrezzatura()!=null && listaVerbali.get(i).getAttrezzatura().getN_panieri_idroestrattori()!=null) {						
						cell.setCellValue(listaVerbali.get(i).getAttrezzatura().getN_panieri_idroestrattori()+"");
					}else {
						cell.setCellValue("");
					}
					 col++;
		    		 cell = row.createCell(col);
					if(listaVerbali.get(i).getAttrezzatura()!=null && listaVerbali.get(i).getAttrezzatura().getModello()!=null) {						
						cell.setCellValue(listaVerbali.get(i).getAttrezzatura().getModello());
					}else {
						cell.setCellValue("");
					}
					 col++;
		    		 cell = row.createCell(col);
					if(listaVerbali.get(i).getAttrezzatura()!=null && listaVerbali.get(i).getAttrezzatura().getNumero_fabbrica()!=null) {						
						cell.setCellValue(listaVerbali.get(i).getAttrezzatura().getNumero_fabbrica());
					}else {
						cell.setCellValue("");
					}
					 col++;
		    		 cell = row.createCell(col);
					if(listaVerbali.get(i).getAttrezzatura()!=null && listaVerbali.get(i).getAttrezzatura().getMarcatura()!=null) {						
						cell.setCellValue(listaVerbali.get(i).getAttrezzatura().getMarcatura());
					}else {
						cell.setCellValue("");
					}
					 col++;
		    		 cell = row.createCell(col);
					if(listaVerbali.get(i).getAttrezzatura()!=null && listaVerbali.get(i).getAttrezzatura().getN_id_on()!=null) {
						cell.setCellValue(listaVerbali.get(i).getAttrezzatura().getN_id_on());
					}else {
						cell.setCellValue("");
					}
					 col++;
		    		 cell = row.createCell(col);
					
					cell.setCellValue(listaVerbali.get(i).getIntervento().getTecnico_verificatore().getCompany().getDenominazione());
					 col++;
		    		 cell = row.createCell(col);
					cell.setCellValue(listaVerbali.get(i).getIntervento().getTecnico_verificatore().getNome());
					 col++;
		    		 cell = row.createCell(col);
					cell.setCellValue(listaVerbali.get(i).getIntervento().getTecnico_verificatore().getCognome());
					
					 col++;
		    		 cell = row.createCell(col);
					cell.setCellValue(""); //CF VERIFICATORE
					
					 col++;
		    		 cell = row.createCell(col);
					cell.setCellValue(""); //TARIFFA
					 col++;
		    		 cell = row.createCell(col);
					cell.setCellValue(""); //TARIFFA REGOLARE
					 col++;
		    		 cell = row.createCell(col);
					cell.setCellValue(""); //CONTRIBUTO
					 col++;
		    		 cell = row.createCell(col);
					cell.setCellValue(""); // ATTR INDIRIZZO
					 col++;
		    		 cell = row.createCell(col);
					cell.setCellValue(""); // ATTR COMUNE
					 col++;
		    		 cell = row.createCell(col);
					cell.setCellValue(""); // ATTR CAP
					 col++;
		    		 cell = row.createCell(col);
					cell.setCellValue(""); // ATTR pROVINCIA
					 col++;
		    		 cell = row.createCell(col);
					cell.setCellValue(""); // ATTR REGIONE
					 col++;
		    		 cell = row.createCell(col);
					cell.setCellValue(""); // FATTURA
					 col++;
		    		 cell = row.createCell(col);
					cell.setCellValue(""); //VERBALE
					 col++;
		    		 cell = row.createCell(col);
		    		 
					
					cell.setCellValue(""); //SCHEDA TECNICA
					
					 col++;
		    		 cell = row.createCell(col);
					cell.setCellValue(""); // USER INSERIMENTO
					
					 col++;
		    		 cell = row.createCell(col);
					cell.setCellValue(""); // COSTRUTTORE RAG SOCIALE
	    	 }
	    		 
		}
	     
	     
	     
	     
	    	 for(int j = 0; j<50;j++) {
	    		 sheet0.autoSizeColumn(j);
	    	 }
	     

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			
			Date from = df.parse(dateFom);
			Date to = df.parse(dateTo);
	     
	 		//String path = "C:\\Users\\antonio.dicivita\\Desktop\\";
	 		String path = Costanti.PATH_ROOT + "ScadenzarioINAIL\\";
	 		
			df = new SimpleDateFormat("ddMMyyyy");
		
	 		
	 		if(!new File(path).exists()) {
	 			new File(path).mkdirs();
	 		}
	        FileOutputStream fileOut = new FileOutputStream(path +"SCAD"+df.format(from)+ df.format(to)+".xlsx");
	        workbook.write(fileOut);
	        fileOut.close();

	        workbook.close();
	  
	}
	
	
	


	public static void main(String[] args) throws Exception {
		System.out.println("START");
		Session session=SessionFacotryDAO.get().openSession();
		
		session.beginTransaction();
		
		UtenteDTO user = GestioneUtenteBO.getUtenteById(""+11, session);
		
		String dateFrom = "2020-07-01";
		String dateTo = "2020-07-23";		
		
		
		
		List<VerbaleDTO> listaVerbali =GestioneVerbaleBO.getListaVerbaliDate(session,user, dateFrom, dateTo);
		
		new CreateScadenzarioInail(listaVerbali, dateFrom, dateTo, session);
		
		session.close();
		System.out.println("END");
	}

}

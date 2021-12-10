package it.portalECI.bo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
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
import it.portalECI.DAO.GestioneCommesseDAO;
import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.AttivitaMilestoneDTO;
import it.portalECI.DTO.ClienteDTO;
import it.portalECI.DTO.CommessaDTO;
import it.portalECI.DTO.DescrizioneGruppoAttrezzaturaDTO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.DTO.VerbaleDTO;
import it.portalECI.Util.Costanti;
import it.portalECI.Util.Utility;
public class CreateScadenzarioVAL {
	
	public CreateScadenzarioVAL(List<VerbaleDTO> listaVerbali,String dateFom, String dateTo, Session session) throws Exception  {

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
		 for(int j = 0; j<50; j++) {
			 rowHeader.createCell(j);
			 
			 if(j==10 || j==23|| j==24|| j==25|| j==27) {
				 rowHeader.getCell(j).setCellStyle(yellowStyle);
			 }else if(j==31){
				 rowHeader.getCell(j).setCellStyle(greenStyle);
			 }
			 else if(j==0 || j== 1 || j == 48) {
				 rowHeader.getCell(j).setCellStyle(titleStyle);
			 }else {
				 rowHeader.getCell(j).setCellStyle(redStyle);
			 }
		
			 
		 }
		 
		 sheet0.addMergedRegion(CellRangeAddress.valueOf("C1:J1"));		 
		 
		 sheet0.getRow(0).getCell(2).setCellValue("Dati sempre obbligatori");
		 
		 sheet0.getRow(0).getCell(10).setCellValue("dati obbligatori solo nel caso esito_verifica sia sospeso");
		 
		 sheet0.addMergedRegion(CellRangeAddress.valueOf("L1:W1"));
		 
		 sheet0.getRow(0).getCell(11).setCellValue("Dati sempre obbligatori");		 
		 
		 sheet0.addMergedRegion(CellRangeAddress.valueOf("X1:Z1"));
		 
		 sheet0.getRow(0).getCell(23).setCellValue("Dati obbligatori per attrezzature GVR vedi anche (Informazioni)");		 
		 
		 sheet0.getRow(0).getCell(26).setCellValue("Dato obbligatorio");
		 
		 sheet0.getRow(0).getCell(27).setCellValue("Dati obbligatori per attrezzature SC vedi anche (Informazioni");		 
		 
		 sheet0.addMergedRegion(CellRangeAddress.valueOf("AC1:AE1"));
		 
		 sheet0.getRow(0).getCell(28).setCellValue("Dati obbligatori");		 
		 
		 sheet0.getRow(0).getCell(31).setCellValue("Dato opzionale");		 
		 
		 sheet0.getRow(0).getCell(32).setCellValue("Dati obbligatori");
		 
		 sheet0.addMergedRegion(CellRangeAddress.valueOf("AG1:AV1"));		 
		 
		 sheet0.getRow(0).getCell(48).setCellValue("Dato non inserire");
		 
		 sheet0.getRow(0).getCell(49).setCellValue("Dato obbligatorio");
		 
		 Row rowTitle = sheet0.createRow(1);
		 		 
		 rowTitle.createCell(0).setCellValue("note");
		 rowTitle.createCell(1).setCellValue("id_verifica");
		 rowTitle.createCell(2).setCellValue("eff_verifica");
		 rowTitle.createCell(3).setCellValue("anno Matricola");
		 rowTitle.createCell(4).setCellValue("cod.att. Matricola");
		 rowTitle.createCell(5).setCellValue("numero Matricola");
		 rowTitle.createCell(6).setCellValue("provincia Matricola");
		 rowTitle.createCell(7).setCellValue("Matricola");
		 rowTitle.createCell(8).setCellValue("esito_verifica");
		 rowTitle.createCell(9).setCellValue("tipo_verifica");
		 rowTitle.createCell(10).setCellValue("sospensione");
		 rowTitle.createCell(11).setCellValue("data_rilascio");
		 rowTitle.createCell(12).setCellValue("data_pvp");
		 rowTitle.createCell(13).setCellValue("dl_rag_sociale");
		 rowTitle.createCell(14).setCellValue("dl_indirizzo");
		 rowTitle.createCell(15).setCellValue("dl_comune");
		 rowTitle.createCell(16).setCellValue("dl_cap");
		 rowTitle.createCell(17).setCellValue("dl_prov");
		 rowTitle.createCell(18).setCellValue("dl_reg");
		 rowTitle.createCell(19).setCellValue("dl_cod_fisc");
		 rowTitle.createCell(20).setCellValue("dl_part_iva");
		 rowTitle.createCell(21).setCellValue("attr_gruppo");
		 rowTitle.createCell(22).setCellValue("tipo_attr");
		 rowTitle.createCell(23).setCellValue("tipo_attr_GVR");
		 rowTitle.createCell(24).setCellValue("tipo_verifica_GVR");
		 rowTitle.createCell(25).setCellValue("Sogg_Messa_Servizio_GVR");
		 rowTitle.createCell(26).setCellValue("ID Specifica");
		 rowTitle.createCell(27).setCellValue("n_Panieri_Idroestrattori");
		 rowTitle.createCell(28).setCellValue("modello_attr");
		 rowTitle.createCell(29).setCellValue("num_fabbrica");
		 rowTitle.createCell(30).setCellValue("marcatura_CE");
		 rowTitle.createCell(31).setCellValue("num_id_ON");
		 rowTitle.createCell(32).setCellValue("sv_rag_sociale");
		 rowTitle.createCell(33).setCellValue("sv_nome_tecn");
		 rowTitle.createCell(34).setCellValue("sv_cognome_tecn");
		 rowTitle.createCell(35).setCellValue("sv_CF_tecn");
		 rowTitle.createCell(36).setCellValue("tariffa_app");
		 rowTitle.createCell(37).setCellValue("tariffa_regolare");
		 rowTitle.createCell(38).setCellValue("contributo_sogg_tit");
		 rowTitle.createCell(39).setCellValue("attr_indirizzo");
		 rowTitle.createCell(40).setCellValue("attr_comune");
		 rowTitle.createCell(41).setCellValue("attr_cap");
		 rowTitle.createCell(42).setCellValue("attr_provincia");
		 rowTitle.createCell(43).setCellValue("attr_regione");
		 rowTitle.createCell(44).setCellValue("fattura");
		 rowTitle.createCell(45).setCellValue("verbale");
		 rowTitle.createCell(46).setCellValue("scheda_tecnica");
		 rowTitle.createCell(47).setCellValue("user_inserimento");
		 rowTitle.createCell(48).setCellValue("inail_conferma");
		 rowTitle.createCell(49).setCellValue("cs_ragionesociale");
		 rowTitle.createCell(50).setCellValue("codice_commessa");
		 rowTitle.createCell(51).setCellValue("numero_verbale");
		 
		 for(int j = 0; j<52; j++) {
			 rowTitle.getCell(j).setCellStyle(titleStyle);
		 }
	     	        
	     int row_index = 0;	        
	     for (int i = 0; i<listaVerbali.size();i++) {
	    	 
	    	 if(listaVerbali.get(i).getAttrezzatura()!=null) {
	    		 
	    		 
	    		 
	    		 Row row = sheet0.createRow(2+row_index);
	    		 
	    		 int col = 0;
	    		 
	    		 Cell cell = row.createCell(col);
	    		 
	    		 cell.setCellValue("");
	    		 
	    		 col++;
	    		 cell = row.createCell(col);
	    		 
	    		 
	    		 String tipo_verifica = "";
	    		 
	    		 if(listaVerbali.get(i).getTipo_verifica()!=0) {
	    			 
	    			 if(listaVerbali.get(i).getAttrezzatura().getTipo_attivita().equals("GVR")) {
	    				 
	    				 if(listaVerbali.get(i).getTipo_verifica()<3) {
	    					 
	    					 tipo_verifica = "1";
	    				 }else {
	    					 tipo_verifica = "2"; //TIPO VERIFICA
	    				 }		    				 
	    				 
	    			 }else {
	    				 
	    				 tipo_verifica = ""+listaVerbali.get(i).getTipo_verifica();
	    				 
	    			 }
	    		 }
	    		 
	    		 Date data_rilascio = null;
	    		 Date data_pvp = getDataPvP(listaVerbali.get(i));
	    		 
	    		 
	    		 if(listaVerbali.get(i).getStato().getId()!=9 && listaVerbali.get(i).getTipo_verifica_gvr()==0 || listaVerbali.get(i).getTipo_verifica_gvr()==1 || listaVerbali.get(i).getTipo_verifica_gvr()==4 || listaVerbali.get(i).getTipo_verifica_gvr()==5) {
	    			 data_rilascio = listaVerbali.get(i).getData_verifica();
	    			// data_pvp = listaVerbali.get(i).getData_prossima_verifica();
	    		 }
	    		 else if(listaVerbali.get(i).getStato().getId()!=9 && listaVerbali.get(i).getTipo_verifica_gvr()!=0 && (listaVerbali.get(i).getTipo_verifica_gvr()==3 || listaVerbali.get(i).getTipo_verifica_gvr()==6)) {
	    			 data_rilascio = listaVerbali.get(i).getData_verifica_interna();
	    		//	 data_pvp = listaVerbali.get(i).getData_prossima_verifica_interna();
	    		 }
	    		 else if(listaVerbali.get(i).getStato().getId()!=9 && listaVerbali.get(i).getTipo_verifica_gvr()!=0 && listaVerbali.get(i).getTipo_verifica_gvr()==2) {
	    			 data_rilascio = listaVerbali.get(i).getData_verifica_integrita();
	    			// data_pvp = listaVerbali.get(i).getData_prossima_verifica_integrita();
	    		 }
	    		 
	    		 
	    		 
	    		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");	
	    		 
	    		 if(listaVerbali.get(i).getAttrezzatura()!=null && listaVerbali.get(i).getAttrezzatura().getMatricola_inail().split("/").length>0) {
	    			 
	    			 df.applyPattern("yyyy-MM-dd");	
		    		 
		    		 Date date = df.parse(df.format(data_pvp)); 
		    		 
		    		 df.applyPattern("ddMMyyyy");				    		 
	    			 
	    			 cell.setCellValue(listaVerbali.get(i).getAttrezzatura().getMatricola_inail().replaceAll("/", "") + tipo_verifica+df.format(date));
	    		 }else {
	    			 cell.setCellValue("");	 
	    		 }
	    		 
	    		 col++;
	    		 cell = row.createCell(col);
	    		 
	    		 if(listaVerbali.get(i).getEffettuazione_verifica()!=0) {
	    			 cell.setCellValue(listaVerbali.get(i).getEffettuazione_verifica());
	    		 }else {
	    			 cell.setCellValue("");	 
	    		 }
	    		 
	    		 col++;
	    		 cell = row.createCell(col);
	    		 
	    		 
	    		 String matr_anno ="";
	    		 String matr_cod_att = "";
	    		 String matr_numero = "";
	    		 String matr_prov ="";
	    		 
	    		 
	    		 if(listaVerbali.get(i).getAttrezzatura()!=null && listaVerbali.get(i).getAttrezzatura().getMatricola_inail().split("/").length>0) {
												
						matr_anno = listaVerbali.get(i).getAttrezzatura().getMatricola_inail().split("/")[0];
	    		 }
				
	    		 cell.setCellValue(matr_anno);
	    		
	    		 
	    		 col++;
	    		 cell = row.createCell(col);
	    		 if(listaVerbali.get(i).getAttrezzatura()!=null && listaVerbali.get(i).getAttrezzatura().getMatricola_inail().split("/").length>1) {
	    			 
						matr_cod_att = listaVerbali.get(i).getAttrezzatura().getMatricola_inail().split("/")[1];
						
					}
	    		 
	    		 cell.setCellValue(matr_cod_att);
	    		 
	    		 col++;
	    		 cell = row.createCell(col);	
					if(listaVerbali.get(i).getAttrezzatura()!=null && listaVerbali.get(i).getAttrezzatura().getMatricola_inail().split("/").length>2) {
						
						matr_numero = listaVerbali.get(i).getAttrezzatura().getMatricola_inail().split("/")[2];
						
					}
					
					cell.setCellValue(matr_numero);
					
					 col++;
		    		 cell = row.createCell(col);	
					if(listaVerbali.get(i).getAttrezzatura()!=null && listaVerbali.get(i).getAttrezzatura().getMatricola_inail().split("/").length>3) {
						
						matr_prov = listaVerbali.get(i).getAttrezzatura().getMatricola_inail().split("/")[3];
						
					}
					
					cell.setCellValue(matr_prov);
					
					 col++;
		    		 cell = row.createCell(col);
					if(listaVerbali.get(i).getAttrezzatura()!=null) {
						cell.setCellValue(listaVerbali.get(i).getAttrezzatura().getMatricola_inail());
					}else {
						cell.setCellValue("");
					}
					 col++;
		    		 cell = row.createCell(col);
		    		 
		    		 String esito ="";
		    		 String sospensione = "";
		    		 
		    		 if(listaVerbali.get(i).getEsito()!=null && listaVerbali.get(i).getEsito().equals("P")) {
		    			 esito = "Positivo";
		    		 }else if(listaVerbali.get(i).getEsito()!=null && listaVerbali.get(i).getEsito().equals("N")) {
		    			 esito = "Negativo";
		    		 }else if(listaVerbali.get(i).getEsito()!=null && listaVerbali.get(i).getEsito().equals("S")) {
		    			 esito = "Sospeso";
		    			 sospensione = listaVerbali.get(i).getDescrizione_sospensione();
		    		 }
		    		 
					cell.setCellValue(esito); //ESITO VERIFICA
					 col++;
		    		 cell = row.createCell(col);
		    		 

					
		    		 cell.setCellValue(tipo_verifica); //TIPO VERIFICA	 
		    		 
		    		 
					 col++;
		    		 cell = row.createCell(col);
					cell.setCellValue(sospensione); //SOSPENSIONE
					 col++;
		    		 cell = row.createCell(col);

		    		 	    		 
		    	
		    		 Date date =null; 
		    				 
		    		 if(data_rilascio!=null) {
		    			 date = df.parse(df.format(data_rilascio));
			    		 
			    		 df.applyPattern("dd/MM/yyyy");		    		
			    		 
						 cell.setCellValue(""+df.format(date)); //DATA RILASCIO
		    		 }else {
		    			 cell.setCellValue("");
		    		 }
		    				 
					 col++;
		    		 cell = row.createCell(col);
		    		 
		    		 
		    		 if(data_pvp!=null) {
		    			 df.applyPattern("yyyy-MM-dd");	
			    		 
			    		 date = df.parse(df.format(data_pvp)); 
			    		 
			    		 df.applyPattern("dd/MM/yyyy");		
			    		 
						 cell.setCellValue(""+df.format(date)); //DATA PROSSIMA VERIFICA PERIODICA
		    		 }else {
		    			 cell.setCellValue("");
		    		 }
		    		 
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
		    		 if(cliente!=null && cliente.getProvincia()!=null) {	
		    			 cell.setCellValue(GestioneAnagraficaRemotaBO.getRegioneFromProvincia(cliente.getProvincia(), session)); //DL REGIONE	 
		    		 }else {
		    			 cell.setCellValue("");
		    		 }
					
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
				
		    		 
		    		 String tipo_ver_gvr="";
		    		
		    		 if(listaVerbali.get(i).getTipo_verifica_gvr()!=0) {
		    			 if((listaVerbali.get(i).getTipo_verifica() == 1 ||listaVerbali.get(i).getTipo_verifica() == 2) || listaVerbali.get(i).getTipo_verifica_gvr()==1 || listaVerbali.get(i).getTipo_verifica_gvr()==7) {
			    			 tipo_ver_gvr = "Verifica di funzionamento";
			    			 
			    		 }else if((listaVerbali.get(i).getTipo_verifica() != 1 && listaVerbali.get(i).getTipo_verifica() != 2) && listaVerbali.get(i).getTipo_verifica_gvr()==2 || listaVerbali.get(i).getTipo_verifica_gvr()==4 || listaVerbali.get(i).getTipo_verifica_gvr()==5|| listaVerbali.get(i).getTipo_verifica_gvr()==6) {
			    			 tipo_ver_gvr = "Verifica di integrità";
			    			 
			    		 }else if((listaVerbali.get(i).getTipo_verifica() != 1 && listaVerbali.get(i).getTipo_verifica() != 2) && listaVerbali.get(i).getTipo_verifica_gvr()==3 ) {
			    			 tipo_ver_gvr = "Verifica interna";
			    			 
			    		 }
		    		 }
		    		
					cell.setCellValue(tipo_ver_gvr); //TIPO VERIFICA GVR
					
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
		    		 
		    		 String cf = "";
		    		 
		    		 if(listaVerbali.get(i).getIntervento().getTecnico_verificatore().getCf()!=null) {
		    			 cf = listaVerbali.get(i).getIntervento().getTecnico_verificatore().getCf(); 
		    		 }
		    		 
					cell.setCellValue(cf); //CF VERIFICATORE
					
					 col++;
		    		 cell = row.createCell(col);		    		 
		    		 
		    		 if(listaVerbali.get(i).getTipo_verifica()!=0) {
		    			 
		    		 String articolo = GestioneAttrezzatureBO.getArticoloFromDescrizione(listaVerbali.get(i).getAttrezzatura().getDescrizione(), listaVerbali.get(i).getTipo_verifica(), session);
		    		 	    		 		    		 
		    		 String tariffe = GestioneCommesseDAO.getTariffeFromArticolo(articolo, listaVerbali.get(i).getIntervento().getIdCommessa());
		    		 
		    		 if(!tariffe.equals("") && tariffe.split(";").length>1) {
		    			 
			    		 String tariffa_app = tariffe.split(";")[0];
			    		 String tariffa_regolare = tariffe.split(";")[1];
			    		 
			    		 cell.setCellValue(new Double(tariffa_app)); //TARIFFA
						 col++;
			    		 cell = row.createCell(col);
						cell.setCellValue(new Double(tariffa_regolare)); //TARIFFA REGOLARE
						
						 col++;
			    		 cell = row.createCell(col);
			    		 
			    		 Double contributo = 0.0;
			    		 
			    		 if(listaVerbali.get(i).getEffettuazione_verifica()==1) {
			    			 
			    			 contributo = new Double(tariffa_regolare)*0.15;
			    			 
			    		 }else if(listaVerbali.get(i).getEffettuazione_verifica()==2){
			    			 contributo = new Double(tariffa_regolare)*0.05;
			    		 }
			    		 
						cell.setCellValue(contributo); //CONTRIBUTO
		    		 
		    		 }else {
		    			 cell.setCellValue(""); //TARIFFA
						 col++;
			    		 cell = row.createCell(col);
						cell.setCellValue(""); //TARIFFA REGOLARE
						 col++;
			    		 cell = row.createCell(col);
						cell.setCellValue(""); //CONTRIBUTO
		    		 }
		    		
		    		 
		    		 }else {
		    			 
		    			 cell.setCellValue(""); //TARIFFA
						 col++;
			    		 cell = row.createCell(col);
						cell.setCellValue(""); //TARIFFA REGOLARE
						 col++;
			    		 cell = row.createCell(col);
						cell.setCellValue(""); //CONTRIBUTO
		    			 
		    		 }
					
					 col++;
		    		 cell = row.createCell(col);
		    		 
		    		 if(listaVerbali.get(i).getAttrezzatura().getComune_div()!=null) {
		    			 cell.setCellValue(listaVerbali.get(i).getAttrezzatura().getIndirizzo_div()); // ATTR INDIRIZZO
						 col++;
			    		 cell = row.createCell(col);
						cell.setCellValue(listaVerbali.get(i).getAttrezzatura().getComune_div()); // ATTR COMUNE
						 col++;
			    		 cell = row.createCell(col);
						cell.setCellValue(listaVerbali.get(i).getAttrezzatura().getCap_div()); // ATTR CAP
						 col++;
			    		 cell = row.createCell(col);
						cell.setCellValue(listaVerbali.get(i).getAttrezzatura().getProvincia_div()); // ATTR PROVINCIA
						 col++;
			    		 cell = row.createCell(col);
						cell.setCellValue(listaVerbali.get(i).getAttrezzatura().getRegione_div()); // ATTR REGIONE 
		    		 }else {
		    			 cell.setCellValue(listaVerbali.get(i).getAttrezzatura().getIndirizzo()); // ATTR INDIRIZZO
						 col++;
			    		 cell = row.createCell(col);
						cell.setCellValue(listaVerbali.get(i).getAttrezzatura().getComune()); // ATTR COMUNE
						 col++;
			    		 cell = row.createCell(col);
						cell.setCellValue(listaVerbali.get(i).getAttrezzatura().getCap()); // ATTR CAP
						 col++;
			    		 cell = row.createCell(col);
						cell.setCellValue(listaVerbali.get(i).getAttrezzatura().getProvincia()); // ATTR PROVINCIA
						 col++;
			    		 cell = row.createCell(col);
						cell.setCellValue(listaVerbali.get(i).getAttrezzatura().getRegione()); // ATTR REGIONE 
		    		 }
					
		    		 
		    		 df.applyPattern("yyyy-MM-dd");	
		    		 if(data_pvp!=null) {
		    			 date = df.parse(df.format(data_pvp)); 
			    		 
			    		 df.applyPattern("ddMMyyyy");		
			    		 
						 col++;
			    		 cell = row.createCell(col);
						cell.setCellValue("fattura "+matr_anno+matr_cod_att+matr_numero+matr_prov+tipo_verifica+df.format(date)); // FATTURA
						 col++;
			    		 cell = row.createCell(col);		    		 
			    				  	    		 
			    		 
						cell.setCellValue("verbale "+matr_anno+matr_cod_att+matr_numero+matr_prov+tipo_verifica+df.format(date)); //VERBALE
						 col++;
			    		 cell = row.createCell(col);
			    		 
						if(listaVerbali.get(i).getSchedaTecnica()!=null) {
							cell.setCellValue("scheda tecnica "+matr_anno+matr_cod_att+matr_numero+matr_prov+tipo_verifica+df.format(date)); //SCHEDA TECNICA
						}else {
							cell.setCellValue("scheda tecnica non rilasciata"); //SCHEDA TECNICA	
						} 
		    		 }else {
		    			 
		    			 col++;
			    		 cell = row.createCell(col);
						cell.setCellValue(""); // FATTURA
						 col++;
			    		 cell = row.createCell(col);		    		 
			    				  	    		 
			    		 
						cell.setCellValue(""); //VERBALE
						 col++;
			    		 cell = row.createCell(col);
			    		 
				
						cell.setCellValue(""); //SCHEDA TECNICA
						
		    		 }
		    		 
					
					
					 col++;
		    		 cell = row.createCell(col);
					cell.setCellValue("ecientespa027");// USER INSERIMENTO
					
					 col++;
		    		 cell = row.createCell(col);
					cell.setCellValue("");
					
					 col++;
		    		 cell = row.createCell(col);
					cell.setCellValue(listaVerbali.get(i).getAttrezzatura().getFabbricante()); // COSTRUTTORE RAG SOCIALE
					
					
					 col++;
		    		 cell = row.createCell(col);
		    		 if(listaVerbali.get(i).getIntervento()!=null) {
		    			 cell.setCellValue(listaVerbali.get(i).getIntervento().getIdCommessa());
		    		 }else {
		    			 cell.setCellValue("");	 
		    		 }
		    		 
		    		 col++;
		    		 cell = row.createCell(col);
		    		 if(listaVerbali.get(i).getNumeroVerbale()!=null) {
		    			 cell.setCellValue(listaVerbali.get(i).getNumeroVerbale());
		    		 }else {
		    			 cell.setCellValue("");	 
		    		 }
		    		 
		    		 
					row_index++;
	    	 }
	    		 
		}
	     
	     
	     
	     
	    	 for(int j = 0; j<52;j++) {
	    		 sheet0.autoSizeColumn(j);
	    	 }
	     

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			
			Date from = df.parse(dateFom);
			Date to = df.parse(dateTo);
	     
	 		//String path = "C:\\Users\\antonio.dicivita\\Desktop\\";
	 		String path = Costanti.PATH_ROOT + "ScadenzarioVAL\\";
	 		
			df = new SimpleDateFormat("ddMMyyyy");
		
	 		
	 		if(!new File(path).exists()) {
	 			new File(path).mkdirs();
	 		}
	        FileOutputStream fileOut = new FileOutputStream(path +"SCADVAL"+df.format(from)+ df.format(to)+".xlsx");
	        workbook.write(fileOut);
	        fileOut.close();

	        workbook.close();
	  
	}
	
	
	


	private Date getDataPvP(VerbaleDTO verbale) {
		
		Date data_pvp = null;
		
			if(verbale.getStato().getId()!=9 && verbale.getTipo_verifica_gvr()==0) {
				data_pvp = verbale.getData_prossima_verifica();
			}else {
				
				 if((verbale.getTipo_verifica() == 1 || verbale.getTipo_verifica() == 2) || verbale.getTipo_verifica_gvr()==1  ) {
					 data_pvp = verbale.getData_prossima_verifica();
	    			 
	    		 }else if((verbale.getTipo_verifica() != 1 && verbale.getTipo_verifica() != 2) && verbale.getTipo_verifica_gvr()==2 ) {
	    			 data_pvp = verbale.getData_prossima_verifica_integrita();
	    			 
	    		 }else if((verbale.getTipo_verifica() != 1 && verbale.getTipo_verifica() != 2) && verbale.getTipo_verifica_gvr()==3 ) {
	    			 data_pvp = verbale.getData_prossima_verifica_interna();	    			 
	    			 
	    		 }else if((verbale.getTipo_verifica() != 1 && verbale.getTipo_verifica() != 2) && verbale.getTipo_verifica_gvr()==7){
	    			 data_pvp = getMinDate(verbale.getData_prossima_verifica(), verbale.getData_prossima_verifica_interna());
	    		 }
	    		 else if((verbale.getTipo_verifica() != 1 && verbale.getTipo_verifica() != 2) && verbale.getTipo_verifica_gvr()==4){
	    			 data_pvp = getMinDate(verbale.getData_prossima_verifica(), verbale.getData_prossima_verifica_integrita());
	    		 }
	    		 else if((verbale.getTipo_verifica() != 1 && verbale.getTipo_verifica() != 2) && verbale.getTipo_verifica_gvr()==5){
	    			 data_pvp = getMinDate(getMinDate(verbale.getData_prossima_verifica(), verbale.getData_prossima_verifica_integrita()), verbale.getData_prossima_verifica_interna());
	    		 }
	    		 else if((verbale.getTipo_verifica() != 1 && verbale.getTipo_verifica() != 2) && verbale.getTipo_verifica_gvr()==6){
	    			 data_pvp = getMinDate(verbale.getData_prossima_verifica_integrita(), verbale.getData_prossima_verifica_interna());
	    		 }
			}
			
				
		return data_pvp;
	}


	private Date getMinDate(Date data1, Date data2) {
		
		Date min = null;
		
		long time1 = data1.getTime();
		long time2 = data2.getTime();
		
		if(time1<=time2) {
			min = data1;
		}else {
			min = data2;
		}
		
		return min;
	}


	public static void main(String[] args) throws Exception {
		System.out.println("START");
		Session session=SessionFacotryDAO.get().openSession();
		
		session.beginTransaction();
		
		UtenteDTO user = GestioneUtenteBO.getUtenteById(""+11, session);
		
		String dateFrom = "2020-09-01";
		String dateTo = "2020-09-23";		
		
		
		
		List<VerbaleDTO> listaVerbali =GestioneVerbaleBO.getListaVerbaliDate(session,user, dateFrom, dateTo);
		
		new CreateScadenzarioVAL(listaVerbali, dateFrom, dateTo, session);
		
		session.close();
		System.out.println("END");
	}

}

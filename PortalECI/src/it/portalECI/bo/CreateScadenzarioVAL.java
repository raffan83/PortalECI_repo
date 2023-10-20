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
import it.portalECI.DTO.AttrezzaturaDTO;
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
		 for(int j = 0; j<63; j++) {
			 rowHeader.createCell(j);
			 
//			 if(j==10 || j==23|| j==24|| j==25|| j==27) {
//				 rowHeader.getCell(j).setCellStyle(yellowStyle);
//			 }else if(j==31){
//				 rowHeader.getCell(j).setCellStyle(greenStyle);
//			 }
//			 else if(j==0 || j== 1 || j == 48) {
//				 rowHeader.getCell(j).setCellStyle(titleStyle);
//			 }else {
//				 rowHeader.getCell(j).setCellStyle(redStyle);
//			 }
			 if(j==7 ||  j==20||j==21|| j==22|| j==24|| j==48|| j==49) {
				 rowHeader.getCell(j).setCellStyle(yellowStyle);
			 }else if(j==28|| j==47|| j==59|| j==60){
				 rowHeader.getCell(j).setCellStyle(greenStyle);
			 }
			 else if(j== 61 || j == 62) {
				 rowHeader.getCell(j).setCellStyle(titleStyle);
			 }else {
				 rowHeader.getCell(j).setCellStyle(redStyle);
			 }
			 
		 }
		 
		 sheet0.getRow(0).getCell(0).setCellValue("Dato obbligatorio per SC, SP, GVR");
		 
		 sheet0.addMergedRegion(CellRangeAddress.valueOf("B1:F1"));		 
		 
		 sheet0.getRow(0).getCell(1).setCellValue("Dati sempre obbligatori");
		 
		 sheet0.getRow(0).getCell(6).setCellValue("Dato obbligatorio per SC, SP, GVR");
		 
		 sheet0.getRow(0).getCell(7).setCellValue("Dato obbligatorio solo nel caso esito_verifica sia sospeso");
		 
		 sheet0.addMergedRegion(CellRangeAddress.valueOf("I1:T1"));
		 
		 sheet0.getRow(0).getCell(8).setCellValue("Dati sempre obbligatori");		 
		 
		 sheet0.addMergedRegion(CellRangeAddress.valueOf("U1:W1"));
		 
		 sheet0.getRow(0).getCell(20).setCellValue("Dati obbligatori per attrezzature GVR vedi anche (Informazioni)");		 
		 
		 sheet0.getRow(0).getCell(23).setCellValue("Dato obbligatorio per SC, SP, GVR");
		 
		 sheet0.getRow(0).getCell(24).setCellValue("Dati obbligatori per attrezzature SC/D1, SC/D2, SC/D3 vedi anche (Informazioni");		 
		 
		 sheet0.addMergedRegion(CellRangeAddress.valueOf("Z1:AB1"));
		 
		 sheet0.getRow(0).getCell(25).setCellValue("Dati obbligatori per SC, SP, GVR");		 
		 
		 sheet0.getRow(0).getCell(28).setCellValue("Solo per GVR (opzionale)");		 
		 
		 sheet0.getRow(0).getCell(29).setCellValue("Dati obbligatori");
		 
		 sheet0.addMergedRegion(CellRangeAddress.valueOf("AD1:AQ1"));	
		 
		 sheet0.getRow(0).getCell(43).setCellValue("Dato obbligatorio per GVR, SP, SC tipo verifica 1 (prima verifica periodica)");
		 
		 sheet0.getRow(0).getCell(44).setCellValue("Dato obbligatorio");
		 
		 sheet0.getRow(0).getCell(45).setCellValue("Dato obbligatorio");
		 
		 sheet0.getRow(0).getCell(46).setCellValue("Dato obbligatorio");
		 
		 sheet0.getRow(0).getCell(47).setCellValue("Dato opzionale");
		 
		 sheet0.getRow(0).getCell(48).setCellValue("Dato obbligatorio per insiemi GVR");
		 
		 sheet0.getRow(0).getCell(49).setCellValue("Dato obbligatorio per GVR se pacco bombola");

		 sheet0.addMergedRegion(CellRangeAddress.valueOf("AY1:AZ1"));
		 
		 sheet0.getRow(0).getCell(50).setCellValue("Dati obbligatori per ITP");
		 
		 sheet0.addMergedRegion(CellRangeAddress.valueOf("BA1:BB1"));
		 
		 sheet0.getRow(0).getCell(52).setCellValue("Dati obbligatori per ITP B e C");
		 
		 sheet0.addMergedRegion(CellRangeAddress.valueOf("BC1:BG1"));		 
		 
		 sheet0.getRow(0).getCell(54).setCellValue("Dati obbligatori per ITP A");		 
		 
		 sheet0.getRow(0).getCell(59).setCellValue("Dato opzionale");
		 
		 sheet0.getRow(0).getCell(60).setCellValue("Dato opzionale");
		 
		 Row rowTitle = sheet0.createRow(1);
		 		 
		 //rowTitle.createCell(0).setCellValue("note");
		 //rowTitle.createCell(1).setCellValue("id_verifica");
		 rowTitle.createCell(0).setCellValue("eff_verifica");
		 rowTitle.createCell(1).setCellValue("anno Matricola");
		 rowTitle.createCell(2).setCellValue("cod.att. Matricola");
		 rowTitle.createCell(3).setCellValue("numero Matricola");
		 rowTitle.createCell(4).setCellValue("provincia Matricola");
		// rowTitle.createCell(7).setCellValue("Matricola");
		 rowTitle.createCell(5).setCellValue("esito_verifica");
		 rowTitle.createCell(6).setCellValue("tipo_verifica");
		 rowTitle.createCell(7).setCellValue("sospensione");
		 rowTitle.createCell(8).setCellValue("data_rilascio");
		 rowTitle.createCell(9).setCellValue("data_pvp");
		 rowTitle.createCell(10).setCellValue("dl_rag_sociale");
		 rowTitle.createCell(11).setCellValue("dl_indirizzo");
		 rowTitle.createCell(12).setCellValue("dl_comune");
		 rowTitle.createCell(13).setCellValue("dl_cap");
		 rowTitle.createCell(14).setCellValue("dl_prov");
		 rowTitle.createCell(15).setCellValue("dl_reg");
		 rowTitle.createCell(16).setCellValue("dl_cod_fisc");
		 rowTitle.createCell(17).setCellValue("dl_part_iva");
		 rowTitle.createCell(18).setCellValue("attr_gruppo");
		 rowTitle.createCell(19).setCellValue("tipo_attr");
		 rowTitle.createCell(20).setCellValue("tipo_attr_GVR");
		 rowTitle.createCell(21).setCellValue("tipo_verifica_GVR");
		 rowTitle.createCell(22).setCellValue("Sogg_Messa_Servizio_GVR");
		 rowTitle.createCell(23).setCellValue("ID Specifica");
		 rowTitle.createCell(24).setCellValue("n_Panieri_Idroestrattori");
		 rowTitle.createCell(25).setCellValue("modello_attr");
		 rowTitle.createCell(26).setCellValue("num_fabbrica");
		 rowTitle.createCell(27).setCellValue("marcatura_CE");
		 rowTitle.createCell(28).setCellValue("num_id_ON");
		 rowTitle.createCell(29).setCellValue("sv_rag_sociale");
		 rowTitle.createCell(30).setCellValue("sv_nome_tecn");
		 rowTitle.createCell(31).setCellValue("sv_cognome_tecn");
		 rowTitle.createCell(32).setCellValue("sv_CF_tecn");
		 rowTitle.createCell(33).setCellValue("tariffa_app");
		 rowTitle.createCell(34).setCellValue("tariffa_regolare");
		 rowTitle.createCell(35).setCellValue("contributo_sogg_tit");
		 rowTitle.createCell(36).setCellValue("attr_indirizzo");
		 rowTitle.createCell(37).setCellValue("attr_comune");
		 rowTitle.createCell(38).setCellValue("attr_cap");
		 rowTitle.createCell(39).setCellValue("attr_provincia");
		 rowTitle.createCell(40).setCellValue("attr_regione");
		 rowTitle.createCell(41).setCellValue("fattura");
		 rowTitle.createCell(42).setCellValue("verbale");
		 rowTitle.createCell(43).setCellValue("scheda tecnica");
		 rowTitle.createCell(44).setCellValue("user_inserimento");
		// rowTitle.createCell(48).setCellValue("inail_conferma");
		 rowTitle.createCell(45).setCellValue("cs_is_ragionesociale");
		 rowTitle.createCell(46).setCellValue("sv_cod_fiscale");
		 rowTitle.createCell(47).setCellValue("cs_is_cod_fiscale");
		 rowTitle.createCell(48).setCellValue("matr_insieme");
		 rowTitle.createCell(49).setCellValue("id_pacco_bombola");
		 rowTitle.createCell(50).setCellValue("num_add");
		 rowTitle.createCell(51).setCellValue("tipol_atv");
		 rowTitle.createCell(52).setCellValue("pot_inst_(kw)");
		 rowTitle.createCell(53).setCellValue("alimentazione");
		 rowTitle.createCell(54).setCellValue("num_prf_asta");
		 rowTitle.createCell(55).setCellValue("num_prf_gabbia");
		 rowTitle.createCell(56).setCellValue("num_strutt_met");
		 rowTitle.createCell(57).setCellValue("num_capn_met");
		 rowTitle.createCell(58).setCellValue("superf_prot_(mq)");
		 rowTitle.createCell(59).setCellValue("sv_CF_tecn_aff");
		 rowTitle.createCell(60).setCellValue("riferimento_Interno_Sa");
		 
		 rowTitle.createCell(61).setCellValue("codice_commessa");
		 rowTitle.createCell(62).setCellValue("numero_verbale");
		 
		 for(int j = 0; j<63; j++) {
			 rowTitle.getCell(j).setCellStyle(titleStyle);
		 }
		 
			     	        
	     int row_index = 0;	        
	     for (int i = 0; i<listaVerbali.size();i++) {
	    	 
	    	 if(listaVerbali.get(i).getAttrezzatura()!=null) {
	    		 
	    		 ArrayList<AttrezzaturaDTO> listaAttrVerbale = new ArrayList<AttrezzaturaDTO>();
	    		 
	    		 listaAttrVerbale.add(listaVerbali.get(i).getAttrezzatura());
	    		 
	    		 if(listaVerbali.get(i).getAttrezzatura().getHas_insieme()==1) {
	    			 listaAttrVerbale.addAll(GestioneAttrezzatureBO.getListaAttrezzatureInsieme(listaVerbali.get(i).getAttrezzatura().getId(), session));
	    		 }
	    		 
	    		 
	    		for(int j = 0; j<listaAttrVerbale.size();j++) {
	    			
	    			if(listaAttrVerbale.get(j).getHas_insieme()==0) {
	    				
	    				Row row = sheet0.createRow(2+row_index);
			    		 
			    		 int col = 0;
			    		 
			    		 Cell cell = row.createCell(col);
			    		 
			    		 //cell.setCellValue("");
			    		 
			    		 //col++;
			    		 //cell = row.createCell(col);
			    		 
			    		 
			    		 String tipo_verifica = "";
			    		 
			    		 if(listaVerbali.get(i).getTipo_verifica()!=0) {
			    			 
			    			 if(listaAttrVerbale.get(j).getTipo_attivita().equals("GVR")) {
			    				 
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
			    		 Date data_pvp = Utility.getDataPvP(listaVerbali.get(i));
			    		 
			    		 
			    		 if(listaVerbali.get(i).getStato().getId()!=9 && listaVerbali.get(i).getTipo_verifica_gvr()==0 || listaVerbali.get(i).getTipo_verifica_gvr()==1 || listaVerbali.get(i).getTipo_verifica_gvr()==4 || listaVerbali.get(i).getTipo_verifica_gvr()==5 || listaVerbali.get(i).getTipo_verifica_gvr()==7) {
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
			    		 
			    		 if(listaAttrVerbale.get(j)!=null && listaAttrVerbale.get(j).getMatricola_inail().split("/").length>0) {
			    			 
			    			 df.applyPattern("yyyy-MM-dd");	
				    		 
				    		 Date date = df.parse(df.format(data_pvp)); 
				    		 
				    		 df.applyPattern("ddMMyyyy");				    		 
			    			 
			    		//	 cell.setCellValue(listaAttrVerbale.get(j).getMatricola_inail().replaceAll("/", "") + tipo_verifica+df.format(date));
			    		 }else {
			    		//	 cell.setCellValue("");	 
			    		 }
			    		 
			    		// col++;
			    		// cell = row.createCell(col);
			    		 
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
			    		 
			    		 
			    		 if(listaAttrVerbale.get(j)!=null && listaAttrVerbale.get(j).getMatricola_inail().split("/").length>0) {
														
								matr_anno = listaAttrVerbale.get(j).getMatricola_inail().split("/")[0];
			    		 }
						
			    		 cell.setCellValue(matr_anno);
			    		
			    		 
			    		 col++;
			    		 cell = row.createCell(col);
			    		 if(listaAttrVerbale.get(j)!=null && listaAttrVerbale.get(j).getMatricola_inail().split("/").length>1) {
			    			 
								matr_cod_att = listaAttrVerbale.get(j).getMatricola_inail().split("/")[1];
								
							}
			    		 
			    		 cell.setCellValue(matr_cod_att);
			    		 
			    		 col++;
			    		 cell = row.createCell(col);	
							if(listaAttrVerbale.get(j)!=null && listaAttrVerbale.get(j).getMatricola_inail().split("/").length>2) {
								
								matr_numero = listaAttrVerbale.get(j).getMatricola_inail().split("/")[2];
								
							}
							
							cell.setCellValue(matr_numero);
							
							 col++;
				    		 cell = row.createCell(col);	
							if(listaAttrVerbale.get(j)!=null && listaAttrVerbale.get(j).getMatricola_inail().split("/").length>3) {
								
								matr_prov = listaAttrVerbale.get(j).getMatricola_inail().split("/")[3];
								
							}
							
							cell.setCellValue(matr_prov);
							
//							 col++;
//				    		 cell = row.createCell(col);
//							if(listaAttrVerbale.get(j)!=null) {
//								cell.setCellValue(listaAttrVerbale.get(j).getMatricola_inail());
//							}else {
//								cell.setCellValue("");
//							}
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
				    		 
				    		 CommessaDTO commessa = GestioneCommesseBO.getCommessaById(listaVerbali.get(i).getIntervento().getIdCommessa());
							
				    		 
				    		 
							ClienteDTO cliente = null;
							
							if(commessa.getID_ANAGEN()!=commessa.getID_ANAGEN_UTIL()) {
								cliente = GestioneAnagraficaRemotaBO.getClienteById(commessa.getID_ANAGEN_UTIL()+"");
							}else {
								cliente = GestioneAnagraficaRemotaBO.getClienteById(listaVerbali.get(i).getIntervento().getId_cliente()+"");
							}
							
												
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
							if(listaAttrVerbale.get(j)!=null && listaAttrVerbale.get(j).getTipo_attivita()!=null) {						
								cell.setCellValue(listaAttrVerbale.get(j).getTipo_attivita());
							}else {
								cell.setCellValue("");
							}
							 col++;
				    		 cell = row.createCell(col);
							if(listaAttrVerbale.get(j)!=null && listaAttrVerbale.get(j).getTipo_attrezzatura()!=null) {						
								cell.setCellValue(listaAttrVerbale.get(j).getTipo_attrezzatura());
							}else {
								cell.setCellValue("");
							}
							 col++;
				    		 cell = row.createCell(col);
							if(listaAttrVerbale.get(j)!=null && listaAttrVerbale.get(j).getTipo_attrezzatura_GVR()!=null) {						
								cell.setCellValue(listaAttrVerbale.get(j).getTipo_attrezzatura_GVR());
							}else {
								cell.setCellValue("");
							}
							 col++;
				    		 cell = row.createCell(col);
						
				    		 
				    		 String tipo_ver_gvr="";
				    		
//				    		 if(listaVerbali.get(i).getTipo_verifica_gvr()!=0) {
//				    			 if((listaVerbali.get(i).getTipo_verifica() == 1 ||listaVerbali.get(i).getTipo_verifica() == 2) || listaVerbali.get(i).getTipo_verifica_gvr()==1 || listaVerbali.get(i).getTipo_verifica_gvr()==7) {
//					    			 tipo_ver_gvr = "Verifica di funzionamento";
//					    			 
//					    		 }else if((listaVerbali.get(i).getTipo_verifica() != 1 && listaVerbali.get(i).getTipo_verifica() != 2) && listaVerbali.get(i).getTipo_verifica_gvr()==2 || listaVerbali.get(i).getTipo_verifica_gvr()==4 || listaVerbali.get(i).getTipo_verifica_gvr()==5|| listaVerbali.get(i).getTipo_verifica_gvr()==6) {
//					    			 tipo_ver_gvr = "Verifica di integrità";
//					    			 
//					    		 }else if((listaVerbali.get(i).getTipo_verifica() != 1 && listaVerbali.get(i).getTipo_verifica() != 2) && listaVerbali.get(i).getTipo_verifica_gvr()==3 ) {
//					    			 tipo_ver_gvr = "Verifica interna";
//					    			 
//					    		 }
//				    		 }
				    		 
				    		 
				    		 if(listaVerbali.get(i).getTipo_verifica_gvr()!=0) {
				    			 if(listaVerbali.get(i).getTipo_verifica_gvr() == 1) {
					    			 tipo_ver_gvr = "Verifica funzionamento: biennale";
					    			 
					    		 }
				    			 else if(listaVerbali.get(i).getTipo_verifica_gvr() == 8) {
				    				 tipo_ver_gvr = "Verifica funzionamento: triennale";
				    			 }
				    			 else if(listaVerbali.get(i).getTipo_verifica_gvr() == 9) {
				    				 tipo_ver_gvr = "Verifica funzionamento: quadriennale";
				    			 }
				    			 else if(listaVerbali.get(i).getTipo_verifica_gvr() == 10) {
				    				 tipo_ver_gvr = "Verifica funzionamento: quinquennale";
				    			 }
				    			 
				    			 else if(listaVerbali.get(i).getTipo_verifica_gvr() == 2) {
					    			 tipo_ver_gvr = "Verifica integrità: decennale";
					    			 
					    		 }else if(listaVerbali.get(i).getTipo_verifica_gvr()==3 ) {
					    			 tipo_ver_gvr = "Visita interna: biennale";
					    			 
					    		 
					    		 }else if(listaVerbali.get(i).getTipo_verifica_gvr()==4 ) {
					    			 tipo_ver_gvr = "Ver. funzionamento biennale + Ver. integrità";
					    			 
					    		 
					    		 }else if(listaVerbali.get(i).getTipo_verifica_gvr()==11 ) {
							    			 tipo_ver_gvr = "Ver. funzionamento triennale + Ver. integrità";
							    			 
							    		 
				    			 }else if(listaVerbali.get(i).getTipo_verifica_gvr()==12 ) {
					    			 tipo_ver_gvr = "Ver. funzionamento quadriennale + Ver. integrità";
					    			 
					    		 
				    			 }else if(listaVerbali.get(i).getTipo_verifica_gvr()==13 ) {
				    				 tipo_ver_gvr = "Ver. funzionamento quinquennale + Ver. integrità";
				    			 
				    			 
				    			 }else if(listaVerbali.get(i).getTipo_verifica_gvr()==5 ) {
				    				 tipo_ver_gvr = "Ver. funzionamento biennale + Ver. integrità + Visita interna";
			    			 
				    		 	}else if(listaVerbali.get(i).getTipo_verifica_gvr()==6 ) {
				    				 tipo_ver_gvr = "Visita interna + Ver. integrità";
					    			 
				    		 	}else if(listaVerbali.get(i).getTipo_verifica_gvr()==7 ) {
				    				 tipo_ver_gvr = "Ver. funzionamento biennale + Visita interna";
					    			 
				    		 	}
				    		 }
				    		
							cell.setCellValue(tipo_ver_gvr); //TIPO VERIFICA GVR
							
							 col++;
				    		 cell = row.createCell(col);				
							if(listaAttrVerbale.get(j)!=null && listaAttrVerbale.get(j).getSogg_messa_serv_GVR()!=null && !listaAttrVerbale.get(j).getSogg_messa_serv_GVR().equals("")) {						
								cell.setCellValue(listaAttrVerbale.get(j).getSogg_messa_serv_GVR());
							}else {
								if(listaAttrVerbale.get(j).getId_insieme()!=null) {
									AttrezzaturaDTO attr = GestioneAttrezzatureBO.getAttrezzaturaFromId(listaAttrVerbale.get(j).getId_insieme(), session);
									if(attr.getSogg_messa_serv_GVR()!=null) {
										cell.setCellValue(attr.getSogg_messa_serv_GVR());
									}else {
										cell.setCellValue("");	
									}
									
								}else {
									cell.setCellValue("");	
								}
								
							}
							
							 col++;
				    		 cell = row.createCell(col);
							if(listaAttrVerbale.get(j)!=null && listaAttrVerbale.get(j).getID_specifica()!=null) {						
								cell.setCellValue(listaAttrVerbale.get(j).getID_specifica());
							}else {
								cell.setCellValue("");
							}
							 col++;
				    		 cell = row.createCell(col);
							if(listaAttrVerbale.get(j)!=null && listaAttrVerbale.get(j).getN_panieri_idroestrattori()!=null) {						
								cell.setCellValue(listaAttrVerbale.get(j).getN_panieri_idroestrattori()+"");
							}else {
								cell.setCellValue("");
							}
							 col++;
				    		 cell = row.createCell(col);
							if(listaAttrVerbale.get(j)!=null && listaAttrVerbale.get(j).getModello()!=null) {						
								cell.setCellValue(listaAttrVerbale.get(j).getModello());
							}else {
								cell.setCellValue("");
							}
							 col++;
				    		 cell = row.createCell(col);
							if(listaAttrVerbale.get(j)!=null && listaAttrVerbale.get(j).getNumero_fabbrica()!=null) {						
								cell.setCellValue(listaAttrVerbale.get(j).getNumero_fabbrica());
							}else {
								cell.setCellValue("");
							}
							 col++;
				    		 cell = row.createCell(col);
							if(listaAttrVerbale.get(j)!=null && listaAttrVerbale.get(j).getMarcatura()!=null) {						
								cell.setCellValue(listaAttrVerbale.get(j).getMarcatura());
							}else {
								cell.setCellValue("");
							}
							 col++;
				    		 cell = row.createCell(col);
							if(listaAttrVerbale.get(j)!=null && listaAttrVerbale.get(j).getN_id_on()!=null) {
								cell.setCellValue(listaAttrVerbale.get(j).getN_id_on());
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
				    			 
				    		 String articolo = GestioneAttrezzatureBO.getArticoloFromDescrizione(listaAttrVerbale.get(j).getDescrizione(), listaVerbali.get(i).getTipo_verifica(), session);
				    		 	    		 		    		 
				    		 String tariffe = GestioneCommesseDAO.getTariffeFromArticolo(articolo, listaVerbali.get(i).getIntervento().getIdCommessa());
				    		 
				    		 if(!tariffe.equals("") && tariffe.split(";").length>1) {
				    			 
					    		 String tariffa_app = tariffe.split(";")[0];
					    		 String tariffa_regolare = tariffe.split(";")[1];
					    		 
					    		 if(listaAttrVerbale.get(j).getId_insieme()==null) {
					    			 cell.setCellValue(new Double(tariffa_app)); //TARIFFA
					    		 }else {
					    			 cell.setCellValue(""); //TARIFFA
					    		 }
					    		
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
				    		 
				    		 if(listaAttrVerbale.get(j).getComune_div()!=null) {
				    			 cell.setCellValue(listaAttrVerbale.get(j).getIndirizzo_div()); // ATTR INDIRIZZO
								 col++;
					    		 cell = row.createCell(col);
								cell.setCellValue(listaAttrVerbale.get(j).getComune_div()); // ATTR COMUNE
								 col++;
					    		 cell = row.createCell(col);
								cell.setCellValue(listaAttrVerbale.get(j).getCap_div()); // ATTR CAP
								 col++;
					    		 cell = row.createCell(col);
								cell.setCellValue(listaAttrVerbale.get(j).getProvincia_div()); // ATTR PROVINCIA
								 col++;
					    		 cell = row.createCell(col);
								cell.setCellValue(listaAttrVerbale.get(j).getRegione_div()); // ATTR REGIONE 
				    		 }else {
				    			 cell.setCellValue(listaAttrVerbale.get(j).getIndirizzo()); // ATTR INDIRIZZO
								 col++;
					    		 cell = row.createCell(col);
								cell.setCellValue(listaAttrVerbale.get(j).getComune()); // ATTR COMUNE
								 col++;
					    		 cell = row.createCell(col);
								cell.setCellValue(listaAttrVerbale.get(j).getCap()); // ATTR CAP
								 col++;
					    		 cell = row.createCell(col);
								cell.setCellValue(listaAttrVerbale.get(j).getProvincia()); // ATTR PROVINCIA
								 col++;
					    		 cell = row.createCell(col);
								cell.setCellValue(listaAttrVerbale.get(j).getRegione()); // ATTR REGIONE 
				    		 }
							
				    		 
				    		 df.applyPattern("yyyy-MM-dd");	
				    		 if(data_pvp!=null) {
				    			 date = df.parse(df.format(data_pvp)); 
					    		 
					    		 df.applyPattern("ddMMyyyy");		
					    		 
								 col++;
					    		 cell = row.createCell(col);
								//cell.setCellValue("fattura "+matr_anno+matr_cod_att+matr_numero+matr_prov+tipo_verifica+df.format(date)); // FATTURA
					    		 cell.setCellValue("fattura "+matr_anno+matr_cod_att+matr_numero+matr_prov+df.format(date)); // FATTURA
								 col++;
					    		 cell = row.createCell(col);		    		 
					    				  	    		 
					    		 
								//cell.setCellValue("verbale "+matr_anno+matr_cod_att+matr_numero+matr_prov+tipo_verifica+df.format(date)); //VERBALE
					    		 cell.setCellValue("verbale "+matr_anno+matr_cod_att+matr_numero+matr_prov+df.format(date)); //VERBALE
								 col++;
					    		 cell = row.createCell(col);
					    		 
								if(listaVerbali.get(i).getSchedaTecnica()!=null) {
									//cell.setCellValue("scheda tecnica "+matr_anno+matr_cod_att+matr_numero+matr_prov+tipo_verifica+df.format(date)); //SCHEDA TECNICA
									cell.setCellValue("scheda tecnica "+matr_anno+matr_cod_att+matr_numero+matr_prov+df.format(date)); //SCHEDA TECNICA
								}else {
									cell.setCellValue(""); //SCHEDA TECNICA	
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
							
//							 col++;
//				    		 cell = row.createCell(col);
//							cell.setCellValue("");
							
							 col++;
				    		 cell = row.createCell(col);
				    		 cell.setCellValue(listaAttrVerbale.get(j).getFabbricante()); // COSTRUTTORE RAG SOCIALE
							
							 col++;
				    		 cell = row.createCell(col);
							cell.setCellValue("02795170600");
							
							 col++;
				    		 cell = row.createCell(col);
							cell.setCellValue("");
							
							 col++;
				    		 cell = row.createCell(col);
				    		 
				    		 if(listaAttrVerbale.get(j).getId_insieme()!=null) {
				    			 
				    			cell.setCellValue(GestioneAttrezzatureBO.getAttrezzaturaFromId(listaAttrVerbale.get(j).getId_insieme(), session).getMatricola_inail());
				    		 }else {
				    			 cell.setCellValue("");
				    		 }
				    		 
							
							
							 col++;
				    		 cell = row.createCell(col);
							cell.setCellValue("");
							
							 col++;
				    		 cell = row.createCell(col);
							cell.setCellValue("");
							
							 col++;
				    		 cell = row.createCell(col);
							cell.setCellValue("");
							
							 col++;
				    		 cell = row.createCell(col);
							cell.setCellValue("");
							
							 col++;
				    		 cell = row.createCell(col);
							cell.setCellValue("");
							
							 col++;
				    		 cell = row.createCell(col);
							cell.setCellValue("");
							
							 col++;
				    		 cell = row.createCell(col);
							cell.setCellValue("");
							
							 col++;
				    		 cell = row.createCell(col);
							cell.setCellValue("");
							
							 col++;
				    		 cell = row.createCell(col);
							cell.setCellValue("");
							
							 col++;
				    		 cell = row.createCell(col);
							cell.setCellValue("");
							
										
					
							
							
//							 rowTitle.createCell(46).setCellValue("cf_eci");
//							 rowTitle.createCell(47).setCellValue("matricola_insieme");
							 
							 col++;
				    		 cell = row.createCell(col);
							cell.setCellValue("");
							
							
							 col++;
				    		 cell = row.createCell(col);
							cell.setCellValue("");
							
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
				
	    		 
	    	 }
	    		 
		}
	     
	     
	     
	     
	    	 for(int j = 0; j<63;j++) {
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

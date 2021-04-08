package it.portalECI.bo;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;

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
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.google.gson.JsonArray;

import TemplateReport.PivotTemplate;
import it.portalECI.DTO.CampioneDTO;
import it.portalECI.DTO.CommessaDTO;
import it.portalECI.DTO.InterventoDTO;

import it.portalECI.Util.Costanti;
import it.portalECI.Util.Templates;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.component.SubreportBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.SplitType;
import net.sf.dynamicreports.report.constant.VerticalTextAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;

public class CreateSchedaScadenzarioCampioni {

	
	public CreateSchedaScadenzarioCampioni(ArrayList<CampioneDTO> campioni, ArrayList<Integer> descrizioni,ArrayList<String> date, String data_start, String data_end) throws Exception {
		try {
		
			build( campioni, descrizioni, date, data_start,  data_end);
		} catch (Exception e) {
			
			e.printStackTrace();
			throw e;
		} 
	}
	
	
	
	
	
	private void build(ArrayList<CampioneDTO> campioni, ArrayList<Integer> descrizioni,ArrayList<String> date, String data_start, String data_end) throws Exception {
	
		
		
		 XSSFWorkbook workbook = new XSSFWorkbook();         
         
		 XSSFSheet sheet0 = workbook.createSheet("Scadenzario campioni");
		 
		 workbook.setSheetOrder("Scadenzario campioni", 0);
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
	     		 
		 
		 Row rowTitle = sheet0.createRow(0);
		 Row rowHeader = sheet0.createRow(1);
		 
		 for(int j = 0; j<6; j++) {
			 rowTitle.createCell(j);			 
			 rowTitle.getCell(j).setCellStyle(yellowStyle);
			 rowHeader.createCell(j);			 
			 rowHeader.getCell(j).setCellStyle(greenStyle);
		 }
		 
		 
		 DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");			
			
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(data_end));
		cal.add(Calendar.DATE, -1);
		Date d = cal.getTime();

		 sheet0.addMergedRegion(CellRangeAddress.valueOf("A1:F1"));
		 sheet0.getRow(0).getCell(0).setCellValue("CAMPIONI IN SCADENZA TRA IL "+ df.format(sdf.parse(data_start)) + " E IL "+ df.format(d));
		 
		 sheet0.getRow(1).getCell(0).setCellValue("Codice Interno");
		 
		 sheet0.getRow(1).getCell(1).setCellValue("Descrizione");
		 
		 sheet0.getRow(1).getCell(2).setCellValue("Matricola");	
		 
		 sheet0.getRow(1).getCell(3).setCellValue("Tipo Scadenza");	
		 		 
		 sheet0.getRow(1).getCell(4).setCellValue("Data scadenza");		 
		 
		 sheet0.getRow(1).getCell(5).setCellValue("Attività di taratura");
  
	     int row_index = 1;	
	     
	     for (int i = 0; i<campioni.size(); i++) {	
			
	    	 CampioneDTO campione = campioni.get(i);
	    	 
	    	 
				if(campione!=null && campione.getDataScadenza()!=null && !campione.getCodice().equals("NA"))
				{
					 Row row = sheet0.createRow(1+row_index);
					
					 int col = 0;
			    	 
			    	 Cell cell = row.createCell(col);	
					
			    	 
			    	 cell.setCellValue(campione.getCodice());

			    	 col++;
			    	 cell = row.createCell(col);
			    	 
			    	 cell.setCellValue(campione.getDescrizione());

			    	 col++;
			    	 cell = row.createCell(col);
			    	 
			    	 cell.setCellValue(campione.getMatricola());

			    	 col++;
			    	 cell = row.createCell(col);
			    	 String tipo = "";
			    	 if(descrizioni!=null && descrizioni.get(i)!=null && descrizioni.get(i)==1) {
		 					tipo = "Manutenzione";
		 				}else if(descrizioni!=null && descrizioni.get(i)!=null && descrizioni.get(i)==2) {
		 					tipo = "Verifica Intermedia";
		 				}else if(descrizioni!=null && descrizioni.get(i)!=null && descrizioni.get(i)==3) {
		 					tipo = "Taratura";
		 				}
			    	 
			    	 cell.setCellValue(tipo);			    	 
			    	 
			    	 col++;
			    	 cell = row.createCell(col);			    	 

			 		 cell.setCellValue(df.format(sdf.parse(date.get(i))));
			 		col++;
			    	 cell = row.createCell(col);
			 		 
			 		 
			 		if(tipo.equals("Taratura")) {
			 			cell.setCellValue(campione.getAttivita_di_taratura());
	 				}else {
	 					cell.setCellValue("");
	 				}
			 		 
			 		
			 		row_index++;
			 		
				}
	     
	     
	     }

//		InputStream is = PivotTemplate.class.getResourceAsStream("scadenzario_campioni_eci.jrxml");		 
//		
//		StyleBuilder textStyle = stl.style(Templates.columnStyle).setBorder(stl.pen1Point()).setFontSize(8);		
// 
//		JasperReportBuilder report = DynamicReports.report(); 
//		
//		try {
// 				
// 			report.setTemplateDesign(is);
//			report.setTemplate(Templates.reportTemplate);
//			
//			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");			
//			
//			Calendar cal = Calendar.getInstance();
//			cal.setTime(sdf.parse(data_end));
//			cal.add(Calendar.DATE, -1);
//			Date d = cal.getTime();
//			
//			report.addParameter("mese", " TRA IL "+ df.format(sdf.parse(data_start)) + " E IL "+ df.format(d));
// 
//			report.setColumnStyle(textStyle); //AGG
//
//			SubreportBuilder subreport = cmp.subreport(getTableReport(campioni, descrizioni, date));
//			
//			report.addDetail(subreport);
//		
//			report.setDataSource(new JREmptyDataSource());
	     
	     
	     for(int j = 0; j<10;j++) {
    		 sheet0.autoSizeColumn(j);
    	 }
     
 		
	     File folder = new File(Costanti.PATH_ROOT+"//ScadenzarioCampioni//");
			if(!folder.exists()) {
				folder.mkdirs();
			}
        FileOutputStream fileOut = new FileOutputStream(folder.getPath() +"//SchedaListacampioni.xlsx");
        workbook.write(fileOut);
        fileOut.close();

        workbook.close();
	     
	     
			
//			File folder = new File(Costanti.PATH_ROOT+"//ScadenzarioCampioni//");
//			if(!folder.exists()) {
//				folder.mkdirs();
//			}
//				
//			  java.io.File file = new java.io.File(Costanti.PATH_ROOT+"//ScadenzarioCampioni//SchedaListacampioni.pdf");
//			  FileOutputStream fos = new FileOutputStream(file);
//			 // report.toPdf(fos);
//			  fos.close();
//			  
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw e;
//			
//		}
	}
	

	public JasperReportBuilder getTableReport(ArrayList<CampioneDTO> campioni, ArrayList<Integer> descrizioni,ArrayList<String> date) throws Exception{

		StyleBuilder textStyle = stl.style(Templates.columnStyle).setBorder(stl.pen1Point()).setFontSize(7);//AGG
		
	 
		JasperReportBuilder report = DynamicReports.report();

		try {
			report.setTemplate(Templates.reportTemplateVerde);
			report.setColumnStyle(textStyle); 
			
 	 		report.addColumn(col.column("Codice Interno", "codInterno", type.stringType()));
	 		report.addColumn(col.column("Descrizione", "descrizione", type.stringType()));
	 		report.addColumn(col.column("Matricola", "matricola", type.stringType()));
	 		report.addColumn(col.column("Tipo scadenza", "tipo", type.stringType()));
	 		report.addColumn(col.column("Data scadenza", "data", type.stringType()));
	 		report.addColumn(col.column("Attività di taratura", "attivita", type.stringType()));

			report.setDetailSplitType(SplitType.PREVENT);
			
			report.setDataSource(createDataSource(campioni, descrizioni, date));
	  
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return report;
	}

	private JRDataSource createDataSource(ArrayList<CampioneDTO> campioni, ArrayList<Integer> descrizioni,ArrayList<String> date)throws Exception {
			
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");			

		String[] listaCodici = new String[6];
		
		listaCodici[0]="codInterno";
		listaCodici[1]="descrizione";
		listaCodici[2]="matricola";
		listaCodici[3]="tipo";
		listaCodici[4]="data";
		listaCodici[5]="attivita";
		
		DRDataSource dataSource = new DRDataSource(listaCodici);
		
		int i = 0;
		for (CampioneDTO campione : campioni) {		
				
				if(campione!=null)
				{
					ArrayList<String> arrayPs = new ArrayList<String>();
					String tipo = "";
					if(campione.getDataScadenza()!=null && !campione.getCodice().equals("NA")) 
					{
	 				arrayPs.add(campione.getCodice());
	 				arrayPs.add(campione.getDescrizione());
	 				arrayPs.add(campione.getMatricola());
	 				if(descrizioni!=null && descrizioni.get(i)!=null && descrizioni.get(i)==1) {
	 					tipo = "Manutenzione";
	 				}else if(descrizioni!=null && descrizioni.get(i)!=null && descrizioni.get(i)==2) {
	 					tipo = "Verifica Intermedia";
	 				}else if(descrizioni!=null && descrizioni.get(i)!=null && descrizioni.get(i)==3) {
	 					tipo = "Taratura";
	 				}
	 				arrayPs.add(tipo);
	 				arrayPs.add(df.format(sdf.parse(date.get(i))));
	 				if(tipo.equals("Taratura")) {
	 					arrayPs.add(campione.getAttivita_di_taratura());
	 				}else {
	 					arrayPs.add("");
	 				}					
	 				
			         Object[] listaValori = arrayPs.toArray();
			        
			         dataSource.add(listaValori);
			         i++;
					}     
				}
			
			}
 		    return dataSource;
 	}
	
//	public static void main(String[] args) throws HibernateException, Exception {
//		
//		InterventoDTO intervento = GestioneInterventoBO.getIntervento("97");
//	
//		ArrayList<MisuraDTO> listaMisure = GestioneInterventoBO.getListaMirureByIntervento(intervento.getId());
//		ArrayList<CampioneDTO> listaCampioni = new ArrayList<CampioneDTO>();
//		
//		for (MisuraDTO misura : listaMisure) {
//		//	List<CampioneDTO> listaCampioniMisura = GestioneMisuraBO.getListaCampioni(misura.getListaPunti());
//		//	listaCampioni.addAll(listaCampioniMisura);
//		}
//		
//		
//		new CreateSchedaListaCampioni(intervento, listaCampioni,null,null);
//	}
}

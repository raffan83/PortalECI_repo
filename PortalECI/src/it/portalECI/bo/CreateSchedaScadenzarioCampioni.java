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
	

		InputStream is = PivotTemplate.class.getResourceAsStream("scadenzario_campioni_eci.jrxml");		 
		
		StyleBuilder textStyle = stl.style(Templates.columnStyle).setBorder(stl.pen1Point()).setFontSize(8);		
 
		JasperReportBuilder report = DynamicReports.report(); 
		
		try {
 				
 			report.setTemplateDesign(is);
			report.setTemplate(Templates.reportTemplate);
			
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");			
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(sdf.parse(data_end));
			cal.add(Calendar.DATE, -1);
			Date d = cal.getTime();
			
			report.addParameter("mese", " TRA IL "+ df.format(sdf.parse(data_start)) + " E IL "+ df.format(d));
 
			report.setColumnStyle(textStyle); //AGG

			SubreportBuilder subreport = cmp.subreport(getTableReport(campioni, descrizioni, date));
			
			report.addDetail(subreport);
		
			report.setDataSource(new JREmptyDataSource());
			
			File folder = new File(Costanti.PATH_ROOT+"//ScadenzarioCampioni//");
			if(!folder.exists()) {
				folder.mkdirs();
			}
				
			  java.io.File file = new java.io.File(Costanti.PATH_ROOT+"//ScadenzarioCampioni//SchedaListacampioni.pdf");
			  FileOutputStream fos = new FileOutputStream(file);
			  report.toPdf(fos);
			  fos.close();
			  
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
			
		}
	}
	

	public JasperReportBuilder getTableReport(ArrayList<CampioneDTO> campioni, ArrayList<Integer> descrizioni,ArrayList<String> date) throws Exception{

		StyleBuilder textStyle = stl.style(Templates.columnStyle).setBorder(stl.pen1Point()).setFontSize(7);//AGG
		
	 
		JasperReportBuilder report = DynamicReports.report();

		try {
			report.setTemplate(Templates.reportTemplateVerde);
			report.setColumnStyle(textStyle); 
			
 	 		report.addColumn(col.column("Codice Interno", "codInterno", type.stringType()));
	 		report.addColumn(col.column("Denominazione", "denominazione", type.stringType()));
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
		listaCodici[1]="denominazione";
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
	 				arrayPs.add(campione.getNome());
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

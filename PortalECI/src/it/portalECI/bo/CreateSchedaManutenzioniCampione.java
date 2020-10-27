package it.portalECI.bo;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import TemplateReport.PivotTemplate;
import it.portalECI.DTO.AcAttivitaCampioneDTO;
import it.portalECI.DTO.CampioneDTO;
import it.portalECI.Util.Costanti;
import it.portalECI.Util.Templates;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.component.SubreportBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.Markup;
import net.sf.dynamicreports.report.constant.VerticalTextAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;

public class CreateSchedaManutenzioniCampione {
	
	public CreateSchedaManutenzioniCampione(ArrayList<AcAttivitaCampioneDTO> lista_manutenzioni, CampioneDTO campione) throws Exception {
		
		build(lista_manutenzioni, campione);		
	}
	
	private void build(ArrayList<AcAttivitaCampioneDTO> lista_manutenzioni, CampioneDTO campione) throws Exception {
		
	InputStream is =  PivotTemplate.class.getResourceAsStream("schedaManutenzioniCampione_eci.jrxml");
		
		JasperReportBuilder report = DynamicReports.report();		
		
		report.setTemplateDesign(is);
		report.setTemplate(Templates.reportTemplate);
				
		report.setDataSource(new JREmptyDataSource());
			
		//CampioneDTO campione = lista_manutenzioni.get(0).getCampione();
		
//		File imageHeader = new File(Costanti.PATH_FOLDER_LOGHI +"logo_sti.png");
//		if(imageHeader!=null) {
//			report.addParameter("immagine",imageHeader);
//		
//		}
		report.addParameter("titolo", "SCHEDA DI MANUTENZIONE APPARECCHIATURA (SMA)");
		report.addParameter("tipo_scheda", "SMA:");
//		if(campione.getNome()!=null) {
//			report.addParameter("denominazione", campione.getNome());
//		}else {
//			report.addParameter("denominazione", "");
//		}
		if(campione.getCodice()!=null) {
			report.addParameter("codice_interno", campione.getCodice());
		}else {
			report.addParameter("codice_interno", "");
		}
//		if(campione.getMatricola()!=null) {
//			report.addParameter("matricola", campione.getMatricola());
//		}else {
//			report.addParameter("matricola", "");
//		}
		
		SubreportBuilder subreport =	cmp.subreport(getTableReport(lista_manutenzioni)); 
		
		report.detail(subreport);
		
		StyleBuilder footerStyle = Templates.footerStyle.setFontSize(8).setTextAlignment(HorizontalTextAlignment.LEFT, VerticalTextAlignment.MIDDLE).setMarkup(Markup.HTML);
		
			report.addPageFooter(cmp.horizontalList(
					cmp.text("MOD-PG007-03").setStyle(footerStyle),
					cmp.horizontalGap(370),
					cmp.text("Rev. A del 24/03/2020").setStyle(footerStyle)
					));
		
		//String path = "C:\\Users\\antonio.dicivita\\Desktop\\";
		String path = Costanti.PATH_ROOT+"\\Campioni\\"+campione.getId()+"\\SchedaManutenzione\\";			
						
				
		  java.io.File folder = new java.io.File(path);
		  if(!folder.exists()) {
			  folder.mkdirs();
			  
		  }
		
		  File file = new File(path+"sma_"+campione.getId() +".pdf");
		  FileOutputStream fos = new FileOutputStream(file);
		  report.toPdf(fos);
		
		  fos.close();
		  
	}


	private JasperReportBuilder getTableReport(ArrayList<AcAttivitaCampioneDTO> lista_manutenzioni) throws Exception {
		
		JasperReportBuilder report = DynamicReports.report();

		report.setColumnStyle((Templates.boldCenteredStyle).setBackgroundColor(Color.WHITE).setFontSize(9));
		report.addColumn(col.column("Data","data", type.stringType()).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER).setFixedWidth(60));	
		report.addColumn(col.column("Tipo Manutenzione", "tipo", type.stringType()).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER).setFixedWidth(65));
	 	report.addColumn(col.column("Registrazione dati rilevati / Descrizione dell'intervento effettuato","descrizione", type.stringType()).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER));
	 	report.addColumn(col.column("Operatore","operatore", type.stringType()).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER).setFixedWidth(65));
	 	
		report.setColumnTitleStyle((Templates.boldCenteredStyle).setBackgroundColor(Color.WHITE).setFontSize(9).setBorder(stl.penThin()));
		
	 	report.setDataSource(createDataSource(lista_manutenzioni));
	 	report.highlightDetailEvenRows();
		return report;
	}

	private JRDataSource createDataSource(ArrayList<AcAttivitaCampioneDTO> lista_manutenzioni)throws Exception {
		DRDataSource dataSource = null;
		String[] listaCodici = null;
			
			listaCodici = new String[4];
					
			listaCodici[0]="data";
			listaCodici[1]="tipo";
			listaCodici[2]="descrizione";
			listaCodici[3]="operatore";
			
			dataSource = new DRDataSource(listaCodici);
			
			SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
			if(lista_manutenzioni.size()>0) {
				for (AcAttivitaCampioneDTO manutenzione : lista_manutenzioni) {						
					if(manutenzione!=null){
						ArrayList<String> arrayPs = new ArrayList<String>();
							
						arrayPs.add(dt.format(manutenzione.getData()));
						if(manutenzione.getTipo_manutenzione()==1) {
							arrayPs.add("Preventiva");
						}else {
							arrayPs.add("Straordinaria");
						}
						arrayPs.add(manutenzione.getDescrizione_attivita());
						if(manutenzione.getOperatore()!=null) {
							arrayPs.add(manutenzione.getOperatore().getNominativo());	
						}else {
							arrayPs.add("");
						}						
						
			 			Object[] listaValori = arrayPs.toArray();
						
					    dataSource.add(listaValori);				   
					}				
				}
			}else {
				dataSource.add("","","","");
			}
				
		 		    return dataSource;
		 	}
	
	
//	public static void main(String[] args) throws Exception {
//		new ContextListener().configCostantApplication();
//		Session session=SessionFacotryDAO.get().openSession();
//		session.beginTransaction();
//		
//		ArrayList<AcAttivitaCampioneDTO> lista_manutenzioni = GestioneAttivitaCampioneBO.getListaManutenzioniCampione(54800, session);
//		new CreateSchedaManutenzioniCampione(lista_manutenzioni);
//		
//		session.close();
//		System.out.println("FINITO");
//	}
}

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

public class CreateSchedaTaraturaVerificaIntermedia {
	
public CreateSchedaTaraturaVerificaIntermedia(ArrayList<AcAttivitaCampioneDTO> lista_tar_ver, CampioneDTO campione) throws Exception {
		
		build(lista_tar_ver, campione);		
	}


private void build(ArrayList<AcAttivitaCampioneDTO> lista_tar_ver,  CampioneDTO campione) throws Exception {
	
	InputStream is =  PivotTemplate.class.getResourceAsStream("schedaManutenzioniCampione_eci.jrxml");
		
		JasperReportBuilder report = DynamicReports.report();		
		
		report.setTemplateDesign(is);
		report.setTemplate(Templates.reportTemplate);
				
		report.setDataSource(new JREmptyDataSource());
			
		//CampioneDTO campione = lista_manutenzioni.get(0).getCampione();
//		
//		File imageHeader = new File(Costanti.PATH_ROOT +"logo_sti.png");
//		if(imageHeader!=null) {
//			report.addParameter("immagine",imageHeader);
//		
//		}
		
		
		report.addParameter("titolo", "SCHEDA TARATURA APPARECCHIATURA (STA)");
		report.addParameter("tipo_scheda", "STA:");
		if(campione.getNome()!=null) {
			report.addParameter("denominazione", campione.getNome());
		}else {
			report.addParameter("denominazione", "");
		}
		if(campione.getCodice()!=null) {
			report.addParameter("codice_interno", campione.getCodice());
		}else {
			report.addParameter("codice_interno", "");
		}
		if(campione.getMatricola()!=null) {
			report.addParameter("matricola", campione.getMatricola());
		}else {
			report.addParameter("matricola", "");
		}
		
		SubreportBuilder subreport = cmp.subreport(getTableReport(lista_tar_ver));	
		
		
		report.detail(subreport);
		StyleBuilder footerStyle = Templates.footerStyle.setFontSize(8).setTextAlignment(HorizontalTextAlignment.LEFT, VerticalTextAlignment.MIDDLE).setMarkup(Markup.HTML);
		
			report.addPageFooter(cmp.horizontalList(
					cmp.text("MOD-PG007-04").setStyle(footerStyle),
					cmp.horizontalGap(370),
					cmp.text("Rev. A del 24/03/2020").setStyle(footerStyle)
					));
	
		
		
		//String path = "C:\\Users\\antonio.dicivita\\Desktop\\";
		
		String path =  Costanti.PATH_ROOT+"\\Campioni\\"+campione.getId()+"\\Taratura\\"; 
		
		
		  java.io.File folder = new java.io.File(path);
		  if(!folder.exists()) {
			  folder.mkdirs();
			  
		  }
		
		  File file = new File(path+"sta_"+campione.getId()+".pdf");
		  FileOutputStream fos = new FileOutputStream(file);
		  report.toPdf(fos);
		
		  fos.close();
		  
	}

	private JasperReportBuilder getTableReport(ArrayList<AcAttivitaCampioneDTO> lista_tar_ver) throws Exception {
		
		JasperReportBuilder report = DynamicReports.report();

		report.setColumnStyle((Templates.boldCenteredStyle).setBackgroundColor(Color.WHITE).setFontSize(9));
		//report.addColumn(col.column("Data","data", type.stringType()).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER).setFixedWidth(60));	
		report.addColumn(col.column("Tipo Attività", "tipo", type.stringType()).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER));
		report.addColumn(col.column("Ente", "ente", type.stringType()).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER));
		report.addColumn(col.column("Data", "data", type.stringType()).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER));
	 	report.addColumn(col.column("Certificato di taratura","certificato", type.stringType()).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER));
	 	report.addColumn(col.column("Data scadenza","data_scadenza", type.stringType()).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER));
	 	report.addColumn(col.column("Etichettatura di conferma","etichettatura", type.stringType()).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER));
	 	report.addColumn(col.column("Stato","stato", type.stringType()).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER));
	 	report.addColumn(col.column("Note","campo_sospesi", type.stringType()).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER));
	 	report.addColumn(col.column("Operatore","operatore", type.stringType()).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER));
	 	
		report.setColumnTitleStyle((Templates.boldCenteredStyle).setBackgroundColor(Color.WHITE).setFontSize(9).setBorder(stl.penThin()));
		
	 	report.setDataSource(createDataSource(lista_tar_ver));
		report.highlightDetailEvenRows();
		return report;
	}


	
	private JRDataSource createDataSource(ArrayList<AcAttivitaCampioneDTO> lista_tar_ver)throws Exception {
		DRDataSource dataSource = null;
		String[] listaCodici = null;
			
			listaCodici = new String[9];
					
			listaCodici[0]="tipo";
			listaCodici[1]="ente";
			listaCodici[2]="data";
			listaCodici[3]="certificato";
			listaCodici[4]="data_scadenza";
			listaCodici[5]="etichettatura";
			listaCodici[6]="stato";
			listaCodici[7]="campo_sospesi";
			listaCodici[8]="operatore";
			
			dataSource = new DRDataSource(listaCodici);
			
			SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
			if(lista_tar_ver.size()>0) {
				for (AcAttivitaCampioneDTO attivita : lista_tar_ver) {						
					if(attivita!=null){
						ArrayList<String> arrayPs = new ArrayList<String>();
						
						arrayPs.add(attivita.getTipo_attivita().getDescrizione());
						if(attivita.getEnte()!=null) {
							arrayPs.add(attivita.getEnte());	
						}else {
							arrayPs.add("");
						}											
						arrayPs.add(dt.format(attivita.getData()));
						if(attivita.getCertificato()!=null) {
							arrayPs.add(attivita.getCertificato());	
						}else {
							arrayPs.add("");
						}
						arrayPs.add(dt.format(attivita.getData_scadenza()));
						arrayPs.add(attivita.getEtichettatura());
						arrayPs.add(attivita.getStato());
						arrayPs.add(attivita.getCampo_sospesi());
						
						if(attivita.getOperatore()!=null) {
							arrayPs.add(attivita.getOperatore().getNominativo());	
						}else {
							arrayPs.add("");
						}						
						
			 			Object[] listaValori = arrayPs.toArray();
						
					    dataSource.add(listaValori);				   
					}				
				}
			}else {
				dataSource.add("","","","","","","","","");
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

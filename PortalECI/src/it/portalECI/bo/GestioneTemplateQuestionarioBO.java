package it.portalECI.bo;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.jsoup.Jsoup;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFile;
import com.itextpdf.tool.xml.html.CssAppliers;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.AbstractImageProvider;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import com.itextpdf.tool.xml.pipeline.html.LinkProvider;

import it.portalECI.DAO.GestioneRispostaQuestionarioDAO;
import it.portalECI.DAO.GestioneTemplateQuestionarioDAO;
import it.portalECI.DTO.ColonnaTabellaQuestionarioDTO;
import it.portalECI.DTO.DomandaQuestionarioDTO;
import it.portalECI.DTO.DomandaSchedaTecnicaQuestionarioDTO;
import it.portalECI.DTO.OpzioneRispostaQuestionarioDTO;
import it.portalECI.DTO.QuestionarioDTO;
import it.portalECI.DTO.RispostaFormulaQuestionarioDTO;
import it.portalECI.DTO.RispostaQuestionario;
import it.portalECI.DTO.RispostaSceltaQuestionarioDTO;
import it.portalECI.DTO.RispostaTabellaQuestionarioDTO;
import it.portalECI.DTO.RispostaTestoQuestionarioDTO;
import it.portalECI.DTO.RispostaVerbaleDTO;
import it.portalECI.DTO.TemplateQuestionarioDTO;
import it.portalECI.Util.Costanti;
import it.portalECI.Util.HeaderFooter;

public class GestioneTemplateQuestionarioBO {

	public static TemplateQuestionarioDTO getQuestionarioById(Integer idTemplate, Session session) {
		return GestioneTemplateQuestionarioDAO.getTemplateById(idTemplate, session);
	}
	
	public static File getAnteprimaQuestionario(TemplateQuestionarioDTO template, QuestionarioDTO questionario, Session session) throws Exception {
		
		String path = "QuestionarioTest"+File.separator;
		new File(Costanti.PATH_CERTIFICATI+path).mkdirs();
		File file = new File(Costanti.PATH_CERTIFICATI+path, "ProvaDownloadQuestionario.pdf");
		FileOutputStream fileOutput = new FileOutputStream(file);
		String html = new String(template.getTemplate());
		html = replacePlaceholders(html, questionario, session);
		String subheader = replacePlaceholders(template.getSubheader(), questionario, session);
		writePDF(fileOutput,html, template, subheader);
		
		return file;
	}
	
	
	
	
	static public void writePDF(OutputStream fileOutput,String html, TemplateQuestionarioDTO template, String subhedaer) throws Exception {
		final org.jsoup.nodes.Document documentJsoup = Jsoup.parse(html);
		documentJsoup.outputSettings().syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.xml);
		String validXHTML = documentJsoup.html();
	
		Document document = new Document(PageSize.A4);
		
		PdfWriter writer = PdfWriter.getInstance(document, fileOutput);
        
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date revisionDate = template.getUpdateDate()==null?new Date():template.getUpdateDate();
		HeaderFooter pageEventHandler;
		
		String revisione = "Revisione del "+dateFormat.format(revisionDate);
		if(template.getRevisione()!=null && !template.getRevisione().equals("")) {
			revisione = template.getRevisione();
		}
		if(subhedaer == null) {
			subhedaer = "";
		}
		
		try {
			pageEventHandler = new HeaderFooter(
					template.getHeader(),
					subhedaer,
					template.getFooter(),
					template.getTitolo(),
					revisione
			);
		}catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		writer.setPageEvent(pageEventHandler);
		pageEventHandler.formatDocument(document);
		document.open();
    
    
		// CSS
		CSSResolver cssResolver = XMLWorkerHelper.getInstance().getDefaultCssResolver(true);
		InputStream iscss = new FileInputStream(Costanti.PATH_FONT_STYLE+"style.css");
		CssFile cssFile = XMLWorkerHelper.getCSS(iscss);
		cssResolver.addCss(cssFile);
		cssResolver.addCss(XMLWorkerHelper.getInstance().getDefaultCSS());
    
		XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider(Costanti.PATH_FONT_STYLE);
		fontProvider.register(Costanti.PATH_FONT_STYLE+"Arial.ttf");        
		fontProvider.register(Costanti.PATH_FONT_STYLE+"Times.ttf");        
		fontProvider.register(Costanti.PATH_FONT_STYLE+"Courier.ttf");
		fontProvider.setUseUnicode(true);
		CssAppliers cssAppliers = new CssAppliersImpl(fontProvider);
		// 	HTML
		HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);
		htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
		htmlContext.setImageProvider(new AbstractImageProvider() {
			public String getImageRootPath() {
				return Costanti.PATH_ROOT;
			}
		});
		htmlContext.setLinkProvider(new LinkProvider() {
			public String getLinkRoot() {
				return Costanti.PATH_ROOT;
			}
		});
		// 	Pipelines
		
		PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
		HtmlPipeline htmlPipeline = new HtmlPipeline(htmlContext, pdf);
		CssResolverPipeline css = new CssResolverPipeline(cssResolver, htmlPipeline);

		XMLWorker worker = new XMLWorker(css, true);
		XMLParser p = new XMLParser(worker);
		p.parse(new ByteArrayInputStream(validXHTML.getBytes()),
    		Charset.forName("UTF-8"));
		
		document.close();

		
	}
	
	
//	private static void image2Pdf(String path) throws Exception {
//		path = "C:\\Users\\antonio.dicivita\\Desktop\\test_cert.pdf";
//		PDDocument document = PDDocument.load(new File(path));
//		PDFRenderer pdfRenderer = new PDFRenderer(document);
//		for (int page = 0; page < document.getNumberOfPages(); ++page)
//		{ 
//		    BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
//
//		    // suffix in filename will be used as the file format
//		    ImageIOUtil.writeImage(bim, path + "-" + (page+1) + ".png", 300);
//		}
//		document.close();
//		
//		PDDocument doc = new PDDocument();
//		doc.addPage(new PDPage());
//		 PDPage page = doc.getPage(0);
//
//         // createFromFile is the easiest way with an image file
//         // if you already have the image in a BufferedImage, 
//         // call LosslessFactory.createFromImage() instead
//         PDImageXObject pdImage = PDImageXObject.createFromFile(path+"-1.png", doc);
//
//         try (PDPageContentStream contentStream = new PDPageContentStream(doc, page, AppendMode.APPEND, true, true))
//         {
//             // contentStream.drawImage(ximage, 20, 20 );
//             // better method inspired by http://stackoverflow.com/a/22318681/535646
//             // reduce this value if the image is too large
//             float scale = 0.22f;
//             contentStream.drawImage(pdImage, 20, 20, pdImage.getWidth() * scale, pdImage.getHeight() * scale);
//         }
//         doc.save(path.split("test_cert")[0]+"test2.pdf");
//		
//	}
	
	private static String replacePlaceholders(String html, QuestionarioDTO questionario, Session session) {
		if(html==null || html.isEmpty()) {
			return "";
		}
		
		for (DomandaQuestionarioDTO domanda:questionario.getDomandeVerbale()) {
			html = replacePlaceholderDomanda(html,domanda, session);
		}
		
		for( DomandaSchedaTecnicaQuestionarioDTO domandeST:questionario.getDomandeSchedaTecnica())
		{
			html = replacePlaceholderDomanda(html,domandeST, session);
		}
		
		//Inserisco il nome del tecnico
		
		html = html.replaceAll("\\$\\{TECNICO_VERIFICATORE\\}", "TECNICO_VERIFICATORE");
		html = html.replaceAll("\\$\\{QUAL_TV\\}", " QUAL. TV");
		//Inserisco numero verbale
		String numeroVerbale = "NUMERO_VERBALE";
		html = html.replaceAll("\\$\\{NUMERO_VERBALE\\}", numeroVerbale);
	
		html = html.replaceAll("\\$\\{CLIENTE_UTILIZZATORE\\}", "CLIENTE UTILIZZATORE");
		html = html.replaceAll("\\$\\{INDIRIZZO_CLIENTE_UTILIZZATORE\\}","INDIRIZZO UTILIZZATORE"); 
		html = html.replaceAll("\\$\\{CLIENTE\\}","CLIENTE");
		html = html.replaceAll("\\$\\{INDIRIZZO_CLIENTE\\}", "INDIRIZZO CLIENTE");
		
		/*Attrezzatura*/
		html = html.replaceAll("\\$\\{ATT_MATRICOLA\\}", "MATRICOLA ATTREZZATURA");
		html = html.replaceAll("\\$\\{ATT_N_FABBRICA\\}", "N° FABBRICA");
		html = html.replaceAll("\\$\\{ATT_DESCRIZIONE\\}", "DESCRIZIONE ATTREZZATURA");
		html = html.replaceAll("\\$\\{ATT_ANNO_COSTRUZIONE\\}", "ANNO COSTRUZIONE");
		html = html.replaceAll("\\$\\{ATT_FABBRICANTE\\}", "FABBRICANTE");
		html = html.replaceAll("\\$\\{ATT_MODELLO\\}", "MODELLO");
		html = html.replaceAll("\\$\\{ATT_SETTORE_IMPIEGO\\}", "SETTORE IMPIEGO");
		
		
		html = html.replaceAll("\\$\\{STR_MARCA\\}", "MARCA");
		html = html.replaceAll("\\$\\{STR_MOD\\}", "MODELLO");
		html = html.replaceAll("\\$\\{STR_MATR\\}", "MATRICOLA");
		html = html.replaceAll("\\$\\{STR_SCADENZA\\}", "SCADENZA TARATURA");
		html = html.replaceAll("\\$\\{STR_ULT_TAR\\}", "ULTIMA TARATURA");
		html = html.replaceAll("\\$\\{STR_NUMERO_CERT\\}", "NUMERO CERTIFICATO");
		
			
		html = html.replaceAll("\\$\\{DATA_VERIFICA\\}", "DATA VERIFICA");
		html = html.replaceAll("\\$\\{DATA_PROSS_VERIFICA\\}", "DATA PROSSIMA VERIFICA");
		html = html.replaceAll("\\$\\{DATA_VER_INTEGRITA\\}", "DATA VERIFICA INTEGRITÀ");
		html = html.replaceAll("\\$\\{DATA_PROSS_VER_INTEGRITA\\}", "DATA PROSSIMA VERIFICA INTEGRITÀ");
		html = html.replaceAll("\\$\\{DATA_VER_INTERNA\\}", "DATA VERIFICA INTERNA");
		html = html.replaceAll("\\$\\{DATA_PROSS_VER_INTERNA\\}", "DATA PROSSIMA VERIFICA INTERNA");
		
		
		html = html.replaceAll("\\$\\{INDIRIZZO_UTILIZZATORE_VIE\\}", "INDIRIZZO UTILIZZATORE VIE");
	
		
		
		html = html.replaceAll("\\$\\{ESERCENTE\\}", "ESERCENTE");
		
		html = html.replaceAll("\\$\\{ORE_UOMO\\}", "ORE UOMO");
		
		html = html.replaceAll("\\$\\{MATRICOLA_VIE\\}", "MATRICOLA VIE");
		
		html = html.replaceAll("\\$\\{COM_PROV\\}", "COMUNE (PROVINCIA)");

		html = html.replaceAll("\\$\\{DATA_CONFERMA\\}", "DATA CONFERMA");
		
		html = html.replaceAll("\\$\\{DATA_VERIFICA_MULTIPLA\\}", "DATA VERIFICA - DATA VERIFICA INTEGRITA' - DATA VERIFICA INTERNA");
		
		String esito = "<img src=\"" + Costanti.PATH_FONT_IMAGE +"unchecked" +"-"+"radio"+".png" + "\" style=\"height:12px;\" />&nbsp;" + "Positivo&nbsp;";
			esito = esito + "<img src=\"" + Costanti.PATH_FONT_IMAGE +"unchecked" +"-"+"radio"+".png" + "\" style=\"height:12px;\" />&nbsp;" + "Negativo";
		
		
		html = html.replaceAll("\\$\\{ESITO_VERIFICA\\}", esito);
		html = html.replace("\\$\\{DATA_CONFERMA\\}", "DATA CONFERMA");
		

//		private static String getTemplateOpzione(OpzioneRispostaQuestionarioDTO opzione) {
//			String typeInput = opzione.getRisposta().getMultipla()?"checkbox":"radio";
//			String checked = "unchecked";
//			String optionName = opzione.getTesto();
//			String template = "<img src=\"" + Costanti.PATH_FONT_IMAGE + checked+"-"+typeInput+".png" + "\" style=\"height:12px;\" />&nbsp;" + optionName;
//			return template;
//		}
		
		String tipo_verifica_val = "<img src=\"" + Costanti.PATH_FONT_IMAGE + "unchecked"+"-"+"radio"+".png" + "\" style=\"height:12px;\" />&nbsp;" + "Prima verifica periodica"+"<br/>";
		
		tipo_verifica_val = tipo_verifica_val+"<img src=\"" + Costanti.PATH_FONT_IMAGE + "unchecked"+"-"+"radio"+".png" + "\" style=\"height:12px;\" />&nbsp;" + "Verifica periodica (successiva alla prima)"+"<br/>";
		
		html = html.replaceAll("\\$\\{TIPO_VERIFICA_VAL\\}", tipo_verifica_val);
		
		String tipo_verifica_val_gvr = "<img src=\"" + Costanti.PATH_FONT_IMAGE + "unchecked"+"-"+"radio"+".png" + "\" style=\"height:12px;\" />&nbsp;" + " Prima delle verifiche periodiche"+"<br/>";		
		tipo_verifica_val_gvr = tipo_verifica_val_gvr+"<img src=\"" + Costanti.PATH_FONT_IMAGE + "unchecked"+"-"+"radio"+".png" + "\" style=\"height:12px;\" />&nbsp;" + " Verifica di funzionamento"+"<br/>";
		tipo_verifica_val_gvr = tipo_verifica_val_gvr+"<img src=\"" + Costanti.PATH_FONT_IMAGE + "unchecked"+"-"+"radio"+".png" + "\" style=\"height:12px;\" />&nbsp;" + " Verifica di visita interna per generatori di vapore/acqua surriscaldata"+"<br/>";
		tipo_verifica_val_gvr = tipo_verifica_val_gvr+"<img src=\"" + Costanti.PATH_FONT_IMAGE + "unchecked"+"-"+"radio"+".png" + "\" style=\"height:12px;\" />&nbsp;" + " Verifica di integrità<br/>";
		
		html = html.replaceAll("\\$\\{TIPO_VERIFICA_VAL_GVR\\}", tipo_verifica_val_gvr);
		
		
		String tipo_verifica_vie = "<img src=\"" + Costanti.PATH_FONT_IMAGE + "unchecked"+"-"+"checkbox"+".png" + "\" style=\"height:12px;\" />&nbsp;" + "Verifica periodica"+"<br/>";
		
		tipo_verifica_vie = tipo_verifica_vie+"<img src=\"" + Costanti.PATH_FONT_IMAGE + "unchecked"+"-"+"checkbox"+".png" + "\" style=\"height:12px;\" />&nbsp;" + "Verifica straordinaria"+"<br/>";
		tipo_verifica_vie = tipo_verifica_vie+"&nbsp;&nbsp;&nbsp;  <img src=\"" + Costanti.PATH_FONT_IMAGE + "unchecked"+"-"+"checkbox"+".png" + "\" style=\"margin-left:25px;height:12px;\" />&nbsp;" + "Verifica periodica con esito negativo"+"<br/>";
		tipo_verifica_vie = tipo_verifica_vie+"&nbsp;&nbsp;&nbsp;  <img src=\"" + Costanti.PATH_FONT_IMAGE + "unchecked"+"-"+"checkbox"+".png" + "\" style=\"margin-left:25px;height:12px;\" />&nbsp;" + "Modifiche sostanziali all'impianto"+"<br/>";
		tipo_verifica_vie = tipo_verifica_vie+"&nbsp;&nbsp;&nbsp;  <img src=\"" + Costanti.PATH_FONT_IMAGE + "unchecked"+"-"+"checkbox"+".png" + "\" style=\"margin-left:15px;height:12px;\" />&nbsp;" + "Richiesta del datore di lavoro"+"<br/>";
		

		html = html.replaceAll("\\$\\{TIPO_VERIFICA_VIE\\}", tipo_verifica_vie);
		
		
		html = html.replaceAll("\\$\\{FIRMA_VERIFICATORE\\}", "FIRMA VERIFICATORE");
		
		
		
		html = html.replaceAll("\\$\\{RUOLO_RIESAME\\}", "Riesaminato RT");
		
	
		html = html.replaceAll("\\$\\{DATA_RIESAME\\}", "DATA RIESAME");	
	

		html = html.replaceAll("\\$\\{FIRMA_RIESAME\\}", "FIRMA RIESAME");
		
		
		String qr = "<img src=\"" + Costanti.PATH_FONT_IMAGE +"qr_example.png" + "\" style=\"height:50px;\" />";
		html = html.replaceAll("\\$\\{QR_CODE\\}", qr);
		
		// Elimino i placeholder non utilizzati
		html = html.replaceAll("\\$\\{(.*?)\\}", "");
		return html;
	}	
	
	private static String replacePlaceholderDomanda(String html,DomandaQuestionarioDTO domanda, Session session) {
		String placeholder = domanda.getPlaceholder();
		html = html.replaceAll("\\$\\{"+placeholder+"\\}", domanda.getTesto());
		RispostaQuestionario risposta = domanda.getRisposta();

		String rispostaValore = null;
		String rispostaPlaceholder = null;
		switch (risposta.getTipo()) {
		case RispostaQuestionario.TIPO_TESTO:
			RispostaTestoQuestionarioDTO rispostaTesto = GestioneRispostaQuestionarioDAO.getRispostaInstance(RispostaTestoQuestionarioDTO.class, risposta.getId(), session);
			rispostaPlaceholder = rispostaTesto.getPlaceholder();
			rispostaValore = "RISPOSTA OPERATORE";
			break;
		case RispostaVerbaleDTO.TIPO_SCELTA:
			RispostaSceltaQuestionarioDTO rispostaScelta = GestioneRispostaQuestionarioDAO.getRispostaInstance(RispostaSceltaQuestionarioDTO.class, risposta.getId(), session);
			rispostaPlaceholder = rispostaScelta.getPlaceholder();
			rispostaValore = getTemplateRisposta(rispostaScelta);
			for(OpzioneRispostaQuestionarioDTO opzione: rispostaScelta.getOpzioni()) {
				String opzionePlaceholder = opzione.getPlaceholder();
				String opzioneValore = getTemplateOpzione(opzione);
				html = html.replaceAll("\\$\\{"+opzionePlaceholder+"\\}", opzioneValore);
				if(opzione.getDomande() != null) {
					for (DomandaQuestionarioDTO domandaOpzione:opzione.getDomande()) {
						html = replacePlaceholderDomanda(html,domandaOpzione, session);
					}
				}
			}
			break;
		case RispostaVerbaleDTO.TIPO_FORMULA:
			RispostaFormulaQuestionarioDTO rispostaFormula = GestioneRispostaQuestionarioDAO.getRispostaInstance(RispostaFormulaQuestionarioDTO.class, risposta.getId(), session);
			rispostaPlaceholder = rispostaFormula.getPlaceholder();
			rispostaValore = getTemplateRisposta(rispostaFormula);
			break;
		case RispostaVerbaleDTO.TIPO_TABELLA:
			RispostaTabellaQuestionarioDTO rispostaTabella = GestioneRispostaQuestionarioDAO.getRispostaInstance(RispostaTabellaQuestionarioDTO.class, risposta.getId(), session);
			rispostaPlaceholder = rispostaTabella.getPlaceholder();
			rispostaValore = getTemplateRisposta(rispostaTabella);
			break;
		default:
			break;
		}
		if(rispostaValore!=null && rispostaPlaceholder!=null ) {
			html = html.replaceAll("\\$\\{"+rispostaPlaceholder+"\\}", rispostaValore);
		}
		return html;
	}
	
	private static String getTemplateRisposta(RispostaSceltaQuestionarioDTO risposta) {
		String template = "";			
		List<OpzioneRispostaQuestionarioDTO> opzioni=new ArrayList<OpzioneRispostaQuestionarioDTO>();
		opzioni.addAll(risposta.getOpzioni());
		
		Collections.sort(opzioni, new Comparator<OpzioneRispostaQuestionarioDTO>() {
	        @Override
	        public int compare(OpzioneRispostaQuestionarioDTO op2, OpzioneRispostaQuestionarioDTO op1){
				int pos1=op1.getPosizione();
				int pos2=op2.getPosizione();
	            return  pos2 - pos1;
	        }
	    });
		for (OpzioneRispostaQuestionarioDTO opzione: opzioni) {
			template +=  getTemplateOpzione(opzione)+"<br/>";
		}
		return template;
	}
	
	private static String getTemplateRisposta(RispostaFormulaQuestionarioDTO risposta) {
		String template = "";
		template += "<b>0</b> (" + risposta.getValore1() + ")&nbsp;&nbsp;";
		template += "<b>" + risposta.getSimboloOperatore() + "&nbsp;</b>";
		template += "<b>0</b> (" + risposta.getValore2() + ")&nbsp;=&nbsp;";
		template += "<b>0</b> (" + risposta.getRisultato() + ")";
		return template;
		
	}
	
	private static String getTemplateOpzione(OpzioneRispostaQuestionarioDTO opzione) {
		String typeInput = opzione.getRisposta().getMultipla()?"checkbox":"radio";
		String checked = "unchecked";
		String optionName = opzione.getTesto();
		String template = "<img src=\"" + Costanti.PATH_FONT_IMAGE + checked+"-"+typeInput+".png" + "\" style=\"height:12px;\" />&nbsp;" + optionName;
		return template;
	}
	
	private static String getTemplateRisposta(RispostaTabellaQuestionarioDTO risposta) {
		String template = "";
		template += "<table class=\"table-question\"><tr>";
		
		String td = "";
		List<ColonnaTabellaQuestionarioDTO> colonne = risposta.getColonne();
		for (ColonnaTabellaQuestionarioDTO colonna: colonne) {
			template += "<th style='width:"+colonna.getLarghezza()+"%;'>"+colonna.getDomanda().getTesto()+"</th>";
			td += "<td>&nbsp;</td>";
		}
		template += "</tr><tr>"+td+"</tr>"+"<tr>"+td+"</tr>"+"<tr>"+td+"</tr></table>";
		
		return template;
		
	}

}

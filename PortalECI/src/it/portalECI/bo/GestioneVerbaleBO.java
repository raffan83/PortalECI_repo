package it.portalECI.bo;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.ValidationException;
import org.hibernate.Session;
import org.jsoup.Jsoup;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPageEvent;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CSS;
import com.itextpdf.tool.xml.css.CssFile;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
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

import it.portalECI.DAO.GestioneDocumentoDAO;
import it.portalECI.DAO.GestioneDomandaVerbaleDAO;
import it.portalECI.DAO.GestioneQuestionarioDAO;
import it.portalECI.DAO.GestioneRispostaQuestionarioDAO;
import it.portalECI.DAO.GestioneRispostaVerbaleDAO;
import it.portalECI.DAO.GestioneStatoInterventoDAO;
import it.portalECI.DAO.GestioneStatoVerbaleDAO;
import it.portalECI.DAO.GestioneVerbaleDAO;
import it.portalECI.DTO.DocumentoDTO;
import it.portalECI.DTO.DomandaOpzioneQuestionarioDTO;
import it.portalECI.DTO.DomandaQuestionarioDTO;
import it.portalECI.DTO.DomandaVerbaleDTO;
import it.portalECI.DTO.InterventoDTO;
import it.portalECI.DTO.OpzioneRispostaQuestionarioDTO;
import it.portalECI.DTO.OpzioneRispostaVerbaleDTO;
import it.portalECI.DTO.QuestionarioDTO;
import it.portalECI.DTO.RispostaFormulaQuestionarioDTO;
import it.portalECI.DTO.RispostaFormulaVerbaleDTO;
import it.portalECI.DTO.RispostaQuestionario;
import it.portalECI.DTO.RispostaSceltaQuestionarioDTO;
import it.portalECI.DTO.RispostaSceltaVerbaleDTO;
import it.portalECI.DTO.RispostaTestoQuestionarioDTO;
import it.portalECI.DTO.RispostaTestoVerbaleDTO;
import it.portalECI.DTO.RispostaVerbaleDTO;
import it.portalECI.DTO.StatoInterventoDTO;
import it.portalECI.DTO.StatoVerbaleDTO;
import it.portalECI.DTO.TemplateQuestionarioDTO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.DTO.VerbaleDTO;
import it.portalECI.Util.Costanti;
import it.portalECI.Util.HeaderFooter;


public class GestioneVerbaleBO {
	
	public static List<VerbaleDTO> getListaVerbali(Session session) throws Exception {
		return GestioneVerbaleDAO.getListaVerbali(session);
	}
	
	public static VerbaleDTO getVerbale(String idVerbale,Session session) {
		return GestioneVerbaleDAO.getVerbale(idVerbale, session);
	}
	
	public static void update(VerbaleDTO verbale, Session session) {		
		session.update(verbale);
	}
	
	public static void cambioStato(VerbaleDTO verbale,StatoVerbaleDTO stato, Session session) {		
		
		verbale.setStato(stato);			
		session.update(verbale);
		InterventoDTO intervento= verbale.getIntervento();	
		if(intervento==null) {
			//la scheda tecnica non ha un intervento associato per il momento
			return;
		}
		Boolean verificato=true;
		
		for(VerbaleDTO verbaleInt : intervento.getVerbali()) {
			if(verbaleInt != null && verbaleInt.getStato().getId()!=StatoVerbaleDTO.RIFIUTATO && verbaleInt.getStato().getId()!= StatoVerbaleDTO.ACCETTATO ) {
				verificato=false;
				break;
			}
			
			if(verbaleInt != null && verbaleInt.getSchedaTecnica()!=null && verbaleInt.getSchedaTecnica().getStato().getId()!=StatoVerbaleDTO.RIFIUTATO && verbaleInt.getSchedaTecnica().getStato().getId()!= StatoVerbaleDTO.ACCETTATO) {
				verificato=false;
				break;
			}
		}
		
		if(verificato) {
			intervento.setStatoIntervento(GestioneStatoInterventoDAO.getStatoInterventoById(StatoInterventoDTO.VERIFICATO, session));
		}else if(intervento.getStatoIntervento().getId()!= StatoInterventoDTO.IN_VERIFICA) {
			intervento.setStatoIntervento(GestioneStatoInterventoDAO.getStatoInterventoById(StatoInterventoDTO.IN_VERIFICA, session));
		}
		
		session.update(intervento);
	}
	
	public static VerbaleDTO buildVerbale(String codiceVerifica, Session session) {
		VerbaleDTO result=null;
		QuestionarioDTO questionario= GestioneQuestionarioDAO.getQuestionarioForVerbaleInstance(codiceVerifica, session);
		if(questionario!=null) {
			VerbaleDTO verbale = new VerbaleDTO();
			verbale.setQuestionarioID(questionario.getId());
			verbale.setCodiceVerifica(questionario.getTipo().getCodice());
			verbale.setCodiceCategoria(questionario.getTipo().getCategoria().getCodice());
			verbale.setDescrizioneVerifica(questionario.getTipo().getCategoria().getDescrizione()+" - "+questionario.getTipo().getDescrizione());
			verbale.setStato(GestioneStatoVerbaleDAO.getStatoVerbaleById(StatoVerbaleDTO.CREATO, session));
			verbale.setType(VerbaleDTO.VERBALE);
	
			if(questionario.getDomandeSchedaTecnica().size()>0) {
				VerbaleDTO schedaTecnica = new VerbaleDTO();
				schedaTecnica.setQuestionarioID(questionario.getId());
				schedaTecnica.setCodiceVerifica(questionario.getTipo().getCodice());
				schedaTecnica.setCodiceCategoria(questionario.getTipo().getCategoria().getCodice());
				schedaTecnica.setDescrizioneVerifica(questionario.getTipo().getCategoria().getDescrizione()+" - "+questionario.getTipo().getDescrizione());
				schedaTecnica.setStato(GestioneStatoVerbaleDAO.getStatoVerbaleById(StatoVerbaleDTO.CREATO, session));
				schedaTecnica.setType(VerbaleDTO.SK_TEC);
				
				GestioneVerbaleDAO.save(schedaTecnica, session);
				verbale.setSchedaTecnica(schedaTecnica);
			}
			GestioneVerbaleDAO.save(verbale, session);
			result=verbale;
		}		
		return result;
	}
	
	
	public static VerbaleDTO buildVerbaleByQuestionario(VerbaleDTO verbale, Session session) {
		VerbaleDTO result = null;
		if (verbale != null) {
			if (verbale.getDomandeVerbale() != null) {
				verbale.getDomandeVerbale().clear();
			}
			QuestionarioDTO questionario = GestioneQuestionarioDAO.getQuestionarioById(verbale.getQuestionarioID(),
					session);
			if (questionario != null) {
				if (questionario.getDomandeVerbale() != null && verbale.getType().equals(VerbaleDTO.VERBALE)) {
					for (DomandaQuestionarioDTO domandaQuestionario : questionario.getDomandeVerbale()) {
						DomandaVerbaleDTO domandaVerbale = buildDomandaVerbalebyDomadaQuestionario(domandaQuestionario, session);
						domandaVerbale.setVerbale(verbale);
						GestioneDomandaVerbaleDAO.save(domandaVerbale, session);
						verbale.addToDomande(domandaVerbale);
					}
					cambioStato(verbale, GestioneStatoVerbaleDAO.getStatoVerbaleById(StatoVerbaleDTO.IN_COMPILAZIONE, session), session);
					result = verbale;
				}
			}
			
			if(verbale.getSchedaTecnica()!=null) {
				VerbaleDTO schedaTecnica=verbale.getSchedaTecnica();
			
				if (schedaTecnica.getDomandeVerbale() != null) {
					schedaTecnica.getDomandeVerbale().clear();
				}
			
				if (questionario != null  && schedaTecnica.getType().equals(VerbaleDTO.SK_TEC)) {
					if (questionario.getDomandeSchedaTecnica() != null) {
						for (DomandaQuestionarioDTO domandaSchedaTecnicaQuestionario : questionario.getDomandeSchedaTecnica()) {
							DomandaVerbaleDTO domandaSchedaTecnicaVerbale = buildDomandaVerbalebyDomadaQuestionario(domandaSchedaTecnicaQuestionario, session);
							domandaSchedaTecnicaVerbale.setVerbale(schedaTecnica);
							GestioneDomandaVerbaleDAO.save(domandaSchedaTecnicaVerbale, session);
							schedaTecnica.addToDomande(domandaSchedaTecnicaVerbale);
						}
						cambioStato(schedaTecnica, GestioneStatoVerbaleDAO.getStatoVerbaleById(StatoVerbaleDTO.IN_COMPILAZIONE, session), session);
						result = verbale;
					}
				}
			}
		}
		return result;
	}
	
	public static DomandaVerbaleDTO buildDomandaVerbalebyDomadaQuestionario(DomandaQuestionarioDTO domandaQuestionario, Session session) {
		DomandaVerbaleDTO domandaVerbaleDTO = new DomandaVerbaleDTO();
		domandaVerbaleDTO.setDomandaQuestionario(domandaQuestionario);
		if (domandaQuestionario.getRisposta() != null) {
			RispostaVerbaleDTO rispostaVerbaleDTO = null;
			switch (domandaQuestionario.getRisposta().getTipo()) {
			case RispostaQuestionario.TIPO_TESTO:
				rispostaVerbaleDTO = new RispostaTestoVerbaleDTO();
				RispostaTestoVerbaleDTO rispostaTestoVerbaleDTO = (RispostaTestoVerbaleDTO) rispostaVerbaleDTO;
				rispostaTestoVerbaleDTO.setRispostaQuestionario(GestioneRispostaQuestionarioDAO
						.getRispostaInstance(RispostaTestoQuestionarioDTO.class,
								domandaQuestionario.getRisposta().getId(), session));
				GestioneRispostaVerbaleDAO.save(rispostaTestoVerbaleDTO, session);
				domandaVerbaleDTO.setRisposta(rispostaTestoVerbaleDTO);
				break;
			case RispostaQuestionario.TIPO_FORMULA:
				rispostaVerbaleDTO = new RispostaFormulaVerbaleDTO();
				RispostaFormulaVerbaleDTO rispostaFormulaVerbaleDTO = (RispostaFormulaVerbaleDTO) rispostaVerbaleDTO;
				rispostaFormulaVerbaleDTO.setRispostaQuestionario(GestioneRispostaQuestionarioDAO
						.getRispostaInstance(RispostaFormulaQuestionarioDTO.class,
								domandaQuestionario.getRisposta().getId(), session));
				GestioneRispostaVerbaleDAO.save(rispostaFormulaVerbaleDTO, session);
				domandaVerbaleDTO.setRisposta(rispostaFormulaVerbaleDTO);
				break;
			case RispostaQuestionario.TIPO_SCELTA:
				rispostaVerbaleDTO = new RispostaSceltaVerbaleDTO();
				RispostaSceltaVerbaleDTO rispostaSceltaVerbaleDTO = (RispostaSceltaVerbaleDTO) rispostaVerbaleDTO;
				RispostaSceltaQuestionarioDTO rispostaSceltaQuestionario = GestioneRispostaQuestionarioDAO
						.getRispostaInstance(RispostaSceltaQuestionarioDTO.class,
								domandaQuestionario.getRisposta().getId(), session);
				rispostaSceltaVerbaleDTO.setRispostaQuestionario(rispostaSceltaQuestionario);
				if (rispostaSceltaQuestionario.getOpzioni() != null) {
					for (OpzioneRispostaQuestionarioDTO opzioneRispostaQuestionarioDTO : rispostaSceltaQuestionario.getOpzioni()) {
						OpzioneRispostaVerbaleDTO opzioneRispostaVerbaleDTO = new OpzioneRispostaVerbaleDTO();
						opzioneRispostaVerbaleDTO.setOpzioneQuestionario(opzioneRispostaQuestionarioDTO);
						opzioneRispostaVerbaleDTO.setRisposta(rispostaSceltaVerbaleDTO);
						for(DomandaOpzioneQuestionarioDTO domandaOpzioneQuestionario:opzioneRispostaQuestionarioDTO.getDomande()) {
							DomandaVerbaleDTO domandaOpzioneVerbale = buildDomandaVerbalebyDomadaQuestionario(domandaOpzioneQuestionario, session);
							domandaOpzioneVerbale.setOpzione(opzioneRispostaVerbaleDTO);
							GestioneDomandaVerbaleDAO.save(domandaOpzioneVerbale, session);
							opzioneRispostaVerbaleDTO.addToDomande(domandaOpzioneVerbale);
						}
						rispostaSceltaVerbaleDTO.addToOpzioni(opzioneRispostaVerbaleDTO);
					}
				}
				GestioneRispostaVerbaleDAO.save(rispostaSceltaVerbaleDTO, session);
				domandaVerbaleDTO.setRisposta(rispostaSceltaVerbaleDTO);
				break;

			default:
				break;

			}
		}
		return domandaVerbaleDTO;
		
	}
	
	public static File getPDFVerbale(VerbaleDTO verbale, QuestionarioDTO questionario, Session session) throws Exception{
		
		if(questionario == null || (questionario.getTemplateVerbale() == null && questionario.getTemplateSchedaTecnica() == null)) {
			throw new IllegalArgumentException();
		}
		
		TemplateQuestionarioDTO template = null;
		int idIntervento = 0;
		if(verbale.getType().equals(VerbaleDTO.VERBALE)) {
			idIntervento = verbale.getIntervento().getId();
			template = questionario.getTemplateVerbale();
		}else {
			VerbaleDTO verb=GestioneVerbaleDAO.getVerbaleFromSkTec(String.valueOf(verbale.getId()), session);
			idIntervento = verb.getIntervento().getId();
			template = questionario.getTemplateSchedaTecnica();
		}
		
		String path = "Intervento_"+idIntervento+File.separator+verbale.getType()+"_"+verbale.getCodiceCategoria()+"_"+verbale.getId()+File.separator;
		new File(Costanti.PATH_CERTIFICATI+path).mkdirs();
		File file = new File(Costanti.PATH_CERTIFICATI+path, questionario.getTitolo()+"_"+questionario.getTipo().getCodice()+"_"+idIntervento+".pdf");
		
		String html = new String(template.getTemplate());
		html = replacePlaceholders(html, verbale, session);
		
    	final org.jsoup.nodes.Document documentJsoup = Jsoup.parse(html);
    	documentJsoup.outputSettings().syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.xml);
    	String validXHTML = documentJsoup.html();
    	
        Document document = new Document(PageSize.A4);
        FileOutputStream fileOutput = new FileOutputStream(file);
	    PdfWriter writer = PdfWriter.getInstance(document, fileOutput);
            
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	    
	    HeaderFooter pageEventHandler = new HeaderFooter(
	    		template.getHeader(),
	    		template.getFooter(),
	    		verbale.getCodiceVerifica(),
	    		"Revisione del "+dateFormat.format(template.getUpdateDate())
	    );
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
        // HTML
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
        // Pipelines
        PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
        HtmlPipeline htmlPipeline = new HtmlPipeline(htmlContext, pdf);
        CssResolverPipeline css = new CssResolverPipeline(cssResolver, htmlPipeline);

        XMLWorker worker = new XMLWorker(css, true);
        XMLParser p = new XMLParser(worker);
        p.parse(new ByteArrayInputStream(validXHTML.getBytes()),
        		Charset.forName("UTF-8"));

        document.close();
        DocumentoDTO certificato = new DocumentoDTO();
        certificato.setFilePath(path+file.getName());
        //cambio il type del DocumentoDTO in base a certificato o scheda_tecnica
        if(verbale.getType().equals(VerbaleDTO.VERBALE)) {
        	certificato.setType(DocumentoDTO.CERTIFIC);
        } else {
        	certificato.setType(DocumentoDTO.SK_TEC);
        }
        certificato.setVerbale(verbale);
        if(verbale.getDocumentiVerbale() != null) {
	        for(DocumentoDTO doc:verbale.getDocumentiVerbale()) {
        		//gestiosco anche qui certificato o scheda tecnica
	        	if(verbale.getType().equals(VerbaleDTO.VERBALE) && doc.getType().equalsIgnoreCase(DocumentoDTO.CERTIFIC)){
		        	certificato.setId(doc.getId());
		        	verbale.getDocumentiVerbale().remove(doc);
	        	} else if (verbale.getType().equals(VerbaleDTO.SK_TEC) && doc.getType().equalsIgnoreCase(DocumentoDTO.SK_TEC)){
	        		certificato.setId(doc.getId());
		        	verbale.getDocumentiVerbale().remove(doc);
	        	}
	        }
        }
        GestioneDocumentoDAO.save(certificato, session);
        verbale.getDocumentiVerbale().add(certificato);
        GestioneVerbaleDAO.save(verbale, session);
    	
		return file;

	}
	
	private static String getTemplateRisposta(RispostaTestoVerbaleDTO risposta) {
		return risposta.getResponseValue();
	}
	
	private static String getTemplateRisposta(RispostaSceltaVerbaleDTO risposta) {

		String template = "";
		String inputType = risposta.getRispostaQuestionario().getMultipla()==false?"radio":"checkbox";		
			
		List<OpzioneRispostaVerbaleDTO> opzioni=new ArrayList<OpzioneRispostaVerbaleDTO>();
		opzioni.addAll(risposta.getOpzioni());
		
		Collections.sort(opzioni, new Comparator<OpzioneRispostaVerbaleDTO>() {
	        @Override
	        public int compare(OpzioneRispostaVerbaleDTO op2, OpzioneRispostaVerbaleDTO op1){
				int pos1=op1.getOpzioneQuestionario().getPosizione();
				int pos2=op2.getOpzioneQuestionario().getPosizione();
	            return  pos2 - pos1;
	        }
	    });
			
		
		if(inputType.equalsIgnoreCase("radio")) {
			template += "<br/>";
			for(int i=0; i<opzioni.size(); i++) {
				OpzioneRispostaVerbaleDTO opzione= (OpzioneRispostaVerbaleDTO) opzioni.get(i);
				String optionName = opzione.getOpzioneQuestionario().getTesto();
				boolean checked = opzione.getChecked();
				if(checked) {
					template += "<img src=\"" + Costanti.PATH_FONT_IMAGE + "checked-radio.png" + "\" height=\"14\" />&nbsp;&nbsp;" + optionName+"<br/>";					
				} else {
					template += "<img src=\"" + Costanti.PATH_FONT_IMAGE + "unchecked-radio.png" + "\" height=\"14\" />&nbsp;&nbsp;" + optionName+"<br/>";
				}
			}
		} else {
			template += "<br/>";
			for(int i=0; i<opzioni.size(); i++) {
				OpzioneRispostaVerbaleDTO opzione= (OpzioneRispostaVerbaleDTO) opzioni.get(i);
				String optionName = opzione.getOpzioneQuestionario().getTesto();
				boolean checked = opzione.getChecked();
				if(checked) {
					template += "<img src=\"" + Costanti.PATH_FONT_IMAGE + "checked-checkbox.png" + "\" height=\"14\" />&nbsp;&nbsp;" + optionName+"<br/>";
				} else {
					template += "<img src=\"" + Costanti.PATH_FONT_IMAGE + "unchecked-checkbox.png" + "\" height=\"14\" />&nbsp;&nbsp;" + optionName+"<br/>";
				}
			}
		}
		template += "";

		return template;
	}
	
	private static String getTemplateRisposta(RispostaFormulaVerbaleDTO risposta) {
		String template = "";
		template += "<b>" + risposta.getValue1() + "</b> (" + risposta.getRispostaQuestionario().getValore1() + ") ";
		template += "<b>&nbsp;" + risposta.getRispostaQuestionario().getSimboloOperatore() + "&nbsp;</b>";
		template += "<b>" + risposta.getValue2() + "</b> (" + risposta.getRispostaQuestionario().getValore2() + ") = ";
		template += "<b>" + risposta.getResponseValue() + "</b> (" + risposta.getRispostaQuestionario().getRisultato() + ") ";
		
		return template;
		
	}
	
	public static void saveVerbaleResponses(UtenteDTO user, JsonObject jsonRequest,Session session) throws ValidationException {

		if (!jsonRequest.isJsonNull()) {
			

			VerbaleDTO verbaleDTO = GestioneVerbaleDAO.getVerbale(jsonRequest.get("verbale_id").getAsString(), session);

			if (verbaleDTO != null) {
				if (verbaleDTO.getStato().getId()!=StatoVerbaleDTO.IN_COMPILAZIONE) {
					throw new ValidationException("Verbale in stato diverso da IN_COMPILAZIONE");
				}
				
				if(verbaleDTO.getType().equals(VerbaleDTO.VERBALE)) {
					if (!verbaleDTO.getIntervento().getTecnico_verificatore().getUser().equals(user.getUser())) {
						throw new ValidationException("Utente non abilitato all'invio del Verbale");
					}
				}else {
					VerbaleDTO verbale = GestioneVerbaleDAO.getVerbaleFromSkTec(jsonRequest.get("verbale_id").getAsString(), session);
					
					if (!verbale.getIntervento().getTecnico_verificatore().getUser().equals(user.getUser())) {
						throw new ValidationException("Utente non abilitato all'invio della Scheda Tecnica");
					}
				}
				
				
				//JsonArray riposteOpzione = scelta.get("risposte").getAsJsonArray();
				JsonArray responses = jsonRequest.get("risposte").getAsJsonArray();
				Iterator<JsonElement> iterator = responses.iterator();
				while (iterator.hasNext()) {
					JsonObject response = (JsonObject)iterator.next();
					parseRispostaJson(response, session);
				}
				cambioStato(verbaleDTO,GestioneStatoVerbaleDAO.getStatoVerbaleById(StatoVerbaleDTO.DA_VERIFICARE, session),session);
				
			} else {
				throw new ValidationException("Verbale Non Trovato");
			}
		}else {
			throw new ValidationException("JSON Richiesta vuoto");
		}
	}
	
	private static String replacePlaceholders(String html, VerbaleDTO verbale, Session session) {
		for (DomandaVerbaleDTO domanda:verbale.getDomandeVerbale()) {
			html = replacePlaceholderDomanda(html,domanda, session);
		}
		html = html.replaceAll("\\$\\{(.*?)\\}", "");
		return html;
	}
	
	private static String replacePlaceholderDomanda(String html,DomandaVerbaleDTO domanda, Session session) {
		String placeholder = domanda.getDomandaQuestionario().getPlaceholder();
		html = html.replaceAll("\\$\\{"+placeholder+"\\}", domanda.getDomandaQuestionario().getTesto());
		RispostaVerbaleDTO rispostaVerbale = domanda.getRisposta();

		String rispostaValore = null;
		String rispostaPlaceholder = null;
		switch (rispostaVerbale.getTipo()) {
		case RispostaVerbaleDTO.TIPO_TESTO:
			RispostaTestoVerbaleDTO rispostaTesto = GestioneRispostaVerbaleDAO.getRispostaInstance(RispostaTestoVerbaleDTO.class, rispostaVerbale.getId(), session);
			rispostaPlaceholder = rispostaTesto.getRispostaQuestionario().getPlaceholder();
			rispostaValore = getTemplateRisposta(rispostaTesto);
			break;
		case RispostaVerbaleDTO.TIPO_SCELTA:
			RispostaSceltaVerbaleDTO rispostaScelta = GestioneRispostaVerbaleDAO.getRispostaInstance(RispostaSceltaVerbaleDTO.class, rispostaVerbale.getId(), session);
			rispostaPlaceholder = rispostaScelta.getRispostaQuestionario().getPlaceholder();
			rispostaValore = getTemplateRisposta(rispostaScelta);
			for(OpzioneRispostaVerbaleDTO opzione: rispostaScelta.getOpzioni()) {
				if(opzione.getDomande() != null) {
					for (DomandaVerbaleDTO domandaOpzione:opzione.getDomande()) {
						html = replacePlaceholderDomanda(html,domandaOpzione, session);
					}
				}
			}
			break;
		case RispostaVerbaleDTO.TIPO_FORMULA:
			RispostaFormulaVerbaleDTO rispostaFormula = GestioneRispostaVerbaleDAO.getRispostaInstance(RispostaFormulaVerbaleDTO.class, rispostaVerbale.getId(), session);
			rispostaPlaceholder = rispostaFormula.getRispostaQuestionario().getPlaceholder();
			rispostaValore = getTemplateRisposta(rispostaFormula);
			break;
		default:
			break;
		}
		if(rispostaValore!=null && rispostaPlaceholder!=null ) {
			html = html.replaceAll("\\$\\{"+rispostaPlaceholder+"\\}", rispostaValore);
		}
		return html;
	}
	
	private static void parseRispostaJson(JsonObject responseVerbale, Session session) {

		int responseID = responseVerbale.get("id").getAsInt();
		switch (responseVerbale.get("type").getAsString()) {
		case "RES_TEXT":
			RispostaTestoVerbaleDTO rispostaTesto = GestioneRispostaVerbaleDAO
					.getRispostaInstance(RispostaTestoVerbaleDTO.class, responseID, session);
			if(responseVerbale.get("valore")!=null)
				rispostaTesto.setResponseValue(responseVerbale.get("valore").getAsString());
			GestioneRispostaVerbaleDAO.save(rispostaTesto, session);
			break;
		case "RES_CHOICE":
			JsonArray scelte = responseVerbale.getAsJsonArray("scelte");
			if (scelte != null) {
				Iterator<JsonElement> iteratorScelte = scelte.iterator();
				while (iteratorScelte.hasNext()) {
					JsonObject scelta = (JsonObject) iteratorScelte.next();
					int sceltaID = scelta.get("id").getAsInt();
					boolean sceltaChoice = scelta.get("choice").getAsBoolean();
					OpzioneRispostaVerbaleDTO opzioneRispostaVerbaleDTO = GestioneRispostaVerbaleDAO
							.getOpzioneVerbale(sceltaID, session);
					opzioneRispostaVerbaleDTO.setChecked(sceltaChoice);
					GestioneRispostaVerbaleDAO.saveOpzioneVerbale(opzioneRispostaVerbaleDTO, session);
					JsonElement riposteOpzione = scelta.get("risposte");
					if(riposteOpzione!=null) {
						Iterator<JsonElement> iteratoRisp = riposteOpzione.getAsJsonArray().iterator();
						while(iteratoRisp.hasNext()) {
							JsonObject rispostaOpzione = (JsonObject) iteratoRisp.next();
							parseRispostaJson(rispostaOpzione, session);
						}
					}
					//TODO:ffff
				}
			}
			break;
		case "RES_FORMULA":
			RispostaFormulaVerbaleDTO rispostaFormula = GestioneRispostaVerbaleDAO
					.getRispostaInstance(RispostaFormulaVerbaleDTO.class, responseID, session);
			if(responseVerbale.get("valore_1")!=null)
				rispostaFormula.setValue1(responseVerbale.get("valore_1").getAsString());
			if(responseVerbale.get("valore_2")!=null)
				rispostaFormula.setValue2(responseVerbale.get("valore_2").getAsString());
			if(responseVerbale.get("risultato")!=null)
				rispostaFormula.setResponseValue(responseVerbale.get("risultato").getAsString());
			GestioneRispostaVerbaleDAO.save(rispostaFormula, session);
			break;

		default:
			break;
		}

	}

}

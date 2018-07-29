package it.portalECI.bo;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
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
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.DTO.VerbaleDTO;
import it.portalECI.Util.Costanti;


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
		
		verbale.setStato(stato );			
		session.update(verbale);
		InterventoDTO intervento= verbale.getIntervento();	
		Boolean verificato=true;
		
		for(VerbaleDTO verbaleInt : intervento.getVerbali()) {
			if(verbaleInt.getStato().getId()!=StatoVerbaleDTO.RIFIUTATO && verbaleInt.getStato().getId()!= StatoVerbaleDTO.ACCETTATO) {
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
				if (questionario.getDomandeVerbale() != null) {
					for (DomandaQuestionarioDTO domandaQuestionario : questionario.getDomandeVerbale()) {
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
										opzioneRispostaVerbaleDTO
												.setOpzioneQuestionario(opzioneRispostaQuestionarioDTO);
										opzioneRispostaVerbaleDTO.setRisposta(rispostaSceltaVerbaleDTO);
										rispostaSceltaVerbaleDTO.addToOpzioni(opzioneRispostaVerbaleDTO);
										
										
									}
								}
								GestioneRispostaVerbaleDAO.save(rispostaSceltaVerbaleDTO, session);
								domandaVerbaleDTO.setRisposta(rispostaSceltaVerbaleDTO);
								break;

							default:
								break;

							}

							domandaVerbaleDTO.setVerbale(verbale);
							GestioneDomandaVerbaleDAO.save(domandaVerbaleDTO, session);
							verbale.addToDomande(domandaVerbaleDTO);

						}
					}
					cambioStato(verbale, GestioneStatoVerbaleDAO.getStatoVerbaleById(StatoVerbaleDTO.IN_COMPILAZIONE, session), session);
					result = verbale;
				}
			}
		}
		return result;
	}
	
	
	public static File getPDFVerbale(VerbaleDTO verbale, QuestionarioDTO questionario, Session session) throws Exception{
		
		String path = "Intervento_"+verbale.getIntervento().getId()+File.separator+"Verbale_"+verbale.getCodiceCategoria()+"_"+verbale.getId();
		new File(Costanti.PATH_CERTIFICATI+path).mkdirs();
		File file = new File(Costanti.PATH_CERTIFICATI+path, questionario.getTitolo()+"_"+questionario.getTipo().getCodice()+"_"+verbale.getIntervento().getId()+".pdf");

		String html = questionario.getTemplateVerbale().getTemplate();
		
		//
		
		List domandeVerbale=new ArrayList();
		domandeVerbale.addAll(verbale.getDomandeVerbale());
		
		Collections.sort(domandeVerbale, new Comparator<DomandaVerbaleDTO>() {
			@Override
			public int compare(DomandaVerbaleDTO op2, DomandaVerbaleDTO op1){
				int pos1=op1.getDomandaQuestionario().getPosizione();
				int pos2=op2.getDomandaQuestionario().getPosizione();
				return  pos2 - pos1;
			}
		});


		
		//
		for(int i=0 ; i< domandeVerbale.size(); i++) {
			DomandaVerbaleDTO domanda = (DomandaVerbaleDTO) domandeVerbale.get(i);
		//for (DomandaVerbaleDTO domanda:verbale.getDomandeVerbale()) {
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
		}
		html = html.replaceAll("\\$\\{(.*?)\\}", "");
    	final org.jsoup.nodes.Document documentJsoup = Jsoup.parse(html);
    	documentJsoup.outputSettings().syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.xml);
    	String str = documentJsoup.html();
    	System.out.println(str);
    	try {
	        Document document = new Document();
	        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();
	        // CSS
	        CSSResolver cssResolver = XMLWorkerHelper.getInstance().getDefaultCssResolver(true);
	        XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
	        fontProvider.register(Costanti.PATH_FONT_STYLE+"arial.ttf");
	        CssAppliers cssAppliers = new CssAppliersImpl(fontProvider);
	        // HTML
	        HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);
	        htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
	        htmlContext.setImageProvider(new AbstractImageProvider() {
	            public String getImageRootPath() {
	                return Costanti.PATH_WS;
	            }
	        });
	        htmlContext.setLinkProvider(new LinkProvider() {
	            public String getLinkRoot() {
	                return Costanti.PATH_WS;
	            }
	        });
	        System.out.println("3mkmmkm");
	        // Pipelines
	        PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
	        HtmlPipeline htmlPipeline = new HtmlPipeline(htmlContext, pdf);
	        CssResolverPipeline css = new CssResolverPipeline(cssResolver, htmlPipeline);

	        XMLWorker worker = new XMLWorker(css, true);
	        XMLParser p = new XMLParser(worker);
	        p.parse(new ByteArrayInputStream(str.getBytes()),
	        		Charset.forName("US-ASCII"));

	        document.close();
	        DocumentoDTO certificato = new DocumentoDTO();
	        certificato.setFilePath(path+file.getName());
	        certificato.setType(DocumentoDTO.CERTIFIC);
	        certificato.setVerbale(verbale);
	        if(verbale.getDocumentiVerbale() != null) {
		        for(DocumentoDTO doc:verbale.getDocumentiVerbale()) {
		        	if (doc.getType().equalsIgnoreCase(DocumentoDTO.CERTIFIC)){
		        		certificato.setId(doc.getId());
		        		verbale.getDocumentiVerbale().remove(doc);
		        	}
		        }
	        }
	        GestioneDocumentoDAO.save(certificato, session);
	        verbale.getDocumentiVerbale().add(certificato);
	        GestioneVerbaleDAO.save(verbale, session);
    	}catch (IOException e) {
    		throw new Exception(e);
		} catch (DocumentException e) {
			throw new Exception(e);
		}
    	
		return file;

	}
	
	private static String getTemplateRisposta(RispostaTestoVerbaleDTO risposta) {
		return risposta.getResponseValue();
	}
	
	private static String getTemplateRisposta(RispostaSceltaVerbaleDTO risposta) {

		String template = "";
		String inputType = risposta.getRispostaQuestionario().getMultipla()==false?"radio":"checkbox";
		if(inputType.equalsIgnoreCase("radio")) {
			template += "<br/>";
			for (OpzioneRispostaVerbaleDTO opzione:risposta.getOpzioni()) {
				String optionName = opzione.getOpzioneQuestionario().getTesto();
				boolean checked = opzione.getChecked();
				if(checked) {
					template += "<img src=\"" + Costanti.PATH_FONTS_IMAGE + "checked-radio.png" + "\" height=\"14\" />&nbsp;&nbsp;" + optionName+"<br/>";					
				} else {
					template += "<img src=\"" + Costanti.PATH_FONTS_IMAGE + "unchecked-radio.png" + "\" height=\"14\" />&nbsp;&nbsp;" + optionName+"<br/>";
				}
			}
		} else {
			template += "<br/>";
			for (OpzioneRispostaVerbaleDTO opzione:risposta.getOpzioni()) {
				String optionName = opzione.getOpzioneQuestionario().getTesto();
				boolean checked = opzione.getChecked();
				if(checked) {
					template += "<img src=\"" + Costanti.PATH_FONTS_IMAGE + "checked-checkbox.png" + "\" height=\"14\" />&nbsp;&nbsp;" + optionName+"<br/>";
				} else {
					template += "<img src=\"" + Costanti.PATH_FONTS_IMAGE + "unchecked-checkbox.png" + "\" height=\"14\" />&nbsp;&nbsp;" + optionName+"<br/>";
				}
			}
		}
		template += "";

		return template;
	}
	
	private static String getTemplateRisposta(RispostaFormulaVerbaleDTO risposta) {
		String template = "";
		template += "<p><b>" + risposta.getValue1() + "</b> (" + risposta.getRispostaQuestionario().getValore1() + ") ";
		template += "<b>&nbsp;" + risposta.getRispostaQuestionario().getSimboloOperatore() + "&nbsp;</b>";
		template += "<b>" + risposta.getValue2() + "</b> (" + risposta.getRispostaQuestionario().getValore2() + ") = ";
		template += "<b>" + risposta.getResponseValue() + "</b> (" + risposta.getRispostaQuestionario().getRisultato() + ") </p>";
		
		return template;
		
	}
	
	public static void saveVerbaleResponses(UtenteDTO user, JsonObject jsonRequest,Session session) throws ValidationException {

		if (!jsonRequest.isJsonNull()) {
			

			VerbaleDTO verbaleDTO = GestioneVerbaleDAO.getVerbale(jsonRequest.get("verbale_id").getAsString(), session);

			if (verbaleDTO != null) {
				if (verbaleDTO.getStato().getId()!=StatoVerbaleDTO.IN_COMPILAZIONE) {
					throw new ValidationException("Verbale in stato diverso da IN_COMPILAZIONE");
				}
				
				if (!verbaleDTO.getIntervento().getTecnico_verificatore().getUser().equals(user.getUser())) {
					throw new ValidationException("Utente non abilitato all'invio del Verbale");
				}
				
				
				JsonArray responses = jsonRequest.getAsJsonArray("risposte");
				Iterator<JsonElement> iterator = responses.iterator();
				while (iterator.hasNext()) {
					JsonObject responseVerbale = (JsonObject) iterator.next();
					int responseID = responseVerbale.get("id").getAsInt();
					switch (responseVerbale.get("type").getAsString()) {
					case "RES_TEXT":
						RispostaTestoVerbaleDTO rispostaTesto = GestioneRispostaVerbaleDAO
								.getRispostaInstance(RispostaTestoVerbaleDTO.class, responseID, session);
						rispostaTesto.setResponseValue(responseVerbale.get("valore").getAsString());
						GestioneRispostaVerbaleDAO.save(rispostaTesto, session);
						break;
					case "RES_CHOICE":
						RispostaSceltaVerbaleDTO rispostaScelta = GestioneRispostaVerbaleDAO
								.getRispostaInstance(RispostaSceltaVerbaleDTO.class, responseID, session);
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
							}
						}
						break;
					case "RES_FORMULA":
						RispostaFormulaVerbaleDTO rispostaFormula = GestioneRispostaVerbaleDAO
								.getRispostaInstance(RispostaFormulaVerbaleDTO.class, responseID, session);
						rispostaFormula.setValue1(responseVerbale.get("valore_1").getAsString());
						rispostaFormula.setValue2(responseVerbale.get("valore_2").getAsString());
						rispostaFormula.setResponseValue(responseVerbale.get("risultato").getAsString());
						GestioneRispostaVerbaleDAO.save(rispostaFormula, session);
						break;

					default:
						break;
					}

				}
				cambioStato(verbaleDTO,GestioneStatoVerbaleDAO.getStatoVerbaleById(StatoVerbaleDTO.DA_VERIFICARE, session),session);
				
			} else {
				throw new ValidationException("Verbale Non Trovato");
			}
		}else {
			throw new ValidationException("JSON Richiesta vuoto");
		}
	}
	

}

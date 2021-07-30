package it.portalECI.bo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.ValidationException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.Session;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.sun.xml.internal.ws.api.server.SDDocumentFilter;

import it.portalECI.DAO.GestioneDocumentoDAO;
import it.portalECI.DAO.GestioneDomandaVerbaleDAO;
import it.portalECI.DAO.GestioneQuestionarioDAO;
import it.portalECI.DAO.GestioneRispostaQuestionarioDAO;
import it.portalECI.DAO.GestioneRispostaVerbaleDAO;
import it.portalECI.DAO.GestioneStatoInterventoDAO;
import it.portalECI.DAO.GestioneStatoVerbaleDAO;
import it.portalECI.DAO.GestioneVerbaleDAO;
import it.portalECI.DTO.AllegatoClienteDTO;
import it.portalECI.DTO.AllegatoMinisteroDTO;
import it.portalECI.DTO.AttrezzaturaDTO;
import it.portalECI.DTO.ColonnaTabellaQuestionarioDTO;
import it.portalECI.DTO.ColonnaTabellaVerbaleDTO;
import it.portalECI.DTO.CommessaDTO;
import it.portalECI.DTO.DocumentoDTO;
import it.portalECI.DTO.DomandaOpzioneQuestionarioDTO;
import it.portalECI.DTO.DomandaQuestionarioDTO;
import it.portalECI.DTO.DomandaVerbaleDTO;
import it.portalECI.DTO.InterventoDTO;
import it.portalECI.DTO.OpzioneRispostaQuestionarioDTO;
import it.portalECI.DTO.OpzioneRispostaVerbaleDTO;
import it.portalECI.DTO.ProgressivoVerbaleDTO;
import it.portalECI.DTO.QuestionarioDTO;
import it.portalECI.DTO.RispostaFormulaQuestionarioDTO;
import it.portalECI.DTO.RispostaFormulaVerbaleDTO;
import it.portalECI.DTO.RispostaQuestionario;
import it.portalECI.DTO.RispostaSceltaQuestionarioDTO;
import it.portalECI.DTO.RispostaSceltaVerbaleDTO;
import it.portalECI.DTO.RispostaTabellaQuestionarioDTO;
import it.portalECI.DTO.RispostaTabellaVerbaleDTO;
import it.portalECI.DTO.RispostaTestoQuestionarioDTO;
import it.portalECI.DTO.RispostaTestoVerbaleDTO;
import it.portalECI.DTO.RispostaVerbaleDTO;
import it.portalECI.DTO.StatoInterventoDTO;
import it.portalECI.DTO.StatoVerbaleDTO;
import it.portalECI.DTO.StoricoEmailVerbaleDTO;
import it.portalECI.DTO.TemplateQuestionarioDTO;
import it.portalECI.DTO.TipoVerificaDTO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.DTO.VerbaleDTO;
import it.portalECI.Util.Costanti;
import it.portalECI.Util.Utility;

import org.apache.commons.codec.binary.Base64;


public class GestioneVerbaleBO {
	
	public static List<VerbaleDTO> getListaVerbali(Session session, UtenteDTO user) throws Exception {
		return GestioneVerbaleDAO.getListaVerbali(session,user);
	}
	
	public static VerbaleDTO getVerbale(String idVerbale,Session session) {
		return GestioneVerbaleDAO.getVerbale(idVerbale, session);
	}
	
	public static void update(VerbaleDTO verbale, Session session) {		
		session.update(verbale);
	}
	
	public static void cambioStato(VerbaleDTO verbale,StatoVerbaleDTO stato, Session session) {		
				
		if((verbale.getStato()==GestioneStatoVerbaleDAO.getStatoVerbaleById(StatoVerbaleDTO.CREATO, session) && stato.getId()==StatoVerbaleDTO.IN_COMPILAZIONE)
				|| (verbale.getStato()==GestioneStatoVerbaleDAO.getStatoVerbaleById(StatoVerbaleDTO.CREATO, session) && stato.getId()==StatoVerbaleDTO.COMPILAZIONE_WEB)) {
			verbale.setDataScaricamento(new Date());
		}else if((verbale.getStato()==GestioneStatoVerbaleDAO.getStatoVerbaleById(StatoVerbaleDTO.IN_COMPILAZIONE, session) && stato.getId()==StatoVerbaleDTO.DA_VERIFICARE)
				|| (verbale.getStato()==GestioneStatoVerbaleDAO.getStatoVerbaleById(StatoVerbaleDTO.COMPILAZIONE_WEB, session) && stato.getId()==StatoVerbaleDTO.DA_VERIFICARE)) {
			verbale.setDataTrasferimento(new Date());
		}
		
		InterventoDTO intervento= verbale.getIntervento();
		
		if (stato.getId()==StatoVerbaleDTO.COMPILAZIONE_WEB) {
			
			setStatoCompilazioneWeb(intervento, stato, session);
			intervento.setStatoIntervento(GestioneStatoInterventoDAO.getStatoInterventoById(StatoInterventoDTO.COMPILAZIONE_WEB, session));
			session.update(intervento);
		} else {
				
			verbale.setStato(stato);			
			session.update(verbale);
			//InterventoDTO intervento= verbale.getIntervento();	
			//if(intervento==null) {
				//la scheda tecnica non ha un intervento associato per il momento
				//return;
			//}
			
			if(intervento!=null) {
				Boolean verificato=true;
				
				for(VerbaleDTO verbaleInt : intervento.getVerbali()) {
					if(verbaleInt != null && verbaleInt.getStato().getId()!=10 && verbaleInt.getStato().getId()!= StatoVerbaleDTO.ACCETTATO ) {
						verificato=false;
						break;
					}
					
					if(verbaleInt != null && verbaleInt.getSchedaTecnica()!=null && verbaleInt.getSchedaTecnica().getStato().getId()!=10 && verbaleInt.getSchedaTecnica().getStato().getId()!= StatoVerbaleDTO.ACCETTATO) {
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
			
		}
		
		
	}
	
	public static VerbaleDTO buildVerbale(String codiceVerifica, Session session, Boolean creaSchedaTecnica,String note, AttrezzaturaDTO attrezzatura, String sedeUtilizzatore, String esercente, String effettuazione_verbale, String tipo_verifica, String descrizione_utilizzatore) {
		VerbaleDTO result=null;
		QuestionarioDTO questionario= GestioneQuestionarioDAO.getQuestionarioForVerbaleInstance(codiceVerifica, session);
		if(questionario!=null) {
			VerbaleDTO verbale = new VerbaleDTO();
			verbale.setQuestionarioID(questionario.getId());
			verbale.setCodiceVerifica(questionario.getTipo().getCodice());
			verbale.setCodiceCategoria(questionario.getTipo().getCategoria().getCodice());
			verbale.setDescrizioneVerifica(questionario.getTipo().getCategoria().getDescrizione()+" - "+questionario.getTipo().getDescrizione());
			verbale.setStato(GestioneStatoVerbaleDAO.getStatoVerbaleById(StatoVerbaleDTO.CREATO, session));
			verbale.setNote(note);
			verbale.setAttrezzatura(attrezzatura);
			verbale.setSedeUtilizzatore(sedeUtilizzatore);
			verbale.setType(VerbaleDTO.VERBALE);
			verbale.setDescrizione_sede_utilizzatore(descrizione_utilizzatore);
			if(effettuazione_verbale!=null && !effettuazione_verbale.equals("")) {
				verbale.setEffettuazione_verifica(Integer.parseInt(effettuazione_verbale));	
			}
			if(tipo_verifica!=null && !tipo_verifica.equals("")) {
				verbale.setTipo_verifica(Integer.parseInt(tipo_verifica));	
			}
						
			if(esercente!=null) {
				verbale.setEsercente(esercente);	
			}			
	
			if(codiceVerifica.startsWith("VS")) {
				verbale.setTipologia_verifica(1);
			}
			else if(codiceVerifica.startsWith("VE")) {
				verbale.setTipologia_verifica(4);
			}
			
			if(questionario.getDomandeSchedaTecnica().size()>0 && creaSchedaTecnica) {
				VerbaleDTO schedaTecnica = new VerbaleDTO();
				schedaTecnica.setQuestionarioID(questionario.getId());
				schedaTecnica.setCodiceVerifica(questionario.getTipo().getCodice());
				schedaTecnica.setCodiceCategoria(questionario.getTipo().getCategoria().getCodice());
				schedaTecnica.setDescrizioneVerifica(questionario.getTipo().getCategoria().getDescrizione()+" - "+questionario.getTipo().getDescrizione());
				schedaTecnica.setStato(GestioneStatoVerbaleDAO.getStatoVerbaleById(StatoVerbaleDTO.CREATO, session));
				schedaTecnica.setType(VerbaleDTO.SK_TEC);
				schedaTecnica.setAttrezzatura(attrezzatura);
				schedaTecnica.setSedeUtilizzatore(sedeUtilizzatore);
				schedaTecnica.setEsercente(esercente);
				
				
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
			case RispostaVerbaleDTO.TIPO_TABELLA:
				rispostaVerbaleDTO = new RispostaTabellaVerbaleDTO();
				RispostaTabellaVerbaleDTO rispostaTabella = (RispostaTabellaVerbaleDTO) rispostaVerbaleDTO;
				rispostaTabella.setColonne(new HashSet<ColonnaTabellaVerbaleDTO>());
				RispostaTabellaQuestionarioDTO rispostaTabellaQuestionario = GestioneRispostaQuestionarioDAO
						.getRispostaInstance(RispostaTabellaQuestionarioDTO.class,
								domandaQuestionario.getRisposta().getId(), session);
				rispostaTabella.setRispostaQuestionario(rispostaTabellaQuestionario);
				if(rispostaTabellaQuestionario.getColonne()!=null) {
					for (ColonnaTabellaQuestionarioDTO colonnaQuestionario: rispostaTabellaQuestionario.getColonne()) {
						ColonnaTabellaVerbaleDTO colonnaVerbale = new ColonnaTabellaVerbaleDTO();
						colonnaVerbale.setColonnaQuestionario(colonnaQuestionario);
						colonnaVerbale.setRispostaParent(rispostaTabella);
						DomandaVerbaleDTO domandaColonna = buildDomandaVerbalebyDomadaQuestionario(colonnaQuestionario.getDomanda(), session);
						GestioneDomandaVerbaleDAO.save(domandaColonna, session);
						colonnaVerbale.setDomanda(domandaColonna);
						rispostaTabella.getColonne().add(colonnaVerbale);
					}
				}
				GestioneRispostaVerbaleDAO.save(rispostaTabella, session);
				domandaVerbaleDTO.setRisposta(rispostaTabella);
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
		
		InterventoDTO intervento = null;
		TemplateQuestionarioDTO template = null;
		String nomefile = null;
		
		if(verbale.getType().equals(VerbaleDTO.VERBALE)) {
			intervento = verbale.getIntervento();
			template = questionario.getTemplateVerbale();
			nomefile = generaNumeroVerbale(verbale,questionario.getTipo(), intervento, session);
			
			verbale.setNumeroVerbale(nomefile);
		}else {
			VerbaleDTO verb=GestioneVerbaleDAO.getVerbaleFromSkTec(String.valueOf(verbale.getId()), session);
			intervento = verb.getIntervento();
			template = questionario.getTemplateSchedaTecnica();
			//nomefile = questionario.getTitolo()+"_"+questionario.getTipo().getCodice()+"_"+intervento.getId();
			nomefile = "SCHEDA TECNICA MATRICOLA "+verbale.getAttrezzatura().getMatricola_inail().replace("/", ".");
		}
		
		String path = "Intervento_"+intervento.getId()+File.separator+verbale.getType()+"_"+verbale.getCodiceCategoria()+"_"+verbale.getId()+File.separator;
		new File(Costanti.PATH_CERTIFICATI+path).mkdirs();
		File file = new File(Costanti.PATH_CERTIFICATI+path,nomefile+".pdf");
		int counter = 0;
		while(file.exists()) {
			counter++;
			file = new File(Costanti.PATH_CERTIFICATI+path,nomefile+"_"+counter+".pdf");
		}
        FileOutputStream fileOutput = new FileOutputStream(file);
		
		String html = new String();
		
		String documentoType = "";
        if(verbale.getType().equals(VerbaleDTO.VERBALE)) {
        	documentoType = DocumentoDTO.CERTIFIC;
        } else {
        	documentoType = DocumentoDTO.SK_TEC;
        }
        
        DocumentoDTO vecchioDocumento = null;
		for(DocumentoDTO documento:verbale.getDocumentiVerbale()) {
			if(!documento.getInvalid() && documento.getType().equals(documentoType)) {
				invalidDocument(documento, session, nomefile );
				vecchioDocumento = documento;
				break; // Ci può essere al massimo un documento valido dello stesso tipo (CERTIFICATO|SCHEDA TECNICA)
			}
		}
       /* 
        if(vecchioDocumento != null) {
        	html = html + "<p style=\"text-align:center;font-size:16px;font-family:Helvetica,sans-serif;font-weight:900\">"+String.format(Costanti.DOCUMENT_INVALIDS_PHRASE, vecchioDocumento.getFileName())+"</p>";
        }
        */
		
		Utility.createQR(verbale.getId(), Costanti.PATH_CERTIFICATI + path);
        
		html = html + template.getTemplate();
		html = replacePlaceholders(html, verbale,intervento, false,  session);
		String subheader = replacePlaceholders(template.getSubheader(), verbale,intervento,false, session);

		GestioneTemplateQuestionarioBO.writePDF(fileOutput, html, template,subheader);

		DocumentoDTO certificato = new DocumentoDTO();
        certificato.setFilePath(path+file.getName());
        //cambio il type del DocumentoDTO in base a certificato o scheda_tecnica
        certificato.setType(documentoType);
        certificato.setVerbale(verbale);        
        verbale.addToDocumentiVerbale(certificato);
        
        if(vecchioDocumento != null) {
        	String vecchioDocFileName = vecchioDocumento.getFileName();
        	vecchioDocFileName = FilenameUtils.removeExtension(vecchioDocFileName);
        	newDocument(certificato.getFilePath(), vecchioDocFileName, verbale);
        }
        
        
        GestioneDocumentoDAO.save(certificato, session);
        verbale.getDocumentiVerbale().add(certificato);
        GestioneVerbaleDAO.save(verbale, session);
    	
		return file;

	}
	
	public static File getPDFVerbaleAnteprima(VerbaleDTO verbale, QuestionarioDTO questionario, Session session) throws Exception{
		if(questionario == null || (questionario.getTemplateVerbale() == null && questionario.getTemplateSchedaTecnica() == null)) {
			throw new IllegalArgumentException();
		}
		
		InterventoDTO intervento = null;
		TemplateQuestionarioDTO template = null;
		
		if(verbale.getType().equals(VerbaleDTO.VERBALE)) {
			intervento = verbale.getIntervento();
			template = questionario.getTemplateVerbale();

		}else {
			VerbaleDTO verb=GestioneVerbaleDAO.getVerbaleFromSkTec(String.valueOf(verbale.getId()), session);
			intervento = verb.getIntervento();
			template = questionario.getTemplateSchedaTecnica();
			
		}
		
		String path = "AnteprimaCertificati"+File.separator;
		new File(Costanti.PATH_CERTIFICATI+path).mkdirs();
		File file = new File(Costanti.PATH_CERTIFICATI+path,"AnteprimaCertificato.pdf");
        FileOutputStream fileOutput = new FileOutputStream(file);
		
		String html = new String();
		
		String documentoType = "";
        if(verbale.getType().equals(VerbaleDTO.VERBALE)) {
        	documentoType = DocumentoDTO.CERTIFIC;
        } else {
        	documentoType = DocumentoDTO.SK_TEC;
        }
        
        DocumentoDTO vecchioDocumento = null;
		for(DocumentoDTO documento:verbale.getDocumentiVerbale()) {
			if(!documento.getInvalid() && documento.getType().equals(documentoType)) {
				vecchioDocumento = documento;
				break;
			}
		}
		
		
		
		html = html + template.getTemplate();
		html = replacePlaceholders(html, verbale,intervento, true, session);
		String subheader = replacePlaceholders(template.getSubheader(), verbale,intervento, true,  session);
		GestioneTemplateQuestionarioBO.writePDF(fileOutput, html, template, subheader);
        
        if(vecchioDocumento != null) {
        	String vecchioDocFileName = vecchioDocumento.getFileName();
        	vecchioDocFileName = FilenameUtils.removeExtension(vecchioDocFileName);
        	newDocument(path+file.getName(), vecchioDocFileName, verbale);
        }
        addBozza(path+file.getName(), "", verbale);
		return file;
	}
	
	private static String getTemplateRisposta(RispostaTestoVerbaleDTO risposta) {
		return risposta.getResponseValue();
	}
	
	private static String getTemplateRisposta(RispostaSceltaVerbaleDTO risposta) {
		String template = "";
		Boolean multipla = risposta.getRispostaQuestionario().getMultipla();	
			
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
		for (OpzioneRispostaVerbaleDTO opzione: opzioni) {
			template +=  getTemplateOpzione(opzione, multipla)+"<br/>";
		}
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
	
	private static String getTemplateRisposta(RispostaTabellaVerbaleDTO risposta, Session session) {
		String template = "";
		template += "<table class=\"table-question\">";

		template += "<tr>";
		//Set<ColonnaTabellaVerbaleDTO> colonne = risposta.getColonne();

		List<ColonnaTabellaVerbaleDTO> colonne=new ArrayList<ColonnaTabellaVerbaleDTO>();
		colonne.addAll(risposta.getColonne());
		
		Collections.sort(colonne, new Comparator<ColonnaTabellaVerbaleDTO>() {
	        @Override
	        public int compare(ColonnaTabellaVerbaleDTO op2, ColonnaTabellaVerbaleDTO op1){
				int pos1=op1.getColonnaQuestionario().getPosizione().intValue();
				int pos2=op2.getColonnaQuestionario().getPosizione().intValue();
	            return  pos2 - pos1;
	        }
	    });
			
		for (ColonnaTabellaVerbaleDTO colonna: colonne) {
			template += "<th style='width:"+colonna.getColonnaQuestionario().getLarghezza()+"%;'>"+colonna.getColonnaQuestionario().getDomanda().getTesto()+"</th>";
			List<RispostaVerbaleDTO> risposte = colonna.getRisposte();
			
		}
		template += "</tr>";
		
		int numeroRighe = colonne.iterator().next().getRisposte().size();
		for (int i=0; i<numeroRighe; i++) {
			template += "<tr>";
			for (ColonnaTabellaVerbaleDTO colonna: colonne) {
				RispostaVerbaleDTO[] rispostaVerbaleList = colonna.getRisposte().toArray(new RispostaVerbaleDTO[numeroRighe]);
				template += "<td>";
				RispostaVerbaleDTO rispostaVerbale =  rispostaVerbaleList[i];
				switch (colonna.getColonnaQuestionario().getDomanda().getRisposta().getTipo()) {
				case RispostaQuestionario.TIPO_TESTO:
					RispostaTestoVerbaleDTO rispostaTesto = GestioneRispostaVerbaleDAO.getRispostaInstance(RispostaTestoVerbaleDTO.class, rispostaVerbale.getId(), session);
					template += getTemplateRisposta(rispostaTesto);
					break;
				case RispostaQuestionario.TIPO_FORMULA:
					RispostaFormulaVerbaleDTO rispostaFormula = GestioneRispostaVerbaleDAO.getRispostaInstance(RispostaFormulaVerbaleDTO.class, rispostaVerbale.getId(), session);
					template += getTemplateRisposta(rispostaFormula);
					break;
				case RispostaQuestionario.TIPO_SCELTA:
					RispostaSceltaVerbaleDTO rispostaScelta = GestioneRispostaVerbaleDAO.getRispostaInstance(RispostaSceltaVerbaleDTO.class, rispostaVerbale.getId(), session);
					for(OpzioneRispostaVerbaleDTO opzione: rispostaScelta.getOpzioni()) {
						if(opzione.getChecked()) {
							template += opzione.getOpzioneQuestionario().getTesto();
							break;
						}		
					}
					break;
				default:
					break;
				}
				template += "</td>";
			}
			template += "</tr>";
		}

		template += "</table>";
		
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
				
				// save documenti
				JsonArray documenti = jsonRequest.get("documenti").getAsJsonArray();
				Iterator<JsonElement> iteratorDoc = documenti.iterator();
				while (iteratorDoc.hasNext()) {
					JsonObject documento = (JsonObject)iteratorDoc.next();
					parseDocumentiJson(documento, verbaleDTO, session);
				}
					
				cambioStato(verbaleDTO,GestioneStatoVerbaleDAO.getStatoVerbaleById(StatoVerbaleDTO.DA_VERIFICARE, session),session);
				
			} else {
				throw new ValidationException("Verbale Non Trovato");
			}
			
		}else {
			throw new ValidationException("JSON Richiesta vuoto");
		}
	}
	
	public static String generaNumeroVerbale(VerbaleDTO verbale, TipoVerificaDTO tipo, InterventoDTO intervento, Session session) throws Exception {
		String numeroVerbale = new String();
		UtenteDTO utente = intervento.getTecnico_verificatore();
		ProgressivoVerbaleDTO progressivo = GestioneVerbaleDAO.getProgressivoVerbale(utente, tipo, session);
		String codUtente = utente.getCodice() == null ? "" : utente.getCodice();
		String sigla = tipo.getSigla() == null ? "" : tipo.getSigla();
		int prog = progressivo.getProgressivo();
		
		
		String codProv="";
		if(verbale.getSedeUtilizzatore()!=null) {
			codProv = verbale.getSedeUtilizzatore().split("\\(")[1].replace(")","");
		}
		else if(verbale.getAttrezzatura()!=null && verbale.getAttrezzatura().getProvincia_div()!=null) {
			codProv = verbale.getAttrezzatura().getProvincia_div();
		}
		else {
			CommessaDTO comm=GestioneCommesseBO.getCommessaById(intervento.getIdCommessa());
			if(comm!=null && comm.getCOD_PROV()!=null ) 
			{
				codProv=comm.getCOD_PROV();
			}
		}
		
		
	//	String codProv = intervento.getCodiceProvincia() == null ? " " : intervento.getCodiceProvincia();
		numeroVerbale = String.format("%s-%s-%03d-%s", codUtente, sigla, prog, codProv);
		//System.out.println("numeroVerbale" + numeroVerbale);
		return numeroVerbale;
	}
	
	private static String replacePlaceholders(String html, VerbaleDTO verbale, InterventoDTO intervento, boolean isAnteprima, Session session) throws Exception {
		
		if(html==null || html.equals("")) {
			return "";
		}
		
		for (DomandaVerbaleDTO domanda:verbale.getDomandeVerbale()) {
			html = replacePlaceholderDomanda(html,domanda, session);
		}
		
		//Inserisco il nome del tecnico
		UtenteDTO verificatore = intervento.getTecnico_verificatore();
		String nomeVerificatore = verificatore.getNome()+" "+verificatore.getCognome();
		if(nomeVerificatore != null) {
			html = html.replaceAll("\\$\\{TECNICO_VERIFICATORE\\}", nomeVerificatore);
		}
		
		if(verificatore.getQualifica() != null) {
			html = html.replaceAll("\\$\\{QUAL_TV\\}", verificatore.getQualifica());
		}
		//Inserisco numero verbale
		String numeroVerbale = verbale.getNumeroVerbale();
		if(numeroVerbale != null) {
			html = html.replaceAll("\\$\\{NUMERO_VERBALE\\}", numeroVerbale);
		}
		
		//Inserisco Nome Utilizzatore
		CommessaDTO clienteUtilizzatore = GestioneCommesseBO.getCommessaById(intervento.getIdCommessa());
		
//		if(clienteUtilizzatore != null && clienteUtilizzatore.getNOME_UTILIZZATORE()!=null) {
//			html = html.replaceAll("\\$\\{CLIENTE_UTILIZZATORE\\}", clienteUtilizzatore.getNOME_UTILIZZATORE());
//		}
		//Inserisco Indirizzo Utilizzatore

//		if(clienteUtilizzatore != null && clienteUtilizzatore.getINDIRIZZO_UTILIZZATORE()!=null) {
//			html = html.replaceAll("\\$\\{INDIRIZZO_CLIENTE_UTILIZZATORE\\}", clienteUtilizzatore.getINDIRIZZO_UTILIZZATORE());
//		}
		String indirizzo_utilizzatore ="";
		String cliente_utilizzatore = "";
		String com_prov = "";	
		
		if(verbale.getAttrezzatura()!=null) {
			
			if(verbale.getAttrezzatura().getIndirizzo_div()!=null) {
				cliente_utilizzatore = verbale.getAttrezzatura().getPresso_div();
				indirizzo_utilizzatore = verbale.getAttrezzatura().getIndirizzo_div() + " - "+verbale.getAttrezzatura().getCap_div()+" - "+verbale.getAttrezzatura().getComune_div()+" ("+verbale.getAttrezzatura().getProvincia_div()+")";
				com_prov = verbale.getAttrezzatura().getComune_div()+" ("+verbale.getAttrezzatura().getProvincia_div()+")";
			}else {
				if(verbale.getAttrezzatura().getId_sede()==0) {
					cliente_utilizzatore = verbale.getAttrezzatura().getNome_cliente();
				}else {
					cliente_utilizzatore = verbale.getAttrezzatura().getNome_sede();
				}
				indirizzo_utilizzatore = verbale.getAttrezzatura().getIndirizzo() + " - "+verbale.getAttrezzatura().getCap()+" - "+verbale.getAttrezzatura().getComune()+" ("+verbale.getAttrezzatura().getProvincia()+")";
				com_prov = verbale.getAttrezzatura().getComune()+" ("+verbale.getAttrezzatura().getProvincia()+")";
			}

			html = html.replaceAll("\\$\\{CLIENTE_UTILIZZATORE\\}", cliente_utilizzatore);
			html = html.replaceAll("\\$\\{INDIRIZZO_CLIENTE_UTILIZZATORE\\}", indirizzo_utilizzatore);
		}else {
			
		
			if(verbale.getDescrizione_sede_utilizzatore()!=null && !verbale.getDescrizione_sede_utilizzatore().equals("")) {
				html = html.replaceAll("\\$\\{CLIENTE_UTILIZZATORE\\}", verbale.getDescrizione_sede_utilizzatore());				
			
			}else {
				if(clienteUtilizzatore != null && clienteUtilizzatore.getNOME_UTILIZZATORE()!=null) {
					html = html.replaceAll("\\$\\{CLIENTE_UTILIZZATORE\\}", clienteUtilizzatore.getNOME_UTILIZZATORE());
				}
			}
			
			
			if(clienteUtilizzatore != null && clienteUtilizzatore.getINDIRIZZO_UTILIZZATORE()!=null) {

				html = html.replaceAll("\\$\\{INDIRIZZO_CLIENTE_UTILIZZATORE\\}", clienteUtilizzatore.getINDIRIZZO_UTILIZZATORE());
				if( clienteUtilizzatore.getINDIRIZZO_UTILIZZATORE().split("-").length>2) {
					com_prov = clienteUtilizzatore.getINDIRIZZO_UTILIZZATORE().split("-")[2];	
				}				
			}
		}
		
		// Inserisco Cliente
		if(clienteUtilizzatore != null && clienteUtilizzatore.getID_ANAGEN_NOME()!=null) {
			html = html.replaceAll("\\$\\{CLIENTE\\}", clienteUtilizzatore.getID_ANAGEN_NOME());
		}
		//Inserisco Indirizzo Utilizzatore

		if(clienteUtilizzatore != null && clienteUtilizzatore.getINDIRIZZO_PRINCIPALE()!=null) {
			html = html.replaceAll("\\$\\{INDIRIZZO_CLIENTE\\}", clienteUtilizzatore.getINDIRIZZO_PRINCIPALE());
		}
		
	
		//Attrezzatura
		if(verbale.getAttrezzatura() != null && verbale.getAttrezzatura().getMatricola_inail()!=null) 
		{
			html = html.replaceAll("\\$\\{ATT_MATRICOLA\\}", verbale.getAttrezzatura().getMatricola_inail());
		}
		
		if(verbale.getAttrezzatura() != null && verbale.getAttrezzatura().getNumero_fabbrica()!=null) 
		{
			html = html.replaceAll("\\$\\{ATT_N_FABBRICA\\}",verbale.getAttrezzatura().getNumero_fabbrica());
		}
		
		if(verbale.getAttrezzatura() != null && verbale.getAttrezzatura().getDescrizione()!=null) 
		{
			html = html.replaceAll("\\$\\{ATT_DESCRIZIONE\\}", verbale.getAttrezzatura().getDescrizione());
		}
		
		if(verbale.getAttrezzatura() != null && verbale.getAttrezzatura().getAnno_costruzione()!=0) 
		{
			html = html.replaceAll("\\$\\{ATT_ANNO_COSTRUZIONE\\}", ""+verbale.getAttrezzatura().getAnno_costruzione());
		}
		
		if(verbale.getAttrezzatura() != null && verbale.getAttrezzatura().getFabbricante()!=null) 
		{
			html = html.replaceAll("\\$\\{ATT_FABBRICANTE\\}", verbale.getAttrezzatura().getFabbricante());
		}
		
		if(verbale.getAttrezzatura() != null && verbale.getAttrezzatura().getModello()!=null) 
		{
			html = html.replaceAll("\\$\\{ATT_MODELLO\\}", verbale.getAttrezzatura().getModello());
		}
		
		if(verbale.getAttrezzatura() != null && verbale.getAttrezzatura().getSettore_impiego()!=null) 
		{
			html = html.replaceAll("\\$\\{ATT_SETTORE_IMPIEGO\\}", verbale.getAttrezzatura().getSettore_impiego());
		}
		
		if(verbale.getStrumento_verificatore() != null && verbale.getStrumento_verificatore().getMarca()!=null) 
		{			
			html = html.replaceAll("\\$\\{STR_MARCA\\}", verbale.getStrumento_verificatore().getMarca());
		}
		
		if(verbale.getStrumento_verificatore() != null && verbale.getStrumento_verificatore().getModello()!=null) 
		{			
			html = html.replaceAll("\\$\\{STR_MOD\\}", verbale.getStrumento_verificatore().getModello());
		}
		
		if(verbale.getStrumento_verificatore() != null && verbale.getStrumento_verificatore().getMatricola()!=null) 
		{			
			html = html.replaceAll("\\$\\{STR_MATR\\}", verbale.getStrumento_verificatore().getMatricola());
		}
		if(verbale.getCampione() != null && verbale.getCampione().getCostruttore()!=null) 
		{			
			html = html.replaceAll("\\$\\{STR_MARCA\\}", verbale.getCampione().getCostruttore());
		}
		
		if(verbale.getCampione() != null && verbale.getCampione().getModello()!=null) 
		{			
			html = html.replaceAll("\\$\\{STR_MOD\\}", verbale.getCampione().getModello());
		}
		
		if(verbale.getCampione() != null && verbale.getCampione().getMatricola()!=null) 
		{			
			if(verbale.getCampione().getCodice()!=null) {
				html = html.replaceAll("\\$\\{STR_MATR\\}", verbale.getCampione().getMatricola() +" ("+verbale.getCampione().getCodice()+")");
			}else{
				html = html.replaceAll("\\$\\{STR_MATR\\}", verbale.getCampione().getMatricola());	
			}
		}
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		
		if(verbale.getStrumento_verificatore() != null && verbale.getStrumento_verificatore().getData_ultima_taratura()!=null) 
		{			
			html = html.replaceAll("\\$\\{STR_ULT_TAR\\}", df.format(verbale.getStrumento_verificatore().getData_ultima_taratura()));
		}
		
		if(verbale.getStrumento_verificatore() != null && verbale.getStrumento_verificatore().getScadenza()!=null) 
		{			
			html = html.replaceAll("\\$\\{STR_SCADENZA\\}", df.format(verbale.getStrumento_verificatore().getScadenza()));
		}
		
		if(verbale.getCampione() != null && verbale.getCampione().getDataVerifica()!=null) 
		{			
			html = html.replaceAll("\\$\\{STR_ULT_TAR\\}", df.format(verbale.getCampione().getDataVerifica()));
		}
		
		if(verbale.getCampione() != null && verbale.getCampione().getDataScadenza()!=null) 
		{			
			html = html.replaceAll("\\$\\{STR_SCADENZA\\}", df.format(verbale.getCampione().getDataScadenza()));
		}
				
		
		if(verbale.getData_verifica() != null) 
		{			
			if(verbale.getData_fine_verifica()!=null && verbale.getData_verifica().getTime() != verbale.getData_fine_verifica().getTime()) {
				html = html.replaceAll("\\$\\{DATA_VERIFICA\\}", df.format(verbale.getData_verifica())+" - "+df.format(verbale.getData_fine_verifica()));
			}else {
				html = html.replaceAll("\\$\\{DATA_VERIFICA\\}", df.format(verbale.getData_verifica()));	
			}			
		}else {
			if(verbale.getType().equals(VerbaleDTO.SK_TEC)) {
				VerbaleDTO verb = GestioneVerbaleDAO.getVerbaleFromSkTec(""+verbale.getId(), session);
				if(verb.getData_fine_verifica()!=null && verb.getData_verifica().getTime() != verb.getData_fine_verifica().getTime()) {
					html = html.replaceAll("\\$\\{DATA_VERIFICA\\}", df.format(verb.getData_verifica())+" - "+df.format(verb.getData_fine_verifica()));
				}else {
				
					html = html.replaceAll("\\$\\{DATA_VERIFICA\\}", df.format(verb.getData_verifica()));	
				}
			}
		}
		
		if(verbale.getData_prossima_verifica() != null) 
		{			
			html = html.replaceAll("\\$\\{DATA_PROSS_VERIFICA\\}", df.format(verbale.getData_prossima_verifica()));
		}
		if(verbale.getAttrezzatura()!=null && verbale.getAttrezzatura().getData_prossima_verifica_funzionamento() != null) 
		{			
			html = html.replaceAll("\\$\\{DATA_PROSS_VERIFICA\\}", df.format(verbale.getAttrezzatura().getData_prossima_verifica_funzionamento()));
		}
		if(verbale.getAttrezzatura()!=null && verbale.getAttrezzatura().getData_verifica_integrita() != null) 
		{			
			html = html.replaceAll("\\$\\{DATA_VER_INTEGRITA\\}", df.format(verbale.getAttrezzatura().getData_verifica_integrita()));
		}
		if(verbale.getAttrezzatura()!=null && verbale.getAttrezzatura().getData_prossima_verifica_integrita() != null) 
		{			
			html = html.replaceAll("\\$\\{DATA_PROSS_VER_INTEGRITA\\}", df.format(verbale.getAttrezzatura().getData_prossima_verifica_integrita()));
		}
		if(verbale.getAttrezzatura()!=null && verbale.getAttrezzatura().getData_verifica_interna() != null) 
		{			
			html = html.replaceAll("\\$\\{DATA_VER_INTERNA\\}", df.format(verbale.getAttrezzatura().getData_verifica_interna()));
		}
		if(verbale.getAttrezzatura()!=null && verbale.getAttrezzatura().getData_prossima_verifica_interna() != null) 
		{			
			html = html.replaceAll("\\$\\{DATA_PROSS_VER_INTERNA\\}", df.format(verbale.getAttrezzatura().getData_prossima_verifica_interna()));
		}
		
		if(verbale.getStrumento_verificatore()!=null && verbale.getStrumento_verificatore().getNumero_certificato()!=null) {
			html = html.replaceAll("\\$\\{STR_NUMERO_CERT\\}", verbale.getStrumento_verificatore().getNumero_certificato());
		}
		if(verbale.getCampione()!=null && verbale.getCampione().getNumeroCertificato()!=null) {
			html = html.replaceAll("\\$\\{STR_NUMERO_CERT\\}", verbale.getCampione().getNumeroCertificato());
		}
		
		if(verbale.getSedeUtilizzatore() != null && verbale.getCodiceCategoria().equals("VIE")) 
		{
			html = html.replaceAll("\\$\\{INDIRIZZO_UTILIZZATORE_VIE\\}", verbale.getSedeUtilizzatore());
			
			int length = verbale.getSedeUtilizzatore().split("-").length;
			
			
			com_prov = verbale.getSedeUtilizzatore().split("-")[length-1];	
			
			
		}
		
		if(verbale.getEsercente() != null ) 
		{
			html = html.replaceAll("\\$\\{ESERCENTE\\}", verbale.getEsercente());
		}
		
		if(verbale.getOre_uomo() != null ) 
		{
			html = html.replaceAll("\\$\\{ORE_UOMO\\}", verbale.getOre_uomo());
		}
		
		
		html = html.replaceAll("\\$\\{COM_PROV\\}", com_prov);
				
		if(verbale.getMatricola_vie() != null ) 
		{
			html = html.replaceAll("\\$\\{MATRICOLA_VIE\\}", verbale.getMatricola_vie());
		}
		
		if(verbale.getData_conferma()!=null)
		{
			html = html.replaceAll("\\$\\{DATA_CONFERMA\\}", df.format(verbale.getData_conferma()));
		}
		
	
		
		
		String esito ="";
		if(verbale.getEsito()!=null && verbale.getEsito().equals("P")) {
			esito = esito + "<img src=\"" + Costanti.PATH_FONT_IMAGE +"checked" +"-"+"radio"+".png" + "\" style=\"height:12px;\" />&nbsp;" + "Positivo&nbsp;";
			esito = esito + "<img src=\"" + Costanti.PATH_FONT_IMAGE +"unchecked" +"-"+"radio"+".png" + "\" style=\"height:12px;\" />&nbsp;" + "Negativo";
		}else if(verbale.getEsito()!=null && verbale.getEsito().equals("N")) {
			esito = esito + "<img src=\"" + Costanti.PATH_FONT_IMAGE +"unchecked" +"-"+"radio"+".png" + "\" style=\"height:12px;\" />&nbsp;" + "Positivo&nbsp;";
			esito = esito + "<img src=\"" + Costanti.PATH_FONT_IMAGE +"checked" +"-"+"radio"+".png" + "\" style=\"height:12px;\" />&nbsp;" + "Negativo";
		}else {
			esito = esito + "<img src=\"" + Costanti.PATH_FONT_IMAGE +"unchecked" +"-"+"radio"+".png" + "\" style=\"height:12px;\" />&nbsp;" + "Positivo&nbsp;";
			esito = esito + "<img src=\"" + Costanti.PATH_FONT_IMAGE +"unchecked" +"-"+"radio"+".png" + "\" style=\"height:12px;\" />&nbsp;" + "Negativo";
		}
		
		html = html.replaceAll("\\$\\{ESITO_VERIFICA\\}", esito);
		
		
		String tipo_verifica_val ="";
				
		String check1="";
		String check2="";
		String check3="";
		String check4="";
		String check5="";
	
		if(verbale.getAttrezzatura()!=null) {			
			
			if(verbale.getCodiceVerifica().startsWith("GVR")) {								
	
				if(verbale.getTipo_verifica()==1 || verbale.getTipo_verifica()==2) {
					check1 = "checked";
										
					
					if(verbale.getTipo_verifica_gvr()==1) {
						
						check2 = "unchecked";
						check3 = "unchecked";
						check4 = "unchecked";
					}
					
					if(verbale.getTipo_verifica_gvr()==4) {
					
						check2 = "unchecked";
						check3 = "unchecked";
						check4 = "checked";
					}
					if(verbale.getTipo_verifica_gvr()==5) {
					
						check2 = "unchecked";
						check3 = "checked";
						check4 = "checked";
					}
					
					if(verbale.getTipo_verifica_gvr()==7) {
				
						check2 = "unchecked";
						check3 = "checked";
						check4 = "unchecked";
					}
					
				}

				else if(verbale.getTipo_verifica()==3 || verbale.getTipo_verifica()==4) {
					if(verbale.getTipo_verifica_gvr()==1) {
						check1 = "unchecked";
						check2 = "checked";
						check3 = "unchecked";
						check4 = "unchecked";
					}
					if(verbale.getTipo_verifica_gvr()==2) {
						check1 = "unchecked";
						check2 = "unchecked";
						check3 = "unchecked";
						check4 = "checked";
					}
					if(verbale.getTipo_verifica_gvr()==3) {
						check1 = "unchecked";
						check2 = "unchecked";
						check3 = "checked";
						check4 = "unchecked";
					}
					if(verbale.getTipo_verifica_gvr()==4) {
						check1 = "unchecked";
						check2 = "checked";
						check3 = "unchecked";
						check4 = "checked";
					}
					if(verbale.getTipo_verifica_gvr()==5) {
						check1 = "unchecked";
						check2 = "checked";
						check3 = "checked";
						check4 = "checked";
					}
					if(verbale.getTipo_verifica_gvr()==6) {
						check1 = "unchecked";
						check2 = "unchecked";
						check3 = "checked";
						check4 = "checked";
					}
					if(verbale.getTipo_verifica_gvr()==7) {
						check1 = "unchecked";
						check2 = "checked";
						check3 = "checked";
						check4 = "unchecked";
					}
					
						
				}
				
								
				tipo_verifica_val = tipo_verifica_val + "<img src=\"" + Costanti.PATH_FONT_IMAGE + check1+"-"+"radio"+".png" + "\" style=\"height:12px;\" />&nbsp;" + "Prima delle verifiche periodiche"+"<br/>";					
				tipo_verifica_val = tipo_verifica_val + "<img src=\"" + Costanti.PATH_FONT_IMAGE + check2+"-"+"radio"+".png" + "\" style=\"height:12px;\" />&nbsp;" + "Verifica di funzionamento"+"<br/>";						
				tipo_verifica_val = tipo_verifica_val + "<img src=\"" + Costanti.PATH_FONT_IMAGE + check3+"-"+"radio"+".png" + "\" style=\"height:12px;\" />&nbsp;" + "Verifica di visita interna per generatori di vapore/acqua surriscaldata"+"<br/>";					
				tipo_verifica_val = tipo_verifica_val + "<img src=\"" + Costanti.PATH_FONT_IMAGE + check4+"-"+"radio"+".png" + "\" style=\"height:12px;\" />&nbsp;" + "Verifica di integrità"+"<br/>";
				

				html = html.replaceAll("\\$\\{TIPO_VERIFICA_VAL_GVR\\}", tipo_verifica_val);
			
			}else {
				
				if(verbale.getTipo_verifica()==1) {
					check1 = "checked";
					check2 = "unchecked";

				}else {
					check1 = "unchecked";
					check2 = "checked";

				}
				
				tipo_verifica_val = tipo_verifica_val + "<img src=\"" + Costanti.PATH_FONT_IMAGE + check1+"-"+"radio"+".png" + "\" style=\"height:12px;\" />&nbsp;" + "Prima verifica periodica"+"<br/>";				
				tipo_verifica_val = tipo_verifica_val + "<img src=\"" + Costanti.PATH_FONT_IMAGE + check2+"-"+"radio"+".png" + "\" style=\"height:12px;\" />&nbsp;" + "Verifica periodica (successiva alla prima)"+"<br/>";		
			}
		}
		
		
		html = html.replaceAll("\\$\\{TIPO_VERIFICA_VAL\\}", tipo_verifica_val);
		

		
		if(verbale.getMotivo_verifica() == 1 ) 
		{
		
			check1 = "checked";
			check2 = "unchecked";
			check3 = "unchecked";
			check4 = "unchecked";
			check5 = "unchecked";
			
		} else if(verbale.getMotivo_verifica() == 2) {
			
			check1 = "unchecked";
			check2 = "checked";
			check3 = "checked";
			check4 = "unchecked";
			check5 = "unchecked";
			
		}else if(verbale.getMotivo_verifica() == 3) {
			
			check1 = "unchecked";
			check2 = "checked";
			check3 = "unchecked";
			check4 = "checked";
			check5 = "unchecked";
			
		}else if(verbale.getMotivo_verifica() == 4) {
			
			check1 = "unchecked";
			check2 = "checked";
			check3 = "unchecked";
			check4 = "unchecked";
			check5 = "checked";
			
		}
		
		String tipo_verifica_vie = "<img src=\"" + Costanti.PATH_FONT_IMAGE + check1+"-"+"checkbox"+".png" + "\" style=\"height:12px;\" />&nbsp;" + "Verifica periodica"+"<br/>";		
		tipo_verifica_vie = tipo_verifica_vie+"<img src=\"" + Costanti.PATH_FONT_IMAGE + check2+"-"+"checkbox"+".png" + "\" style=\"height:12px;\" />&nbsp;" + "Verifica straordinaria"+"<br/>";
		tipo_verifica_vie = tipo_verifica_vie+"&nbsp;&nbsp;&nbsp;  <img src=\"" + Costanti.PATH_FONT_IMAGE + check3+"-"+"checkbox"+".png" + "\" style=\"margin-left:25px;height:12px;\" />&nbsp;" + "Verifica periodica con esito negativo"+"<br/>";
		tipo_verifica_vie = tipo_verifica_vie+"&nbsp;&nbsp;&nbsp;  <img src=\"" + Costanti.PATH_FONT_IMAGE + check4+"-"+"checkbox"+".png" + "\" style=\"margin-left:25px;height:12px;\" />&nbsp;" + "Modifiche sostanziali all'impianto"+"<br/>";
		tipo_verifica_vie = tipo_verifica_vie+"&nbsp;&nbsp;&nbsp;  <img src=\"" + Costanti.PATH_FONT_IMAGE + check5+"-"+"checkbox"+".png" + "\" style=\"margin-left:15px;height:12px;\" />&nbsp;" + "Richiesta del datore di lavoro"+"<br/>";
		

		html = html.replaceAll("\\$\\{TIPO_VERIFICA_VIE\\}", tipo_verifica_vie);
		
		
		ArrayList<Date> lista_date_multi = new ArrayList<Date>();
		String data_multi ="";
		if(verbale.getTipo_verifica_gvr()!=0) {
			if(verbale.getTipo_verifica_gvr()==1) {
				
				lista_date_multi.add(verbale.getData_verifica());
				
			}else if(verbale.getTipo_verifica_gvr()==2) {
				
				lista_date_multi.add(verbale.getData_verifica_integrita());
				
			}else if(verbale.getTipo_verifica_gvr()==3) {
				
				lista_date_multi.add(verbale.getData_verifica_interna());
				
			}else if(verbale.getTipo_verifica_gvr()==4) {
				lista_date_multi.add(verbale.getData_verifica());
				lista_date_multi.add(verbale.getData_verifica_integrita());
				
			}else if(verbale.getTipo_verifica_gvr()==5) {
				
				lista_date_multi.add(verbale.getData_verifica());
				lista_date_multi.add(verbale.getData_verifica_integrita());
				lista_date_multi.add(verbale.getData_verifica_interna());
				
			}else if(verbale.getTipo_verifica_gvr()==6) {
				lista_date_multi.add(verbale.getData_verifica_integrita());
				lista_date_multi.add(verbale.getData_verifica_interna());
				
			}else if(verbale.getTipo_verifica_gvr()==7) {
				
				lista_date_multi.add(verbale.getData_verifica());
				lista_date_multi.add(verbale.getData_verifica_interna());
				
			}
			
			
			
			 if(lista_date_multi.size()>1) {
				Collections.sort(lista_date_multi, new Comparator<Date>() {
					  public int compare(Date o1, Date o2) {
						  if (o1 == null || o2 == null)
						      return 0;
					      return o1.compareTo(o2);
					  }
					});
				
			}
			
			if(lista_date_multi.get(0)!=null) {
				data_multi = ""+df.format(lista_date_multi.get(0)); 
			}
			
			for (int i = 1; i<lista_date_multi.size();i++) {
				if(!data_multi.contains(df.format(lista_date_multi.get(i)))){
					data_multi +=" - "+ df.format(lista_date_multi.get(i));	
				}
				
			}
			
		}
		
		html = html.replaceAll("\\$\\{DATA_VERIFICA_MULTIPLA\\}", data_multi);
		
		
		String firma_verificatore = "";
		
		if(verificatore.getFile_firma()!=null && !isAnteprima) {
			firma_verificatore ="<img src=\"" + Costanti.PATH_ROOT+"/FileFirme/" + verificatore.getFile_firma()+ "\" style=\"height:40px;\" /><br/>"; 
		}
		
		
		html = html.replaceAll("\\$\\{FIRMA_VERIFICATORE\\}", firma_verificatore);
		
		
		String firma_riesame = "";
		String ruolo = "Riesaminato";
		
		if(verbale.getResponsabile_approvatore()!=null && verbale.getResponsabile_approvatore().checkRuolo("RT")) {
			ruolo = ruolo + " RT<br>"+verbale.getResponsabile_approvatore().getQualifica()+" "+verbale.getResponsabile_approvatore().getNominativo();
		}else if(verbale.getResponsabile_approvatore()!=null && verbale.getResponsabile_approvatore().checkRuolo("SRT")){
			ruolo = ruolo + " SRT<br>Per. Ind. "+verbale.getResponsabile_approvatore().getNominativo();
		}
		
		
		html = html.replaceAll("\\$\\{RUOLO_RIESAME\\}", ruolo);
		
//		if(verbale.getData_approvazione()!=null) {
//			html = html.replaceAll("\\$\\{DATA_RIESAME\\}", df.format(verbale.getData_approvazione()));	
//		}
		
		if(verbale.getResponsabile_approvatore()!=null && verbale.getResponsabile_approvatore().getFile_firma()!=null && !isAnteprima) {			
			
			firma_riesame = "<img src=\"" + Costanti.PATH_ROOT+"/FileFirme/" + verbale.getResponsabile_approvatore().getFile_firma()+ "\" style=\"height:40px;\" /><br/>";
		}
		
		html = html.replaceAll("\\$\\{FIRMA_RIESAME\\}", firma_riesame);

		if(!isAnteprima) {
			String path = "Intervento_"+intervento.getId()+"/"+verbale.getType()+"_"+verbale.getCodiceCategoria()+"_"+verbale.getId()+"/";
			String qr = "<img src=\"" + Costanti.PATH_CERTIFICATI + path +"qrcode.png\""+ "\" style=\"height:50px;\" />";
			
			html = html.replaceAll("\\$\\{QR_CODE\\}", qr);
		}
		
		
		
		// Elimino i placeholder non utilizzati
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
				String opzionePlaceholder = opzione.getOpzioneQuestionario().getPlaceholder();
				Boolean multipla = opzione.getOpzioneQuestionario().getRisposta().getMultipla();
				String opzioneValore = getTemplateOpzione(opzione, multipla);
				html = html.replaceAll("\\$\\{"+opzionePlaceholder+"\\}", opzioneValore);
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
		case RispostaVerbaleDTO.TIPO_TABELLA:
			RispostaTabellaVerbaleDTO rispostaTabella = GestioneRispostaVerbaleDAO.getRispostaInstance(RispostaTabellaVerbaleDTO.class, rispostaVerbale.getId(), session);
			rispostaPlaceholder = rispostaTabella.getRispostaQuestionario().getPlaceholder();
			rispostaValore = getTemplateRisposta(rispostaTabella, session);
			break;
		default:
			break;
		}
		if(rispostaValore!=null && rispostaPlaceholder!=null ) {
			html = html.replaceAll("\\$\\{"+rispostaPlaceholder+"\\}", rispostaValore);
		}
		return html;
	}
	
	private static String getTemplateOpzione(OpzioneRispostaVerbaleDTO opzione, Boolean multipla) {
		String typeInput = multipla?"checkbox":"radio";
		String checked = opzione.getChecked()?"checked":"unchecked";
		String optionName = opzione.getOpzioneQuestionario().getTesto();
		String template = "<img src=\"" + Costanti.PATH_FONT_IMAGE + checked+"-"+typeInput+".png" + "\" style=\"height:12px;\" />&nbsp;" + optionName;
		return template;
	}

	private static void parseRispostaJson(JsonObject responseVerbale, Session session) {
		RispostaVerbaleDTO result = null;
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
		case "RES_TABLE":
			RispostaTabellaVerbaleDTO rispostaTabella = GestioneRispostaVerbaleDAO
					.getRispostaInstance(RispostaTabellaVerbaleDTO.class, responseID, session);
			JsonArray colonnejson = responseVerbale.getAsJsonArray("colonne");
			if (colonnejson != null) {
				Iterator<JsonElement> iteraColonne = colonnejson.iterator();
				while (iteraColonne.hasNext()) {
					JsonObject colonna = (JsonObject) iteraColonne.next();
					int colonnaID = colonna.get("id").getAsInt();
					ColonnaTabellaVerbaleDTO colonnaVerbale = GestioneRispostaVerbaleDAO
							.getColonnaVerble(colonnaID, session);
					DomandaQuestionarioDTO domandaQuestionario = colonnaVerbale.getColonnaQuestionario().getDomanda();
					JsonArray rispostejson = colonna.getAsJsonArray("risposte");
					if(rispostejson!=null) {
						Iterator<JsonElement> risposteIterator = rispostejson.iterator();
						while (risposteIterator.hasNext()) {
							colonnaVerbale.getRisposte().add(getRispostaByJson((JsonObject)risposteIterator.next(), domandaQuestionario, session));
							
						}
					}
				}
			}
			break;
		default:
			break;
		}

	}
	
	private static <T extends RispostaVerbaleDTO> T getRispostaByJson(JsonObject risposta, DomandaQuestionarioDTO domandaQuestionario, Session session) {
		RispostaQuestionario rispostaQuestionario = domandaQuestionario.getRisposta();
		String tipo = rispostaQuestionario.getTipo();
		RispostaVerbaleDTO result = null; 
		switch (tipo) {
		case RispostaVerbaleDTO.TIPO_TESTO:
			RispostaTestoVerbaleDTO rispostaVerbale = new RispostaTestoVerbaleDTO();
			rispostaVerbale.setRispostaQuestionario((RispostaTestoQuestionarioDTO)session.get(RispostaTestoQuestionarioDTO.class, rispostaQuestionario.getId()));
			if(risposta.get("valore")!=null) rispostaVerbale.setResponseValue(risposta.get("valore").getAsString());
			result = rispostaVerbale;
			break;
		case RispostaVerbaleDTO.TIPO_FORMULA:
			RispostaFormulaVerbaleDTO rispostaFormula =  new RispostaFormulaVerbaleDTO();
			rispostaFormula.setRispostaQuestionario((RispostaFormulaQuestionarioDTO) session.get(RispostaFormulaQuestionarioDTO.class, rispostaQuestionario.getId()));
			rispostaFormula.setValue1(risposta.get("valore_1").getAsString());
			rispostaFormula.setValue2(risposta.get("valore_2").getAsString());
			rispostaFormula.setResponseValue(risposta.get("risultato").getAsString());
			result = rispostaFormula;
			break;
		case RispostaVerbaleDTO.TIPO_SCELTA:
			RispostaSceltaVerbaleDTO rispostaScelta = new RispostaSceltaVerbaleDTO();
			RispostaSceltaQuestionarioDTO rispostaSceltaQuestionario = (RispostaSceltaQuestionarioDTO) session.get(RispostaSceltaQuestionarioDTO.class, rispostaQuestionario.getId());
			rispostaScelta.setRispostaQuestionario(rispostaSceltaQuestionario);
			rispostaScelta.setOpzioni(new HashSet<OpzioneRispostaVerbaleDTO>());
			JsonArray scelte = risposta.getAsJsonArray("scelte");
			if (scelte != null) {
				Iterator<JsonElement> iteratorScelte = scelte.iterator();
				while (iteratorScelte.hasNext()) {
					JsonObject scelta = (JsonObject) iteratorScelte.next();
					OpzioneRispostaVerbaleDTO opzioneRispostaVerbale = new OpzioneRispostaVerbaleDTO();
					opzioneRispostaVerbale.setChecked(scelta.get("choice").getAsBoolean());
					opzioneRispostaVerbale.setRisposta(rispostaScelta);
					OpzioneRispostaQuestionarioDTO opzioneQuestionario = null;
					int posizione = scelta.get("posizione").getAsInt();
					for(OpzioneRispostaQuestionarioDTO opz: rispostaSceltaQuestionario.getOpzioni()) {
						if(opz.getPosizione() == posizione) {
							opzioneQuestionario = opz;
							break;
						}
					}
					opzioneRispostaVerbale.setOpzioneQuestionario(opzioneQuestionario);
					rispostaScelta.getOpzioni().add(opzioneRispostaVerbale);
					//Non si gestiscono domande annidate perchè le opzioni delle domande figlie di una tabella non possono avere altre domande
				}
				
			}
			result = rispostaScelta;
			break;
		case RispostaVerbaleDTO.TIPO_TABELLA:
			//Metodo usato per parsare json di domanda tabella, la domanda tabella non può contenere altre domande tabella
			break;
		}
		
		return (T) result;
	}

	private static void parseDocumentiJson(JsonObject documento, VerbaleDTO verbale, Session session) {
		String fileName = documento.get("fileName").getAsString();
		String encodedFile = documento.get("encodedFile").getAsString();
		
		int idIntervento = verbale.getIntervento().getId();
		
		String path = "Intervento_"+idIntervento+File.separator+verbale.getType()+"_"+verbale.getCodiceCategoria()+"_"+verbale.getId()+File.separator+"Allegati"+File.separator;
		new File(Costanti.PATH_CERTIFICATI+path).mkdirs();
		File file = new File(Costanti.PATH_CERTIFICATI+path, fileName);
		
		byte[] decoded = Base64.decodeBase64(encodedFile);
		
		try {
			FileUtils.writeByteArrayToFile(file, decoded);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			DocumentoDTO allegato = new DocumentoDTO();
			allegato.setFilePath(path+fileName);
			allegato.setType(DocumentoDTO.ATTACHMENT);
			allegato.setVerbale(verbale);

	        GestioneDocumentoDAO.save(allegato, session);
	        verbale.getDocumentiVerbale().add(allegato);
	        GestioneVerbaleDAO.save(verbale, session);
		}

	}
	
	public static void invalidDocument(DocumentoDTO documento, Session session, String newfile) {
		documento.setInvalid(true);
		GestioneDocumentoDAO.save(documento, session);
		try {
			String filepath = Costanti.PATH_CERTIFICATI+documento.getFilePath();
			//System.out.println("filepath" + filepath);
			File tmpFile = new File(filepath+"tmp");
	        PdfReader reader = new PdfReader(filepath);
	        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(tmpFile));
	        Font f = new Font(FontFamily.HELVETICA, 12);
	        f.setColor(BaseColor.RED);
	        int pages = reader.getNumberOfPages();
	        for (int i=0; i<pages; i++) {	        
		        PdfContentByte over = stamper.getOverContent(i+1);
		        Phrase p = new Phrase(String.format(Costanti.DOCUMENT_IS_INVALID_PHRASE, newfile), f);
		        over.saveState();
		        PdfGState gs1 = new PdfGState();
		        gs1.setFillOpacity(0.7f);
		        over.setGState(gs1);
		        ColumnText.showTextAligned(over, Element.ALIGN_CENTER, p, 580, 450, 90);
		        over.restoreState();
	        }
	        stamper.close();
	        reader.close();
	        File fil = new File (filepath);
	        if(fil.exists()) {
	        	fil.delete();
	        }
			tmpFile.renameTo(new File(filepath));
		} catch (IOException e) {
			System.out.println("IOException: " + e);
			e.printStackTrace();
		} catch (DocumentException e) {
			System.out.println("DocumentException: " + e);
			e.printStackTrace();
		}
		
		
	}
	
	public static void newDocument(String path, String newfile, VerbaleDTO verbale) {

		try {
			String filepath = Costanti.PATH_CERTIFICATI+path;
			File tmpFile = new File(filepath+"tmp");
	        PdfReader reader = new PdfReader(filepath);
	        FileOutputStream tmpfos = new FileOutputStream(tmpFile);
	        PdfStamper stamper = new PdfStamper(reader, tmpfos);
	        Font f = new Font(FontFamily.HELVETICA, 12);
	        //f.setColor(BaseColor.RED);
	        int pages = reader.getNumberOfPages();
	        if(verbale.getType().equals(VerbaleDTO.VERBALE)){
		        for (int i=0; i<pages; i++) {	        
			        PdfContentByte over = stamper.getOverContent(i+1);
			        Phrase p = new Phrase(String.format(Costanti.DOCUMENT_INVALIDS_PHRASE, newfile), f);
			        over.saveState();
			        PdfGState gs1 = new PdfGState();
			        gs1.setFillOpacity(0.7f);
			        over.setGState(gs1);
			        ColumnText.showTextAligned(over, Element.ALIGN_CENTER, p, 25, 450, 90);
			        over.restoreState();
		        }
	        }
	        stamper.close();
	        tmpfos.close();
	        reader.close();
	        File fil = new File (filepath);
	        if(fil.exists()) {
	        	fil.delete();
	        }
			tmpFile.renameTo(new File(filepath));
		} catch (IOException e) {
			System.out.println("IOException: " + e);
			e.printStackTrace();
		} catch (DocumentException e) {
			System.out.println("DocumentException: " + e);
			e.printStackTrace();
		}
		
		
	}
	
	
	public static void addBozza(String path, String newfile, VerbaleDTO verbale) {

		try {
			String filepath = Costanti.PATH_CERTIFICATI+path;
			File tmpFile = new File(filepath+"tmp");
	        PdfReader reader = new PdfReader(filepath);
	        FileOutputStream tmpfos = new FileOutputStream(tmpFile);
	        PdfStamper stamper = new PdfStamper(reader, tmpfos);
	        Font f = new Font(FontFamily.HELVETICA, 50);
	        
	        String bozza = "BOZZA";
	        for(int i = 0; i<5;i++) {
	        	bozza=bozza+" - BOZZA";
	        }
	        
	        //f.setColor(BaseColor.RED);
	        int pages = reader.getNumberOfPages();
	        
		        for (int i=0; i<pages; i++) {	        
			        PdfContentByte over = stamper.getOverContent(i+1);
			        Phrase p = new Phrase(String.format(bozza, newfile), f);
			        over.saveState();
			        PdfGState gs1 = new PdfGState();
			        gs1.setFillOpacity(0.08f);			        
			        over.setGState(gs1);
			        for(int j = 0; j<13; j++) {
			        	ColumnText.showTextAligned(over, Element.ALIGN_CENTER, p, 300, (1000-(100*j)), 315);	
			        }
			        over.restoreState();
		        }
	        
	        stamper.close();
	        tmpfos.close();
	        reader.close();
	        File fil = new File (filepath);
	        if(fil.exists()) {
	        	fil.delete();
	        }
			tmpFile.renameTo(new File(filepath));
		} catch (IOException e) {
			System.out.println("IOException: " + e);
			e.printStackTrace();
		} catch (DocumentException e) {
			System.out.println("DocumentException: " + e);
			e.printStackTrace();
		}
		
		
	}
	


	
	public static void setStatoCompilazioneWeb(InterventoDTO intervento, StatoVerbaleDTO stato, Session session) {	
		for (VerbaleDTO verbale: intervento.getVerbali()) {
			
			if(verbale.getStato().getId() == StatoVerbaleDTO.CREATO) {
				if (verbale.getSchedaTecnica()!=null) {
					verbale.getSchedaTecnica().setDataScaricamento(new Date());
					buildVerbaleVuotoByQuestionario(verbale.getSchedaTecnica(), session);
					verbale.getSchedaTecnica().setStato(stato);	
				}
				verbale.setDataScaricamento(new Date());
				buildVerbaleVuotoByQuestionario(verbale, session);
				verbale.setStato(stato);
				session.update(verbale);
			}
			
		}
	}
	
	// metodo usato per creare un verbale o una scheda tecnica con questionario vuoto quando si passa nello stato "compilazione web"
	public static void buildVerbaleVuotoByQuestionario(VerbaleDTO verbale, Session session) {
		if (verbale != null) {
			if (verbale.getDomandeVerbale() != null) {
				verbale.getDomandeVerbale().clear();
			}
			QuestionarioDTO questionario = GestioneQuestionarioDAO.getQuestionarioById(verbale.getQuestionarioID(),
					session);
			
			if (questionario != null) {
				if (verbale.getType().equals(VerbaleDTO.VERBALE)) {
					if (questionario.getDomandeVerbale() != null) {
						for (DomandaQuestionarioDTO domandaQuestionario : questionario.getDomandeVerbale()) {
							DomandaVerbaleDTO domandaVerbale = buildDomandaVerbalebyDomadaQuestionario(domandaQuestionario, session);
							domandaVerbale.setVerbale(verbale);
							GestioneDomandaVerbaleDAO.save(domandaVerbale, session);
							verbale.addToDomande(domandaVerbale);
						}
					}
				} else if (verbale.getType().equals(VerbaleDTO.SK_TEC)){
					if (questionario.getDomandeSchedaTecnica() != null) {
						for (DomandaQuestionarioDTO domandaQuestionario : questionario.getDomandeSchedaTecnica()) {
							DomandaVerbaleDTO domandaVerbale = buildDomandaVerbalebyDomadaQuestionario(domandaQuestionario, session);
							domandaVerbale.setVerbale(verbale);
							GestioneDomandaVerbaleDAO.save(domandaVerbale, session);
							verbale.addToDomande(domandaVerbale);
						}
					}
				}
			}

		}
	}

	public static List<VerbaleDTO> getListaVerbaliDate(Session session, UtenteDTO user, String dateFrom,String dateTo) throws Exception {
		
		return GestioneVerbaleDAO.getListaVerbaliDate(session, user, dateFrom, dateTo);
	}

	public static ArrayList<VerbaleDTO> getListaVerbaliFromAttrezzatura(int id_attrezzatura,UtenteDTO user, Session session) {
		
		return GestioneVerbaleDAO.getListaVerbaliFromAttrezzatura(id_attrezzatura, user,session);
	}

	public static List<DocumentoDTO> getVerbaliPDFAll(Session session, String dateFrom, String dateTo) throws ParseException, Exception {
		
		return GestioneVerbaleDAO.getVerbaliPDFAll(session, dateFrom, dateTo);
	}

	public static List<VerbaleDTO> getListaVerbaliMinistero(Session session, String dateFrom, String dateTo) throws ParseException, Exception {
	
		return GestioneVerbaleDAO.getListaVerbaliMinistero(session, dateFrom, dateTo);
	}

	public static ArrayList<AllegatoMinisteroDTO> getListaAllegatiMinistero(Session session) {
		
		return GestioneVerbaleDAO.getListaAllegatiMinistero(session);
	}

	public static AllegatoMinisteroDTO getAllegatoMinistero(int id_allegato, Session session) {
		
		return GestioneVerbaleDAO.getAllegatoMinistero(id_allegato, session);
	}

	public static ArrayList<StoricoEmailVerbaleDTO> getListaEmailVerbale(int id, Session session) {
		
		return GestioneVerbaleDAO.getListaEmailVerbale(id, session);
	}

	public static ArrayList<AllegatoClienteDTO> getListaAllegatiCliente(int id_cliente, int id_sede, Session session) {
		
		return GestioneVerbaleDAO.getListaAllegatiCliente(id_cliente,id_sede, session);
	}


public static AllegatoClienteDTO getAllegatoCliente(int id_allegato, Session session) {
		
		return GestioneVerbaleDAO.getAllegatoCliente(id_allegato, session);
	}

}

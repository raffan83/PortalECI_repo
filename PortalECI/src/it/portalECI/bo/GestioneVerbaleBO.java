package it.portalECI.bo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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

import it.portalECI.DAO.GestioneDocumentoDAO;
import it.portalECI.DAO.GestioneDomandaVerbaleDAO;
import it.portalECI.DAO.GestioneQuestionarioDAO;
import it.portalECI.DAO.GestioneRispostaQuestionarioDAO;
import it.portalECI.DAO.GestioneRispostaVerbaleDAO;
import it.portalECI.DAO.GestioneStatoInterventoDAO;
import it.portalECI.DAO.GestioneStatoVerbaleDAO;
import it.portalECI.DAO.GestioneVerbaleDAO;
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
import it.portalECI.DTO.TemplateQuestionarioDTO;
import it.portalECI.DTO.TipoVerificaDTO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.DTO.VerbaleDTO;
import it.portalECI.Util.Costanti;

import org.apache.commons.codec.binary.Base64;


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
				
		if(verbale.getStato()==GestioneStatoVerbaleDAO.getStatoVerbaleById(StatoVerbaleDTO.CREATO, session) && stato.getId()==StatoVerbaleDTO.IN_COMPILAZIONE) {
			verbale.setDataScaricamento(new Date());
		}else if(verbale.getStato()==GestioneStatoVerbaleDAO.getStatoVerbaleById(StatoVerbaleDTO.IN_COMPILAZIONE, session) && stato.getId()==StatoVerbaleDTO.DA_VERIFICARE) {
			verbale.setDataTrasferimento(new Date());
		}
				
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
	
	public static VerbaleDTO buildVerbale(String codiceVerifica, Session session, Boolean creaSchedaTecnica) {
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
	
			if(questionario.getDomandeSchedaTecnica().size()>0 && creaSchedaTecnica) {
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
			nomefile = generaNumeroVerbale(questionario.getTipo(), intervento, session);
			
			verbale.setNumeroVerbale(nomefile);
		}else {
			VerbaleDTO verb=GestioneVerbaleDAO.getVerbaleFromSkTec(String.valueOf(verbale.getId()), session);
			intervento = verb.getIntervento();
			template = questionario.getTemplateSchedaTecnica();
			nomefile = questionario.getTitolo()+"_"+questionario.getTipo().getCodice()+"_"+intervento.getId();
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
        
		html = html + template.getTemplate();
		html = replacePlaceholders(html, verbale,intervento, session);
		String subheader = replacePlaceholders(template.getSubheader(), verbale,intervento, session);

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
        	newDocument(certificato.getFilePath(), vecchioDocFileName);
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
		html = replacePlaceholders(html, verbale,intervento, session);
		String subheader = replacePlaceholders(template.getSubheader(), verbale,intervento, session);
		GestioneTemplateQuestionarioBO.writePDF(fileOutput, html, template, subheader);
        
        if(vecchioDocumento != null) {
        	String vecchioDocFileName = vecchioDocumento.getFileName();
        	vecchioDocFileName = FilenameUtils.removeExtension(vecchioDocFileName);
        	newDocument(path+file.getName(), vecchioDocFileName);
        }

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
	
	public static String generaNumeroVerbale(TipoVerificaDTO tipo, InterventoDTO intervento, Session session) throws Exception {
		String numeroVerbale = new String();
		UtenteDTO utente = intervento.getTecnico_verificatore();
		ProgressivoVerbaleDTO progressivo = GestioneVerbaleDAO.getProgressivoVerbale(utente, tipo, session);
		String codUtente = utente.getCodice() == null ? "" : utente.getCodice();
		String sigla = tipo.getSigla() == null ? "" : tipo.getSigla();
		int prog = progressivo.getProgressivo();
		CommessaDTO comm=GestioneCommesseBO.getCommessaById(intervento.getIdCommessa());
		
		String codProv="";
		if(comm!=null && comm.getCOD_PROV()!=null ) 
		{
			codProv=comm.getCOD_PROV();
		}
		
	//	String codProv = intervento.getCodiceProvincia() == null ? " " : intervento.getCodiceProvincia();
		numeroVerbale = String.format("%s-%s-%03d-%s", codUtente, sigla, prog, codProv);
		//System.out.println("numeroVerbale" + numeroVerbale);
		return numeroVerbale;
	}
	
	private static String replacePlaceholders(String html, VerbaleDTO verbale, InterventoDTO intervento, Session session) throws Exception {
		
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
		
		//Inserisco la sede del cliente
		String sedeIntevento = intervento.getNome_sede();
		if(sedeIntevento != null) {
			html = html.replaceAll("\\$\\{SEDE_CLIENTE\\}", sedeIntevento);
		}

		//Inserisco numero verbale
		String numeroVerbale = verbale.getNumeroVerbale();
		if(numeroVerbale != null) {
			html = html.replaceAll("\\$\\{NUMERO_VERBALE\\}", numeroVerbale);
		}
		
		//Inserisco Nome Utilizzatore
		CommessaDTO clienteUtilizzatore = GestioneCommesseBO.getCommessaById(intervento.getIdCommessa());
		if(clienteUtilizzatore != null && clienteUtilizzatore.getNOME_UTILIZZATORE()!=null) {
			html = html.replaceAll("\\$\\{CLIENTE_UTILIZZATORE\\}", clienteUtilizzatore.getNOME_UTILIZZATORE());
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
			File tmpFile = new File(filepath+".tmp");
	        PdfReader reader = new PdfReader(filepath);
	        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(tmpFile));
	        Font f = new Font(FontFamily.HELVETICA, 15);
	        f.setColor(BaseColor.RED);
	        int pages = reader.getNumberOfPages();
	        for (int i=0; i<pages; i++) {	        
		        PdfContentByte over = stamper.getOverContent(i+1);
		        Phrase p = new Phrase(String.format(Costanti.DOCUMENT_IS_INVALID_PHRASE, newfile), f);
		        over.saveState();
		        PdfGState gs1 = new PdfGState();
		        gs1.setFillOpacity(0.7f);
		        over.setGState(gs1);
		        ColumnText.showTextAligned(over, Element.ALIGN_CENTER, p, 563, 450, 90);
		        over.restoreState();
	        }
	        stamper.close();
	        reader.close();
			tmpFile.renameTo(new File(filepath));
		} catch (IOException e) {
			System.out.println("IOException: " + e);
			e.printStackTrace();
		} catch (DocumentException e) {
			System.out.println("DocumentException: " + e);
			e.printStackTrace();
		}
		
		
	}
	
	public static void newDocument(String path, String newfile) {

		try {
			String filepath = Costanti.PATH_CERTIFICATI+path;
			File tmpFile = new File(filepath+"tmp");
	        PdfReader reader = new PdfReader(filepath);
	        FileOutputStream tmpfos = new FileOutputStream(tmpFile);
	        PdfStamper stamper = new PdfStamper(reader, tmpfos);
	        Font f = new Font(FontFamily.HELVETICA, 15);
	        //f.setColor(BaseColor.RED);
	        int pages = reader.getNumberOfPages();
	        for (int i=0; i<pages; i++) {	        
		        PdfContentByte over = stamper.getOverContent(i+1);
		        Phrase p = new Phrase(String.format(Costanti.DOCUMENT_INVALIDS_PHRASE, newfile), f);
		        over.saveState();
		        PdfGState gs1 = new PdfGState();
		        gs1.setFillOpacity(0.7f);
		        over.setGState(gs1);
		        ColumnText.showTextAligned(over, Element.ALIGN_CENTER, p, 35, 450, 90);
		        over.restoreState();
	        }
	        stamper.close();
	        tmpfos.close();
	        reader.close();
			tmpFile.renameTo(new File(filepath));
		} catch (IOException e) {
			System.out.println("IOException: " + e);
			e.printStackTrace();
		} catch (DocumentException e) {
			System.out.println("DocumentException: " + e);
			e.printStackTrace();
		}
		
		
	}

}

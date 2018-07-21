package it.portalECI.bo;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import it.portalECI.DAO.GestioneDomandaVerbaleDAO;
import it.portalECI.DAO.GestioneQuestionarioDAO;
import it.portalECI.DAO.GestioneRispostaQuestionarioDAO;
import it.portalECI.DAO.GestioneRispostaVerbaleDAO;
import it.portalECI.DAO.GestioneStatoInterventoDAO;
import it.portalECI.DAO.GestioneStatoVerbaleDAO;
import it.portalECI.DAO.GestioneVerbaleDAO;
import it.portalECI.DAO.SessionFacotryDAO;
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
import it.portalECI.DTO.VerbaleDTO;

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

					result = verbale;
				}
			}
		}
		return result;
	}
	
	
	public static void saveVerbaleResponses(JsonArray jsonRequest) {
		
		if(!jsonRequest.isJsonNull() ) {
			Session session=SessionFacotryDAO.get().openSession();
			session.beginTransaction();
			Iterator<JsonElement> iterator=jsonRequest.iterator();
			while(iterator.hasNext()) {
				JsonObject responseVerbale = (JsonObject)iterator.next();
				int responseID = responseVerbale.get("id").getAsInt();
				switch (responseVerbale.get("type").getAsString()) {
				case "RES_TEXT":
					RispostaTestoVerbaleDTO rispostaTesto = GestioneRispostaVerbaleDAO.getRispostaInstance(RispostaTestoVerbaleDTO.class, responseID, session);
					rispostaTesto.setResponseValue(responseVerbale.get("valore").getAsString());
					GestioneRispostaVerbaleDAO.save(rispostaTesto, session);
					break;
				case "RES_CHOICE":
					RispostaSceltaVerbaleDTO rispostaScelta = GestioneRispostaVerbaleDAO.getRispostaInstance(RispostaSceltaVerbaleDTO.class, responseID, session);
					JsonArray scelte = responseVerbale.getAsJsonArray("scelte");
					if(scelte!=null) {
						Iterator<JsonElement> iteratorScelte=scelte.iterator();
						while(iteratorScelte.hasNext()) {
							JsonObject scelta = (JsonObject)iteratorScelte.next();
							int sceltaID = scelta.get("id").getAsInt();
							boolean sceltaChoice = scelta.get("choice").getAsBoolean();
							OpzioneRispostaVerbaleDTO opzioneRispostaVerbaleDTO =GestioneRispostaVerbaleDAO.getOpzioneVerbale(sceltaID,session);
							opzioneRispostaVerbaleDTO.setChecked(sceltaChoice);
							GestioneRispostaVerbaleDAO.saveOpzioneVerbale(opzioneRispostaVerbaleDTO, session);
						}
					}
					break;
				case "RES_FORMULA":
					RispostaFormulaVerbaleDTO rispostaFormula = GestioneRispostaVerbaleDAO.getRispostaInstance(RispostaFormulaVerbaleDTO.class, responseID, session);
					rispostaFormula.setValue1(responseVerbale.get("valore_1").getAsString());
					rispostaFormula.setValue2(responseVerbale.get("valore_2").getAsString());
					rispostaFormula.setResponseValue(responseVerbale.get("risultato").getAsString());
					GestioneRispostaVerbaleDAO.save(rispostaFormula, session);
					break;

				default:
					break;
				}
				
			}
			session.getTransaction().commit();
			session.close();    	
		}
	}

}

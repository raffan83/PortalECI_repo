package it.portalECI.bo;

import org.hibernate.Session;

import it.portalECI.DAO.GestioneDomandaVerbaleDAO;
import it.portalECI.DAO.GestioneQuestionarioDAO;
import it.portalECI.DAO.GestioneRispostaVerbaleDAO;
import it.portalECI.DAO.GestioneStatoVerbaleDAO;
import it.portalECI.DAO.GestioneVerbaleDAO;
import it.portalECI.DTO.DomandaQuestionarioDTO;
import it.portalECI.DTO.DomandaVerbaleDTO;
import it.portalECI.DTO.QuestionarioDTO;
import it.portalECI.DTO.RispostaVerbaleDTO;
import it.portalECI.DTO.StatoVerbaleDTO;
import it.portalECI.DTO.VerbaleDTO;

public class GestioneVerbaleBO {
	
	
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
		VerbaleDTO result=null;
		QuestionarioDTO questionario= GestioneQuestionarioDAO.getQuestionarioById(verbale.getQuestionarioID(), session);
		if(questionario!=null) {
			if(questionario.getDomandeVerbale()!=null) {
				for(DomandaQuestionarioDTO domanda:questionario.getDomandeVerbale()) {
					DomandaVerbaleDTO domandaVerbaleDTO = new DomandaVerbaleDTO();
					domandaVerbaleDTO.setObbligatoria(domanda.getObbligatoria());
					domandaVerbaleDTO.setPlaceholder(domanda.getPlaceholder());
					domandaVerbaleDTO.setTesto(domanda.getTesto());
					if(domanda.getRisposta()!=null) {
						RispostaVerbaleDTO rispostaVerbaleDTO = new RispostaVerbaleDTO();
						rispostaVerbaleDTO.setPlaceholder(domanda.getRisposta().getPlaceholder());
						rispostaVerbaleDTO.setTipo(domanda.getRisposta().getTipo());
						GestioneRispostaVerbaleDAO.save(rispostaVerbaleDTO,session);
						domandaVerbaleDTO.setRisposta(rispostaVerbaleDTO);
					}
					GestioneDomandaVerbaleDAO.save(domandaVerbaleDTO, session);
					verbale.addToDomande(domandaVerbaleDTO);
				}
			}
			GestioneVerbaleDAO.save(verbale, session);
			
			result=verbale;
		}
			
		return result;
	}
	
	
	

}
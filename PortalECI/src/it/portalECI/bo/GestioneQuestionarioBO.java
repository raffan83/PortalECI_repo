package it.portalECI.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Session;

import it.portalECI.DAO.GestioneQuestionarioDAO;
import it.portalECI.DAO.GestioneRispostaQuestionarioDAO;
import it.portalECI.DAO.GestioneVerbaleDAO;
import it.portalECI.DTO.DomandaOpzioneQuestionarioDTO;
import it.portalECI.DTO.DomandaQuestionarioDTO;
import it.portalECI.DTO.DomandaSchedaTecnicaQuestionarioDTO;
import it.portalECI.DTO.DomandaVerbaleQuestionarioDTO;
import it.portalECI.DTO.OpzioneRispostaQuestionarioDTO;
import it.portalECI.DTO.QuestionarioDTO;
import it.portalECI.DTO.RispostaQuestionario;
import it.portalECI.DTO.RispostaSceltaQuestionarioDTO;
import it.portalECI.DTO.TipoVerificaDTO;
import it.portalECI.DTO.VerbaleDTO;

public class GestioneQuestionarioBO {
	
	public static final String[] GLOBAL_PLACEHOLDERS = new String[]{"TECNICO_VERIFICATORE","NUMERO_VERBALE","CLIENTE_UTILIZZATORE","INDIRIZZO_CLIENTE_UTILIZZATORE","CLIENTE","INDIRIZZO_CLIENTE",
																	"ATT_MATRICOLA","ATT_N_FABBRICA","ATT_DESCRIZIONE","ATT_ANNO_COSTRUZIONE","ATT_FABBRICANTE","ATT_MODELLO","ATT_SETTORE_IMPIEGO","SEDE_VIE"};

	public static List<QuestionarioDTO> getListaQuestionari(Session session) {
		return GestioneQuestionarioDAO.getListaQuestionari(session); 
	}

	public static QuestionarioDTO getQuestionarioById(String idQuestionario, Session session) {
		Integer idQuestionarioInt = 0;
		try {
			idQuestionarioInt = Integer.parseInt(idQuestionario);
		}catch (NumberFormatException e) {
			return null;
		}
		return getQuestionarioById(idQuestionarioInt, session);
	}
	
	public static QuestionarioDTO getQuestionarioById(Integer idQuestionario, Session session) {
		return GestioneQuestionarioDAO.getQuestionarioById(idQuestionario, session);
	}
	
	public static List<String> getQuestionariPlaceholder(String type,String idQuestionario, Session session) {
		QuestionarioDTO questionario  = GestioneQuestionarioDAO.getQuestionarioById(Integer.parseInt(idQuestionario), session);
		List<String> placeholders = new ArrayList<String>();
		if(type.equals("SchedaTecnica")) {
			for(DomandaSchedaTecnicaQuestionarioDTO domandaSchedaTecnica:questionario.getDomandeSchedaTecnica()) {
				getDomandaPlaceholder(domandaSchedaTecnica, session, placeholders);
			}
		}else {
			for(DomandaVerbaleQuestionarioDTO domandaVerbale:questionario.getDomandeVerbale()) {
				getDomandaPlaceholder(domandaVerbale, session, placeholders);
			}
		}
		placeholders.addAll(Arrays.asList(GLOBAL_PLACEHOLDERS));
		return placeholders;
	}

	public static void getDomandaPlaceholder(DomandaQuestionarioDTO domanda, Session session,  List<String> placeholders){
		placeholders.add(domanda.getPlaceholder());
		placeholders.add(domanda.getRisposta().getPlaceholder());
		if (domanda.getRisposta().getTipo().equals(RispostaQuestionario.TIPO_SCELTA)) {
			RispostaSceltaQuestionarioDTO rispostaScelta= GestioneRispostaQuestionarioDAO.getRispostaInstance(RispostaSceltaQuestionarioDTO.class, domanda.getRisposta().getId(), session);
			for(OpzioneRispostaQuestionarioDTO opzione:rispostaScelta.getOpzioni()) {
				placeholders.add(opzione.getPlaceholder());
				for(DomandaOpzioneQuestionarioDTO domandaOpzione:opzione.getDomande()) {
					getDomandaPlaceholder(domandaOpzione, session, placeholders);
				}
				
			}
		}
	}
	
	
	public static Boolean controlloQuestionarioInUso(Integer idQuestionario,Session session) {
		return GestioneQuestionarioDAO.controlloQuestionarioInUso(idQuestionario, session);
	}
	
	public static QuestionarioDTO getLastQuestionarioByVerifica(TipoVerificaDTO ver, Session session) {
		return GestioneQuestionarioDAO.getQuestionarioForVerbaleInstance(ver.getCodice(), session);
	}
	
	public static List<VerbaleDTO> getVerbaliConQuestionarioAggiornato(int idQuestionarioOld, Session session) {
		return GestioneVerbaleDAO.getVerbaliConQuestionarioAggiornato(idQuestionarioOld, session);
	}
	
}
package it.portalECI.bo;

import java.util.List;

import org.hibernate.Session;

import it.portalECI.DAO.GestioneQuestionarioDAO;
import it.portalECI.DAO.GestioneVerbaleDAO;
import it.portalECI.DTO.QuestionarioDTO;
import it.portalECI.DTO.TipoVerificaDTO;

public class GestioneQuestionarioBO {
	public static List<QuestionarioDTO> getListaQuestionari(Session session) {
		return GestioneQuestionarioDAO.getListaQuestionari(session); 
	}

	public static QuestionarioDTO getQuestionarioById(Integer idQuestionario, Session session) {
		return GestioneQuestionarioDAO.getQuestionarioById(idQuestionario, session);
	}
	
	public static List getQuestionariPlaceholder(Session session) {
		return GestioneQuestionarioDAO.getQuestionariPlaceholder( session);
	}
	
	public static Boolean controlloQuestionarioInUso(Integer idQuestionario,Session session) {
		return GestioneQuestionarioDAO.controlloQuestionarioInUso(idQuestionario, session);
	}
	
	public static QuestionarioDTO getLastQuestionarioByVerifica(TipoVerificaDTO ver, Session session) {
		return GestioneQuestionarioDAO.getQuestionarioForVerbaleInstance(ver.getCodice(), session);
	}
	
	public static void updateVerbaliConQuestionarioAggiornato(int idQuestionarioOld, int idQuestionarioNew, Session session) {
		GestioneVerbaleDAO.updateVerbaliConQuestionarioAggiornato(idQuestionarioOld, idQuestionarioNew, session);
	}
	
}
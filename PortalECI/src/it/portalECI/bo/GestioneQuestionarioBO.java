package it.portalECI.bo;

import java.util.List;

import org.hibernate.Session;

import it.portalECI.DAO.GestioneQuestionarioDAO;
import it.portalECI.DTO.QuestionarioDTO;

public class GestioneQuestionarioBO {
	public static List<QuestionarioDTO> getListaQuestionari(Session session) {
		return GestioneQuestionarioDAO.getListaQuestionari(session); 
	}
}

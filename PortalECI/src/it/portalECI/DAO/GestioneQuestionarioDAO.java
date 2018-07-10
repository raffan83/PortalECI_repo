package it.portalECI.DAO;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import it.portalECI.DTO.QuestionarioDTO;

public class GestioneQuestionarioDAO {
	
	public static List<QuestionarioDTO> getListaQuestionari(Session session){
		Query query  = session.createQuery( "from QuestionarioDTO");
		List<QuestionarioDTO> result = query.list();
		return result;
	}

	public static QuestionarioDTO getQuestionarioById(Integer idQuestionario, Session session) {
		QuestionarioDTO questionario  = (QuestionarioDTO) session.get( QuestionarioDTO.class, idQuestionario);
		return questionario;
	}

}

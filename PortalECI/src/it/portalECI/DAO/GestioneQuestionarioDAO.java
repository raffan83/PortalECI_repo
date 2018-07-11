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

	public static QuestionarioDTO getQuestionarioForVerbaleInstance(String codiceVerifica, Session session) {
		QuestionarioDTO result= null;
		Query query  = session.createQuery( "from QuestionarioDTO where tipo.codice = :_codice_verifica order by date(updateDate) desc");
		query.setParameter("_codice_verifica", codiceVerifica);	
		query.setMaxResults(1);
		
;		return (QuestionarioDTO) query.uniqueResult();
	}

}

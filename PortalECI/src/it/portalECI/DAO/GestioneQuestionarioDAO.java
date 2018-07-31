package it.portalECI.DAO;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import it.portalECI.DTO.CategoriaVerificaDTO;
import it.portalECI.DTO.InterventoDTO;
import it.portalECI.DTO.QuestionarioDTO;
import it.portalECI.DTO.StatoVerbaleDTO;
import it.portalECI.DTO.TipoVerificaDTO;

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
		Query query  = session.createQuery( "from QuestionarioDTO where tipo.codice = :_codice_verifica order by updateDate desc");
		query.setParameter("_codice_verifica", codiceVerifica);	
		query.setMaxResults(1);
		return (QuestionarioDTO) query.uniqueResult();
	}
	
	public static List getQuestionariPlaceholder(Session session) {			
		Query queryDom = session.createQuery("select DISTINCT(placeholder) from DomandaQuestionarioDTO" );
		Query queryRis =session.createQuery("select DISTINCT(placeholder) from RispostaQuestionario");
		return ListUtils.union(queryDom.list(), queryRis.list()); 
	}

	public static Boolean controlloQuestionarioInUso(Integer idQuestionario, Session session){
		List<String> lista =null;	
		Query query=session.createQuery("select stato.id from VerbaleDTO where questionarioID = :_idQuestionario");
		query.setParameter("_idQuestionario", idQuestionario);
		//settare i verbali col nuovo id
		lista=query.list();			
		int occurrences = Collections.frequency(lista, StatoVerbaleDTO.CREATO);		
		System.out.println("occurrences:" + occurrences + " - lista.size():" + lista.size());
		if(lista.size()==0 || occurrences==lista.size()) {
			return false;
		}
		return true;
	}


}

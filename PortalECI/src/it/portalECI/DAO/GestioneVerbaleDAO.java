package it.portalECI.DAO;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import it.portalECI.DTO.QuestionarioDTO;
import it.portalECI.DTO.StatoVerbaleDTO;
import it.portalECI.DTO.VerbaleDTO;

public class GestioneVerbaleDAO {
	
	public static List<VerbaleDTO> getListaVerabali(Session session){
		Query query  = session.createQuery( "from VerbaleDTO");
		List<VerbaleDTO> result = query.list();
		return result;
	}

	public static VerbaleDTO getQuestionarioById(Integer idVerbale, Session session) {
		VerbaleDTO verbale  = (VerbaleDTO) session.get( VerbaleDTO.class, idVerbale);
		return verbale;
	}

	public static void save(VerbaleDTO verbale, Session session) {
		session.save(verbale);
	}

	
	
}

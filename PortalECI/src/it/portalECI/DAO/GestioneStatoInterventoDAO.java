package it.portalECI.DAO;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import it.portalECI.DTO.QuestionarioDTO;
import it.portalECI.DTO.StatoInterventoDTO;
import it.portalECI.DTO.VerbaleDTO;

public class GestioneStatoInterventoDAO {
	
	public static List<StatoInterventoDTO> getListaStatoIntervento(Session session){
		Query query  = session.createQuery( "from StatoInterventoDTO");
		List<StatoInterventoDTO> result = query.list();
		return result;
	}

	public static StatoInterventoDTO getStatoInterventoById(Integer idStatoIntervento, Session session) {
		StatoInterventoDTO statoInterventoDTO  = (StatoInterventoDTO) session.get( StatoInterventoDTO.class, idStatoIntervento);
		return statoInterventoDTO;
	}

	public static void save(StatoInterventoDTO verbale, Session session) {
		session.save(verbale);
	}

}

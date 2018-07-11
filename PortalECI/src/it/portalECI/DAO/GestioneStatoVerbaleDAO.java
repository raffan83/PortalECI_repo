package it.portalECI.DAO;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import it.portalECI.DTO.QuestionarioDTO;
import it.portalECI.DTO.StatoInterventoDTO;
import it.portalECI.DTO.StatoVerbaleDTO;
import it.portalECI.DTO.VerbaleDTO;

public class GestioneStatoVerbaleDAO {
	
	public static List<StatoVerbaleDTO> getListaStatoVerbale(Session session){
		Query query  = session.createQuery( "from StatoVerbaleDTO");
		List<StatoVerbaleDTO> result = query.list();
		return result;
	}

	public static StatoVerbaleDTO getStatoVerbaleById(Integer idStatoIntervento, Session session) {
		StatoVerbaleDTO statoVerbaleDTO  = (StatoVerbaleDTO) session.get( StatoVerbaleDTO.class, idStatoIntervento);
		return statoVerbaleDTO;
	}

	public static void save(StatoVerbaleDTO verbale, Session session) {
		session.save(verbale);
	}

}

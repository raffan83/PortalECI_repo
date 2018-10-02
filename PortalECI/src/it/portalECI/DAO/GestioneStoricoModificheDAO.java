package it.portalECI.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Query;
import org.hibernate.Session;

import it.portalECI.DTO.StoricoModificheDTO;

public class GestioneStoricoModificheDAO {
	
	public static List<Integer> getListaStoricoModificheVerbale(Integer idVerbale, Session session){
		Query query  = session.createQuery( "from StoricoModificheDTO  WHERE verbale.id= :_idVerbale GROUP BY idRisposta");
		
		query.setParameter("_idVerbale", idVerbale);
		List<StoricoModificheDTO> result = query.list();
		List<Integer> ids = new ArrayList<Integer>();
		for (StoricoModificheDTO storicoModifiche: result) {
			int id = storicoModifiche.getIdRisposta();
			ids.add(id);
		}
		//List<Integer> ids = result.stream()
        //        .map(StoricoModificheDTO::getIdRisposta).collect(Collectors.toList());
		return ids;
	}
	
	public static List<StoricoModificheDTO> getListaStoricoModificheRisposta(Integer idRisposta, Session session){
		Query query  = session.createQuery( "from StoricoModificheDTO  WHERE idRisposta = :_idrisposta");
		
		query.setParameter("_idrisposta", idRisposta);
	
		return query.list();
	}

	public static StoricoModificheDTO getStoricoModificheById(Integer idStoricoModifiche, Session session) {
		StoricoModificheDTO storicoModifiche  = (StoricoModificheDTO) session.get( StoricoModificheDTO.class, idStoricoModifiche);
		return storicoModifiche;
	}

	public static void save(StoricoModificheDTO storicoModifiche, Session session) {
		session.save(storicoModifiche);
	}

}

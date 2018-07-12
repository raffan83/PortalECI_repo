package it.portalECI.DAO;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

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

	public static VerbaleDTO  getVerbale(String idVerbale, Session session) {
		
		Query query=null;
		VerbaleDTO verbale=null;
		try {
		
			String s_query = "from VerbaleDTO WHERE id = :_id";
			query = session.createQuery(s_query);
			query.setParameter("_id",Integer.parseInt(idVerbale));
		
			verbale=(VerbaleDTO)query.list().get(0);
			
			
			if(query.list().size()>0){	
				return (VerbaleDTO) query.list().get(0);
			}
			return null;
						

	    } catch(Exception e){
	    	e.printStackTrace();
	    }
		return verbale;		
	}
	
}

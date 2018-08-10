package it.portalECI.DAO;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import it.portalECI.DTO.StatoVerbaleDTO;
import it.portalECI.DTO.VerbaleDTO;

public class GestioneVerbaleDAO {
	
	public static List<VerbaleDTO> getListaVerbali(Session session){
		Query query  = session.createQuery( "from VerbaleDTO WHERE type = :_type");
		query.setParameter("_type",VerbaleDTO.VERBALE);
		
		List<VerbaleDTO> result = query.list();
		return result;
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
			
			if(query.list().size()>0){	
				return (VerbaleDTO) query.list().get(0);
			}
			return null;
						

	    } catch(Exception e){
	    	e.printStackTrace();
	    }
		return verbale;		
	}
	
	public static VerbaleDTO getVerbaleFromSkTec(String idSchedaTecnica, Session session) {
		
		Query query=null;
		VerbaleDTO verbale=null;
		try {
		
			String s_query = "from VerbaleDTO WHERE schedaTecnica.id = :_id";
			query = session.createQuery(s_query);
			query.setParameter("_id",Integer.parseInt(idSchedaTecnica));			
			
			if(query.list().size()>0){	
				return (VerbaleDTO) query.list().get(0);
			}
			return null;
						

	    } catch(Exception e){
	    	e.printStackTrace();
	    }
		return verbale;		
	}
	
	public static List<VerbaleDTO> getVerbaliConQuestionarioAggiornato(int idQuestionarioOld, Session session) {
		
		Query query=session.createQuery("from VerbaleDTO WHERE stato.id = :_stato and questionarioID = :_questionarioOld and type = :_type");	
			query.setParameter("_stato",StatoVerbaleDTO.CREATO);	
			query.setParameter("_questionarioOld",idQuestionarioOld);
			query.setParameter("_type",VerbaleDTO.VERBALE);
			List<VerbaleDTO> result = query.list();
			return result;
	}
	
}

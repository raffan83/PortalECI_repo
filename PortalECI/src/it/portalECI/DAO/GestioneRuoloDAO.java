package it.portalECI.DAO;


import it.portalECI.DTO.RuoloDTO;


import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class GestioneRuoloDAO {

	public static RuoloDTO getRuoloById(String id, Session session)throws HibernateException, Exception {

		Query query  = session.createQuery( "from RuoloDTO WHERE id= :_id");
	
		query.setParameter("_id", Integer.parseInt(id));
		List<RuoloDTO> result =query.list();
	
		if(result.size()>0){			
			return result.get(0);
		}
		return null;	
	}

}

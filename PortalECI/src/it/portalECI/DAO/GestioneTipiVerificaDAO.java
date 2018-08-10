package it.portalECI.DAO;


import it.portalECI.DTO.TipoVerificaDTO;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class GestioneTipiVerificaDAO {


	public static TipoVerificaDTO getTipoVerificaById(String id, Session session)throws HibernateException, Exception {

		Query query  = session.createQuery( "from TipoVerificaDTO WHERE id= :_id");
	
		query.setParameter("_id", Integer.parseInt(id));
		List<TipoVerificaDTO> result =query.list();
	
		if(result.size()>0){			
			return result.get(0);
		}
		return null;	
	}
	
}
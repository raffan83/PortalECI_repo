package it.portalECI.DAO;


import it.portalECI.DTO.CategoriaVerificaDTO;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class GestioneCategorieVerificaDAO {


	public static CategoriaVerificaDTO getCategoriaVerificaById(String id, Session session)throws HibernateException, Exception {

		Query query  = session.createQuery( "from CategoriaVerificaDTO WHERE id= :_id");
	
		query.setParameter("_id", Integer.parseInt(id));
		List<CategoriaVerificaDTO> result =query.list();
	
		if(result.size()>0){			
			return result.get(0);
		}
		return null;	
	}
	
}
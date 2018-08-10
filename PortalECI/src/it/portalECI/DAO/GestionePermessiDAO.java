package it.portalECI.DAO;

import it.portalECI.DTO.PermessoDTO;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class GestionePermessiDAO {

	public static PermessoDTO getPermessoById(String id, Session session)throws HibernateException, Exception {

		Query query  = session.createQuery( "from PermessoDTO WHERE id= :_id");
	
		query.setParameter("_id", Integer.parseInt(id));
		List<PermessoDTO> result =query.list();
	
		if(result.size()>0){			
			return result.get(0);
		}
		return null;	
	}
	
	public static Boolean checkChiavePermesso(String chiave_permesso, int idPermesso, Session session){		
		session.beginTransaction();
		Query query= session.createQuery( "from PermessoDTO WHERE chiave_permesso= :_chiave_permesso");		
		query.setParameter("_chiave_permesso", chiave_permesso);
			
		List<PermessoDTO> lista= query.list();
		
		if(lista.size()>0 && idPermesso==0) {
   			return false;				
   		}else if(idPermesso!=0) {
   			for(PermessoDTO perm : lista) {
   				if(perm.getIdPermesso()!=idPermesso) {
   					return false;
   				}
   			}
   		}
		
		return true;
	}

}

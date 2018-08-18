package it.portalECI.bo;

import org.hibernate.Session;

import it.portalECI.DAO.GestioneCategorieVerificaDAO;
import it.portalECI.DTO.CategoriaVerificaDTO;

import org.hibernate.Query;

public class GestioneCategorieVerificaBO {
	
	public static CategoriaVerificaDTO getCategoriaVerificaById(String id_str, Session session) throws Exception {

		return GestioneCategorieVerificaDAO.getCategoriaVerificaById(id_str, session);
	}

	public static int saveCategoriaVerifica(CategoriaVerificaDTO categoria, String action, Session session) {
		int toRet=0;
		
		try{
			int idCategoria=0;
		
			if(action.equals("modifica")){
				session.update(categoria);
				idCategoria=categoria.getId();
			}else if(action.equals("nuovo")){
				idCategoria=(Integer) session.save(categoria);
			}
		
			toRet=0;	
			
		}catch (Exception ex){
			toRet=1;
			throw ex;	 			 		
		}
		return toRet;	
	}
	
	public static int deleteCategoriaVerifica(CategoriaVerificaDTO categoria, Session session) {
		Query query = session.createQuery("from TipoVerificaDTO  WHERE categoria.id = :_categoria");
		query.setParameter("_categoria",categoria.getId());			

		if(query.list().size()>0){	
			return 2;
		}
		
		int toRet=0;
		try {
			session.delete(categoria);
			toRet=0;
		}catch (Exception ex){
			toRet=1;	 			 		
		}
		return toRet;	
	}
	
}


package it.portalECI.DAO;


import org.hibernate.Query;
import org.hibernate.Session;

import it.portalECI.DTO.DocumentoDTO;

public class GestioneDocumentoDAO {
	

	public static void save(DocumentoDTO documento, Session session) {
		session.saveOrUpdate(documento);
	}

	public static DocumentoDTO  getDocumento(String idDocumento, Session session) {
		
		Query query=null;
		DocumentoDTO documento=null;
		try {
		
			String s_query = "from DocumentoDTO WHERE id = :_id";
			query = session.createQuery(s_query);
			query.setParameter("_id",Integer.parseInt(idDocumento));			
			
			if(query.list().size()>0){	
				return (DocumentoDTO) query.list().get(0);
			}
			return null;
						

	    } catch(Exception e){
	    	e.printStackTrace();
	    }
		return documento;		
	}
	
}

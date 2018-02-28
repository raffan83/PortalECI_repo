package it.portalECI.DAO;

import it.portalECI.DTO.InterventoDTO;
import it.portalECI.DTO.MisuraDTO;
import it.portalECI.DTO.PuntoMisuraDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

public class GestioneMisuraDAO {

	public static int getTabellePerMisura(int idMisura) {
		
		Session session=SessionFacotryDAO.get().openSession();
		
			
		session.beginTransaction();
		Query query  = session.createQuery( "from InterventoDTO WHERE id_commessa= :_id_commessa");
		
	//	query.setParameter("_id_commessa", idCommessa);
				
	//	lista=query.list();
		
		session.getTransaction().commit();
		session.close();
		
		return 0;
	}
	
	public static MisuraDTO getMiruraByID(int idMisura) {
		
		MisuraDTO misura=null;
		try {
			Session session =SessionFacotryDAO.get().openSession();
			misura =  (MisuraDTO) session.get(MisuraDTO.class, idMisura);
	     } 
		catch(Exception e)
	     {
	    	 e.printStackTrace();
	    	 throw e;
	     }
		
		return misura;
	}
	public static int getMaxMisuraDaStrumento(int id, Session session) {
		
		
		Query query  = session.createQuery( "select max(id) from MisuraDTO as misuradto where id_strumento=:_id_str");
		query.setParameter("_id_str", id);
		
		
		List currentSeq = query.list();
		
        if(currentSeq.get(0) == null)
        {
            return 0;
        }
        else
        {
        	return (int)currentSeq.get(0);
        
        }
        
	
	}

	public static PuntoMisuraDTO getPuntoMisuraById(String id) {
		PuntoMisuraDTO punto=null;
		try {
			Session session =SessionFacotryDAO.get().openSession();
			punto =  (PuntoMisuraDTO) session.get(PuntoMisuraDTO.class, Integer.parseInt(id));
	     } 
		catch(Exception e)
	     {
	    	 e.printStackTrace();
	    	 throw e;
	     }
		
		return punto;
	}

	public static ArrayList<PuntoMisuraDTO> getListaPuntiByIdTabellaERipetizione(int idMisura, int idTabella, int idRipetizione) {
		Query query=null;
		
		ArrayList<PuntoMisuraDTO> lista=null;
		try {
		Session session =SessionFacotryDAO.get().openSession();
		session.beginTransaction();
		
		
		String s_query = "from PuntoMisuraDTO WHERE id_misura = :_id_misura AND id_tabella = :_id_tabella AND id_ripetizione = :_id_ripetizione";

	    query = session.createQuery(s_query);
	    query.setParameter("_id_misura", idMisura);
	    query.setParameter("_id_tabella", idTabella);
	    query.setParameter("_id_ripetizione", idRipetizione);
	   
	    
	    lista=(ArrayList<PuntoMisuraDTO>)query.list();

	     } 
		catch(Exception e)
	     {
	    	 e.printStackTrace();
	    	 throw e;
	     }
		
		return lista;
	}
	
}

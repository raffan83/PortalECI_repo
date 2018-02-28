package it.portalECI.DAO;

import it.portalECI.DTO.InterventoDTO;
import it.portalECI.DTO.InterventoDatiDTO;
import it.portalECI.DTO.MisuraDTO;
import it.portalECI.DTO.ObjSavePackDTO;
import it.portalECI.DTO.PrenotazioneDTO;
import it.portalECI.DTO.StrumentoDTO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

public class GestioneInterventoDAO {
	
	private final static String sqlQuery_Inervento="select a.id,presso_destinatario,id__SEDE_, nome_sede , data_creazione , b.descrizione, u.NOMINATIVO " +
			"from intervento  a " +
			"Left join stato_intervento  b  ON a.id_stato_intervento=b.id  " +
			"left join users u on a.id__user_creation=u.ID " +
			"where id_Commessa=?";

	

	public static List<InterventoDTO> getListaInterventi(String idCommessa, Session session) throws Exception {
		
		List<InterventoDTO> lista =null;
			
		session.beginTransaction();
		Query query  = session.createQuery( "from InterventoDTO WHERE id_commessa= :_id_commessa");
		
		query.setParameter("_id_commessa", idCommessa);
				
		lista=query.list();
		
		return lista;
		}



	public static InterventoDTO  getIntervento(String idIntervento) {
		
		Query query=null;
		InterventoDTO intervento=null;
		try {
			
		Session session = SessionFacotryDAO.get().openSession();
	    
		session.beginTransaction();
		
		String s_query = "from InterventoDTO WHERE id = :_id";
	    query = session.createQuery(s_query);
	    query.setParameter("_id",Integer.parseInt(idIntervento));
		
	    intervento=(InterventoDTO)query.list().get(0);
		session.getTransaction().commit();
		session.close();

	     } catch(Exception e)
	     {
	    	 e.printStackTrace();
	     }
		return intervento;
		
	}



	public static boolean isPresentStrumento(int id, StrumentoDTO strumento, Session session) {
		
		Query query=null;
		boolean isPresent=false;
		List<MisuraDTO> misura=null;
		try {
			Session session1=SessionFacotryDAO.get().openSession();	
			session1.beginTransaction();
			
		String s_query = "from MisuraDTO WHERE intervento.id = :_intervento AND strumento.__id =:_strumento";
						  
	    query = session1.createQuery(s_query);
	    query.setParameter("_intervento",id);
	    query.setParameter("_strumento",strumento.get__id());
		
	    misura=(List<MisuraDTO>)query.list();
		
	    session1.getTransaction().commit();
		session1.close();
		
	    if(misura.size()>0)
	    {
	    	return true;
	    }
	    	else
	    {
	    	return false;
	    }
	    
	    
		
	     } catch(Exception e)
	     {
	    	 e.printStackTrace();
	     }
		
		
		return isPresent;
		
	}



	public static void misuraObsoleta(MisuraDTO misura, Session session) throws Exception
	{
		
		Query query=null;
		
	
		
		String s_query = "update MisuraDTO SET obsoleto='S' WHERE id = :_id";
						  
	    query = session.createQuery(s_query);
	    query.setParameter("_id",misura.getId());
		
	   query.executeUpdate();	
	     	
	}



	public static void puntoMisuraObsoleto(int idTemp)throws Exception {
		
		Query query=null;
		
		Session session=SessionFacotryDAO.get().openSession();	
		session.beginTransaction();
		
		String s_query = "update PuntoMisuraDTO SET obsoleto='S' WHERE id_misura =:_idMisura";
						  
	    query = session.createQuery(s_query);
	    query.setParameter("_idMisura",idTemp);
	   
		
	   query.executeUpdate();
		

		session.getTransaction().commit();
		session.close();

	     	
	}



	public static MisuraDTO getMisuraObsoleta(int id, String idStr)throws Exception {
		
		Query query=null;
		MisuraDTO misura=null;

			
		Session session = SessionFacotryDAO.get().openSession();
	    
		session.beginTransaction();
		
		String s_query = "from MisuraDTO WHERE intervento.id = :_idIntervento AND strumento.__id=:_idStrumento AND obsoleto='N'";
					  //  from MisuraDTO WHERE intervento.id =36              
	    query = session.createQuery(s_query);
	    query.setParameter("_idIntervento",id);
	    query.setParameter("_idStrumento",Integer.parseInt(idStr));
		
	    misura=(MisuraDTO)query.list().get(0);
		session.getTransaction().commit();
		session.close();

	     
		return misura;
	}



	public static void update(InterventoDTO intervento, Session session) {
	
		session.update(intervento);
		
	}



	public static ArrayList<MisuraDTO> getListaMirureByInterventoDati(int idInterventoDati) {
		Query query=null;
		
		ArrayList<MisuraDTO> misura=null;
		try {
		Session session =SessionFacotryDAO.get().openSession();
		session.beginTransaction();
		
		
		String s_query = "from MisuraDTO WHERE interventoDati.id = :_interventoDati";
						  
	    query = session.createQuery(s_query);
	    query.setParameter("_interventoDati",idInterventoDati);
		
	    misura=(ArrayList<MisuraDTO>)query.list();

	     } 
		catch(Exception e)
	     {
	    	 e.printStackTrace();
	    	 throw e;
	     }
		
		return misura;
	}

	public static ArrayList<MisuraDTO> getListaMirureByIntervento(int idIntervento) {
		Query query=null;
		
		ArrayList<MisuraDTO> misura=null;
		try {
		Session session =SessionFacotryDAO.get().openSession();
		session.beginTransaction();
		
		
		String s_query = "from MisuraDTO WHERE intervento.id = :_idIntervento";
						  
	    query = session.createQuery(s_query);
	    query.setParameter("_idIntervento",idIntervento);
		
	    misura=(ArrayList<MisuraDTO>)query.list();

	     } 
		catch(Exception e)
	     {
	    	 e.printStackTrace();
	    	 throw e;
	     }
		
		return misura;
	}


	public static void update(InterventoDTO intervento) {
		Session s = SessionFacotryDAO.get().openSession();
		s.getTransaction().begin();
		
		s.update(intervento);
		
		s.getTransaction().commit();
		s.close();
		
	}



	public static void updateInterventoDati(InterventoDatiDTO interventoDati,Session session) {
		
		session.update(interventoDati);
	}



	public static ArrayList<InterventoDTO> getListaInterventiDaSede(String idCliente, String idSede, Integer idCompany,
			Session session) {
		ArrayList<InterventoDTO> lista =null;
		
		
		Query query  = session.createQuery( "from InterventoDTO WHERE id__sede_= :_idSede AND company.id=:_idCompany AND id_cliente=:_idcliente");
		
				query.setParameter("_idSede", Integer.parseInt(idSede));
				query.setParameter("_idCompany", idCompany);
				query.setParameter("_idcliente",  Integer.parseInt(idCliente));
				
		
		lista=(ArrayList<InterventoDTO>) query.list();
		
		return lista;
	}



	public static ArrayList<Integer> getListaClientiInterventi() {
		Query query=null;
		
		ArrayList<Integer> lista=null;
		try {
		Session session =SessionFacotryDAO.get().openSession();
		session.beginTransaction();
		
		
		String s_query = "select DISTINCT(int.id_cliente) from InterventoDTO as int";
						  
	    query = session.createQuery(s_query);
 		
	    lista=(ArrayList<Integer>)query.list();

	     } 
		catch(Exception e)
	     {
	    	 e.printStackTrace();
	    	 throw e;
	     }
		
		return lista;
	}



	public static ArrayList<Integer> getListaSediInterventi() {
		Query query=null;
		
		ArrayList<Integer> lista=null;
		try {
		Session session =SessionFacotryDAO.get().openSession();
		session.beginTransaction();
		
		
		String s_query = "select DISTINCT(int.idSede) from InterventoDTO as int";
						  
	    query = session.createQuery(s_query);
 		
	    lista=(ArrayList<Integer>)query.list();

	     } 
		catch(Exception e)
	     {
	    	 e.printStackTrace();
	    	 throw e;
	     }
		
		return lista;
	}
}

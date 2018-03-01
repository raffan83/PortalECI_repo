package it.portalECI.DAO;

import it.portalECI.DTO.InterventoDTO;


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



	

	public static void update(InterventoDTO intervento) {
		Session s = SessionFacotryDAO.get().openSession();
		s.getTransaction().begin();
		
		s.update(intervento);
		
		s.getTransaction().commit();
		s.close();
		
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

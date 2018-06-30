package it.portalECI.DAO;

import it.portalECI.DTO.AttivitaMilestoneDTO;
import it.portalECI.DTO.CategoriaVerificaDTO;
import it.portalECI.DTO.CommessaDTO;
import it.portalECI.DTO.InterventoDTO;
import it.portalECI.DTO.TipoVerificaDTO;
import it.portalECI.DTO.UtenteDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

public class GestioneInterventoDAO {
	
	private final static String sqlQuery_Inervento="select a.id,presso_destinatario,id__SEDE_, nome_sede , data_creazione , b.descrizione, u.NOMINATIVO " +
			"from intervento  a " +
			"Left join stato_intervento  b  ON a.id_stato_intervento=b.id  " +
			"left join users u on a.id__user_creation=u.ID " +
			"where id_Commessa=?";
	
	/*private static String querySqlServerCom="SELECT ID_COMMESSA,DT_COMMESSA,FIR_CHIUSURA_DT, B.ID_ANAGEN,b.NOME," +
			"a.DESCR,a.SYS_STATO,C.K2_ANAGEN_INDIR,C.DESCR,C.INDIR,C.CITTA,C.CODPROV,b.INDIR AS INDIRIZZO_PRINCIPALE,b.CITTA AS CITTAPRINCIPALE, b.CODPROV AS CODICEPROVINCIA,NOTE_GEN,N_ORDINE, ID_ANAGEN_COMM " +
			"FROM BWT_COMMESSA AS a " +
			"LEFT JOIN BWT_ANAGEN AS b ON  a.ID_ANAGEN=b.ID_ANAGEN " +
			"LEFT JOIN BWT_ANAGEN_INDIR AS c on a.K2_ANAGEN_INDIR=c.K2_ANAGEN_INDIR AND a.ID_ANAGEN=c.ID_ANAGEN ";*/

	public static List<InterventoDTO> getListaInterventi(String idCommessa, Session session) throws Exception {
		
		List<InterventoDTO> lista =null;
			
		session.beginTransaction();
		Query query;  
		if(idCommessa!=null) {
			query= session.createQuery( "from InterventoDTO WHERE id_commessa= :_id_commessa");
		
			query.setParameter("_id_commessa", idCommessa);		
		}else {
			query= session.createQuery( "from InterventoDTO");
		}
		
		lista=query.list();
			
		return lista;
	}
	

	public static InterventoDTO  getIntervento(String idIntervento, Session session) {
		
		Query query=null;
		InterventoDTO intervento=null;
		try {
		
			String s_query = "from InterventoDTO WHERE id = :_id";
			query = session.createQuery(s_query);
			query.setParameter("_id",Integer.parseInt(idIntervento));
		
			intervento=(InterventoDTO)query.list().get(0);
			
			
			if(query.list().size()>0){	
				return (InterventoDTO) query.list().get(0);
			}
			return null;
						

	    } catch(Exception e){
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
	
	public static ArrayList<TipoVerificaDTO> getListaTipoVerifica(Session session){
		
		ArrayList<TipoVerificaDTO> lista =null;
		
		Query query = session.createQuery("from TipoVerificaDTO");
		
		return lista= (ArrayList<TipoVerificaDTO>)query.list();
		
	}
	
	public static TipoVerificaDTO getTipoVerifica(String id, Session session) {
		
		Query query = session.createQuery("from TipoVerificaDTO where Id= :id");
		query.setParameter("id", Integer.parseInt(id));
		List<TipoVerificaDTO> result =query.list();
		
		if(result.size()>0){	
			return result.get(0);
		}
		return null;
	}
	
	public static CategoriaVerificaDTO getCategoriaVerifica(String id, Session session) {
		
		Query query = session.createQuery("from CategoriaVerificaDTO where Id= :id");
		query.setParameter("id", Integer.parseInt(id));
		List<CategoriaVerificaDTO> result =query.list();
		
		if(result.size()>0){	
			return result.get(0);
		}
		return null;
	}

	public static ArrayList<CategoriaVerificaDTO> getListaCategoriaVerifica(Session session){
		
		ArrayList<CategoriaVerificaDTO> lista =null;
		
		Query query = session.createQuery("from CategoriaVerificaDTO");
		
		return lista= (ArrayList<CategoriaVerificaDTO>)query.list();
		
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

	public static ArrayList<InterventoDTO> getListaInterventiTecnico(Session session, int idTecnicoVerificatore ) {
		ArrayList<InterventoDTO> lista =null;
		
		session.beginTransaction();
		Query query  = session.createQuery( "from InterventoDTO WHERE id_tecnico_verificatore= :_id_tecnico_verificatore");
		
		query.setParameter("_id_tecnico_verificatore", idTecnicoVerificatore);
				
		lista=(ArrayList<InterventoDTO>)query.list();
		
		return lista;
	}
	
	public static InterventoDTO getInterventoTecnico(Session session, int idTecnicoVerificatore,int idIntervento ) {
				
		session.beginTransaction();
		Query query  = session.createQuery( "from InterventoDTO WHERE id_tecnico_verificatore= :_id_tecnico_verificatore AND id= :_id_intervento");
		
		query.setParameter("_id_tecnico_verificatore", idTecnicoVerificatore);
		query.setParameter("_id_intervento", idIntervento);
		List<InterventoDTO> result =query.list();
		
		if(result.size()>0){	
			return result.get(0);
		}
		return null;
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

	    } catch(Exception e){
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

	    } catch(Exception e){
	    	e.printStackTrace();
	    	throw e;
	    }
		
		return lista;
	}
}
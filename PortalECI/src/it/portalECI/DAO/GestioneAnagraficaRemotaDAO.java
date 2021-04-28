package it.portalECI.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import it.portalECI.DTO.ClienteDTO;
import it.portalECI.DTO.ComuneDTO;
import it.portalECI.DTO.ProvinciaDTO;
import it.portalECI.DTO.SedeDTO;
import it.portalECI.DAO.ManagerSQLServer;


public class GestioneAnagraficaRemotaDAO {

	
	
	public static List<ClienteDTO> getListaClienti(String id_company) throws Exception  {
		
		List<ClienteDTO> lista =new ArrayList<ClienteDTO>();
		
		Connection con=null;
		PreparedStatement pst = null;
		ResultSet rs=null;
		
		try {
			con=ManagerSQLServer.getConnectionSQL();
			pst=con.prepareStatement("SELECT * FROM BWT_ANAGEN WHERE TOK_COMPANY LIKE ? ORDER BY NOME ASC");
			pst.setString(1, "%"+id_company+"%");
			rs=pst.executeQuery();
			
			ClienteDTO cliente=null;
			
			while(rs.next())
			{
				cliente= new ClienteDTO();
				cliente.set__id(rs.getInt("ID_ANAGEN"));
				cliente.setNome(rs.getString("NOME"));
			
				
				lista.add(cliente);
			}
			
		} catch (Exception e) {
			
			throw e;
		//	e.printStackTrace();
			
		}finally
		{
			pst.close();
			con.close();
		}
		
		return lista;
	}
	
	public static List<SedeDTO> getListaSedi() throws SQLException {
		List<SedeDTO> lista =new ArrayList<SedeDTO>();
		
		Connection con=null;
		PreparedStatement pst = null;
		ResultSet rs=null;
		
		try {
			con=ManagerSQLServer.getConnectionSQL();
			pst=con.prepareStatement("SELECT * FROM BWT_ANAGEN_INDIR ");
			rs=pst.executeQuery();
			
			SedeDTO sede=null;
			
			while(rs.next())
			{
				sede= new SedeDTO();
				sede.set__id(rs.getInt("K2_ANAGEN_INDIR"));
				sede.setId__cliente_(rs.getInt("ID_ANAGEN"));
				sede.setComune(rs.getString("CITTA"));
				sede.setIndirizzo(rs.getString("INDIR"));
				sede.setSiglaProvincia(rs.getString("CODPROV"));
				sede.setDescrizione(rs.getString("DESCR"));
				sede.setCap(rs.getString("CAP")); 
				sede.setEsercente(rs.getString("NOTE"));
				
				
				lista.add(sede);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally
		{
			pst.close();
			con.close();
		}
		
		return lista;
	}
	
	public static HashMap<String, String> getListaNominativiSediClienti() throws SQLException {
		
		HashMap<String, String> lista =new HashMap<String, String>();
		
		Connection con=null;
		PreparedStatement pst = null;
		ResultSet rs=null;
		
		try {
			con=ManagerSQLServer.getConnectionSQL();
			pst=con.prepareStatement("SELECT DESCR,K2_ANAGEN_INDIR FROM BWT_ANAGEN_INDIR");
			rs=pst.executeQuery();
			
			
			
			while(rs.next())
			{
				lista.put(rs.getString("K2_ANAGEN_INDIR"), rs.getString("DESCR"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally
		{
			pst.close();
			con.close();
		}
		
		return lista;
	}


		public static HashMap<String, String> getListaNominativiClienti() throws SQLException {
			
			HashMap<String, String> lista =new HashMap<String, String>();
			
			Connection con=null;
			PreparedStatement pst = null;
			ResultSet rs=null;
			
			try {
				con=ManagerSQLServer.getConnectionSQL();
				pst=con.prepareStatement("SELECT ID_ANAGEN,NOME FROM BWT_ANAGEN");
				rs=pst.executeQuery();
				
				
				
				while(rs.next())
				{
					lista.put(rs.getString("ID_ANAGEN"), rs.getString("NOME"));
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally
			{
				pst.close();
				con.close();
			}
			
			return lista;
		}
		
		public static ClienteDTO getClienteById(String id_cliente) throws Exception  {
			
			
			Connection con=null;
			PreparedStatement pst = null;
			ResultSet rs=null;
			ClienteDTO cliente=null;
			try {
				con=ManagerSQLServer.getConnectionSQL();
				pst=con.prepareStatement("SELECT * FROM BWT_ANAGEN WHERE ID_ANAGEN = "+ id_cliente);
				//pst.setString(1, "%"+id_cliente+"%");
				rs=pst.executeQuery();
				
				while(rs.next())
				{
					cliente= new ClienteDTO();
					cliente.set__id(rs.getInt("ID_ANAGEN"));
					cliente.setNome(rs.getString("NOME"));
					cliente.setPartita_iva(rs.getString("PIVA"));
					cliente.setCf(rs.getString("CODFIS"));
					cliente.setTelefono(rs.getString("TELEF01"));
					cliente.setCodice(rs.getString("CODCLI"));
					cliente.setIndirizzo(rs.getString("INDIR"));
					cliente.setCap(rs.getString("CAP"));
					cliente.setCitta(rs.getString("CITTA"));
					cliente.setProvincia(rs.getString("CODPROV"));
					cliente.setEmail(rs.getString("EMAIL"));
					cliente.setPec(rs.getString("EMAIL_PEC"));
					
				}
				
			} catch (Exception e) {
				
				throw e;
			//	e.printStackTrace();
				
			}finally
			{
				pst.close();
				con.close();
			}
			
			return cliente;
		}

	
	public static ClienteDTO getClienteFromSede(String id_cliente,String id_sede) throws Exception  {
		
		
		Connection con=null;
		PreparedStatement pst = null;
		ResultSet rs=null;
		ClienteDTO cliente=null;
		try {
			con=ManagerSQLServer.getConnectionSQL();
			pst=con.prepareStatement("SELECT *, BWT_ANAGEN_INDIR.TELEF01 as tel  FROM BWT_ANAGEN JOIN BWT_ANAGEN_INDIR ON BWT_ANAGEN_INDIR.ID_ANAGEN=BWT_ANAGEN.ID_ANAGEN WHERE BWT_ANAGEN_INDIR.ID_ANAGEN = " +id_cliente+ " AND K2_ANAGEN_INDIR = "+id_sede);
			//pst.setString(1, "%"+id_cliente+"%");
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				cliente= new ClienteDTO();
				cliente.set__id(rs.getInt("ID_ANAGEN"));
				cliente.setNome(rs.getString("NOME"));
				cliente.setPartita_iva(rs.getString("PIVA"));
				cliente.setTelefono(rs.getString("tel"));
				cliente.setCodice(rs.getString("CODCLI"));
				cliente.setCf(rs.getString("CODFIS"));
				cliente.setEmail(rs.getString("EMAIL"));
				cliente.setPec(rs.getString("EMAIL_PEC"));
			}
			
		} catch (Exception e) {
			
			throw e;
		//	e.printStackTrace();
			
		}finally
		{
			pst.close();
			con.close();
		}
		
		return cliente;
	}





		
		
		public static List<ClienteDTO> getListaFornitori(String id_company) throws Exception  {
			
			List<ClienteDTO> lista =new ArrayList<ClienteDTO>();
			
			Connection con=null;
			PreparedStatement pst = null;
			ResultSet rs=null;
			
			try {
				con=ManagerSQLServer.getConnectionSQL();
				pst=con.prepareStatement("SELECT * FROM BWT_ANAGEN WHERE TOK_COMPANY LIKE ? AND TIPO LIKE ?");
				pst.setString(1, "%"+id_company+"%");
				pst.setString(2, "%FOR%");
				rs=pst.executeQuery();
				
				ClienteDTO cliente=null;
				
				while(rs.next())
				{
					cliente= new ClienteDTO();
					cliente.set__id(rs.getInt("ID_ANAGEN"));
					cliente.setNome(rs.getString("NOME"));
				
					
					lista.add(cliente);
				}
				
			} catch (Exception e) {
				
				throw e;
			//	e.printStackTrace();
				
			}finally
			{
				pst.close();
				con.close();
			}
			
			return lista;
		}

		public static HashMap<String, String> getListaSedeAll() throws Exception {
			Connection con=null;
			PreparedStatement pst = null;
			ResultSet rs=null;
			
			HashMap<String, String> listaSedi= new HashMap<>();
			
			try {
				con=ManagerSQLServer.getConnectionSQL();
				pst=con.prepareStatement("SELECT * FROM BWT_ANAGEN_INDIR");
				
				rs=pst.executeQuery();
				
			
				
				while(rs.next())
				{
					listaSedi.put(rs.getInt("ID_ANAGEN")+"_"+rs.getInt("K2_ANAGEN_INDIR"), rs.getString("DESCR"));
					
				}
			
			} catch (Exception e) {
				
				throw e;
			//	e.printStackTrace();
				
			}finally
			{
				pst.close();
				con.close();
			}
			return listaSedi;
		}
		
		public static HashMap<Integer, String> getListaClientiAll() throws Exception {
			Connection con=null;
			PreparedStatement pst = null;
			ResultSet rs=null;
			
			HashMap<Integer, String> listaSedi= new HashMap<>();
			
			try {
				con=ManagerSQLServer.getConnectionSQL();
				pst=con.prepareStatement("SELECT * FROM BWT_ANAGEN");
				
				rs=pst.executeQuery();
				
			
				
				while(rs.next())
				{
					listaSedi.put(rs.getInt("ID_ANAGEN"), rs.getString("NOME"));
				}
				
			} catch (Exception e) {
				
				throw e;
			//	e.printStackTrace();
				
			}finally
			{
				pst.close();
				con.close();
			}
			return listaSedi;
		}

		public static ArrayList<ComuneDTO> getListaComuni(Session session) {
			
			ArrayList<ComuneDTO> lista = null;
			
			Query query = session.createQuery("from ComuneDTO");
			lista = (ArrayList<ComuneDTO>) query.list();
			return lista;
		}	
		
		public static ArrayList<ProvinciaDTO> getListaprovince(Session session) {
			
			ArrayList<ProvinciaDTO> lista = null;
			
			Query query = session.createQuery("from ProvinciaDTO");
			lista = (ArrayList<ProvinciaDTO>) query.list();
			return lista;
		}

		public static ArrayList<ComuneDTO> getListaComuniFromCAP(String cap, Session session) {
			ArrayList<ComuneDTO> lista = null;

			
			Query query = session.createQuery("from ComuneDTO where cap = :_cap");
			query.setParameter("_cap", cap);
			lista = (ArrayList<ComuneDTO>) query.list();
			

			return lista;
		}

		public static ComuneDTO getComuneFromId(int comune, Session session) {

			ArrayList<ComuneDTO> lista = null;
			ComuneDTO result = null;

			
			Query query = session.createQuery("from ComuneDTO where id = :_id");
			query.setParameter("_id", comune);
			lista = (ArrayList<ComuneDTO>) query.list();
			
			if(lista.size()>0) {
				result = lista.get(0);
			}

			return result;
		}

		public static String getRegioneFromProvincia(String siglaProvincia, Session session) {

			ArrayList<String> lista = null;
			String result = null;
			
			Query query = session.createQuery("select regione from ComuneDTO where provincia = :_siglaProvincia");
			query.setParameter("_siglaProvincia", siglaProvincia);
			lista = (ArrayList<String>) query.list();
			
			if(lista.size()>0) {
				result = lista.get(0);
			}

			return result;
		}	


}

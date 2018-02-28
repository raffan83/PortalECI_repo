package it.portalECI.DAO;

import it.portalECI.DTO.CampioneDTO;
import it.portalECI.DTO.ClienteDTO;
import it.portalECI.DTO.CompanyDTO;
import it.portalECI.DTO.DocumentiEsterniStrumentoDTO;
import it.portalECI.DTO.InterventoDTO;
import it.portalECI.DTO.InterventoDatiDTO;
import it.portalECI.DTO.MisuraDTO;
import it.portalECI.DTO.ObjSavePackDTO;
import it.portalECI.DTO.ProceduraDTO;
import it.portalECI.DTO.SedeDTO;
import it.portalECI.DTO.StrumentoDTO;
import it.portalECI.DTO.TipoMisuraDTO;
import it.portalECI.DTO.TipoRapportoDTO;
import it.portalECI.DTO.TipoStrumentoDTO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.Util.Costanti;
import it.portalECI.bo.GestioneInterventoBO;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class GestioneStrumentoDAO {

	public static List<StrumentoDTO> getListaStrumentiPerSede(String idSede) throws HibernateException, Exception
	{
	Session session=SessionFacotryDAO.get().openSession();
	List<StrumentoDTO> lista =null;
	
	session.beginTransaction();
	Query query  = session.createQuery( "from StrumentoDTO WHERE id__sede_= :_id_sede");
	
			query.setParameter("_id_sede", Integer.parseInt(idSede));
			
	
	lista=query.list();
	
	
	session.getTransaction().commit();
	session.close();
	
	return lista;
	}
	
	public static List<TipoStrumentoDTO> getListaTipoStrumento() throws HibernateException, Exception
	{
	Session session=SessionFacotryDAO.get().openSession();
	List<TipoStrumentoDTO> lista =null;
	
	session.beginTransaction();
	Query query  = session.createQuery( "from TipoStrumentoDTO Order BY nome ASC");

	lista=query.list();
	
	session.getTransaction().commit();
	session.close();

	return lista;
	}
	
	public static List<TipoRapportoDTO> getListaTipoRapporto() throws HibernateException, Exception
	{
	Session session=SessionFacotryDAO.get().openSession();
	List<TipoRapportoDTO> lista =null;
	
	session.beginTransaction();
	Query query  = session.createQuery( "from TipoRapportoDTO Order BY nome ASC");

	lista=query.list();
	
	session.getTransaction().commit();
	session.close();
	
	return lista;
	}

	public static List<ClienteDTO> getListaClienti() throws HibernateException, Exception {
		Session session=SessionFacotryDAO.get().openSession();
		List<ClienteDTO> lista =null;
		
		session.beginTransaction();
		Query query  = session.createQuery( "from ClienteDTO Order BY nome ASC" );
		
		lista=query.list();
		
		session.getTransaction().commit();
		session.close();
		
		return lista;
	}
	
	public static List<ClienteDTO> getListaClientiNew(String id_company) throws Exception  {
		
		List<ClienteDTO> lista =new ArrayList<ClienteDTO>();
		
		Connection con=null;
		PreparedStatement pst = null;
		ResultSet rs=null;
		
		try {
			con=ManagerSQLServer.getConnectionSQL();
			pst=con.prepareStatement("SELECT * FROM BWT_ANAGEN WHERE TOK_COMPANY LIKE ?");
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
	
public static List<SedeDTO> getListaSedi() throws HibernateException, Exception {
		
		Session session=SessionFacotryDAO.get().openSession();
		List<SedeDTO> lista =null;
		
		session.beginTransaction();
		Query query  = session.createQuery( "from SedeDTO Order BY id__cliente_ ASC" );
		
		lista=query.list();
		
		session.getTransaction().commit();
		session.close();
		
		return lista;
	}

public static List<SedeDTO> getListaSediNEW() throws SQLException {
	List<SedeDTO> lista =new ArrayList<SedeDTO>();
	
	Connection con=null;
	PreparedStatement pst = null;
	ResultSet rs=null;
	
	try {
		con=ManagerSQLServer.getConnectionSQL();
		pst=con.prepareStatement("SELECT * FROM BWT_ANAGEN_INDIR");
		rs=pst.executeQuery();
		
		SedeDTO sede=null;
		
		while(rs.next())
		{
			sede= new SedeDTO();
			sede.set__id(rs.getInt("K2_ANAGEN_INDIR"));
			sede.setId__cliente_(rs.getInt("ID_ANAGEN"));
			sede.setComune(rs.getString("LOCALITA"));
			sede.setIndirizzo(rs.getString("INDIR"));
			sede.setDescrizione(rs.getString("DESCR"));
			
			
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


public static List<TipoMisuraDTO> getListaTipiMisura(String tpS) throws HibernateException, Exception {
	Session session=SessionFacotryDAO.get().openSession();
	List<TipoMisuraDTO> lista =null;
	
	session.beginTransaction();
	Query query  = session.createQuery( "from TipoMisuraDTO WHERE id__tipo_strumento_= :_id_tipo_strumento");
	
			query.setParameter("_id_tipo_strumento", Integer.parseInt(tpS));
			
	
	lista=query.list();
	
	
	session.getTransaction().commit();
	session.close();
	
	return lista;
}

public static StrumentoDTO getStrumentoById(String id, Session session)throws HibernateException, Exception {


	Query query  = session.createQuery( "from StrumentoDTO WHERE id= :_id");
	
	query.setParameter("_id", Integer.parseInt(id));
	List<StrumentoDTO> result =query.list();
	


	if(result.size()>0)
	{			
		return result.get(0);
	}
	return null;
	
}

public static ArrayList<StrumentoDTO> getListaStrumenti(String idCliente,String idSede, Integer idCompany, Session session) throws Exception {
	
	ArrayList<StrumentoDTO> lista =null;
	
	
	Query query  = session.createQuery( "from StrumentoDTO WHERE id__sede_= :_idSede AND company.id=:_idCompany AND id_cliente=:_idcliente");
	
			query.setParameter("_idSede", Integer.parseInt(idSede));
			query.setParameter("_idCompany", idCompany);
			query.setParameter("_idcliente",  Integer.parseInt(idCliente));
			
	
	lista=(ArrayList<StrumentoDTO>) query.list();
	
	return lista;
}

public static ArrayList<MisuraDTO> getListaMirureByStrumento(int idStrumento) {

		Query query=null;
		
		ArrayList<MisuraDTO> misura=null;
		try {
		Session session =SessionFacotryDAO.get().openSession();
		session.beginTransaction();
		
		
		String s_query = "from MisuraDTO WHERE strumento.__id = :_idStrumento";
						  
	    query = session.createQuery(s_query);
	    query.setParameter("_idStrumento",idStrumento);
		
	    misura=(ArrayList<MisuraDTO>)query.list();
		
		session.getTransaction().commit();
		session.close();
	     } 
		catch(Exception e)
	     {
	    	 e.printStackTrace();
	    	 throw e;
	     }
		
		return misura;
	}

public static ProceduraDTO getProcedura(String proc) throws Exception {
	
	
	Session session =SessionFacotryDAO.get().openSession();
	session.beginTransaction();
	Query query  = session.createQuery( "from ProceduraDTO WHERE nome= :_nome");
	
	query.setParameter("_nome", proc);
	List<ProceduraDTO> result =query.list();
	


	if(result.size()>0)
	{			
		return result.get(0);
	}
	
	session.getTransaction().commit();
	session.close();
	return null;
}



public static HashMap<String, Integer> getListaStrumentiScadenziario(UtenteDTO user) {
	
	Query query=null;
	HashMap<String, Integer> listMap= new HashMap<String, Integer>();
	try {
		
	Session session = SessionFacotryDAO.get().openSession();
    
	session.beginTransaction();
	List<StrumentoDTO> lista =null;
	
	if(user.isTras())
	{
		query  = session.createQuery( "from StrumentoDTO ");	
	}
	else
	{
			if(user.getIdSede() != 0 && user.getIdCliente() != 0) {
				query  = session.createQuery( "from StrumentoDTO WHERE company.id= :_id_cmp AND id__sede_ =:_id_sede AND id_cliente=:idCliente");
				query.setParameter("_id_cmp", user.getCompany().getId());
				query.setParameter("_id_sede",user.getIdSede());
				query.setParameter("idCliente", user.getIdCliente());
				
		
			}else if(user.getIdSede() == 0 && user.getIdCliente() != 0) {
				query  = session.createQuery( "from StrumentoDTO WHERE company.id= :_id_cmp AND id_cliente=:idCliente");
				query.setParameter("_id_cmp", user.getCompany().getId());
				query.setParameter("idCliente", user.getIdCliente());
		
			}else {
				
				query  = session.createQuery( "from StrumentoDTO WHERE company.id= :_id_cmp");
				query.setParameter("_id_cmp", user.getCompany().getId());
			}
	}
	
	lista=query.list();
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	for (StrumentoDTO str: lista) {
		
		if(str.getScadenzaDTO()!=null && str.getScadenzaDTO().getDataProssimaVerifica()!=null)
		{
			int i=1;
			
			if(listMap.get(sdf.format(str.getScadenzaDTO().getDataProssimaVerifica()))!=null)
			{
				i= listMap.get(sdf.format(str.getScadenzaDTO().getDataProssimaVerifica()))+1;
			}
			
				listMap.put(sdf.format(str.getScadenzaDTO().getDataProssimaVerifica()),i);
				
		}
	}
	
	session.getTransaction().commit();
	session.close();

    } 
	catch(Exception e)
     {
    	 e.printStackTrace();
     } 
	return listMap;
}




public static List<StrumentoDTO> getListaStrumentiFromUser(UtenteDTO user, String dateFrom, String dateTo) {
	Query query=null;
	List<StrumentoDTO> list=null;

	try {

		Session session = SessionFacotryDAO.get().openSession();

		session.beginTransaction();

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		
		if(user.isTras())
		{
			if(dateFrom==null && dateTo!=null)
			{
				
		
				
				query  = session.createQuery( "select strumentodto from StrumentoDTO as strumentodto left join strumentodto.listaScadenzeDTO as lista where lista.dataProssimaVerifica = :dateTo");
				query.setParameter("dateTo",df.parse(dateTo));
			}
			
			else
			{
				query=session.createQuery("select strumentodto from StrumentoDTO as strumentodto left join strumentodto.listaScadenzeDTO as lista where lista.dataProssimaVerifica BETWEEN :dateFrom AND :dateTo");
				query.setParameter("dateFrom",df.parse(dateFrom));
				query.setParameter("dateTo",df.parse(dateTo));
			}

		}

		else
		{
		
			if(dateFrom==null && dateTo!=null)
			{
				if(user.getIdCliente()==0 && user.getIdSede()==0)
				{
					query  = session.createQuery( "select strumentodto from StrumentoDTO as strumentodto left join strumentodto.listaScadenzeDTO as lista where strumentodto.company.id=:_idCmp AND lista.dataProssimaVerifica = :dateTo ");
					query.setParameter("_idCmp", user.getCompany().getId());
					query.setParameter("dateTo",df.parse(dateTo));
				}
				else if(user.getIdCliente()!=0 && user.getIdSede()==0)
				{
					query  = session.createQuery( "select strumentodto from StrumentoDTO as strumentodto left join strumentodto.listaScadenzeDTO as lista where strumentodto.company.id=:_idCmp AND lista.dataProssimaVerifica = :dateTo  AND strumentodto.id_cliente=:idCliente");
					query.setParameter("_idCmp", user.getCompany().getId());
					query.setParameter("idCliente", user.getIdCliente());
					query.setParameter("dateTo",df.parse(dateTo));
				}
				else
				{
					query  = session.createQuery( "select strumentodto from StrumentoDTO as strumentodto left join strumentodto.listaScadenzeDTO as lista where lista.dataProssimaVerifica = :dateTo AND strumentodto.company.id=:_idCmp AND strumentodto.id_cliente=:idCliente AND strumentodto.id__sede_=:idSede");
					query.setParameter("_idCmp", user.getCompany().getId());
					query.setParameter("idCliente", user.getIdCliente());
					query.setParameter("idSede", user.getIdSede());
					query.setParameter("dateTo",df.parse(dateTo));
				}
			}else
			{
				if(user.getIdCliente()==0 && user.getIdSede()==0)
				{
					query  = session.createQuery( "select strumentodto from StrumentoDTO as strumentodto left join strumentodto.listaScadenzeDTO as lista where strumentodto.company.id=:_idCmp AND lista.dataProssimaVerifica BETWEEN :dateFrom AND :dateTo");
					query.setParameter("_idCmp", user.getCompany().getId());
					query.setParameter("dateTo",df.parse(dateTo));
					query.setParameter("dateFrom",df.parse(dateFrom));
				}
				else if(user.getIdCliente()!=0 && user.getIdSede()==0)
				{
					query  = session.createQuery( "select strumentodto from StrumentoDTO as strumentodto left join strumentodto.listaScadenzeDTO as lista where lista.dataProssimaVerifica BETWEEN :dateFrom AND :dateTo AND company.id=:_idCmp AND strumentodto.id_cliente=:idCliente");
					query.setParameter("_idCmp", user.getCompany().getId());
					query.setParameter("idCliente", user.getIdCliente());
					query.setParameter("dateTo",df.parse(dateTo));
					query.setParameter("dateFrom",df.parse(dateFrom));	
				}
				else
				{
					query  = session.createQuery( "select strumentodto from StrumentoDTO as strumentodto left join strumentodto.listaScadenzeDTO as lista where lista.dataProssimaVerifica BETWEEN :dateFrom AND :dateTo AND company.id=:_idCmp AND strumentodto.id_cliente=:idCliente AND strumentodto.id__sede_=:idSede");
					query.setParameter("_idCmp", user.getCompany().getId());
					query.setParameter("idCliente", user.getIdCliente());
					query.setParameter("idSede", user.getIdSede());
					query.setParameter("dateTo",df.parse(dateTo));
					query.setParameter("dateFrom",df.parse(dateFrom));
	
				}
			}
		}


		list=query.list();

		session.getTransaction().commit();
		session.close();

	} catch(Exception e)
	{
		e.printStackTrace();
	} 
	return list;
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
	
	public static ArrayList<StrumentoDTO> getListaStrumentiIntervento(InterventoDTO intervento) {
		
		ArrayList<StrumentoDTO> list=null;
		Session session = SessionFacotryDAO.get().openSession(); 
		session.beginTransaction();
		try {
		
			String s_query = "SELECT m.strumento from MisuraDTO m WHERE m.intervento =:_intervento GROUP BY m.strumento";
	
	    
			Query query = session.createQuery(s_query);
	
			query.setParameter("_intervento", intervento);
			
			list = (ArrayList<StrumentoDTO>)query.list();
			
			session.getTransaction().commit();
			session.close();
		
		}catch(Exception e)
	    {
			e.printStackTrace();
	    } 
		return list;

	}

	public static DocumentiEsterniStrumentoDTO getDocumentoEsterno(String idDocumento, Session session) {
		ArrayList<DocumentiEsterniStrumentoDTO> list=null;
		try {

			Query query  = session.createQuery( "from DocumentiEsterniStrumentoDTO WHERE id= :_id_doc");
			
			query.setParameter("_id_doc", Integer.parseInt(idDocumento));

			list = (ArrayList<DocumentiEsterniStrumentoDTO>)query.list();
			
	
		
		}catch(Exception e)
	    {
			e.printStackTrace();
	    } 
		
		if(list.size()>0)
		{			
			return list.get(0);
		}
		return null;
	}

	public static void deleteDocumentoEsterno(String idDocumento, Session session) {
		// TODO Auto-generated method stub
		DocumentiEsterniStrumentoDTO documento = getDocumentoEsterno(idDocumento, session);
		session.delete(documento);
		
	}

	public static ObjSavePackDTO saveDocumentoEsterno(FileItem fileUploaded, StrumentoDTO strumento, String dataVerifica,  Session session) {

		ObjSavePackDTO  objSave= new ObjSavePackDTO();
		
		File directory =new File(Costanti.PATH_FOLDER+"//DocumentiEsterni//"+strumento.get__id());
			
		if(directory.exists()==false)
		{
			directory.mkdir();
		}
		
		File file = new File(Costanti.PATH_FOLDER+"//DocumentiEsterni//"+strumento.get__id()+"/"+fileUploaded.getName());
				
		try {
			
			fileUploaded.write(file);
			objSave.setPackNameAssigned(file);
			
			
			DocumentiEsterniStrumentoDTO documento = new DocumentiEsterniStrumentoDTO();
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			Date dataCaricamento = format.parse(dataVerifica);
			documento.setDataCaricamento(dataCaricamento);
			documento.setId_strumento(strumento.get__id());
			documento.setNomeDocumento(fileUploaded.getName());
			
			session.save(documento);
			
			objSave.setEsito(1);

		
		} catch (Exception e) {

			e.printStackTrace();
			objSave.setEsito(0);
			objSave.setErrorMsg("Errore Salvataggio Dati");

			return objSave; 
		}
	
		return objSave;
	}
	

	public static ArrayList<Integer> getListaClientiStrumenti() {
		Query query=null;
		
		ArrayList<Integer> lista=null;
		try {
		Session session =SessionFacotryDAO.get().openSession();
		session.beginTransaction();
		
		
		String s_query = "select DISTINCT(str.id_cliente) from StrumentoDTO as str";
						  
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



	public static ArrayList<Integer> getListaSediStrumenti() {
		Query query=null;
		
		ArrayList<Integer> lista=null;
		try {
		Session session =SessionFacotryDAO.get().openSession();
		session.beginTransaction();
		
		
		String s_query = "select DISTINCT(str.id__sede_) from StrumentoDTO as str";
						  
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

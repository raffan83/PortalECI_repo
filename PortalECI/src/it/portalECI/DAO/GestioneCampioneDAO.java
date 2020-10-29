package it.portalECI.DAO;


import it.portalECI.DTO.AcAttivitaCampioneDTO;
import it.portalECI.DTO.CampioneDTO;
import it.portalECI.DTO.CertificatoCampioneDTO;
import it.portalECI.DTO.DocumentoDTO;
import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.TipoCampioneDTO;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;



public class GestioneCampioneDAO {

	private static String updateCompanyUtilizzatoreCampione="UPDATE campione set id_company_utilizzatore=? WHERE __id=?";

	public static ArrayList<CampioneDTO> getListaCampioni(String date, int idCompany) {
		Query query=null;
		ArrayList<CampioneDTO> list=null;
		try {
			
		Session session = SessionFacotryDAO.get().openSession();
	    
		session.beginTransaction();
		
		if(idCompany==0)
		{
		
		
				if(date!=null)
				{
				String s_query = "from CampioneDTO WHERE data_scadenza = :date and stato_campione != 'F'";
			    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		        Date dt = df.parse(date);
			    query = session.createQuery(s_query);
			    query.setParameter("date",dt);
				}
				else
				{
			     query  = session.createQuery( "from CampioneDTO");
				}
		}
		else
		{
			if(date!=null)
			{
			String s_query = "from CampioneDTO WHERE data_scadenza = :date AND id_company =:_idc and stato_campione != 'F'";
		    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	        Date dt = df.parse(date);
		    query = session.createQuery(s_query);
		    query.setParameter("date",dt);
		    query.setParameter("_idc", idCompany);
			}
			else
			{
		     query  = session.createQuery( "from CampioneDTO WHERE id_company=:_idc");
		     query.setParameter("_idc", idCompany);
			}
		}
		
		list = (ArrayList<CampioneDTO>)query.list();
		
		session.getTransaction().commit();
		session.close();
	
	     } catch(Exception e)
	     {
	    	 e.printStackTrace();
	     } 
		return list;

	}
	
	



	public static CampioneDTO getCampioneFromId(String campioneId) throws Exception{
		try 
		{
			Session session = SessionFacotryDAO.get().openSession();	    
			session.beginTransaction();
			
			CampioneDTO campione = (CampioneDTO) session.get(CampioneDTO.class, Integer.parseInt(campioneId));
			session.getTransaction().commit();
			session.close();
			
			return campione;
		}catch (Exception e){
			throw e;
		}

	}


	public static CampioneDTO getCampioneFromCodice(String codice) {
		Query query=null;
		CampioneDTO campione=null;
		try {
			
		Session session = SessionFacotryDAO.get().openSession();
	    
		session.beginTransaction();
		
		String s_query = "from CampioneDTO WHERE codice = :_codice ";
	    query = session.createQuery(s_query);
	    query.setParameter("_codice",codice);

	    
	    
	    if(query.list().size()>0)
	    {
	    	campione = (CampioneDTO)query.list().get(0);
	    }
		session.getTransaction().commit();
		session.close();

	     } catch(Exception e)
	     {
	    	 e.printStackTrace();
	     } 
		return campione;
	}

	public static CampioneDTO getCampioneFromCodiceCertificato(String codice) {
		Query query=null;
		CampioneDTO campione=null;
		try {
			
		Session session = SessionFacotryDAO.get().openSession();
	    
		session.beginTransaction();
		
		String s_query = "from CampioneDTO cp JOIN cp.listaCertificatiCampione ccp WHERE codice = :_codice AND ccp.obsoleto='N'";
	    query = session.createQuery(s_query);
	    query.setParameter("_codice",codice);

	    
	    
	    if(query.list().size()>0)
	    {
	    	campione = (CampioneDTO)query.list().get(0);
	    }
		session.getTransaction().commit();
		session.close();

	     } catch(Exception e)
	     {
	    	 e.printStackTrace();
	     } 
		return campione;
	}



	public static int saveCertifiactoCampione(
		CertificatoCampioneDTO certificatoCampioneDTO, Session session) {
		
		
		return (Integer) session.save(certificatoCampioneDTO);
	}


	public static void updateCertificatoCampione(
			CertificatoCampioneDTO certificatoCampioneDTO, Session session) {
	
		session.update(certificatoCampioneDTO);
		
	}




	public static void rendiObsoletiValoriCampione(Session session, int id) {
		
		Query query=null;
		try {
	    
		session.beginTransaction();
		
		String s_query = "UPDATE ValoreCampioneDTO set obsoleto = 'S' WHERE campione.id = :_id";

		query = session.createQuery(s_query);
	
		query.setParameter("_id", id);

	    query.executeUpdate();
		
	  
	     } catch(Exception e)
	     {
	    	 e.printStackTrace();
	    	 throw e;
	     } 
		
	}






	public static CertificatoCampioneDTO getCertifiactoCampioneById(String idCert) throws Exception{
		Query query=null;
		CertificatoCampioneDTO campione=null;
		try {
			
		Session session = SessionFacotryDAO.get().openSession();
	    
		session.beginTransaction();
		
		String s_query = "from CertificatoCampioneDTO WHERE id = :_id";
	    query = session.createQuery(s_query);
	    query.setParameter("_id",Integer.parseInt(idCert));
		
	    if(query.list().size()>0)
	    {
	    	campione = (CertificatoCampioneDTO)query.list().get(0);
	    }
		session.getTransaction().commit();
		session.close();

	     } catch(Exception e)
	     {
	    	 e.printStackTrace();
	    	 throw e;
	     } 
		return campione;
	}


	
	public static JsonArray getCampioniScadenzaDate(String data_start, String data_end,  int id_company) throws Exception {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");		
		Session session = SessionFacotryDAO.get().openSession();
		    
		session.beginTransaction();

		ArrayList<AcAttivitaCampioneDTO> attivita = null;
			
		ArrayList<CampioneDTO> lista = new ArrayList<CampioneDTO>();			
		ArrayList<Integer> lista_tipo = new ArrayList<Integer>();
		ArrayList<String> lista_date = new ArrayList<String>();
		JsonArray list = new JsonArray();			
	
		Query query = session.createQuery("from AcAttivitaCampioneDTO where  data_scadenza between :_date_start and :_date_end and obsoleta='N'");	
		query.setParameter("_date_start", df.parse(data_start));
		query.setParameter("_date_end", df.parse(data_end));
				
		attivita = (ArrayList<AcAttivitaCampioneDTO>) query.list();
				
		if(attivita!=null) {
			for (AcAttivitaCampioneDTO a : attivita) {
				if(a.getData_scadenza().after(df.parse(data_start)) && a.getData_scadenza().before(df.parse(data_end))) {
					lista.add(a.getCampione());
					lista_tipo.add(a.getTipo_attivita().getId());
					lista_date.add(df.format(a.getData_scadenza()));
				}
			}
		}
			
		Gson gson = new Gson(); 
		      
		JsonElement obj = gson.toJsonTree(lista);
		JsonElement obj_tipo = gson.toJsonTree(lista_tipo);
		JsonElement obj_date = gson.toJsonTree(lista_date);
			
		list.add(obj);
		list.add(obj_tipo);
		list.add(obj_date);
		session.close();
			
		return list;		
	}


	
public static Integer[] getProgressivoCampione() {
		
		int prog_sti = 0;
		int prog_cdt = 0;
		
		Integer[] ret =  new Integer[2];
		
		Session session = SessionFacotryDAO.get().openSession();
	    
		session.beginTransaction();
		
				
		Query query = session.createQuery("select seq_sti_campione from SequenceDTO");
			
		List<Integer> result = (List<Integer>)query.list();
				
		if(result.size()>0) {
			prog_sti = result.get(0);
		}
		
		
		query = session.createQuery("select seq_cdt_campione from SequenceDTO");
		
		result = (List<Integer>)query.list();
		
		if(result.size()>0) {
			prog_cdt = result.get(0);
		}
		
		ret[0] = prog_sti;
		ret[1] = prog_cdt;

		session.close();
		return ret;
	}




//public static SequenceDTO getSequence(Session session) {
//	
//	List<SequenceDTO> list = null;
//	SequenceDTO result = null;	
//			
//	Query query = session.createQuery("from SequenceDTO");
//		
//	list = (List<SequenceDTO>)query.list();
//			
//	if(list.size()>0) {
//		result = list.get(0);
//	}
//	
//	
//	return result;
//}




public static void updateManutenzioniObsolete(CampioneDTO campione, Session session) {

	Query query = session.createQuery("update RegistroEventiDTO set obsoleta='S' where id_campione =:_id_campione and tipo_evento=1 and tipo_manutenzione=1 ");
	
	query.setParameter("_id_campione", campione.getId());
	
	query.executeUpdate();
	
}




//public static ArrayList<RegistroEventiDTO> getListaManutenzioniNonObsolete() {
//	
//	Session session = SessionFacotryDAO.get().openSession();
//    
//	session.beginTransaction();
//	ArrayList<RegistroEventiDTO> lista = null;
//	
//	Query query = session.createQuery("from RegistroEventiDTO where tipo_evento.id = 1 and campione.statoCampione != 'F' and (obsoleta = null or obsoleta = 'N')");
//	
//			
//	lista = (ArrayList<RegistroEventiDTO>) query.list();
//	session.close();
//			
//	return lista;
//}




public static ArrayList<CampioneDTO> getListaCampioniInServizio() {
	
	Session session = SessionFacotryDAO.get().openSession();
    
	session.beginTransaction();
	ArrayList<CampioneDTO> lista = null;
	
	Query query = session.createQuery("from CampioneDTO where stato_campione!='F' and codice not like '%CDT%'");
	
			
	lista = (ArrayList<CampioneDTO>) query.list();
	session.close();
			
	return lista;
}



public static ArrayList<TipoCampioneDTO> getListaTipoCampione() {
	Query query=null;
	ArrayList<TipoCampioneDTO> list=null;
	try {
		
	Session session = SessionFacotryDAO.get().openSession();
    
	session.beginTransaction();
	
	String s_query = "from TipoCampioneDTO";
    query = session.createQuery(s_query);
    
	
	list = (ArrayList<TipoCampioneDTO>)query.list();
	
	session.getTransaction().commit();
	session.close();

     } catch(Exception e)
     {
    	 e.printStackTrace();
     } 
	return list;

}





public static ArrayList<DocumentoDTO> getListaDocumentiEsterni(int id_campione, Session session) {
	
	ArrayList<DocumentoDTO> lista = null;
	
	Query query = session.createQuery("from DocumentoDTO where campione.id = :_id_campione");
	query.setParameter("_id_campione", id_campione);
			
	lista = (ArrayList<DocumentoDTO>) query.list();
			
	return lista;
}
	
	
}




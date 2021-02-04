package it.portalECI.DAO;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import it.portalECI.DTO.AcAttivitaCampioneDTO;
import it.portalECI.DTO.AttrezzaturaDTO;
import it.portalECI.DTO.CampioneDTO;
import it.portalECI.DTO.DescrizioneGruppoAttrezzaturaDTO;
import it.portalECI.DTO.InterventoDTO;


public class GestioneAttrezzatureDAO {

	public static ArrayList<AttrezzaturaDTO> getlistaAttrezzature(Session session) {
		
		ArrayList<AttrezzaturaDTO> lista =null;
		
		Query query = session.createQuery("from AttrezzaturaDTO");
		
		return lista= (ArrayList<AttrezzaturaDTO>)query.list();
	}
	
	public static ArrayList<AttrezzaturaDTO> getlistaAttrezzatureSede(int id_cliente, int id_sede, boolean tutte, Session session) {
	
		ArrayList<AttrezzaturaDTO> lista =null;
		
		Query query = null;
		
		if(tutte) {
			query = session.createQuery("from AttrezzaturaDTO where id_cliente = :_id_cliente and id_sede =:_id_sede ");
		}else {
			query = session.createQuery("from AttrezzaturaDTO where id_cliente = :_id_cliente and id_sede =:_id_sede and obsoleta = 0 ");
		}
		query.setParameter("_id_cliente", id_cliente);
		query.setParameter("_id_sede", id_sede);
		 
		return lista= (ArrayList<AttrezzaturaDTO>)query.list();
	}

	public static AttrezzaturaDTO getAttrezzaturaFromId(int id_attrezzatura, Session session) {
		
		ArrayList<AttrezzaturaDTO> lista =null;
		AttrezzaturaDTO result = null;
		Query query = session.createQuery("from AttrezzaturaDTO where id = :_id");
		query.setParameter("_id", id_attrezzatura);
		
		lista= (ArrayList<AttrezzaturaDTO>)query.list();
		
		if(lista!=null && lista.size()>0) {
			result = lista.get(0);
		}
		return result;
	}
	
	public static ArrayList<HashMap<String, Integer>> getListaAttrezzatureScadenziario(Session session) {
		
		Query query=null;
		ArrayList<HashMap<String, Integer>> listMap= new ArrayList<HashMap<String, Integer>>();
		HashMap<String, Integer> mapFunzionamento = new HashMap<String, Integer>();
		HashMap<String, Integer> mapIntegrita = new HashMap<String, Integer>();
		HashMap<String, Integer> mapInterna = new HashMap<String, Integer>();
		List<AttrezzaturaDTO> lista =null;
		
		query  = session.createQuery( "from AttrezzaturaDTO ");	
		
		lista=query.list();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		for (AttrezzaturaDTO attr: lista) {
			
			
			if(attr.getData_prossima_verifica_funzionamento()!=null) {
			
				int i=1;
				if(mapFunzionamento.get(sdf.format(attr.getData_prossima_verifica_funzionamento()))!=null) {
					i= mapFunzionamento.get(sdf.format(attr.getData_prossima_verifica_funzionamento()))+1;
				}
				
				mapFunzionamento.put(sdf.format(attr.getData_prossima_verifica_funzionamento()), i);
				
			}
			
			if(attr.getData_prossima_verifica_integrita()!=null) {
				
				int i=1;
				if(mapIntegrita.get(sdf.format(attr.getData_prossima_verifica_integrita()))!=null) {
					i= mapIntegrita.get(sdf.format(attr.getData_prossima_verifica_integrita()))+1;
				}
				
				mapIntegrita.put(sdf.format(attr.getData_prossima_verifica_integrita()), i);
				
			}
			
			if(attr.getData_prossima_verifica_interna()!=null) {
				
				int i=1;
				if(mapInterna.get(sdf.format(attr.getData_prossima_verifica_interna()))!=null) {
					i= mapInterna.get(sdf.format(attr.getData_prossima_verifica_interna()))+1;
				}
				
				mapInterna.put(sdf.format(attr.getData_prossima_verifica_interna()), i);
				
			}
			

	    }
		
		listMap.add(mapFunzionamento);
		listMap.add(mapIntegrita);
		listMap.add(mapInterna);

		return listMap;
	}

	public static ArrayList<AttrezzaturaDTO> getlistaAttrezzatureData(Date data, String tipo_data, Session session) {
		
		ArrayList<AttrezzaturaDTO> lista =null;
		
		Query query = session.createQuery("from AttrezzaturaDTO where "+tipo_data+" = :_data");
		query.setParameter("_data", data);
				 
		return lista= (ArrayList<AttrezzaturaDTO>)query.list();		
		
	}

	public static ArrayList<DescrizioneGruppoAttrezzaturaDTO> getListaDescrizioniGruppi(Session session) {
		
		ArrayList<DescrizioneGruppoAttrezzaturaDTO> lista =null;
		
		Query query = session.createQuery("from DescrizioneGruppoAttrezzaturaDTO");
		
		lista= (ArrayList<DescrizioneGruppoAttrezzaturaDTO>)query.list();	
		
		return 	lista;
	}

//	public static ArrayList<Integer> getClientiSediTecnico(Session session, int id_tecnico, int cliente_sede) {		
//		
//		ArrayList<Integer> lista =null;
//		
//		Query query  = null;
//		
//		if(cliente_sede==0) {
//			query = session.createQuery( " select distinct id_cliente from InterventoDTO WHERE id_tecnico_verificatore= :_id_tecnico_verificatore");	
//		}else {
//			query = session.createQuery( " select distinct idSede from InterventoDTO WHERE id_tecnico_verificatore= :_id_tecnico_verificatore");
//		}		
//		
//		query.setParameter("_id_tecnico_verificatore", id_tecnico);
//		lista=(ArrayList<Integer>)query.list();
//		
//		return lista;
//	}
	
	
	public static ArrayList<Object[]> getClientiSediTecnico(Session session, int id_tecnico, int cliente_sede) {		
		
		ArrayList<Object[]> lista =null;
		
		Query query  = session.createQuery( " select distinct id_cliente, idSede from InterventoDTO WHERE id_tecnico_verificatore= :_id_tecnico_verificatore and idCommessa like '%VAL%'");	
			
		query.setParameter("_id_tecnico_verificatore", id_tecnico);
		
		
		lista=(ArrayList<Object[]>)query.list();
		
		return lista;
	}

	public static AttrezzaturaDTO getAttrezzaturaFromMatricola(String matricola, Session session) {
		
		ArrayList<AttrezzaturaDTO> lista =null;
		AttrezzaturaDTO result = null;
		Query query = session.createQuery("from AttrezzaturaDTO where matricola_inail = :_matricola");
		query.setParameter("_matricola", matricola);
		
		lista= (ArrayList<AttrezzaturaDTO>)query.list();
		
		if(lista!=null && lista.size()>0) {
			result = lista.get(0);
		}
		return result;
	}

	public static String getArticoloFromDescrizione(String descrizione, int tipo_verifica, Session session) {
		
		ArrayList<String> lista =null;
		String result = null;
		String indice_verifica = "indice_verifica_"+tipo_verifica;
		Query query = session.createQuery("select " +indice_verifica +" from DescrizioneGruppoAttrezzaturaDTO where descrizione = :_descrizione");
		query.setParameter("_descrizione", descrizione);
		
		lista= (ArrayList<String>)query.list();
		
		if(lista!=null && lista.size()>0) {
			result = lista.get(0);
		}
		return result;
	
	}

	public static ArrayList<AttrezzaturaDTO> getlistaAttrezzatureData(String data_start, String data_end, String tipo_data) throws HibernateException, ParseException {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");		
		Session session = SessionFacotryDAO.get().openSession();
		    
		session.beginTransaction();

		ArrayList<AttrezzaturaDTO> lista = new ArrayList<AttrezzaturaDTO>();			

		Query query = null;
		
		if(tipo_data.equals("0")) {
			
			query = session.createQuery("from AttrezzaturaDTO where (data_prossima_verifica_funzionamento between :_date_start and :_date_end) "
					+ "or  (data_prossima_verifica_integrita between :_date_start and :_date_end) "
					+ "or (data_prossima_verifica_interna between :_date_start and :_date_end) "
					+ "and obsoleta='N'  order by data_prossima_verifica_funzionamento asc");	
			
		}else if(tipo_data.equals("1")) {			
			query = session.createQuery("from AttrezzaturaDTO where data_prossima_verifica_funzionamento between :_date_start and :_date_end and obsoleta='N' order by data_prossima_verifica_funzionamento asc");	

		}else if(tipo_data.equals("2")) {			
			query = session.createQuery("from AttrezzaturaDTO where data_prossima_verifica_integrita between :_date_start and :_date_end and obsoleta='N' order by data_prossima_verifica_integrita asc");	

		}else if(tipo_data.equals("3")) {			
			query = session.createQuery("from AttrezzaturaDTO where data_prossima_verifica_interna between :_date_start and :_date_end and obsoleta='N' order by data_prossima_verifica_interna asc");	
		}
		query.setParameter("_date_start", df.parse(data_start));
		
		Date d = df.parse(data_end);
		long sec = d.getTime()-3600000;
		d.setTime(sec);
		query.setParameter("_date_end", d);
				
		lista = (ArrayList<AttrezzaturaDTO>) query.list();
		
		session.close();
			
		return lista;		
	}





public static ArrayList<AttrezzaturaDTO> getlistaAttrezzatureScadenza(String data_start, String data_end, String id_cliente, String id_sede,Session session) throws HibernateException, ParseException {

	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");	

	ArrayList<AttrezzaturaDTO> lista = new ArrayList<AttrezzaturaDTO>();			

	Query query = null;
	
	if(id_cliente.equals("0") && id_sede.equals("0")) {
		
		query = session.createQuery("from AttrezzaturaDTO where (data_prossima_verifica_funzionamento between :_date_start and :_date_end) "
				+ "or  (data_prossima_verifica_integrita between :_date_start and :_date_end) "
				+ "or (data_prossima_verifica_interna between :_date_start and :_date_end) "
				+ "and obsoleta='N'  order by data_prossima_verifica_funzionamento asc");	
		
	}else {			
		query = session.createQuery("from AttrezzaturaDTO where (data_prossima_verifica_funzionamento between :_date_start and :_date_end) "
				+ "or  (data_prossima_verifica_integrita between :_date_start and :_date_end) "
				+ "or (data_prossima_verifica_interna between :_date_start and :_date_end) "
				+ "and obsoleta='N' and id_cliente =:_id_cliente and id_sede =:_id_sede order by data_prossima_verifica_funzionamento asc");
		
		query.setParameter("_id_cliente", Integer.parseInt(id_cliente));
		query.setParameter("_id_sede", Integer.parseInt(id_sede));
	}
	query.setParameter("_date_start", df.parse(data_start));
	query.setParameter("_date_end", df.parse(data_end));
			
	lista = (ArrayList<AttrezzaturaDTO>) query.list();
	

	return lista;		
}

}

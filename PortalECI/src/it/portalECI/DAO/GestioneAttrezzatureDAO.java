package it.portalECI.DAO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import it.portalECI.DTO.AttrezzaturaDTO;


public class GestioneAttrezzatureDAO {

	public static ArrayList<AttrezzaturaDTO> getlistaAttrezzature(Session session) {
		
		ArrayList<AttrezzaturaDTO> lista =null;
		
		Query query = session.createQuery("from AttrezzaturaDTO");
		
		return lista= (ArrayList<AttrezzaturaDTO>)query.list();
	}
	
	public static ArrayList<AttrezzaturaDTO> getlistaAttrezzatureSede(int id_cliente, int id_sede, Session session) {
	
		ArrayList<AttrezzaturaDTO> lista =null;
		
		Query query = session.createQuery("from AttrezzaturaDTO where id_cliente = :_id_cliente and id_sede =:_id_sede) ");
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

}

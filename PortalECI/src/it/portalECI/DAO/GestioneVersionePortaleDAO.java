package it.portalECI.DAO;

import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;

import it.portalECI.DTO.VersionePortaleDTO;

public class GestioneVersionePortaleDAO {

	public static ArrayList<VersionePortaleDTO> getListaVersioniPortale(Session session) {
		
		ArrayList<VersionePortaleDTO> lista = null;
		
		Query query = session.createQuery("from VersionePortaleDTO where disabilitato = 0");
		
		lista = (ArrayList<VersionePortaleDTO>) query.list();
		
		return lista;
	}

	public static VersionePortaleDTO getVersioneFromId(int id_versione, Session session) {
		
		ArrayList<VersionePortaleDTO> lista;
		VersionePortaleDTO result = null;
		
		Query query = session.createQuery("from VersionePortaleDTO where id =:_id_versione");
		query.setParameter("_id_versione", id_versione);
		
		lista = (ArrayList<VersionePortaleDTO>) query.list();
		
		if(lista.size()>0) {
			result = lista.get(0);
		}
		
		return result;
	}

	public static String getVersioneCorrente(Session session) {
		
		ArrayList<String> lista;
		String result = null;
		
		Query query = session.createQuery("select versione from VersionePortaleDTO where disabilitato = 0 order by id desc ");
		
		
		lista = (ArrayList<String>) query.list();
		
		if(lista.size()>0) {
			result = lista.get(0);
		}
		
		return result;
	}

}

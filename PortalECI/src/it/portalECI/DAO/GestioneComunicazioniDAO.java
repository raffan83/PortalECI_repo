package it.portalECI.DAO;

import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;

import it.portalECI.DTO.ComunicazioneUtenteDTO;
import it.portalECI.DTO.TipoComunicazioneUtenteDTO;
import it.portalECI.DTO.UtenteDTO;

public class GestioneComunicazioniDAO {

	public static ArrayList<TipoComunicazioneUtenteDTO> getListaTipoComunicazione(Session session) {
		
		 ArrayList<TipoComunicazioneUtenteDTO> lista = null;
		
		Query query = session.createQuery("from TipoComunicazioneUtenteDTO");
		
		lista = (ArrayList<TipoComunicazioneUtenteDTO>) query.list();
		
		return lista;
	}

	public static TipoComunicazioneUtenteDTO getComunicazioneFromId(int id_comunicazione, Session session) {

		ArrayList<TipoComunicazioneUtenteDTO> lista = null;
		TipoComunicazioneUtenteDTO result = null;;
		
		Query query = session.createQuery("from TipoComunicazioneUtenteDTO where id =:_id_comunicazione");
		query.setParameter("_id_comunicazione", id_comunicazione);
		
		lista = (ArrayList<TipoComunicazioneUtenteDTO>) query.list();
		
		if(lista.size() > 0) {
			result = lista.get(0);
		}
		
		return result;
	}

	public static ArrayList<UtenteDTO> getListaUtentiComunicazione(String codiceCategoria, Session session) {

		ArrayList<UtenteDTO> lista = null;
		
		
		Query query = session.createQuery("select a.utente from ComunicazioneUtenteDTO a where a.tipo_comunicazione.descrizione =:_codiceCategoria");
		query.setParameter("_codiceCategoria", codiceCategoria);
		
		lista = (ArrayList<UtenteDTO>) query.list();
		
		
		
		return lista;
	}

}

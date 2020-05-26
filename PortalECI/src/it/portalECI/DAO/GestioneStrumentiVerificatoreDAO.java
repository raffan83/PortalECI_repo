package it.portalECI.DAO;

import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;

import it.portalECI.DTO.StrumentoVerificatoreDTO;

public class GestioneStrumentiVerificatoreDAO {

	public static ArrayList<StrumentoVerificatoreDTO> getListaStrumentiAll(Session session) {
		
		ArrayList<StrumentoVerificatoreDTO> lista = null;
		
		Query query = session.createQuery("from StrumentoVerificatoreDTO");
		
		lista = (ArrayList<StrumentoVerificatoreDTO>) query.list();

		return lista;
	}

	public static StrumentoVerificatoreDTO getStrumentoFromId(int id_strumento, Session session) {

		ArrayList<StrumentoVerificatoreDTO> lista = null;
		StrumentoVerificatoreDTO result = null;
		
		Query query = session.createQuery("from StrumentoVerificatoreDTO where id = :_id_strumento");
		query.setParameter("_id_strumento", id_strumento);
		
		lista = (ArrayList<StrumentoVerificatoreDTO>) query.list();
		
		if(lista.size()>0) {
			result = lista.get(0);
		}

		return result;
	}

}

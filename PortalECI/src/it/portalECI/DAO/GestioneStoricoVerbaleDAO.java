package it.portalECI.DAO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import it.portalECI.DTO.UtenteDTO;
import it.portalECI.DTO.VerbaleDTO;
import it.portalECI.DTO.VerbaleStoricoAllegatoDTO;
import it.portalECI.DTO.VerbaleStoricoDTO;

public class GestioneStoricoVerbaleDAO {

	public static ArrayList<VerbaleStoricoDTO> getListaVerbaliStorico(Session session, UtenteDTO user) {
		
		Query query=null;
		
		boolean ck_AM=user.checkRuolo("AM");
		boolean ck_ST=user.checkRuolo("ST");
		boolean ck_RT=user.checkRuolo("RT");
		boolean ck_SRT=user.checkRuolo("SRT");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		if(ck_ST==false && ck_AM==false && ck_RT==false && ck_SRT==false) 
		{		 
			query  = session.createQuery( "from VerbaleStoricoDTO WHERE codice_verificatore =:_codice");
		
			query.setParameter("_codice",user.getCodice());
			
		}
		else 
		{
		
			ArrayList<VerbaleStoricoDTO> lista;
			
			 query = session.createQuery("from VerbaleStoricoDTO");
		}
		ArrayList<VerbaleStoricoDTO> lista = (ArrayList<VerbaleStoricoDTO>) query.list();
		
		return lista;
	}

	public static List<VerbaleStoricoDTO> getListaVerbaliStoricoDate(Session session, UtenteDTO user, String dateFrom, String dateTo) throws Exception, ParseException {
		
		Query query=null;
		
		boolean ck_AM=user.checkRuolo("AM");
		boolean ck_ST=user.checkRuolo("ST");
		boolean ck_RT=user.checkRuolo("RT");
		boolean ck_SRT=user.checkRuolo("SRT");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		if(ck_ST==false && ck_AM==false && ck_RT==false && ck_SRT==false) 
		{		 
			query  = session.createQuery( "from VerbaleStoricoDTO WHERE codice_verificatore =:_codice and data_verifica between :_dateFrom and :_dateTo");
		
			query.setParameter("_codice",user.getCodice());
			query.setParameter("_dateFrom", sdf.parse(dateFrom));
			query.setParameter("_dateTo", sdf.parse(dateTo));
			
		}
		else 
		{

			query  = session.createQuery( "from VerbaleStoricoDTO WHERE data_verifica between :_dateFrom and :_dateTo");
			query.setParameter("_dateFrom", sdf.parse(dateFrom));
			query.setParameter("_dateTo", sdf.parse(dateTo));
			
		}
		List<VerbaleStoricoDTO> result = query.list();
		
		return result;
	}

	public static ArrayList<VerbaleStoricoAllegatoDTO> getAllegatiVerbale(int id_verbale, Session session) {

		ArrayList<VerbaleStoricoAllegatoDTO> lista;
		
		Query query = session.createQuery("from VerbaleStoricoAllegatoDTO where id_verbale_storico = :_id_verbale and disabilitato = 0");
		query.setParameter("_id_verbale", id_verbale);
		
		lista = (ArrayList<VerbaleStoricoAllegatoDTO>) query.list();
		
		return lista;
	}

	public static VerbaleStoricoAllegatoDTO getAllegatoFormId(int id, Session session) {
		ArrayList<VerbaleStoricoAllegatoDTO> lista;
		VerbaleStoricoAllegatoDTO result = null;
		
		Query query = session.createQuery("from VerbaleStoricoAllegatoDTO where id = :_id");
		query.setParameter("_id", id);
		
		lista = (ArrayList<VerbaleStoricoAllegatoDTO>) query.list();
		
		if(lista.size()>0) {
			result = lista.get(0);
		}
		
		return result;
	}

	public static VerbaleStoricoDTO getVerbaleFromCommessa(String codice_commessa, Session session) {
		
		ArrayList<VerbaleStoricoDTO> lista;
		VerbaleStoricoDTO result = null;
		
		Query query = session.createQuery("from VerbaleStoricoDTO where codice_commessa = :_codice_commessa");
		query.setParameter("_codice_commessa", codice_commessa);
		
		lista = (ArrayList<VerbaleStoricoDTO>) query.list();
		
		if(lista.size()>0) {
			result = lista.get(0);
		}
		
		return result;
	}

	public static int getVerbaleFromIdStorico(int verbeletter_id, Session session) {
		ArrayList<Integer> lista;
		int result = 0;
		
		Query query = session.createQuery("select id from VerbaleStoricoDTO where id_verbale_storico = :_verbeletter_id order by id desc");
		query.setParameter("_verbeletter_id", verbeletter_id);
		
		lista = (ArrayList<Integer>) query.list();
		
		if(lista.size()>0) {
			result = lista.get(0);
		}
		
		return result;
	}

}

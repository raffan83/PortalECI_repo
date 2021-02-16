package it.portalECI.bo;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import it.portalECI.DAO.GestioneStoricoVerbaleDAO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.DTO.VerbaleStoricoAllegatoDTO;
import it.portalECI.DTO.VerbaleStoricoDTO;

public class GestioneStoricoVerbaleBO {

	public static ArrayList<VerbaleStoricoDTO> getListaVerbaliStorico(Session session,UtenteDTO user) {
		
		return GestioneStoricoVerbaleDAO.getListaVerbaliStorico(session, user);
	}

	public static List<VerbaleStoricoDTO> getListaVerbaliStoricoDate(Session session,UtenteDTO user, String dateFrom, String dateTo) throws Exception, ParseException{
		
		return GestioneStoricoVerbaleDAO.getListaVerbaliStoricoDate(session, user, dateFrom, dateTo);
	}

	public static ArrayList<VerbaleStoricoAllegatoDTO> getAllegatiVerbale(int id_verbale, Session session) {
		
		return GestioneStoricoVerbaleDAO.getAllegatiVerbale(id_verbale, session);
	}

	public static VerbaleStoricoAllegatoDTO getAllegatoFormId(int id_allegato, Session session) {
		// 
		return GestioneStoricoVerbaleDAO.getAllegatoFormId(id_allegato, session);
	}

	public static VerbaleStoricoDTO getVerbaleFromCommessa(String codice_commessa, Session session) {
		
		return GestioneStoricoVerbaleDAO.getVerbaleFromCommessa(codice_commessa, session);
	}

}

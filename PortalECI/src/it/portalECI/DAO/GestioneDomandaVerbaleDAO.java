package it.portalECI.DAO;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import it.portalECI.DTO.DomandaVerbaleDTO;
import it.portalECI.DTO.QuestionarioDTO;
import it.portalECI.DTO.StatoInterventoDTO;
import it.portalECI.DTO.VerbaleDTO;

public class GestioneDomandaVerbaleDAO {
	


	public static void save(DomandaVerbaleDTO domanda, Session session) {
		session.saveOrUpdate(domanda);
	}

	public static void delete(DomandaVerbaleDTO domandaVerbaleDTO, Session session) {
		session.delete(domandaVerbaleDTO);
		
	}

}

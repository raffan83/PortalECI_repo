package it.portalECI.DAO;

import org.hibernate.Session;

import it.portalECI.DTO.RispostaVerbaleDTO;

public class GestioneRispostaVerbaleDAO {
	


	public static void save(RispostaVerbaleDTO domanda, Session session) {
		session.save(domanda);
	}

}

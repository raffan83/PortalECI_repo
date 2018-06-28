package it.portalECI.bo;

import org.hibernate.Session;

import it.portalECI.DAO.GestionePermessiDAO;

import it.portalECI.DTO.PermessoDTO;


public class GestionePermessiBO {

	
	public static PermessoDTO getPermessoById(String id_str, Session session) throws Exception {

		return GestionePermessiDAO.getPermessoById(id_str, session);
	}


}

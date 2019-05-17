package it.portalECI.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.hibernate.Session;

import it.portalECI.DAO.GestioneAttrezzatureDAO;
import it.portalECI.DTO.AttrezzaturaDTO;

public class GestioneAttrezzatureBO {

	public static ArrayList<AttrezzaturaDTO> getlistaAttrezzatureSede(int id_cliente, int id_sede, Session session) {
		
		return GestioneAttrezzatureDAO.getlistaAttrezzatureSede(id_cliente, id_sede, session);
	}

	public static AttrezzaturaDTO getAttrezzaturaFromId(int id_attrezzatura, Session session) {
		
		return GestioneAttrezzatureDAO.getAttrezzaturaFromId(id_attrezzatura, session);
	}

	public static ArrayList<HashMap<String, Integer>> getListaAttrezzatureScadenziario(Session session) {
		
		return GestioneAttrezzatureDAO.getListaAttrezzatureScadenziario(session);
	}

	public static ArrayList<AttrezzaturaDTO> getlistaAttrezzatureData(Date data, String tipo_data, Session session) {
	
		return GestioneAttrezzatureDAO.getlistaAttrezzatureData(data, tipo_data, session);
	}

}

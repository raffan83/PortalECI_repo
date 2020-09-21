package it.portalECI.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.hibernate.Session;

import it.portalECI.DAO.GestioneAttrezzatureDAO;
import it.portalECI.DTO.AttrezzaturaDTO;
import it.portalECI.DTO.DescrizioneGruppoAttrezzaturaDTO;

public class GestioneAttrezzatureBO {

	public static ArrayList<AttrezzaturaDTO> getlistaAttrezzature(Session session) {
		
		return GestioneAttrezzatureDAO.getlistaAttrezzature(session);
	}
	
	public static ArrayList<AttrezzaturaDTO> getlistaAttrezzatureSede(int id_cliente, int id_sede, boolean tutte, Session session) {
		
		return GestioneAttrezzatureDAO.getlistaAttrezzatureSede(id_cliente, id_sede, tutte, session);
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

	public static ArrayList<DescrizioneGruppoAttrezzaturaDTO> getListaDescrizioniGruppi(Session session) {
		
		return GestioneAttrezzatureDAO.getListaDescrizioniGruppi(session);
	}

//	public static ArrayList<Integer> getClientiSediTecnico(Session session, int id_tecnico, int cliente_sede) {
//		
//		return GestioneAttrezzatureDAO.getClientiSediTecnico(session, id_tecnico, cliente_sede);
//	}

	public static ArrayList<Object[]> getClientiSediTecnico(Session session, int id_tecnico, int cliente_sede) {
		
		return GestioneAttrezzatureDAO.getClientiSediTecnico(session, id_tecnico, cliente_sede);
	}

	public static AttrezzaturaDTO getAttrezzaturaFromMatricola(String matricola, Session session) {
		
		return GestioneAttrezzatureDAO.getAttrezzaturaFromMatricola(matricola, session);
	}

	public static String getArticoloFromDescrizione(String descrizione, int tipo_verifica, Session session) {

		return GestioneAttrezzatureDAO.getArticoloFromDescrizione(descrizione, tipo_verifica,session);
	}
}

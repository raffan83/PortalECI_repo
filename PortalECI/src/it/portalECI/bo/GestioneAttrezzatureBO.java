package it.portalECI.bo;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.google.gson.JsonArray;

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

	public static ArrayList<AttrezzaturaDTO> getlistaAttrezzatureData(String data_start, String data_end, String tipo_data) throws HibernateException, ParseException {
		
		return GestioneAttrezzatureDAO.getlistaAttrezzatureData(data_start, data_end, tipo_data);
	}
	
	public static ArrayList<AttrezzaturaDTO> getlistaAttrezzatureScadenza(String data_start, String data_end, String id_cliente, String id_sede,Session session) throws HibernateException, ParseException {
		
		return GestioneAttrezzatureDAO.getlistaAttrezzatureScadenza(data_start, data_end, id_cliente, id_sede, session);
	}

	public static AttrezzaturaDTO checkMatricola(String matricola_inail, Session session) {
		
		return GestioneAttrezzatureDAO.checkMatricola(matricola_inail, session);
	}

	public static ArrayList<AttrezzaturaDTO> getAttrezzatureScadenzaVentennale(Session session) {
		
		return GestioneAttrezzatureDAO.getAttrezzatureScadenzaVentennale(session);
	}

	public static ArrayList<AttrezzaturaDTO> getListaAttrezzatureInsieme(int id_attrezzatura, Session session) {
		
		return GestioneAttrezzatureDAO.getListaAttrezzatureInsieme(id_attrezzatura, session);
	}
}

package it.portalECI.bo;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import org.hibernate.Session;

import it.portalECI.DAO.GestioneAttivitaCampioneDAO;
import it.portalECI.DTO.AcAttivitaCampioneDTO;
import it.portalECI.DTO.AcTipoAttivitaCampioniDTO;
import it.portalECI.DTO.CampioneDTO;


public class GestioneAttivitaCampioneBO {

public static ArrayList<AcAttivitaCampioneDTO> getListaAttivita(int idC, Session session) {
		
		return GestioneAttivitaCampioneDAO.getListaAttivita(idC, session);
	}

	public static ArrayList<AcTipoAttivitaCampioniDTO> getListaTipoAttivitaCampione(Session session) {

		return GestioneAttivitaCampioneDAO.getListaTipoAttivitaCampione(session);
	}

	public static AcAttivitaCampioneDTO getAttivitaFromId(int id_attivita, Session session) {
		
		return GestioneAttivitaCampioneDAO.getAttivitaFromId(id_attivita, session);
	}

	public static ArrayList<AcAttivitaCampioneDTO> getListaManutenzioni(int id_campione, Session session) {
		
		return GestioneAttivitaCampioneDAO.getListaManutenzioni(id_campione, session);
	}
	
	public static ArrayList<AcAttivitaCampioneDTO> getListaTaratureVerificheIntermedie(int id_campione, Session session) {
		
		return GestioneAttivitaCampioneDAO.getListaTaratureVerificheIntermedie(id_campione, session);
	}


	public static ArrayList<AcAttivitaCampioneDTO> getListaVerificheIntermedie(int id_campione, Session session) {
		
		return GestioneAttivitaCampioneDAO.getListaVerificheIntermedie(id_campione, session);
	}



	public static ArrayList<HashMap<String, Integer>> getListaAttivitaScadenziario(Session session) {
		
		return GestioneAttivitaCampioneDAO.getListaAttivitaScadenziario(session);
	}

	public static ArrayList<HashMap<String, Integer>> getListaAttivitaScadenziarioCampione(CampioneDTO campione, Session session) {
		
		return GestioneAttivitaCampioneDAO.getListaAttivitaScadenziarioCampione(campione, session);
	}
	
	public static ArrayList<CampioneDTO> getListaCampioniPerData(String data, String tipo_data_lat) throws ParseException, Exception {
		
		return GestioneAttivitaCampioneDAO.getListaCampioniPerData(data,tipo_data_lat);
	}

	public static void updateObsolete(String idC, int tipo_attivita, Session session) {
	
		GestioneAttivitaCampioneDAO.updateObsolete(idC, tipo_attivita, session);
	}
}

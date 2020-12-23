package it.portalECI.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.portalECI.DAO.GestioneGraficiDAO;
import it.portalECI.DTO.UtenteDTO;

public class GestioneGraficiBO {

	public static ArrayList<ArrayList<String>> getGraficoTipoVerifica(UtenteDTO user) {
		
		return GestioneGraficiDAO.getGraficoTipoVerifica(user);
	}

	public static HashMap<String, Integer> getGraficoVerbaliVerificatore(UtenteDTO user) {
		
		return GestioneGraficiDAO.getGraficoVerbaliVerificatore( user);
	}

	public static ArrayList<HashMap<String, String>>  getGraficoStatiVerbali(UtenteDTO user) {
		
		return GestioneGraficiDAO.getGraficoStatiVerbali( user);
	}

}

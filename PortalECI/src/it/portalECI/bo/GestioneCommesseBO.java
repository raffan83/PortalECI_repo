package it.portalECI.bo;

import it.portalECI.DAO.GestioneCommesseDAO;
import it.portalECI.DTO.CommessaDTO;
import it.portalECI.DTO.CompanyDTO;
import it.portalECI.DTO.UtenteDTO;

import java.util.ArrayList;
import java.util.Map;

public class GestioneCommesseBO {

	public static ArrayList<CommessaDTO> getListaCommesse(CompanyDTO company, UtenteDTO user,int year) throws Exception {
				
		return GestioneCommesseDAO.getListaCommesse(company,user,year);
	}
	
	public static CommessaDTO getCommessaById(String idCommessa) throws Exception {
				
		return GestioneCommesseDAO.getCommessaById(idCommessa);
	}
	
	public static Map<String,String> getMappaClienti() throws Exception {
		return GestioneCommesseDAO.getMappaClienti();
	}
}

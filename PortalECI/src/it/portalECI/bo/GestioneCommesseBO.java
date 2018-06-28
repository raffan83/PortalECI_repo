package it.portalECI.bo;

import it.portalECI.DAO.GestioneCommesseDAO;
import it.portalECI.DTO.CommessaDTO;
import it.portalECI.DTO.CompanyDTO;
import it.portalECI.DTO.UtenteDTO;

import java.util.ArrayList;

public class GestioneCommesseBO {

	public static ArrayList<CommessaDTO> getListaCommesse(CompanyDTO company, String categoria, UtenteDTO user) throws Exception {
				
		return GestioneCommesseDAO.getListaCommesse(company,categoria,user);
	}
	
	public static CommessaDTO getCommessaById(String idCommessa) throws Exception {
				
		return GestioneCommesseDAO.getCommessaById(idCommessa);
	}

}

package it.portalECI.bo;

import java.util.ArrayList;

import org.hibernate.Session;

import it.portalECI.DAO.GestioneStrumentiVerificatoreDAO;
import it.portalECI.DTO.StrumentoVerificatoreDTO;

public class GestioneStrumentiVerificatoreBO {

	public static ArrayList<StrumentoVerificatoreDTO> getListaStrumentiAll(Session session) {
		
		return GestioneStrumentiVerificatoreDAO.getListaStrumentiAll(session);
	}

	public static StrumentoVerificatoreDTO getStrumentoFromId(int id_strumento, Session session) {
		
		return GestioneStrumentiVerificatoreDAO.getStrumentoFromId(id_strumento, session);
	}

}

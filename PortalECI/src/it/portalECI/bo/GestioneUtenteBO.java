package it.portalECI.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.hibernate.Session;

import it.portalECI.DAO.GestioneUtenteDAO;
import it.portalECI.DTO.UtenteDTO;


public class GestioneUtenteBO {

	
	public static UtenteDTO getUtenteById(String id_str, Session session) throws Exception {

		return GestioneUtenteDAO.getUtenteById(id_str, session);
	}


	public static void save(UtenteDTO utente, Session session) throws Exception {
		
		GestioneUtenteDAO.save(session,utente);
	}
	
	public static int saveUtente(UtenteDTO utente, String action, Session session) {
		int toRet=0;
		
		try{
			int idUtente=0;
		
			if(action.equals("modifica")){
				session.update(utente);
				idUtente=utente.getId();
			}else if(action.equals("nuovo")){
				idUtente=(Integer) session.save(utente);
			}
		
			toRet=0;	
			
		}catch (Exception ex){
			toRet=1;
			throw ex;
		}
		return toRet;
	}

	public static List<UtenteDTO> getTecnici(String tipo, Session session){
		
		List<UtenteDTO> result = GestioneUtenteDAO.getListaTecnici(tipo, session);
		
		return result;		
	}

}

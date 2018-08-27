package it.portalECI.bo;

import java.util.List;

import org.hibernate.Session;

import it.portalECI.DAO.GestionePermessiDAO;
import it.portalECI.DTO.PermessoDTO;

public class GestionePermessiBO {

	
	public static PermessoDTO getPermessoById(String id_str, Session session) throws Exception {

		return GestionePermessiDAO.getPermessoById(id_str, session);
	}

	public static Boolean savePermesso(PermessoDTO permesso, String action, Session session) {
		Boolean toRet=true;
		
		try{		
			if(action.equals("modifica")){
				session.update(permesso);
			}else if(action.equals("nuovo")){
				session.save(permesso);
			}					
		}catch (Exception ex){
			toRet=false;			 	
		}
		
		return toRet;		
	}
	
	public static Boolean checkChiavePermesso(String chiave_permesso,int idpermesso, Session session){		
		return GestionePermessiDAO.checkChiavePermesso(chiave_permesso,idpermesso, session);		
	}
}

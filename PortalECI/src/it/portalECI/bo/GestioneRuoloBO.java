package it.portalECI.bo;

import org.hibernate.Session;

import it.portalECI.DAO.GestioneRuoloDAO;

import it.portalECI.DTO.RuoloDTO;



public class GestioneRuoloBO {

	public static RuoloDTO getRuoloById(String id_str, Session session) throws Exception {

		return GestioneRuoloDAO.getRuoloById(id_str, session);
	}

	public static int saveRuolo(RuoloDTO ruolo, String action, Session session) {
		int toRet=0;
		
		try{
			int idRuolo=0;
		
			if(action.equals("modifica")){
				session.update(ruolo);
				idRuolo=ruolo.getId();
			}else if(action.equals("nuovo")){
				idRuolo=(Integer) session.save(ruolo);
			}
		
			toRet=0;	
			
		}catch (Exception ex){
			toRet=1;
			throw ex;	 			 	
		}
		return toRet;		
	}

}

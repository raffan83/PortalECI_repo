package it.portalECI.bo;

import org.hibernate.Session;

import it.portalECI.DAO.GestioneTipiVerificaDAO;
import it.portalECI.DTO.CategoriaVerificaDTO;
import it.portalECI.DTO.TipoVerificaDTO;




public class GestioneTipiVerificaBO {
	
	public static TipoVerificaDTO getTipoVerificaById(String id_str, Session session) throws Exception {

		return GestioneTipiVerificaDAO.getTipoVerificaById(id_str, session);
	}

	public static int saveTipoVerifica(TipoVerificaDTO tipo, String action, Session session) {
		int toRet=0;
		
		try{
			int idTipo=0;
		
			if(action.equals("modifica")){
				session.update(tipo);
				idTipo=tipo.getId();
			}else if(action.equals("nuovo")){
				idTipo=(Integer) session.save(tipo);
			}
		
			toRet=0;	
			
		}catch (Exception ex){
			toRet=1;
			throw ex;	 			 		
		}
		return toRet;	
	}
	
	public static int deleteTipoVerifica(TipoVerificaDTO tipo, Session session) {
		int toRet=0;
		try {
			session.delete(tipo);
			toRet=0;
		}catch (Exception ex){
			toRet=1;
			throw ex;	 			 		
		}
		return toRet;	
	}
	
}

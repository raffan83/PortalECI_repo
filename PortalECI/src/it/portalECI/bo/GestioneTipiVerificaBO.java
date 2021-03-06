package it.portalECI.bo;

import org.hibernate.Query;
import org.hibernate.Session;

import it.portalECI.DAO.GestioneQuestionarioDAO;
import it.portalECI.DAO.GestioneTipiVerificaDAO;
import it.portalECI.DTO.CategoriaVerificaDTO;
import it.portalECI.DTO.QuestionarioDTO;
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
		Query query = session.createQuery("from VerbaleDTO WHERE codiceVerifica = :_codiceVerifica");
		query.setParameter("_codiceVerifica",tipo.getCodice());
		
		if(query.list().size()>0){	
			return 2;
		}
		
		QuestionarioDTO quest=GestioneQuestionarioDAO.getQuestionarioForVerbaleInstance(tipo.getCodice(), session);
		if(quest!=null) {
			return 2;
		}
		
		int toRet=0;
		try {
			session.delete(tipo);
			toRet=0;
		}catch (Exception ex){
			toRet=1;
		}
		return toRet;	
	}
	
}

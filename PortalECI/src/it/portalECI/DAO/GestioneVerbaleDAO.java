package it.portalECI.DAO;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import it.portalECI.DTO.ProgressivoVerbaleDTO;
import it.portalECI.DTO.StatoVerbaleDTO;
import it.portalECI.DTO.TipoVerificaDTO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.DTO.VerbaleDTO;

public class GestioneVerbaleDAO {
	
	public static List<VerbaleDTO> getListaVerbali(Session session, UtenteDTO user){
		Query query=null;
		
		boolean ck=user.checkRuolo("AM");
		if(user.getTipoutente().equals("2") && ck==false) 
		{
		 
		query  = session.createQuery( "from VerbaleDTO WHERE type = :_type AND intervento.user.id=:_idUser");
		query.setParameter("_type",VerbaleDTO.VERBALE);
		query.setParameter("_idUser",user.getId());
		}
		else 
		{
			 query  = session.createQuery( "from VerbaleDTO WHERE type = :_type");
			query.setParameter("_type",VerbaleDTO.VERBALE);
		}
		List<VerbaleDTO> result = query.list();
		return result;
	}

	public static void save(VerbaleDTO verbale, Session session) {
		session.save(verbale);
	}

	public static VerbaleDTO  getVerbale(String idVerbale, Session session) {
		
		Query query=null;
		VerbaleDTO verbale=null;
		try {
		
			String s_query = "from VerbaleDTO WHERE id = :_id";
			query = session.createQuery(s_query);
			query.setParameter("_id",Integer.parseInt(idVerbale));			
			
			if(query.list().size()>0){	
				return (VerbaleDTO) query.list().get(0);
			}
			return null;
						

	    } catch(Exception e){
	    	e.printStackTrace();
	    }
		return verbale;		
	}
	
	public static VerbaleDTO getVerbaleFromSkTec(String idSchedaTecnica, Session session) {
		
		Query query=null;
		VerbaleDTO verbale=null;
		try {
		
			String s_query = "from VerbaleDTO WHERE schedaTecnica.id = :_id";
			query = session.createQuery(s_query);
			query.setParameter("_id",Integer.parseInt(idSchedaTecnica));			
			
			if(query.list().size()>0){	
				return (VerbaleDTO) query.list().get(0);
			}
			return null;
						

	    } catch(Exception e){
	    	e.printStackTrace();
	    }
		return verbale;		
	}
	
	public static List<VerbaleDTO> getVerbaliConQuestionarioAggiornato(int idQuestionarioOld, Session session) {
		
		Query query=session.createQuery("from VerbaleDTO WHERE stato.id = :_stato and questionarioID = :_questionarioOld and type = :_type");	
			query.setParameter("_stato",StatoVerbaleDTO.CREATO);	
			query.setParameter("_questionarioOld",idQuestionarioOld);
			query.setParameter("_type",VerbaleDTO.VERBALE);
			List<VerbaleDTO> result = query.list();
			return result;
	}

	public static synchronized ProgressivoVerbaleDTO getProgressivoVerbale(UtenteDTO utente, TipoVerificaDTO tipo, Session session) {
		Query query = session.createQuery("from ProgressivoVerbaleDTO where idUtente= :_idUtente and idTipo= :_idTipo");
		query.setInteger("_idTipo", tipo.getId());
		query.setInteger("_idUtente", utente.getId());
		ProgressivoVerbaleDTO progressivo = (ProgressivoVerbaleDTO) query.uniqueResult();
		if(progressivo == null) progressivo= new ProgressivoVerbaleDTO(utente.getId(), tipo.getId());
		progressivo.incrementaProgressivo();
		session.saveOrUpdate(progressivo);
		return progressivo;
	}
	
}

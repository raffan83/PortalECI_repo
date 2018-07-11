package it.portalECI.bo;

import it.portalECI.DAO.GestioneInterventoDAO;
import it.portalECI.DAO.GestioneStatoInterventoDAO;
import it.portalECI.DTO.CategoriaVerificaDTO;
import it.portalECI.DTO.InterventoDTO;
import it.portalECI.DTO.QuestionarioDTO;
import it.portalECI.DTO.StatoInterventoDTO;
import it.portalECI.DTO.TipoVerificaDTO;
import it.portalECI.DTO.VerbaleDTO;

import java.util.ArrayList;

import java.util.List;

import org.hibernate.Session;

public class GestioneInterventoBO {

	public static List<InterventoDTO> getListaInterventi(String idCommessa, Session session) throws Exception {
		return GestioneInterventoDAO.getListaInterventi(idCommessa,session);
	}
	
	public static boolean scaricaIntervento(InterventoDTO intervento, Session session) {
		boolean result=false;
		
			if(buildVerbali( intervento, session)) {
				intervento.cambioStatoIntervento(GestioneStatoInterventoDAO.getStatoInterventoById(StatoInterventoDTO.SCARICATO, session));
				update(intervento, session);
				result=true;
			}
		return result;
	}

	private static boolean buildVerbali(InterventoDTO intervento, Session session) {
		//TODO BUILD REAL INSTANCE OF QUESTIONARIO FOR THIS INTERVENTO
		boolean result =true;
		if(intervento!=null) {
			for(VerbaleDTO verbale:intervento.getVerbali()) {
				//retrieve questionario for tipoVerificaCodice
				verbale= GestioneVerbaleBO.buildVerbaleByQuestionario(verbale, session);
				System.out.println("verbale TROVATO : "+(verbale!=null?verbale.getId():-1));
				if(verbale==null) {
					result=false;
				}
			}
		}else {
			result=false;
		}
		return result;
	}

	public static Integer save(InterventoDTO intervento, Session session) throws Exception {
		return (Integer) session.save(intervento);			
	}

	public static ArrayList<TipoVerificaDTO> getTipoVerifica(Session session){
		return GestioneInterventoDAO.getListaTipoVerifica(session);
	}
	
	
	public static TipoVerificaDTO getTipoVerifica(String id,  Session session) {
		return GestioneInterventoDAO.getTipoVerifica(id,  session);
	}
	
	public static ArrayList<CategoriaVerificaDTO> getCategoriaVerifica(Session session){
		return GestioneInterventoDAO.getListaCategoriaVerifica(session);
	}

	public static CategoriaVerificaDTO getCategoriaVerifica(String id,  Session session) {
		return GestioneInterventoDAO.getCategoriaVerifica(id, session);
	}

	public static InterventoDTO getIntervento(String idIntervento,Session session) {
		return GestioneInterventoDAO.getIntervento(idIntervento, session);
	}



	public static void update(InterventoDTO intervento, Session session) {		
		session.update(intervento);
	}


	public static ArrayList<InterventoDTO> getListaInterventiDaSede(String idCliente, String idSede, Integer idCompany,
			Session session) {
		// TODO Auto-generated method stub
		return GestioneInterventoDAO.getListaInterventiDaSede(idCliente,idSede,idCompany, session);
	}

	public static ArrayList<Integer> getListaClientiInterventi() {
		// TODO Auto-generated method stub
		return GestioneInterventoDAO.getListaClientiInterventi();
	}

	public static ArrayList<Integer> getListaSediInterventi() {
		// TODO Auto-generated method stub
		return GestioneInterventoDAO.getListaSediInterventi();
	}
	
	public static ArrayList<InterventoDTO> getListaInterventiDownload(Session session, int idTecnicoVerificatore) {
		// TODO Auto-generated method stub
		return GestioneInterventoDAO.getListaInterventiDownload( session,  idTecnicoVerificatore);
	}
	
	public static ArrayList<InterventoDTO> getListaInterventiTecnico(Session session, int idTecnicoVerificatore) {
		// TODO Auto-generated method stub
		return GestioneInterventoDAO.getListaInterventiTecnico( session,  idTecnicoVerificatore);
	}
	
	public static InterventoDTO getInterventoTecnico(Session session, int idTecnicoVerificatore , int idIntervento) {
		// TODO Auto-generated method stub
		return GestioneInterventoDAO.getInterventoTecnico( session,  idTecnicoVerificatore,idIntervento);
	}
	
	/*public static List<Map> getTuttiInterventi(Session session) throws Exception{
		// TODO Auto-generated method stub
		return GestioneInterventoDAO.getTuttiInterventi( session);
	}*/
}
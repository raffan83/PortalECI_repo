package it.portalECI.bo;

import it.portalECI.DAO.GestioneInterventoDAO;
import it.portalECI.DTO.CategoriaVerificaDTO;
import it.portalECI.DTO.InterventoDTO;
import it.portalECI.DTO.TipoVerificaDTO;

import java.util.ArrayList;

import java.util.List;

import org.hibernate.Session;

public class GestioneInterventoBO {

	public static List<InterventoDTO> getListaInterventi(String idCommessa, Session session) throws Exception {


		return GestioneInterventoDAO.getListaInterventi(idCommessa,session);
	}

	public static void save(InterventoDTO intervento, Session session) throws Exception {

		session.save(intervento);

		
		
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

	public static InterventoDTO getIntervento(String idIntervento) {

		return GestioneInterventoDAO.getIntervento(idIntervento);

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

}
package it.portalECI.bo;

import it.portalECI.DAO.GestioneInterventoDAO;
import it.portalECI.DAO.SQLLiteDAO;
import it.portalECI.DAO.SessionFacotryDAO;

import it.portalECI.DTO.CommessaDTO;
import it.portalECI.DTO.InterventoDTO;

import it.portalECI.DTO.UtenteDTO;
import it.portalECI.Util.Costanti;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.fileupload.FileItem;
import org.hibernate.Session;

public class GestioneInterventoBO {

	public static List<InterventoDTO> getListaInterventi(String idCommessa, Session session) throws Exception {


		return GestioneInterventoDAO.getListaInterventi(idCommessa,session);
	}

	public static void save(InterventoDTO intervento, Session session) throws Exception {

		session.save(intervento);

		
		
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

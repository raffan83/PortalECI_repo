package it.portalECI.action;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import it.portalECI.DAO.DirectMySqlDAO;
import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.AttrezzaturaDTO;
import it.portalECI.bo.GestioneAttrezzatureBO;
import it.portalECI.bo.GestioneComunicazioniBO;

public class ControlloScadenzaVentennaleScheduler implements Job{
	
	static final Logger logger = Logger.getLogger(ControlloScadenzaVentennaleScheduler.class);
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		try {
			Session session = SessionFacotryDAO.get().openSession();	    
			session.beginTransaction();
			
			ArrayList<AttrezzaturaDTO>lista_attrezzature_scadenza = GestioneAttrezzatureBO.getAttrezzatureScadenzaVentennale(session);
			if(lista_attrezzature_scadenza.size()>0) {
				GestioneComunicazioniBO.sendEmailScadenzaVentennale(lista_attrezzature_scadenza, session);	
			}
			
			
			logger.error("Controllo scadenza ventennale attrezzature eseguito con successo dallo scheduler di Quartz!");
			
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}

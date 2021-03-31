package it.portalECI.action;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import it.portalECI.DAO.DirectMySqlDAO;
import it.portalECI.DAO.GestioneCampioneDAO;


public class AggiornamentoCampioneScheduler implements Job{
	static final Logger logger = Logger.getLogger(AggiornamentoCampioneScheduler.class);
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		try {
			DirectMySqlDAO.aggiornaCampioniScadenza();
			
			logger.error("Aggiornamento Stato Campione eseguito con successo dallo scheduler di Quartz!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}

package it.portalECI.action;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import it.portalECI.DAO.DirectMySqlDAO;
import it.portalECI.DAO.GestioneCampioneDAO;



public class AggiornaCampioneScheduler implements Job{
	static final Logger logger = Logger.getLogger(AggiornaCampioneScheduler.class);
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {

		try {
			GestioneCampioneDAO.updateCampioneScheduler();

			logger.debug("Aggiornamento Stato Campione eseguito con successo dallo scheduler di Quartz!");
			logger.error("Aggiornamento Stato Campione eseguito con successo dallo scheduler di Quartz!");
			
			
		} catch (Exception e) {
		
			e.printStackTrace();
		}
		
	}
	
	

}

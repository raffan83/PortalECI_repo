package it.portalECI.action;

import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import it.portalECI.DAO.DirectMySqlDAO;
import it.portalECI.DAO.GestioneCampioneDAO;
import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.AcAttivitaCampioneDTO;
import it.portalECI.DTO.AcTipoAttivitaCampioniDTO;


public class AggiornamentoCampioneScheduler implements Job{
	static final Logger logger = Logger.getLogger(AggiornamentoCampioneScheduler.class);
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		try {
			ArrayList<Integer> list = DirectMySqlDAO.aggiornaCampioniScadenza();
			
			if(list!=null && list.size()>0) {
				Session session = SessionFacotryDAO.get().openSession();	    
				session.beginTransaction();
				
				for (Integer cmp : list) {
					AcAttivitaCampioneDTO attivita = new AcAttivitaCampioneDTO();
					attivita.setTipo_attivita(new AcTipoAttivitaCampioniDTO(5, ""));
					attivita.setData(new Date());
					attivita.setCampione(GestioneCampioneDAO.getCampioneFromId(""+cmp, session));
					
					session.save(attivita);
				}
				
				session.getTransaction().commit();
				session.close();
				
			}
			
			
			logger.error("Aggiornamento Stato Campione eseguito con successo dallo scheduler di Quartz!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
